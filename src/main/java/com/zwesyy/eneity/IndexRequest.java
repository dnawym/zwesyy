package com.zwesyy.eneity;

/**
 * 索引请求信息
 * @author: zhangyongbin
 * @description:
 * @date: 2018年4月25日
 */
public class IndexRequest {
	
	//索引名称
	private String indexName;
	//索引别名
	private String alias;
	//主分片数量
	private int numberOfShards=3;
	//副分片数量
	private int numberOfReplicas=1;
	//索引结构
	private String mappings;
	//索引结构
	private String mappingName;
	//
	private String timeout="2m";
	//
	private String masterNodeTimeout="1m";
	
	private Integer waitForActiveShards;
	
	public IndexRequest() {
		this.timeout ="2m";
		this.masterNodeTimeout ="1m";
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
