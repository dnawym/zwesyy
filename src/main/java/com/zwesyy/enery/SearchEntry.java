package com.zwesyy.enery;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilder;

/**
 * 搜索信息类
 * 
 * @author: zhangyongbin
 * @description:
 * @date: 2018年4月26日
 */
public class SearchEntry {

	private String index;

	private String[] types;

	private String routing;

	private IndicesOptions indicesOptions = IndicesOptions.lenientExpandOpen();

	private String preference;

	public class SourceBuilder {

		private int form;

		private int size;

		private TimeValue timeout = new TimeValue(60, TimeUnit.SECONDS);

		private QueryBuilder query;

		private SortBuilder<?> sort;
		// 是否包含_source
		private boolean fetchSource = true;

		private String[] includeFields;

		private String[] excludeFields;

		public void setForm(int form) {
			this.form = form;
		}

		public void setSize(int size) {
			this.size = size;
		}

		public void setTimeout(TimeValue timeout) {
			this.timeout = timeout;
		}

		public void setQuery(QueryBuilder query) {
			this.query = query;
		}

		public void setSort(SortBuilder<?> sort) {
			this.sort = sort;
		}

		public void setFetchSource(boolean fetchSource) {
			this.fetchSource = fetchSource;
		}

		public void setIncludeFields(String... includeFields) {
			this.includeFields = includeFields;
		}

		public void setExcludeFields(String... excludeFields) {
			this.excludeFields = excludeFields;
		}

		public SearchSourceBuilder getSourceBuilder() {
			SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
			sourceBuilder.query(query);
			sourceBuilder.from(form);
			sourceBuilder.size(size);
			sourceBuilder.timeout(timeout);
			sourceBuilder.sort(sort);
			sourceBuilder.fetchSource(includeFields, excludeFields);
			return sourceBuilder;
		}

//		public SearchSourceBuilder getHighlightingSourceBuilder() {
//			SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//			sourceBuilder.query(query);
//			sourceBuilder.from(form);
//			sourceBuilder.size(size);
//			sourceBuilder.timeout(timeout);
//			sourceBuilder.sort(sort);
//			sourceBuilder.fetchSource(includeFields, excludeFields);
//
//			HighlightBuilder highlightBuilder = new HighlightBuilder();
//			HighlightBuilder.Field highlightTitle = new HighlightBuilder.Field("title");
//			highlightTitle.highlighterType("");
//			highlightBuilder.field(highlightTitle);
//			HighlightBuilder.Field highlightUser = new HighlightBuilder.Field("user");
//			highlightBuilder.field(highlightUser);
//			sourceBuilder.highlighter(highlightBuilder);
//
//			return sourceBuilder;
//		}
	}

	private SourceBuilder sourceBuilder;

	public void setSourceBuilder(SourceBuilder sourceBuilder) {
		this.sourceBuilder = sourceBuilder;
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

	public String getRouting() {
		return routing;
	}

	public void setRouting(String routing) {
		this.routing = routing;
	}

	public IndicesOptions getIndicesOptions() {
		return indicesOptions;
	}

	public void setIndicesOptions(IndicesOptions indicesOptions) {
		this.indicesOptions = indicesOptions;
	}

	public String getPreference() {
		return preference;
	}

	public void setPreference(String preference) {
		this.preference = preference;
	}

	public SearchRequest getSearchRequest() {
		SearchRequest request = new SearchRequest(index);
		request.types(types);

		if (StringUtils.isNotBlank(routing))
			request.routing(routing);

		if (StringUtils.isNotBlank(preference))
			request.preference(preference);

		request.indicesOptions(IndicesOptions.lenientExpandOpen());
		request.source(sourceBuilder.getSourceBuilder());
		return request;
	}

}
