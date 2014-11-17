package com.hifun.soul.robot.msg;

import com.hifun.soul.core.msg.sys.SessionClosedMessage;
import com.hifun.soul.robot.startup.IRobotClientSession;

public class RobotClientSessionClosedMsg extends SessionClosedMessage<IRobotClientSession>{

	public RobotClientSessionClosedMsg(IRobotClientSession sender) {
		super(sender);
	}
	
	@Override
	public void execute() {		
		
	}

}
