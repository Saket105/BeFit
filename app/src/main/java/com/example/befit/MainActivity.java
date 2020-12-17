package com.example.befit;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.befit.ui.DashboardFragment;
import com.example.befit.ui.HomeFragment;
import com.example.befit.ui.NotificationsFragment;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

    ChipNavigationBar chipNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        chipNavigationBar = findViewById(R.id.nav_view);
        chipNavigationBar.setItemSelected(R.id.navigation_home,true);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
        bottomMenu();

    }

    private void bottomMenu() {
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;
                switch (i){
                    case R.id.navigation_home:
                        fragment = new HomeFragment();
                        break;

                    case R.id.navigation_dashboard:
                        fragment = new DashboardFragment();
                        break;

                    case R.id.navigation_notifications:
                        fragment = new NotificationsFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            }
        });
    }

}