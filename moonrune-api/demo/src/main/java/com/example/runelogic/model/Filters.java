package com.example.runelogic.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Filters(@JsonProperty("matching") String matching, @JsonProperty("isDiacritic") int isDiacritic, 
@JsonProperty("grades") String[] grades, @JsonProperty("jlpt") String[] jlpt, @JsonProperty("strokes") int[] strokes, 
@JsonProperty("frequency") int[] frequency) {

    public String getMatchingQuery(int collectionID) {
        String matchingInsert = "%" + matching + "%";
        if (matching.equals("")) return "";
        if (collectionID != 6) {
            return String.format("""
            and (terms.name like "%s"
            or hasMeaning.meaning like "%s")  
            """
            ,matchingInsert, matchingInsert);
        }
        return String.format(""" 
            and (terms.name like "%s"
            or hasMeaning.meaning like "%s"
            or terms.termID in
            ((
            select hasReading.termID from hasReading
            where reading like "%s"
            or romaji like "%s"
            ) union (
            select hasLower.termID from hasLower
            where lower like "%s"
            ) union (
            select hasGreekName.termID from hasGreekName
            where greekname like "%s"
            )))
            """, matchingInsert, matchingInsert, matchingInsert, matchingInsert, matchingInsert, matching);
    }

    public String getDiacriticQuery() {
        switch (isDiacritic) {
            case 0:
                return """ 
                    and terms.termID not in (
                    select isDiacritic.termID from isDiacritic
                    )
                    """;
            case 2:
                return """
                    and terms.termID in (
                    select isDiacritic.termID from isDiacritic
                    )
                    """;
        }
        return "";
    }

    public String getGradeQuery() {
        String gradeString = "";
        for (int i = 0; i < grades.length; i++) {
            gradeString.concat(grades[i]);
            if (i != grades.length - 1) gradeString.concat("\", \"");
        }
        System.out.println(gradeString);
        if (gradeString.isBlank()) return "";
        return String.format("""
                and terms.termID in (
                select kanjiProperties.termID from kanjiProperties
                where grade in (%s))
                """, gradeString);
    }

    public String getJlptQuery() {
        String jlptString = "";
        for (int i = 0; i < jlpt.length; i++) {
            jlptString.concat(jlpt[i]);
            if (i != jlpt.length - 1) jlptString.concat("\", \"");
        }
        if (jlptString.isBlank()) return "";
        return String.format("""
                and terms.termID in (
                select kanjiProperties.termID from kanjiProperties
                where jlpt in (%s))
                """, jlptString);
    }

    public String getStrokeQuery() {
        String part1 = "";
        String part2 = "";
        if (strokes[0] > 0) {
            part1 = String.format("where strokes >= %d", strokes[0]);
            if (strokes[1] > 0) {
                part2 = " and ";
            } 
        }
        if (strokes[1] > 0) {
            part2.concat(String.format("where strokes <= %d", strokes[1]));
        }
        if (part1.equals(part2)) return "";
        return String.format("""
                and terms.termID in (
                select kanjiProperties.termID from kanjiProperties
                %s
                %s
                )
                """, part1, part2);
    }

    public String getFrequencyQuery() {
        String part1 = "";
        String part2 = "";
        if (frequency[0] > 0) {
            part1 = String.format("where kanjiRank >= %d", frequency[0]);
            if (frequency[1] > 0) {
                part2 = " and ";
            } 
        }
        if (frequency[1] > 0) {
            part2.concat(String.format("where kanjiRank <= %d", frequency[1]));
        }
        if (part1.equals(part2)) return "";
        return String.format("""
                and terms.termID in (
                select kanjiProperties.termID from kanjiProperties
                %s
                %s
                )
                """, part1, part2);
    }
} 
