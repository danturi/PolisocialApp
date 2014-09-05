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

@Api(name = "interestnotificationendpoint", namespace = @ApiNamespace(ownerDomain = "polimi.it", ownerName = "polimi.it", packagePath = "dima.polisocial.entity"))
public class InterestNotificationEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listInterestNotification")
	public CollectionResponse<InterestNotification> listInterestNotification(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<InterestNotification> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr
					.createQuery("select from InterestNotification as InterestNotification");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<InterestNotification>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (InterestNotification obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<InterestNotification> builder()
				.setItems(execute).setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getInterestNotification")
	public InterestNotification getInterestNotification(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		InterestNotification interestnotification = null;
		try {
			interestnotification = mgr.find(InterestNotification.class, id);
		} finally {
			mgr.close();
		}
		return interestnotification;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param interestnotification the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertInterestNotification")
	public InterestNotification insertInterestNotification(
			InterestNotification interestnotification) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsInterestNotification(interestnotification)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.persist(interestnotification);
		} finally {
			mgr.close();
		}
		return interestnotification;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param interestnotification the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateInterestNotification")
	public InterestNotification updateInterestNotification(
			InterestNotification interestnotification) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsInterestNotification(interestnotification)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(interestnotification);
		} finally {
			mgr.close();
		}
		return interestnotification;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeInterestNotification")
	public void removeInterestNotification(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		try {
			InterestNotification interestnotification = mgr.find(
					InterestNotification.class, id);
			mgr.remove(interestnotification);
		} finally {
			mgr.close();
		}
	}

	private boolean containsInterestNotification(
			InterestNotification interestnotification) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			InterestNotification item = mgr.find(InterestNotification.class,
					interestnotification.getId());
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
