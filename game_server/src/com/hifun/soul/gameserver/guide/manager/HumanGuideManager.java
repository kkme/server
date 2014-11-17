package com.hifun.soul.gameserver.guide.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.gamedb.cache.CacheEntry;
import com.hifun.soul.gamedb.cache.ICachableComponent;
import com.hifun.soul.gamedb.entity.HumanEntity;
import com.hifun.soul.gamedb.entity.HumanGuideEntity;
import com.hifun.soul.gameserver.battle.Battle;
import com.hifun.soul.gameserver.battle.BattleType;
import com.hifun.soul.gameserver.battle.context.IBattleContext;
import com.hifun.soul.gameserver.battle.gem.EnergyType;
import com.hifun.soul.gameserver.battle.gem.GemObject;
import com.hifun.soul.gameserver.battle.gem.GemType;
import com.hifun.soul.gameserver.battle.gem.Move;
import com.hifun.soul.gameserver.battleguide.msg.GCBattleGuideChessboard;
import com.hifun.soul.gameserver.battleguide.msg.GCBattleGuideFourBombs;
import com.hifun.soul.gameserver.battleguide.msg.GCBattleGuideSkillInfo;
import com.hifun.soul.gameserver.battleguide.msg.GCBattleGuideThreeBombs;
import com.hifun.soul.gameserver.battleguide.msg.GCBattleGuideUseSkill;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.guide.GuideType;
import com.hifun.soul.gameserver.guide.converter.GuideInfoToEntityConverter;
import com.hifun.soul.gameserver.guide.msg.GuideStepInfo;
import com.hifun.soul.gameserver.guide.service.GuideTemplateManager;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.IHumanPersistenceManager;
import com.hifun.soul.gameserver.player.state.PlayerState;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.PropertyType;
import com.hifun.soul.gameserver.skill.ISkill;
import com.hifun.soul.gameserver.skill.SkillType;
import com.hifun.soul.proto.data.entity.Entity.HumanGuide;

/**
 * 新手引导管理器
 * @author magicstone
 */
public class HumanGuideManager implements IHumanPersistenceManager,ICachableComponent {
	/** 所属玩家角色 */
	private Human human;
	/** 已完成的引导 */
	private Set<Integer> _finishedSet;
	/** 转化器 */
	private GuideInfoToEntityConverter converter;
	/** 引导缓存 */
	private CacheEntry<Integer, Integer> guideCaches = new CacheEntry<Integer, Integer>();
	private GuideTemplateManager templateManager;
	
	/**
	 * 类参数构造器
	 * 
	 * @param human
	 * @param guideServ 
	 * 
	 */
	public HumanGuideManager(Human human) {
		this.human = human;
		this._finishedSet = Sets.newHashSet();
		converter = new GuideInfoToEntityConverter(human);
		templateManager = GameServerAssist.getGuideTemplateManager();
		
		this.human.registerCachableManager(this);
		this.human.registerPersistenceManager(this);
	}

	/**
	 * 获取玩家角色
	 * 
	 * @return
	 */
	public Human getHuman() {
		return this.human;
	}
	
	/**
	 * 是否完全结束战斗引导, 战斗引导不走通用引导模版， 
	 * 根据策划需求需要加一个参数决定什么时候完全结束战斗引导 
	 * @return
	 */
	public boolean isFinishBattleGuide() {
		return human.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(HumanIntProperty.FINISHED_BATTLE_GUIDE)>0?true:false;
	}
	
	public void setIsFinishBattleGuide(int finish) {
		human.getPropertyManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
			.setPropertyValue(HumanIntProperty.FINISHED_BATTLE_GUIDE, finish);
	}

