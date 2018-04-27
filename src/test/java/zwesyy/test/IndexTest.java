package zwesyy.test;

import java.io.IOException;

import org.elasticsearch.action.admin.indices.open.OpenIndexResponse;

import com.zwesyy.enery.IndexEntry;
import com.zwesyy.index.IndexUtil;

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
				"	\"content\":{\r\n" + 
				"		\"properties\":{\r\n" + 
				"			\"title\":{\r\n" + 
				"				\"type\":\"text\",\r\n" + 
				"				\"analyzer\" : \"ik_max_word\",\"index\" : true,\"search_analyzer\" : \"ik_max_word\" \r\n" + 
				"			},\r\n" + 
				"			\"content\":{\r\n" + 
				"				\"type\":\"text\",\r\n" + 
				"				\"analyzer\" : \"ik_max_word\",\"index\" : true,\"search_analyzer\" : \"ik_max_word\" \r\n" + 
				"			},\r\n" + 
				"			\"summary\":{\r\n" + 
				"				\"type\":\"text\",\r\n" + 
				"				\"analyzer\" : \"ik_max_word\",\"index\" : true,\"search_analyzer\" : \"ik_max_word\" \r\n" + 
				"			},\r\n" + 
				"			\"add_date\":{\r\n" + 
				"				\"type\":\"date_range\",\r\n" + 
				"				\"format\" : \"yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis\"\r\n" + 
				"			}\r\n" + 
				"		}\r\n" + 
				"	}\r\n" + 
				"}";
		IndexEntry request = new IndexEntry();
		request.setIndexName("zxwyybcon");
		request.setAlias("zyb_content");
		request.setMappingName("content");
		request.setMappings(mappings);
		
		
		System.out.println("create index " + IndexUtil.createIndex(request));
		
		
		OpenIndexResponse response = IndexUtil.openIndex(request);
		System.out.println("open index " + response.isAcknowledged());
		
	}
	

}
