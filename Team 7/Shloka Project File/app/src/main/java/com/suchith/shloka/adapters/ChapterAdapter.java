package com.suchith.shloka.adapters;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.suchith.shloka.R;
import com.suchith.shloka.VerseListActivity;
import com.suchith.shloka.models.Chapter;

import java.util.List;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ViewHolder> {
    private Context mContext;
    private List<Chapter> chapterList;

    public ChapterAdapter(Context context, List<Chapter> chapters) {
        this.mContext = context;
        this.chapterList = chapters;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_chapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Chapter chapter = chapterList.get(position);
        holder.chapterTitle.setText(chapter.getChapterName());
        holder.verseCount.setText(mContext.getString(R.string.verses_count, chapter.getTotalVerses()));

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, VerseListActivity.class);
            intent.putExtra("chapter", chapter);
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return chapterList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView chapterTitle, verseCount;

        public ViewHolder(View itemView) {
            super(itemView);
            chapterTitle = itemView.findViewById(R.id.chapterTitle);
            verseCount = itemView.findViewById(R.id.verseCount);
        }
    }
}
