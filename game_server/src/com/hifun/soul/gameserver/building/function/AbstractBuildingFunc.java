package com.hifun.soul.gameserver.building.function;

import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.template.GameFuncTemplate;

/**
 * 抽象建筑功能
 * 
 * @author magicstone
 *
 */
public abstract class AbstractBuildingFunc implements IBuildingFunc{
	
	private String name;
	
	private String desc;
	
	private GameFuncTemplate template;
	
	public void init() {
		template = GameServerAssist.getTemplateService().get(getBuildingFuncType(), GameFuncTemplate.class);
		
		if(template != null){
			this.setName(template.getName());
			this.setDesc(template.getDesc());
		}
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	@Override
	public boolean isOpen(){
		return true;
	}
}
