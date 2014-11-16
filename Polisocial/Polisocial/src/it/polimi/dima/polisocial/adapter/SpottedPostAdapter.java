package it.polimi.dima.polisocial.adapter;

import it.polimi.dima.polisocial.CloudEndpointUtils;
import it.polimi.dima.polisocial.FullScreenPicActivity;
import it.polimi.dima.polisocial.HitOnDialogFragment;
import it.polimi.dima.polisocial.R;
import it.polimi.dima.polisocial.ShowRelatedCommentsActivity;
import it.polimi.dima.polisocial.customListeners.BitmapParameterOnClickListener;
import it.polimi.dima.polisocial.customListeners.IdParameterOnClickListener;
import it.polimi.dima.polisocial.entity.dislikeendpoint.Dislikeendpoint;
import it.polimi.dima.polisocial.entity.dislikeendpoint.model.DisLike;
import it.polimi.dima.polisocial.entity.likeendpoint.Likeendpoint;
import it.polimi.dima.polisocial.entity.likeendpoint.model.Like;
import it.polimi.dima.polisocial.entity.postimageendpoint.Postimageendpoint;
import it.polimi.dima.polisocial.entity.postspottedendpoint.model.PostSpotted;
import it.polimi.dima.polisocial.tabactivityAndFragments.TabActivity;
import it.polimi.dima.polisocial.utilClasses.PostType;
import it.polimi.dima.polisocial.utilClasses.SessionManager;
import it.polimi.dima.polisocial.utilClasses.WhatToShow;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;

public class SpottedPostAdapter extends EndlessListAdapter<PostSpotted> {

	private final int VIEW_CUPIDO = 1;
	private final int VIEW_NO_CUPIDO = 0;
	private final int VIEW_LOADING = 2;

	private Long userId;
	private String name;
	private Context cont;

	public SpottedPostAdapter(Context context, Long userId, String name) {
		super(context, R.layout.spotted_post_item);
		this.userId = userId;
		this.name = name;
		this.cont = context;
	}

	@Override
	public int getItemViewType(int position) {
		PostSpotted item = getItem(position);
		// Define a way to determine which layout to use.
		if (position >= (getCount() - loading_row)) {
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
		final SpottedViewHolder holder;
		int type = getItemViewType(position);
		final SessionManager session = new SessionManager(cont);

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
				holder.numbOfLikes = (TextView) view
						.findViewById(R.id.numb_of_likes);
				holder.numbOfDisLikes = (TextView) view
						.findViewById(R.id.numb_of_dislikes);
				holder.likeButton = (Button) view
						.findViewById(R.id.imageLikeButton);
				holder.disLikeButton = (Button) view
						.findViewById(R.id.imageDisLikeButton);

			}
			view.setTag(holder);
		} else {
			holder = (SpottedViewHolder) view.getTag();
		}

