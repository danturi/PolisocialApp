package it.polimi.dima.polisocial;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public class SpottedPostListLoader extends AsyncTaskLoader<List<PostItem>> {
    
    List<PostItem> mPostItems;

    public SpottedPostListLoader(Context context) {
        super(context);
    }

    @Override
    public List<PostItem> loadInBackground() {
        try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
         // You should perform the heavy task of getting data from 
         // Internet or database or other source 
         // Here, we are generating some Sample data

        
        //GET DEI POST DA APP ENGINE
        //GET PROFILE PIC DA APP ENGINE
        
     // 1) create a java calendar instance
        Calendar calendar = Calendar.getInstance();
         
        // 2) get a java.util.Date from the calendar instance.
//            this date will represent the current instant, or "now".
        java.util.Date now = calendar.getTime();
         
        // 3) a java current time (now) instance
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        // Create corresponding array of entries and load with data.
        List<PostItem> entries = new ArrayList<PostItem>(5);
        entries.add(new PostItem(10, "Giulio Cesare", null, "Gallia est omnis divisa in partes tres, quarum unam incolunt Belgae, aliam Aquitani, tertiam qui ipsorum lingua Celtae, nostra Galli appellantur. Hi omnes lingua institutis legibus inter se differunt.", null, currentTimestamp.toString(),10));
        entries.add(new PostItem(10, "Giulio Cesare", null, "these issues we need to compress the image and give proper rotation before loading it to memory. The following method compresses image", null, currentTimestamp.toString(),3));
        entries.add(new PostItem(10, "Giulio Cesare", null, "these issues we need to compress the image and give proper rotation before loading it to memory. The following method compresses image", null, currentTimestamp.toString(),2));
        entries.add(new PostItem(10, "Giulio Cesare", null, "these issues we need to compress the image and give proper rotation before loading it to memory. The following method compresses image", null, currentTimestamp.toString(),11));
        entries.add(new PostItem(10, "Giulio Cesare", null, "these issues we need to compress the image and give proper rotation before loading it to memory. The following method compresses image", null, currentTimestamp.toString(),11));
        entries.add(new PostItem(10, "Giulio Cesare", null, "these issues we need to compress the image and give proper rotation before loading it to memory. The following method compresses image", null, currentTimestamp.toString(),11));
        return entries;
    }
     
    /**
     * Called when there is new data to deliver to the client.  The
     * super class will take care of delivering it; the implementation
     * here just adds a little more logic.
     */
    @Override public void deliverResult(List<PostItem> listOfData) {
        if (isReset()) {
            // An async query came in while the loader is stopped.  We
            // don't need the result.
            if (listOfData != null) {
                onReleaseResources(listOfData);
            }
        }
        List<PostItem> oldApps = listOfData;
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
    @Override protected void onStartLoading() {
        if (mPostItems != null) {
            // If we currently have a result available, deliver it
            // immediately.
            deliverResult(mPostItems);
        }


        if (takeContentChanged() || mPostItems == null) {
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
    @Override public void onCanceled(List<PostItem> apps) {
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
        if (mPostItems != null) {
            onReleaseResources(mPostItems);
            mPostItems = null;
        }
    }

    /**
     * Helper function to take care of releasing resources associated
     * with an actively loaded data set.
     */
    protected void onReleaseResources(List<PostItem> apps) {}
     
}