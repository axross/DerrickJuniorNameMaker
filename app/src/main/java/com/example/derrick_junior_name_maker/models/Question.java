package com.example.derrick_junior_name_maker.models;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Objects;

public class Question {
    public Question(int id, String text, List<QuestionOption> options) {
        this.id = id;
        this.text = text;
        this.options = options;
    }

    private final int id;

    private final String text;

    private final List<QuestionOption> options;

    public String getText() {
        return text;
    }

    public List<QuestionOption> getOptions() {
        return options;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("Question<%d>", id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return id == question.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
