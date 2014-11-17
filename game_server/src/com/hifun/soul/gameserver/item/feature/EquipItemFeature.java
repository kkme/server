package com.hifun.soul.gameserver.item.feature;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.hifun.soul.common.LogReasons.PropertyLogReason;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.util.KeyValuePair;
import com.hifun.soul.core.util.MathUtils;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.item.Item;
import com.hifun.soul.gameserver.item.ItemConstants;
import com.hifun.soul.gameserver.item.assist.GemItemInfo;
import com.hifun.soul.gameserver.item.template.EquipItemTemplate;
import com.hifun.soul.gameserver.item.template.GemAttribute;
import com.hifun.soul.gameserver.item.template.ItemForgeRandomTemplate;
import com.hifun.soul.gameserver.role.properties.amend.AmendMethod;
import com.hifun.soul.gameserver.role.properties.manager.HumanPropertyManager;

/**
 * 
 * 装备特有属性
 * 
 * @author magicstone
 * 
 */
public class EquipItemFeature implements ItemFeature {
	private Logger logger = Loggers.ITEM_LOGGER;
	/** 装备 */
	protected Item item;
	/** 模版 */
	private EquipItemTemplate template;
	/** 装备耐久 */
	private int endure;
	/** 强化等级 */
	private int level = 0;
	/** 装备的特殊属性 */
	private List<KeyValuePair<Integer, Integer>> equipSpecialAttributes = new ArrayList<KeyValuePair<Integer, Integer>>();
	/** 装备洗练的尚未替换的属性 */
	private List<KeyValuePair<Integer, Integer>> newEquipSpecialAttributes = null;
	/** 装备上的宝石信息 */
	private GemItemInfo[] gemItemInfos = null;
	/** 宝石孔数 */
	private int equipHole;
	/** 是否已装备 */
	private boolean isEquiped;
	/** 装备位置(在装备背包中的位置索引) */
	private int equipIndex = -1;

	public EquipItemFeature(Item item) {
		this.item = item;
		template = (EquipItemTemplate) item.getTemplate();
		initGemItemInfos();
		initSpecialAttributes();
	}

	public EquipItemTemplate getTemplate() {
		return template;
	}

	private void initGemItemInfos() {
		equipHole = template.getEquipHole();
		int maxEquipHole = template.getMaxEquipHole();
		if (maxEquipHole > ItemConstants.MAX_EQUIP_HOLE) {
			maxEquipHole = ItemConstants.MAX_EQUIP_HOLE;
		}
		gemItemInfos = new GemItemInfo[maxEquipHole];
	}

	private void initSpecialAttributes() {
		if (equipSpecialAttributes != null && equipSpecialAttributes.size() > 0) {
			return;
		}
		equipSpecialAttributes = new ArrayList<KeyValuePair<Integer, Integer>>();
		List<GemAttribute> _gemAttributes = template.getSpecialAttributes();
		if (_gemAttributes == null || _gemAttributes.size() == 0) {
			return;
		}
		for (GemAttribute specialAttribute : _gemAttributes) {
			if (specialAttribute == null) {
				continue;
			}
			if (specialAttribute.getPropKey() <= 0) {
				continue;
			}
			KeyValuePair<Integer, Integer> keyValuePair = new KeyValuePair<Integer, Integer>();
			keyValuePair.setKey(specialAttribute.getPropKey());
			keyValuePair.setValue(0);
			equipSpecialAttributes.add(keyValuePair);
		}
	}

	public int getEndure() {
		return endure;
	}

	public void setEndure(int endure) {
		this.endure = endure;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level,PropertyLogReason reason, String param) {
		if(isEquiped){
			// 在修改level之前调用
			updateHumanPropertity(level, reason, param);
		}
		this.level = level;
	}

