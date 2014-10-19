package it.polimi.dima.polisocial.loader;

import it.polimi.dima.polisocial.CloudEndpointUtils;
import it.polimi.dima.polisocial.entity.commentendpoint.Commentendpoint;
import it.polimi.dima.polisocial.entity.commentendpoint.model.ResponseObject;
import it.polimi.dima.polisocial.entity.initiativeendpoint.model.Initiative;
import it.polimi.dima.polisocial.entity.initiativeendpoint.Initiativeendpoint;
import it.polimi.dima.polisocial.entity.initiativeendpoint.model.CollectionResponseInitiative;

import java.io.IOException;


import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public class EventListLoader extends
		AsyncTaskLoader<CollectionResponseInitiative> {

	CollectionResponseInitiative mEventItems;
	String cursor;

	public EventListLoader(Context context, String curs) {
		super(context);
		cursor = curs;
	}

	@Override
	public CollectionResponseInitiative loadInBackground() {
		// GET DEI POST DA APP ENGINE
		Initiativeendpoint.Builder builder = new Initiativeendpoint.Builder(
				AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
				null);
		builder = CloudEndpointUtils.updateBuilder(builder);
		Initiativeendpoint endpoint = builder
				.setApplicationName("polimisocial").build();

		Commentendpoint.Builder build = new Commentendpoint.Builder(
				AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
				null);

		build = CloudEndpointUtils.updateBuilder(build);
		Commentendpoint commEndpoint = build.setApplicationName("polimisocial")
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
	public void deliverResult(CollectionResponseInitiative listOfData) {
		if (isReset()) {
			// An async query came in while the loader is stopped. We
			// don't need the result.
			if (listOfData != null) {
				onReleaseResources(listOfData);
			}
		}
		CollectionResponseInitiative oldApps = listOfData;
		mEventItems = listOfData;

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
		if (takeContentChanged() || mEventItems == null || cursor == null) {
			// If the data has changed since the last time it was loaded
			// or is not currently available, start a load.
			forceLoad();
		}
		if (mEventItems != null) {
			// If we currently have a result available, deliver it
			// immediately.
			deliverResult(mEventItems);
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
	public void onCanceled(CollectionResponseInitiative apps) {
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
		if (mEventItems != null) {
			onReleaseResources(mEventItems);
			mEventItems = null;
		}
	}

	/**
	 * Helper function to take care of releasing resources associated with an
	 * actively loaded data set.
	 */
	protected void onReleaseResources(CollectionResponseInitiative apps) {
	}

}
