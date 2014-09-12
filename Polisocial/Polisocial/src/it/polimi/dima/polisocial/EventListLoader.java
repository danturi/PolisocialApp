package it.polimi.dima.polisocial;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public class EventListLoader extends AsyncTaskLoader<List<EventItem>> {
	   
    List<EventItem> mEventItems;

    public EventListLoader(Context context) {
        super(context);
    }

    @Override
    public List<EventItem> loadInBackground() {
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
        
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
      
        // Create corresponding array of entries and load with data.
        List<EventItem> entries = new ArrayList<EventItem>(5);
        entries.add(new EventItem(10, "Maratona del Buthan", "L�evento inizia Venerd� 29 maggio con l�incontro dei partecipanti nell�hotel di Thimphu, la capital del Bhutan e finir� a Paro Sabato 6 giugno.", now, 4,"sport"));
        entries.add(new EventItem(10, "Maratona del Buthan", "L�evento inizia Venerd� 29 maggio con l�incontro dei partecipanti nell�hotel di Thimphu, la capital del Bhutan e finir� a Paro Sabato 6 giugno.", now, 4,"sport"));
        entries.add(new EventItem(10, "Maratona del Buthan", "L�evento inizia Venerd� 29 maggio con l�incontro dei partecipanti nell�hotel di Thimphu, la capital del Bhutan e finir� a Paro Sabato 6 giugno.", now, 4,"sport"));
        entries.add(new EventItem(10, "Maratona del Buthan", "L�evento inizia Venerd� 29 maggio con l�incontro dei partecipanti nell�hotel di Thimphu, la capital del Bhutan e finir� a Paro Sabato 6 giugno.", now, 4,"sport"));
        entries.add(new EventItem(10, "Maratona del Buthan", "L�evento inizia Venerd� 29 maggio con l�incontro dei partecipanti nell�hotel di Thimphu, la capital del Bhutan e finir� a Paro Sabato 6 giugno.", now, 4,"sport"));
        return entries;
    }
     
    /**
     * Called when there is new data to deliver to the client.  The
     * super class will take care of delivering it; the implementation
     * here just adds a little more logic.
     */
    @Override public void deliverResult(List<EventItem> listOfData) {
        if (isReset()) {
            // An async query came in while the loader is stopped.  We
            // don't need the result.
            if (listOfData != null) {
                onReleaseResources(listOfData);
            }
        }
        List<EventItem> oldApps = listOfData;
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
    @Override protected void onStartLoading() {
        if (mEventItems != null) {
            // If we currently have a result available, deliver it
            // immediately.
            deliverResult(mEventItems);
        }


        if (takeContentChanged() || mEventItems == null) {
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
    @Override public void onCanceled(List<EventItem> apps) {
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
        if (mEventItems != null) {
            onReleaseResources(mEventItems);
            mEventItems = null;
        }
    }

    /**
     * Helper function to take care of releasing resources associated
     * with an actively loaded data set.
     */
    protected void onReleaseResources(List<EventItem> apps) {}
     

}
