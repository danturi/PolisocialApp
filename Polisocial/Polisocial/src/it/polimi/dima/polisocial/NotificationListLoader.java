package it.polimi.dima.polisocial;

import it.polimi.dima.polisocial.entity.notificationendpoint.Notificationendpoint;
import it.polimi.dima.polisocial.entity.notificationendpoint.model.CollectionResponseNotification;
import it.polimi.dima.polisocial.entity.notificationendpoint.model.Notification;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;

public class NotificationListLoader extends AsyncTaskLoader<List<Notification>> {

	List<Notification> mNotificationItems;
	long userId;

    public NotificationListLoader(Context context, long userId) {
        super(context);
        this.userId= userId;
        
    }

    @Override
    public List<Notification> loadInBackground() {
     
    	//retrive notification of the user from APP ENGINE
        Notificationendpoint.Builder builder = new Notificationendpoint.Builder(AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);
        builder = CloudEndpointUtils.updateBuilder(builder);
        Notificationendpoint endpointNotif = builder.setApplicationName("polimisocial").build();
        
    	List<Notification> entries = new ArrayList<Notification>();
    	try {
			CollectionResponseNotification response = endpointNotif.getUserNotification(userId).execute();
			if(response.getItems()!=null){
				for (Notification n : response.getItems()){
					entries.add(n);
				}
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
    	//endpointNotif.
        /*
        // Create corresponding array of entries and load with data.
        
        entries.add(new Notification(10,15, "#polisocial example post title", currentTimestamp.toString(),NotificationCategory.NOT_FROM_NOTIFICATION.toString()));
        entries.add(new Notification(10,15, "#polisocial example post title", currentTimestamp.toString(),NotificationCategory.SIMPLE_SPOTTED.toString()));
        entries.add(new Notification(10,15, "#polisocial event example post title", currentTimestamp.toString(),NotificationCategory.EVENT.toString()));
        entries.add(new Notification(10,15, "#polisocial example post title", currentTimestamp.toString(),NotificationCategory.SIMPLE_SPOTTED.toString()));
        entries.add(new Notification(10,15, "#polisocial example post title", currentTimestamp.toString(),NotificationCategory.SIMPLE_SPOTTED.toString()));
        entries.add(new Notification(10,15, "#polisocial example post title", currentTimestamp.toString(),NotificationCategory.SIMPLE_SPOTTED.toString()));
        entries.add(new Notification(10,15, "#polisocial example post title", currentTimestamp.toString(),NotificationCategory.SIMPLE_SPOTTED.toString()));
        entries.add(new Notification(10,15, "#polisocial example post title", currentTimestamp.toString(),NotificationCategory.SIMPLE_SPOTTED.toString()));
        */
        return entries;
    }
     
    /**
     * Called when there is new data to deliver to the client.  The
     * super class will take care of delivering it; the implementation
     * here just adds a little more logic.
     */
    @Override public void deliverResult(List<Notification> listOfData) {
        if (isReset()) {
            // An async query came in while the loader is stopped.  We
            // don't need the result.
            if (listOfData != null) {
                onReleaseResources(listOfData);
            }
        }
        List<Notification> oldApps = listOfData;
        mNotificationItems = listOfData;

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
    @Override protected void onStartLoading() {
        if (mNotificationItems != null) {
            // If we currently have a result available, deliver it
            // immediately.
            deliverResult(mNotificationItems);
        }


        if (takeContentChanged() || mNotificationItems == null) {
            // If the data has changed since the last time it was loaded
            // or is not currently available, start a load.
            forceLoad();
        }
    }

    /**
     * Handles a request to stop the Loader.
     */
    @Override protected void onStopLoading() {
        // Attempt to cancel the current load task if possible.
        cancelLoad();
    }

    /**
     * Handles a request to cancel a load.
     */
    @Override public void onCanceled(List<Notification> apps) {
        super.onCanceled(apps);

        // At this point we can release the resources associated with 'apps'
        // if needed.
        onReleaseResources(apps);
    }

    /**
     * Handles a request to completely reset the Loader.
     */
    @Override protected void onReset() {
        super.onReset();

        // Ensure the loader is stopped
        onStopLoading();

        // At this point we can release the resources associated with 'apps'
        // if needed.
        if (mNotificationItems != null) {
            onReleaseResources(mNotificationItems);
            mNotificationItems = null;
        }
    }

    /**
     * Helper function to take care of releasing resources associated
     * with an actively loaded data set.
     */
    protected void onReleaseResources(List<Notification> apps) {}
     

}
