package it.polimi.dima.polisocial.adapter;

import it.polimi.dima.polisocial.CloudEndpointUtils;
import it.polimi.dima.polisocial.R;
import it.polimi.dima.polisocial.entity.poliuserendpoint.Poliuserendpoint;
import it.polimi.dima.polisocial.entity.poliuserendpoint.model.ResponseObject;
import it.polimi.dima.polisocial.entity.poliuserendpoint.model.UserDTO;

import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;

public class UserAdapter extends EndlessListAdapter<UserDTO> {

	private final LayoutInflater mInflater;
	TextView statusMsg;
	private final int VIEW_USER = 0;
	private final int VIEW_LOADING = 1;

	public UserAdapter(Context context) {
		super(context, R.layout.searchpoliuser_item);
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		UserListViewHolder holder;
		int type = getItemViewType(position);

		if (view == null) {
			holder = new UserListViewHolder();
			if (type == VIEW_LOADING) {
				view = mInflater.inflate(R.layout.progress, parent, false);
			} else {
				view = mInflater.inflate(R.layout.searchpoliuser_item, parent,
						false);
				holder.name = (TextView) view.findViewById(R.id.text);
				holder.profilePic = (ImageView) view
						.findViewById(R.id.poliuser_icon);
			}
			view.setTag(holder);
		} else {
			holder = (UserListViewHolder) view.getTag();
		}

		if (type != VIEW_LOADING) {
			UserDTO item = getItem(position);

			holder.name.setText(item.getNickname());

			new AsyncTask<Object, Void, Boolean>() {
				private UserListViewHolder v;
				private String s;
				private UserDTO ud;

				@Override
				protected Boolean doInBackground(Object... params) {
					v = (UserListViewHolder) params[0];
					Poliuserendpoint.Builder builder = new Poliuserendpoint.Builder(
							AndroidHttp.newCompatibleTransport(),
							new JacksonFactory(), null);
					builder = CloudEndpointUtils.updateBuilder(builder);
					Poliuserendpoint endpoint = builder.setApplicationName(
							"polimisocial").build();

					// check if email is available
					ResponseObject pic = null;
					String byteArrayPic;
					try {
						s = (String) endpoint.getPictureUser(ud.getUserId())
								.execute().getObject();
					} catch (IOException e2) {
						System.out.println(e2.getMessage());
						return false;
					}
					return true;
				}

				@Override
				protected void onPostExecute(Boolean result) {
					if (result && s != null) {
						final byte[] byteArrayImage = Base64.decode(s,
								Base64.DEFAULT);
						Bitmap bitmap = BitmapFactory.decodeByteArray(
								byteArrayImage, 0, byteArrayImage.length);
						v.profilePic.setImageBitmap(bitmap);
						ud.setBitmap(bitmap);
					} else {
						v.profilePic
								.setImageResource(R.drawable.no_picture_pic);
					}
				}
			}.execute(holder, item);
		}
		return view;
	}

	@Override
	public int getItemViewType(int position) {
		if (position >= (getCount() - loading_row)) {
			return VIEW_LOADING;
		}
		return VIEW_USER;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	static class UserListViewHolder {
		TextView name;
		ImageView profilePic;
	}

	/*
	 * @Override public Filter getFilter() { return new Filter(){
	 * 
	 * @Override protected FilterResults performFiltering(CharSequence
	 * constraint) { constraint = constraint.toString().toLowerCase();
	 * FilterResults result = new FilterResults();
	 * 
	 * 
	 * if (constraint != null && constraint.toString().length() > 0) {
	 * List<UserDTO> founded = new ArrayList<UserDTO>(); for(UserDTO item:
	 * origData){ if(item.getNickname().toLowerCase().startsWith((String)
	 * constraint)){ founded.add(item); } }
	 * 
	 * result.values = founded; result.count = founded.size(); }else {
	 * result.values = origData; result.count = origData.size(); } return
	 * result; }
	 * 
	 * @Override protected void publishResults(CharSequence constraint,
	 * FilterResults results) { clear(); for (UserDTO item : (List<UserDTO>)
	 * results.values) { add(item); } notifyDataSetChanged();
	 * 
	 * 
	 * }
	 * 
	 * }; }
	 */

}
