package de.ostfalia.s2.fahrrad.control;

import de.ostfalia.s2.control.AbstractReadOnlyService;
import de.ostfalia.s2.fahrrad.entity.Fahrrad;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

public class FahrradService extends AbstractReadOnlyService<Fahrrad, Integer> {

    @PersistenceContext(unitName = "FahrradDaten")
    EntityManager em;


    @Override
    protected Class<Fahrrad> getEntityClass() {
        return null;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected TypedQuery<Fahrrad> getAllQuery() {
        return  em.createNamedQuery("bicycle.getAll", getEntityClass());
    }
}
