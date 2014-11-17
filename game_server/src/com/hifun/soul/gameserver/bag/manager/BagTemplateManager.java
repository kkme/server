package com.hifun.soul.gameserver.bag.manager;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.bag.template.MainBagUpgradeTemplate;

@Scope("singleton")
@Component
public class BagTemplateManager implements IInitializeRequired {
	private Map<Integer,MainBagUpgradeTemplate> mainBagUpgradeTemplates;
	@Autowired
	private TemplateService templateService;
	@Override
	public void init() {
		mainBagUpgradeTemplates	= templateService.getAll(MainBagUpgradeTemplate.class);
	}
	
	/**
	 * 查询升级需要花费的钱
	 * @param currentLevel
	 * @param upgradeLevel
	 * @return
	 */
	public int getMainBagUpgradePrice(int currentLevel,int upgradeLevel){
		if(upgradeLevel>mainBagUpgradeTemplates.size()){
			throw new IllegalArgumentException("主背包升级级别超出配置范围");
		}
		int price = 0;
		for(int i=1;i<=upgradeLevel;i++){
			price += mainBagUpgradeTemplates.get(currentLevel+i).getCostNum();
		}
		return price;
	}
}
