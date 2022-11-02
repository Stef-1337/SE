package de.ostfalia.s2.control;

//import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public abstract class AbstractReadOnlyService <T, I>{
    protected abstract Class<T> getEntityClass();


    protected abstract EntityManager getEntityManager();

    public T findbyID (I id){
        return getEntityManager().find(getEntityClass(),id);
    }

    public List<T> getAll(){
        TypedQuery<T> query = getAllQuery();
        return query.getResultList();
    }

    protected abstract TypedQuery<T> getAllQuery();
}
