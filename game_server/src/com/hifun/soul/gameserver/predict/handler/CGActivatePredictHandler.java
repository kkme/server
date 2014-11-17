package com.hifun.soul.gameserver.predict.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.predict.msg.CGActivatePredict;
import com.hifun.soul.gameserver.predict.msg.GCActivatePredict;
import com.hifun.soul.gameserver.predict.template.PredictTemplate;

/**
 * 激活预见
 * 
 * @author yandajun
 * 
 */
@Component
public class CGActivatePredictHandler implements
		IMessageHandlerWithType<CGActivatePredict> {

	@Override
	public short getMessageType() {
		return MessageType.CG_ACTIVATE_PREDICT;
	}

	@Override
	public void execute(CGActivatePredict message) {
		Human human = message.getPlayer().getHuman();
		// 判断功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.PREDICT, true)) {
			return;
		}

		// 该预见已激活
		int predictId = message.getPredictId();
		if (human.getHumanPredictManager().isPredictActivated(predictId)) {
			human.sendWarningMessage(LangConstants.PREDICT_HAD_ACTIVATED);
			return;
		}
		// 前面还有没激活的预见
		if (predictId - human.getCurrentPredictId() > 1) {
			human.sendWarningMessage(LangConstants.PREDICT_ACTIVATE_NEED_ORDER);
			return;
		}
		// 等级是否足够
		PredictTemplate template = GameServerAssist.getPredictTemplateManager()
				.getPredictTemplate(predictId);
		if (human.getLevel() < template.getNeedLevel()) {
			human.sendWarningMessage(LangConstants.PREDICT_ACTIVATE_LEVEL,
					template.getNeedLevel());
			return;
		}
		// 激活预见
		human.getHumanPredictManager().activatePredict(predictId);

		// 返回消息
		GCActivatePredict msg = new GCActivatePredict();
		msg.setPredictId(predictId);
		msg.setActivated(true);
		msg.setCurrentProperties(human.getHumanPredictManager()
				.getPredictProperties(human.getCurrentPredictId()));
		msg.setNextProperties(human.getHumanPredictManager()
				.getPredictProperties(human.getCurrentPredictId() + 1));
		msg.setCanActivateNum(GameServerAssist.getPredictTemplateManager()
				.canActivatePredictNum(human.getLevel())
				- human.getHumanPredictManager().getActivatedPredictNum());
		human.sendMessage(msg);
	}

}
