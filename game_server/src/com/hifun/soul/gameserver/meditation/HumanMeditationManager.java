package com.hifun.soul.gameserver.meditation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.LogReasons.TechPointLogReason;
import com.hifun.soul.common.LogReasons.TrainCoinLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gamedb.cache.CacheEntry;
import com.hifun.soul.gamedb.cache.ICachableComponent;
import com.hifun.soul.gamedb.entity.HumanEntity;
import com.hifun.soul.gamedb.entity.HumanMeditationEntity;
import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.cd.manager.HumanCdManager;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.currency.manager.IWallet;
import com.hifun.soul.gameserver.friend.FriendInfo;
import com.hifun.soul.gameserver.friend.assist.FriendAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.IHumanPersistenceManager;
import com.hifun.soul.gameserver.human.ILoginManager;
import com.hifun.soul.gameserver.meditation.msg.GCStartMeditation;
import com.hifun.soul.gameserver.meditation.msg.GCUpdateMeditationAssistPosition;
import com.hifun.soul.gameserver.meditation.msg.GCUpdateMeditationState;
import com.hifun.soul.gameserver.meditation.msg.MeditationFriendAssistInfo;
import com.hifun.soul.gameserver.meditation.template.MeditationInfo;
import com.hifun.soul.gameserver.meditation.template.MeditationModeTemplate;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.PropertyType;
import com.hifun.soul.proto.data.entity.Entity.HumanMeditation;
import com.hifun.soul.proto.data.entity.Entity.MeditationAssistFriend;

/**
 * 冥想管理器
 * 
 * @author magicstone
 * 
 */
public class HumanMeditationManager implements IHumanPersistenceManager, ICachableComponent, ILoginManager {
	private static final int DEFAULT_ASSIST_POSITION_NUM = 1;
	private Human human;
	/** 协助中的好友 */
	private Map<Long, MeditationAssistFriend> assistFriendMap = new ConcurrentHashMap<Long, MeditationAssistFriend>();
	private int meditatiomState;
	private int selectedModeIndex;
	private int selectedTimeIndex;
	private int assistPositionNum = DEFAULT_ASSIST_POSITION_NUM; //默认一个协助位开启
	private long startTime;

	private CacheEntry<Long, HumanMeditationEntity> cache = new CacheEntry<Long, HumanMeditationEntity>();

	public HumanMeditationManager(Human human) {
		this.human = human;
		this.human.registerPersistenceManager(this);
		this.human.registerCachableManager(this);
		this.human.registerLoginManager(this);
		init();
	}

	private void init() {
		this.meditatiomState = MeditationState.STOP;
		this.selectedModeIndex = 0;
		this.selectedTimeIndex = 0;
	}

	public void reset() {
		this.meditatiomState = MeditationState.STOP;
		HumanCdManager cdManager = human.getHumanCdManager();
		cdManager.removeCdFree(CdType.MEDITATION);
		cdManager.snapCdQueueInfo(CdType.MEDITATION);
		assistFriendMap.clear();
		cache.addUpdate(human.getHumanGuid(), this.convertToEntity());
	}

	/**
	 * 设置剩余协助好友次数
	 */
	private void setRemainAssistMeditatiomTimes(int leftTimes) {
		human.getPropertyManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.setPropertyValue(HumanIntProperty.LEFT_ASSIST_MEDITATION_TIMES, leftTimes);
	}

	/**
	 * 获取剩余协助好友次数
	 * 
	 * @return
	 */
	public int getRemainAssistMeditatiomTimes() {
		return human.getPropertyManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.getPropertyValue(HumanIntProperty.LEFT_ASSIST_MEDITATION_TIMES);
	}

	/**
	 * 重设剩余有报酬的协助次数
	 */
	public void resetRemainAssistMeditationTimes() {
		setRemainAssistMeditatiomTimes(GameServerAssist.getGameConstants().getHasRewardMeditationAssist());
	}

