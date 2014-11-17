package com.hifun.soul.gameserver.role.properties.manager;

import java.util.HashMap;
import java.util.Map;

import com.hifun.soul.common.LogReasons.PropertyLogReason;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.gameserver.battle.property.BattleProperty;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.role.Role;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.HumanLongProperty;
import com.hifun.soul.gameserver.role.properties.IntPropertyCacheSet;
import com.hifun.soul.gameserver.role.properties.IntPropertySet;
import com.hifun.soul.gameserver.role.properties.Level1Property;
import com.hifun.soul.gameserver.role.properties.Level2Property;
import com.hifun.soul.gameserver.role.properties.PropertyType;
import com.hifun.soul.gameserver.role.properties.amend.AmendMethod;

/**
 * 角色属性管理器;
 * 
 * @author crazyjohn
 * 
 */
public abstract class RolePropertyManager<R extends Role<?>> {
	protected R role;
	protected Map<Integer, IntPropertyCacheSet> intPropertySets = new HashMap<Integer, IntPropertyCacheSet>();
	/** 人物裸身属性 */
	protected IntPropertyCacheSet[] initProperties;
	/** 长整形属性集 */
	protected HumanLongProperty longPropertySet;
	/** 加法运算属性数组 */
	protected IntPropertyCacheSet[] addProperties;
	/** 乘法运算属性数组 */
	protected IntPropertyCacheSet[] multipleProperties;
	/** 最终的属性存放 */
	protected IntPropertyCacheSet[] finalProperties;
	/** 是否需要重新计算属性 */
	protected boolean needRecalculate = false;

	public RolePropertyManager(R role) {
		this.role = role;
		// 人物裸身属性
		initProperties = new IntPropertyCacheSet[2];
		initProperties[0] = new Level1Property();
		initProperties[1] = new Level2Property();
		// 加法运算属性
		addProperties = new IntPropertyCacheSet[2];
		addProperties[0] = new Level1Property();
		addProperties[1] = new Level2Property();
		// 乘法运算属性
		multipleProperties = new IntPropertyCacheSet[2];
		multipleProperties[0] = new Level1Property();
		multipleProperties[1] = new Level2Property();
		// 最终属性
		finalProperties = new IntPropertyCacheSet[2];
		finalProperties[0] = new Level1Property();
		finalProperties[1] = new Level2Property();
		// 初始化属性集合
		intPropertySets.put(PropertyType.LEVEL1_PROPERTY, finalProperties[0]);
		intPropertySets.put(PropertyType.LEVEL2_PROPERTY, finalProperties[1]);
		intPropertySets.put(PropertyType.HUMAN_INT_PROPERTY,
				new HumanIntProperty());
		longPropertySet = new HumanLongProperty();
	}

	protected void setProperty(int key, long value) {
		// 先获取属性类型
		int properType = PropertyType.propertyKeyToPropertyType(key);
		int index = PropertyType.propertyKeyToPropertyIndex(key);
		if (properType == PropertyType.HUMAN_LONG_PROPERTY) {
			this.longPropertySet.setPropertyValue(index, value);
			return;
		}
		if (properType < 0 || properType > PropertyType.INT_PROPERTY_LENGTH) {
			return;
		}
		IntPropertySet propertySet = getIntPropertySet(properType);
		if (propertySet != null) {
			int newValue = (int) value;
			propertySet.setPropertyValue(index, newValue);
		}
	}

	public long getLongPropertyValue(int index) {
		return this.longPropertySet.getPropertyValue(index);
	}

	public void setLongPropertyValue(int index, long value) {
		this.longPropertySet.setPropertyValue(index, value);
	}

	public IntPropertyCacheSet getIntPropertySet(int propertyType) {
		return this.intPropertySets.get(propertyType);
	}

	/**
	 * 重新计算裸身属性;
	 */
	public abstract void recalculateInitProperties(R role);

	/**
	 * 设置角色二级裸身属性;
	 * 
	 * @param index
	 * @param value
	 */
	void setInitLevel2Property(int index, int value) {
		this.initProperties[1].setPropertyValue(index, value);
	}
	
	/**
	 * 获取二级裸身属性;
	 * @param index
	 * @return
	 */
	protected int getInitLevel2Property(int index) {
		return this.initProperties[1].getPropertyValue(index);
	}

	/**
	 * 获取角色整形属性;
	 * 
	 * @param index
	 * @return
	 */
	int getHumanIntProperty(int index) {
		return this.intPropertySets.get(PropertyType.HUMAN_INT_PROPERTY)
				.getPropertyValue(index);
	}

	/**
	 * 清空角色裸身属性;
	 */
	protected void clearInitProperties() {
		for (IntPropertyCacheSet each : this.initProperties) {
			each.clear();
		}
	}

	/**
	 * 清空一二级属性;
	 */
	protected void clearLevel1AndLevel2Properties() {
		this.finalProperties[0].clear();
		this.finalProperties[1].clear();
	}

	/**
	 * 设置角色一级裸身属性;
	 * 
	 * @param index
	 * @param value
	 */
	void setInitLevel1Property(int index, int value) {
		this.initProperties[0].setPropertyValue(index, value);
	}

	/**
	 * 重新计算所有属性;
	 */
	public void recalculateAllProperties() {
		// 清空最终属性
		clearFinalProperties();
		// 计算一级属性
		calculateLevel1Properties();
		// 计算一二级属性转化
		calculateLevel1ToLevel2Properties();
		// 计算二级属性
		calculateLevel2Properties();
		this.needRecalculate = false;
	}

