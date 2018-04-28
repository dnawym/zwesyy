package com.zwesyy.enery.search;

import java.util.concurrent.TimeUnit;

import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;

public class SearchEntry {

	private String index;

	private String[] types;

	private int page = 1;

	private int size = 15;

	private TimeValue timeout = new TimeValue(60, TimeUnit.SECONDS);

	private QueryBuilder query ;

	private SortBuilder<?> sort = new ScoreSortBuilder();
	
	private SortOrder order = SortOrder.ASC;
	// 是否包含_source
	private boolean fetchSource = true;

	private String[] includeFields = {};

	private String[] excludeFields = {};
	
	
	public SearchEntry(String index,String[] types,String name,String text) {
		this.index = index;
		this.types = types;
		this.name = name;
		this.text = text;
	}

	public SearchEntry(String index,String[] types,int page, int size,QueryBuilder query) {
		this.query = query;
		QueryBuilders.matchQuery("user", "kimchy")

	}

	public SearchEntry(int page, int size, SortBuilder<?> sort, boolean fetchSource, String[] includeFields, String[] excludeFields) {
		this.page = page;
		this.size = size;
		this.sort = sort;
		this.fetchSource = fetchSource;
		this.includeFields = includeFields;
		this.excludeFields = excludeFields;
	}

	public SearchSourceBuilder getSourceBuilder() {
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.query(query);
		sourceBuilder.from(getForm());
		sourceBuilder.size(size);
		sourceBuilder.timeout(timeout);
		sourceBuilder.sort(sort);
		sourceBuilder.fetchSource(includeFields, excludeFields);
		return sourceBuilder;
	}

	public int getForm() {
		return (page - 1) * size;
	}

}