	/**
	 * 获得协助奖励
	 */
	public void acceptAssistReward() {
		int remainTimes = getRemainAssistMeditatiomTimes();
		int maxHasRewardAssistTime = GameServerAssist.getGameConstants().getHasRewardMeditationAssist();
		if(remainTimes>maxHasRewardAssistTime){
			remainTimes = maxHasRewardAssistTime;
			setRemainAssistMeditatiomTimes(remainTimes);			
		}
		if ( remainTimes <= 0) {
			human.sendSuccessMessage(LangConstants.ASSIST_MEDITATION_NO_REWARD);
			return;
		}
		setRemainAssistMeditatiomTimes(remainTimes - 1);
		int assistReward = GameServerAssist.getMeditationTemplateManager().getAssistReward(human.getLevel());
		human.getHumanTechnologyManager().addTechnologyPoints(assistReward,
				true, TechPointLogReason.ASSIST_MEDITATION_ADD_TECH_POINT, "");
	}

	/**
	 * 获取好友协助位数量
	 * 
	 * @return
	 */
	public int getAssistPositionNum() {
		return assistPositionNum;
	}

	/**
	 * 增加好友协助位
	 */
	public void addAssistPosition() {
		this.assistPositionNum++;
		cache.addUpdate(human.getHumanGuid(), this.convertToEntity());
	}

	/**
	 * 获取协助好友
	 * 
	 * @return
	 */
	public Collection<MeditationAssistFriend> getAssistFriends() {
		return assistFriendMap.values();
	}

	/**
	 * 是否已在协助队列中
	 * 
	 * @param friendId
	 * @return
	 */
	public boolean isInAssistFriendQueue(long friendId) {
		return assistFriendMap.containsKey(friendId);
	}

	public int getMeditatiomState() {
		updateMeditationState();
		return meditatiomState;
	}

	/**
	 * 刷新冥想状态
	 */
	private void updateMeditationState() {
		if (meditatiomState == MeditationState.INPROGRESS) {
			HumanCdManager cdManager = human.getHumanCdManager();
			if (cdManager.canAddCd(CdType.MEDITATION, 0)) {
				meditatiomState = MeditationState.FINISH;
			}
		}
	}

	public int getSelectedModeIndex() {
		return selectedModeIndex;
	}

	public int getSelectedTimeIndex() {
		return selectedTimeIndex;
	}

	/**
	 * 转化为entity
	 * 
	 * @return
	 */
	private HumanMeditationEntity convertToEntity() {
		HumanMeditation.Builder builder = HumanMeditation.newBuilder();
		builder.addAllAssistFriends(this.assistFriendMap.values());
		builder.setAssistPositionNum(this.assistPositionNum);
		builder.setHumanGuid(human.getHumanGuid());
		builder.setMeditatiomState(this.meditatiomState);
		builder.setSelectedModeIndex(this.selectedModeIndex);
		builder.setSelectedTimeIndex(this.getSelectedTimeIndex());
		builder.setStartTime(this.startTime);
		HumanMeditationEntity entity = new HumanMeditationEntity(builder);
		return entity;
	}

	@Override
	public Human getOwner() {
		return human;
	}

	@Override
	public void onLoad(HumanEntity humanEntity) {
		HumanMeditation meditation = humanEntity.getBuilder().getMeditation();
		if (meditation != null) {
			for (MeditationAssistFriend assistFriend : meditation.getAssistFriendsList()) {
				assistFriendMap.put(assistFriend.getAssistFriendId(), assistFriend);
			}
			if(meditation.getAssistPositionNum()>DEFAULT_ASSIST_POSITION_NUM){
				this.assistPositionNum = meditation.getAssistPositionNum();
			}			
			this.meditatiomState = meditation.getMeditatiomState();
			this.selectedModeIndex = meditation.getSelectedModeIndex();
			this.selectedTimeIndex = meditation.getSelectedTimeIndex();
			this.startTime = meditation.getStartTime();
		}		
		updateMeditationState();
	}

	@Override
	public void onPersistence(HumanEntity humanEntity) {
		HumanMeditation.Builder builder = HumanMeditation.newBuilder();
		builder.addAllAssistFriends(this.assistFriendMap.values());
		builder.setAssistPositionNum(this.assistPositionNum);
		builder.setHumanGuid(human.getHumanGuid());
		builder.setMeditatiomState(this.meditatiomState);
		builder.setSelectedModeIndex(this.selectedModeIndex);
		builder.setSelectedTimeIndex(this.getSelectedTimeIndex());
		builder.setStartTime(this.startTime);
		humanEntity.getBuilder().setMeditation(builder);
	}

