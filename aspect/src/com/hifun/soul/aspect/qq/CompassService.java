package com.hifun.soul.aspect.qq;

import java.util.HashMap;

import com.hifun.soul.core.msg.SysInternalMessage;
import com.qq.open.OpensnsException;
import com.qq.open.SnsNetwork;

/**
 * 罗盘服务;<br>
 * 通过异步的汇报处理器去处理汇报;
 * 
 * @author crazyjohn
 * 
 */
public class CompassService implements ICompassService {
	private String prefix = "http://tencentlog.com/stat/report_";
	private String protocol = "http";
	private IReportProcessor reportProcessor;

	@Override
	public void login(final int appid, final int domain, final int worldid,
			final long opuid, final String opopenid, final int level) {
		reportProcessor.put(new SysInternalMessage() {

			@Override
			public void execute() {
				// 获取url
				String url = getRequestUrl(CompassInterfaceType.LOGIN);
				// 构建参数
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("appid", String.valueOf(appid));
				params.put("domain", String.valueOf(domain));
				params.put("worldid", String.valueOf(worldid));
				params.put("opuid", String.valueOf(opuid));
				params.put("opopenid", String.valueOf(opopenid));
				params.put("level", String.valueOf(level));
				// 构建cookie
				HashMap<String, String> cookies = new HashMap<String, String>();
				// 发送请求
				try {
					String resp = SnsNetwork.postRequest(url, params, cookies,
							protocol);
				} catch (OpensnsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

	}

	@Override
	public void register(int appid, int domain, int worldid, long opuid,
			String opopenid) {
		this.reportProcessor.put(new SysInternalMessage() {

			@Override
			public void execute() {
				// TODO Auto-generated method stub

			}
		});
	}

	@Override
	public void consume(int appid, int domain, int worldid, long opuid,
			String opopenid, int modifyfee) {
		this.reportProcessor.put(new SysInternalMessage() {

			@Override
			public void execute() {
				// TODO Auto-generated method stub

			}
		});
	}

	private String getRequestUrl(CompassInterfaceType type) {
		StringBuilder sb = new StringBuilder(64);
		sb.append(protocol).append("://").append(prefix)
				.append(type.getInterfaceDesc());
		return sb.toString();
	}

}
