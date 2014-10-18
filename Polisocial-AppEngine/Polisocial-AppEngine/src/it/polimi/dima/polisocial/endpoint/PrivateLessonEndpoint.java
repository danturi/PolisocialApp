package it.polimi.dima.polisocial.endpoint;

import it.polimi.dima.polisocial.entity.EMF;
import it.polimi.dima.polisocial.entity.PrivateLesson;

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

@Api(name = "privatelessonendpoint", namespace = @ApiNamespace(ownerDomain = "polimi.it", ownerName = "polimi.it", packagePath="dima.polisocial.entity"))
public class PrivateLessonEndpoint {

  /**
   * This method lists all the entities inserted in datastore.
   * It uses HTTP GET method and paging support.
   *
   * @return A CollectionResponse class containing the list of all entities
   * persisted and a cursor to the next page.
   */
  @SuppressWarnings({"unchecked", "unused"})
  @ApiMethod(name = "listPrivateLesson")
  public CollectionResponse<PrivateLesson> listPrivateLesson(
    @Nullable @Named("cursor") String cursorString,
    @Nullable @Named("limit") Integer limit) {

    EntityManager mgr = null;
    Cursor cursor = null;
    List<PrivateLesson> execute = null;

    try{
      mgr = getEntityManager();
      Query query = mgr.createQuery("select from PrivateLesson as PrivateLesson");
      if (cursorString != null && cursorString != "") {
        cursor = Cursor.fromWebSafeString(cursorString);
        query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
      }

      if (limit != null) {
        query.setFirstResult(0);
        query.setMaxResults(limit);
      }

      execute = (List<PrivateLesson>) query.getResultList();
      cursor = JPACursorHelper.getCursor(execute);
      if (cursor != null) cursorString = cursor.toWebSafeString();

      // Tight loop for fetching all entities from datastore and accomodate
      // for lazy fetch.
      for (PrivateLesson obj : execute);
    } finally {
      mgr.close();
    }

    return CollectionResponse.<PrivateLesson>builder()
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
  @ApiMethod(name = "getPrivateLesson")
  public PrivateLesson getPrivateLesson(@Named("id") Long id) {
    EntityManager mgr = getEntityManager();
    PrivateLesson privatelesson  = null;
    try {
      privatelesson = mgr.find(PrivateLesson.class, id);
    } finally {
      mgr.close();
    }
    return privatelesson;
  }

  /**
   * This inserts a new entity into App Engine datastore. If the entity already
   * exists in the datastore, an exception is thrown.
   * It uses HTTP POST method.
   *
   * @param privatelesson the entity to be inserted.
   * @return The inserted entity.
   */
  @ApiMethod(name = "insertPrivateLesson")
  public PrivateLesson insertPrivateLesson(PrivateLesson privatelesson) {
    EntityManager mgr = getEntityManager();
    try {
      if(containsPrivateLesson(privatelesson)) {
        throw new EntityExistsException("Object already exists");
      }
      mgr.persist(privatelesson);
    } finally {
      mgr.close();
    }
    return privatelesson;
  }

  /**
   * This method is used for updating an existing entity. If the entity does not
   * exist in the datastore, an exception is thrown.
   * It uses HTTP PUT method.
   *
   * @param privatelesson the entity to be updated.
   * @return The updated entity.
   */
  @ApiMethod(name = "updatePrivateLesson")
  public PrivateLesson updatePrivateLesson(PrivateLesson privatelesson) {
    EntityManager mgr = getEntityManager();
    try {
      if(!containsPrivateLesson(privatelesson)) {
        throw new EntityNotFoundException("Object does not exist");
      }
      mgr.persist(privatelesson);
    } finally {
      mgr.close();
    }
    return privatelesson;
  }

  /**
   * This method removes the entity with primary key id.
   * It uses HTTP DELETE method.
   *
   * @param id the primary key of the entity to be deleted.
   */
  @ApiMethod(name = "removePrivateLesson")
  public void removePrivateLesson(@Named("id") Long id) {
    EntityManager mgr = getEntityManager();
    try {
      PrivateLesson privatelesson = mgr.find(PrivateLesson.class, id);
      mgr.remove(privatelesson);
    } finally {
      mgr.close();
    }
  }

  private boolean containsPrivateLesson(PrivateLesson privatelesson) {
    EntityManager mgr = getEntityManager();
    boolean contains = true;
    try {
      PrivateLesson item = mgr.find(PrivateLesson.class, privatelesson.getId());
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
