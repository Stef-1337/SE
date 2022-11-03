package de.ostfalia.s2.fahrrad.control;

import de.ostfalia.s2.control.AbstractReadOnlyService;
import de.ostfalia.s2.fahrrad.entity.Bicycle;
import de.ostfalia.s2.fahrrad.entity.BicycleID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

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
