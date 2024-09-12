package com.suchith.shloka.adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.suchith.shloka.R;
import com.suchith.shloka.VerseDetailActivity;
import com.suchith.shloka.models.Verse;

import java.util.List;

public class VerseAdapter extends RecyclerView.Adapter<VerseAdapter.ViewHolder> {

    private Context context;
    private List<Integer> verseNumbers;
    private int chapterNumber;

    public VerseAdapter(Context context, List<Integer> verseNumbers, int chapterNumber) {
        this.context = context;
        this.verseNumbers = verseNumbers;
        this.chapterNumber = chapterNumber;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_verse, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int verseNumber = verseNumbers.get(position);
        holder.verseNumber.setText(String.valueOf(verseNumber));

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, VerseDetailActivity.class);
            intent.putExtra("chapterNumber", chapterNumber);
            intent.putExtra("verseNumber", verseNumber);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return verseNumbers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView verseNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            verseNumber = itemView.findViewById(R.id.verseNumber);
        }
    }
}