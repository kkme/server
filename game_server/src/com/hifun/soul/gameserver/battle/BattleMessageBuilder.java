package com.hifun.soul.gameserver.battle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.hifun.soul.gameserver.battle.gem.ColNewGems;
import com.hifun.soul.gameserver.battle.gem.GemChessBoard;
import com.hifun.soul.gameserver.battle.gem.MagicSlotInfo;
import com.hifun.soul.gameserver.battle.msg.GCStartBattleInfo;
import com.hifun.soul.gameserver.guide.manager.HumanGuideManager;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.skill.ISkill;
import com.hifun.soul.gameserver.skill.template.SkillInfo;

/**
 * 战斗消息构建器;
 * 
 * @author crazyjohn
 * 
 */
public class BattleMessageBuilder {
	// 战斗单元
	protected IBattleUnit oneGuy;
	protected IBattleUnit otherGuy;
	protected GemChessBoard board;

	public BattleMessageBuilder(IBattleUnit oneGuy, IBattleUnit otherGuy,
			GemChessBoard board) {
		this.oneGuy = oneGuy;
		this.otherGuy = otherGuy;
		this.board = board;
	}

	public GCStartBattleInfo buildBattleInfoMessage(Human challenger) {
		GCStartBattleInfo battleInfoMsg = new GCStartBattleInfo();
		// 战斗背景
		battleInfoMsg.setBattleBgId(challenger.getBattleBgId());
		// 第一个单元
		// 设置血量
		battleInfoMsg.setGuid1(this.oneGuy.getUnitGuid());
		battleInfoMsg.setBattleUnit1Hp(this.oneGuy.getBattleContext()
				.getBattleProperty().getHp());
		battleInfoMsg.setLevel1(this.oneGuy.getLevel());
		battleInfoMsg.setName1(this.oneGuy.getUnitName());
		battleInfoMsg.setResourceId1(this.oneGuy.getUnitModelId());
		battleInfoMsg.setHeadId1(this.oneGuy.getUnitHeadId());
		battleInfoMsg.setRoleType1(this.oneGuy.getRoleType().getIndex());
		battleInfoMsg.setOccupation1(this.oneGuy.getOccupation().getIndex());
		battleInfoMsg.setFirstAttack1(this.oneGuy.getBattleContext()
				.getBattleProperty().getFirstAttack());
		// 设置技能
		List<SkillInfo> skills1 = new ArrayList<SkillInfo>();
		List<ISkill> carriedSkills1 = this.oneGuy.getCarriedSkills();
		List<Integer> unit1Buffs = new ArrayList<Integer>();
		for (ISkill skill : carriedSkills1) {
			SkillInfo skillInfo = skill.toSkillInfo();
			// 设置被携带;
			skillInfo.setIsCarried(true);
			skills1.add(skillInfo);
			unit1Buffs.addAll(skill.getTriggerBuffResourceList());
		}
		battleInfoMsg.setBattleUnit1Skills(skills1.toArray(new SkillInfo[0]));
		// 设置魔法槽
		List<MagicSlotInfo> slots1 = this.oneGuy.getAllMagicSlots();
		battleInfoMsg.setBattleUnit1MagicSlots(slots1
				.toArray(new MagicSlotInfo[0]));

		// 第二个单元
		battleInfoMsg.setGuid2(this.otherGuy.getUnitGuid());
		battleInfoMsg.setResourceId2(this.otherGuy.getUnitModelId());
		battleInfoMsg.setHeadId2(this.otherGuy.getUnitHeadId());
		// 设置血量
		battleInfoMsg.setBattleUnit2Hp(this.otherGuy.getBattleContext()
				.getBattleProperty().getHp());
		battleInfoMsg.setLevel2(this.otherGuy.getLevel());
		battleInfoMsg.setName2(this.otherGuy.getUnitName());
		battleInfoMsg.setRoleType2(this.otherGuy.getRoleType().getIndex());
		battleInfoMsg.setOccupation2(this.otherGuy.getOccupation().getIndex());
		battleInfoMsg.setFirstAttack2(this.otherGuy.getBattleContext()
				.getBattleProperty().getFirstAttack());
		// 设置技能
		List<SkillInfo> skills2 = new ArrayList<SkillInfo>();
		List<ISkill> carriedSkills2 = this.otherGuy.getCarriedSkills();
		List<Integer> unit2Buffs = new ArrayList<Integer>();
		for (ISkill skill : carriedSkills2) {
			SkillInfo skillInfo = skill.toSkillInfo();
			// 设置被携带;
			skillInfo.setIsCarried(true);
			skills2.add(skillInfo);
			unit2Buffs.addAll(skill.getTriggerBuffResourceList());
		}
		battleInfoMsg.setBattleUnit2Skills(skills2.toArray(new SkillInfo[0]));
		// 设置魔法槽
		List<MagicSlotInfo> slots2 = this.otherGuy.getAllMagicSlots();
		battleInfoMsg.setBattleUnit2MagicSlots(slots2
				.toArray(new MagicSlotInfo[0]));
		// buff信息
		Set<Integer> buffSet = new HashSet<Integer>();
		unit1Buffs.addAll(unit2Buffs);
		for (Integer each : unit1Buffs) {
			buffSet.add(each);
		}
		int[] buffArray = new int[buffSet.size()];
		Iterator<Integer> it = buffSet.iterator();
		int i = 0;
		while (it.hasNext()) {
			buffArray[i] = it.next();
			i++;
		}
		battleInfoMsg.setBattleBuffTypes(buffArray);
		// 棋盘信息
		List<ColNewGems> gemsList = this.board.getAllColGems();
		battleInfoMsg.setChessBoardCols(gemsList.toArray(new ColNewGems[0]));
		battleInfoMsg.setGuide(isNeedBattleGuide(challenger));
		battleInfoMsg.setDefaultSelfActionId(this.oneGuy.getDefaultActionId());
		battleInfoMsg.setDefaultOtherActionId(this.otherGuy.getDefaultActionId());
		return battleInfoMsg;
	}
	
	/**
	 * 战斗引导是否已经都做完
	 * @param challenger
	 * @return
	 */
	private boolean isNeedBattleGuide(Human challenger){
		HumanGuideManager guideManager = challenger.getHumanGuideManager();
		return guideManager.isFinishBattleGuide();
	}

}
