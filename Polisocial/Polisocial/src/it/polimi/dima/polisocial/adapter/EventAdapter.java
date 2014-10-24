package it.polimi.dima.polisocial.adapter;

import java.io.IOException;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import it.polimi.dima.polisocial.CloudEndpointUtils;
import it.polimi.dima.polisocial.FullScreenPicActivity;
import it.polimi.dima.polisocial.R;
import it.polimi.dima.polisocial.ShowRelatedCommentsActivity;
import it.polimi.dima.polisocial.customListeners.IdParameterOnClickListener;
import it.polimi.dima.polisocial.entity.initiativeendpoint.model.Initiative;
import it.polimi.dima.polisocial.entity.postimageendpoint.Postimageendpoint;
import it.polimi.dima.polisocial.utilClasses.NotificationCategory;

public class EventAdapter extends EndlessListAdapter<Initiative> {
	
	private final int VIEW_STANDARD=0;
	private final int VIEW_LOADING=1;
	
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
			if(type==VIEW_STANDARD){
				view = mInflater.inflate(R.layout.event_item, parent, false);

				holder.title = (TextView) view.findViewById(R.id.title);
				holder.beginningDate = (TextView) view
						.findViewById(R.id.beginning_date);
				holder.location = (TextView) view.findViewById(R.id.location);
				holder.eventPicture = (ImageView) view
						.findViewById(R.id.event_picture);
				holder.description = (TextView) view.findViewById(R.id.description);
				holder.creationDate = (TextView) view.findViewById(R.id.timestamp);
				holder.numbOfComments = (TextView) view
						.findViewById(R.id.numb_of_comments);

				
			}else{
				view = mInflater.inflate(R.layout.progress, parent, false);
			}
			holder.type = type;
			view.setTag(holder);
		} else {
			holder = (EventViewHolder) view.getTag();
		}
		
		if(type==VIEW_STANDARD){

			Initiative item = getItem(position);
			holder.numbOfComments.setOnClickListener(new IdParameterOnClickListener(item
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
			if(item.getHavePicture()){
				//asynctask to retrieve post image
				new AsyncTask<Object, Void, Boolean>() {
				    private EventViewHolder v;
				    private String s;
				    private Initiative ip;

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
							ip = (Initiative) params[1];
							s=imageEndpoint
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

				    	if(result){
				    		final byte[] byteArrayImage = Base64.decode(s,
									Base64.DEFAULT);
				    		Bitmap bitmap = BitmapFactory.decodeByteArray(
									byteArrayImage, 0, byteArrayImage.length);
							v.eventPicture.setImageBitmap(bitmap);
				    		ip.setBitmap(bitmap);
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
				}.execute(holder,item);
			}else {
				holder.eventPicture.setImageResource(R.drawable.event_no_pic);
			}
			
			holder.title.setText(item.getTitle());
			holder.location.setText(item.getLocation());
			
	        String dateTime = item.getBeginningDate().toString();
	        String dateString = composeDateString(dateTime.substring(0,4), dateTime.substring(5,7), dateTime.substring(8,10) );
	        String time = dateTime.substring(11, Math.min(dateTime.length(),16));
			
	        holder.beginningDate.setText(dateString+" at "+ time);
	        holder.description.setText(item.getText());

			// Converting timestamp into time ago format
			CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(item
					.getTimestamp().getValue(), System.currentTimeMillis(),
					DateUtils.SECOND_IN_MILLIS);
			holder.creationDate.setText("created " + timeAgo);

			makeTextViewResizable(holder.description, 3, "View More", true);
			holder.numbOfComments.setText(item.getNumOfComments() + " comments");
		
		}
		
		
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
	
	public static String composeDateString(String year, String month, String day){
    String stringMonth;
    switch(month){
    	case "01": 
    		stringMonth="jan";
    		break;
    	case "02": 
    		stringMonth="febr";
    		break;
    		case "03": 
    		stringMonth="mar";
    		break;
    		case "04": 
    		stringMonth="apr";
    		break;
    		case "05": 
    		stringMonth="may";
    		break;
    		case "06": 
    		stringMonth="june";
    		break;
    		case "07": 
    		stringMonth="july";
    		break;
    		case "08": 
    		stringMonth="aug";
    		break;
    		case "09": 
    		stringMonth="sept";
    		break;
    		case "10": 
    		stringMonth="oct";
    		break;
    		case "11": 
    		stringMonth="nov";
    		break;
    		case "12": 
    		stringMonth="dec";
    		break;
    		default:stringMonth="error";
    }

    return day +" "+ stringMonth + " " + year; 
    }

	
	static class EventViewHolder {
		public int type;
		
		TextView title;
		TextView location;
		ImageView eventPicture;
		TextView numbOfComments;
		TextView creationDate;		
		TextView description;
		TextView beginningDate;		
	}

}
