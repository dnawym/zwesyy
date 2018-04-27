package com.zwesyy.index;

import java.io.IOException;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.close.CloseIndexRequest;
import org.elasticsearch.action.admin.indices.close.CloseIndexResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexResponse;
import org.elasticsearch.client.RestHighLevelClient;

import com.zwesyy.client.ESCilent;
import com.zwesyy.enery.IndexEntry;

/**
 * 索引 工具类
 *
 * @author: zhangyongbin
 * @description:
 * @date: 2018年4月27日
 */
public class IndexUtil {

	private static RestHighLevelClient client = null;

	static {
		client = ESCilent.getInstance().getClient();
	}
	
	/**
	 * 创建索引
	 */
	public static boolean createIndex(IndexEntry entry) throws IOException {
		CreateIndexRequest request = entry.getCreateRequest();
		CreateIndexResponse response = client.indices().create(request);
		return response.isAcknowledged();
	}

	public static void createIndexAsync(IndexEntry entry, ActionListener listener) throws IOException {
		CreateIndexRequest request = entry.getCreateRequest();
		client.indices().createAsync(request, listener);
	}

	/**
	 * 删除索引
	 * 
	 * @throws IOException
	 */
	public static boolean deleteIndex(IndexEntry entry) throws IOException {
		DeleteIndexRequest request = entry.getDeleteRequest();
		DeleteIndexResponse response = client.indices().delete(request);
		return response.isAcknowledged();
	}

	public static void deleteIndexAsync(IndexEntry entry, ActionListener listener) throws IOException {
		DeleteIndexRequest request = entry.getDeleteRequest();
		client.indices().deleteAsync(request, listener);
	}

	/**
	 * 打开索引
	 */
	public static OpenIndexResponse openIndex(IndexEntry entry) throws IOException {
		OpenIndexRequest request = entry.getOpenRequest();
		OpenIndexResponse response = client.indices().open(request);
		return response;
	}

	public static void openIndexAsync(IndexEntry entry, ActionListener listener) throws IOException {
		OpenIndexRequest request = entry.getOpenRequest();
		client.indices().openAsync(request, listener);
	}

	/**
	 * 关闭索引
	 */
	public static boolean closeIndex(IndexEntry entry) throws IOException {
		CloseIndexRequest request = entry.getCloseRequest();
		CloseIndexResponse response = client.indices().close(request);
		return response.isAcknowledged();
	}

	public static void closeIndexAsync(IndexEntry entry, ActionListener listener) throws IOException {
		CloseIndexRequest request = entry.getCloseRequest();
		client.indices().closeAsync(request, listener);
	}

}
