package ru.yandex.yamblz.ui.fragments;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;

import butterknife.BindView;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.ui.other.OnScrollChangeListener;
import ru.yandex.yamblz.ui.views.ObservableScrollView;

public class ContentFragment extends BaseFragment implements OnScrollChangeListener {

    @BindView(R.id.scrollView)
    ObservableScrollView scrollView;
    @BindView(R.id.image)
    ImageView imageView;
    @BindView(R.id.img_container)
    View imgContainer;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_content, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        scrollView.setScrollChangeListener(this);
    }

    @Override
    public void onScrollChanged(int deltaX, int deltaY) {
        Rect scrollBounds = new Rect();
        scrollView.getHitRect(scrollBounds);
        // Check if image container is visible in the screen
        // so to apply the translation only when the container is visible to the user
        if (imgContainer.getLocalVisibleRect(scrollBounds)) {
            Display display = getActivity().getWindowManager().getDefaultDisplay();
            DisplayMetrics outMetrics = new DisplayMetrics();
            display.getMetrics(outMetrics);
            float density  = getResources().getDisplayMetrics().density;

            float dpHeight = outMetrics.heightPixels / density;
            int screen_height_pixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpHeight, getResources().getDisplayMetrics());
            int half_screen_height = screen_height_pixels / 2;

            int container_height_pixels = imgContainer.getMeasuredHeight();

            int center = half_screen_height - (container_height_pixels / 2);

            // get the location (x,y) of the image container
            int[] loc_screen = {0, 0};
            imgContainer.getLocationOnScreen(loc_screen);

            // trying to transform the current image container location into percentage
            // so when the image container is exaclty in the middle of the screen percentage should be zero
            // and as the image container getting closer to the edges of the screen should increase to 100%
            int final_loc = ((loc_screen[1] - center) * 100) / half_screen_height;

            // translate the inner image taking consideration also the density of the screen
            imageView.setTranslationY(-final_loc * 0.4f * density);
        }
    }
}
