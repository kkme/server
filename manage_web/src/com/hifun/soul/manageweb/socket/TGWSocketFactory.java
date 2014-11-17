package com.hifun.soul.manageweb.socket;

import java.io.IOException;
import java.net.Socket;
import java.text.MessageFormat;

import com.hifun.soul.common.constants.LocalConstants;
import com.hifun.soul.core.client.socket.SocketFactory;

public class TGWSocketFactory extends SocketFactory {
	private boolean tgwOpen;
	private String serverHost;
	private int port;

	public TGWSocketFactory(String serverHost, int serverPort, SocketConfig socketConfig, boolean tgwOpen) {
		super(serverHost, serverPort, socketConfig);
		this.tgwOpen = tgwOpen;
		this.serverHost = serverHost;
		this.port = serverPort;
	}

	@Override
	public Socket openSocket() throws IOException {
		Socket socket = super.openSocket();
		String msg = MessageFormat.format(LocalConstants.TGW_TEMPLATE, serverHost, port);
		// 如果连接已经建立, 就先发送TGW窜进行认证;
		if (tgwOpen && socket.isConnected()) {
			sendTGW(socket, msg);
		}
		return socket;
	}

	/**
	 * 发送TGW窜;
	 * 
	 * @param socket
	 * @param msg
	 * @throws IOException
	 */
	private void sendTGW(Socket socket, String msg) throws IOException {
		byte[] datas = msg.getBytes("GBK");
		socket.getOutputStream().write(datas, 0, datas.length - 1);
	}

}
