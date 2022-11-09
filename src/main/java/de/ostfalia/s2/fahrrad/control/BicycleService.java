package de.ostfalia.s2.fahrrad.control;

import de.ostfalia.s2.control.AbstractReadOnlyService;
import de.ostfalia.s2.fahrrad.entity.Bicycle;
import de.ostfalia.s2.fahrrad.entity.BicycleID;

import javax.ejb.Local;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.GregorianCalendar;

public class BicycleService extends AbstractReadOnlyService<Bicycle, BicycleID> {
//test
    @PersistenceContext(unitName = "Fahrraddaten")
    EntityManager em;
    @Override
    protected Class<Bicycle> getEntityClass() {
        return Bicycle.class;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }


    public List<Bicycle> getByFahrradDatenChannelWithLimit(int channel, int limit) {
        TypedQuery<Bicycle> query = em.createNamedQuery("bicycle.getByBicycleChannelWithLimit", Bicycle.class);
        query.setParameter("channelBicycle", channel );
        query.setMaxResults(limit);
        return query.getResultList();
    }

    public List<Bicycle> getByFahrradDatenChannelWithTimeLimits(int channel, LocalDateTime from, LocalDateTime to){
        TypedQuery<Bicycle> query = em.createNamedQuery("bicycle.getByBicycleChannelWithTimeLimits", Bicycle.class);
        query.setParameter("channelBicycle", channel);
        query.setParameter("from", from == null ? LocalDateTime.MIN : from);
        query.setParameter("to", to == null ? LocalDateTime.now() : to);
        Logger.getLogger(BicycleService.class.getSimpleName()).info("Found Entrys " + query.getResultList().size());
        return query.getResultList();
    }

    public Bicycle getByFahrradDatenChannelWithTimestamp(int channel, LocalDateTime timestamp) {
        TypedQuery<Bicycle> query = em.createNamedQuery("bicycle.getByBicycleChannelWithTimestamp", Bicycle.class);
        query.setParameter("channelBicycle", channel);
        query.setParameter("timestamp", timestamp);
        List<Bicycle> resultList = query.getResultList();

        if(resultList.isEmpty()){
            return null;
        }
        return resultList.get(0);
    }
    //Variante 3
    public List<Bicycle> getByFahrradDatenChannelWithTimeUsage(int channel, LocalDateTime timestamp, boolean before){
        Bicycle tempBicycle = getByFahrradDatenChannelWithTimestamp(channel, timestamp);
        while(tempBicycle == null) {
            if(before == true) {
                timestamp = timestamp.minusSeconds(1);
            } else {
                timestamp = timestamp.plusSeconds(1);
            }

            tempBicycle = getByFahrradDatenChannelWithTimestamp(channel, timestamp);
        }

        List<Bicycle> resultList = new ArrayList<>();

        while (tempBicycle != null){
            resultList.add(tempBicycle);

            if(before == true) {
                timestamp = timestamp.minusSeconds(1);
            } else {
                timestamp = timestamp.plusSeconds(1);
            }

            tempBicycle = getByFahrradDatenChannelWithTimestamp(channel, timestamp);
        }

        return resultList;
    }

    public int sumInterval(int channel, LocalDateTime start, LocalDateTime end) {
        List<Bicycle> bicycles = getByFahrradDatenChannelWithTimeLimits(channel, start, end);
        int rotations = 0;

        //Den Wert der Rotationen pro Sekunde zum vorherigen Wert addieren
        for (Bicycle bicycle:bicycles){
            rotations += bicycle.getRotations_per_second();
        }
        return rotations;
    }

    public double averageInterval(int channel, LocalDateTime start, LocalDateTime end) {
        List<Bicycle> bicycles = getByFahrradDatenChannelWithTimeLimits(channel, start, end);
        int rotations = 0;
        int count = bicycles.size();

        for (Bicycle bicycle:bicycles) {
            rotations += bicycle.getRotations_per_second();
        }

        double average = rotations / count;

        return average;
    }

    public double averageSpeedInterval(int channel, LocalDateTime start, LocalDateTime end) {
        List <Bicycle> bicycles = getByFahrradDatenChannelWithTimeLimits(channel, start, end);
        double distance = 0.0;
        for (Bicycle bicycle:bicycles) {
            distance += bicycle.getRotations_per_second() * 2.111;
        }

        double speed = (distance / (end.getSecond() - start.getSecond()));
        return speed;
    }

    public List<Double> distanceInterval(int channel, LocalDateTime start, LocalDateTime end, int minutes) {
        List<Double> distance = new ArrayList<>();
        LocalDateTime tempIntervalStart = start;
        LocalDateTime tempIntervalEnd = start;
        tempIntervalEnd = tempIntervalEnd.plusMinutes(minutes);

        while (tempIntervalEnd.getSecond() < end.getSecond()) {
            int rotations = sumInterval(channel, tempIntervalStart, tempIntervalEnd);

            distance.add(rotations * 2.111);

            tempIntervalStart = tempIntervalStart.plusMinutes(minutes);
            tempIntervalEnd = tempIntervalEnd.plusMinutes(minutes);
        }

        int rotations = sumInterval(channel, tempIntervalStart, end);
        distance.add(rotations * 2.111);

        return distance;
    }


    public List<Bicycle> getByChannel(int channel){
        TypedQuery<Bicycle> query = em.createNamedQuery("bicycle.getByBicycleChannel", Bicycle.class);
        query.setParameter("channelBicycle", channel);
        return query.getResultList();
    }
    public Bicycle getLast(int channel){
        TypedQuery<Bicycle> query = em.createNamedQuery("bicycle.getByBicycleChannel", Bicycle.class);
        query.setParameter("channelBicycle", channel);
        query.setFirstResult(0);
        query.setMaxResults(1);
        return query.getSingleResult();
    }

    public Bicycle vor12h(int channelBicycle) {

        Bicycle first = getLast(channelBicycle);

        TypedQuery<Bicycle> daten = em.createNamedQuery("bicycle.getByBicycleChannelBeforeTime", getEntityClass());
        daten.setParameter("channelBicycle", channelBicycle);
        daten.setParameter("tBefore", first.getTimestamp().minus(Duration.ofHours(12)));
        daten.setMaxResults(1);
        return daten.getSingleResult();


    }

    @Override
    protected TypedQuery<Bicycle> getAllQuery() {
        return em.createNamedQuery("bicycle.getAll",getEntityClass());
    }
}
