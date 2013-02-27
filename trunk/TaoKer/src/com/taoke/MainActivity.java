package com.taoke;

import com.taobao.top.android.TopAndroidClient;
import com.taoke.constant.Constant;
import com.taoke.taobao.TaobaoGetShop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button) this.findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				test();
			}
		});
		test();

	}

	private void test()
	{
		TopAndroidClient client = TopAndroidClient.getAndroidClientByAppKey(Constant.APP_KEY);

		TaobaoGetShop taobaoGetShop = new TaobaoGetShop();
		String url = taobaoGetShop.getShopList(client, null);
		Intent intent = new Intent(this, BrowserActivity.class);
		Bundle bl = new Bundle();
		bl.putString("url", url);
		intent.putExtras(bl);
		startActivity(intent);

	}
}
