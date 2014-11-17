package com.hifun.soul.gameserver.activity.question;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.hifun.soul.common.LogReasons.ExperienceLogReason;
import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.LogReasons.TechPointLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.core.util.MathUtils;
import com.hifun.soul.gamedb.cache.CacheEntry;
import com.hifun.soul.gamedb.cache.ICachableComponent;
import com.hifun.soul.gamedb.callback.IDBCallback;
import com.hifun.soul.gamedb.entity.HumanEntity;
import com.hifun.soul.gamedb.entity.HumanQuestionEntity;
import com.hifun.soul.gameserver.activity.ActivityType;
import com.hifun.soul.gameserver.activity.IHumanActivityManager;
import com.hifun.soul.gameserver.activity.question.model.AnswerInfo;
import com.hifun.soul.gameserver.activity.question.model.AnswerQuestionData;
import com.hifun.soul.gameserver.activity.question.model.ExchangeScore;
import com.hifun.soul.gameserver.activity.question.msg.GCBuyBlessCost;
import com.hifun.soul.gameserver.activity.question.msg.GCExchangeScore;
import com.hifun.soul.gameserver.activity.question.msg.GCUpdateQuestionPannel;
import com.hifun.soul.gameserver.activity.question.msg.GCUpdateScoreExchangePanel;
import com.hifun.soul.gameserver.activity.question.template.Answer;
import com.hifun.soul.gameserver.activity.question.template.QuestionAnswerTemplate;
import com.hifun.soul.gameserver.activity.question.template.QuestionConsumeTemplate;
import com.hifun.soul.gameserver.activity.question.template.QuestionScoreExchangeTemplate;
import com.hifun.soul.gameserver.activity.template.ActivityTemplate;
import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.guide.GuideType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.IHumanPersistenceManager;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.HumanLongProperty;
import com.hifun.soul.gameserver.role.properties.PropertyType;
import com.hifun.soul.gameserver.vip.template.VipPrivilegeTemplate;

/**
 * 问答管理器
 * 
 * @author magicstone
 * 
 */
public class AnswerQuestionManager implements IHumanActivityManager, IHumanPersistenceManager,ICachableComponent{
	private static Logger logger = Loggers.ACTIVITY_LOGGER;
	private AnswerQuestionData answerQuestionData = null;
	/** 问答模板 */
	private QuestionAnswerTemplate questionAnswerTemplate;
	/** 角色 */
	private Human human;
	/** 物品实体缓存 */
	protected CacheEntry<Integer, IEntity> questionEntityCaches = new CacheEntry<Integer, IEntity>();
	
	public AnswerQuestionManager(Human human) {
		this.human = human;
		this.human.registerPersistenceManager(this);
		this.human.registerCachableManager(this);
	}
	
	private void initQuestionData(){		
		answerQuestionData = new AnswerQuestionData(human.getHumanGuid());		
		answerQuestionData.setUsableBlessNum(0);
		int scoreListCount = GameServerAssist.getQuestionTemplateManager().getQuestionScoreExchangeCount();
		int[] scoreExchangeState = new int[scoreListCount];
		for(int i=0;i<scoreListCount;i++){
			scoreExchangeState[i]=0;
		}
		answerQuestionData.setScoreExchangeState(scoreExchangeState);
	}
	
	public int getTotalScore() {
		return answerQuestionData.getTotalScore();
	}

	public void addTotalScore(int addScore){
		if(addScore==0){
			return;
		}
		if(addScore<0){
			throw new IllegalArgumentException("The argument 'addScore' can not be smaller than zero! addScore:"+addScore);
		}
		this.answerQuestionData.setTotalScore(this.getTotalScore()+addScore);
		//发送提示消息
		String langScore = GameServerAssist.getSysLangService().read(LangConstants.ANSWER_QUESTION_SCORE);
		String operate = addScore>=0?"+":"";
		human.sendImportantMessage(LangConstants.COMMON_OBTAIN, addScore, langScore, operate);
	}

	public int getUsableBlessNum() {
		return answerQuestionData.getUsableBlessNum();
	}

	public void setUsableBlessNum(int usableBlessNum) {
		this.answerQuestionData.setUsableBlessNum(usableBlessNum);
	}	

	public int getBuyBlessTimes() {
		return answerQuestionData.getBuyBlessTimes();
	}

	public void setBuyBlessTimes(int buyBlessTimes) {
		this.answerQuestionData.setBuyBlessTimes(buyBlessTimes);
	}
	
