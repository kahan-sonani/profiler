  package com.reb3llion.profiler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.app.TimePickerDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.reb3llion.profiler.fragments.AddOrEditProfileFragment;
import com.reb3llion.profiler.fragments.HelpFragment;
import com.reb3llion.profiler.fragments.ListProfilesFragment;

import java.util.NavigableMap;
import java.util.Objects;


  public class SingleActivity extends AppCompatActivity {

      private HelpFragment helpFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

      @Override
      protected void onPostCreate(@Nullable Bundle savedInstanceState) {
          super.onPostCreate(savedInstanceState);
          if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
              setSupportActionBar(findViewById(R.id.homeToolbar));
              Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
              getSupportActionBar().setCustomView(R.layout.main_activity_app_bar_custom_layout);
          }

          NavController controller = Navigation.findNavController(this, R.id.navHostFragment);
          NavigationUI.setupWithNavController((BottomNavigationView) findViewById(R.id.bottom_navigation), controller);
          ((BottomNavigationView) findViewById(R.id.bottom_navigation)).setOnNavigationItemReselectedListener(item -> {});
      }

      public void onHelp(MenuItem item) {
        if(item.getItemId() == R.id.menu_help)
            if(helpFragment == null)
                helpFragment = new HelpFragment();
           helpFragment.show(getSupportFragmentManager(), HelpFragment.TAG);
      }
  }