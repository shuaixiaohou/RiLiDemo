package com.housaiying.rilidemo.index;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.housaiying.rilidemo.Article;
import com.housaiying.rilidemo.ArticleAdapter;
import com.housaiying.rilidemo.R;
import com.housaiying.rilidemo.base.activity.BaseActivity;
import com.housaiying.rilidemo.colorful.ColorfulActivity;
import com.housaiying.rilidemo.group.GroupItemDecoration;
import com.housaiying.rilidemo.group.GroupRecyclerView;

import java.util.HashMap;
import java.util.Map;

public class IndexActivity extends BaseActivity implements
        CalendarView.OnCalendarSelectListener,
        CalendarView.OnCalendarLongClickListener,
        CalendarView.OnYearChangeListener,
        View.OnClickListener {

    TextView mTextMonthDay;

    TextView mTextYear;

    TextView mTextLunar;


    CalendarView mCalendarView;

    RelativeLayout mRelativeTool;
    CalendarLayout mCalendarLayout;
    GroupRecyclerView mRecyclerView;
    private int mYear;

    public static void show(Context context) {
        context.startActivity(new Intent(context, IndexActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_index;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initView() {
        setStatusBarDarkMode();
        mTextMonthDay = findViewById(R.id.tv_month_day);
        mTextYear = findViewById(R.id.tv_year);
        mTextLunar = findViewById(R.id.tv_lunar);
        mRelativeTool = findViewById(R.id.rl_tool);
        mCalendarView = findViewById(R.id.calendarView);
        mTextMonthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mCalendarLayout.isExpand()) {
                    mCalendarLayout.expand();
                    return;
                }
                mCalendarView.showYearSelectLayout(mYear);
                mTextLunar.setVisibility(View.GONE);
                mTextYear.setVisibility(View.GONE);
                mTextMonthDay.setText(String.valueOf(mYear));
            }
        });
        mCalendarLayout = findViewById(R.id.calendarLayout);
        mCalendarView.setOnCalendarSelectListener(this);
        mCalendarView.setOnYearChangeListener(this);
        mCalendarView.setOnCalendarLongClickListener(this, false);
        mTextYear.setText(String.valueOf(mCalendarView.getCurYear()));
        mYear = mCalendarView.getCurYear();
        mTextMonthDay.setText(mCalendarView.getCurMonth() + "月" + mCalendarView.getCurDay() + "日");
        mTextLunar.setText("今日");
    }

    @Override
    protected void initData() {

        int year = mCalendarView.getCurYear();
        int month = mCalendarView.getCurMonth();

        Map<String, Calendar> map = new HashMap<>();
        map.put(getSchemeCalendar(year, month, 3, 0xFF40db25).toString(),
                getSchemeCalendar(year, month, 3, 0xFF40db25));
        map.put(getSchemeCalendar(year, month, 6, 0xFFe69138).toString(),
                getSchemeCalendar(year, month, 6, 0xFFe69138));
        map.put(getSchemeCalendar(year, month, 9, 0xFFdf1356).toString(),
                getSchemeCalendar(year, month, 9, 0xFFdf1356));
        map.put(getSchemeCalendar(year, month, 13, 0xFFedc56d).toString(),
                getSchemeCalendar(year, month, 13, 0xFFedc56d));
        map.put(getSchemeCalendar(year, month, 14, 0xFFedc56d).toString(),
                getSchemeCalendar(year, month, 14, 0xFFedc56d));
        map.put(getSchemeCalendar(year, month, 15, 0xFFaacc44).toString(),
                getSchemeCalendar(year, month, 15, 0xFFaacc44));
        map.put(getSchemeCalendar(year, month, 18, 0xFFbc13f0).toString(),
                getSchemeCalendar(year, month, 18, 0xFFbc13f0));
        map.put(getSchemeCalendar(year, month, 25, 0xFF13acf0).toString(),
                getSchemeCalendar(year, month, 25, 0xFF13acf0));
        map.put(getSchemeCalendar(year, month, 27, 0xFF13acf0).toString(),
                getSchemeCalendar(year, month, 27, 0xFF13acf0));
        //此方法在巨大的数据量上不影响遍历性能，推荐使用
        mCalendarView.setSchemeDate(map);


        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new GroupItemDecoration<String, Article>());
        mRecyclerView.setAdapter(new ArticleAdapter(this));
        mRecyclerView.notifyDataSetChanged();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_colorful:
                ColorfulActivity.show(this);
                break;
            case R.id.ll_index:
                IndexActivity.show(this);
                break;
        }
    }

    private Calendar getSchemeCalendar(int year, int month, int day, int color) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
     //   calendar.setScheme(text);
        return calendar;
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        mTextLunar.setVisibility(View.VISIBLE);
        mTextYear.setVisibility(View.VISIBLE);
        mTextMonthDay.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
        mTextYear.setText(String.valueOf(calendar.getYear()));
//        mTextLunar.setText(calendar.getLunar());
        mYear = calendar.getYear();
    }

    @Override
    public void onCalendarLongClickOutOfRange(Calendar calendar) {

    }

    @Override
    public void onCalendarLongClick(Calendar calendar) {
        Log.e("onDateLongClick", "  -- " + calendar.getDay() + "  --  " + calendar.getMonth());
    }

    @Override
    public void onYearChange(int year) {
        mTextMonthDay.setText(String.valueOf(year));
    }


}
