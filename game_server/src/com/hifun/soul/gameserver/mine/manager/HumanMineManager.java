package com.hifun.soul.gameserver.mine.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.LogReasons.TrainCoinLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.core.util.MathUtils;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gamedb.cache.CacheEntry;
import com.hifun.soul.gamedb.cache.ICachableComponent;
import com.hifun.soul.gamedb.entity.HumanEntity;
import com.hifun.soul.gamedb.entity.HumanMineEntity;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.cd.manager.HumanCdManager;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.event.MineEvent;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.IHumanPersistenceManager;
import com.hifun.soul.gameserver.item.Item;
import com.hifun.soul.gameserver.item.ItemDetailType;
import com.hifun.soul.gameserver.item.assist.ItemFactory;
import com.hifun.soul.gameserver.legion.enums.LegionTechnologyType;
import com.hifun.soul.gameserver.mine.MineFieldInfo;
import com.hifun.soul.gameserver.mine.MineFieldModel;
import com.hifun.soul.gameserver.mine.MineHarvestType;
import com.hifun.soul.gameserver.mine.msg.GCBuyMineTimes;
import com.hifun.soul.gameserver.mine.msg.GCMineFieldList;
import com.hifun.soul.gameserver.mine.msg.GCMineHarvest;
import com.hifun.soul.gameserver.mine.msg.GCOpenMineField;
import com.hifun.soul.gameserver.mine.msg.GCOpenMineFieldBattleRequest;
import com.hifun.soul.gameserver.mine.msg.GCResetMineField;
import com.hifun.soul.gameserver.mine.msg.GCUpdateMinePannel;
import com.hifun.soul.gameserver.mine.template.MineConsumeTemplate;
import com.hifun.soul.gameserver.mine.template.MineFieldTypeTemplate;
import com.hifun.soul.gameserver.mine.template.MineLevelTemplate;
import com.hifun.soul.gameserver.monster.template.MonsterTemplate;
import com.hifun.soul.gameserver.vip.template.VipPrivilegeTemplate;
import com.hifun.soul.proto.common.Mine.HumanMineInfo;
import com.hifun.soul.proto.common.Mine.MineField;

/**
 * 矿场管理器
 * 
 * @author magicstone
 * 
 */
public class HumanMineManager implements IHumanPersistenceManager, ICachableComponent{
	public static final int MAX_MINE_FIELD_NUM = 9;
	private static Logger logger = Loggers.MINE_LOGGER;
	private Map<Integer, MineFieldModel> mineFields = new HashMap<Integer, MineFieldModel>();
	private Human human;
	/** 购买矿坑开采权次数 */
	private int buyMineFieldNum;
	/** 重置矿坑次数 */
	private int resetMineFieldNum;
	/** 剩余开采次数 */
	private int remainFreeMineNum;
	/** 花钱购买的开采次数 */
	private int remainBoughtMineNum;

	/** 当前矿坑类型 */
	private int currentFieldtype = 0;
	/** 当前矿坑位置索引 */
	private int currentIndex = -1;
	/** 当前怪物id */
	private int currenctMonsterId = 0;
	/** 是否有矿坑正在开启中 */
	private boolean mineOpening;
	/** 缓存 */
	private CacheEntry<Long, HumanMineEntity> cache = new CacheEntry<Long, HumanMineEntity>();
	/** 矿坑类型 */
	private static final CdType[] mineTypes = new CdType[]{CdType.MINE_1,CdType.MINE_2,CdType.MINE_3,
		CdType.MINE_4,CdType.MINE_5,CdType.MINE_6,CdType.MINE_7,CdType.MINE_8,CdType.MINE_9};

	public HumanMineManager(Human human) {
		this.human = human;
		human.registerPersistenceManager(this);
		human.registerCachableManager(this);
	}

