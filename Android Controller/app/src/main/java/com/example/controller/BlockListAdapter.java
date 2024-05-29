package com.example.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BlockListAdapter extends RecyclerView.Adapter<BlockListAdapter.CodeItemHolder> {
    private List<Direction> codeImgIDs;
    private Context context;

    public BlockListAdapter(List<Direction> codeImgIDs, Context context) {
        this.codeImgIDs = codeImgIDs;
        this.context = context;
    }

    @NonNull
    @Override
    public CodeItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.codeitem, parent, false);

        return new CodeItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CodeItemHolder holder, int position) {
        Direction code = codeImgIDs.get(position);

        holder.codeImg.setImageDrawable(ContextCompat.
                getDrawable(context, code.getDrawableID()));

        holder.itemDeleteBtn.setOnClickListener(view -> {
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, getItemCount());
            codeImgIDs.remove(position);
        });
    }

    @Override
    public int getItemCount() {
        return codeImgIDs.size();
    }

    class CodeItemHolder extends RecyclerView.ViewHolder{
        ImageView codeImg;
        Button itemDeleteBtn;

        public CodeItemHolder(View view){
            super(view);
            codeImg = view.findViewById(R.id.codeImg);
            itemDeleteBtn = view.findViewById(R.id.itemDeleteBtn);

        }
    }
}
