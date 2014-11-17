package com.hifun.soul.gameserver.escort.converter;

import com.hifun.soul.gamedb.entity.EscortEntity;
import com.hifun.soul.gamedb.entity.EscortHelpEntity;
import com.hifun.soul.gamedb.entity.EscortInviteEntity;
import com.hifun.soul.gamedb.entity.EscortLegionPrayEntity;
import com.hifun.soul.gamedb.entity.EscortLogEntity;
import com.hifun.soul.gamedb.entity.EscortRobRankEntity;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.escort.EscortHelp;
import com.hifun.soul.gameserver.escort.EscortInvite;
import com.hifun.soul.gameserver.escort.EscortLog;
import com.hifun.soul.gameserver.escort.EscortRobRank;
import com.hifun.soul.gameserver.escort.info.EscortInfo;
import com.hifun.soul.gameserver.escort.info.LegionPrayInfo;
import com.hifun.soul.gameserver.escort.manager.EscortTemplateManager;
import com.hifun.soul.gameserver.escort.template.EscortMonsterTemplate;
import com.hifun.soul.gameserver.legion.Legion;

public class EscortConverters {
	public static EscortInfo escortEntityToInfo(EscortEntity escortEntity) {
		EscortTemplateManager templateManager = GameServerAssist
				.getEscortTemplateManager();
		EscortInfo escortInfo = new EscortInfo();
		escortInfo.setEscortId((Long) escortEntity.getId());
		int monsterType = escortEntity.getMonsterType();
		escortInfo.setMonsterType(escortEntity.getMonsterType());
		EscortMonsterTemplate monsterTempalte = templateManager
				.getMonsterTemplate(monsterType);
		if (monsterTempalte != null) {
			escortInfo.setMonsterModelId(monsterTempalte.getModelId());
		}
		escortInfo.setOwnerId(escortEntity.getOwnerId());
		escortInfo.setOwnerName(escortEntity.getOwnerName());
		escortInfo.setOwnerLevel(escortEntity.getOwnerLevel());
		Legion legion = GameServerAssist.getGlobalLegionManager().getLegion(
				escortEntity.getOwnerId());
		if (legion != null) {
			escortInfo.setOwnerLegionName(legion.getLegionName());
		}
		escortInfo.setHelperId(escortEntity.getHelperId());
		escortInfo.setHelperName(escortEntity.getHelperName());
		escortInfo.setHelperLevel(escortEntity.getHelperLevel());
		int maxBeRobbedNum = templateManager.getConstantsTemplate()
				.getMaxBeRobbedNum();
		escortInfo.setRemainBeRobbedNum(maxBeRobbedNum
				- escortEntity.getBeRobbedNum());
		escortInfo.setMaxBeRobbedNum(maxBeRobbedNum);
		escortInfo.setEncourageRate(escortEntity.getEncourageRate());
		escortInfo.setEscortRewardCoin(escortEntity.getEscortRewardCoin());
		escortInfo.setHelpLevelDiff(escortEntity.getHelpLevelDiff());
		escortInfo.setEscortTime(escortEntity.getEscortTime());
		escortInfo.setEndTime(escortEntity.getEndTime());
		escortInfo.setEscortRewardState(escortEntity.getEscortRewardState());
		escortInfo.setEscortState(escortEntity.getEscortState());
		escortInfo.setRobing(false);
		return escortInfo;
	}

	public static EscortEntity escortInfoToEntity(EscortInfo escortInfo) {
		EscortEntity escortEntity = new EscortEntity();
		escortEntity.setId(escortInfo.getEscortId());
		escortEntity.setMonsterType(escortInfo.getMonsterType());
		escortEntity.setOwnerId(escortInfo.getOwnerId());
		escortEntity.setOwnerName(escortInfo.getOwnerName());
		escortEntity.setOwnerLevel(escortInfo.getOwnerLevel());
		escortEntity.setHelperId(escortInfo.getHelperId());
		escortEntity.setHelperName(escortInfo.getHelperName());
		escortEntity.setHelperLevel(escortInfo.getHelperLevel());
		escortEntity.setBeRobbedNum(escortInfo.getMaxBeRobbedNum()
				- escortInfo.getRemainBeRobbedNum());
		escortEntity.setEncourageRate(escortInfo.getEncourageRate());
		escortEntity.setEscortRewardCoin(escortInfo.getEscortRewardCoin());
		escortEntity.setHelpLevelDiff(escortInfo.getHelpLevelDiff());
		escortEntity.setEscortTime(escortInfo.getEscortTime());
		escortEntity.setEndTime(escortInfo.getEndTime());
		escortEntity.setEscortRewardState(escortInfo.getEscortRewardState());
		escortEntity.setEscortState(escortInfo.getEscortState());
		return escortEntity;
	}

