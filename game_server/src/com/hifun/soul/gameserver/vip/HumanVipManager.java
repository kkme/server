package com.hifun.soul.gameserver.vip;

import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.event.RechargeEvent;
import com.hifun.soul.gameserver.event.VipLevelChangeEvent;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.recharge.Recharge;
import com.hifun.soul.gameserver.recharge.RechargeService;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.IntPropertyCacheSet;
import com.hifun.soul.gameserver.role.properties.PropertyType;
import com.hifun.soul.gameserver.role.properties.manager.HumanPropertyManager;
import com.hifun.soul.gameserver.vip.msg.GCVipShowPannel;
import com.hifun.soul.gameserver.vip.template.VipPrivilegeTemplate;

public class HumanVipManager {
	private Human human;
	
	public HumanVipManager(Human human){
		this.human = human;
	}
	
	/**
	 * 获取当前等级对应的vip模板
	 * @return
	 */
	public VipPrivilegeTemplate getCurrenctVipTemplate(){
		return GameServerAssist.getVipPrivilegeTemplateManager().getVipTemplate(human.getVipLevel());
	}
	
	/**
	 * 充值
	 * @param firstCurrencyNum 一级货币数量
	 * @param rate 兑换比率
	 * @param isGm 是否有gm充值
	 */
	public void recharge(int firstCurrencyNum, int rate, RechargeType rechargeType){
		int rechargeNum = firstCurrencyNum * rate;
		int rewardNum = GameServerAssist.getRechargeTemplateManager().getRechargeReward(rechargeNum);
		int totalNum = rechargeNum+rewardNum;
		addRechargeNum(rechargeNum,rewardNum,rechargeType);
		// 记录充值记录
		RechargeService.recharge(human, firstCurrencyNum, rewardNum, totalNum, rechargeType);
		sendUpdateVipPanelMessage();
		// 发送充值事件
		RechargeEvent event = new RechargeEvent(); 
		Recharge recharge = new Recharge();
		recharge.setRechargeNum(rechargeNum);
		recharge.setRechargeType(rechargeType);
		event.setRecharge(recharge);
		human.getEventBus().fireEvent(event);
	}
	
	public void addRechargeNum(int rechargeNum,int rewardNum, RechargeType rechargeType){
		//更新VIP等级		
		MoneyLogReason rechargeLogReason = MoneyLogReason.RECHARGE;
		MoneyLogReason rewardLogReason = MoneyLogReason.RECHARGE_REWARD;
		if(rechargeType == RechargeType.NORMAL_RECHARGE){
			rechargeLogReason = MoneyLogReason.RECHARGE;
			rewardLogReason = MoneyLogReason.RECHARGE_REWARD;
		}else if(rechargeType == RechargeType.GM_RECHARGE){
			rechargeLogReason = MoneyLogReason.GM_COMMAND;
			rewardLogReason = MoneyLogReason.GM_RECHARGE_REWARD;
		}
		human.getWallet().addMoney(CurrencyType.CRYSTAL, rechargeNum, true,rechargeLogReason, "");
		human.getWallet().addMoney(CurrencyType.CRYSTAL, rewardNum, true,rewardLogReason, "");
		int rechargedTotalNum = human.getHumanPropertiesManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
		.getPropertyValue(HumanIntProperty.RECHARGED_NUM)+rechargeNum;
		human.getHumanPropertiesManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
		.setPropertyValue(HumanIntProperty.RECHARGED_NUM, rechargedTotalNum);		
		int vipLevel = GameServerAssist.getVipPrivilegeTemplateManager().getVipLevelByRechargedNum(rechargedTotalNum);
		if(vipLevel == getVipLevelReal()){
			return;
		}		
		setVipLevelReal(vipLevel);
	}
	
