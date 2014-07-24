package it.polimi.dima.polisocial;


import it.polimi.dima.polisocial.foursquare.foursquareendpoint.Foursquareendpoint;

import java.io.IOException;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.foursquare.android.nativeoauth.FoursquareCancelException;
import com.foursquare.android.nativeoauth.FoursquareDenyException;
import com.foursquare.android.nativeoauth.FoursquareInvalidRequestException;
import com.foursquare.android.nativeoauth.FoursquareOAuth;
import com.foursquare.android.nativeoauth.FoursquareOAuthException;
import com.foursquare.android.nativeoauth.FoursquareUnsupportedVersionException;
import com.foursquare.android.nativeoauth.model.AuthCodeResponse;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;



public class OAuthAccessActivity extends FragmentActivity {

	private static final int REQUEST_CODE_FSQ_CONNECT = 200;
	private static final int REQUEST_CODE_FSQ_TOKEN_EXCHANGE = 201;

	private static final String CLIENT_ID ="C0UAYKHQET5QIKKQY50WOMCR50BESMVBGAN1BR5NSEHJ4NKU";

	private Intent intent;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_oauth_access);
		ensureUi();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_CODE_FSQ_CONNECT:
			onCompleteConnect(resultCode, data);
			break;

		case REQUEST_CODE_FSQ_TOKEN_EXCHANGE:
			onCompleteTokenExchange();
			break;

		default:
			super.onActivityResult(requestCode, resultCode, data);
		}
	}


	private void ensureUi() {

		TextView tvMessage = (TextView) findViewById(R.id.tvMessage);
		tvMessage.setVisibility(View.VISIBLE);

		intent = FoursquareOAuth.getConnectIntent(OAuthAccessActivity.this, CLIENT_ID);

		// If the device does not have the Foursquare app installed, we'd
		// get an intent back that would open the Play Store for download.
		// Otherwise we start the auth flow.
		if (FoursquareOAuth.isPlayStoreIntent(intent)) {
			toastMessage(OAuthAccessActivity.this, getString(R.string.app_not_installed_message));
			startActivity(intent);
		} else {
			startActivityForResult(intent, REQUEST_CODE_FSQ_CONNECT);
		}
	}
	
	private void onCompleteConnect(int resultCode, Intent data) {
        AuthCodeResponse codeResponse = FoursquareOAuth.getAuthCodeFromResult(resultCode, data);
        Exception exception = codeResponse.getException();
        
        if (exception == null) {
            // Success.
            String code = codeResponse.getCode();
            Foursquareendpoint.Builder builder = new Foursquareendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);
			builder = CloudEndpointUtils.updateBuilder(builder);
			Foursquareendpoint endpoint = builder.build();
            
            try {
				endpoint.performTokenRequest(code);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        } else {
            if (exception instanceof FoursquareCancelException) {
                // Cancel.
                toastMessage(this, "Canceled");

            } else if (exception instanceof FoursquareDenyException) {
                // Deny.
             toastMessage(this, "Denied");
                
            } else if (exception instanceof FoursquareOAuthException) {
                // OAuth error.
                String errorMessage = exception.getMessage();
                String errorCode = ((FoursquareOAuthException) exception).getErrorCode();
                toastMessage(this, errorMessage + " [" + errorCode + "]");
                
            } else if (exception instanceof FoursquareUnsupportedVersionException) {
                // Unsupported Fourquare app version on the device.
             toastError(this, exception);
                
            } else if (exception instanceof FoursquareInvalidRequestException) {
                // Invalid request.
             toastError(this, exception);
                
            } else {
                // Error.
             toastError(this, exception);
            }
        }
    }
    private void onCompleteTokenExchange() {
    }
    
 
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.oauth_access, menu);
			return true;
		}
		
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			// Handle action bar item clicks here. The action bar will
			// automatically handle clicks on the Home/Up button, so long
			// as you specify a parent activity in AndroidManifest.xml.
			int id = item.getItemId();
			if (id == R.id.action_settings) {
				return true;
			}
			return super.onOptionsItemSelected(item);
		}
		
		public static void toastMessage(Context context, String message) {
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
		}
		
		public static void toastError(Context context, Throwable t) {
			Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
		}
}
