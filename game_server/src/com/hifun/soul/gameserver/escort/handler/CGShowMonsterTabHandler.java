package com.hifun.soul.gameserver.escort.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.escort.EscortInvite;
import com.hifun.soul.gameserver.escort.info.EscortFriendInfo;
import com.hifun.soul.gameserver.escort.info.EscortMonsterInfo;
import com.hifun.soul.gameserver.escort.manager.GlobalEscortManager;
import com.hifun.soul.gameserver.escort.msg.CGShowMonsterTab;
import com.hifun.soul.gameserver.escort.msg.GCShowMonsterTab;
import com.hifun.soul.gameserver.escort.template.EscortConstantsTemplate;
import com.hifun.soul.gameserver.escort.template.EscortMonsterTemplate;
import com.hifun.soul.gameserver.escort.template.EscortRewardTemplate;
import com.hifun.soul.gameserver.friend.FriendInfo;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;

/**
 * 展示押运怪物页
 * 
 * @author yandajun
 * 
 */
@Component
public class CGShowMonsterTabHandler implements
		IMessageHandlerWithType<CGShowMonsterTab> {

	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_MONSTER_TAB;
	}

	@Override
	public void execute(CGShowMonsterTab message) {
		Human human = message.getPlayer().getHuman();
		// 判断功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.ESCORT, true)) {
			return;
		}
		// 判断是否有押运次数
		if (human.getEscortRemainNum() <= 0) {
			human.sendErrorMessage(LangConstants.HAS_NO_ESCORT_NUM);
			return;
		}
		GlobalEscortManager globalEscortManager = GameServerAssist
				.getGlobalEscortManager();
		EscortConstantsTemplate constantsTemplate = GameServerAssist
				.getEscortTemplateManager().getConstantsTemplate();
		GCShowMonsterTab msg = new GCShowMonsterTab();
		msg.setDefaultMonsterType(globalEscortManager
				.getEscortMonsterType(human.getHumanGuid()));
		msg.setMonsterInfos(generateMonsterInfoList(human).toArray(
				new EscortMonsterInfo[0]));
		msg.setCanCall(human.getVipLevel() >= constantsTemplate
				.getCallVipLevel());
		msg.setCallMonsterCost(constantsTemplate.getCallCost());
		int refreshNum = human.getEscortRefreshMonsterNum();
		msg.setRefreshMonsterCost(GameServerAssist.getEscortTemplateManager()
				.getRefreshMonsterCost(refreshNum + 1));
		msg.setEncourageCost(constantsTemplate.getEncourageCost());
		msg.setEncourageAttackRate(constantsTemplate.getEncourageAttackRate());
		msg.setEncourageHpRate(constantsTemplate.getEncourageHpRate());
		msg.setFriendInfos(generateFriendInfoList(human).toArray(
				new EscortFriendInfo[0]));
		// 邀请信息
		msg.setInviteFriendState(globalEscortManager
				.getInviteEscortState(human));
		EscortInvite escortInvite = globalEscortManager.getEscortInvite(human
				.getHumanGuid());
		if (escortInvite != null) {
			long remainTime = escortInvite.getAgreeEndTime()
					- GameServerAssist.getSystemTimeService().now();
			if (remainTime > 0) {
				msg.setInviteRemainTime((int) remainTime);
			} else {
				msg.setInviteRemainTime(0);
			}
		}
		human.sendMessage(msg);
	}

	/**
	 * 生成押运怪物信息
	 */
	private List<EscortMonsterInfo> generateMonsterInfoList(Human human) {
		List<EscortMonsterInfo> monsterInfoList = new ArrayList<EscortMonsterInfo>();
		Map<Integer, EscortMonsterTemplate> monsterTemplates = GameServerAssist
				.getEscortTemplateManager().getMonsterTemplates();
		for (int monsterType : monsterTemplates.keySet()) {
			EscortMonsterTemplate monsterTemplate = monsterTemplates
					.get(monsterType);
			EscortMonsterInfo monsterInfo = new EscortMonsterInfo();
			monsterInfo.setMonsterType(monsterType);
			monsterInfo.setMonsterIconId(monsterTemplate.getIconId());
			monsterInfo.setMonsterName(monsterTemplate.getMonsterName());
			EscortRewardTemplate rewardTemplate = GameServerAssist
					.getEscortTemplateManager().getEscortRewardTemplate(
							human.getLevel());
			int rewardCoin = (int) (rewardTemplate.getRewardCoin() * monsterTemplate
					.getCoinRate());
			monsterInfo.setEscortRewardCoin(rewardCoin);
			int escortTime = (int) (rewardTemplate.getRescortTime() * monsterTemplate
					.getEscortTimeRate());
			monsterInfo.setEscortTime(escortTime);
			monsterInfoList.add(monsterInfo);
		}
		return monsterInfoList;
	}

	/**
	 * 生成邀请好友信息
	 */
	private List<EscortFriendInfo> generateFriendInfoList(Human human) {
		List<EscortFriendInfo> escortFriendInfoList = new ArrayList<EscortFriendInfo>();
		Map<Long, FriendInfo> friendInfoMap = GameServerAssist
				.getFriendService().getAllFriends(human.getHumanGuid());

		for (Long friendId : friendInfoMap.keySet()) {
			Human friend = GameServerAssist.getGameWorld()
					.getSceneHumanManager().getHumanByGuid(friendId);
			// 好友必须在线，有剩余协助次数，且功能需要开启
			if (friend != null
					&& friend.getEscortHelpRemainNum() > 0
					&& GameServerAssist.getGameFuncService().gameFuncIsOpen(
							friend, GameFuncType.ESCORT, false)) {
				EscortFriendInfo escortFriendInfo = new EscortFriendInfo();
				escortFriendInfo.setFriendId(friendId);
				escortFriendInfo.setFriendName(friend.getName());
				escortFriendInfo.setFriendLevel(friend.getLevel());
				escortFriendInfo.setMaxHelpNum(GameServerAssist
						.getEscortTemplateManager().getConstantsTemplate()
						.getMaxHelpNum());
				escortFriendInfo.setRemainHelpNum(friend
						.getEscortHelpRemainNum());
				escortFriendInfoList.add(escortFriendInfo);
			}

		}
		return escortFriendInfoList;
	}
}