	private void updateHumanPropertity(int level, PropertyLogReason reason, String param) {
		if (item.getHuman() == null) {
			return;
		}
		int nowLevel = this.level;
		int nextLevel = level;
		KeyValuePair<Integer, Integer>[] nowKeyValuePairs = GameServerAssist.getEquipUpgradeTemplateManager().getEquipUpgradeAttributes(
				item.getItemId(), nowLevel);
		KeyValuePair<Integer, Integer>[] nextKeyValuePairs = GameServerAssist.getEquipUpgradeTemplateManager().getEquipUpgradeAttributes(
				item.getItemId(), nextLevel);
		if (nowKeyValuePairs != null && nowKeyValuePairs.length > 0) {
			for (KeyValuePair<Integer, Integer> keyValuePair : nowKeyValuePairs) {
				if(keyValuePair.getKey() <= 0){
					continue;
				}
				item.getHuman()
						.getHumanPropertiesManager()
						.amendProperty(item.getHuman(), AmendMethod.ADD, keyValuePair.getKey(),
								-keyValuePair.getValue(), PropertyLogReason.EQUIP_UPGRADE, "");
			}
		}
		if (nextKeyValuePairs != null && nextKeyValuePairs.length > 0) {
			for (KeyValuePair<Integer, Integer> keyValuePair : nextKeyValuePairs) {
				if(keyValuePair.getKey() <= 0){
					continue;
				}
				item.getHuman()
						.getHumanPropertiesManager()
						.amendProperty(item.getHuman(), AmendMethod.ADD, keyValuePair.getKey(),
								keyValuePair.getValue(), PropertyLogReason.EQUIP_UPGRADE, "");
			}
		}
	}

	/**
	 * 获取装备的基础属性.(考虑到装备强化之后的)
	 * 
	 * @return
	 */
	public List<KeyValuePair<Integer, Integer>> getEquipBaseAttributes() {
		return getEquipTemplateBaseAttributes();
	}

	/**
	 * 模版配置的装备的基础属性
	 * 
	 * @return
	 */
	public List<KeyValuePair<Integer, Integer>> getEquipTemplateBaseAttributes() {
		List<KeyValuePair<Integer, Integer>> oldAttributes = template.getEquipAttributes();
		List<KeyValuePair<Integer, Integer>> newAttributes = new ArrayList<KeyValuePair<Integer, Integer>>();
		if (oldAttributes == null || oldAttributes.size() == 0) {
			return null;
		}
		for (KeyValuePair<Integer, Integer> keyValue : oldAttributes) {
			if (keyValue.getKey() != null
					&& keyValue.getKey() > 0
					&& keyValue.getValue() != null) {
				newAttributes.add(keyValue);
			}
		}
		return newAttributes;
	}

	/**
	 * 获取装备的特殊属性
	 * 
	 * @return
	 */
	public List<KeyValuePair<Integer, Integer>> getEquipSpecialAttributes() {
		return equipSpecialAttributes;
	}

	/**
	 * 设置装备的特殊属性
	 * 
	 * @param equipSpecialAttributes
	 */
	public void setEquipSpecialAttributes(List<KeyValuePair<Integer, Integer>> equipSpecialAttributes) {
		this.equipSpecialAttributes = equipSpecialAttributes;
	}

	/**
	 * 获取装备上宝石的信息
	 * 
	 * @return
	 */
	public GemItemInfo[] getGemItemInfos() {
		return gemItemInfos;
	}

	/**
	 * 设置装备上宝石属性
	 * 
	 * @param gemItemInfos
	 */
	public void addGemItemInfo(GemItemInfo gemItemInfo, int index, PropertyLogReason reason, String param) {
		if (gemItemInfos == null) {
			return;
		}
		gemItemInfo.setIndex(index);
		gemItemInfos[index] = gemItemInfo;
		if (item.getHuman() == null) {
			return;
		}
		if(isEquiped){
			for (KeyValuePair<Integer, Integer> keyValue : gemItemInfo.getEquipGemAttributes()) {
				if(keyValue.getKey()<=0){
					continue;
				}
				item.getHuman()
						.getPropertyManager()
						.amendProperty(item.getHuman(), AmendMethod.ADD, keyValue.getKey(), keyValue.getValue().intValue(),
								reason, param);
			}
		}
	}

	/**
	 * 重置装备上的宝石
	 * 
	 * @param gemItemInfos
	 */
	public void removeGemItemInfos() {
		for (int i = 0; i < gemItemInfos.length; i++) {
			if (gemItemInfos[i] == null) {
				continue;
			}
			if (item.getHuman() == null) {
				continue;
			}
			if(isEquiped){
				for (KeyValuePair<Integer, Integer> keyValue : gemItemInfos[i].getEquipGemAttributes()) {
					if(keyValue.getKey() <= 0){
						continue;
					}
					item.getHuman()
							.getPropertyManager()
							.amendProperty(item.getHuman(), AmendMethod.ADD, keyValue.getKey(),
									-keyValue.getValue().intValue(), PropertyLogReason.GEM_OFF, "");
				}
			}
			gemItemInfos[i] = null;
		}
	}

	/**
	 * 获取装备上某个孔的宝石
	 * 
	 * @param gemIndex
	 * @return
	 */
	public GemItemInfo getGemItemInfo(int gemIndex) {
		return gemItemInfos[gemIndex];
	}

