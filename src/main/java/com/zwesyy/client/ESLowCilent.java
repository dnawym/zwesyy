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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zwesyy.util.ConfigUtils;

/**
 * 低版本
 * 
 * @author: zhangyongbin
 * @description:
 * @date: 2018年4月20日
 */
public class ESLowCilent {

	private static final Logger logger = LoggerFactory.getLogger(ESLowCilent.class);

	private static RestClient client = null;

	private static String clusterName = null;

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
		clusterName = ConfigUtils.getConfig("cluster.name");
		hosts = ConfigUtils.getConfig("es.hosts").split(",");
		schema = ConfigUtils.getConfig("es.schema");
		port = 9200;
		maxRetryTimeout = ConfigUtils.getConfig("es.maxRetryTimeout") != null ? Integer.parseInt(ConfigUtils.getConfig("es.maxRetryTimeout")) : 6000;
		connectTimeout = ConfigUtils.getConfig("es.connectTimeout") != null ? Integer.parseInt(ConfigUtils.getConfig("es.connectTimeout")) : 6000;
		socketTimeout = ConfigUtils.getConfig("es.socketTimeout") != null ? Integer.parseInt(ConfigUtils.getConfig("es.socketTimeout")) : 60000;
		ioThreadCount = ConfigUtils.getConfig("es.ioThreadCount") != null ? Integer.parseInt(ConfigUtils.getConfig("es.ioThreadCount")) : 1;

		userName = ConfigUtils.getConfig("es.userName");
		password = ConfigUtils.getConfig("es.password");
	}

	public static RestClient getClient() {
		if (client == null)
			initClient();

		return client;

	}

	private static void initClient() {
		RestClient restClient = null;
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

			restClient = builder.build();

			client = restClient;

		} catch (Exception e) {
			logger.error("初始化异常：{}", e);
		}

	}

}
