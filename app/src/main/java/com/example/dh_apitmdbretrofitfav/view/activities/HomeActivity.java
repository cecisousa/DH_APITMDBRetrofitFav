package com.example.dh_apitmdbretrofitfav.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;

import android.os.Bundle;

import com.example.dh_apitmdbretrofitfav.view.fragments.FavoriteFragment;
import com.example.dh_apitmdbretrofitfav.view.fragments.HomeFragment;
import com.example.dh_dh_apitmdbretrofitfav.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        replaceFragment(new HomeFragment());

        BottomNavigationView navigationView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_list, R.id.navigation_fav).build();

        navigationView.setOnNavigationItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();

            if (id == R.id.navigation_list) {
                replaceFragment(new HomeFragment());
            } else if (id == R.id.navigation_fav) {
                replaceFragment(new FavoriteFragment());
            }
            return true;
        });
    }

    public void replaceFragment (Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }
}
