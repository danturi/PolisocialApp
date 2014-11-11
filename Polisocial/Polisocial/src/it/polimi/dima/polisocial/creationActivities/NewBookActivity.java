package it.polimi.dima.polisocial.creationActivities;

import it.polimi.dima.polisocial.CloudEndpointUtils;
import it.polimi.dima.polisocial.R;
import it.polimi.dima.polisocial.entity.secondhandbookendpoint.Secondhandbookendpoint;
import it.polimi.dima.polisocial.entity.secondhandbookendpoint.model.SecondHandBook;
import it.polimi.dima.polisocial.utilClasses.SessionManager;
import it.polimi.dima.polisocial.utilClasses.ShowProgress;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.StringTokenizer;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;

public class NewBookActivity extends Activity {

	private EditText mIsbnCode;
	private EditText mBookTitle;
	private EditText mBookCondition;
	private EditText mBookPublisher;
	private EditText mBookPublicationDate;
	private Spinner mSpinnerFaculty;
	private EditText mBookPrice;
	private EditText mBookAuthors;
	private Button mIsbnButton;
	private Double price;

	private View mProgressViewFullScreen;
	private View mProgressViewIsbnLoading;
	private View mBookCreation;
	private View mBookNotIsbnPanel;

	private SessionManager sessionManager;

