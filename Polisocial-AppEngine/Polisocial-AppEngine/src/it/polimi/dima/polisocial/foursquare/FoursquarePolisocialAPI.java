package it.polimi.dima.polisocial.foursquare;
import it.polimi.dima.polisocial.foursquare.constants.Constants;

import java.util.ArrayList;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;

import fi.foyt.foursquare.api.FoursquareApi;
import fi.foyt.foursquare.api.FoursquareApiException;
import fi.foyt.foursquare.api.Result;
import fi.foyt.foursquare.api.entities.CompactVenue;
import fi.foyt.foursquare.api.entities.VenuesSearchResult;


@Api(name= "foursquareendpoint", version = "v1", namespace = @ApiNamespace(ownerDomain= "polimi.it", ownerName= "polimi.it", packagePath="dima.polisocial.foursquare"))
public class FoursquarePolisocialAPI {

	
	
	@ApiMethod(name = "searchVenues")
	public ArrayList<String> searchVenues(@Named("ll") String ll) throws FoursquareApiException {

		// Coordinate Politecnico di Milano
		// ll = "45.478178,9.228031";

		// Categorie cibo
		String categoryIds = "4bf58dd8d48988d143941735,52e81612bcbc57f1066b79f4,4bf58dd8d48988d16c941735,"
				+ "4bf58dd8d48988d16d941735,4bf58dd8d48988d16d941735,4bf58dd8d48988d1cb941735,4bf58dd8d48988d1ca941735,4bf58dd8d48988d1ca941735,"
				+ "4bf58dd8d48988d1bd941735";


		// First we need a initialize FoursquareApi. 
		FoursquareApi foursquareApi = new FoursquareApi(Constants.CLIENT_ID, Constants.CLIENT_SECRET, Constants.CALLBACK_URL);
		Result<VenuesSearchResult> result = foursquareApi.venuesSearch(ll, null, null, null, null, null, "browse",categoryIds, null, null, null,800 , null);
		ArrayList<String> venuesName = new ArrayList<String>();

		if (result.getMeta().getCode() == 200) {

			// if query was ok we can finally we do something with the data

			for (CompactVenue venue : result.getResult().getVenues())
				venuesName.add(venue.getName());
		return venuesName;
		} else {

			System.out.println("Error occured: ");
			System.out.println("  code: " + result.getMeta().getCode());
			System.out.println("  type: " + result.getMeta().getErrorType());
			System.out.println("  detail: " + result.getMeta().getErrorDetail()); 
			return venuesName;
		}
	}
	
	@ApiMethod(name="performTokenRequest")
	public void performTokenRequest(@Named("code") String code){
		System.out.println("richiesta token");
	
		
	}
	
	
	
}
