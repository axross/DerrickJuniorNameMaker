package com.example.derrick_junior_name_maker.models;

import androidx.annotation.Nullable;

import java.util.Objects;

public class QuestionOption {
    public QuestionOption(int id, String text, @Nullable Integer nextQuestionId) {
        this.id = id;
        this.text = text;
        this.nextQuestionId = nextQuestionId;
    }

    private final int id;

    private final String text;

    private final Integer nextQuestionId;

    public String getText() {
        return text;
    }

    public Integer getNextQuestionId() {
        return nextQuestionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionOption that = (QuestionOption) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
