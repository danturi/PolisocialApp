package it.polimi.dima.polisocial;

import it.polimi.dima.polisocial.customOnClickListeners.IdParameterOnClickListener;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class PostAdapter extends ArrayAdapter<PostItem> {
 
	private final LayoutInflater mInflater;
	private Context context;
    public PostAdapter(Context context) {
        super(context, R.layout.post_item);
        this.context=context;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
 
    public void setData(List<PostItem> data) {
        clear();
        if (data != null) {
            for (PostItem appEntry : data) {
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
            view = mInflater.inflate(R.layout.post_item, parent, false);
        } else {
            view = convertView;
        }
 
        PostItem item = getItem(position);
        TextView name = (TextView) view.findViewById(R.id.name);
		TextView timestamp = (TextView) view
				.findViewById(R.id.timestamp);
		TextView statusMsg = (TextView) view
				.findViewById(R.id.text);
		ImageView profilePic = (ImageView) view
				.findViewById(R.id.profilePic);
		ImageView postImage = (ImageView) view
				.findViewById(R.id.postImage);
		TextView numbOfComments = (TextView) view.findViewById(R.id.numb_of_comments);

		numbOfComments.setOnClickListener(new IdParameterOnClickListener(item.getId()) {

            @Override
            public void onClick(View v) {
            	Intent showRelativeCommentsIntent = new Intent(context, ShowRelatedCommentsActivity.class);
            	showRelativeCommentsIntent.putExtra("postId",id);
            	context.startActivity(showRelativeCommentsIntent);
            }
        });
		
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
		//TODO: controlla se il post � anonimo, in tal caso metti l'immagine di incognito navigation
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
		// Feed image
		if (item.getImage() != null) {
			int blobImageLength = 0;
			try {
				blobImageLength = (int) item.getImage().length();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			byte[] byteArrayImage = null;
			try {
				byteArrayImage = item.getImage().getBytes(1, blobImageLength);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//postImage.setImageBitmap(BitmapFactory.decodeByteArray(byteArrayImage, 0 ,byteArrayImage.length));
			postImage.setVisibility(View.VISIBLE);
			//da cancellare
			postImage.setImageResource(R.drawable.imageprova);
		} else {
			postImage.setVisibility(View.GONE);
		}

        
		numbOfComments.setText(item.getNumbOfComments()+" comments");
		
		
		return view;
    }
} 