	public int getQuestionIndex() {
		return answerQuestionData.getQuestionIndex();
	}
	
	public long getLastDailyResetTime() {
		return human.getHumanPropertiesManager().getLongPropertyValue(HumanLongProperty.LAST_DAILY_QUESTION_RESET_TIME);
	}
	
	public long getLastWeeklyResetTime() {
		return human.getHumanPropertiesManager().getLongPropertyValue(HumanLongProperty.LAST_WEEKLY_QUESTION_RESET_TIME);
	}
	
	public void setLastDailyResetTime(long lastDailyResetTime) {
		human.getHumanPropertiesManager().setLongPropertyValue(HumanLongProperty.LAST_DAILY_QUESTION_RESET_TIME, lastDailyResetTime);
	}
	
	public void setLastWeeklyResetTime(long lastWeeklyResetTime) {
		human.getHumanPropertiesManager().setLongPropertyValue(HumanLongProperty.LAST_WEEKLY_QUESTION_RESET_TIME, lastWeeklyResetTime);
	}
	
	public ExchangeScore[] getScoreExchangeList() {
		 ExchangeScore[] exchangeScore = GameServerAssist.getQuestionTemplateManager().getScoreExchangeList();
		 int[] state = answerQuestionData.getScoreExchangeState();
		 for(int i=0; i<exchangeScore.length;i++){
			 if(state.length-1<i){
				 exchangeScore[i].setExchangeState(ScoreExchangeState.NOT_EXCHANGE);
			 }
			 else{
				 exchangeScore[i].setExchangeState(state[i]);
			 }
		 }
		 return exchangeScore;
	}
	/**
	 * 重置个人每天的问答数据
	 */
	public void resetDailyData(int needResetTime){		
		questionAnswerTemplate = null;
		answerQuestionData.setQuestionId(0);
		answerQuestionData.setQuestionIndex(0);	
		answerQuestionData.setBuyBlessTimes(0);
		answerQuestionData.getAnsweredQuestionIds().clear();
		if (needResetTime > 0) {
			if (answerQuestionData.getUsableBlessNum() >= GameServerAssist.getGameConstants().getMaxHandselBlessNum() - needResetTime) {
				answerQuestionData.setUsableBlessNum(GameServerAssist.getGameConstants().getMaxHandselBlessNum());
			} else {
				answerQuestionData.setUsableBlessNum(answerQuestionData.getUsableBlessNum() + needResetTime);
			}
		}
		human.getHumanCdManager().removeCdFree(CdType.ANSWER_QUESTION);
		GenerateNewQuestion();		
		logger.debug("resetDailyQuestionData complete.[resetTime="+needResetTime+"]");
	}
	
	/**
	 * 重置个人每周的问答数据
	 */
	public void resetWeeklyData(){
		int scoreListCount = GameServerAssist.getQuestionTemplateManager().getQuestionScoreExchangeCount();
		int[] scoreExchangeState = new int[scoreListCount];
		for(int i=0;i<scoreListCount;i++){
			scoreExchangeState[i] = ScoreExchangeState.NOT_EXCHANGE;
		}
		answerQuestionData.setScoreExchangeState(scoreExchangeState);
		// 策划新需求：积分不重置
        // answerQuestionData.setTotalScore(0);
		this.onPersistence();
		logger.debug("resetWeeklyQuestionData complete.");
	}
	
	

	/**
	 * 获取每日问答的题数
	 * 
	 * @return
	 */
	public int getDailyQuestionCount() {
		return GameServerAssist.getGameConstants().getDailyQuestionCount();
	}

	/**
	 * 获取免费赠送的祈福次数最大累计值
	 * 
	 * @return
	 */
	public int getHandselBlessMaxCount() {
		return GameServerAssist.getGameConstants().getMaxHandselBlessNum();
	}

	/**
	 * 获取题目
	 * 
	 * @return
	 */
	public QuestionAnswerTemplate getQuestionAndAnswerTemplate() {
		if (questionAnswerTemplate == null) {
			GenerateNewQuestion();
		}
		return questionAnswerTemplate;
	}
	
