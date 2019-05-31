package com.example.derrick_junior_name_maker.models;

import com.example.derrick_junior_name_maker.view_models.QuestionViewModel;

import java.util.ArrayList;

public class NameLogic {

    private static NameLogic nameLogic = null;

    private String sex;
    private ArrayList<QuestionViewModel> questionList = new ArrayList<>();
    private WorldPeopleNameList worldPeopleNameList;

    private WorldPeopleNameListItem countryPeopleNameList;

    private NameLogic() {}

    public static NameLogic getNameLogic(){
        if(nameLogic == null){
            nameLogic = new NameLogic();
        }
        return nameLogic;
    }

    public void setCountry(String country) {
        for (WorldPeopleNameListItem item: this.worldPeopleNameList.getList()) {
            if (item.getRegion() == country) {
                this.countryPeopleNameList = item;
            }
        }
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

    public String getName(ArrayList<QuestionViewModel> questions) {
        int total = 0;

        for (int i = 0; i < questions.size(); ++i) {
            int id = questions.get(i).getSelectedOption().getValue().getId();

            total = total | id << i;
        }

        if(sex.equals("MALE")){
            return this.countryPeopleNameList.getMaleNames().get(total % this.countryPeopleNameList.getMaleNames().size());
        } else {
            return this.countryPeopleNameList.getFemaleNames().get(total % this.countryPeopleNameList.getFemaleNames().size());
        }
    }
}
