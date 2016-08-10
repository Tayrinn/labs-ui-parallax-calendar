package ru.yandex.yamblz.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import ru.yandex.yamblz.ui.other.OnScrollChangeListener;

/**
 * Created by Volha on 10.08.2016.
 */

public class ObservableScrollView extends ScrollView {

    private ru.yandex.yamblz.ui.other.OnScrollChangeListener listener;

    public ObservableScrollView(Context context) {
        super(context);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObservableScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        listener.onScrollChanged(l - oldl, t - oldt);
    }

    public void setScrollChangeListener(ru.yandex.yamblz.ui.other.OnScrollChangeListener listener) {
        this.listener = listener;
    }
}
