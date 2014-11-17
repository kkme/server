package com.hifun.soul.gameserver.friend.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.gamedb.agent.query.DataQueryConstants;
import com.hifun.soul.gamedb.callback.IDBCallback;
import com.hifun.soul.gamedb.entity.FriendBattleEntity;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.event.EventType;
import com.hifun.soul.gameserver.event.IEvent;
import com.hifun.soul.gameserver.event.IEventListener;
import com.hifun.soul.gameserver.friend.FriendBattleInfo;
import com.hifun.soul.gameserver.friend.FriendInfo;
import com.hifun.soul.gameserver.friend.FriendInfos;
import com.hifun.soul.gameserver.friend.assist.FriendAssist;
import com.hifun.soul.gameserver.friend.assist.FriendSorter;
import com.hifun.soul.gameserver.friend.converter.FriendBattleToEntityConverter;
import com.hifun.soul.gameserver.friend.msg.GCFriendApply;
import com.hifun.soul.gameserver.friend.msg.GCShowFriendApplyList;
import com.hifun.soul.gameserver.friend.msg.GCShowFriends;
import com.hifun.soul.gameserver.friend.msg.GCShowLuckFriends;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.ILoginManager;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.HumanLongProperty;
import com.hifun.soul.gameserver.role.properties.PropertyType;

/**
 * 好友管理器
 */
public class HumanFriendManager implements IEventListener,ILoginManager {
	private static Logger logger = Loggers.FRIEND_LOGGER;
	private Human human;
	/** 为了保证申请好友消息处理的原子性(中间有数据库操作),加一个标记 */
	private boolean isApplying = false;
	private LinkedList<FriendBattleInfo> friendBattleList = new LinkedList<FriendBattleInfo>();
	private FriendBattleToEntityConverter converter = new FriendBattleToEntityConverter();
	
	public HumanFriendManager(Human human) {
		this.human = human;
		this.human.registerLoginManager(this);
		human.getEventBus().addListener(EventType.LEVEL_UP_EVENT, this);
	}
	
	public Human getHuman() {
		return human;
	}
	
	/**
	 * 开始好友申请的处理
	 */
	public void startApplying() {
		this.isApplying = true;
	}
	
	/**
	 * 好友申请处理完成
	 */
	public void stopApplying() {
		this.isApplying = false;
	}
	
	/**
	 * 是否正在处理好友申请
	 * @return
	 */
	public boolean isApplying() {
		return this.isApplying;
	}

	public long getLastResetTime() {
		return human.getHumanPropertiesManager()
				.getLongPropertyValue(HumanLongProperty.LAST_FRIEND_REWARD_RESET_TIME);
	}
	
	public void setLastResetTime(long lastResetTime) {
		human.getHumanPropertiesManager()
			.setLongPropertyValue(HumanLongProperty.LAST_FRIEND_REWARD_RESET_TIME, lastResetTime);
	}
	
	/**
	 * 判断领取次数已经满了
	 * @return
	 */
	public boolean canGetEnergy() {
		return  getGetRewardTime() < GameServerAssist.getGameConstants().getFriendRewardMax();
	}
	
	public int getGetRewardTime() {
		return human.getHumanPropertiesManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(HumanIntProperty.FRIEND_REWARD_TIME);
	}
	
