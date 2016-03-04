package updatechecker.orgware.com.phonecalldismiss.config;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

/**
 * Created by Nanda on 04/03/16.
 */
public class MethodUtils {

    public static void showDialog(Context context){
        Dialog dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
    }

}
