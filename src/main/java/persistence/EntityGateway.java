package persistence;

import java.util.List;

/**
 * This interface is used to interact with a persistence context.
 * The EntityGateway is used to create, remove and retrieve entity instances.
 *
 * @author DiegoDc 2016-09-10
 */
public interface EntityGateway {

    /**
     * Persists the given entity.
     *
     * @param entity the entity to be persisted
     */
    void persist(Object entity);

    /**
     * Removes the given entity.
     *
     * @param entity the entity to be removed
     */
    void remove(Object entity);

    /**
     * Checks if an entity of the specified class and id exists in the repositoy.
     *
     * @param entityClass entity class
     * @param id entity id
     * @param <T> entity
     * @return true if the object exists in the repository
     */
    <T> boolean exists(Class<T> entityClass, String id);

    /**
     * Searchs for an entity of the specified class and id.
     *
     * @param entityClass entity class
     * @param id entity id
     * @param <T> entity
     * @return the found entity
     */
    <T> T findBy(Class<T> entityClass, String id);

    /**
     * Searchs for all entities of the specified class and id.
     *
     * @param <T> entity
     * @param entityClass entity class
     * @return the list of found entities
     */
    <T> List<T> findAllOf(Class<T> entityClass);
}
