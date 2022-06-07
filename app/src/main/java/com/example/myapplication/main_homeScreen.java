package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class main_homeScreen extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    private BottomNavigationView bottomNavigationView;
    private TextView wish;
    private Fragment selectorFragment;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home_screen);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        viewPager = findViewById(R.id.viewpager);
        wish = findViewById(R.id.Hello);
        Bundle extras = getIntent().getExtras();
        String username = extras.getString("username");
        String patientName = extras.getString("PatientName");
        Log.d("user",username);
        Log.d("patientUser",patientName);
        wish.setText("Hello, "+patientName);

        final ViewPager_Adapter adapter = new ViewPager_Adapter(getSupportFragmentManager(), main_homeScreen.this, 4,username,patientName);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch(position){
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.nav_search).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.nav_history).setChecked(true);
                        break;

                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.nav_account).setChecked(true);
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



    }


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            switch(menuItem.getItemId()){
                case R.id.nav_home:
                     viewPager.setCurrentItem(0);
                     break;
                case R.id.nav_search:
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.nav_history:
                    viewPager.setCurrentItem(2);
                    break;

                case R.id.nav_account:
                    viewPager.setCurrentItem(3);
                    break;

            }
        return true;
    }



//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch(item.getItemId()){
//
//                    case R.id.nav_home:
//                        selectorFragment = new HomeFragment();
//                        break;
//
//                    case R.id.nav_search:
//                        selectorFragment = new SearchFragment();
//                        break;
//
//                    case R.id.nav_history:
//                        selectorFragment = new HistoryFragment();
//                        break;
//
//                    case R.id.nav_pharmacy:
//                        selectorFragment = new PharmacyFragment();
//                        break;
//
//                    case R.id.nav_account:
//                        selectorFragment = new AccountFragment();
//                        break;
//                }
//
//                if(selectorFragment !=null){
//                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectorFragment).commit();
//                }
//
//                return true;
//
//            }
//        });

    }
