package com.taoke;

import com.taobao.top.android.TopAndroidClient;
import com.taoke.constant.Constant;
import com.taoke.taobao.TaobaoGetShop;

import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;

/***
 * 
 * @author Alone
 * 
 */
public class MyApplication extends Application
{
		@Override
	public void onCreate()
	{
		super.onCreate();
		
		//淘宝SDK
		TopAndroidClient.registerAndroidClient(getApplicationContext(), Constant.APP_KEY, Constant.APP_SECRET, "callback://authresult");
	}

}
