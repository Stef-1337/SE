package de.ostfalia.s2.control;

public abstract class AbstractService<T, I> extends AbstractReadOnlyService<T, I>{

    public void create(T entity){
        getEntityManager().persist(entity);
    }

    public T update(T entity){
        return getEntityManager().merge(entity);
    }

    public void remove(T entity){
        entity = getEntityManager().merge(entity);
        getEntityManager().remove(entity);
    }
}