	/**
	 * 生成题目
	 */
	public void GenerateNewQuestion() {
		int questionId = generateQuestionId(answerQuestionData.getAnsweredQuestionIds());
		questionAnswerTemplate = GameServerAssist.getQuestionTemplateManager().getQuestionAnswerTemplate(questionId);
		if(questionAnswerTemplate==null){
			logger.error("问答题目模板配置有误，可能由于配置表中的id不连续导致。");
		}
		this.answerQuestionData.setQuestionIndex(answerQuestionData.getQuestionIndex()+1);
		answerQuestionData.setQuestionId(questionId);
		answerQuestionData.getAnsweredQuestionIds().add(questionId);
		setRemainQuestionNum();
		this.onPersistence();
	}
	
	private int generateQuestionId(List<Integer> answeredQuestionIds){
		int questionId = MathUtils.random(1, GameServerAssist.getQuestionTemplateManager().getQuestionCount());
		if(answeredQuestionIds.contains(questionId)){
			return generateQuestionId(answeredQuestionIds);
		}
		return questionId;
	}
	
	/**
	 * 获取一键答题的所有题目模板
	 */
	public List<QuestionAnswerTemplate> getOnekeyAnswerQuestionTemplates() {
		List<QuestionAnswerTemplate> questionTemplateList = new ArrayList<QuestionAnswerTemplate>();
		int remainQuestionNum = getRemainQuestionNum();
		List<Integer> weightList = new ArrayList<Integer>();
		for (int i = 0; i < GameServerAssist.getQuestionTemplateManager()
				.getQuestionCount(); i++) {
			weightList.add(1);
		}
		int[] indexs = MathUtils.getRandomUniqueIndex(weightList,
				remainQuestionNum);
		for (int index : indexs) {
			questionTemplateList.add(GameServerAssist
					.getQuestionTemplateManager().getQuestionAnswerTemplate(
							index + 1));
		}
		return questionTemplateList;
	}
	
	/**
	 * 发送购买祈福确认信息
	 */
	public void sendBuyBlessNumConfirmInfo(int answerIndex){
		VipPrivilegeTemplate vipTemplate = GameServerAssist.getVipPrivilegeTemplateManager().getVipTemplate(human.getVipLevel());
		if (vipTemplate == null) {
			human.sendErrorMessage(LangConstants.SERVER_ERROR);
			logger.error("vip权限配置表中未找到level为" + human.getVipLevel() + "的模板。");
			return;
		}
		int maxBuyTimes = GameServerAssist.getVipPrivilegeTemplateManager().getVipTemplate(human.getVipLevel()).getMaxBuyAnswerBlessTimes();
		int buyTime = getBuyBlessTimes();
		if (buyTime >= maxBuyTimes) {// 购买次数不够时
			human.sendGenericMessage(LangConstants.NO_BUY_UNBLESS_TIME);
			return;
		}					
		QuestionConsumeTemplate qcTemplate = GameServerAssist.getQuestionTemplateManager().getQuestionConsumeTemplate(buyTime+1);
		GCBuyBlessCost gcBuyBlessMsg = new GCBuyBlessCost();
		gcBuyBlessMsg.setAnswerIndex(answerIndex);
		gcBuyBlessMsg.setCostNum(qcTemplate.getCostNum());
		gcBuyBlessMsg.setCurrencyType(CurrencyType.CRYSTAL.getIndex());
		human.sendMessage(gcBuyBlessMsg);
	}
	public boolean buyBlessNum(){
		VipPrivilegeTemplate vipTemplate = GameServerAssist.getVipPrivilegeTemplateManager().getVipTemplate(human.getVipLevel());
		if (vipTemplate == null) {
			human.sendErrorMessage(LangConstants.SERVER_ERROR);
			logger.error("vip权限配置表中未找到level为" + human.getVipLevel() + "的模板。");
			return false;
		}
		int maxBuyTimes = GameServerAssist.getVipPrivilegeTemplateManager().getVipTemplate(human.getVipLevel()).getMaxBuyAnswerBlessTimes();
		int buyTime = getBuyBlessTimes();
		if (buyTime > maxBuyTimes-1) {// 购买次数不够时
			human.sendGenericMessage(LangConstants.NO_BUY_UNBLESS_TIME);
			return false;
		}	
		
		QuestionConsumeTemplate qcTemplate = GameServerAssist.getQuestionTemplateManager().getQuestionConsumeTemplate(buyTime+1);
		if(!human.getWallet().isEnough(CurrencyType.CRYSTAL, qcTemplate.getCostNum())){
			human.sendWarningMessage(LangConstants.COMMON_NOT_ENOUGH,CurrencyType.CRYSTAL.getDesc());
			return false;
		}
		setBuyBlessTimes(++buyTime);
		human.getWallet().costMoney(CurrencyType.CRYSTAL, qcTemplate.getCostNum(), MoneyLogReason.ANSWER_QUESTION, "");	
		setUsableBlessNum(getUsableBlessNum()+1);		
		return true;
	}