	/**
	 * 获取新手引导
	 * 
	 * @param guideType 
	 * @return
	 */
	public void showGuide(int guideTypeId) {
		if(!templateManager.guideIsOpen()){
			return;
		}
		if (this._finishedSet.contains(guideTypeId)) {
			// 如果已经为玩家显示过引导, 
			// 则直接跳过
			return;
		}
		// 显示新手引导
		GameServerAssist.getGuideTemplateManager().showGuide(this.human, guideTypeId);
		this._finishedSet.add(guideTypeId);
		// 当前新手引导同类型之前的引导是否有没有触发的设置为已经触发
		/** edit by yandajun 策划需求变更
		Set<Integer> guideIdList = GameServerAssist.getGuideTemplateManager().getSameGuideTypeIds(guideTypeId);
		if(guideIdList != null){
			for(int guideId : guideIdList){
				if(!this._finishedSet.contains(guideId)
						&& guideId < guideTypeId){
					this._finishedSet.add(guideId);
					guideCaches.addUpdate(guideId, guideId);
				}
			}
		}
		*/
		// 同步缓存
		guideCaches.addUpdate(guideTypeId, guideTypeId);
	}

	@Override
	public List<IEntity> getUpdateEntities() {
		List<IEntity> updateList = new ArrayList<IEntity>();
		for(Integer guideId : guideCaches.getAllUpdateData()){
			HumanGuideEntity entity = this.converter.convert(guideId);
			updateList.add(entity);
		}
		return updateList;
	}

	@Override
	public List<IEntity> getDeleteEntities() {
		List<IEntity> deleteList = new ArrayList<IEntity>();
		for(Integer guideId : guideCaches.getAllDeleteData()){
			HumanGuideEntity entity = this.converter.convert(guideId);
			deleteList.add(entity);
		}
		return deleteList;
	}

	@Override
	public Human getOwner() {
		return human;
	}

	@Override
	public void onLoad(HumanEntity humanEntity) {
		for (HumanGuide guide : humanEntity.getBuilder().getGuideList()) {
			_finishedSet.add(guide.getGuideType());
		}
	}

	@Override
	public void onPersistence(HumanEntity humanEntity) {
		if(_finishedSet.size() > 0){
			for(Integer guideType : _finishedSet){
				humanEntity.getBuilder().addGuide(this.converter.convert(guideType).getBuilder());
			}
		}		
	}

	public boolean isFinishedGuide(int type) {
		if(!templateManager.guideIsOpen()){
			return true;
		}
		if (this._finishedSet.contains(type)) {
			// 如果已经为玩家显示过引导, 则直接跳过
			return true;
		}
		return false;
	}
	
	/**
	 * 战斗界面的引导
	 * @param guideTypeId
	 */
	public void showGuideInBattle(int guideTypeId) {
		if (this._finishedSet.contains(guideTypeId)) {
			// 如果已经为玩家显示过引导, 
			// 则直接跳过
			return;
		}
		this._finishedSet.add(guideTypeId);
		// 同步缓存
		guideCaches.addUpdate(guideTypeId, guideTypeId);
	}
	
	/**
	 * 是否所有的战斗引导类型都已经做过一遍
	 * @return
	 */
	private boolean isFristBattleGuideDoAll() {
		return this.isFinishedGuide(GuideType.BATTLE_GUIDE_CHESSBOARD.getIndex()) 
				&& this.isFinishedGuide(GuideType.BATTLE_GUIDE_FOUR_BOMBS.getIndex())
				&& this.isFinishedGuide(GuideType.BATTLE_GUIDE_THREE_BOMBS_WHITE.getIndex())
				&& this.isFinishedGuide(GuideType.BATTLE_GUIDE_THREE_BOMBS_BLACK.getIndex())
				&& this.isFinishedGuide(GuideType.BATTLE_GUIDE_USE_SKILL.getIndex())
				&& this.isFinishedGuide(GuideType.BATTLE_GUIDE_SKILL_INFO.getIndex())
				&& this.isFinishedGuide(GuideType.BATTLE_GUIDE_THREE_BOMBS_RED.getIndex())
				&& this.isFinishedGuide(GuideType.BATTLE_GUIDE_THREE_BOMBS_YELLOW.getIndex())
				&& this.isFinishedGuide(GuideType.BATTLE_GUIDE_THREE_BOMBS_BLUE.getIndex())
				&& this.isFinishedGuide(GuideType.BATTLE_GUIDE_THREE_BOMBS_GREEN.getIndex())
				&& this.isFinishedGuide(GuideType.BATTLE_GUIDE_THREE_BOMBS_PURPLE.getIndex())
				;
	}
	
