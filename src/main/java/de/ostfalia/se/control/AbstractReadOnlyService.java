package de.ostfalia.se.control;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;


/**
 * This is a base class for defining an application level entry
 * point into the persistence layer. It acts as a kind of
 * middleware between the application logic layer and JPA
 * Persistence Layer (which itself is another middleware between
 * the Database and the Java Object Model).
 *
 * It defines, prepares and executes database transactions and
 * queries on a table of an entity type T</br>
 *
 * This Base-Class only provides RO Access,
 * @see AbstractService for a fully featured version
 *
 * @param <T> Type of managed {@link Entity}
 * @param <I> Type of primary key
 */
public abstract class AbstractReadOnlyService<T, I> {

	/**
	 * Getter for Type T
	 * 
	 * @return Class&lt;T&gt; 
	 */
    protected abstract Class<T> getEntityClass();

	/**
	 * getter for the EntityManager (desired instance has to be
	 * injected into the child class via CDI by WELD)
	 * 
	 * @return EntityManager 
	 */
    protected abstract EntityManager getEntityManager();

    

    /**
     * Finds {@link Entity} managed by the service
     * @param id ID of the {@link Entity}
     * @return instance of the searched {@link Entity} or null
     */
    public T findById(I id) {
        return getEntityManager().find(getEntityClass(), id);
    }


    /**
     * Gets all DB Entries = Java Entities form the Table
     *
     * @return List of all entities
     */
    public List<T> getAll() {
        TypedQuery<T> query = getAllQuery();
        return query.getResultList();
    }

	/**
	 * Query that is executed to fulfill the getAll() Method
	 * 
	 * @return TypedQuery&lt;T&gt; 
	 */
    protected abstract TypedQuery<T> getAllQuery();

}