	/**
	 * 开启矿坑
	 * 
	 * @param index
	 */
	public void openMineField(int index) {
		if (mineFieldIsOpen(index)) {
			return;
		}
		if (mineOpening) {
			// 正遭遇怪物，不能开启其他矿坑
			return;
		}
		if (getRemainMineNum() <= 0) {
			if (!buyMineTimes()) {
				return;
			}
		}
		// 随机出矿坑类型
		int mineFieldType = GameServerAssist.getMineTemplateManager().randomMineFieldType(human.getLevel());
		MineFieldTypeTemplate template = GameServerAssist.getMineTemplateManager().getMineFieldTypeTemplate(mineFieldType);
		if (template == null) {
			return;
		}
		boolean isBadMineField = template.getId() == GameServerAssist.getGameConstants().getDefaultMineFieldType();
		if (!isBadMineField && (Math.random() < template.getMonsterRate() / SharedConstants.DEFAULT_FLOAT_BASE)) {
			// 遇到怪物
			int monsterId = GameServerAssist.getMineTemplateManager().randomMineMonsterId(human.getLevel());
			MonsterTemplate monsterTemplate = GameServerAssist.getTemplateService().get(monsterId, MonsterTemplate.class);
			if (monsterTemplate == null) {
				doOpenMineFieldAction(index, mineFieldType);
				return;
			}			
			if (mineOpening) {
				return;
			}
			mineOpening = true;
			currentFieldtype = mineFieldType;
			currentIndex = index;
			currenctMonsterId = monsterId;
			GCOpenMineFieldBattleRequest gcMsg = new GCOpenMineFieldBattleRequest();
			gcMsg.setMonsterLevel(monsterTemplate.getLevel());
			gcMsg.setMonsterName(monsterTemplate.getName());
			gcMsg.setMineFieldName(template.getName());
			MineConsumeTemplate consumeTemplate = GameServerAssist.getMineTemplateManager().getMineConsumeTemplate(buyMineFieldNum + 1);
			CurrencyType costType = CurrencyType.indexOf(consumeTemplate.getBuyMineFieldCostType());
			int costNum = consumeTemplate.getBuyMineFieldCostNum();
			gcMsg.setBuyMineTimeCostType(costType.getIndex());
			gcMsg.setBuyMineTimeCostNum(costNum);
			human.sendMessage(gcMsg);
			return;
		}
		doOpenMineFieldAction(index, mineFieldType);
		cache.addUpdate(human.getHumanGuid(), convertToEntity());
	}

	/**
	 * 想客户端发送开启矿坑消息
	 * 
	 * @param index
	 * @param template
	 */
	private void doOpenMineFieldAction(int index, int mineFieldType) {
		if (mineFields.containsKey(index)) {
			return;
		}
		mineFields.put(index, new MineFieldModel(index, mineFieldType));
		useRemainMineMun();	
		MineFieldTypeTemplate template = GameServerAssist.getMineTemplateManager().getMineFieldTypeTemplate(mineFieldType);
		MineFieldInfo mineFieldInfo = new MineFieldInfo();
		mineFieldInfo.setIndex(index);
		mineFieldInfo.setName(template.getName());
		mineFieldInfo.setType(mineFieldType);
		mineFieldInfo.setDesc(template.getDesc());
		mineFieldInfo.setPicId(template.getPicId());
		boolean isBadField = mineFieldType == GameServerAssist.getGameConstants().getDefaultMineFieldType();
		mineFieldInfo.setIsBadMineField(isBadField);
		GCOpenMineField gcMsg = new GCOpenMineField();
		gcMsg.setChallenge(false);
		gcMsg.setChallengeResult(false);
		gcMsg.setMineField(mineFieldInfo);
		MineConsumeTemplate consumeTemplate = GameServerAssist.getMineTemplateManager().getMineConsumeTemplate(buyMineFieldNum + 1);
		CurrencyType costType = CurrencyType.indexOf(consumeTemplate.getBuyMineFieldCostType());
		int costNum = consumeTemplate.getBuyMineFieldCostNum();
		gcMsg.setBuyMineTimeCostType(costType.getIndex());
		gcMsg.setBuyMineTimeCostNum(costNum);
		int canBuyNum = human.getHumanVipManager().getCurrenctVipTemplate().getMaxBuyMineFieldNum() - buyMineFieldNum;
		gcMsg.setCanBuyNum(canBuyNum);
		human.sendMessage(gcMsg);
		if (isBadField) {
			human.sendGenericMessage(LangConstants.OPEN_BAD_MINE_FIELD, mineFieldInfo.getName());
		} else {				
			human.sendSuccessMessage(LangConstants.OPEN_GOOD_MINE_FIELD, mineFieldInfo.getName());
		}
		HumanCdManager cdManager = human.getHumanCdManager();		
		CdType cdType = getCdType(index);
		cdManager.snapCdQueueInfo(cdType);
		// 更新面板
		sendUpdateMinePanelMessage();
	}

