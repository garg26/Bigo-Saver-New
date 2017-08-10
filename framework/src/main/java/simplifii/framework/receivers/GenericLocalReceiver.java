package simplifii.framework.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by nitin on 09/02/16.
 */
public class GenericLocalReceiver extends BroadcastReceiver {
    private DiscvrReceiver listener;

    public GenericLocalReceiver(DiscvrReceiver listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null != listener) {
            listener.onReceive(intent);
        }
    }

    public static interface DiscvrReceiver {
        String ACTION_REFRESH_SERVICE_REQUESTS = "refresh_service_requests";

        public void onReceive(Intent intent);
    }

    public static void sendBroadcast(Context ctx, String action) {
        Intent i = new Intent(action);
        ctx.sendBroadcast(i);
    }
}
