package com.hifun.soul.gameserver.friend.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.common.LogReasons.FriendLogReason;
import com.hifun.soul.common.constants.GlobalKeyConstants;
import com.hifun.soul.core.orm.IDBService;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.gamedb.agent.query.DataQueryConstants;
import com.hifun.soul.gamedb.cache.CacheEntry;
import com.hifun.soul.gamedb.cache.CacheManager;
import com.hifun.soul.gamedb.cache.ICachableComponent;
import com.hifun.soul.gamedb.entity.FriendEntity;
import com.hifun.soul.gamedb.service.IDataService;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.common.log.LogService;
import com.hifun.soul.gameserver.friend.FriendInfo;
import com.hifun.soul.gameserver.friend.FriendInfos;
import com.hifun.soul.gameserver.friend.FriendRewardState;
import com.hifun.soul.gameserver.friend.converter.FriendInfosToEntityConverter;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.tencent.TencentUserInfo;
import com.hifun.soul.gameserver.timetask.manager.GlobalTimeTaskManager;

/**
 * 好友service
 */
@Scope("singleton")
@Component
public class FriendService implements IInitializeRequired,ICachableComponent{
	@Autowired
	private LogService logService;
	@Autowired
	private IDataService dataService;
	/** 玩家的好友列表 */
	private Map<Long,FriendInfos> roleIdfriends = new ConcurrentHashMap<Long,FriendInfos>();
	/** 好友信息到entity之间的转化 */
	private FriendInfosToEntityConverter converter = new FriendInfosToEntityConverter();
	@Autowired
	private CacheManager cacheManager;
	private CacheEntry<Long,IEntity> cache = new CacheEntry<Long, IEntity>();
	@Autowired
	private GlobalTimeTaskManager globalTimeTaskManager;
	
	@Override
	public void init() {
		cacheManager.registerOtherCachableComponent(this);
	}
	
	@SuppressWarnings("unchecked")
	public void start(IDBService dbService){
		// 加载所有玩家的好友信息
		List<?> friendEntitys = dbService.findByNamedQuery(DataQueryConstants.QUERY_ALL_FRIEND);
		if(friendEntitys != null
				&& !friendEntitys.isEmpty()){
			List<FriendEntity> friendEntityList = (List<FriendEntity>)friendEntitys;
			for(FriendEntity friendEntity : friendEntityList){
				FriendInfos friendInfos = converter.reverseConvert(friendEntity);
				roleIdfriends.put((Long)friendEntity.getId(),friendInfos);
			}
		}
	}
	
	public long getLastResetRewardTime() {
		return globalTimeTaskManager.getLastRunTime(GlobalKeyConstants.FRIEND_REWARD_RESET_TIME);
	}
	
	/**
	 * 重置排行榜奖励调用
	 * 
	 * @param now
	 */
	public void setLastResetRewardTime(long now) {
		globalTimeTaskManager.setLastRunTime(GlobalKeyConstants.FRIEND_REWARD_RESET_TIME, now);
	}
	
	/**
	 * 清空好友奖励
	 */
	public void resetFriendReward() {
		for(FriendInfos friendInfos : roleIdfriends.values()){
			// 清空好友奖励
			clearRewardInfos(friendInfos.getHumanId());
		}
	}
	
	/**
	 * 标记更新数据
	 * @param roleId
	 */
	private void updateFriendEntity(long roleId) {
		FriendInfos friendInfos = roleIdfriends.get(roleId);
		if(friendInfos == null){
			return;
		}
		cache.addUpdate(roleId, converter.convert(friendInfos));
	}
	
	/**
	 * 是否需要初始化好友数据
	 * @param roleId
	 * @return
	 */
	public boolean isNeedInitFriendInfos(long roleId) {
		if(roleIdfriends.get(roleId) == null){
			return true;
		}
		return false;
	}
	
	/**
	 * 初始化好友数据
	 * @param friendInfos
	 */
	public void initFriendInfos(FriendInfos friendInfos) {
		if(friendInfos == null){
			return;
		}
		roleIdfriends.put(friendInfos.getHumanId(), friendInfos);
		dataService.insert(converter.convert(friendInfos));
	}
	
	/**
	 * 判断是否已经向好友赠送过体力
	 * @param selfId
	 * @param friendId
	 * @return
	 */
	public boolean EnergyIsSended(long selfId, long friendId) {
		// 找不到好友关系就当是送过了
		if(roleIdfriends.get(selfId) == null){
			return true;
		}
		return roleIdfriends.get(selfId).getSendEnergyToOtherFriendIds().contains(friendId);
	}
	
