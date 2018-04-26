package com.zwesyy.util.es;

import java.io.IOException;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.close.CloseIndexRequest;
import org.elasticsearch.action.admin.indices.close.CloseIndexResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.open.OpenIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexResponse;
import org.elasticsearch.action.support.ActiveShardCount;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;

import com.zwesyy.client.ESHighCilent;
import com.zwesyy.eneity.IndexRequest;

/**
 * 索引工具类
 * @author: zhangyongbin
 * @description:
 * @date: 2018年4月25日
 */
public class IndexUtils {
	
	private static RestHighLevelClient client = null;

	static {
		client = ESHighCilent.getClient();
	}

	/**
	 * 创建索引
	 */
	public static boolean createIndex(IndexRequest indexRequest) throws IOException {
		CreateIndexRequest request = new CreateIndexRequest(indexRequest.getIndexName());
		request.settings(Settings.builder().put("index.number_of_shards", indexRequest.getNumberOfShards()).put("index.number_of_replicas", indexRequest.getNumberOfReplicas()));
		request.mapping(indexRequest.getMappingName(), indexRequest.getMappings(), XContentType.JSON);
		request.alias(new Alias(indexRequest.getAlias()));
		request.timeout(indexRequest.getTimeout());
		request.masterNodeTimeout(indexRequest.getMasterNodeTimeout());
		if (indexRequest.getWaitForActiveShards() == null)
			request.waitForActiveShards(ActiveShardCount.DEFAULT);
		else
			request.waitForActiveShards(indexRequest.getWaitForActiveShards());
		
		CreateIndexResponse response = client.indices().create(request);
		return response.isAcknowledged();
	}
	
	public static void createIndexAsyn(IndexRequest indexRequest,ActionListener listener) throws IOException {
		CreateIndexRequest request = new CreateIndexRequest(indexRequest.getIndexName());
		request.settings(Settings.builder().put("index.number_of_shards", indexRequest.getNumberOfShards()).put("index.number_of_replicas", indexRequest.getNumberOfReplicas()));
		request.mapping(indexRequest.getMappingName(), indexRequest.getMappings(), XContentType.JSON);
		request.alias(new Alias(indexRequest.getAlias()));
		request.timeout(indexRequest.getTimeout());
		request.masterNodeTimeout(indexRequest.getMasterNodeTimeout());
		if (indexRequest.getWaitForActiveShards() == null)
			request.waitForActiveShards(ActiveShardCount.DEFAULT);
		else
			request.waitForActiveShards(indexRequest.getWaitForActiveShards());
		
		client.indices().createAsync(request, listener);
	}
	
	/**
	 * 删除索引
	 * @throws IOException 
	 */
	public static boolean deleteIndex(IndexRequest indexRequest) throws IOException {
		DeleteIndexRequest request = new DeleteIndexRequest(indexRequest.getIndexName());
		request.timeout(indexRequest.getTimeout());
		request.masterNodeTimeout(indexRequest.getMasterNodeTimeout());
		request.indicesOptions(IndicesOptions.lenientExpandOpen());
		
		DeleteIndexResponse response = client.indices().delete(request);
		return response.isAcknowledged();
	}
	
	public static void deleteIndexAsyn(IndexRequest indexRequest,ActionListener listener) throws IOException {
		DeleteIndexRequest request = new DeleteIndexRequest(indexRequest.getIndexName());
		request.timeout(indexRequest.getTimeout());
		request.masterNodeTimeout(indexRequest.getMasterNodeTimeout());
		request.indicesOptions(IndicesOptions.lenientExpandOpen());
		
		client.indices().deleteAsync(request,listener);
	}
	
	/**
	 * 打开索引
	 */
	public static OpenIndexResponse openIndex(IndexRequest indexRequest) throws IOException {
		OpenIndexRequest request = new OpenIndexRequest(indexRequest.getIndexName());
		request.timeout(indexRequest.getTimeout());
		request.masterNodeTimeout(indexRequest.getMasterNodeTimeout());
		request.indicesOptions(IndicesOptions.strictExpandOpen());
//		if (indexRequest.getWaitForActiveShards() == null)
//			request.waitForActiveShards(ActiveShardCount.DEFAULT);
//		else
//			request.waitForActiveShards(indexRequest.getWaitForActiveShards());
		
		OpenIndexResponse response = client.indices().open(request);
		return response;
	}
	
	public static void openIndexAsyn(IndexRequest indexRequest,ActionListener listener) throws IOException {
		OpenIndexRequest request = new OpenIndexRequest(indexRequest.getIndexName());
		request.timeout(indexRequest.getTimeout());
		request.masterNodeTimeout(indexRequest.getMasterNodeTimeout());
		request.indicesOptions(IndicesOptions.strictExpandOpen());
		if (indexRequest.getWaitForActiveShards() == null)
			request.waitForActiveShards(ActiveShardCount.DEFAULT);
		else
			request.waitForActiveShards(indexRequest.getWaitForActiveShards());
		
		client.indices().openAsync(request,listener);
	}
	
	/**
	 * 关闭索引
	 */
	public static boolean closeIndex(IndexRequest indexRequest) throws IOException {
		CloseIndexRequest request = new CloseIndexRequest(indexRequest.getIndexName());
		request.timeout(indexRequest.getTimeout());
		request.masterNodeTimeout(indexRequest.getMasterNodeTimeout());
		request.indicesOptions(IndicesOptions.strictExpandOpen());
		
		CloseIndexResponse response = client.indices().close(request);
		return response.isAcknowledged();
	}
	
	public static void closeIndexAsyn(IndexRequest indexRequest,ActionListener listener) throws IOException {
		CloseIndexRequest request = new CloseIndexRequest(indexRequest.getIndexName());
		request.timeout(indexRequest.getTimeout());
		request.masterNodeTimeout(indexRequest.getMasterNodeTimeout());
		request.indicesOptions(IndicesOptions.strictExpandOpen());
		
		client.indices().closeAsync(request,listener);
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
