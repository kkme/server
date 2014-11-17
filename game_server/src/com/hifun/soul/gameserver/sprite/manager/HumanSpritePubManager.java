package com.hifun.soul.gameserver.sprite.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.core.util.MathUtils;
import com.hifun.soul.gamedb.cache.CacheEntry;
import com.hifun.soul.gamedb.cache.ICachableComponent;
import com.hifun.soul.gamedb.entity.HumanEntity;
import com.hifun.soul.gamedb.entity.HumanPubSpriteEntity;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.event.SpritePubEvent;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.IHumanPersistenceManager;
import com.hifun.soul.gameserver.human.ILoginManager;
import com.hifun.soul.gameserver.role.properties.HumanLongProperty;
import com.hifun.soul.gameserver.sprite.FingerGuessResultType;
import com.hifun.soul.gameserver.sprite.FingerGuessType;
import com.hifun.soul.gameserver.sprite.FingerType;
import com.hifun.soul.gameserver.sprite.SpriteSoulType;
import com.hifun.soul.gameserver.sprite.model.SpritePubPageInfo;
import com.hifun.soul.gameserver.sprite.model.SpritePubInfo;
import com.hifun.soul.gameserver.sprite.msg.GCFingerGuessing;
import com.hifun.soul.gameserver.sprite.msg.GCGiveFinger;
import com.hifun.soul.gameserver.sprite.msg.GCPubPageInfo;
import com.hifun.soul.gameserver.sprite.msg.GCRecruitConfig;
import com.hifun.soul.gameserver.sprite.template.SpriteCostTemplate;
import com.hifun.soul.gameserver.sprite.template.SpriteTemplate;
import com.hifun.soul.gameserver.vip.template.VipPrivilegeTemplate;
import com.hifun.soul.proto.data.entity.Entity.HumanPubSprite;

/**
 * 玩家的精灵酒馆管理器;
 * 
 * @author crazyjohn
 * 
 */
