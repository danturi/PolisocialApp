package it.polimi.dima.polisocial.endpoint;

import it.polimi.dima.polisocial.ResponseObject;
import it.polimi.dima.polisocial.entity.EMF;
import it.polimi.dima.polisocial.entity.Notification;

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

@Api(name = "notificationendpoint", namespace = @ApiNamespace(ownerDomain = "polimi.it", ownerName = "polimi.it", packagePath="dima.polisocial.entity"))
public class NotificationEndpoint {

  /**
   * This method lists all the entities inserted in datastore.
   * It uses HTTP GET method and paging support.
   *
   * @return A CollectionResponse class containing the list of all entities
   * persisted and a cursor to the next page.
   */
  @SuppressWarnings({"unchecked", "unused"})
  @ApiMethod(name = "listNotification")
  public CollectionResponse<Notification> listNotification(
    @Nullable @Named("cursor") String cursorString,
    @Nullable @Named("limit") Integer limit) {

    EntityManager mgr = null;
    Cursor cursor = null;
    List<Notification> execute = null;

    try{
      mgr = getEntityManager();
      Query query = mgr.createQuery("select from Notification as Notification");
      if (cursorString != null && cursorString != "") {
        cursor = Cursor.fromWebSafeString(cursorString);
        query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
      }

      if (limit != null) {
        query.setFirstResult(0);
        query.setMaxResults(limit);
      }

      execute = (List<Notification>) query.getResultList();
      cursor = JPACursorHelper.getCursor(execute);
      if (cursor != null) cursorString = cursor.toWebSafeString();

      // Tight loop for fetching all entities from datastore and accomodate
      // for lazy fetch.
      for (Notification obj : execute);
    } finally {
      mgr.close();
    }

    return CollectionResponse.<Notification>builder()
      .setItems(execute)
      .setNextPageToken(cursorString)
      .build();
  }

  /**
   * This method gets the entity having primary key id. It uses HTTP GET method.
   *
   * @param id the primary key of the java bean.
   * @return A CollectionResponse class containing the list of all entities
   *         persisted and a cursor to the next page.
   */
  @ApiMethod(name = "getNotification")
  public Notification getNotification(@Named("id") Long id) {
    EntityManager mgr = getEntityManager();
    Notification notification  = null;
    try {
      notification = mgr.find(Notification.class, id);
    } finally {
      mgr.close();
    }
    return notification;
  }

  /**
   * This method gets user notifications. It uses HTTP GET method.
   *
   * @param userId the primary id of the PoliUser entity.
   * @return 
   */
  @SuppressWarnings("unchecked")
@ApiMethod(name = "getUserNotification")
  public CollectionResponse<Notification> getUserNotification(@Named("userId") Long userId,@Nullable @Named("cursor") String cursorString,
		  @Nullable @Named("limit") Integer limit) {
	  
	  EntityManager mgr = null;
	  Cursor cursor = null;
	  List<Notification> execute = null;

			  try{
				  mgr = getEntityManager();
				  Query query = mgr.createQuery("select n from Notification n where n.userId=?1");
				  query.setParameter(1, userId);
				  if (cursorString != null && cursorString != "") {
					  cursor = Cursor.fromWebSafeString(cursorString);
					  query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
				  }

				  if (limit != null) {
					  query.setFirstResult(0);
					  query.setMaxResults(limit);
				  }

				  execute = (List<Notification>) query.getResultList();
				  cursor = JPACursorHelper.getCursor(execute);
				  if (cursor != null) cursorString = cursor.toWebSafeString();

				  // Tight loop for fetching all entities from datastore and accomodate
				  // for lazy fetch.
				  for (Notification obj : execute);
			  } finally {
				  mgr.close();
			  }

	  return CollectionResponse.<Notification>builder()
			  .setItems(execute)
			  .setNextPageToken(cursorString)
			  .build();
  }
  
