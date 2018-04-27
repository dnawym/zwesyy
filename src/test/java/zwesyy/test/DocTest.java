package zwesyy.test;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;

import com.zwesyy.enery.DocumentEntry;
import com.zwesyy.index.DocumentUtil;

public class DocTest {

	public static void main(String[] args) throws IOException {
		test();
	}

	// @Test
	public static void test() throws IOException {
		
		String index = "zxwyybcon";
		String type="content";
		
		DocumentEntry entry = new DocumentEntry();
		entry.setIndexName(index);
		entry.setType(type);
		

		Map<String, Object> jsonMap = new Hashtable<>();
		// 创建doc
		for (int i = 130; i < 200; i++) {
			jsonMap.put("title", "一个文档不仅仅包含它的数据 ，也包含 元数据 —— 有关 文档的信息"+i);
			jsonMap.put("content", i+"数据可能在索引中只是松散的组合在一起，但是通常明确定义一些数据中的子分区是很有用的。 例如，所有的产品都放在一个索引中，但是你有许多不同的产品类别，比如 \"electronics\" 、 \"kitchen\" 和 \"lawn-care\"。\r\n" + 
					"\r\n" + 
					"这些文档共享一种相同的（或非常相似）的模式：他们有一个标题、描述、产品代码和价格。他们只是正好属于“产品”下的一些子类。\r\n" + 
					"\r\n" + 
					"Elasticsearch 公开了一个称为 types （类型）的特性，它允许您在索引中对数据进行逻辑分区。不同 types 的文档可能有不同的字段，但最好能够非常相似。 我们将在 类型和映射 中更多的讨论关于 types 的一些应用和限制。\r\n" + 
					"\r\n" + 
					"一个 _type 命名可以是大写或者小写，但是不能以下划线或者句号开头，不应该包含逗号， 并且长度限制为256个字符. 我们使用 blog 作为类型名举例");
			jsonMap.put("summary", i+"一个 索引 应该是因共同的特性被分组到一起的文档集合。 例如，你可能存储所有的产品在索引 products 中，而存储所有销售的交易到索引 sales 中。 虽然也允许存储不相关的数据到一个索引中，但这通常看作是一个反模式的做法");
			//jsonMap.put("add_date", new Date());
			IndexResponse createResponse = DocumentUtil.createDoc(entry, jsonMap);
			System.out.println("create doc " + createResponse.getResult());
		}
		
		// 获取
		GetRequest getRequest = new GetRequest(index, type, "131");
		GetResponse getResponse = DocumentUtil.getDoc(entry);
		System.out.println("get doc " + getResponse.getSourceAsString());

		// 更新
		UpdateRequest updateRequest = new UpdateRequest(index, type, "131");

		jsonMap.put("summary", "测试更新");
		UpdateResponse updateResponse = DocumentUtil.updateDoc(entry, jsonMap);
		System.out.println("update doc " + updateResponse.getId());
		// 获取
		getResponse = DocumentUtil.getDoc(entry);
		System.out.println("get doc " + getResponse.getSourceAsString());

		// 删除
		DeleteRequest deleteRequest = new DeleteRequest(index, type, "131");
		DeleteResponse deleteResponse = DocumentUtil.deleteDoc(entry);
		System.out.println("delete doc " + deleteResponse.getId());

		// 获取
		getResponse = DocumentUtil.getDoc(entry);
		System.out.println("get doc " + getResponse.getSourceAsString());
		
	}

}
