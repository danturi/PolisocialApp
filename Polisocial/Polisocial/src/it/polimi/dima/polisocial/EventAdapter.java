package it.polimi.dima.polisocial;

import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import it.polimi.dima.polisocial.customOnClickListeners.IdParameterOnClickListener;

public class EventAdapter extends ArrayAdapter<EventItem> {
	private final LayoutInflater mInflater;
	private Context context;
    public EventAdapter(Context context) {
        super(context, R.layout.event_item);
        this.context=context;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
 
    public void setData(List<EventItem> data) {
        clear();
        if (data != null) {
            for (EventItem appEntry : data) {
                add(appEntry);
            }
        }
    }
 
    /**
     * Populate new items in the list.
     */
    @Override public View getView(int position, View convertView, ViewGroup parent) {
        View view;
 
        if (convertView == null) {
            view = mInflater.inflate(R.layout.event_item, parent, false);
        } else {
            view = convertView;
        }
 
        ImageView eventPicture=(ImageView) view
				.findViewById(R.id.event_picture);
        EventItem item = getItem(position);
        TextView title = (TextView) view.findViewById(R.id.title);
		TextView beginningDate = (TextView) view
				.findViewById(R.id.beginning_date);
		TextView description = (TextView) view
				.findViewById(R.id.description);

		TextView creationDate = (TextView) view.findViewById(R.id.timestamp);
		
		TextView numbOfComments = (TextView) view.findViewById(R.id.numb_of_comments);

		numbOfComments.setOnClickListener(new IdParameterOnClickListener(item.getId()) {

            @Override
            public void onClick(View v) {
            	Intent showRelativeCommentsIntent = new Intent(context, ShowRelatedCommentsActivity.class);
            	showRelativeCommentsIntent.putExtra("postId",id);
            	showRelativeCommentsIntent.putExtra("notificationCategory", NotificationCategory.NOT_FROM_NOTIFICATION.toString());
            	context.startActivity(showRelativeCommentsIntent);
            }
        });
		
		
		eventPicture.setImageResource(R.drawable.imageprova);
		
		title.setText(item.getTitle());

		// Converting timestamp into time ago format
		/**CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
				Long.parseLong(item.getTimeStamp()),
				System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
		
		timestamp.setText(timeAgo);
**/
		
		
		beginningDate.setText("BEGINS: "+ item.getBeginningDate().toString());
		creationDate.setText("created" + item.getTimestamp().toString());
		description.setText(item.getShortDescription());
        
		makeTextViewResizable(description, 3, "View More", true);
		
		numbOfComments.setText(item.getNumbOfComments()+" comments");
				
		return view;
    }
    
    public static void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {

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
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), BufferType.SPANNABLE);
                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                    int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), BufferType.SPANNABLE);
                } else {
                    int lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, lineEndIndex, expandText,
                                    viewMore), BufferType.SPANNABLE);
                }
            }
        });

    }

    private static SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
            final int maxLine, final String spanableText, final boolean viewMore) {
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
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;

    }

    
    
    
}
