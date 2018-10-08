package com.yugimaster.javhub.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ljy.devring.DevRing;
import com.yugimaster.javhub.R;
import com.yugimaster.javhub.view.RowItem;

import java.util.List;

/**
 * Created by yugimaster on 2018/10/8.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<RowItem> rowItems;

    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    /**
     * Set onClick event
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * Set long click event
     */
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public MyRecyclerViewAdapter(List<RowItem> rowItems) {
        this.rowItems = rowItems;
    }

    @Override
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        MyRecyclerViewAdapter.ViewHolder viewHolder = new MyRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyRecyclerViewAdapter.ViewHolder holder, int position) {
        RowItem rowItem = rowItems.get(position);

        DevRing.imageManager().loadNet(rowItem.getPoster_url(), holder.mPoster);
        holder.mTitle.setText(rowItem.getTitle());
        holder.mLink.setText(rowItem.getTags());
        holder.mDuration.setText(rowItem.getProductId());

        int adapterPosition = holder.getAdapterPosition();
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new MyOnClickListener(position, rowItem));
        }
        if (onItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new MyLongClickListener(position, rowItem));
        }
    }

    @Override
    public int getItemCount() {
        return rowItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mPoster;
        TextView mTitle;
        TextView mLink;
        TextView mDuration;
        ViewHolder(View itemView) {
            super(itemView);
            mPoster = itemView.findViewById(R.id.movie_poster);
            mTitle = itemView.findViewById(R.id.movie_title);
            mLink = itemView.findViewById(R.id.movie_link);
            mDuration = itemView.findViewById(R.id.movie_duration);
        }
    }

    private class MyLongClickListener implements View.OnLongClickListener {
        private int postion;
        private RowItem rowItem;

        public MyLongClickListener(int postion, RowItem rowItem) {
            this.postion = postion;
            this.rowItem = rowItem;
        }

        @Override
        public boolean onLongClick(View v) {
            onItemLongClickListener.onItemLongClick(v, postion, rowItem);
            return true;
        }
    }

    private class MyOnClickListener implements View.OnClickListener {
        private int position;
        private RowItem rowItem;

        public MyOnClickListener(int position, RowItem rowItem) {
            this.position = position;
            this.rowItem = rowItem;
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v, position, rowItem);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, RowItem rowItem);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position, RowItem rowItem);
    }
}
