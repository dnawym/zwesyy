package com.zwesyy.enery;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.common.xcontent.XContentType;

/**
 * bulk 实体对象
 * 
 * @author: zhangyongbin
 * @description:
 * @date: 2018年4月27日
 */
public class BulkEntry {

	private String index;

	private String type;

	private List<BulkSource> bulkSource;

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<BulkSource> getBulkSource() {
		return bulkSource;
	}

	public void setBulkSource(List<BulkSource> bulkSource) {
		this.bulkSource = bulkSource;
	}

	public void addBulkSource(String id, XContentType xContentType, Object[] source) {
		if (bulkSource == null)
			bulkSource = new ArrayList<>();

		bulkSource.add(new BulkSource(id, xContentType, source));
	}

	public class BulkSource {

		private String id;

		private XContentType xContentType;

		private Object[] source;

		public BulkSource(String id, XContentType xContentType, Object... source) {
			this.id = id;
			this.xContentType = xContentType;
			this.source = source;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public XContentType getxContentType() {
			return xContentType;
		}

		public void setxContentType(XContentType xContentType) {
			this.xContentType = xContentType;
		}

		public Object[] getSource() {
			return source;
		}

		public void setSource(Object[] source) {
			this.source = source;
		}

	}

}