	/**
	 * 自己的赠送次数已经用完
	 * @param selfId
	 * @return
	 */
	public boolean sendEnergyTimesIsFull(long selfId){
		// 找不到好友关系就当是送过了
		if(roleIdfriends.get(selfId) == null){
			return true;
		}
		return roleIdfriends.get(selfId).getSendEnergyToOtherFriendIds().size() >= GameServerAssist.getGameConstants().getMaxSendEnergyTimes();
	}
	
	/**
	 * 判断自己被赠送的体力次数是否已经满了
	 * @param selfId
	 * @return
	 */
	public boolean beSendedEnergyTimesIsFull(long selfId){
		// 找不到好友关系就当是送过了
		if(roleIdfriends.get(selfId) == null){
			return true;
		}
		return roleIdfriends.get(selfId).getSendEnergyToSelfFriendIds().size() >= GameServerAssist.getGameConstants().getMaxBeSendedEnergyTimes();
	}
	
	/**
	 * 赠送体力
	 * @param selfId
	 * @param friendId
	 */
	public void sendEnergy(long selfId, long friendId) {
		FriendInfos selfFiendInfos = roleIdfriends.get(selfId);
		FriendInfos otherFriendInfos = roleIdfriends.get(friendId);
		if(selfFiendInfos == null
				|| otherFriendInfos == null){
			return;
		}
		selfFiendInfos.getSendEnergyToOtherFriendIds().add(friendId);
		otherFriendInfos.getSendEnergyToSelfFriendIds().add(selfId);
		updateFriendEntity(selfId);
		updateFriendEntity(friendId);
	}
	
	/**
	 * 领取体力
	 * @param selfId
	 * @param friendId
	 */
	public void getEnergy(long selfId, long friendId) {
		FriendInfos selfFiendInfos = roleIdfriends.get(selfId);
		if(selfFiendInfos == null){
			return;
		}
		selfFiendInfos.getRecievedEnergyFriendIds().add(friendId);
		selfFiendInfos.getSendEnergyToSelfFriendIds().remove(friendId);
		updateFriendEntity(selfId);
	}
	
	/**
	 * 是否可以领取体力
	 * @param selfId
	 * @param friendId
	 * @return
	 */
	public boolean canGetEnergy(long selfId, long friendId) {
		FriendInfos selfFiendInfos = roleIdfriends.get(selfId);
		if(selfFiendInfos == null){
			return false;
		}
		if(selfFiendInfos.getSendEnergyToSelfFriendIds().contains(friendId)
				&& !selfFiendInfos.getRecievedEnergyFriendIds().contains(friendId)){
			return true;
		}
		return false;
	}
	
	/**
	 * 清空好友奖励
	 * @param selfId
	 */
	private void clearRewardInfos(long selfId) {
		FriendInfos fiendInfos = roleIdfriends.get(selfId);
		if(fiendInfos == null){
			return;
		}
		fiendInfos.getSendEnergyToOtherFriendIds().clear();
		fiendInfos.getSendEnergyToSelfFriendIds().clear();
		fiendInfos.getRecievedEnergyFriendIds().clear();
		updateFriendEntity(selfId);
	}
	
	/**
	 * 判断是否已经向玩家发出过申请
	 * @param selfId
	 * @param name
	 * @return
	 */
	public boolean isSelfApplyed(long selfId, long friendId) {
		FriendInfos friendInfos = roleIdfriends.get(friendId);
		if(friendInfos == null){
			return false;
		}
		return friendInfos.getOtherSendFriendApplyIds().contains(selfId);
	}
	
	/**
	 * 其他人是否向自己发出过申请
	 * @param selfId
	 * @param friendId
	 * @return
	 */
	public boolean isFriendApplying(long selfId, long friendId) {
		FriendInfos friendInfos = roleIdfriends.get(selfId);
		if(friendInfos == null){
			return true;
		}
		return friendInfos.getOtherSendFriendApplyIds().contains(friendId);
	}
	
	/**
	 * 发送好友请求
	 * @param selfId
	 * @param friendId
	 */
	public void sendFriendApplying(long selfId, long friendId) {
		FriendInfos otherFriendInfos = roleIdfriends.get(friendId);
		if(otherFriendInfos == null){
			return;
		}
		otherFriendInfos.getOtherSendFriendApplyIds().add(selfId);
		updateFriendEntity(friendId);
	}
	
