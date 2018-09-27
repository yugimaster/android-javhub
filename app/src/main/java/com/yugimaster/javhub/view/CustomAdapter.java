package com.yugimaster.javhub.view;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yugimaster.javhub.R;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    Context mContext;
    List<RowItem> rowItems;

    public CustomAdapter(Context context, List<RowItem> rowItems) {
        this.mContext = context;
        this.rowItems = rowItems;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }

    /* private view holder class */
    private class ViewHolder {
        ImageView movie_poster;
        TextView movie_title;
        TextView movie_link;
        TextView movie_duration;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        RowItem row_pos = rowItems.get(position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.movie_poster = (ImageView) convertView.findViewById(R.id.movie_poster);
            holder.movie_title = (TextView) convertView.findViewById(R.id.movie_title);
            holder.movie_link = (TextView) convertView.findViewById(R.id.movie_link);
            holder.movie_duration = (TextView) convertView.findViewById(R.id.movie_duration);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Glide
            .with(mContext)
            .load(row_pos.getPoster_url()) // image url
            .placeholder(R.drawable.no_poster) // the default image
            .diskCacheStrategy(DiskCacheStrategy.RESULT)
            .into(holder.movie_poster);
        holder.movie_title.setText(row_pos.getTitle());
        holder.movie_duration.setText(row_pos.getProductId());
        holder.movie_link.setText(row_pos.getTags());

        return convertView;
    }
}
