package org.gh.mergePic.dao;

import java.util.List;

/**
 * @author gh
 * @param <T>
 * 其实这里定义为接口比较合适
 */
public abstract class BaseDao<T> {
	
	public int count = 1;
	
	public abstract List<T> queryList(String sql);
	
	public void say(){
		System.out.println("This is BaseQuery<T>.say()");
	}
	
}
