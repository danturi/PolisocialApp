package it.polimi.dima.polisocial;

import it.polimi.dima.polisocial.adapter.CommentAdapter;
import it.polimi.dima.polisocial.adapter.EventAdapter;
import it.polimi.dima.polisocial.customListeners.IdTextParametersOnClickListener;
import it.polimi.dima.polisocial.entity.commentendpoint.Commentendpoint;
import it.polimi.dima.polisocial.entity.commentendpoint.model.Comment;
import it.polimi.dima.polisocial.entity.initiativeendpoint.model.Initiative;
import it.polimi.dima.polisocial.entity.postimageendpoint.Postimageendpoint;
import it.polimi.dima.polisocial.entity.postspottedendpoint.model.PostSpotted;
import it.polimi.dima.polisocial.entity.rentalendpoint.model.Rental;
import it.polimi.dima.polisocial.entity.secondhandbookendpoint.model.SecondHandBook;
import it.polimi.dima.polisocial.loader.CommentListLoader;
import it.polimi.dima.polisocial.utilClasses.PostType;
import it.polimi.dima.polisocial.utilClasses.SessionManager;
import it.polimi.dima.polisocial.utilClasses.ShowProgress;
import it.polimi.dima.polisocial.utilClasses.WhatToShow;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowRelatedCommentsActivity<D> extends SwipeBackActivity implements
		LoaderManager.LoaderCallbacks<List<Object>> {

	private SwipeBackLayout mSwipeBackLayout;
	private CommentAdapter mAdapter;
	private long postId;
	private String whatToShow;
	private String postType;
	private ListView mList;
	private View mProgressView;
	private SessionManager sessionManager;
	private Commentendpoint endpoint;
	private View bottomControlBar;
	private View header;
	private String mCursor = null;

	TextView headerTitle;
	TextView headerTimestamp;
	TextView headerText;
	ImageView headerProfilePic;
	ImageView headerPostImage;
	TextView headerLocation;
	TextView headerBeginningDate;
	
	 LinearLayout rentalGallery;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_related_comments);

		Commentendpoint.Builder builder = new Commentendpoint.Builder(
				AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
				null);

		builder = CloudEndpointUtils.updateBuilder(builder);
		endpoint = builder.setApplicationName("polimisocial").build();

		sessionManager = new SessionManager(getApplicationContext());
		mSwipeBackLayout = getSwipeBackLayout();
		mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_ALL);
		mList = (ListView) findViewById(R.id.comment_list);
		mProgressView = findViewById(R.id.progress);

		postId = getIntent().getLongExtra("postId", 0);
		whatToShow = getIntent().getStringExtra("notificationCategory");
		postType = getIntent().getStringExtra("type");

		// Create an empty adapter we will use to display the loaded data.
		mAdapter = new CommentAdapter(this, postType);

		// if we show comments, set up the bottom bar containing the editext to
		// write a comment
		if (!postType.equals(PostType.HIT_ON.toString())) {
			final TextView commentText = (TextView) findViewById(R.id.comment);
			ImageButton sendComment = (ImageButton) findViewById(R.id.send_comment_button);

			sendComment.setOnClickListener(new IdTextParametersOnClickListener(
					postId, commentText) {

				@Override
				public void onClick(View v) {
					String text = commentText.getText().toString();
					// if the user try to send a non-empty comment...
					if (!text.matches("")) {
						ShowProgress.showProgress(true, mProgressView, mList,
								getApplicationContext());
						new InsertCommentTask(postId).execute(text);

					}
				}
			});
		} else {
			bottomControlBar = findViewById(R.id.bottom_control_bar);
			bottomControlBar.setVisibility(View.GONE);
		}

		// if we have to show a header
		if (!whatToShow.equals(WhatToShow.ONLY_COMMENTS.toString())) {

			if (postType.equals(PostType.HIT_ON.toString())
					|| postType.equals(PostType.SPOTTED.toString())) {

				header = View.inflate(getApplicationContext(),
						R.layout.spotted_notification_header, null);

				headerTitle = (TextView) header.findViewById(R.id.title);
				headerTimestamp = (TextView) header
						.findViewById(R.id.timestamp);
				headerText = (TextView) header.findViewById(R.id.text);
				headerProfilePic = (ImageView) header
						.findViewById(R.id.profilePic);
				headerPostImage = (ImageView) header
						.findViewById(R.id.postImage);

			} else if (postType.equals(PostType.EVENT.toString())) {

				header = View.inflate(getApplicationContext(),
						R.layout.event_notification_header, null);

				headerPostImage = (ImageView) header
						.findViewById(R.id.event_picture);
				headerTitle = (TextView) header.findViewById(R.id.title);
				headerBeginningDate = (TextView) header
						.findViewById(R.id.beginning_date);
				headerText = (TextView) header.findViewById(R.id.description);
				headerTimestamp = (TextView) header
						.findViewById(R.id.timestamp);
				headerLocation = (TextView) header.findViewById(R.id.location);

			} else if (postType.equals(PostType.SECOND_HAND_BOOK.toString())) {

				header = View.inflate(getApplicationContext(),
						R.layout.book_notification_header, null);

				headerTitle = (TextView) header.findViewById(R.id.title);
			} else if (postType.equals(PostType.PRIVATE_LESSON.toString())) {

				// TODO set-up header for lesson

			} else if (postType.equals(PostType.RENTAL.toString())) {

				header = View.inflate(getApplicationContext(),
						R.layout.rental_notification_header, null);

				rentalGallery = (LinearLayout)findViewById(R.id.rental_gallery);

				headerTitle = (TextView) header.findViewById(R.id.title);

				// TODO set-up header for rental

			}
			mList.addHeaderView(header);

		}

		mList.setAdapter(mAdapter);
		getSupportLoaderManager().initLoader(0, null, this);
	}

	private void loadData() {
		getLoaderManager().restartLoader(0, null, (LoaderCallbacks<D>) this);
	}

	@Override
	public Loader<List<Object>> onCreateLoader(int arg0, Bundle bundle) {
		ShowProgress.showProgress(true, mProgressView, mList,
				getApplicationContext());
		return new CommentListLoader(this, mCursor, whatToShow, postType,
				postId);

	}

	@Override
	public void onLoadFinished(Loader<List<Object>> arg0, List<Object> data) {
		ShowProgress.showProgress(false, mProgressView, mList,
				getApplicationContext());
		// TODO prendere cursor per i commenti
		if (!data.isEmpty()) {

			// only if we have a header...
			if (!whatToShow.equals(WhatToShow.ONLY_COMMENTS.toString())) {

				if (postType.equals(PostType.SPOTTED.toString())
						|| postType.equals(PostType.HIT_ON.toString())) {
					fillUpSpottedHeader((PostSpotted) data.remove(0));
				} else if (postType.equals(PostType.EVENT.toString())) {

					fillUpEventHeader((Initiative) data.remove(0));

				} else if (postType
						.equals(PostType.SECOND_HAND_BOOK.toString())) {
					fillUpBookHeader((SecondHandBook) data.remove(0));

				} else if (postType.equals(PostType.PRIVATE_LESSON.toString())) {

					// TODO fill header for lesson

				} else if (postType.equals(PostType.RENTAL.toString())) {

					fillUpRentalHeader((Rental) data.remove(0));

				}

			}

			mAdapter.setData(data);
		} else {
			TextView t = (TextView) findViewById(R.id.no_comment);
			t.setVisibility(View.VISIBLE);
		}

	}

	
	private void fillUpBookHeader(SecondHandBook item) {
		// TODO Auto-generated method stub
		headerTitle.setText(item.getTitle());
	}
	
	private void fillUpRentalHeader(Rental item) {
		// TODO Auto-generated method stub
		headerTitle.setText(item.getTitle());
		
		/*for (Bitmap b : bitmaps ){
	         rentalGallery.addView(insertPhoto(b));
	        }*/   
	}

	@Override
	public void onLoaderReset(Loader<List<Object>> arg0) {
		mAdapter.setData(null);
	}

	public class InsertCommentTask extends AsyncTask<String, Void, Boolean> {

		Long postId;
		Comment comment;

		public InsertCommentTask(long postId) {
			this.postId = postId;
		}

		@Override
		protected Boolean doInBackground(String... params) {

			comment = new Comment();
			comment.setAuthorId(Long.valueOf(sessionManager.getUserDetails()
					.get(SessionManager.KEY_USERID)));
			comment.setAuthorName(sessionManager.getUserDetails().get(
					SessionManager.KEY_NAME));
			Calendar calendar = Calendar.getInstance();
			Date now = calendar.getTime();
			// Timestamp currentTimestamp = new
			// java.sql.Timestamp(now.getTime());
			comment.setCommentTimestamp(new DateTime(now));
			comment.setPostId(postId);
			comment.setText(params[0]);
			comment.setType(postType);

			try {
				endpoint.insertComment(comment).execute();
				// endpoint.sendNotification(comment).execute();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}

			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if (result) {
				ShowProgress.showProgress(false, mProgressView, mList,
						getApplicationContext());
				// new SendNotificationTask().execute(comment);
				Toast.makeText(getBaseContext(), "Insert comment done!",
						Toast.LENGTH_LONG).show();
				new SendNotificationTask().execute(comment);

			} else {
				ShowProgress.showProgress(false, mProgressView, mList,
						getApplicationContext());
				Toast.makeText(getBaseContext(),
						"Insert comment failed.Connection error.",
						Toast.LENGTH_SHORT).show();
			}
			super.onPostExecute(result);
		}

	}

	public class SendNotificationTask extends AsyncTask<Comment, Void, Void> {

		@Override
		protected Void doInBackground(Comment... params) {
			Commentendpoint.Builder builder = new Commentendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
					null);

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

	private void fillUpSpottedHeader(PostSpotted item) {

		headerTitle.setText(item.getTitle());
		// Converting timestamp into time ago format
		CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(item
				.getTimestamp().getValue(), System.currentTimeMillis(),
				DateUtils.SECOND_IN_MILLIS);
		headerTimestamp.setText(timeAgo);
		// Check for empty status message
		if (!TextUtils.isEmpty(item.getText())) {
			headerText.setText(item.getText());
			headerText.setVisibility(View.VISIBLE);
		} else {
			// status is empty, remove from view
			headerText.setVisibility(View.GONE);
		}
		switch (item.getPostCategory()) {
		case "Fun":
			headerProfilePic.setImageResource(R.drawable.owl_fun);
			break;
		case "Love":
			headerProfilePic.setImageResource(R.drawable.owl_love);
			break;
		case "Complaint":
			headerProfilePic.setImageResource(R.drawable.owl_complaint);
			break;
		case "Advice":
			headerProfilePic.setImageResource(R.drawable.owl_advice);
			break;
		default:
			headerProfilePic.setImageResource(R.drawable.owl_fun);

		}
		// Feed image
		if (item.getHavePicture()) {
			headerPostImage.setVisibility(View.VISIBLE);

			// asynctask to retrieve post image
			new AsyncTask<Object, Void, Boolean>() {
				private String s;

				@Override
				protected Boolean doInBackground(Object... params) {
					Postimageendpoint.Builder imageBuilder = new Postimageendpoint.Builder(
							AndroidHttp.newCompatibleTransport(),
							new JacksonFactory(), null);

					imageBuilder = CloudEndpointUtils
							.updateBuilder(imageBuilder);

					Postimageendpoint imageEndpoint = imageBuilder
							.setApplicationName("polimisocial").build();

					try {
						s = imageEndpoint.getImageFromPostId((long) params[0])
								.execute().getImage();
					} catch (IOException e2) {
						System.out.println(e2.getMessage());
						return false;
					}
					return true;
				}

				@Override
				protected void onPostExecute(Boolean result) {
					if (result) {
						final byte[] byteArrayImage = Base64.decode(s,
								Base64.DEFAULT);
						headerPostImage.setImageBitmap(BitmapFactory
								.decodeByteArray(byteArrayImage, 0,
										byteArrayImage.length));
						headerPostImage
								.setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View v) {
										Intent showFullScreenPicIntent = new Intent(
												getBaseContext(),
												FullScreenPicActivity.class);
										showFullScreenPicIntent.putExtra(
												"picInByte", byteArrayImage);
										getBaseContext().startActivity(
												showFullScreenPicIntent);
									}
								});
					}
				}
			}.execute(item.getId());
		}
	}

	private void fillUpEventHeader(Initiative item) {
		// Event image
		if (item.getHavePicture()) {
			// asynctask to retrieve post image
			new AsyncTask<Object, Void, Boolean>() {
				private String s;

				@Override
				protected Boolean doInBackground(Object... params) {
					Postimageendpoint.Builder imageBuilder = new Postimageendpoint.Builder(
							AndroidHttp.newCompatibleTransport(),
							new JacksonFactory(), null);

					imageBuilder = CloudEndpointUtils
							.updateBuilder(imageBuilder);

					Postimageendpoint imageEndpoint = imageBuilder
							.setApplicationName("polimisocial").build();

					try {
						s = imageEndpoint.getImageFromPostId((long) params[0])
								.execute().getImage();
					} catch (IOException e2) {
						System.out.println(e2.getMessage());
						return false;
					}
					return true;
				}

				@Override
				protected void onPostExecute(Boolean result) {

					if (result) {
						final byte[] byteArrayImage = Base64.decode(s,
								Base64.DEFAULT);
						headerPostImage.setImageBitmap(BitmapFactory
								.decodeByteArray(byteArrayImage, 0,
										byteArrayImage.length));
						headerPostImage
								.setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View v) {
										Intent showFullScreenPicIntent = new Intent(
												getBaseContext(),
												FullScreenPicActivity.class);
										showFullScreenPicIntent.putExtra(
												"picInByte", byteArrayImage);
										startActivity(showFullScreenPicIntent);
									}
								});
					}
				}
			}.execute(item.getId());
		}

		headerTitle.setText(item.getTitle());

		headerLocation.setText(item.getLocation());
		String dateTime = item.getBeginningDate().toString();
		String dateString = EventAdapter.composeDateString(
				dateTime.substring(0, 4), dateTime.substring(5, 7),
				dateTime.substring(8, 10), null, null);
		String time = dateTime.substring(11, Math.min(dateTime.length(), 16));

		headerBeginningDate.setText(dateString + " at " + time);

		CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(item
				.getTimestamp().getValue(), System.currentTimeMillis(),
				DateUtils.SECOND_IN_MILLIS);
		headerTimestamp.setText("created " + timeAgo);
		headerText.setText(item.getText());

	}
	
	
    View insertPhoto(String path){
      //  Bitmap bm = decodeSampledBitmapFromUri(path, 220, 220);
        
        LinearLayout layout = new LinearLayout(getApplicationContext());
        layout.setLayoutParams(new LayoutParams(250, 250));
        layout.setGravity(Gravity.CENTER);
        
        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setLayoutParams(new LayoutParams(220, 220));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
       // imageView.setImageBitmap(bm);
        
        layout.addView(imageView);
        return layout;
       }

}
