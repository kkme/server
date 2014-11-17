package com.hifun.soul.gameserver.gmquestion.handler;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.core.time.SystemTimeService;
import com.hifun.soul.core.uuid.UUIDService;
import com.hifun.soul.core.uuid.UUIDType;
import com.hifun.soul.gamedb.entity.QuestionEntity;
import com.hifun.soul.gamedb.service.IDataService;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.gmquestion.QuestionType;
import com.hifun.soul.gameserver.gmquestion.msg.CGSubmitQuestion;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.PropertyType;

@Component
public class CGSubmitQuestionHandler implements
		IMessageHandlerWithType<CGSubmitQuestion> {

	@Autowired
	private UUIDService uuidService;
	@Autowired
	private IDataService dataService;
	@Autowired
	private SystemTimeService systemTimeService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_SUBMIT_QUESTION;
	}

	@Override
	public void execute(CGSubmitQuestion message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		//检查今日提交次数
		int submitTime = human.getPropertyManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.getPropertyValue(HumanIntProperty.SUBMIT_GM_QUESTION_TIMES);
		if(submitTime>=GameServerAssist.getGameConstants().getSubmitGmQuestionNum()){
			human.sendErrorMessage(LangConstants.SUBMIT_GM_QUESTION_TIME_USE_OUT);
			return;
		}
		QuestionType questionType = QuestionType.indexOf(message.getQuestionType());
		if(questionType == null){
			human.sendErrorMessage(LangConstants.ERROR_QUESTION_TYPE);
			return;
		}
		
		String content = message.getContent();
		if(content == null
				|| "".equals(content.trim())){
			return;
		}
		submitTime++;
		human.getPropertyManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
			.setPropertyValue(HumanIntProperty.SUBMIT_GM_QUESTION_TIMES, submitTime);
		QuestionEntity entity = new QuestionEntity();
		entity.setAskerId(human.getHumanGuid());
		entity.setAskerName(human.getName());
		entity.setAskTime(new Date(systemTimeService.now()));
		entity.setQuestionId(uuidService.getNextUUID(UUIDType.GMQUESTION));
		entity.setQuestionType(questionType.getIndex());
		entity.setContent(content);
		dataService.insert(entity);
		
	}

}
