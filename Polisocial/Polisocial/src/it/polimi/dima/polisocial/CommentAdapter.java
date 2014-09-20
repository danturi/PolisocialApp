package it.polimi.dima.polisocial;

import it.polimi.dima.polisocial.entity.commentendpoint.model.Comment;
import it.polimi.dima.polisocial.entity.initiativeendpoint.model.Initiative;
import it.polimi.dima.polisocial.entity.postspottedendpoint.model.PostSpotted;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.text.format.DateUtils;
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
		
		//here we handle the case in which the user has clicked show comments in a section
		//and we have to show only a list of comments
		if (notFromNotification()) {
			return VIEW_ONLY_COMMENTS;
		//here we handle the case in which the user come from notification section
		} else {
			//if this is the first element of the list, we have to set an appropriate layout,
			//depending on the type of notification
			switch (position) {
			case 0:
				//if it is a notification about a spotted post...
				if (spottedPostNotification()) {
					return VIEW_SPOTTED;
				//if it is a notification about an event post...
				} else if (eventPostNotification()) {
					return VIEW_EVENT;
				}
				// TODO add announcement case
				break;
			//if it is not the first element of the list, then inflate standard comment layout
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
			if(getItemViewType(position)==VIEW_ONLY_COMMENTS){
				view = mInflater.inflate(R.layout.comment_item, parent, false);
				setUpCommentRow(position, view);
			}else if(getItemViewType(position)==VIEW_SPOTTED){
				view = mInflater.inflate(
						R.layout.spotted_notification_header, parent,
						false);
				setUpSpottedHeader(position, view);
			}else if(getItemViewType(position)==VIEW_EVENT){
				view = mInflater.inflate(R.layout.event_notification_header, parent,
						false);
				setUpEventHeader(position, view);
			}
					//TODO add announcement case
		}else{
			view=convertView;
		}
		return view;
	
	}

	private void setUpEventHeader(int position, View view) {
		Initiative eventItem;
		eventItem = (Initiative) getItem(position);
	       ImageView eventPicture=(ImageView) view
					.findViewById(R.id.event_picture);
	        TextView title = (TextView) view.findViewById(R.id.title);
			TextView beginningDate = (TextView) view
					.findViewById(R.id.beginning_date);
			TextView description = (TextView) view
					.findViewById(R.id.description);

			TextView creationDate = (TextView) view.findViewById(R.id.timestamp);
			eventPicture.setImageResource(R.drawable.imageprova);
			title.setText(eventItem.getTitle());
			// Converting timestamp into time ago format
			/*CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
					eventItem.getTimestamp().getValue(),
					System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
			
			timestamp.setText(timeAgo);*/
	
			beginningDate.setText("BEGINS: "+ eventItem.getBeginningDate().toString());
			creationDate.setText("created" + eventItem.getTimestamp().toString());
			description.setText(eventItem.getText());
			EventAdapter.makeTextViewResizable(description, 3, "View More", true);
		
	}

	private void setUpSpottedHeader(int position, View view) {
		PostSpotted spottedItem;
		spottedItem = (PostSpotted) getItem(position);
		TextView title = (TextView) view.findViewById(R.id.title);
		TextView timestamp = (TextView) view.findViewById(R.id.timestamp);
		TextView statusMsg = (TextView) view.findViewById(R.id.text);
		ImageView profilePic = (ImageView) view.findViewById(R.id.profilePic);
		ImageView postImage = (ImageView) view.findViewById(R.id.postImage);

		title.setText(spottedItem.getTitle());
		CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
				spottedItem.getTimestamp().getValue(),
				System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
		
		timestamp.setText(timeAgo);
		statusMsg.setText(spottedItem.getText());
		profilePic.setImageResource(R.drawable.profilepicprova);
		postImage.setVisibility(View.GONE);
	}

	private void setUpCommentRow(int position, View view) {
		Comment commentItem;
		commentItem = (Comment) getItem(position);

		TextView name = (TextView) view.findViewById(R.id.name);
		TextView timestamp = (TextView) view.findViewById(R.id.timestamp);
		TextView statusMsg = (TextView) view.findViewById(R.id.text);
		ImageView profilePic = (ImageView) view.findViewById(R.id.profilePic);

		name.setText(commentItem.getAuthorName());

		// Converting timestamp into time ago format
		
		 CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
		 commentItem.getCommentTimestamp().getValue(),
		 System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
		 timestamp.setText(timeAgo);   
		

		// Check for empty status message
		if (!TextUtils.isEmpty(commentItem.getText())) {
			statusMsg.setText(commentItem.getText());
			statusMsg.setVisibility(View.VISIBLE);
		} else {
			// status is empty, remove from view
			statusMsg.setVisibility(View.GONE);
		}

		// user profile pic
		// TODO: 
		/*
		int blobProfilePicLength = 0;
		byte[] byteArrayProfilePic = null;
		if (commentItem.getProfilePic() != null) {
			try {
				blobProfilePicLength = (int) commentItem.getProfilePic()
						.length();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				byteArrayProfilePic = commentItem.getProfilePic().getBytes(1,
						blobProfilePicLength);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// profilePic.setImageBitmap(BitmapFactory.decodeByteArray(byteArrayProfilePic,
		// 0 ,byteArrayProfilePic.length));*/
		profilePic.setImageResource(R.drawable.profilepicprova);
	}

	private boolean eventPostNotification() {
		return notificationCategory.equals(NotificationCategory.EVENT
				.toString());
	}

	private boolean spottedPostNotification() {
		return notificationCategory.equals(NotificationCategory.SIMPLE_SPOTTED
				.toString()) || notificationCategory.equals(NotificationCategory.HIT_ON
						.toString());
	}

	private boolean notFromNotification() {
		return notificationCategory
				.equals(NotificationCategory.NOT_FROM_NOTIFICATION.toString());
	}
}
