package nithra.tamil.matrimony;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import nithra.tamil.matrimony.cropimage.CropImage;
import nithra.tamil.matrimony.cropimage.CropImageView;
import nithra.tamil.matrimony.utils.MultipartUtility;
import nithra.tamil.matrimony.utils.SharedPreference;
import nithra.tamil.matrimony.utils.Utils;

public class Registration_Four extends AppCompatActivity {
    SQLiteDatabase mydatabase;
    SharedPreference sp;
    ImageView img_1, img_2, img_3, img_4;
    EditText about;
    TextView close_1, close_2, close_3, close_4, complete,skip;
    ArrayList<String> img_path = new ArrayList<>();
    public static ArrayList<String> img_path1 = new ArrayList<>();
    public static ArrayList<String> img_path2 = new ArrayList<>();
    public static ArrayList<String> img_path3 = new ArrayList<>();
    public static ArrayList<String> img_path4 = new ArrayList<>();
    int req_code;
    String status;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_four);
        getSupportActionBar().setTitle("Profile Registration (Step -4)");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#EF5350")));
        mydatabase = this.openOrCreateDatabase("matrimony", MODE_PRIVATE, null);
        sp = new SharedPreference();
        img_1 = findViewById(R.id.img_1);
        img_2 = findViewById(R.id.img_2);
        img_3 = findViewById(R.id.img_3);
        img_4 = findViewById(R.id.img_4);
        close_1 = findViewById(R.id.close_1);
        close_2 = findViewById(R.id.close_2);
        close_3 = findViewById(R.id.close_3);
        close_4 = findViewById(R.id.close_4);
        skip = findViewById(R.id.skip);
        complete = findViewById(R.id.buttonContinue);
        about = findViewById(R.id.about_your_self);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Registration_Four.this,Match_List.class);
                startActivity(in);
                finish();
            }
        });
        img_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image_pick(100);

            }
        });
        img_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image_pick(101);
            }
        });
        img_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image_pick(102);
            }
        });
        img_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image_pick(103);
            }
        });
        close_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              delete_img(1);
            }
        });
        close_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete_img(2);
            }
        });
        close_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete_img(3);
            }
        });
        close_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete_img(4);
            }
        });
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send_server();

            }
        });
        Cursor c = mydatabase.rawQuery("select * from profile_four where userid='" + sp.getString(Registration_Four.this, "user_id") + "'", null);
        System.out.println("ssss " + c.getCount());
        if (c.getCount() != 0) {
            c.moveToFirst();
            about.setText(c.getString(c.getColumnIndex("about")));
            System.out.println("ssss " + c.getString(c.getColumnIndex("about")));
        }
        c.close();
    }
