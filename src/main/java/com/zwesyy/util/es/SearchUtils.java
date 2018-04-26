package com.zwesyy.util.es;

import java.io.IOException;
import java.util.HashMap;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import com.zwesyy.client.ESHighCilent;
import com.zwesyy.eneity.SearchQuery;

/**
 * 搜索工具类
 * 
 * @author: zhangyongbin
 * @description:
 * @date: 2018年4月26日
 */
public class SearchUtils {

	private static RestHighLevelClient client = null;

	static {
		client = ESHighCilent.getClient();
	}

	/**
	 * search
	 */
	public static SearchResponse search(SearchQuery query) throws IOException {
		SearchRequest request = query.getRequest();
		SearchResponse response = client.search(request);
		return response;
	}

	public static void searchAsyn(SearchQuery query, ActionListener listener) throws IOException {
		SearchRequest request = query.getRequest();
		client.searchAsync(request, listener);
	}
	
	public static void close() {
		if(client!=null)
			try {
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

}
