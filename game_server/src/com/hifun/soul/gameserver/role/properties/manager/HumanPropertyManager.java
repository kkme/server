package com.hifun.soul.gameserver.role.properties.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.hifun.soul.common.IHeartBeatable;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.core.util.KeyValuePair;
import com.hifun.soul.gamedb.cache.ICachableComponent;
import com.hifun.soul.gamedb.entity.HumanEntity;
import com.hifun.soul.gamedb.entity.HumanOtherPropertiesEntity;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.IHumanPersistenceManager;
import com.hifun.soul.gameserver.human.msg.GCCharacterProperties;
import com.hifun.soul.gameserver.human.occupation.template.CharacterOccupationInitPropTemplate;
import com.hifun.soul.gameserver.human.occupation.template.CharacterPropConverterRateTemplate;
import com.hifun.soul.gameserver.human.template.BuyEnergyCostTemplate;
import com.hifun.soul.gameserver.human.template.HumanGemPropTemplate;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.HumanLongProperty;
import com.hifun.soul.gameserver.role.properties.IntPropertyCacheSet;
import com.hifun.soul.gameserver.role.properties.IntPropertySet;
import com.hifun.soul.gameserver.role.properties.Level1Property;
import com.hifun.soul.gameserver.role.properties.Level2Property;
import com.hifun.soul.gameserver.role.properties.PropertyType;
import com.hifun.soul.proto.common.Character.CharProperty;
import com.hifun.soul.proto.data.entity.Entity.HumanOtherProperties;

/**
 * 玩家属性管理器;
 * 
 * @author crazyjohn
 * 
 */
