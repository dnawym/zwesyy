package zwesyy.test;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.common.xcontent.XContentType;

import com.zwesyy.enery.BulkEntry;
import com.zwesyy.enery.DocumentEntry;
import com.zwesyy.index.BulkUtil;
import com.zwesyy.index.DocumentUtil;

import db.comDAL;

public class DocTest {

	public static void main(String[] args) throws IOException, SQLException {
		test();
	}

	// @Test
	public static void test() throws IOException, SQLException {
		
		String index = "zxwyybcon";
		String type="content";
		
		DocumentEntry entry = new DocumentEntry();
		entry.setIndexName(index);
		entry.setType(type);
		
		comDAL dal = new comDAL();
		BulkEntry bulkEntry = new BulkEntry(index,type);
		
		ResultSet set = dal.DoSelect("select * from content");
		Map<String, Object> map = new HashMap<>();
		while (set.next()) {
			System.out.println(set.getString("title")+set.getString("createDate"));
			map.put("content",set.getString("title"));
			map.put("title",set.getString("longtitle"));
			map.put("summary",set.getString("title"));
//			
//			DocumentUtil.createDoc(entry, map);
			bulkEntry.addBulkSource(null, XContentType.JSON, map);
		}
		
		BulkUtil.createDoc(bulkEntry);
		
		System.out.println("创建完成");
		
		
		dal.close();
	}

}
