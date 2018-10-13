package com.linwei.lw.tablayoutdemo.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.linwei.lw.tablayoutdemo.R;
import com.linwei.lw.tablayoutdemo.Utils.CommentUtils;
import com.linwei.lw.tablayoutdemo.Utils.CustomImageSpan;
import com.linwei.lw.tablayoutdemo.ui.fragment.BaseFragment;
import com.linwei.lw.tablayoutdemo.ui.fragment.FragmentFactory;

public class ImageTabActivity extends AppCompatActivity {
    private TabLayout mTab;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_tab);
        initView();
        initData();
    }

    private void initData() {
        ImagePagerAdapter adapter = new ImagePagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mTab.setupWithViewPager(mViewPager);

    }

    private void initView() {
        mTab = (TabLayout) findViewById(R.id.tab);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
    }

    private class ImagePagerAdapter extends FragmentPagerAdapter {
        public int[] mTitleImage;

        public ImagePagerAdapter(FragmentManager fm) {
            super(fm);
            mTitleImage = new int[]{R.mipmap.topline, R.mipmap.news, R.mipmap.entertainment, R.mipmap.sports};
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Drawable drawable = ContextCompat.getDrawable(ImageTabActivity.this, mTitleImage[position]);
            //Drawable的setBounds方法有四个参数，setBounds(int left, int top, int right, int bottom),
            // 这个四参数指的是drawable将在被绘制在canvas的哪个矩形区域内。
            //getIntrinsicWidth()和getIntrinsicHeight()，顾名思义他们是用来取得Drawable的固有的宽度和高度
            //getIntrinsicWidth()和getIntrinsicHeight()返回的宽高应该是dp为单位的哦。
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

            //自定义imageSpan实现图片与文字的居中对齐
            /*
            四种属性：

Spanned.SPAN_INCLUSIVE_EXCLUSIVE 从起始下标到终了下标，包括起始下标
Spanned.SPAN_INCLUSIVE_INCLUSIVE 从起始下标到终了下标，同时包括起始下标和终了下标
Spanned.SPAN_EXCLUSIVE_EXCLUSIVE 从起始下标到终了下标，但都不包括起始下标和终了下标
Spanned.SPAN_EXCLUSIVE_INCLUSIVE 从起始下标到终了下标，包括终了下标

作者：码农小阿飞CoderMario
链接：https://www.jianshu.com/p/84067ad289d2
來源：简书
简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。
             */
            CustomImageSpan imageSpan = new CustomImageSpan(drawable, CustomImageSpan.ALIGN_FONTCENTER);
            SpannableString spannableString = new SpannableString(" ");
            spannableString.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spannableString;
        }

        @Override
        public BaseFragment getItem(int position) {
            BaseFragment fragment = FragmentFactory.createFragment(position);
            return fragment;
        }

        @Override
        public int getCount() {
            return CommentUtils.TAB_SHORT_COUNT;
        }
    }
}
