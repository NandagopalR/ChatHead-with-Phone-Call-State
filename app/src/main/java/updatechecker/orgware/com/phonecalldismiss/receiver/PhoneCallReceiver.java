package updatechecker.orgware.com.phonecalldismiss.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import updatechecker.orgware.com.phonecalldismiss.service.CallDisconnectService;

/**
 * Created by Nanda on 03/03/16.
 */
public class PhoneCallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
//                Toast.makeText(context, "Phone Is Ringing", Toast.LENGTH_LONG).show();
            }

            if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
//                Toast.makeText(context, "Call Recieved", Toast.LENGTH_LONG).show();
                context.startService(new Intent(context, CallDisconnectService.class));
            }

            if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                context.stopService(new Intent(context, CallDisconnectService.class));
//                Toast.makeText(context, "Phone Is Idle", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