  @ApiMethod(name = "getNotificationForPost",path="getNotificationForPost")
	public Notification getNotificationForPost(@Named("postId") Long postId,@Named("userId") Long userId) {
		EntityManager mgr = getEntityManager();
		Notification notif=null;
		
		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("SELECT n FROM Notification n WHERE n.postId =?1 AND n.userId =?2");
			query.setParameter(1, postId);
			query.setParameter(2, userId);
			notif = (Notification) query.getSingleResult();
		}finally {
			mgr.close();
		}
		return notif;
	}
  
  @ApiMethod(name = "getCountUnreadTypeNotificationForUser",path="getCountUnreadTypeNotificationForUser")
 	public ResponseObject getCountUnreadTypeNotificationForUser(@Named("userId") Long userId,@Named("type")String type) {
 		EntityManager mgr = getEntityManager();
 		Notification notif=null;
 		ResponseObject o = new ResponseObject();
 		
 		try {
 			mgr = getEntityManager();
 			Query query = mgr.createQuery("SELECT COUNT(n.id) FROM Notification n WHERE n.postType =?1 AND n.userId =?2 AND n.readFlag=false");
 			query.setParameter(1, type);
 			query.setParameter(2, userId);
 			long count = (long)query.getSingleResult();
 			o.setObject(count);
 		}finally {
 			mgr.close();
 		}
 		return o;
 	}
   
  
  
  @ApiMethod(name = "haveNotificationForPost")
	public ResponseObject haveNotificationForPost(@Named("postId") Long postId,@Named("userId") Long userId) {
		EntityManager mgr = getEntityManager();
		ResponseObject o = new ResponseObject();
		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("SELECT COUNT(n.id) FROM Notification n WHERE n.postId =?1 AND n.userId =?2");
			query.setParameter(1, postId);
			query.setParameter(2, userId);
			long count = (long)query.getSingleResult();
			Boolean response = true;
			if (count == 0){
				response = false;
			}
			o.setObject(response);
		}finally {
			mgr.close();
		}
		return o;
	}
  
  @ApiMethod(name = "haveHitOnNotificationForPost")
	public ResponseObject haveHitOnNotificationForPost(@Named("postId") Long postId,@Named("userId") Long userId) {
		EntityManager mgr = getEntityManager();
		ResponseObject o = new ResponseObject();
		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("SELECT COUNT(n.postId) FROM Notification n WHERE n.postId =?1 AND n.userId =?2 AND n.postType=?3");
			query.setParameter(1, postId);
			query.setParameter(2, userId);
			query.setParameter(3,"hit_on");
			long count = (long)query.getSingleResult();
			Boolean response = true;
			if (count == 0){
				response = false;
			}
			o.setObject(response);
		}finally {
			mgr.close();
		}
		return o;
	}
  
  /**
   * This inserts a new entity into App Engine datastore. If the entity already
   * exists in the datastore, an exception is thrown.
   * It uses HTTP POST method.
   *
   * @param notification the entity to be inserted.
   * @return The inserted entity.
   */
  @ApiMethod(name = "insertNotification")
  public Notification insertNotification(Notification notification) {
    EntityManager mgr = getEntityManager();
    try {
      if(containsNotification(notification)) {
        throw new EntityExistsException("Object already exists");
      }
      mgr.persist(notification);
    } finally {
      mgr.close();
    }
    return notification;
  }

  /**
   * This method is used for updating an existing entity. If the entity does not
   * exist in the datastore, an exception is thrown.
   * It uses HTTP PUT method.
   *
   * @param notification the entity to be updated.
   * @return The updated entity.
   */
  @ApiMethod(name = "updateNotification")
  public Notification updateNotification(Notification notification) {
    EntityManager mgr = getEntityManager();
    try {
      if(!containsNotification(notification)) {
        throw new EntityNotFoundException("Object does not exist");
      }
      mgr.persist(notification);
    } finally {
      mgr.close();
    }
    return notification;
  }

  /**
   * This method removes the entity with primary key id.
   * It uses HTTP DELETE method.
   *
   * @param id the primary key of the entity to be deleted.
   */
  @ApiMethod(name = "removeNotification")
  public void removeNotification(@Named("id") Long id) {
    EntityManager mgr = getEntityManager();
    try {
      Notification notification = mgr.find(Notification.class, id);
      mgr.remove(notification);
    } finally {
      mgr.close();
    }
  }

  private boolean containsNotification(Notification notification) {
    EntityManager mgr = getEntityManager();
    boolean contains = true;
    try {
    	if(notification.getId()==null)
    		return false;
      Notification item = mgr.find(Notification.class, notification.getId());
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
