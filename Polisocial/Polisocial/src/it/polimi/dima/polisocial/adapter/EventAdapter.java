package it.polimi.dima.polisocial.adapter;

import java.text.DateFormat;
import java.util.List;
import java.util.StringTokenizer;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import it.polimi.dima.polisocial.R;
import it.polimi.dima.polisocial.ShowRelatedCommentsActivity;
import it.polimi.dima.polisocial.R.drawable;
import it.polimi.dima.polisocial.R.id;
import it.polimi.dima.polisocial.R.layout;
import it.polimi.dima.polisocial.customListeners.IdParameterOnClickListener;
import it.polimi.dima.polisocial.entity.initiativeendpoint.model.Initiative;
import it.polimi.dima.polisocial.utilClasses.NotificationCategory;

public class EventAdapter extends ArrayAdapter<Initiative> {
	private final LayoutInflater mInflater;
	private Context context;

	public EventAdapter(Context context) {
		super(context, R.layout.event_item);
		this.context = context;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public void setData(List<Initiative> data) {
		clear();
		if (data != null) {
			for (Initiative appEntry : data) {
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
			view = mInflater.inflate(R.layout.event_item, parent, false);
		} else {
			view = convertView;
		}
		Initiative item = getItem(position);

		TextView title = (TextView) view.findViewById(R.id.title);
		TextView beginningDate = (TextView) view
				.findViewById(R.id.beginning_date);
		TextView location = (TextView) view.findViewById(R.id.location);
		ImageView eventPicture = (ImageView) view
				.findViewById(R.id.event_picture);
		TextView description = (TextView) view.findViewById(R.id.description);
		TextView creationDate = (TextView) view.findViewById(R.id.timestamp);
		TextView numbOfComments = (TextView) view
				.findViewById(R.id.numb_of_comments);

		numbOfComments.setOnClickListener(new IdParameterOnClickListener(item
				.getId()) {
			@Override
			public void onClick(View v) {
				Intent showRelativeCommentsIntent = new Intent(context,
						ShowRelatedCommentsActivity.class);
				showRelativeCommentsIntent.putExtra("postId", id);
				showRelativeCommentsIntent.putExtra("notificationCategory",
						NotificationCategory.NOT_FROM_NOTIFICATION.toString());
				showRelativeCommentsIntent.putExtra("type", NotificationCategory.EVENT.toString());
				context.startActivity(showRelativeCommentsIntent);
			}
		});

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
        String date = dateTime.substring(0, Math.min(dateTime.length(), 10));
        String time = dateTime.substring(11, Math.min(dateTime.length(),16));
		
		beginningDate.setText(date+" at "+ time);
		description.setText(item.getText());

		// Converting timestamp into time ago format
		CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(item
				.getTimestamp().getValue(), System.currentTimeMillis(),
				DateUtils.SECOND_IN_MILLIS);
		creationDate.setText("created " + timeAgo);

		makeTextViewResizable(description, 3, "View More", true);
		numbOfComments.setText(item.getNumOfComments() + " comments");

		return view;
	}

	public static void makeTextViewResizable(final TextView tv,
			final int maxLine, final String expandText, final boolean viewMore) {

		if (tv.getTag() == null) {
			tv.setTag(tv.getText());
		}
		ViewTreeObserver vto = tv.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onGlobalLayout() {

				ViewTreeObserver obs = tv.getViewTreeObserver();
				obs.removeGlobalOnLayoutListener(this);
				if (maxLine == 0) {
					int lineEndIndex = tv.getLayout().getLineEnd(0);
					String text = tv.getText().subSequence(0,
							lineEndIndex - expandText.length() + 1)
							+ " " + expandText;
					tv.setText(text);
					tv.setMovementMethod(LinkMovementMethod.getInstance());
					tv.setText(
							addClickablePartTextViewResizable(
									Html.fromHtml(tv.getText().toString()), tv,
									maxLine, expandText, viewMore),
							BufferType.SPANNABLE);
				} else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
					int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
					String text = tv.getText().subSequence(0,
							lineEndIndex - expandText.length() + 1)
							+ " " + expandText;
					tv.setText(text);
					tv.setMovementMethod(LinkMovementMethod.getInstance());
					tv.setText(
							addClickablePartTextViewResizable(
									Html.fromHtml(tv.getText().toString()), tv,
									maxLine, expandText, viewMore),
							BufferType.SPANNABLE);
				} else {
					int lineEndIndex = tv.getLayout().getLineEnd(
							tv.getLayout().getLineCount() - 1);
					String text = tv.getText().subSequence(0, lineEndIndex)
							+ " " + expandText;
					tv.setText(text);
					tv.setMovementMethod(LinkMovementMethod.getInstance());
					tv.setText(
							addClickablePartTextViewResizable(
									Html.fromHtml(tv.getText().toString()), tv,
									lineEndIndex, expandText, viewMore),
							BufferType.SPANNABLE);
				}
			}
		});

	}

	private static SpannableStringBuilder addClickablePartTextViewResizable(
			final Spanned strSpanned, final TextView tv, final int maxLine,
			final String spanableText, final boolean viewMore) {
		String str = strSpanned.toString();
		SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

		if (str.contains(spanableText)) {
			ssb.setSpan(new ClickableSpan() {

				@Override
				public void onClick(View widget) {

					if (viewMore) {
						tv.setLayoutParams(tv.getLayoutParams());
						tv.setText(tv.getTag().toString(), BufferType.SPANNABLE);
						tv.invalidate();
						makeTextViewResizable(tv, -1, "View Less", false);
					} else {
						tv.setLayoutParams(tv.getLayoutParams());
						tv.setText(tv.getTag().toString(), BufferType.SPANNABLE);
						tv.invalidate();
						makeTextViewResizable(tv, 3, "View More", true);
					}

				}
			}, str.indexOf(spanableText), str.indexOf(spanableText)
					+ spanableText.length(), 0);

		}
		return ssb;

	}

}
