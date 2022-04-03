package com.example.smart_stats;

public class playerDetails {
    private String playerName;
    private String team;
    private String yellowCards;
    private String appearance;
    private String league;
    private String gamesPlayed;
    private String playerGoals;

    public playerDetails(String playerName, String team, String league, String yellowCards, String appearance) {
        this.playerName = playerName;
        this.team = team;
        this.yellowCards = yellowCards;
        this.appearance = appearance;
        this.league = league;
    }

    public playerDetails(String playerName, String team, String gamesPlayed, String totalGoals) {
        this.playerName = playerName;
        this.team = team;
        this.gamesPlayed = gamesPlayed;
        this.playerGoals = totalGoals;
    }

    // Default constructor

    public playerDetails() {}

    // Property getters/setters


    public String getGamesPlayed() {
        return gamesPlayed;
    }

    public String getPlayerGoals() {
        return playerGoals;
    }

    public String getLeague() {
        return league;
    }

    public String getAppearance() {
        return appearance;
    }

    public String getYellowCards() {
        return yellowCards;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getTeam() {
        return team;
    }

    public void setGamesPlayed(String gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public void setPlayerGoals(String playerGoals) {
        this.playerGoals = playerGoals;
    }

    public void setAppearance(String appearance) {
        this.appearance = appearance;
    }

    public void setYellowCards(String  yellowCards) {
        this.yellowCards = yellowCards;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public void setLeague(String league) {
        this.league = league;
    }
}
