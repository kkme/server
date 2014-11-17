package com.hifun.soul.gameserver.skill.msg.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.bag.manager.HumanBagManager;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.Item;
import com.hifun.soul.gameserver.item.service.ItemTemplateManager;
import com.hifun.soul.gameserver.item.template.ItemTemplate;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.skill.ISkill;
import com.hifun.soul.gameserver.skill.SkillStateType;
import com.hifun.soul.gameserver.skill.msg.CGStudySkill;
import com.hifun.soul.gameserver.skill.template.SkillScrollTemplate;
import com.hifun.soul.gameserver.skill.template.SkillTemplateManager;

@Component
public class CGStudySkillHandler implements
		IMessageHandlerWithType<CGStudySkill> {
	@Autowired
	private SkillTemplateManager skillTemplateManager;
	@Autowired
	private ItemTemplateManager itemTemplateManager;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_STUDY_SKILL;
	}

	@Override
	public void execute(CGStudySkill message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		// 判断技能id是否存在
		SkillScrollTemplate skillScrollTemplate = skillTemplateManager.getSkillScrollTemplateBySkillId(message.getSkillId());
		if(skillScrollTemplate == null){
			human.sendErrorMessage(LangConstants.SKILL_CAN_NOT_STUDY_NO_TEMPLATE);
			return;
		}
		ISkill skill = human.getSkillManager().getSkills().get(message.getSkillId());
		if(skill != null
				&& skill.getSkillState() == SkillStateType.RESETED.getIndex()){
			// 学习技能
			human.getSkillManager().studySkill(message.getSkillId(), null);
			return;
		}
		HumanBagManager bagManager = human.getBagManager();
		// 判断是否需要技能卷轴
		if(skillScrollTemplate.getNeedSkillScrollId() <= 0){
			human.getSkillManager().studySkill(message.getSkillId(), null);
		}
		else{
			int count = bagManager.getItemCount(skillScrollTemplate.getNeedSkillScrollId());
			if(count <= 0){
				ItemTemplate itemTemplate = itemTemplateManager.getItemTemplate(skillScrollTemplate.getNeedSkillScrollId());
				if(itemTemplate != null){
					human.sendWarningMessage(LangConstants.SKILL_CAN_NOT_STUDY_ONE, itemTemplate.getName());
				}
				return;
			}
			// 从背包拿取卷轴
			List<Item> items = bagManager.getItemsFromMainBag(skillScrollTemplate.getNeedSkillScrollId(), 1);
			if(items.size() <= 0 || items.get(0) == null){
				human.sendWarningMessage(LangConstants.SKILL_CAN_NOT_STUDY_ONE, items.get(0).getName());
				return;
			}
			// 学习技能
			human.getSkillManager().studySkill(message.getSkillId(), items.get(0));
		}
	}

}
