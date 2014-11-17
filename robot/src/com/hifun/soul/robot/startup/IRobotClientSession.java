package com.hifun.soul.robot.startup;

import org.apache.mina.common.IoSession;

import com.hifun.soul.core.session.ISession;
import com.hifun.soul.robot.Robot;

public interface IRobotClientSession extends ISession{
	
	void setRobot(Robot player);

	Robot getRobot();
	
	IoSession getIoSession();

}
