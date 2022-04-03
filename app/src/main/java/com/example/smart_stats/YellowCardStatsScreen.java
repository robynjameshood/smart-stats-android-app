package com.example.smart_stats;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class YellowCardStatsScreen extends AppCompatActivity {

    Bundle data; // instantiates data as a Bundle data-type
    String value; // instantiates string value
    ArrayList<String> topYellowCards;// = instantiates a string array to be used in the array adapter.
    FirebaseDatabase database;
    DatabaseReference reference;
    ArrayList<String> backUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yellow_card_stats_screen);

        leagues();

        topYellowCards = new ArrayList<>();
        backUp = new ArrayList<>();

    }

    public void addPlayerToDatabase(String league, playerDetails player) {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://smart-stats-d26be-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference reference = database.getReference(league);

        String playerName = player.getPlayerName();

        String formattedName = playerName.replace(".", "");

        reference.child("Yellow Cards").child(formattedName).setValue(player);
    }

    public void apiCall(int leagueID, String league) {
        OkHttpClient client = new OkHttpClient();

        Calendar date = Calendar.getInstance();
        int year = date.get(Calendar.YEAR) - 1;
        String formattedYear = String.valueOf(year);

        Request request = new Request.Builder()
                .url("https://api-football-v1.p.rapidapi.com/v3/players/topyellowcards?league="+leagueID+"&season="+formattedYear)
                .get()
                .addHeader("X-RapidAPI-Host", "api-football-v1.p.rapidapi.com")
                .addHeader("X-RapidAPI-Key", "876d579235msh398dd1932516a96p1ffad5jsnd2510f2f083f")
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

                    YellowCardStatsScreen.this.runOnUiThread(() -> {

                        try {
                            for (int i = 0; i < responseArray.length(); i++) {
                                JSONObject player = (JSONObject) responseArray.getJSONObject(i).getJSONObject("player"); // this cycles through each index in the response array and gets each json object called player.
                                String playerName = player.getString("name");

                                JSONObject index_leagueName = (JSONObject) responseArray.getJSONObject(i);
                                JSONArray stats_index_leagueName = (JSONArray) index_leagueName.getJSONArray("statistics");
                                JSONObject league = (JSONObject) stats_index_leagueName.getJSONObject(0).getJSONObject("league");
                                String leagueName = league.getString("name");


                                JSONObject index_team = (JSONObject) responseArray.getJSONObject(i);
                                JSONArray stats_index_team = (JSONArray) index_team.getJSONArray("statistics");
                                JSONObject team = (JSONObject) stats_index_team.getJSONObject(0).getJSONObject("team");
                                String teamName = team.getString("name");

                                JSONObject index_appearances = (JSONObject) responseArray.getJSONObject(i);
                                JSONArray stats_index_appearances = (JSONArray) index_appearances.getJSONArray("statistics");
                                JSONObject playerAppearences = (JSONObject) stats_index_appearances.getJSONObject(0).getJSONObject("games");
                                String gamesPlayed = playerAppearences.getString("appearences");

                                JSONObject index_goals = (JSONObject) responseArray.getJSONObject(i);
                                JSONArray stats_index_playerGoals = (JSONArray) index_goals.getJSONArray("statistics");
                                JSONObject goals = (JSONObject) stats_index_playerGoals.getJSONObject(0).getJSONObject("cards");
                                String playerCards = goals.getString("yellow");

                                topYellowCards.add("Name: " + playerName + "\nTeam: " + teamName + "\nGames Played: " + gamesPlayed + "\nYellow Cards: " + playerCards);

                                playerDetails playerDB = new playerDetails(playerName, teamName, leagueName, playerCards, gamesPlayed);

                                addPlayerToDatabase(leagueName, playerDB);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        listBuilder(topYellowCards, league);

                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void listBuilder(ArrayList<String> liveMatches, String league) {
        playerDetails pd = new playerDetails();
        if (!liveMatches.isEmpty()) {
            ListView listView = findViewById(R.id.listViewTopYellowCards);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.activity_listview_center, liveMatches);
            listView.setAdapter(adapter);
        }
        else { // change this else to call firebase database if the api falls to call
            FirebaseDatabase database = FirebaseDatabase.getInstance("https://smart-stats-d26be-default-rtdb.europe-west1.firebasedatabase.app/");
            DatabaseReference leagueName = database.getReference(league);
            DatabaseReference goalScorers = leagueName.child("Yellow Cards");


            goalScorers.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot snap : snapshot.getChildren()) {
                        backUp.add(snap.child(pd.getPlayerName()).getValue().toString());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            // listview adapter via firebase
            ListView listViewDB = findViewById(R.id.listViewTopYellowCards);
            ArrayAdapter<String> adapterDB = new ArrayAdapter<>(this, R.layout.activity_listview_center, backUp);
            listViewDB.setAdapter(adapterDB);

            // listview adapter via api
            liveMatches.add("No goalscorers data for " + league + " available");
            ListView listViewAPI = findViewById(R.id.listViewTopYellowCards);
            ArrayAdapter<String> adapterAPI = new ArrayAdapter<>(this, R.layout.activity_listview_center, liveMatches);
            listViewAPI.setAdapter(adapterAPI);
        }
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