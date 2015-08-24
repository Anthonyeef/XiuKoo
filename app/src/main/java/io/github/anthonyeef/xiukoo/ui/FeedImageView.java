package io.github.anthonyeef.xiukoo.ui;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

/**
 * Created by anthonyeef on 8/22/15.
 */
public class FeedImageView extends ImageView{

    private String mUrl;
    private int mDefaultImageId;
    private int mErrorImageId;
    private ResponseObserver mObserver;

    private ImageLoader mImageLoader;
    private ImageLoader.ImageContainer mImageContainer;

    public interface ResponseObserver {
        public void onError();
        public void onSuccess();
    }

    public void setResponseObserver(ResponseObserver observer) {
        mObserver = observer;
    }
    public FeedImageView(Context context) {
        this(context, null);
    }

    public FeedImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FeedImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setImageUrl(String url, ImageLoader imageLoader) {
        mUrl = url;
        mImageLoader = imageLoader;
        loadImageIfNecessary(false);
    }

    public void loadImageIfNecessary(final boolean isInlayoutPass) {
        final int width = getWidth();
        int height = getHeight();

        boolean isFullWrapContent = getLayoutParams() != null
                && getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT
                && getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT;

        if (width == 0 && height == 0 && isFullWrapContent) {
            return;
        }

        if (TextUtils.isEmpty(mUrl)) {
            if (mImageContainer != null) {
                mImageContainer.cancelRequest();
                mImageContainer = null;
            }

            setDefaultImageOrNull();
            return;
        }
        if (mImageContainer != null && mImageContainer.getRequestUrl() != null) {
            if (mImageContainer.getRequestUrl().equals(mUrl)){
                return;
            } else {
                mImageContainer.cancelRequest();
                setDefaultImageOrNull();
            }
        }
        ImageLoader.ImageContainer newContainer = mImageLoader.get(mUrl, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(final ImageLoader.ImageContainer response, boolean isImmediate) {
                if (isImmediate && isInlayoutPass) {
                    post(new Runnable() {
                        @Override
                        public void run() {
                            onResponse(response, false);
                        }
                    });
                    return;
                }
                int bWidth = 0, bHeight = 0;
                if (response.getBitmap() != null) {
                    setImageBitmap(response.getBitmap());
                    bWidth = response.getBitmap().getWidth();
                    bHeight = response.getBitmap().getHeight();
                    adjustImageAspect(bWidth, bHeight);
                } else if (mDefaultImageId != 0) {
                    setImageResource(mDefaultImageId);
                }

                if (mObserver != null) {
                    mObserver.onSuccess();
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                if (mErrorImageId != 0) {
                    setImageResource(mErrorImageId);
                }

                if (mObserver != null) {
                    mObserver.onError();
                }
            }
        });
        mImageContainer = newContainer;
    }

    private void setDefaultImageOrNull() {
        if (mDefaultImageId != 0) {
            setImageResource(mDefaultImageId);
        }else {
            setImageBitmap(null);
        }
    }
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        loadImageIfNecessary(true);
    }

    @Override
    protected void onDetachedFromWindow() {
        if (mImageContainer != null) {
            mImageContainer.cancelRequest();
            setImageBitmap(null);
            mImageContainer = null;
        }
        super.onDetachedFromWindow();
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        invalidate();
    }

    private void adjustImageAspect(int bWidth, int bHeight) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) getLayoutParams();

        if (bWidth == 0 || bWidth == 0) {
            return;
        }

        int swidth =getWidth();
        int new_height = 0;
        params.width = swidth * bHeight / bWidth;
        params.height = new_height;
        setLayoutParams(params);
    }
}
