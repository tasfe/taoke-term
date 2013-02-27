package com.taoke;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.taoke.R;
import com.taoke.constant.Constant;
import com.taoke.view.PwsListView;

/**
 * 
 * @author Alone
 *
 */
public class MainActivity extends Activity {
	
	private ViewPager viewPager;
	private PwsListView lv_main;
	private LinearLayout ll_point;
	private FrameLayout frameLayout;
	private ArrayList<View> arrayList;
	private int image_id[] = { R.drawable.a, R.drawable.b, R.drawable.c };
	private int frameheight;// 图片的高度
	private ArrayList<ImageView> imageViews;
	private Timer timer;
	private LayoutInflater layoutInflater;
	private int window_width;

	private final String msg[] = { "one", "two", "three", "four", "five","six", "seven" };

	private View view;// 此时触摸的view
	
	/***
	 * 初始化 point
	 */
	int i;
	void initPoint() {
		imageViews = new ArrayList<ImageView>();
		ImageView imageView;

		for (i = 0; i < image_id.length; i++) {
			imageView = new ImageView(this);
			imageView.setBackgroundResource(R.drawable.indicator);
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
			layoutParams.leftMargin = 10;
			layoutParams.rightMargin = 10;
			ll_point.addView(imageView, layoutParams);
			imageViews.add(imageView);
		}
	}
	
	/***
	 * 初始化 PagerChild
	 */
	void initPagerChild() {
		arrayList = new ArrayList<View>();
		for (int i = 0; i < image_id.length; i++) {
			ImageView imageView = new ImageView(MainActivity.this);
			imageView.setScaleType(ScaleType.FIT_XY);
			Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
					image_id[i]);
			Bitmap bitmap2 = getBitmap(bitmap, window_width);
			frameheight = bitmap2.getHeight();// 获取要显示的高度
			Log.e(Constant.TAG, "frameheight=" + frameheight);
			imageView.setImageBitmap(bitmap2);
			arrayList.add(imageView);
		}
		initPoint();
	}
	
	/***
	 * 初始化 HeadImage
	 */
	void initHeadImage() {
		layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View headview = layoutInflater.inflate(R.layout.head_image, null);
		viewPager = (ViewPager) headview.findViewById(R.id.viewpager);
		ll_point = (LinearLayout) headview.findViewById(R.id.ll_point);
		frameLayout = (FrameLayout) headview.findViewById(R.id.fl_main);
		initPagerChild();
		LayoutParams layoutParams = frameLayout.getLayoutParams();
		layoutParams.height = frameheight;
		frameLayout.setLayoutParams(layoutParams);

		viewPager.setAdapter(new ViewPagerAdapter(arrayList));
		draw_Point(0);// 默认首次进入
		lv_main.addHeaderView(headview);// 要卸载setAdapter前面
		lv_main.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, msg));
		lv_main.setViewPager(viewPager);
	}
	
	/***
	 * init view
	 */
	void initView() {
		setTitle("jjhappyforever...");
		setContentView(R.layout.activity_main);
		lv_main = (PwsListView) findViewById(R.id.lv_main);
		lv_main.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				if (position != 0)
					Toast.makeText(MainActivity.this, msg[position - 1], 1).show();
			}
		});
		initHeadImage();
	}
	
	/***
	 * 更新选中点
	 * 
	 * @param index
	 */
	private void draw_Point(int index) {
		for (int i = 0; i < imageViews.size(); i++) {
			imageViews.get(i).setImageResource(R.drawable.indicator);
		}
		imageViews.get(index).setImageResource(R.drawable.indicator_focused);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 获取屏幕的宽度
		window_width = (int) getResources().getDimension(R.dimen.window_width);
		initView();

		timer = new Timer(true);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						int index = viewPager.getCurrentItem();
						if (index == arrayList.size() - 1)
							index = 0;
						else
							index++;
						viewPager.setCurrentItem(index);

					}
				});
			}
		}, 5000, 5000);
		
		/***
		 * viewpager
		 * 
		 * PageChangeListener
		 */
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				draw_Point(arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}
	
	/***
	 * 对图片处理
	 * 
	 * @author zhangjia
	 * 
	 */
	Bitmap getBitmap(Bitmap bitmap, int width) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scale = (float) width / w;
		// 保证图片不变形.
		matrix.postScale(scale, scale);
		// w,h是原图的属性.
		return Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
	}

	public class ViewPagerAdapter extends PagerAdapter {
		// 界面列表
		private List<View> views;

		public ViewPagerAdapter(List<View> views) {
			this.views = views;
		}

		// 销毁arg1位置的界面
		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(views.get(arg1));
		}

		// 获得当前界面数
		@Override
		public int getCount() {
			if (views != null) {
				// 返回一个比较大的数字
				return views.size();
			}
			return 0;
		}

		// 初始化arg1位置的界面
		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(views.get(arg1));
			return views.get(arg1);
		}

		// 判断是否由对象生成界面
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return (arg0 == arg1);
		}
	}
}
