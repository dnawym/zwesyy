package zwesyy.test;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.RequestLine;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.support.ActiveShardCount;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;

import com.zwesyy.client.ESCilent;
import com.zwesyy.client.ESLowCilent;

public class ClientTest {

	public static void main(String[] args) throws Exception {
		
		String indexName = "aiyy";

		RestClient client = ESLowCilent.getClient();

		Response response = client.performRequest("GET", "/");
		RequestLine requestLine = response.getRequestLine(); // (1) 请求的信息
		HttpHost host = response.getHost(); // (2) 响应的主机信息
		int statusCode = response.getStatusLine().getStatusCode(); // (3) 响应状态行，例如你可以从中获取响应状态码
		Header[] headers = response.getHeaders(); // (4) 响应头信息，也可以使用getHeader(String)通过名称获取具体的响应头
		String responseBody = EntityUtils.toString(response.getEntity()); // (5) 响应体信息，在org.apache.http.HttpEntity对象中

		//
		System.out.println(responseBody);

		RestHighLevelClient highClient = ESCilent.getInstance().getClient();

		CreateIndexRequest request = new CreateIndexRequest("twitter");
		request.settings(Settings.builder().put("index.number_of_shards", 3).put("index.number_of_replicas", 2));

		request.mapping("tweet", "  {\n" + "    \"tweet\": {\n" + "      \"properties\": {\n" + "        \"message\": {\n" + "          \"type\": \"text\"\n" + "        }\n" + "      }\n" + "    }\n" + "  }", XContentType.JSON);

		request.alias(new Alias("twitter_alias"));
		
		request.timeout(TimeValue.timeValueMinutes(2)); 
		request.timeout("2m"); 
		
		request.masterNodeTimeout(TimeValue.timeValueMinutes(1)); 
		request.masterNodeTimeout("1m"); 
		
		request.waitForActiveShards(2); 
		request.waitForActiveShards(ActiveShardCount.DEFAULT); 
		
		CreateIndexResponse createIndexResponse = highClient.indices().create(request);

		System.out.println(createIndexResponse);
		
		client.close();
		highClient.close();
	}
	

}
