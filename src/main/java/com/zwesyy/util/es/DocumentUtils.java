package com.zwesyy.util.es;

import java.io.IOException;
import java.util.Map;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;

import com.zwesyy.client.ESHighCilent;

/**
 * 文档工具类
 * 
 * @author: zhangyongbin
 * @description:
 * @date: 2018年4月25日
 */
public class DocumentUtils {

	private static RestHighLevelClient client = null;

	static {
		client = ESHighCilent.getClient();
	}

	/**
	 * 创建
	 */
	public static IndexResponse createDoc(IndexRequest request, String source) throws IOException {
		request.source(source, XContentType.JSON);
		IndexResponse response = client.index(request);
		return response;
	}

	public static IndexResponse createDoc(IndexRequest request, Map<String, Object> jsonMap) throws IOException {
		request.source(jsonMap);
		IndexResponse response = client.index(request);
		return response;
	}

	public static IndexResponse createDoc(IndexRequest request, XContentBuilder builder) throws IOException {
		request.source(builder);
		IndexResponse response = client.index(request);
		return response;
	}

	public static void createDocAsyn(IndexRequest request, String source, ActionListener listener) throws IOException {
		request.source(source, XContentType.JSON);
		client.indexAsync(request, listener);
	}

	public static void createDocAsyn(IndexRequest request, Map<String, Object> jsonMap, ActionListener listener) throws IOException {
		request.source(jsonMap);
		client.indexAsync(request, listener);
	}

	public static void createDocAsyn(IndexRequest request, XContentBuilder builder, ActionListener listener) throws IOException {
		request.source(builder);
		client.indexAsync(request, listener);
	}

	/**
	 * 获取
	 */
	public static GetResponse getDoc(GetRequest request) throws IOException {
		GetResponse response = client.get(request);
		return response;
	}

	public static void getDocAsyn(GetRequest request, ActionListener listener) throws IOException {
		client.getAsync(request, listener);
	}

	/**
	 * 删除
	 */
	public static DeleteResponse deleteDoc(DeleteRequest request) throws IOException {
		DeleteResponse response = client.delete(request);
		return response;
	}

	public static void deleteDocAsyn(DeleteRequest request, ActionListener listener) throws IOException {
		client.deleteAsync(request, listener);
	}
	
	/**
	 * 修改
	 */
	public static UpdateResponse updateDoc(UpdateRequest  request, String source) throws IOException {
		request.doc(source, XContentType.JSON);
		UpdateResponse response = client.update(request);
		return response;
	}

	public static UpdateResponse updateDoc(UpdateRequest  request, Map<String, Object> jsonMap) throws IOException {
		request.doc(jsonMap);
		UpdateResponse response = client.update(request);
		return response;
	}

	public static UpdateResponse updateDoc(UpdateRequest  request, XContentBuilder builder) throws IOException {
		request.doc(builder);
		UpdateResponse response = client.update(request);
		return response;
	}

	public static void updateDocAsyn(UpdateRequest  request, String source, ActionListener listener) throws IOException {
		request.doc(source, XContentType.JSON);
		client.updateAsync(request, listener);
	}

	public static void updateDocAsyn(UpdateRequest  request, Map<String, Object> jsonMap, ActionListener listener) throws IOException {
		request.doc(jsonMap);
		client.updateAsync(request, listener);
	}

	public static void updateDocAsyn(UpdateRequest  request, XContentBuilder builder, ActionListener listener) throws IOException {
		request.doc(builder);
		client.updateAsync(request, listener);
	}
	
	/**
	 * 批量操作
	 */
	public static BulkResponse bulkDoc(BulkRequest   request) throws IOException {
		BulkResponse response = client.bulk(request);
		return response;
	}

	public static void bulkDocAsyn(BulkRequest   request, ActionListener listener) throws IOException {
		client.bulkAsync(request, listener);
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
