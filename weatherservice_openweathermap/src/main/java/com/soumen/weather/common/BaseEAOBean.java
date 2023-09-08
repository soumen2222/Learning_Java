
package com.soumen.weather.common;

import java.util.List;
import javax.ejb.DuplicateKeyException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


public abstract class BaseEAOBean<EntityType extends BaseEntity> implements
		BaseEAO<EntityType> {
	
	@PersistenceContext(unitName = "weather")
	protected EntityManager em;
	
	public BaseEAOBean(Class<EntityType> entityClass) {
		this.entityClass = entityClass;
	}

	private Class<EntityType> entityClass;

	@Override
	public EntityType get(EntityType entity) throws EntityNotFoundException {
		EntityType found;		
		found = getById(entity.getUUID());		
		return found;
	}


    @Override
    public EntityType merge(EntityType entity)  {
        return em.merge(entity);
    }
    
    @Override
    public EntityType update(EntityType entity) throws EntityNotFoundException {

		String uuid = entity.getUUID();
		if (uuid == null) {

			// let's find by something besides the ID
			// will throw not found exception if unable to identify
			EntityType found = get(entity);
			// transfer the known identifier to the given entity since the
			// entity provided is using
			// a business key to identify the entity, not the UUID
			entity.setUUID(found.getUUID());
		} else {
			// just make sure entity exists because merge will apparently create
			// one even if it is a bogus id
			getById(uuid);
		}
       return em.merge(entity);

    }

    //            

   

	@SuppressWarnings("unchecked")
	@Override
	public List<EntityType> getAll() {
		StringBuffer queryValue = new StringBuffer();
		queryValue.append("select e from ");
		queryValue.append(entityClass.getSimpleName());
		queryValue.append(" e");
		Query query = em.createQuery(queryValue.toString());
		List<EntityType> result = query.getResultList();
		return result;
	}


	/**
	 * Given the UUID this will return the Entity represented by that ID.
	 * 
	 * @param uuid
	 *            the UUID of the Entity desired.
	 */
	@Override
	public EntityType getById(String uuid) throws EntityNotFoundException {
		EntityType result = em.find(entityClass, uuid);
		if (result == null) {
			throw new EntityNotFoundException(uuid);
		}
		return result;
	}

	

    @Override
    public EntityType create(EntityType entity) throws DuplicateKeyException {

    	entity.deleteUUID();
    	em.persist(entity);
        return entity;
    }
    


	@Override
	public void delete(String uuid) throws EntityNotFoundException {
		EntityType entity = getById(uuid);
		em.remove(entity);

	}

	@Override
	public void delete(EntityType entity) throws EntityNotFoundException {
		delete(entity.getUUID());
	}
	
  
}