	/**
	 * 兑换积分
	 * 
	 * @return
	 */
	public void ExchangeScore(int exchangeScoreid) {		
		ExchangeScore[] exchangeScore = GameServerAssist.getQuestionTemplateManager().getScoreExchangeList();
		int index = -1;
		for(int i=0;i<exchangeScore.length;i++){			
			if(exchangeScore[i].getId() == exchangeScoreid){
				index = i;
				break;
			}
		}
		if(index == -1){
			logger.error("积分兑换id不正确。");
			return;		
		}
		QuestionScoreExchangeTemplate template = GameServerAssist.getQuestionTemplateManager().getQuestionScoreExchangeTemplate(exchangeScoreid);
		if(answerQuestionData.getTotalScore()<template.getScore()){
			human.sendWarningMessage(LangConstants.SCORE_NOT_ENOUGH);	
			return ;
		}
		answerQuestionData.setTotalScore(answerQuestionData.getTotalScore() - template.getScore());
		answerQuestionData.getScoreExchangeState()[index]=ScoreExchangeState.EXCHANGED;
		human.getWallet().addMoney(CurrencyType.COIN, template.getCoinNum(),true,MoneyLogReason.ANSWER_QUESTION_POINT_EXCHANGE, "");
		human.addExperience(template.getExp(),true,ExperienceLogReason.EXCHANGE_SCORE_ADD_EXP, "");
		human.getHumanTechnologyManager().addTechnologyPoints(template.getTechnologyPoint(), true, TechPointLogReason.DAILY_QUESTION_ADD_TECH_POINT, "");

		GCExchangeScore gcMsg = new GCExchangeScore();
		gcMsg.setExchangeIndex(exchangeScoreid);
		gcMsg.setTotalScore(answerQuestionData.getTotalScore());
		gcMsg.setCoinNum(template.getCoinNum());
		gcMsg.setExp(template.getExp());
		gcMsg.setTechnologyPoint(template.getTechnologyPoint());
		gcMsg.setTotalScore(answerQuestionData.getTotalScore());
		gcMsg.setResultCode(1);
		human.sendMessage(gcMsg);
		this.onPersistence();
	}
	
	/**
	 * 发送问答题目与相关数据到客户端
	 */
	public void sendQuestionInfo(){
		QuestionAnswerTemplate template = getQuestionAndAnswerTemplate();
		if(template==null){
			return;
		}
		GCUpdateQuestionPannel gcMsg = new GCUpdateQuestionPannel();
		gcMsg.setQuestionIndex(getQuestionIndex());
		gcMsg.setQuestion(template.getQuestion());		
		gcMsg.setTotalQuestionNum(getDailyQuestionCount());
		int answerSize = template.getAnswers().size();
		AnswerInfo[] answers = new AnswerInfo[answerSize];
		for(int i=0;i<answerSize;i++){
			Answer answer = template.getAnswers().get(i);
			answers[i]= new AnswerInfo();
			answers[i].setAnswerIndex(i);
			answers[i].setContent(answer.getContent());
		}
		gcMsg.setAnswers(getRandomAnswers(answers));
		gcMsg.setMaxUsableBlessNum(getHandselBlessMaxCount());
		gcMsg.setUsableBlessNum(getUsableBlessNum());
		int canBuyBlessNum = human.getHumanVipManager().getCurrenctVipTemplate().getMaxBuyAnswerBlessTimes() - answerQuestionData.getBuyBlessTimes();
		gcMsg.setCanBuyBlessNum(canBuyBlessNum);
		gcMsg.setOnekeyAnswerCost(getOnekeyAnswerCost());
		gcMsg.setOnekeyAnswerOpen(human.getVipLevel() >= 
				GameServerAssist.getGameConstants().getOnekyAnswerVipLevel());
		human.sendMessage(gcMsg);
	}
	/**
	 * 获取一键答题消费魔晶 
	 */
	public int getOnekeyAnswerCost() {
		int singleCost = GameServerAssist.getGameConstants()
				.getOnekeyAnswerSingleCost();
		int remainQuestionNum = getDailyQuestionCount()
				- getQuestionIndex() + 1;
		int totalCost = singleCost * remainQuestionNum;
		return totalCost;
	}
	
