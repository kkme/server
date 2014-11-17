package com.hifun.soul.gameserver.levy.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.GameConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.guide.GuideType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.levy.HumanLevyManager;
import com.hifun.soul.gameserver.levy.MagicStoneInfo;
import com.hifun.soul.gameserver.levy.msg.CGOpenMainCityPanel;
import com.hifun.soul.gameserver.levy.msg.GCUpdateMainCityPanel;
import com.hifun.soul.gameserver.vip.template.VipPrivilegeTemplate;

/**
 * 打开主城面板
 * 
 * @author magicstone
 * 
 */
@Component
public class CGOpenMainCityPanelHandler implements
		IMessageHandlerWithType<CGOpenMainCityPanel> {

	@Override
	public short getMessageType() {
		return MessageType.CG_OPEN_MAIN_CITY_PANEL;
	}

	@Override
	public void execute(CGOpenMainCityPanel message) {
		if (message.getPlayer() == null) {
			return;
		}
		Human human = message.getPlayer().getHuman();
		if (human == null) {
			return;
		}
		HumanLevyManager manager = human.getLevyManager();
		GameConstants gameConstants = GameServerAssist.getGameConstants();
		GCUpdateMainCityPanel gcUpdateMsg = new GCUpdateMainCityPanel();
		int maxCrystalCollect = GameServerAssist
				.getVipPrivilegeTemplateManager()
				.getVipTemplate(human.getVipLevel()).getMaxCrystalCollectNum();
		gcUpdateMsg.setCrystalCollectRemainNum(maxCrystalCollect
				- manager.getCostCrystalCollectNum());
		gcUpdateMsg.setCollectTarget(manager.getCollectTargets().toArray(
				new MagicStoneInfo[0]));
		gcUpdateMsg.setCurrentCollected(manager.getCollectedMagicStones()
				.toArray(new MagicStoneInfo[0]));
		gcUpdateMsg.setCostCrystalNum(manager.getCostCrystalNum());
		gcUpdateMsg.setFreeCollectNum(manager.getFreeCollectNum());
		gcUpdateMsg.setRemainLevyNum(manager.getRemainLevyNum());
		gcUpdateMsg.setMaxLevyNum(gameConstants.getLevyTime());
		gcUpdateMsg.setLevyExtraRate(manager.getLevyExtraRate());
		gcUpdateMsg.setMaxLevyExtraRate(gameConstants.getMaxLeveExtraRate());
		gcUpdateMsg.setLevyRevenue(manager.getLevyRevenue());
		gcUpdateMsg.setRemainBetNum(human.getLevyBetRemainNum());
		gcUpdateMsg.setMaxtBetNum(gameConstants.getLevyBetTime());
		gcUpdateMsg.setBetPoints(manager.getBetPoints());
		VipPrivilegeTemplate vipTemplate = GameServerAssist
				.getVipPrivilegeTemplateManager().getVipTemplate(
						human.getVipLevel());
		if (vipTemplate == null) {
			return;
		}
		int vipMaxWinNum = vipTemplate.getMaxLevyCertainWinTimes();
		gcUpdateMsg.setRemainCertainWinNum(vipMaxWinNum
				- human.getLevyCertainWinUsedNum());
		gcUpdateMsg.setCertainWinCost(GameServerAssist.getLevyTemplateManager()
				.getCertainWinCost(human.getLevyCertainWinUsedNum() + 1));
		gcUpdateMsg.setRecoverEnergyNum(GameServerAssist.getGameConstants()
				.getEnergyHandRecoverNum());
		gcUpdateMsg.setRemainRecoverNum(human.getTotalRecoverEnergyNum());
		int maxTimes = GameServerAssist.getVipPrivilegeTemplateManager()
				.getVipTemplate(human.getVipLevel())
				.getMaxEnergyRecoverTotalTimes();
		gcUpdateMsg.setMaxRecoverNum(maxTimes);
		human.sendMessage(gcUpdateMsg);
		// 下发税收CD信息
		human.getHumanCdManager().snapCdQueueInfo(CdType.LEVY);
		// 下发体力恢复CD信息
		human.getHumanCdManager().snapCdQueueInfo(CdType.ENERGY_RECOVER);
		// 引导
		human.getHumanGuideManager().showGuide(
				GuideType.OPEN_MAIN_CITY.getIndex());
	}

}
