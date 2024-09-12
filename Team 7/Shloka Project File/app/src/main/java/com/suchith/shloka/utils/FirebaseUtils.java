package com.suchith.shloka.utils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.suchith.shloka.models.Chapter;

public class FirebaseUtils {

    private static FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();

    public static void uploadChapter(String chapterId, Chapter chapter) {
        DatabaseReference chapterRef = mDatabase.getReference("Chapters/" + chapterId);
        chapterRef.setValue(chapter);
    }

    public static DatabaseReference getChaptersReference() {
        return mDatabase.getReference("Chapters");
    }

    public static DatabaseReference getVersesReference(String chapterId) {
        return mDatabase.getReference("Chapters/" + chapterId + "/verses");
    }

    public static DatabaseReference getVerseReference(int chapterNumber, int verseNumber) {
        return mDatabase.getReference("Chapters/Chapter" + chapterNumber + "/verses/verse" + verseNumber);
    }
}