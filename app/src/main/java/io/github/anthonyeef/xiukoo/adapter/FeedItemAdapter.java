package io.github.anthonyeef.xiukoo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.github.anthonyeef.xiukoo.R;
import io.github.anthonyeef.xiukoo.model.FeedItem;

/**
 * Created by anthonyeef on 8/25/15.
 */
public class FeedItemAdapter extends RecyclerView.Adapter<FeedItemAdapter.FeedViewHolder>{

    private List<FeedItem> feedItemList;
    private Context mContext;

    public FeedItemAdapter(Context context,List<FeedItem> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
    }

    @Override
    public int getItemCount() {
        return feedItemList.size();
    }

    @Override
    public void onBindViewHolder(FeedViewHolder feedViewHolder, int i) {
        feedViewHolder.vTitle.setText(feedItemList.get(i).getTitle());
        feedViewHolder.vName.setText(feedItemList.get(i).getName());
        feedViewHolder.vSource.setText(feedItemList.get(i).getSource());
        feedViewHolder.vTimestamp.setText(feedItemList.get(i).getPostTime());
        feedViewHolder.vContent.setText(feedItemList.get(i).getContent());
    }
    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.feed_item, viewGroup, false);

        return new FeedViewHolder(itemView);
    }

    public static class FeedViewHolder extends RecyclerView.ViewHolder {

        protected TextView vTitle;
        protected TextView vName;
        protected TextView vSource;
        protected TextView vTimestamp;
        protected TextView vContent;

        public FeedViewHolder(View v) {
            super(v);
            vTitle = (TextView) v.findViewById(R.id.title);
            vName = (TextView) v.findViewById(R.id.txtName);
            vSource = (TextView) v.findViewById(R.id.txtSource);
            vTimestamp = (TextView) v.findViewById(R.id.timestamp);
            vContent = (TextView) v.findViewById(R.id.content);
        }
    }
}