	/**
	 * 一级属性到二级属性的转换;
	 */
	protected abstract void calculateLevel1ToLevel2Properties();

	/**
	 * 计算一级属性;
	 */
	protected abstract void calculateLevel1Properties();

	protected void calculateLevel2Properties() {
		for (int index = Level2Property._BEGIN; index <= Level2Property._END; index++) {
			calculateLevel2FinalPropertyByIndex(index);
		}

	}

	protected void setLevel2PropertyByIndex(int index, int value) {
		this.finalProperties[1].setPropertyValue(index, value);
	}

	protected int getLevel1PropertyByIndex(int index) {
		return this.finalProperties[0].getPropertyValue(index);
	}

	protected int getLevel2PropertyByIndex(int index) {
		return this.finalProperties[1].getPropertyValue(index);
	}

	protected void calculateLevel2FinalPropertyByIndex(int index) {
		finalProperties[1]
				.setPropertyValue(
						index,
						(int) (Math.floor((this.finalProperties[1]
								.getPropertyValue(index)
								+ this.initProperties[1]
										.getPropertyValue(index) + this.addProperties[1]
									.getPropertyValue(index))
								* (SharedConstants.PROPERTY_DIVISOR + this.multipleProperties[1]
										.getPropertyValue(index))
								/ SharedConstants.PROPERTY_DIVISOR)));
	}

	/**
	 * 计算一级属性的最终结果;
	 * 
	 * @param index
	 */
	protected void calculateLevel1FinalPropertyByIndex(int index) {
		finalProperties[0]
				.setPropertyValue(
						index,
						(int) (Math.floor((this.initProperties[0]
								.getPropertyValue(index) + this.addProperties[0]
								.getPropertyValue(index))
								* (SharedConstants.PROPERTY_DIVISOR + this.multipleProperties[0]
										.getPropertyValue(index))
								/ SharedConstants.PROPERTY_DIVISOR)));
	}

	protected void clearFinalProperties() {
		this.finalProperties[0].clear();
		this.finalProperties[1].clear();
	}

	/**
	 * 修改玩家属性计算，修改参与玩家属性计算的乘法和加法的一级与二级属性, 修改属性后将重新计算标志位置为true, 下一次心跳将重新计算
	 * 
	 * @param amendType
	 *            修正方式
	 * @param propId
	 *            属性ID
	 * @param value
	 *            <font color = 'red'>属性值，正值则为增加，负值为减少</font>
	 * @return 赋值成功返回true，失败返回false
	 * @exception ArrayIndexOutOfBoundsException
	 *                如果index<0 或者 index>=size时会抛出此异常
	 */
	public boolean amendProperty(Human human, AmendMethod amendType,
			int propId, int value, PropertyLogReason reason, String param) {
		boolean result = false;
		if(propId<=0){
			return result;
		}
		// 属性类型, 一级或者二级属性, 或者整形属性
		int properType = PropertyType.propertyKeyToPropertyType(propId);
		int index = PropertyType.propertyKeyToPropertyIndex(propId);
		if (properType < 0 || properType > PropertyType.INT_PROPERTY_LENGTH) {
			return result;
		}
		// 加法
		if (amendType == AmendMethod.ADD) {
			this.needRecalculate = true;
			result = addAmendProperty(human, properType, index, value);
		}
		if (amendType == AmendMethod.MULIPLY) {
			this.needRecalculate = true;
			result = multipleAmendProperty(human, properType, index, value);
		}
		// 发送属性变更日志
		if(result && reason != null){
			GameServerAssist.getLogService().sendPropertyLog(human, reason, param, properType,
						String.valueOf(value));
		}
		return result;
	}

	/**
	 * 乘法加成属性;
	 * 
	 * @param properType
	 * @param index
	 * @param value
	 * @return
	 */
	private boolean multipleAmendProperty(Human human, int properType,
			int index, int value) {
		this.multipleProperties[properType - 1].setPropertyValue(
				index,
				value
						+ this.multipleProperties[properType - 1]
								.getPropertyValue(index));
		this.needRecalculate = true;
		return true;
	}

	/**
	 * 加法加成属性;
	 * 
	 * @param properType
	 * @param index
	 * @param value
	 * @return
	 */
	private boolean addAmendProperty(Human human, int properType, int index,
			int value) {
		this.addProperties[properType - 1].setPropertyValue(index, value
				+ this.addProperties[properType - 1].getPropertyValue(index));
		this.needRecalculate = true;
		return true;
	}

	/**
	 * 构建角色战斗属性;
	 * 
	 * 
	 * @return
	 */
	public BattleProperty buildBattleProperty() {
		BattleProperty battleProperty = new BattleProperty();
		for (int i = Level2Property._BEGIN; i <= Level2Property._END; i++) {
			battleProperty.setBattleInitPropertyByIndex(i,
					this.finalProperties[1].getPropertyValue(i));
		}
		// 设置血量
		battleProperty.setHp(this.finalProperties[1]
				.getPropertyValue(Level2Property.MAX_HP));
		// 设置等级
		battleProperty.setLevel(this.getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
				HumanIntProperty.LEVEL));

		battleProperty.recalculate();
		return battleProperty;
	}
}
