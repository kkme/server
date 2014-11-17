package com.hifun.soul.gameserver.training.manager;

import java.util.Map;

import org.slf4j.Logger;

import com.hifun.soul.common.LogReasons.ExperienceLogReason;
import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.LogReasons.TrainCoinLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.cd.manager.HumanCdManager;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.event.TrainingEvent;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.ILoginManager;
import com.hifun.soul.gameserver.legion.enums.LegionTechnologyType;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.HumanLongProperty;
import com.hifun.soul.gameserver.role.properties.PropertyType;
import com.hifun.soul.gameserver.training.TrainingState;
import com.hifun.soul.gameserver.training.TrainingType;
import com.hifun.soul.gameserver.training.model.NormalTrainingInfo;
import com.hifun.soul.gameserver.training.model.VipTrainingInfo;
import com.hifun.soul.gameserver.training.msg.GCCollectTrainingExp;
import com.hifun.soul.gameserver.training.msg.GCTrainingBeginUpdate;
import com.hifun.soul.gameserver.training.msg.GCUpdateTrainingState;
import com.hifun.soul.gameserver.training.template.TrainingTemplate;
import com.hifun.soul.gameserver.training.template.TrainingTypeTemplate;
import com.hifun.soul.gameserver.training.template.VipTrainingTypeTemplate;

/**
 * 训练场
 * 
 * @author magicstone
 * 
 */
public class HumanTrainingManager implements ILoginManager {
	private static Logger logger = Loggers.TRAINING_LOGGER;
	private Human human;

	public HumanTrainingManager(Human human) {
		this.human = human;
		this.human.registerLoginManager(this);
	}

	/**
	 * 获取普通训练剩余分钟数
	 * 
	 * @return
	 */
	public int getNormalTrainingTimeRemain() {
		return human.getHumanPropertiesManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.getPropertyValue(HumanIntProperty.NORMAL_TRAINING_TIME);
	}
	
	/**
	 * 设置普通训练剩余分钟数
	 * 
	 * @return
	 */
	public void setNormalTrainingTimeRemain(int remainTime) {
		human.getHumanPropertiesManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.setPropertyValue(HumanIntProperty.NORMAL_TRAINING_TIME,remainTime);
	}

	/**
	 * 获取vip训练剩余可消耗魔晶数
	 * 
	 * @return
	 */
	public int getVipTrainingConsumableCrystalNum() {
		return human.getHumanPropertiesManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.getPropertyValue(HumanIntProperty.VIP_TRAINING_CRYSTAL_CONSUME_NUM);
	}
	
	/**
	 * 设置vip训练剩余可消耗魔晶数
	 * 
	 * @return
	 */
	public void setVipTrainingConsumableCrystalNum(int consumableNum) {
		human.getHumanPropertiesManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.setPropertyValue(HumanIntProperty.VIP_TRAINING_CRYSTAL_CONSUME_NUM,consumableNum);
	}
	
	/**
	 * 获取上次重置时间
	 * @return
	 */
	public long getLastResetTime() {
		return human.getHumanPropertiesManager()
				.getLongPropertyValue(HumanLongProperty.LAST_TRAINING_RESET_TIME);
	}
	
	/**
	 * 设置上次重置时间
	 * 
	 * @param lastResetTime
	 */
	public void setLastResetTime(long lastResetTime) {
		human.getHumanPropertiesManager()
			.setLongPropertyValue(HumanLongProperty.LAST_TRAINING_RESET_TIME, lastResetTime);
	}
	
	/**
	 * 重置训练相关数据
	 */
	public void resetTrainingData() {
		setNormalTrainingTimeRemain(GameServerAssist.getGameConstants().getNormalTrainingMaxTime());
		setVipTrainingConsumableCrystalNum(GameServerAssist.getGameConstants().getVipTraingMaxCrystalConsume());	
	}

	/**
	 * 获取当前正在训练的类型
	 * 
	 * @return
	 */
	public int getCurrentTrainingType() {
		return human.getHumanPropertiesManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.getPropertyValue(HumanIntProperty.CURRENT_TRAINING_TYPE);
	}

	/**
	 * 设置当前正在训练的类型
	 * 
	 * @return
	 */
	private void setCurrentTrainingType(int normalTrainingId) {
		human.getHumanPropertiesManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.setPropertyValue(HumanIntProperty.CURRENT_TRAINING_TYPE, normalTrainingId);
	}

