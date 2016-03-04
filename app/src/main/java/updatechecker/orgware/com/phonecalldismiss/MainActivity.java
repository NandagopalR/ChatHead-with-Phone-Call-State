package updatechecker.orgware.com.phonecalldismiss;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Method;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView mEnd;
    TelephonyManager tm;
    Method m1, m2, m3;
    Object iTelephony;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEnd = (TextView) findViewById(R.id.endCall);
        mEnd.setOnClickListener(this);
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
    }

    @Override
    public void onClick(View v) {
        try {
            m3.invoke(iTelephony);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //7708644237

}
