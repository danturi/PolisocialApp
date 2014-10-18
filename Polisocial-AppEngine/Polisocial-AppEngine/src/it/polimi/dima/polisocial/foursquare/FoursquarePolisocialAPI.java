package it.polimi.dima.polisocial.foursquare;

import fi.foyt.foursquare.api.FoursquareApi;
import fi.foyt.foursquare.api.FoursquareApiException;
import fi.foyt.foursquare.api.Result;
import fi.foyt.foursquare.api.entities.Category;
import fi.foyt.foursquare.api.entities.CompactVenue;
import fi.foyt.foursquare.api.entities.VenuesSearchResult;
import it.polimi.dima.polisocial.ResponseObject;
import it.polimi.dima.polisocial.endpoint.PoliUserEndpoint;
import it.polimi.dima.polisocial.entity.PoliUser;
import it.polimi.dima.polisocial.foursquare.constants.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.logging.Logger;

import javax.annotation.Nullable;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.appengine.labs.repackaged.org.json.JSONTokener;

@Api(name = "foursquareendpoint", version = "v1", namespace = @ApiNamespace(ownerDomain = "polimi.it", ownerName = "polimi.it", packagePath = "dima.polisocial.foursquare"))
public class FoursquarePolisocialAPI {

	private static final Logger log = Logger
			.getLogger(FoursquarePolisocialAPI.class.getName());
	private PoliUserEndpoint endpointUser = new PoliUserEndpoint();

	@ApiMethod(name = "searchVenues")
	public ResponseObject searchVenues(@Named("ll") String ll) {

		Result<VenuesSearchResult> result = null;
		String exception = null;
		ResponseObject response = new ResponseObject();
		ArrayList<ArrayList<String>> venues = new ArrayList<ArrayList<String>>();
		try {
			result = searchVenuesRequest(ll);
		} catch (FoursquareApiException e) {
			// warning Eccezione...
			log.warning(e.getCause() + " " + e.getMessage());
		}
		if (result != null) {
			int codeResponse = result.getMeta().getCode();
			// tutto ok
			if (codeResponse == 200) {

				for (CompactVenue venue : result.getResult().getVenues()) {
					ArrayList<String> venueDetails = new ArrayList<String>();
					venueDetails.add(venue.getId());
					venueDetails.add(venue.getName());
					venueDetails.add(venue.getLocation().getLat() + ","
							+ venue.getLocation().getLng());
					venues.add(venueDetails);

				}

				response.setException(exception);
				response.setObject(venues);
			}
			// problemi
			if (codeResponse == 400)
				response.setException("Bad Request");
			if (codeResponse == 401)
				response.setException("Unauthorized");
			if (codeResponse == 403)
				response.setException("Forbidden");
			if (codeResponse == 404)
				response.setException("Not Found");
			if (codeResponse == 405)
				response.setException("Method Not Allowed");
			if (codeResponse == 500)
				response.setException("Internal Server Error");
			return response;
		} else {
			response.setException("Problem");
			return response;
		}
	}

