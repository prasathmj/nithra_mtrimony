package nithra.tamil.matrimony;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import nithra.tamil.matrimony.utils.SharedPreference;
import nithra.tamil.matrimony.utils.Utils;

public class Registration extends AppCompatActivity implements View.OnClickListener {
    EditText name,email;
    TextView con,profile,mother,religin,caste,gender,date,month,year,mobile,marital_status;
    SQLiteDatabase mydatabase;
    SharedPreference sp;
    DrawerLayout drawer;
    ListView listView;
    RelativeLayout first,second;
    EditText search_bar;
    NavigationView navigationView2;
    ArrayList<String> data_list;
    ArrayAdapter<String> data_adapter;
    String json = null;
    View view_view;
    List<Integer> temp_id = new ArrayList<>();
    List<Integer> crt_id = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        getSupportActionBar().setTitle("Profile Registration (Step-1)");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#EF5350")));
        mydatabase = this.openOrCreateDatabase("matrimony", MODE_PRIVATE, null);
        sp = new SharedPreference();
        inisiation();
        click_function();
            try {
                InputStream is = Registration.this.getAssets().open("master.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");
                System.out.println("json "+json);
            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println("eeeee "+ex);
                            }

         data_list = new ArrayList<String>();
        religin.setText("Hindu");
        mobile.setText(sp.getString(Registration.this,"mobile_number"));
        profile.setOnClickListener(this);
        gender.setOnClickListener(this);
        mother.setOnClickListener(this);
        caste.setOnClickListener(this);
        date.setOnClickListener(this);
        month.setOnClickListener(this);
        year.setOnClickListener(this);
        marital_status.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               // Toasty.normal(Registration.this,""+temp_id.get((int)data_adapter.getItemId(i))).show();
                if(view_view == findViewById(R.id.profile)){
                    profile.setText(data_adapter.getItem(i));
                    profile.setTag(temp_id.get((int)data_adapter.getItemId(i)));
                }
                else if(view_view == findViewById(R.id.gender)){
                    gender.setText(data_adapter.getItem(i));
                    gender.setTag(temp_id.get((int)data_adapter.getItemId(i)));
                }
                else if(view_view == findViewById(R.id.marital_status)){
                    marital_status.setText(data_adapter.getItem(i));
                    marital_status.setTag(temp_id.get((int)data_adapter.getItemId(i)));
                }
                else if(view_view == findViewById(R.id.mother)){
                    mother.setText(data_adapter.getItem(i));
                    mother.setTag(temp_id.get((int)data_adapter.getItemId(i)));
                }
                else if(view_view == findViewById(R.id.caste)){
                    caste.setText(data_adapter.getItem(i));
                    caste.setTag(temp_id.get((int)data_adapter.getItemId(i)));
                }
                else if(view_view == findViewById(R.id.date)){
                    date.setText(data_adapter.getItem(i));
                    date.setTag(temp_id.get((int)data_adapter.getItemId(i)));
                }
                else if(view_view == findViewById(R.id.month)){
                    month.setText(data_adapter.getItem(i));
                    month.setTag(temp_id.get((int)data_adapter.getItemId(i)));
                }
                else if(view_view == findViewById(R.id.year)){
                    year.setText(data_adapter.getItem(i));
                    year.setTag(temp_id.get((int)data_adapter.getItemId(i)));
                }
                drawer.closeDrawer(GravityCompat.END);
                search_bar.setText("");
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(listView.getWindowToken(), 0);
            }
        });

        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                List<String> hai = new ArrayList<>();

                hai.clear();
                temp_id.clear();
                for (int i = 0; i < data_list.size(); i++) {
                    if (data_list.get(i).toUpperCase().contains(cs.toString().toUpperCase())) {
                        hai.add(data_list.get(i));
                        temp_id.add(crt_id.get(i));
                    }
                }
                data_adapter = new ArrayAdapter<String>(Registration.this, android.R.layout.simple_spinner_dropdown_item, hai);
                listView.setAdapter(data_adapter);
                //adapter1.getFilter().filter(cs);
            }

            @Override

            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,int arg3) {
                // TODO Auto-generated method stub
  }
@Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

    }

    private void click_function() {
        con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.isNetworkAvailable(Registration.this)) {
                if (profile.getText().length() != 0 && !profile.getText().equals("Not Specify")) {
                if (name.getText().length() != 0) {
                if (gender.getText().length() != 0 && !gender.getText().equals("Not Specify")) {
                                if (date.getText().length() != 0 && !date.getText().equals("Date")) {
                                    if (month.getText().length() != 0 && !month.getText().equals("Month")) {
                                        if (year.getText().length() != 0 && !year.getText().equals("Year")) {
                                            if ((email.getText().length() != 0 )&& (email.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+"))) {
                                                if ((mobile.length() != 0) && (mobile.length() == 10)) {
                                                    if (marital_status.getText().length() != 0 && !marital_status.getText().equals("Not Specify")) {
                                                        if (mother.getText().length() != 0 && !mother.getText().equals("Not Specify")) {
                                                            if (religin.getText().length() != 0 && !religin.getText().equals("Not Specify")) {
                                                                if (caste.getText().length() != 0 && !caste.getText().equals("Not Specify")) {
                                                                    //   Toasty.normal(Registration.this, "okkk").show();
                                                                    data_in();
                                                                } else {
                                                                    Toasty.normal(Registration.this, "Choose Caste").show();
                                                                }
                                                            } else {
                                                                Toasty.normal(Registration.this, "Choose Religion").show();
                                                            }
                                                        } else {
                                                            Toasty.normal(Registration.this, "Choose Mother Tongue").show();
                                                        }
                                                    }else{
                                                        Toasty.normal(Registration.this, "Choose Marital Status").show();
                                                    }
                                                }
                                                else {
                                                    Toasty.normal(Registration.this, "Enter valid mobile number").show();
                                                }
                                            } else {
                                                Toasty.normal(Registration.this, "Enter valid Email Id").show();
                                            }
                                        } else {
                                            Toasty.normal(Registration.this, "Choose Year").show();
                                        }
                                    } else {
                                        Toasty.normal(Registration.this, "Choose Month").show();
                                    }
                                } else {
                                    Toasty.normal(Registration.this, "Choose Date").show();
                                }
                            } else {
                                Toasty.normal(Registration.this, "Choose Gender").show();
                            }
                        } else {
                            Toasty.normal(Registration.this, "Enter the Name").show();
                        }
                    } else {
                        Toasty.normal(Registration.this, "Choose Profile Created").show();
                    }
                } else {
                    Toasty.normal(Registration.this, "Check Internet Connection").show();
                }
            }
        });
    }

    private void data_in() {
        con.setClickable(false);
        RequestQueue queue = Volley.newRequestQueue(Registration.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, Utils.URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        con.setClickable(true);
                        System.out.println("Date_value:" + response);
                        try {
                            JSONArray jArray = new JSONArray(response);
                            JSONObject json_data = null;
                            String status = "",otp="",user_id,des = "";
                            System.out.println("Date_value:" + jArray.length());
                            for (int i = 0; i < jArray.length(); i++) {
                                json_data = jArray.getJSONObject(i);
                                status = json_data.getString("status");
                                des = json_data.getString("description");
                                System.out.println("rrrr " + status);
                            }
                            if(status.equals("success")){
                                Cursor c = mydatabase.rawQuery("select * from profile where userid='"+sp.getString(Registration.this,"user_id")+"'",null);
                                if(c.getCount()==0){
                                    c.moveToFirst();
                                    mydatabase.execSQL("INSERT INTO profile(userid,profile,name,gender,date,month,year,mother_tongue,religion,caste,mobile,mail,marital_status,gender_id,mother_tongue_id,caste_id,marital_status_id) " +
                                            "values ('" + sp.getString(Registration.this,"user_id") + "','" + profile.getText().toString() + "','" + name.getText().toString() + "','" +gender.getText().toString()+ "'" +
                                            ",'" +date.getText().toString()+ "','" +month.getText().toString()+ "','" +year.getText().toString()+ "','" +mother.getText().toString()+ "'" +
                                            ",'" +religin.getText().toString()+ "','" +caste.getText().toString()+ "','" + mobile.getText().toString() + "','" + email.getText().toString() + "','" + marital_status.getText().toString() + "'" +
                                            ",'" + gender.getTag().toString() + "','" + mother.getTag().toString() + "','" + caste.getTag().toString() + "','" + marital_status.getTag().toString() + "');");

                                }
                                c.close();
                                Cursor c1 = mydatabase.rawQuery("select * from profile_four where userid='"+sp.getString(Registration.this,"user_id")+"'",null);
                                if(c1.getCount()==0){
                                    mydatabase.execSQL("INSERT INTO profile_four(userid,about) " +
                                            "values ('" + sp.getString(Registration.this,"user_id") + "','" +des + "');");

                                }else{
                                    mydatabase.execSQL("Update profile_four set about='"+des+"' where userid='"+ sp.getString(Registration.this,"user_id") +"'");

                                }
                                c1.close();
                                Toasty.normal(Registration.this, "Register Done").show();
                                sp.putString(Registration.this,"profile_verify","yes");

                                Intent in = new Intent(Registration.this,Registration_Two.class);
                                startActivity(in);
                                finish();


                            }


                        } catch (JSONException e1) {
                            System.out.println("rrrr " + e1);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("rrrr " + error);
                        con.setClickable(true);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("action", "register");
                params.put("user_id", ""+sp.getString(Registration.this,"user_id"));
                params.put("primary[profile_for]", ""+profile.getTag().toString());
                params.put("primary[name]", ""+name.getText().toString());
                params.put("primary[gender]", ""+gender.getTag().toString());
                params.put("primary[dob]", year.getText()+"-"+month.getText()+"-"+date.getText());
                params.put("primary[email]", ""+email.getText().toString());
                params.put("primary[mobile1]", ""+mobile.getText().toString());
                params.put("primary[caste]", ""+caste.getTag().toString());
                params.put("primary[mother_tongue]", ""+mother.getTag().toString());
                params.put("primary[marital_status]", ""+marital_status.getTag().toString());
                return params;
            }
        };
        queue.add(postRequest);

       /*else{
            mydatabase.execSQL("UPDATE profile SET is_ins='1' where pac='"+ c.getString(c.getColumnIndex("pac"))+ "' ;");
        }*/

    }

    private void inisiation() {
        drawer = findViewById(R.id.drawer_layout);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        navigationView2 = findViewById(R.id.nav_view2);
        //navigationView2.setNavigationItemSelectedListener(this);
        first = findViewById(R.id.first);
        second = findViewById(R.id.second);
        listView = findViewById(R.id.list);
        search_bar = findViewById(R.id.search_bar);
        profile = findViewById(R.id.profile);
        date = findViewById(R.id.date);
        month = findViewById(R.id.month);
        year = findViewById(R.id.year);
        mother = findViewById(R.id.mother);
        religin = findViewById(R.id.religin);
        caste = findViewById(R.id.caste);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        mobile = findViewById(R.id.mobile);
        con = findViewById(R.id.buttonContinue);
        gender = findViewById(R.id.gender);
        marital_status = findViewById(R.id.marital_status);
    }

    @Override
    public void onClick(View view) {
        view_view =view;
        second.setVisibility(View.GONE);
        first.setVisibility(View.VISIBLE);
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view_view.getWindowToken(), 0);
        if(view == findViewById(R.id.profile)){
           assign_data_array("profile_for");
        }
        else if(view == findViewById(R.id.marital_status)){
            assign_data_array("marital_status");
        }
        else if(view == findViewById(R.id.mother)){
            assign_data_array("mother_tongue");
        }
        else if(view == findViewById(R.id.caste)){
            assign_data_array("caste");
        }
        else if(view == findViewById(R.id.gender)){
            assign_data_array("gender");
        }
        else if(view == findViewById(R.id.date)){
            //assign_data_array("gender");
            data_list.clear();
            temp_id.clear();
            crt_id.clear();
            for (int i = 1; i <= 31; i++) {
                if(Integer.toString(i).length()==1){
                    data_list.add("0"+i);
                }else {
                    data_list.add(""+i);
                }
                temp_id.add(i);
                crt_id.add(i);
            }
            data_adapter= new ArrayAdapter<String>(Registration.this,android.R.layout.simple_spinner_dropdown_item ,data_list);
            listView.setAdapter(data_adapter);
            if (drawer.isDrawerOpen(GravityCompat.END)) {
                drawer.closeDrawer(GravityCompat.END);
            } else {
                drawer.openDrawer(GravityCompat.END);
            }
        }
        else if(view == findViewById(R.id.month)){
            //assign_data_array("gender");
            data_list.clear();
            temp_id.clear();
            crt_id.clear();
            for (int i = 1; i <= 12; i++) {
                if(Integer.toString(i).length()==1){
                    data_list.add("0"+i);
                }else {
                    data_list.add(""+i);
                }
                temp_id.add(i);
                crt_id.add(i);
            }
            data_adapter= new ArrayAdapter<String>(Registration.this,android.R.layout.simple_spinner_dropdown_item ,data_list);
            listView.setAdapter(data_adapter);
            if (drawer.isDrawerOpen(GravityCompat.END)) {
                drawer.closeDrawer(GravityCompat.END);
            } else {
                drawer.openDrawer(GravityCompat.END);
            }
        }
        else if(view == findViewById(R.id.year)){
           // assign_data_array("gender");
            data_list.clear();
            temp_id.clear();
            crt_id.clear();
            int thisYear = Calendar.getInstance().get(Calendar.YEAR);
            for (int i = thisYear-18; i >= thisYear-70; i--) {
                data_list.add(Integer.toString(i));
                temp_id.add(i);
                crt_id.add(i);
            }
            data_adapter= new ArrayAdapter<String>(Registration.this,android.R.layout.simple_spinner_dropdown_item ,data_list);
            listView.setAdapter(data_adapter);
            if (drawer.isDrawerOpen(GravityCompat.END)) {
                drawer.closeDrawer(GravityCompat.END);
            } else {
                drawer.openDrawer(GravityCompat.END);
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    public void assign_data_array(final String name){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                data_list.clear();
                temp_id.clear();
                crt_id.clear();
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    JSONArray jArray = new JSONArray(json);
                    JSONObject json_data = null;
                    for (int i = 0; i < jArray.length(); i++) {
                        json_data = jArray.getJSONObject(i);
                        JSONArray profileArray = new JSONArray(json_data.getString(""+name));
                        for (int j = 1; j < profileArray.length(); j++) {
                            JSONObject profile_json_data = profileArray.getJSONObject(j);
                                data_list.add(profile_json_data.getString("english"));
                                crt_id.add(profile_json_data.getInt("id"));
                                temp_id.add(profile_json_data.getInt("id"));

                        }
                    }
                } catch (JSONException e1) {
                    System.out.println("error : " + e1);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                data_adapter= new ArrayAdapter<String>(Registration.this,android.R.layout.simple_spinner_dropdown_item ,data_list);
                listView.setAdapter(data_adapter);
                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                } else {
                    drawer.openDrawer(GravityCompat.END);
                }
                super.onPostExecute(aVoid);
            }
        }.execute();
    }

    @Override
    public void onBackPressed() {
        if(search_bar.length()!=0){
            search_bar.setText("");
        }else if (drawer.isDrawerOpen(GravityCompat.END)){
            drawer.closeDrawer(GravityCompat.END);
        }else{
            finish();
        }

    }
}