	/**
	 * 判断roleId对应的玩家的好友申请是否已经满了
	 * @param roleId
	 * @return
	 */
	public boolean friendApplyIsFull(long roleId) {
		FriendInfos fiendInfos = roleIdfriends.get(roleId);
		if(fiendInfos == null){
			return true;
		}
		return fiendInfos.getOtherSendFriendApplyIds().size()>=GameServerAssist.getGameConstants().getFriendApplyMaxNum();
	}
	
	/**
	 * 判断是否是自己的好友
	 * @param selfId
	 * @param friendUUID
	 * @return
	 */
	public boolean isFriend(long selfId, long friendUUID) {
		FriendInfos friendInfos = this.roleIdfriends.get(selfId);
		if(friendInfos == null){
			return false;
		}
		return friendInfos.getFriendIds().contains(friendUUID);
	}
	
	/**
	 * 根据id判断好友是否存在
	 * @param roleId
	 * @param friendId
	 * @return
	 */
	public FriendInfo getFriendInfo(long roleId, long friendId) {
		// 好友数据肯定是双方的,任何一方为空都说明数据有问题
		FriendInfos selfFriendInfos = this.roleIdfriends.get(roleId);
		FriendInfos otherFriendInfos = this.roleIdfriends.get(friendId);
		if(selfFriendInfos == null
				|| otherFriendInfos == null){
			return null;
		}
		// 构造好友信息
		FriendInfo friendInfo = new FriendInfo();
		friendInfo.setRoleId(otherFriendInfos.getHumanId());
		friendInfo.setRoleName(otherFriendInfos.getHumanName());
		friendInfo.setLevel(otherFriendInfos.getHumanLevel());
		friendInfo.setOccupation(otherFriendInfos.getHumanOccupation());
		friendInfo.setSendState(selfFriendInfos.getSendEnergyToOtherFriendIds().contains(friendId));
		int getState = FriendRewardState.NO_REWARD.getIndex();
		if(selfFriendInfos.getRecievedEnergyFriendIds().contains(friendId)){
			getState = FriendRewardState.GETTED.getIndex();
		}
		else if(selfFriendInfos.getSendEnergyToSelfFriendIds().contains(friendId)){
			getState = FriendRewardState.CAN_GET.getIndex();
		}
		friendInfo.setGetState(getState);
		friendInfo.setIsOnLine(GameServerAssist.getGameWorld().getSceneHumanManager().getHumanByGuid(otherFriendInfos.getHumanId())!=null);
		TencentUserInfo txUserInfo = GameServerAssist.getTencentUserInfoManager().getTencentUserInfo(friendId);
		if(txUserInfo != null){
			friendInfo.setYellowVipLevel(txUserInfo.getYellowVipLevel());
			friendInfo.setIsYearYellowVip(txUserInfo.getIsYearYellowVip());
			friendInfo.setIsYellowHighVip(txUserInfo.getIsYellowHighVip());
		}else{
			friendInfo.setYellowVipLevel(0);
			friendInfo.setIsYearYellowVip(false);
			friendInfo.setIsYellowHighVip(false);
		}
		return friendInfo;
	}

	/**
	 * 是否好友数量已经到达上限
	 * @param selfId
	 * @param num
	 * @return
	 */
	public boolean canAddFriends(long selfId, int num){
		FriendInfos friendInfos = this.roleIdfriends.get(selfId);
		if(friendInfos == null){
			return false;
		}
		return GameServerAssist.getGameConstants().getFriendMax()>=(friendInfos.getFriendIds().size()+num);
	}
	
	/**
	 * 添加好友关系
	 * @param human
	 * @param roleId
	 * @param friendInfo
	 */
	public void addFriendInfo(Human human, long selfId, long friendId) {
		FriendInfos selfFriendInfos = this.roleIdfriends.get(selfId);
		FriendInfos otherFriendInfos = this.roleIdfriends.get(friendId);
		if(selfFriendInfos == null
				|| otherFriendInfos == null){
			return;
		}
		selfFriendInfos.getFriendIds().add(friendId);
		otherFriendInfos.getFriendIds().add(selfId);
		selfFriendInfos.getOtherSendFriendApplyIds().remove(friendId);
		updateFriendEntity(selfId);
		updateFriendEntity(friendId);
		if(human != null){
			sendFriendLogMsg(human, FriendLogReason.ADD_FRIEND, "", otherFriendInfos);
		}
	}
	
