package com.hifun.soul.gameserver.magic.msg.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.battle.gem.EnergyType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.guide.GuideType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.magic.model.MagicInfo;
import com.hifun.soul.gameserver.magic.msg.CGOpenAttachMagicPanel;
import com.hifun.soul.gameserver.magic.msg.GCOpenAttachMagicPanel;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.role.properties.Level2Property;
import com.hifun.soul.gameserver.role.properties.PropertyType;
import com.hifun.soul.gameserver.skill.template.SkillTemplateManager;

/**
 * 請求打開附魔面板;
 * 
 * @author crazyjohn
 * 
 */
@Component
public class CGOpenAttachMagicPanelHandler implements
		IMessageHandlerWithType<CGOpenAttachMagicPanel> {
	@Autowired
	private SkillTemplateManager skillTemplateManager;

	@Override
	public short getMessageType() {
		return MessageType.CG_OPEN_ATTACH_MAGIC_PANEL;
	}

	@Override
	public void execute(CGOpenAttachMagicPanel message) {
		Player player = message.getPlayer();
		if (player == null) {
			return;
		}
		final Human human = player.getHuman();
		if (human == null) {
			return;
		}
		// 判断功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.ATTACH_MAGIC, true)) {
			return;
		}
		// 发送附魔数据
		GCOpenAttachMagicPanel attachMessage = new GCOpenAttachMagicPanel();
		attachMessage.setChangeTypeCrystalCost(GameServerAssist
				.getGameConstants().getChangeSkillDevelopCostCrystal());
		attachMessage.setCurrentMagicList(getCurrentMagicList(human));
		attachMessage.setSkillDevelopList(skillTemplateManager
				.getSkillDevelopInfos(human.getOccupation().getIndex()));
		attachMessage.setSkillDevelopType(human.getskillDevelopType()
				.getIndex());
		human.sendMessage(attachMessage);

		// 新手引导
		human.getHumanGuideManager().showGuide(
				GuideType.OPEN_ATTACH_MAGIC_PANEL.getIndex());
	}

	/**
	 * 获取当前魔法列表;
	 * 
	 * @return
	 */
	private MagicInfo[] getCurrentMagicList(Human human) {
		List<MagicInfo> result = new ArrayList<MagicInfo>();
		// 红
		MagicInfo redSlot = new MagicInfo();
		redSlot.setEnergyType(EnergyType.RED.getIndex());
		redSlot.setMaxValue(human.getPropertyManager()
				.getIntPropertySet(PropertyType.LEVEL2_PROPERTY)
				.getPropertyValue(Level2Property.RED_MAX));
		redSlot.setInitValue(human.getPropertyManager()
				.getIntPropertySet(PropertyType.LEVEL2_PROPERTY)
				.getPropertyValue(Level2Property.RED_INIT_VALUE));
		redSlot.setAddValue(human.getPropertyManager()
				.getIntPropertySet(PropertyType.LEVEL2_PROPERTY)
				.getPropertyValue(Level2Property.RED_ELIMINATE_BONUS));
		// 黄
		MagicInfo yellowSlot = new MagicInfo();
		yellowSlot.setEnergyType(EnergyType.YELLOW.getIndex());
		yellowSlot.setMaxValue(human.getPropertyManager()
				.getIntPropertySet(PropertyType.LEVEL2_PROPERTY)
				.getPropertyValue(Level2Property.YELLOW_MAX));
		yellowSlot.setInitValue(human.getPropertyManager()
				.getIntPropertySet(PropertyType.LEVEL2_PROPERTY)
				.getPropertyValue(Level2Property.YELLOW_INIT_VALUE));
		yellowSlot.setAddValue(human.getPropertyManager()
				.getIntPropertySet(PropertyType.LEVEL2_PROPERTY)
				.getPropertyValue(Level2Property.YELLOW_ELIMINATE_BONUS));
		// 蓝
		MagicInfo blueSlot = new MagicInfo();
		blueSlot.setEnergyType(EnergyType.BLUE.getIndex());
		blueSlot.setMaxValue(human.getPropertyManager()
				.getIntPropertySet(PropertyType.LEVEL2_PROPERTY)
				.getPropertyValue(Level2Property.BLUE_MAX));
		blueSlot.setInitValue(human.getPropertyManager()
				.getIntPropertySet(PropertyType.LEVEL2_PROPERTY)
				.getPropertyValue(Level2Property.BLUE_INIT_VALUE));
		blueSlot.setAddValue(human.getPropertyManager()
				.getIntPropertySet(PropertyType.LEVEL2_PROPERTY)
				.getPropertyValue(Level2Property.BLUE_ELIMINATE_BONUS));
		// 绿
		MagicInfo greenSlot = new MagicInfo();
		greenSlot.setEnergyType(EnergyType.GREEN.getIndex());
		greenSlot.setMaxValue(human.getPropertyManager()
				.getIntPropertySet(PropertyType.LEVEL2_PROPERTY)
				.getPropertyValue(Level2Property.GREEN_MAX));
		greenSlot.setInitValue(human.getPropertyManager()
				.getIntPropertySet(PropertyType.LEVEL2_PROPERTY)
				.getPropertyValue(Level2Property.GREEN_INIT_VALUE));
		greenSlot.setAddValue(human.getPropertyManager()
				.getIntPropertySet(PropertyType.LEVEL2_PROPERTY)
				.getPropertyValue(Level2Property.GREEN_ELIMINATE_BONUS));
		// 紫
		MagicInfo purpleSlot = new MagicInfo();
		purpleSlot.setEnergyType(EnergyType.PURPLE.getIndex());
		purpleSlot.setMaxValue(human.getPropertyManager()
				.getIntPropertySet(PropertyType.LEVEL2_PROPERTY)
				.getPropertyValue(Level2Property.PURPLE_MAX));
		purpleSlot.setInitValue(human.getPropertyManager()
				.getIntPropertySet(PropertyType.LEVEL2_PROPERTY)
				.getPropertyValue(Level2Property.PURPLE_INIT_VALUE));
		purpleSlot.setAddValue(human.getPropertyManager()
				.getIntPropertySet(PropertyType.LEVEL2_PROPERTY)
				.getPropertyValue(Level2Property.PURPLE_ELIMINATE_BONUS));
		result.add(redSlot);
		result.add(yellowSlot);
		result.add(blueSlot);
		result.add(greenSlot);
		result.add(purpleSlot);
		return result.toArray(new MagicInfo[0]);
	}
}
