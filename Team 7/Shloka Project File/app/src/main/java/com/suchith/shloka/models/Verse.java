package com.suchith.shloka.models;

public class Verse {
    private String sanskrit;
    private String english;
    private String explanation;

    // Default constructor required for Firebase
    public Verse() {}

    public Verse(String sanskrit, String english, String explanation) {
        this.sanskrit = sanskrit;
        this.english = english;
        this.explanation = explanation;
    }

    public String getSanskrit() {
        return sanskrit;
    }

    public void setSanskrit(String sanskrit) {
        this.sanskrit = sanskrit;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}
