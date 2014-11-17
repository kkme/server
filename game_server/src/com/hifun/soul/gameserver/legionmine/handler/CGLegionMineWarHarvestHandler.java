package com.hifun.soul.gameserver.legionmine.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gameserver.activity.ActivityType;
import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.cd.manager.HumanCdManager;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legionmine.LegionMineMember;
import com.hifun.soul.gameserver.legionmine.manager.GlobalLegionMineWarManager;
import com.hifun.soul.gameserver.legionmine.msg.CGLegionMineWarHarvest;

@Component
public class CGLegionMineWarHarvestHandler implements
		IMessageHandlerWithType<CGLegionMineWarHarvest> {
	@Autowired
	private GlobalLegionMineWarManager globalLegionMineWarManager;

	@Override
	public short getMessageType() {
		return MessageType.CG_LEGION_MINE_WAR_HARVEST;
	}

	@Override
	public void execute(CGLegionMineWarHarvest message) {
		Human human = message.getPlayer().getHuman();
		LegionMineMember mineMember = globalLegionMineWarManager
				.getJoinLegionMineMember(human.getHumanGuid());
		if (mineMember == null) {
			return;
		}
		if (mineMember.getMineIndex() == GlobalLegionMineWarManager.ORIGIN_INDEX) {
			human.sendErrorMessage(LangConstants.LEGION_MINE_CANT_HARVEST);
			return;
		}
		// 判断cd是否冷却
		HumanCdManager cdManager = human.getHumanCdManager();
		long cd = cdManager.getSpendTime(CdType.LEGION_MINE_WAR_HARVEST,
				GameServerAssist.getLegionMineWarTemplateManager()
						.getConstantsTemplate().getHarvestBaseCdTime()
						* TimeUtils.MIN);
		if (!cdManager.canAddCd(CdType.LEGION_MINE_WAR_HARVEST, cd)) {
			human.sendErrorMessage(LangConstants.CD_LIMIT);
			return;
		}
		// 结算占领值
		int harvestOccupyValue = globalLegionMineWarManager
				.getCurrentOccupyValue(human.getHumanGuid());
		mineMember.setOccupyValue(mineMember.getOccupyValue()
				+ harvestOccupyValue);
		human.sendImportantMessage(LangConstants.HARVEST_OCCUPY_VALUE,
				harvestOccupyValue);
		globalLegionMineWarManager.updateLegionMineMember(mineMember);
		globalLegionMineWarManager.broadCastJoinLegionInfos(human
				.getHumanGuid());
		// 如果军团占领值达到条件，结束矿战
		if (globalLegionMineWarManager.getOccupyValueOfLegion(mineMember
				.getLegionType()) >= GameServerAssist
				.getLegionMineWarTemplateManager().getConstantsTemplate()
				.getLegionWinNeedOccupyValue()) {
			GameServerAssist.getGlobalActivityManager().abortAcivity(
					ActivityType.LEGION_MINE);
		} else {
			// 添加CD
			cdManager.addCd(CdType.LEGION_MINE_WAR_HARVEST, cd);
			cdManager.snapCdQueueInfo(CdType.LEGION_MINE_WAR_HARVEST);
		}
	}
}
