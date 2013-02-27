package com.taoke;

import com.taobao.top.android.TopAndroidClient;
import com.taoke.constant.Constant;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

/***
 * 
 * @author Alone
 * 
 */
public class MyApplication extends Application
{

	public static int localVersion = 0;// 本地安装版本

	public static int serverVersion = 2;// 服务器版本

	public static String downloadDir = "Taoke/";// 安装目录

	@Override
	public void onCreate()
	{
		super.onCreate();
		try
		{
			PackageInfo packageInfo = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0);
			localVersion = packageInfo.versionCode;
		} catch (NameNotFoundException e)
		{
			e.printStackTrace();
		}
		//淘宝SDK
		TopAndroidClient.registerAndroidClient(getApplicationContext(), Constant.APP_KEY, Constant.APP_SECRET, "callback://authresult");
	}

}
