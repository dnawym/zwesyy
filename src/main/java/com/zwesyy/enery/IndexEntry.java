package com.zwesyy.enery;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.close.CloseIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexRequest;
import org.elasticsearch.action.support.ActiveShardCount;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;

/**
 * 索引信息实体
 * 
 * @author: zhangyongbin
 * @description:
 * @date: 2018年4月25日
 */
public class IndexEntry {
	// 索引名称 索引名必须是小写字母
	private String indexName;
	// 索引别名
	private String alias;
	// 主分片数量
	private int numberOfShards = 3;
	// 副分片数量
	private int numberOfReplicas = 1;
	// 索引结构
	private String mappings;
	// 索引结构
	private String mappingName;
	//
	private String timeout = "2m";
	//
	private String masterNodeTimeout = "1m";

	private Integer waitForActiveShards;

	public IndexEntry() {
		this.timeout = "2m";
		this.masterNodeTimeout = "1m";
	}

	public CreateIndexRequest getCreateRequest() {
		CreateIndexRequest request = new CreateIndexRequest(indexName);
		request.settings(Settings.builder().put("index.number_of_shards", numberOfShards).put("index.number_of_replicas", numberOfReplicas));
		if (StringUtils.isNotBlank(mappingName) && StringUtils.isNotBlank(mappings))
			request.mapping(mappingName, mappings, XContentType.JSON);

		if (StringUtils.isNotBlank(mappingName))
			request.alias(new Alias(alias));

		request.timeout(timeout);
		request.masterNodeTimeout(masterNodeTimeout);
		if (waitForActiveShards == null)
			request.waitForActiveShards(ActiveShardCount.DEFAULT);
		else
			request.waitForActiveShards(waitForActiveShards);
		return request;
	}

	public DeleteIndexRequest getDeleteRequest() {
		DeleteIndexRequest request = new DeleteIndexRequest(indexName);
		request.timeout(timeout);
		request.masterNodeTimeout(masterNodeTimeout);
		request.indicesOptions(IndicesOptions.lenientExpandOpen());
		return request;
	}

	public OpenIndexRequest getOpenRequest() {
		OpenIndexRequest request = new OpenIndexRequest(indexName);
		request.timeout(timeout);
		request.masterNodeTimeout(masterNodeTimeout);
		request.indicesOptions(IndicesOptions.strictExpandOpen());
		return request;
	}

	public CloseIndexRequest getCloseRequest() {
		CloseIndexRequest request = new CloseIndexRequest(indexName);
		request.timeout(timeout);
		request.masterNodeTimeout(masterNodeTimeout);
		request.indicesOptions(IndicesOptions.strictExpandOpen());
		return request;
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public int getNumberOfShards() {
		return numberOfShards;
	}

	public void setNumberOfShards(int numberOfShards) {
		this.numberOfShards = numberOfShards;
	}

	public int getNumberOfReplicas() {
		return numberOfReplicas;
	}

	public void setNumberOfReplicas(int numberOfReplicas) {
		this.numberOfReplicas = numberOfReplicas;
	}

	public String getMappings() {
		return mappings;
	}

	public void setMappings(String mappings) {
		this.mappings = mappings;
	}

	public String getMappingName() {
		return mappingName;
	}

	public void setMappingName(String mappingName) {
		this.mappingName = mappingName;
	}

	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}

	public String getMasterNodeTimeout() {
		return masterNodeTimeout;
	}

	public void setMasterNodeTimeout(String masterNodeTimeout) {
		this.masterNodeTimeout = masterNodeTimeout;
	}

	public Integer getWaitForActiveShards() {
		return waitForActiveShards;
	}

	public void setWaitForActiveShards(Integer waitForActiveShards) {
		this.waitForActiveShards = waitForActiveShards;
	}

}
