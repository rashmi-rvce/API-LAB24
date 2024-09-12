package com.suchith.shloka.models;

import java.io.Serializable;

public class Chapter implements Serializable {
    private String chapterName;
    private int chapterNumber;
    private int totalVerses;

    // Default constructor required for Firebase
    public Chapter() {}

    public Chapter(String chapterName, int chapterNumber, int totalVerses) {
        this.chapterName = chapterName;
        this.chapterNumber = chapterNumber;
        this.totalVerses = totalVerses;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public int getChapterNumber() {
        return chapterNumber;
    }

    public void setChapterNumber(int chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    public int getTotalVerses() {
        return totalVerses;
    }

    public void setTotalVerses(int totalVerses) {
        this.totalVerses = totalVerses;
    }
}