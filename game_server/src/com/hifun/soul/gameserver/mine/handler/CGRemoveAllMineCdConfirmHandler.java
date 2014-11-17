package com.hifun.soul.gameserver.mine.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.mine.manager.HumanMineManager;
import com.hifun.soul.gameserver.mine.msg.CGRemoveAllMineCdConfirm;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGRemoveAllMineCdConfirmHandler implements
		IMessageHandlerWithType<CGRemoveAllMineCdConfirm> {
	@Autowired
	private GameFuncService gameFuncService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_REMOVE_ALL_MINE_CD_CONFIRM;
	}

	@Override
	public void execute(CGRemoveAllMineCdConfirm message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		
		// 判断功能是否开启
		if(!gameFuncService.gameFuncIsOpen(human, GameFuncType.REMOVE_ALL_MINE_CD, true)){
			return;
		}
		
		// 清除矿坑cd
		HumanMineManager mineManager = human.getHumanMineManager();
		mineManager.removeAllMineCd();
	}

}
