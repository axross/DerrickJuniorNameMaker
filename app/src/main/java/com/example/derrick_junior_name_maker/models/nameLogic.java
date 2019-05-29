package com.example.derrick_junior_name_maker.models;

import com.example.derrick_junior_name_maker.view_models.QuestionViewModel;

import java.util.ArrayList;
import java.util.Random;

public class nameLogic {
    private String country;
    private String sex;
    private ArrayList<QuestionViewModel> questionList;
    private WorldPeopleNameList worldPeopleNameList;

    private ArrayList<WorldPeopleNameListItem> countryPeopleNameList;

    public nameLogic(String country, String sex, ArrayList<QuestionViewModel> questionList, WorldPeopleNameList worldPeopleNameList){
        this.country = country;
        this.sex = sex;
        this.questionList = questionList;
        this.worldPeopleNameList = worldPeopleNameList;

        countryPeopleNameList = new ArrayList<>();
    }

    public String getName(){

        getCountryPeopleNameList();

        questionList.get(0).getSelectedOption();

        Random random = new Random();

        if(false) {
            return "https://dna-testing.ca/locations/vancouver.html";
        }

        if(sex.equals("MALE")){
            return countryPeopleNameList.get(0).getMaleNames().get(random.nextInt(countryPeopleNameList.get(0).getMaleNames().size()));
        } else {
            return countryPeopleNameList.get(0).getMaleNames().get(random.nextInt(countryPeopleNameList.get(0).getFemaleNames().size()));
        }
    }

    private void getCountryPeopleNameList(){
        for (WorldPeopleNameListItem worldPeopleNameListItem: worldPeopleNameList.getList()) {
            if(worldPeopleNameListItem.getRegion().equals(this.country)){
                countryPeopleNameList.add(worldPeopleNameListItem);
            }
        }
    }
}
