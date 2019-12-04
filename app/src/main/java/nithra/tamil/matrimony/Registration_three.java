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
import android.view.Menu;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import nithra.tamil.matrimony.utils.SharedPreference;
import nithra.tamil.matrimony.utils.Utils;

public class Registration_three extends AppCompatActivity implements  View.OnClickListener{
    SQLiteDatabase mydatabase;
    SharedPreference sp;
    NavigationView navigationView2;
    ArrayList<String> data_list;
    ArrayList<String> data_id;
    ArrayAdapter<String> data_adapter;
    String json = null;
    View view_view;
    ArrayList<Integer> temp_id = new ArrayList<>();
    ArrayList<Integer> crt_id = new ArrayList<>();
    DrawerLayout drawer;
    RelativeLayout first,second;
    ListView listView,listView1;
    EditText search_bar,search_bar1,father_name,mother_name;
    TextView skip,eating_habit,drinking_habit,smoking_habit,education,occupation,employed,annual_income,family_status,family_type,family_value,
    father_status,mother_status,brother_married,brother_unmarried,sister_married,sister_unmarried,con;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_three);
        getSupportActionBar().setTitle("Profile Registration (Step -3)");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#EF5350")));
        mydatabase = this.openOrCreateDatabase("matrimony", MODE_PRIVATE, null);
        sp = new SharedPreference();
        inisiation();
        try {
            InputStream is = Registration_three.this.getAssets().open("master.json");
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Toasty.normal(Registration.this,""+temp_id.get((int)data_adapter.getItemId(i))).show();

                if(view_view == findViewById(R.id.eating_habits)){
                    eating_habit.setText(data_adapter.getItem(i));
                    eating_habit.setTag(temp_id.get((int)data_adapter.getItemId(i)));
                }
                else if(view_view == findViewById(R.id.drinking_habits)){
                    drinking_habit.setText(data_adapter.getItem(i));
                    drinking_habit.setTag(temp_id.get((int)data_adapter.getItemId(i)));
                }
                else if(view_view == findViewById(R.id.smoking_habits)){
                    smoking_habit.setText(data_adapter.getItem(i));
                    smoking_habit.setTag(temp_id.get((int)data_adapter.getItemId(i)));
                }
                else if(view_view == findViewById(R.id.education)){
                    education.setText(data_adapter.getItem(i));
                    education.setTag(temp_id.get((int)data_adapter.getItemId(i)));
                }
                else if(view_view == findViewById(R.id.occupation)){
                    occupation.setText(data_adapter.getItem(i));
                    occupation.setTag(temp_id.get((int)data_adapter.getItemId(i)));
                }
                else if(view_view == findViewById(R.id.employed)){
                    employed.setText(data_adapter.getItem(i));
                    employed.setTag(temp_id.get((int)data_adapter.getItemId(i)));
                }
                else if(view_view == findViewById(R.id.annual_income)){
                    annual_income.setText(data_adapter.getItem(i));
                    annual_income.setTag(temp_id.get((int)data_adapter.getItemId(i)));
                }
                else if(view_view == findViewById(R.id.family_status)){
                    family_status.setText(data_adapter.getItem(i));
                    family_status.setTag(temp_id.get((int)data_adapter.getItemId(i)));
                }
                else if(view_view == findViewById(R.id.family_type)){
                    family_type.setText(data_adapter.getItem(i));
                    family_type.setTag(temp_id.get((int)data_adapter.getItemId(i)));
                }
                else if(view_view == findViewById(R.id.family_value)){
                    family_value.setText(data_adapter.getItem(i));
                    family_value.setTag(temp_id.get((int)data_adapter.getItemId(i)));
                }
                else if(view_view == findViewById(R.id.father_status)){
                    father_status.setText(data_adapter.getItem(i));
                    father_status.setTag(crt_id.get((int)data_adapter.getItemId(i)));
                }
                else if(view_view == findViewById(R.id.mother_status)){
                    mother_status.setText(data_adapter.getItem(i));
                    mother_status.setTag(crt_id.get((int)data_adapter.getItemId(i)));
                }
                else if(view_view == findViewById(R.id.broter_married)){
                    brother_married.setText(data_adapter.getItem(i));
                    brother_married.setTag(crt_id.get((int)data_adapter.getItemId(i)));
                }
                else if(view_view == findViewById(R.id.brother_unmarried)){
                    brother_unmarried.setText(data_adapter.getItem(i));
                    brother_unmarried.setTag(crt_id.get((int)data_adapter.getItemId(i)));
                }
                else if(view_view == findViewById(R.id.sister_married)){
                    sister_married.setText(data_adapter.getItem(i));
                    sister_married.setTag(crt_id.get((int)data_adapter.getItemId(i)));
                }
                else if(view_view == findViewById(R.id.sister_unmarried)){
                    sister_unmarried.setText(data_adapter.getItem(i));
                    sister_unmarried.setTag(crt_id.get((int)data_adapter.getItemId(i)));
                }
                 //Toasty.normal(Registration_three.this,""+temp_id.get((int)data_adapter.getItemId(i))).show();

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
                        System.out.println("iiiiikkk "+crt_id.get(i));
                    }
                }
                data_adapter = new ArrayAdapter<String>(Registration_three.this, android.R.layout.simple_spinner_dropdown_item, hai);
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
        con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toasty.normal(Registration_Two.this,""+dhosam.getText().toString()).show();
                data_in();
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent(Registration_three.this,Registration_Four.class);
                startActivity(in);
                finish();
            }
        });
    }

    private void inisiation() {
        drawer = findViewById(R.id.drawer_layout);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        navigationView2 = findViewById(R.id.nav_view2);
        first = findViewById(R.id.first);
        second = findViewById(R.id.second);
        con = findViewById(R.id.buttonContinue);
        listView = findViewById(R.id.list);
        listView1 = findViewById(R.id.list1);
        search_bar = findViewById(R.id.search_bar);
        search_bar1 = findViewById(R.id.search_bar1);
        eating_habit = findViewById(R.id.eating_habits);
        drinking_habit = findViewById(R.id.drinking_habits);
        smoking_habit = findViewById(R.id.smoking_habits);
        education = findViewById(R.id.education);
        occupation = findViewById(R.id.occupation);
        employed = findViewById(R.id.employed);
        annual_income = findViewById(R.id.annual_income);
        family_status = findViewById(R.id.family_status);
        family_type = findViewById(R.id.family_type);
        family_value = findViewById(R.id.family_value);
        father_name = findViewById(R.id.father_name);
        father_status = findViewById(R.id.father_status);
        mother_name = findViewById(R.id.mother_name);
        mother_status = findViewById(R.id.mother_status);
        brother_married = findViewById(R.id.broter_married);
        brother_unmarried = findViewById(R.id.brother_unmarried);
        sister_married = findViewById(R.id.sister_married);
        sister_unmarried = findViewById(R.id.sister_unmarried);
        skip = findViewById(R.id.skip);

        eating_habit.setTag("0");
        drinking_habit.setTag("0");
        smoking_habit.setTag("0");
        education.setTag("0");
        occupation.setTag("0");
        employed.setTag("0");
        annual_income.setTag("0");
        family_status.setTag("0");
        family_type.setTag("0");
        family_value.setTag("0");
        father_status.setTag("0");
        mother_status.setTag("0");
        brother_married.setTag("0");
        brother_unmarried.setTag("0");
        sister_married.setTag("0");
        sister_unmarried.setTag("0");

        data_list = new ArrayList<String>();
        data_id = new ArrayList<String>();
        eating_habit.setOnClickListener(this);
        drinking_habit.setOnClickListener(this);
        smoking_habit.setOnClickListener(this);
        education.setOnClickListener(this);
        occupation.setOnClickListener(this);
        employed.setOnClickListener(this);
        annual_income.setOnClickListener(this);
        family_status.setOnClickListener(this);
        family_type.setOnClickListener(this);
        family_value.setOnClickListener(this);
        father_status.setOnClickListener(this);
        mother_status.setOnClickListener(this);
        brother_married.setOnClickListener(this);
        brother_unmarried.setOnClickListener(this);
        sister_married.setOnClickListener(this);
        sister_unmarried.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        view_view =view;
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view_view.getWindowToken(), 0);
        if(view == findViewById(R.id.eating_habits)){
            assign_data_array("eating_habits");
        }
        else if(view == findViewById(R.id.drinking_habits)){
            assign_data_array("drinking_habits");
        }
        else if(view == findViewById(R.id.smoking_habits)){
            assign_data_array("smoking_habits");
        }
        else if(view == findViewById(R.id.education)){
            assign_data_array("qualification");
        }
        else if(view == findViewById(R.id.occupation)){
            assign_data_array("occupation");
        }
        else if(view == findViewById(R.id.employed)){
            assign_data_array("employed_in");
        }
        else if(view == findViewById(R.id.annual_income)){
            assign_data_array("annual_income");
        }
        else if(view == findViewById(R.id.family_status)){
            assign_data_array("family_status");
        }
        else if(view == findViewById(R.id.family_type)){
            assign_data_array("family_type");
        }
        else if(view == findViewById(R.id.family_value)){
            assign_data_array("family_value");
        }
        else if(view == findViewById(R.id.father_status)){
            assign_data_array("father_status");
        }
        else if(view == findViewById(R.id.mother_status)){
            assign_data_array("mother_status");
        }
        else if(view == findViewById(R.id.broter_married)){
            assign_data_array("married_brothers");
        }
        else if(view == findViewById(R.id.brother_unmarried)){
            assign_data_array("unmarried_brothers");
        }
        else if(view == findViewById(R.id.sister_married)){
            assign_data_array("married_sisters");
        }
        else if(view == findViewById(R.id.sister_unmarried)){
            assign_data_array("unmarried_sisters");
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

                    second.setVisibility(View.GONE);
                    first.setVisibility(View.VISIBLE);
                    data_adapter = new ArrayAdapter<String>(Registration_three.this, android.R.layout.simple_spinner_dropdown_item, data_list);
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
    private void data_in() {
        con.setClickable(false);
        RequestQueue queue = Volley.newRequestQueue(Registration_three.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, Utils.URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        con.setClickable(true);
                        System.out.println("Date_value:" + response);
                        try {
                            JSONArray jArray = new JSONArray(response);
                            JSONObject json_data = null;
                            String status = "",otp="",user_id,des="";
                            System.out.println("Date_value:" + jArray.length());
                            for (int i = 0; i < jArray.length(); i++) {
                                json_data = jArray.getJSONObject(i);
                                status = json_data.getString("status");
                                des = json_data.getString("description");
                                System.out.println("rrrr " + status);
                            }
                            if(status.equals("success")){
                                Cursor c = mydatabase.rawQuery("select * from profile_three where userid='"+sp.getString(Registration_three.this,"user_id")+"'",null);
                                if(c.getCount()==0){
                                    c.moveToFirst();

                                    mydatabase.execSQL("INSERT INTO profile_three(userid,eating_habit,drinking_habit,smoking_habit,education,occupation,employed,annual_income,family_status,family_type,family_value,father_name,father_status,mother_name,mother_status,married_brothers,unmarried_brothers,married_sisters,unmarried_sisters " +
                                            ",eating_habit_id,smoking_habit_id,drinking_habit_id,education_id,occupation_id,employed_id,annual_income_id,family_status_id ,family_type_id,family_value_id,father_status_id,mother_status_id,married_brothers_id,unmarried_brothers_id,married_sisters_id,unmarried_sisters_id) " +
                                            "values ('" + sp.getString(Registration_three.this,"user_id") + "','" + eating_habit.getText().toString() + "','" + drinking_habit.getText().toString() + "','" +smoking_habit.getText().toString()+ "'" +
                                            ",'" +education.getText().toString()+ "','" +occupation.getText().toString()+ "','" +employed.getText().toString()+ "','" +annual_income.getText().toString()+ "'" +
                                            ",'" +family_status.getText().toString()+ "','" +family_type.getText().toString()+ "','" + family_value.getText().toString() + "','" + father_name.getText().toString() + "'" +
                                            ",'" +father_status.getText().toString()+ "','" +mother_name.getText().toString()+ "','" + mother_status.getText().toString()+ "','" +brother_married.getText().toString()+ "','" +brother_unmarried.getText().toString()+ "','" +sister_married.getText().toString()+ "','" +sister_unmarried.getText().toString()+ "','" + eating_habit.getTag()+ "','" + drinking_habit.getTag()+ "'" +
                                            ",'" +smoking_habit.getTag()+ "','" +education.getTag()+ "','" + occupation.getTag() + "','" + employed.getTag()+ "','" + annual_income.getTag()+ "'" +
                                            ",'" +family_status.getTag()+ "','" +family_type.getTag()+ "','" + family_value.getTag() + "','" + father_status.getTag()+ "','" + mother_status.getTag()+ "'" +
                                            ",'" +brother_married.getTag()+ "','" +brother_unmarried.getTag()+ "','" + sister_married.getTag() + "','" + sister_unmarried.getTag() + "');");


                                }
                                c.close();
                                Cursor c1 = mydatabase.rawQuery("select * from profile_four where userid='"+sp.getString(Registration_three.this,"user_id")+"'",null);
                                if(c1.getCount()==0){
                                    mydatabase.execSQL("INSERT INTO profile_four(userid,about) " +
                                            "values ('" + sp.getString(Registration_three.this,"user_id") + "','" +des + "');");

                                }else{
                                    mydatabase.execSQL("Update profile_four set about='"+des+"' where userid='"+ sp.getString(Registration_three.this,"user_id") +"'");

                                }
                                c1.close();


                                Toasty.normal(Registration_three.this, "Register Done").show();
                                sp.putString(Registration_three.this,"register_one","yes");

                                Intent in = new Intent(Registration_three.this,Registration_Four.class);
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
                params.put("user_id", ""+sp.getString(Registration_three.this,"user_id"));
                params.put("secondary[eating_habits]", ""+eating_habit.getTag());
                params.put("secondary[drinking_habits]", ""+drinking_habit.getTag());
                params.put("secondary[smoking_habits]", ""+smoking_habit.getTag());
                params.put("secondary[qualification]", ""+education.getTag());
                params.put("secondary[occupation]", ""+occupation.getTag());
                params.put("secondary[employed_in]", ""+employed.getTag());
                params.put("secondary[annual_income]", ""+annual_income.getTag());
                params.put("secondary[family_status]", ""+family_status.getTag());
                params.put("secondary[family_type]", ""+family_type.getTag());
                params.put("secondary[family_value]", ""+family_value.getTag());
                params.put("secondary[father_name]", ""+father_name.getText().toString());
                params.put("secondary[father_status]", ""+father_status.getTag());
                params.put("secondary[mother_name]", ""+mother_name.getText().toString());
                params.put("secondary[mother_status]", ""+mother_status.getTag());
                params.put("secondary[married_brothers]", ""+brother_married.getTag());
                params.put("secondary[unmarried_brothers]", ""+brother_unmarried.getTag());
                params.put("secondary[married_sisters]", ""+sister_married.getTag());
                params.put("secondary[unmarried_sisters]", ""+sister_unmarried.getTag());

                return params;
            }
        };
        queue.add(postRequest);

       /*else{
            mydatabase.execSQL("UPDATE profile SET is_ins='1' where pac='"+ c.getString(c.getColumnIndex("pac"))+ "' ;");
        }*/

    }
    @Override
    public void onBackPressed() {
        if(search_bar.length()!=0){
            search_bar.setText("");
        }
        else if (drawer.isDrawerOpen(GravityCompat.END)){
            drawer.closeDrawer(GravityCompat.END);
        }

        super.onBackPressed();
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }*/
}
