package zwesyy.test;

import java.io.IOException;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;

import com.zwesyy.util.es.DocumentUtils;

public class DocTest {

	public static void main(String[] args) throws IOException {
		test();
	}

	// @Test
	public static void test() throws IOException {

		Map<String, Object> jsonMap = new Hashtable<>();
		// 创建doc
		for (int i = 121; i < 130; i++) {
			IndexRequest createRequest = new IndexRequest("zxwyyb", "news", i+"");

			jsonMap.put("title", "The document source can be provided in different ways"+i);
			jsonMap.put("content", i+"The asynchronous method does not block and returns immediately. Once it is completed the ActionListener is called back using the onResponse method if the execution successfully completed or using the onFailure method if it failed.");
			jsonMap.put("summary", i+"The asynchronous execution of an index request requires both the IndexRequest instance and an ActionListener instance to be passed to the asynchronous method:");
			//jsonMap.put("add_date", new Date());
			IndexResponse createResponse = DocumentUtils.createDoc(createRequest, jsonMap);
			System.out.println("create doc " + createResponse.getResult());
		}
		
		// 获取
		GetRequest getRequest = new GetRequest("zxwyyb", "news", "121");
		GetResponse getResponse = DocumentUtils.getDoc(getRequest);
		System.out.println("get doc " + getResponse.getSourceAsString());

		// 更新
		UpdateRequest updateRequest = new UpdateRequest("zxwyyb", "news", "121");

		jsonMap.put("summary", "测试更新");
		UpdateResponse updateResponse = DocumentUtils.updateDoc(updateRequest, jsonMap);
		System.out.println("update doc " + updateResponse.getId());
		// 获取
		getResponse = DocumentUtils.getDoc(getRequest);
		System.out.println("get doc " + getResponse.getSourceAsString());

		// 删除
		DeleteRequest deleteRequest = new DeleteRequest("zxwyyb", "news", "121");
		DeleteResponse deleteResponse = DocumentUtils.deleteDoc(deleteRequest);
		System.out.println("delete doc " + deleteResponse.getId());

		// 获取
		getResponse = DocumentUtils.getDoc(getRequest);
		System.out.println("get doc " + getResponse.getSourceAsString());
		
		DocumentUtils.close();

	}

}