	/**
	 * 引导战斗棋盘
	 */
	private void battleGuideChessBoard(Battle battle) {
		// 如果战斗棋盘的引导没有做过肯定要做棋盘引导
		if(!this.isFinishedGuide(GuideType.BATTLE_GUIDE_CHESSBOARD.getIndex())){
			GCBattleGuideChessboard gcMsg = new GCBattleGuideChessboard();
			gcMsg.setGuideType((short)GuideType.BATTLE_GUIDE_CHESSBOARD.getIndex());
			List<GuideStepInfo> guideStepInfos = GameServerAssist.getGuideTemplateManager()
					.getGuideStepInfos(GuideType.BATTLE_GUIDE_CHESSBOARD.getIndex());
			if(guideStepInfos == null){
				gcMsg.setGuideStepList(new GuideStepInfo[0]);
			}
			else{
				gcMsg.setGuideStepList(guideStepInfos.toArray(new GuideStepInfo[0]));
			}
			human.sendMessage(gcMsg);
			showGuideInBattle(GuideType.BATTLE_GUIDE_CHESSBOARD.getIndex());
			battle.setIsGuideing(true);
		}
	}
	
	/**
	 * 第一次携带技能引导
	 * @param battle
	 */
	private void battleGuideSkillInfo(Battle battle) {
		// 第一次携带技能引导
		if(!this.isFinishedGuide(GuideType.BATTLE_GUIDE_SKILL_INFO.getIndex())
				&& human.getCarriedSkills() != null
				&& human.getCarriedSkills().size() > 0){
			boolean isEquipSkill = false;
			for(ISkill skill : human.getCarriedSkills()){
				if(skill.getSlotIndex() >= 0){
					isEquipSkill = true;
					break;
				}
			}
			if(isEquipSkill){
				GCBattleGuideSkillInfo gcMsg = new GCBattleGuideSkillInfo();
				gcMsg.setGuideType((short) GuideType.BATTLE_GUIDE_SKILL_INFO.getIndex());
				List<GuideStepInfo> guideStepInfos = GameServerAssist.getGuideTemplateManager()
						.getGuideStepInfos(GuideType.BATTLE_GUIDE_SKILL_INFO.getIndex());
				if(guideStepInfos == null){
					gcMsg.setGuideStepList(new GuideStepInfo[0]);
				}
				else{
					gcMsg.setGuideStepList(guideStepInfos.toArray(new GuideStepInfo[0]));
				}
				human.sendMessage(gcMsg);
				showGuideInBattle(GuideType.BATTLE_GUIDE_SKILL_INFO.getIndex());
				battle.setIsGuideing(true);
			}
		}
	}
	
