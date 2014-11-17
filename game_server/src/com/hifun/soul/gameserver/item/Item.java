package com.hifun.soul.gameserver.item;

import java.util.List;

import com.hifun.soul.common.LogReasons.PropertyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.util.KeyValuePair;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.common.text.LinkType;
import com.hifun.soul.gameserver.common.text.RichTextHelper;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.assist.GemItemInfo;
import com.hifun.soul.gameserver.item.feature.EquipItemFeature;
import com.hifun.soul.gameserver.item.feature.ItemFeature;
import com.hifun.soul.gameserver.item.feature.MaterialItemFeature;
import com.hifun.soul.gameserver.item.template.ItemTemplate;
import com.hifun.soul.gameserver.util.ColorUtils;

/**
 * 抽象物品
 * @author magicstone
 *         说明： Item中很多抽象方法其实应该在本类中统一实现，因为自动生成工具限制，
 *         不能为EquipItemTemplateVO、MaterialItemVO、ConsumeItemVO构建
 *         父类。所以Item的一些方法需要到对应的子类去实现
 */
public abstract class Item {
	/** 物品所属的角色 */
	private Human human;
	/** 物品的UUID */
	private String UUID;
	/** 所在的包裹 */
	private BagType bagType;
	/** 所在包裹的位置 */
	private int bagIndex;
	/** 物品的模版id */
	private int itemId;
	/** 物品特色 */
	protected ItemFeature itemFeature;
	/** 物品堆叠数量 */
	private int overlapNum;

	public Item(Human human, String UUID, Integer itemId) {
		this.human = human;
		this.UUID = UUID;
		this.itemId = itemId;
		this.bagType = BagType.OTHER;
		this.bagIndex=-1;
		this.overlapNum=1;
		initTemplate();
		initFeature(this); 
		
	}
	
	public Item(String UUID, Integer itemId) {
		this(null,UUID,itemId);
	}
	
	public void setHuman(Human human){
		this.human = human;
	}

	public Human getHuman() {
		return human;
	}

	public String getUUID() {
		return UUID;
	}

	public BagType getBagType() {
		return bagType;
	}

	public int getBagIndex() {
		return bagIndex;
	}

	public ItemFeature getFeature() {
		return itemFeature;
	}

	public int getOverlapNum() {
		return overlapNum;
	}


	/**
	 * 设置物品叠加数量
	 * 
	 * <pre>
	 * 仅当物品可叠加时有效,参数overlapNum超出最大叠加数会时，属性将被设置为最大叠加数
	 * </pre>
	 * 
	 * @param overlapNum
	 */
	public void setOverlapNum(int overlapNum) {
		if (this.isOverlapable() == true) {
				this.overlapNum = overlapNum;
		}
	}

	/**
	 * 设置所在包裹的类型
	 * @param bagIndex
	 */
	public void setBagType(BagType bagType){
		this.bagType = bagType;
	}
	
	/**
	 * 设置所在包裹的位置
	 * @param bagIndex
	 */
	public void setBagIndex(int bagIndex){
		this.bagIndex = bagIndex;
	}
	
	/**
	 * 获取道具的模版id
	 * 
	 * @return
	 */
	public Integer getItemId() {
		return itemId;
	}
	
	/**
	 * 判断是否可以打孔
	 * 
	 * @return
	 */
	public boolean canGemPunch(){
		if(!isEquip()){
			return false;
		}
		
		EquipItemFeature itemFeature = (EquipItemFeature)getFeature();
		int equipHole = itemFeature.getEquipHole();
		if(equipHole < itemFeature.getMaxEquipHole()){
			return true;
		}
		
		return false;
	}
	
	/**
	 * 判断是否可以镶嵌宝石
	 * 
	 * @return
	 */
	public boolean canEmbedGem(Item gemItem, int index) {
		if(!isEquip()){
			return false;
		}
		
		EquipItemFeature itemFeature = (EquipItemFeature)getFeature();
		
		int equipHole = itemFeature.getEquipHole();
		if(equipHole <= 0){
			return false;
		}
		
		if(index >= equipHole){
			return false;
		}
		
		// 判断是否已经装备了同类型的宝石
		for(GemItemInfo gemItemInfo : itemFeature.getGemItemInfos()){
			// 对于宝石使用levelLimit区分是否是同类宝石
			if(gemItemInfo != null
					&& gemItemInfo.getRarity() == gemItem.getTemplate().getLevelLimit()){
				// 不能装备同类型的宝石
				this.human.sendErrorMessage(LangConstants.SAME_RALITY_GEM);
				return false;
			}
		}
		
		GemItemInfo gemItemInfo = itemFeature.getGemItemInfo(index);
		if(gemItemInfo == null){
			return true;
		}
		
		return false;
	}
	
