package com.zwesyy.index;

import java.io.IOException;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;

import com.zwesyy.client.ESCilent;
import com.zwesyy.enery.SearchEntry;
import com.zwesyy.enery.search.matchall.MatchAllQuery;

/**
 * search
 * 
 * @author: zhangyongbin
 * @description:
 * @date: 2018年4月27日
 */
public class SearchUtil {
	
	private static RestHighLevelClient client = null;

	static {
		client = ESCilent.getInstance().getClient();
	}
	
	/**
	 * 查询全部
	 */
	public static SearchResponse matchAll(MatchAllQuery query) throws IOException {
		SearchRequest request = query.getSearchRequest();
		SearchResponse response = client.search(request);
		return response;
	}
	
	
	
	

	/**
	 * 
	 */
	public static SearchResponse search(SearchEntry query) throws IOException {
		SearchRequest request = query.getSearchRequest();
		SearchResponse response = client.search(request);
		return response;
	}

	public static void searchAsyn(SearchEntry query, ActionListener listener) throws IOException {
		SearchRequest request = query.getSearchRequest();
		client.searchAsync(request, listener);
	}
	
	/**
	 * Highlighting search 
	 */
	
	
	

}
