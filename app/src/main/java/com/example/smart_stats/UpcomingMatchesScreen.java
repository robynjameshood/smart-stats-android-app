package com.example.smart_stats;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UpcomingMatchesScreen extends AppCompatActivity {

    Bundle data; // instantiates data as a Bundle data-type
    String value; // instantiates string value
    ArrayList<String> liveFixtures;// = instantiates a string array to be used in the array adapter.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_matches_screen);
        FirebaseApp.initializeApp(this);

        leagues();

        liveFixtures = new ArrayList<>();

    }

    public void apiCall(int leagueID, String league) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api-football-v1.p.rapidapi.com/v3/fixtures?league="+leagueID+"&next=20")
                .get()
                .addHeader("X-RapidAPI-Host", "api-football-v1.p.rapidapi.com")
                .addHeader("X-RapidAPI-Key", "332c0a65dbmshaa2eb7ba72092d5p1dce5bjsna84d3145dcf2")
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String json = response.body().string();

                try {

                    JSONObject bodyResponse = new JSONObject(json);

                    JSONArray responseArray = bodyResponse.getJSONArray("response");

                    UpcomingMatchesScreen.this.runOnUiThread(() -> {

                        try {
                            for (int i = 0; i < responseArray.length(); i++) {
                                JSONObject teams = (JSONObject) responseArray.getJSONObject(i).get("teams");
                                JSONObject fixtureDate = (JSONObject) responseArray.getJSONObject(i).get("fixture");
                                String date = fixtureDate.getString("date");
                                String formattedDate = date.substring(0, 10);
                                JSONObject homeTeam = (JSONObject) teams.getJSONObject("home");
                                JSONObject awayTeam = (JSONObject) teams.getJSONObject("away");
                                liveFixtures.add(homeTeam.getString("name") + " v " + awayTeam.getString("name") + " " + "\nDate: " + formattedDate);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("output", liveFixtures.toString());
                        listBuilder(liveFixtures, league);

                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void listBuilder(ArrayList<String> liveMatches, String league) {
        if (!liveMatches.isEmpty()) {
            ListView listView = findViewById(R.id.listViewUpcomingGames);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.activity_listview_center, liveMatches);
            listView.setAdapter(adapter);
        }
        else {
            liveMatches.add("No live " + league + " matches in progress");
            ListView listView = findViewById(R.id.listViewUpcomingGames);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.activity_listview_center, liveMatches);
            listView.setAdapter(adapter);
        }
        Log.d("output", liveFixtures.toString());
    }

    public void leagues() {
        data = getIntent().getExtras();
        value = data.getString("league");
        int id;

        switch (value) {
            case "Premiership":
                id = 39;
                apiCall(id, value);
                break;
            case "Championship":
                id = 40;
                apiCall(id, value);
                break;
            case "Ligue One":
                id = 61;
                apiCall(id, value);
                break;
            case "Serie A":
                id = 135;
                apiCall(id, value);
                break;
            case "LaLiga":
                id = 140;
                apiCall(id, value);
                break;
            case "Bundesliga":
                id = 78;
                apiCall(id, value);
                break;
            default:
                break;
        }
    }
}