	/**
	 * 获取开启的矿坑列表
	 * 
	 * @return
	 */
	public List<MineFieldInfo> getMineFileds() {
		List<MineFieldInfo> resultList = new ArrayList<MineFieldInfo>();
		for (MineFieldModel mineField : mineFields.values()) {
			MineFieldTypeTemplate template = GameServerAssist.getMineTemplateManager().getMineFieldTypeTemplate(mineField.getType());
			if (template == null) {
				continue;
			}
			MineFieldInfo mineFieldInfo = new MineFieldInfo();
			mineFieldInfo.setIndex(mineField.getIndex());
			mineFieldInfo.setType(mineField.getType());
			mineFieldInfo.setName(template.getName());
			mineFieldInfo.setDesc(template.getDesc());
			mineFieldInfo.setPicId(template.getPicId());
			boolean isBadField = mineField.getType() == GameServerAssist.getGameConstants().getDefaultMineFieldType();
			mineFieldInfo.setIsBadMineField(isBadField);
			resultList.add(mineFieldInfo);
		}
		return resultList;
	}

	public int getBuyMineFieldNum() {
		return buyMineFieldNum;
	}

	public int getResetMineFieldNum() {
		return resetMineFieldNum;
	}

	public int getRemainMineNum() {
		return remainFreeMineNum + remainBoughtMineNum;
	}

	private void useRemainMineMun() {
		if (remainFreeMineNum > 0) {
			remainFreeMineNum--;
		} else if (remainBoughtMineNum > 0) {
			remainBoughtMineNum--;
		}
	}

	/**
	 * 指定索引的矿坑是否已经开启
	 * 
	 * @param index
	 * @return
	 */
	public boolean mineFieldIsOpen(int index) {
		if (this.mineFields.containsKey(index)) {
			return true;
		}
		return false;
	}

	public int getMonsterId() {
		return currenctMonsterId;
	}

	/**
	 * 收获
	 * 
	 * @param index
	 */
	public void harvest(int index) {
		if (this.mineFields.containsKey(index)) {
			MineFieldModel mineField = mineFields.get(index);
			if (GameServerAssist.getGameConstants().getDefaultMineFieldType() == mineField.getType()) {
				return;
			}
			MineFieldTypeTemplate template = GameServerAssist.getMineTemplateManager().getMineFieldTypeTemplate(mineField.getType());
			if (template == null) {
				return;
			}
			HumanCdManager cdManager = human.getHumanCdManager();
			CdType cdType = getCdType(index);
			long cdTime = cdManager.getSpendTime(cdType, template.getCdTime() * TimeUtils.MIN);
			if (!cdManager.canAddCd(cdType, cdTime)) {
				human.sendErrorMessage(LangConstants.COMMON_COOLINGDOWN);
				return;
			}
			if (human.getBagManager().isFull(BagType.MAIN_BAG)) {
				human.sendWarningMessage(LangConstants.BAG_IS_FULL);
				return;
			}
			// 收获金币
			if (template.getItemIdArray()[0] == MineHarvestType.COIN.getIndex()) {
				harvestCoin();
			} else if (template.getItemIdArray()[0] == MineHarvestType.TRAIN_COIN.getIndex()) {
				harvestTrainCoin();
			} else {
				// 收获物品
				harvestItem(index, template);
			}
			// 添加CD
			cdManager.addCd(cdType, cdTime);
			cdManager.snapCdQueueInfo(cdType);
			cache.addUpdate(human.getHumanGuid(), convertToEntity());
		}
	}
	
