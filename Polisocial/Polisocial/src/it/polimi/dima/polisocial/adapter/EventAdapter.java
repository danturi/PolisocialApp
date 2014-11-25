package it.polimi.dima.polisocial.adapter;

import it.polimi.dima.polisocial.CloudEndpointUtils;
import it.polimi.dima.polisocial.FullScreenPicActivity;
import it.polimi.dima.polisocial.R;
import it.polimi.dima.polisocial.ShowRelatedCommentsActivity;
import it.polimi.dima.polisocial.customListeners.BitmapParameterOnClickListener;
import it.polimi.dima.polisocial.customListeners.IdButtonParameterOnClickListener;
import it.polimi.dima.polisocial.customListeners.IdParameterOnClickListener;
import it.polimi.dima.polisocial.entity.dislikeendpoint.Dislikeendpoint;
import it.polimi.dima.polisocial.entity.dislikeendpoint.model.DisLike;
import it.polimi.dima.polisocial.entity.initiativeendpoint.model.Initiative;
import it.polimi.dima.polisocial.entity.likeendpoint.Likeendpoint;
import it.polimi.dima.polisocial.entity.likeendpoint.model.Like;
import it.polimi.dima.polisocial.entity.postimageendpoint.Postimageendpoint;
import it.polimi.dima.polisocial.utilClasses.PostType;
import it.polimi.dima.polisocial.utilClasses.SessionManager;
import it.polimi.dima.polisocial.utilClasses.WhatToShow;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;

public class EventAdapter extends EndlessListAdapter<Initiative> {

	private final int VIEW_STANDARD = 0;
	private final int VIEW_LOADING = 1;
	
	final Animation animScale = AnimationUtils.loadAnimation(getContext(), R.anim.scale_anim);
	
	private Long userId;
	
	ArrayList<Long> postsLike = new ArrayList<>();
	ArrayList<Long> postsDisLike = new ArrayList<>();
	final SessionManager session;
	

	public EventAdapter(Context context, Long userId) {
		super(context, R.layout.event_item);
		session = new SessionManager(context);
		postsLike = session.loadArrayLikeSpotted();
		postsDisLike = session.loadArrayDisLike();
	}

