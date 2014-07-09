package it.polimi.dima.polisocial.entity;

import it.polimi.dima.polisocial.EMF;

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

@Api(name = "postspottedendpoint", namespace = @ApiNamespace(ownerDomain = "polimi.it", ownerName = "polimi.it", packagePath = "dima.polisocial.entity"))
public class PostSpottedEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listPostSpotted")
	public CollectionResponse<PostSpotted> listPostSpotted(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<PostSpotted> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr
					.createQuery("select from PostSpotted as PostSpotted");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<PostSpotted>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (PostSpotted obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<PostSpotted> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getPostSpotted")
	public PostSpotted getPostSpotted(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		PostSpotted postspotted = null;
		try {
			postspotted = mgr.find(PostSpotted.class, id);
		} finally {
			mgr.close();
		}
		return postspotted;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param postspotted the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertPostSpotted")
	public PostSpotted insertPostSpotted(PostSpotted postspotted) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsPostSpotted(postspotted)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.persist(postspotted);
		} finally {
			mgr.close();
		}
		return postspotted;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param postspotted the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updatePostSpotted")
	public PostSpotted updatePostSpotted(PostSpotted postspotted) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsPostSpotted(postspotted)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(postspotted);
		} finally {
			mgr.close();
		}
		return postspotted;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removePostSpotted")
	public void removePostSpotted(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		try {
			PostSpotted postspotted = mgr.find(PostSpotted.class, id);
			mgr.remove(postspotted);
		} finally {
			mgr.close();
		}
	}

	private boolean containsPostSpotted(PostSpotted postspotted) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			PostSpotted item = mgr
					.find(PostSpotted.class, postspotted.getKey());
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
