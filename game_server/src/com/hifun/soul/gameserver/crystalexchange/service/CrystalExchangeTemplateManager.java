package com.hifun.soul.gameserver.crystalexchange.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.crystalexchange.template.CrystalExchangeConsumeTemplate;
import com.hifun.soul.gameserver.crystalexchange.template.CrystalExchangeRewardTemplate;
import com.hifun.soul.gameserver.vip.service.VipPrivilegeTemplateManager;
import com.hifun.soul.gameserver.vip.template.VipPrivilegeTemplate;

@Scope("singleton")
@Component
public class CrystalExchangeTemplateManager implements IInitializeRequired {
	
	@Autowired
	private TemplateService templateService;
	@Autowired
	private VipPrivilegeTemplateManager vipTemplateManager;
	
	/** 当找不到消耗模版时默认最大消耗模版*/
	private CrystalExchangeConsumeTemplate costTemplate;
	
	@Override
	public void init() {
		for(CrystalExchangeConsumeTemplate template : templateService.getAll(CrystalExchangeConsumeTemplate.class).values()){
			if(costTemplate == null){
				costTemplate = template;
			}
			else{
				if(template.getCurrencyNum() > costTemplate.getCurrencyNum()){
					costTemplate = template;
				}
			}
		}
	}
	
	/**
	 * 返回最终消耗模版
	 * 
	 * @return
	 */
	public CrystalExchangeConsumeTemplate getMaxCostTemplate() {
		return costTemplate;
	}
	
	/**
	 * vip等级对应的魔晶兑换次数
	 * 
	 * @param vipLevel
	 * @return
	 */
	public int getTotalCrystalExchangeTime(int vipLevel) {
		VipPrivilegeTemplate template = vipTemplateManager.getVipTemplate(vipLevel);
		if(template == null){
			return 0;
		}
		
		return template.getMaxExchangeTime();
	}
	
	/**
	 * 魔晶兑换的消耗
	 * 
	 * @param time
	 * @param vipLevel
	 * @return
	 */
	public int getCrystalExchangeConsume(int time, int vipLevel) {
		CrystalExchangeConsumeTemplate consumeTemplate = getCrystalExchangeConsumeTemplate(time);
		if(consumeTemplate == null){
			return costTemplate.getCurrencyNum();
		}
		
		return consumeTemplate.getCurrencyNum();
	}
	
	/**
	 * 魔晶兑换的奖励
	 * 
	 * @param vipLevel
	 * @param humanLevel
	 * @return
	 */
	public int getCrystalExchangeReward(int vipLevel, int humanLevel) {
		CrystalExchangeRewardTemplate rewardTemplate = getCrystalExchangeRewardTemplate(humanLevel);
		if(rewardTemplate == null){
			return 0;
		}
		
		return rewardTemplate.getCurrencyNum();
	}
	
	public CrystalExchangeConsumeTemplate getCrystalExchangeConsumeTemplate(int time) {
		return templateService.get(time, CrystalExchangeConsumeTemplate.class);
	}
	
	public CrystalExchangeRewardTemplate getCrystalExchangeRewardTemplate(int level) {
		return templateService.get(level, CrystalExchangeRewardTemplate.class);
	}
	
}
