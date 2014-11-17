package com.hifun.soul.core.rpc.client;

import java.io.IOException;

import org.apache.mina.common.ByteBuffer;

import com.google.protobuf.BlockingRpcChannel;
import com.google.protobuf.Descriptors.MethodDescriptor;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.RpcController;
import com.google.protobuf.ServiceException;
import com.hifun.soul.core.client.socket.ISocketConnection;
import com.hifun.soul.core.client.socket.SocketConnectionPool;
import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.core.msg.MessageParseException;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.rpc.msg.RpcErrorMessage;
import com.hifun.soul.core.rpc.msg.RpcRequestMessage;
import com.hifun.soul.core.rpc.msg.RpcResponseMessage;
import com.hifun.soul.core.rpc.util.PacketUtil;

/**
 * RPC客户端;
 * 
 * @author crazyjohn
 * 
 */
public class RpcClient implements BlockingRpcChannel {
	/** 连接池 */
	private SocketConnectionPool connectionPool;

	private static final int INIT_BUFFER_LEN = 512;

	/** 消息识别器 */
	private RpcClientMessageRecognizer recognizer = new RpcClientMessageRecognizer();

	public RpcClient(SocketConnectionPool pool) {
		this.connectionPool = pool;
	}

	@Override
	public Message callBlockingMethod(MethodDescriptor method,
			RpcController controller, Message request, Message responsePrototype)
			throws ServiceException {
		// 创建request消息
		RpcRequestMessage msg = new RpcRequestMessage();
		msg.setServiceIndentifier(method.getService().getName());
		msg.setMethodIndentifier(method.getName());
		msg.setParams(request.toByteArray());
		try {
			sendMessage(msg);
		} catch (Exception e) {
			throw new RuntimeException("Send message error", e);
		}
		return parseReturnValue(responsePrototype);
	}

	/**
	 * 负责从响应消息中解析出返回值
	 * 
	 * @param responsePrototype
	 * @return
	 * @throws ServiceException
	 */
	private Message parseReturnValue(Message responsePrototype)
			throws ServiceException {
		// 阻塞等待返回消息
		IMessage receiveMsg = null;
		Message result = null;
		try {
			receiveMsg = receiveMessage();
		} catch (Exception e) {
			throw new RuntimeException("Reveive message error", e);
		}
		// FIXME: crazyjohn 对请求的处理最好能封装在handler处理器中
		if(receiveMsg==null){
			return null;
		}
		if (receiveMsg.getType() == MessageType.RPC_RESPONSE) {
			RpcResponseMessage responseMsg = (RpcResponseMessage) receiveMsg;
			try {
				result = responsePrototype.newBuilderForType()
						.mergeFrom(responseMsg.getReturnValues()).build();
			} catch (InvalidProtocolBufferException e) {
				throw new RuntimeException("Parse return value error", e);
			}
		} else if (receiveMsg.getType() == MessageType.RPC_ERROR) {
			RpcErrorMessage errorMsg = (RpcErrorMessage) receiveMsg;
			throw new ServiceException(errorMsg.getErrorInfo());
		} else {
			throw new IllegalArgumentException("Unknown message type: "
					+ receiveMsg.getType());
		}
		return result;
	}

	/**
	 * 发送消息
	 * 
	 * @param message
	 * @throws MessageParseException
	 * @throws IOException
	 */
	public void sendMessage(RpcRequestMessage msg)
			throws MessageParseException, IOException {
		boolean needClose = false;
		ByteBuffer buffer = ByteBuffer.allocate(INIT_BUFFER_LEN).setAutoExpand(
				true);
		msg.setBuffer(buffer);
		msg.write();
		msg.getBuffer().flip();
		ByteBuffer packet = PacketUtil.cutPacket(msg.getBuffer());
		ISocketConnection connection = this.connectionPool.getConnection();
		try {
			connection.send(packet);
		} catch (Exception e) {
			// 发生异常, 关闭连接,确保后续数据正常
			needClose = true;
		} finally {
			if (needClose) {
				closeNativeConnection(connection);
			} else {
				closeConnection(connection);
			}
		}
	}

	private void closeConnection(ISocketConnection connection) {
		if (connection == null) {
			return;
		}
		try {
			connection.close();
		} catch (IOException e) {
		}
	}

	private void closeNativeConnection(ISocketConnection connection) {
		if (connection == null) {
			return;
		}
		try {
			connection.closeNative();
		} catch (IOException e) {
		}
	}

	/**
	 * 同步接收消息
	 * 
	 * @return
	 * @throws IOException
	 * @throws MessageParseException
	 */
	private IMessage receiveMessage() throws IOException, MessageParseException {
		ByteBuffer buffer = null;
		boolean needClose = false;
		IMessage msg = null;
		ISocketConnection connection = this.connectionPool.getConnection();
		try {
			buffer = connection.receive();

			short type = buffer.getShort(IMessage.HEADER_LEN_BYTES);
			msg = recognizer.createMessage(type);
			msg.setBuffer(buffer);
			msg.read();
		} catch (Exception e) {
			// 如果发生异常, 关闭连接;
			needClose = true;
		} finally {
			if (needClose) {
				this.closeNativeConnection(connection);
			} else {
				this.closeConnection(connection);
			}
		}

		return msg;
	}

}
