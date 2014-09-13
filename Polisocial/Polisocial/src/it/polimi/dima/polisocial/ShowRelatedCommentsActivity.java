package it.polimi.dima.polisocial;

import it.polimi.dima.polisocial.customOnClickListeners.IdTextParametersOnClickListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class ShowRelatedCommentsActivity<D> extends SwipeBackActivity implements LoaderManager.LoaderCallbacks<List<CommentItem>> {

	SwipeBackLayout mSwipeBackLayout;
	CommentAdapter mAdapter;	
	long postId;
	private ListView mList;
	private View mProgressView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_related_comments);

		mSwipeBackLayout = getSwipeBackLayout();
		mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_ALL);
		mList = (ListView) findViewById(R.id.comment_list);
		postId=getIntent().getLongExtra("postId", 0);
		mProgressView = findViewById(R.id.comments_progress);
		
		TextView commentText = (TextView) findViewById(R.id.comment);
		ImageButton sendComment = (ImageButton)findViewById(R.id.send_comment_button);
		
		sendComment.setOnClickListener(new IdTextParametersOnClickListener(postId,commentText) {

            @Override
            public void onClick(View v) {
            	String text= comment.getText().toString();
            	if(!text.matches("")){
            		//addCommentToServer(postId,text,nomeutente);
            		finish();
            	}
            }
        });
		
        // Create an empty adapter we will use to display the loaded data.
        mAdapter = new CommentAdapter(this);
        mList.setAdapter(mAdapter);
	
        //show a progress bar instead of list
        
        // Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
        getSupportLoaderManager().initLoader(0, null,this);
	}

	/**
	 * Shows the progress UI and hides the comment list.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mList.setVisibility(show ? View.GONE : View.VISIBLE);
			mList.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mList.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});

			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
			mProgressView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mProgressView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
			mList.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}
	
	
	
	@Override
	public Loader<List<CommentItem>> onCreateLoader(int arg0, Bundle arg1) {
		showProgress(true);
		return new CommentListLoader(this);
        
	}

	@Override
	public void onLoadFinished(Loader<List<CommentItem>> arg0,
			List<CommentItem> data) {
		showProgress(false);
		mAdapter.setData(data);
        // The list should now be shown.
	}

	@Override
	public void onLoaderReset(Loader<List<CommentItem>> arg0) {
		 mAdapter.setData(null);
	}
	
	
	
	
	
	
	public static class CommentListLoader extends AsyncTaskLoader<List<CommentItem>> {
        
        List<CommentItem> mCommentItems;
 
        public CommentListLoader(Context context) {
            super(context);
        }
 
        @Override
        public List<CommentItem> loadInBackground() {
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
//                this date will represent the current instant, or "now".
            java.util.Date now = calendar.getTime();
             
            // 3) a java current time (now) instance
            java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
            // Create corresponding array of entries and load with data.
            List<CommentItem> entries = new ArrayList<CommentItem>(5);
            entries.add(new CommentItem(10, "Giulio Cesare", null, "Gallia est omnis divisa in partes tres, quarum unam incolunt Belgae, aliam Aquitani, tertiam qui ipsorum lingua Celtae, nostra Galli appellantur. Hi omnes lingua institutis legibus inter se differunt.", null, currentTimestamp.toString(),10));
            entries.add(new CommentItem(10, "Giulio Cesare", null, "these issues we need to compress the image and give proper rotation before loading it to memory. The following method compresses image", null, currentTimestamp.toString(),3));
            entries.add(new CommentItem(10, "Giulio Cesare", null, "these issues we need to compress the image and give proper rotation before loading it to memory. The following method compresses image", null, currentTimestamp.toString(),2));
            entries.add(new CommentItem(10, "Giulio Cesare", null, "these issues we need to compress the image and give proper rotation before loading it to memory. The following method compresses image", null, currentTimestamp.toString(),11));
            entries.add(new CommentItem(10, "Giulio Cesare", null, "these issues we need to compress the image and give proper rotation before loading it to memory. The following method compresses image", null, currentTimestamp.toString(),2));
            entries.add(new CommentItem(10, "Giulio Cesare", null, "these issues we need to compress the image and give proper rotation before loading it to memory. The following method compresses image", null, currentTimestamp.toString(),11));
 
            return entries;
        }
         
        /**
         * Called when there is new data to deliver to the client.  The
         * super class will take care of delivering it; the implementation
         * here just adds a little more logic.
         */
        @Override public void deliverResult(List<CommentItem> listOfData) {
            if (isReset()) {
                // An async query came in while the loader is stopped.  We
                // don't need the result.
                if (listOfData != null) {
                    onReleaseResources(listOfData);
                }
            }
            List<CommentItem> oldApps = listOfData;
            mCommentItems = listOfData;
 
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
            if (mCommentItems != null) {
                // If we currently have a result available, deliver it
                // immediately.
                deliverResult(mCommentItems);
            }
 
 
            if (takeContentChanged() || mCommentItems == null) {
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
        @Override public void onCanceled(List<CommentItem> apps) {
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
            if (mCommentItems != null) {
                onReleaseResources(mCommentItems);
                mCommentItems = null;
            }
        }
 
        /**
         * Helper function to take care of releasing resources associated
         * with an actively loaded data set.
         */
        protected void onReleaseResources(List<CommentItem> apps) {}
         
    }
	
	
	
}
