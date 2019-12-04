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
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
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

public class Registration_Two extends AppCompatActivity implements View.OnClickListener {
    SQLiteDatabase mydatabase;
    SharedPreference sp;
    EditText sub_caste,gothram,edt_state,edt_city;
    TextView con,height,weight,body_type,complexion,any_disability,rasi,natchathiram,having_dhosam,dhosam,skip,
    country,state,city;
    LinearLayout liner_having_dosam;
    DrawerLayout drawer;
    RelativeLayout first,second;
    ListView listView,listView1;
    EditText search_bar,search_bar1;
    NavigationView navigationView2;
    ArrayList<String> data_list;
    ArrayList<String> data_id;
    ArrayAdapter<String> data_adapter;
    String json = null;
    View view_view;
    ArrayList<Integer> temp_id = new ArrayList<>();
    ArrayList<Integer> active = new ArrayList<>();
    ArrayList<Integer> crt_id = new ArrayList<>();
    String tes;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_two);
        getSupportActionBar().setTitle("Profile Registration (Step -2)");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#EF5350")));
        mydatabase = this.openOrCreateDatabase("matrimony", MODE_PRIVATE, null);
        sp = new SharedPreference();
        inisiation();
        try {
            InputStream is = Registration_Two.this.getAssets().open("master.json");
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
        data_id = new ArrayList<String>();

        height.setOnClickListener(Registration_Two.this);
        weight.setOnClickListener(Registration_Two.this);
        body_type.setOnClickListener(Registration_Two.this);
        complexion.setOnClickListener(Registration_Two.this);
        any_disability.setOnClickListener(Registration_Two.this);
        rasi.setOnClickListener(Registration_Two.this);
        natchathiram.setOnClickListener(Registration_Two.this);
        having_dhosam.setOnClickListener(Registration_Two.this);
        dhosam.setOnClickListener(Registration_Two.this);
        country.setOnClickListener(Registration_Two.this);
        state.setOnClickListener(Registration_Two.this);
        city.setOnClickListener(Registration_Two.this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Toasty.normal(Registration.this,""+temp_id.get((int)data_adapter.getItemId(i))).show();

                 if(view_view == findViewById(R.id.height)){
                    height.setText(data_adapter.getItem(i));
                    height.setTag(temp_id.get((int)data_adapter.getItemId(i)));
                }
                else if(view_view == findViewById(R.id.weight)){
                    weight.setText(data_adapter.getItem(i));
                    weight.setTag(temp_id.get((int)data_adapter.getItemId(i)));
                }
                else if(view_view == findViewById(R.id.body_type)){
                    body_type.setText(data_adapter.getItem(i));
                    body_type.setTag(temp_id.get((int)data_adapter.getItemId(i)));
                }
                else if(view_view == findViewById(R.id.complexion)){
                    complexion.setText(data_adapter.getItem(i));
                    complexion.setTag(temp_id.get((int)data_adapter.getItemId(i)));
                }
                else if(view_view == findViewById(R.id.any_disability)){
                    any_disability.setText(data_adapter.getItem(i));
                    any_disability.setTag(temp_id.get((int)data_adapter.getItemId(i)));
                }
                else if(view_view == findViewById(R.id.rasi)){
                    rasi.setText(data_adapter.getItem(i));
                    rasi.setTag(temp_id.get((int)data_adapter.getItemId(i)));
                }
                else if(view_view == findViewById(R.id.natchathiram)){
                    natchathiram.setText(data_adapter.getItem(i));
                    natchathiram.setTag(temp_id.get((int)data_adapter.getItemId(i)));
                }
                else if(view_view == findViewById(R.id.having_dhosam)){
                    having_dhosam.setText(data_adapter.getItem(i));
                    having_dhosam.setTag(temp_id.get((int)data_adapter.getItemId(i)));
                    if(having_dhosam.getText().equals("Yes")){
                        liner_having_dosam.setVisibility(View.VISIBLE);
                    }else{
                        liner_having_dosam.setVisibility(View.GONE);
                        dhosam.setText("");
                        dhosam.setTag("");
                        active.clear();
                    }
                }
                else if(view_view == findViewById(R.id.country)){
                    country.setText(data_adapter.getItem(i));
                    country.setTag(temp_id.get((int)data_adapter.getItemId(i)));
                    if(country.getText().toString().equals("India")){
                        edt_city.setVisibility(View.GONE);
                        edt_state.setVisibility(View.GONE);
                        edt_city.setText("");
                        edt_state.setText("");
                        city.setVisibility(View.VISIBLE);
                        state.setVisibility(View.VISIBLE);
                    }else{
                        edt_city.setVisibility(View.VISIBLE);
                        edt_state.setVisibility(View.VISIBLE);
                        city.setVisibility(View.GONE);
                        state.setVisibility(View.GONE);
                        city.setText("");
                        state.setText("");
                        city.setTag("");
                        state.setTag("");
                    }

                }
                else if(view_view == findViewById(R.id.state)){
                    state.setText(data_adapter.getItem(i));
                    state.setTag(temp_id.get((int)data_adapter.getItemId(i)));
                }
                else if(view_view == findViewById(R.id.city)){
                    city.setText(data_adapter.getItem(i));
                    city.setTag(crt_id.get((int)data_adapter.getItemId(i)));
                }
               // Toasty.normal(Registration_Two.this,""+temp_id.get((int)data_adapter.getItemId(i))).show();

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
                data_adapter = new ArrayAdapter<String>(Registration_Two.this, android.R.layout.simple_spinner_dropdown_item, hai);
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
        search_bar1.addTextChangedListener(new TextWatcher() {
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
                data_adapter = new ArrayAdapter<String>(Registration_Two.this, android.R.layout.simple_list_item_multiple_choice, hai);
                listView1.setAdapter(data_adapter);


                System.out.println("iiiii "+active);
                System.out.println("iiiiikkk "+temp_id);
                for(int i=0;i< temp_id.size(); i++ )  {
                    for(int j=0;j<active.size();j++){
                        System.out.println(active.get(j)+" iiiiilll "+temp_id.get(i));
                        if(active.get(j).equals(temp_id.get(i))){
                            listView1.setItemChecked(i, true);
                        }
                    }
                }
             //   listView1.setItemChecked(0,true);
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
        listView1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //active.clear();
        String prefix="";
        StringBuilder temp = new StringBuilder();
        SparseBooleanArray sbArray = listView1.getCheckedItemPositions();
        for (int j = 0; j < sbArray.size(); j++) {
            int key = sbArray.keyAt(j);
            if (sbArray.get(key)){
                if(!active.contains(temp_id.get(sbArray.keyAt(j)))){
                    active.add(temp_id.get(sbArray.keyAt(j)));
                }
                temp.append(prefix);
                prefix = ", ";
                temp.append(listView1.getItemAtPosition(j).toString());

            }else{
                active.remove(temp_id.get(sbArray.keyAt(j)));
            }
           // Toasty.normal(Registration_Two.this,""+active).show();

        }
        dhosam.setText(temp);

    }
});

con.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        //Toasty.normal(Registration_Two.this,""+dhosam.getText().toString()).show();

if(having_dhosam.getText().equals("Yes")){
    if(dhosam.length()!=0){
        data_in();
    }else{
        Toasty.normal(Registration_Two.this,"Choose Dhosam").show();
    }
}else{
    data_in();
}


    }
});
skip.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        Intent in = new Intent(Registration_Two.this,Registration_three.class);
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
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
        body_type = findViewById(R.id.body_type);
        complexion = findViewById(R.id.complexion);
        any_disability = findViewById(R.id.any_disability);
        rasi = findViewById(R.id.rasi);
        natchathiram = findViewById(R.id.natchathiram);
        having_dhosam = findViewById(R.id.having_dhosam);
        dhosam = findViewById(R.id.dhosam);
        country = findViewById(R.id.country);
        state = findViewById(R.id.state);
        city = findViewById(R.id.city);
        sub_caste = findViewById(R.id.sub_caste);
        gothram = findViewById(R.id.gothram);
        edt_city = findViewById(R.id.edt_city);
        edt_state = findViewById(R.id.edt_state);
        liner_having_dosam = findViewById(R.id.liner_having_dosam);
        liner_having_dosam.setVisibility(View.GONE);
        skip = findViewById(R.id.skip
        );
    }
    @SuppressLint("StaticFieldLeak")
    public void assign_data_array(final String name){

        if(name.equals("city")) {
             tes = state.getTag().toString();
        }
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
                            if(name.equals("city")){
                                if(tes.equals(profile_json_data.getString("state"))){
                                    data_list.add(profile_json_data.getString("english"));
                                    crt_id.add(profile_json_data.getInt("id"));
                                    temp_id.add(profile_json_data.getInt("id"));
                                    System.out.println(" okokok ");
                                }
                            }else{
                                data_list.add(profile_json_data.getString("english"));
                                crt_id.add(profile_json_data.getInt("id"));
                                temp_id.add(profile_json_data.getInt("id"));
                                System.out.println(" state_tw " + tes);
                            }
                        }
                    }
                } catch (JSONException e1) {
                    System.out.println("error : " + e1);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if(name.equals("dosham")){
                    first.setVisibility(View.GONE);
                    second.setVisibility(View.VISIBLE);
                    data_adapter = new ArrayAdapter<String>(Registration_Two.this, android.R.layout.simple_list_item_multiple_choice, data_list);
                    listView1.setAdapter(data_adapter);
                    if(active.size()!=0) {
                        for (int i = 0; i < temp_id.size(); i++) {
                            for (int j = 0; j < active.size(); j++) {
                                System.out.println(active.get(j) + " iiiiilll " + temp_id.get(i));
                                if (active.get(j).equals(temp_id.get(i))) {
                                    listView1.setItemChecked(i, true);
                                }
                            }
                        }
                    }
                }else {
                    second.setVisibility(View.GONE);
                    first.setVisibility(View.VISIBLE);
                    data_adapter = new ArrayAdapter<String>(Registration_Two.this, android.R.layout.simple_spinner_dropdown_item, data_list);
                    listView.setAdapter(data_adapter);

                }
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
    public void onClick(View view) {
        view_view =view;
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view_view.getWindowToken(), 0);
        if(view == findViewById(R.id.marital_status)){
            assign_data_array("marital_status");
        }
        else if(view == findViewById(R.id.height)){
            assign_data_array("height");
        }
        else if(view == findViewById(R.id.weight)){
            assign_data_array("weight");
        }
        else if(view == findViewById(R.id.body_type)){
            assign_data_array("body_type");
        }
        else if(view == findViewById(R.id.complexion)){
            assign_data_array("complexion");
        }
        else if(view == findViewById(R.id.any_disability)){
            assign_data_array("disability");
        }
        else if(view == findViewById(R.id.rasi)){
            assign_data_array("rasi");
        }
        else if(view == findViewById(R.id.natchathiram)){
            assign_data_array("star");
        }
        else if(view == findViewById(R.id.having_dhosam)){
            assign_data_array("having_dosham");
        }
        else if(view == findViewById(R.id.dhosam)){
            assign_data_array("dosham");
        }
        else if(view == findViewById(R.id.country)){
            assign_data_array("country");
        }
        else if(view == findViewById(R.id.state)){
            if(country.length()!=0) {
                assign_data_array("state");
            }else{
                Toasty.normal(Registration_Two.this, "Please Select Country").show();
            }
        }
        else if(view == findViewById(R.id.city)){
            if(state.length()!=0) {
                assign_data_array("city");
            }else{
                Toasty.normal(Registration_Two.this, "Please Select State").show();
            }
        }



    }

    private void data_in() {
        con.setClickable(false);
        RequestQueue queue = Volley.newRequestQueue(Registration_Two.this);
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
                                Cursor c = mydatabase.rawQuery("select * from profile_two where userid='"+sp.getString(Registration_Two.this,"user_id")+"'",null);
                                if(c.getCount()==0){
                                    c.moveToFirst();
                                     String a1,a2;
                                    if(state.length()!=0){
                                       a1=""+state.getTag().toString();
                                       a2=""+city.getTag().toString();
                                    }else{
                                        a1=""+edt_state.getText().toString();
                                        a2=""+edt_city.getText().toString();
                                    }

                                     mydatabase.execSQL("INSERT INTO profile_two(userid,sub_caste,gothram,height,weight,body_type,complexion,any_disability,rasi,natchathiram,having_dhosam,dhosam" +
                                            ",country,state,city,height_id,weight_id,body_type_id,complexion_id,any_disability_id,rasi_id,natchathiram_id,having_dhosam_id,dhosam_id,country_id,state_id,city_id) " +
                                            "values ('" + sp.getString(Registration_Two.this,"user_id") + "'" +
                                             ",'" + sub_caste.getText().toString() + "'" +
                                             ",'" + gothram.getText().toString() + "'" +
                                             ",'" +height.getText().toString()+ "'" +
                                            ",'" +weight.getText().toString()+ "'," +
                                             "'" +body_type.getText().toString()+ "'," +
                                             "'" +complexion.getText().toString()+ "'," +
                                             "'" +any_disability.getText().toString()+ "'" +
                                            ",'" +rasi.getText().toString()+ "'" +
                                             ",'" +natchathiram.getText().toString()+ "'" +
                                             ",'" + having_dhosam.getText().toString() + "'" +
                                             ",'" + dhosam.getText().toString() + "'" +
                                            ",'" +country.getText().toString()+ "'" +
                                             ",'" +a1+ "','" + a2+ "'" +
                                             ",'" + height.getTag()+ "'" +
                                             ",'" + weight.getTag()+ "'" +
                                            ",'" +body_type.getTag()+ "'" +
                                             ",'" +complexion.getTag()+ "'" +
                                             ",'" + any_disability.getTag()+ "'" +
                                             ",'" + rasi.getTag()+ "'" +
                                             ",'" + natchathiram.getTag()+ "'" +
                                            ",'" +having_dhosam.getTag()+ "'" +
                                             ",'" +active.toString()+ "'" +
                                             ",'" + country.getTag() + "'" +
                                             ",'" + state.getTag() + "'" +
                                             ",'" + city.getTag()+ "');");



                                }
                                c.close();
                                Cursor c1 = mydatabase.rawQuery("select * from profile_four where userid='"+sp.getString(Registration_Two.this,"user_id")+"'",null);
                                if(c1.getCount()==0){
                                    mydatabase.execSQL("INSERT INTO profile_four(userid,about) " +
                                            "values ('" + sp.getString(Registration_Two.this,"user_id") + "','" +des + "');");

                                }else{
                                    mydatabase.execSQL("Update profile_four set about='"+des+"' where userid='"+ sp.getString(Registration_Two.this,"user_id") +"'");

                                }
                                c1.close();
                                Toasty.normal(Registration_Two.this, "Step 2 Completed").show();
                                sp.putString(Registration_Two.this,"register_one","yes");

                                Intent in = new Intent(Registration_Two.this,Registration_three.class);
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
                params.put("user_id", ""+sp.getString(Registration_Two.this,"user_id"));
                params.put("secondary[sub_caste]", ""+sub_caste.getText().toString());
                params.put("secondary[gothram]", ""+gothram.getText().toString());
                params.put("secondary[height]", ""+height.getText().toString());
                params.put("secondary[weight]", ""+weight.getTag());
                params.put("secondary[body_type]", ""+body_type.getTag());
                params.put("secondary[complexion]", ""+complexion.getTag());
                params.put("secondary[disability]", ""+any_disability.getTag());
                params.put("secondary[rasi]", ""+rasi.getTag());
                params.put("secondary[star]", ""+natchathiram.getTag());
                params.put("secondary[having_dosham]", ""+having_dhosam.getTag());
                params.put("secondary[dosham]", ""+active.toString());
                params.put("secondary[country]", ""+country.getTag());
                if(state.length()!=0){
                    params.put("secondary[state]", ""+state.getTag());
                    params.put("secondary[city]", ""+city.getTag());
                }else{
                    params.put("secondary[state]", ""+edt_state.getText().toString());
                    params.put("secondary[city]", ""+edt_city.getText().toString());
                }

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
        }else if(search_bar1.length()!=0){
            search_bar1.setText("");
        }
        else if (drawer.isDrawerOpen(GravityCompat.END)){
            drawer.closeDrawer(GravityCompat.END);
        }

        super.onBackPressed();
    }
}