	private String INITIAL_PRICE = "Tap to set book price";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_book);

		sessionManager = new SessionManager(getApplicationContext());
		mProgressViewFullScreen = findViewById(R.id.progress);
		mProgressViewIsbnLoading = findViewById(R.id.progress_isbn);
		mBookCreation = findViewById(R.id.book_creation_form);
		mBookNotIsbnPanel = findViewById(R.id.not_isbn_panel);

		mBookCondition = (EditText) findViewById(R.id.condition);
		mBookAuthors = (EditText) findViewById(R.id.authors);
		mBookPublicationDate = (EditText) findViewById(R.id.publication_date);
		mBookPublisher = (EditText) findViewById(R.id.publisher);
		mBookTitle = (EditText) findViewById(R.id.title);
		mBookPrice = (EditText) findViewById(R.id.price);
		mIsbnCode = (EditText) findViewById(R.id.isbn);
		mIsbnButton = (Button) findViewById(R.id.buttonIsbn);
		mSpinnerFaculty = (Spinner) findViewById(R.id.spinnerFaculty);
		mIsbnButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String inputCode = mIsbnCode.getText().toString();
				if (inputCode.length() == 13 || inputCode.length() == 10) {

					long l;
					try {
						l = Long.valueOf(inputCode);
					} catch (NumberFormatException e) {
						mIsbnCode
								.setError(getString(R.string.error_invalid_isbn));
						mBookPublicationDate.requestFocus();
						return;
					}
					// TODO chiamata all asynctask che chiama book api
					new RetrieveBookInfo().execute(String.valueOf(l));
				} else {
					mIsbnCode.setError(getString(R.string.error_invalid_isbn));
					mBookPublicationDate.requestFocus();
					return;
				}
			}
		});

		// Attach TextWatcher to EditText
		/*
		 * mIsbnCode.addTextChangedListener(new TextWatcher() {
		 * 
		 * public void beforeTextChanged(CharSequence s, int start, int count,
		 * int after) { }
		 * 
		 * public void onTextChanged(CharSequence s, int start, int before, int
		 * count) { }
		 * 
		 * public void afterTextChanged(Editable s) { if (s.length() == 13) {
		 * 
		 * double d; try { d =
		 * Double.parseDouble(mIsbnCode.getText().toString()); } catch
		 * (NumberFormatException e) { mIsbnCode
		 * .setError(getString(R.string.error_invalid_year));
		 * mBookPublicationDate.requestFocus(); return; } // TODO chiamata all
		 * asynctask che chiama book api new
		 * RetrieveBookInfo().execute(String.valueOf(d)); } } });
		 */

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_spotted_post, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		View focusView = null;
		boolean cancel = false;

		String publicationYearString = mBookPublicationDate.getText()
				.toString();
		Integer publicationYear = null;
		if (!TextUtils.isEmpty(publicationYearString)) {
			try {
				publicationYear = Integer.parseInt(publicationYearString);
			} catch (NumberFormatException e) {
				mBookPublicationDate
						.setError(getString(R.string.error_invalid_year));
				focusView = mBookPublicationDate;
				cancel = true;
			}
		} else {
			publicationYear = null;
		}
		String title = mBookTitle.getText().toString();
		if (TextUtils.isEmpty(title)) {
			mBookTitle.setError(getString(R.string.error_field_required));
			focusView = mBookTitle;
			cancel = true;
		}
		String authors = mBookAuthors.getText().toString();
		if(!authors.matches("[a-zA-Z]+(\\,[a-zA-Z]+)*")){
			mBookAuthors.setError(getString(R.string.error_field_authors));
			focusView = mBookAuthors;
			cancel = true;
		}
		if (TextUtils.isEmpty(authors)) {
			mBookAuthors.setError(getString(R.string.error_field_required));
			focusView = mBookAuthors;
			cancel = true;
		}
		
		String priceString = mBookPrice.getText().toString();
		if (TextUtils.isEmpty(priceString)) {
			mBookPrice.setError(getString(R.string.error_field_required));
			focusView = mBookPrice;
			cancel = true;
		} else {
			try {
				price = Double.parseDouble(priceString);
			} catch (NumberFormatException e) {
				mBookPrice.setError(getString(R.string.error_invalid_price));
				focusView = mBookPrice;
				cancel = true;
			}
		}

		if (cancel) {
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task
			ShowProgress.showProgress(true, mProgressViewFullScreen,
					mBookCreation, getApplicationContext());

			try {
				new CreateNewBookTask(mIsbnCode.getText().toString(), title,
						authors, price, mBookCondition.getText().toString(),
						publicationYear, mSpinnerFaculty.getSelectedItem()
								.toString(), mBookPublisher.getText()
								.toString()).execute();
			} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return super.onOptionsItemSelected(item);

	}

	// TODO Asynctask per chiamata a book api

	/**
	 * Represents an asynchronous task used to create a new post and send it to
	 * the server
	 */

	public class CreateNewBookTask extends AsyncTask<Void, Void, Boolean> {

		private long mUserId;
		private final String isbnCode;
		private final String title;
		private final String authors;
		private final Double price;
		private final String publisher;
		private final Integer publication_date;
		private final String condition;
		private final String faculty;

		SecondHandBook newBook;

		CreateNewBookTask(String isbnCode, String title, String authors,
				Double price, String condition, Integer publicationYear,
				String faculty, String publisher)
				throws NoSuchAlgorithmException, UnsupportedEncodingException {
			this.isbnCode = isbnCode;
			this.authors = authors;
			this.price = price;
			this.title = title;
			this.condition = condition;
			this.publication_date = publicationYear;
			this.faculty = faculty;
			this.publisher = publisher;
		}

		@Override
		protected Boolean doInBackground(Void... params) {

			newBook = new SecondHandBook();
			newBook.setIsbn(isbnCode);
			newBook.setTitle(title);
			ArrayList<String> authorsArray = new ArrayList<String>();
			StringTokenizer tokenizer = new StringTokenizer(authors,",");
			while(tokenizer.hasMoreElements()){
				authorsArray.add((String) tokenizer.nextElement());
			}
			newBook.setAuthorsBook(authorsArray);
			newBook.setPrice(price);
			newBook.setText(condition);
			newBook.setPublishedDate(publication_date);
			newBook.setPublisher(publisher);
			newBook.setFaculty(faculty);
			newBook.setHavePicture(false);
			
			String id = sessionManager.getUserDetails().get(
					SessionManager.KEY_USERID);
			mUserId = Long.valueOf(id);
			newBook.setUserId(mUserId);
			Calendar calendar = Calendar.getInstance();
			Date now = calendar.getTime();
			newBook.setTimestamp(new DateTime(now));

			Secondhandbookendpoint.Builder builder = new Secondhandbookendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
					null);

			builder = CloudEndpointUtils.updateBuilder(builder);

			Secondhandbookendpoint endpoint = builder.setApplicationName(
					"polimisocial").build();

			try {
				newBook = endpoint.insertSecondHandBook(newBook).execute();
			} catch (IOException e2) {
				System.out.println(e2.getMessage());
				return false;
			}

			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			ShowProgress.showProgress(false, mProgressViewFullScreen,
					mBookCreation, getApplicationContext());
			if (result) {
				Toast toast = Toast.makeText(getApplicationContext(),
						"DONE! New book created", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER_VERTICAL,
						Gravity.CENTER_HORIZONTAL, 0);
				toast.show();
				finish();
			} else {
				Toast toast = Toast.makeText(getApplicationContext(),
						"Can't perform operation. Please retry",
						Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER_VERTICAL,
						Gravity.CENTER_HORIZONTAL, 0);
				toast.show();
			}

		}

	}

	public class RetrieveBookInfo extends
			AsyncTask<String, Void, SecondHandBook> {

		@Override
		protected SecondHandBook doInBackground(String... params) {

			
			String isbn = params[0];
			
			SecondHandBook book = null;
			Secondhandbookendpoint.Builder builder = new Secondhandbookendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
					null);

			builder = CloudEndpointUtils.updateBuilder(builder);

			Secondhandbookendpoint endpoint = builder.setApplicationName(
					"polimisocial").build();
			try {
				book = endpoint.getBookInfoFromISBN(isbn).execute();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}

			return book;  
			
			/*
			HttpClient client = new DefaultHttpClient();
			 
			String baseUrl = "https://www.googleapis.com/books/v1/volumes?q=isbn:";
			StringBuilder urlBuilder = new StringBuilder(baseUrl);
			urlBuilder.append(isbn);
			urlBuilder.append("&country=IT");
			urlBuilder.append("&key=AIzaSyDbvAFfj1A5KLRrjPXiViT2DYdxC5fLIbo");
			String url = urlBuilder.toString();
			// System.out.println(url);
			String line;
			StringBuilder content = new StringBuilder();
			HttpURLConnection connection = null;
			SecondHandBook book = new SecondHandBook();
			try {
				 URL url2 = new URL(url);
	                connection = (HttpURLConnection) url2.openConnection();
	                connection.setRequestMethod("GET");
				 //HttpGet httpget = new HttpGet(url);
				 //HttpResponse httpResponse=client.execute(httpget);
				 //InputStream inputStream = httpResponse.getEntity().getContent();
				 BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				 while (null != (line = reader.readLine())) {
						content.append(line);
					}
				 System.out.println(content.toString());
				 
				JSONObject obj = (JSONObject) new JSONTokener(content.toString())
						.nextValue();
				//System.out.println(obj);
				if (!obj.has("items")) {
					return null;
				}
				JSONArray results = (JSONArray) obj.get("items");
				obj = (JSONObject) results.get(0);

				JSONObject infoBook = (JSONObject) obj.get("volumeInfo");
				String title = (String) infoBook.get("title");
				JSONArray authorsJson = (JSONArray) infoBook.get("authors");
				ArrayList<String> authors = new ArrayList<String>();
				for (int j = 0; j < authorsJson.length(); j++) {
					authors.add(authorsJson.getString(j));
				}
				if(infoBook.has("publisher")){
				String publisher = (String) infoBook.get("publisher");
				book.setPublisher(publisher);
				}
				String publishedDate = (String) infoBook.get("publishedDate");

				book.setAuthorsBook(authors);
				book.setTitle(title);
				
				book.setPublishedDate(Integer.valueOf(publishedDate));
				book.setIsbn(isbn);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (org.json.JSONException e) {
				e.printStackTrace();
			}
				return book;
			*/
		}

		@Override
		protected void onPostExecute(SecondHandBook result) {
			super.onPostExecute(result);
			Toast toast;
			if (result == null) {
				toast = Toast.makeText(getApplicationContext(),
						"Can't find book with this isbn.", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER_VERTICAL,
						Gravity.CENTER_HORIZONTAL, 0);
				toast.show();
			} else {

				mBookTitle.setText(result.getTitle());

				StringBuilder authors = new StringBuilder();
				if(result.getAuthorsBook()!=null){
				if (result.getAuthorsBook().size() > 1) {
					Iterator<String> iterator = result.getAuthorsBook()
							.iterator();
					authors.append(iterator.next());
					while (iterator.hasNext()) {
						authors.append("," + iterator.next());
					}
				} else {
					authors.append(result.getAuthorsBook().get(0));
				}
				}
				mBookAuthors.setText(authors.toString());
				System.out.println(result.getPublishedDate());
				mBookPublicationDate.setText(String.valueOf(result.getPublishedDate()));
				mBookPublisher.setText(result.getPublisher());
			}
		}

	}

}
