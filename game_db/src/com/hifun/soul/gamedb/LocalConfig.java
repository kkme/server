package com.hifun.soul.gamedb;

import com.hifun.soul.core.config.Config;
import com.hifun.soul.core.server.QueueMessageProcessor;

/**
 * 平台相关的配置;<br>
 * 之所以放到db项目里是因为平台的http调用都是费时的异步调用, 所以会委托到db线程去做;
 * 
 * @author crazyjohn
 * 
 */
public class LocalConfig implements Config {
	// 平台相关的配置
	/** 是否开启平台认证策略 */
	private boolean useLocalAuthorize = false;
	/** 连接腾讯的tgw窜; FUCK */
	private String tgwString;
	// 应用基本信息
	/** appId腾讯分配 */
	private String appId;
	/** appKey加密窜腾讯分配 */
	private String appKey;
	/** 腾讯的api服务器 */
	private String apiServerName;
	/** 腾讯的平台(空间，朋友之类) */
	private String qqPf;
	/** 汇报处理器 */
	private QueueMessageProcessor reportProcessor = new QueueMessageProcessor("LocalReportProcessor");
	/** cdn路径 */
	private String cdnPath;
	/** 充值图片子路径 */
	private String rechargeIconPath;
	/** zone分区id */
	private int zoneId;
	/** 充值注册URL地址 */
	private String payUrlPath;
	
	/**
	 * 开启汇报服务;
	 */
	public void startReportProcessor() {
		this.reportProcessor.start();
	}
	
	public void stopReportProcessor() {
		this.reportProcessor.stop();
	}
	
	public boolean isUseLocalAuthorize() {
		return useLocalAuthorize;
	}

	public void setUseLocalAuthorize(boolean useLocalAuthorize) {
		this.useLocalAuthorize = useLocalAuthorize;
	}

	public String getTgwString() {
		return tgwString;
	}

	public void setTgwString(String tgwString) {
		this.tgwString = tgwString;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getApiServerName() {
		return apiServerName;
	}

	public void setApiServerName(String apiServerName) {
		this.apiServerName = apiServerName;
	}

	public String getQqPf() {
		return qqPf;
	}

	public void setQqPf(String qqPf) {
		this.qqPf = qqPf;
	}

	@Override
	public String getVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validate() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getIsDebug() {
		// TODO Auto-generated method stub
		return false;
	}

	public QueueMessageProcessor getReportProcessor() {
		return reportProcessor;
	}

	public int getZoneId() {
		return zoneId;
	}

	public void setZoneId(int zoneId) {
		this.zoneId = zoneId;
	}

	public String getPayUrlPath() {
		return payUrlPath;
	}

	public void setPayUrlPath(String payUrlPath) {
		this.payUrlPath = payUrlPath;
	}

	public String getCdnPath() {
		return cdnPath;
	}

	public void setCdnPath(String cdnPath) {
		this.cdnPath = cdnPath;
	}

	public String getRechargeIconPath() {
		return rechargeIconPath;
	}

	public void setRechargeIconPath(String rechargeIconPath) {
		this.rechargeIconPath = rechargeIconPath;
	}
	
}
