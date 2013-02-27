package com.taoke.taobao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;
import android.util.Log;

import com.taobao.top.android.TopAndroidClient;
import com.taobao.top.android.TopParameters;
import com.taobao.top.android.api.ApiError;
import com.taobao.top.android.api.TopApiListener;
import com.taoke.constant.Constant;
import com.taoke.util.TaobaokeItem;

/**
 * 
 * @author Alone
 *
 */
public class TaobaoGetShop
{

	private String mes;
	protected List<TaobaokeItem> listData = new ArrayList<TaobaokeItem>();

	public String getShopList(TopAndroidClient client, Long userId)
	{
		TopParameters params = new TopParameters();
		//API方法
		params.setMethod("taobao.taobaoke.items.get");
		//返回字段
		params.addFields("num_iid,title,nick,pic_url,price,click_url,commission,commission_rate,commission_num,commission_volume,shop_click_url,seller_credit_score,item_location,volume");
		//封装参数
		Map<String, String> map = new HashMap<String, String>();
		map.put("is_mobile", "true");
		map.put("page_size", "40");
		map.put("cid", "50004887");
		params.setParams(map);

		client.api(params, userId, new TopApiListener()
		{
			@Override
			public void onException(Exception e)
			{
				Log.e(Constant.TAG, e.getMessage());
			}

			@Override
			public void onError(ApiError error)
			{
				Log.e(Constant.TAG, error.toString());
			}

			@Override
			public void onComplete(JSONObject json)
			{
				try
				{
					Log.e(Constant.TAG, json.toString());
					TaobaokeItem item = null;
					JSONObject j1 = json.getJSONObject("taobaoke_items_get_response");
					JSONObject j2 = j1.getJSONObject("taobaoke_items");
					JSONArray jsonArray = j2.getJSONArray("taobaoke_item");
					for (int i = 0; i < jsonArray.length(); i++)
					{
						item = new TaobaokeItem();
						item.num_iid = jsonArray.getJSONObject(i).optLong("num_iid");
						item.click_url = jsonArray.getJSONObject(i).optString("click_url");
						mes = item.click_url;
						Log.e(Constant.TAG, mes);

						listData.add(item);
					}

				} catch (JSONException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, true);
		return mes;
	}
}
