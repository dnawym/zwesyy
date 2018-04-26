package com.zwesyy.util.es;

import org.elasticsearch.client.RestHighLevelClient;

import com.zwesyy.client.ESHighCilent;

/**
 * 搜索工具类
 * @author: zhangyongbin
 * @description:
 * @date: 2018年4月26日
 */
public class SearchUtils {
	
	private static RestHighLevelClient client = null;

	static {
		client = ESHighCilent.getClient();
	}
	
	
	

}
