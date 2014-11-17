package com.hifun.soul.robot.task.impl;

import org.h2.util.MathUtils;

import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.gameserver.horoscope.StargazerType;
import com.hifun.soul.gameserver.horoscope.msg.CGHoroscopeCompoundAuto;
import com.hifun.soul.gameserver.horoscope.msg.CGHoroscopeCompoundConfirm;
import com.hifun.soul.gameserver.horoscope.msg.CGHoroscopeGamble;
import com.hifun.soul.gameserver.horoscope.msg.CGHoroscopeGambleAuto;
import com.hifun.soul.gameserver.horoscope.msg.CGHoroscopeOff;
import com.hifun.soul.gameserver.horoscope.msg.CGHoroscopeOn;
import com.hifun.soul.robot.Robot;
import com.hifun.soul.robot.task.LoopExecuteTask;

public class HoroscopeTask extends LoopExecuteTask {

	private int i = 0;
	
	private StargazerType[] stargazerTypes = StargazerType.values();
	
	public HoroscopeTask(Robot robot, int delay, int interval) {
		super(robot, delay, interval);
	}

	@Override
	public void doAction() {
		switch(i){
		case 0:
			CGHoroscopeGamble cgHoroscopeGamble = new CGHoroscopeGamble();
			cgHoroscopeGamble.setStargazerId(stargazerTypes[MathUtils.randomInt(stargazerTypes.length)].getIndex());
			getRobot().sendMessage(cgHoroscopeGamble);
			break;
		case 1:
			CGHoroscopeGambleAuto cgHoroscopeGambleAuto = new CGHoroscopeGambleAuto();
			getRobot().sendMessage(cgHoroscopeGambleAuto);
			break;
		case 2:
			CGHoroscopeCompoundConfirm cgHoroscopeCompound = new CGHoroscopeCompoundConfirm();
			cgHoroscopeCompound.setFromBagType(1);
			cgHoroscopeCompound.setFromBagIndex(0);
			cgHoroscopeCompound.setToBagType(1);
			cgHoroscopeCompound.setToBagIndex(1);
			getRobot().sendMessage(cgHoroscopeCompound);
			break;
		case 3:
			CGHoroscopeCompoundAuto cgHoroscopeCompoundAuto = new CGHoroscopeCompoundAuto();
			getRobot().sendMessage(cgHoroscopeCompoundAuto);
			break;
		case 4:
			CGHoroscopeOn cgHoroscopeOn = new CGHoroscopeOn();
			cgHoroscopeOn.setIndex(0);
			getRobot().sendMessage(cgHoroscopeOn);
			break;
		case 5:
			CGHoroscopeOff cgHoroscopeOff = new CGHoroscopeOff();
			cgHoroscopeOff.setIndex(0);
			getRobot().sendMessage(cgHoroscopeOff);
			break;
		}
		
		i = (i++)%6;
	}

	@Override
	public void onResponse(IMessage message) {

	}

}
