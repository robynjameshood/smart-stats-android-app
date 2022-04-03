package com.example.smart_stats;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class leagues extends AppCompatActivity {

    Bundle data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leagues);

        data = getIntent().getExtras();

        TextView testTestView =  findViewById(R.id.leagues_title_display);

        String menuSeleceted = data.getString("menuSelect"); // assigns "value" by getting the string passed from intent (data).

        testTestView.setText(menuSeleceted);

        //userTag();
    }

    public void userTag() // use firebase to retrieve this data - userDisplayText
    {
        data = getIntent().getExtras();
        String email = data.getString("email");

        TextView userTag = (TextView) findViewById(R.id.userDisplayText);
        if (!email.equals(""))
        {
            userTag.setText(email);
        }

        else {
            String blank = getResources().getString(R.string.input_email_placeholder_text);
            userTag.setText(blank);
        }
    }

    public void leagueSelect(View view) {

        Button buttonPressed = (Button)view; // assigns buttonPressed to the value of the button that's captured by view.

        String league = buttonPressed.getText().toString();

        String value = data.getString("option"); // assigns "value" by getting the string passed from intent (data).

        Intent intent; // instantiates intent.

        //Created case statements based on the option number that calls firebase data based on the option numbers task.

        switch (value) {
            case "1":
                intent = new Intent(leagues.this, InplayMatchesScreen.class);
                intent.putExtra("league", league);
                startActivity(intent);
                break;
            case "2":
                intent = new Intent(leagues.this, UpcomingMatchesScreen.class);
                intent.putExtra("league", league);
                startActivity(intent);
                break;
            case "3":
                intent = new Intent(leagues.this, TopGoalscorersScreen.class);
                intent.putExtra("league", league);
                startActivity(intent);
                break;
            case "4":
                intent = new Intent(leagues.this, YellowCardStatsScreen.class);
                intent.putExtra("league", league);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}