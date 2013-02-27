package com.taoke;

import com.taoke.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * 
 * @author Alone
 *
 */
public class LoadActivity extends Activity {

	private final int SPLASH_DISPLAY_LENGHT = 1000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_load);
		
		new Handler().postDelayed(new Runnable(){  
			@Override  
	        public void run() {  
	            Intent mainIntent = new Intent(LoadActivity.this,MainTabActivity.class);  
	            LoadActivity.this.startActivity(mainIntent);  
	            LoadActivity.this.finish();  
	        }   
		}, SPLASH_DISPLAY_LENGHT);  
	}
}
