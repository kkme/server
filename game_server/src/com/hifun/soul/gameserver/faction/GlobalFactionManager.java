package com.hifun.soul.gameserver.faction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.core.orm.IDBService;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gamedb.agent.query.DataQueryConstants;
import com.hifun.soul.gamedb.cache.CacheManager;
import com.hifun.soul.gamedb.cache.ICachableComponent;
import com.hifun.soul.gamedb.entity.FactionMemberEntity;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.common.IGlobalManager;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.faction.model.FactionSimpleInfo;
import com.hifun.soul.gameserver.faction.msg.GCSelectFaction;
import com.hifun.soul.gameserver.faction.template.FactionTemplate;
import com.hifun.soul.gameserver.human.Human;

/**
 * 全局的阵营管理器;
 * 
 * @author crazyjohn
 * 
 */
@Scope("singleton")
@Component
public class GlobalFactionManager implements ICachableComponent,
		IGlobalManager, IInitializeRequired {
	@Autowired
	private TemplateService templateService;
	/** 阵营信息 */
	private Map<FactionType, Faction> factions = new HashMap<FactionType, Faction>();
	@Autowired
	private CacheManager cacheManager;

	@Override
	public void loadDataFromDB(IDBService dbService) {
		// 构建数据
		Faction brightFaction = new Faction(FactionType.BRIGHT);
		Faction darkFaction = new Faction(FactionType.DARK);
		factions.put(FactionType.BRIGHT, brightFaction);
		factions.put(FactionType.DARK, darkFaction);
		// 从数据库加载数据
		@SuppressWarnings("unchecked")
		List<FactionMemberEntity> result = (List<FactionMemberEntity>) dbService
				.findByNamedQuery(DataQueryConstants.QUERY_ALL_Faction_Member);
		for (FactionMemberEntity each : result) {
			FactionType type = FactionType.typeOf(each.getFactionType());
			if (type == null) {
				continue;
			}
			FactionMember member = new FactionMember();
			member.setFactionType(each.getFactionType());
			member.setHumanGuid(each.getGuid());
			if (type == FactionType.BRIGHT) {
				brightFaction.add(member);
			} else {
				darkFaction.add(member);
			}
		}

	}

	/**
	 * 判断玩家是否加入了阵营;
	 * 
	 * @param human
	 * @return
	 */
	public boolean areadyJoinFaction(Human human) {
		for (Faction fation : factions.values()) {
			if (fation.isMyMember(human.getHumanGuid())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断玩家是否加入了阵营;
	 * 
	 * @param humanGuid
	 * @return
	 */
	public boolean areadyJoinFaction(long humanGuid) {
		for (Faction fation : factions.values()) {
			if (fation.isMyMember(humanGuid)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 随机选择一个阵营;
	 * 
	 * @param human
	 */
	public void randomSelectFaction(Human human) {
		if (areadyJoinFaction(human)) {
			return;
		}
		// 给人少的阵营添加成员,并且发奖
		Faction lessMemberFaction = getLessMemberFaction();
		// 发奖
		human.getWallet().addMoney(
				CurrencyType.COIN,
				GameServerAssist.getGameConstants()
						.getRandomSelectFactionRewardCoin(), true,
				MoneyLogReason.RANDOM_JOIN_FACTION, "");
		this.selectFaction(human, lessMemberFaction.getFactionType());
	}

	/**
	 * 获取人少的阵营;
	 * 
	 * @return
	 */
	private Faction getLessMemberFaction() {
		if (this.factions.get(FactionType.BRIGHT).size() <= this.factions.get(
				FactionType.DARK).size()) {
			return this.factions.get(FactionType.BRIGHT);
		}
		return this.getFactionByFactionType(FactionType.DARK);
	}

	/**
	 * 选择加入指定的阵营;
	 * 
	 * @param human
	 * @param factionType
	 */
	public void selectFaction(Human human, FactionType type) {
		// 是否已经加入了阵营;
		if (areadyJoinFaction(human)) {
			return;
		}
		// 取出指定阵营;
		Faction faction = getFactionByFactionType(type);
		// 添加阵营成员
		faction.addFactionMember(human);
		GCSelectFaction selectFaction = new GCSelectFaction();
		selectFaction.setFactionType(type.getIndex());
		human.sendMessage(selectFaction);
	}

	/**
	 * 取出指定的阵营信息;
	 * 
	 * @param type
	 * @return
	 */
	private Faction getFactionByFactionType(FactionType type) {
		return factions.get(type);
	}

	/**
	 * 获取光明阵营信息;
	 * 
	 * @return
	 */
	public FactionSimpleInfo getBrightInfo() {
		FactionTemplate template = templateService.get(
				FactionType.BRIGHT.getIndex(), FactionTemplate.class);
		if (template != null) {
			return template.toInfo();
		}
		return null;
	}

	/**
	 * 获取黑暗阵营信息;
	 * 
	 * @return
	 */
	public FactionSimpleInfo getDarkInfo() {
		FactionTemplate template = templateService.get(
				FactionType.DARK.getIndex(), FactionTemplate.class);
		if (template != null) {
			return template.toInfo();
		}
		return null;
	}

	/**
	 * 获取玩家的阵营类型;<br>
	 * 使用前首先使用{@link areadyJoinFaction} 方法判断是否加入了阵营;
	 * 
	 * @param human
	 * @return
	 */
	public FactionType getFactionTypeByHuman(Human human) {
		Faction myFaction = getFactionByHuman(human.getHumanGuid());
		return myFaction.getFactionType();
	}

	/**
	 * 获取玩家的阵营类型;<br>
	 * 使用前首先使用{@link areadyJoinFaction} 方法判断是否加入了阵营;
	 * 
	 * @param humanGuid
	 * @return
	 */
	public FactionType getFactionTypeByHuman(long humanGuid) {
		Faction myFaction = getFactionByHuman(humanGuid);
		return myFaction.getFactionType();
	}

	private Faction getFactionByHuman(long humanGuid) {
		for (Faction faction : factions.values()) {
			if (faction.isMyMember(humanGuid)) {
				return faction;
			}
		}
		return null;
	}

	@Override
	public List<IEntity> getUpdateEntities() {
		List<IEntity> updateList = new ArrayList<IEntity>();
		for (Faction faction : factions.values()) {
			updateList.addAll(faction.getUpdateEntities());
		}
		return updateList;
	}

	@Override
	public List<IEntity> getDeleteEntities() {
		return new ArrayList<IEntity>();
	}

	@Override
	public void init() {
		cacheManager.registerOtherCachableComponent(this);
	}

}
