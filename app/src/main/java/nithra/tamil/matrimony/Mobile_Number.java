package nithra.tamil.matrimony;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import nithra.tamil.matrimony.utils.SharedPreference;
import nithra.tamil.matrimony.utils.Utils;


public class Mobile_Number extends AppCompatActivity {
    TextInputEditText mobile_number;
    TextView next;
    SharedPreference sp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mobile_screen);
        getSupportActionBar().hide();
        sp = new SharedPreference();
        mobile_number = findViewById(R.id.editTextPhone);
        next = findViewById(R.id.buttonContinue);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Utils.isNetworkAvailable(Mobile_Number.this)){
                    if ((mobile_number.length() != 0) && (mobile_number.length() == 10)) {
                        next.setClickable(false);
                        RequestQueue queue = Volley.newRequestQueue(Mobile_Number.this);
                        StringRequest postRequest = new StringRequest(Request.Method.POST, Utils.URL,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        // response
                                        // Log.d("Response", response);
                                        next.setClickable(true);
                                        System.out.println("rrrr " + response);
                                        try {
                                            JSONArray jArray = new JSONArray(response);
                                            JSONObject json_data = null;
                                            String status = "",otp="",user_id,user_name;
                                            System.out.println("Date_value:" + jArray.length());
                                            for (int i = 0; i < jArray.length(); i++) {
                                                json_data = jArray.getJSONObject(i);
                                                status = json_data.getString("status");
                                                otp = json_data.getString("otp");
                                                user_id = json_data.getString("user_id");
                                                user_name = json_data.getString("user_name");
                                                sp.putString(Mobile_Number.this,"user_id",user_id);
                                            }

                                            if(status.equals("new")){
                                                //OTP SCREEN
                                                Intent in = new Intent(Mobile_Number.this,Mobile_Otp.class);
                                                in.putExtra("otp",otp);
                                                in.putExtra("number",mobile_number.getText().toString());
                                                in.putExtra("profile","new_profile");
                                                startActivity(in);
                                                finish();
                                            }else  if(status.equals("exist")){
                                                //DETAILS SCREEN
                                                Intent in = new Intent(Mobile_Number.this,Mobile_Otp.class);
                                                in.putExtra("otp",otp);
                                                in.putExtra("number",mobile_number.getText().toString());
                                                in.putExtra("profile","exist_profile");
                                                startActivity(in);
                                                finish();
                                            }


                                        } catch (JSONException e1) {
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        System.out.println("rrrr " + error);
                                        next.setClickable(true);
                                    }
                                }
                        ) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("action", "login");
                                params.put("mobile1", mobile_number.getText().toString());

                                return params;
                            }
                        };
                        queue.add(postRequest);

                    } else {
                        Toasty.normal(Mobile_Number.this, "Please enter a valid phone number").show();
                    }
                }else{
                    Toasty.normal(Mobile_Number.this, "Check Internet Connection").show();
                }

            }
        });

    }


}
