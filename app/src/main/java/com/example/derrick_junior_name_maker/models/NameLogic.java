package com.example.derrick_junior_name_maker.models;

import com.example.derrick_junior_name_maker.view_models.QuestionViewModel;

import java.util.ArrayList;
import java.util.Random;

public class NameLogic {

    private static final NameLogic nameLogic = new NameLogic();

    private String country;
    private String sex;
    private ArrayList<QuestionViewModel> questionList;
    private WorldPeopleNameList worldPeopleNameList;

    private ArrayList<WorldPeopleNameListItem> countryPeopleNameList;

    private NameLogic() {}

    public static NameLogic getNameLogic(){
        return nameLogic;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setQuestionList(ArrayList<QuestionViewModel> questionList) {
        this.questionList = questionList;
    }

    public void setWorldPeopleNameList(WorldPeopleNameList worldPeopleNameList) {
        this.worldPeopleNameList = worldPeopleNameList;
    }

    public void setCountryPeopleNameList(ArrayList<WorldPeopleNameListItem> countryPeopleNameList) {
        this.countryPeopleNameList = countryPeopleNameList;
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