	@Override
	public List<IEntity> getUpdateEntities() {
		List<IEntity> updateList = new ArrayList<IEntity>();
		for (HumanMeditationEntity entity : cache.getAllUpdateData()) {
			updateList.add(entity);
		}
		return updateList;
	}

	@Override
	public List<IEntity> getDeleteEntities() {
		return null;
	}

	public int getCurrentTechPoint() {
		updateMeditationState();
		if (this.meditatiomState == MeditationState.STOP) {
			return 0;
		}
		MeditationInfo timeMode = GameServerAssist.getMeditationTemplateManager().getMeditationTimeMode(human.getLevel(), this.selectedTimeIndex);
		float modeRate = GameServerAssist.getMeditationTemplateManager().getAllMeditatiomMode()[selectedModeIndex].getRate()
				/ SharedConstants.DEFAULT_FLOAT_BASE;
		int totalReward = 0;
		long endTime = GameServerAssist.getSystemTimeService().now();
		long timeUnit = GameServerAssist.getGameConstants().getMeditationTimeUnit() * TimeUtils.SECOND;
		long timeUnitNum = 0;
		if (this.meditatiomState == MeditationState.FINISH) {
			timeUnitNum = timeMode.getMeditationTime() * TimeUtils.MIN / timeUnit;
			totalReward = (int) (timeMode.getUnitTechPoint() * timeUnitNum * modeRate);
			endTime = timeMode.getMeditationTime() * TimeUtils.MIN + startTime;
		} else if (this.meditatiomState == MeditationState.INPROGRESS) {
			timeUnitNum = (endTime - this.startTime) / timeUnit;
			totalReward = (int) (timeMode.getUnitTechPoint() * timeUnitNum * modeRate);
		}
		for (MeditationAssistFriend assistFriend : this.getAssistFriends()) {
			timeUnitNum = (endTime - assistFriend.getAssistBeginTime()) / timeUnit;
			totalReward += timeMode.getUnitTechPoint()
					* timeUnitNum
					* (GameServerAssist.getMeditationTemplateManager().getAssistRate(assistFriend.getPositionIndex() + 1) / SharedConstants.DEFAULT_FLOAT_BASE);
		}
		return totalReward;
	}
	
	public int getTotalTechPoint() {
		if (this.meditatiomState == MeditationState.STOP) {
			return 0;
		}
		return getTotalTechPointByMode(selectedModeIndex,selectedTimeIndex,startTime);
	}
	
	/**
	 * 根据选择的模式返回可以收获的总科技点，为了不影响原来的逻辑，所以跟getTotalTechPoint()处理分开
	 * @param modeIndex
	 * @param timeIndex
	 * @param time
	 * @return
	 */
	public int getTotalTechPointByMode(int modeIndex, int timeIndex, long time) {
		MeditationInfo timeMode = GameServerAssist.getMeditationTemplateManager().getMeditationTimeMode(human.getLevel(), timeIndex);
		if(timeMode==null){
			return 0;
		}
		MeditationModeTemplate[] allMode = GameServerAssist.getMeditationTemplateManager().getAllMeditatiomMode();
		if(allMode.length<=modeIndex){
			return 0;
		}
		float modeRate = allMode[modeIndex].getRate()
				/ SharedConstants.DEFAULT_FLOAT_BASE;
		int totalReward = 0;		
		long timeUnit = GameServerAssist.getGameConstants().getMeditationTimeUnit() * TimeUtils.SECOND;		
		long timeUnitNum = timeMode.getMeditationTime() * TimeUtils.MIN / timeUnit;
		totalReward = (int) (timeMode.getUnitTechPoint() * timeUnitNum * modeRate);
		long endTime = timeMode.getMeditationTime() * TimeUtils.MIN + time;
		for (MeditationAssistFriend assistFriend : this.getAssistFriends()) {
			timeUnitNum = (endTime - assistFriend.getAssistBeginTime()) / timeUnit;
			totalReward += timeMode.getUnitTechPoint()
					* timeUnitNum
					* (GameServerAssist.getMeditationTemplateManager().getAssistRate(assistFriend.getPositionIndex() + 1) / SharedConstants.DEFAULT_FLOAT_BASE);
		}
		totalReward = totalReward>0 ? totalReward : 0;
		return totalReward;
	}
	
