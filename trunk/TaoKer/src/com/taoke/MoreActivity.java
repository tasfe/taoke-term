package com.taoke;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter;

import com.taoke.R;
import com.taoke.view.CornerListView;

public class MoreActivity extends Activity {
	
	private CornerListView mListView = null;
	ArrayList<HashMap<String, String>> map_list1 = null;
	ArrayList<HashMap<String, String>> map_list2 = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more);
		getDataSource1();
		SimpleAdapter adapter1 = new SimpleAdapter(this, map_list1,R.layout.simple_list_item_1, new String[] { "item" },new int[] { R.id.item_title });
		
		mListView = (CornerListView) findViewById(R.id.list1);
		mListView.setAdapter(adapter1);
		mListView.setOnItemClickListener(new OnItemListSelectedListener());

	}

	public ArrayList<HashMap<String, String>> getDataSource1() {

		map_list1 = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map1 = new HashMap<String, String>();
		HashMap<String, String> map2 = new HashMap<String, String>();
		HashMap<String, String> map3 = new HashMap<String, String>();

		map1.put("item", "设置1");
		map2.put("item", "设置2");
		map3.put("item", "设置3");

		map_list1.add(map1);
		map_list1.add(map2);
		map_list1.add(map3);

		return map_list1;
	}

	class OnItemListSelectedListener implements OnItemClickListener {

		@SuppressWarnings("rawtypes")
		@Override
		public void onItemClick(AdapterView arg0, View arg1, int arg2,long arg3) {
			if (arg2 == 0) {
				System.out.println("0");
			}else{
				System.out.println("1");
			}
		}
	}

}
