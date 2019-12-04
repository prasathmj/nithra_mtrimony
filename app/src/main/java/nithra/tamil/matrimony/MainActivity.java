package nithra.tamil.matrimony;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;

import nithra.tamil.matrimony.utils.SharedPreference;

public class MainActivity extends AppCompatActivity {
    SharedPreference sp;
    SQLiteDatabase mydatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar myActionBar = getSupportActionBar();
        myActionBar.hide();
         sp= new SharedPreference();
        mydatabase = this.openOrCreateDatabase("matrimony", MODE_PRIVATE, null);
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS profile(id integer NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "userid VARCHAR,profile VARCHAR,name VARCHAR,gender VARCHAR,date VARCHAR,month VARCHAR,year VARCHAR," +
                "mother_tongue VARCHAR,religion VARCHAR,caste VARCHAR,mobile VARCHAR,mail VARCHAR," +
                "marital_status VARCHAR,gender_id VARCHAR,mother_tongue_id VARCHAR,caste_id VARCHAR,marital_status_id VARCHAR);");

        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS profile_two(id integer NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "userid VARCHAR,sub_caste VARCHAR,gothram VARCHAR,height VARCHAR,weight VARCHAR,body_type VARCHAR," +
                "complexion VARCHAR,any_disability VARCHAR,rasi VARCHAR,natchathiram VARCHAR,having_dhosam VARCHAR," +
                "dhosam VARCHAR,country VARCHAR,state VARCHAR,city VARCHAR,weight_id VARCHAR," +
                "body_type_id VARCHAR,complexion_id VARCHAR,any_disability_id VARCHAR,rasi_id VARCHAR,natchathiram_id VARCHAR," +
                "having_dhosam_id VARCHAR,dhosam_id VARCHAR,country_id VARCHAR,state_id VARCHAR,city_id VARCHAR,height_id VARCHAR);");

        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS profile_three(id integer NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "userid VARCHAR,eating_habit VARCHAR,drinking_habit VARCHAR,smoking_habit VARCHAR,education VARCHAR,occupation VARCHAR,employed VARCHAR," +
                "annual_income VARCHAR,family_status VARCHAR,family_type VARCHAR,family_value VARCHAR,father_name VARCHAR," +
                "father_status VARCHAR,mother_name VARCHAR,mother_status VARCHAR,married_brothers VARCHAR,unmarried_brothers VARCHAR,married_sisters VARCHAR,unmarried_sisters VARCHAR,eating_habit_id VARCHAR," +
                "smoking_habit_id VARCHAR,drinking_habit_id VARCHAR,education_id VARCHAR,occupation_id VARCHAR,employed_id VARCHAR,annual_income_id VARCHAR," +
                "family_status_id VARCHAR,family_type_id VARCHAR,family_value_id VARCHAR,father_status_id VARCHAR,mother_status_id VARCHAR,married_brothers_id VARCHAR,unmarried_brothers_id VARCHAR,married_sisters_id VARCHAR,unmarried_sisters_id VARCHAR);");

        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS profile_four(id integer NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "userid VARCHAR,about VARCHAR);");


        Handler handel = new Handler();
        handel.postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                if(sp.getString(MainActivity.this,"otp_verify").equals("yes")){
                    if(sp.getString(MainActivity.this,"profile_verify").equals("yes")){
                        //PARTNER SHOWE SCREEN
                        Intent in = new Intent(MainActivity.this,Match_List.class);
                        startActivity(in);
                        finish();
                    }else{
                        //PROFILE REGISTER SCREEN
                        Intent in = new Intent(MainActivity.this,Registration.class);
                        startActivity(in);
                        finish();
                    }
                }else{
                    //MOBILE VERIFY SCREEN
                    Intent in = new Intent(MainActivity.this,Mobile_Number.class);
                    startActivity(in);
                    finish();
                }
            }
        }, 2000);



    }
}