		if (getItemViewType(position) != VIEW_LOADING) {
			final PostSpotted item = getItem(position);

			holder.numbOfComments
					.setOnClickListener(new IdParameterOnClickListener(item
							.getId()) {

						@Override
						public void onClick(View v) {
							Intent showRelativeCommentsIntent = new Intent(
									context, ShowRelatedCommentsActivity.class);
							showRelativeCommentsIntent.putExtra("postId", id);
							showRelativeCommentsIntent.putExtra("type",
									PostType.SPOTTED.toString());
							showRelativeCommentsIntent.putExtra(
									"notificationCategory",
									WhatToShow.ONLY_COMMENTS.toString());
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
					holder.postImage
							.setImageResource(R.drawable.loading_animation);
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
					notifyDataSetChanged();

					holder.postImage
							.setOnClickListener(new BitmapParameterOnClickListener(
									item.getBitmap()) {

								@Override
								public void onClick(View v) {
									Intent showFullScreenPicIntent = new Intent(
											context,
											FullScreenPicActivity.class);
									ByteArrayOutputStream stream = new ByteArrayOutputStream();
									bitmap.compress(Bitmap.CompressFormat.PNG,
											100, stream);
									final byte[] byteArray = stream
											.toByteArray();
									showFullScreenPicIntent.putExtra(
											"picInByte", byteArray);
									context.startActivity(showFullScreenPicIntent);
								}
							});
				}

				// case with no post picture
			} else {
				holder.postImage.setVisibility(View.GONE);
			}

			holder.numbOfComments.setText(item.getNumOfComments() + " "
					+ getContext().getResources().getString(R.string.comments));
			holder.numbOfLikes.setText(item.getNumberLike() + " "
					+ getContext().getResources().getString(R.string.likes));
			holder.numbOfDisLikes.setText(item.getNumberDislike() + " "
					+ getContext().getResources().getString(R.string.dislikes));

			ArrayList<Long> postsLike = session.loadArrayLikeSpotted();

			if (postsLike != null && !postsLike.isEmpty()) {
				Iterator<Long> iter = postsLike.iterator();
				while (iter.hasNext()) {
					if (iter.next().compareTo(item.getId()) == 0) {
						holder.likeButton.setEnabled(false);
						holder.likeButton.setBackgroundColor(cont
								.getResources().getColor(
										R.color.post_button_pressed));
						break;
					}
				}
			}
			holder.likeButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					new AddLikeOrDisLikeTask(item.getId(), holder, v)
							.execute("like");

				}
			});

			ArrayList<Long> postsDisLike = session.loadArrayDisLike();
			if (postsDisLike != null && !postsDisLike.isEmpty()) {
				Iterator<Long> iter = postsDisLike.iterator();
				while (iter.hasNext()) {
					if (iter.next().compareTo(item.getId()) == 0) {
						holder.disLikeButton.setEnabled(false);
						holder.disLikeButton.setBackgroundColor(cont
								.getResources().getColor(
										R.color.post_button_pressed));
						break;
					}
				}
			}
			holder.disLikeButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					new AddLikeOrDisLikeTask(item.getId(), holder, v)
							.execute("dislike");
				}
			});

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

	static class SpottedViewHolder {
		TextView title;
		TextView timestamp;
		TextView statusMsg;
		ImageView profilePic;
		ImageView postImage;
		TextView numbOfComments;
		TextView numbOfLikes;
		TextView numbOfDisLikes;
		ImageButton hitOnButton;
		Button likeButton;
		Button disLikeButton;
		Button contactButton;
	}

	public class AddLikeOrDisLikeTask extends AsyncTask<String, Void, Boolean> {

		private Long post;
		private String type;
		private SpottedViewHolder holder;
		private View v;

		public AddLikeOrDisLikeTask(Long postId, SpottedViewHolder holder,
				View v) {
			this.post = postId;
			this.holder = holder;
			this.v = v;
		}

		@Override
		protected Boolean doInBackground(String... params) {

			if (params == null)
				return false;

			type = params[0];

			if (type.equals("like")) {

				Likeendpoint.Builder build1 = new Likeendpoint.Builder(
						AndroidHttp.newCompatibleTransport(),
						new JacksonFactory(), null);

				build1 = CloudEndpointUtils.updateBuilder(build1);
				Likeendpoint likeEndpoint = build1.setApplicationName(
						"polimisocial").build();

				Like like = new Like();
				like.setUserId(userId);
				like.setPostId(post);
				try {
					likeEndpoint.insertLike("spotted", like).execute();
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
				return true;
			}

			if (type.equals("dislike")) {

				Dislikeendpoint.Builder build2 = new Dislikeendpoint.Builder(
						AndroidHttp.newCompatibleTransport(),
						new JacksonFactory(), null);

				build2 = CloudEndpointUtils.updateBuilder(build2);
				Dislikeendpoint disLikeEndpoint = build2.setApplicationName(
						"polimisocial").build();

				DisLike disLike = new DisLike();
				disLike.setUserId(userId);
				disLike.setPostId(post);
				try {
					disLikeEndpoint.insertDisLike("spotted", disLike).execute();
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
				return true;
			}

			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if (result) {
				SessionManager session = new SessionManager(cont);
				if (type.equals("like")) {
					Toast.makeText(context, "You liked this post!",
							Toast.LENGTH_SHORT).show();
					holder.likeButton.setEnabled(false);
					holder.likeButton.setBackgroundColor(v.getResources()
							.getColor(R.color.post_button_pressed));
					session.setLikeSpotted(post);
				} else {
					Toast.makeText(context, "You disliked this post!",
							Toast.LENGTH_SHORT).show();

					holder.disLikeButton.setEnabled(false);
					holder.disLikeButton.setBackgroundColor(v.getResources()
							.getColor(R.color.post_button_pressed));
					session.setDisLike(post);
				}

			} else {
				Toast.makeText(context,
						"Can't perform this operation.Retry later",
						Toast.LENGTH_LONG).show();
			}
		}

	}

}
