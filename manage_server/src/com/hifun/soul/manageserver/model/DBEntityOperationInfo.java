package com.hifun.soul.manageserver.model;

/**
 * 数据库实体操作请求信息实体
 * @author magicstone
 *
 */
public class DBEntityOperationInfo {
	private String className;
	private int updateCount;
	private int insertCount;
	private int deleteCount;
	private int getCount;
	private int queryCount;
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public int getUpdateCount() {
		return updateCount;
	}
	public void setUpdateCount(int updateCount) {
		this.updateCount = updateCount;
	}
	public int getInsertCount() {
		return insertCount;
	}
	public void setInsertCount(int insertCount) {
		this.insertCount = insertCount;
	}
	public int getDeleteCount() {
		return deleteCount;
	}
	public void setDeleteCount(int deleteCount) {
		this.deleteCount = deleteCount;
	}
	public int getGetCount() {
		return getCount;
	}
	public void setGetCount(int getCount) {
		this.getCount = getCount;
	}
	public int getQueryCount() {
		return queryCount;
	}
	public void setQueryCount(int queryCount) {
		this.queryCount = queryCount;
	}	
}
