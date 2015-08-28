package io.github.anthonyeef.xiukoo.model;

/**
 * Created by anthonyeef on 8/22/15.
 */
public class FeedItem {
    private String title, name, source, content, postTime, image;

    public FeedItem() {

    }

    public FeedItem(String title, String name, String source, String content, String postTime, String image) {
        super();
        this.title = title;
        this.name = name;
        this.source = source;
        this.content = content;
        this.postTime = postTime;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }
    public void setImage(String image) {
        this.image = image;
    }

    public String getImage(String image) {
        return image;
    }
}
