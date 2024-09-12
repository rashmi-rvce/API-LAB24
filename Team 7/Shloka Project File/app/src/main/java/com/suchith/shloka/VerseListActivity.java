package com.suchith.shloka;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.suchith.shloka.adapters.VerseAdapter;
import com.suchith.shloka.models.Chapter;
import com.suchith.shloka.models.Verse;
import com.suchith.shloka.utils.FirebaseUtils;

import java.util.ArrayList;
import java.util.List;

public class VerseListActivity extends AppCompatActivity {

    private RecyclerView verseRecyclerView;
    private VerseAdapter verseAdapter;
    private TextView chapterTitle, chapterSubtitle, totalVerses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verse_list);

        chapterTitle = findViewById(R.id.chapterTitle);
        chapterSubtitle = findViewById(R.id.chapterSubtitle);
        totalVerses = findViewById(R.id.totalVerses);
        verseRecyclerView = findViewById(R.id.verseRecyclerView);

        Chapter chapter = (Chapter) getIntent().getSerializableExtra("chapter");

        if (chapter != null) {
            chapterTitle.setText("Chapter: " + chapter.getChapterNumber());
            chapterSubtitle.setText(chapter.getChapterName());
            totalVerses.setText("Total Verses: " + chapter.getTotalVerses());

            List<Integer> verseNumbers = new ArrayList<>();
            for (int i = 1; i <= chapter.getTotalVerses(); i++) {
                verseNumbers.add(i);
            }

            verseAdapter = new VerseAdapter(this, verseNumbers, chapter.getChapterNumber());
            verseRecyclerView.setLayoutManager(new GridLayoutManager(this, 5)); // 5 columns
            verseRecyclerView.setAdapter(verseAdapter);
        }
    }
}