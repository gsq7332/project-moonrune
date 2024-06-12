package com.example.runelogic.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Filters(@JsonProperty("matching") String matching, @JsonProperty("isDiacritic") int isDiacritic, 
@JsonProperty("grades") String[] grades, @JsonProperty("jlpt") String[] jlpt, @JsonProperty("strokes") int[] strokes, 
@JsonProperty("frequency") int[] frequency) {

    public String getMatchingQuery() {
        return String.format("""
                and termID in ((
                select termID from term
                where name like "%%s%"
                ) union (
                select termID from hasMeaning
                where meaning like "%%s%"
                ) union (
                select termID from hasReading
                where reading like "%%s%"
                or romaji like "%%s%"
                ) union (
                select termID from hasLower
                where lower like "%%s%"
                )
                select termID from hasGreekName
                where greekname like "%%s%"
                """, matching, matching, matching, matching, matching, matching);
    }

    public String getDiacriticQuery() {
        switch (isDiacritic) {
            case 0:
                return """
                        and termID not in (
                        select termID from isDiacritic
                        )
                        """;
            case 2:
                return """
                        and termID in (
                        select termID from isDiacritic
                        )
                        """;
        }
        return "";
    }

    public String getGradeQuery() {
        String gradeString = "";
        for (int i = 0; i < grades.length; i++) {
            gradeString.concat(grades[i]);
            if (i != grades.length - 1) gradeString.concat(", ");
        }
        return String.format("""
                and termID in (
                select termID from kanjiProperties
                where grade in (%s))
                """, gradeString);
    }

    public String getJlptQuery() {
        String jlptString = "";
        for (int i = 0; i < jlpt.length; i++) {
            jlptString.concat(jlpt[i]);
            if (i != jlpt.length - 1) jlptString.concat(", ");
        }
        return String.format("""
                and termID in (
                select termID from kanjiProperties
                where jlpt in (%s))
                """, jlptString);
    }

    public String getStrokeQuery() {
        String part1 = "";
        String part2 = "";
        if (strokes[0] > 0) {
            part1 = String.format("where strokes >= %d", strokes[0]);
            if (strokes[1] > 0) {
                part2 = "and ";
            } 
        }
        if (strokes[1] > 0) {
            part2.concat(String.format("where strokes <= %d", strokes[1]));
        }
        if (part1.equals(part2)) return "";
        return String.format("""
                and termID in (
                select termID from kanjiProperties
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
                part2 = "and ";
            } 
        }
        if (frequency[1] > 0) {
            part2.concat(String.format("where kanjiRank <= %d", frequency[1]));
        }
        if (part1.equals(part2)) return "";
        return String.format("""
                and termID in (
                select termID from kanjiProperties
                %s
                %s
                )
                """, part1, part2);
    }
} 
