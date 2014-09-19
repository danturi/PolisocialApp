package it.polimi.dima.polisocial;

import java.util.List;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NotificationAdapter extends ArrayAdapter<NotificationItem> {
	private final LayoutInflater mInflater;
	
	private String notificationTextIntroduction = "The post you are following '"; //context.getString(R.string.notification_text_introduction_part);
	private String notificationTextSection = "' in the section ";//context.getString(R.string.notification_text_section_part);
	private String notificationTextCommented = " received a comment"; //context.getString(R.string.notification_text_comment_received_part);
	private String notificationTextHit = "received an hit";//context.getString(R.string.notification_text_hit_received_part);
	
    
	public NotificationAdapter(Context context) {
        super(context, R.layout.notification_item);
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
 
    public void setData(List<NotificationItem> data) {
        clear();
        if (data != null) {
            for (NotificationItem appEntry : data) {
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
            view = mInflater.inflate(R.layout.notification_item, parent, false);
        } else {
            view = convertView;
        }
 
        NotificationItem item = getItem(position);
        
        TextView text = (TextView) view.findViewById(R.id.text);
		TextView timestamp = (TextView) view
				.findViewById(R.id.timestamp);
		ImageView notificationTypeIcon = (ImageView) view
				.findViewById(R.id.notification_type_icon);
		
		
		
		//TODO: creare enum per il tipo di notifica?
		if(item.getCategory()=="HitOn"){
			text.setText(notificationTextIntroduction+item.getPostTitle()+notificationTextSection+item.getCategory()+notificationTextHit);
		}else{
			text.setText(Html.fromHtml(notificationTextIntroduction+"<font color=blue>"+item.getPostTitle()+"</font>"+notificationTextSection+item.getCategory()+notificationTextCommented));
		}	
		
		notificationTypeIcon.setImageResource(R.drawable.spotted_icon_normal);
		
		//TODO: Converting timestamp into time ago format
		/**CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
				Long.parseLong(item.getTimeStamp()),
				System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
		
		timestamp.setText(timeAgo);
**/
		timestamp.setText(item.getTimeStamp());
			
		
		return view;
    }
} 
