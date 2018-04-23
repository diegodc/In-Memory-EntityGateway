package persistence;

import java.util.*;

/**
 * Implementation of the EntityGateway interface using an in memory Map.
 *
 * @author DiegoDc 2016-09-10
 */
public class InMemoryGateway implements EntityGateway {

    private Map<Class<?>, Map<String, Object>> entities = new HashMap<>();

    @Override
    public void persist(Object entity) {
        Class<?> entityClass = entity.getClass();
        initializeMapForClass(entityClass);

        String objectId = getEntityId(entity);
        getMapForClass(entityClass).put(objectId, entity);
    }

    private void initializeMapForClass(Class<?> entityClass) {
        if (notContainsMapForClass(entityClass)) {
            entities.put(entityClass, new HashMap<>());
        }
    }

    private boolean notContainsMapForClass(Class<?> entityClass) {
        return !entities.containsKey(entityClass);
    }

    private String getEntityId(Object entity) {
        try {
            return (String) entity.getClass().getDeclaredMethod("getId").invoke(entity);
        } catch (Exception e) {
            throw new InMemoryGatewayException("Entity must implement getId():String method");
        }
    }

    private Map<String, Object> getMapForClass(Class<?> entityClass) {

        if (notContainsMapForClass(entityClass)) {
            throw new InMemoryGatewayException("No map exists for class");
        }
        return entities.get(entityClass);
    }

    @Override
    public void remove(Object entity) {
        getMapForClass(entity.getClass()).remove(getEntityId(entity));
    }

    @Override
    public <T> boolean exists(Class<T> entityClass, String id) {
        return !notContainsMapForClass(entityClass) && getMapForClass(entityClass).containsKey(id);

    }

    @Override
    public <T> T findBy(Class<T> entityClass, String id) {

        if (notContainsMapForClass(entityClass)) {
            throw new InMemoryGatewayException("No map exists for class");
        }

        return entityClass.cast(getMapForClass(entityClass).get(id));
    }

    @Override
    public <T> List<T> findAllOf(Class<T> entityClass) {

        return new ArrayList<>((Collection<? extends T>) getMapForClass(entityClass).values());
    }

    public class InMemoryGatewayException extends RuntimeException {
        InMemoryGatewayException(String message) {
            super(message);
        }
    }

}
