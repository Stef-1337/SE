package de.ostfalia.s2.fahrrad.boundary;

import de.ostfalia.s2.fahrrad.control.BicycleService;
import de.ostfalia.s2.fahrrad.entity.Bicycle;
import de.ostfalia.s2.fahrrad.entity.BicycleDetailData;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScaleLabel;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartOptions;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.primefaces.model.charts.line.LineChartModel;


import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Named
@RequestScoped
public class BicycleLineChartView {
    //test
    @Inject
    BicycleService bs;

    @Inject
    BeanManager beanManager;

    private static final int STEPS = 12;

    private HashMap<Integer, BicycleDetailData> detailDatas = new HashMap<>();
    private List<Bicycle> daten;
    private final HashMap<String, LineChartModel> lineModelList = new HashMap<>();

    public void init(String key, String name, long step, boolean smoothed, Kennzahl type, List<Date> timeRange, Integer... channels) {
        LocalDateTime from, to;

        if (timeRange != null && timeRange.size() > 0) {
            timeRange.sort(Comparator.naturalOrder());
            from = timeRange.get(0).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            to = timeRange.get(1).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        } else {
            from = LocalDateTime.now().minus(12, ChronoUnit.HOURS);
            to = LocalDateTime.now();
        }

        if(step == -1){
            long timeIntervall = ChronoUnit.MILLIS.between(from, to);
            step = timeIntervall/STEPS;
        }

        for (Integer channel : channels) {
            if (channel == null || channel == -1)
                continue;

            daten = bs.getFahrradDaten(channel, from, to, step);

            DataOperation operation = new DataOperationOhneGlattung();
            if(smoothed){
                operation = new DataOperationMitGlattung();
            }

            detailDatas.put(channel, new BicycleDetailData(operation.operateData(type.getType(), daten, step), name, step, type.getType()));

            Bean<BicycleDetailView> bean = (Bean<BicycleDetailView>) beanManager.getBeans("bicycleDetailView").stream().filter(Objects::nonNull).findFirst().get();
            BicycleDetailView viewBean = (BicycleDetailView) beanManager.getReference(bean, BicycleDetailView.class, beanManager.createCreationalContext(bean));

            viewBean.setTotal(operation.getTotal());
            viewBean.setAverage(operation.getAverage());
        }
        initBicycleData(key, name);
    }

    private static final String[] COLORS = new String[]{"26, 79, 163", "33, 176, 28", "189, 25, 25", "166, 22, 166"};

    public void initBicycleData(String key, String name) {
        LineChartModel lineModel = new LineChartModel();
        ChartData data = new ChartData();
        int colorCount = 0;

        for (int channel : detailDatas.keySet()) {
            BicycleDetailData detailData = detailDatas.get(channel);
            List<Object> values = new ArrayList<>();
            List<String> labels = new ArrayList<>();

            for (int i = 0; i < detailData.getSize(); i++) {
                String label = detailData.getIntervalString(i);
                Object value = detailData.getValue(i);

                labels.add(label);
                values.add(value);
            }

            LineChartDataSet dataSet = new LineChartDataSet();
            dataSet.setData(values);
            dataSet.setFill(false);
            dataSet.setLabel(detailData.getName() + " " + channel);
            dataSet.setBorderColor("rgb(" + COLORS[colorCount++]);
            dataSet.setYaxisID("small-scale");
            data.addChartDataSet(dataSet);

            data.setLabels(labels);
        }

        LineChartOptions options = new LineChartOptions();

        CartesianScales cartesianScales = new CartesianScales();

        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        linearAxes.setId("small-scale");
        linearAxes.setPosition("right");
        CartesianScaleLabel yRightLabel = new CartesianScaleLabel();
        yRightLabel.setDisplay(true);
        yRightLabel.setLabelString("Fahrraddaten");

        linearAxes.setScaleLabel(yRightLabel);
        cartesianScales.addYAxesData(linearAxes);

        options.setScales(cartesianScales);

        Title title = new Title();
        title.setDisplay(true);
        title.setText(name);

        options.setTitle(title);

        lineModel.setOptions(options);
        lineModel.setData(data);

        lineModelList.put(key, lineModel);
    }

    public LineChartModel getLineModel(String name, long step, boolean smoothed, Kennzahl type, List<Date> timeRange, Integer... channels) {
        String key = channels[0] + "#" + name;
        if (!lineModelList.containsKey(key))
            init(key, name, step, smoothed, type, timeRange, channels);

        return lineModelList.get(key);
    }

    public LineChartModel getLineModel(String name, long step, boolean smoothed, Kennzahl type, List<Date> timeRange, Integer channel1, Integer channel2) {
        return getLineModel(name, step, smoothed, type, timeRange, new Integer[]{channel1, channel2});
    }

