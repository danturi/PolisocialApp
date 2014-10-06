package it.polimi.dima.polisocial.entity;

import it.polimi.dima.polisocial.entity.EMF;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JPACursorHelper;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Api(name = "postimageendpoint", namespace = @ApiNamespace(ownerDomain = "polimi.it", ownerName = "polimi.it", packagePath = "dima.polisocial.entity"))
public class PostImageEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listPostImage")
	public CollectionResponse<PostImage> listPostImage(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<PostImage> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from PostImage as PostImage");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<PostImage>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (PostImage obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<PostImage> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getPostImage")
	public PostImage getPostImage(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		PostImage postimage = null;
		try {
			postimage = mgr.find(PostImage.class, id);
		} finally {
			mgr.close();
		}
		return postimage;
	}
	
	@ApiMethod(name = "getImageFromPostId",path="getImageFromPostId")
	public PostImage getImageFromPostId(@Named("postId") Long postId) throws NotFoundException {
		EntityManager mgr = getEntityManager();
		PostImage postimage = null;
		try {
			Query query = mgr
					.createQuery("SELECT i FROM PostImage i WHERE i.postId =?1");
			query.setParameter(1, postId);
			postimage = (PostImage) query.getResultList();
			if (postimage == null)
				throw new NotFoundException("Not Found Image");
		} finally {
			mgr.close();
		}
		return postimage;
	}
	

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param postimage the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertPostImage")
	public PostImage insertPostImage(PostImage postimage) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsPostImage(postimage)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.persist(postimage);
		} finally {
			mgr.close();
		}
		return postimage;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param postimage the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updatePostImage")
	public PostImage updatePostImage(PostImage postimage) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsPostImage(postimage)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(postimage);
		} finally {
			mgr.close();
		}
		return postimage;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removePostImage")
	public void removePostImage(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		try {
			PostImage postimage = mgr.find(PostImage.class, id);
			mgr.remove(postimage);
		} finally {
			mgr.close();
		}
	}

	private boolean containsPostImage(PostImage postimage) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			if (postimage.getId() == null)
				return false;
			PostImage item = mgr.find(PostImage.class, postimage.getId());
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
