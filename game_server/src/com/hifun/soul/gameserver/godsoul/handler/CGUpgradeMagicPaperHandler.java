package com.hifun.soul.gameserver.godsoul.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.godsoul.manager.GodsoulTemplateManager;
import com.hifun.soul.gameserver.godsoul.msg.CGUpgradeMagicPaper;
import com.hifun.soul.gameserver.godsoul.template.MagicPaperLevelTemplate;
import com.hifun.soul.gameserver.human.Human;

@Component
public class CGUpgradeMagicPaperHandler implements
		IMessageHandlerWithType<CGUpgradeMagicPaper> {
	@Autowired
	private GameFuncService gameFuncService;

	@Override
	public short getMessageType() {
		return MessageType.CG_UPGRADE_MAGIC_PAPER;
	}

	@Override
	public void execute(CGUpgradeMagicPaper message) {
		if (message.getPlayer() == null) {
			return;
		}
		Human human = message.getPlayer().getHuman();
		if (human == null) {
			return;
		}
		// 判断功能是否开放
		if (!gameFuncService.gameFuncIsOpen(human, GameFuncType.GODSOUL, true)) {
			return;
		}
		int paperId = message.getPaperId();
		int currentLevel = human.getHumanGodsoulManager()
				.getMagicPaperCurrentLevel(paperId);
		GodsoulTemplateManager templateManager = GameServerAssist
				.getGodsoulTemplateManager();
		// 判断是否达到最高级
		if (currentLevel >= templateManager.getMagicPaperMaxLevell(paperId)) {
			human.sendErrorMessage(LangConstants.UPGRADE_EQUIP_BIT_TO_MAX);
			return;
		}
		MagicPaperLevelTemplate currentTemplate = templateManager
				.getMagicPaperTemplate(paperId, currentLevel);
		// 判断材料是否充足
		int costItemId = currentTemplate.getCostItemId();
		int costItemNum = currentTemplate.getCostItemNum();
		if (human.getBagManager().getItemCount(costItemId) < costItemNum) {
			human.sendErrorMessage(LangConstants.UPGRADE_MAGIC_PAPER_MATERIAL_NOT_ENOUGH);
			return;
		}
		// 升级灵图
		human.getHumanGodsoulManager().upgradeMagicPaper(paperId);
	}
}
