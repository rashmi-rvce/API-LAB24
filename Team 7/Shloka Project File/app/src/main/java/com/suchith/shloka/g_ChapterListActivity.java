package com.suchith.shloka;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.suchith.shloka.adapters.ChapterAdapter;
import com.suchith.shloka.models.Chapter;
import com.suchith.shloka.utils.FirebaseUtils;

import java.util.ArrayList;
import java.util.List;

public class g_ChapterListActivity extends AppCompatActivity {

    private static final String TAG = "ChapterListActivity";
    private RecyclerView chapterRecyclerView;
    private ChapterAdapter chapterAdapter;
    private List<Chapter> chapterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_list);

        Log.d(TAG, "onCreate: Starting ChapterListActivity");

        chapterRecyclerView = findViewById(R.id.chapterRecyclerView);
        chapterRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        chapterList = new ArrayList<>();
        chapterAdapter = new ChapterAdapter(this, chapterList);
        chapterRecyclerView.setAdapter(chapterAdapter);

        Log.d(TAG, "RecyclerView initialized, loading chapters.");
        loadChaptersFromFirebase();
    }

    private void loadChaptersFromFirebase() {
        Log.d(TAG, "loadChaptersFromFirebase: Starting to load chapters");
        FirebaseUtils.getChaptersReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chapterList.clear();
                Log.d(TAG, "onDataChange: Received data snapshot: " + dataSnapshot.toString());
                Log.d(TAG, "onDataChange: Number of children: " + dataSnapshot.getChildrenCount());

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.d(TAG, "Processing snapshot: " + snapshot.getKey());
                    Log.d(TAG, "Snapshot value: " + snapshot.getValue());

                    Chapter chapter = snapshot.getValue(Chapter.class);
                    if (chapter != null) {
                        chapterList.add(chapter);
                        Log.d(TAG, "Loaded chapter: " + chapter.getChapterName() + ", Verses: " + chapter.getTotalVerses());
                    } else {
                        Log.e(TAG, "Chapter data is null for snapshot: " + snapshot.toString());
                    }
                }

                Log.d(TAG, "Total chapters loaded: " + chapterList.size());
                chapterAdapter.notifyDataSetChanged();

                if (chapterList.isEmpty()) {
                    Log.w(TAG, "No chapters were loaded. Check your Firebase data structure.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Database error: " + databaseError.getMessage());
            }
        });
    }
}