	/**
	 * 转化GuideStepInfo到BattleGuideInfo
	 * @param guideStepInfos
	 * @return
	 */
	private List<GuideStepInfo> getBattleGuideInfos(List<GuideStepInfo> guideStepInfos) {
		if(guideStepInfos == null
				|| guideStepInfos.size() <= 0){
			return null;
		}
		List<GuideStepInfo> battleGuideInfos = new ArrayList<GuideStepInfo>();
		for(GuideStepInfo guideStepInfo : guideStepInfos){
			if(guideStepInfo != null){
				GuideStepInfo battleGuideInfo = new GuideStepInfo();
				battleGuideInfo.setArrow1PosX(guideStepInfo.getArrow1PosX());
				battleGuideInfo.setArrow1PosY(guideStepInfo.getArrow1PosY());
				battleGuideInfo.setArrow2PosX(guideStepInfo.getArrow2PosX());
				battleGuideInfo.setArrow2PosY(guideStepInfo.getArrow2PosY());
				battleGuideInfo.setArrow3PosX(guideStepInfo.getArrow3PosX());
				battleGuideInfo.setArrow3PosY(guideStepInfo.getArrow3PosY());
				battleGuideInfo.setAutoCommit(guideStepInfo.getAutoCommit());
				battleGuideInfo.setControlName(guideStepInfo.getControlName());
				battleGuideInfo.setEvent(guideStepInfo.getEvent());
				battleGuideInfo.setGuideText(guideStepInfo.getGuideText());
				battleGuideInfo.setGuideTextLangId(guideStepInfo.getGuideTextLangId());
				battleGuideInfo.setGuideTypeId(guideStepInfo.getGuideTypeId());
				battleGuideInfo.setHighlite1BorderColor(guideStepInfo.getHighlite1BorderColor());
				battleGuideInfo.setHighlite1BorderThickness(guideStepInfo.getHighlite1BorderThickness());
				battleGuideInfo.setHighlite1Height(guideStepInfo.getHighlite1Height());
				battleGuideInfo.setHighlite1PosX(guideStepInfo.getHighlite1PosX());
				battleGuideInfo.setHighlite1PosY(guideStepInfo.getHighlite1PosY());
				battleGuideInfo.setHighlite1Width(guideStepInfo.getHighlite1Width());
				battleGuideInfo.setHighlite2BorderColor(guideStepInfo.getHighlite2BorderColor());
				battleGuideInfo.setHighlite2BorderThickness(guideStepInfo.getHighlite2BorderThickness());
				battleGuideInfo.setHighlite2Height(guideStepInfo.getHighlite2Height());
				battleGuideInfo.setHighlite2PosX(guideStepInfo.getHighlite2PosX());
				battleGuideInfo.setHighlite2PosY(guideStepInfo.getHighlite2PosY());
				battleGuideInfo.setHighlite2Width(guideStepInfo.getHighlite2Width());
				battleGuideInfo.setHighlite3BorderColor(guideStepInfo.getHighlite3BorderColor());
				battleGuideInfo.setHighlite3BorderThickness(guideStepInfo.getHighlite3BorderThickness());
				battleGuideInfo.setHighlite3Height(guideStepInfo.getHighlite3Height());
				battleGuideInfo.setHighlite3PosX(guideStepInfo.getHighlite3PosX());
				battleGuideInfo.setHighlite3PosY(guideStepInfo.getHighlite3PosY());
				battleGuideInfo.setHighlite3Width(guideStepInfo.getHighlite3Width());
				battleGuideInfo.setHighlite4BorderColor(guideStepInfo.getHighlite4BorderColor());
				battleGuideInfo.setHighlite4BorderThickness(guideStepInfo.getHighlite4BorderThickness());
				battleGuideInfo.setHighlite4Height(guideStepInfo.getHighlite4Height());
				battleGuideInfo.setHighlite4PosX(guideStepInfo.getHighlite4PosX());
				battleGuideInfo.setHighlite4PosY(guideStepInfo.getHighlite4PosY());
				battleGuideInfo.setHighlite4Width(guideStepInfo.getHighlite4Width());
				battleGuideInfo.setHighlite5BorderColor(guideStepInfo.getHighlite5BorderColor());
				battleGuideInfo.setHighlite5BorderThickness(guideStepInfo.getHighlite5BorderThickness());
				battleGuideInfo.setHighlite5Height(guideStepInfo.getHighlite5Height());
				battleGuideInfo.setHighlite5PosX(guideStepInfo.getHighlite5PosX());
				battleGuideInfo.setHighlite5PosY(guideStepInfo.getHighlite5PosY());
				battleGuideInfo.setHighlite5Width(guideStepInfo.getHighlite5Width());
				battleGuideInfo.setMaskAlpha(guideStepInfo.getMaskAlpha());
				battleGuideInfo.setMaskColorInt(guideStepInfo.getMaskColorInt());
				battleGuideInfo.setMovie(guideStepInfo.getMovie());
				battleGuideInfo.setOperationalBorderColor(guideStepInfo.getOperationalBorderColor());
				battleGuideInfo.setOperationalBorderThickness(guideStepInfo.getOperationalBorderThickness());
				battleGuideInfo.setOperationalHeight(guideStepInfo.getOperationalHeight());
				battleGuideInfo.setOperationalPosX(guideStepInfo.getOperationalPosX());
				battleGuideInfo.setOperationalPosY(guideStepInfo.getOperationalPosY());
				battleGuideInfo.setOperationalWidth(guideStepInfo.getOperationalWidth());
				battleGuideInfo.setSceneType(guideStepInfo.getSceneType());
				battleGuideInfo.setTextMode(guideStepInfo.getTextMode());
				battleGuideInfo.setTextPosX(guideStepInfo.getTextPosX());
				battleGuideInfo.setTextPosY(guideStepInfo.getTextPosY());
				battleGuideInfo.setGuideIcon(guideStepInfo.getGuideIcon());
				battleGuideInfo.setGuideIconX(guideStepInfo.getGuideIconX());
				battleGuideInfo.setGuideIconY(guideStepInfo.getGuideIconY());
				battleGuideInfo.setNeedSetGemPos(guideStepInfo.isNeedSetGemPos());
				battleGuideInfo.setNeedSetSkill(guideStepInfo.isNeedSetSkill());
				battleGuideInfos.add(battleGuideInfo);
			}
		}
		return battleGuideInfos;
	}
	
