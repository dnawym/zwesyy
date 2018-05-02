package com.zwesyy.enery.search;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilder;

/**
 * 搜索信息类
 * 
 * @author: zhangyongbin
 * @description:
 * @date: 2018年4月26日
 */
public class SearchEntry {
	// 索引名必须是小写字母
	private String index;
	// 类型是索引内部的逻辑分区
	private String[] types;

	private String routing;

	private IndicesOptions indicesOptions = IndicesOptions.lenientExpandOpen();

	private String preference;

	private SourceBuilder sourceBuilder;

	public class SourceBuilder {

		private int page = 1;

		private int size = 15;

		private TimeValue timeout = new TimeValue(60, TimeUnit.SECONDS);

		private QueryBuilder query;

		private SortBuilder<?> sort;
		// 是否包含_source
		private boolean fetchSource = true;

		private String[] includeFields;

		private String[] excludeFields;

		public void setPage(int page) {
			this.page = page;
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
			sourceBuilder.from((page - 1) * size);
			sourceBuilder.size((page + 1) * size - 1);
			sourceBuilder.timeout(timeout);
			sourceBuilder.sort(sort);
			if (includeFields != null && excludeFields != null)
				sourceBuilder.fetchSource(includeFields, excludeFields);
			else
				sourceBuilder.fetchSource(fetchSource);
			return sourceBuilder;
		}
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

	public SourceBuilder getSourceBuilder() {
		return sourceBuilder;
	}

	public void setSourceBuilder(SourceBuilder sourceBuilder) {
		this.sourceBuilder = sourceBuilder;
	}

}