	/**
	 * 删除好友关系
	 * @param human
	 * @param selfId
	 * @param friendId
	 */
	public void removeFriendInfo(Human human, long selfId, long friendId) {
		FriendInfos selfFriendInfos = this.roleIdfriends.get(selfId);
		FriendInfos otherFriendInfos = this.roleIdfriends.get(friendId);
		if(selfFriendInfos == null
				|| otherFriendInfos == null){
			return;
		}
		selfFriendInfos.getFriendIds().remove(friendId);
		otherFriendInfos.getFriendIds().remove(selfId);
		updateFriendEntity(selfId);
		updateFriendEntity(friendId);
		if(human != null){
			sendFriendLogMsg(human, FriendLogReason.REMOVE_FRIEND, "", otherFriendInfos);
		}
	}
	
	/**
	 * 取消申请
	 * @param selfId
	 * @param friendId
	 */
	public void cancelApplying(long selfId, long friendId) {
		FriendInfos selfFriendInfos = this.roleIdfriends.get(selfId);
		FriendInfos otherFriendInfos = this.roleIdfriends.get(friendId);
		if(selfFriendInfos == null
				|| otherFriendInfos == null){
			return;
		}
		otherFriendInfos.getOtherSendFriendApplyIds().remove(selfId);
		updateFriendEntity(selfId);
		updateFriendEntity(friendId);
	}
	
	/**
	 * 拒绝别人的好友请求
	 * @param selfId
	 * @param friendId
	 */
	public void refuseApplying(long selfId, long friendId) {
		FriendInfos selfFriendInfos = this.roleIdfriends.get(selfId);
		if(selfFriendInfos == null){
			return;
		}
		selfFriendInfos.getOtherSendFriendApplyIds().remove(friendId);
		updateFriendEntity(selfId);
	}
	
	/**
	 * 更新自己的等级
	 * @param selfId
	 * @param level
	 */
	public void updateFriendLevel(long selfId, int level) {
		FriendInfos selfFriendInfos = this.roleIdfriends.get(selfId);
		if(selfFriendInfos == null){
			return;
		}
		selfFriendInfos.setHumanLevel(level);
		updateFriendEntity(selfId);
	}
	
	/**
	 * 获取玩家所有好友
	 * @return
	 */
	public Set<Long> getFriendIds(long roleId) {
		FriendInfos friendInfos = this.roleIdfriends.get(roleId);
		if(friendInfos == null){
			return null;
		}
		return friendInfos.getFriendIds();
	}
	
	/**
	 * 获取玩家所有的好友信息
	 * @param roleId
	 * @return
	 */
	public Map<Long,FriendInfo> getAllFriends(long roleId) {
		Map<Long,FriendInfo> friendInfoMap = new HashMap<Long,FriendInfo>();
		FriendInfos friendInfos = this.roleIdfriends.get(roleId);
		if(friendInfos == null){
			return null;
		}
		for(long friendId : friendInfos.getFriendIds()){
			FriendInfo friendInfo = getFriendInfo(roleId, friendId);
			if(friendInfo != null){
				friendInfoMap.put(friendId, friendInfo);
			}
		}
		return friendInfoMap;
	}
	
	/**
	 * 获取其他玩家给自己发送的好友申请
	 * @param selfId
	 * @return
	 */
	public Map<Long,FriendInfo> getAllFriendApplys(long selfId) {
		Map<Long,FriendInfo> friendApplyInfoMap = new HashMap<Long,FriendInfo>();
		FriendInfos friendInfos = this.roleIdfriends.get(selfId);
		if(friendInfos == null){
			return null;
		}
		for(long friendId : friendInfos.getOtherSendFriendApplyIds()){
			FriendInfos otherFriendInfos = this.roleIdfriends.get(friendId);
			if(otherFriendInfos == null){
				continue;
			}
			FriendInfo friendInfo = new FriendInfo();
			friendInfo.setRoleId(otherFriendInfos.getHumanId());
			friendInfo.setRoleName(otherFriendInfos.getHumanName());
			friendInfo.setLevel(otherFriendInfos.getHumanLevel());
			friendInfo.setOccupation(otherFriendInfos.getHumanOccupation());
			friendApplyInfoMap.put(friendInfo.getRoleId(), friendInfo);
		}
		return friendApplyInfoMap;
	}
	