public void delete_img(final int value){
    AlertDialog.Builder builder = new AlertDialog.Builder(Registration_Four.this);
    builder.setTitle("Nithra Matrimoney");
    builder.setMessage("Are you sure to delete this image");
    builder.setPositiveButton("Yes",
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(value==1){
                        img_1.setImageResource(R.drawable.pick_img);
                        close_1.setVisibility(View.GONE);
                        new File(img_path1.get(0)).delete();
                        img_path1.clear();
                    }
                    else if(value==2){
                        img_2.setImageResource(R.drawable.pick_img);
                        close_2.setVisibility(View.GONE);
                        new File(img_path2.get(0)).delete();
                        img_path2.clear();
                    }
                    else if(value==3){
                        img_3.setImageResource(R.drawable.pick_img);
                        close_3.setVisibility(View.GONE);
                        new File(img_path3.get(0)).delete();
                        img_path3.clear();
                    }
                    else if(value==4){
                        img_4.setImageResource(R.drawable.pick_img);
                        close_4.setVisibility(View.GONE);
                        new File(img_path4.get(0)).delete();
                        img_path4.clear();
                    }

                }
            });
    builder.setNegativeButton("No",
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
    builder.create().show();
}
    @SuppressLint("StaticFieldLeak")
    private void send_server() {
         final ProgressDialog dialog;
         dialog = new ProgressDialog(Registration_Four.this);
        dialog.setCancelable(false);
        dialog.setMessage("please wait.");
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                complete.setClickable(false);
                dialog.show();
                super.onPreExecute();
            }
            @SuppressLint("WrongThread")
            @Override
            protected Void doInBackground(Void... voids) {
                String responseUploadDocumentString = null;
                try {
                    String charset = "UTF-8";
                    MultipartUtility multipart = new MultipartUtility(Utils.URL, charset);
                    multipart.addFormField("action", "register");
                    multipart.addFormField("user_id", "" + sp.getString(Registration_Four.this, "user_id"));
                    multipart.addFormField("secondary[aboutus]", "" + about.getText().toString());
                    File myDir = null;
                    String root = Environment.getExternalStorageDirectory().toString();
                    myDir = new File(root + "/.Nithra_Matrimony");
                    myDir.mkdirs();
                    String[] child_image1 = myDir.list();
                    if (child_image1 != null) {
                        if (child_image1.length > 0) {
                            for (String s : child_image1) {
                                File file_image = new File(myDir, s);
                                multipart.addFilePart("photos[]", file_image);
                            }
                        } else {

                            multipart.addFormField("photos[]", "");
                        }
                    }
                    List<String> responseUploadDocument = multipart.finish();
                    System.out.println("SERVER REPLIED:" + responseUploadDocument);
                    for (String line : responseUploadDocument) {
                        responseUploadDocumentString = line;
                        System.out.println("==============line" + responseUploadDocumentString);
                        try {
                            JSONArray jArray = new JSONArray(responseUploadDocumentString);
                            JSONObject json_data = null;
                            System.out.println("Date_value:" + jArray.length());
                            for (int i = 0; i < jArray.length(); i++) {
                                json_data = jArray.getJSONObject(i);
                                status = json_data.getString("status");
                            }

                        } catch (JSONException e1) {
                            System.out.println("rrrr " + e1);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("==============www" + e);
                }
                System.out.println("==============" + responseUploadDocumentString);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                complete.setClickable(true);
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                if(status.equals("success")){
                    Cursor c1 = mydatabase.rawQuery("select * from profile_four where userid='"+sp.getString(Registration_Four.this,"user_id")+"'",null);
                    if(c1.getCount()==0){
                        mydatabase.execSQL("INSERT INTO profile_four(userid,about) " +
                                "values ('" + sp.getString(Registration_Four.this,"user_id") + "','" +about.getText() + "');");

                    }else{
                        mydatabase.execSQL("Update profile_four set about='"+about.getText()+"' where userid='"+ sp.getString(Registration_Four.this,"user_id") +"'");

                    }
                    c1.close();
                    File dir = new File(Environment.getExternalStorageDirectory()+"/.Nithra_Matrimony");
                    if (dir.isDirectory())
                    {
                        String[] children = dir.list();
                        if (children != null) {
                            for (String child : children) {
                                new File(dir, child).delete();
                            }
                        }
                    }
                    Toasty.normal(Registration_Four.this, "Step 4 Completed").show();
                    Intent in = new Intent(Registration_Four.this,Match_List.class);
                    startActivity(in);
                    finish();
                }

                super.onPostExecute(aVoid);
            }
        }.execute();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println(requestCode + " path " + requestCode);
        if ((requestCode == 100) || (requestCode == 101) || (requestCode == 102) || (requestCode == 103)) {
            Uri selectedImage = data.getData();
            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE = (requestCode + 100);
            CropImage.activity(selectedImage)
                    .start(this);
            System.out.println("path " + selectedImage);
        } else if (requestCode == 200) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                System.out.println("res " + resultUri);
                if (resultUri != null) {
                    img_1.setImageURI(resultUri);
                    close_1.setVisibility(View.VISIBLE);
                    //img_path1.add(resultUri.toString());
                    //img_1.setTag(resultUri.toString());
                } else {
                    close_1.setVisibility(View.GONE);
                }
            }
        } else if (requestCode == 201) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                System.out.println("res " + resultUri);
                if (resultUri != null) {
                    img_2.setImageURI(resultUri);
                    close_2.setVisibility(View.VISIBLE);
                    //  img_2.setTag(resultUri.toString());
                   // img_path2.add(resultUri.toString());
                } else {
                    close_2.setVisibility(View.GONE);
                }
            }

        } else if (requestCode == 202) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                System.out.println("res " + resultUri);
                if (resultUri != null) {
                    img_3.setImageURI(resultUri);
                    close_3.setVisibility(View.VISIBLE);
                    // img_3.setTag(resultUri.toString());
                   // img_path3.add(resultUri.toString());
                } else {
                    close_3.setVisibility(View.GONE);
                }
            }
        } else if (requestCode == 203) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                System.out.println("res " + resultUri);
                if (resultUri != null) {
                    img_4.setImageURI(resultUri);
                    close_4.setVisibility(View.VISIBLE);
                    // img_4.setTag(resultUri.toString());
                   // img_path4.add(resultUri.toString());
                } else {
                    close_4.setVisibility(View.GONE);
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);  // You MUST have this line to be here
        // so ImagePicker can work with fragment mode
    }

    public void image_pick(final int req_code1) {
        req_code=req_code1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(Registration_Four.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Utils.createFolder();
                CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE = (req_code + 100);
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(this);
            } else {
                if (sp.getString(Registration_Four.this, "permission").equals("yes")) {
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Registration_Four.this);
                    builder.setTitle("Nithra Matrimoney");
                    builder.setMessage("To pick image you need to allow Storage Permission in the Settings area.");
                    builder.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                                    intent.setData(uri);
                                    startActivityForResult(intent,200);
                                }
                            });
                    builder.create().show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Registration_Four.this);
                    builder.setTitle("Nithra Matrimoney");
                    builder.setMessage("Allow the following permissions to pick the image");
                    builder.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
                                }
                            });

                    builder.create().show();

                }
            }
        } else {
            Utils.createFolder();
            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE = (req_code + 100);
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(this);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 200) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.i("", "Permission has been denied by user");
                Utils.createFolder();
                CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE = (req_code + 100);
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(this);
            } else {
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    boolean showRationale = false;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        showRationale = shouldShowRequestPermissionRationale(permissions[0]);
                    }
                    if (!showRationale) {
                        sp.putString(Registration_Four.this, "permission", "yes");
                    }
                }
            }
        }
    }
}
