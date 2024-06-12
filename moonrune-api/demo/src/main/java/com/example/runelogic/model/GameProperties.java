package com.example.runelogic.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GameProperties(@JsonProperty("numQuestions") int numQuestions, @JsonProperty("numAnswers") int numAnswers,
@JsonProperty("questionType") String questionType, @JsonProperty("answerType") String answerType) {
    
}