	/**
	 * 装备镶嵌宝石
	 * 
	 * @param item
	 * @return
	 */
	public boolean embedGem(Item item,int index) {
		if(item.getType() != ItemDetailType.GEM.getIndex()){
			return false;
		}
		if(!canEmbedGem(item,index)){
			return false;
		}
		if(item == null
				|| item.getType() != ItemDetailType.GEM.getIndex()){
			return false;
		}
		EquipItemFeature itemFeature = (EquipItemFeature)getFeature();
		GemItemInfo gemItemInfo = new GemItemInfo();
		gemItemInfo.setItemId(item.getItemId());
		ItemTemplate gemTemplate = GameServerAssist.getItemTemplateManager().getItemTemplate(item.getItemId());
		gemItemInfo.setRarity(gemTemplate.getLevelLimit());
		List<KeyValuePair<Integer,Integer>> equipGemAttributes = ((MaterialItemFeature)item.getFeature())
				.getGemAttributes();
		if(equipGemAttributes == null){
			KeyValuePair<Integer, Integer>[] emptyAttributes = KeyValuePair
					.newKeyValuePairArray(0);
			gemItemInfo.setEquipGemAttributes(emptyAttributes);
		}
		else{
			KeyValuePair<Integer, Integer>[] attributes = KeyValuePair
					.newKeyValuePairArray(equipGemAttributes.size());
			for(int j=0; j<attributes.length; j++) {
				attributes[j] = equipGemAttributes.get(j);
			}
			gemItemInfo.setEquipGemAttributes(attributes);
		}
		itemFeature.addGemItemInfo(gemItemInfo,index, PropertyLogReason.GEM_EMBED,"");	
		return true;
		
	}
	
	/**
	 * 获取带链接的物品名称
	 * @return
	 */
	public String getLinkedName(){
		String humanId="";
		if(this.human != null && this.bagType != BagType.OTHER)
		{
			humanId = Long.toHexString(this.human.getHumanGuid());
		}
		String textColor = ColorUtils.RGB_WHITE;
		if(this.isEquip()){
			textColor = ColorUtils.getQualityColor(((EquipItem)this).getRarity());
		}
		if(isOverlapable() || humanId.length()==0){
			return RichTextHelper.addLink(this.getName(),LinkType.COMMON_ITEM.getIndex(),humanId, this.getItemId().toString(),textColor);
		}
		else{
			return RichTextHelper.addLink(this.getName(),LinkType.SPECIAL_ITEM.getIndex(),humanId,this.getUUID(),textColor);
		}	
	}
	
	/**
	 * 初始化物品对应的模版
	 * 
	 */
	protected abstract void initTemplate();

	/**
	 * 初始化物品属性
	 * 
	 * @param item
	 */
	protected abstract void initFeature(Item item);

	/**
	 * 获取模版
	 * 
	 * @return
	 */
	public abstract ItemTemplate getTemplate();

	/**
	 * 是否是装备
	 * 
	 * @return
	 */
	public abstract boolean isEquip();

	/**
	 * 获取道具名称
	 * 
	 * @return
	 */
	public abstract String getName();

	/**
	 * 获取道具描述
	 * 
	 * @return
	 */
	public abstract String getDesc();

	/**
	 * 获取道具类型
	 * 
	 * @return
	 */
	public abstract int getType();

	/**
	 * 获取道具图标
	 * 
	 * @return
	 */
	public abstract int getIcon();

	/**
	 * 获取道具品质
	 * 
	 * @return
	 */
	public abstract int getRarity();

	/**
	 * 获取绑定类型
	 * 
	 * @return
	 */
	public abstract int getBind();
	
	/**
	 * 获取出售获得货币类型
	 * 
	 * @return
	 */
	public abstract short getSellCurrencyType();
	
	/**
	 * 获取出售获取货币数量 
	 * 
	 * @return
	 */
	public abstract int getSellCurrencyNum();
	
	/**
	 * 是否可叠加
	 * 
	 * @return
	 */
	public abstract boolean isOverlapable() ;
	
	/**
	 * 获取该物品最大叠加数
	 */
	public abstract int getMaxOverlap();
	
	/**
	 * 是否可出售
	 * @return
	 */
	public abstract boolean isCanSell();
	
}
