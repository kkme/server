package com.hifun.soul.gameserver.bloodtemple.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gameserver.battle.manager.BattleManager;
import com.hifun.soul.gameserver.bloodtemple.BloodTempleOwnerType;
import com.hifun.soul.gameserver.bloodtemple.BloodTempleRoom;
import com.hifun.soul.gameserver.bloodtemple.battle.BloodTemplePVEBattleCallback;
import com.hifun.soul.gameserver.bloodtemple.battle.BloodTemplePVPBattleCallback;
import com.hifun.soul.gameserver.bloodtemple.manager.GlobalBloodTempleManager;
import com.hifun.soul.gameserver.bloodtemple.msg.CGLootBloodTempleRoom;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.monster.Monster;

@Component
public class CGLootBloodTempleRoomHandler implements
		IMessageHandlerWithType<CGLootBloodTempleRoom> {
	@Autowired
	private BattleManager battleManager;

	@Override
	public short getMessageType() {
		return MessageType.CG_LOOT_BLOOD_TEMPLE_ROOM;
	}

	@Override
	public void execute(CGLootBloodTempleRoom message) {
		Human human = message.getPlayer().getHuman();
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.BLOOD_TEMPLE, true)) {
			return;
		}
		GlobalBloodTempleManager manager = GameServerAssist
				.getGlobalBloodTempleManager();
		// 判断角斗次数
		if (human.getBloodTempleRemainNum() < 1) {
			human.sendErrorMessage(LangConstants.NO_MORE_BLOOD_TEMPLE_WRESTLE_TIMES);
			return;
		}
		BloodTempleRoom room = manager.getBloodTempleRoom(message.getRoomId());
		if (room == null) {
			human.sendErrorMessage(LangConstants.BLOOD_TEMPLE_ROOM_NOT_EXIST);
			return;
		}
		// 如果是玩家占据房间，判断是否在保护时间内
		if (room.getOwnerType() == BloodTempleOwnerType.PLAYER_WRESTLER
				.getIndex()
				&& GameServerAssist.getSystemTimeService().now() < room
						.getLootTime() + room.getProtectTime() * TimeUtils.MIN) {
			human.sendErrorMessage(LangConstants.BLOOD_TEMPLE_ROOM_PROTECTING);
			return;
		}
		// 是否在抢夺中
		if (room.isFighting()) {
			human.sendErrorMessage(LangConstants.BLOOD_TEMPLE_ROOM_IS_FIGHTING);
			return;
		}
		// 如果已经在房间中，提示：抢夺成功后，将与你当前房间互换
		BloodTempleRoom humanRoom = manager.getHumanBloodTempleRoom(human
				.getHumanGuid());
		if (humanRoom != null) {
			human.sendImportantMessage(LangConstants.BLOOD_TEMPLE_ROOM_WILL_CHANGE);
		}
		BloodTempleOwnerType ownerType = BloodTempleOwnerType.indexOf(room
				.getOwnerType());
		switch (ownerType) {
		case NPC_WRESTLER:
			Monster monster = GameServerAssist.getMonsterFactory()
					.createMonster((int) room.getOwnerId());
			battleManager
					.startBattleWithMapMonster(
							human,
							monster,
							new BloodTemplePVEBattleCallback(human, message
									.getRoomId()));
			break;
		case PLAYER_WRESTLER:
			battleManager
					.pvpBattleEnter(
							human,
							room.getOwnerId(),
							new BloodTemplePVPBattleCallback(human, message
									.getRoomId()));
			break;
		}
	}
}
