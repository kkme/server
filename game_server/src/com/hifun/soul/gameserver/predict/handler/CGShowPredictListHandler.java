package com.hifun.soul.gameserver.predict.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.predict.manager.PredictTemplateManager;
import com.hifun.soul.gameserver.predict.msg.CGShowPredictList;
import com.hifun.soul.gameserver.predict.msg.GCShowPredictList;
import com.hifun.soul.gameserver.predict.msg.info.PredictInfo;
import com.hifun.soul.gameserver.predict.template.PredictPageTemplate;

/**
 * 展示某页的预见列表
 * 
 * @author yandajun
 * 
 */
@Component
public class CGShowPredictListHandler implements
		IMessageHandlerWithType<CGShowPredictList> {

	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_PREDICT_LIST;
	}

	@Override
	public void execute(CGShowPredictList message) {
		Human human = message.getPlayer().getHuman();
		if (human == null) {
			return;
		}
		// 判断功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.PREDICT, true)) {
			return;
		}
		PredictTemplateManager templateManager = GameServerAssist
				.getPredictTemplateManager();
		int pageIndex = message.getPageIndex();
		if (pageIndex > templateManager.getLastPageIndex() || pageIndex <= 0) {
			return;
		}
		// 获得页面模板
		PredictPageTemplate pageTemplate = templateManager
				.getPageTemplate(pageIndex);
		// 该页面是否可见
		if (human.getLevel() < pageTemplate.getVisibleLevel()) {
			human.sendWarningMessage(LangConstants.PREDICT_PAGE_NOT_VISIBLE,
					pageTemplate.getVisibleLevel());
			return;
		}
		// 返回消息
		GCShowPredictList msg = new GCShowPredictList();
		msg.setPageIndex(pageIndex);
		msg.setMinLevel(pageTemplate.getMinLevel());
		msg.setMaxLevel(pageTemplate.getMaxLevel());
		msg.setPredictInfos(human.getHumanPredictManager()
				.getPredictInfoList(pageIndex).toArray(new PredictInfo[0]));
		msg.setIsLastPage(pageIndex == templateManager.getLastPageIndex());
		human.sendMessage(msg);
	}

}
