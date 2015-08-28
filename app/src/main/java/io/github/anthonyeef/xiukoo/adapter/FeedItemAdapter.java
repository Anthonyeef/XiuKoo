package io.github.anthonyeef.xiukoo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;

import io.github.anthonyeef.xiukoo.R;
import io.github.anthonyeef.xiukoo.app.AppController;
import io.github.anthonyeef.xiukoo.model.FeedItem;
import io.github.anthonyeef.xiukoo.ui.FeedImageView;

/**
 * Created by anthonyeef on 8/25/15.
 */
public class FeedItemAdapter extends RecyclerView.Adapter<FeedItemAdapter.FeedViewHolder>{

    private List<FeedItem> feedItemList;
    private Context mContext;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

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

        if (feedItemList.get(i).getImage() == null){
            feedViewHolder.vImageView.setVisibility(View.GONE);
        }else {
            feedViewHolder.vImageView.setImageUrl(feedItemList.get(i).getImage(), imageLoader);
            feedViewHolder.vImageView.setVisibility(View.VISIBLE);

            feedViewHolder.vImageView.setResponseObserver(new FeedImageView.ResponseObserver() {
                @Override
                public void onError() {
                    Log.e("ImageView Error", "遭辣，图片加载不出来辣！");
                }

                @Override
                public void onSuccess() {
                    Log.d("ImageView Success", "Yeah here is the image.");
                }
            });
        }
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
        protected FeedImageView vImageView;

        public FeedViewHolder(View v) {
            super(v);
            vTitle = (TextView) v.findViewById(R.id.title);
            vName = (TextView) v.findViewById(R.id.txtName);
            vSource = (TextView) v.findViewById(R.id.txtSource);
            vTimestamp = (TextView) v.findViewById(R.id.timestamp);
            vContent = (TextView) v.findViewById(R.id.content);
            vImageView = (FeedImageView) v.findViewById(R.id.feedImage);


        }
    }
}
