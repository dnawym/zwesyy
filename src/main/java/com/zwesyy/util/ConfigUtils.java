package com.zwesyy.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 读取配置文件
 * @author: zhangyongbin
 * @description:
 * @date: 2018年4月11日
 */
public class ConfigUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(ConfigUtils.class);
	
	private static Map<String, String> map = null;
	
	private final static String configFile= "config.properties";
	
	public static String getConfig(String key) {
		if(map == null)
			getResourceMap(configFile,"UTF-8");
		
		return map.get(key);
	}
	
	
	private static Map<String, String> getResourceMap(String path, String encode) {
		map = new HashMap<String, String>();
		Properties properties = new Properties();
		InputStream ins = ConfigUtils.class.getClassLoader().getResourceAsStream(path);
		Reader inReader = null;
		try {
			inReader = new InputStreamReader(ins, StringUtils.isEmpty(encode) ? "UTF-8" : encode);
			properties.load(inReader);
			Set<Entry<Object, Object>> set = properties.entrySet();
			Iterator<Entry<Object, Object>> it = set.iterator();
			while (it.hasNext()) {
				Entry<Object, Object> entry = it.next();
				map.put((String) entry.getKey(), (String) entry.getValue());
			}
		} catch (Exception e) {
			logger.error(path + " 读取异常: {}", path, e);
		} finally {
			try {
				ins.close();
				inReader.close();
			} catch (IOException e) {
				logger.error(path + " 读取异常: {}", path, e);
			}
		}
		return map;
	}
}
