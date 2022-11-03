package de.ostfalia.s2.fahrrad.boundary;

import de.ostfalia.s2.fahrrad.control.BicycleService;
import de.ostfalia.s2.fahrrad.entity.Bicycle;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScaleLabel;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartOptions;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.primefaces.model.charts.line.LineChartModel;


import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Named
@RequestScoped
public class BicycleLineChartView {
    @Inject
    BicycleService bs;

    List<Bicycle> daten;
    private final HashMap<String, LineChartModel> lineModelList = new HashMap<>();

    public void init(String key, String name, int channel, int limit){
        daten = bs.getByFahrradDatenChannelWithLimit(channel, limit);
        Collections.reverse(daten);
        init(key,name);
    }

    public void init(String key, String name, int channel){
        daten = bs.getByChannel(channel);
        init(key,name);
    }

    public void init(String key, String name, int channel, LocalDateTime from){
        daten = bs.getByFahrradDatenChannelWithTimeLimits(channel, from, null);
        init(key, name);
    }

    public void init(String key, String name, int channelBicycle, LocalDateTime from, LocalDateTime to){
        daten = bs.getByFahrradDatenChannelWithTimeLimits(channelBicycle, from, to);
    }

    public void init(String key, String name){
        LineChartModel lineModle = new LineChartModel();

        ChartData data = new ChartData();

        List<Object> values_rotations_per_second = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        DateTimeFormatter formatter = name.contains("Tage")?DateTimeFormatter.ofPattern("yyyyy MM dd"):DateTimeFormatter.ofPattern("HH:mm");

        for(int i = 0; i < daten.size(); i++){
            Bicycle bc = daten.get(i);
            values_rotations_per_second.add(bc.getRotations_per_second());
        }

        LineChartDataSet dataSet = new LineChartDataSet();
        dataSet.setData(values_rotations_per_second);
        dataSet.setFill(false);
        dataSet.setLabel("Rotation per Second");
        dataSet.setBorderColor("rgb(188, 155, 73");
        dataSet.setYaxisID("large-scale");
        data.addChartDataSet(dataSet);

        data.setLabels(labels);

        LineChartOptions options = new LineChartOptions();

        CartesianScales cartesianScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        linearAxes.setId("large-scale");
        linearAxes.setPosition("left");
        CartesianScaleLabel yLeftLabel = new CartesianScaleLabel();
        yLeftLabel.setDisplay(true);
        yLeftLabel.setLabelString("Rotation per Second");
        yLeftLabel.setFontColor("rgb(65, 139, 178)");
        linearAxes.setScaleLabel(yLeftLabel);

        CartesianLinearAxes linearAxes2 = new CartesianLinearAxes();
        linearAxes2.setId("small-scale");
        linearAxes2.setPosition("right");
        CartesianScaleLabel yRightLabel = new CartesianScaleLabel();
        yRightLabel.setDisplay(true);
        yRightLabel.setLabelString("Zu-/Abfluss in m^3/sec");

        linearAxes2.setScaleLabel(yRightLabel);

        cartesianScales.addYAxesData(linearAxes);
        cartesianScales.addYAxesData(linearAxes2);

        options.setScales(cartesianScales);

        Title title = new Title();
        title.setDisplay(true);
        title.setText(name);


        options.setTitle(title);

        lineModle.setOptions(options);
        lineModle.setData(data);


        lineModelList.put(key, lineModle);
    }

    public LineChartModel getLineModellLimit(int channelBicycle, int limit, String name) {
        String key = channelBicycle + "#L" +limit;
        if(!lineModelList.containsKey(key)) {
            init(key, name, channelBicycle, limit);
        }
        return lineModelList.get(key);
    }

    public LineChartModel getLineModel24h(int channelBicycle){
        String key = channelBicycle + "#24h";
        if (!lineModelList.containsKey(key)){
            init(key, "24 Stunden", channelBicycle, 96);
        }
        return lineModelList.get(key);
    }

    public LineChartModel getLineModel(int chanelBicycle){
        String key = "" + chanelBicycle;
        if(!lineModelList.containsKey(key)){
            init(key, "Seit Beginn der Aufzeichnungen", chanelBicycle);
        }
        return lineModelList.get(key);
    }

    public LineChartModel getLineModel14d(int channelBicycle) {
        String key = channelBicycle + "#14d";
        ZoneId zone = ZoneId.of("Europe/Berlin");
        LocalDateTime old = LocalDateTime.now(zone).minusDays(14);
        LocalDateTime now = LocalDateTime.now(zone);
        if (!lineModelList.containsKey(key)) {
            init(key,"14 Tage", channelBicycle, old, now);
        }

        return lineModelList.get(key);
    }



}
