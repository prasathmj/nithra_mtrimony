package nithra.tamil.matrimony;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import nithra.tamil.matrimony.utils.SharedPreference;
import nithra.tamil.matrimony.utils.Utils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Mobile_Otp extends AppCompatActivity implements OnOtpCompletionListener {
    ProgressDialog mProgress;
    String number;
    OtpView otpView;
    String otp_num,profile;
    TextView privacy;
    TextView resend;

    SharedPreference sp;
    TextView txt;
    TextView verify;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.mobile_otp);
        getSupportActionBar().hide();
        this.sp = new SharedPreference();
        this.verify = (TextView) findViewById(R.id.buttonSignIn);
        this.txt = (TextView) findViewById(R.id.txt);
        this.resend = (TextView) findViewById(R.id.resend);
        this.privacy = (TextView) findViewById(R.id.privacy);
        this.otpView = (OtpView) findViewById(R.id.otp_view);
        this.otpView.setOtpCompletionListener(this);
        Intent intent = getIntent();
        this.otp_num = intent.getStringExtra("otp");
        this.number = intent.getStringExtra("number");
        this.profile = intent.getStringExtra("profile");
        TextView textView = this.txt;
        StringBuilder sb = new StringBuilder();
        sb.append("6 Digit OTP has been sent to ");
        sb.append(this.number);
        sb.append(", Please enter it below");
        textView.setText(sb.toString());
        this.verify.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (Mobile_Otp.this.otp_num.equals(Mobile_Otp.this.otpView.getText().toString())) {
                    Toasty.success((Context) Mobile_Otp.this, (CharSequence) "OTP Verify").show();
                    Mobile_Otp.this.sp.putString(Mobile_Otp.this, "otp_verify", "yes");
                    SharedPreference sharedPreference = Mobile_Otp.this.sp;
                    Mobile_Otp mobile_Otp = Mobile_Otp.this;
                    StringBuilder sb = new StringBuilder();
                    sb.append("");
                    sb.append(Mobile_Otp.this.number);
                    sharedPreference.putString(mobile_Otp, "mobile_number", sb.toString());
                    if(profile.equals("new_profile")){
                        Mobile_Otp.this.startActivity(new Intent(Mobile_Otp.this, Registration.class));
                        Mobile_Otp.this.finish();
                    }else{
                        Mobile_Otp.this.startActivity(new Intent(Mobile_Otp.this, Registration.class));
                        Mobile_Otp.this.finish();
                    }

                    return;
                }
                Toasty.error((Context) Mobile_Otp.this, (CharSequence) "Please Enter Correct OTP").show();
            }
        });
        privacy.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(privacy.getWindowToken(), 0);
                }
                if (Utils.isNetworkAvailable(Mobile_Otp.this)) {
                    Mobile_Otp.this.loadUrlInWebViewprivacy("https://www.nithra.mobi/privacy.php");
                } else {
                    Toasty.normal((Context) Mobile_Otp.this, (CharSequence) "Check Internet Connection").show();
                }
            }
        });
        resend.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (Utils.isNetworkAvailable(Mobile_Otp.this)) {
                    Mobile_Otp.this.send_number();
                } else {
                    Toasty.normal((Context) Mobile_Otp.this, (CharSequence) "Check Internet Connection").show();
                }
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(resend.getWindowToken(), 0);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void send_number() {
        this.resend.setClickable(false);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest r1 = new StringRequest(1, Utils.URL, new Listener<String>() {
            public void onResponse(String response) {
                String str = "";
                Mobile_Otp.this.resend.setClickable(true);
                PrintStream printStream = System.out;
                StringBuilder sb = new StringBuilder();
                sb.append("rrrr ");
                sb.append(response);
                printStream.println(sb.toString());
                try {
                    JSONArray jArray = new JSONArray(response);
                    String str2 = str;
                    PrintStream printStream2 = System.out;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Date_value:");
                    sb2.append(jArray.length());
                    printStream2.println(sb2.toString());
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json_data = jArray.getJSONObject(i);
                        String status = json_data.getString(NotificationCompat.CATEGORY_STATUS);
                        Mobile_Otp.this.otp_num = json_data.getString("otp");
                        String string = json_data.getString("user_id");
                        String string2 = json_data.getString("user_name");
                    }
                } catch (JSONException e) {
                }
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                PrintStream printStream = System.out;
                StringBuilder sb = new StringBuilder();
                sb.append("rrrr ");
                sb.append(error);
                printStream.println(sb.toString());
                Mobile_Otp.this.resend.setClickable(true);
            }
        }) {
            /* access modifiers changed from: protected */
            public Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("action", "login");
                params.put("mobile1", Mobile_Otp.this.number);
                return params;
            }
        };
        queue.add(r1);
    }

    public void onOtpCompleted(String otp) {
        if (this.otp_num.equals(otp)) {
            Toasty.success((Context) this, (CharSequence) "OTP Verify").show();
            this.sp.putString(this, "otp_verify", "yes");
            SharedPreference sharedPreference = this.sp;
            StringBuilder sb = new StringBuilder();
            sb.append("");
            sb.append(this.number);
            sharedPreference.putString(this, "mobile_number", sb.toString());
            if(profile.equals("new_profile")){
                startActivity(new Intent(this, Registration.class));
                finish();
            }else{
                startActivity(new Intent(this, Registration.class));
                finish();
            }

            return;
        }
        Toasty.error((Context) this, (CharSequence) "Enter Valid OTP Number").show();
    }

    public void loadUrlInWebViewprivacy(String url) {
        Dialog urlDialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        urlDialog.setContentView(R.layout.common_web);
        urlDialog.getWindow().setLayout(-1, -1);
        WebView notesWebView = (WebView) urlDialog.findViewById(R.id.common_web);
        notesWebView.getSettings().setJavaScriptEnabled(true);
        notesWebView.loadUrl(url);
        notesWebView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Mobile_Otp mobile_Otp = Mobile_Otp.this;
                mobile_Otp.mProgress = ProgressDialog.show(mobile_Otp, null, "Loading please wait");
                Mobile_Otp.this.mProgress.setCancelable(true);
                super.onPageStarted(view, url, favicon);
            }

            public void onPageFinished(WebView view, String url) {
                Mobile_Otp.this.mProgress.dismiss();
                super.onPageFinished(view, url);
            }
        });
        urlDialog.setOnDismissListener(new OnDismissListener() {
            public void onDismiss(DialogInterface arg0) {
            }
        });
        urlDialog.show();
    }
}
