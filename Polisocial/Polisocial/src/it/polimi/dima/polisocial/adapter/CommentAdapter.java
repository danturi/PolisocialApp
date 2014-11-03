package it.polimi.dima.polisocial.adapter;

import it.polimi.dima.polisocial.CloudEndpointUtils;
import it.polimi.dima.polisocial.ProfileActivity;
import it.polimi.dima.polisocial.R;
import it.polimi.dima.polisocial.customListeners.IdParameterOnClickListener;
import it.polimi.dima.polisocial.entity.commentendpoint.model.Comment;
import it.polimi.dima.polisocial.entity.hitonendpoint.model.HitOn;
import it.polimi.dima.polisocial.entity.poliuserendpoint.Poliuserendpoint;
import it.polimi.dima.polisocial.entity.poliuserendpoint.model.ResponseObject;
import it.polimi.dima.polisocial.utilClasses.PostType;

import java.io.IOException;
import java.util.List;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.format.DateUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CommentAdapter extends ArrayAdapter<Object> {

	private final int VIEW_ONLY_COMMENTS = 0;
	private final int VIEW_HIT_ON = 1;

	private final LayoutInflater mInflater;
	private Context context;
	private String postType;

	public CommentAdapter(Context context, String postType) {
		super(context, 0);
		this.context = context;
		this.postType = postType;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public void setData(List<Object> data) {
		clear();
		if (data != null) {
			for (Object appEntry : data) {
				add(appEntry);
			}
		}
	}

	@Override
	public int getItemViewType(int position) {
		if(!postType.equals(PostType.HIT_ON.toString())){ 
			return VIEW_ONLY_COMMENTS;
		}else{
			return VIEW_HIT_ON;
		}
		
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
		CommentViewHolder holder;
		int type = getItemViewType(position);

		if (view == null) {
			holder = new CommentViewHolder();
				view = mInflater.inflate(R.layout.comment_item, parent, false);
				holder.name = (TextView) view.findViewById(R.id.name);
				holder.timestamp = (TextView) view.findViewById(R.id.timestamp);
				holder.statusMsg = (TextView) view.findViewById(R.id.text);
				holder.profilePic = (ImageView) view
						.findViewById(R.id.profilePic);
				view.setTag(holder);
		} else {
			holder = (CommentViewHolder) view.getTag();
		}
		if (type == VIEW_ONLY_COMMENTS) {
		setUpCommentRow(position, holder);
		}else{
			setUpHitOnRow(position, holder);
		}
		return view;
	}

	private void setUpHitOnRow(int position, CommentViewHolder holder) {
		HitOn commentItem;
		commentItem = (HitOn) getItem(position);

		holder.name.setText(commentItem.getAuthorName());
		holder.name.setOnClickListener(new IdParameterOnClickListener(
				commentItem.getSeducerId()) {
			@Override
			public void onClick(View v) {
				Intent showProfileIntent = new Intent(context,
						ProfileActivity.class);
				showProfileIntent.putExtra("userToRetrieveId", id);
				context.startActivity(showProfileIntent);
			}
		});
		// Converting timestamp into time ago format
		CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(commentItem
				.getTimestamp().getValue(), System.currentTimeMillis(),
				DateUtils.SECOND_IN_MILLIS);
		holder.timestamp.setText(timeAgo);

		holder.statusMsg.setText(commentItem.getContact());

		holder.profilePic.setOnClickListener(new IdParameterOnClickListener(
				commentItem.getSeducerId()) {
			@Override
			public void onClick(View v) {
				Intent showProfileIntent = new Intent(context,
						ProfileActivity.class);
				showProfileIntent.putExtra("userToRetrieveId", id);
				context.startActivity(showProfileIntent);
			}
		});
		// retrieve profile pic with asynctask
		new RetrieveCommentProfilePicTask(holder.profilePic,
				commentItem.getSeducerId()).execute();

	}

	private void setUpCommentRow(int position, CommentViewHolder holder) {
		Comment commentItem;
		commentItem = (Comment) getItem(position);

		holder.name.setText(commentItem.getAuthorName());
		holder.name.setOnClickListener(new IdParameterOnClickListener(
				commentItem.getAuthorId()) {
			@Override
			public void onClick(View v) {
				Intent showProfileIntent = new Intent(context,
						ProfileActivity.class);
				showProfileIntent.putExtra("userToRetrieveId", id);
				context.startActivity(showProfileIntent);
			}
		});
		// Converting timestamp into time ago format
		CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(commentItem
				.getCommentTimestamp().getValue(), System.currentTimeMillis(),
				DateUtils.SECOND_IN_MILLIS);
		holder.timestamp.setText(timeAgo);
		holder.statusMsg.setText(commentItem.getText());
		holder.profilePic.setOnClickListener(new IdParameterOnClickListener(
				commentItem.getAuthorId()) {
			@Override
			public void onClick(View v) {
				Intent showProfileIntent = new Intent(context,
						ProfileActivity.class);
				showProfileIntent.putExtra("userToRetrieveId", id);
				context.startActivity(showProfileIntent);
			}
		});
		// retrieve profile pic with asynctask
		new RetrieveCommentProfilePicTask(holder.profilePic,
				commentItem.getAuthorId()).execute();
	}


	public class RetrieveCommentProfilePicTask extends
			AsyncTask<Void, String, String> {
		private ImageView view;
		private long userId;

		public RetrieveCommentProfilePicTask(ImageView view, long userId) {
			this.view = view;
			this.userId = userId;
		}

		@Override
		protected String doInBackground(Void... voids) {
			// TODO
			// retrieve from server the picture of the user having id = userId
			// and pass it to a
			Poliuserendpoint.Builder builder = new Poliuserendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
					null);
			builder = CloudEndpointUtils.updateBuilder(builder);
			Poliuserendpoint endpoint = builder.setApplicationName(
					"polimisocial").build();

			// check if email is available
			ResponseObject pic = null;
			String byteArrayPic;
			try {
				pic = endpoint.getPictureUser(userId).execute();
			} catch (IOException e2) {
			}
			if (pic.getObject() != null) {
				byteArrayPic = (String) pic.getObject();
			} else {
				byteArrayPic = null;
			}

			return byteArrayPic;

		}

		@Override
		protected void onPostExecute(String pic) {
			if (pic != null && view != null) {
				byte[] byteArrayImage = Base64.decode(pic, Base64.DEFAULT);
				view.setImageBitmap(BitmapFactory.decodeByteArray(
						byteArrayImage, 0, byteArrayImage.length));
			} else {
				view.setImageResource(R.drawable.no_picture_pic);
			}
		}
	}

	static class CommentViewHolder {
		public int type;
		TextView name;
		TextView timestamp;
		TextView statusMsg;
		ImageView profilePic;
	}

}
