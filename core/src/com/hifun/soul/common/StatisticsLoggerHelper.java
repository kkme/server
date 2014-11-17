package com.hifun.soul.common;

import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.msg.IMessage;

public class StatisticsLoggerHelper {

	/**
	 * 统计收到的消息
	 * 
	 * @param msg
	 */
	public static void logMessageRecived(IMessage msg) {
		// 采集接收流量
//		PIProbeCollector.collect(ProbeName.NET_TRAFFIC, NetTraffic.RECEIVE, msg
//				.getLength());
	}

	/**
	 * 统计发送的消息
	 * 
	 * @param msg
	 */
	public static void logMessageSent(IMessage msg) {
		// 采集发送流量
//		PIProbeCollector.collect(ProbeName.NET_TRAFFIC, NetTraffic.SEND, msg
//				.getLength());
	}

	/**
	 * 统计所有收发的消息
	 * 
	 * @param msg
	 */
	public static void logMessage(IMessage msg) {
		Loggers.SERVER_STATUS_LOGGER.info("#Message{" + msg.getTypeName()
				+ " Length:" + msg.getLength() + " }");
	}
}
