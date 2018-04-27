package com.zwesyy.enery;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;

/**
 * 文档信息实体
 * 
 * @author: zhangyongbin
 * @description:
 * @date: 2018年4月27日
 */
public class DocumentEntry {

	private String indexName;

	private String type;

	private String id;

	private String routing;

	private String parent;

	private String timeout;

	private String RefreshPolicy;

	private Long version;

	private String versionType;

	public DocumentEntry() {
		timeout = "1s";
		RefreshPolicy = "wait_for";
	}

	public IndexRequest getCreateRequest() {
		IndexRequest request = new IndexRequest(indexName, type, id);
		if (StringUtils.isNotBlank(routing))
			request.routing(routing);
		if (StringUtils.isNotBlank(parent))
			request.parent(parent);
		request.timeout(timeout);
		request.setRefreshPolicy(RefreshPolicy);
		if (version != null)
			request.version(version);
		return request;
	}

	public GetRequest getGetRequest(boolean realtime, boolean refresh) {
		GetRequest request = new GetRequest(indexName, type, id);
		if (StringUtils.isNotBlank(routing))
			request.routing(routing);
		if (StringUtils.isNotBlank(parent))
			request.parent(parent);
		if (version != null)
			request.version(version);
		request.realtime(realtime);
		request.refresh(refresh);
		return request;
	}

	public GetRequest getGetRequest() {
		return getGetRequest(true, false);
	}

	public DeleteRequest getDeleteRequest() {
		DeleteRequest request = new DeleteRequest(indexName, type, id);
		if (StringUtils.isNotBlank(routing))
			request.routing(routing);
		if (StringUtils.isNotBlank(parent))
			request.parent(parent);
		request.timeout(timeout);
		request.setRefreshPolicy(RefreshPolicy);
		if (version != null)
			request.version(version);
		return request;
	}

	public UpdateRequest getUpdateRequest(int retryOnConflict, boolean fetchSource) {
		UpdateRequest request = new UpdateRequest(indexName, type, id);
		if (StringUtils.isNotBlank(routing))
			request.routing(routing);
		if (StringUtils.isNotBlank(parent))
			request.parent(parent);
		request.timeout(timeout);
		request.setRefreshPolicy(RefreshPolicy);
		if (version != null)
			request.version(version);

		request.retryOnConflict(retryOnConflict);
		request.fetchSource(fetchSource);
		return request;
	}

	public UpdateRequest getUpdateRequest() {
		return getUpdateRequest(3, true);
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRouting() {
		return routing;
	}

	public void setRouting(String routing) {
		this.routing = routing;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}

	public String getRefreshPolicy() {
		return RefreshPolicy;
	}

	public void setRefreshPolicy(String refreshPolicy) {
		RefreshPolicy = refreshPolicy;
	}

	public String getVersionType() {
		return versionType;
	}

	public void setVersionType(String versionType) {
		this.versionType = versionType;
	}

}
