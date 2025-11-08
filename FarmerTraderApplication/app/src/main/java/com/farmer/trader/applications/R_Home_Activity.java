package com.farmer.trader.applications;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;



public class R_Home_Activity extends AppCompatActivity {


    TableRow EatableType, HandcraftedType, FruitsType, VegetableType, GrainsType;
    LinearLayout AllType;
    TableRow CartOption, OrderOption, ReviewOption, FavoriteOption, ProfileOption, LogoutOption;
    double Lat = 0, Lng = 0;
    String UserId, UserType;
    SharedPreferences pref;
    String LanType = "";

    TextView AllTypeText, EatableTypeText, HandcraftedTypeText, FruitsTypeText, VegetableTypeText,
            GrainTypeText, ProfileOptionText, CartOptionText, OrderOptionText,
            ReviewOptionText, FavoriteOptionText, LogoutOptionText;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().hide();

        pref = getSharedPreferences("FarmerTrader", Context.MODE_PRIVATE);
        UserId = pref.getString("UserId", "");
        UserType = pref.getString("LoginType", "");
        LanType = pref.getString("LanType", "en");

        if (LanType.compareTo("en") == 0) {

            String languageToLoad = "en"; // your language
            Locale locale = new Locale(languageToLoad);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
            this.setContentView(R.layout.r_home_layout);


            init();

        } else if (LanType.compareTo("gu") == 0) {

            String languageToLoad = "gu"; // your language
            Locale locale = new Locale(languageToLoad);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
            this.setContentView(R.layout.r_home_layout);


            init();

        } else {
            setContentView(R.layout.r_home_layout);
            getSupportActionBar().hide();
            init();

        }

    }


    @SuppressLint("SetTextI18n")
    public void init() {

        EatableType = (TableRow) findViewById(R.id.eateable_type_btn);
        HandcraftedType = (TableRow) findViewById(R.id.handcrafted_type_btn);
        FruitsType = (TableRow) findViewById(R.id.fruits_type_btn);
        VegetableType = (TableRow) findViewById(R.id.vegetable_type_btn);
        GrainsType = (TableRow) findViewById(R.id.grains_type_btn);
        AllType = (LinearLayout) findViewById(R.id.all_type_btn);

        CartOption = (TableRow) findViewById(R.id.cart_btn);
        OrderOption = (TableRow) findViewById(R.id.order_btn);
        ReviewOption = (TableRow) findViewById(R.id.review_btn);
        FavoriteOption = (TableRow) findViewById(R.id.fav_btn);
        ProfileOption = (TableRow) findViewById(R.id.profile_btn);
        LogoutOption = (TableRow) findViewById(R.id.logout_btn);

        AllTypeText = (TextView) findViewById(R.id.all_type_text);
        EatableTypeText = (TextView) findViewById(R.id.eateable_type_text);
        HandcraftedTypeText = (TextView) findViewById(R.id.handcrafted_type_text);
        FruitsTypeText = (TextView) findViewById(R.id.fruits_type_text);
        VegetableTypeText = (TextView) findViewById(R.id.vegetable_type_text);
        GrainTypeText = (TextView) findViewById(R.id.grains_type_text);
        ProfileOptionText = (TextView) findViewById(R.id.profile_text);
        CartOptionText = (TextView) findViewById(R.id.cart_text);
        OrderOptionText = (TextView) findViewById(R.id.order_text);
        ReviewOptionText = (TextView) findViewById(R.id.review_text);
        FavoriteOptionText = (TextView) findViewById(R.id.fav_text);
        LogoutOptionText = (TextView) findViewById(R.id.logout_text);


        AllTypeText.setText(getResources().getString(R.string.AllType));
        EatableTypeText.setText("Medicines");
        HandcraftedTypeText.setText("Machinery");
        FruitsTypeText.setText(getResources().getString(R.string.FruitsType));
        VegetableTypeText.setText(getResources().getString(R.string.VegetableType));
        GrainTypeText.setText(getResources().getString(R.string.GrainType));
        ProfileOptionText.setText(getResources().getString(R.string.ProfileType));
        CartOptionText.setText(getResources().getString(R.string.CartType));
        OrderOptionText.setText(getResources().getString(R.string.OrderType));
        ReviewOptionText.setText(getResources().getString(R.string.ReviewType));
        FavoriteOptionText.setText(getResources().getString(R.string.FavoriteType));
        LogoutOptionText.setText(getResources().getString(R.string.LogoutType));


        CartOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(R_Home_Activity.this, R_Cart_Activity.class);
                startActivity(intent);
            }
        });

        OrderOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(R_Home_Activity.this, R_OrderList_Activity.class);
                startActivity(intent);
            }
        });


        ReviewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(R_Home_Activity.this, R_ReviewList_Activity.class);
                startActivity(intent);
            }
        });

        FavoriteOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(R_Home_Activity.this, R_FavouriteList_Activity.class);
                startActivity(intent);
            }
        });

        ProfileOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(R_Home_Activity.this, R_ProfileActivity.class);
                startActivity(intent);
            }
        });


        LogoutOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences pref = getSharedPreferences("FarmerTrader", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.apply();

                Intent intent = new Intent(R_Home_Activity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


        EatableType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GPS_Tracker gps_tracker = new GPS_Tracker(R_Home_Activity.this, R_Home_Activity.this);
                if (gps_tracker.canGetLocation()) {
                    Lat = gps_tracker.getLatitude();
                    Lng = gps_tracker.getLongitude();

                    if (Lat != 0 && Lng != 0) {

                        Intent intent = new Intent(R_Home_Activity.this, R_ProductList_Activity.class);
                        intent.putExtra("CategoryType", "Medicines");
                        startActivity(intent);

                    } else {
                        Toast.makeText(R_Home_Activity.this, "Determining Your cordinates,Click on Add Button Again", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(R_Home_Activity.this, "Enable Your GPS(Location)", Toast.LENGTH_SHORT).show();
                }


            }
        });

        HandcraftedType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                GPS_Tracker gps_tracker = new GPS_Tracker(R_Home_Activity.this, R_Home_Activity.this);
                if (gps_tracker.canGetLocation()) {
                    Lat = gps_tracker.getLatitude();
                    Lng = gps_tracker.getLongitude();

                    if (Lat != 0 && Lng != 0) {


                        Intent intent = new Intent(R_Home_Activity.this, R_ProductList_Activity.class);
                        intent.putExtra("CategoryType", "Machinery");
                        startActivity(intent);

                    } else {
                        Toast.makeText(R_Home_Activity.this, "Determining Your cordinates,Click on Add Button Again", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(R_Home_Activity.this, "Enable Your GPS(Location)", Toast.LENGTH_SHORT).show();
                }


            }
        });


        FruitsType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GPS_Tracker gps_tracker = new GPS_Tracker(R_Home_Activity.this, R_Home_Activity.this);
                if (gps_tracker.canGetLocation()) {
                    Lat = gps_tracker.getLatitude();
                    Lng = gps_tracker.getLongitude();

                    if (Lat != 0 && Lng != 0) {

                        Intent intent = new Intent(R_Home_Activity.this, R_ProductList_Activity.class);
                        intent.putExtra("CategoryType", "Fruits");
                        startActivity(intent);

                    } else {
                        Toast.makeText(R_Home_Activity.this, "Determining Your cordinates,Click on Add Button Again", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(R_Home_Activity.this, "Enable Your GPS(Location)", Toast.LENGTH_SHORT).show();
                }


            }
        });


        VegetableType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                GPS_Tracker gps_tracker = new GPS_Tracker(R_Home_Activity.this, R_Home_Activity.this);
                if (gps_tracker.canGetLocation()) {
                    Lat = gps_tracker.getLatitude();
                    Lng = gps_tracker.getLongitude();

                    if (Lat != 0 && Lng != 0) {

                        Intent intent = new Intent(R_Home_Activity.this, R_ProductList_Activity.class);
                        intent.putExtra("CategoryType", "Vegetable");
                        startActivity(intent);


                    } else {
                        Toast.makeText(R_Home_Activity.this, "Determining Your cordinates,Click on Add Button Again", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(R_Home_Activity.this, "Enable Your GPS(Location)", Toast.LENGTH_SHORT).show();
                }


            }
        });

        GrainsType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                GPS_Tracker gps_tracker = new GPS_Tracker(R_Home_Activity.this, R_Home_Activity.this);
                if (gps_tracker.canGetLocation()) {
                    Lat = gps_tracker.getLatitude();
                    Lng = gps_tracker.getLongitude();

                    if (Lat != 0 && Lng != 0) {

                        Intent intent = new Intent(R_Home_Activity.this, R_ProductList_Activity.class);
                        intent.putExtra("CategoryType", "Grains");
                        startActivity(intent);

                    } else {
                        Toast.makeText(R_Home_Activity.this, "Determining Your cordinates,Click on Add Button Again", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(R_Home_Activity.this, "Enable Your GPS(Location)", Toast.LENGTH_SHORT).show();
                }


            }
        });


        AllType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GPS_Tracker gps_tracker = new GPS_Tracker(R_Home_Activity.this, R_Home_Activity.this);
                if (gps_tracker.canGetLocation()) {
                    Lat = gps_tracker.getLatitude();
                    Lng = gps_tracker.getLongitude();

                    if (Lat != 0 && Lng != 0) {

                        Intent intent = new Intent(R_Home_Activity.this, R_ProductList_Activity.class);
                        intent.putExtra("CategoryType", "All");
                        startActivity(intent);

                    } else {
                        Toast.makeText(R_Home_Activity.this, "Determining Your cordinates,Click on Add Button Again", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(R_Home_Activity.this, "Enable Your GPS(Location)", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }


}
