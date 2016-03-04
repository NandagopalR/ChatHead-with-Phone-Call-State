package updatechecker.orgware.com.phonecalldismiss.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import java.lang.reflect.Method;

import updatechecker.orgware.com.phonecalldismiss.R;

/**
 * Created by Nanda on 02/03/16.
 */
public class CallDisconnectService extends Service {
    public View myView;
    TelephonyManager tm;
    Method m1, m2, m3;
    Object iTelephony;
    int count = 1;
    WindowManager winManager;
    ImageView img;
    WindowManager.LayoutParams params;

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

            m1 = tm.getClass().getDeclaredMethod("getITelephony");
            m1.setAccessible(true);
            iTelephony = m1.invoke(tm);

            m2 = iTelephony.getClass().getDeclaredMethod("silenceRinger");
            m3 = iTelephony.getClass().getDeclaredMethod("endCall");

//            m2.invoke(iTelephony);
//            m3.invoke(iTelephony);
        } catch (Exception e) {
            e.printStackTrace();
        }

        winManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        img = new ImageView(this);
        img.setClickable(true);
        img.setFocusable(true);
        img.setFocusableInTouchMode(true);
        img.setImageDrawable(getResources()
                .getDrawable(R.mipmap.ic_launcher));

        params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.CENTER_VERTICAL | Gravity.LEFT;
        params.x = 0;
        params.y = 100;

        winManager.addView(img, params);

        img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    m3.invoke(iTelephony);
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                Intent in = new Intent(getApplicationContext(),
//                        CustomDialogActivity.class);
//                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(in);
            }
        });

        img.setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = params.x;
                        initialY = params.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        params.x = initialX
                                + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY
                                + (int) (event.getRawY() - initialTouchY);
                        winManager.updateViewLayout(img, params);
                        break;
                }
                return false;
            }
        });

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (img != null)
            winManager.removeView(img);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
