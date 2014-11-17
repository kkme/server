package com.hifun.soul.gameserver.cd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.cd.template.CdTemplate;
import com.hifun.soul.gameserver.cd.template.CdTiredTemplate;


/**
 * 
 * cd模版服务，对模版数据以常用到的结构组织到内存
 * 
 * @author magicstone
 *
 */
@Scope("singleton")
@Component
public class CdTemplateManager implements IInitializeRequired {

	@Autowired
	private TemplateService templateService;
	/** 最大的疲劳度系数 */
	private CdTiredTemplate maxCdTiredTemplate;
	
	@Override
	public void init() {
		for(CdTiredTemplate template : templateService.getAll(CdTiredTemplate.class).values()){
			if(maxCdTiredTemplate == null){
				maxCdTiredTemplate = template;
			}
			else if(template.getId() > maxCdTiredTemplate.getId()){
				maxCdTiredTemplate = template;
			}
		}
	}
	
	public CdTemplate getCdTemplate(Integer cdType) {
		return templateService.get(cdType, CdTemplate.class);
	}
	
	public CdTiredTemplate getCdTiredTemplate(Integer time) {
		return templateService.get(time, CdTiredTemplate.class);
	}

	/**
	 * 最大的疲劳度系数
	 * @return
	 */
	public float getMaxCdTired() {
		return maxCdTiredTemplate.getTired();
	}

}
