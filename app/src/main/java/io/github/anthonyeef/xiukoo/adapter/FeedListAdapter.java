package io.github.anthonyeef.xiukoo.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;

import io.github.anthonyeef.xiukoo.app.AppController;
import io.github.anthonyeef.xiukoo.ui.FeedImageView;
import io.github.anthonyeef.xiukoo.R;
import io.github.anthonyeef.xiukoo.model.FeedItem;

/**
 * Created by anthonyeef on 8/22/15.
 */
public class FeedListAdapter extends BaseAdapter{
    private Activity activity;
    private LayoutInflater inflater;
    private List<FeedItem> feedItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public FeedListAdapter(Activity activity, List<FeedItem> feedItems) {
        this.activity = activity;
        this.feedItems = feedItems;
    }

    @Override
    public int getCount() {
        return feedItems.size();
    }

    @Override
    public Object getItem(int location) {
        return feedItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return  position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.feed_item, null);
        }
        if (imageLoader == null) {
            imageLoader = AppController.getInstance().getImageLoader();
        }

        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView content = (TextView) convertView.findViewById(R.id.txtContent);
        TextView source = (TextView) convertView.findViewById(R.id.txtSource);
        TextView timestamp = (TextView) content.findViewById(R.id.timestamp);

        FeedImageView feedImageView = (FeedImageView) convertView.findViewById(R.id.feedImage);

        FeedItem item = feedItems.get(position);

        title.setText(item.getTitle());
        name.setText(item.getName());
        content.setText(item.getContent());
        source.setText(item.getSource());
        timestamp.setText(item.getPostTime());

        if (item.getImageUrl() != null) {
            feedImageView.setImageUrl(item.getImageUrl(), imageLoader);
            feedImageView.setVisibility(View.VISIBLE);

            feedImageView.setResponseObserver(new FeedImageView.ResponseObserver() {
                @Override
                public void onError() {

                }

                @Override
                public void onSuccess() {

                }
            });
        } else {
            feedImageView.setVisibility(View.GONE);
        }

        return convertView;
    }
}