	public void setGetRewardTime(int time) {
		human.getHumanPropertiesManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY).setPropertyValue(HumanIntProperty.FRIEND_REWARD_TIME, time);
	}
	
	/**
	 * 重置好友奖励
	 */
	public void resetFriendReward() {
		// 重置领取体力次数
		setGetRewardTime(0);
	}
	
	/**
	 * 发送好友信息 
	 */
	public void sendFriendInfos() {
		List<FriendInfo> friendInfoList = null;
		Collection<FriendInfo> friendInfos = GameServerAssist.getFriendService().getAllFriends(human.getHumanGuid()).values();
		if(friendInfos == null){
			friendInfoList = new ArrayList<FriendInfo>();
		}
		else{
			friendInfoList = new ArrayList<FriendInfo>(friendInfos);
		}
		Collections.sort(friendInfoList,new FriendSorter());
		// 好友信息
		GCShowFriends gcMsg = new GCShowFriends();
		gcMsg.setMaxFriendNum(GameServerAssist.getGameConstants().getFriendMax());
		gcMsg.setMaxRewardNum(GameServerAssist.getGameConstants().getFriendRewardMax());
		gcMsg.setLeftRewardNum(GameServerAssist.getGameConstants().getFriendRewardMax()-getGetRewardTime());
		gcMsg.setFriendInfos(friendInfoList.toArray(new FriendInfo[0]));
		human.sendMessage(gcMsg);
	}
	
	/**
	 * 更新别人向自己发送的申请
	 */
	public void sendFriendApplyInfos() {
		List<FriendInfo> applyList = null;
		Collection<FriendInfo> characterInfos = GameServerAssist.getFriendService().getAllFriendApplys(human.getHumanGuid()).values();
		if(characterInfos == null){
			applyList = new ArrayList<FriendInfo>();
		}
		else{
			applyList = new ArrayList<FriendInfo>(characterInfos);
		}
		GCShowFriendApplyList gcMsg = new GCShowFriendApplyList();
		gcMsg.setApplyList(applyList.toArray(new FriendInfo[0]));
		human.sendMessage(gcMsg);
	}
	
	/**
	 * 推荐好友
	 */
	public void sendLuckFriendInfos() {
		// 根据配置的好友推荐数量推荐好友给玩家
		List<FriendInfo> friendInfos = FriendAssist.getRandomFriendInfos(GameServerAssist.getFriendService(), GameServerAssist.getGameWorld(),GameServerAssist.getGameConstants().getFriendRecommendNum(),human.getHumanGuid());
		if(friendInfos == null){
			friendInfos = new ArrayList<FriendInfo>();
		}
		Collections.sort(friendInfos, new FriendSorter());
		// 返回推荐的好友列表
		GCShowLuckFriends gcMsg = new GCShowLuckFriends();
		gcMsg.setFriendInfos(friendInfos.toArray(new FriendInfo[friendInfos.size()]));
		human.sendMessage(gcMsg);
	}
	
	/**
	 * 获得好友战斗信息
	 * @return
	 */
	public List<FriendBattleInfo> getFriendBattleInfos() {
		return friendBattleList;
	}

	/**
	 * 添加好友战斗信息
	 * @param friendBattleInfo
	 */
	public void addFriendBattleInfo(FriendBattleInfo friendBattleInfo) {
		if (friendBattleList.size() >= GameServerAssist.getGameConstants().getMaxFriendBattleInfoSize()) {
			friendBattleList.removeFirst();
		}
		friendBattleList.addLast(friendBattleInfo);
	}
	

	@Override
	public void onEvent(IEvent event) {
		// 升级事件,更新自己的等级
		GameServerAssist.getFriendService().updateFriendLevel(human.getHumanGuid(), human.getLevel());
	}

	@Override
	public void onLogin() {
		// 好友数据初始化
		if(GameServerAssist.getFriendService().isNeedInitFriendInfos(human.getHumanGuid())){
			FriendInfos friendInfos = new FriendInfos();
			friendInfos.setHumanId(human.getHumanGuid());
			friendInfos.setHumanName(human.getName());
			friendInfos.setHumanLevel(human.getLevel());
			friendInfos.setHumanOccupation(human.getOccupation().getIndex());
			GameServerAssist.getFriendService().initFriendInfos(friendInfos);
		}
		// 申请添加好友的玩家的信息
		Map<Long,FriendInfo> characterInfos = GameServerAssist.getFriendService().getAllFriendApplys(human.getHumanGuid());
		if(characterInfos == null){
			return;
		}
		for(FriendInfo apply : characterInfos.values())
		{
			GCFriendApply gcFriendApply = new GCFriendApply();
			gcFriendApply.setApplyer(apply);
			human.sendMessage(gcFriendApply);
		}
		// 好友切磋信息
		GameServerAssist.getDataService().query(DataQueryConstants.QUERY_FRIEND_BATTLE_BY_HUMANGUID, new String[] { "roleId" },
				new Object[] { human.getHumanGuid() }, new IDBCallback<List<?>>() {
					@SuppressWarnings("unchecked")
					@Override
					public void onSucceed(List<?> result) {
						if (!result.isEmpty()) {
							List<FriendBattleEntity> friendBattleEntitys = (List<FriendBattleEntity>) result;
							for (FriendBattleEntity friendBattleEntity : friendBattleEntitys) {
								FriendBattleInfo friendBattleInfo = converter.reverseConvert(friendBattleEntity);
								friendBattleList.addFirst(friendBattleInfo);
							}
						}
					}

					@Override
					public void onFailed(String errorMsg) {
						logger.error(errorMsg);
					}
				});
		// 发送好友信息
		sendFriendInfos();
	}
}
