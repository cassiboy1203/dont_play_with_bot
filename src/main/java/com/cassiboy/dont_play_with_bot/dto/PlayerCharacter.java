package com.cassiboy.dont_play_with_bot.dto;

public class PlayerCharacter {
    private String name;
    private String charClass;
    private String server;
    private String stronghold;
    private int numberOfReports;

    public String getStronghold() {
        return stronghold;
    }

    public void setStronghold(String stronghold) {
        this.stronghold = stronghold;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCharClass() {
        return charClass;
    }

    public void setCharClass(String charClass) {
        this.charClass = charClass;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public int getNumberOfReports() {
        return numberOfReports;
    }

    public void setNumberOfReports(int numberOfReports) {
        this.numberOfReports = numberOfReports;
    }
}
