package it.polimi.dima.polisocial.adapter;

import it.polimi.dima.polisocial.ProfileActivity;
import it.polimi.dima.polisocial.R;
import it.polimi.dima.polisocial.R.drawable;
import it.polimi.dima.polisocial.R.id;
import it.polimi.dima.polisocial.R.layout;
import it.polimi.dima.polisocial.customListeners.IdParameterOnClickListener;
import it.polimi.dima.polisocial.entity.commentendpoint.model.Comment;
import it.polimi.dima.polisocial.entity.initiativeendpoint.model.Initiative;
import it.polimi.dima.polisocial.entity.postspottedendpoint.model.PostSpotted;
import it.polimi.dima.polisocial.utilClasses.NotificationCategory;

import java.util.List;

import android.content.ClipData.Item;
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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CommentAdapter extends ArrayAdapter<Object> {

	private final int VIEW_ONLY_COMMENTS = 0;
	private final int VIEW_SPOTTED = 1;
	private final int VIEW_EVENT = 2;

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
				return VIEW_ONLY_COMMENTS;
			}
		}
		return VIEW_ONLY_COMMENTS;
	}

	@Override
	public int getViewTypeCount() {
		return 3;
	}

	/**
	 * Populate new items in the list.
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		if (convertView == null) {
			if (getItemViewType(position) == VIEW_ONLY_COMMENTS) {
				view = mInflater.inflate(R.layout.comment_item, parent, false);
				setUpCommentRow(position, view);
			} else if (getItemViewType(position) == VIEW_SPOTTED) {
				view = mInflater.inflate(R.layout.spotted_notification_header,
						parent, false);
				setUpSpottedHeader(position, view);
			} else if (getItemViewType(position) == VIEW_EVENT) {
				view = mInflater.inflate(R.layout.event_notification_header,
						parent, false);
				setUpEventHeader(position, view);
			}
			// TODO add announcement case
		} else {
			view = convertView;
		}
		return view;

	}

	private void setUpEventHeader(int position, View view) {
		Initiative item;
		item = (Initiative) getItem(position);
		ImageView eventPicture = (ImageView) view
				.findViewById(R.id.event_picture);
		TextView title = (TextView) view.findViewById(R.id.title);
		TextView beginningDate = (TextView) view
				.findViewById(R.id.beginning_date);
		TextView description = (TextView) view.findViewById(R.id.description);
		TextView creationDate = (TextView) view.findViewById(R.id.timestamp);
		TextView location = (TextView) view.findViewById(R.id.location);

		// Event image
		if (item.getPicture() != null) {
			byte[] byteArrayImage = Base64.decode(item.getPicture(),
					Base64.DEFAULT);
			eventPicture.setImageBitmap(BitmapFactory.decodeByteArray(
					byteArrayImage, 0, byteArrayImage.length));
		} else {
			eventPicture.setImageResource(R.drawable.event_no_pic);
		}
		title.setText(item.getTitle());

		location.setText(item.getLocation());
		String dateTime = item.getBeginningDate().toString();
        String dateString = EventAdapter.composeDateString(dateTime.substring(0,4), dateTime.substring(5,7), dateTime.substring(8,10) );
        String time = dateTime.substring(11, Math.min(dateTime.length(),16));
		
		beginningDate.setText(dateString+" at "+ time);

		CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(item
				.getTimestamp().getValue(), System.currentTimeMillis(),
				DateUtils.SECOND_IN_MILLIS);
		creationDate.setText("created " + timeAgo);
		description.setText(item.getText());
		EventAdapter.makeTextViewResizable(description, 3, "View More", true);

	}

	private void setUpSpottedHeader(int position, View view) {
		PostSpotted item = (PostSpotted) getItem(position);
		TextView title = (TextView) view.findViewById(R.id.title);
		TextView timestamp = (TextView) view.findViewById(R.id.timestamp);
		TextView statusMsg = (TextView) view.findViewById(R.id.text);
		ImageView profilePic = (ImageView) view.findViewById(R.id.profilePic);
		ImageView postImage = (ImageView) view.findViewById(R.id.postImage);

		title.setText(item.getTitle());
		// Converting timestamp into time ago format
		CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(item
				.getTimestamp().getValue(), System.currentTimeMillis(),
				DateUtils.SECOND_IN_MILLIS);
		timestamp.setText(timeAgo);
		// Check for empty status message
		if (!TextUtils.isEmpty(item.getText())) {
			statusMsg.setText(item.getText());
			statusMsg.setVisibility(View.VISIBLE);
		} else {
			// status is empty, remove from view
			statusMsg.setVisibility(View.GONE);
		}
		switch (item.getPostCategory()) {
		case "Fun":
			profilePic.setImageResource(R.drawable.owl_fun);
			break;
		case "Love":
			profilePic.setImageResource(R.drawable.owl_love);
			break;
		case "Complaint":
			profilePic.setImageResource(R.drawable.owl_complaint);
			break;
		case "Advice":
			profilePic.setImageResource(R.drawable.owl_advice);
			break;
		default:
			profilePic.setImageResource(R.drawable.owl_fun);

		}
		// Feed image
		if (item.getPicture() != null) {
			byte[] byteArrayImage = Base64.decode(item.getPicture(),
					Base64.DEFAULT);
			postImage.setImageBitmap(BitmapFactory.decodeByteArray(
					byteArrayImage, 0, byteArrayImage.length));
			postImage.setVisibility(View.VISIBLE);
		} else {
			postImage.setVisibility(View.GONE);
		}
	}

	private void setUpCommentRow(int position, View view) {
		Comment commentItem;
		commentItem = (Comment) getItem(position);

		TextView name = (TextView) view.findViewById(R.id.name);
		TextView timestamp = (TextView) view.findViewById(R.id.timestamp);
		TextView statusMsg = (TextView) view.findViewById(R.id.text);
		ImageView profilePic = (ImageView) view.findViewById(R.id.profilePic);

		name.setText(commentItem.getAuthorName());
		name.setOnClickListener(new IdParameterOnClickListener(commentItem.getAuthorId()){
			 @Override
	            public void onClick(View v) {
				 Intent showProfileIntent = new Intent(context,
							ProfileActivity.class);
					showProfileIntent.putExtra("userToRetrieveId", id);
					context.startActivity(showProfileIntent);
	            }
		} );
		// Converting timestamp into time ago format
		CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(commentItem
				.getCommentTimestamp().getValue(), System.currentTimeMillis(),
				DateUtils.SECOND_IN_MILLIS);
		timestamp.setText(timeAgo);

		// Check for empty status message
		if (!TextUtils.isEmpty(commentItem.getText())) {
			statusMsg.setText(commentItem.getText());
			statusMsg.setVisibility(View.VISIBLE);
		} else {
			// status is empty, remove from view
			statusMsg.setVisibility(View.GONE);
		}
		profilePic.setOnClickListener(new IdParameterOnClickListener(commentItem.getAuthorId()){
			 @Override
	            public void onClick(View v) {
				 Intent showProfileIntent = new Intent(context,
							ProfileActivity.class);
					showProfileIntent.putExtra("userToRetrieveId", id);
					context.startActivity(showProfileIntent);
	            }
		} );
		// retrieve profile pic with asynctask
		new RetrieveCommentProfilePicTask(profilePic, commentItem.getAuthorId()).execute();
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
		protected String doInBackground(Void...voids) {
			//TODO
			// retrieve from server the picture of the user having id = userId
			// and pass it to a
			return null;
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
}
