package it.polimi.dima.polisocial;

import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
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
 
        EventItem item = getItem(position);
        TextView title = (TextView) view.findViewById(R.id.title);
		TextView beginningDate = (TextView) view
				.findViewById(R.id.beginning_date);
		TextView shortDescription = (TextView) view
				.findViewById(R.id.short_description);
		ImageView categoryImage=(ImageView) view
				.findViewById(R.id.category_image);
		TextView numbOfComments = (TextView) view.findViewById(R.id.numb_of_comments);

		numbOfComments.setOnClickListener(new IdParameterOnClickListener(item.getId()) {

            @Override
            public void onClick(View v) {
            	Intent showRelativeCommentsIntent = new Intent(context, ShowRelatedCommentsActivity.class);
            	showRelativeCommentsIntent.putExtra("postId",id);
            	context.startActivity(showRelativeCommentsIntent);
            }
        });
		
		title.setText(item.getTitle());

		// Converting timestamp into time ago format
		/**CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
				Long.parseLong(item.getTimeStamp()),
				System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
		
		timestamp.setText(timeAgo);
**/
		beginningDate.setText("BEGINS: "+ item.getBeginningDate().toString());
	
		shortDescription.setText(item.getShortDescription());

		categoryImage.setImageDrawable(context.getResources().getDrawable(R.drawable.notifications_icon_normal));
        
		numbOfComments.setText(item.getNumbOfComments()+" comments");
		
		
		return view;
    }
}
