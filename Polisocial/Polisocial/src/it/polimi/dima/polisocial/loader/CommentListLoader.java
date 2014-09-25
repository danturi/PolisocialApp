package it.polimi.dima.polisocial.loader;

import it.polimi.dima.polisocial.CloudEndpointUtils;
import it.polimi.dima.polisocial.entity.commentendpoint.Commentendpoint;
import it.polimi.dima.polisocial.entity.commentendpoint.model.CollectionResponseComment;
import it.polimi.dima.polisocial.entity.commentendpoint.model.Comment;
import it.polimi.dima.polisocial.entity.initiativeendpoint.Initiativeendpoint;
import it.polimi.dima.polisocial.entity.initiativeendpoint.model.Initiative;
import it.polimi.dima.polisocial.entity.postspottedendpoint.Postspottedendpoint;
import it.polimi.dima.polisocial.entity.postspottedendpoint.model.PostSpotted;
import it.polimi.dima.polisocial.utilClasses.NotificationCategory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.widget.Toast;

public class CommentListLoader extends AsyncTaskLoader<List<Object>> {
	List<Object> mObjects;
	String notificationCategory;
	long postId;
	List<Object> entries = new ArrayList<Object>();
	private Postspottedendpoint spottedEnd;
	private Initiativeendpoint initiativeEnd;

	public CommentListLoader(Context context, String notificationCategory, long postId) {
		super(context);
		this.notificationCategory = notificationCategory;
		this.postId = postId;
	}

	@Override
	public List<Object> loadInBackground() {

		// Create corresponding array of entries and load with data.
		List<Object> entries = new ArrayList<Object>();
		//if the notification is about a spotted post, then...
		if (spottedPostNotification()) {
			//retrieve from server the spotted post identified by postId
			Postspottedendpoint.Builder builder = new Postspottedendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);

			builder = CloudEndpointUtils.updateBuilder(builder);
			spottedEnd = builder.setApplicationName("polimisocial").build();
			try {
				PostSpotted post = spottedEnd.getPostSpotted(postId).execute();
				entries.add(post);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else if(eventPostNotification()){
			//retrieve from server event post identified by postId
			Initiativeendpoint.Builder builder = new Initiativeendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);

			builder = CloudEndpointUtils.updateBuilder(builder);
			initiativeEnd = builder.setApplicationName("polimisocial").build();
			try {
				Initiative post = initiativeEnd.getInitiative(postId).execute();
				entries.add(post);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		//announcements case
		if(notHitOn()){
			//retrieve comments related to the post
        	
        	Commentendpoint.Builder build = new Commentendpoint.Builder(
    				AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);

    		build= CloudEndpointUtils.updateBuilder(build);
    		Commentendpoint commEndpoint = build.setApplicationName("polimisocial").build();
    		
    		CollectionResponseComment comments;
			try {
				comments = commEndpoint.getPostComments(postId).execute();
				
	    		
	    		if(comments.getItems()!=null){
	    			for ( Comment c : comments.getItems())
	    	    		entries.add(c);
	    		}
			} catch (IOException e) {
				e.printStackTrace();
				Toast.makeText(getContext(), "Retreiving comments failed.Connection error.",Toast.LENGTH_SHORT).show();
			}
		//case hit_on
		}else{
			//TODO retrieve hit_on related to the post
		}
			
		return entries;
	}

	private boolean spottedPostNotification() {
		return notificationCategory.equals(NotificationCategory.SIMPLE_SPOTTED
				.toString()) || notificationCategory.equals(NotificationCategory.HIT_ON
						.toString());
	}

	private boolean eventPostNotification() {
		return notificationCategory.equals(NotificationCategory.EVENT
				.toString());
	}
	
	private boolean notHitOn() {
		return !notificationCategory.equals(notificationCategory.equals(NotificationCategory.HIT_ON
						.toString()));
	}
	/**
	 * Called when there is new data to deliver to the client. The super
	 * class will take care of delivering it; the implementation here just
	 * adds a little more logic.
	 */
	@Override
	public void deliverResult(List<Object> listOfData) {
		if (isReset()) {
			// An async query came in while the loader is stopped. We
			// don't need the result.
			if (listOfData != null) {
				onReleaseResources(listOfData);
			}
		}
		List<Object> oldApps = listOfData;
		mObjects = listOfData;

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
		if (mObjects != null) {
			// If we currently have a result available, deliver it
			// immediately.
			deliverResult(mObjects);
		}

		if (takeContentChanged() || mObjects == null) {
			// If the data has changed since the last time it was loaded
			// or is not currently available, start a load.
			forceLoad();
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
	public void onCanceled(List<Object> apps) {
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
		if (mObjects != null) {
			onReleaseResources(mObjects);
			mObjects = null;
		}
	}

	/**
	 * Helper function to take care of releasing resources associated with
	 * an actively loaded data set.
	 */
	protected void onReleaseResources(List<Object> apps) {
	}

}