public class HumanSpritePubManager implements IHumanPersistenceManager,
		ICachableComponent, ILoginManager {
	private Human human;
	/** 当前选中的猜拳精灵 */
	private List<SpritePubInfo> selectedSprite = new ArrayList<SpritePubInfo>();
	/** 缓存数据 */
	private CacheEntry<Integer, SpritePubInfo> cache = new CacheEntry<Integer, SpritePubInfo>();

	public HumanSpritePubManager(Human human) {
		this.human = human;
		human.registerPersistenceManager(this);
		human.registerCachableManager(this);
		human.registerLoginManager(this);
	}

	@Override
	public Human getOwner() {
		return human;
	}

	@Override
	public void onLoad(HumanEntity humanEntity) {
		for (HumanPubSprite.Builder eachSpriteBuilder : humanEntity
				.getBuilder().getPubSpiteBuilderList()) {
			SpritePubInfo eachInfo = GameServerAssist.getTemplateService()
					.get(eachSpriteBuilder.getSpriteId(), SpriteTemplate.class)
					.toSimpleInfo();
			eachInfo.setWin(eachSpriteBuilder.getPass());
			selectedSprite.add(eachInfo);
		}
	}

	@Override
	public void onPersistence(HumanEntity humanEntity) {
		for (SpritePubInfo eachInfo : selectedSprite) {
			HumanPubSprite.Builder eachBuilder = HumanPubSprite.newBuilder();
			eachBuilder.setSpriteId(eachInfo.getSpriteId());
			eachBuilder.setHumanGuid(humanEntity.getId());
			eachBuilder.setPass(eachInfo.getWin());
			humanEntity.getBuilder().addPubSpite(eachBuilder);
		}
	}

	/**
	 * 是否完成了上次对酒;
	 * 
	 * @return
	 */
	public boolean finishedLashFingerGuess() {
		return !this.canGiveFinger();
	}

	/**
	 * 构建上次未完成的对酒消息;
	 * 
	 * @return
	 */
	public GCFingerGuessing buildLastGuessMessage() {
		// 构建一个猜拳界面的消息;
		GCFingerGuessing guessMessage = new GCFingerGuessing();
		guessMessage.setIsLastGuess(true);
		guessMessage.setCurrentIndex(this.getcurrentIndex());
		guessMessage.setSelectedList(this.selectedSprite
				.toArray(new SpritePubInfo[0]));
		guessMessage.setSucceedCrystalCost(human.getHumanSpritePubManager()
				.getSucceedCrystalCost());
		guessMessage.setRemainSucceedNum(human.getHumanSpritePubManager()
				.getRemainSucceedNum());
		return guessMessage;
	}

	/**
	 * 获取当前的
	 * 
	 * @return
	 */
	private int getcurrentIndex() {
		for (int i = 0; i < this.selectedSprite.size(); i++) {
			if (!this.selectedSprite.get(i).getWin()) {
				return i;
			}
		}
		return 0;
	}

	@Override
	public void onLogin() {
		// 下发酒馆页签信息
		GCPubPageInfo pubPageMessage = new GCPubPageInfo();
		List<SpritePubPageInfo> pageInfoList = GameServerAssist
				.getSpriteTemplateManager().getAllPubPages();
		pubPageMessage.setCommonOperationInfo(pageInfoList
				.toArray(new SpritePubPageInfo[0]));
		pubPageMessage.setSucceedCrystalCost(getSucceedCrystalCost());
		human.sendMessage(pubPageMessage);
		// 下发招募页签配置信息
		GCRecruitConfig recruitConfigMessage = new GCRecruitConfig();
		recruitConfigMessage.setConfigList(GameServerAssist
				.getSpriteTemplateManager().getRecruitConfigList());
		human.sendMessage(recruitConfigMessage);
	}

	/**
	 * 构建对酒精灵列表;
	 * 
	 * @param idToSpriteTemplate
	 * @return
	 */
	public List<SpritePubInfo> buildFingerGuessSpriteList(
			Map<Integer, SpriteTemplate> idToSpriteTemplate) {
		// 先清理
		this.doClear();
		for (SpriteTemplate each : idToSpriteTemplate.values()) {
			SpritePubInfo info = each.toSimpleInfo();
			selectedSprite.add(info);
			// 添加到缓存
			cache.addUpdate(each.getId(), info);
		}
		return selectedSprite;
	}

	/**
	 * 是否可以出拳;
	 * 
	 * @return
	 */
	public boolean canGiveFinger() {
		return selectedSprite.size() > 0;
	}

	/**
	 * 开始本轮猜拳;
	 * 
	 * @param fingerType
	 */
	public void startFingerGuessRound(FingerType fingerType) {
		// 取出当前精灵
		SpritePubInfo currentSprite = getCurrentSprite();
		// 取出精灵的出拳类型
		FingerType spriteFingerType = giveAFinger(currentSprite);
		// 給出結果
		FingerGuessResultType resultType = fingerType
				.giveResult(spriteFingerType);
		// 是否猜拳胜利
		GCGiveFinger giveFingerMessage = new GCGiveFinger();
		// 猜拳结果
		int result;
		boolean isOver = false;
		int rewardNum = 0;
		if (resultType == FingerGuessResultType.WIN) {
			// 设置已经猜拳了
			currentSprite.setWin(true);
			// 添加到缓存
			cache.addUpdate(currentSprite.getSpriteId(), currentSprite);
			result = FingerGuessResultType.WIN.getIndex();
			rewardNum = currentSprite.getSoulNum();
			isOver = !haveAnySpriteToFingerGuess();
			addSoulByType(currentSprite);

		} else if (resultType == FingerGuessResultType.LOSE) {
			// lose, 猜拳结束，清理数据
			isOver = true;
			result = FingerGuessResultType.LOSE.getIndex();
		} else {
			result = FingerGuessResultType.DRAW.getIndex();
		}
		// 如果對酒結束
		int returnMoney = 0;
		if (isOver) {
			returnMoney = returnMoney();
			// 清理数据
			doClear();
			// 发送事件
			human.getEventBus().fireEvent(new SpritePubEvent());
		}
		giveFingerMessage.setYourFingerType(fingerType.getIndex());
		giveFingerMessage.setSpriteFingerType(spriteFingerType.getIndex());
		giveFingerMessage.setIsGuessOver(isOver);
		giveFingerMessage.setResult(result);
		giveFingerMessage.setRewardSoulType(currentSprite.getSoulType());
		giveFingerMessage.setRewardSoulNum(rewardNum);
		giveFingerMessage.setSucceedCrystalCost(this.getSucceedCrystalCost());
		giveFingerMessage.setRemainSucceedNum(getRemainSucceedNum());
		giveFingerMessage.setReturnMoney(returnMoney);
		human.sendMessage(giveFingerMessage);
	}

	/**
	 * 根据胜利次数返回金钱
	 */
	private int returnMoney() {
		int winTimes = 0;
		for (SpritePubInfo spriteInfo : selectedSprite) {
			if (spriteInfo.getWin()) {
				winTimes++;
			}
		}
		FingerGuessType guessType = FingerGuessType.typeOf(human
				.getSpritePubGuessType());
		if (guessType == null) {
			return 0;
		}
		SpriteCostTemplate pageTemplate = GameServerAssist.getTemplateService()
				.get(selectedSprite.get(0).getPubPageId(),
						SpriteCostTemplate.class);
		if (pageTemplate == null) {
			return 0;
		}
		int returnRate = 0;
		if (winTimes == 0) {
			returnRate = pageTemplate.getZeroTimeReturnRate();
		} else if (winTimes == 1) {
			returnRate = pageTemplate.getOneTimeReturnRate();
		} else if (winTimes == 2) {
			returnRate = pageTemplate.getTwoTimesReturnRate();
		}
		CurrencyType currencyType = guessType.getCurrencyType(pageTemplate);
		int costCount = guessType.getCostNum(pageTemplate);
		int returnMoney = (int) (costCount * returnRate / SharedConstants.DEFAULT_FLOAT_BASE);
		if (returnMoney < 0) {
			return 0;

		}
		human.getWallet().addMoney(currencyType, returnMoney, false,
				MoneyLogReason.FINGER_GUESS, "");
		return returnMoney;
	}

	/**
	 * 对酒必胜消费
	 * 
	 * @return
	 */
	public int getSucceedCrystalCost() {
		return GameServerAssist.getVipPrivilegeTemplateManager()
				.getSpritePubSucceedCost(human.getSpritePubWinUsedNum() + 1,
						human.getVipLevel());
	}

	/**
	 * 剩余对酒必胜次数
	 * 
	 * @return
	 */
	public int getRemainSucceedNum() {
		VipPrivilegeTemplate vipTemplate = GameServerAssist
				.getVipPrivilegeTemplateManager().getVipTemplate(
						human.getVipLevel());
		return vipTemplate.getMaxSpritePubCertainWinTimes()
				- human.getSpritePubWinUsedNum();
	}

	/**
	 * 根据类型添加精魂;
	 * 
	 * @param currentSprite
	 */
	private void addSoulByType(SpritePubInfo currentSprite) {
		// 取出精魂类型
		SpriteSoulType soulType = SpriteSoulType.typeOf(currentSprite
				.getSoulType());
		// 添加精魂
		soulType.doAddSpriteNum(human, currentSprite.getSoulNum());
	}

	/**
	 * 清理;
	 */
	private void doClear() {
		for (SpritePubInfo eachInfo : this.selectedSprite) {
			this.cache.addDelete(eachInfo.getSpriteId(), eachInfo);
		}
		this.selectedSprite.clear();
	}

	/**
	 * 开始必胜猜拳;
	 */
	public void startSucceedFingerGuessRound() {
		// 3. 给出胜利响应
		// 取出当前精灵
		SpritePubInfo currentSprite = getCurrentSprite();
		currentSprite.setWin(true);
		// 添加精魂
		addSoulByType(currentSprite);
		// 取出精灵的出拳类型
		FingerType spriteFingerType = giveAFinger(currentSprite);
		// 胜利出拳类型
		FingerType yourFingerType = spriteFingerType.getBigType();
		// 消耗必胜次数
		human.setSpritePubWinUsedNum(human.getSpritePubWinUsedNum() + 1);
		GCGiveFinger giveFingerMessage = new GCGiveFinger();
		boolean isOver = !haveAnySpriteToFingerGuess();
		giveFingerMessage.setIsGuessOver(isOver);
		giveFingerMessage.setResult(FingerGuessResultType.WIN.getIndex());
		giveFingerMessage.setRewardSoulType(currentSprite.getSoulNum());
		giveFingerMessage.setRewardSoulNum(currentSprite.getSoulNum());
		giveFingerMessage.setSpriteFingerType(spriteFingerType.getIndex());
		giveFingerMessage.setYourFingerType(yourFingerType.getIndex());
		giveFingerMessage.setSucceedCrystalCost(this.getSucceedCrystalCost());
		giveFingerMessage.setRemainSucceedNum(getRemainSucceedNum());
		human.sendMessage(giveFingerMessage);
		// 如果對酒結束
		if (isOver) {
			// 清理数据
			doClear();
		}
	}

	/**
	 * 是否还有没猜拳的精灵;
	 * 
	 * @return
	 */
	private boolean haveAnySpriteToFingerGuess() {
		return getCurrentSprite() != null;
	}

	/**
	 * 精灵出拳;
	 * 
	 * @param currentSprite
	 * @return
	 */
	private FingerType giveAFinger(SpritePubInfo currentSprite) {
		// TODO: crazyjohn 以后给精灵加获胜的概率;
		return MathUtils.random(FingerType.class);
	}

	private SpritePubInfo getCurrentSprite() {
		for (SpritePubInfo eachSprite : selectedSprite) {
			// 返回没有参加过猜拳的精灵
			if (!eachSprite.getWin()) {
				return eachSprite;
			}
		}
		return null;
	}

	@Override
	public List<IEntity> getUpdateEntities() {
		List<IEntity> updateList = new ArrayList<IEntity>();
		for (SpritePubInfo each : this.cache.getAllUpdateData()) {
			HumanPubSpriteEntity entity = new HumanPubSpriteEntity();
			entity.getBuilder().setHumanGuid(this.human.getHumanGuid());
			entity.getBuilder().setSpriteId(each.getSpriteId());
			entity.getBuilder().setPass(each.getWin());
			updateList.add(entity);
		}
		return updateList;
	}

	@Override
	public List<IEntity> getDeleteEntities() {
		List<IEntity> deleteList = new ArrayList<IEntity>();
		for (SpritePubInfo each : this.cache.getAllDeleteData()) {
			HumanPubSpriteEntity entity = new HumanPubSpriteEntity();
			entity.getBuilder().setHumanGuid(this.human.getHumanGuid());
			entity.getBuilder().setSpriteId(each.getSpriteId());
			entity.getBuilder().setPass(each.getWin());
			deleteList.add(entity);
		}
		return deleteList;
	}

	public void setLastResetDailyDataTime(long lastTime) {
		human.getHumanPropertiesManager().setLongPropertyValue(
				HumanLongProperty.LAST_RESET_SPRITE_PUB_DATA_TIME, lastTime);
	}

	public long getLastResetDailyDataTime() {
		return human.getHumanPropertiesManager().getLongPropertyValue(
				HumanLongProperty.LAST_RESET_SPRITE_PUB_DATA_TIME);
	}

	/**
	 * 重置酒馆日常数据
	 */
	public void resetPubDailyData() {
		human.setSpritePubWinUsedNum(0);
	}
}
