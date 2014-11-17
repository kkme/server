package com.hifun.soul.gameserver.battle.counter;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.gameserver.battle.Battle;
import com.hifun.soul.gameserver.battle.IBattleUnit;
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
 * 战斗回合计数器;
 * 
 * @author crazyjohn
 * 
 */
public class BattleRoundCounterTest {
	private IBattleUnit a;
	private IBattleUnit b;
	private IBattleRoundCounter counter;

	@Before
	public void setUp() throws Exception {
		a = new MockBattleUnit();
		b = new MockBattleUnit();
		//counter = new BattleRoundCounter();
	}

	@Test
	public void testRecord() {
		// a - b
		counter.recordActionUnit(a);
		Assert.assertEquals(0, counter.getCurrentRound());
		counter.recordActionUnit(b);
		Assert.assertEquals(1, counter.getCurrentRound());
		// a
		counter.recordActionUnit(a);
		Assert.assertEquals(1, counter.getCurrentRound());
		// jump b
		counter.recoredCanNotAction(b);
		Assert.assertEquals(2, counter.getCurrentRound());
	}

	private static class MockBattleUnit implements IBattleUnit {

		@Override
		public List<MagicSlotInfo> getAllMagicSlots() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<ISkill> getCarriedSkills() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IBattleContext getBattleContext() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void notifyAction() {
			// TODO Auto-generated method stub

		}

		@Override
		public IBattleContext buildBattleContext(Battle aBattle) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void enterBattleState() {
			// TODO Auto-generated method stub

		}

		@Override
		public void exitBattleState() {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean isCurrentActionFinished() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void finishCurrentAction() {
			// TODO Auto-generated method stub

		}

		@Override
		public void resetFinishActionState() {
			// TODO Auto-generated method stub

		}

		

		@Override
		public boolean isDead() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void onExitBattle() {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean isInBattleState() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public ISkill getComboAttackSkill() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ISkill getNormalAttackSkill() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getUnitGuid() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public MagicChange updateMagicSlots(List<ChessBoardSnap> snaps) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getUnitModelId() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void deductHp(int changeHp) {
			// TODO Auto-generated method stub

		}

		@Override
		public void addMagicEnergy(EnergyType energyType, int value) {
			// TODO Auto-generated method stub

		}

		@Override
		public void reduceMagicEnergy(EnergyType energyType, int value) {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean canAction() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean forbidMagic() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean hasEnoughMagicToUseSuchSkill(ISkill skill) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public MagicChange getCurrentMagicSnap() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getUnitName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void sendMessage(IMessage message) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public int getLevel() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public RoleType getRoleType() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Occupation getOccupation() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void onNormalActionInvalid(int row1, int col1, int row2, int col2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public int getBattleBgId() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void setBattleBgId(int bgId) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public RolePropertyManager<?> getPropertyManager() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getUnitHeadId() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getDefaultActionId() {
			// TODO Auto-generated method stub
			return 0;
		}

	}

}
