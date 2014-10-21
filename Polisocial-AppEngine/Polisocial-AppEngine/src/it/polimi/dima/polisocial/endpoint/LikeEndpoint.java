package it.polimi.dima.polisocial.endpoint;

import it.polimi.dima.polisocial.ResponseObject;
import it.polimi.dima.polisocial.entity.EMF;
import it.polimi.dima.polisocial.entity.Like;

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

@Api(name = "likeendpoint", namespace = @ApiNamespace(ownerDomain = "polimi.it", ownerName = "polimi.it", packagePath = "dima.polisocial.entity"))
public class LikeEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listLike")
	public CollectionResponse<Like> listLike(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<Like> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from Like as Like");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<Like>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Like obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Like> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getLike")
	public Like getLike(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		Like like = null;
		try {
			like = mgr.find(Like.class, id);
		} finally {
			mgr.close();
		}
		return like;
	}
	
	@ApiMethod(name = "getPostLike")
	public ResponseObject getPostLike(@Named("postId") Long postId) {

		EntityManager mgr = null;
		ResponseObject o = new ResponseObject();

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select count(l.id) from Like l where l.postId=?1");
			query.setParameter(1, postId);
			long count = (long) query.getSingleResult();
			o.setObject(count);
		} finally {
			mgr.close();
		}

		return o;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param like the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertLike")
	public Like insertLike(Like like) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsLike(like)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.persist(like);
		} finally {
			mgr.close();
		}
		return like;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param like the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateLike")
	public Like updateLike(Like like) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsLike(like)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(like);
		} finally {
			mgr.close();
		}
		return like;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeLike")
	public void removeLike(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		try {
			Like like = mgr.find(Like.class, id);
			mgr.remove(like);
		} finally {
			mgr.close();
		}
	}

	private boolean containsLike(Like like) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			Like item = mgr.find(Like.class, like.getId());
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
