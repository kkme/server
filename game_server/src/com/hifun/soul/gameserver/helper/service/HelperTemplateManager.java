package com.hifun.soul.gameserver.helper.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.helper.template.HelperTemplate;

@Scope("singleton")
@Component
public class HelperTemplateManager implements IInitializeRequired {

	@Autowired
	private TemplateService templateService;
	/** 小助手开放状态 */
	private Map<Integer,Boolean> helperStateMap = new HashMap<Integer,Boolean>();
	
	@Override
	public void init() {
		for(HelperTemplate template : templateService.getAll(HelperTemplate.class).values()){
			if(template.getOpen() > 0){
				helperStateMap.put(template.getId(), true);
			}
			else{
				helperStateMap.put(template.getId(), false);
			}
		}
	}
	
	/**
	 * 判断某个小助手是否开启
	 * 
	 * @param helperType
	 * @return
	 */
	public boolean helperIsOpen(int helperType) {
		if(helperStateMap.get(helperType) != null){
			return helperStateMap.get(helperType);
		}
		else{
			return false;
		}
	}

}
