package com.hifun.soul.gameserver.pay;

import org.apache.mina.common.IoSession;

import com.hifun.soul.core.session.MinaSession;

public class PSClientSession extends MinaSession {

	public PSClientSession(IoSession s) {
		super(s);
	}

}
