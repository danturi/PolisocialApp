package it.polimi.dima.polisocial.loader;

import it.polimi.dima.polisocial.CloudEndpointUtils;
import it.polimi.dima.polisocial.entity.secondhandbookendpoint.Secondhandbookendpoint;
import it.polimi.dima.polisocial.entity.secondhandbookendpoint.model.CollectionResponseSecondHandBook;
import it.polimi.dima.polisocial.entity.secondhandbookendpoint.model.ResponseObject;
import it.polimi.dima.polisocial.entity.secondhandbookendpoint.model.SecondHandBook;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import android.content.Context;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;

public class SecondHandBookListLoader extends
		EndlessListAsyncTaskLoader<CollectionResponseSecondHandBook> {

	private String bookTitle;
	private String authorName;
	private String userFaculty;
	private int userAge;
	private boolean mSuggestion;

	public SecondHandBookListLoader(Context context, String cursor,
			String bookTitle, String authorName, boolean suggestion) {
		super(context, cursor);
		this.bookTitle = bookTitle;
		this.authorName = authorName;
		this.mSuggestion = suggestion;
	}

	public SecondHandBookListLoader(Context context, String cursor,
			String userFaculty, int userAge, boolean suggestion) {
		super(context, cursor);
		this.userFaculty = userFaculty;
		this.userAge = userAge;
		this.mSuggestion = suggestion;
	}

	@Override
	public CollectionResponseSecondHandBook loadInBackground() {
		// retrieve data from server
		Secondhandbookendpoint.Builder builder = new Secondhandbookendpoint.Builder(
				AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
				null);
		builder = CloudEndpointUtils.updateBuilder(builder);
		Secondhandbookendpoint endpoint = builder.setApplicationName(
				"polimisocial").build();

		CollectionResponseSecondHandBook list = new CollectionResponseSecondHandBook();

		// TODO differenziare le query in base al contenuto
		if (mSuggestion) {
			// query suggested book depending on user age and faculty
			try {
				if (cursor != null) { 
					list =endpoint.listSecondHandBook().setLimit(10) .setCursor(cursor).execute();
				} else { 
					list = endpoint.listSecondHandBook().setLimit(10).execute(); 
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			// query book depending on title and/or author typed by the user
			try {
				if (cursor != null) { 
					list = endpoint.searchFullTextBook(bookTitle)
							.setAuthor(authorName).setCursor(cursor).execute();
				}else {
					list = endpoint.searchFullTextBook(bookTitle)
							.setAuthor(authorName).execute();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
				if (new StringTokenizer(e.getMessage().toString()).nextToken()
						.equals("404"))
					return list;

			}

		}
		return list;
	}
	/**
	 * Commentendpoint.Builder build = new Commentendpoint.Builder(
	 * AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);
	 * 
	 * build = CloudEndpointUtils.updateBuilder(build); Commentendpoint
	 * commEndpoint = build.setApplicationName("polimisocial") .build();
	 **/
	/*
	 * try { if (cursor != null) { list =
	 * endpoint.listSecondHandBook().setLimit(10) .setCursor(cursor).execute();
	 * } else { list = endpoint.listSecondHandBook().setLimit(10).execute(); }
	 * /** if (list.getItems() != null) { for (PostSpotted post :
	 * list.getItems()) { ResponseObject count =
	 * commEndpoint.getNumbPostComments( post.getId()).execute();
	 * post.setNumOfComments(Integer.valueOf((String) count .getObject())); } }
	 * 
	 * } catch (IOException e) { e.printStackTrace(); }
	 */

}