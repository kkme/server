package com.hifun.soul.gameserver.battle;

import java.util.List;

import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.gameserver.battle.context.IBattleContext;
import com.hifun.soul.gameserver.battle.gem.EnergyType;
import com.hifun.soul.gameserver.battle.gem.MagicSlotInfo;
import com.hifun.soul.gameserver.battle.msg.ChessBoardSnap;
import com.hifun.soul.gameserver.role.Occupation;
import com.hifun.soul.gameserver.role.RoleType;
import com.hifun.soul.gameserver.role.properties.manager.RolePropertyManager;
import com.hifun.soul.gameserver.skill.ISkill;
import com.hifun.soul.gameserver.skill.msg.MagicChange;

/**
 * 战斗单元接口;
 * 
 * @author crazyjohn
 * 
 */
public interface IBattleUnit {

	/**
	 * 战斗单元携带的魔法槽;
	 * 
	 * @return
	 */
	public List<MagicSlotInfo> getAllMagicSlots();

	/**
	 * 获取携带的所有技能;
	 * 
	 * @return
	 */
	public List<ISkill> getCarriedSkills();

	/**
	 * 获取战斗上下文;
	 * 
	 * @return
	 */
	public IBattleContext getBattleContext();

	/**
	 * 通知可以开始行动了;
	 */
	public void notifyAction();

	/**
	 * 构建战斗上下文;
	 * 
	 * @param aBattle
	 */
	public IBattleContext buildBattleContext(Battle aBattle);

	/**
	 * 进入战斗状态;
	 */
	public void enterBattleState();

	/**
	 * 退出战斗状态;
	 */
	public void exitBattleState();

	/**
	 * 当前执行的动作是否完成了;
	 * 
	 * @return true 表示完成了;
	 */
	public boolean isCurrentActionFinished();

	/**
	 * 完成当前的动作;
	 */
	public void finishCurrentAction();

	/**
	 * 重新设置行动标记;
	 */
	public void resetFinishActionState();

	/**
	 * 普通攻击非法的情况;<br>
	 * 根据策划规则可能回去扣血;
	 * 
	 * @param col2
	 * @param row2
	 * @param col1
	 * @param row1
	 */
	public void onNormalActionInvalid(int row1, int col1, int row2, int col2);

	/**
	 * 判断战斗单元是否已经挂了;
	 * 
	 * @return true表示挂了;
	 */
	public boolean isDead();

	/**
	 * 退出战斗的处理;
	 */
	public void onExitBattle();

	/**
	 * 是否在战斗状态;
	 * 
	 * @return
	 */
	public boolean isInBattleState();

	/**
	 * 获取战斗单元特有的超级必杀技;<br>
	 * 有连击的时候使用此技能;
	 * 
	 * @return
	 */
	public ISkill getComboAttackSkill();

	/**
	 * 获取战斗单元特有的基础攻击技能;
	 * 
	 * @return
	 */
	public ISkill getNormalAttackSkill();

	/**
	 * 获取战斗单元唯一标识;
	 * 
	 * @return
	 */
	public long getUnitGuid();

	public String getUnitName();

	/**
	 * 根据快照信息,获取魔法变化;
	 * 
	 * @param snaps
	 * @return
	 */
	public MagicChange updateMagicSlots(List<ChessBoardSnap> snaps);

	/**
	 * 获取战斗单元资源ID;
	 * 
	 * @return
	 */
	public int getUnitModelId();

	/**
	 * 获取战斗单元头像id;
	 * 
	 * @return
	 */
	public int getUnitHeadId();

	/**
	 * 扣除血量;
	 */
	public void deductHp(int changeHp);

	/**
	 * 添加能量;
	 * 
	 * @param energyType
	 * @param value
	 */
	public void addMagicEnergy(EnergyType energyType, int value);

	/**
	 * 减少能量;
	 * 
	 * @param energyType
	 * @param value
	 */
	public void reduceMagicEnergy(EnergyType energyType, int value);

	/**
	 * 是否可以行动;
	 * 
	 * @return true表示可以行动;
	 */
	public boolean canAction();

	/**
	 * 是否禁魔了;<br>
	 * 禁魔是不能释放技能的;<br>
	 * 
	 * @return true 表示禁魔了;
	 */
	public boolean forbidMagic();

	/**
	 * 是否有足够的魔法去使用技能;
	 * 
	 * @param skill
	 * @return
	 */
	public boolean hasEnoughMagicToUseSuchSkill(ISkill skill);

	/**
	 * 获取当前的魔法快照;
	 * 
	 * @return
	 */
	public MagicChange getCurrentMagicSnap();

	public void sendMessage(IMessage message);

	public int getLevel();

	public RoleType getRoleType();

	public Occupation getOccupation();

	public int getBattleBgId();

	public void setBattleBgId(int bgId);

	public RolePropertyManager<?> getPropertyManager();
	
	public int getDefaultActionId();
}
