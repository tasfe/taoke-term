package com.taoke.view;

import com.taoke.constant.Constant;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 左右切换图片
 * @author Alone
 *
 */
public class PwsListView extends ListView {
	private GestureDetector mGestureDetector;
	View.OnTouchListener mGestureListener;
	private Context context;
	private ViewPager viewPager;

	public void setViewPager(ViewPager viewPager) {
		this.viewPager = viewPager;
	}

	public PwsListView(Context context) {
		super(context);
		this.context = context;
	}

	@SuppressWarnings("deprecation")
	public PwsListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		mGestureDetector = new GestureDetector(new YScrollDetector());
		setFadingEdgeLength(0);

	}

	public PwsListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		super.onInterceptTouchEvent(ev);
		return mGestureDetector.onTouchEvent(ev);
	}

	class YScrollDetector extends SimpleOnGestureListener {
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			if (Math.abs(distanceY) >= Math.abs(distanceX)) {
				Log.e(Constant.TAG, "上下....");
				return true;
			}
			Log.e(Constant.TAG, "左右....");
			return false;
		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			Toast.makeText(context, "图" + viewPager.getCurrentItem(), 1).show();
			return super.onSingleTapUp(e);
		}
	}
}