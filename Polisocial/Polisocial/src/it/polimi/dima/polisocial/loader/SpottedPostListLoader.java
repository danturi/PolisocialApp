package it.polimi.dima.polisocial.loader;

import it.polimi.dima.polisocial.CloudEndpointUtils;
import it.polimi.dima.polisocial.entity.commentendpoint.Commentendpoint;
import it.polimi.dima.polisocial.entity.commentendpoint.model.ResponseObject;
import it.polimi.dima.polisocial.entity.dislikeendpoint.Dislikeendpoint;
import it.polimi.dima.polisocial.entity.likeendpoint.Likeendpoint;
import it.polimi.dima.polisocial.entity.postspottedendpoint.Postspottedendpoint;
import it.polimi.dima.polisocial.entity.postspottedendpoint.model.CollectionResponsePostSpotted;
import it.polimi.dima.polisocial.entity.postspottedendpoint.model.PostSpotted;

import java.io.IOException;

import android.content.Context;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;

public class SpottedPostListLoader extends
		EndlessListAsyncTaskLoader<CollectionResponsePostSpotted> {

	public SpottedPostListLoader(Context context, String cursor) {
		super(context, cursor);
	}

	@Override
	public CollectionResponsePostSpotted loadInBackground() {
		//retrieve data from server
		Postspottedendpoint.Builder builder = new Postspottedendpoint.Builder(
				AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
				null);
		builder = CloudEndpointUtils.updateBuilder(builder);
		Postspottedendpoint endpoint = builder.setApplicationName(
				"polimisocial").build();

		Commentendpoint.Builder build = new Commentendpoint.Builder(
				AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
				null);

		build = CloudEndpointUtils.updateBuilder(build);
		Commentendpoint commEndpoint = build.setApplicationName("polimisocial")
				.build();
		/**
		Likeendpoint.Builder build1 = new Likeendpoint.Builder(
				AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
				null);

		build1 = CloudEndpointUtils.updateBuilder(build1);
		Likeendpoint likeEndpoint = build1.setApplicationName("polimisocial")
				.build();
		
		Dislikeendpoint.Builder build2 = new Dislikeendpoint.Builder(
				AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
				null);

		build2 = CloudEndpointUtils.updateBuilder(build2);
		Dislikeendpoint disLikeEndpoint = build2.setApplicationName("polimisocial")
				.build();
		 **/
		CollectionResponsePostSpotted list = new CollectionResponsePostSpotted();
		try {
			if (cursor != null) {
				list = endpoint.listPostSpotted().setLimit(10)
						.setCursor(cursor).execute();
			} else {
				list = endpoint.listPostSpotted().setLimit(10).execute();
			}

			if (list.getItems() != null) {
				for (PostSpotted post : list.getItems()) {
					/**post.setNumOfComments(0);
					post.setNumOfLikes(0);
					post.setNumOfDisLikes(0);**/
					ResponseObject count = commEndpoint.getNumbPostComments(
							post.getId()).execute();
					post.setNumOfComments(Integer.valueOf((String) count
							.getObject()));
					
					/**
					it.polimi.dima.polisocial.entity.likeendpoint.model.ResponseObject likeCount = likeEndpoint.getPostLike(
							post.getId()).execute();
					post.setNumOfLikes(Integer.valueOf((String) likeCount
							.getObject()));
					
					it.polimi.dima.polisocial.entity.dislikeendpoint.model.ResponseObject disLikeCount = disLikeEndpoint.getPostDisLike(
							post.getId()).execute();
					post.setNumOfDisLikes(Integer.valueOf((String) disLikeCount
							.getObject()));
							**/
					
					post.setNumberLike(post.getNumberLike());
					post.setNumberDislike(post.getNumberDislike());
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
}