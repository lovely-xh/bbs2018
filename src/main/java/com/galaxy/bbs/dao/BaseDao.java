package com.galaxy.bbs.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.style.ValueStyler;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Statement;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * DAO基类，其它DAO可以直接继承这个DAO，不但可以复用共用的方法，还可以获得泛型的好处。
 */
public class BaseDao<T>{
	
	private Class<T> entityClass;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	/**
	 * 通过反射获取子类确定的泛型类
	 */
	public BaseDao() {
		Type genType = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		entityClass = (Class) params[0];
	}	

	/**
	 * 分页查询函数，使用hql.
	 *
	 * @param pageNo 页号,从1开始.
	 */
	public Page pagedQuery(String hql, int pageNo, int pageSize, Object... values) {
		Assert.hasText(hql);
		Assert.isTrue(pageNo >= 1, "pageNo should start from 1");
		
		int board_id = (Integer) values[0];
		
		// Count查询
		String countQueryString = " select count(*) " + removeSelect(removeOrders(hql));
		int totalCount = getJdbcTemplate().queryForObject(countQueryString, new Object[] { board_id }, Integer.class);

		if (totalCount < 1) {
			return new Page();
		}

		// 实际查询返回分页对象
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
//		int endIndex = Page.getEndOfPage(pageNo, pageSize, totalCount);
		
		String queryString = " select * " + hql;
		List list = getJdbcTemplate().queryForList(String.format("%s limit %d,%d", queryString, startIndex, pageSize), new Object[] { board_id });

		return new Page(startIndex, totalCount, pageSize, list);
	}

	/**
	 * 去除hql的select 子句，未考虑union的情况,用于pagedQuery.
	 *
	 * @see #pagedQuery(String,int,int,Object[])
	 */
	private static String removeSelect(String hql) {
		Assert.hasText(hql);
		int beginPos = hql.toLowerCase().indexOf("from");
		Assert.isTrue(beginPos != -1, " hql : " + hql + " must has a keyword 'from'");
		return hql.substring(beginPos);
	}
	
	/**
	 * 去除hql的orderby 子句，用于pagedQuery.
	 *
	 * @see #pagedQuery(String,int,int,Object[])
	 */
	private static String removeOrders(String hql) {
		Assert.hasText(hql);
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}
	
}