	/**
	 * fromRoleId给toRoleId发送信息
	 * @param fromRoleId
	 * @param toRoleId
	 */
	public void updateLatestFriends(Long fromRoleId, Long toRoleId) {
		FriendInfos fromRoleFriendInfos = this.roleIdfriends.get(fromRoleId);
		FriendInfos toRoleFriendInfos = this.roleIdfriends.get(toRoleId);
		if(fromRoleFriendInfos == null
				|| toRoleFriendInfos == null){
			return;
		}
		// 判断是否需要更新各自的最近联系人
		checkLatestFriends(fromRoleFriendInfos, fromRoleId, toRoleId);
		checkLatestFriends(toRoleFriendInfos, toRoleId, fromRoleId);
	}
	
	/**
	 * 校验是否需要更新最近联系人信息
	 * @param fromRoleFriendInfos
	 * @param fromRoleId
	 * @param toRoleId
	 */
	private void checkLatestFriends(FriendInfos fromRoleFriendInfos, Long fromRoleId, Long toRoleId) {
		if(!fromRoleFriendInfos.getLatestFriendIds().contains(toRoleId)){
			if(fromRoleFriendInfos.getLatestFriendIds().size() >= GameServerAssist.getGameConstants().getMaxLatestFriendNum()){
				Long removeId = null;
				for(Long roleId : fromRoleFriendInfos.getLatestFriendIds()){
					removeId = roleId;
					break;
				}
				fromRoleFriendInfos.getLatestFriendIds().remove(removeId);
				fromRoleFriendInfos.getLatestFriendIds().add(toRoleId);
			}
			else{
				fromRoleFriendInfos.getLatestFriendIds().add(toRoleId);
			}
			// 更新好友的信息入库
			updateFriendEntity(fromRoleId);
		}
	}
	
	/**
	 * 获取最近联系人信息
	 * @param roleId
	 * @return
	 */
	public FriendInfo[] getLatestFriendInfos(long roleId) {
		List<FriendInfo> friendInfoList = new ArrayList<FriendInfo>();
		FriendInfos friendInfos = this.roleIdfriends.get(roleId);
		if(friendInfos == null
				|| friendInfos.getLatestFriendIds() == null
				|| friendInfos.getLatestFriendIds().size() <= 0){
			return new FriendInfo[0];
		}
		for(long friendId : friendInfos.getLatestFriendIds()){
			FriendInfos latestFriend = this.roleIdfriends.get(friendId);
			if(latestFriend == null){
				continue;
			}
			FriendInfo friendInfo = new FriendInfo();
			friendInfo.setRoleId(latestFriend.getHumanId());
			friendInfo.setRoleName(latestFriend.getHumanName());
			friendInfo.setLevel(latestFriend.getHumanLevel());
			friendInfo.setOccupation(latestFriend.getHumanOccupation());
			friendInfo.setIsOnLine(GameServerAssist.getGameWorld().getSceneHumanManager().getHumanByGuid(latestFriend.getHumanId())!=null);
			TencentUserInfo txUserInfo = GameServerAssist.getTencentUserInfoManager().getTencentUserInfo(friendId);
			if(txUserInfo != null){
				friendInfo.setYellowVipLevel(txUserInfo.getYellowVipLevel());
				friendInfo.setIsYearYellowVip(txUserInfo.getIsYearYellowVip());
				friendInfo.setIsYellowHighVip(txUserInfo.getIsYellowHighVip());
			}else{
				friendInfo.setYellowVipLevel(0);
				friendInfo.setIsYearYellowVip(false);
				friendInfo.setIsYellowHighVip(false);
			}			
			friendInfoList.add(friendInfo);
		}
		return friendInfoList.toArray(new FriendInfo[0]);
	}
	
	/**
	 * 发送好友日志
	 * 
	 * @param reason
	 * @param param
	 * @param friendInfo
	 */
	public void sendFriendLogMsg(Human human, FriendLogReason reason, String param, FriendInfos friendInfos) {
		if(friendInfos == null){
			return;
		}
		logService.sendFriendLog(human, reason, param, 
				human.getHumanGuid(), 
				human.getName(), 
				friendInfos.getHumanId(), 
				friendInfos.getHumanName());
	}

	@Override
	public List<IEntity> getUpdateEntities() {
		return cache.getAllUpdateData();
	}

	@Override
	public List<IEntity> getDeleteEntities() {
		return null;
	}
	
	/**
	 * 获取角色信息
	 */
	public FriendInfos getHumanInfo(long humanGuid) {
		return roleIdfriends.get(humanGuid);
	}
}
