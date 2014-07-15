package it.polimi.dima.polisocial.foursquare;
import it.polimi.dima.polisocial.foursquare.constants.Constants;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		//ll = "45.478178,9.228031";

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
	
	
	
	
	
	
	
	
	//@ApiMethod(name="foursquareAuthenticationRequest")
	public void authenticationRequest(HttpServletRequest request, HttpServletResponse response) {
		FoursquareApi foursquareApi = new FoursquareApi(Constants.CLIENT_ID, Constants.CLIENT_SECRET, Constants.CALLBACK_URL);
		try {
			// First we need to redirect our user to authentication page.
		
			response.sendRedirect(foursquareApi.getAuthenticationUrl());
		} catch (IOException e) {
			// TODO: Error handling
		}
	}
	//@ApiMethod(name="handleCallback")
	public void handleCallback(HttpServletRequest request,HttpServletResponse response) {
		// After user has logged in and confirmed that our program may access user's Foursquare account
		// Foursquare redirects user back to callback url.
		FoursquareApi foursquareApi = new FoursquareApi(Constants.CLIENT_ID, Constants.CLIENT_SECRET, Constants.CALLBACK_URL);
		// Callback url contains authorization code
		String code = request.getParameter("code");
		try {
			// finally we need to authenticate that authorization code
			foursquareApi.authenticateCode(code);
			// ... and voilà we have a authenticated Foursquare client
		} catch (FoursquareApiException e) {
			// TODO: Error handling
		}
	}
}
