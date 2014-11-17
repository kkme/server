package com.hifun.soul.gameserver.horoscope.handler;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.HoroscopeLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.horoscope.HoroscopeBagType;
import com.hifun.soul.gameserver.horoscope.manager.HumanHoroscopeManager;
import com.hifun.soul.gameserver.horoscope.msg.CGHoroscopePickUp;
import com.hifun.soul.gameserver.horoscope.msg.GCHoroscopeUpdateAndRemove;
import com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;

/**
 * 拾取星运处理器;
 * 
 * @author crazyjohn
 * 
 */
@Component
public class CGHoroscopePickUpHandler implements
		IMessageHandlerWithType<CGHoroscopePickUp> {
	@Autowired
	private GameFuncService gameFuncService;

	@Override
	public short getMessageType() {
		return MessageType.CG_HOROSCOPE_PICK_UP;
	}

	@Override
	public void execute(CGHoroscopePickUp message) {
		Player player = message.getPlayer();
		if (player == null) {
			return;
		}

		Human human = player.getHuman();
		if (human == null) {
			return;
		}
		int fromBagIndex = message.getFromBagIndex();
		// 判断功能是否开放
		if (!gameFuncService.gameFuncIsOpen(human, GameFuncType.GAMBLE, true)) {
			return;
		}

		if (fromBagIndex < 0) {
			return;
		}
		HumanHoroscopeManager manager = human.getHumanHoroscopeManager();
		HoroscopeInfo fromHoroscopeInfo = manager.getHoroscopeInfo(
				HoroscopeBagType.MAIN_BAG, fromBagIndex);
		// from星运不能为空
		if (fromHoroscopeInfo == null) {
			return;
		}
		// 存储背包是否有空位
		if (!manager.hasEmptyGridStorageBag()) {
			human.sendErrorMessage(LangConstants.HOROSCOPE_STRORAGE_FULL);
			return;
		}
		String param = MessageFormat.format(HoroscopeLogReason.MOVE.getReasonText(), HoroscopeBagType.MAIN_BAG,fromBagIndex);
		HoroscopeInfo horoscopeInfo = manager.updateHoroscopeInfo(HoroscopeBagType.STORAGE_BAG, -1, fromHoroscopeInfo, HoroscopeLogReason.MOVE, param);
		manager.removeHoroscopeInfo(HoroscopeBagType.MAIN_BAG, fromBagIndex, null, "");
		
		GCHoroscopeUpdateAndRemove gcMsg = new GCHoroscopeUpdateAndRemove();
		gcMsg.setHoroscopeInfo(horoscopeInfo);
		gcMsg.setRemoveBagType(HoroscopeBagType.MAIN_BAG.getIndex());
		gcMsg.setRemoveBagIndex(fromBagIndex);
		human.sendMessage(gcMsg);
		// 最后通知移除消息
		
	}

}
