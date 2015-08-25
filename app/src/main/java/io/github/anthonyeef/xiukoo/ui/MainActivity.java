package io.github.anthonyeef.xiukoo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import io.github.anthonyeef.xiukoo.R;
import io.github.anthonyeef.xiukoo.model.FeedItem;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ListView listView;
    private List<FeedItem> feedItems;

    private String URL = "http://www.xiukoo.org";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView list = (RecyclerView) findViewById(R.id.list);
        list.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(llm);



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

    public ArrayList<FeedItem> parseFeedItem(String href) {
        ArrayList<FeedItem> feedItemList = new ArrayList<FeedItem>();
        try {

            Document doc = Jsoup.connect(href).timeout(10000).get();
            Elements feedBox = doc.select("div.tie-box");

            for (int i = 0; i < feedBox.size(); i++) {
                FeedItem feedItem = new FeedItem();
                Element feedElement = feedBox.get(i);
                Element titleElement = feedElement.select("h2.tie-title").first();
                Element nameElement = feedElement.select("span.user-name").first();
                Element sourceElement = feedElement.select("span.user-from").first();
                Element timestampElement = feedElement.select("p.tie-date").first();

                String title = titleElement.text();
                String name = nameElement.text();
                String source = sourceElement.text();
                String timestamp = timestampElement.text();

                feedItem.setTitle(title);
                feedItem.setName(name);
                feedItem.setPostTime(timestamp);
                feedItem.setSource(source);

            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return feedItemList;
    }
}