	/**
	 * 获取所有普通训练信息列表
	 * 
	 * @return
	 */
	public NormalTrainingInfo[] getNormalTrainings() {
		Map<Integer, TrainingTypeTemplate> normalTrainings = GameServerAssist.getTrainingTemplateManager().getNormalTrainingTypes();
		TrainingTemplate trainingTemplate = GameServerAssist.getTrainingTemplateManager().getTrainingTemplate(human.getLevel());
		NormalTrainingInfo[] trainings = new NormalTrainingInfo[normalTrainings.size()];
		int currentTrainingType = getCurrentTrainingType();
		int index = 0;
		TrainingTypeTemplate typeTemplate = null;
		for (Integer id : normalTrainings.keySet()) {
			typeTemplate = normalTrainings.get(id);
			float expRate = typeTemplate.getExpRate()/SharedConstants.DEFAULT_FLOAT_BASE;
			trainings[index] = new NormalTrainingInfo();
			trainings[index].setTrainingId(id);
			trainings[index].setTrainingName(typeTemplate.getTrainingTypeName());
			trainings[index].setCostTime(typeTemplate.getTrainingTime());
			trainings[index].setTrainingType(TrainingType.NORMAL_TRAINING.getIndex());
			trainings[index].setExp((int)(typeTemplate.getTrainingTime() * trainingTemplate.getNormalTrainingExpUnit() * expRate));
			trainings[index].setCostCoinNum(getNormalTrainingCostNum(id,trainingTemplate));
			if( currentTrainingType == trainings[index].getTrainingId()){
				HumanCdManager cdManager = human.getHumanCdManager();
				if(cdManager.canAddCd(CdType.TRAINING, 5000)){
					//可收获训练经验
					trainings[index].setTrainingState(TrainingState.COMPLETE);
				}
				else{
					//正在训练中
					trainings[index].setTrainingState(TrainingState.IN_PROGRESS);
				}
			}
			else{
				//待训练
				trainings[index].setTrainingState(TrainingState.NOT_BEGIN);
			}
			
			index++;
		}
		return trainings;
	}

	/**
	 * 获取所有VIP训练信息列表
	 * 
	 * @return
	 */
	public VipTrainingInfo[] getVipTrainings() {
		Map<Integer, VipTrainingTypeTemplate> vipTrainings = GameServerAssist.getTrainingTemplateManager().getVipTrainingTypes();
		TrainingTemplate trainingTemplate = GameServerAssist.getTrainingTemplateManager().getTrainingTemplate(human.getLevel());
		VipTrainingInfo[] trainings = new VipTrainingInfo[vipTrainings.size()];
		int index = 0;
		VipTrainingTypeTemplate typeTemplate = null;
		float revenueRate = human.getHumanAntiIndulgeManager().getRevenueRate();
		for (Integer id : vipTrainings.keySet()) {
			typeTemplate = vipTrainings.get(id);
			trainings[index] = new VipTrainingInfo();
			trainings[index].setTrainingId(id);
			trainings[index].setTrainingName(typeTemplate.getVipTrainingTypeName());
			trainings[index].setTrainingType(TrainingType.VIP_TRAINING.getIndex());
			trainings[index].setCostCurrencyType(typeTemplate.getCostCurrencyType());
			trainings[index].setCostCurrencyNum(typeTemplate.getCostCurrencyNum());
			trainings[index].setExp((int)(revenueRate*getVipTrainingExp(id,trainingTemplate)));
			trainings[index].setOpenVipLevel(typeTemplate.getVipLevel());
			index++;
		}		
		return trainings;
	}
	/**
	 * 获取普通训练花费培养币数量
	 * 
	 * @param trainingId
	 * @param trainingTemplate
	 * @return
	 */
	private int getNormalTrainingCostNum(int trainingId,TrainingTemplate trainingTemplate){
		int result = 0;
		switch(trainingId){
		case 1:
			result = trainingTemplate.getNormalTrainingCost1();
			break;
		case 2:
			result = trainingTemplate.getNormalTrainingCost2();
			break;
		case 3:
			result = trainingTemplate.getNormalTrainingCost3();
			break;
		case 4:
			result = trainingTemplate.getNormalTrainingCost4();
			break;
		}
		return result;
	}
	
