package de.ostfalia.se.control;


/**
 * This is a base class for defining an application level entry
 * point into the persistence layer. It acts as a kind of
 * middleware between the application logic layer and JPA
 * Persistence Layer (which itself is another middleware between
 * the Database and the Java Object Model).
 *
 * It defines, prepares(EM) and executes(EM) database
 * transactions and queries on a table of an entity type T</br>
 * Most of it's work is done by the EntityManager which is in
 * charge of flushing/committing the changes to the database
 * In doubt, take a look at @see javax.persistence.EntityManager
 *
 * This Base-Class only provides fully WR Access,
 * @see AbstractReadOnlyService for a RO version
 *
 * @param <T> Type of managed {@link javax.persistence.Entity}
 * @param <I> Type of primary key
 */
public abstract class AbstractService<T, I> extends AbstractReadOnlyService<T,I> {


	/**
	 * Registers an entity to be managed by the EM
	 * Usually, this is used to (db) create new row's in
	 * a table resp. (java) save a newly constructed object
	 * into the database. 
	 *
	 * @param entity
	 *
	 * @throw EntityExistsException , @see JPA
	 */
    public void create(T entity) {
        getEntityManager().persist(entity);
    }

 

    /**
     * Updates the value of a given {@link javax.persistence.Entity}
     *
     * @param entity Entity to update
	 * @return the managed instance of the entity that the state was
	 *  	   merged into
     */
    public T update(T entity) {
        return getEntityManager().merge(entity);
    }



    /**
	 * deletes the given {@link javax.persistence.Entity} from the
	 * table
     *
     * @param entity to remove
     */
    public void remove(T entity) {
		// Entity may not be managed by the current persistence context,
		// therefore merge it into the context which allows it to be safely
		// deleted by the EM
        entity = getEntityManager().merge(entity);
        getEntityManager().remove(entity);
    }



}
