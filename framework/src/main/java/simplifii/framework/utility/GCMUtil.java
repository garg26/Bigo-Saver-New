//package simplifii.framework.utility;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.widget.Toast;
//
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.GoogleApiAvailability;
//
//import simplifii.framework.receivers.RegistrationIntentService;
//
//public class GCMUtil {
//    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 101;
//    private Activity context;
//
//    public GCMUtil(Activity context) {
//        this.context = context;
//    }
//
//    public void startGCMService() {
//        if (checkPlayServices()) {
//            // Start IntentService to register this application with GCM.
//            Intent intent = new Intent(context, RegistrationIntentService.class);
//            context.startService(intent);
//        }
//    }
//
//    private boolean checkPlayServices() {
//        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
//        int resultCode = apiAvailability.isGooglePlayServicesAvailable(context);
//        if (resultCode != ConnectionResult.SUCCESS) {
//            if (apiAvailability.isUserResolvableError(resultCode)) {
//                apiAvailability.getErrorDialog(context, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
//                        .show();
//            } else {
//                Toast.makeText(context, "This device is not supported", Toast.LENGTH_LONG).show();
//            }
//            return false;
//        }
//        return true;
//    }
//}
