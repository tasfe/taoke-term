package com.taoke;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TextView;

import com.taoke.R;
import com.taoke.upgrade.UpdateService;

@SuppressWarnings("deprecation")
public class MainTabActivity extends TabActivity {
	
	private TabHost tabHost;
	private TextView main_tab_new_message;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); 
		setContentView(R.layout.activity_main_tab);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);  //titlebar标题栏的布局
		
		main_tab_new_message=(TextView) findViewById(R.id.main_tab_new_message);
        main_tab_new_message.setVisibility(View.VISIBLE);
        main_tab_new_message.setText("10");
        
        tabHost = this.getTabHost();
        TabHost.TabSpec spec;
        Intent intent;
        
        intent = new Intent().setClass(this, MainActivity.class);
        spec = tabHost.newTabSpec("首页").setIndicator("首页").setContent(intent);
        tabHost.addTab(spec);
        
        intent = new Intent().setClass(this,HomeActivity.class);
        spec = tabHost.newTabSpec("分类").setIndicator("分类").setContent(intent);
        tabHost.addTab(spec);
        
        intent = new Intent().setClass(this, MoreActivity.class);
        spec=tabHost.newTabSpec("喜欢").setIndicator("喜欢").setContent(intent);
        tabHost.addTab(spec);
        
        intent = new Intent().setClass(this, MoreActivity.class);
        spec = tabHost.newTabSpec("设置").setIndicator("设置").setContent(intent);
        tabHost.addTab(spec);
        tabHost.setCurrentTab(0);
        
        RadioGroup radioGroup = (RadioGroup) this.findViewById(R.id.main_tab_group);
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        	@Override
        	public void onCheckedChanged(RadioGroup group, int checkedId) {
        		switch (checkedId) {
        		case R.id.radio_button0:
        			tabHost.setCurrentTabByTag("首页");
        			break;
        		case R.id.radio_button1:
        			tabHost.setCurrentTabByTag("分类");
        			break;
        		case R.id.radio_button2:
        			tabHost.setCurrentTabByTag("喜欢");
        			break;
        		case R.id.radio_button3:
        			tabHost.setCurrentTabByTag("设置");
        			break;
        		default:
        			break;
        		}
        	}
		});
        //检查更新
        //checkVersion();
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main_tab, menu);
		return true;
	}
	
	
	private MyApplication application;
	/***
	 * 
	 * 检查是否更新版本
	 * 
	 */
	public void checkVersion() {
		application = (MyApplication) getApplication();
		if (MyApplication.localVersion < MyApplication.serverVersion) {
			// 发现新版本，提示用户更新
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setTitle("软件升级").setMessage("发现新版本,建议立即更新使用.").setPositiveButton("更新",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int which) {
						// 开启更新服务UpdateService
						// 这里为了把update更好模块化，可以传一些updateService依赖的值
						// 如布局ID，资源ID，动态获取的标题,这里以app_name为例
						Intent updateIntent = new Intent(MainTabActivity.this,UpdateService.class);
						updateIntent.putExtra("app_name",getResources().getString(R.string.app_name));
						startService(updateIntent);
					}
				}).setNegativeButton("取消",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int which) {
						dialog.dismiss();
					}
				});
			alert.create().show();
		}
	}
}