	/**
	 * 开始冥想
	 */
	public void startMeditation(int selectedModeIndex, int selectedTimeIndex) {
		if (selectedModeIndex < 0 || selectedTimeIndex < 0) {
			return;
		}
		if(this.getMeditatiomState()!=MeditationState.STOP){
			return;
		}
		// 判断冥想模式和时间模式是否开启
		if(!GameServerAssist.getMeditationTemplateManager()
				.checkFuncIsOpen(human,selectedModeIndex,selectedTimeIndex)){			
			return;
		}
		HumanCdManager cdManager = human.getHumanCdManager();
		if (!cdManager.canAddCd(CdType.MEDITATION, 0)) {
			return;
		}
		MeditationInfo timeMode = GameServerAssist.getMeditationTemplateManager().getMeditationTimeMode(human.getLevel(), selectedTimeIndex);
		if(timeMode==null){
			return;
		}
		IWallet walllet = human.getWallet();
		MeditationModeTemplate[] allMode = GameServerAssist.getMeditationTemplateManager().getAllMeditatiomMode();
		if(selectedModeIndex>=allMode.length){
			return;
		}
		MeditationModeTemplate selectedMode = allMode[selectedModeIndex];
		// 消耗培养币特殊处理
		MeditationCurrencyType timeType = MeditationCurrencyType.indexOf(timeMode.getCurrencyType());
		MeditationCurrencyType modeType = MeditationCurrencyType.indexOf(selectedMode.getCurrencyType());
		if(timeType == MeditationCurrencyType.TRAIN_COIN && modeType == MeditationCurrencyType.TRAIN_COIN){
			int costNum = timeMode.getCurrencyNum() + selectedMode.getCurrencyNum();
			if(human.getTrainCoin()<costNum){
				String trainCoinDesc = GameServerAssist.getSysLangService().read(LangConstants.TRAIN_COIN);
				human.sendErrorMessage(LangConstants.COMMON_NOT_ENOUGH,trainCoinDesc);
				return;
			}
			human.costTrainCoin(costNum, TrainCoinLogReason.MEDITATION, "");
		}else if(timeType == MeditationCurrencyType.TRAIN_COIN && modeType != MeditationCurrencyType.TRAIN_COIN){
			int costTrainCoin = timeMode.getCurrencyNum();
			if(human.getTrainCoin()<costTrainCoin){
				String trainCoinDesc = GameServerAssist.getSysLangService().read(LangConstants.TRAIN_COIN);
				human.sendErrorMessage(LangConstants.COMMON_NOT_ENOUGH,trainCoinDesc);
				return;
			}
			human.costTrainCoin(costTrainCoin, TrainCoinLogReason.MEDITATION, "");
			int costMoney = selectedMode.getCurrencyNum();
			CurrencyType currencyType = CurrencyType.indexOf(selectedMode.getCurrencyType());
			if(!walllet.isEnough(currencyType, costMoney)){
				human.sendErrorMessage(LangConstants.COMMON_NOT_ENOUGH, currencyType.getDesc());
				return;
			}
			walllet.costMoney(currencyType, costMoney, MoneyLogReason.MEDITATION, "");
		}else if(timeType != MeditationCurrencyType.TRAIN_COIN && modeType == MeditationCurrencyType.TRAIN_COIN){
			int costTrainCoin = selectedMode.getCurrencyNum();
			if(human.getTrainCoin()<costTrainCoin){
				String trainCoinDesc = GameServerAssist.getSysLangService().read(LangConstants.TRAIN_COIN);
				human.sendErrorMessage(LangConstants.COMMON_NOT_ENOUGH,trainCoinDesc);
				return;
			}
			human.costTrainCoin(costTrainCoin, TrainCoinLogReason.MEDITATION, "");
			int costMoney = timeMode.getCurrencyNum();
			CurrencyType currencyType = CurrencyType.indexOf(timeMode.getCurrencyType());
			if(!walllet.isEnough(currencyType, costMoney)){
				human.sendErrorMessage(LangConstants.COMMON_NOT_ENOUGH, currencyType.getDesc());
				return;
			}
			walllet.costMoney(currencyType, costMoney, MoneyLogReason.MEDITATION, "");
		}else{
			CurrencyType timeModeCurrencyType = CurrencyType.indexOf(timeMode.getCurrencyType());
			if (!walllet.isEnough(timeModeCurrencyType, timeMode.getCurrencyNum())) {
				human.sendWarningMessage(LangConstants.COMMON_NOT_ENOUGH, timeModeCurrencyType.getDesc());
				return;
			}
			CurrencyType modeCurrencyType = CurrencyType.indexOf(selectedMode.getCurrencyType());
			int modeCurrencyNum = selectedMode.getCurrencyNum();
			if (modeCurrencyType == timeModeCurrencyType) {
				modeCurrencyNum += timeMode.getCurrencyNum();
			}
			if (!walllet.isEnough(modeCurrencyType, modeCurrencyNum)) {
				human.sendErrorMessage(LangConstants.COMMON_NOT_ENOUGH, modeCurrencyType.getDesc());
				return;
			}
			walllet.costMoney(timeModeCurrencyType, timeMode.getCurrencyNum(), MoneyLogReason.MEDITATION, "");
			walllet.costMoney(modeCurrencyType, selectedMode.getCurrencyNum(), MoneyLogReason.MEDITATION, "");
		}
		this.meditatiomState = MeditationState.INPROGRESS;
		this.selectedModeIndex = selectedModeIndex;
		this.selectedTimeIndex = selectedTimeIndex;
		this.startTime = GameServerAssist.getSystemTimeService().now();
		long cdTime = GameServerAssist.getMeditationTemplateManager().getMeditationTimeMode(human.getLevel(), this.selectedTimeIndex)
				.getMeditationTime() * TimeUtils.MIN;
		cdManager.addCd(CdType.MEDITATION, cdTime);
		cdManager.snapCdQueueInfo(CdType.MEDITATION);
		cache.addUpdate(human.getHumanGuid(), this.convertToEntity());
		
		GCStartMeditation gcMsg = new GCStartMeditation();
		gcMsg.setTotalPoint(getTotalTechPoint());
		human.sendMessage(gcMsg);
	}