	/**
	 * 清楚装备上某个孔上的宝石信息
	 * 
	 * @param gemIndex
	 */
	public void removeGameItemInfo(int gemIndex) {
		if (gemItemInfos[gemIndex] == null) {
			return;
		}
		if (item.getHuman() == null) {
			return;
		}
		if(isEquiped){
			for (KeyValuePair<Integer, Integer> keyValue : gemItemInfos[gemIndex].getEquipGemAttributes()) {
				if(keyValue.getKey() <= 0){
					continue;
				}
				item.getHuman()
						.getPropertyManager()
						.amendProperty(item.getHuman(), AmendMethod.ADD, keyValue.getKey(),
								-keyValue.getValue().intValue(), PropertyLogReason.GEM_OFF, "");
			}
		}
		gemItemInfos[gemIndex] = null;
	}

	/**
	 * 获取装备上默认的宝石孔数
	 * 
	 * @return
	 */
	public int getDefaultEquipHole() {
		return template.getEquipHole();
	}

	/**
	 * 获取装备最大可开启的宝石孔数
	 * 
	 * @return
	 */
	public int getMaxEquipHole() {
		return template.getMaxEquipHole();
	}

	/**
	 * 获取位置,装备类道具特有
	 * 
	 * @return
	 */
	public int getPosition() {
		return template.getPosition();
	}

	public int getEquipHole() {
		return equipHole;
	}

	public void setEquipHole(int equipHole) {
		this.equipHole = equipHole;
	}
	
	public boolean isEquiped() {
		return isEquiped;
	}

	public void setEquiped(boolean isEquiped) {
		this.isEquiped = isEquiped;
	}

	public int getEquipIndex() {
		return equipIndex;
	}

	public void setEquipIndex(int equipIndex) {
		this.equipIndex = equipIndex;
	}

	public List<KeyValuePair<Integer, Integer>> getNewEquipSpecialAttributes() {
		return newEquipSpecialAttributes;
	}

	public void setNewEquipSpecialAttributes(
			List<KeyValuePair<Integer, Integer>> newEquipSpecialAttributes) {
		this.newEquipSpecialAttributes = newEquipSpecialAttributes;
	}

	public int getNextGemPunchItemId() {
		switch (equipHole) {
		case 0:
			return getTemplate().getFirstItemId();
		case 1:
			return getTemplate().getSecondItemId();
		case 2:
			return getTemplate().getThirdItemId();
		case 3:
			return getTemplate().getFourItemId();
		}
		return 0;
	}

	public int getNextGemPunchItemNum() {
		switch (equipHole) {
		case 0:
			return getTemplate().getFirstItemNum();
		case 1:
			return getTemplate().getSecondItemNum();
		case 2:
			return getTemplate().getThirdItemNum();
		case 3:
			return getTemplate().getFourItemNum();
		}

		logger.error("no equipGemPubch Template! itemId:" + item.getTemplate().getId() + ";humanGuid:"
				+ item.getHuman().getHumanGuid() + ";nowEquipHole:" + equipHole);
		return Integer.MAX_VALUE;
	}

	/**
	 * 获取当前装备该等级的强化属性
	 * 
	 * @return
	 */
	public KeyValuePair<Integer, Integer>[] getEquipUpgradeAttributes() {
		return GameServerAssist.getEquipUpgradeTemplateManager().getEquipUpgradeAttributes(template.getId(), level);
	}

