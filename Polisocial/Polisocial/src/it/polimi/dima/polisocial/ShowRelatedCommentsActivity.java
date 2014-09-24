package it.polimi.dima.polisocial;

import it.polimi.dima.polisocial.customOnClickListeners.IdTextParametersOnClickListener;
import it.polimi.dima.polisocial.entity.commentendpoint.Commentendpoint;
import it.polimi.dima.polisocial.entity.commentendpoint.model.CollectionResponseComment;
import it.polimi.dima.polisocial.entity.commentendpoint.model.Comment;
import it.polimi.dima.polisocial.entity.initiativeendpoint.Initiativeendpoint;
import it.polimi.dima.polisocial.entity.initiativeendpoint.model.Initiative;
import it.polimi.dima.polisocial.entity.poliuserendpoint.Poliuserendpoint;
import it.polimi.dima.polisocial.entity.poliuserendpoint.model.PoliUser;
import it.polimi.dima.polisocial.entity.postspottedendpoint.Postspottedendpoint;
import it.polimi.dima.polisocial.entity.postspottedendpoint.model.PostSpotted;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TwoLineListItem;

public class ShowRelatedCommentsActivity<D> extends SwipeBackActivity implements
		LoaderManager.LoaderCallbacks<List<Object>> {

	private SwipeBackLayout mSwipeBackLayout;
	private CommentAdapter mAdapter;
	private long postId;
	private String notificationCategory;
	private String type;
	private ListView mList;
	private View mProgressView;
	private SessionManager sessionManager;
	private Commentendpoint endpoint;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_related_comments);

		Commentendpoint.Builder builder = new Commentendpoint.Builder(
				AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);

		builder = CloudEndpointUtils.updateBuilder(builder);
		endpoint = builder.setApplicationName("polimisocial").build();
		
		sessionManager = new SessionManager(getApplicationContext());
		mSwipeBackLayout = getSwipeBackLayout();
		mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_ALL);
		mList = (ListView) findViewById(R.id.comment_list);
		mProgressView = findViewById(R.id.progress);

		postId = getIntent().getLongExtra("postId", 0);
		notificationCategory = getIntent().getStringExtra(
				"notificationCategory");
		type = getIntent().getStringExtra("type");
		

		// if we show comments, set up the bottom bar containing the editext to
		// write a comment
		if (!notificationCategory
				.equals(NotificationCategory.HIT_ON.toString())) {
			final TextView commentText = (TextView) findViewById(R.id.comment);
			ImageButton sendComment = (ImageButton) findViewById(R.id.send_comment_button);

			sendComment.setOnClickListener(new IdTextParametersOnClickListener(
					postId, commentText) {

				@Override
				public void onClick(View v) {
					String text= commentText.getText().toString();
	            	if(!text.matches("")){
	            		showProgress(true);
	            		new InsertCommentTask(postId).execute(text);
	            		 
					}
				}
			});
		}

		// anyway, a list with comments or hit_on has to be set up, passing an
		// appropriate bundle to loader
		// so that it can know whether to load comments or hit_on and the id of
		// the post from which it loads
		// comments or hit_on

		// Create an empty adapter we will use to display the loaded data.
		mAdapter = new CommentAdapter(this, notificationCategory);
		mList.setAdapter(mAdapter);

		// Prepare the loader. Either re-connect with an existing one,
		// or start a new one.

		Bundle bundle = new Bundle();
		bundle.putString("notificationCategory", notificationCategory);
		bundle.putLong("postId", postId);
		getSupportLoaderManager().initLoader(0, bundle, this);
	}

	/**
	 * Shows the progress UI and hides the login form.
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
			mList.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mList.setVisibility(show ? View.GONE : View.VISIBLE);
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
	public Loader<List<Object>> onCreateLoader(int arg0, Bundle bundle) {
		showProgress(true);
		String headerBehaviour = (String) bundle.get("notificationCategory");
		long postId = (Long) bundle.get("postId");
		return new CommentListLoader(this, headerBehaviour, postId);

	}

	@Override
	public void onLoadFinished(Loader<List<Object>> arg0, List<Object> data) {
		showProgress(false);
		if(!data.isEmpty())
			// The list should now be shown.
			mAdapter.setData(data);
		else {
			TextView t= (TextView) findViewById(R.id.no_comments);
			t.setText("There are no comments for this post yet");
		}
		
	}

	@Override
	public void onLoaderReset(Loader<List<Object>> arg0) {
		mAdapter.setData(null);
	}

	public static class CommentListLoader extends AsyncTaskLoader<List<Object>> {

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

	public class InsertCommentTask extends AsyncTask<String, Void, Boolean>{

		Long postId;
		Comment comment;
		
		public InsertCommentTask(long postId) {
			this.postId = postId;
		}

		@Override
		protected Boolean doInBackground(String... params) {
			
			comment = new Comment();
    		comment.setAuthorId(Long.valueOf(sessionManager.getUserDetails().get(SessionManager.KEY_USERID)));
    		comment.setAuthorName(sessionManager.getUserDetails().get(SessionManager.KEY_NAME));
    		Calendar calendar = Calendar.getInstance();
			Date now = calendar.getTime();
    		//Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
    		comment.setCommentTimestamp(new DateTime(now));
    		comment.setPostId(postId);
    		comment.setText(params[0]);
    		comment.setType(type);
    		
    		try {
    			endpoint.insertComment(comment).execute();
    			//endpoint.sendNotification(comment).execute();
    		} catch (IOException e) {
    			e.printStackTrace();
    			return false;
    		}
    	
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if(result){
				showProgress(false);
				//new SendNotificationTask().execute(comment);
				Toast.makeText(getBaseContext(), "Insert comment done!",Toast.LENGTH_LONG).show();
				new SendNotificationTask().execute(comment);
				
			}else {
				showProgress(false);
				Toast.makeText(getBaseContext(), "Insert comment failed.Connection error.",Toast.LENGTH_SHORT).show();
			}
			super.onPostExecute(result);
		}
		
		
	}
	public class SendNotificationTask extends AsyncTask<Comment, Void, Void>{

		@Override
		protected Void doInBackground(Comment... params) {
			Commentendpoint.Builder builder = new Commentendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);

			builder = CloudEndpointUtils.updateBuilder(builder);
			endpoint = builder.setApplicationName("polimisocial").build();
			try {
				endpoint.sendNotification(params[0]).execute();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			
			super.onPostExecute(result);
			finish();
		}
		
	}
	
}
