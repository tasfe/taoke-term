package com.taoke;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.taoke.R;
import com.taobao.top.android.TopAndroidClient;
import com.taobao.top.android.auth.AccessToken;
import com.taobao.top.android.auth.AuthActivity;
import com.taobao.top.android.auth.AuthError;
import com.taobao.top.android.auth.AuthException;
import com.taobao.top.android.auth.AuthorizeListener;
import com.taoke.constant.Constant;
import com.taoke.taobao.TaobaoGetShop;
import com.taoke.view.ImageDownLoadAsyncTask;
import com.taoke.view.LazyScrollView;

public class HomeActivity extends AuthActivity implements LazyScrollView.OnScrollListener{

	private LazyScrollView lazyScrollView;
	private LinearLayout waterfall_container;
	private ArrayList<LinearLayout> linearLayouts;// 列布局

	private LinearLayout progressbar;// 进度条
	//private ViewFlipper viewFlipper;
	private TextView loadtext;// 底部加载view

	private AssetManager assetManager;

	private List<String> image_filenames; // 图片集合
	private ImageDownLoadAsyncTask asyncTask;

	private int current_page = 0;// 页码
	private int count = 20;// 每页显示的个数
	private int column = 3;// 显示列数

	private int item_width;// 每一个item的宽度
	private final String file = "images";
	
	private TopAndroidClient client = TopAndroidClient.getAndroidClientByAppKey(Constant.APP_KEY);
	private Long userId = null;
	private String nick;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		assetManager = this.getAssets();
		// 获取图片集合
		try {
			image_filenames = Arrays.asList(assetManager.list(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 第一次加载
		addImage(current_page, count);
		
	}
	
	/***
	 * init view
	 */
	@SuppressWarnings("deprecation")
	public void initView() {
		setContentView(R.layout.activity_home);
		lazyScrollView = (LazyScrollView) findViewById(R.id.waterfall_scroll);
		lazyScrollView.getView();
		lazyScrollView.setOnScrollListener(this);
		waterfall_container = (LinearLayout) findViewById(R.id.waterfall_container);
		progressbar = (LinearLayout) findViewById(R.id.progressbar);
		loadtext = (TextView) findViewById(R.id.loadtext);

		item_width = getWindowManager().getDefaultDisplay().getWidth() / column;
		linearLayouts = new ArrayList<LinearLayout>();

		// 添加三列到waterfall_container
		for (int i = 0; i < column; i++) {
			LinearLayout layout = new LinearLayout(this);
			LinearLayout.LayoutParams itemParam = new LinearLayout.LayoutParams(item_width, LayoutParams.WRAP_CONTENT);
			layout.setOrientation(LinearLayout.VERTICAL);
			layout.setLayoutParams(itemParam);
			linearLayouts.add(layout);
			waterfall_container.addView(layout);
		}

	}
	
	/***
	 * 加载更多
	 * 
	 * @param current_page
	 * @param count
	 */
	private void addImage(int current_page, int count) {
		int j = 0;
		int imagecount = image_filenames.size();
		for (int i = current_page * count; i < count * (current_page + 1) && i < imagecount; i++) {
			addBitMapToImage(image_filenames.get(i), j, i);
			j++;
			if (j >= column)
				j = 0;
		}

	}
	
	/***
	 * 添加图片到相应image
	 * 
	 * @param string 图片名称
	 * @param j 列
	 * @param i 图片下标
	 */
	private void addBitMapToImage(String imageName, int j, int i) {
		ImageView imageView = getImageview(imageName);
		asyncTask = new ImageDownLoadAsyncTask(this, imageName, imageView,item_width);

		asyncTask.setProgressbar(progressbar);
		asyncTask.setLoadtext(loadtext);
		asyncTask.execute();

		imageView.setTag(i);
		// 添加相应view
		linearLayouts.get(j).addView(imageView);

		imageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				TaobaoGetShop taobaoGetShop = new TaobaoGetShop();
				//if( client != null )
				String url = taobaoGetShop.getShopList(client,userId);
				//Toast.makeText(HomeActivity.this,"您点击了" + v.getTag() + "个Item!\n\r"+taobaoGetShop.getShopList(client,userId), Toast.LENGTH_SHORT).show();
				
				Intent intent=new Intent(HomeActivity.this, BrowserActivity.class);
				Bundle bl = new Bundle();
				bl.putString("url", url);
				intent.putExtras(bl);
				startActivity(intent);
			}
		});
	}
	
	/***
	 * 获取imageview
	 * 
	 * @param imageName
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public ImageView getImageview(String imageName) {
		BitmapFactory.Options options = getBitmapBounds(imageName);
		// 创建显示图片的对象
		ImageView imageView = new ImageView(this);
		LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.FILL_PARENT);
		imageView.setLayoutParams(layoutParams);
		//
		imageView.setMinimumHeight(options.outHeight);
		imageView.setMinimumWidth(options.outWidth);
		imageView.setPadding(2, 0, 2, 2);
		imageView.setBackgroundResource(R.drawable.image_border);
		if (options != null)
			options = null;
		return imageView;
	}

	/***
	 * 
	 * 获取相应图片的 BitmapFactory.Options
	 */
	public BitmapFactory.Options getBitmapBounds(String imageName) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;// 只返回bitmap的大小，可以减少内存使用，防止OOM.
		InputStream is = null;
		try {
			is = assetManager.open(file + "/" + imageName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		BitmapFactory.decodeStream(is, null, options);
		return options;
	}
	
	@Override
	public void onBottom() {
		addImage(++current_page, count);
	}

	@Override
	public void onTop() {

	}

	@Override
	public void onScroll() {
		
	}

	
	@Override
	protected TopAndroidClient getTopAndroidClient() {
		return client;
	}

	@Override
	protected AuthorizeListener getAuthorizeListener() {
		return new AuthorizeListener() {
			
			@Override
			public void onError(AuthError e) {
				Log.e(Constant.TAG, e.getErrorDescription());
			}
			
			@Override
			public void onComplete(AccessToken accessToken) {
				String id = accessToken.getAdditionalInformation().get(AccessToken.KEY_SUB_TAOBAO_USER_ID);
				if(id == null){
					id=accessToken.getAdditionalInformation().get(AccessToken.KEY_TAOBAO_USER_ID);
				}
				HomeActivity.this.userId = Long.parseLong(id);
				nick = accessToken.getAdditionalInformation().get(AccessToken.KEY_SUB_TAOBAO_USER_NICK);
				if(nick == null){
					nick = accessToken.getAdditionalInformation().get(AccessToken.KEY_TAOBAO_USER_NICK);
				}
				String r2_expires = accessToken.getAdditionalInformation().get(AccessToken.KEY_R2_EXPIRES_IN);
				Date start = accessToken.getStartDate();
				Date end = new Date(start.getTime() + Long.parseLong(r2_expires) * 1000L);
			}
			
			@Override
			public void onAuthException(AuthException e) {
				Log.e(Constant.TAG, e.getMessage());
			}
		};
	}
}
