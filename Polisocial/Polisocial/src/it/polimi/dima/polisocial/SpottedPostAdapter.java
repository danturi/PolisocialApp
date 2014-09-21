package it.polimi.dima.polisocial;

import it.polimi.dima.polisocial.customOnClickListeners.IdParameterOnClickListener;
import it.polimi.dima.polisocial.entity.postspottedendpoint.model.PostSpotted;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class SpottedPostAdapter extends ArrayAdapter<PostSpotted> {

	private final LayoutInflater mInflater;
	private Context context;

	public SpottedPostAdapter(Context context) {
		super(context, R.layout.spotted_post_item);
		this.context = context;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public void setData(List<PostSpotted> data) {
		clear();
		if (data != null) {
			for (PostSpotted appEntry : data) {
				add(appEntry);
			}
		}
	}

	/**
	 * Populate new items in the list.
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;

		if (convertView == null) {
			view = mInflater.inflate(R.layout.spotted_post_item, parent, false);
		} else {
			view = convertView;
		}

		PostSpotted item = getItem(position);
		TextView title = (TextView) view.findViewById(R.id.title);
		TextView timestamp = (TextView) view.findViewById(R.id.timestamp);
		TextView statusMsg = (TextView) view.findViewById(R.id.text);
		ImageView profilePic = (ImageView) view.findViewById(R.id.profilePic);
		ImageView postImage = (ImageView) view.findViewById(R.id.postImage);
		TextView numbOfComments = (TextView) view
				.findViewById(R.id.numb_of_comments);
		ImageButton hitOnButton = (ImageButton) view.findViewById(R.id.hitOnButton);

		numbOfComments.setOnClickListener(new IdParameterOnClickListener(item
				.getId()) {

			@Override
			public void onClick(View v) {
				Intent showRelativeCommentsIntent = new Intent(context,
						ShowRelatedCommentsActivity.class);
				showRelativeCommentsIntent.putExtra("postId", id);
				showRelativeCommentsIntent.putExtra("type",
						NotificationCategory.SIMPLE_SPOTTED.toString());
				showRelativeCommentsIntent.putExtra("notificationCategory",
						NotificationCategory.NOT_FROM_NOTIFICATION.toString());
				context.startActivity(showRelativeCommentsIntent);
			}
		});

		if(item.getPostCategory().equals("Love")){
			hitOnButton.setOnClickListener(new IdParameterOnClickListener(item
					.getId()) {

				@Override
				public void onClick(View v) {
					//TODO passare l'id utente dal listener soprastante e chiamare l'asynktask che inserisce l'hit on
				}
			});
		}
		
		
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

		numbOfComments.setText(item.getNumOfComments() + " comments");

		return view;
	}
}