    public LineChartModel getLineModel(String name, long step, TimeUnit factor, boolean smoothed, Kennzahl type, List<Date> timeRange, Integer channel1, Integer channel2) {
        return getLineModel(name, step == -1 ? -1 : factor.toMillis(step), smoothed, type, timeRange, channel1, channel2);
    }

//
//    public void init(String key, String name, int channel, int limit) {
//        daten = bs.getByFahrradDatenChannelWithLimit(channel, limit);
//        Collections.reverse(daten);
//        init(key, name);
//    }
//
//    public void init(String key, String name, int channel) {
//        daten = bs.getByChannel(channel);
//        init(key, name);
//    }
//
//    public void init(String key, String name, int channel, LocalDateTime from) {
//        daten = bs.getByFahrradDatenChannelWithTimeLimits(channel, from, null);
//        init(key, name);
//    }
//
//    public void init(String key, String name, int channelBicycle, LocalDateTime from, LocalDateTime to) {
//        daten = bs.getByFahrradDatenChannelWithTimeLimits(channelBicycle, from, to);
//        init(key, name);
//    }
//
//
//    public void init(String key, String name) {
//        LineChartModel lineModle = new LineChartModel();
//
//        ChartData data = new ChartData();
//
//        List<Object> values_rotations_per_second = new ArrayList<>();
//        List<String> labels = new ArrayList<>();
//
//        DateTimeFormatter formatter = name.contains("Tage") ? DateTimeFormatter.ofPattern("yyyyy MM dd") : DateTimeFormatter.ofPattern("HH:mm");
//
//        for (int i = 0; i < daten.size(); i++) {
//            Bicycle bc = daten.get(i);
//            values_rotations_per_second.add(bc.getRotations_per_second());
//            labels.add(bc.getTimestamp().format(formatter));
//        }
//
//        LineChartDataSet dataSet = new LineChartDataSet();
//        dataSet.setData(values_rotations_per_second);
//        dataSet.setFill(false);
//        dataSet.setLabel("Rotations per Second");
//        dataSet.setBorderColor("rgb(166, 184, 40");
//        dataSet.setYaxisID("small-scale");
//        data.addChartDataSet(dataSet);
//
//        data.setLabels(labels);
//
//        LineChartOptions options = new LineChartOptions();
//
//        CartesianScales cartesianScales = new CartesianScales();
//        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
//        linearAxes.setId("large-scale");
//        linearAxes.setPosition("left");
//        CartesianScaleLabel yLeftLabel = new CartesianScaleLabel();
//        yLeftLabel.setDisplay(true);
//        yLeftLabel.setLabelString("Rotations per Second");
//        yLeftLabel.setFontColor("rgb(65, 139, 178)");
//        linearAxes.setScaleLabel(yLeftLabel);
//
//        CartesianLinearAxes linearAxes2 = new CartesianLinearAxes();
//        linearAxes2.setId("small-scale");
//        linearAxes2.setPosition("right");
//        CartesianScaleLabel yRightLabel = new CartesianScaleLabel();
//        yRightLabel.setDisplay(true);
//        yRightLabel.setLabelString("Zu-/Abfluss in m^3/sec");
//
//
//        linearAxes2.setScaleLabel(yRightLabel);
//        cartesianScales.addYAxesData(linearAxes);
//        cartesianScales.addYAxesData(linearAxes2);
//
//        options.setScales(cartesianScales);
//
//        Title title = new Title();
//        title.setDisplay(true);
//        title.setText(name);
//
//
//        options.setTitle(title);
//
//        lineModle.setOptions(options);
//        lineModle.setData(data);
//
//
//        lineModelList.put(key, lineModle);
//    }
//
//    public LineChartModel getLineModellLimit(int channelBicycle, int limit, String name) {
//        String key = channelBicycle + "#L" + limit;
//        if (!lineModelList.containsKey(key)) {
//            init(key, name, channelBicycle, limit);
//        }
//        return lineModelList.get(key);
//    }
//
//    public LineChartModel getLineModel(int channel, String type){
//        String key = channel + "";
//        if(!lineModelList.containsKey(key))
//            init(key, "", channel, type);
//
//        return lineModelList.get(key);
//    }
//
//    public LineChartModel getLineModel24hTest(int channelBicycle){
//        String key = channelBicycle + "#12hTest";
//        if(!lineModelList.containsKey(key))
//            initTest(key, "12 Stunden", 1000 * 60 * 60, channelBicycle, "DISTANCE");
//
//        return lineModelList.get(key);
//    }
//
//    public LineChartModel getLineModel24hDefault(int channelBicycle){
//        String key = channelBicycle + "#12hDefault";
//        if(!lineModelList.containsKey(key))
//            initTest(key, "12 Stunden", 1000 * 60 * 60, channelBicycle, "DEFAULT");
//
//        return lineModelList.get(key);
//    }
//
//    public LineChartModel getLineModel24h(int channelBicycle) {
//        String key = channelBicycle + "#12h";
//        if (!lineModelList.containsKey(key)) {
//            //init(key, "24 Stunden", channelBicycle, 96);
//            LocalDateTime now = LocalDateTime.now();
//
//            init(key, "12 Stunden", channelBicycle, now.minusHours(12), now);
//        }
//        return lineModelList.get(key);
//    }
//
//    public LineChartModel getLineModel(int chanelBicycle) {
//        String key = "" + chanelBicycle;
//        if (!lineModelList.containsKey(key)) {
//            init(key, "Seit Beginn der Aufzeichnungen", chanelBicycle);
//        }
//        return lineModelList.get(key);
//    }
//
//    public LineChartModel getLineModel14d(int channelBicycle) {
//        String key = channelBicycle + "#14d";
//        ZoneId zone = ZoneId.of("Europe/Berlin");
//        LocalDateTime old = LocalDateTime.now(zone).minusDays(14);
//        LocalDateTime now = LocalDateTime.now(zone);
//        if (!lineModelList.containsKey(key)) {
//            init(key, "14 Tage", channelBicycle, old, now);
//        }
//
//        return lineModelList.get(key);
//    }
//
//    public LineChartModel getLineModelMinus(int channelBicycle, int minus) {
//        String key = channelBicycle + "# " + minus + "d";
//        ZoneId zone = ZoneId.of("Europe/Berlin");
//        LocalDateTime old = LocalDateTime.now(zone).minusDays(minus);
//        LocalDateTime now = LocalDateTime.now(zone);
//        if (!lineModelList.containsKey(key)) {
//            init(key, minus + " Tage", channelBicycle, old, now);
//        }
//
//        return lineModelList.get(key);
//    }

}
