package it.polimi.dima.polisocial.adapter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import it.polimi.dima.polisocial.CloudEndpointUtils;
import it.polimi.dima.polisocial.FullScreenPicActivity;
import it.polimi.dima.polisocial.R;
import it.polimi.dima.polisocial.ShowRelatedCommentsActivity;
import it.polimi.dima.polisocial.customListeners.BitmapParameterOnClickListener;
import it.polimi.dima.polisocial.customListeners.IdParameterOnClickListener;
import it.polimi.dima.polisocial.entity.initiativeendpoint.model.Initiative;
import it.polimi.dima.polisocial.entity.postimageendpoint.Postimageendpoint;
import it.polimi.dima.polisocial.utilClasses.PostType;
import it.polimi.dima.polisocial.utilClasses.WhatToShow;

public class EventAdapter extends EndlessListAdapter<Initiative> {

	private final int VIEW_STANDARD = 0;
	private final int VIEW_LOADING = 1;

	public EventAdapter(Context context) {
		super(context, R.layout.event_item);
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
		EventViewHolder holder;
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
				holder.numbOfGoing = (TextView) view.findViewById(R.id.numb_of_participants);
				holder.month = (TextView) view.findViewById(R.id.month);
				holder.day = (TextView) view.findViewById(R.id.day);

			} else {
				view = mInflater.inflate(R.layout.progress_white, parent, false);
			}
			view.setTag(holder);
		} else {
			holder = (EventViewHolder) view.getTag();
		}

		if (type == VIEW_STANDARD) {

			Initiative item = getItem(position);
			holder.numbOfLikes
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

			holder.numbOfLikes
					.setText(item.getNumOfLikes() + " " + getContext().getResources().getString(R.string.likes));
			holder.numbOfGoing.setText(item.getNumOfGoing() + " " + getContext().getResources().getString(R.string.going));

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
	}

}