	private AnswerInfo[] getRandomAnswers(AnswerInfo[] answers){
		if(answers == null){
			return null;
		}
		AnswerInfo[] result = new AnswerInfo[answers.length];
		Integer[] randomArray = new Integer[0];
		List<Integer> range = new ArrayList<Integer>();
		for(int i=0;i<answers.length;i++){
			range.add(i);
		}
		for(int i=0;i<answers.length;i++){
			randomArray = range.toArray(new Integer[0]);
			int oldIndex = MathUtils.randomFromArray(randomArray);
			result[i] =answers[oldIndex];
			range.remove((Object)oldIndex);			
		}
		return result;
	}
	
	/**
	 * 发送积分兑换数据到客户端
	 */
	public void sendScoreExchangeInfo(){
		String desc = "";
		ActivityTemplate template = GameServerAssist.getActivityTemplateManager().getActivityTemplate(this.getActivityType().getIndex());
		if(template!=null){
			desc = template.getDesc();
		}
		GCUpdateScoreExchangePanel gcUpdateScore = new GCUpdateScoreExchangePanel();
		gcUpdateScore.setDescription(desc);
		gcUpdateScore.setTotalScore(getTotalScore());
		gcUpdateScore.setExchangeScoreList(getScoreExchangeList());
		human.sendMessage(gcUpdateScore);
	}

	@Override
	public void onLoad(HumanEntity humanEntity) {		
		HumanQuestionEntity questionEntity = humanEntity.getQuestionEntity();
		if(questionEntity ==null){
			initQuestionData();
			questionEntity = answerQuestionData.toEntity();
			GameServerAssist.getDataService().insert(questionEntity, new IDBCallback<Integer>(){
				@Override
				public void onSucceed(Integer result) {
					if(result==null){
						return;
					}
					answerQuestionData.setId(result);
				}

				@Override
				public void onFailed(String errorMsg) {
					logger.error("添加角色问答活动信息失败，errorMsg:"+errorMsg);				
				}});
		}
		else{
			answerQuestionData = new AnswerQuestionData(questionEntity);
			questionAnswerTemplate = GameServerAssist.getQuestionTemplateManager().getQuestionAnswerTemplate(answerQuestionData.getQuestionId());
		}
	
	}
	
	public void onPersistence(){
		if(answerQuestionData==null || this.answerQuestionData.getId()==0){
			return;
		}
		HumanQuestionEntity questionEntity = this.answerQuestionData.toEntity();	
		questionEntityCaches.addUpdate(questionEntity.getId(), questionEntity);	
	}

	@Override
	public void onPersistence(HumanEntity humanEntity) {
		// 下线持久化需要设置数据;
		if(answerQuestionData==null || this.answerQuestionData.getId()==0){
			return;
		}
		HumanQuestionEntity questionEntity = this.answerQuestionData.toEntity();	
		humanEntity.setQuestionEntity(questionEntity);
	}

	@Override
	public ActivityType getActivityType() {		
		return ActivityType.ANSWER_QUESTION;
	}
	
	@Override
	public void onOpenClick() {		
		sendQuestionInfo();
		sendScoreExchangeInfo();		
		human.getHumanCdManager().snapCdQueueInfo(CdType.ANSWER_QUESTION);
		human.getHumanGuideManager().showGuide(GuideType.OPEN_QUESTION_PANEL.getIndex());
	}

	@Override
	public Human getOwner() {
		return human;
	}
	
	private void setRemainQuestionNum(){
		int remainNum = this.getDailyQuestionCount()-getQuestionIndex()+1;
		human.getPropertyManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY).setPropertyValue(HumanIntProperty.REMAIN_QUESTION_NUM, remainNum);
	}
	
	public int getRemainQuestionNum() {
		return getDailyQuestionCount() - getQuestionIndex() + 1;
	}

	@Override
	public List<IEntity> getUpdateEntities() {
		return questionEntityCaches.getAllUpdateData();		
	}

	@Override
	public List<IEntity> getDeleteEntities() {		
		return null;
	}
	
	/**
	 * 一键答题 
	 */
	public void onekeyAnswer() {
		answerQuestionData.setQuestionIndex(getDailyQuestionCount()+1);
		setRemainQuestionNum();
		this.onPersistence();
	}
}
