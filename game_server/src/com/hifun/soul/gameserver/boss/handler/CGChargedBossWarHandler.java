package com.hifun.soul.gameserver.boss.handler;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gameserver.boss.BossRoleInfo;
import com.hifun.soul.gameserver.boss.msg.CGChargedBossWar;
import com.hifun.soul.gameserver.boss.msg.GCChargedBossWar;
import com.hifun.soul.gameserver.boss.service.BossWarService;
import com.hifun.soul.gameserver.boss.template.BossTemplate;
import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.cd.manager.HumanCdManager;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGChargedBossWarHandler implements
		IMessageHandlerWithType<CGChargedBossWar> {
	private Logger logger = Loggers.BOSS_LOGGER;
	
	@Autowired
	private BossWarService bossWarService;
	@Autowired
	private GameFuncService gameFuncService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_CHARGED_BOSS_WAR;
	}

	@Override
	public void execute(CGChargedBossWar message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		// 判断功能是否开放
		if(!gameFuncService.gameFuncIsOpen(human, GameFuncType.BOSS_WAR, true)){
			return;
		}
		// 判断当前是否正处在boss战的时间段
		if(!bossWarService.bossWarIsOpen()){
			human.sendErrorMessage(LangConstants.BOSS_WAR_NOT_OPEN);
			return;
		}
		// 判断充能的cd时间是否已经到了
		HumanCdManager cdManager = human.getHumanCdManager();
		BossTemplate bossTemplate = bossWarService.getBossTemplate();
		if(bossTemplate == null){
			logger.error("can not find bossTempate!");
			return;
		}
		long spendTime = cdManager.getSpendTime(CdType.CHARGED_STRIKE_CD, bossTemplate.getCd() * TimeUtils.MIN);
		if(!cdManager.canAddCd(CdType.CHARGED_STRIKE_CD, spendTime)){
			human.sendErrorMessage(LangConstants.CD_LIMIT);
			return;
		}
		// 判断充能的上限是否已经到了上限
		BossRoleInfo bossRoleInfo = bossWarService.getBossRoleInfo(human.getHumanGuid());
		if(bossRoleInfo == null){
			return;
		}
		if(bossRoleInfo.getChargedstrikeRate() >= bossTemplate.getMaxChargedRate()){
			human.sendErrorMessage(LangConstants.CHARGED_FULL);
			return;
		}
		// 添加充能cd
		cdManager.addCd(CdType.CHARGED_STRIKE_CD, spendTime);
		// 改变充能值
		int newChargedstrikeRate = bossRoleInfo.getChargedstrikeRate() + bossTemplate.getChargedRate();
		if(newChargedstrikeRate > bossTemplate.getMaxChargedRate()){
			newChargedstrikeRate = bossTemplate.getMaxChargedRate();
		}
		bossRoleInfo.setChargedstrikeRate(newChargedstrikeRate);
		// 更新boss战角色信息
		bossWarService.updateBossRoleInfoToDB(bossRoleInfo);
		GCChargedBossWar gcMsg = new GCChargedBossWar();
		gcMsg.setChargedstrikeRate(newChargedstrikeRate*100/bossTemplate.getMaxChargedRate());
		human.sendMessage(gcMsg);
		human.getHumanCdManager().snapCdQueueInfo(CdType.CHARGED_STRIKE_CD);
	}

}
