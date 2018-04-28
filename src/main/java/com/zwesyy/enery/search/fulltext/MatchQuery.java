package com.zwesyy.enery.search.fulltext;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

/**
 * Match : The match family of queries does not go through a "query parsing"
 * process. It does not support field name prefixes, wildcard characters, or
 * other "advanced" features. For this reason, chances of it failing are very
 * small / non existent, and it provides an excellent behavior when it comes to
 * just analyze and run that text as a query behavior (which is usually what a
 * text search box does). Also, the phrase_prefix type can provide a great "as
 * you type" behavior to automatically load search results.
 * 
 * @author: zhangyongbin
 * @description:
 * @date: 2018年4月28日
 */
public class MatchQuery {

	private String index;

	private String[] types;

	private String minimumShouldMatch;
	// 字段
	private String field;
	// 值
	private Object text;

	public MatchQuery(String index, String field, Object text, String... types) {
		this.index = index;
		this.types = types;
		this.field = field;
		this.text = text;
	}

	public MatchQuery(String index, String field, Object text, String minimumShouldMatch, String... types) {
		this.index = index;
		this.types = types;
		this.field = field;
		this.minimumShouldMatch = minimumShouldMatch;

	}

	public SearchRequest getSearchRequest() {
		return match(field, text);
	}

	private SearchRequest match(String name, Object value) {
		SearchRequest searchRequest = new SearchRequest(index);
		searchRequest.types(types);
		MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(name, value);
		if (StringUtils.isNotBlank(minimumShouldMatch))
			matchQueryBuilder.minimumShouldMatch(minimumShouldMatch);
		return searchRequest;
	}

}
