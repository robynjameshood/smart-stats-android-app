package com.example.smart_stats;

public class match {

    private String home;
    private String away;
    private String homeGoals;
    private String awayGoals;

    public match () {}

    public match (String home, String away, String homeGoals, String awayGoals) {
        this.home = home;
        this.away = away;
        this.homeGoals = homeGoals;
        this.awayGoals = awayGoals;
    }

    public String getHome() {
        return this.home;
    }

    public String getAway() {
        return this.away;
    }

    public String getHomeGoals() {
        return this.homeGoals;
    }

    public String getAwayGoals() {
        return this.awayGoals;
    }
}
