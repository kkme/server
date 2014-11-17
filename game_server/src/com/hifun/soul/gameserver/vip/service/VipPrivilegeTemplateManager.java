package com.hifun.soul.gameserver.vip.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.vip.template.DailyQuestBuyNumCostTemplate;
import com.hifun.soul.gameserver.vip.template.SpritePubCertainWinCostTemplate;
import com.hifun.soul.gameserver.vip.template.VipPrivilegeTemplate;

/**
 * VIP权限服务
 * 
 * @author magicstone
 *
 */
@Scope("singleton")
@Component
public class VipPrivilegeTemplateManager implements IInitializeRequired {

	private Map<Integer, VipPrivilegeTemplate> vipPrivilegeTemplates = new HashMap<Integer, VipPrivilegeTemplate>();
	private List<Integer> sortedKey = new ArrayList<Integer>();
	@Autowired
	private TemplateService templateService;
	int maxLevel = 0;
	@Override
	public void init() {
		Map<Integer, VipPrivilegeTemplate> vipPrivilegeTemp = templateService.getAll(VipPrivilegeTemplate.class);
		for(Integer id : vipPrivilegeTemp.keySet()){
			vipPrivilegeTemplates.put(vipPrivilegeTemp.get(id).getLevel(), vipPrivilegeTemp.get(id));
			sortedKey.add(vipPrivilegeTemp.get(id).getLevel());
		}
		Collections.sort(sortedKey);		
		for( Integer level : vipPrivilegeTemplates.keySet()){			
			if(maxLevel<level){
				maxLevel = level.intValue();
			}
		}	
	}
	
	/**
	 * 获取Vip特权模板
	 * 
	 * @param vipLevel
	 * @return
	 */
	public VipPrivilegeTemplate getVipTemplate(int vipLevel){
		if(!vipPrivilegeTemplates.containsKey(vipLevel)){
			return null;
		}
		return vipPrivilegeTemplates.get(vipLevel);
	}

	/**
	 * 获取VIP最高等级
	 * @return
	 */
	public int getMaxVipLevel(){			
		return maxLevel;
	}
	
	/**
	 * 根据已充值数获取对应的vip等级
	 * @param recharedNum
	 * @return
	 */
	public int getVipLevelByRechargedNum(int rechargedNum){
		int vipLevel = 0;
		for( Integer level : sortedKey){			
			if(vipPrivilegeTemplates.get(level).getRechargeNum()>rechargedNum){
				break;
			}
			vipLevel = level;
		}
		return vipLevel;
	}
	
	public int getMaxBuyEnergyTimes(int vipLevel){
		return vipPrivilegeTemplates.get(vipLevel).getMaxBuyEnergyTimes();
	}
	
	public int getMaxRefreshEliteStageTimes(int vipLevel){
		return vipPrivilegeTemplates.get(vipLevel).getMaxRefreshEliteStageTimes();
	}
	
	public int getDailyQuestBuyNumCost(int buyNum,int vipLevel) {
		Map<Integer, DailyQuestBuyNumCostTemplate> costTemplates = templateService.getAll(DailyQuestBuyNumCostTemplate.class);
		if (buyNum > costTemplates.size()) {
			return 0;
		}
		if (buyNum > getVipTemplate(vipLevel).getMaxBuyDailyQuestTimes()) {
			return 0;
		}
		return costTemplates.get(buyNum).getCrystal();
	}

	public int getSpritePubSucceedCost(int usedNum,int vipLevel) {
		Map<Integer, SpritePubCertainWinCostTemplate> costTemplates = templateService.getAll(SpritePubCertainWinCostTemplate.class);
		if (usedNum > costTemplates.size()) {
			return 0;
		}
		if (usedNum > getVipTemplate(vipLevel).getMaxSpritePubCertainWinTimes()) {
			return 0;
		}
		return costTemplates.get(usedNum).getCrystal();
	}
}