	/**
	 * 接收到好友协助
	 * 
	 * @param friendId
	 * @return 是否成功接受协助
	 */
	public boolean receivedFriendAssist(Human friend) {
		if (assistFriendMap.size() >= this.assistPositionNum) {
			return false;
		}
		MeditationAssistFriend.Builder assistFriend = MeditationAssistFriend.newBuilder();
		assistFriend.setAssistFriendId(friend.getHumanGuid());
		assistFriend.setPositionIndex(this.assistFriendMap.size());
		assistFriend.setAssistBeginTime(GameServerAssist.getSystemTimeService().now());
		this.assistFriendMap.put(friend.getHumanGuid(), assistFriend.build());
		GCUpdateMeditationAssistPosition gcUpdateAssistMsg = new GCUpdateMeditationAssistPosition();
		MeditationFriendAssistInfo friendAssist = new MeditationFriendAssistInfo();
		friendAssist.setIndex(assistFriend.getPositionIndex());
		FriendInfo friendInfo = FriendAssist.getFriendInfo(friend);
		friendAssist.setFriendInfo(friendInfo);
		friendAssist.setAssistRate(GameServerAssist.getMeditationTemplateManager().getAssistRate(assistFriend.getPositionIndex() + 1));
		friendAssist.setTotalTechPoint(getTotalTechPoint());
		gcUpdateAssistMsg.setFriendAssistInfo(friendAssist);		
		human.sendMessage(gcUpdateAssistMsg);
		cache.addUpdate(human.getHumanGuid(), this.convertToEntity());
		return true;
	}

	@Override
	public void onLogin() {
		GCUpdateMeditationState gcMsg = new GCUpdateMeditationState();
		gcMsg.setMeditationState(this.getMeditatiomState());
		human.sendMessage(gcMsg);		
	}

}
