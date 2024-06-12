package com.example.runelogic.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Filters(@JsonProperty("matching") String matching, @JsonProperty("isDiacritic") boolean isDiacritic, 
@JsonProperty("grades") String[] grades, @JsonProperty("jlpt") String[] jlpt, @JsonProperty("strokes") int[] strokes, 
@JsonProperty("frequency") int[] frequency) {
} 
