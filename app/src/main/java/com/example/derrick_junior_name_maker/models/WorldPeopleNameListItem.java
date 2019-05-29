package com.example.derrick_junior_name_maker.models;

import java.util.ArrayList;

public class WorldPeopleNameListItem {
    public WorldPeopleNameListItem(String region, ArrayList<String> maleNames, ArrayList<String> femaleNames) {
        this.region = region;
        this.maleNames = maleNames;
        this.femaleNames = femaleNames;
    }

    private final String region;

    private final ArrayList<String> maleNames;

    private final ArrayList<String> femaleNames;

    public String getRegion() {
        return region;
    }

    public ArrayList<String> getMaleNames() { return maleNames; }

    public ArrayList<String> getFemaleNames() { return femaleNames; }
}