	/**
	 * 卸载或穿上装备是更新角色属性
	 * 
	 * @param isEquipOn
	 *            是否为穿上装备,否则为卸下装备
	 */
	public void amendHumanProperty(boolean isEquipOn, HumanPropertyManager propertyManager,PropertyLogReason reason,String param) {
		if (isEquipOn) {
			for (KeyValuePair<Integer, Integer> keyValue : this.getEquipBaseAttributes()) {
				// 装备位强化，属性加成
				int amendValue = (int) (item.getHuman().getHumanGodsoulManager().getCurrentEffect(item.getType()) * keyValue.getValue() / SharedConstants.DEFAULT_FLOAT_BASE);
				propertyManager.amendProperty(item.getHuman(), AmendMethod.ADD, keyValue.getKey(), keyValue.getValue() + amendValue,
						reason, param);
			}
			for (KeyValuePair<Integer, Integer> keyValue : this.getEquipSpecialAttributes()) {
				propertyManager.amendProperty(item.getHuman(), AmendMethod.ADD, keyValue.getKey(),
								keyValue.getValue().intValue(), reason, param);
			}
			for (KeyValuePair<Integer, Integer> keyValue : this.getEquipUpgradeAttributes()) {
				propertyManager.amendProperty(item.getHuman(), AmendMethod.ADD, keyValue.getKey(), keyValue.getValue(),
						reason, param);
			}
			for (GemItemInfo gemItem : this.getGemItemInfos()) {
				if (gemItem == null) {
					continue;
				}
				for (KeyValuePair<Integer, Integer> keyValue : gemItem.getEquipGemAttributes()) {
					propertyManager.amendProperty(item.getHuman(), AmendMethod.ADD, keyValue.getKey(),
									keyValue.getValue().intValue(), reason, param);
				}
			}
		} else {
			for (KeyValuePair<Integer, Integer> keyValue : this.getEquipBaseAttributes()) {
				// 装备位强化，属性加成
				int amendValue = (int) (item.getHuman().getHumanGodsoulManager().getCurrentEffect(item.getType()) * keyValue.getValue() / SharedConstants.DEFAULT_FLOAT_BASE);
				propertyManager.amendProperty(item.getHuman(), AmendMethod.ADD, keyValue.getKey(), -keyValue.getValue() - amendValue,
								PropertyLogReason.EQUIP_ONOFF, "");
			}
			for (KeyValuePair<Integer, Integer> keyValue : this.getEquipSpecialAttributes()) {
				propertyManager.amendProperty(item.getHuman(), AmendMethod.ADD, keyValue.getKey(),
								-keyValue.getValue().intValue(), PropertyLogReason.EQUIP_ONOFF, "");
			}
			for (KeyValuePair<Integer, Integer> keyValue : this.getEquipUpgradeAttributes()) {
				propertyManager.amendProperty(item.getHuman(), AmendMethod.ADD, keyValue.getKey(), -keyValue.getValue(),
								PropertyLogReason.EQUIP_ONOFF, "");
			}
			for (GemItemInfo gemItem : this.getGemItemInfos()) {
				if (gemItem == null) {
					continue;
				}
				for (KeyValuePair<Integer, Integer> keyValue : gemItem.getEquipGemAttributes()) {
					propertyManager.amendProperty(item.getHuman(), AmendMethod.ADD, keyValue.getKey(),
									-keyValue.getValue().intValue(), PropertyLogReason.EQUIP_ONOFF, "");
				}
			}
		}
	}
	
	/**
	 * 离线战斗时的属性加成
	 * @param propertyManager 
	 * @param reason
	 * @param param
	 */
	public void amendBattleProperty(HumanPropertyManager propertyManager,PropertyLogReason reason,String param) {
		for (KeyValuePair<Integer, Integer> keyValue : this.getEquipBaseAttributes()) {
			// 装备位强化，属性加成
			int amendValue = (int) (item.getHuman().getHumanGodsoulManager().getCurrentEffect(item.getType()) * keyValue.getValue() / SharedConstants.DEFAULT_FLOAT_BASE);
			propertyManager.amendProperty(item.getHuman(), AmendMethod.ADD, keyValue.getKey(), keyValue.getValue() + amendValue,
					reason, param);
		}
		for (KeyValuePair<Integer, Integer> keyValue : this.getEquipSpecialAttributes()) {
			propertyManager.amendProperty(item.getHuman(), AmendMethod.ADD, keyValue.getKey(),
							keyValue.getValue().intValue(), reason, param);
		}
		for (KeyValuePair<Integer, Integer> keyValue : this.getEquipUpgradeAttributes()) {
			propertyManager.amendProperty(item.getHuman(), AmendMethod.ADD, keyValue.getKey(), keyValue.getValue(),
					reason, param);
		}
		for (GemItemInfo gemItem : this.getGemItemInfos()) {
			if (gemItem == null) {
				continue;
			}
			for (KeyValuePair<Integer, Integer> keyValue : gemItem.getEquipGemAttributes()) {
				propertyManager.amendProperty(item.getHuman(), AmendMethod.ADD, keyValue.getKey(),
								keyValue.getValue().intValue(), reason, param);
			}
		}
	}
	
