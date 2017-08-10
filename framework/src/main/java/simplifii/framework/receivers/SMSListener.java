package simplifii.framework.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.regex.Pattern;

/**
 * Created by manish on 6/8/2015.
 */
public class SMSListener extends BroadcastReceiver {
    private static final String TAG = "SMSLIstener";
    private GenericLocalReceiver.DiscvrReceiver listener;
    private String otp;
    public static final Pattern CODE_PATTERN = Pattern.compile("\\d{6}");

    public SMSListener() {

    }

    public SMSListener(GenericLocalReceiver.DiscvrReceiver listener, String otp) {
        this.listener = listener;
        this.otp = otp;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Bundle bundle = intent.getExtras();           //---get the SMS message passed in---
            SmsMessage[] msgs = null;
            String msg_from;
            if (bundle != null) {
                //---retrieve the SMS message received---
                try {
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for (int i = 0; i < msgs.length; i++) {
                        msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                        String msgBody = msgs[i].getMessageBody().toLowerCase();
                        Log.d(TAG, "" + msgBody);
                        if (msgBody.toUpperCase().contains(this.otp)) {
                            if(this.listener != null){
                                intent.putExtra("otp", this.otp);
                                this.listener.onReceive(intent);
                            }
//                            Matcher m = CODE_PATTERN.matcher(msgBody);
//                            if (m.find()) {
//                                String activationCode = m.group(1);
//                                if(this.listener != null){
//                                    intent.putExtra("otp", activationCode);
//                                    this.listener.onReceive(intent);
//                                }
//                                Log.d(TAG, "Code:" + activationCode);
//                            }
                        }
                    }
                } catch (Exception e) {
                    Log.d(TAG, "Exception :" + e.getMessage());
                }
            }
        }
    }
}
