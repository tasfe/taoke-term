package com.taoke.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Scroller;

/***
 * 自定义view
 * 
 * @author Alone
 * 
 */
public class SlidingMenuViewGroup extends ViewGroup {
	private Scroller scroller;// 滑动
	private int distance;// 滑动距离

	private View menu_view, content_view;
	private int duration = 500;

	private ViewTreeObserver viewTreeObserver;
	private Context context;
	private CloseAnimation closeAnimation;

	public static boolean isMenuOpned = false;// 菜单是否打开

	public SlidingMenuViewGroup(Context context) {
		super(context, null);
	}

	public void setCloseAnimation(CloseAnimation closeAnimation) {
		this.closeAnimation = closeAnimation;
	}

	public SlidingMenuViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		scroller = new Scroller(context);
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (changed) {
			menu_view = getChildAt(0);// 获取滑动菜单的view
			content_view = getChildAt(1);// 获得主页view
			// 相当于fill_parent
			content_view.measure(0, 0);
			content_view.layout(0, 0, getWidth(), getHeight());
		}
	}

	@Override
	public void computeScroll() {
		if (scroller.computeScrollOffset()) {
			scrollTo(scroller.getCurrX(), scroller.getCurrY());
			postInvalidate();// 刷新
			if (closeAnimation != null)
				closeAnimation.closeMenuAnimation();
		}
	}

	public void showMenu() {
		isMenuOpned = true;
		scroller.startScroll(getScrollX(), 0, -distance, 0, duration);
		invalidate();// 刷新
	}

	// 关闭菜单（执行自定义动画）
	public void closeMenu() {
		isMenuOpned = false;
		scroller.startScroll(getScrollX(), 0, distance, 0, duration);
		invalidate();// 刷新
	}

	// 关闭菜单（执行自定义动画）
	public void closeMenu_1() {
		isMenuOpned = false;
		scroller.startScroll(getScrollX(), 0, distance - getWidth(), 0,duration);
		invalidate();// 刷新
	}

	// 关闭菜单（执行自定义动画）
	public void closeMenu_2() {
		isMenuOpned = false;
		scroller.startScroll(getScrollX(), 0, getWidth(), 0, duration);
		invalidate();// 刷新
	}

	/***
	 * Menu startScroll(startX, startY, dx, dy)
	 * 
	 * dx=e1的减去e2的x,所以右移为负，左移动为正 dx为移动的距离，如果为正，则标识向左移动|dx|，如果为负，则标识向右移动|dx|
	 */
	public void slidingMenu() {
		// 没有超过半屏
		if (getScrollX() > -getWidth() / 2) {
			scroller.startScroll(getScrollX(), 0, -getScrollX(), 0, duration);
			isMenuOpned = false;
		}else if (getScrollX() <= -getWidth() / 2) {// 超过半屏
			scroller.startScroll(getScrollX(), 0, -(distance + getScrollX()),
					0, duration);
			isMenuOpned = true;
		}
		invalidate();// 刷新
		Log.v("jj", "getScrollX()=" + getScrollX());
	}
	
}


