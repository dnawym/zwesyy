package com.zwesyy.index;

import java.io.IOException;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import com.zwesyy.client.ESCilent;
import com.zwesyy.enery.BulkEntry;

/**
 * The bulk API allows one to index and delete several documents in a single
 * request.
 *
 * 批量操作
 * 
 * @author: zhangyongbin
 * @description:
 * @date: 2018年4月27日
 */
public class BulkUtil {

	private static RestHighLevelClient client = null;

	static {
		client = ESCilent.getInstance().getClient();
	}

	/**
	 * 批量添加
	 */
	public static void createDoc(BulkEntry bulkEntry) throws IOException {
		BulkRequest request = getOpRequest(bulkEntry, OpType.CREATE);
		BulkResponse bulkResponse = client.bulk(request);
	}

	public static void createDocAsync(BulkEntry bulkEntry, ActionListener listener) {
		BulkRequest request = getOpRequest(bulkEntry, OpType.CREATE);
		client.bulkAsync(request, listener);
	}

	/**
	 * 批量修改
	 */
	public static void updateDoc(BulkEntry bulkEntry) throws IOException {
		BulkRequest request = getOpRequest(bulkEntry, OpType.UPDATE);
		BulkResponse bulkResponse = client.bulk(request);
	}

	public static void updateDocAsync(BulkEntry bulkEntry, ActionListener listener) {
		BulkRequest request = getOpRequest(bulkEntry, OpType.UPDATE);
		client.bulkAsync(request, listener);
	}

	/**
	 * 批量删除
	 */
	public static void deleteDoc(String _index, String _type, String... _ids) throws IOException {
		BulkRequest request = new BulkRequest();
		for (String _id : _ids) {
			request.add(new DeleteRequest(_index, _type, _id));
		}

		client.bulk(request);
	}

	public static void deleteDocAsync(String _index, String _type, ActionListener listener, String... _ids) {
		BulkRequest request = new BulkRequest();
		for (String _id : _ids) {
			request.add(new DeleteRequest(_index, _type, _id));
		}

		client.bulkAsync(request, listener);
	}

	enum OpType {

		CREATE(1),

		UPDATE(2);

		private final byte op;

		OpType(int op) {
			this.op = (byte) op;
		}

		public byte getId() {
			return op;
		}
	}

	private static BulkRequest getOpRequest(BulkEntry bulkEntry, OpType opType) {
		BulkRequest request = new BulkRequest();

		for (BulkEntry.BulkSource bulkSource : bulkEntry.getBulkSource()) {

			if (opType.getId() == OpType.CREATE.getId()) {

				IndexRequest indexRequest = new IndexRequest(bulkEntry.getIndex(), bulkEntry.getType(), bulkSource.getId());

				for (int i = 0; i < bulkSource.getSource().length; i++) {
					indexRequest.source(XContentType.JSON, bulkSource.getSource()[i++].toString(), bulkSource.getSource()[i]);
				}

				request.add(indexRequest);

			} else if (opType.getId() == OpType.UPDATE.getId()) {

				UpdateRequest updateRequest = new UpdateRequest(bulkEntry.getIndex(), bulkEntry.getType(), bulkSource.getId());

				for (int i = 0; i < bulkSource.getSource().length; i++) {
					updateRequest.doc(XContentType.JSON, bulkSource.getSource()[i++].toString(), bulkSource.getSource()[i]);
				}

				request.add(updateRequest);

			}

		}
		return request;
	}

	/**
	 * 解析结果
	 */
	// for (BulkItemResponse bulkItemResponse : bulkResponse) {
	// DocWriteResponse itemResponse = bulkItemResponse.getResponse();
	//
	// if (bulkItemResponse.getOpType() == DocWriteRequest.OpType.INDEX
	// || bulkItemResponse.getOpType() == DocWriteRequest.OpType.CREATE) {
	// IndexResponse indexResponse = (IndexResponse) itemResponse;
	//
	// } else if (bulkItemResponse.getOpType() == DocWriteRequest.OpType.UPDATE) {
	// UpdateResponse updateResponse = (UpdateResponse) itemResponse;
	//
	// } else if (bulkItemResponse.getOpType() == DocWriteRequest.OpType.DELETE) {
	// DeleteResponse deleteResponse = (DeleteResponse) itemResponse;
	// }
	// }
}
