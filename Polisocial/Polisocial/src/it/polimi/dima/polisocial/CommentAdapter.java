package it.polimi.dima.polisocial;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CommentAdapter extends ArrayAdapter<CommentItem> {
	
	private final LayoutInflater mInflater;
	private Context context;
    public CommentAdapter(Context context) {
        super(context, R.layout.comment_item);
        this.context=context;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
 
    public void setData(List<CommentItem> data) {
        clear();
        if (data != null) {
            for (CommentItem appEntry : data) {
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
            view = mInflater.inflate(R.layout.comment_item, parent, false);
        } else {
            view = convertView;
        }
 
        CommentItem item = getItem(position);
        TextView name = (TextView) view.findViewById(R.id.name);
		TextView timestamp = (TextView) view
				.findViewById(R.id.timestamp);
		TextView statusMsg = (TextView) view
				.findViewById(R.id.text);
		ImageView profilePic = (ImageView) view
				.findViewById(R.id.profilePic);
		
		name.setText(item.getName());

		// Converting timestamp into time ago format
		/**CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
				Long.parseLong(item.getTimeStamp()),
				System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
		
		timestamp.setText(timeAgo);
**/
		timestamp.setText(item.getTimeStamp());
	
		// Check for empty status message
		if (!TextUtils.isEmpty(item.getTex())) {
			statusMsg.setText(item.getTex());
			statusMsg.setVisibility(View.VISIBLE);
		} else {
			// status is empty, remove from view
			statusMsg.setVisibility(View.GONE);
		}

		// user profile pic
		//TODO: controlla se il post è anonimo, in tal caso metti l'immagine di incognito navigation
		int blobProfilePicLength = 0;
		byte[] byteArrayProfilePic = null;
		if (item.getProfilePic()!=null) {
			try {
				blobProfilePicLength = (int) item.getProfilePic().length();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				byteArrayProfilePic = item.getProfilePic().getBytes(1,
						blobProfilePicLength);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//profilePic.setImageBitmap(BitmapFactory.decodeByteArray(byteArrayProfilePic, 0 ,byteArrayProfilePic.length));
		profilePic.setImageResource(R.drawable.profilepicprova);		
		
		return view;
    }
} 
