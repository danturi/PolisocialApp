package it.polimi.dima.polisocial.adapter;

import it.polimi.dima.polisocial.CloudEndpointUtils;
import it.polimi.dima.polisocial.FullScreenPicActivity;
import it.polimi.dima.polisocial.HitOnDialogFragment;
import it.polimi.dima.polisocial.R;
import it.polimi.dima.polisocial.ShowRelatedCommentsActivity;
import it.polimi.dima.polisocial.TabActivity;
import it.polimi.dima.polisocial.customListeners.IdParameterOnClickListener;

import it.polimi.dima.polisocial.entity.postimageendpoint.Postimageendpoint;
import it.polimi.dima.polisocial.entity.postspottedendpoint.model.PostSpotted;
import it.polimi.dima.polisocial.utilClasses.NotificationCategory;

import java.io.IOException;
import java.util.List;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
		View view = convertView;
		SpottedViewHolder holder;
		int type = getItemViewType(position);

		if (view == null) {
			holder = new SpottedViewHolder();
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
				holder.postImage = (ImageView) view
						.findViewById(R.id.postImage);
				holder.numbOfComments = (TextView) view
						.findViewById(R.id.numb_of_comments);

			}
			holder.type = type;
			view.setTag(holder);
		} else {
			holder = (SpottedViewHolder) view.getTag();
		}

		if (getItemViewType(position) != VIEW_LOADING) {
			PostSpotted item = getItem(position);

			holder.numbOfComments
					.setOnClickListener(new IdParameterOnClickListener(item
							.getId()) {

						@Override
						public void onClick(View v) {
							Intent showRelativeCommentsIntent = new Intent(
									context, ShowRelatedCommentsActivity.class);
							showRelativeCommentsIntent.putExtra("postId", id);
							showRelativeCommentsIntent.putExtra("type",
									NotificationCategory.SIMPLE_SPOTTED
											.toString());
							showRelativeCommentsIntent.putExtra(
									"notificationCategory",
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
			if (item.getHavePicture()) {
				holder.postImage.setVisibility(View.VISIBLE);

				if (item.getBitmap() == null) {
					// asynctask to retrieve post image
					new AsyncTask<Object, Void, Boolean>() {
						private SpottedViewHolder v;
						private String s;
						private PostSpotted ps;

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
								ps = (PostSpotted) params[1];
								s = imageEndpoint
										.getImageFromPostId(ps.getId())
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
								Bitmap bitmap = BitmapFactory.decodeByteArray(
										byteArrayImage, 0,
										byteArrayImage.length);
								v.postImage.setImageBitmap(bitmap);
								ps.setBitmap(bitmap);
								v.postImage
										.setOnClickListener(new OnClickListener() {

											@Override
											public void onClick(View v) {
												Intent showFullScreenPicIntent = new Intent(
														context,
														FullScreenPicActivity.class);
												showFullScreenPicIntent
														.putExtra("picInByte",
																byteArrayImage);
												context.startActivity(showFullScreenPicIntent);
											}
										});
							}
						}
					}.execute(holder, item);
				} else {
					holder.postImage.setImageBitmap(item.getBitmap());
				}

				// case with no post picture
			} else {
				holder.postImage.setVisibility(View.GONE);
			}

			holder.numbOfComments
					.setText(item.getNumOfComments() + " comments");
			if (getItemViewType(position) == VIEW_CUPIDO) {

				holder.hitOnButton
						.setOnClickListener(new IdParameterOnClickListener(item
								.getId()) {

							@Override
							public void onClick(View v) {
								showNoticeDialog(id);

							}

						});
			}

		}
		return view;
	}

	public void showNoticeDialog(long postId) {
		// Create an instance of the dialog fragment and show it
		FragmentManager fm = ((TabActivity) context).getFragmentManager();
		DialogFragment dialog = HitOnDialogFragment.newInstance(name, userId,
				postId);
		dialog.show(fm, "HitOnDialogFragm");
	}

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

	static class SpottedViewHolder {

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
