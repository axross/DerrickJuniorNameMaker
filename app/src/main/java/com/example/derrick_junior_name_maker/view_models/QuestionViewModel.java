package com.example.derrick_junior_name_maker.view_models;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.derrick_junior_name_maker.models.Question;
import com.example.derrick_junior_name_maker.models.QuestionOption;

import java.util.List;
import java.util.Objects;

public class QuestionViewModel extends ViewModel {
    QuestionViewModel(Question question) {
        this.question = question;
        this.selectedOption = new MutableLiveData<>();
    }

    private final Question question;
    private final MutableLiveData<QuestionOption> selectedOption;

    public Question getQuestion() {
        return question;
    }

    public LiveData<QuestionOption> getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(QuestionOption option) {
        List<QuestionOption> availableOptions = question.getOptions();

        assert option == null || availableOptions.contains(option): "option is null or one in the question";

        selectedOption.setValue(option);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionViewModel that = (QuestionViewModel) o;
        return Objects.equals(question, that.question);
    }

    @Override
    public int hashCode() {
        return Objects.hash(question);
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("QuestionViewModel<%s>", question);
    }
}
