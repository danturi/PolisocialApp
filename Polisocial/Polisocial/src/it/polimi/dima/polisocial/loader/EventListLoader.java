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
					
					post.setNumOfLikes(post.getNumOfLikes());
					post.setNumOfGoing(post.getNumOfGoing());
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

}
