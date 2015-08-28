package io.github.anthonyeef.xiukoo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import io.github.anthonyeef.xiukoo.R;
import io.github.anthonyeef.xiukoo.adapter.FeedItemAdapter;
import io.github.anthonyeef.xiukoo.app.AppController;
import io.github.anthonyeef.xiukoo.model.FeedItem;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private FeedItemAdapter mFeedItemAdapter;
    private ArrayList<FeedItem> mFeedItems;
    public static final String url = "http://www.xiukoo.org";

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);


        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mFeedItems = new ArrayList<>();

        mFeedItemAdapter = new FeedItemAdapter(this, mFeedItems);

        mRecyclerView.setAdapter(mFeedItemAdapter);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(llm);

        llm.setOrientation(LinearLayoutManager.VERTICAL);


        StringRequest req = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                   parseFeedItem(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error:", error.getMessage());
            }
        });

        AppController.getInstance().addToRequestQueue(req, TAG);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void parseFeedItem(String resource) {
        try {
            Document doc = Jsoup.parse(resource);
            Element masthead = doc.select("div.tie-wrapper").first();
            Elements feedBoxs = masthead.select("div.tie-box");

            Elements userInfo = feedBoxs.select("div.tie-content div.tie-user div.user-info");
            Elements contentInfo = feedBoxs.select("div.tie-content p.content ");

            Elements titleElements = feedBoxs.select("div.tie-header h2.tie-title a");
            Elements nameElements = userInfo.select("p span.user-name");
            Elements sourceElements = userInfo.select("p span.user-form");
            Elements timestampElements = userInfo.select("p.tie-date");
            Elements imageElements = ;



            for (int i = 0; i < feedBoxs.size(); i++) {
                FeedItem feedItem = new FeedItem();

                Element titleElement = titleElements.get(i);
                Element nameElement = nameElements.get(i);
                Element sourceElement = sourceElements.get(i);
                Element timestampElement = timestampElements.get(i);
                Element contentElement = contentInfo.get(i);
                Element imageElement = imageElements.get(i);


                String title = titleElement.text();
                String name = nameElement.text();
                String source = sourceElement.text();
                String timestamp = timestampElement.text();
                String content = contentElement.text();
                String image = imageElement.text();

                feedItem.setTitle(title);
                feedItem.setName(name);
                feedItem.setPostTime(timestamp);
                feedItem.setSource(source);
                feedItem.setContent(content);
                feedItem.setImage(image);


                mFeedItems.add(feedItem);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        mFeedItemAdapter.notifyDataSetChanged();
    }
}