	/**
	 * 获取VIP训练所能获得的经验值
	 * 
	 * @param trainingId
	 * @param trainingTemplate
	 * @return
	 */
	private int getVipTrainingExp(int trainingId,TrainingTemplate trainingTemplate){
		int result = 0;
		switch(trainingId){
		case 1:
			result = trainingTemplate.getVipTrainingExp1();
			break;
		case 2:
			result = trainingTemplate.getVipTrainingExp2();
			break;
		case 3:
			result = trainingTemplate.getVipTrainingExp3();
			break;
		case 4:
			result = trainingTemplate.getVipTrainingExp4();
			break;
		}
		return result;
	}

	/**
	 * 开始训练
	 * 
	 * @param trainingType
	 * @param trainingId
	 */
	public void trainingBegin(int trainingType, int trainingId) {
		if(human.maxLevelReached()){
			human.sendGenericMessage(LangConstants.ROLE_REACHED_MAX_LEVEL);
			return;
		}
		TrainingTemplate trainingTemplate = GameServerAssist.getTrainingTemplateManager().getTrainingTemplate(human.getLevel());
		if (trainingType == TrainingType.NORMAL_TRAINING.getIndex()) {
			if(this.getNormalTrainingTimeRemain()<=0){
				human.sendGenericMessage(LangConstants.NO_NORMAL_TRAINING_TIME);
				return;
			}
			int currentTrainingType = getCurrentTrainingType();
			if (currentTrainingType != 0) {
				human.sendErrorMessage(LangConstants.BE_TRAINING);
				logger.error("目前正在训练中，不能开始新的训练。humanId:"+human.getHumanGuid());
				return;
			}
			TrainingTypeTemplate typeTemplate = GameServerAssist.getTrainingTemplateManager().getNormalTrainingTypes().get(trainingId);
			if (typeTemplate == null) {
				logger.error("普通训练模板未找到。traingId:"+trainingId+"humanId:"+human.getHumanGuid());
				return;
			}
			if(this.getNormalTrainingTimeRemain()<=0 || this.getNormalTrainingTimeRemain()-typeTemplate.getTrainingTime()<0){
				human.sendGenericMessage(LangConstants.NO_NORMAL_TRAINING_TIME);
				return;
			}
			// 培养币是否足够
			int costCoinNum = this.getNormalTrainingCostNum(trainingId, trainingTemplate);
			if(human.getTrainCoin() < costCoinNum){
				String trainCoinDesc = GameServerAssist.getSysLangService().read(LangConstants.TRAIN_COIN);
				human.sendWarningMessage(LangConstants.COMMON_NOT_ENOUGH, trainCoinDesc);
				return;
			}
			HumanCdManager cdManager = human.getHumanCdManager();
			if (!cdManager.canAddCd(CdType.TRAINING, typeTemplate.getTrainingTime()*TimeUtils.MIN)) {	
				logger.error("训练Cd未冷却，不能开始新的训练。humanId:"+human.getHumanGuid());
				return;
			}
			// 消耗培养币
			if(!human.costTrainCoin(costCoinNum, TrainCoinLogReason.TRAINING, "")){
				logger.error("普通训练消费培养币失败。humanId:"+human.getHumanGuid());
				return;
			}
			setCurrentTrainingType(trainingId);
			setNormalTrainingTimeRemain(getNormalTrainingTimeRemain()-typeTemplate.getTrainingTime());
			cdManager.addCd(CdType.TRAINING, typeTemplate.getTrainingTime()*TimeUtils.MIN);	
			cdManager.snapCdQueueInfo(CdType.TRAINING);
			
		} else if (trainingType == TrainingType.VIP_TRAINING.getIndex()) {
			//直接获得经验			
			VipTrainingTypeTemplate typeTemplate = GameServerAssist.getTrainingTemplateManager().getVipTrainingTypes().get(trainingId);
			if (typeTemplate == null) {
				logger.error("VIP训练模板未找到。traingId:"+trainingId+"humanId:"+human.getHumanGuid());				
				return;
			}
			int costNum = typeTemplate.getCostCurrencyNum();
			if(this.getVipTrainingConsumableCrystalNum()<=0 || this.getVipTrainingConsumableCrystalNum()-costNum<0){
				human.sendGenericMessage(LangConstants.NO_VIP_TRAINING_CRYSTAL);
				return;
			}			
			CurrencyType costCurrencyType = CurrencyType.indexOf(typeTemplate.getCostCurrencyType());
			if(!human.getWallet().isEnough(costCurrencyType,costNum)){
				human.sendWarningMessage(LangConstants.COMMON_NOT_ENOUGH, costCurrencyType.getDesc());
				return;
			}
			if(!human.getWallet().costMoney(CurrencyType.indexOf(typeTemplate.getCostCurrencyType()),costNum, MoneyLogReason.TRAIN, "")){
				logger.error("VIP训练消费魔晶失败。humanId:"+human.getHumanGuid());
				return;
			}
			this.setVipTrainingConsumableCrystalNum(this.getVipTrainingConsumableCrystalNum()-costNum);
			int exp = this.getVipTrainingExp(trainingId, trainingTemplate);
			exp = (int)(exp*human.getHumanAntiIndulgeManager().getRevenueRate());
			human.addExperience(exp,true,ExperienceLogReason.TRAINING_ADD_EXP, "");
			GCCollectTrainingExp gcCollectExpMsg = new GCCollectTrainingExp();
			gcCollectExpMsg.setExpNum(exp);
			gcCollectExpMsg.setTrainingType(trainingType);
			human.sendMessage(gcCollectExpMsg);			
		}	
		GCTrainingBeginUpdate gcMsg = new GCTrainingBeginUpdate();
		gcMsg.setTrainingType(trainingType);
		gcMsg.setTrainingDetailType(trainingId);
		gcMsg.setNormalTrainingTimeRemain(getNormalTrainingTimeRemain());
		gcMsg.setVipTrainingConsumableCrystalNum(getVipTrainingConsumableCrystalNum());
		human.sendMessage(gcMsg);
		// 发送训练事件
		TrainingEvent trainingEvent = new TrainingEvent();
		human.getEventBus().fireEvent(trainingEvent);
	}
	
