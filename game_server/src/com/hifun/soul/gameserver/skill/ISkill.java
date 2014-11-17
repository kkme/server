package com.hifun.soul.gameserver.skill;

import java.util.List;

import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.counter.IRoundListener;
import com.hifun.soul.gameserver.battle.msg.ChessBoardSnap;
import com.hifun.soul.gameserver.skill.template.SkillInfo;
import com.hifun.soul.gameserver.skill.template.SkillScrollTemplate;
import com.hifun.soul.gameserver.skill.template.SkillTemplate;

/**
 * 技能接口;
 * 
 * @author crazyjohn
 * 
 */
public interface ISkill extends IRoundListener {

	/**
	 * 技能是否可以使用;
	 * 
	 * @return
	 */
	public boolean canUseSkill();

	/**
	 * 获取技能模版;
	 * 
	 * @return
	 */
	public SkillTemplate getSkillTemplate();

	/**
	 * 技能ID;
	 * 
	 * @return
	 */
	public int getSkillId();

	/**
	 * 技能名称;
	 * 
	 * @return
	 */
	public String getSkillName();

	/**
	 * 使用技能;
	 * 
	 * @param attacker
	 *            攻击者;
	 * @param target
	 *            目标;
	 * @param combo
	 *            连击;
	 * @param snaps
	 *            快照;
	 * @param selectedRow
	 *            选中的宝石的行号;
	 * @param selectedCol
	 *            选中宝石的列号;
	 * @return
	 */
	public boolean useSkill(IBattleUnit attacker, IBattleUnit target,
			int combo, List<ChessBoardSnap> snaps, int selectedRow,
			int selectedCol);

	/**
	 * 获取攻击类型;
	 * 
	 * @return
	 */
	public AttackType getAttackType();

	/**
	 * 转化出简单的技能信息;
	 * 
	 * @return
	 */
	public SkillInfo toSkillInfo();

	/**
	 * 技能释放是否需要选择宝石;
	 * 
	 * @return
	 */
	public boolean needSelectedGem();

	/**
	 * 使用此技能是否会导致当前回合结束;
	 * 
	 * @return true表示结束当前回合;
	 */
	public boolean currentActionOver();

	/**
	 * 技能是否冷却了;
	 * 
	 * @return true表示冷却了;
	 */
	public boolean isCooldown();



	/**
	 * 获取可能触发的buff效果类型;
	 * 
	 * @return 不会返回Null;
	 */
	public List<Integer> getTriggerBuffResourceList();

	public void setSlotIndex(int slotIndex);

	public int getSlotIndex();

	public ISkill copy();
	
	public int getSkillState();
	
	public void setSkillState(int skillState);
	
	public SkillScrollTemplate getSkillScrollTemplate();

	public int getCooldownRound();

}