	/**
	 * 神魄装备位强化时更新角色属性
	 */
	public void amendHumanPropertyByGodsoul(HumanPropertyManager propertyManager,PropertyLogReason reason,String param) {
		for (KeyValuePair<Integer, Integer> keyValue : this.getEquipBaseAttributes()) {
			// 装备位强化，属性加成
			// 首先脱掉之前加的属性
			int oldEffect = item.getHuman().getHumanGodsoulManager().getCurrentEffect(item.getType()) - item.getHuman().getHumanGodsoulManager().getPerLevelEffect(item.getType());
			int reduceValue = (int) (oldEffect * keyValue.getValue() / SharedConstants.DEFAULT_FLOAT_BASE);
			propertyManager.amendProperty(item.getHuman(), AmendMethod.ADD, keyValue.getKey(), - reduceValue,
							PropertyLogReason.EQUIP_ONOFF, "");
			int amendValue = (int) (item.getHuman().getHumanGodsoulManager().getCurrentEffect(item.getType()) * keyValue.getValue() / SharedConstants.DEFAULT_FLOAT_BASE);
			propertyManager.amendProperty(item.getHuman(), AmendMethod.ADD, keyValue.getKey(), amendValue,
					reason, param);
		}
	}
	
	/**
	 * 调用方法时已经对lockIndexs进行校验
	 * @param lockIndexs
	 */
	public void equipForge(int[] lockIndexs) {
		List<Integer> lockIndexList = new ArrayList<Integer>();
		for(Integer lockIndex : lockIndexs){
			lockIndexList.add(lockIndex);
		}
		List<KeyValuePair<Integer, Integer>> newSpecialAttributes = new ArrayList<KeyValuePair<Integer, Integer>>();
		List<GemAttribute> gemAttributes = template.getSpecialAttributes();
		for(int i=0; i<getEquipSpecialAttributes().size(); i++){
			KeyValuePair<Integer, Integer> keyValuePair = getEquipSpecialAttributes().get(i);
			// 如果是锁定属性则不变更
			if(lockIndexList.contains(i)){
				newSpecialAttributes.add(keyValuePair);
			}
			else{
				// 重新随机该属性的值
				ItemForgeRandomTemplate itemForgeRandomTemplate = GameServerAssist.getForgeTemplateManager().getItemForgeRandomTemplate();
				if(itemForgeRandomTemplate == null){
					logger.error("can not find itemForgeRandomTemplate!");
					return;
				}
				// value = 最小值+(最大值-最小值)*最小值与最大值之间随机数/10000
				int rate = MathUtils.random(itemForgeRandomTemplate.getMinValue(), itemForgeRandomTemplate.getMaxValue());
				int value = (int) (gemAttributes.get(i).getPropValue()+(gemAttributes.get(i).getPropMaxValue()-gemAttributes.get(i).getPropValue())*rate/SharedConstants.DEFAULT_FLOAT_BASE);
				KeyValuePair<Integer, Integer> newKeyValuePair = new KeyValuePair<Integer, Integer>();
				newKeyValuePair.setKey(gemAttributes.get(i).getPropKey());
				newKeyValuePair.setValue(value);
				newSpecialAttributes.add(newKeyValuePair);
			}
		}
		setNewEquipSpecialAttributes(newSpecialAttributes);
	}
	
	/**
	 * 装备洗练保存
	 */
	public void equipForgeReplace() {
		if(getNewEquipSpecialAttributes() == null
				|| getNewEquipSpecialAttributes().size() <= 0
				|| getEquipSpecialAttributes().size() != getNewEquipSpecialAttributes().size()){
			return;
		}
		for(int i=0; i<getEquipSpecialAttributes().size(); i++){
			KeyValuePair<Integer, Integer> keyValuePair = getEquipSpecialAttributes().get(i);
			KeyValuePair<Integer, Integer> newKeyValuePair = getNewEquipSpecialAttributes().get(i);
			if(isEquiped){
				// 将原来的属性加成删除掉，增加新的属性加成
				item.getHuman().getHumanPropertiesManager().amendProperty(
						item.getHuman(), AmendMethod.ADD, keyValuePair.getKey(),
						-keyValuePair.getValue().intValue(), PropertyLogReason.EQUIP_FORGE, "");
				item.getHuman().getHumanPropertiesManager().amendProperty(
						item.getHuman(), AmendMethod.ADD, newKeyValuePair.getKey(),
						newKeyValuePair.getValue().intValue(), PropertyLogReason.EQUIP_FORGE, "");
			}
		}
		setEquipSpecialAttributes(getNewEquipSpecialAttributes());
		setNewEquipSpecialAttributes(null);
	}
	
	/**
	 * 装备洗练取消
	 */
	public void equipForgeCancel() {
		setNewEquipSpecialAttributes(null);
	}
}
