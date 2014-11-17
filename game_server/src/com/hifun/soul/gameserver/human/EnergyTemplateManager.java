package com.hifun.soul.gameserver.human;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.human.template.BuyEnergyCostTemplate;

@Scope("singleton")
@Component
public class EnergyTemplateManager implements IInitializeRequired {

	private Map<Integer,BuyEnergyCostTemplate> buyEnergyCostTemplates;
	@Autowired
	private TemplateService templateService;
	private int maxBuyTimeConfig=0;
	@Override
	public void init() {
		buyEnergyCostTemplates = templateService.getAll(BuyEnergyCostTemplate.class);
		for(BuyEnergyCostTemplate template : buyEnergyCostTemplates.values()){
			if(template.getId()>maxBuyTimeConfig){
				maxBuyTimeConfig = template.getId();
			}
		}
	}
	
	public BuyEnergyCostTemplate getBuyEnergyCostTemplate(int buyTimes){
		if(!buyEnergyCostTemplates.containsKey(buyTimes)){
			return buyEnergyCostTemplates.get(maxBuyTimeConfig);
		}
		return buyEnergyCostTemplates.get(buyTimes);
	}

}
