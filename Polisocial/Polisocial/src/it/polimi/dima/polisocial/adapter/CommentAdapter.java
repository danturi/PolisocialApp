package it.polimi.dima.polisocial.adapter;

import it.polimi.dima.polisocial.CloudEndpointUtils;
import it.polimi.dima.polisocial.FullScreenPicActivity;
import it.polimi.dima.polisocial.ProfileActivity;
import it.polimi.dima.polisocial.R;
import it.polimi.dima.polisocial.customListeners.IdParameterOnClickListener;
import it.polimi.dima.polisocial.entity.commentendpoint.model.Comment;
import it.polimi.dima.polisocial.entity.hitonendpoint.model.HitOn;
import it.polimi.dima.polisocial.entity.initiativeendpoint.model.Initiative;
import it.polimi.dima.polisocial.entity.poliuserendpoint.Poliuserendpoint;
import it.polimi.dima.polisocial.entity.poliuserendpoint.model.ResponseObject;
import it.polimi.dima.polisocial.entity.postimageendpoint.Postimageendpoint;
import it.polimi.dima.polisocial.entity.postspottedendpoint.model.PostSpotted;
import it.polimi.dima.polisocial.utilClasses.NotificationCategory;

import java.io.IOException;
import java.util.List;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CommentAdapter extends ArrayAdapter<Object> {

	private final int VIEW_ONLY_COMMENTS = 0;
	private final int VIEW_SPOTTED = 1;
	private final int VIEW_EVENT = 2;
	private final int VIEW_HIT_ON = 3;

	private final LayoutInflater mInflater;
	private Context context;
	private String notificationCategory;

	public CommentAdapter(Context context, String notificationCategory) {
		super(context, 0);
		this.context = context;
		this.notificationCategory = notificationCategory;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public void setData(List<Object> data) {
		clear();
		if (data != null) {
			for (Object appEntry : data) {
				add(appEntry);
			}
		}
	}

	@Override
	public int getItemViewType(int position) {
		// Define a way to determine which layout to use.

		// here we handle the case in which the user has clicked show comments
		// in a section
		// and we have to show only a list of comments
		if (notFromNotification()) {
			return VIEW_ONLY_COMMENTS;
			// here we handle the case in which the user come from notification
			// section
		} else {
			// if this is the first element of the list, we have to set an
			// appropriate layout,
			// depending on the type of notification
			switch (position) {
			case 0:
				// if it is a notification about a spotted post...
				if (spottedPostNotification()) {
					return VIEW_SPOTTED;
					// if it is a notification about an event post...
				} else if (eventPostNotification()) {
					return VIEW_EVENT;
				}
				// TODO add announcement case
				break;
			// if it is not the first element of the list, then inflate standard
			// comment layout
			default:
				if (!notificationCategory.equals(NotificationCategory.HIT_ON
						.toString())) {
					return VIEW_ONLY_COMMENTS;
				} else {
					return VIEW_HIT_ON;
				}
			}
		}
		return VIEW_ONLY_COMMENTS;
	}

	@Override
	public int getViewTypeCount() {
		return 4;
	}

	/**
	 * Populate new items in the list.
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int type = getItemViewType(position);

		switch (type) {
		case VIEW_ONLY_COMMENTS:
			CommentViewHolder holder;
			View view = convertView;
			if (view == null) {
				view = mInflater.inflate(R.layout.comment_item, parent, false);
				holder = new CommentViewHolder();
				holder.name = (TextView) view.findViewById(R.id.name);
				holder.timestamp = (TextView) view.findViewById(R.id.timestamp);
				holder.statusMsg = (TextView) view.findViewById(R.id.text);
				holder.profilePic = (ImageView) view
						.findViewById(R.id.profilePic);
			} else {
				holder = (CommentViewHolder) view.getTag();
			}
			setUpCommentRow(position, holder);
			return view;

		case VIEW_SPOTTED:
			SpottedViewHolder spottedHolder;
			View spottedView = convertView;
			if (spottedView == null) {
				spottedView = mInflater.inflate(
						R.layout.spotted_notification_header, parent, false);
				spottedHolder = new SpottedViewHolder();
				spottedHolder.title = (TextView) spottedView.findViewById(R.id.title);
				spottedHolder.timestamp = (TextView) spottedView
						.findViewById(R.id.timestamp);
				spottedHolder.statusMsg = (TextView) spottedView
						.findViewById(R.id.text);
				spottedHolder.profilePic = (ImageView) spottedView
						.findViewById(R.id.profilePic);
				spottedHolder.postImage = (ImageView) spottedView
						.findViewById(R.id.postImage);
			} else {
				spottedHolder = (SpottedViewHolder) spottedView.getTag();
			}
			setUpSpottedHeader(position, spottedHolder);
			return spottedView;

		case VIEW_EVENT:
			EventViewHolder eventHolder;
			View eventView = convertView;
			if (eventView == null) {
				eventView = mInflater.inflate(
						R.layout.event_notification_header, parent, false);
				eventHolder = new EventViewHolder();
				eventHolder.eventPicture = (ImageView) eventView
						.findViewById(R.id.event_picture);
				eventHolder.title = (TextView) eventView.findViewById(R.id.title);
				eventHolder.beginningDate = (TextView) eventView
						.findViewById(R.id.beginning_date);
				eventHolder.description = (TextView) eventView
						.findViewById(R.id.description);
				eventHolder.creationDate = (TextView) eventView
						.findViewById(R.id.timestamp);
				eventHolder.location = (TextView) eventView.findViewById(R.id.location);

			} else {
				eventHolder = (EventViewHolder)eventView.getTag();
			}
			setUpEventHeader(position, eventHolder);
			return eventView;
			
		case VIEW_HIT_ON:
			CommentViewHolder hitHolder;
			View hitView = convertView;
			if(hitView == null){
				hitView = mInflater.inflate(R.layout.comment_item, parent, false);
				hitHolder = new CommentViewHolder();
				hitHolder.name = (TextView) hitView.findViewById(R.id.name);
				hitHolder.timestamp = (TextView) hitView.findViewById(R.id.timestamp);
				hitHolder.statusMsg = (TextView) hitView.findViewById(R.id.text);
				hitHolder.profilePic = (ImageView) hitView.findViewById(R.id.profilePic);
			}else{
				hitHolder = (CommentViewHolder) hitView.getTag();
			}
			setUpHitOnRow(position, hitHolder);
			return hitView;
		}
		return null;
	}

	private void setUpHitOnRow(int position, CommentViewHolder holder) {
		HitOn commentItem;
		commentItem = (HitOn) getItem(position);

		holder.name.setText(commentItem.getAuthorName());
		holder.name.setOnClickListener(new IdParameterOnClickListener(commentItem
				.getSeducerId()) {
			@Override
			public void onClick(View v) {
				Intent showProfileIntent = new Intent(context,
						ProfileActivity.class);
				showProfileIntent.putExtra("userToRetrieveId", id);
				context.startActivity(showProfileIntent);
			}
		});
		// Converting timestamp into time ago format
		CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(commentItem
				.getTimestamp().getValue(), System.currentTimeMillis(),
				DateUtils.SECOND_IN_MILLIS);
		holder.timestamp.setText(timeAgo);

			holder.statusMsg.setText(commentItem.getContact());

			holder.profilePic.setOnClickListener(new IdParameterOnClickListener(
				commentItem.getSeducerId()) {
			@Override
			public void onClick(View v) {
				Intent showProfileIntent = new Intent(context,
						ProfileActivity.class);
				showProfileIntent.putExtra("userToRetrieveId", id);
				context.startActivity(showProfileIntent);
			}
		});
		// retrieve profile pic with asynctask
		new RetrieveCommentProfilePicTask(holder.profilePic,
				commentItem.getSeducerId()).execute();

	}

	private void setUpEventHeader(int position, EventViewHolder holder) {
		Initiative item;
		item = (Initiative) getItem(position);

		// Event image
		if(item.getHavePicture()){
			//asynctask to retrieve post image
			new AsyncTask<Object, Void, Boolean>() {
			    private EventViewHolder v;
			    private String s;

			    @Override
			    protected Boolean doInBackground(Object... params) {
			        v = (EventViewHolder)params[0];
			        Postimageendpoint.Builder imageBuilder = new Postimageendpoint.Builder(
							AndroidHttp.newCompatibleTransport(),
							new JacksonFactory(), null);

					imageBuilder = CloudEndpointUtils.updateBuilder(imageBuilder);

					Postimageendpoint imageEndpoint = imageBuilder
							.setApplicationName("polimisocial").build();

					try {
						s=imageEndpoint.getImageFromPostId((long)params[1]).execute().getImage();
					} catch (IOException e2) {
						System.out.println(e2.getMessage());
						return false;
					}
					return true;
			    }


			    @Override
			    protected void onPostExecute(Boolean result) {

			    	if(result){
			    		final byte[] byteArrayImage = Base64.decode(s,
								Base64.DEFAULT);
						v.eventPicture.setImageBitmap(BitmapFactory.decodeByteArray(
								byteArrayImage, 0, byteArrayImage.length));
						v.eventPicture.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								Intent showFullScreenPicIntent = new Intent(context,
										FullScreenPicActivity.class);
								showFullScreenPicIntent.putExtra("picInByte", byteArrayImage);
								context.startActivity(showFullScreenPicIntent);
							}
						});
			    	}
			    }
			}.execute(holder,item.getId());
		}else {
			holder.eventPicture.setImageResource(R.drawable.event_no_pic);
		}
		
		
		holder.title.setText(item.getTitle());

		holder.location.setText(item.getLocation());
		String dateTime = item.getBeginningDate().toString();
		String dateString = EventAdapter.composeDateString(
				dateTime.substring(0, 4), dateTime.substring(5, 7),
				dateTime.substring(8, 10));
		String time = dateTime.substring(11, Math.min(dateTime.length(), 16));

		holder.beginningDate.setText(dateString + " at " + time);

		CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(item
				.getTimestamp().getValue(), System.currentTimeMillis(),
				DateUtils.SECOND_IN_MILLIS);
		holder.creationDate.setText("created " + timeAgo);
		holder.description.setText(item.getText());
		//EventAdapter.makeTextViewResizable(holder.description, 3, "View More", true);

	}

	private void setUpSpottedHeader(int position, SpottedViewHolder holder) {
		PostSpotted item = (PostSpotted) getItem(position);

		holder.title.setText(item.getTitle());
		// Converting timestamp into time ago format
		CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(item
				.getTimestamp().getValue(), System.currentTimeMillis(),
				DateUtils.SECOND_IN_MILLIS);
		holder.timestamp.setText(timeAgo);
		// Check for empty status message
		if (!TextUtils.isEmpty(item.getText())) {
			holder.statusMsg.setText(item.getText());
			holder.statusMsg.setVisibility(View.VISIBLE);
		} else {
			// status is empty, remove from view
			holder.statusMsg.setVisibility(View.GONE);
		}
		switch (item.getPostCategory()) {
		case "Fun":
			holder.profilePic.setImageResource(R.drawable.owl_fun);
			break;
		case "Love":
			holder.profilePic.setImageResource(R.drawable.owl_love);
			break;
		case "Complaint":
			holder.profilePic.setImageResource(R.drawable.owl_complaint);
			break;
		case "Advice":
			holder.profilePic.setImageResource(R.drawable.owl_advice);
			break;
		default:
			holder.profilePic.setImageResource(R.drawable.owl_fun);

		}
		// Feed image
		if (item.getHavePicture()) {
			holder.postImage.setVisibility(View.VISIBLE);

			// asynctask to retrieve post image
			new AsyncTask<Object, Void, Boolean>() {
				private SpottedViewHolder v;
				private String s;

				@Override
				protected Boolean doInBackground(Object... params) {
					v = (SpottedViewHolder) params[0];
					Postimageendpoint.Builder imageBuilder = new Postimageendpoint.Builder(
							AndroidHttp.newCompatibleTransport(),
							new JacksonFactory(), null);

					imageBuilder = CloudEndpointUtils
							.updateBuilder(imageBuilder);

					Postimageendpoint imageEndpoint = imageBuilder
							.setApplicationName("polimisocial").build();

					try {
						s=imageEndpoint.getImageFromPostId((long) params[1])
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
						v.postImage.setImageBitmap(BitmapFactory
								.decodeByteArray(byteArrayImage, 0,
										byteArrayImage.length));
						v.postImage.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								Intent showFullScreenPicIntent = new Intent(
										context, FullScreenPicActivity.class);
								showFullScreenPicIntent.putExtra("picInByte",
										byteArrayImage);
								context.startActivity(showFullScreenPicIntent);
							}
						});
					}
				}
			}.execute(holder, item.getId());

			// case with no post picture
		} else {
			holder.postImage.setVisibility(View.GONE);
		}
	}

	private void setUpCommentRow(int position, CommentViewHolder holder) {
		Comment commentItem;
		commentItem = (Comment) getItem(position);

		holder.name.setText(commentItem.getAuthorName());
		holder.name.setOnClickListener(new IdParameterOnClickListener(
				commentItem.getAuthorId()) {
			@Override
			public void onClick(View v) {
				Intent showProfileIntent = new Intent(context,
						ProfileActivity.class);
				showProfileIntent.putExtra("userToRetrieveId", id);
				context.startActivity(showProfileIntent);
			}
		});
		// Converting timestamp into time ago format
		CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(commentItem
				.getCommentTimestamp().getValue(), System.currentTimeMillis(),
				DateUtils.SECOND_IN_MILLIS);
		holder.timestamp.setText(timeAgo);
		holder.statusMsg.setText(commentItem.getText());
		holder.profilePic.setOnClickListener(new IdParameterOnClickListener(
				commentItem.getAuthorId()) {
			@Override
			public void onClick(View v) {
				Intent showProfileIntent = new Intent(context,
						ProfileActivity.class);
				showProfileIntent.putExtra("userToRetrieveId", id);
				context.startActivity(showProfileIntent);
			}
		});
		// retrieve profile pic with asynctask
		new RetrieveCommentProfilePicTask(holder.profilePic,
				commentItem.getAuthorId()).execute();
	}

	private boolean eventPostNotification() {
		return notificationCategory.equals(NotificationCategory.EVENT
				.toString());
	}

	private boolean spottedPostNotification() {
		return notificationCategory.equals(NotificationCategory.SIMPLE_SPOTTED
				.toString())
				|| notificationCategory.equals(NotificationCategory.HIT_ON
						.toString());
	}

	private boolean notFromNotification() {
		return notificationCategory
				.equals(NotificationCategory.NOT_FROM_NOTIFICATION.toString());
	}

	public class RetrieveCommentProfilePicTask extends
			AsyncTask<Void, String, String> {
		private ImageView view;
		private long userId;

		public RetrieveCommentProfilePicTask(ImageView view, long userId) {
			this.view = view;
			this.userId = userId;
		}

		@Override
		protected String doInBackground(Void... voids) {
			// TODO
			// retrieve from server the picture of the user having id = userId
			// and pass it to a
			Poliuserendpoint.Builder builder = new Poliuserendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
					null);
			builder = CloudEndpointUtils.updateBuilder(builder);
			Poliuserendpoint endpoint = builder.setApplicationName(
					"polimisocial").build();

			// check if email is available
			ResponseObject pic = null;
			String byteArrayPic;
			try {
				pic = endpoint.getPictureUser(userId).execute();
			} catch (IOException e2) {
			}
			if (pic.getObject() != null) {
				byteArrayPic = (String) pic.getObject();
			} else {
				byteArrayPic = null;
			}

			return byteArrayPic;

		}

		@Override
		protected void onPostExecute(String pic) {
			if (pic != null && view != null) {
				byte[] byteArrayImage = Base64.decode(pic, Base64.DEFAULT);
				view.setImageBitmap(BitmapFactory.decodeByteArray(
						byteArrayImage, 0, byteArrayImage.length));
			} else {
				view.setImageResource(R.drawable.no_picture_pic);
			}
		}
	}

	static class EventViewHolder {
		public int type;

		TextView title;
		TextView location;
		ImageView eventPicture;
		TextView creationDate;
		TextView description;
		TextView beginningDate;
	}

	static class SpottedViewHolder {

		public int type;

		TextView title;
		TextView timestamp;
		TextView statusMsg;
		ImageView profilePic;
		ImageView postImage;
	}

	static class CommentViewHolder {
		public int type;
		TextView name;
		TextView timestamp;
		TextView statusMsg;
		ImageView profilePic;
	}

}
