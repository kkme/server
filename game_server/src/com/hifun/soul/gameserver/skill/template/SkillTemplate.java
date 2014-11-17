package com.hifun.soul.gameserver.skill.template;

import java.util.ArrayList;
import java.util.List;

import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.core.context.ApplicationContext;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.item.service.ItemTemplateManager;
import com.hifun.soul.gameserver.item.template.ItemTemplate;
import com.hifun.soul.gameserver.skill.effect.IBuffEffEctor;
import com.hifun.soul.gameserver.skill.effect.SkillEffectType;
import com.hifun.soul.gameserver.skill.effect.manager.ISkillEffectManager;
import com.hifun.soul.gameserver.skill.effect.manager.SkillEffectManager;

/**
 * 技能模版;
 * 
 * @author crazyjohn
 * 
 */
@ExcelRowBinding
public class SkillTemplate extends SkillTemplateVO {
	private ISkillEffectManager effectorManager;
	private List<Integer> buffList = new ArrayList<Integer>();
	private SkillScrollTemplate scrollTemplate;
	/** 前置技能模版 */
	private SkillTemplate preSkillTemplate;
	/** 需要的卷轴物品模版 */
	private ItemTemplate itemTemplate;
	private TemplateService templateService;
	private ItemTemplateManager itemTemplateManager;

	public SkillTemplate() {
		effectorManager = new SkillEffectManager();
		templateService = ApplicationContext.getApplicationContext().getBean(
				TemplateService.class);
		itemTemplateManager = ApplicationContext.getApplicationContext()
				.getBean(ItemTemplateManager.class);
	}

	@Override
	public void check() throws TemplateConfigException {
		// 检查第二效果的取值范围
		SkillEffectTemplate effect = this.effects.get(1);
		if (effect == null) {
			return;
		}
		boolean find = false;
		for (SkillEffectType type : SkillEffectType.getSecondEffectTypes()) {
			if (type.getIndex() == effect.getEffectId()) {
				find = true;
			}
		}
		if (!find && effect.getEffectId() != 0) {
			throw new TemplateConfigException(this.getSheetName(),
					this.getId(), 23, "[ 技能效果列表] 技能第二效果不可以为："
							+ effect.getEffectId());
		}
		// 构建buff列表
		buildBuffList();
		// 构建技能卷轴模版
		this.setScrollTemplate(templateService.get(this.id, SkillScrollTemplate.class));
		if (this.scrollTemplate == null) {
			return;
		}
		// 前置技能
		this.setPreSkillTemplate(templateService.get(
				scrollTemplate.getPreSkillId(), SkillTemplate.class));
		// 卷轴物品模版
		setItemTemplate(itemTemplateManager
				.getItemTemplate(scrollTemplate.getNeedSkillScrollId()));

	}

	public List<Integer> getTriggerBuffList() {
		return buffList;
	}

	private void buildBuffList() {
		if (!this.canTriggerBuff()) {
			return;
		}
		// 第一效果
		SkillEffectTemplate effect1 = this.getEffects().get(0);
		if (effect1 == null) {
			return;
		}
		if (this.isBuffEffect(effect1.getEffectId())) {
			IBuffEffEctor buffEffector = (IBuffEffEctor) effectorManager
					.getSkillEffector(SkillEffectType.typeOf(effect1
							.getEffectId()));
			buffList.add(buffEffector.getBuffResourceId(this.getEffects().get(0)
					.formParams()));
		}
		// 第二效果
		SkillEffectTemplate effect2 = this.getEffects().get(1);
		if (effect2 == null) {
			return;
		}
		if (this.isBuffEffect(effect2.getEffectId())) {
			IBuffEffEctor buffEffector = (IBuffEffEctor) effectorManager
					.getSkillEffector(SkillEffectType.typeOf(effect2
							.getEffectId()));
			buffList.add(buffEffector.getBuffResourceId(this.getEffects().get(1)
					.formParams()));
		}
	}

	/**
	 * 是否是一个会触发buff的技能;
	 * 
	 * @param effectId
	 * @return
	 */
	private boolean isBuffEffect(int effectId) {
		SkillEffectType effectType = SkillEffectType.typeOf(effectId);
		if (effectType.isBuffEffector()) {
			return true;
		}
		return false;
	}

	private boolean canTriggerBuff() {
		SkillEffectTemplate effect1 = this.getEffects().get(0);
		if (effect1 == null) {
			return false;
		}
		SkillEffectTemplate effect2 = this.getEffects().get(1);
		if (!isBuffEffect(effect1.getEffectId())
				&& ((effect2 == null) || !isBuffEffect(effect2.getEffectId()))) {
			return false;
		}
		return true;
	}

	public SkillScrollTemplate getScrollTemplate() {
		return scrollTemplate;
	}

	public void setScrollTemplate(SkillScrollTemplate scrollTemplate) {
		this.scrollTemplate = scrollTemplate;
	}

	public SkillTemplate getPreSkillTemplate() {
		return preSkillTemplate;
	}

	public void setPreSkillTemplate(SkillTemplate preSkillTemplate) {
		this.preSkillTemplate = preSkillTemplate;
	}

	public ItemTemplate getItemTemplate() {
		return itemTemplate;
	}

	public void setItemTemplate(ItemTemplate itemTemplate) {
		this.itemTemplate = itemTemplate;
	}

}
