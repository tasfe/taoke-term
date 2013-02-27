/**
 * 
 */
package com.taobao.top.android.sample;

import com.taobao.top.android.TopAndroidClient;
//import com.taobao.top.android.TopAndroidClient.Env;

import android.app.Application;

/**
 * @author junyan.hj
 *
 */
public class SampleApplication extends Application {
	@Override  
	public void onCreate() {  
		super.onCreate();
		TopAndroidClient.registerAndroidClient(getApplicationContext(), "21082926", "f3e4c3f0b794160aabb4d7687e8cc174", "callback://authresult");
		//TopAndroidClient.registerAndroidClient(getApplicationContext(), "519255", "988d57871c1fb8767a9b0875b28e5c17", "callback://authresult",Env.DAILY);
		
	}
}
