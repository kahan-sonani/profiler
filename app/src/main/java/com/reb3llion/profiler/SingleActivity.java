  package com.reb3llion.profiler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.reb3llion.profiler.fragments.HelpFragment;

import java.util.Objects;


  public class SingleActivity extends AppCompatActivity {

      private MaterialToolbar toolbar;
      private HelpFragment helpFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.homeToolbar);
    }

      @Override
      protected void onPostCreate(@Nullable Bundle savedInstanceState) {
          super.onPostCreate(savedInstanceState);
          setSupportActionBar(toolbar);
          Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
          getSupportActionBar().setCustomView(R.layout.main_activity_app_bar_custom_layout);

          NavController controller = Navigation.findNavController(this, R.id.navHostFragment);
          NavigationUI.setupWithNavController((BottomNavigationView) findViewById(R.id.bottom_navigation), controller);
      }

      public void onHelp(MenuItem item) {
        if(item.getItemId() == R.id.menu_help)
            if(helpFragment == null)
                helpFragment = new HelpFragment();
           helpFragment.show(getSupportFragmentManager(), HelpFragment.TAG);
      }
  }