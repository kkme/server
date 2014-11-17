package com.hifun.soul.gameserver.battle.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务器下发开始战斗面板的信息
 *
 * @author SevenSoul
 */
@Component
public class GCStartBattleInfo extends GCMessage{
	
	/** 战斗背景ID */
	private int battleBgId;
	/** 玩家1唯一标示 */
	private long guid1;
	/** 玩家1等级 */
	private int level1;
	/** 玩家1角色类型 */
	private int roleType1;
	/** 玩家1职业类型 */
	private int occupation1;
	/** 玩家1名称 */
	private String name1;
	/** 玩家1资源ID（模型id） */
	private int resourceId1;
	/** 玩家1头像（头像id） */
	private int headId1;
	/** 战斗单元1携带的技能 */
	private com.hifun.soul.gameserver.skill.template.SkillInfo[] battleUnit1Skills;
	/** 战斗单元1的魔法槽信息 */
	private com.hifun.soul.gameserver.battle.gem.MagicSlotInfo[] battleUnit1MagicSlots;
	/** 战斗单元1血量 */
	private int battleUnit1Hp;
	/** 战斗单元1先攻 */
	private int firstAttack1;
	/** 玩家2唯一标示 */
	private long guid2;
	/** 玩家2等级 */
	private int level2;
	/** 玩家2角色类型  */
	private int roleType2;
	/** 玩家2职业类型 */
	private int occupation2;
	/** 玩家2名称 */
	private String name2;
	/** 玩家2资源ID */
	private int resourceId2;
	/** 玩家2头像（头像id） */
	private int headId2;
	/** 战斗单元2携带的技能 */
	private com.hifun.soul.gameserver.skill.template.SkillInfo[] battleUnit2Skills;
	/** 战斗单元2的魔法槽信息 */
	private com.hifun.soul.gameserver.battle.gem.MagicSlotInfo[] battleUnit2MagicSlots;
	/** 战斗单元2血量 */
	private int battleUnit2Hp;
	/** 战斗单元2先攻 */
	private int firstAttack2;
	/** 本次战斗需要加载的buff类型集合 */
	private int[] battleBuffTypes;
	/** 棋盘信息, 按照列发送 */
	private com.hifun.soul.gameserver.battle.gem.ColNewGems[] chessBoardCols;
	/** 是否需要加载引导资源 */
	private boolean guide;
	/** 自己默认的动作ID */
	private int defaultSelfActionId;
	/** 被攻击方的默认动作ID */
	private int defaultOtherActionId;

	public GCStartBattleInfo (){
	}
	
