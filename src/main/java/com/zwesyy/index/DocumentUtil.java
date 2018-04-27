package com.zwesyy.index;

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

import com.zwesyy.client.ESCilent;
import com.zwesyy.enery.DocumentEntry;

/**
 * 文档工具类
 * 
 * @author: zhangyongbin
 * @description:
 * @date: 2018年4月27日
 */
public class DocumentUtil {

	private static RestHighLevelClient client = null;

	static {
		client = ESCilent.getInstance().getClient();
	}

	/**
	 * 创建
	 */
	public static IndexResponse createDoc(DocumentEntry entry, String source) throws IOException {
		IndexRequest request = entry.getCreateRequest();
		request.source(source, XContentType.JSON);
		IndexResponse response = client.index(request);
		return response;
	}

	public static IndexResponse createDoc(DocumentEntry entry, Map<String, Object> source) throws IOException {
		IndexRequest request = entry.getCreateRequest();
		request.source(source);
		IndexResponse response = client.index(request);
		return response;
	}

	public static IndexResponse createDoc(DocumentEntry entry, String... source) throws IOException {
		IndexRequest request = entry.getCreateRequest();
		request.source(source);
		IndexResponse response = client.index(request);
		return response;
	}

	public static void createDocAsyn(DocumentEntry entry, String source, ActionListener listener) throws IOException {
		IndexRequest request = entry.getCreateRequest();
		request.source(source, XContentType.JSON);
		client.indexAsync(request, listener);
	}

	public static void createDocAsyn(DocumentEntry entry, Map<String, Object> source, ActionListener listener) throws IOException {
		IndexRequest request = entry.getCreateRequest();
		request.source(source);
		client.indexAsync(request, listener);
	}

	public static void createDocAsyn(DocumentEntry entry, ActionListener listener, String... source) throws IOException {
		IndexRequest request = entry.getCreateRequest();
		request.source(source);
		client.indexAsync(request, listener);
	}
	
	/**
	 * 获取
	 */
	public static GetResponse getDoc(DocumentEntry entry) throws IOException {
		GetRequest request = entry.getGetRequest();
		GetResponse response = client.get(request);
		return response;
	}

	public static void getDocAsyn(DocumentEntry entry, ActionListener listener) throws IOException {
		GetRequest request = entry.getGetRequest();
		client.getAsync(request, listener);
	}
	
	public static GetResponse getDoc(DocumentEntry entry,boolean realtime, boolean refresh) throws IOException {
		GetRequest request = entry.getGetRequest(realtime,refresh);
		GetResponse response = client.get(request);
		return response;
	}

	public static void getDocAsyn(DocumentEntry entry, boolean realtime, boolean refresh,ActionListener listener) throws IOException {
		GetRequest request = entry.getGetRequest(realtime,refresh);
		client.getAsync(request, listener);
	}

	/**
	 * 删除
	 */
	public static DeleteResponse deleteDoc(DocumentEntry entry) throws IOException {
		DeleteRequest request = entry.getDeleteRequest();
		DeleteResponse response = client.delete(request);
		return response;
	}

	public static void deleteDocAsyn(DocumentEntry entry, ActionListener listener) throws IOException {
		DeleteRequest request = entry.getDeleteRequest();
		client.deleteAsync(request, listener);
	}

	/**
	 * 修改
	 */
	public static UpdateResponse updateDoc(DocumentEntry entry, String source) throws IOException {
		UpdateRequest request = entry.getUpdateRequest();
		request.doc(source, XContentType.JSON);
		UpdateResponse response = client.update(request);
		return response;
	}

	public static UpdateResponse updateDoc(DocumentEntry entry, Map<String, Object> source) throws IOException {
		UpdateRequest request = entry.getUpdateRequest();
		request.doc(source);
		UpdateResponse response = client.update(request);
		return response;
	}

	public static UpdateResponse updateDoc(DocumentEntry entry, String... source) throws IOException {
		UpdateRequest request = entry.getUpdateRequest();
		request.doc(source);
		UpdateResponse response = client.update(request);
		return response;
	}

	public static void updateDocAsyn(DocumentEntry entry, String source, ActionListener listener) throws IOException {
		UpdateRequest request = entry.getUpdateRequest();
		request.doc(source, XContentType.JSON);
		client.updateAsync(request, listener);
	}

	public static void updateDocAsyn(DocumentEntry entry, Map<String, Object> source, ActionListener listener) throws IOException {
		UpdateRequest request = entry.getUpdateRequest();
		request.doc(source);
		client.updateAsync(request, listener);
	}

	public static void updateDocAsyn(DocumentEntry entry, ActionListener listener, String source) throws IOException {
		UpdateRequest request = entry.getUpdateRequest();
		request.doc(source);
		client.updateAsync(request, listener);
	}
	
	public static UpdateResponse updateDoc(DocumentEntry entry,int retryOnConflict, boolean fetchSource, String source) throws IOException {
		UpdateRequest request = entry.getUpdateRequest(retryOnConflict,fetchSource);
		request.doc(source, XContentType.JSON);
		UpdateResponse response = client.update(request);
		return response;
	}

	public static UpdateResponse updateDoc(DocumentEntry entry,int retryOnConflict, boolean fetchSource, Map<String, Object> source) throws IOException {
		UpdateRequest request = entry.getUpdateRequest(retryOnConflict,fetchSource);
		request.doc(source);
		UpdateResponse response = client.update(request);
		return response;
	}

	public static UpdateResponse updateDoc(DocumentEntry entry,int retryOnConflict, boolean fetchSource, String... source) throws IOException {
		UpdateRequest request = entry.getUpdateRequest(retryOnConflict,fetchSource);
		request.doc(source);
		UpdateResponse response = client.update(request);
		return response;
	}

	public static void updateDocAsyn(DocumentEntry entry, String source,int retryOnConflict, boolean fetchSource, ActionListener listener) throws IOException {
		UpdateRequest request = entry.getUpdateRequest(retryOnConflict,fetchSource);
		request.doc(source, XContentType.JSON);
		client.updateAsync(request, listener);
	}

	public static void updateDocAsyn(DocumentEntry entry,int retryOnConflict, boolean fetchSource, Map<String, Object> source, ActionListener listener) throws IOException {
		UpdateRequest request = entry.getUpdateRequest(retryOnConflict,fetchSource);
		request.doc(source);
		client.updateAsync(request, listener);
	}

	public static void updateDocAsyn(DocumentEntry entry,int retryOnConflict, boolean fetchSource, ActionListener listener, String source) throws IOException {
		UpdateRequest request = entry.getUpdateRequest(retryOnConflict,fetchSource);
		request.doc(source);
		client.updateAsync(request, listener);
	}

}
