package com.hifun.soul.gameserver.target.manager;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.target.template.TargetTemplate;

/**
 * 个人目标模板管理器
 * 
 * @author yandajun
 * 
 */
@Component
public class TargetTemplateManager implements IInitializeRequired {
	private Map<Integer, TargetTemplate> targetTemplates = new HashMap<Integer, TargetTemplate>();

	@Override
	public void init() {
		targetTemplates = GameServerAssist.getTemplateService().getAll(
				TargetTemplate.class);
	}

	public Map<Integer, TargetTemplate> getTargetTemplates() {
		return targetTemplates;
	}

	public TargetTemplate getTargetTemplate(int targetId) {
		return targetTemplates.get(targetId);
	}
}
