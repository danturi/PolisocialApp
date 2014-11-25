package it.polimi.dima.polisocial.loader;

import it.polimi.dima.polisocial.CloudEndpointUtils;
import it.polimi.dima.polisocial.entity.commentendpoint.Commentendpoint;
import it.polimi.dima.polisocial.entity.commentendpoint.model.CollectionResponseComment;
import it.polimi.dima.polisocial.entity.commentendpoint.model.Comment;
import it.polimi.dima.polisocial.entity.hitonendpoint.Hitonendpoint;
import it.polimi.dima.polisocial.entity.hitonendpoint.model.CollectionResponseHitOn;
import it.polimi.dima.polisocial.entity.hitonendpoint.model.HitOn;
import it.polimi.dima.polisocial.entity.initiativeendpoint.Initiativeendpoint;
import it.polimi.dima.polisocial.entity.initiativeendpoint.model.Initiative;
import it.polimi.dima.polisocial.entity.postspottedendpoint.Postspottedendpoint;
import it.polimi.dima.polisocial.entity.postspottedendpoint.model.PostSpotted;
import it.polimi.dima.polisocial.entity.rentalendpoint.Rentalendpoint;
import it.polimi.dima.polisocial.entity.rentalendpoint.model.Rental;
import it.polimi.dima.polisocial.utilClasses.PostType;
import it.polimi.dima.polisocial.utilClasses.WhatToShow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;
import android.content.Context;
import android.widget.Toast;

public class CommentListLoader extends EndlessListAsyncTaskLoader<List<Object>> {

	String whatToShow;
	String postType;
	long postId;
	private Postspottedendpoint spottedEnd;
	private Initiativeendpoint initiativeEnd;
	private Rentalendpoint rentalEnd;

	public CommentListLoader(Context context, String cursor, String whatToShow,
			String postType, long postId) {
		super(context, cursor);
		this.whatToShow = whatToShow;
		this.postId = postId;
		this.postType = postType;
	}

	@Override
	public List<Object> loadInBackground() {

		mItems = new ArrayList<Object>();

		//if we come from notification fragment or we want the details about a post, then...
		if (whatToShow.equals(WhatToShow.FROM_NOTIFICATION.toString())
				|| whatToShow.equals(WhatToShow.DETAILS.toString())) {

			// if the notification is about a spotted post, then...
			if (postType.equals(PostType.SPOTTED.toString())
					|| postType.equals(PostType.HIT_ON.toString())) {
				// retrieve from server the spotted post identified by postId
				Postspottedendpoint.Builder builder = new Postspottedendpoint.Builder(
						AndroidHttp.newCompatibleTransport(),
						new JacksonFactory(), null);

				builder = CloudEndpointUtils.updateBuilder(builder);
				spottedEnd = builder.setApplicationName("polimisocial").build();
				try {
					PostSpotted post = spottedEnd.getPostSpotted(postId)
							.execute();
					mItems.add(post);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (postType.equals(PostType.EVENT.toString())) {
				// retrieve from server event post identified by postId
				Initiativeendpoint.Builder builder = new Initiativeendpoint.Builder(
						AndroidHttp.newCompatibleTransport(),
						new JacksonFactory(), null);

				builder = CloudEndpointUtils.updateBuilder(builder);
				initiativeEnd = builder.setApplicationName("polimisocial")
						.build();
				try {
					Initiative post = initiativeEnd.getInitiative(postId)
							.execute();
					mItems.add(post);
				} catch (IOException e) {

					e.printStackTrace();
				}
			} else if (postType.equals(PostType.PRIVATE_LESSON.toString())) {
				
				// TODO retrieve post needed as above
			
			}else if(postType.equals(PostType.RENTAL.toString())){
				// retrieve from server event post identified by postId
				Rentalendpoint.Builder builder = new Rentalendpoint.Builder(
						AndroidHttp.newCompatibleTransport(),
						new JacksonFactory(), null);

				builder = CloudEndpointUtils.updateBuilder(builder);
				rentalEnd = builder.setApplicationName("polimisocial")
						.build();
				try {
					Rental post = rentalEnd.getRental(postId)
							.execute();
					mItems.add(post);
				} catch (IOException e) {

					e.printStackTrace();
				}				
			}else if(postType.equals(PostType.SECOND_HAND_BOOK.toString())){
				
				//TODO retrieve post needed as above
				
			}
		}
		//if we have to show comment and not hiton, then retrieve comments related to the post
		if (!postType.equals(PostType.HIT_ON.toString())) {
			
			//TODO limitare la query a 5/10 commenti e passare il cursore nella lista
			
			Commentendpoint.Builder build = new Commentendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
					null);

			build = CloudEndpointUtils.updateBuilder(build);
			Commentendpoint commEndpoint = build.setApplicationName(
					"polimisocial").build();

			CollectionResponseComment comments;
			try {
				comments = commEndpoint.getPostComments(postId).execute();

				if (comments.getItems() != null) {
					for (Comment c : comments.getItems())
						mItems.add(c);
				}
			} catch (IOException e) {
				e.printStackTrace();
				Toast.makeText(getContext(),
						"Retreiving comments failed.Connection error.",
						Toast.LENGTH_SHORT).show();
			}
			// instead, if we want to show hit_on...
		} else {
			Hitonendpoint.Builder build = new Hitonendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
					null);

			build = CloudEndpointUtils.updateBuilder(build);
			Hitonendpoint commEndpoint = build.setApplicationName(
					"polimisocial").build();

			CollectionResponseHitOn comments;
			try {
				comments = commEndpoint.getUserHitOn(postId).execute();

				if (comments.getItems() != null) {
					for (HitOn c : comments.getItems())
						mItems.add(c);
				}
			} catch (IOException e) {
				e.printStackTrace();
				Toast.makeText(getContext(),
						"Retreiving comments failed.Connection error.",
						Toast.LENGTH_SHORT).show();
			}
		}

		return mItems;
	}

}