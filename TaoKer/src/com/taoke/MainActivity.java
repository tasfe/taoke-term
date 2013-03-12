package com.taoke;

import com.taobao.top.android.TopAndroidClient;
import com.taoke.constant.Constant;
import com.taoke.taobao.TaobaoClient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity
{
	TaobaoClient taobaoClient;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button) this.findViewById(R.id.button1);
		taobaoClient = new TaobaoClient();
		Handler handler = new Handler()
		{
			public void handleMessage(android.os.Message msg)
			{
				switch (msg.what)
				{
				case 1:
				{
					Bundle date = msg.getData();
					date.getInt("count");
					
					Intent intent = new Intent(MainActivity.this, BrowserActivity.class);
					Bundle bl = new Bundle();
					bl.putString("url", taobaoClient.listItem.get(0).click_url);
					intent.putExtras(bl);
					startActivity(intent);
				}
					break;
				}
			};
		};

		taobaoClient.setHandler(handler);
		btn.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				taobaoClient.getListItem();
			}
		});
	}
}
