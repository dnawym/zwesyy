package com.zwesyy.enery.search.matchall;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import com.zwesyy.enery.search.SearchEntry;

/**
 * Match All 查询所有的数据，相当于不带条件查询,
 * 
 * ES服务端默认对查询结果做了分页处理，每页默认的大小为10。如果想自己指定查询的数据，可使用from和size字段，并且按指定的字段排序.
 * 不要把from设得过大（超过10000），否则会导致ES服务端因频繁GC而无法正常提供服务。其实实际项目中也没有谁会翻那么多页，但是为了ES的可用性，务必要对分页查询的页码做一定的限制
 * 
 * @author: zhangyongbin
 * @description:
 * @date: 2018年4月28日
 */
public class MatchAllQuery {

	private String index;

	private String[] types;

	public MatchAllQuery(String index, String... types) {
		this.index = index;
		this.types = types;
	}

	public SearchRequest getMatchAllRequest(int page, int size) {
		SearchRequest searchRequest = new SearchRequest(index);
		searchRequest.types(types);
		SearchEntry entry = new SearchEntry(QueryBuilders.matchAllQuery());
		entry.setSize(size);
		entry.setPage(page);
		SearchSourceBuilder searchSourceBuilder = entry.getSourceBuilder();
		searchRequest.source(searchSourceBuilder);
		return searchRequest;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String[] getTypes() {
		return types;
	}

	public void setTypes(String... types) {
		this.types = types;
	}

}