	/**
	 * 战斗中使用技能引导
	 * @param battle
	 * @return
	 */
	private boolean battleGuideUseSkill(Battle battle, List<Move> moves, boolean notFirstGuide) {
		// 判断是否可以使用技能
		if(!this.isFinishedGuide(GuideType.BATTLE_GUIDE_USE_SKILL.getIndex())
				|| notFirstGuide){
			List<ISkill> skills = human.getBattleContext().getBattleSkills();
			if(skills == null
					|| skills.size() <= 0){
				return false;
			}
			ISkill canUseSkill = null;
			for(ISkill skill : skills){
				if(skill.getSkillTemplate().getSkillType() == SkillType.OTHER.getIndex()
						&& skill.canUseSkill()
						&& human.hasEnoughMagicToUseSuchSkill(skill)
						&& !human.forbidMagic()){
					canUseSkill = skill;
				}
			}
			// 找到可以释放的技能
			if(canUseSkill != null){
				GCBattleGuideUseSkill gcMsg = new GCBattleGuideUseSkill();
				gcMsg.setGuideType((short)GuideType.BATTLE_GUIDE_USE_SKILL.getIndex());
				List<GuideStepInfo> guideStepInfos = GameServerAssist.getGuideTemplateManager()
						.getGuideStepInfos(GuideType.BATTLE_GUIDE_USE_SKILL.getIndex());
				if(guideStepInfos == null
						|| guideStepInfos.size() <= 0){
					gcMsg.setGuideStepList(new GuideStepInfo[0]);
				}
				else{
					List<GuideStepInfo> battleGuideInfos = getBattleGuideInfos(guideStepInfos);
					if(battleGuideInfos == null
							|| battleGuideInfos.size() <= 0){
						gcMsg.setGuideStepList(new GuideStepInfo[0]);
					}
					else{
						for(GuideStepInfo battleGuideInfo : battleGuideInfos){
							if(battleGuideInfo.isNeedSetSkill()){
								battleGuideInfo.setSkillId(canUseSkill.getSkillId());
							}
						}
						gcMsg.setGuideStepList(battleGuideInfos.toArray(new GuideStepInfo[0]));
					}
				}
				human.sendMessage(gcMsg);
				showGuideInBattle(GuideType.BATTLE_GUIDE_USE_SKILL.getIndex());
				battle.setIsGuideing(true);
				return true;
			}
			// 没有找到可以释放的技能
			else{
				List<GemType> gemTypes = new ArrayList<GemType>();
				gemTypes.add(GemType.ALL);
				// 先判断能不能消白的,消白的肯定能弥补能量
				if(checkThreeBombsGuide(battle,moves,GuideType.BATTLE_GUIDE_THREE_BOMBS_WHITE,gemTypes,notFirstGuide)){
					return true;
				}
				for(ISkill skill : skills){
					// 找个魔法值不够的技能
					if(skill.getSlotIndex() >= 0
							&& skill.canUseSkill()
							&& !human.forbidMagic()
							&& !human.hasEnoughMagicToUseSuchSkill(skill)){
						// 找到不够的魔法类型数组
						List<EnergyType> energyTypes = human.getNotEnoughEnergyTypes(skill);
						if(energyTypes != null
								&& energyTypes.size() > 0){
							for(EnergyType energyType : energyTypes){
								switch(energyType){
								case RED:
									gemTypes.clear();
									gemTypes.add(GemType.RED);
									// 红宝石引导
									if(checkThreeBombsGuide(battle,moves,GuideType.BATTLE_GUIDE_THREE_BOMBS_RED,gemTypes,false)){
										return true;
									}
									break;
								case YELLOW:
									gemTypes.clear();
									gemTypes.add(GemType.YELLOW);
									// 黄宝石引导
									if(checkThreeBombsGuide(battle,moves,GuideType.BATTLE_GUIDE_THREE_BOMBS_YELLOW,gemTypes,false)){
										return true;
									}
									break;
								case BLUE:
									gemTypes.clear();
									gemTypes.add(GemType.BLUE);
									// 蓝宝石引导
									if(checkThreeBombsGuide(battle,moves,GuideType.BATTLE_GUIDE_THREE_BOMBS_BLUE,gemTypes,false)){
										return true;
									}
									break;
								case GREEN:
									gemTypes.clear();
									gemTypes.add(GemType.GREEN);
									// 蓝宝石引导
									if(checkThreeBombsGuide(battle,moves,GuideType.BATTLE_GUIDE_THREE_BOMBS_GREEN,gemTypes,false)){
										return true;
									}
									break;
								case PURPLE:
									gemTypes.clear();
									gemTypes.add(GemType.PURPLE);
									// 紫宝石引导
									if(checkThreeBombsGuide(battle,moves,GuideType.BATTLE_GUIDE_THREE_BOMBS_PURPLE,gemTypes,false)){
										return true;
									}
									break;
								}
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * 四连消引导
	 * @param battle
	 * @param fourMoves
	 * @return
	 */
	private boolean battleGuideFourBombs(Battle battle, List<Move> fourMoves, boolean notFirstGuide) {
		// 是否有四连消
		if((!this.isFinishedGuide(GuideType.BATTLE_GUIDE_FOUR_BOMBS.getIndex())
				|| notFirstGuide)
				&& fourMoves != null
				&& fourMoves.size() > 0) {
			GCBattleGuideFourBombs gcMsg = new GCBattleGuideFourBombs();
			gcMsg.setGuideType((short)GuideType.BATTLE_GUIDE_FOUR_BOMBS.getIndex());
			List<GuideStepInfo> guideStepInfos = GameServerAssist.getGuideTemplateManager()
					.getGuideStepInfos(GuideType.BATTLE_GUIDE_FOUR_BOMBS.getIndex());
			if(guideStepInfos != null
					&& guideStepInfos.size() > 0){
				List<GuideStepInfo> battleGuideInfos = getBattleGuideInfos(guideStepInfos);
				if(battleGuideInfos == null
						|| battleGuideInfos.size() <= 0){
					gcMsg.setGuideStepList(new GuideStepInfo[0]);
				}
				else{
					for(GuideStepInfo battleGuideInfo : battleGuideInfos){
						if(battleGuideInfo.isNeedSetGemPos()){
							battleGuideInfo.setRow1((short)fourMoves.get(0).getRow1());
							battleGuideInfo.setCol1((short)fourMoves.get(0).getCol1());
							battleGuideInfo.setRow2((short)fourMoves.get(0).getRow2());
							battleGuideInfo.setCol2((short)fourMoves.get(0).getCol2());
						}
					}
					gcMsg.setGuideStepList(battleGuideInfos.toArray(new GuideStepInfo[0]));
				}
			}
			else{
				gcMsg.setGuideStepList(new GuideStepInfo[0]);
			}
			human.sendMessage(gcMsg);
			showGuideInBattle(GuideType.BATTLE_GUIDE_FOUR_BOMBS.getIndex());
			battle.setIsGuideing(true);
			return true;
		}
		return false;
	}
	
	/**
	 * 当前战斗引导的情况
	 */
	public void checkGuide() {
		// 引导功能没开退出
		if(!templateManager.guideIsOpen()){
			return;
		}
		// 已经做过足够的引导退出
		if(isFinishBattleGuide()){
			return;
		}
		// 判断当前的状态是不是战斗中
		if(human.getPlayer().getState() != PlayerState.BATTLING){
			return;
		}
		// 战斗上下文;
		IBattleContext context = human.getBattleContext();
		if (context == null) {
			return;
		}
		Battle battle = context.getBattle();
		if (battle == null) {
			return;
		}
		// 只有pve战斗才会引导
		if(battle.getBattleType() != BattleType.PVE){
			return;
		}
		battle.setIsGuideing(false);
		// 是否有可以移动的宝石
		List<Move> moves = battle.getMoves();
		if(moves == null
				|| moves.size() <= 0){
			return;
		}
		// 根据策划需求各种类型的引导要先都做一个遍，有一个优先顺序，如果都做了一个遍并且
		// 还处在战斗引导阶段(策划配置打过哪个关卡战斗引导消失)，按照另一个优先顺序引导
		if(!isFristBattleGuideDoAll()){
			// 棋盘指示引导，不影响继续做其他引导
			battleGuideChessBoard(battle);
			// 技能说明引导，不影响继续做其他引导
			battleGuideSkillInfo(battle);
			// 技能引导，引导之后本轮不做其他引导
			if(battleGuideUseSkill(battle,moves,false)){
				return;
			}
			List<GemType> gemTypes = new ArrayList<GemType>();
			gemTypes.add(GemType.RED);
			// 红宝石引导
			if(checkThreeBombsGuide(battle,moves,GuideType.BATTLE_GUIDE_THREE_BOMBS_RED,gemTypes,false)){
				return;
			}
			// 黄宝石引导
			gemTypes.clear();
			gemTypes.add(GemType.YELLOW);
			if(checkThreeBombsGuide(battle,moves,GuideType.BATTLE_GUIDE_THREE_BOMBS_YELLOW,gemTypes,false)){
				return;
			}
			// 蓝宝石引导
			gemTypes.clear();
			gemTypes.add(GemType.BLUE);
			if(checkThreeBombsGuide(battle,moves,GuideType.BATTLE_GUIDE_THREE_BOMBS_BLUE,gemTypes,false)){
				return;
			}
			// 绿宝石引导
			gemTypes.clear();
			gemTypes.add(GemType.GREEN);
			if(checkThreeBombsGuide(battle,moves,GuideType.BATTLE_GUIDE_THREE_BOMBS_GREEN,gemTypes,false)){
				return;
			}
			// 紫宝石引导
			gemTypes.clear();
			gemTypes.add(GemType.PURPLE);
			if(checkThreeBombsGuide(battle,moves,GuideType.BATTLE_GUIDE_THREE_BOMBS_PURPLE,gemTypes,false)){
				return;
			}
			// 四连消引导
			if(battleGuideFourBombs(battle, battle.getFourBombsMoves(),false)){
				return;
			}
			// 黑宝石引导
			gemTypes.clear();
			gemTypes.add(GemType.ATTACK);
			if(checkThreeBombsGuide(battle,moves,GuideType.BATTLE_GUIDE_THREE_BOMBS_BLACK,gemTypes,false)){
				return;
			}
			// 白宝石引导
			gemTypes.clear();
			gemTypes.add(GemType.ALL);
			if(checkThreeBombsGuide(battle,moves,GuideType.BATTLE_GUIDE_THREE_BOMBS_WHITE,gemTypes,false)){
				return;
			}
		}
		// 四连消引导
		if(battleGuideFourBombs(battle, battle.getFourBombsMoves(),true)){
			return;
		}
		// 技能引导，引导之后本轮不做其他引导
		if(battleGuideUseSkill(battle,moves,true)){
			return;
		}
		List<GemType> gemTypes = new ArrayList<GemType>();
		// 黑宝石引导
		gemTypes.clear();
		gemTypes.add(GemType.ATTACK);
		if(checkThreeBombsGuide(battle,moves,GuideType.BATTLE_GUIDE_THREE_BOMBS_BLACK,gemTypes,true)){
			return;
		}
		// 白宝石引导
		gemTypes.clear();
		gemTypes.add(GemType.ALL);
		if(checkThreeBombsGuide(battle,moves,GuideType.BATTLE_GUIDE_THREE_BOMBS_WHITE,gemTypes,true)){
			return;
		}
		// 红宝石引导
		gemTypes.clear();
		gemTypes.add(GemType.RED);
		if(checkThreeBombsGuide(battle,moves,GuideType.BATTLE_GUIDE_THREE_BOMBS_RED,gemTypes,true)){
			return;
		}
		// 黄宝石引导
		gemTypes.clear();
		gemTypes.add(GemType.YELLOW);
		if(checkThreeBombsGuide(battle,moves,GuideType.BATTLE_GUIDE_THREE_BOMBS_YELLOW,gemTypes,true)){
			return;
		}
		// 蓝宝石引导
		gemTypes.clear();
		gemTypes.add(GemType.BLUE);
		if(checkThreeBombsGuide(battle,moves,GuideType.BATTLE_GUIDE_THREE_BOMBS_BLUE,gemTypes,true)){
			return;
		}
		// 绿宝石引导
		gemTypes.clear();
		gemTypes.add(GemType.GREEN);
		if(checkThreeBombsGuide(battle,moves,GuideType.BATTLE_GUIDE_THREE_BOMBS_GREEN,gemTypes,true)){
			return;
		}
		// 紫宝石引导
		gemTypes.clear();
		gemTypes.add(GemType.PURPLE);
		if(checkThreeBombsGuide(battle,moves,GuideType.BATTLE_GUIDE_THREE_BOMBS_PURPLE,gemTypes,true)){
			return;
		}
	}

	/**
	 * 三连引导
	 * @param battle
	 * @param moves
	 * @param guideType
	 * @return
	 */
	private boolean checkThreeBombsGuide(Battle battle, List<Move> moves, GuideType guideType, List<GemType> gemTypes, boolean notFirstGuide) {
		if(!this.isFinishedGuide(guideType.getIndex())
				|| notFirstGuide){
			for(Move move : moves){
				GemObject gemObject = battle.getGemObject(move.getRow1(), move.getCol1());
				if(gemObject != null
						&&  gemTypes.contains(gemObject.getType())){
					GCBattleGuideThreeBombs gcMsg = new GCBattleGuideThreeBombs();
					gcMsg.setGuideType((short)guideType.getIndex());
					List<GuideStepInfo> guideStepInfos = GameServerAssist.getGuideTemplateManager()
							.getGuideStepInfos(guideType.getIndex());
					if(guideStepInfos != null
							&& guideStepInfos.size() > 0){
						List<GuideStepInfo> battleGuideInfos = getBattleGuideInfos(guideStepInfos);
						if(battleGuideInfos == null
								|| battleGuideInfos.size() <= 0){
							gcMsg.setGuideStepList(new GuideStepInfo[0]);
						}
						else{
							for(GuideStepInfo battleGuideInfo : battleGuideInfos){
								if(battleGuideInfo.isNeedSetGemPos()){
									battleGuideInfo.setRow1((short)move.getRow1());
									battleGuideInfo.setCol1((short)move.getCol1());
									battleGuideInfo.setRow2((short)move.getRow2());
									battleGuideInfo.setCol2((short)move.getCol2());
								}
							}
							gcMsg.setGuideStepList(battleGuideInfos.toArray(new GuideStepInfo[0]));
						}
					}
					else{
						gcMsg.setGuideStepList(new GuideStepInfo[0]);
					}
					human.sendMessage(gcMsg);
					showGuideInBattle(guideType.getIndex());
					battle.setIsGuideing(true);
					return true;
				}
			}
		}
		return false;
	}
}
