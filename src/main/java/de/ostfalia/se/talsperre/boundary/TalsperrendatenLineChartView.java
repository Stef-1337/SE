package de.ostfalia.se.talsperre.boundary;

import de.ostfalia.se.talsperre.control.TalsperrendatenService;
import de.ostfalia.se.talsperre.entity.Talsperrendaten;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScaleLabel;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;
import org.primefaces.model.charts.line.LineChartOptions;
import org.primefaces.model.charts.optionconfig.title.Title;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Backend Bean zum Generieren von LineCharts für die Talsperren.
 */


@Named
@RequestScoped
public class TalsperrendatenLineChartView {

    @Inject
    TalsperrendatenService tsds;

    List<Talsperrendaten> daten;


    private final HashMap<String, LineChartModel> lineModelList = new HashMap<>();

    public void init(String key,String name, int idTalsperre, int limit) {
        daten = tsds.getByTalsperrenIDWithLimit(idTalsperre,limit);
        Collections.reverse(daten);
        init(key,name);

    }


    public void init(String key,String name, int idTalsperre) {
        daten = tsds.getByTalsperrenID(idTalsperre);
        init(key,name);

    }

    public void init(String key,String name, int idTalsperre, LocalDateTime from) {
        daten = tsds.getByTalsperrenIDWithTimeLimits(idTalsperre, from, null);
        init(key,name);

    }


    public void init(String key,String name, int idTalsperre, LocalDateTime from, LocalDateTime to) {
        daten = tsds.getByTalsperrenIDWithTimeLimits(idTalsperre, from, to);
        init(key,name);
    }

    private void init(String key,String name) {
        LineChartModel lineModel = new LineChartModel();

        ChartData data = new ChartData();


        List<Object> valuesZufluss = new ArrayList<>();
        List<Object> valuesPegel = new ArrayList<>();
        List<Object> valuesAbfluss = new ArrayList<>();
        List<String> labels = new ArrayList<>();



        DateTimeFormatter formatter = name.contains("Tage")?DateTimeFormatter.ofPattern("yyyy MM dd"):DateTimeFormatter.ofPattern("HH:mm");

        for (int i = 0; i < daten.size(); i++) {
            Talsperrendaten tsd = daten.get(i);
            valuesZufluss.add(tsd.getZufluss());
            valuesPegel.add(tsd.getStauinhalt());
            valuesAbfluss.add(tsd.getAbgabe());
            labels.add(tsd.getTstamp().format(formatter));
        }


        LineChartDataSet dataSet = new LineChartDataSet();
        dataSet.setData(valuesZufluss);
        dataSet.setFill(false);
        dataSet.setLabel("Zufluss");
        dataSet.setBorderColor("rgb(166, 184, 40)");
//        dataSet.setTension(0.8);
        dataSet.setYaxisID("small-scale");
        data.addChartDataSet(dataSet);

        dataSet = new LineChartDataSet();
        dataSet.setData(valuesPegel);
        dataSet.setFill(false);
        dataSet.setLabel("Stauinhalt");
        dataSet.setBorderColor("rgb(65, 139, 178)");
//        dataSet.setTension(0.5);
        dataSet.setYaxisID("large-scale");
        data.addChartDataSet(dataSet);


        dataSet = new LineChartDataSet();
        dataSet.setData(valuesAbfluss);
        dataSet.setFill(false);
        dataSet.setLabel("Abfluss");
        dataSet.setBorderColor("rgb(221, 85, 38)");
//        dataSet.setTension(0.2);
        dataSet.setYaxisID("small-scale");
        data.addChartDataSet(dataSet);

        data.setLabels(labels);

        //Options
        LineChartOptions options = new LineChartOptions();


        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        linearAxes.setId("large-scale");
        linearAxes.setPosition("left");
        CartesianScaleLabel yLeftLabel = new CartesianScaleLabel();
        yLeftLabel.setDisplay(true);
        yLeftLabel.setLabelString("Stauinhalt in Mio. m^3");
        yLeftLabel.setFontColor("rgb(65, 139, 178)");
        linearAxes.setScaleLabel(yLeftLabel);


        CartesianLinearAxes linearAxes2 = new CartesianLinearAxes();
        linearAxes2.setId("small-scale");
        linearAxes2.setPosition("right");
        CartesianScaleLabel yRightLabel = new CartesianScaleLabel();
        yRightLabel.setDisplay(true);
        yRightLabel.setLabelString("Zu-/Abfluss in m^3/sec");

        linearAxes2.setScaleLabel(yRightLabel);

        cScales.addYAxesData(linearAxes);
        cScales.addYAxesData(linearAxes2);


        options.setScales(cScales);



        Title title = new Title();
        title.setDisplay(true);
        title.setText(name);


        options.setTitle(title);

        lineModel.setOptions(options);
        lineModel.setData(data);


        lineModelList.put(key, lineModel);
    }

    public LineChartModel getLineModelLimit(int idTalsperre,int limit,String name) {
        String key = idTalsperre + "#L"+limit;
        if (!lineModelList.containsKey(key)) {
            init(key,name, idTalsperre, limit);
        }
        return lineModelList.get(key);
    }

    public LineChartModel getLineModel24h(int idTalsperre) {
        String key = idTalsperre + "#24h";
        if (!lineModelList.containsKey(key)) {
            init(key,"24 Stunden", idTalsperre, 96);
        }
        return lineModelList.get(key);
    }


    public LineChartModel getLineModel(int idTalsperre) {
        String key = "" + idTalsperre;
        if (!lineModelList.containsKey(key)) {

            init(key,"Seit Beginn der Aufzeichnung", idTalsperre);
        }


        return lineModelList.get(key);
    }


}
