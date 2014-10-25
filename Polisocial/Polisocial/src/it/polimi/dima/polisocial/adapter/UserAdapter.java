package it.polimi.dima.polisocial.adapter;


import it.polimi.dima.polisocial.CloudEndpointUtils;
import it.polimi.dima.polisocial.R;
import it.polimi.dima.polisocial.entity.poliuserendpoint.Poliuserendpoint;
import it.polimi.dima.polisocial.entity.poliuserendpoint.model.ResponseObject;
import it.polimi.dima.polisocial.entity.poliuserendpoint.model.UserDTO;

import java.io.IOException;
import java.util.List;

import android.content.Context;
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

public class UserAdapter extends EndlessListAdapter<UserDTO>{

	
	private final LayoutInflater mInflater;
	TextView statusMsg;
	private final int VIEW_USER = 0;
	private final int VIEW_LOADING = 1;
	
	public UserAdapter(Context context) {
		super(context, 0);
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	}

	public void setData(List<UserDTO> list) {
		clear();
		if (list != null) {
			for (UserDTO appEntry : list) {
				add(appEntry);
			}
		}
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view=convertView;
		int type = getItemViewType(position);
		
        if (view == null) {
        	if (type == VIEW_LOADING) {
				view = mInflater.inflate(R.layout.progress, parent, false);
        	}else {
        		view = mInflater.inflate(R.layout.searchpoliuser_item, parent, false);
        	}
            
        } else {
            view = convertView;
        }
        
        UserDTO item = getItem(position);
        
        TextView text = (TextView) view.findViewById(R.id.text);
        final ImageView image = (ImageView) view.findViewById(R.id.poliuser_icon);
        text.setText(item.getNickname());
        
        new AsyncTask<Long, Void, String>() {


			@Override
			protected String doInBackground(Long... params) {
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
					pic = endpoint.getPictureUser(params[0]).execute();
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
				if (pic != null && image != null) {
					byte[] byteArrayImage = Base64.decode(pic, Base64.DEFAULT);
					image.setImageBitmap(BitmapFactory.decodeByteArray(
							byteArrayImage, 0, byteArrayImage.length));
				} else {
					image.setImageResource(R.drawable.no_picture_pic);
				}
			}
		}.execute(item.getUserId());
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

	/*
	@Override
	public Filter getFilter() {
		return new Filter(){

	        @Override
	        protected FilterResults performFiltering(CharSequence constraint) {
	             constraint = constraint.toString().toLowerCase();
	             FilterResults result = new FilterResults();
	             

	                if (constraint != null && constraint.toString().length() > 0) {
	                  List<UserDTO> founded = new ArrayList<UserDTO>();
	                        for(UserDTO item: origData){
	                            if(item.getNickname().toLowerCase().startsWith((String) constraint)){
	                                founded.add(item);
	                            }
	                    }

	                        result.values = founded;
	                        result.count = founded.size();
	                    }else {
	                        result.values = origData;
	                        result.count = origData.size();
	                    }
	            return result;
	        }

			@Override
			protected void publishResults(CharSequence constraint,
					FilterResults results) {
				clear();
		           for (UserDTO item : (List<UserDTO>) results.values) {
		                 add(item);
		           }
		    notifyDataSetChanged();
		   
				
			}

	    };
	} 
	*/
		
	
	
	
}