	/**
	 * 收获物品
	 */
	private void harvestItem(int index, MineFieldTypeTemplate template) {
		int hitIndex = MathUtils.random(template.getItemWeightArray());
		if (hitIndex == -1) {
			return;
		}
		int itemId = template.getItemIdArray()[hitIndex];
		int itemNum = template.getItemNumArray()[hitIndex];
		int itemIcon = template.getItemIconArray()[hitIndex];
		Item item = ItemFactory.creatNewItem(human, itemId);
		// 军团科技加成强化石收益
		if (item.getType() == ItemDetailType.UPGRADESTONE.getIndex()) {
			itemNum = human.getHumanLegionManager().getLegionTechnologyAmend(
					LegionTechnologyType.MINE, itemNum);
		}
		if (human.getBagManager().putItems(BagType.MAIN_BAG, itemId, itemNum, true, ItemLogReason.MINE_HARVEST, "")) {
			// 发送收矿事件
			MineEvent event = new MineEvent();
			human.getEventBus().fireEvent(event);
			GCMineHarvest gcMsg = new GCMineHarvest();
			gcMsg.setIndex(index);
			gcMsg.setItemIcon(itemIcon);
			gcMsg.setItemCount(itemNum);
			gcMsg.setItemName(item.getName());
			human.sendMessage(gcMsg);
		}
	}
	
	/**
	 * 收获金币
	 */
	private void harvestCoin() {
		MineLevelTemplate levelTemplate = GameServerAssist.getMineTemplateManager().getMineLevelTemplate(human.getLevel());
		if (levelTemplate == null) {
			return;
		}
		human.getWallet().addMoney(CurrencyType.COIN, levelTemplate.getCoin(), true, MoneyLogReason.MINE_HARVEST, "");
		// 发送收矿事件
		MineEvent event = new MineEvent();
		human.getEventBus().fireEvent(event);
	}
	
	/**
	 * 收获培养币
	 */
	private void harvestTrainCoin() {
		MineLevelTemplate levelTemplate = GameServerAssist.getMineTemplateManager().getMineLevelTemplate(human.getLevel());
		if (levelTemplate == null) {
			return;
		}
		human.addTrainCoin(levelTemplate.getTrainCoin(), true, TrainCoinLogReason.LEVY_BET_WIN, "");
		// 发送收矿事件
		MineEvent event = new MineEvent();
		human.getEventBus().fireEvent(event);
	}
	

	public void resetMineFiled(int index) {
		if (this.mineFields.containsKey(index)) {
			int maxMineNum = GameServerAssist.getGameConstants().getMaxFreeMineNum()+human.getHumanVipManager().getCurrenctVipTemplate().getMaxBuyMineFieldNum();
			if (resetMineFieldNum >= maxMineNum) {
				// 购买次数已用完
				human.sendGenericMessage(LangConstants.BUY_OPEN_MINE_TIME_USE_OUT);
				return;
			}
			resetMineFieldNum++;					
			this.mineFields.remove(index);
			cache.addUpdate(human.getHumanGuid(), convertToEntity());
			//更新cd
			HumanCdManager cdManager = human.getHumanCdManager();
			CdType cdType = getCdType(index);
//			cdManager.removeCdFree(cdType);
			cdManager.snapCdQueueInfo(cdType);
			//返回移除成功消息			
			GCResetMineField gcMsg = new GCResetMineField();
			gcMsg.setIndex(index);
			gcMsg.setResetMineCostType(0);
			gcMsg.setResetMineCostNum(0);
			human.sendMessage(gcMsg);
		}
	}

