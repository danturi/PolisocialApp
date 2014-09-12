package it.polimi.dima.polisocial.entity;

import it.polimi.dima.polisocial.DeviceInfoEndpoint;
import it.polimi.dima.polisocial.entity.EMF;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.datanucleus.query.JPACursorHelper;

@Api(name = "commentendpoint", namespace = @ApiNamespace(ownerDomain = "polimi.it", ownerName = "polimi.it", packagePath = "dima.polisocial.entity"))
public class CommentEndpoint {


	private DeviceInfoEndpoint deviceInfoEndpoint = new DeviceInfoEndpoint();
	private PostSpottedEndpoint spottedEndpoint = new PostSpottedEndpoint();
	private RentalEndpoint rentalEndpoint = new RentalEndpoint();
	private SecondHandBookEndpoint secondHandEndpoint = new SecondHandBookEndpoint();
	private PrivateLessonEndpoint privateLessonEndpoint = new PrivateLessonEndpoint();
	private InitiativeEndpoint initiativeEndpoint = new InitiativeEndpoint();
	
	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listComment")
	public CollectionResponse<Comment> listComment(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<Comment> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from Comment as Comment");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<Comment>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Comment obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Comment> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 * @throws NotFoundException 
	 */
	@ApiMethod(name = "getComment")
	public Comment getComment(@Named("id") Long id) throws NotFoundException {
		EntityManager mgr = getEntityManager();
		Comment comment = null;
		try {
			comment = mgr.find(Comment.class, id);
			if (comment == null) throw new NotFoundException("Not Found Entity");
		} finally {
			mgr.close();
		}
		return comment;
	}
	
	@SuppressWarnings({ "unchecked" })
	@ApiMethod(name = "getPostComments")
	public CollectionResponse<Comment> getPostComments(Post post) {
		EntityManager mgr = getEntityManager();
		
		List<Comment> comments = null;
		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("SELECT c FROM Comment c WHERE c.postKey =?1");
			query.setParameter(1, post.getKey());
			comments = query.getResultList();
		} finally {
			mgr.close();
		}
		return CollectionResponse.<Comment> builder().setItems(comments).build();
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param comment the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertComment")
	public Comment insertComment(Comment comment) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsComment(comment)) {
				throw new EntityExistsException("Object already exists");
			}
			
			//mando notifiche
			sendNotification(comment);
			
			mgr.persist(comment);
		} finally {
			mgr.close();
		}
		

		
		
		return comment;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param comment the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateComment")
	public Comment updateComment(Comment comment) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsComment(comment)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(comment);
		} finally {
			mgr.close();
		}
		return comment;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeComment")
	public void removeComment(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		try {
			Comment comment = mgr.find(Comment.class, id);
			mgr.remove(comment);
		} finally {
			mgr.close();
		}
	}
	

	private boolean containsComment(Comment comment) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			Comment item = mgr.find(Comment.class, comment.getCommentKey());
			if (item == null) {
				contains = false;
			}
		} finally {
			mgr.close();
		}
		return contains;
	}
	
	/*TODO*/
	private void sendNotification(Comment comment){
		
		//Long userId = comment.getAuthorKey().getId();
		Key postKey = comment.getPostKey();
		String type = comment.getType();
		Post post=null;
		if (type == "Spotted")
			post = spottedEndpoint.getPostSpotted(postKey.getId());
		if (type == "Rental")
			post = rentalEndpoint.getRental(postKey.getId());
		if (type == "SecondHandBook")
			post = secondHandEndpoint.getSecondHandBook(postKey.getId());
		if (type == "PrivateLesson")
			post = privateLessonEndpoint.getPrivateLesson(postKey.getId());
		if (type == "Initiative")
			post = initiativeEndpoint.getInitiative(postKey.getId());
		
		//id autore del post commentato
		Long idAuthorPost = post.getUserId();
		CollectionResponse<Comment> postComments = CommentEndpoint.this.getPostComments(post);
		//ids degli autori dei commenti precedenti
		ArrayList<Long> authorsCommentIds = new ArrayList<Long>();
		for (Comment c : postComments.getItems())
			authorsCommentIds.add(c.getAuthorKey().getId());
		
		//notifico autore del post
		deviceInfoEndpoint.sendToUser(idAuthorPost,post.getId(),type);
		//notifico gli autori dei commenti
		for (Long id : authorsCommentIds)
			deviceInfoEndpoint.sendToUser(id,post.getId(),type);
	}
	
	
	private static EntityManager getEntityManager() {
		return EMF.get().createEntityManager();
	}

}