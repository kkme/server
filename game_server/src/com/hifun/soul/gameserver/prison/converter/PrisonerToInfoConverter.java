package com.hifun.soul.gameserver.prison.converter;

import java.util.ArrayList;
import java.util.List;

import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.legion.Legion;
import com.hifun.soul.gameserver.prison.Prisoner;
import com.hifun.soul.gameserver.prison.msg.HelperInfo;
import com.hifun.soul.gameserver.prison.msg.MasterInfo;
import com.hifun.soul.gameserver.prison.msg.PrisonerInfo;
import com.hifun.soul.gameserver.prison.msg.SlaveInfo;

public class PrisonerToInfoConverter {

	public static PrisonerInfo convert(Prisoner prisoner) {
		PrisonerInfo info = new PrisonerInfo();
		info.setHumanId(prisoner.getHumanId());
		info.setHumanName(prisoner.getHumanName());
		info.setHumanLevel(prisoner.getHumanLevel());
		Legion legion = GameServerAssist.getGlobalLegionManager().getLegion(
				prisoner.getHumanId());
		if (legion != null) {
			info.setLegionId(legion.getId());
			info.setLegionName(legion.getLegionName());
		}
		info.setOccupationType(GameServerAssist.getFriendService()
				.getHumanInfo(info.getHumanId()).getHumanOccupation());
		return info;
	}

	public static HelperInfo convertToHelperInfo(Prisoner prisoner) {
		HelperInfo info = new HelperInfo();
		info.setHumanId(prisoner.getHumanId());
		info.setHumanName(prisoner.getHumanName());
		info.setHumanLevel(prisoner.getHumanLevel());
		Legion legion = GameServerAssist.getGlobalLegionManager().getLegion(
				prisoner.getHumanId());
		if (legion != null) {
			info.setLegionId(legion.getId());
			info.setLegionName(legion.getLegionName());
		}
		info.setOccupationType(GameServerAssist.getFriendService()
				.getHumanInfo(info.getHumanId()).getHumanOccupation());
		return info;
	}

	public static MasterInfo convertToMasterInfo(Prisoner master, Prisoner slave) {
		MasterInfo info = new MasterInfo();
		info.setHumanId(master.getHumanId());
		info.setHumanName(master.getHumanName());
		info.setHumanLevel(master.getHumanLevel());
		Legion legion = GameServerAssist.getGlobalLegionManager().getLegion(
				master.getHumanId());
		if (legion != null) {
			info.setLegionId(legion.getId());
			info.setLegionName(legion.getLegionName());
		}
		// 显示主人与奴隶互动剩余时间或奴隶与主人互动时间
		long now = GameServerAssist.getSystemTimeService().now();
		long longTimeDiff = slave.getLastBeInteractedTime()
				+ GameServerAssist.getGameConstants().getInteractTimeLimit()
				* TimeUtils.MIN - now;
		if (longTimeDiff <= 0) {
			longTimeDiff = slave.getLastInteractTime()
					+ GameServerAssist.getGameConstants()
							.getInteractTimeLimit() * TimeUtils.MIN - now;
		}
		if (longTimeDiff > 0) {
			info.setInteractTimeDiff((int) longTimeDiff);
		}
		info.setOccupationType(GameServerAssist.getFriendService()
				.getHumanInfo(info.getHumanId()).getHumanOccupation());
		return info;
	}

	public static PrisonerInfo[] convertToArray(List<Prisoner> prisonerList) {
		PrisonerInfo[] infos = new PrisonerInfo[prisonerList.size()];
		List<PrisonerInfo> infoList = new ArrayList<PrisonerInfo>();
		for (Prisoner prisoner : prisonerList) {
			PrisonerInfo info = new PrisonerInfo();
			info.setHumanId(prisoner.getHumanId());
			info.setHumanName(prisoner.getHumanName());
			info.setHumanLevel(prisoner.getHumanLevel());
			Legion legion = GameServerAssist.getGlobalLegionManager()
					.getLegion(prisoner.getHumanId());
			if (legion != null) {
				info.setLegionId(legion.getId());
				info.setLegionName(legion.getLegionName());
			}
			info.setIdentityType(prisoner.getIdentityType());
			info.setOccupationType(GameServerAssist.getFriendService()
					.getHumanInfo(info.getHumanId()).getHumanOccupation());
			infoList.add(info);
		}
		return infoList.toArray(infos);
	}

	public static HelperInfo[] convertToHelperInfoArray(
			List<Prisoner> prisonerList) {
		HelperInfo[] infos = new HelperInfo[prisonerList.size()];
		List<HelperInfo> infoList = new ArrayList<HelperInfo>();
		for (Prisoner prisoner : prisonerList) {
			HelperInfo info = new HelperInfo();
			info.setHumanId(prisoner.getHumanId());
			info.setHumanName(prisoner.getHumanName());
			info.setHumanLevel(prisoner.getHumanLevel());
			Legion legion = GameServerAssist.getGlobalLegionManager()
					.getLegion(prisoner.getHumanId());
			if (legion != null) {
				info.setLegionId(legion.getId());
				info.setLegionName(legion.getLegionName());
			}
			info.setOccupationType(GameServerAssist.getFriendService()
					.getHumanInfo(info.getHumanId()).getHumanOccupation());
			infoList.add(info);
		}
		return infoList.toArray(infos);
	}

	public static SlaveInfo[] convertToSlaveInfoArray(List<Prisoner> slaveList) {
		SlaveInfo[] infos = new SlaveInfo[slaveList.size()];
		List<SlaveInfo> infoList = new ArrayList<SlaveInfo>();
		for (Prisoner slave : slaveList) {
			SlaveInfo info = new SlaveInfo();
			info.setHumanId(slave.getHumanId());
			info.setHumanName(slave.getHumanName());
			// 显示被抓捕时的等级
			info.setHumanLevel(slave.getBeSlaveSelfLevel());
			Legion legion = GameServerAssist.getGlobalLegionManager()
					.getLegion(slave.getHumanId());
			if (legion != null) {
				info.setLegionId(legion.getId());
				info.setLegionName(legion.getLegionName());
			}
			info.setCurrentExperience(slave.getCurrentExperience());
			info.setTotalExperience(slave.getTotalExperience());
			long now = GameServerAssist.getSystemTimeService().now();
			long slaveTimeDiff = slave.getBeSlaveTime()
					+ GameServerAssist.getGameConstants()
							.getHoldSlaveTimeLimit() * TimeUtils.HOUR - now;
			if (slaveTimeDiff > 0) {
				info.setSlaveTimeDiff((int) slaveTimeDiff);
			}
			// 显示主人与奴隶互动剩余时间或奴隶与主人互动时间
			long longInteractTimeDiff = slave.getLastBeInteractedTime()
					+ GameServerAssist.getGameConstants()
							.getInteractTimeLimit() * TimeUtils.MIN - now;
			if (longInteractTimeDiff <= 0) {
				longInteractTimeDiff = slave.getLastInteractTime()
						+ GameServerAssist.getGameConstants()
								.getInteractTimeLimit() * TimeUtils.MIN - now;
			}
			if (longInteractTimeDiff > 0) {
				info.setBeInteractedTimeDiff((int) longInteractTimeDiff);
			}
			info.setOccupationType(GameServerAssist.getFriendService()
					.getHumanInfo(info.getHumanId()).getHumanOccupation());
			infoList.add(info);
		}
		return infoList.toArray(infos);
	}

}