	/**
	 * Riceve dal client il code inviato da Foursquare,crea la richiesta del
	 * token aggiungendo il ClientSecret parsa il Json di risposta, salvando il
	 * token nel database per poter essere riutilizzato.
	 * 
	 * @throws BadRequestException
	 * 
	 * **/
	@ApiMethod(name = "performTokenRequest", httpMethod = HttpMethod.GET)
	public void performTokenRequest(@Named("code") String code,
			@Named("userId") Long userId) throws BadRequestException {
		log.info("richiesta token");
		try {
			String baseUrl = "https://foursquare.com/oauth2/access_token?client_id=";
			StringBuilder urlBuilder = new StringBuilder(baseUrl);
			urlBuilder.append(Constants.CLIENT_ID);
			urlBuilder.append("&client_secret=" + Constants.CLIENT_SECRET);
			urlBuilder.append("&grant_type=authorization_code&code=" + code);
			String url = urlBuilder.toString();
			// log.info(url);
			PoliUser user = null;

			URL tokenUrl = new URL(url);
			// https://foursquare.com/oauth2/access_token?client_id=C0UAYKHQET5QIKKQY50WOMCR50BESMVBGAN1BR5NSEHJ4NKU&client_secret=2RYQF34SHI2IGOETK1RS4PYTCMBHXODSZXJMD1SH4WRKQGCD&grant_type=authorization_code&code=

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					tokenUrl.openStream()));
			JSONObject object = (JSONObject) new JSONTokener(reader.readLine())
					.nextValue();
			if (!object.has("access_token"))
				throw new BadRequestException("client code error");
			String token = object.getString("access_token");
			user = endpointUser.getPoliUser(userId);
			if (user != null) {
				user.setTokenFsq(token);
				// log.info(token);
				endpointUser.updatePoliUser(user);
			}

		} catch (MalformedURLException e) {
			log.warning(e.getMessage());
		} catch (IOException e) {
			log.warning(e.getMessage());
		} catch (JSONException e) {
			log.info(e.getCause().toString());
			log.info(e.getMessage());
		}

	}

	// aggiunge una venue a Foursquare utilizzando il token dell'utente
	@ApiMethod(name = "addVenueGPSInfo")
	public void addVenueGPSInfo(@Named("userId") Long userId,
			@Named("name") String name, @Nullable @Named("phone") String phone,
			@Named("coordinates") String coordinates,
			@Named("categoryId") String categoryId)
			throws UnauthorizedException {

		FoursquareApi foursquareApiWithAuth = new FoursquareApi(
				Constants.CLIENT_ID, Constants.CLIENT_SECRET,
				Constants.CALLBACK_URL2);
		PoliUser user = endpointUser.getPoliUser(userId);
		if (user.getTokenFsq() == null)
			throw new UnauthorizedException("Token error"); // http code == 401
		foursquareApiWithAuth.setoAuthToken(user.getTokenFsq());
		Boolean addressFound = true;
		ArrayList<String> arrayAddressInfo = new ArrayList<String>();

		try {
			arrayAddressInfo = findInfoAddress(coordinates);
		} catch (NotFoundException e) {
			e.printStackTrace();
			addressFound = false;
		}
		if (addressFound)
			try {
				foursquareApiWithAuth
						.venuesAdd(name, arrayAddressInfo.get(0), null,
								arrayAddressInfo.get(1),
								arrayAddressInfo.get(2),
								arrayAddressInfo.get(3), phone, coordinates,
								categoryId);
			} catch (FoursquareApiException e) {
				e.printStackTrace();
			}
	}

	/**
	 * 
	 * @param coordinates
	 *            from gps
	 * @return address,city,state,zip of the coordinates in that order
	 * @throws NotFoundException
	 */
	@ApiMethod(name = "findInfoAddress", httpMethod = HttpMethod.GET)
	public ArrayList<String> findInfoAddress(
			@Named("coordinates") String coordinates) throws NotFoundException {

		//String coordinates = "45.478178,9.228031";
		String address = null;
		String city = null;
		String state = null;
		String zip = null;
		ArrayList<String> arrayAddressInfo = new ArrayList<String>();

		// find address from coordinates via Google Web service
		String baseUrl = "https://maps.googleapis.com/maps/api/geocode/json?latlng=";
		StringBuilder urlBuilder = new StringBuilder(baseUrl);
		urlBuilder.append(coordinates);
		urlBuilder.append("&location_type=ROOFTOP&result_type=street_address");
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
				results = (JSONArray) obj.get("address_components");
				System.out.println(results);
				for (int i = 0; i < results.length(); i++) {
					obj = (JSONObject) results.get(i);
					if (obj.get("types").toString().contains("route"))
						address = (String) obj.get("short_name");
					if (obj.get("types").toString()
							.contains("administrative_area_level_2"))
						city = (String) obj.get("short_name");
					if (obj.get("types").toString().contains("country"))
						state = (String) obj.get("short_name");
					if (obj.get("types").toString().contains("postal_code"))
						zip = (String) obj.get("short_name");
				}

				arrayAddressInfo.add(address);
				arrayAddressInfo.add(city);
				arrayAddressInfo.add(state);
				arrayAddressInfo.add(zip);
			}

			else
				throw new NotFoundException("errore");

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return arrayAddressInfo;

	}

	/**
	 * @return Map di categorie venues Foursquare con coppia Nome/Id (ordinate
	 *         per nome)
	 * */
	@ApiMethod(name = "findVenuesCategories")
	public TreeMap<String, String> findVenuesCategories()
			throws NotFoundException {

		Result<Category[]> result;
		TreeMap<String, String> category = new TreeMap<String, String>();
		FoursquareApi foursquareApiNoAuth = new FoursquareApi(
				Constants.CLIENT_ID, Constants.CLIENT_SECRET,
				Constants.CALLBACK_URL2);
		try {
			result = foursquareApiNoAuth.venuesCategories();
			for (Category categ : result.getResult()) {
				if (categ.getName().equals("Food")) {
					category.put(categ.getName(), categ.getId());
					for (Category c : categ.getCategories())
						category.put(c.getName(), c.getId());
				}
			}

		} catch (FoursquareApiException e) {
			throw new NotFoundException("Not found venues");
		}

		return category;
	}

	/**
	 * 
	 * 
	 * @param origLat
	 * @param origLong
	 * @param listVenues
	 * @return
	 * @throws NotFoundException 
	 */
	@ApiMethod(name = "findDistanceAndWalkingDuration", httpMethod = HttpMethod.GET)
	public ResponseObject findDistanceAndWalkingDuration(
			@Named("origLat") Double origLat, @Named("origLong") Double origLong,
			@Named("venuesCoord") String venuesCoord) throws NotFoundException {

		//venuesCoord.add("45.478178,9.228031");
		//venuesCoord.add("45.478178,9.228031");
		ArrayList<String> venuesCoordArray = new ArrayList<String>();
		StringTokenizer tokenizer = new StringTokenizer(venuesCoord,",");
		while(tokenizer.hasMoreElements()){
			StringBuilder coord = new StringBuilder();
			coord.append((String) tokenizer.nextElement());
			coord.append(",");
			coord.append((String) tokenizer.nextElement());
			venuesCoordArray.add(coord.toString());
		}
		log.info(venuesCoordArray.toString());
		System.out.println(venuesCoordArray.toString());
		
		// find distance from coordinates via Google Web service
		String baseUrl = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=";
		StringBuilder urlBuilder = new StringBuilder(baseUrl);
		urlBuilder.append(origLat + "," + origLong);
		urlBuilder.append("&destinations=");
		
		 Iterator<String> iter = venuesCoordArray.iterator();
		 urlBuilder.append(iter.next());
		 while(iter.hasNext()){
			 log.info("ciclo");
			 try {
				 urlBuilder.append(URLEncoder.encode("|","UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			 urlBuilder.append(iter.next());
		 }

		//urlBuilder.append(venueCoord);
		urlBuilder.append("&mode=walking&language=en-EN&key="
				+ Constants.GOOGLE_API_SERVER_KEY);
		String url = urlBuilder.toString();
		System.out.println(url);
		URL addressUrl;
		JSONObject obj = null;
		ArrayList<ArrayList<String>> attributes = new ArrayList<ArrayList<String>>();
		
		ResponseObject response = new ResponseObject();
		String exception = null;

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
			System.out.println(obj);
			if (obj.get("status").equals("OK")) {
				results = (JSONArray) obj.get("rows");
				JSONObject elements = results.getJSONObject(0);
				
				String duration = "";
				String distance = "";
				Integer value =99999999;
				JSONArray element = (JSONArray) elements.get("elements");
				for (int i=0;i<element.length();i++){
					
					JSONObject myElem = (JSONObject) element.get(i);
					if (myElem.get("status").equals("OK")) {
						duration = myElem.getJSONObject("duration").getString("text");
								
						distance = myElem.getJSONObject("distance").getString("text");
						value = (Integer) myElem.getJSONObject("distance").get("value");
					}
					ArrayList<String> attr = new ArrayList<String>();
						attr.add(value.toString());
						attr.add(distance+"    "+duration+" by foot");
						attributes.add(attr);
					}
				
			
			} else {
				if(obj.has("error_message")){
					exception = obj.get("error_message").toString();
					log.warning(exception);
				}
				throw new NotFoundException(exception);
			}
			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info(attributes.toString()); 
		response.setException(exception);
		response.setObject(attributes);
		return response;

	}

	private Result<VenuesSearchResult> searchVenuesRequest(String coordinates)
			throws FoursquareApiException {

		// Coordinate Politecnico di Milano
		// ll = "45.478178,9.228031";
		FoursquareApi foursquareApiNoAuth = new FoursquareApi(
				Constants.CLIENT_ID, Constants.CLIENT_SECRET,
				Constants.CALLBACK_URL2);
		// Categorie cibo
		String categoryIds = "4bf58dd8d48988d143941735,52e81612bcbc57f1066b79f4,4bf58dd8d48988d16c941735,"
				+ "4bf58dd8d48988d16d941735,4bf58dd8d48988d16d941735,4bf58dd8d48988d1cb941735,4bf58dd8d48988d1ca941735,4bf58dd8d48988d1ca941735,"
				+ "4bf58dd8d48988d1bd941735";
		Result<VenuesSearchResult> result;
		result = foursquareApiNoAuth.venuesSearch(coordinates, null, null,
				null, null, null, "browse", categoryIds, null, null, null, 800,
				null);
		return result;

	}

}
