package it.polimi.dima.polisocial.adapter;


import it.polimi.dima.polisocial.CloudEndpointUtils;
import it.polimi.dima.polisocial.FullScreenPicActivity;
import it.polimi.dima.polisocial.R;
import it.polimi.dima.polisocial.entity.poliuserendpoint.Poliuserendpoint;
import it.polimi.dima.polisocial.entity.poliuserendpoint.model.ResponseObject;
import it.polimi.dima.polisocial.entity.poliuserendpoint.model.UserDTO;
import it.polimi.dima.polisocial.entity.postimageendpoint.Postimageendpoint;
import it.polimi.dima.polisocial.foursquare.foursquareendpoint.Foursquareendpoint.FindVenuesCategories;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.StaticLayout;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

public class UserAdapter extends ArrayAdapter<UserDTO>{

	
	private final LayoutInflater mInflater;
	private Context context;
	private List<UserDTO> origData = new ArrayList<UserDTO>();
	TextView statusMsg;
	
	public UserAdapter(Context context) {
		super(context, 0);
		this.context = context;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	}

	public void setData(List<UserDTO> list) {
		clear();
		origData=list;
		if (list != null) {
			for (UserDTO appEntry : list) {
				add(appEntry);
			}
		}
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		
        if (convertView == null) {
            view = mInflater.inflate(R.layout.searchpoliuser_item, parent, false);
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
		
	
	
	
}
