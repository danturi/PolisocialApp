package it.polimi.dima.polisocial.loader;

import it.polimi.dima.polisocial.CloudEndpointUtils;
import it.polimi.dima.polisocial.entity.poliuserendpoint.Poliuserendpoint;
import it.polimi.dima.polisocial.entity.poliuserendpoint.model.CollectionResponseUserDTO;

import java.io.IOException;
import java.util.StringTokenizer;

import android.content.Context;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;

public class UserListLoader extends EndlessListAsyncTaskLoader<CollectionResponseUserDTO> {

	private String username;

	public UserListLoader(Context context, String cursor, String username) {
		super(context,cursor);
		this.username=username;
	}

	@Override
	public CollectionResponseUserDTO loadInBackground() {
		//retrieve data from server
        Poliuserendpoint.Builder builder = new Poliuserendpoint.Builder(AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);
        builder = CloudEndpointUtils.updateBuilder(builder);
		Poliuserendpoint endpoint = builder.setApplicationName("polimisocial").build();
		
		CollectionResponseUserDTO list = new CollectionResponseUserDTO();
		
		try {
			if(username!=null){
				list = endpoint.searchPoliUser().setLimit(15).setCursor(cursor).setUsername(username).execute();				
			}else {
				list = endpoint.searchPoliUser().setLimit(15).setCursor(cursor).execute();
			}
		}catch (IOException e) {
			if( new StringTokenizer(e.getMessage().toString()).nextToken().equals("404"))
				return list;
		}
        return list;
		
	}
	
}