public class HumanPropertyManager extends RolePropertyManager<Human> implements
		ICachableComponent, IHumanPersistenceManager, IHeartBeatable {
	public HumanPropertyManager(Human role) {
		super(role);
		this.role.registerCachableManager(this);
		this.role.registerPersistenceManager(this);
	}

	@Override
	public Human getOwner() {
		return role;
	}

	@Override
	public void onLoad(HumanEntity humanEntity) {
		HumanOtherProperties.Builder props = humanEntity.getBuilder()
				.getOtherPropertiesBuilder();
		for (CharProperty prop : props.getPropsList()) {
			this.setProperty(prop.getKey(), prop.getValue());
		}
		// 重新计算裸身属性
		this.recalculateInitProperties(role);
		recalculateConstantProperties();
		recalculateTemplateProperties();
	}

	/**
	 * 初始化常量属性
	 */
	private void recalculateConstantProperties() {
		// 每次体力值恢复的点数
		this.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.setPropertyValue(HumanIntProperty.MAX_ENERGY,
						GameServerAssist.getGameConstants().getMaxEnergy());
		// 体力值恢复时间间隔
		this.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.setPropertyValue(HumanIntProperty.ENERGY_RECOVER_INTERVAL,
						GameServerAssist.getGameConstants().getEnergyRecoverInterval());
		// 每次体力值恢复的点数
		this.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.setPropertyValue(HumanIntProperty.ENERGY_RECOVER_NUM,
						GameServerAssist.getGameConstants().getEnergyRecoverNum());

	}

	/**
	 * 初始化模板中的属性
	 */
	private void recalculateTemplateProperties() {
		int buyTime = this.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.getPropertyValue(HumanIntProperty.BUY_ENERGY_TIMES);
		int remainBuyTime = GameServerAssist.getVipPrivilegeTemplateManager().getMaxBuyEnergyTimes(role
				.getVipLevel()) - buyTime;
		// 下次购买体力花费
		BuyEnergyCostTemplate buyEnergyTemplate = GameServerAssist.getEnergyTemplateManager()
				.getBuyEnergyCostTemplate(buyTime + 1);
		this.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.setPropertyValue(HumanIntProperty.BUY_ENERGY_CRYSTAL_COST_NUM,
						buyEnergyTemplate.getCrystalCost());
		// 下次购买体力获得的点数
		this.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.setPropertyValue(HumanIntProperty.BUY_ENERGY_ADD_NUM,
						buyEnergyTemplate.getEnergyNum());
		// 剩余购买体力值的次数
		this.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.setPropertyValue(HumanIntProperty.BUY_ENERGY_REMAIN_TIMES,
						remainBuyTime);
	}

	@Override
	public void onPersistence(HumanEntity humanEntity) {
		HumanOtherProperties.Builder otherProps = HumanOtherProperties
				.newBuilder();
		otherProps.setHumanGuid(humanEntity.getId());
		for (Entry<Integer, Long> entry : this.getAllPersistenceProperties()
				.entrySet()) {
			otherProps.addProps(CharProperty.newBuilder()
					.setKey(entry.getKey()).setValue(entry.getValue()));
		}

		humanEntity.getBuilder().setOtherProperties(otherProps);
	}

	/**
	 * 获取所有需要存储的属性;
	 * 
	 * @return
	 */
	private Map<Integer, Long> getAllPersistenceProperties() {
		Map<Integer, Long> propertyMap = new HashMap<Integer, Long>();
		Map<Integer, Integer> commonPropMap = this.getAllCommonProps();
		// 合并
		for (Map.Entry<Integer, Integer> entry : commonPropMap.entrySet()) {
			propertyMap.put(entry.getKey(), entry.getValue().longValue());
		}
		// 需要持久化的长整形属性;
		for (int i = HumanLongProperty._BEGIN; i < HumanLongProperty._END; i++) {
			propertyMap.put(PropertyType.genPropertyKey(i,
					PropertyType.HUMAN_LONG_PROPERTY), getLongPropertyValue(i));
		}
		return propertyMap;
	}

	private Map<Integer, Integer> getAllCommonProps() {
		Map<Integer, Integer> propertyMap = new HashMap<Integer, Integer>();
		// 存储1级属性
		for (int i = Level1Property._BEGIN; i <= Level1Property._END; i++) {
			propertyMap.put(PropertyType.genPropertyKey(i,
					PropertyType.LEVEL1_PROPERTY),
					getIntPropertySet(PropertyType.LEVEL1_PROPERTY)
							.getPropertyValue(i));
		}
		// 存储2级属性
		for (int i = Level2Property._BEGIN; i <= Level2Property._END; i++) {
			propertyMap.put(PropertyType.genPropertyKey(i,
					PropertyType.LEVEL2_PROPERTY),
					getIntPropertySet(PropertyType.LEVEL2_PROPERTY)
							.getPropertyValue(i));
		}
		// 存储角色整形属性
		for (int i = HumanIntProperty._BEGIN; i <= HumanIntProperty._END; i++) {
			propertyMap.put(PropertyType.genPropertyKey(i,
					PropertyType.HUMAN_INT_PROPERTY),
					getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
							.getPropertyValue(i));
		}
		return propertyMap;
	}

	/**
	 * 获取所有需要通知客户端的属性;
	 * 
	 * @return
	 */
	public Map<Integer, Integer> getAllProperties() {
		return this.getAllCommonProps();
	}

	public boolean isChanged() {
		for (IntPropertySet propSet : this.intPropertySets.values()) {
			if (propSet.isChanged()) {
				return true;
			}
		}
		if (this.longPropertySet.isChanged()) {
			return true;
		}
		return false;
	}

	@Override
	public List<IEntity> getUpdateEntities() {
		List<IEntity> updateList = new ArrayList<IEntity>();
		if (isLongPropModified() || isIntPropModified()) {
			HumanOtherPropertiesEntity propertyEntity = new HumanOtherPropertiesEntity();
			HumanOtherProperties.Builder builder = propertyEntity.getBuilder();
			builder.setHumanGuid(this.role.getHumanGuid());
			for (Entry<Integer, IntPropertyCacheSet> entrySet : this.intPropertySets
					.entrySet()) {
				for (Integer index : entrySet.getValue().getCacheData()
						.getAllUpdateData()) {
					builder.addProps(CharProperty
							.newBuilder()
							.setKey(PropertyType.genPropertyKey(index,
									entrySet.getKey()))
							.setValue(
									entrySet.getValue().getPropertyValue(index)));
				}
			}
			for (Integer index : this.longPropertySet.getCacheData()
					.getAllUpdateData()) {
				builder.addProps(CharProperty
						.newBuilder()
						.setKey(PropertyType.genPropertyKey(index,
								longPropertySet.getPropertyType()))
						.setValue(longPropertySet.getPropertyValue(index)));
			}
			updateList.add(propertyEntity);

		}

		return updateList;
	}

	private boolean isIntPropModified() {
		for (IntPropertyCacheSet intCache : this.initProperties) {
			if (intCache.isModified()) {
				return true;
			}
		}
		return false;
	}

	private boolean isLongPropModified() {
		return this.longPropertySet.isModified();
	}

	@Override
	public List<IEntity> getDeleteEntities() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 将有改动的数值数据发送到客户端
	 * 
	 * @param reset
	 *            如果reset标识为true,则会在快照完成后重置更新状态
	 */

	public void snapChangedProperty(boolean reset) {
		// 如果 LevelA,LevelB,DynamicNumProp,DynamicOtherProp 均无变化，则返回NULL
		if (!isChanged()) {
			return;
		}
		// 保存数值类属性变化
		List<KeyValuePair<Integer, Integer>> _numChanged = changedNum();

		KeyValuePair<Integer, Integer>[] empty = KeyValuePair
				.newKeyValuePairArray(0);

		if (_numChanged != null && !_numChanged.isEmpty()) {
			role.sendMessage(new GCCharacterProperties(role.getId(),
					_numChanged.toArray(empty)));
		}

		if (reset) {
			resetChange();
		}
	}

	private void resetChange() {
		for (IntPropertySet propSet : this.intPropertySets.values()) {
			propSet.resetChanged();
		}
		this.longPropertySet.resetChanged();
	}

	private List<KeyValuePair<Integer, Integer>> changedNum() {
		// 保存数值类属性变化
		List<KeyValuePair<Integer, Integer>> intNumChanged = new ArrayList<KeyValuePair<Integer, Integer>>();

		// 处理 baseIntProps
		for (IntPropertySet propSet : this.intPropertySets.values()) {
			if (propSet.isChanged()) {
				KeyValuePair<Integer, Integer>[] changes = propSet.getChanged();
				for (KeyValuePair<Integer, Integer> pair : changes) {
					intNumChanged.add(pair);
				}
			}
		}

		return intNumChanged;
	}

	@Override
	public void recalculateInitProperties(Human human) {
		// 重新计算裸身属性
		clearInitProperties();
		// 清空一二级属性;
		clearLevel1AndLevel2Properties();

		// 出生的基础一级属性点 + 升级后系统分配的一级属性点
		CharacterOccupationInitPropTemplate initPropTemplate = GameServerAssist.getTemplateService()
				.get(this.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
						.getPropertyValue(HumanIntProperty.OCCUPATION),
						CharacterOccupationInitPropTemplate.class);
		if (initPropTemplate == null) {
			throw new RuntimeException(
					"No such InitPropTemplate, occupationType: "
							+ human.getOccupation());
		}
		RoleInitPropsHelper.initHumanProps(this, initPropTemplate);

		this.needRecalculate = true;

	}

	@Override
	protected void calculateLevel1ToLevel2Properties() {
		// FIXME: crazyjohn 策划新添加的一二级属性转换需要去维护这里;
		Map<Integer, CharacterPropConverterRateTemplate> convertMap = GameServerAssist.getPropertyConverterRateManager()
				.getOccupationPropConvertRateMap(
						this.role.getOccupation().getIndex());
		// HP
		float hp = 0;
		for (int i = 0; i < convertMap.size(); i++) {
			hp += convertMap.get(i).getHp() / SharedConstants.PROPERTY_DIVISOR
					* getLevel1PropertyByIndex(i);
		}
		this.setLevel2PropertyByIndex(
				Level2Property.MAX_HP,
				(int) (Math.floor(hp) + getLevel2PropertyByIndex(Level2Property.MAX_HP)));
		// 技能攻击
		float attack = 0;
		for (int i = 0; i < convertMap.size(); i++) {
			attack += convertMap.get(i).getSkillAttack()
					/ SharedConstants.PROPERTY_DIVISOR
					* getLevel1PropertyByIndex(i);
		}
		this.setLevel2PropertyByIndex(
				Level2Property.ATTACK,
				(int) (Math.floor(attack) + getLevel2PropertyByIndex(Level2Property.ATTACK)));

		// 技能防御
		float defense = 0;
		for (int i = 0; i < convertMap.size(); i++) {
			defense += convertMap.get(i).getSkillDefense()
					/ SharedConstants.PROPERTY_DIVISOR
					* getLevel1PropertyByIndex(i);
		}
		this.setLevel2PropertyByIndex(
				Level2Property.DEFENSE,
				(int) (Math.floor(defense) + getLevel2PropertyByIndex(Level2Property.DEFENSE)));

		// 宝石攻击
		float gemAttack = 0;
		for (int i = 0; i < convertMap.size(); i++) {
			gemAttack += convertMap.get(i).getGemAttack()
					/ SharedConstants.PROPERTY_DIVISOR
					* getLevel1PropertyByIndex(i);
		}
		this.setLevel2PropertyByIndex(
				Level2Property.GEM_ATTACK,
				(int) (Math.floor(gemAttack) + getLevel2PropertyByIndex(Level2Property.GEM_ATTACK)));

		// 宝石防御
		float gemDefense = 0;
		for (int i = 0; i < convertMap.size(); i++) {
			gemDefense += convertMap.get(i).getGemDefense()
					/ SharedConstants.PROPERTY_DIVISOR
					* getLevel1PropertyByIndex(i);
		}
		this.setLevel2PropertyByIndex(
				Level2Property.GEM_DEFENSE,
				(int) (Math.floor(gemDefense) + getLevel2PropertyByIndex(Level2Property.GEM_DEFENSE)));
		// 命中
		int hit = 0;
		for (int i = 0; i < convertMap.size(); i++) {
			hit += convertMap.get(i).getHit() * getLevel1PropertyByIndex(i);
		}
		this.setLevel2PropertyByIndex(Level2Property.HIT, hit
				+ getLevel2PropertyByIndex(Level2Property.HIT));

		// 闪避
		int dodge = 0;
		for (int i = 0; i < convertMap.size(); i++) {
			dodge += convertMap.get(i).getDodge() * getLevel1PropertyByIndex(i);
		}
		this.setLevel2PropertyByIndex(Level2Property.DODGE, dodge
				+ getLevel2PropertyByIndex(Level2Property.DODGE));

		// 暴击
		int critical = 0;
		for (int i = 0; i < convertMap.size(); i++) {
			critical += convertMap.get(i).getCritical()
					* getLevel1PropertyByIndex(i);
		}
		this.setLevel2PropertyByIndex(Level2Property.CRITICAL, critical
				+ getLevel2PropertyByIndex(Level2Property.CRITICAL));

		// 韧性
		int uncritical = 0;
		for (int i = 0; i < convertMap.size(); i++) {
			uncritical += convertMap.get(i).getUncritical()
					* getLevel1PropertyByIndex(i);
		}
		this.setLevel2PropertyByIndex(Level2Property.UNCRITICAL, uncritical
				+ getLevel2PropertyByIndex(Level2Property.UNCRITICAL));

		// 暴击伤害
		int criticalDamage = 0;
		for (int i = 0; i < convertMap.size(); i++) {
			criticalDamage += convertMap.get(i).getCriticalDamage()
					* getLevel1PropertyByIndex(i);
		}
		this.setLevel2PropertyByIndex(
				Level2Property.CRITICAL_DAMAGE,
				criticalDamage
						+ getLevel2PropertyByIndex(Level2Property.CRITICAL_DAMAGE));

		// 韧性伤害
		int unCriticalDamage = 0;
		for (int i = 0; i < convertMap.size(); i++) {
			unCriticalDamage += convertMap.get(i).getUncriticalDamage()
					* getLevel1PropertyByIndex(i);
		}
		this.setLevel2PropertyByIndex(
				Level2Property.UNCRITICAL_DAMAGE,
				unCriticalDamage
						+ getLevel2PropertyByIndex(Level2Property.UNCRITICAL_DAMAGE));

		// 格挡
		int parry = 0;
		for (int i = 0; i < convertMap.size(); i++) {
			parry += convertMap.get(i).getParry() * getLevel1PropertyByIndex(i);
		}
		this.setLevel2PropertyByIndex(Level2Property.PARRY, parry
				+ getLevel2PropertyByIndex(Level2Property.PARRY));

		// 格挡伤害
		int parryDamage = 0;
		for (int i = 0; i < convertMap.size(); i++) {
			parryDamage += convertMap.get(i).getParryDamage()
					* getLevel1PropertyByIndex(i);
		}
		this.setLevel2PropertyByIndex(Level2Property.PARRY_DAMAGE, parryDamage
				+ getLevel2PropertyByIndex(Level2Property.PARRY_DAMAGE));

		// 破击
		int unParry = 0;
		for (int i = 0; i < convertMap.size(); i++) {
			unParry += convertMap.get(i).getUnparry()
					* getLevel1PropertyByIndex(i);
		}
		this.setLevel2PropertyByIndex(Level2Property.UNPARRY, unParry
				+ getLevel2PropertyByIndex(Level2Property.UNPARRY));

		// 格挡伤害
		int unParryDamage = 0;
		for (int i = 0; i < convertMap.size(); i++) {
			unParryDamage += convertMap.get(i).getUnparryDamage()
					* getLevel1PropertyByIndex(i);
		}
		this.setLevel2PropertyByIndex(
				Level2Property.UNPARRY_DAMAGE,
				unParryDamage
						+ getLevel2PropertyByIndex(Level2Property.UNPARRY_DAMAGE));

		// 先攻
		int firstAttack = 0;
		for (int i = 0; i < convertMap.size(); i++) {
			firstAttack += convertMap.get(i).getFirstAttack()
					/ SharedConstants.PROPERTY_DIVISOR
					* getLevel1PropertyByIndex(i);
		}
		this.setLevel2PropertyByIndex(Level2Property.FIRST_ATTACK, firstAttack
				+ getLevel2PropertyByIndex(Level2Property.FIRST_ATTACK));

		// 火焰
		int power = getLevel1PropertyByIndex(Level1Property.FIRE);
		HumanGemPropTemplate gemPropTemplate_power = GameServerAssist.getTemplateService()
				.get(power, HumanGemPropTemplate.class);
		if (gemPropTemplate_power != null) {
			this.setLevel2PropertyByIndex(
					Level2Property.RED_INIT_VALUE,
					gemPropTemplate_power.getInitValue()
							+ getLevel2PropertyByIndex(Level2Property.RED_INIT_VALUE));
			this.setLevel2PropertyByIndex(Level2Property.RED_MAX,
					gemPropTemplate_power.getMaxValue()
							+ getLevel2PropertyByIndex(Level2Property.RED_MAX));
			this.setLevel2PropertyByIndex(
					Level2Property.RED_ELIMINATE_BONUS,
					gemPropTemplate_power.getEliminateBonus()
							+ getLevel2PropertyByIndex(Level2Property.RED_ELIMINATE_BONUS));
		}
		// 冰霜
		int agile = getLevel1PropertyByIndex(Level1Property.ICE);
		HumanGemPropTemplate gemPropTemplate_agile = GameServerAssist.getTemplateService()
				.get(agile, HumanGemPropTemplate.class);
		if (gemPropTemplate_agile != null) {
			this.setLevel2PropertyByIndex(
					Level2Property.BLUE_INIT_VALUE,
					gemPropTemplate_agile.getInitValue()
							+ getLevel2PropertyByIndex(Level2Property.BLUE_INIT_VALUE));
			this.setLevel2PropertyByIndex(Level2Property.BLUE_MAX,
					gemPropTemplate_agile.getMaxValue()
							+ getLevel2PropertyByIndex(Level2Property.BLUE_MAX));
			this.setLevel2PropertyByIndex(
					Level2Property.BLUE_ELIMINATE_BONUS,
					gemPropTemplate_agile.getEliminateBonus()
							+ getLevel2PropertyByIndex(Level2Property.BLUE_ELIMINATE_BONUS));
		}
		// 光明
		int stamina = getLevel1PropertyByIndex(Level1Property.LIGHT);
		HumanGemPropTemplate gemPropTemplate_stamina = GameServerAssist.getTemplateService()
				.get(stamina, HumanGemPropTemplate.class);
		if (gemPropTemplate_stamina != null) {
			this.setLevel2PropertyByIndex(
					Level2Property.YELLOW_INIT_VALUE,
					gemPropTemplate_stamina.getInitValue()
							+ getLevel2PropertyByIndex(Level2Property.YELLOW_INIT_VALUE));
			this.setLevel2PropertyByIndex(
					Level2Property.YELLOW_MAX,
					gemPropTemplate_stamina.getMaxValue()
							+ getLevel2PropertyByIndex(Level2Property.YELLOW_MAX));
			this.setLevel2PropertyByIndex(
					Level2Property.YELLOW_ELIMINATE_BONUS,
					gemPropTemplate_stamina.getEliminateBonus()
							+ getLevel2PropertyByIndex(Level2Property.YELLOW_ELIMINATE_BONUS));
		}
		// 暗影
		int intelligence = getLevel1PropertyByIndex(Level1Property.SHADOW);
		HumanGemPropTemplate gemPropTemplate_intelligence = GameServerAssist.getTemplateService()
				.get(intelligence, HumanGemPropTemplate.class);
		if (gemPropTemplate_intelligence != null) {
			this.setLevel2PropertyByIndex(
					Level2Property.PURPLE_INIT_VALUE,
					gemPropTemplate_intelligence.getInitValue()
							+ getLevel2PropertyByIndex(Level2Property.PURPLE_INIT_VALUE));
			this.setLevel2PropertyByIndex(
					Level2Property.PURPLE_MAX,
					gemPropTemplate_intelligence.getMaxValue()
							+ getLevel2PropertyByIndex(Level2Property.PURPLE_MAX));
			this.setLevel2PropertyByIndex(
					Level2Property.PURPLE_ELIMINATE_BONUS,
					gemPropTemplate_intelligence.getEliminateBonus()
							+ getLevel2PropertyByIndex(Level2Property.PURPLE_ELIMINATE_BONUS));
		}
		// 自然
		int spirit = getLevel1PropertyByIndex(Level1Property.NATURE);
		HumanGemPropTemplate gemPropTemplate_spirit = GameServerAssist.getTemplateService()
				.get(spirit, HumanGemPropTemplate.class);
		if (gemPropTemplate_spirit != null) {
			this.setLevel2PropertyByIndex(
					Level2Property.GREEN_INIT_VALUE,
					gemPropTemplate_spirit.getInitValue()
							+ getLevel2PropertyByIndex(Level2Property.GREEN_INIT_VALUE));
			this.setLevel2PropertyByIndex(
					Level2Property.GREEN_MAX,
					gemPropTemplate_spirit.getMaxValue()
							+ getLevel2PropertyByIndex(Level2Property.GREEN_MAX));
			this.setLevel2PropertyByIndex(
					Level2Property.GREEN_ELIMINATE_BONUS,
					gemPropTemplate_spirit.getEliminateBonus()
							+ getLevel2PropertyByIndex(Level2Property.GREEN_ELIMINATE_BONUS));
		}

	}

	@Override
	protected void calculateLevel1Properties() {
		for (int index = Level1Property._BEGIN; index <= Level1Property._END; index++) {
			calculateLevel1FinalPropertyByIndex(index);
		}

	}

	@Override
	public void heartBeat() {
		// 重新计算属性
		if (this.needRecalculate) {
			this.recalculateAllProperties();
		}
		snapChangedProperty(true);
	}

}
