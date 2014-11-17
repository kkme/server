package com.hifun.soul.core.log;

import java.net.InetSocketAddress;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.ConnectFuture;
import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.SocketConnector;
import org.apache.mina.transport.socket.nio.SocketConnectorConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hifun.soul.core.codec.GameCodecFactory;
import com.hifun.soul.core.msg.BaseMessage;
import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.core.msg.MessageParseException;
import com.hifun.soul.core.msg.recognizer.IMessageRecognizer;
import com.hifun.soul.core.server.SingleThreadModel;

/**
 * 实现UdpLoggerClient
 * 
 */
public class UdpLoggerClient {
	private static final Logger logger = LoggerFactory
			.getLogger(UdpLoggerClient.class);
	private String ip;
	private int port;
	private int regionId;
	private int serverId;
	private IoSession session;

	private static UdpLoggerClient _inst;
	
	/** 超时时间 */
	private static final int CONNECT_TIMEOUT = 10; // seconds

	private UdpLoggerClient() {

	}

	public static UdpLoggerClient getInstance() {
		if (_inst == null) {
			_inst = new UdpLoggerClient();
		}
		return _inst;
	}

	/**
	 * 初始化
	 * 
	 * @param ip
	 * @param port
	 */
	public void initialize(String ip, int port) {
		this.ip = ip;
		this.port = port;
		connect(ip, port);
	}

	/**
	 * 关闭
	 */
	public void close() {
		if (session != null) {
			session.close();
			this.session = null;
		}
	}

	/**
	 * 连接已经被关闭
	 */
	public void closed() {
		this.session = null;
	}

	public int getRegionId() {
		return regionId;
	}

	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public synchronized boolean isEnable() {
		return session != null;
	}

	public void sendMessage(IMessage msg) {
		try {
			if (session != null && session.isConnected()) {
				session.write(msg);
			}
		} catch (Exception e) {
			if (logger.isWarnEnabled()) {
				logger
						.warn("[#GS.UdpLoggerClient.reconnect] [send log message exception]");
			}
		}
	}

	/**
	 * 重新建立连接
	 */
	public void reconnect() {
		this.connect(this.ip, this.port);
	}

	private synchronized void connect(String ip, int port) {
		if (this.session != null && this.session.isConnected()) {
			return;
		}
		/*
		 * 将gameserver与logserver之间的连接由UDP改为TCP 
		 * 
		 * 更改之后优点：
		 * 	1. 保证在GameServer启动之前LogServer必须启动
		 *  2. 保证不会丢失数据包
		 * 
		 * 更改之后可能带来的问题：
		 * 	1. 性能可能有一定的影响
		 *  2. 如果因为某种原因连接还在但是数据发送异常会导致内存暴涨
		 */

//		DatagramConnectorConfig cfg = new DatagramConnectorConfig();
//		cfg.setSessionRecycler(IoSessionRecycler.NOOP);
		SocketConnectorConfig cfg = new SocketConnectorConfig();
		cfg.setThreadModel(SingleThreadModel.getInstance());
		cfg.setConnectTimeout(CONNECT_TIMEOUT);
		cfg.getFilterChain().addLast(
				"codec",
				new ProtocolCodecFilter(new GameCodecFactory(
						new IMessageRecognizer() {
							@Override
							public IMessage recognize(ByteBuffer buf)
									throws MessageParseException {
								return new FooBar();
							}

							@Override
							public int recognizePacketLen(ByteBuffer buff) {
								return -1;
							}
						})));

		//IoConnector connector = new DatagramConnector();
		SocketConnector connector = new SocketConnector();
		ConnectFuture _future = connector.connect(new InetSocketAddress(ip, port), new LogClientIoHandler(
				this), cfg);
		_future.join();
		if (_future.isConnected()) {
			session = _future.getSession();
		} else {
			throw new IllegalStateException(
					"Can't connect to the remote server.");
		}
	}

	private static class FooBar extends BaseMessage {

		@Override
		protected boolean readImpl() {
			return true;
		}

		@Override
		protected boolean writeImpl() {
			return true;
		}

		@Override
		public short getType() {
			return 0;
		}

		@Override
		public String getTypeName() {
			return null;
		}

		@Override
		public void execute() {
		}
	}

}
