package de.ostfalia.se.talsperre.control;

import de.ostfalia.se.control.AbstractReadOnlyService;
import de.ostfalia.se.talsperre.entity.Talsperre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 */

public class TalsperreService extends AbstractReadOnlyService<Talsperre,Integer> {

    @PersistenceContext(unitName = "Talsperrendaten")
    EntityManager em;

    @Override
    protected Class<Talsperre> getEntityClass() {
        return Talsperre.class;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected TypedQuery<Talsperre> getAllQuery() {
        return em.createNamedQuery("talsperre.getAll", getEntityClass());
    }
}
