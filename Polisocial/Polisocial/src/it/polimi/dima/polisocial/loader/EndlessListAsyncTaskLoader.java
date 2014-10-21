package it.polimi.dima.polisocial.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public abstract class EndlessListAsyncTaskLoader<D> extends AsyncTaskLoader<D> {

	protected D mItems;
	protected String cursor;
	
	public EndlessListAsyncTaskLoader(Context context, String cursor) {
		super(context);
		this.cursor = cursor;
	}

	
	@Override
	public abstract D loadInBackground();
	
	/**
	 * Called when there is new data to deliver to the client. The super class
	 * will take care of delivering it; the implementation here just adds a
	 * little more logic.
	 */
	@Override
	public void deliverResult(D listOfData) {
		if (isReset()) {
			// An async query came in while the loader is stopped. We
			// don't need the result.
			if (listOfData != null) {
				onReleaseResources(listOfData);
			}
		}
		D oldApps = listOfData;
		mItems = listOfData;
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

		if (takeContentChanged() || mItems == null || cursor == null) {
			// If the data has changed since the last time it was loaded
			// or is not currently available, start a load.
			forceLoad();
		}
		if (mItems != null) {
			// If we currently have a result available, deliver it
			// immediately.
			deliverResult(mItems);
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
	public void onCanceled(D apps) {
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
		if (mItems != null) {
			onReleaseResources(mItems);
			mItems = null;
		}
	}
	
	/**
	 * Helper function to take care of releasing resources associated with an
	 * actively loaded data set.
	 */
	protected void onReleaseResources(D listOfData) {
	}
	
}
