package de.ostfalia.se.talsperre.control;

import de.ostfalia.se.control.AbstractReadOnlyService;
import de.ostfalia.se.talsperre.entity.Talsperrendaten;
import de.ostfalia.se.talsperre.entity.TalsperrendatenID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;



public class TalsperrendatenService extends AbstractReadOnlyService<Talsperrendaten, TalsperrendatenID> {

    @PersistenceContext(unitName = "Talsperrendaten")
    EntityManager em;

    @Override
    protected Class<Talsperrendaten> getEntityClass() {
        return Talsperrendaten.class;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<Talsperrendaten> getByTalsperrenIDWithLimit(int idTalsperre, int limit) {
        TypedQuery<Talsperrendaten> query = em.createNamedQuery("talsperrendaten.getByTalsperrenIDWithLimit", Talsperrendaten.class);
        query.setParameter("idTalsperre", idTalsperre);
        query.setMaxResults(limit);
        return query.getResultList();

    }

    public List<Talsperrendaten> getByTalsperrenIDWithTimeLimits(int idTalsperre, LocalDateTime from, LocalDateTime to) {
        TypedQuery<Talsperrendaten> query = em.createNamedQuery("talsperrendaten.getByTalsperrenIDWithTimeLimits", Talsperrendaten.class);
        query.setParameter("idTalsperre", idTalsperre);
        query.setParameter("from", from == null ? LocalDate.MIN : from);
        query.setParameter("to", to == null ? LocalDate.now() : to);
        Logger.getLogger(TalsperrendatenService.class.getSimpleName()).info("Found Entrys: " + query.getResultList().size());
        return query.getResultList();

    }

    public List<Talsperrendaten> getByTalsperrenID(int idTalsperre) {
        TypedQuery<Talsperrendaten> query = em.createNamedQuery("talsperrendaten.getByTalsperrenID", Talsperrendaten.class);
        query.setParameter("idTalsperre", idTalsperre);
        return query.getResultList();
    }


    public Talsperrendaten getLast(int idTalsperre) {
        TypedQuery<Talsperrendaten> query = em.createNamedQuery("talsperrendaten.getByTalsperrenID", Talsperrendaten.class);
        query.setParameter("idTalsperre", idTalsperre);
        query.setFirstResult(0);
        query.setMaxResults(1);
        return query.getSingleResult();
    }


    public Talsperrendaten vor12h(int idTalsperre) {

        Talsperrendaten first = getLast(idTalsperre);

        TypedQuery<Talsperrendaten> daten = em.createNamedQuery("talsperrendaten.getByTalsperrenIDBeforeTime", getEntityClass());
        daten.setParameter("idTalsperre", idTalsperre);
        daten.setParameter("tBefore", first.getTstamp().minus(Duration.ofHours(12)));
        daten.setMaxResults(1);
        return daten.getSingleResult();


    }


    @Override
    protected TypedQuery<Talsperrendaten> getAllQuery() {

        return em.createNamedQuery("talsperrendaten.getAll", getEntityClass());

    }
}
