package com.hifun.soul.gameserver.abattoir.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gameserver.abattoir.AbattoirOwnerType;
import com.hifun.soul.gameserver.abattoir.AbattoirRoom;
import com.hifun.soul.gameserver.abattoir.battle.AbattoirPVEBattleCallback;
import com.hifun.soul.gameserver.abattoir.battle.AbattoirPVPBattleCallback;
import com.hifun.soul.gameserver.abattoir.manager.GlobalAbattoirManager;
import com.hifun.soul.gameserver.abattoir.msg.CGLootAbattoirRoom;
import com.hifun.soul.gameserver.battle.manager.BattleManager;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.monster.Monster;

@Component
public class CGLootAbattoirRoomHandler implements
		IMessageHandlerWithType<CGLootAbattoirRoom> {
	@Autowired
	private BattleManager battleManager;

	@Override
	public short getMessageType() {
		return MessageType.CG_LOOT_ABATTOIR_ROOM;
	}

	@Override
	public void execute(CGLootAbattoirRoom message) {
		Human human = message.getPlayer().getHuman();
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.ABATTOIR, true)) {
			return;
		}
		GlobalAbattoirManager manager = GameServerAssist
				.getGlobalAbattoirManager();
		// 判断角斗次数
		if (human.getAbattoirRemainNum() < 1) {
			human.sendErrorMessage(LangConstants.NO_MORE_ABATTOIR_WRESTLE_TIMES);
			return;
		}
		AbattoirRoom room = manager.getAbattoirRoom(message.getRoomId());
		if (room == null) {
			human.sendErrorMessage(LangConstants.ABATTOIR_ROOM_NOT_EXIST);
			return;
		}
		// 如果是玩家占据房间，判断是否在保护时间内
		if (room.getOwnerType() == AbattoirOwnerType.PLAYER_WRESTLER.getIndex()
				&& GameServerAssist.getSystemTimeService().now() < room
						.getLootTime() + room.getProtectTime() * TimeUtils.MIN) {
			human.sendErrorMessage(LangConstants.ABATTOIR_ROOM_PROTECTING);
			return;
		}
		// 是否在抢夺中
		if (room.isFighting()) {
			human.sendErrorMessage(LangConstants.ABATTOIR_ROOM_IS_FIGHTING);
			return;
		}
		// 如果已经在房间中，提示：抢夺成功后，将与你当前房间互换
		AbattoirRoom humanRoom = manager.getHumanAbattoirRoom(human
				.getHumanGuid());
		if (humanRoom != null) {
			human.sendImportantMessage(LangConstants.ABATTOIR_ROOM_WILL_CHANGE);
		}
		AbattoirOwnerType ownerType = AbattoirOwnerType.indexOf(room
				.getOwnerType());
		switch (ownerType) {
		case NPC_WRESTLER:
			Monster monster = GameServerAssist.getMonsterFactory()
					.createMonster((int) room.getOwnerId());
			battleManager.startBattleWithMapMonster(human, monster,
					new AbattoirPVEBattleCallback(human, message.getRoomId()));
			break;
		case PLAYER_WRESTLER:
			battleManager.pvpBattleEnter(human, room.getOwnerId(),
					new AbattoirPVPBattleCallback(human, message.getRoomId()));
			break;
		}
	}
}
