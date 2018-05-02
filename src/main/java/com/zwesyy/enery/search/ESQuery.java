package com.zwesyy.enery.search;

import java.util.Collection;

import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.ExistsQueryBuilder;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;

/**
 * 原则上来说，使用查询语句做全文本搜索或其他需要进行相关性评分的时候，剩下的全部用过滤语句
 * 
 * @author: zhangyongbin5
 * @description:
 * @date: 2018年4月28日
 */
public class ESQuery {

	// 模糊匹配选项
	private Fuzziness fuzziness = Fuzziness.AUTO;
	// 前缀长度选项
	private Integer prefixLength;
	// 最大模糊匹配长度选项
	private Integer maxExpansions;

	public ESQuery() {
	}

	public ESQuery(Fuzziness fuzziness) {
		this.fuzziness = fuzziness;
	}

	public ESQuery(Fuzziness fuzziness, Integer prefixLength, Integer maxExpansions) {
		this.fuzziness = fuzziness;
		this.prefixLength = prefixLength;
		this.maxExpansions = maxExpansions;
	}
	
	/**
	 * 查询全部
	 */
	public MatchAllQueryBuilder getMatchAllQuery() {
		MatchAllQueryBuilder query = QueryBuilders.matchAllQuery();
		return query;
	}

	/**
	 * 精确查询
	 */
	public MatchQueryBuilder getMatchQuery(String name, Object text) {
		MatchQueryBuilder query = QueryBuilders.matchQuery(name, text).fuzziness(fuzziness);
		if (prefixLength != null)
			query.prefixLength(prefixLength);
		if (maxExpansions != null)
			query.maxExpansions(maxExpansions);
		return query;
	}

	/**
	 * 短语匹配。 匹配同时包含 “A” 和 “B” ，并且 二者以短语 “A B” 的形式紧挨着的记录
	 */
	public MatchPhraseQueryBuilder getMatchPhraseQuery(String name, Object text) {
		MatchPhraseQueryBuilder query = QueryBuilders.matchPhraseQuery(name, text);
		return query;
	}

	/**
	 * 精确匹配,过滤查询
	 */
	public TermQueryBuilder getTermQuery(String name, Object text) {
		TermQueryBuilder query = QueryBuilders.termQuery(name, text);
		return query;
	}

	/**
	 * 多个字段上执行相同的 match 查询
	 */
	public MultiMatchQueryBuilder getMultiMatchQuery(String text, String... fields) {
		MultiMatchQueryBuilder query = QueryBuilders.multiMatchQuery(text, fields);
		return query;
	}

	/**
	 * 精确匹配,过滤查询多个匹配条件
	 */
	public TermsQueryBuilder getTermsQuery(Collection<?> values, String name) {
		TermsQueryBuilder query = QueryBuilders.termsQuery(name, values);
		return query;
	}

	public TermsQueryBuilder getTermsQuery(String name, Object... values) {
		TermsQueryBuilder query = QueryBuilders.termsQuery(name, values);
		return query;
	}

	/**
	 * 指定区间内的数字或者时间 gt : 大于 ,gte:大于等于,lt:小于,lte小于等于
	 */
	public RangeQueryBuilder getRangeGtLtQuery(String name, Object gtValue, Object ltValue) {
		RangeQueryBuilder query = QueryBuilders.rangeQuery(name);
		if (gtValue != null)
			query.gt(gtValue);
		if (ltValue != null)
			query.lt(ltValue);
		return query;
	}

	public RangeQueryBuilder getRangeGteLtQuery(String name, Object gteValue, Object ltValue) {
		RangeQueryBuilder query = QueryBuilders.rangeQuery(name);
		if (gteValue != null)
			query.gte(gteValue);
		if (ltValue != null)
			query.lt(ltValue);
		return query;
	}

	public RangeQueryBuilder getRangeGtLteQuery(String name, Object gteValue, Object lteValue) {
		RangeQueryBuilder query = QueryBuilders.rangeQuery(name);
		if (gteValue != null)
			query.gte(gteValue);
		if (lteValue != null)
			query.lt(lteValue);
		return query;
	}

	/**
	 * 查找文档中是否包含指定字段或没有某个字段
	 */
	public ExistsQueryBuilder getExistsQuery(String name) {
		ExistsQueryBuilder query = QueryBuilders.existsQuery(name);
		return query;
	}

	/**
	 * 用来合并多个过滤条件查询结果的布尔逻辑,它包含一下操作符：
	 * must :: 多个查询条件的完全匹配,相当于 and。
	 * must_not :: 多个查询条件的相反匹配，相当于 not。
	 * should :: 至少有一个查询条件匹配, 相当于 or
	 */
//	public BoolQueryBuilder getBoolQuery(String name,String... must) {
//		BoolQueryBuilder query = QueryBuilders.boolQuery();
//		for(int i=0;i<must.length;i++) {
//			query.m.must(QueryBuilders.termsQuery(name, must));
//		}
//		return query;
//	}

}