	/**
	 * 获取vip等级（真实vip等级与使用体验卡所获得的vip等级两者中的较大者）
	 * @return
	 */
	public int getVipLevel() {
		HumanPropertyManager propertyManager = human.getPropertyManager();
		 int vip = propertyManager.getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
				HumanIntProperty.VIPLEVEL);
		 int vipTemp = propertyManager.getIntPropertySet(
					PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
							HumanIntProperty.VIP_LEVEL_TEMPORARY);
		 return vip>vipTemp ? vip : vipTemp;
	}
	
	/**
	 * 获取玩家真实的vip等级
	 * @return
	 */
	public int getVipLevelReal(){
		HumanPropertyManager propertyManager = human.getPropertyManager();
		return propertyManager.getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
				HumanIntProperty.VIPLEVEL);
	}
	/**
	 * 设置玩家真实的vip等级
	 * @return
	 */
	public void setVipLevelReal(int vipLevel){
		if(vipLevel<0){
			vipLevel = 0;
		}
		HumanPropertyManager propertyManager = human.getPropertyManager();
		boolean vipChange = false;
		int realLevel = getVipLevelReal();
		int tempLevel = getVipLevelTemporary();
		int oldLevel = realLevel > tempLevel ? realLevel : tempLevel;
		if (realLevel > tempLevel && vipLevel != realLevel) {
			vipChange = true;
		}
		else if(realLevel <= tempLevel && vipLevel > tempLevel){
			vipChange = true;
		}		
		propertyManager.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY).setPropertyValue(HumanIntProperty.VIPLEVEL,
				vipLevel);
		if (vipChange) {
			this.onVipLevelChange(oldLevel);
		}
		// 更新角色VIP排行榜
		human.getHumanVipRankManager().updateVipRankData();
	}
	/**
	 * 获取玩家体验临时vip等级
	 * @return
	 */
	public int getVipLevelTemporary(){
		HumanPropertyManager propertyManager = human.getPropertyManager();
		return propertyManager.getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
				HumanIntProperty.VIP_LEVEL_TEMPORARY);
	}
	
	/**
	 * 设置玩家体验临时vip等级
	 * @return
	 */
	public void setVipLevelTemporary(int vipLevel){
		if(vipLevel<0){
			vipLevel = 0;
		}
		HumanPropertyManager propertyManager = human.getPropertyManager();
		boolean vipChange = false;
		int realLevel = getVipLevelReal();
		int tempLevel = getVipLevelTemporary();	
		int oldLevel = realLevel > tempLevel ? realLevel : tempLevel;		
		if (realLevel >= tempLevel && vipLevel > realLevel) {
			vipChange = true;
		}
		else if(realLevel < tempLevel && vipLevel != tempLevel){
			vipChange = true;
		}
		propertyManager.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY).setPropertyValue(
				HumanIntProperty.VIP_LEVEL_TEMPORARY, vipLevel);
		if (vipChange) {
			this.onVipLevelChange(oldLevel);
		}
	}
	
	/**
	 * 刷新角色vip等级提升相关的属性变化
	 * 
	 * @param oldLevel
	 * 
	 */
	private void onVipLevelChange(int oldLevel){
		int currentLevel = human.getVipLevel();
		IntPropertyCacheSet propertySet = human.getPropertyManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY);
		int remainTimes = GameServerAssist.getVipPrivilegeTemplateManager().getVipTemplate(currentLevel).getMaxBuyEnergyTimes() -
				propertySet.getPropertyValue(HumanIntProperty.BUY_ENERGY_TIMES);
		if(remainTimes<0){
			remainTimes = 0;
		}
		propertySet.setPropertyValue(HumanIntProperty.BUY_ENERGY_REMAIN_TIMES,remainTimes);
		// 发送vip等级变更时间
		VipLevelChangeEvent vipLevelChangeEvent = new VipLevelChangeEvent();
		vipLevelChangeEvent.setVipLevel(currentLevel);
		vipLevelChangeEvent.setChangeLevel(currentLevel-oldLevel);
		human.getEventBus().fireEvent(vipLevelChangeEvent);
	}
	
	/**
	 * 发送更新vip面板消息
	 */
	public void sendUpdateVipPanelMessage(){
		int vipLevel = human.getVipLevel();
		VipPrivilegeTemplate currentLevelTemplate = GameServerAssist.getVipPrivilegeTemplateManager().getVipTemplate(vipLevel);
		VipPrivilegeTemplate nextLevelTemplate = GameServerAssist.getVipPrivilegeTemplateManager().getVipTemplate(vipLevel+1);
		int nextLevelRecharedNum = human.getHumanPropertiesManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.getPropertyValue(HumanIntProperty.RECHARGED_NUM);		
		int nextLevelNeedNum=0;
		if(nextLevelTemplate != null){			
			nextLevelNeedNum=nextLevelTemplate.getRechargeNum();					
		}
		else{
			nextLevelTemplate = currentLevelTemplate;
			nextLevelNeedNum=currentLevelTemplate.getRechargeNum();
		}
		GCVipShowPannel gcMsg = new GCVipShowPannel();		
		gcMsg.setCurrentLevel(vipLevel);
		gcMsg.setNextLevelDesc(nextLevelTemplate.getDesc());
		gcMsg.setMaxLevel(GameServerAssist.getVipPrivilegeTemplateManager().getMaxVipLevel());
		gcMsg.setRechargedNum(nextLevelRecharedNum);
		gcMsg.setNextLevelRechargeNum(nextLevelNeedNum);
		human.sendMessage(gcMsg);
	}
}
