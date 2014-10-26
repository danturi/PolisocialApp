package it.polimi.dima.polisocial.endpoint;

import it.polimi.dima.polisocial.ResponseObject;
import it.polimi.dima.polisocial.entity.EMF;
import it.polimi.dima.polisocial.entity.SecondHandBook;
import it.polimi.dima.polisocial.foursquare.FoursquarePolisocialAPI;
import it.polimi.dima.polisocial.foursquare.constants.Constants;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.search.*;
import com.google.appengine.api.search.Document.Builder;
import com.google.appengine.datanucleus.query.JPACursorHelper;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.appengine.labs.repackaged.org.json.JSONTokener;
import com.google.gson.JsonArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Api(name = "secondhandbookendpoint", namespace = @ApiNamespace(ownerDomain = "polimi.it", ownerName = "polimi.it", packagePath = "dima.polisocial.entity"))
public class SecondHandBookEndpoint {

	
	private static final Logger log = Logger
			.getLogger(FoursquarePolisocialAPI.class.getName());
	/**
	 * This method lists all the entities inserted in datastore. It uses HTTP
	 * GET method and paging support.
	 * 
	 * @return A CollectionResponse class containing the list of all entities
	 *         persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listSecondHandBook")
	public CollectionResponse<SecondHandBook> listSecondHandBook(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<SecondHandBook> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr
					.createQuery("select from SecondHandBook as SecondHandBook");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<SecondHandBook>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and
			// accomodate
			// for lazy fetch.
			for (SecondHandBook obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<SecondHandBook> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET
	 * method.
	 * 
	 * @param id
	 *            the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getSecondHandBook")
	public SecondHandBook getSecondHandBook(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		SecondHandBook secondhandbook = null;
		try {
			secondhandbook = mgr.find(SecondHandBook.class, id);
		} finally {
			mgr.close();
		}
		return secondhandbook;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity
	 * already exists in the datastore, an exception is thrown. It uses HTTP
	 * secondhandbook method.
	 * 
	 * @param secondhandbook
	 *            the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertSecondHandBook")
	public SecondHandBook insertSecondHandBook(SecondHandBook secondhandbook) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsSecondHandBook(secondhandbook)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.persist(secondhandbook);
			addDocumentIndex(secondhandbook);
		} finally {
			mgr.close();
		}
		return secondhandbook;
	}

	private void addDocumentIndex(SecondHandBook secondhandbook) {

		Builder builder = Document
				.newBuilder()
				.setId(secondhandbook.getId().toString())
				.addField(
						Field.newBuilder().setName("bookTitle")
								.setText(secondhandbook.getBookTitle()))
				.addField(
						Field.newBuilder().setName("publisher")
								.setText(secondhandbook.getPublisher()))
				.addField(
						Field.newBuilder().setName("faculty")
								.setText(secondhandbook.getFaculty()))
				.addField(
						Field.newBuilder().setName("price")
								.setNumber(secondhandbook.getPrice()));

		for (String author : secondhandbook.getAuthorsBook()) {
			builder.addField(Field.newBuilder().setName("author")
					.setText(author));
		}

		Document document = builder.build();

		// creates an Index and saves the Document
		IndexSpec indexSpec = IndexSpec.newBuilder().setName("SecondHandBook")
				.build();
		Index index = SearchServiceFactory.getSearchService().getIndex(
				indexSpec);
		index.put(document);

	}

	@ApiMethod(name = "searchFullTextBook", path = "searchFullTextBook")
	public ResponseObject searchFullTextBook(
			@Named("title") String title,
			@Nullable @Named("author") String author,
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) throws NotFoundException {

		ResponseObject response = new ResponseObject();
		com.google.appengine.api.search.Cursor cursor;
		
		IndexSpec indexSpec = IndexSpec.newBuilder().setName("SecondHandBook")
				.build();
		Index index = SearchServiceFactory.getSearchService().getIndex(
				indexSpec);
		SortOptions sortOptions = SortOptions
				.newBuilder()
				.addSortExpression(
						SortExpression
								.newBuilder()
								.setExpression("price")
								.setDirection(
										SortExpression.SortDirection.ASCENDING)
								.setDefaultValueNumeric(99999)).build();
		try {
			if(cursorString != null && cursorString != ""){
				cursor = com.google.appengine.api.search.Cursor.newBuilder().build(cursorString);
			}else {
		    // create the initial cursor
				cursor = com.google.appengine.api.search.Cursor.newBuilder().build();
			}
		        // build options and query
		        QueryOptions options = QueryOptions.newBuilder()
		            .setCursor(cursor)
		            .setSortOptions(sortOptions)
		            .setLimit(20)
		            .build();
		        String queryString;
		        if(author!=null){
		        	queryString = "bookTitle: "+title+" AND author: "+author;
		        }else {
		        	queryString = "bookTitle: "+title;
		        }
		        com.google.appengine.api.search.Query query = com.google.appengine.api.search.Query.newBuilder().setOptions(options).build(queryString);
		        
		        // search at least once
		        Results<ScoredDocument> result = index.search(query);
		        int numberRetrieved = result.getNumberReturned();
		        cursor = result.getCursor();

		        if (numberRetrieved > 0) {
		        	log.info("book found");
		            // process the matched docs
		        	response.setObject(result.getResults());
		        	if(cursor!=null){
		        	//metto qui il cursore
		        	response.setException(cursor.toWebSafeString());
		        	}
		        }else {
		        	throw new NotFoundException("Not found book");
		        }
		    
		} catch (SearchException e) {
		    // handle exception...
			e.printStackTrace();
		}

		return response;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does
	 * not exist in the datastore, an exception is thrown. It uses HTTP PUT
	 * method.
	 * 
	 * @param secondhandbook
	 *            the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateSecondHandBook")
	public SecondHandBook updateSecondHandBook(SecondHandBook secondhandbook) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsSecondHandBook(secondhandbook)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(secondhandbook);
		} finally {
			mgr.close();
		}
		return secondhandbook;
	}

	/**
	 * This method removes the entity with primary key id. It uses HTTP DELETE
	 * method.
	 * 
	 * @param id
	 *            the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeSecondHandBook")
	public void removeSecondHandBook(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		try {
			SecondHandBook secondhandbook = mgr.find(SecondHandBook.class, id);
			mgr.remove(secondhandbook);
		} finally {
			mgr.close();
		}
	}

	@ApiMethod(name = "getBookInfoFromISBN", httpMethod = HttpMethod.GET, path = "getBookInfoFromISBN")
	public SecondHandBook getBookInfoFromISBN(@Named("isbn") String isbn)
			throws NotFoundException {

		String baseUrl = "https://www.googleapis.com/books/v1/volumes?q=isbn:";
		StringBuilder urlBuilder = new StringBuilder(baseUrl);
		urlBuilder.append(isbn);
		urlBuilder.append("&country=IT");
		urlBuilder.append("&key=" + Constants.GOOGLE_API_SERVER_KEY);
		String url = urlBuilder.toString();
		// System.out.println(url);
		URL addressUrl;
		String line;
		StringBuilder content = new StringBuilder();
		SecondHandBook book = new SecondHandBook();
		try {
			addressUrl = new URL(url);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					addressUrl.openStream()));

			while (null != (line = reader.readLine())) {
				content.append(line);
			}
			JSONObject obj = (JSONObject) new JSONTokener(content.toString())
					.nextValue();
			System.out.println(obj);
			if (!obj.has("items")) {
				throw new NotFoundException("Not found book");
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
			String publisher = (String) infoBook.get("publisher");
			String publishedDate = (String) infoBook.get("publishedDate");

			book.setAuthorsBook(authors);
			book.setBookTitle(title);
			book.setPublisher(publisher);
			book.setPublishedDate(new SimpleDateFormat("yyyy-MM-dd")
					.parse(publishedDate));
			book.setIsbn(isbn);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return book;
	}

	@ApiMethod(name = "searchBookByTitleOrAuthor")
	public CollectionResponse<SecondHandBook> searchBookByTitleOrAuthor(
			@Named("title") String title,
			@Nullable @Named("author") String author,
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<SecondHandBook> execute = null;

		try {
			mgr = getEntityManager();
			Query query;
			if (author == null) {
				query = mgr
						.createQuery("select s from SecondHandBook s where s.bookTitle like :string");
				query.setParameter("string", title + "%");
			} else {
				query = mgr
						.createQuery("select s from SecondHandBook s where s.bookTitle like :string and :author member of s.authorsBook");
				query.setParameter("string", "%" + title + "%");
				query.setParameter("author", author);
			}

			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<SecondHandBook>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and
			// accomodate
			// for lazy fetch.
			for (SecondHandBook obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<SecondHandBook> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	private boolean containsSecondHandBook(SecondHandBook secondhandbook) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			if (secondhandbook.getId() == null)
				return false;
			SecondHandBook item = mgr.find(SecondHandBook.class,
					secondhandbook.getId());
			if (item == null) {
				contains = false;
			}
		} finally {
			mgr.close();
		}
		return contains;
	}

	private static EntityManager getEntityManager() {
		return EMF.get().createEntityManager();
	}

}
