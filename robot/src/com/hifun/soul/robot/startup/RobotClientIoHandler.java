package com.hifun.soul.robot.startup;

import org.apache.mina.common.IoSession;

import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.core.server.AbstractIoHandler;
import com.hifun.soul.robot.Robot;
import com.hifun.soul.robot.msg.RobotClientSessionClosedMsg;
import com.hifun.soul.robot.msg.RobotClientSessionOpenedMsg;

public class RobotClientIoHandler extends AbstractIoHandler<RobotClientSession>{

	@Override
	public void sessionOpened(IoSession session) {
		if (Robot.robotLogger.isDebugEnabled()) {
			Robot.robotLogger.debug("Session opened");
		}
		curSessionCount++;
		RobotClientSession s = this.createSession(session);
		session.setAttachment(s);
		IMessage msg = new RobotClientSessionOpenedMsg(s);
		msgProcessor.put(msg);
	}
	
	@Override
	public void sessionClosed(IoSession session) {
		if (Robot.robotLogger.isDebugEnabled()) {
			Robot.robotLogger.debug("Session closed");
		}
		curSessionCount--;
		RobotClientSession ms = (RobotClientSession) session.getAttachment();
		if (ms == null) {
			return;
		}
		session.setAttachment(null);
		IMessage msg = new RobotClientSessionClosedMsg(ms);
		msgProcessor.put(msg);
	}


	@Override
	protected RobotClientSession createSession(IoSession session) {		
		return new RobotClientSession(session);
	}
	
}
