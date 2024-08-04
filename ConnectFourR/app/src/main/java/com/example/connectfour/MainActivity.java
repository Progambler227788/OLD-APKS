package com.example.connectfour;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Get the ActionBar object
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            // Set the background color of the ActionBar
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3C50B4")));
            actionBar.show();
        }



        // Instantiate a BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bView);

        // Instantiate a NavHostFragment
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fc);

        if (navHostFragment != null) {
            // Instantiate a NavController
            NavController navController = navHostFragment.getNavController();

            // Set up BottomNavigationView with NavController
            navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
                if (destination.getId() == R.id.boardFragment) {
                    // When navigating to BoardFragment
                    bottomNavigationView.getMenu().findItem(R.id.C4).setChecked(true);
                } else if (destination.getId() == R.id.gameOptionsFragment) {
                    // When navigating to GameOptionsFragment
                    bottomNavigationView.getMenu().findItem(R.id.Options).setChecked(true);
                }
            });

            //noinspection deprecation
            bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
                if (item.getItemId() == R.id.C4) {
                    // Navigate to BoardFragment
                    navController.navigate(R.id.action_gameOptions_to_board);
                    return true;
                } else if (item.getItemId() == R.id.Options) {
                    // Navigate to GameOptionsFragment
                    navController.navigate(R.id.action_board_to_gameOptions);
                    return true;
                }
                return false;
            });
        }
    }
}
