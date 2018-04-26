package zwesyy.test;

import java.io.IOException;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;

import com.zwesyy.eneity.SearchQuery;
import com.zwesyy.util.es.SearchUtils;

public class SearchTest {
	
	public static void main(String[] args) throws IOException {
		
		
		SearchRequest searchRequest = new SearchRequest(); 
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder(); 
		searchSourceBuilder.query(QueryBuilders.matchAllQuery()); 
		searchRequest.source(searchSourceBuilder); 
		
		SearchQuery query = new SearchQuery();
		query.setIndex("zxwyyb");
		query.setTypes("news");
		SearchQuery.SourceBuilder sourceBuilder = query.new SourceBuilder();
		sourceBuilder.setForm(0);
		sourceBuilder.setSize(10);
		sourceBuilder.setIncludeFields("id","content","title","summary","add_date");
		sourceBuilder.setQuery(QueryBuilders.queryStringQuery("ActionListener"));
		sourceBuilder.setSort(new ScoreSortBuilder().order(SortOrder.DESC));
		query.setSourceBuilder(sourceBuilder);
		
		SearchResponse response = SearchUtils.search(query);
		SearchHits hits = response.getHits();
		for (SearchHit hit : hits) {
			String index = hit.getIndex();
			String type = hit.getType();
			String id = hit.getId();
			float score = hit.getScore();
			
			String sourceAsString = hit.getSourceAsString();
			System.out.println("记录 ：" +index +"-"+type+"-"+id+"-"+score+"-"+sourceAsString);
		}
		
		SearchUtils.close();
	}

}
