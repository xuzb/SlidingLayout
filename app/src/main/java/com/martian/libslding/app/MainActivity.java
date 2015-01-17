package com.martian.libslding.app;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.martian.libsliding.SlidingLayout;
import com.martian.libsliding.SlidingAdapter;
import com.martian.libsliding.slider.OverlappedSlider;
import com.martian.libsliding.slider.PageSlider;

public class MainActivity extends ActionBarActivity {

    private SlidingLayout mSlidingLayout;
    private boolean mPagerMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mSlidingLayout = (SlidingLayout) findViewById(R.id.sliding_container);
        mSlidingLayout.setOnTapListener(new SlidingLayout.OnTapListener() {

            @Override
            public void onSingleTap(MotionEvent event) {
                int screenWidth = getResources().getDisplayMetrics().widthPixels;;
                int x = (int) event.getX();

                if (x > screenWidth / 2) {
                    mSlidingLayout.slideNext();
                } else if (x <= screenWidth / 2) {
                    mSlidingLayout.slidePrevious();
                }
            }
        });

        // 默认为左右平移模式
        switchSlidingMode();
    }

    private void switchSlidingMode() {
        if (mPagerMode) {
            mSlidingLayout.setAdapter(new TestSlidingAdapter());
            mSlidingLayout.setSlider(new OverlappedSlider());
            Toast.makeText(this, "已切换为左右覆盖模式", Toast.LENGTH_SHORT).show();
        } else {
            mSlidingLayout.setAdapter(new TestSlidingAdapter());
            mSlidingLayout.setSlider(new PageSlider());
            Toast.makeText(this, "已切换为左右平移模式", Toast.LENGTH_SHORT).show();
        }
        mPagerMode = !mPagerMode;
    }

    public void onDownloadClick(View v) {
        Uri uri = Uri.parse("http://openbox.mobilem.360.cn/qcms/view/t/detail?t=2&sid=2369773&from=groupmessage&isappinstalled=0");
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_switch) {
            switchSlidingMode();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    class TestSlidingAdapter extends SlidingAdapter<String> {

        private int index = 0;
        private static final String ADS = "最好的小说导航阅读神器\n淘小说\n";

        @Override
        public View getView(View contentView, String s) {
            if (contentView == null)
                contentView = getLayoutInflater().inflate(R.layout.sliding_content, null);

            TextView tv_content = (TextView) contentView.findViewById(R.id.tv_content);
            tv_content.setText(s);
            return contentView;
        }

        @Override
        public boolean hasNext() {
            return index < 10;
        }

        @Override
        protected void computeNext() {
            ++index;
        }

        @Override
        protected void computePrevious() {
            --index;
        }

        @Override
        public String getNext() {
            return "第 " + (index + 1) + " 页\n\n" + ADS;
        }

        @Override
        public boolean hasPrevious() {
            return index > 0;
        }

        @Override
        public String getPrevious() {
            return "第 " + (index - 1) + " 页\n\n" + ADS;
        }

        @Override
        public String getCurrent() {
            return "第 " + index + " 页\n\n" + ADS;
        }
    }
}