	public static LegionPrayInfo legionPrayEntityToInfo(
			EscortLegionPrayEntity prayEntity) {
		LegionPrayInfo prayInfo = new LegionPrayInfo();
		prayInfo.setLegionId((Long) prayEntity.getId());
		prayInfo.setPrayMemberId(prayEntity.getPrayMemberId());
		prayInfo.setPrayMemberName(prayEntity.getPrayMemberName());
		prayInfo.setPrayEndTime(prayEntity.getEndTime());
		prayInfo.setPrayRevenue(GameServerAssist.getEscortTemplateManager()
				.getConstantsTemplate().getLegionPrayRate());
		return prayInfo;
	}

	public static EscortLegionPrayEntity legionPrayInfoToEntity(
			LegionPrayInfo prayInfo) {
		EscortLegionPrayEntity prayEntity = new EscortLegionPrayEntity();
		prayEntity.setId(prayInfo.getLegionId());
		prayEntity.setPrayMemberId(prayInfo.getPrayMemberId());
		prayEntity.setPrayMemberName(prayInfo.getPrayMemberName());
		prayEntity.setEndTime(prayInfo.getPrayEndTime());
		return prayEntity;
	}

	public static EscortInvite entityToInvite(EscortInviteEntity inviteEntity) {
		EscortInvite escortInvite = new EscortInvite();
		escortInvite.setHumanGuid((Long) inviteEntity.getId());
		escortInvite.setInviteFriendId(inviteEntity.getInviteFriendId());
		escortInvite.setInviteState(inviteEntity.getInviteState());
		escortInvite.setAgreeEndTime(inviteEntity.getAgreeEndTime());
		return escortInvite;
	}

	public static EscortInviteEntity inviteToEntity(EscortInvite invite) {
		EscortInviteEntity entity = new EscortInviteEntity();
		entity.setId(invite.getHumanGuid());
		entity.setInviteFriendId(invite.getInviteFriendId());
		entity.setInviteState(invite.getInviteState());
		entity.setAgreeEndTime(invite.getAgreeEndTime());
		return entity;
	}

	public static EscortRobRank entityToRobRank(
			EscortRobRankEntity robRankEntity) {
		EscortRobRank robRank = new EscortRobRank();
		robRank.setRobberId((Long) robRankEntity.getId());
		robRank.setRobberName(robRankEntity.getRobberName());
		robRank.setYesterdayRobCoin(robRankEntity.getYesterdayRobCoin());
		robRank.setTodayRobCoin(robRankEntity.getTodayRobCoin());
		robRank.setRewardState(robRankEntity.getRewardState());
		return robRank;
	}

	public static EscortRobRankEntity robRankToEntity(EscortRobRank robRank) {
		EscortRobRankEntity entity = new EscortRobRankEntity();
		entity.setId(robRank.getRobberId());
		entity.setRobberName(robRank.getRobberName());
		entity.setYesterdayRobCoin(robRank.getYesterdayRobCoin());
		entity.setTodayRobCoin(robRank.getTodayRobCoin());
		entity.setRewardState(robRank.getRewardState());
		return entity;
	}

	public static EscortLog entityToLog(EscortLogEntity logEntity) {
		EscortLog logInfo = new EscortLog();
		logInfo.setId((Long) logEntity.getId());
		logInfo.setFirstId(logEntity.getFirstId());
		logInfo.setFirstName(logEntity.getFirstName());
		logInfo.setSecondId(logEntity.getSecondId());
		logInfo.setSecondName(logEntity.getSecondName());
		logInfo.setMonserName(logEntity.getMonserName());
		logInfo.setRobCoin(logEntity.getRobCoin());
		return logInfo;
	}

	public static EscortLogEntity logToEntity(EscortLog log) {
		EscortLogEntity entity = new EscortLogEntity();
		entity.setId(log.getId());
		entity.setFirstId(log.getFirstId());
		entity.setFirstName(log.getFirstName());
		entity.setSecondId(log.getSecondId());
		entity.setSecondName(log.getSecondName());
		entity.setMonserName(log.getMonserName());
		entity.setRobCoin(log.getRobCoin());
		return entity;
	}

	public static EscortHelp entityToHelp(EscortHelpEntity helpEntity) {
		EscortHelp help = new EscortHelp();
		help.setHumanGuid((Long) helpEntity.getId());
		help.setRemainHelpNum(helpEntity.getRemainHelpNum());
		help.setRewardCoin(helpEntity.getRewardCoin());
		help.setRewardState(helpEntity.getRewardState());
		return help;
	}

	public static EscortHelpEntity helpToEntiy(EscortHelp help) {
		EscortHelpEntity helpEntity = new EscortHelpEntity();
		helpEntity.setId(help.getHumanGuid());
		helpEntity.setRemainHelpNum(help.getRemainHelpNum());
		helpEntity.setRewardCoin(help.getRewardCoin());
		helpEntity.setRewardState(help.getRewardState());
		return helpEntity;
	}
}
