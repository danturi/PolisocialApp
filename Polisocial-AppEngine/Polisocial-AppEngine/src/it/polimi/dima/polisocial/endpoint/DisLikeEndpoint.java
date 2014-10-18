package it.polimi.dima.polisocial.endpoint;

import it.polimi.dima.polisocial.entity.DisLike;
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

@Api(name = "dislikeendpoint", namespace = @ApiNamespace(ownerDomain = "polimi.it", ownerName = "polimi.it", packagePath = "dima.polisocial.entity"))
public class DisLikeEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listDisLike")
	public CollectionResponse<DisLike> listDisLike(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<DisLike> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from DisLike as DisLike");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<DisLike>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (DisLike obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<DisLike> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getDisLike")
	public DisLike getDisLike(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		DisLike dislike = null;
		try {
			dislike = mgr.find(DisLike.class, id);
		} finally {
			mgr.close();
		}
		return dislike;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param dislike the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertDisLike")
	public DisLike insertDisLike(DisLike dislike) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsDisLike(dislike)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.persist(dislike);
		} finally {
			mgr.close();
		}
		return dislike;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param dislike the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateDisLike")
	public DisLike updateDisLike(DisLike dislike) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsDisLike(dislike)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(dislike);
		} finally {
			mgr.close();
		}
		return dislike;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeDisLike")
	public void removeDisLike(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		try {
			DisLike dislike = mgr.find(DisLike.class, id);
			mgr.remove(dislike);
		} finally {
			mgr.close();
		}
	}

	private boolean containsDisLike(DisLike dislike) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			DisLike item = mgr.find(DisLike.class, dislike.getId());
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
