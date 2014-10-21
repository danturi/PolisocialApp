package it.polimi.dima.polisocial.loader;

import it.polimi.dima.polisocial.CloudEndpointUtils;
import it.polimi.dima.polisocial.entity.commentendpoint.Commentendpoint;
import it.polimi.dima.polisocial.entity.commentendpoint.model.ResponseObject;
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
		// GET DEI POST DA APP ENGINE
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

		CollectionResponsePostSpotted list = new CollectionResponsePostSpotted();
		// List<PostSpotted> entries = new ArrayList<PostSpotted>();
		try {
			if (cursor != null) {
				list = endpoint.listPostSpotted().setLimit(10)
						.setCursor(cursor).execute();
			} else {
				list = endpoint.listPostSpotted().setLimit(10).execute();
			}

			if (list.getItems() != null) {
				for (PostSpotted post : list.getItems()) {
					ResponseObject count = commEndpoint.getNumbPostComments(
							post.getId()).execute();
					post.setNumOfComments(Integer.valueOf((String) count
							.getObject()));
					// entries.add(post);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		cursor = null;
		return list;
	}
}