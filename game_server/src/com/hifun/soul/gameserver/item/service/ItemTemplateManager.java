package com.hifun.soul.gameserver.item.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.core.util.StringUtils;
import com.hifun.soul.gameserver.item.ItemConstantId;
import com.hifun.soul.gameserver.item.ItemConstantIndex;
import com.hifun.soul.gameserver.item.template.ConsumeItemTemplate;
import com.hifun.soul.gameserver.item.template.EquipItemAttribute;
import com.hifun.soul.gameserver.item.template.EquipItemTemplate;
import com.hifun.soul.gameserver.item.template.ItemConstantIdTemplate;
import com.hifun.soul.gameserver.item.template.ItemTemplate;
import com.hifun.soul.gameserver.item.template.MaterialItemTemplate;
import com.hifun.soul.gameserver.role.properties.amend.AmendService;
import com.hifun.soul.gameserver.role.properties.amend.AmendTriple;

/**
 * 模版初始化完成之后调用
 * @author magicstone
 */
@Scope("singleton")
@Component
public class ItemTemplateManager implements IInitializeRequired {

	@Autowired
	private TemplateService templateService;
	@Autowired
	private AmendService amendService;

	/** key:itemId value:EquipItemTemplate */
	private Map<Integer, EquipItemTemplate> euqipItemTemplates = new HashMap<Integer, EquipItemTemplate>();
	/** key:itemId value:MaterialItemTemplate */
	private Map<Integer, MaterialItemTemplate> materialItemTemplates = new HashMap<Integer, MaterialItemTemplate>();
	/** key:itemId value:ConsumeItemTemplate */
	private Map<Integer, ConsumeItemTemplate> consumeItemTemplates = new HashMap<Integer, ConsumeItemTemplate>();
		
	@Override
	public void init() {
		Map<Integer, EquipItemTemplate> equips = templateService
				.getAll(EquipItemTemplate.class);
		Map<Integer, MaterialItemTemplate> materials = templateService
				.getAll(MaterialItemTemplate.class);
		Map<Integer, ConsumeItemTemplate> consumes = templateService
				.getAll(ConsumeItemTemplate.class);

		for (Integer id : equips.keySet()) {
			EquipItemTemplate equip = equips.get(id);
			euqipItemTemplates.put(equip.getId(), equip);
		}

		for (Integer id : materials.keySet()) {
			MaterialItemTemplate material = materials.get(id);
			materialItemTemplates.put(material.getId(), material);
		}

		for (Integer id : consumes.keySet()) {
			ConsumeItemTemplate consume = consumes.get(id);
			consumeItemTemplates.put(consume.getId(), consume);
		}
		
		//初始化道具常量
		Map<Integer,ItemConstantIdTemplate> itemConstantIdTemplates = templateService.getAll(ItemConstantIdTemplate.class);
		ItemConstantId.SIMPLE_FORTUNE_STONE_ID = itemConstantIdTemplates.get(ItemConstantIndex.SIMPLE_FORTUNE_STONE_INDEX).getItemId();
		ItemConstantId.SUPER_FORTUNE_STONE_ID = itemConstantIdTemplates.get(ItemConstantIndex.SUPER_FORTUNE_STONE_INDEX).getItemId();
		ItemConstantId.GUARD_STONE_ID = itemConstantIdTemplates.get(ItemConstantIndex.GUARD_STONE_INDEX).getItemId();
		ItemConstantId.HORN_ID = itemConstantIdTemplates.get(ItemConstantIndex.HORN_INDEX).getItemId();
		ItemConstantId.RESET_PROP_ITEM_ID = itemConstantIdTemplates.get(ItemConstantIndex.RESET_PROP_ITEM_INDEX).getItemId();
		ItemConstantId.BOSS_WAR_DAMAGE_SPREE_ID = itemConstantIdTemplates.get(ItemConstantIndex.BOSS_WAR_DAMAGE_SPREE_INDEX).getItemId();
		ItemConstantId.RESET_SKILL_POINT_ITEM_ID = itemConstantIdTemplates.get(ItemConstantIndex.RESET_SKILL_POINT_ITEM_INDEX).getItemId();
		ItemConstantId.RESET_GIFT_POINT_ITEM_ID = itemConstantIdTemplates.get(ItemConstantIndex.RESET_GIFT_POINT_ITEM_INDEX).getItemId();
		ItemConstantId.BATTLE_ROBOT_ID = itemConstantIdTemplates.get(ItemConstantIndex.BATTLE_ROBOT_INDEX).getItemId();
		ItemConstantId.FORGE_STONE_ID = itemConstantIdTemplates.get(ItemConstantIndex.FORGE_STONE_INDEX).getItemId();
		ItemConstantId.MARS_KILL_REWARD_ID = itemConstantIdTemplates.get(ItemConstantIndex.MARS_KILL_REWARD_INDEX).getItemId();
		ItemConstantId.LEGION_BOSS_DAMAGE_SPREE_ID = itemConstantIdTemplates.get(ItemConstantIndex.LEGION_BOSS_DAMAGE_SPREE_INDEX).getItemId();
	}

	public boolean isEquip(Integer itemId) {
		if (itemId == null) {
			return false;
		}

		if (euqipItemTemplates.get(itemId) == null) {
			return false;
		}

		return true;
	}

	public boolean isMaterial(Integer itemId) {
		if (itemId == null) {
			return false;
		}

		if (materialItemTemplates.get(itemId) == null) {
			return false;
		}

		return true;
	}

	public boolean isConsume(Integer itemId) {
		if (itemId == null) {
			return false;
		}

		if (consumeItemTemplates.get(itemId) == null) {
			return false;
		}

		return true;
	}

	/**
	 * 根据{@link EquipItemAttribute}生成对应的{@link AmendTriple}
	 * 
	 * @param attrs
	 * @return
	 */
	public List<AmendTriple> convertAmendTuples(List<EquipItemAttribute> attrs) {
		if (attrs == null) {
			return Collections.emptyList();
		}

		ArrayList<AmendTriple> tuples = new ArrayList<AmendTriple>();
		for (EquipItemAttribute attr : attrs) {
			if (attr == null || StringUtils.isEmpty(attr.getPropKey())) {
				continue;
			}

			AmendTriple tuple = amendService.createAmendTriple(
					attr.getPropKey(), attr.getPropValue(),
					attr.getEnhancePerLevel());
			tuples.add(tuple);
		}
		return tuples;
	}

	public EquipItemTemplate getEquipItemTemplate(Integer itemId) {
		return euqipItemTemplates.get(itemId);
	}

	public MaterialItemTemplate getMaterialItemTemplate(Integer itemId) {
		return materialItemTemplates.get(itemId);
	}

	public ConsumeItemTemplate getConsumeItemTemplate(Integer itemId) {
		return consumeItemTemplates.get(itemId);
	}

	public ItemTemplate getItemTemplate(int itemId) {
		if (getEquipItemTemplate(itemId) != null) {
			return getEquipItemTemplate(itemId);
		}
		if (getMaterialItemTemplate(itemId) != null) {
			return getMaterialItemTemplate(itemId);
		}
		if (getConsumeItemTemplate(itemId) != null) {
			return getConsumeItemTemplate(itemId);
		}
		return null;
	}
	
}
