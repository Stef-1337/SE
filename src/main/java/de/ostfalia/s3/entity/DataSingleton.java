package de.ostfalia.s3.entity;

import de.ostfalia.s2.fahrrad.control.BicycleService;
import de.ostfalia.s2.fahrrad.entity.Bicycle;
import lombok.Getter;

import javax.ejb.Local;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Singleton
public class DataSingleton implements Serializable {

    private static DataSingleton instance;

    public static DataSingleton getInstance() {
        if (instance == null) instance = new DataSingleton();

        System.out.println(instance);

        return instance;
    }

    private BicycleService bicycleService;

    @PersistenceContext(unitName = "Fahrraddaten")
    private EntityManager entityManager;

    private List<Integer> channels;

    @Getter
    private int index;

    private DataSingleton() {

    }

    private HashMap<Integer, List<Bicycle>> data = new HashMap<>();

    public List<Bicycle> getData(int channel) {
        if (data == null || data.size() <= 0) return List.of();

        return data.get(channel);
    }

    @Schedule(hour="*", minute="*", second="*", persistent = false)
    public void tick(){
        index++;
    }

    @Schedule(hour = "*", minute = "*", second = "*/60", persistent = false)
    public void call() {
        if (instance != this) instance = this;

        HashMap<Integer, List<Bicycle>> data = new HashMap<>();

        for (int channel : getChannels()) {
            TypedQuery<Bicycle> query = entityManager.createNamedQuery("bicycle.getByBicycleChannelWithTimeLimits", Bicycle.class);

            LocalDateTime to = LocalDateTime.now();
            to = LocalDateTime.of(2022, 12, 9, 8, 30);
            //TODO entfernen, wenn Datenbank wieder geht

            LocalDateTime from = to.minusMinutes(1);

            query.setParameter("channelBicycle", channel);
            query.setParameter("from", from);
            query.setParameter("to", to);

            List<Bicycle> results = query.getResultList();
            data.put(channel, results);
        }

        index = 0;

        System.out.println("Fetched data for " + channels);
        this.data = data;
    }

    private List<Integer> getChannels() {
        if (channels == null) channels = getBicycleIDs();

        return channels;
    }

    private List<Integer> getBicycleIDs() {
        List<Integer> list = new ArrayList<>();

        List<Bicycle> channelBicycle = entityManager.createNamedQuery("bicycle.getBicycleIDs", Bicycle.class).getResultList();

        channelBicycle.forEach(bicycle -> list.add(bicycle.getChannel()));

        return list;
    }

}
