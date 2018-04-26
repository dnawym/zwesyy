package zwesyy.test;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.RequestLine;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.open.OpenIndexResponse;
import org.elasticsearch.action.support.ActiveShardCount;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;

import com.zwesyy.client.ESHighCilent;
import com.zwesyy.client.ESLowCilent;
import com.zwesyy.eneity.IndexRequest;
import com.zwesyy.util.es.IndexUtils;

public class IndexTest {

	public static void main(String[] args) throws Exception {

//		String indexName = "aiyy";
//
//		RestClient client = ESLowCilent.getClient();
//
//		Response response = client.performRequest("GET", "/");
//		RequestLine requestLine = response.getRequestLine(); // (1) 请求的信息
//		HttpHost host = response.getHost(); // (2) 响应的主机信息
//		int statusCode = response.getStatusLine().getStatusCode(); // (3) 响应状态行，例如你可以从中获取响应状态码
//		Header[] headers = response.getHeaders(); // (4) 响应头信息，也可以使用getHeader(String)通过名称获取具体的响应头
//		String responseBody = EntityUtils.toString(response.getEntity()); // (5) 响应体信息，在org.apache.http.HttpEntity对象中
//
//		//
//		System.out.println(responseBody);
//
//		RestHighLevelClient highClient = ESHighCilent.getClient();
//
//		CreateIndexRequest request = new CreateIndexRequest("twitter");
//		request.settings(Settings.builder().put("index.number_of_shards", 3).put("index.number_of_replicas", 2));
//
//		request.mapping("tweet", "  {\n" + "    \"tweet\": {\n" + "      \"properties\": {\n" + "        \"message\": {\n" + "          \"type\": \"text\"\n" + "        }\n" + "      }\n" + "    }\n" + "  }", XContentType.JSON);
//
//		request.alias(new Alias("twitter_alias"));
//		
//		request.timeout(TimeValue.timeValueMinutes(2)); 
//		request.timeout("2m"); 
//		
//		request.masterNodeTimeout(TimeValue.timeValueMinutes(1)); 
//		request.masterNodeTimeout("1m"); 
//		
//		request.waitForActiveShards(2); 
//		request.waitForActiveShards(ActiveShardCount.DEFAULT); 
//		
//		CreateIndexResponse createIndexResponse = highClient.indices().create(request);
//
//		System.out.println(createIndexResponse);
//		
//		client.close();
//		highClient.close();
		test();
	}
	
	
	//@Test
	public static void test() throws IOException {
		String mappings="{ \r\n" + 
				"\"news\":{\r\n" + 
				"		\"properties\":{\r\n" + 
				"			\"title\":{\r\n" + 
				"				\"type\":\"text\",\r\n" + 
				"				\"index\" : true \r\n" + 
				"			},\r\n" + 
				"			\"content\":{\r\n" + 
				"				\"type\":\"text\",\r\n" + 
				"				\"index\" : true \r\n" + 
				"			},\r\n" + 
				"			\"summary\":{\r\n" + 
				"				\"type\":\"text\",\r\n" + 
				"				\"index\" : true \r\n" + 
				"			},\r\n" + 
				"			\"add_date\":{\r\n" + 
				"				\"type\":\"date_range\",\r\n" + 
				"				\"format\" : \"yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis\"\r\n" + 
				"			}\r\n" + 
				"		}\r\n" + 
				"	}\r\n" + 
				"}";
		IndexRequest request = new IndexRequest();
		request.setIndexName("zxwyyb");
		request.setAlias("zyb_news");
		request.setMappingName("news");
		request.setMappings(mappings);
		
		
		System.out.println("create index " + IndexUtils.createIndex(request));
		
		
		OpenIndexResponse response = IndexUtils.openIndex(request);
		System.out.println("open index " + response.isAcknowledged());
		
		IndexUtils.close();
	}
	

}
