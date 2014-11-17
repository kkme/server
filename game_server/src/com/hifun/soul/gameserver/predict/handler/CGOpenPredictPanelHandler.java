package com.hifun.soul.gameserver.predict.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.guide.GuideType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.predict.manager.PredictTemplateManager;
import com.hifun.soul.gameserver.predict.msg.CGOpenPredictPanel;
import com.hifun.soul.gameserver.predict.msg.GCOpenPredictPanel;
import com.hifun.soul.gameserver.predict.msg.info.PredictInfo;
import com.hifun.soul.gameserver.predict.template.PredictPageTemplate;

/**
 * 打开预见面板
 * 
 * @author yandajun
 * 
 */
@Component
public class CGOpenPredictPanelHandler implements
		IMessageHandlerWithType<CGOpenPredictPanel> {

	@Override
	public short getMessageType() {
		return MessageType.CG_OPEN_PREDICT_PANEL;
	}

	@Override
	public void execute(CGOpenPredictPanel message) {
		Human human = message.getPlayer().getHuman();
		// 判断功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.PREDICT, true)) {
			return;
		}
		PredictTemplateManager templateManager = GameServerAssist
				.getPredictTemplateManager();
		// 根据角色当前激活的预见ID获取页码模板
		PredictPageTemplate pageTemplate = templateManager
				.getCurrentPageTemplate(human.getCurrentPredictId());
		int pageIndex = pageTemplate.getId();
		// 返回消息
		GCOpenPredictPanel msg = new GCOpenPredictPanel();
		msg.setPageIndex(pageIndex);
		msg.setCanActivateNum(human.getHumanPredictManager()
				.getRemainCanActivatePredictNum());
		msg.setMinLevel(pageTemplate.getMinLevel());
		msg.setMaxLevel(pageTemplate.getMaxLevel());
		msg.setPredictInfos(human.getHumanPredictManager()
				.getPredictInfoList(pageIndex).toArray(new PredictInfo[0]));
		msg.setCurrentProperties(human.getHumanPredictManager()
				.getPredictProperties(human.getCurrentPredictId()));
		msg.setNextProperties(human.getHumanPredictManager()
				.getPredictProperties(human.getCurrentPredictId() + 1));
		msg.setIsLastPage(pageIndex == templateManager.getLastPageIndex());
		human.sendMessage(msg);
		// 新手引导
		human.getHumanGuideManager().showGuide(
				GuideType.OPEN_PREDICT_PANEL.getIndex());
	}
}
