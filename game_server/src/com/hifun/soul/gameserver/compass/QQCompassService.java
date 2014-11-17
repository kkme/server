package com.hifun.soul.gameserver.compass;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hifun.soul.core.msg.SysInternalMessage;
import com.hifun.soul.core.server.QueueMessageProcessor;
import com.hifun.soul.gamedb.LocalConfig;
import com.hifun.soul.gameserver.common.config.GameServerConfig;
import com.qq.open.OpensnsException;
import com.qq.open.SnsNetwork;

/**
 * 罗盘服务;<br>
 * 通过异步的汇报处理器去处理汇报;
 * 
 * @author crazyjohn
 * 
 */
public class QQCompassService implements ICompassService {
	private Logger logger = LoggerFactory.getLogger(QQCompassService.class);
	private String prefix = "http://tencentlog.com/stat/report_";
	private String protocol = "http";
	/** 汇报处理器 */
	private QueueMessageProcessor reportProcessor;
	private LocalConfig localConfig;
	private GameServerConfig gameConfig;

	public QQCompassService(QueueMessageProcessor reportProcessor,
			GameServerConfig gameConfig, LocalConfig localConfig) {
		this.reportProcessor = reportProcessor;
		this.localConfig = localConfig;
		this.gameConfig = gameConfig;
	}

	@Override
	public void login(final int opuid, final String opopenid, final int level) {
		reportProcessor.put(new SysInternalMessage() {

			@Override
			public void execute() {
				// 获取url
				String url = getRequestUrl(QQCompassInterfaceType.LOGIN);
				// 构建参数
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("appid", String.valueOf(localConfig.getAppId()));
				// 设置平台
				params.put(
						"domain",
						String.valueOf(QQDomainType.platOf(
								localConfig.getQqPf()).getIndex()));
				params.put("worldid", gameConfig.getServerId());
				params.put("opuid", String.valueOf(opuid));
				params.put("opopenid", String.valueOf(opopenid));
				params.put("level", String.valueOf(level));
				// 构建cookie
				HashMap<String, String> cookies = new HashMap<String, String>();
				// 发送请求
				try {
					String resp = SnsNetwork.postRequest(url, params, cookies,
							protocol);
					if (logger.isDebugEnabled()) {
						logger.debug(resp);
					}
				} catch (OpensnsException e) {
					logger.error("Report login error", e);
				}

			}
		});

	}

	@Override
	public void register(final int opuid, final String opopenid) {
		this.reportProcessor.put(new SysInternalMessage() {

			@Override
			public void execute() {
				// 获取url
				String url = getRequestUrl(QQCompassInterfaceType.REGISTER);
				// 构建参数
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("appid", String.valueOf(localConfig.getAppId()));
				// 设置平台
				params.put(
						"domain",
						String.valueOf(QQDomainType.platOf(
								localConfig.getQqPf()).getIndex()));
				params.put("worldid", gameConfig.getServerId());
				params.put("opuid", String.valueOf(opuid));
				params.put("opopenid", String.valueOf(opopenid));
				// 构建cookie
				HashMap<String, String> cookies = new HashMap<String, String>();
				// 发送请求
				try {
					String resp = SnsNetwork.postRequest(url, params, cookies,
							protocol);
					if (logger.isDebugEnabled()) {
						logger.debug(resp);
					}
				} catch (OpensnsException e) {
					logger.error("Report register error", e);
				}
			}
		});
	}

	@Override
	public void consumeCoin(final int opuid, final String opopenid,
			final String itemid, final String itemtype, final int itemcnt,
			final int modifycoin, final int totalcoin, final int level) {
		this.reportProcessor.put(new SysInternalMessage() {

			@Override
			public void execute() {
				// 获取url
				String url = getRequestUrl(QQCompassInterfaceType.CONSUME);
				// 构建参数
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("appid", String.valueOf(localConfig.getAppId()));
				// 设置平台
				params.put(
						"domain",
						String.valueOf(QQDomainType.platOf(
								localConfig.getQqPf()).getIndex()));
				params.put("worldid", gameConfig.getServerId());
				params.put("opuid", String.valueOf(opuid));
				params.put("opopenid", String.valueOf(opopenid));
				params.put("modifyfee", String.valueOf(0));
				params.put("itemid", itemid);
				params.put("itemtype", itemtype);
				params.put("itemcnt", String.valueOf(itemcnt));
				params.put("modifycoin", String.valueOf(modifycoin));
				params.put("totalcoin", String.valueOf(totalcoin));
				params.put("level", String.valueOf(level));
				// 构建cookie
				HashMap<String, String> cookies = new HashMap<String, String>();
				// 发送请求
				try {
					String resp = SnsNetwork.postRequest(url, params, cookies,
							protocol);
					if (logger.isDebugEnabled()) {
						logger.debug(resp);
					}
				} catch (OpensnsException e) {
					logger.error("Report consumeCoin error", e);
				}
			}
		});
	}

	@Override
	public void consumeCrystal(final int opuid, final String opopenid,
			final int modifyfee, final String itemid, final String itemtype,
			final int itemcnt, final int totalfee, final int level) {
		this.reportProcessor.put(new SysInternalMessage() {

			@Override
			public void execute() {
				// 获取url
				String url = getRequestUrl(QQCompassInterfaceType.CONSUME);
				// 构建参数
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("appid", String.valueOf(localConfig.getAppId()));
				// 设置平台
				params.put(
						"domain",
						String.valueOf(QQDomainType.platOf(
								localConfig.getQqPf()).getIndex()));
				params.put("worldid", gameConfig.getServerId());
				params.put("opuid", String.valueOf(opuid));
				params.put("opopenid", String.valueOf(opopenid));
				params.put("modifyfee", String.valueOf(modifyfee));
				params.put("itemid", itemid);
				params.put("itemtype", itemtype);
				params.put("itemcnt", String.valueOf(itemcnt));
				params.put("totalfee", String.valueOf(totalfee));
				params.put("level", String.valueOf(level));
				// 构建cookie
				HashMap<String, String> cookies = new HashMap<String, String>();
				// 发送请求
				try {
					String resp = SnsNetwork.postRequest(url, params, cookies,
							protocol);
					if (logger.isDebugEnabled()) {
						logger.debug(resp);
					}
				} catch (OpensnsException e) {
					logger.error("Report consumeCrystal error", e);
				}
			}
		});

	}

	/**
	 * 获取指定接口的URL;
	 * 
	 * @param type
	 * @return
	 */
	private String getRequestUrl(QQCompassInterfaceType type) {
		StringBuilder sb = new StringBuilder(64);
		sb.append(prefix).append(type.getInterfaceDesc()).append(".php");
		return sb.toString();
	}

}