	public boolean buyMineTimes() {
		VipPrivilegeTemplate vipTemplate= human.getHumanVipManager().getCurrenctVipTemplate();
		int maxBuyNum = vipTemplate.getMaxBuyMineFieldNum();
		if (this.buyMineFieldNum >= maxBuyNum) {
			// 购买次数已用完
			human.sendGenericMessage(LangConstants.BUY_OPEN_MINE_TIME_USE_OUT);
			return false;
		}
		MineConsumeTemplate consumeTemplate = GameServerAssist.getMineTemplateManager().getMineConsumeTemplate(buyMineFieldNum + 1);
		CurrencyType costType = CurrencyType.indexOf(consumeTemplate.getBuyMineFieldCostType());
		int costNum = consumeTemplate.getBuyMineFieldCostNum();
		boolean costResult = human.getWallet().costMoney(costType, costNum, MoneyLogReason.BUY_MINE_TIMES, "");
		if (costResult) {
			buyMineFieldNum++;
			remainBoughtMineNum++;
			cache.addUpdate(human.getHumanGuid(), convertToEntity());
		}
		return costResult;
	}

	/**
	 * 发送购买开矿次数成功的消息
	 */
	public void sendBuyMineTimesMessage() {
		VipPrivilegeTemplate vipTemplate= human.getHumanVipManager().getCurrenctVipTemplate();
		int maxBuyNum = vipTemplate.getMaxBuyMineFieldNum();
		GCBuyMineTimes gcMsg = new GCBuyMineTimes();
		gcMsg.setCanBuyNum(maxBuyNum - buyMineFieldNum);
		gcMsg.setRemainMineNum(getRemainMineNum());
		MineConsumeTemplate consumeTemplate = GameServerAssist.getMineTemplateManager().getMineConsumeTemplate(buyMineFieldNum + 1);
		gcMsg.setBuyMineTimeCostType(consumeTemplate.getBuyMineFieldCostType());
		gcMsg.setBuyMineTimeCostNum(consumeTemplate.getBuyMineFieldCostNum());
		human.sendMessage(gcMsg);
	}

	/**
	 * 开启矿坑遭遇结束处理
	 * 
	 * @param isWin
	 */
	public void onEncounterBattleEnd(boolean isWin) {
		int mineFieldType = isWin ? currentFieldtype : GameServerAssist.getGameConstants().getDefaultMineFieldType();
		cache.addUpdate(human.getHumanGuid(), convertToEntity());
		doOpenMineFieldAction(currentIndex, mineFieldType);
		mineOpening = false;
	}

	@Override
	public Human getOwner() {
		return human;
	}

	@Override
	public void onLoad(HumanEntity humanEntity) {
		HumanMineInfo humanMineInfo = humanEntity.getBuilder().getMine();
		this.buyMineFieldNum = humanMineInfo.getBuyMineFieldNum();
		this.remainFreeMineNum = humanMineInfo.getRemainFreeMineNum();
		this.remainBoughtMineNum = humanMineInfo.getRemainBoughtMineNum();
		this.resetMineFieldNum = humanMineInfo.getResetMineFieldNum();
		for (MineField field : humanMineInfo.getMineFieldsList()) {
			MineFieldModel mineField = new MineFieldModel(field);
			this.mineFields.put(mineField.getIndex(), mineField);
		}
	}

	@Override
	public void onPersistence(HumanEntity humanEntity) {
		humanEntity.getBuilder().setMine(convertToBuilder());
	}

