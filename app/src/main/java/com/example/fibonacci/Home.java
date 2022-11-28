package com.example.fibonacci;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.EmptyStackException;

public class Home extends AppCompatActivity {

    private final int ID_HOME = 1;
    private final int ID_DATE = 2;
    private final int ID_EXAM = 3;
    private final int ID_PROFILE = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homes);

        TextView textView = findViewById(R.id.nav_title);

        MeowBottomNavigation bottomNavigation = findViewById(R.id.bottom_nav);
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_HOME , R.drawable.ic_baseline_home_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_DATE , R.drawable.ic_baseline_date_range_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_EXAM, R.drawable.ic_baseline_class_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_PROFILE , R.drawable.ic_baseline_account_circle_24));

        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                String name;
                switch (item.getId()){
                    case ID_HOME:
                        startActivity(new Intent(getApplicationContext(),Home.class));
                        break;
                    case ID_DATE:
                        startActivity(new Intent(getApplicationContext(),Date.class));
                        break;
                    case ID_EXAM:
                        startActivity(new Intent(getApplicationContext(),Exam.class));
                        break;
                    case ID_PROFILE:
                        startActivity(new Intent(getApplicationContext(),Profile.class));
                        break;
                    default:
                        name = "";
                }
                    }
                });

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {

            }
        });

        bottomNavigation.show(ID_HOME , true);
    }
}