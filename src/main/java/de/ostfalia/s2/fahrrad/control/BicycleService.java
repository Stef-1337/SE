package de.ostfalia.s2.fahrrad.control;

import de.ostfalia.s2.control.AbstractReadOnlyService;
import de.ostfalia.s2.fahrrad.entity.Bicycle;
import de.ostfalia.s2.fahrrad.entity.FahrradDatenID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

public class BicycleService extends AbstractReadOnlyService<Bicycle, FahrradDatenID> {

    @PersistenceContext(unitName = "Bicycle")
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
        TypedQuery<Bicycle> query = em.createNamedQuery("bicycle.getByFahrradDat" +
                "enChannelWithLimit", Bicycle.class);
        query.setParameter("channel", channel);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    public List<Bicycle> getByFahrradDatenChannelWithTimeLimits(int channel, LocalDateTime from, LocalDateTime to){
        TypedQuery<Bicycle> query = em.createNamedQuery("bicycle.getByFahrradDatenChannelWithTimeLimits", Bicycle.class);
        query.setParameter("channel", channel);
        query.setParameter("from", from);
        query.setParameter("to", to);
        Logger.getLogger(BicycleService.class.getSimpleName()).info("Found Entrys " + query.getResultList().size());
        return query.getResultList();
    }

    public List<Bicycle> getByChannel(int channel){
        TypedQuery<Bicycle> query = em.createNamedQuery("bicycle.getByChannel", Bicycle.class);
        query.setParameter("channel", channel);
        return query.getResultList();
    }
    public Bicycle getLast(int channel){
        TypedQuery<Bicycle> query = em.createNamedQuery("bicycle.getByChannel", Bicycle.class);
        query.setParameter("channel", channel);
        query.setFirstResult(0);
        query.setMaxResults(1);
        return query.getSingleResult();
    }

    @Override
    protected TypedQuery<Bicycle> getAllQuery() {
        return em.createNamedQuery("bicycle.getAll",getEntityClass());
    }
}
