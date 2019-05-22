package com.example.derrick_junior_name_maker.models;

import java.util.HashMap;
import java.util.Map;

public class QuestionListBuilder {
    public QuestionListBuilder() {
        this.questions = new HashMap<>();
    }

    final private Map<Integer, Question> questions;

    public void setQuestion(int id, Question question) {
        questions.put(id, question);
    }

    public QuestionList build() {
        return new BuiltQuestionList(questions);
    }
}

class BuiltQuestionList implements QuestionList {
    BuiltQuestionList(Map<Integer, Question> questions) {
        this.questions = questions;
    }

    private final Map<Integer, Question> questions;

    @Override
    public Question getQuestionById(int id) {
        return questions.get(id);
    }
}