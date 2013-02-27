package com.taoke.view;

import com.taoke.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * 圆角ListView
 * @author Alone
 * @date 2013-02-04
 */
public class CornerListView extends ListView{
	
	
	public CornerListView(Context context) {
		super(context);
	}
	
	public CornerListView(Context context,AttributeSet attrs) {
		super(context,attrs);
	}
	
	public CornerListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev){
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			int x = (int) ev.getX();
			int y = (int) ev.getY();
			int itemnum = pointToPosition(x, y);
			if (itemnum == AdapterView.INVALID_POSITION){
				break;
			}else {
				if (itemnum == 0) {
					if (itemnum == (getAdapter().getCount() - 1)) {
						setSelector(R.drawable.app_list_corner_round);//只有一项
					} else {
						setSelector(R.drawable.app_list_corner_round_top);//第一项
					}
				} else if (itemnum == (getAdapter().getCount() - 1)){
					setSelector(R.drawable.app_list_corner_round_bottom);//最后一项
				}else {
					setSelector(R.drawable.app_list_corner_round_center);//中间项
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			break;
		}
		return super.onInterceptTouchEvent(ev);
	}

}
