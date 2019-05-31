package com.example.derrick_junior_name_maker.view_models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.derrick_junior_name_maker.models.Question;
import com.example.derrick_junior_name_maker.models.QuestionList;

import java.util.ArrayList;

public class QuestionStackViewModel extends ViewModel {
    public QuestionStackViewModel(QuestionList questionList) {
        this.questionList = questionList;
        this.questions = createQuestionsLiveData();
    }

    private final QuestionList questionList;

    private final MutableLiveData<ArrayList<QuestionViewModel>> questions;

    public LiveData<ArrayList<QuestionViewModel>> getQuestions() {
        return questions;
    }

    public void setFirstQuestion(int questionId) {
        insertQuestionAfter(null, questionList.getQuestionById(questionId));
    }

    private void insertQuestionAfter(Question afterThis, Question question) {
        ArrayList<QuestionViewModel> questionViewModels = afterThis == null
            ? new ArrayList<>(new ArrayList<>())
            : new ArrayList<>(this.getQuestions().getValue().subList(0, this.getQuestions().getValue().indexOf(new QuestionViewModel(afterThis)) + 1));

        QuestionViewModel questionViewModel = new QuestionViewModel(question);

        questionViewModel.getSelectedOption().observeForever(option -> {
            if (option.getNextQuestionId() != null) {
                insertQuestionAfter(question, questionList.getQuestionById(option.getNextQuestionId()));
            }
        });

        questionViewModels.add(questionViewModel);

        this.questions.setValue(questionViewModels);
    }

    private static MutableLiveData<ArrayList<QuestionViewModel>> createQuestionsLiveData() {
        MutableLiveData<ArrayList<QuestionViewModel>> questions = new MutableLiveData<>();

        questions.setValue(new ArrayList<>());

        return questions;
    }
}
