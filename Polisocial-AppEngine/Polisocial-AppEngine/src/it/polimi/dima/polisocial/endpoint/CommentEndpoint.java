package it.polimi.dima.polisocial.endpoint;

import it.polimi.dima.polisocial.ResponseObject;
import it.polimi.dima.polisocial.entity.Comment;
import it.polimi.dima.polisocial.entity.EMF;
import it.polimi.dima.polisocial.entity.Initiative;
import it.polimi.dima.polisocial.entity.Post;
import it.polimi.dima.polisocial.entity.PostSpotted;
import it.polimi.dima.polisocial.entity.PrivateLesson;
import it.polimi.dima.polisocial.entity.Rental;
import it.polimi.dima.polisocial.entity.SecondHandBook;

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
	 * This method lists all the entities inserted in datastore. It uses HTTP
	 * GET method and paging support.
	 * 
	 * @return A CollectionResponse class containing the list of all entities
	 *         persisted and a cursor to the next page.
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

			// Tight loop for fetching all entities from datastore and
			// accomodate
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
	 * This method gets the entity having primary key id. It uses HTTP GET
	 * method.
	 * 
	 * @param id
	 *            the primary key of the java bean.
	 * @return The entity with primary key id.
	 * @throws NotFoundException
	 */
	@ApiMethod(name = "getComment")
	public Comment getComment(@Named("id") Long id) throws NotFoundException {
		EntityManager mgr = getEntityManager();
		Comment comment = null;
		try {
			comment = mgr.find(Comment.class, id);
			if (comment == null)
				throw new NotFoundException("Not Found Entity");
		} finally {
			mgr.close();
		}
		return comment;
	}

	@SuppressWarnings({ "unchecked" })
	@ApiMethod(name = "getPostComments")
	public CollectionResponse<Comment> getPostComments(
			@Named("postId") Long postId) {
		EntityManager mgr = getEntityManager();

		// TODO ordine discendente per timeStamp
		List<Comment> comments = null;
		try {
			mgr = getEntityManager();
			Query query = mgr
					.createQuery("SELECT c FROM Comment c WHERE c.postId =?1 ORDER BY c.commentTimestamp ASC");
			query.setParameter(1, postId);
			comments = query.getResultList();
		} finally {
			mgr.close();
		}
		return CollectionResponse.<Comment> builder().setItems(comments)
				.build();
	}

	@ApiMethod(name = "getNumbPostComments")
	public ResponseObject getNumPostComments(@Named("postId") Long postId) {
		EntityManager mgr = getEntityManager();
		ResponseObject o = new ResponseObject();
		try {
			mgr = getEntityManager();
			Query query = mgr
					.createQuery("SELECT COUNT(c.commentId) FROM Comment c WHERE c.postId =?1");
			query.setParameter(1, postId);
			long count = (long) query.getSingleResult();
			o.setObject(count);
		} finally {
			mgr.close();
		}
		return o;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity
	 * already exists in the datastore, an exception is thrown. It uses HTTP
	 * POST method.
	 * 
	 * @param comment
	 *            the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertComment")
	public Comment insertComment(Comment comment) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsComment(comment)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.persist(comment);
		} finally {
			mgr.close();
		}

		return comment;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does
	 * not exist in the datastore, an exception is thrown. It uses HTTP PUT
	 * method.
	 * 
	 * @param comment
	 *            the entity to be updated.
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
	 * This method removes the entity with primary key id. It uses HTTP DELETE
	 * method.
	 * 
	 * @param id
	 *            the primary key of the entity to be deleted.
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
			if (comment.getCommentId() == null)
				return false;
			Comment item = mgr.find(Comment.class, comment.getCommentId());
			if (item == null) {
				contains = false;
			}
		} finally {
			mgr.close();
		}
		return contains;
	}

	@ApiMethod(name = "sendNotification")
	public void sendNotification(Comment comment) {

		Long postId = comment.getPostId();
		String type = comment.getType();
		Post post = null;
		if (type.equals("spotted")){
			post = spottedEndpoint.getPostSpotted(postId);
			post.setNumOfComments(post.getNumOfComments()+1);
			spottedEndpoint.updatePostSpotted((PostSpotted) post);
		}
		if (type.equals("rental")){
			post = rentalEndpoint.getRental(postId);
			post.setNumOfComments(post.getNumOfComments()+1);
			rentalEndpoint.updateRental((Rental) post);
		}
		if (type.equals("secondHandBook")){
			post = secondHandEndpoint.getSecondHandBook(postId);
			post.setNumOfComments(post.getNumOfComments()+1);
			secondHandEndpoint.updateSecondHandBook((SecondHandBook) post);
		}
		if (type.equals("privateLesson")){
			post = privateLessonEndpoint.getPrivateLesson(postId);
			post.setNumOfComments(post.getNumOfComments()+1);
			privateLessonEndpoint.updatePrivateLesson((PrivateLesson) post);
		}
		if (type.equals("event")){
			post = initiativeEndpoint.getInitiative(postId);
			post.setNumOfComments(post.getNumOfComments()+1);
			initiativeEndpoint.updateInitiative((Initiative) post);
		}

		// id autore del post commentato
		Long idAuthorPost = post.getUserId();
		String postTitle = post.getTitle();
		CollectionResponse<Comment> postComments = CommentEndpoint.this
				.getPostComments(postId);
		List<Comment> comments = (List<Comment>) postComments.getItems();

		// prendo l'id dell'user che ha appena commentato e lo tolgo dagli utenti
		// da notificare
		Long userLastComment = comment.getAuthorId();
		// ids degli autori dei commenti precedenti tranne l'user autore del
		// commento appena inserito
		ArrayList<Long> authorsCommentIds = new ArrayList<Long>();
		for (Comment c : postComments.getItems()) {
			if (!(c.getAuthorId().compareTo(userLastComment)==0)) {
				// elimina doppia notifica all'autore post che si è auto-commentato precedentemente
				// e elimina doppioni notifiche a chi ha commentato più volte il
				// post
				if (!(c.getAuthorId().compareTo(idAuthorPost) == 0)
						&& !authorsCommentIds.contains(c.getAuthorId())) {
					authorsCommentIds.add(c.getAuthorId());
				}
			}
		}
		// notifico autore del post se non è lui che ha commentato
		if (!(idAuthorPost.compareTo(userLastComment) == 0))
			deviceInfoEndpoint.sendToUser(idAuthorPost, post.getId(), type,
					postTitle);
		// notifico gli autori dei commenti
		if (!authorsCommentIds.isEmpty()) {
			for (Long id : authorsCommentIds)
				deviceInfoEndpoint
						.sendToUser(id, post.getId(), type, postTitle);
		}
		
		
	}

	private static EntityManager getEntityManager() {
		return EMF.get().createEntityManager();
	}

}