	public GCStartBattleInfo (
			int battleBgId,
			long guid1,
			int level1,
			int roleType1,
			int occupation1,
			String name1,
			int resourceId1,
			int headId1,
			com.hifun.soul.gameserver.skill.template.SkillInfo[] battleUnit1Skills,
			com.hifun.soul.gameserver.battle.gem.MagicSlotInfo[] battleUnit1MagicSlots,
			int battleUnit1Hp,
			int firstAttack1,
			long guid2,
			int level2,
			int roleType2,
			int occupation2,
			String name2,
			int resourceId2,
			int headId2,
			com.hifun.soul.gameserver.skill.template.SkillInfo[] battleUnit2Skills,
			com.hifun.soul.gameserver.battle.gem.MagicSlotInfo[] battleUnit2MagicSlots,
			int battleUnit2Hp,
			int firstAttack2,
			int[] battleBuffTypes,
			com.hifun.soul.gameserver.battle.gem.ColNewGems[] chessBoardCols,
			boolean guide,
			int defaultSelfActionId,
			int defaultOtherActionId ){
			this.battleBgId = battleBgId;
			this.guid1 = guid1;
			this.level1 = level1;
			this.roleType1 = roleType1;
			this.occupation1 = occupation1;
			this.name1 = name1;
			this.resourceId1 = resourceId1;
			this.headId1 = headId1;
			this.battleUnit1Skills = battleUnit1Skills;
			this.battleUnit1MagicSlots = battleUnit1MagicSlots;
			this.battleUnit1Hp = battleUnit1Hp;
			this.firstAttack1 = firstAttack1;
			this.guid2 = guid2;
			this.level2 = level2;
			this.roleType2 = roleType2;
			this.occupation2 = occupation2;
			this.name2 = name2;
			this.resourceId2 = resourceId2;
			this.headId2 = headId2;
			this.battleUnit2Skills = battleUnit2Skills;
			this.battleUnit2MagicSlots = battleUnit2MagicSlots;
			this.battleUnit2Hp = battleUnit2Hp;
			this.firstAttack2 = firstAttack2;
			this.battleBuffTypes = battleBuffTypes;
			this.chessBoardCols = chessBoardCols;
			this.guide = guide;
			this.defaultSelfActionId = defaultSelfActionId;
			this.defaultOtherActionId = defaultOtherActionId;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		battleBgId = readInteger();
		guid1 = readLong();
		level1 = readInteger();
		roleType1 = readInteger();
		occupation1 = readInteger();
		name1 = readString();
		resourceId1 = readInteger();
		headId1 = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		battleUnit1Skills = new com.hifun.soul.gameserver.skill.template.SkillInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.skill.template.SkillInfo objbattleUnit1Skills = new com.hifun.soul.gameserver.skill.template.SkillInfo();
			battleUnit1Skills[i] = objbattleUnit1Skills;
					objbattleUnit1Skills.setSkillId(readInteger());
							objbattleUnit1Skills.setSkillName(readString());
							objbattleUnit1Skills.setSkillDesc(readString());
							objbattleUnit1Skills.setSkillActionId(readInteger());
							objbattleUnit1Skills.setFlyEffectId(readInteger());
							objbattleUnit1Skills.setBeHitedEffectId(readInteger());
							objbattleUnit1Skills.setEnemyBeHitedEffectId(readInteger());
							objbattleUnit1Skills.setCooldownRound(readInteger());
							objbattleUnit1Skills.setAttackType(readInteger());
							objbattleUnit1Skills.setSkillType(readInteger());
							objbattleUnit1Skills.setNeedSelectGem(readBoolean());
							objbattleUnit1Skills.setRangeType(readInteger());
							objbattleUnit1Skills.setUseRoundOver(readBoolean());
							objbattleUnit1Skills.setRedCost(readInteger());
							objbattleUnit1Skills.setYellowCost(readInteger());
							objbattleUnit1Skills.setGreenCost(readInteger());
							objbattleUnit1Skills.setBlueCost(readInteger());
							objbattleUnit1Skills.setPurpleCost(readInteger());
							objbattleUnit1Skills.setIsCarried(readBoolean());
							objbattleUnit1Skills.setSkillSound(readInteger());
							objbattleUnit1Skills.setSkillIcon(readInteger());
							objbattleUnit1Skills.setSlotIndex(readInteger());
							objbattleUnit1Skills.setSkillState(readInteger());
							objbattleUnit1Skills.setSkillDevelopType(readInteger());
							objbattleUnit1Skills.setNeedLevel(readInteger());
							objbattleUnit1Skills.setNeedSkillPoints(readInteger());
							objbattleUnit1Skills.setSkillScrollName(readString());
							objbattleUnit1Skills.setIsNeedSkillScroll(readBoolean());
							objbattleUnit1Skills.setPreSkillName(readString());
							objbattleUnit1Skills.setPreSkillIsOpen(readBoolean());
							objbattleUnit1Skills.setHasSkillScroll(readBoolean());
							objbattleUnit1Skills.setSkillPointsIsEnough(readBoolean());
				}
		count = readShort();
		count = count < 0 ? 0 : count;
		battleUnit1MagicSlots = new com.hifun.soul.gameserver.battle.gem.MagicSlotInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.battle.gem.MagicSlotInfo objbattleUnit1MagicSlots = new com.hifun.soul.gameserver.battle.gem.MagicSlotInfo();
			battleUnit1MagicSlots[i] = objbattleUnit1MagicSlots;
					objbattleUnit1MagicSlots.setCurrentSize(readInteger());
							objbattleUnit1MagicSlots.setCapacity(readInteger());
							objbattleUnit1MagicSlots.setEnergyType(readInteger());
				}
		battleUnit1Hp = readInteger();
		firstAttack1 = readInteger();
		guid2 = readLong();
		level2 = readInteger();
		roleType2 = readInteger();
		occupation2 = readInteger();
		name2 = readString();
		resourceId2 = readInteger();
		headId2 = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		battleUnit2Skills = new com.hifun.soul.gameserver.skill.template.SkillInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.skill.template.SkillInfo objbattleUnit2Skills = new com.hifun.soul.gameserver.skill.template.SkillInfo();
			battleUnit2Skills[i] = objbattleUnit2Skills;
					objbattleUnit2Skills.setSkillId(readInteger());
							objbattleUnit2Skills.setSkillName(readString());
							objbattleUnit2Skills.setSkillDesc(readString());
							objbattleUnit2Skills.setSkillActionId(readInteger());
							objbattleUnit2Skills.setFlyEffectId(readInteger());
							objbattleUnit2Skills.setBeHitedEffectId(readInteger());
							objbattleUnit2Skills.setEnemyBeHitedEffectId(readInteger());
							objbattleUnit2Skills.setCooldownRound(readInteger());
							objbattleUnit2Skills.setAttackType(readInteger());
							objbattleUnit2Skills.setSkillType(readInteger());
							objbattleUnit2Skills.setNeedSelectGem(readBoolean());
							objbattleUnit2Skills.setRangeType(readInteger());
							objbattleUnit2Skills.setUseRoundOver(readBoolean());
							objbattleUnit2Skills.setRedCost(readInteger());
							objbattleUnit2Skills.setYellowCost(readInteger());
							objbattleUnit2Skills.setGreenCost(readInteger());
							objbattleUnit2Skills.setBlueCost(readInteger());
							objbattleUnit2Skills.setPurpleCost(readInteger());
							objbattleUnit2Skills.setIsCarried(readBoolean());
							objbattleUnit2Skills.setSkillSound(readInteger());
							objbattleUnit2Skills.setSkillIcon(readInteger());
							objbattleUnit2Skills.setSlotIndex(readInteger());
							objbattleUnit2Skills.setSkillState(readInteger());
							objbattleUnit2Skills.setSkillDevelopType(readInteger());
							objbattleUnit2Skills.setNeedLevel(readInteger());
							objbattleUnit2Skills.setNeedSkillPoints(readInteger());
							objbattleUnit2Skills.setSkillScrollName(readString());
							objbattleUnit2Skills.setIsNeedSkillScroll(readBoolean());
							objbattleUnit2Skills.setPreSkillName(readString());
							objbattleUnit2Skills.setPreSkillIsOpen(readBoolean());
							objbattleUnit2Skills.setHasSkillScroll(readBoolean());
							objbattleUnit2Skills.setSkillPointsIsEnough(readBoolean());
				}
		count = readShort();
		count = count < 0 ? 0 : count;
		battleUnit2MagicSlots = new com.hifun.soul.gameserver.battle.gem.MagicSlotInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.battle.gem.MagicSlotInfo objbattleUnit2MagicSlots = new com.hifun.soul.gameserver.battle.gem.MagicSlotInfo();
			battleUnit2MagicSlots[i] = objbattleUnit2MagicSlots;
					objbattleUnit2MagicSlots.setCurrentSize(readInteger());
							objbattleUnit2MagicSlots.setCapacity(readInteger());
							objbattleUnit2MagicSlots.setEnergyType(readInteger());
				}
		battleUnit2Hp = readInteger();
		firstAttack2 = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		battleBuffTypes = new int[count];
		for(int i=0; i<count; i++){
			battleBuffTypes[i] = readInteger();
		}
		count = readShort();
		count = count < 0 ? 0 : count;
		chessBoardCols = new com.hifun.soul.gameserver.battle.gem.ColNewGems[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.battle.gem.ColNewGems objchessBoardCols = new com.hifun.soul.gameserver.battle.gem.ColNewGems();
			chessBoardCols[i] = objchessBoardCols;
					objchessBoardCols.setCol(readInteger());
								{
	int subCountgems = readShort();
		int[] subListgems = new int[subCountgems];
		objchessBoardCols.setGems(subListgems);
	for(int jgems = 0; jgems < subCountgems; jgems++){
						subListgems[jgems] = readInteger();
			}
	}
				}
		guide = readBoolean();
		defaultSelfActionId = readInteger();
		defaultOtherActionId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(battleBgId);
		writeLong(guid1);
		writeInteger(level1);
		writeInteger(roleType1);
		writeInteger(occupation1);
		writeString(name1);
		writeInteger(resourceId1);
		writeInteger(headId1);
	writeShort(battleUnit1Skills.length);
	for(int i=0; i<battleUnit1Skills.length; i++){
	com.hifun.soul.gameserver.skill.template.SkillInfo objbattleUnit1Skills = battleUnit1Skills[i];
				writeInteger(objbattleUnit1Skills.getSkillId());
				writeString(objbattleUnit1Skills.getSkillName());
				writeString(objbattleUnit1Skills.getSkillDesc());
				writeInteger(objbattleUnit1Skills.getSkillActionId());
				writeInteger(objbattleUnit1Skills.getFlyEffectId());
				writeInteger(objbattleUnit1Skills.getBeHitedEffectId());
				writeInteger(objbattleUnit1Skills.getEnemyBeHitedEffectId());
				writeInteger(objbattleUnit1Skills.getCooldownRound());
				writeInteger(objbattleUnit1Skills.getAttackType());
				writeInteger(objbattleUnit1Skills.getSkillType());
				writeBoolean(objbattleUnit1Skills.getNeedSelectGem());
				writeInteger(objbattleUnit1Skills.getRangeType());
				writeBoolean(objbattleUnit1Skills.getUseRoundOver());
				writeInteger(objbattleUnit1Skills.getRedCost());
				writeInteger(objbattleUnit1Skills.getYellowCost());
				writeInteger(objbattleUnit1Skills.getGreenCost());
				writeInteger(objbattleUnit1Skills.getBlueCost());
				writeInteger(objbattleUnit1Skills.getPurpleCost());
				writeBoolean(objbattleUnit1Skills.getIsCarried());
				writeInteger(objbattleUnit1Skills.getSkillSound());
				writeInteger(objbattleUnit1Skills.getSkillIcon());
				writeInteger(objbattleUnit1Skills.getSlotIndex());
				writeInteger(objbattleUnit1Skills.getSkillState());
				writeInteger(objbattleUnit1Skills.getSkillDevelopType());
				writeInteger(objbattleUnit1Skills.getNeedLevel());
				writeInteger(objbattleUnit1Skills.getNeedSkillPoints());
				writeString(objbattleUnit1Skills.getSkillScrollName());
				writeBoolean(objbattleUnit1Skills.getIsNeedSkillScroll());
				writeString(objbattleUnit1Skills.getPreSkillName());
				writeBoolean(objbattleUnit1Skills.getPreSkillIsOpen());
				writeBoolean(objbattleUnit1Skills.getHasSkillScroll());
				writeBoolean(objbattleUnit1Skills.getSkillPointsIsEnough());
	}
	writeShort(battleUnit1MagicSlots.length);
	for(int i=0; i<battleUnit1MagicSlots.length; i++){
	com.hifun.soul.gameserver.battle.gem.MagicSlotInfo objbattleUnit1MagicSlots = battleUnit1MagicSlots[i];
				writeInteger(objbattleUnit1MagicSlots.getCurrentSize());
				writeInteger(objbattleUnit1MagicSlots.getCapacity());
				writeInteger(objbattleUnit1MagicSlots.getEnergyType());
	}
		writeInteger(battleUnit1Hp);
		writeInteger(firstAttack1);
		writeLong(guid2);
		writeInteger(level2);
		writeInteger(roleType2);
		writeInteger(occupation2);
		writeString(name2);
		writeInteger(resourceId2);
		writeInteger(headId2);
	writeShort(battleUnit2Skills.length);
	for(int i=0; i<battleUnit2Skills.length; i++){
	com.hifun.soul.gameserver.skill.template.SkillInfo objbattleUnit2Skills = battleUnit2Skills[i];
				writeInteger(objbattleUnit2Skills.getSkillId());
				writeString(objbattleUnit2Skills.getSkillName());
				writeString(objbattleUnit2Skills.getSkillDesc());
				writeInteger(objbattleUnit2Skills.getSkillActionId());
				writeInteger(objbattleUnit2Skills.getFlyEffectId());
				writeInteger(objbattleUnit2Skills.getBeHitedEffectId());
				writeInteger(objbattleUnit2Skills.getEnemyBeHitedEffectId());
				writeInteger(objbattleUnit2Skills.getCooldownRound());
				writeInteger(objbattleUnit2Skills.getAttackType());
				writeInteger(objbattleUnit2Skills.getSkillType());
				writeBoolean(objbattleUnit2Skills.getNeedSelectGem());
				writeInteger(objbattleUnit2Skills.getRangeType());
				writeBoolean(objbattleUnit2Skills.getUseRoundOver());
				writeInteger(objbattleUnit2Skills.getRedCost());
				writeInteger(objbattleUnit2Skills.getYellowCost());
				writeInteger(objbattleUnit2Skills.getGreenCost());
				writeInteger(objbattleUnit2Skills.getBlueCost());
				writeInteger(objbattleUnit2Skills.getPurpleCost());
				writeBoolean(objbattleUnit2Skills.getIsCarried());
				writeInteger(objbattleUnit2Skills.getSkillSound());
				writeInteger(objbattleUnit2Skills.getSkillIcon());
				writeInteger(objbattleUnit2Skills.getSlotIndex());
				writeInteger(objbattleUnit2Skills.getSkillState());
				writeInteger(objbattleUnit2Skills.getSkillDevelopType());
				writeInteger(objbattleUnit2Skills.getNeedLevel());
				writeInteger(objbattleUnit2Skills.getNeedSkillPoints());
				writeString(objbattleUnit2Skills.getSkillScrollName());
				writeBoolean(objbattleUnit2Skills.getIsNeedSkillScroll());
				writeString(objbattleUnit2Skills.getPreSkillName());
				writeBoolean(objbattleUnit2Skills.getPreSkillIsOpen());
				writeBoolean(objbattleUnit2Skills.getHasSkillScroll());
				writeBoolean(objbattleUnit2Skills.getSkillPointsIsEnough());
	}
	writeShort(battleUnit2MagicSlots.length);
	for(int i=0; i<battleUnit2MagicSlots.length; i++){
	com.hifun.soul.gameserver.battle.gem.MagicSlotInfo objbattleUnit2MagicSlots = battleUnit2MagicSlots[i];
				writeInteger(objbattleUnit2MagicSlots.getCurrentSize());
				writeInteger(objbattleUnit2MagicSlots.getCapacity());
				writeInteger(objbattleUnit2MagicSlots.getEnergyType());
	}
		writeInteger(battleUnit2Hp);
		writeInteger(firstAttack2);
	writeShort(battleBuffTypes.length);
	for(int i=0; i<battleBuffTypes.length; i++){
	Integer objbattleBuffTypes = battleBuffTypes[i];
			writeInteger(objbattleBuffTypes);
}
	writeShort(chessBoardCols.length);
	for(int i=0; i<chessBoardCols.length; i++){
	com.hifun.soul.gameserver.battle.gem.ColNewGems objchessBoardCols = chessBoardCols[i];
				writeInteger(objchessBoardCols.getCol());
					int[] gems_objchessBoardCols=objchessBoardCols.getGems();
	writeShort(gems_objchessBoardCols.length);
	for(int jgems=0; jgems<gems_objchessBoardCols.length; jgems++){
					writeInteger(gems_objchessBoardCols[jgems]);
			}
	}
		writeBoolean(guide);
		writeInteger(defaultSelfActionId);
		writeInteger(defaultOtherActionId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_START_BATTLE_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_START_BATTLE_INFO";
	}

	public int getBattleBgId(){
		return battleBgId;
	}
		
	public void setBattleBgId(int battleBgId){
		this.battleBgId = battleBgId;
	}

	public long getGuid1(){
		return guid1;
	}
		
	public void setGuid1(long guid1){
		this.guid1 = guid1;
	}

	public int getLevel1(){
		return level1;
	}
		
	public void setLevel1(int level1){
		this.level1 = level1;
	}

	public int getRoleType1(){
		return roleType1;
	}
		
	public void setRoleType1(int roleType1){
		this.roleType1 = roleType1;
	}

	public int getOccupation1(){
		return occupation1;
	}
		
	public void setOccupation1(int occupation1){
		this.occupation1 = occupation1;
	}

	public String getName1(){
		return name1;
	}
		
	public void setName1(String name1){
		this.name1 = name1;
	}

	public int getResourceId1(){
		return resourceId1;
	}
		
	public void setResourceId1(int resourceId1){
		this.resourceId1 = resourceId1;
	}

	public int getHeadId1(){
		return headId1;
	}
		
	public void setHeadId1(int headId1){
		this.headId1 = headId1;
	}

	public com.hifun.soul.gameserver.skill.template.SkillInfo[] getBattleUnit1Skills(){
		return battleUnit1Skills;
	}

	public void setBattleUnit1Skills(com.hifun.soul.gameserver.skill.template.SkillInfo[] battleUnit1Skills){
		this.battleUnit1Skills = battleUnit1Skills;
	}	

	public com.hifun.soul.gameserver.battle.gem.MagicSlotInfo[] getBattleUnit1MagicSlots(){
		return battleUnit1MagicSlots;
	}

	public void setBattleUnit1MagicSlots(com.hifun.soul.gameserver.battle.gem.MagicSlotInfo[] battleUnit1MagicSlots){
		this.battleUnit1MagicSlots = battleUnit1MagicSlots;
	}	

	public int getBattleUnit1Hp(){
		return battleUnit1Hp;
	}
		
	public void setBattleUnit1Hp(int battleUnit1Hp){
		this.battleUnit1Hp = battleUnit1Hp;
	}

	public int getFirstAttack1(){
		return firstAttack1;
	}
		
	public void setFirstAttack1(int firstAttack1){
		this.firstAttack1 = firstAttack1;
	}

	public long getGuid2(){
		return guid2;
	}
		
	public void setGuid2(long guid2){
		this.guid2 = guid2;
	}

	public int getLevel2(){
		return level2;
	}
		
	public void setLevel2(int level2){
		this.level2 = level2;
	}

	public int getRoleType2(){
		return roleType2;
	}
		
	public void setRoleType2(int roleType2){
		this.roleType2 = roleType2;
	}

	public int getOccupation2(){
		return occupation2;
	}
		
	public void setOccupation2(int occupation2){
		this.occupation2 = occupation2;
	}

	public String getName2(){
		return name2;
	}
		
	public void setName2(String name2){
		this.name2 = name2;
	}

	public int getResourceId2(){
		return resourceId2;
	}
		
	public void setResourceId2(int resourceId2){
		this.resourceId2 = resourceId2;
	}

	public int getHeadId2(){
		return headId2;
	}
		
	public void setHeadId2(int headId2){
		this.headId2 = headId2;
	}

	public com.hifun.soul.gameserver.skill.template.SkillInfo[] getBattleUnit2Skills(){
		return battleUnit2Skills;
	}

	public void setBattleUnit2Skills(com.hifun.soul.gameserver.skill.template.SkillInfo[] battleUnit2Skills){
		this.battleUnit2Skills = battleUnit2Skills;
	}	

	public com.hifun.soul.gameserver.battle.gem.MagicSlotInfo[] getBattleUnit2MagicSlots(){
		return battleUnit2MagicSlots;
	}

	public void setBattleUnit2MagicSlots(com.hifun.soul.gameserver.battle.gem.MagicSlotInfo[] battleUnit2MagicSlots){
		this.battleUnit2MagicSlots = battleUnit2MagicSlots;
	}	

	public int getBattleUnit2Hp(){
		return battleUnit2Hp;
	}
		
	public void setBattleUnit2Hp(int battleUnit2Hp){
		this.battleUnit2Hp = battleUnit2Hp;
	}

	public int getFirstAttack2(){
		return firstAttack2;
	}
		
	public void setFirstAttack2(int firstAttack2){
		this.firstAttack2 = firstAttack2;
	}

	public int[] getBattleBuffTypes(){
		return battleBuffTypes;
	}

	public void setBattleBuffTypes(int[] battleBuffTypes){
		this.battleBuffTypes = battleBuffTypes;
	}	

	public com.hifun.soul.gameserver.battle.gem.ColNewGems[] getChessBoardCols(){
		return chessBoardCols;
	}

	public void setChessBoardCols(com.hifun.soul.gameserver.battle.gem.ColNewGems[] chessBoardCols){
		this.chessBoardCols = chessBoardCols;
	}	

	public boolean getGuide(){
		return guide;
	}
		
	public void setGuide(boolean guide){
		this.guide = guide;
	}

	public int getDefaultSelfActionId(){
		return defaultSelfActionId;
	}
		
	public void setDefaultSelfActionId(int defaultSelfActionId){
		this.defaultSelfActionId = defaultSelfActionId;
	}

	public int getDefaultOtherActionId(){
		return defaultOtherActionId;
	}
		
	public void setDefaultOtherActionId(int defaultOtherActionId){
		this.defaultOtherActionId = defaultOtherActionId;
	}
}