	@Override
	public List<IEntity> getUpdateEntities() {
		List<IEntity> updateEntitys = new ArrayList<IEntity>();
		for (HumanMineEntity entity : cache.getAllUpdateData()) {
			updateEntitys.add(entity);
		}
		return updateEntitys;
	}

	@Override
	public List<IEntity> getDeleteEntities() {
		return null;
	}

	/**
	 * 转换为数据实体
	 * 
	 * @return
	 */
	private HumanMineEntity convertToEntity() {
		return new HumanMineEntity(convertToBuilder());
	}

	private HumanMineInfo.Builder convertToBuilder() {
		HumanMineInfo.Builder builder = HumanMineInfo.newBuilder();
		builder.setHumanGuid(human.getHumanGuid());
		builder.setBuyMineFieldNum(this.buyMineFieldNum);
		builder.setRemainFreeMineNum(this.remainFreeMineNum);
		builder.setRemainBoughtMineNum(this.remainBoughtMineNum);
		builder.setResetMineFieldNum(this.resetMineFieldNum);
		for (MineFieldModel model : mineFields.values()) {
			builder.addMineFields(model.convertToMineField());
		}
		return builder;
	}

	/**
	 * 重置每日数据
	 */
	public void resetDailyData() {
		this.remainFreeMineNum = GameServerAssist.getGameConstants().getMaxFreeMineNum();
		this.buyMineFieldNum = 0;
		this.resetMineFieldNum = 0;
		this.mineFields.clear();			
		cache.addUpdate(human.getHumanGuid(), convertToEntity());
		resetMineCd();		
		sendMineFieldListMessage();
		sendUpdateMinePanelMessage();
	}
	
	/**
	 * 移除矿坑的cd时间
	 */
	private void resetMineCd(){
		HumanCdManager cdManager = human.getHumanCdManager();
		cdManager.removeCdFree(CdType.MINE_1);
		cdManager.snapCdQueueInfo(CdType.MINE_1);
		cdManager.removeCdFree(CdType.MINE_2);
		cdManager.snapCdQueueInfo(CdType.MINE_2);
		cdManager.removeCdFree(CdType.MINE_3);
		cdManager.snapCdQueueInfo(CdType.MINE_3);
		cdManager.removeCdFree(CdType.MINE_4);
		cdManager.snapCdQueueInfo(CdType.MINE_4);
		cdManager.removeCdFree(CdType.MINE_5);
		cdManager.snapCdQueueInfo(CdType.MINE_5);
		cdManager.removeCdFree(CdType.MINE_6);
		cdManager.snapCdQueueInfo(CdType.MINE_6);
		cdManager.removeCdFree(CdType.MINE_7);
		cdManager.snapCdQueueInfo(CdType.MINE_7);
		cdManager.removeCdFree(CdType.MINE_8);
		cdManager.snapCdQueueInfo(CdType.MINE_8);
		cdManager.removeCdFree(CdType.MINE_9);
		cdManager.snapCdQueueInfo(CdType.MINE_9);
	}

	/**
	 * 根据矿坑索引获取对应的Cd类型
	 * 
	 * @param mineFieldIndex
	 * @return
	 */
	public CdType getCdType(int mineFieldIndex) {
		switch (mineFieldIndex) {
		case 0:
			return CdType.MINE_1;
		case 1:
			return CdType.MINE_2;
		case 2:
			return CdType.MINE_3;
		case 3:
			return CdType.MINE_4;
		case 4:
			return CdType.MINE_5;
		case 5:
			return CdType.MINE_6;
		case 6:
			return CdType.MINE_7;
		case 7:
			return CdType.MINE_8;
		case 8:
			return CdType.MINE_9;
		default:
			throw new IllegalArgumentException("mineFieldIndex out of bound.");
		}
	}


