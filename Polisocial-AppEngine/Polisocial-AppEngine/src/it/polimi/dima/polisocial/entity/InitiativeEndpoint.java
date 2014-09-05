package it.polimi.dima.polisocial.entity;

import it.polimi.dima.polisocial.entity.EMF;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;

import com.google.appengine.datanucleus.query.JPACursorHelper;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;

@Api(name = "initiativeendpoint", namespace = @ApiNamespace(ownerDomain = "polimi.it", ownerName = "polimi.it", packagePath = "dima.polisocial.entity"))
public class InitiativeEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listInitiative")
	public CollectionResponse<Initiative> listInitiative(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<Initiative> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr
					.createQuery("select from Initiative as Initiative");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<Initiative>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Initiative obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Initiative> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getInitiative")
	public Initiative getInitiative(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		Initiative initiative = null;
		try {
			initiative = mgr.find(Initiative.class, id);
		} finally {
			mgr.close();
		}
		return initiative;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param initiative the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertInitiative")
	public Initiative insertInitiative(Initiative initiative) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsInitiative(initiative)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.persist(initiative);
		} finally {
			mgr.close();
		}
		return initiative;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param initiative the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateInitiative")
	public Initiative updateInitiative(Initiative initiative) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsInitiative(initiative)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(initiative);
		} finally {
			mgr.close();
		}
		return initiative;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeInitiative")
	public void removeInitiative(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		try {
			Initiative initiative = mgr.find(Initiative.class, id);
			mgr.remove(initiative);
		} finally {
			mgr.close();
		}
	}

	private boolean containsInitiative(Initiative initiative) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			Initiative item = mgr.find(Initiative.class, initiative.getKey());
			if (item == null) {
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
