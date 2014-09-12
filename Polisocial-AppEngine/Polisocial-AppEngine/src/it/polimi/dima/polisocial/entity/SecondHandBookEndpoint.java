package it.polimi.dima.polisocial.entity;

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

@Api(name = "secondhandbookendpoint", namespace = @ApiNamespace(ownerDomain = "polimi.it", ownerName = "polimi.it", packagePath="dima.polisocial.entity"))
public class SecondHandBookEndpoint {

  /**
   * This method lists all the entities inserted in datastore.
   * It uses HTTP GET method and paging support.
   *
   * @return A CollectionResponse class containing the list of all entities
   * persisted and a cursor to the next page.
   */
  @SuppressWarnings({"unchecked", "unused"})
  @ApiMethod(name = "listSecondHandBook")
  public CollectionResponse<SecondHandBook> listSecondHandBook(
    @Nullable @Named("cursor") String cursorString,
    @Nullable @Named("limit") Integer limit) {

    EntityManager mgr = null;
    Cursor cursor = null;
    List<SecondHandBook> execute = null;

    try{
      mgr = getEntityManager();
      Query query = mgr.createQuery("select from SecondHandBook as SecondHandBook");
      if (cursorString != null && cursorString != "") {
        cursor = Cursor.fromWebSafeString(cursorString);
        query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
      }

      if (limit != null) {
        query.setFirstResult(0);
        query.setMaxResults(limit);
      }

      execute = (List<SecondHandBook>) query.getResultList();
      cursor = JPACursorHelper.getCursor(execute);
      if (cursor != null) cursorString = cursor.toWebSafeString();

      // Tight loop for fetching all entities from datastore and accomodate
      // for lazy fetch.
      for (SecondHandBook obj : execute);
    } finally {
      mgr.close();
    }

    return CollectionResponse.<SecondHandBook>builder()
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
  @ApiMethod(name = "getSecondHandBook")
  public SecondHandBook getSecondHandBook(@Named("id") Long id) {
    EntityManager mgr = getEntityManager();
    SecondHandBook secondhandbook  = null;
    try {
      secondhandbook = mgr.find(SecondHandBook.class, id);
    } finally {
      mgr.close();
    }
    return secondhandbook;
  }

  /**
   * This inserts a new entity into App Engine datastore. If the entity already
   * exists in the datastore, an exception is thrown.
   * It uses HTTP POST method.
   *
   * @param secondhandbook the entity to be inserted.
   * @return The inserted entity.
   */
  @ApiMethod(name = "insertSecondHandBook")
  public SecondHandBook insertSecondHandBook(SecondHandBook secondhandbook) {
    EntityManager mgr = getEntityManager();
    try {
      if(containsSecondHandBook(secondhandbook)) {
        throw new EntityExistsException("Object already exists");
      }
      mgr.persist(secondhandbook);
    } finally {
      mgr.close();
    }
    return secondhandbook;
  }

  /**
   * This method is used for updating an existing entity. If the entity does not
   * exist in the datastore, an exception is thrown.
   * It uses HTTP PUT method.
   *
   * @param secondhandbook the entity to be updated.
   * @return The updated entity.
   */
  @ApiMethod(name = "updateSecondHandBook")
  public SecondHandBook updateSecondHandBook(SecondHandBook secondhandbook) {
    EntityManager mgr = getEntityManager();
    try {
      if(!containsSecondHandBook(secondhandbook)) {
        throw new EntityNotFoundException("Object does not exist");
      }
      mgr.persist(secondhandbook);
    } finally {
      mgr.close();
    }
    return secondhandbook;
  }

  /**
   * This method removes the entity with primary key id.
   * It uses HTTP DELETE method.
   *
   * @param id the primary key of the entity to be deleted.
   */
  @ApiMethod(name = "removeSecondHandBook")
  public void removeSecondHandBook(@Named("id") Long id) {
    EntityManager mgr = getEntityManager();
    try {
      SecondHandBook secondhandbook = mgr.find(SecondHandBook.class, id);
      mgr.remove(secondhandbook);
    } finally {
      mgr.close();
    }
  }

  private boolean containsSecondHandBook(SecondHandBook secondhandbook) {
    EntityManager mgr = getEntityManager();
    boolean contains = true;
    try {
      SecondHandBook item = mgr.find(SecondHandBook.class, secondhandbook.getId());
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