	/**
	 * 发送矿场面板更新消息
	 */
	public void sendUpdateMinePanelMessage() {
		VipPrivilegeTemplate vipTemplate = human.getHumanVipManager().getCurrenctVipTemplate();
		int nextBuyMineFieldNum = getBuyMineFieldNum() + 1;
		int resetMineFieldNum = getResetMineFieldNum() + 1;

		MineConsumeTemplate buyMineTemplate = GameServerAssist.getMineTemplateManager().getMineConsumeTemplate(nextBuyMineFieldNum);
		if (buyMineTemplate == null) {
			logger.error("矿场消费模板未找到。[buyTimes=" + nextBuyMineFieldNum + "]");
			return;
		}
		MineConsumeTemplate resetMineTemplate = GameServerAssist.getMineTemplateManager().getMineConsumeTemplate(resetMineFieldNum);
		if (resetMineTemplate == null) {
			logger.error("矿场消费模板未找到。[buyTimes=" + resetMineFieldNum + "]");
			return;
		}
		int canBuyNum = vipTemplate == null ? 0 : vipTemplate.getMaxBuyMineFieldNum()-getBuyMineFieldNum();
		canBuyNum = canBuyNum>0 ? canBuyNum : 0;
		GCUpdateMinePannel gcMsg = new GCUpdateMinePannel();
		gcMsg.setBuyMineTimeCostNum(buyMineTemplate.getBuyMineFieldCostNum());
		gcMsg.setBuyMineTimeCostType(buyMineTemplate.getBuyMineFieldCostType());
		gcMsg.setCanBuyNum(canBuyNum);
		gcMsg.setRemainMineNum(getRemainMineNum());
		gcMsg.setResetMineCostNum(resetMineTemplate.getBuyMineFieldCostNum());
		gcMsg.setResetMineCostType(resetMineTemplate.getBuyMineFieldCostType());
		human.sendMessage(gcMsg);
	}

	/**
	 * 发送矿坑列表消息
	 */
	public void sendMineFieldListMessage() {
		// 下发CD信息
		for(CdType cdType : mineTypes){
			human.getHumanCdManager().snapCdQueueInfo(cdType);
		}
		MineFieldInfo[] mineFieldArray = getMineFileds().toArray(new MineFieldInfo[0]);
		GCMineFieldList gcMsg = new GCMineFieldList();
		gcMsg.setMineFields(mineFieldArray);
		human.sendMessage(gcMsg);
	}
	
	public Map<Integer, MineFieldModel> getMineFields(){
		return mineFields;
	}
	
	public int getRemainFreeMineNum() {
		return remainFreeMineNum;
	}
	
	public int getRemainBoughtMineNum() {
		return remainBoughtMineNum;
	}
	
	/**
	 * 消除所有矿坑的消耗
	 * @return
	 */
	public int getRemoveAllMineCdCost() {
		HumanCdManager cdManager = human.getHumanCdManager();
		int needCostNum = 0;
		for(CdType cdType : mineTypes){
			needCostNum += cdManager.getRemoveCdCost(cdType, cdManager.getDiffTime(cdManager.getCdInfo(cdType).getEndTime()));
		}
		return needCostNum;
	}
	
	/**
	 * 清除矿坑cd
	 */
	public void removeAllMineCd() {
		// 判断是否有足够的魔晶
		int needCostNum = getRemoveAllMineCdCost();
		if(!human.getWallet().isEnough(CurrencyType.CRYSTAL, needCostNum)){
			human.sendWarningMessage(LangConstants.COMMON_NOT_ENOUGH,CurrencyType.CRYSTAL.getDesc());
			return;
		}
		
		// 消耗魔晶消除cd
		HumanCdManager cdManager = human.getHumanCdManager();
		for(CdType cdType : mineTypes){
			cdManager.removeCd(cdType);
			cdManager.snapCdQueueInfo(cdType);
		}
	}
	
	/**
	 * 收获所有矿坑
	 */
	public void harvestAllMine() {
		for(int i=0; i<MAX_MINE_FIELD_NUM; i++){
			harvest(i);
		}
	}

}
