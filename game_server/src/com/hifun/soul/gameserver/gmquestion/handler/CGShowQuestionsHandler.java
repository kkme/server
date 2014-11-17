package com.hifun.soul.gameserver.gmquestion.handler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gamedb.agent.query.DataQueryConstants;
import com.hifun.soul.gamedb.callback.IDBCallback;
import com.hifun.soul.gamedb.entity.QuestionEntity;
import com.hifun.soul.gamedb.service.IDataService;
import com.hifun.soul.gameserver.gmquestion.GmQuestionInfo;
import com.hifun.soul.gameserver.gmquestion.msg.CGShowQuestions;
import com.hifun.soul.gameserver.gmquestion.msg.GCShowQuestions;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGShowQuestionsHandler implements
		IMessageHandlerWithType<CGShowQuestions> {
	private Logger logger = Loggers.GM_QUESTION_LOGGER;
	
	@Autowired
	private IDataService dataService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_QUESTIONS;
	}

	@Override
	public void execute(CGShowQuestions message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		
		final Human human = player.getHuman();
		if(human == null){
			return;
		}
		
		// 查询当前玩家所有的提问信息返回
		dataService.query(DataQueryConstants.QUERY_GM_QUESTIONS_BY_ID, 
				new String[]{"askerId"}, new Object[]{human.getHumanGuid()}, 
				new IDBCallback<List<?>>() {
					
					@Override
					public void onSucceed(List<?> result) {
						List<GmQuestionInfo> gmQuestionInfos = new ArrayList<GmQuestionInfo>();
						if (result == null) {
							return;
						}
						@SuppressWarnings("unchecked")
						List<QuestionEntity> entitys = (List<QuestionEntity>)result;
						for(QuestionEntity entity : entitys){
							GmQuestionInfo gmQuestionInfo = new GmQuestionInfo();
							gmQuestionInfo.setQuestionId(entity.getQuestionId());
							gmQuestionInfo.setName(entity.getAskerName());
							gmQuestionInfo.setContent(entity.getContent());
							
							gmQuestionInfos.add(gmQuestionInfo);
						}
						// 发送问答内容
						GCShowQuestions gcMsg = new GCShowQuestions();
						gcMsg.setQuestions(gmQuestionInfos.toArray(new GmQuestionInfo[0]));
						human.sendMessage(gcMsg);
					}
					
					@Override
					public void onFailed(String errorMsg) {
						logger.error(errorMsg);
					}
				});
		
	}

}
