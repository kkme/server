package com.hifun.soul.core.codec;

import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

import com.hifun.soul.core.msg.recognizer.IMessageRecognizer;

/**
 * 实现Mina的编码/解码器工厂接口
 * 
 * 
 */
public class GameCodecFactory implements ProtocolCodecFactory {

	/** 编码器 * */
	private static final GameEncoder ENCODER = new GameEncoder();

	/** 消息识别器 * */
	private final IMessageRecognizer recognizer;
	private short tgwLength;
	
	public GameCodecFactory(IMessageRecognizer recog, int tgwLength) {
		this(recog);
		this.tgwLength = (short) tgwLength;
	}
	
	public GameCodecFactory(IMessageRecognizer recog) {
		recognizer = recog;
	}

	public ProtocolDecoder getDecoder() throws Exception {
		return new GameDecoder(recognizer, tgwLength);
	}

	public ProtocolEncoder getEncoder() throws Exception {
		return ENCODER;
	}

	public IMessageRecognizer getRecognizer() {
		return recognizer;
	}

}
