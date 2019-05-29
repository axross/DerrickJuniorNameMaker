package com.example.derrick_junior_name_maker.models;

import java.util.ArrayList;

public class WorldPeopleNameList {
    public WorldPeopleNameList(ArrayList<WorldPeopleNameListItem> list) {
        this.list = list;
    }

    private final ArrayList<WorldPeopleNameListItem> list;

    public ArrayList<WorldPeopleNameListItem> getList() {
        return list;
    }

    public ArrayList<String> getCountries() {
        ArrayList<String> countries = new ArrayList<>();

        for (WorldPeopleNameListItem item: list) {
            countries.add(item.getRegion());
        }

        return countries;
    }
}
