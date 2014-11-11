package it.polimi.dima.polisocial.loader;

import it.polimi.dima.polisocial.CloudEndpointUtils;
import it.polimi.dima.polisocial.entity.dislikeendpoint.Dislikeendpoint;
import it.polimi.dima.polisocial.entity.initiativeendpoint.model.Initiative;
import it.polimi.dima.polisocial.entity.initiativeendpoint.Initiativeendpoint;
import it.polimi.dima.polisocial.entity.initiativeendpoint.model.CollectionResponseInitiative;
import it.polimi.dima.polisocial.entity.likeendpoint.Likeendpoint;

import java.io.IOException;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;
import android.content.Context;

public class EventListLoader extends
		EndlessListAsyncTaskLoader<CollectionResponseInitiative> {

	public EventListLoader(Context context, String cursor) {
		super(context, cursor);
	}

	@Override
	public CollectionResponseInitiative loadInBackground() {
		//retrieve data from server
		Initiativeendpoint.Builder builder = new Initiativeendpoint.Builder(
				AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
				null);
		builder = CloudEndpointUtils.updateBuilder(builder);
		Initiativeendpoint endpoint = builder
				.setApplicationName("polimisocial").build();

/**		Commentendpoint.Builder build = new Commentendpoint.Builder(
				AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
				null);

		build = CloudEndpointUtils.updateBuilder(build);
		Commentendpoint commEndpoint = build.setApplicationName("polimisocial")
				.build();
**/
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

		
		CollectionResponseInitiative list = new CollectionResponseInitiative();
		try {
			if (cursor != null) {
				list = endpoint.listInitiative().setLimit(10).setCursor(cursor)
						.execute();
			} else {
				list = endpoint.listInitiative().setLimit(10).execute();
			}

			if (list.getItems() != null) {
				for (Initiative post : list.getItems()) {
/**					ResponseObject count = commEndpoint.getNumbPostComments(
							post.getId()).execute();
					post.setNumOfComments(Integer.valueOf((String) count
							.getObject()));**/
					it.polimi.dima.polisocial.entity.likeendpoint.model.ResponseObject likeCount = likeEndpoint.getPostLike(
							post.getId()).execute();
					post.setNumOfLikes(Integer.valueOf((String) likeCount
							.getObject()));
					
					it.polimi.dima.polisocial.entity.dislikeendpoint.model.ResponseObject disLikeCount = disLikeEndpoint.getPostDisLike(
							post.getId()).execute();
					post.setNumOfGoing(Integer.valueOf((String) disLikeCount
							.getObject()));
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

}
