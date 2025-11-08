package com.farmer.trader.applications;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.Locale;

import static java.security.AccessController.getContext;




public class LoginActivity extends AppCompatActivity {

    SharedPreferences pref;
    protected EditText EmailID, Password;
    protected Button SignIn, SignUp;
    protected RelativeLayout relativeLayout;
    Dialog mDialog;
    String LanType = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);


        mDialog = new Dialog(LoginActivity.this, R.style.AppTheme);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.setContentView(R.layout.circular_dialog);
        mDialog.setCancelable(false);

        stopService(new Intent(LoginActivity.this, BackgroundService.class));
        startService(new Intent(LoginActivity.this, BackgroundService.class));

        Boolean ans = weHavePermission();
        if (!ans) {
            requestforPermissionFirst();
        }

//        getSupportActionBar().hide();


        pref = getSharedPreferences("FarmerTrader", Context.MODE_PRIVATE);
        String userId_pref = pref.getString("LoginType", "");
        LanType = pref.getString("LanType", "en");

        if (userId_pref.compareTo("Farmer") == 0) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("position", 0);
            startActivity(intent);
            finish();

        } else if (userId_pref.compareTo("Retailer") == 0) {
            Intent intent = new Intent(LoginActivity.this, R_Home_Activity.class);
            startActivity(intent);
            finish();

        } else if (userId_pref.compareTo("MahilaUdyog") == 0) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("position", 0);
            startActivity(intent);
            finish();

        } else {
            setContentView(R.layout.login_activity);
            init();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        pref = getSharedPreferences("FarmerTrader", Context.MODE_PRIVATE);
        LanType = pref.getString("LanType", "en");
        init();
    }

    protected void init() {

        EmailID = (EditText) findViewById(R.id.loginUserName);
        Password = (EditText) findViewById(R.id.loginPassword);
        SignIn = (Button) findViewById(R.id.loginButton);
        SignUp = (Button) findViewById(R.id.signUp);

        relativeLayout = (RelativeLayout) findViewById(R.id.activity_login);


        SignUp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog();
                    }
                }
        );

        SignIn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (EmailID.getText().toString().equals("")) {
//                                Snackbar.make(relativeLayout, "User Name is required", Snackbar.LENGTH_SHORT).show();
                            Snackbar.make(relativeLayout, getText(R.string.empty_user), Snackbar.LENGTH_SHORT).show();
                            EmailID.requestFocus();

                        } else if (Password.getText().toString().equals("")) {
                            Snackbar.make(relativeLayout, getText(R.string.empty_password), Snackbar.LENGTH_SHORT).show();
                            Password.requestFocus();
                        } else {

                            new logintask().execute(EmailID.getText().toString(), Password.getText().toString());
                        }


                    }
                }

        );

    }


    public void showDialog() {

        final Dialog dialog = new Dialog(LoginActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(false);
        dialog.setContentView(R.layout.registration_type_selection_dialog);

        TableRow MahilaUdyog = (TableRow) dialog.findViewById(R.id.mahila_udyog);
        TableRow Retailers = (TableRow) dialog.findViewById(R.id.retailer);
        TableRow Farmer = (TableRow) dialog.findViewById(R.id.farmer);


        //Mahila Udyog option
        MahilaUdyog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                intent.putExtra("RegistrationType", "MahilaUdyog");
                startActivity(intent);
                dialog.dismiss();
            }
        });


        // Retailers Option
        Retailers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                intent.putExtra("RegistrationType", "Retailer");
                startActivity(intent);
                dialog.dismiss();
            }
        });


        //Farmer Option
        Farmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                intent.putExtra("RegistrationType", "Farmer");
                startActivity(intent);
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    public class logintask extends AsyncTask<String, JSONObject, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String a = "back";
            RestAPI api = new RestAPI();
            try {
                JSONObject json = api.Login(params[0], params[1]);
                JSONPARSE jp = new JSONPARSE();
                a = jp.parse(json);
            } catch (Exception e) {
                a = e.getMessage();
            }
            return a;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mDialog.dismiss();

//            Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();

            if (s.contains("*")){
                String temp[] = s.split("\\*");

//                Toast.makeText(LoginActivity.this, temp[0], Toast.LENGTH_SHORT).show();

                if (temp[0].compareTo("Farmer") == 0) {

                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("UserId",EmailID.getText().toString().trim());
                    editor.putString("LoginType", temp[0]);
                    editor.putString("CertificateNo",temp[1]);
                    editor.apply();
                    editor.commit();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("position", 0);
                    startActivity(intent);
                    finish();

                } else if (temp[0].compareTo("Retailer") == 0) {

                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("UserId", EmailID.getText().toString());
                    editor.putString("LoginType", temp[0]);
                    editor.apply();
                    editor.commit();
                    Intent intent = new Intent(LoginActivity.this, R_Home_Activity.class);
                    startActivity(intent);
                    finish();

                } else if (temp[0].compareTo("MahilaUdyog") == 0) {


                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("UserId", EmailID.getText().toString());
                    editor.putString("LoginType", temp[0]);
                    editor.apply();
                    editor.commit();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("position", 0);
                    startActivity(intent);
                    finish();

                }


            } else if (s.compareTo("false") == 0) {
                Snackbar.make(relativeLayout, getText(R.string.Invalid_login), Snackbar.LENGTH_LONG).show();
                EmailID.setText("");
                Password.setText("");

            } else {
                if (s.contains("Unable to resolve host")) {
                    AlertDialog.Builder ad = new AlertDialog.Builder(LoginActivity.this);
                    ad.setTitle("Unable to Connect!");
                    ad.setMessage("Check your Internet Connection,Unable to connect the Server");
                    ad.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    ad.show();
                } else {
                    Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    //Android Runtime Permission
    private boolean weHavePermission() {
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestforPermissionFirst() {
        if ((ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) || (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) || (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) || (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) || (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA))) {
            requestForResultContactsPermission();
        } else {
            requestForResultContactsPermission();
        }
    }

    private void requestForResultContactsPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CAMERA}, 111);
    }

}



