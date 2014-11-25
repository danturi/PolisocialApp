package it.polimi.dima.polisocial.endpoint;

import it.polimi.dima.polisocial.entity.EMF;
import it.polimi.dima.polisocial.entity.Rental;
import it.polimi.dima.polisocial.foursquare.constants.Constants;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JPACursorHelper;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.appengine.labs.repackaged.org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.mortbay.log.Log;

@Api(name = "rentalendpoint", namespace = @ApiNamespace(ownerDomain = "polimi.it", ownerName = "polimi.it", packagePath = "dima.polisocial.entity"))
public class RentalEndpoint {

	/**
	 * This method lists all the entities inserted in datastore. It uses HTTP
	 * GET method and paging support.
	 * 
	 * @return A CollectionResponse class containing the list of all entities
	 *         persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listRental")
	public CollectionResponse<Rental> listRental(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<Rental> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from Rental as Rental");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<Rental>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and
			// accomodate
			// for lazy fetch.
			for (Rental obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Rental> builder().setItems(execute)
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
	@ApiMethod(name = "getRental")
	public Rental getRental(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		Rental rental = null;
		try {
			rental = mgr.find(Rental.class, id);
		} finally {
			mgr.close();
		}
		return rental;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity
	 * already exists in the datastore, an exception is thrown. It uses HTTP
	 * POST method.
	 * 
	 * @param rental
	 *            the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertRental")
	public Rental insertRental(Rental rental) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsRental(rental)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.persist(rental);
		} finally {
			mgr.close();
		}
		return rental;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does
	 * not exist in the datastore, an exception is thrown. It uses HTTP PUT
	 * method.
	 * 
	 * @param rental
	 *            the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateRental")
	public Rental updateRental(Rental rental) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsRental(rental)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.merge(rental);
		} finally {
			mgr.close();
		}
		return rental;
	}

	/**
	 * This method removes the entity with primary key id. It uses HTTP DELETE
	 * method.
	 * 
	 * @param id
	 *            the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeRental")
	public void removeRental(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		try {
			Rental rental = mgr.find(Rental.class, id);
			mgr.remove(rental);
		} finally {
			mgr.close();
		}
	}

	/**
	 * This method is used for instert entity. It uses HTTP PUT
	 * method.
	 * 
	 * @param rental
	 *            the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "findLocationAndAddRental")
	public Rental findLocationAndAddRental(Rental rental) throws NotFoundException {

		// find coordinates from address via Google Web service
		String baseUrl = "https://maps.googleapis.com/maps/api/geocode/json?address=";
		StringBuilder urlBuilder = new StringBuilder(baseUrl);
		
		System.out.println(rental.getAddress().replaceAll("\\s",""));
		urlBuilder.append(rental.getAddress().replaceAll("\\s",""));
		urlBuilder.append("&key=" + Constants.GOOGLE_API_SERVER_KEY);
		String url = urlBuilder.toString();
		URL addressUrl;
		JSONObject obj = null;

		try {
			addressUrl = new URL(url);

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					addressUrl.openStream()));
			StringBuilder content = new StringBuilder();
			String line;
			while (null != (line = reader.readLine())) {
				content.append(line);
			}
			obj = (JSONObject) new JSONTokener(content.toString()).nextValue();
			JSONArray results = null;
			if (obj.get("status").equals("OK")) {
				results = (JSONArray) obj.get("results");
				obj = (JSONObject) results.get(0);
				JSONObject geometry =  (JSONObject) obj.get("geometry");
				JSONObject location = (JSONObject) geometry.get("location");
				Double lat = (Double) location.get("lat");
				Double lng = (Double) location.get("lng");
				
				rental.setLatitude(lat);
				rental.setLongitude(lng);
				insertRental(rental);
				
			} else {
				throw new NotFoundException("errore");
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return rental;

	}

	private boolean containsRental(Rental rental) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			if (rental.getId() == null)
				return false;
			Rental item = mgr.find(Rental.class, rental.getId());
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
