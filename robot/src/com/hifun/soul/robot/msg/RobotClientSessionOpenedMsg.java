package com.hifun.soul.robot.msg;

import com.hifun.soul.core.msg.sys.SessionOpenedMessage;
import com.hifun.soul.robot.startup.IRobotClientSession;

public class RobotClientSessionOpenedMsg extends SessionOpenedMessage<IRobotClientSession>{

	public RobotClientSessionOpenedMsg(IRobotClientSession session) {
		super(session);
	}

	@Override
	public void execute() {	
		
	}
}
