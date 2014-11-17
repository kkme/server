package com.hifun.soul.gameserver.godsoul.manager;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.godsoul.template.EquipBitBuffTemplate;
import com.hifun.soul.gameserver.godsoul.template.EquipBitLevelTemplate;
import com.hifun.soul.gameserver.godsoul.template.EquipBitPropertyAmendTemplate;
import com.hifun.soul.gameserver.godsoul.template.MagicPaperBuffTemplate;
import com.hifun.soul.gameserver.godsoul.template.MagicPaperLevelTemplate;

/**
 * 神魄模板管理器
 * 
 * @author yandajun
 * 
 */
@Component
public class GodsoulTemplateManager implements IInitializeRequired {
	@Autowired
	private TemplateService templateService;
	private Map<Integer, EquipBitLevelTemplate> equipBitLevelTemplates = new HashMap<Integer, EquipBitLevelTemplate>();
	private Map<Integer, EquipBitPropertyAmendTemplate> equipBitPropertyAmendTemplates = new HashMap<Integer, EquipBitPropertyAmendTemplate>();
	private Map<Integer, MagicPaperLevelTemplate> magicPaperLevelTemplates = new HashMap<Integer, MagicPaperLevelTemplate>();
	private Map<Integer, EquipBitBuffTemplate> equipBitBuffTemplates = new HashMap<Integer, EquipBitBuffTemplate>();
	private Map<Integer, MagicPaperBuffTemplate> magicPaperTemplates = new HashMap<Integer, MagicPaperBuffTemplate>();

	@Override
	public void init() {
		equipBitLevelTemplates = templateService
				.getAll(EquipBitLevelTemplate.class);
		equipBitPropertyAmendTemplates = templateService
				.getAll(EquipBitPropertyAmendTemplate.class);
		magicPaperLevelTemplates = templateService
				.getAll(MagicPaperLevelTemplate.class);
		equipBitBuffTemplates = templateService
				.getAll(EquipBitBuffTemplate.class);
		magicPaperTemplates = templateService
				.getAll(MagicPaperBuffTemplate.class);
	}

	public EquipBitLevelTemplate getEquipBitLevelTemplate(int level) {
		return equipBitLevelTemplates.get(level);
	}

	/**
	 * 获取装备位的最高等级
	 */
	public int getEquipBitMaxLevel() {
		// 由于配了0级，所以减1
		return equipBitLevelTemplates.size() - 1;
	}

	public EquipBitPropertyAmendTemplate getEquipBitPropertyAmendTemplate(
			int equipBitType) {
		return equipBitPropertyAmendTemplates.get(equipBitType);
	}

	public MagicPaperLevelTemplate getMagicPaperTemplate(int paperId, int level) {
		for (Integer id : magicPaperLevelTemplates.keySet()) {
			MagicPaperLevelTemplate template = magicPaperLevelTemplates.get(id);
			if (template.getPaperId() == paperId
					&& template.getPaperLevel() == level) {
				return template;
			}
		}
		return null;
	}

	/**
	 * 获取灵图的最高等级
	 */
	public int getMagicPaperMaxLevell(int paperId) {
		int level = 0;
		for (Integer id : magicPaperLevelTemplates.keySet()) {
			MagicPaperLevelTemplate template = magicPaperLevelTemplates.get(id);
			if (template.getPaperId() == paperId) {
				level++;
			}
		}
		return level - 1;
	}

	public Map<Integer, EquipBitBuffTemplate> getEquipBitBuffTemplates() {
		return equipBitBuffTemplates;
	}

	public Map<Integer, MagicPaperBuffTemplate> getMagicPaperBuffTemplates() {
		return magicPaperTemplates;
	}
}
