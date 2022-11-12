package de.ostfalia.s2.fahrrad.control;

import de.ostfalia.s2.control.AbstractReadOnlyService;
import de.ostfalia.s2.fahrrad.entity.Bicycle;
import de.ostfalia.s2.fahrrad.entity.BicycleID;

import javax.enterprise.context.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

@SessionScoped
public class BicycleService extends AbstractReadOnlyService<Bicycle, BicycleID> implements Serializable {
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

    /**
     * @param channel Bicycle channel
     * @param from    Min timestamp
     * @param to      Max timestamp
     * @param step    Step between each entry in millis
     * @return Matching bicycles
     */
    public List<Bicycle> getFahrradDaten(int channel, LocalDateTime from, LocalDateTime to, long step) {
        TypedQuery<Bicycle> query = em.createNamedQuery("bicycle.getByBicycleChannelWithTimeLimits", Bicycle.class);

        query.setParameter("channelBicycle", channel);
        query.setParameter("from", from == null ? LocalDateTime.MIN : from);
        query.setParameter("to", to == null ? LocalDateTime.now() : to);

        return query.getResultList();


//        List<Bicycle> results = new ArrayList<>();
//        bicycles.sort(Comparator.comparing(Bicycle::getTimestamp));
//        LocalDateTime last = LocalDateTime.MIN;
//        for(Bicycle bike : bicycles){
//            if(bike.getTimestamp().isAfter(last.plus(step, ChronoUnit.MILLIS))){
//                results.add(bike);
//                last = bike.getTimestamp();
//            }
//        }

//        return results;
    }

    public List<Bicycle> getByFahrradDatenChannelWithLimit(int channel, int limit) {
        TypedQuery<Bicycle> query = em.createNamedQuery("bicycle.getByBicycleChannelWithLimit", Bicycle.class);
        query.setParameter("channelBicycle", channel);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    public List<Bicycle> getByFahrradDatenChannelWithTimeLimits(int channel, LocalDateTime from, LocalDateTime to) {
        TypedQuery<Bicycle> query = em.createNamedQuery("bicycle.getByBicycleChannelWithTimeLimits", Bicycle.class);
        query.setParameter("channelBicycle", channel);
        query.setParameter("from", from == null ? LocalDateTime.MIN : from);
        query.setParameter("to", to == null ? LocalDateTime.now() : to);
        Logger.getLogger(BicycleService.class.getSimpleName()).info("Found Entrys " + query.getResultList().size());
        return query.getResultList();
    }

    public List<Bicycle> getByChannel(int channel) {
        TypedQuery<Bicycle> query = em.createNamedQuery("bicycle.getByBicycleChannel", Bicycle.class);
        query.setParameter("channelBicycle", channel);
        return query.getResultList();
    }

    public Bicycle getLast(int channel) {
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

    public List<Bicycle> over(int channel) {
        TypedQuery<Bicycle> query = em.createNamedQuery("bicycle.getByBicycleChannelOverOneWeek", Bicycle.class);
        query.setParameter("channelBicycle", channel);
        return query.getResultList();
    }

    @Override
    protected TypedQuery<Bicycle> getAllQuery() {
        return em.createNamedQuery("bicycle.getAll", getEntityClass());
    }
}
