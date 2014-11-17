package com.hifun.soul.gameserver.faction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.gamedb.cache.CacheEntry;
import com.hifun.soul.gamedb.cache.ICachableComponent;
import com.hifun.soul.gamedb.entity.FactionMemberEntity;
import com.hifun.soul.gameserver.human.Human;

/**
 * 阵营对象;
 * 
 * @author crazyjohn
 * 
 */
public class Faction implements ICachableComponent {
	/** 所有的阵营成员 */
	private Map<Long, FactionMember> members = new HashMap<Long, FactionMember>();
	/** 阵营类型 */
	private FactionType type;
	/** 緩存 */
	private CacheEntry<Long, FactionMember> memberCache = new CacheEntry<Long, FactionMember>();

	public Faction(FactionType type) {
		this.type = type;
	}

	/**
	 * 添加成员到指定的阵营;
	 * 
	 * @param human
	 */
	public void addFactionMember(Human human) {
		FactionMember member = new FactionMember();
		member.setFactionType(this.type.getIndex());
		member.setHumanGuid(human.getHumanGuid());
		this.add(member);
		// 添加到缓存
		memberCache.addUpdate(human.getHumanGuid(), member);
	}

	/**
	 * 获取阵营类型;
	 * 
	 * @return
	 */
	public FactionType getFactionType() {
		return type;
	}

	/**
	 * 当前玩家是否是本阵营成员;
	 * 
	 * @param human
	 * @return
	 */
	public boolean isMyMember(long humanGuid) {
		return members.get(humanGuid) != null;
	}

	@Override
	public List<IEntity> getUpdateEntities() {
		List<IEntity> updateList = new ArrayList<IEntity>();
		for (FactionMember member : this.memberCache.getAllUpdateData()) {
			FactionMemberEntity entity = new FactionMemberEntity();
			entity.setFactionType(member.getFactionType());
			entity.setGuid(member.getHumanGuid());
			updateList.add(entity);
		}
		return updateList;
	}

	@Override
	public List<IEntity> getDeleteEntities() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 阵营成员的数目;
	 * 
	 * @return
	 */
	public int size() {
		return members.size();
	}

	/**
	 * 添加成员;
	 * 
	 * @param member
	 */
	public void add(FactionMember member) {
		this.members.put(member.getHumanGuid(), member);
	}
}
