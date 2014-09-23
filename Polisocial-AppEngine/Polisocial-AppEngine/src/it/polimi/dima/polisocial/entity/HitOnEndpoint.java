package it.polimi.dima.polisocial.entity;

import it.polimi.dima.polisocial.DeviceInfoEndpoint;
import it.polimi.dima.polisocial.entity.EMF;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JPACursorHelper;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Api(name = "hitonendpoint", namespace = @ApiNamespace(ownerDomain = "polimi.it", ownerName = "polimi.it", packagePath="dima.polisocial.entity"))
public class HitOnEndpoint {

  /**
   * This method lists all the entities inserted in datastore.
   * It uses HTTP GET method and paging support.
   *
   * @return A CollectionResponse class containing the list of all entities
   * persisted and a cursor to the next page.
   */
  @SuppressWarnings({"unchecked", "unused"})
  @ApiMethod(name = "listHitOn")
  public CollectionResponse<HitOn> listHitOn(
    @Nullable @Named("cursor") String cursorString,
    @Nullable @Named("limit") Integer limit) {

    EntityManager mgr = null;
    Cursor cursor = null;
    List<HitOn> execute = null;

    try{
      mgr = getEntityManager();
      Query query = mgr.createQuery("select from HitOn as HitOn");
      if (cursorString != null && cursorString != "") {
        cursor = Cursor.fromWebSafeString(cursorString);
        query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
      }

      if (limit != null) {
        query.setFirstResult(0);
        query.setMaxResults(limit);
      }

      execute = (List<HitOn>) query.getResultList();
      cursor = JPACursorHelper.getCursor(execute);
      if (cursor != null) cursorString = cursor.toWebSafeString();

      // Tight loop for fetching all entities from datastore and accomodate
      // for lazy fetch.
      for (HitOn obj : execute);
    } finally {
      mgr.close();
    }

    return CollectionResponse.<HitOn>builder()
      .setItems(execute)
      .setNextPageToken(cursorString)
      .build();
  }

  /**
   * This method gets the entity having primary key id. It uses HTTP GET method.
   *
   * @param id the primary key of the java bean.
   * @return The entity with primary key id.
   */
  @ApiMethod(name = "getHitOn")
  public HitOn getHitOn(@Named("id") Long id) {
    EntityManager mgr = getEntityManager();
    HitOn hiton  = null;
    try {
      hiton = mgr.find(HitOn.class, id);
    } finally {
      mgr.close();
    }
    return hiton;
  }

  /**
   * This inserts a new entity into App Engine datastore. If the entity already
   * exists in the datastore, an exception is thrown.
   * It uses HTTP POST method.
   *
   * @param hiton the entity to be inserted.
   * @return The inserted entity.
   */
  @ApiMethod(name = "insertHitOn")
  public HitOn insertHitOn(HitOn hiton) {
    EntityManager mgr = getEntityManager();
    try {
      if(containsHitOn(hiton)) {
        throw new EntityExistsException("Object already exists");
      }
      mgr.persist(hiton);
    } finally {
      mgr.close();
    }
    return hiton;
  }

  @ApiMethod(name="sendHitOnNotification")
  public void sendHitOnNotification(HitOn hitOn){
	 
	  DeviceInfoEndpoint deviceInfo = new DeviceInfoEndpoint();
	  deviceInfo.sendToUserHitOn(hitOn.getPostId());
  }
  
  /**
   * This method is used for updating an existing entity. If the entity does not
   * exist in the datastore, an exception is thrown.
   * It uses HTTP PUT method.
   *
   * @param hiton the entity to be updated.
   * @return The updated entity.
   */
  @ApiMethod(name = "updateHitOn")
  public HitOn updateHitOn(HitOn hiton) {
    EntityManager mgr = getEntityManager();
    try {
      if(!containsHitOn(hiton)) {
        throw new EntityNotFoundException("Object does not exist");
      }
      mgr.persist(hiton);
    } finally {
      mgr.close();
    }
    return hiton;
  }

  /**
   * This method removes the entity with primary key id.
   * It uses HTTP DELETE method.
   *
   * @param id the primary key of the entity to be deleted.
   */
  @ApiMethod(name = "removeHitOn")
  public void removeHitOn(@Named("id") Long id) {
    EntityManager mgr = getEntityManager();
    try {
      HitOn hiton = mgr.find(HitOn.class, id);
      mgr.remove(hiton);
    } finally {
      mgr.close();
    }
  }
  
  @SuppressWarnings("unchecked")
  @ApiMethod(name = "getUserHitOn")
    public CollectionResponse<HitOn> getUserHitOn(@Named("userId") Long userId,@Nullable @Named("cursor") String cursorString,
  		  @Nullable @Named("limit") Integer limit) {
  	  
  	  EntityManager mgr = null;
  	  Cursor cursor = null;
  	  List<HitOn> execute = null;

  			  try{
  				  mgr = getEntityManager();
  				  Query query = mgr.createQuery("select h from HitOn h where h.userId=?1");
  				  query.setParameter(1, userId);
  				  if (cursorString != null && cursorString != "") {
  					  cursor = Cursor.fromWebSafeString(cursorString);
  					  query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
  				  }

  				  if (limit != null) {
  					  query.setFirstResult(0);
  					  query.setMaxResults(limit);
  				  }

  				  execute = (List<HitOn>) query.getResultList();
  				  cursor = JPACursorHelper.getCursor(execute);
  				  if (cursor != null) cursorString = cursor.toWebSafeString();

  				  // Tight loop for fetching all entities from datastore and accomodate
  				  // for lazy fetch.
  				  for (HitOn obj : execute);
  			  } finally {
  				  mgr.close();
  			  }

  	  return CollectionResponse.<HitOn>builder()
  			  .setItems(execute)
  			  .setNextPageToken(cursorString)
  			  .build();
    }

  private boolean containsHitOn(HitOn hiton) {
    EntityManager mgr = getEntityManager();
    boolean contains = true;
    try {
    	if (hiton.getId()==null)
    		return false;
      HitOn item = mgr.find(HitOn.class, hiton.getId());
      if(item == null) {
        contains = false;
      }
    } finally {
      mgr.close();
    }
    return contains;
  }

  private static EntityManager getEntityManager() {
    return EMF.get().createEntityManager();
  }

}
