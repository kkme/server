package com.hifun.soul.manageweb.character;

import com.hifun.soul.manageweb.common.PagingInfo;

/**
 * 查询角色列表的条件类
 * 
 * @author magicstone
 *
 */
public class CharacterQueryCondition {
	/** 过滤名称 */
	private String queryName="";
	/**id */
	private String queryId="";
	/**账户名称 */
	private String accountName="";
	/** 分页查询信息 */
	private PagingInfo pagingInfo;
	
	public String getQueryName() {
		return queryName;
	}
	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}	
	public String getQueryId() {
		return queryId;
	}
	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}
	public PagingInfo getPagingInfo() {
		return pagingInfo;
	}
	public void setPagingInfo(PagingInfo pagingInfo) {
		this.pagingInfo = pagingInfo;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
}
