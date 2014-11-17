package com.hifun.soul.manageweb.common;

/**
 * 分页信息
 * 
 * @author magicstone
 *
 */
public class PagingInfo {
	private int pageIndex;
	private int pageSize;
	private long totalCount;
	
	public PagingInfo(){}
			
	public PagingInfo(int pageSize){
		this.pageSize = pageSize;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
}
