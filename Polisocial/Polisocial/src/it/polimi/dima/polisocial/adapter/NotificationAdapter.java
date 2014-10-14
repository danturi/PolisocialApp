package it.polimi.dima.polisocial.adapter;

import it.polimi.dima.polisocial.R;
import it.polimi.dima.polisocial.entity.notificationendpoint.model.Notification;
import it.polimi.dima.polisocial.utilClasses.NotificationCategory;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NotificationAdapter extends ArrayAdapter<Notification> {
	private final LayoutInflater mInflater;
	
	private String notificationTextIntroduction = "The post you are following '"; //context.getString(R.string.notification_text_introduction_part);
	private String notificationTextSection = "' in the section ";//context.getString(R.string.notification_text_section_part);
	private String notificationTextCommented = " received a comment"; //context.getString(R.string.notification_text_comment_received_part);
	private String notificationTextHit = "received an hit";//context.getString(R.string.notification_text_hit_received_part);
	
    
	public NotificationAdapter(Context context) {
        super(context, R.layout.notification_item);
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
 
    public void setData(List<Notification> data) {
        clear();
        if (data != null) {
            for (Notification appEntry : data) {
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
 
        Notification item = getItem(position);
        
        TextView text = (TextView) view.findViewById(R.id.text);
		TextView timestamp = (TextView) view
				.findViewById(R.id.timestamp);
		ImageView notificationTypeIcon = (ImageView) view
				.findViewById(R.id.notification_type_icon);
		
		
		
		
		
		if(item.getTypePost()=="HitOn"){
			text.setText(notificationTextIntroduction+item.getPostTitle()+notificationTextSection+item.getTypePost()+notificationTextHit);
		}else{
			text.setText(Html.fromHtml(notificationTextIntroduction+"<font color=blue>"+item.getPostTitle()+"</font>"+notificationTextSection+item.getTypePost()+notificationTextCommented));
		}	
		
		String postType=item.getTypePost();
		//depending on notification type and read flag, pick the appropriate icon
    	if (postType.equals(NotificationCategory.SIMPLE_SPOTTED.toString())){
    		if(item.getReadFlag()){
    			notificationTypeIcon.setImageResource(R.drawable.spotted_icon_normal);
    		}else{
    			notificationTypeIcon.setImageResource(R.drawable.spotted_icon_selected);
    		}
    	}else if(postType.equals(NotificationCategory.EVENT.toString())){
    		if(!item.getReadFlag()){
    			notificationTypeIcon.setImageResource(R.drawable.events_icon_normal);
    		}else{
    			notificationTypeIcon.setImageResource(R.drawable.events_icon_selected);
    		}
    	}else if(postType.equals(NotificationCategory.HIT_ON.toString())){
    		if(!item.getReadFlag()){
    			notificationTypeIcon.setImageResource(R.drawable.cupido_pressed);
    		}else{
    			notificationTypeIcon.setImageResource(R.drawable.cupido);
    		}
    	}else if(postType.equals(NotificationCategory.SECOND_HAND_BOOK.toString())){
    		
    	}else if(postType.equals(NotificationCategory.RENTAL.toString())){
    		
    	}else if(postType.equals(NotificationCategory.PRIVATE_LESSON.toString())){
    		
    	} 	
		
		//TODO: Converting timestamp into time ago format
		CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
				item.getTimestamp().getValue(),
				System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
		
		timestamp.setText(timeAgo);

			
		
		return view;
    }
} 
