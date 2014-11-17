package com.hifun.soul.gameserver.activity.question;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.activity.question.model.ExchangeScore;
import com.hifun.soul.gameserver.activity.question.template.QuestionAnswerTemplate;
import com.hifun.soul.gameserver.activity.question.template.QuestionConsumeTemplate;
import com.hifun.soul.gameserver.activity.question.template.QuestionScoreExchangeTemplate;

@Scope("singleton")
@Component
public class QuestionTemplateManager implements IInitializeRequired{
	private Map<Integer,QuestionAnswerTemplate> questionTemplates = new HashMap<Integer,QuestionAnswerTemplate>();
	private Map<Integer,QuestionScoreExchangeTemplate> scoreExchangeTemplates = new HashMap<Integer,QuestionScoreExchangeTemplate>();
	private Map<Integer,QuestionConsumeTemplate> consumeTemplates = new HashMap<Integer,QuestionConsumeTemplate>();
	@Autowired
	private TemplateService templateService;
	private ExchangeScore[] scoreList;
	
	@Override
	public void init() {		
		questionTemplates = templateService.getAll(QuestionAnswerTemplate.class);
		scoreExchangeTemplates = templateService.getAll(QuestionScoreExchangeTemplate.class);
		consumeTemplates = templateService.getAll(QuestionConsumeTemplate.class);
		scoreList = new ExchangeScore[scoreExchangeTemplates.size()];		
		int index = 0;
		for(QuestionScoreExchangeTemplate template : scoreExchangeTemplates.values()){
			scoreList[index] = new ExchangeScore();
			scoreList[index].setScore(template.getScore());
			scoreList[index].setId(template.getId());
			scoreList[index].setCoin(template.getCoinNum());
			scoreList[index].setExp(template.getExp());
			scoreList[index].setTechPoint(template.getTechnologyPoint());
			index++;
		}
	}
	public QuestionAnswerTemplate getQuestionAnswerTemplate(int index){
		return questionTemplates.get(index);
	}
	public int getQuestionCount(){
		return questionTemplates.size();
	}
	
	public QuestionScoreExchangeTemplate getQuestionScoreExchangeTemplate(int index){
		return scoreExchangeTemplates.get(index);
	}
	
	public QuestionConsumeTemplate getQuestionConsumeTemplate(int buyTimes){
		return consumeTemplates.get(buyTimes);
	}
	
	public int getQuestionScoreExchangeCount(){
		return scoreExchangeTemplates.size();
	}
	
	/**
	 * 获取积分兑换的分数线列表
	 * 
	 * @return
	 */
	public ExchangeScore[] getScoreExchangeList() {
		return scoreList;
	}
	
}
