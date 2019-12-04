package nithra.tamil.matrimony.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;

import java.io.File;

public class Utils {

    public static String URL ="http://192.168.57.27/matrimony/admin/api/data.php";
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connec = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connec != null) {
            activeNetworkInfo = connec.getActiveNetworkInfo();
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public static void createFolder() {
        String root = Environment.getExternalStorageDirectory().toString();
        File mydir = new File(root + "/.Nithra_Matrimony");
        if (!mydir.exists()) {
            mydir.mkdirs();
        }

    }
}
