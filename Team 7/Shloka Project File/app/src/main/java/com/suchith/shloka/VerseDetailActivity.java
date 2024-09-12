package com.suchith.shloka;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.suchith.shloka.models.Verse;
import com.suchith.shloka.utils.FirebaseUtils;

public class VerseDetailActivity extends AppCompatActivity {

    private static final String TAG = "VerseDetailActivity";
    private TextView sanskritText, englishText, explanationText;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verse_detail);

        sanskritText = findViewById(R.id.sanskritText);
        englishText = findViewById(R.id.englishText);
        explanationText = findViewById(R.id.explanationText);
        progressBar = findViewById(R.id.progressBar);

        int chapterNumber = getIntent().getIntExtra("chapterNumber", 1);
        int verseNumber = getIntent().getIntExtra("verseNumber", 1);

        Log.d(TAG, "Loading verse details for Chapter " + chapterNumber + ", Verse " + verseNumber);
        loadVerseDetails(chapterNumber, verseNumber);
    }

    private void loadVerseDetails(int chapterNumber, int verseNumber) {
        showLoading(true);
        DatabaseReference verseRef = FirebaseUtils.getVerseReference(chapterNumber, verseNumber);
        verseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showLoading(false);
                if (dataSnapshot.exists()) {
                    Log.d(TAG, "Verse data snapshot: " + dataSnapshot.toString());
                    Verse verse = dataSnapshot.getValue(Verse.class);
                    if (verse != null) {
                        sanskritText.setText(verse.getSanskrit());
                        englishText.setText(verse.getEnglish());
                        explanationText.setText(verse.getExplanation());
                    } else {
                        Log.e(TAG, "Verse object is null");
                        showError("Error loading verse details");
                    }
                } else {
                    Log.e(TAG, "Verse data does not exist");
                    showError("Verse not found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                showLoading(false);
                Log.e(TAG, "Database error: " + databaseError.getMessage());
                showError("Error: " + databaseError.getMessage());
            }
        });
    }

    private void showLoading(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}