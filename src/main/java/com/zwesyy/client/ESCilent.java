package com.zwesyy.client;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback;
import org.elasticsearch.client.RestClientBuilder.RequestConfigCallback;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zwesyy.util.ConfigUtils;

/**
 * We plan on deprecating the TransportClient in Elasticsearch 7.0 and removing
 * it completely in 8.0. Instead, you should be using the Java High Level REST
 * Client, which executes HTTP requests rather than serialized Java requests.
 * The migration guide describes all the steps needed to migrate.
 * 
 * 5.x之前的版本都是用 TransportClient 来连接，高版本官方推荐使用 Java High Level REST Client
 * 来代替之前的，7.0以后要取消 TransportClient 的使用
 * 
 * @author: zhangyongbin
 * @description:
 * @date: 2018年4月25日
 */
public class ESCilent {

	private static final Logger logger = LoggerFactory.getLogger(ESCilent.class);

	private static ESCilent client = null;

	private static String[] hosts = null;

	private static String schema = null;

	private static int maxRetryTimeout = 6000;

	private static int connectTimeout = 6000;

	private static int socketTimeout = 60000;

	private static int port = 9200;

	private static int ioThreadCount = 1;

	private static String userName = null;

	private static String password = null;

	static {
		hosts = ConfigUtils.getConfig("es.hosts").split(",");
		schema = ConfigUtils.getConfig("es.schema");
		maxRetryTimeout = ConfigUtils.getConfig("es.maxRetryTimeout") != null ? Integer.parseInt(ConfigUtils.getConfig("es.maxRetryTimeout")) : 6000;
		connectTimeout = ConfigUtils.getConfig("es.connectTimeout") != null ? Integer.parseInt(ConfigUtils.getConfig("es.connectTimeout")) : 6000;
		socketTimeout = ConfigUtils.getConfig("es.socketTimeout") != null ? Integer.parseInt(ConfigUtils.getConfig("es.socketTimeout")) : 60000;
		ioThreadCount = ConfigUtils.getConfig("es.ioThreadCount") != null ? Integer.parseInt(ConfigUtils.getConfig("es.ioThreadCount")) : 1;

		userName = ConfigUtils.getConfig("es.userName");
		password = ConfigUtils.getConfig("es.password");
	}

	public static ESCilent getInstance() {
		if (client == null) {
			synchronized (ESCilent.class) {
				if (client == null) {
					client = new ESCilent();
				}
			}
		}
		return client;
	}

	public RestHighLevelClient getClient() {
		RestHighLevelClient restClient = null;
		try {

			HttpHost[] httpHosts = new HttpHost[hosts.length];

			for (int i = 0; i < hosts.length; i++) {

				String[] _address = hosts[i].split(":");
				if (_address.length > 1)
					port = Integer.parseInt(_address[1]);

				httpHosts[i] = (new HttpHost(_address[0], port, schema));

			}

			RestClientBuilder builder = RestClient.builder(httpHosts).setMaxRetryTimeoutMillis(maxRetryTimeout);

			// 设置请求认证信息
			final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
			credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(userName, password));

			builder.setHttpClientConfigCallback(new HttpClientConfigCallback() {

				public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
					httpClientBuilder.disableAuthCaching();
					return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider)
							// 设置异步请求处理线程数
							.setDefaultIOReactorConfig(IOReactorConfig.custom().setIoThreadCount(ioThreadCount).build());
				}

			});

			// 设置请求超时时间
			builder.setRequestConfigCallback(new RequestConfigCallback() {

				public Builder customizeRequestConfig(Builder requestConfigBuilder) {
					return requestConfigBuilder.setConnectTimeout(connectTimeout).setSocketTimeout(socketTimeout);
				}

			});

			restClient = new RestHighLevelClient(builder);

		} catch (Exception e) {
			logger.error("初始化异常：{}", e);
		}
		return restClient;
	}

}
