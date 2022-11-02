package de.ostfalia.s2.fahrrad.control;

import de.ostfalia.s2.control.AbstractReadOnlyService;
import de.ostfalia.s2.fahrrad.entity.FahrradDaten;
import de.ostfalia.s2.fahrrad.entity.FahrradDatenID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

public class FahrradDatenService extends AbstractReadOnlyService<FahrradDaten, FahrradDatenID> {

    @PersistenceContext(unitName = "FahrradDaten")
    EntityManager em;
    @Override
    protected Class<FahrradDaten> getEntityClass() {
        return FahrradDaten.class;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }


    public List<FahrradDaten> getByFahrradDatenChannelWithLimit(int channel, int limit) {
        TypedQuery<FahrradDaten> query = em.createNamedQuery("bicycle.getByFahrradDatenChannelWithLimit", FahrradDaten.class);
        query.setParameter("channel", channel);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    public List<FahrradDaten> getByFahrradDatenChannelWithTimeLimits(int channel, LocalDateTime from, LocalDateTime to){
        TypedQuery<FahrradDaten> query = em.createNamedQuery("bicycle.getByFahrradDatenChannelWithTimeLimits", FahrradDaten.class);
        query.setParameter("channel", channel);
        query.setParameter("from", from);
        query.setParameter("to", to);
        Logger.getLogger(FahrradDatenService.class.getSimpleName()).info("Found Entrys " + query.getResultList().size());
        return query.getResultList();
    }

    public List<FahrradDaten> getByChannel(int channel){
        TypedQuery<FahrradDaten> query = em.createNamedQuery("bicycle.getByChannel", FahrradDaten.class);
        query.setParameter("channel", channel);
        return query.getResultList();
    }
    public FahrradDaten getLast(int channel){
        TypedQuery<FahrradDaten> query = em.createNamedQuery("bicycle.getByChannel", FahrradDaten.class);
        query.setParameter("channel", channel);
        query.setFirstResult(0);
        query.setMaxResults(1);
        return query.getSingleResult();
    }

    @Override
    protected TypedQuery<FahrradDaten> getAllQuery() {
        return em.createNamedQuery("bicycle.getAll",getEntityClass());
    }
}
