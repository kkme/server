package com.hifun.soul.payserver.net;

import org.apache.mina.common.IoSession;

import com.hifun.soul.core.session.MinaSession;

/**
 * 支付服和游戏服的回话;
 * 
 * @author crazyjohn
 * 
 */
public class PSClientSession extends MinaSession {

	public PSClientSession(IoSession s) {
		super(s);
	}
}
