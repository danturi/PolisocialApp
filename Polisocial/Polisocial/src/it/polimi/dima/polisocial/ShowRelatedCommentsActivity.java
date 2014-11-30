package it.polimi.dima.polisocial;

import it.polimi.dima.polisocial.adapter.CommentAdapter;
import it.polimi.dima.polisocial.adapter.EventAdapter;
import it.polimi.dima.polisocial.customListeners.IdTextParametersOnClickListener;
import it.polimi.dima.polisocial.entity.commentendpoint.Commentendpoint;
import it.polimi.dima.polisocial.entity.commentendpoint.model.Comment;
import it.polimi.dima.polisocial.entity.initiativeendpoint.model.Initiative;
import it.polimi.dima.polisocial.entity.postimageendpoint.Postimageendpoint;
import it.polimi.dima.polisocial.entity.postimageendpoint.model.CollectionResponsePostImage;
import it.polimi.dima.polisocial.entity.postimageendpoint.model.PostImage;
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

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;

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

	private TextView headerTitle;
	private TextView headerTimestamp;
	private TextView headerText;
	private ImageView headerProfilePic;
	private ImageView headerPostImage;
	private TextView headerLocation;
	private TextView headerBeginningDate;
	private TextView headerPrice;
	private TextView headerAuthor;
	private TextView headerPublisher;
	private TextView headerPubYear;
	private TextView headerIsbnCode;
	private TextView headerTypeRentAndSqm;
	private TextView headerAvailableFrom;
	private TextView headerContact;

	private Long postAuthor;

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
		mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_BOTTOM);
		mList = (ListView) findViewById(R.id.comment_list);
		mProgressView = findViewById(R.id.progress);

		postId = getIntent().getLongExtra("postId", 0);
		postAuthor = getIntent().getLongExtra("postAuthor", 0);
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
				headerPrice = (TextView) header.findViewById(R.id.price);
				headerAuthor = (TextView) header.findViewById(R.id.authors);
				headerText = (TextView) header.findViewById(R.id.condition);
				headerPublisher = (TextView) header
						.findViewById(R.id.publisher);
				headerPubYear = (TextView) header
						.findViewById(R.id.publication_year);
				headerIsbnCode = (TextView) header.findViewById(R.id.isbn_code);

			} else if (postType.equals(PostType.PRIVATE_LESSON.toString())) {

				// TODO set-up header for lesson

			} else if (postType.equals(PostType.RENTAL.toString())) {

				header = View.inflate(getApplicationContext(),
						R.layout.rental_notification_header, null);

				rentalGallery = (LinearLayout) header
						.findViewById(R.id.rental_gallery);

				headerTitle = (TextView) header.findViewById(R.id.title);
				headerPrice = (TextView) header.findViewById(R.id.price);
				headerLocation = (TextView) header.findViewById(R.id.address);
				headerAvailableFrom = (TextView) header
						.findViewById(R.id.available_from);
				headerTypeRentAndSqm = (TextView) header
						.findViewById(R.id.type_and_sqmeters);
				headerContact = (TextView) header.findViewById(R.id.contact);
				headerText = (TextView) header
						.findViewById(R.id.description);
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

			if (postType.equals(PostType.SPOTTED.toString())) {
				mAdapter.setData(data, postAuthor);
			} else {
				mAdapter.setData(data);
			}
		} else {
			TextView t = (TextView) findViewById(R.id.no_comment);
			t.setVisibility(View.VISIBLE);
		}

	}

	private void fillUpBookHeader(SecondHandBook item) {
		// TODO Auto-generated method stub
		headerTitle.setText(item.getTitle());
		headerText.setText(item.getText());

		headerPrice.setText(item.getPrice().toString() + " €");
		String authors = item.getAuthorsBook().toString();
		headerAuthor.setText(authors.substring(1, authors.length() - 1));
		if (item.getText() != null)
			headerText.setText("\"" + item.getText() + "\"");
		else
			headerText.setVisibility(View.GONE);
		if (item.getPublisher() != null)
			headerPublisher.setText(item.getPublisher());
		else
			headerPublisher.setVisibility(View.GONE);
		if (item.getPublishedDate() != null)
			headerPubYear.setText(item.getPublishedDate().toString());
		else
			headerPubYear.setVisibility(View.GONE);
		if (item.getIsbn() != null)
			headerIsbnCode.setText(item.getIsbn());
		else
			headerIsbnCode.setVisibility(View.GONE);
	}

	private void fillUpRentalHeader(Rental item) {
		// TODO Auto-generated method stub
		new RetrieveRentalPhotoTask().execute(item.getId());

		headerTitle.setText(item.getTitle());
		headerLocation.setText(item.getAddress());
		headerTypeRentAndSqm.setText(item.getType() + "        "
				+ item.getSquaredMeter() + " mq");
		headerAvailableFrom.setText(this.getResources().getString(R.string.available_from) +"  "+ item.getAvailability().toString().substring(0, 10));
		if (item.getText() != null)
			headerText.setText("\"" + item.getText() + "\"");
		headerContact.setText(this.getResources().getString(R.string.contact)+ "  "+ item.getContact());
		headerPrice.setText(item.getPrice().toString() + " €");
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
		headerTimestamp.setText(this.getResources().getString(R.string.created)
				+ " " + timeAgo);
		headerText.setText(item.getText());

	}

	View insertPhoto(List<PostImage> list) {

		LinearLayout layout = new LinearLayout(getApplicationContext());
		layout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		layout.setHorizontalScrollBarEnabled(true);
		layout.setGravity(Gravity.CENTER);

		for (PostImage img : list) {
			ImageView imageView = new ImageView(getApplicationContext());
			imageView.setLayoutParams(new LayoutParams(450, 450));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setClickable(true);

			final byte[] byteArrayImage = Base64.decode(img.getImage(),
					Base64.DEFAULT);
			;

			imageView.setImageBitmap(BitmapFactory.decodeByteArray(
					byteArrayImage, 0, byteArrayImage.length));
			layout.addView(imageView);

			imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent showFullScreenPicIntent = new Intent(
							getBaseContext(), FullScreenPicActivity.class);
					showFullScreenPicIntent.putExtra("picInByte",
							byteArrayImage);
					startActivity(showFullScreenPicIntent);

				}
			});
		}

		return layout;
	}

	public class RetrieveRentalPhotoTask extends AsyncTask<Long, Void, Boolean> {

		CollectionResponsePostImage arrayPostImage;

		@Override
		protected Boolean doInBackground(Long... params) {

			Postimageendpoint.Builder imageBuilder = new Postimageendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
					null);

			imageBuilder = CloudEndpointUtils.updateBuilder(imageBuilder);

			Postimageendpoint imageEndpoint = imageBuilder.setApplicationName(
					"polimisocial").build();

			try {
				arrayPostImage = imageEndpoint.getImagesFromPostId(params[0])
						.execute();

			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);

			if (result) {
				rentalGallery.addView(insertPhoto(arrayPostImage.getItems()));
			} else {
				rentalGallery.setVisibility(View.GONE);
			}
		}

	}

}
