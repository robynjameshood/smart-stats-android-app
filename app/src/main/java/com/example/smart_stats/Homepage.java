package com.example.smart_stats;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Homepage extends AppCompatActivity {

    String emailInput;
    TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
    }

    public void pageSwitcher(String menuSelect, String option) {

        email = findViewById(R.id.userDisplayText);
        emailInput = email.getText().toString();

        Intent intent = new Intent(Homepage.this, leagues.class);
        intent.putExtra("email", emailInput);
        intent.putExtra("menuSelect", menuSelect);
        intent.putExtra("option", option);
        startActivity(intent);
    }

    public void onClick(View view) {

        Button buttonPressed = (Button)view; // assigns buttonPressed to the value of the button that's captured by view.
        String menuSelect = buttonPressed.getText().toString(); // assigns league to the text of the button pressed captured by view.
        String option;

        switch (menuSelect) { // menuSelect is the text from the button press
            case "Inplay Matches":
                option = "1"; // option number refers to the case statements in leagues
                pageSwitcher(menuSelect, option);
                break;
            case "Upcoming Matches":
                option = "2";
                pageSwitcher(menuSelect, option);
                break;
            case "Top Goalscorers":
                option = "3";
                pageSwitcher(menuSelect, option);
                break;
            case "Yellow Card Stats":
                option = "4";
                pageSwitcher(menuSelect, option);
                break;
            default:
                break;
        }
    }
}