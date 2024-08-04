package com.example.connectfour;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;

public class GameOptionsFragment extends Fragment {
    // Defining a key for storing the selected game mode in SharedPreferences
    private static final String PREF_SELECTED_MODE = "selected_mode";

    private SharedPreferences sharedPreferences;
    private RadioGroup radioOptions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_options, container, false);

        radioOptions = view.findViewById(R.id.radioOptions);
        RadioButton easyRadioButton = view.findViewById(R.id.easy);
        RadioButton mediumRadioButton = view.findViewById(R.id.medium);
        RadioButton hardRadioButton = view.findViewById(R.id.hard);


        // Initialize SharedPreferences
        getActivity();
        sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE);

        // Get the selected game mode from SharedPreferences
        int selectedModeId = sharedPreferences.getInt(PREF_SELECTED_MODE, R.id.easy);

        // Set the selected radio button as checked
        radioOptions.check(selectedModeId);

        // Add a click callback to all radio buttons
        easyRadioButton.setOnClickListener(this::onLevelSelected);

        mediumRadioButton.setOnClickListener(this::onLevelSelected);

        hardRadioButton.setOnClickListener(this::onLevelSelected);

        return view;
    }

    private void onLevelSelected(View view) {
        // Save the selected level ID in SharedPreferences
        int selectedModeId = radioOptions.getCheckedRadioButtonId();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(PREF_SELECTED_MODE, selectedModeId);
        editor.apply();
    }
}
