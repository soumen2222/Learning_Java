/**
 * 
 */
package com.soumen.weather.common;

import java.util.List;

import javax.ejb.DuplicateKeyException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;


/**
 * Generic interface encouraging naming standards and providing common methods
 * to Managers and a common implementation for those common items like CRUD and
 * Finders.
 * 
 * 
 * 
 */
public interface BaseEAO<EntityType extends BaseEntity> {
	
	/**
	 * @param uuid
	 *         identifying what to delete	 
	 */
	public void delete(String uuid) ;

	/**Deletes the entity by finding using whatever means.  
	 * @see #get(BaseEntity)
	 * 
	 * @param entity the attached or detached entity. 
	 * @throws EntityNotFoundException when a detached entity cannot be found by any identifier
	 */
	public void delete(EntityType entity) throws EntityNotFoundException;

	/**
	 * @param id
	 *  the UUID of the attached entity
	 * @return  the attached entity
	 * @throws EntityNotFoundException  when entity is not found
	 */
	public EntityType getById(String id) throws EntityNotFoundException;

	/**
	 * Provides the attached entity of what is likely not attached. This is
	 * useful because it will attempt to find the entity by other means than
	 * just the id (i.e. programName, etc)
	 * 
	 * @param entity
	 *            the fully filled unattached entity
	 * @return the attached entity
	 * @throws EntityNotFoundException
	 *             when can't be found by any identifier
	 */
	public EntityType get(EntityType entity) throws EntityNotFoundException;

	/**
	 * Persists the entity given and returns the attached entity for more fun by
	 * managers.
	 * 
	 * @param entity
	 *            to create fully populated
	 * @return the attached entity with the new id
	 * @throws DuplicateKeyException same entity is available
	 */
   
    public EntityType create(EntityType entity) throws DuplicateKeyException;
 
	/**
	 * Updates the entity. It will not create the entity so it will throw an
	 * exception if given and ID that is not found in the system.
	 * 
	 * @param entity the attached entity, should be called after getting the entity.
	 * @return the attached entity or null
	 */
    public EntityType merge(EntityType entity);
	/**
	 * @param entity the attached entity, should be called after getting the entity.
	 * @return  Update the attached entity
	 * @throws EntityNotFoundException when entity is not found
	 */
	public EntityType update(EntityType entity) throws EntityNotFoundException;  

    /**Finds all the Entities for the type this EAO is managing.  
	 * 
	 * Use wisely as this list can get large and there is no built-in limit to the number of results.
	 * 
	 * @return Use wisely as this list can get large and there is no built-in limit to the number of results
	 */
	
	public List<EntityType> getAll();	

	


}
