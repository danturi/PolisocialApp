package it.polimi.dima.polisocial.foursquare;
import it.polimi.dima.polisocial.foursquare.constants.Constants;

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
	public void searchVenues(@Named("ll") String ll) throws FoursquareApiException {
		
		//ll = "45.478126,9.227911";
		
		
		// First we need a initialize FoursquareApi. 
	    FoursquareApi foursquareApi = new FoursquareApi(Constants.CLIENT_ID, Constants.CLIENT_SECRET, Constants.CALLBACK_URL);
	    
	    // After client has been initialized we can make queries.
	    Result<VenuesSearchResult> result = foursquareApi.venuesSearch(ll, null, null, null, null, null, null, null, null, null, null, null, null);
	    
	    if (result.getMeta().getCode() == 200) {
	      // if query was ok we can finally we do something with the data
	      for (CompactVenue venue : result.getResult().getVenues()) {
	        // TODO: Do something we the data
	        System.out.println(venue.getName());
	      }
	    } else {
	      // TODO: Proper error handling
	      System.out.println("Error occured: ");
	      System.out.println("  code: " + result.getMeta().getCode());
	      System.out.println("  type: " + result.getMeta().getErrorType());
	      System.out.println("  detail: " + result.getMeta().getErrorDetail()); 
	    }
	  }
		
}
