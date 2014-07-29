package it.polimi.dima.polisocial.foursquare;
import it.polimi.dima.polisocial.entity.StringObjectCollection;
import it.polimi.dima.polisocial.foursquare.constants.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.ws.http.HTTPException;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.appengine.labs.repackaged.org.json.JSONTokener;

import fi.foyt.foursquare.api.FoursquareApi;
import fi.foyt.foursquare.api.FoursquareApiException;
import fi.foyt.foursquare.api.Result;
import fi.foyt.foursquare.api.entities.CompactVenue;
import fi.foyt.foursquare.api.entities.VenuesSearchResult;


@Api(name= "foursquareendpoint", version = "v1", namespace = @ApiNamespace(ownerDomain= "polimi.it", ownerName= "polimi.it", packagePath="dima.polisocial.foursquare"))
public class FoursquarePolisocialAPI {

	
	private static final Logger log = Logger.getLogger(FoursquarePolisocialAPI.class.getName());
	private List<String> venuesName = new ArrayList<String>();
	
	@ApiMethod(name = "searchVenues")
	public StringObjectCollection searchVenues(@Named("ll")String ll) throws FoursquareApiException  {

		// Coordinate Politecnico di Milano
		 //ll = "45.478178,9.228031";

		// Categorie cibo
		String categoryIds = "4bf58dd8d48988d143941735,52e81612bcbc57f1066b79f4,4bf58dd8d48988d16c941735,"
				+ "4bf58dd8d48988d16d941735,4bf58dd8d48988d16d941735,4bf58dd8d48988d1cb941735,4bf58dd8d48988d1ca941735,4bf58dd8d48988d1ca941735,"
				+ "4bf58dd8d48988d1bd941735";
		String coordinates = ll;
		
		
		// First we need a initialize FoursquareApi. 
		FoursquareApi foursquareApi = new FoursquareApi(Constants.CLIENT_ID, Constants.CLIENT_SECRET, Constants.CALLBACK_URL2);
		Result<VenuesSearchResult> result;

			result = foursquareApi.venuesSearch(coordinates, null, null, null, null, null, "browse",categoryIds, null, null, null,800 , null);
			StringObjectCollection venuesListObj = new StringObjectCollection();

			if (result.getMeta().getCode() == 200) {

				// if query was ok we can finally we do something with the data

				for (CompactVenue venue : result.getResult().getVenues())
					venuesName.add(venue.getName());
			venuesListObj.setStringList(venuesName);
			return venuesListObj;
			} else {
				return venuesListObj;
			}
		

	}
	
	@ApiMethod(name= "performTokenRequest", httpMethod = HttpMethod.GET)
	public void performTokenRequest(@Named("code") String code){
		log.info("richiesta token");
		try {
			String baseUrl= "https://foursquare.com/oauth2/access_token?client_id=";
			StringBuilder urlBuilder = new StringBuilder(baseUrl);
			urlBuilder.append(Constants.CLIENT_ID);
			urlBuilder.append("&client_secret="+Constants.CLIENT_SECRET);
			urlBuilder.append("&grant_type=authorization_code&code="+code);
			String url = urlBuilder.toString();
			log.info(url);
			
			URL tokenUrl = new URL(url);
			//https://foursquare.com/oauth2/access_token?client_id=C0UAYKHQET5QIKKQY50WOMCR50BESMVBGAN1BR5NSEHJ4NKU&client_secret=2RYQF34SHI2IGOETK1RS4PYTCMBHXODSZXJMD1SH4WRKQGCD&grant_type=authorization_code&code=
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(tokenUrl.openStream()));
			JSONObject object = (JSONObject) new JSONTokener(reader.readLine()).nextValue();
			String token = object.getString("access_token");
			log.info(token);
			
		} catch (MalformedURLException e) {
			log.warning(e.getMessage());
		} catch (IOException e) {
			log.warning(e.getMessage());
		} catch (JSONException e) {
			log.info(e.getCause().toString());
			log.info(e.getMessage());
		}
		
		
	}
	
	@ApiMethod(name = "provaToken")
	public void provaToken() {
		log.info("prova");
	}
	
}
