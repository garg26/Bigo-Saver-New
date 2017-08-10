package simplifii.framework.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

/**
 * Created by nitin on 04/12/15.
 */
public class CartUpdateReceiver extends BroadcastReceiver {

    public ArrayList<CartAmountListener> listeners = new ArrayList<>();
    public static final String ACTION_CART_UPDATE = "CART_UPDATE";

    public CartUpdateReceiver(CartAmountListener listener) {
        this.listeners.add(listener);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION_CART_UPDATE)) {
            for (CartAmountListener listener : listeners) {
                listener.onReceive(intent);
            }
        }
    }

    public void removeListener(CartAmountListener listener){
        this.listeners.remove(listener);
    }

    public static interface CartAmountListener {
        public void onReceive(Intent intent);
    }
}