	/**
	 * 收获经验
	 * 
	 * @param trainingType
	 * @param trainingId
	 */
	public void CollectTrainingExp(int trainingType,int trainingId){
		TrainingTemplate trainingTemplate = GameServerAssist.getTrainingTemplateManager().getTrainingTemplate(human.getLevel());
		if (trainingType == TrainingType.NORMAL_TRAINING.getIndex()) {
			if(this.getCurrentTrainingType() != trainingId){
				logger.error("收获类型与正在训练的类型不一致。");
				return;
			}
			TrainingTypeTemplate typeTemplate = GameServerAssist.getTrainingTemplateManager().getNormalTrainingTypes().get(trainingId);
			if (typeTemplate == null) {
				logger.error("普通训练模板未找到。traingId:"+trainingId+"humanId:"+human.getHumanGuid());
				return;
			}
			HumanCdManager cdManager = human.getHumanCdManager();
			if (!cdManager.canAddCd(CdType.TRAINING, typeTemplate.getTrainingTime())) {				
				logger.error("训练Cd未冷却，不能开始新的训练。humanId:"+human.getHumanGuid());
				return;
			}			
			float expFloat = trainingTemplate.getNormalTrainingExpUnit()*typeTemplate.getTrainingTime();
			float expRate = typeTemplate.getExpRate()/SharedConstants.DEFAULT_FLOAT_BASE;
			int exp = (int)(expFloat * expRate * human.getHumanAntiIndulgeManager().getRevenueRate());
			// 军团科技加成训练场经验收益
			exp = human.getHumanLegionManager().getLegionTechnologyAmend(
					LegionTechnologyType.TRAINING, exp);
			human.addExperience(exp,true,ExperienceLogReason.TRAINING_ADD_EXP, "");
			this.setCurrentTrainingType(0);//重置正在训练的类型
			GCCollectTrainingExp gcCollectExpMsg = new GCCollectTrainingExp();
			gcCollectExpMsg.setExpNum(exp);
			gcCollectExpMsg.setTrainingType(trainingType);
			human.sendMessage(gcCollectExpMsg);			
		}
	}

	@Override
	public void onLogin() {
		GCUpdateTrainingState gcMsg = new GCUpdateTrainingState();
		if (this.getCurrentTrainingType() != 0) {
			HumanCdManager cdManager = human.getHumanCdManager();
			if (cdManager.canAddCd(CdType.TRAINING, 5000)) {
				gcMsg.setTrainingState(TrainingState.COMPLETE);
			} else {
				gcMsg.setTrainingState(TrainingState.IN_PROGRESS);
			}
		} else {
			gcMsg.setTrainingState(TrainingState.NOT_BEGIN);
		}
		human.sendMessage(gcMsg);
	}

	public Human getHuman() {
		return human;
	}
}
