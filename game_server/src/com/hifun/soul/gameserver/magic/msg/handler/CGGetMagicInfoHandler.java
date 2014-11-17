package com.hifun.soul.gameserver.magic.msg.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.battle.gem.EnergyType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.magic.model.MagicInfo;
import com.hifun.soul.gameserver.magic.msg.CGGetMagicInfo;
import com.hifun.soul.gameserver.magic.msg.GCGetMagicInfo;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.skill.SkillDevelopType;
import com.hifun.soul.gameserver.skill.template.SkillOccupationTemplate;

/**
 * 请求获取魔法信息;
 * 
 * @author crazyjohn
 * 
 */
@Component
public class CGGetMagicInfoHandler implements
		IMessageHandlerWithType<CGGetMagicInfo> {

	@Override
	public short getMessageType() {
		return MessageType.CG_GET_MAGIC_INFO;
	}

	@Override
	public void execute(CGGetMagicInfo message) {
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
		// 获取对应的魔法属性值
		GCGetMagicInfo magicMessage = new GCGetMagicInfo();
		magicMessage.setCurrentMagicList(getMagicInfoByType(human,
				message.getSkillDevelopType()));
		magicMessage.setSkillDevelopType(message.getSkillDevelopType());
		human.sendMessage(magicMessage);
	}

	/**
	 * 根据职业系类型获取魔法信息;
	 * 
	 * @param human
	 * @param skillDevelopType
	 * @return
	 */
	private MagicInfo[] getMagicInfoByType(Human human, int skillDevelopType) {
		List<MagicInfo> result = new ArrayList<MagicInfo>();
		// ===== 魔法属性 =====
		// 根据职业和当前技能系取出想要模版
		SkillOccupationTemplate magicPropTemplate = GameServerAssist
				.getSkillTemplateManager().getLevelMagicPropTemplate(
						human.getLevel(), human.getOccupation(),
						SkillDevelopType.typeOf(skillDevelopType));
		// 红魔信息
		MagicInfo redInfo = new MagicInfo();
		redInfo.setAddValue(magicPropTemplate.getRedEliminateBonus());
		redInfo.setEnergyType(EnergyType.RED.getIndex());
		redInfo.setInitValue(magicPropTemplate.getRedInit());
		redInfo.setMaxValue(magicPropTemplate.getRedMax());
		result.add(redInfo);
		// 黄魔信息
		MagicInfo yellowInfo = new MagicInfo();
		yellowInfo.setAddValue(magicPropTemplate.getYellowEliminateBonus());
		yellowInfo.setEnergyType(EnergyType.YELLOW.getIndex());
		yellowInfo.setInitValue(magicPropTemplate.getYellowInit());
		yellowInfo.setMaxValue(magicPropTemplate.getYellowMax());
		result.add(yellowInfo);
		// 蓝魔信息
		MagicInfo blueInfo = new MagicInfo();
		blueInfo.setAddValue(magicPropTemplate.getBlueEliminateBonus());
		blueInfo.setEnergyType(EnergyType.BLUE.getIndex());
		blueInfo.setInitValue(magicPropTemplate.getBlueInit());
		blueInfo.setMaxValue(magicPropTemplate.getBlueMax());
		result.add(blueInfo);
		// 绿魔信息
		MagicInfo greenInfo = new MagicInfo();
		greenInfo.setAddValue(magicPropTemplate.getGreenEliminateBonus());
		greenInfo.setEnergyType(EnergyType.GREEN.getIndex());
		greenInfo.setInitValue(magicPropTemplate.getGreenInit());
		greenInfo.setMaxValue(magicPropTemplate.getGreenMax());
		result.add(greenInfo);
		// 紫魔信息
		MagicInfo purpleInfo = new MagicInfo();
		purpleInfo.setAddValue(magicPropTemplate.getPurpleEliminateBonus());
		purpleInfo.setEnergyType(EnergyType.PURPLE.getIndex());
		purpleInfo.setInitValue(magicPropTemplate.getPurpleInit());
		purpleInfo.setMaxValue(magicPropTemplate.getPurpleMax());
		result.add(purpleInfo);

		return result.toArray(new MagicInfo[0]);
	}

}
