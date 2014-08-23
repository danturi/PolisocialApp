package it.polimi.dima.polisocial.entity;

import it.polimi.dima.polisocial.EMF;

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
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.Email;

@Api(name = "poliuserendpoint", namespace = @ApiNamespace(ownerDomain = "polimi.it", ownerName = "polimi.it", packagePath = "dima.polisocial.entity"))
public class PoliUserEndpoint {

	

	/**
	 * 
	 * This method check user credentials. It uses HTTP GET method.
	 *
	 * @param nickname 
	 * @param password 
	 * @return poliuser, if not null the credentials exists and user can log in
	 */
	@ApiMethod(name = "checkCredentials", httpMethod = HttpMethod.GET)
	public PoliUser checkCredentials(@Named("email") String email, @Named("password") String password) {
		EntityManager mgr = getEntityManager();
		PoliUser poliuser = null;
		try {
			poliuser= (PoliUser)mgr.createQuery("select user from PoliUser user where user.email= :email and user.password= :password").setParameter("email", email).setParameter("password", password).getSingleResult();
		} finally {
			mgr.close();
		}
		return poliuser;
	}

	/**
	 * 
	 * This method check if the email address passed as parameter already exists in db.
	 *  It uses HTTP GET method.
	 *
	 * @param email.
	 * @return poliuser, if not null the email already exists in db
	 */
	@ApiMethod(name = "checkForDuplicateEmail", httpMethod = HttpMethod.GET)
	public PoliUser checkForDuplicateEmail(@Named("email") String email) {
		EntityManager mgr = getEntityManager();
		PoliUser poliuser = null;
		try {
			poliuser= (PoliUser)mgr.createQuery("select user from PoliUser user where user.email= :email").setParameter("email", email).getSingleResult();
		} finally {
			mgr.close();
		}
		return poliuser;
	}
	
	/**
	 * 
	 * This method check if the username passed as parameter already exists in db.
	 *  It uses HTTP GET method.
	 *
	 * @param nickname.
	 * @return poliuser, if not null the nickname already exists in db
	 */
	@ApiMethod(name = "checkForDuplicateUsername", httpMethod = HttpMethod.GET)
	public PoliUser checkForDuplicateUsername(@Named("nickname") String nickname) {
		EntityManager mgr = getEntityManager();
		PoliUser poliuser = null;
		try {
			poliuser= (PoliUser)mgr.createQuery("select user from PoliUser user where user.nickname= :nickname").setParameter("nickname", nickname).getSingleResult();
		} finally {
			mgr.close();
		}
		return poliuser;
	}
	
	
	
	
	
	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listPoliUser")
	public CollectionResponse<PoliUser> listPoliUser(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<PoliUser> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from PoliUser as PoliUser");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<PoliUser>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (PoliUser obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<PoliUser> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getPoliUser")
	public PoliUser getPoliUser(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		PoliUser poliuser = null;
		try {
			poliuser = mgr.find(PoliUser.class, id);
		} finally {
			mgr.close();
		}
		return poliuser;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param poliuser the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertPoliUser")
	public PoliUser insertPoliUser(PoliUser poliuser) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsPoliUser(poliuser)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.persist(poliuser);
		} finally {
			mgr.close();
		}
		return poliuser;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param poliuser the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updatePoliUser")
	public PoliUser updatePoliUser(PoliUser poliuser) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsPoliUser(poliuser)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(poliuser);
		} finally {
			mgr.close();
		}
		return poliuser;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removePoliUser")
	public void removePoliUser(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		try {
			PoliUser poliuser = mgr.find(PoliUser.class, id);
			mgr.remove(poliuser);
		} finally {
			mgr.close();
		}
	}
	
	@ApiMethod(name="prova")
	public void prova(){
		
	}
	

	private boolean containsPoliUser(PoliUser poliuser) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			if (poliuser.getUserKey() == null)
				return false;
			PoliUser item = mgr.find(PoliUser.class, poliuser.getUserKey());
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
