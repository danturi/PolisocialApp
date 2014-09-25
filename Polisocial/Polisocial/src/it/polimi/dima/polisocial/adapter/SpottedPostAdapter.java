package it.polimi.dima.polisocial.adapter;

import it.polimi.dima.polisocial.FullScreenPicActivity;
import it.polimi.dima.polisocial.HitOnDialogFragment;
import it.polimi.dima.polisocial.R;
import it.polimi.dima.polisocial.ShowRelatedCommentsActivity;
import it.polimi.dima.polisocial.TabActivity;
import it.polimi.dima.polisocial.HitOnDialogFragment.HitOnDialogListener;
import it.polimi.dima.polisocial.R.drawable;
import it.polimi.dima.polisocial.R.id;
import it.polimi.dima.polisocial.R.layout;
import it.polimi.dima.polisocial.customListeners.IdParameterOnClickListener;
import it.polimi.dima.polisocial.entity.hitonendpoint.Hitonendpoint;
import it.polimi.dima.polisocial.entity.hitonendpoint.model.HitOn;
import it.polimi.dima.polisocial.entity.initiativeendpoint.Initiativeendpoint;
import it.polimi.dima.polisocial.entity.postspottedendpoint.model.PostSpotted;
import it.polimi.dima.polisocial.utilClasses.NotificationCategory;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class SpottedPostAdapter extends ArrayAdapter<PostSpotted> {

	private final int VIEW_CUPIDO = 1;
	private final int VIEW_NO_CUPIDO = 0;
	private final int VIEW_LOADING = 2;

	private final LayoutInflater mInflater;
	private Context context;
	private Long userId;
	private String name;
	private Long postId;

	public SpottedPostAdapter(Context context, Long userId, String name) {
		super(context, R.layout.spotted_post_item);
		this.context = context;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.userId = userId;
		this.name = name;
	}

	public void setData(List<PostSpotted> data) {
		clear();
		if (data != null) {
			for (PostSpotted appEntry : data) {
				add(appEntry);
			}
		}
	}

	@Override
	public int getItemViewType(int position) {
		PostSpotted item = getItem(position);
		// Define a way to determine which layout to use.
		if (position >= (getCount() - 1)) {
			return VIEW_LOADING;
		}
		if (item.getPostCategory().equals("Love")) {
			if (userId.compareTo(item.getUserId()) != 0) {
				return VIEW_CUPIDO;
			}
		}
		return VIEW_NO_CUPIDO;

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
		// View view;
		View view = convertView;
		ViewHolder holder;
		int type = getItemViewType(position);

		if (view == null) {
			holder = new ViewHolder();
			if (type == VIEW_LOADING) {
				view = mInflater.inflate(R.layout.progress, parent, false);
			} else {
				if (type == VIEW_NO_CUPIDO) {
					view = mInflater.inflate(R.layout.spotted_post_item,
							parent, false);
					
				} else {
					view = mInflater.inflate(R.layout.spotted_post_item_cupido,
							parent, false);
					holder.hitOnButton = (ImageButton) view
							.findViewById(R.id.hitOnButton);
				}
				
				holder.title = (TextView) view.findViewById(R.id.title);
				holder.timestamp = (TextView) view.findViewById(R.id.timestamp);
				holder.statusMsg = (TextView) view.findViewById(R.id.text);
				holder.profilePic = (ImageView) view
						.findViewById(R.id.profilePic);
				holder.postImage = (ImageView) view.findViewById(R.id.postImage);
				holder.numbOfComments = (TextView) view
						.findViewById(R.id.numb_of_comments);
				
			}
			holder.type = type;
			view.setTag(holder);
		} else {
				holder = (ViewHolder) view.getTag();

		}

		if (getItemViewType(position) != VIEW_LOADING) {
			PostSpotted item = getItem(position);
			
			holder.numbOfComments.setOnClickListener(new IdParameterOnClickListener(
					item.getId()) {

				@Override
				public void onClick(View v) {
					Intent showRelativeCommentsIntent = new Intent(context,
							ShowRelatedCommentsActivity.class);
					showRelativeCommentsIntent.putExtra("postId", id);
					showRelativeCommentsIntent.putExtra("type",
							NotificationCategory.SIMPLE_SPOTTED.toString());
					showRelativeCommentsIntent.putExtra("notificationCategory",
							NotificationCategory.NOT_FROM_NOTIFICATION
									.toString());
					context.startActivity(showRelativeCommentsIntent);
				}
			});
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
			if (item.getPicture() != null) {
				final byte[] byteArrayImage = Base64.decode(item.getPicture(),
						Base64.DEFAULT);
				holder.postImage.setImageBitmap(BitmapFactory.decodeByteArray(
						byteArrayImage, 0, byteArrayImage.length));
				holder.postImage.setVisibility(View.VISIBLE);
				holder.postImage.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent showFullScreenPicIntent = new Intent(context,
								FullScreenPicActivity.class);
						showFullScreenPicIntent.putExtra("picInByte", byteArrayImage);
						context.startActivity(showFullScreenPicIntent);
					}
				});
			} else {
				holder.postImage.setVisibility(View.GONE);
			}
			holder.numbOfComments.setText(item.getNumOfComments() + " comments");
			if (getItemViewType(position) == VIEW_CUPIDO) {
				
				holder.hitOnButton.setOnClickListener(new IdParameterOnClickListener(
						item.getId()) {

					@Override
					public void onClick(View v) {
						postId = id;
						showNoticeDialog();

					}

				});
			}

		}
		return view;
	}

	public void showNoticeDialog() {
		// Create an instance of the dialog fragment and show it
		FragmentManager fm = ((TabActivity) context).getFragmentManager();
		DialogFragment dialog = HitOnDialogFragment.newInstance(name, userId,
				postId);
		dialog.show(fm, "HitOnDialogFragm");
	}

	// CAMBIAMENTI

	@Override
	public int getCount() {
		return super.getCount() + 1;
	}

	@Override
	public PostSpotted getItem(int position) {
		if (position < (getCount() - 1))
			return super.getItem(position);
		else
			return null;
	}

	@Override
	public long getItemId(int position) {
		if (position < (getCount() - 1))
			return super.getItemId(position);
		else
			return -1;
	}

	static class ViewHolder {

		public int type;

		TextView title;
		TextView timestamp;
		TextView statusMsg;
		ImageView profilePic;
		ImageView postImage;
		TextView numbOfComments;
		ImageButton hitOnButton;
	}

}
