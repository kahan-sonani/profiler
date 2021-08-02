package com.reb3llion.profiler.presenter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.reb3llion.profiler.R;
import com.reb3llion.profiler.presenter.fragments.HelpFragment;


public class MainActivity extends AppCompatActivity {

    private HelpFragment helpFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.homeToolbar));
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemReselectedListener(item -> {
        });
        NavigationUI.setupWithNavController(bottomNavigationView,
                Navigation.findNavController(this, R.id.navHostFragment));
    }

    public void onHelp(MenuItem item) {
        if (item.getItemId() == R.id.menu_help)
            if (helpFragment == null)
                helpFragment = new HelpFragment();
        helpFragment.show(getSupportFragmentManager(), HelpFragment.TAG);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflow_singleactivity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.app_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        }
        return true;
    }
}