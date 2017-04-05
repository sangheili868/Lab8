package com.cs40333.cmaheu.lab7;

import java.io.Serializable;

public class Team implements Serializable {
    String shortDate;
    String longDate;
    String location;
    String logo;
    String placeName;
    String teamName;
    String record;
    String score;
    String timeleft;
    int id;

    Team (String _shortDate, String _longDate, String _location, String _logo,
          String _placeName, String _teamName, String _record, String _score,
          String _timeleft, int _id) {
        setShortDate(_shortDate);
        setLongDate(_longDate);
        setLocation(_location);
        setLogo(_logo);
        setPlaceName(_placeName);
        setTeamName(_teamName);
        setRecord(_record);
        setScore(_score);
        setTimeleft(_timeleft);
        setId(_id);

    }

    public String getShortDate() {
        return shortDate;
    }

    public String getLongDate() {
        return longDate;
    }

    public String getLocation() {
        return location;
    }

    public String getLogo() {
        return logo;
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getRecord() {
        return record;
    }

    public String getScore() {
        return score;
    }

    public String getTimeleft() {
        return timeleft;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setShortDate(String shortDate) {
        this.shortDate = shortDate;
    }

    public void setLongDate(String longDate) {
        this.longDate = longDate;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void setTimeleft(String timeleft) {
        this.timeleft = timeleft;
    }
}