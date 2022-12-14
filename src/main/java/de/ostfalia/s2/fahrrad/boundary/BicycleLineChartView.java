package de.ostfalia.s2.fahrrad.boundary;

import de.ostfalia.s2.fahrrad.control.BicycleService;
import de.ostfalia.s2.fahrrad.data.template.DataOperation;
import de.ostfalia.s2.fahrrad.data.DataOperationMitGlattung;
import de.ostfalia.s2.fahrrad.data.DataOperationOhneGlattung;
import de.ostfalia.s2.fahrrad.entity.Bicycle;
import de.ostfalia.s2.fahrrad.entity.BicycleDetailData;
import de.ostfalia.s2.fahrrad.kennzahl.Kennzahl;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScaleTitle;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartOptions;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.primefaces.model.charts.line.LineChartModel;


import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Named
@RequestScoped
public class BicycleLineChartView {

    @Inject
    BicycleService bs;

    @Inject
    BeanManager beanManager;

    private static final int STEPS = 12;

    private HashMap<Integer, BicycleDetailData> detailDatas = new HashMap<>();
    private List<Bicycle> daten;
    private final HashMap<String, LineChartModel> lineModelList = new HashMap<>();

    private List<Bicycle> cache;

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

        if (step == -1) {
            long timeIntervall = ChronoUnit.MILLIS.between(from, to);
            step = timeIntervall / STEPS;
        }

        Bean<BicycleDetailView> bean = (Bean<BicycleDetailView>) beanManager.getBeans("bicycleDetailView").stream().filter(Objects::nonNull).findFirst().get();
        BicycleDetailView viewBean = (BicycleDetailView) beanManager.getReference(bean, BicycleDetailView.class, beanManager.createCreationalContext(bean));

        int count = 0;
        HashMap<Integer, List<Bicycle>> caches = viewBean.getCaches();
        if (caches == null) caches = new HashMap<>();

        for (Integer channel : channels) {
            if (channel == null || channel == -1)
                continue;

            if (!caches.containsKey(channel)) caches.put(channel, new ArrayList<>());

            List<Bicycle> cache = caches.get(channel);
            if (cache.size() <= 0) {
                cache = bs.getFahrradDaten(channel, from, to, step);
                caches.put(channel, cache);
            }

            daten = cache;

            DataOperation operation = new DataOperationOhneGlattung();
            if (smoothed) {
                operation = new DataOperationMitGlattung();
            }

            detailDatas.put(channel, new BicycleDetailData(operation.operateData(type.getType(), daten, step), name, step, type.getType()));

            double total = operation.getTotal();
            double average = operation.getAverage();

            if (count == 0) {
                viewBean.setTotal(total);
                viewBean.setAverage(average);
            } else {
                viewBean.setTotal2(total);
                viewBean.setAverage2(average);
            }
            count++;
        }
        viewBean.setCaches(caches);

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

        CartesianScaleTitle yRightLabel = new CartesianScaleTitle();
        yRightLabel.setDisplay(true);
        yRightLabel.setText("Fahrraddaten");

        linearAxes.setScaleTitle(yRightLabel);
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

}
