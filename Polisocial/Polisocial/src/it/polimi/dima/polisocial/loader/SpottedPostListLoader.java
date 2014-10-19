package it.polimi.dima.polisocial.loader;

import it.polimi.dima.polisocial.CloudEndpointUtils;
import it.polimi.dima.polisocial.entity.commentendpoint.Commentendpoint;
import it.polimi.dima.polisocial.entity.commentendpoint.model.ResponseObject;
import it.polimi.dima.polisocial.entity.postspottedendpoint.Postspottedendpoint;
import it.polimi.dima.polisocial.entity.postspottedendpoint.model.CollectionResponsePostSpotted;
import it.polimi.dima.polisocial.entity.postspottedendpoint.model.PostSpotted;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;

import com.google.android.gms.wearable.NodeApi.GetLocalNodeResult;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;

public class SpottedPostListLoader extends
		AsyncTaskLoader<CollectionResponsePostSpotted> {

	CollectionResponsePostSpotted mPostItems;
	String cursor;

	public SpottedPostListLoader(Context context, String curs) {
		super(context);
		cursor = curs;
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

	/**
	 * Called when there is new data to deliver to the client. The super class
	 * will take care of delivering it; the implementation here just adds a
	 * little more logic.
	 */
	@Override
	public void deliverResult(CollectionResponsePostSpotted listOfData) {
		if (isReset()) {
			// An async query came in while the loader is stopped. We
			// don't need the result.
			if (listOfData != null) {
				onReleaseResources(listOfData);
			}
		}
		CollectionResponsePostSpotted oldApps = listOfData;
		mPostItems = listOfData;
		if (isStarted()) {
			// If the Loader is currently started, we can immediately
			// deliver its results.
			super.deliverResult(listOfData);
		}

		// At this point we can release the resources associated with
		// 'oldApps' if needed; now that the new result is delivered we
		// know that it is no longer in use.
		if (oldApps != null) {
			onReleaseResources(oldApps);
		}
	}

	/**
	 * Handles a request to start the Loader.
	 */
	@Override
	protected void onStartLoading() {

		if (takeContentChanged() || mPostItems == null || cursor == null) {
			// If the data has changed since the last time it was loaded
			// or is not currently available, start a load.
			forceLoad();
		}
		if (mPostItems != null) {
			// If we currently have a result available, deliver it
			// immediately.
			deliverResult(mPostItems);
		}

	}

	/**
	 * Handles a request to stop the Loader.
	 */
	@Override
	protected void onStopLoading() {
		// Attempt to cancel the current load task if possible.
		cancelLoad();
	}

	/**
	 * Handles a request to cancel a load.
	 */
	@Override
	public void onCanceled(CollectionResponsePostSpotted apps) {
		super.onCanceled(apps);

		// At this point we can release the resources associated with 'apps'
		// if needed.
		onReleaseResources(apps);
	}

	/**
	 * Handles a request to completely reset the Loader.
	 */
	@Override
	protected void onReset() {
		super.onReset();

		// Ensure the loader is stopped
		onStopLoading();

		// At this point we can release the resources associated with 'apps'
		// if needed.
		if (mPostItems != null) {
			onReleaseResources(mPostItems);
			mPostItems = null;
		}
	}

	/**
	 * Helper function to take care of releasing resources associated with an
	 * actively loaded data set.
	 */
	protected void onReleaseResources(CollectionResponsePostSpotted listOfData) {
	}

}