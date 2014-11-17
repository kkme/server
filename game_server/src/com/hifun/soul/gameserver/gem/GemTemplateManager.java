package com.hifun.soul.gameserver.gem;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.gem.template.GemUpgradeTemplate;
import com.hifun.soul.gameserver.gem.template.GiftChipTemplate;
import com.hifun.soul.gameserver.gem.template.MagicPaperUpgradeTemplate;

@Scope("singleton")
@Component
public class GemTemplateManager implements IInitializeRequired {

	private Map<Integer, GemUpgradeTemplate> gemUpgradeTemplates = new HashMap<Integer, GemUpgradeTemplate>();
	private Map<Integer, MagicPaperUpgradeTemplate> magicPaperUpgradeTemplates = new HashMap<Integer, MagicPaperUpgradeTemplate>();
	private Map<Integer, GiftChipTemplate> giftChipTemplates = new HashMap<Integer, GiftChipTemplate>();
	@Autowired
	private TemplateService templateService;

	@Override
	public void init() {
		gemUpgradeTemplates = templateService.getAll(GemUpgradeTemplate.class);
		magicPaperUpgradeTemplates = templateService
				.getAll(MagicPaperUpgradeTemplate.class);
		giftChipTemplates = templateService.getAll(GiftChipTemplate.class);
	}

	/**
	 * 根据宝石Id获取宝石升级模板
	 * 
	 * @param gemItemId
	 * @return
	 */
	public GemUpgradeTemplate getGemUpgradeTemplate(int gemItemId) {
		return gemUpgradeTemplates.get(gemItemId);
	}

	/**
	 * 根据灵图ID获取灵图合成模板
	 */
	public MagicPaperUpgradeTemplate getMagicPaperUpgradeTemplate(
			int magicPaperItemId) {
		return magicPaperUpgradeTemplates.get(magicPaperItemId);
	}

	/**
	 * 根据天赋碎片ID获取天赋碎片合成模板
	 */
	public GiftChipTemplate getGiftChipTemplate(int giftChipId) {
		return giftChipTemplates.get(giftChipId);
	}
}