	@Override
	public int getItemViewType(int position) {
		// Define a way to determine which layout to use.
		if (position >= (getCount() - loading_row)) {
			return VIEW_LOADING;
		}
		return VIEW_STANDARD;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	/**
	 * Populate new items in the list.
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		final EventViewHolder holder;
		int type = getItemViewType(position);

		if (view == null) {
			holder = new EventViewHolder();
			if (type == VIEW_STANDARD) {
				view = mInflater.inflate(R.layout.event_item, parent, false);

				holder.title = (TextView) view.findViewById(R.id.title);
				holder.beginningDate = (TextView) view
						.findViewById(R.id.beginning_date);
				holder.location = (TextView) view.findViewById(R.id.location);
				holder.eventPicture = (ImageView) view
						.findViewById(R.id.event_picture);
				holder.numbOfLikes = (TextView) view
						.findViewById(R.id.numb_of_likes);
				holder.numbOfGoing = (TextView) view
						.findViewById(R.id.numb_of_participants);
				holder.month = (TextView) view.findViewById(R.id.month);
				holder.day = (TextView) view.findViewById(R.id.day);
				holder.likeButton = (Button) view
						.findViewById(R.id.imageButtonEventLike);
				holder.goingButton = (Button) view
						.findViewById(R.id.imageButtonEventGoing);

			} else {
				view = mInflater
						.inflate(R.layout.progress_white, parent, false);
			}
			view.setTag(holder);
		} else {
			holder = (EventViewHolder) view.getTag();
		}

		if (type == VIEW_STANDARD) {

			final Initiative item = getItem(position);
			/**holder.numbOfLikes
					.setOnClickListener(new IdParameterOnClickListener(item
							.getId()) {
						@Override
						public void onClick(View v) {
							Intent showRelativeCommentsIntent = new Intent(
									context, ShowRelatedCommentsActivity.class);
							showRelativeCommentsIntent.putExtra("postId", id);
							showRelativeCommentsIntent.putExtra(
									"notificationCategory",
									WhatToShow.ONLY_COMMENTS.toString());
							showRelativeCommentsIntent.putExtra("type",
									PostType.EVENT.toString());
							context.startActivity(showRelativeCommentsIntent);
						}
					});
**/
			// Event image
			if (item.getHavePicture()) {
				// asynctask to retrieve post image

				if (item.getBitmap() == null) {
					new AsyncTask<Object, Void, Boolean>() {
						private EventViewHolder v;
						private String s;
						private Initiative ip;

						@Override
						protected Boolean doInBackground(Object... params) {
							v = (EventViewHolder) params[0];
							Postimageendpoint.Builder imageBuilder = new Postimageendpoint.Builder(
									AndroidHttp.newCompatibleTransport(),
									new JacksonFactory(), null);

							imageBuilder = CloudEndpointUtils
									.updateBuilder(imageBuilder);

							Postimageendpoint imageEndpoint = imageBuilder
									.setApplicationName("polimisocial").build();

							try {
								ip = (Initiative) params[1];
								s = imageEndpoint
										.getImageFromPostId(ip.getId())
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
								v.eventPicture.setImageBitmap(bitmap);
								ip.setBitmap(bitmap);
								v.eventPicture
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
					holder.eventPicture.setImageBitmap(item.getBitmap());
					notifyDataSetChanged();

					holder.eventPicture
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
			}

			holder.title.setText(item.getTitle());
			holder.location.setText(item.getLocation());

			String dateTime = item.getBeginningDate().toString();

			String dateString = composeDateString(dateTime.substring(0, 4),
					dateTime.substring(5, 7), dateTime.substring(8, 10),
					holder.month, holder.day);
			String time = dateTime.substring(11,
					Math.min(dateTime.length(), 16));

			holder.beginningDate.setText(dateString + " at " + time);

			holder.numbOfLikes.setText(item.getNumOfLikes() + " "
					+ getContext().getResources().getString(R.string.likes));
			holder.numbOfGoing.setText(item.getNumOfGoing() + " "
					+ getContext().getResources().getString(R.string.going));
			
			holder.likeButton.setSelected(false);
			holder.likeButton.setEnabled(true);
			if (postsLike != null && !postsLike.isEmpty()) {
				for(Long postId : postsLike){
					if(postId.compareTo(item.getId()) == 0){
						holder.likeButton.setEnabled(false);
						holder.likeButton.setSelected(true);
						break;
					}
				}
			}
			
			holder.likeButton
					.setOnClickListener(new IdButtonParameterOnClickListener(
							item.getId(), holder.likeButton) {

						@Override
						public void onClick(View v) {
							v.startAnimation(animScale);
							button.setEnabled(false);
							button.setSelected(true);							
							new AddLikeOrDisLikeTask(id,
									button)
									.execute("like");
						}
					});

			
			holder.goingButton.setSelected(false);
			holder.goingButton.setEnabled(true);
			if (postsDisLike != null && !postsDisLike.isEmpty()) {
				for(Long postId : postsDisLike){
					if(postId.compareTo(item.getId()) == 0){
						holder.goingButton.setEnabled(false);
						holder.goingButton.setSelected(true);
						break;
					}
				}
			}
			
			holder.goingButton
					.setOnClickListener(new IdButtonParameterOnClickListener(
							item.getId(), holder.goingButton) {

						@Override
						public void onClick(View v) {
							v.startAnimation(animScale);
							button.setEnabled(false);
							button.setSelected(true);							
							new AddLikeOrDisLikeTask(id,
									button)
									.execute("dislike");
						}
					});

		}

		return view;
	}

	public static String composeDateString(String year, String month,
			String day, TextView monthView, TextView dayView) {
		String stringMonth;
		switch (month) {
		case "01":
			stringMonth = "jan";
			break;
		case "02":
			stringMonth = "febr";
			break;
		case "03":
			stringMonth = "mar";
			break;
		case "04":
			stringMonth = "apr";
			break;
		case "05":
			stringMonth = "may";
			break;
		case "06":
			stringMonth = "june";
			break;
		case "07":
			stringMonth = "july";
			break;
		case "08":
			stringMonth = "aug";
			break;
		case "09":
			stringMonth = "sept";
			break;
		case "10":
			stringMonth = "oct";
			break;
		case "11":
			stringMonth = "nov";
			break;
		case "12":
			stringMonth = "dec";
			break;
		default:
			stringMonth = "error";
		}
		if (monthView != null)
			monthView.setText(stringMonth.toUpperCase());
		if (dayView != null)
			dayView.setText(day);
		return day + " " + stringMonth + " " + year;
	}

	static class EventViewHolder {
		TextView title;
		TextView location;
		ImageView eventPicture;
		TextView numbOfLikes;
		TextView numbOfGoing;
		TextView beginningDate;
		TextView month;
		TextView day;
		Button likeButton;
		Button goingButton;
	}

	
	public class AddLikeOrDisLikeTask extends AsyncTask<String, Void, Boolean> {

		private Long post;
		private String type;
		private Button button;

		public AddLikeOrDisLikeTask(Long postId, Button button) {
			this.post = postId;
			this.button = button;
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
					likeEndpoint.insertLike("initiative", like).execute();
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
					disLikeEndpoint.insertDisLike("initiative", disLike).execute();
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
				if (type.equals("like")) {
					Toast.makeText(context, "You liked this event!",
							Toast.LENGTH_SHORT).show();
					session.setLikeSpotted(post);
					postsLike.add(post);
				} else {
					Toast.makeText(context, "You'are going to this event!",
							Toast.LENGTH_SHORT).show();
					
					session.setDisLike(post);
					postsDisLike.add(post);
				}

			} else {
					button.setEnabled(true);
					button.setSelected(false);
				Toast.makeText(context,
						"Can't perform this operation.Retry later",
						Toast.LENGTH_LONG).show();
			}
		}

	}
	
	
}
