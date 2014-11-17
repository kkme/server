package com.hifun.soul.robot.startup;

import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.core.server.IMessageHandler;
import com.hifun.soul.core.session.MinaSession;
import com.hifun.soul.core.util.MathUtils;
import com.hifun.soul.gameserver.common.msg.GCMessage;
import com.hifun.soul.gameserver.human.msg.GCCharacterInfo;
import com.hifun.soul.gameserver.player.msg.CGCreateChar;
import com.hifun.soul.gameserver.player.msg.CGEnterSceneReady;
import com.hifun.soul.gameserver.player.msg.CGGetCharList;
import com.hifun.soul.gameserver.player.msg.CGSelectChar;
import com.hifun.soul.gameserver.player.msg.GCCharList;
import com.hifun.soul.gameserver.player.msg.GCCharacterTemplate;
import com.hifun.soul.gameserver.player.msg.GCCreateCharResult;
import com.hifun.soul.gameserver.player.msg.GCEnterScene;
import com.hifun.soul.gameserver.player.msg.GCPlayerLoginResult;
import com.hifun.soul.robot.Robot;
import com.hifun.soul.robot.RobotManager;
import com.hifun.soul.robot.task.IRobotTask;

public class RobotClientMsgHandler implements IMessageHandler<IMessage> {
	
	private Robot getRobot(IMessage message)
	{
		MinaSession minaSession = (MinaSession)((GCMessage)message).getSession();
		return RobotManager.getInstance().getRobot(minaSession);		
	}

	@Override
	public void execute(IMessage message) {
		Robot robot = getRobot(message);
		{
			if(message instanceof GCPlayerLoginResult){
				// 登陆成功会发GCCharacterTemplate的消息
			}
			else if(message instanceof GCCharacterTemplate){
				CGGetCharList getCharList = new CGGetCharList();
				robot.sendMessage(getCharList);
			}
			else if(message instanceof GCCreateCharResult){
				// 创建完的同时会下发角色列表，这个地方什么都不做
			}
			else if(message instanceof GCCharList){
				GCCharList msg = (GCCharList)message;
				if(msg.getCharList().length > 0){
					CGSelectChar selectChar = new CGSelectChar();
					selectChar.setCharIndex(0);
					robot.sendMessage(selectChar);
				}
				else{
					CGCreateChar createChar = new CGCreateChar();
					createChar.setName(robot.getName());
					createChar.setOccupation(MathUtils.random(new Integer[]{1,2,4}));
					robot.sendMessage(createChar);
				}
			}
			else if(message instanceof GCEnterScene){
				CGEnterSceneReady enterSceneReady = new CGEnterSceneReady();
				robot.sendMessage(enterSceneReady);
			}
			else if(message instanceof GCCharacterInfo){
				robot.setCanHandleMsg(true);
				robot.startTasks();
				System.out.println(robot.getName());
			}
			else{
				for(IRobotTask task : robot.getTaskList())
				{
					task.onResponse(message);
				}
			}
		}
	}

	@Override
	public short[] getTypes() {
		return null;
	}


}
