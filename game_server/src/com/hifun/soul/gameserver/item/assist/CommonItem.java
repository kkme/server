package com.hifun.soul.gameserver.item.assist;

import com.hifun.soul.core.util.KeyValuePair;


/**
 * 
 * 与客户端同步时用到的道具抽象结构
 * 
 * @author magicstone
 *
 */
public class CommonItem {
	
	/** UUID */
	private String UUID;
	/** 道具Id */
	private Integer itemId;
	/** 道具名称 */
	private String name;
	/** 道具描述 */
	private String desc;
	/** 道具小类 */
	private int type;
	/** 道具图标 */
	private int icon;
	/** 道具品质 */
	private int rarity;
	/** 道具绑定模式 */
	private int bind;
	/** 道具堆叠数量 */
	private int overlapNum;
	/** 所在背包类型 */
	private int bagType;
	/** 所在背包位置索引 */
	private int bagIndex;
	/** 出售获取货币类型 */
	private short sellCurrencyType;
	/** 出售获取货币数量 */
	private int sellNum;
	
	/** 装备位置 */
	private int position;
	/** 装备耐久 */
	private int endure;
	/** 装备孔数 */
	private int equipHole;
	/** 装备最大可以开的孔数 */
	private int maxEquipHole;
	/** 装备身上带的基础属性 */
	private KeyValuePair<Integer,Integer>[] equipBaseAttributes;
	/** 装备身上带的特殊属性 */
	private KeyValuePair<Integer,Integer>[] equipSpecialAttributes;
	/** 装备身上带的强化属性 */
	private KeyValuePair<Integer,Integer>[] equipUpgradeAttributes;
	/** 装备身上的宝石 */
	private GemItemInfo[] equipGemItemInfos;
	/** 是否已装备 */
	private boolean isEquiped;
	
	/** 镶嵌花费货币类型 */
	private short embedCurrencyType;
	/** 镶嵌花费货币数量 */
	private int embedCurrencyNum;
	/** 拆除花费货币类型 */
	private short extractCurrencyType;
	/** 拆除花费货币数量 */
	private int extractCurrencyNum;
	/** 宝石属性 */
	private KeyValuePair<Integer,Integer>[] gemAttributes;
	
	/** 商店购买货币类型 */
	private short shopCurrencyType;
	/** 商店购买货币数量 */
	private int shopCurrencyNum;
	/** 幸运石的成功率加成值 */
	private float extraSuccessRate;
	/** 装备强化等级 */
	private int upgradeLevel;
	/** 物品可以使用的职业限制 */
	private int limitOccupation;
	/** 物品可以使用的等级限制 */
	private int limitLevel;
	/**制作装备需要的材料列表(装备图纸特有属性) */
	private KeyValuePair<String,Integer>[] materialsOfEquipPaper;
	/** 最大可叠加数 */
	private int maxOverlap;
	/** 是否可出售 */
	private boolean canSell;
	
	/** 商店、商城、神秘商店物品的可见等级限制,服务器端用，不涉及到与客户端通信 */
	private int canSeeLevelLimit;
	/** 商店、商城、神秘商店物品的可见职业限制,服务器端用，不涉及到与客户端通信 */
	private int canSeeOccupationLimit;
	
	public int getBagType() {
		return bagType;
	}

	public void setBagType(int bagType) {
		this.bagType = bagType;
	}

	public int getBagIndex() {
		return bagIndex;
	}

	public void setBagIndex(int bagIndex) {
		this.bagIndex = bagIndex;
	}
	public String getUUID() {
		return UUID;
	}

	public void setUUID(String uUID) {
		UUID = uUID;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public int getRarity() {
		return rarity;
	}

	public void setRarity(int rarity) {
		this.rarity = rarity;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getEndure() {
		return endure;
	}

	public void setEndure(int endure) {
		this.endure = endure;
	}

	public int getBind() {
		return bind;
	}

	public void setBind(int bind) {
		this.bind = bind;
	}

	public int getOverlapNum() {
		return overlapNum;
	}

	public void setOverlapNum(int overlapNum) {
		this.overlapNum = overlapNum;
	}

	public short getSellCurrencyType() {
		return sellCurrencyType;
	}

	public void setSellCurrencyType(short sellCurrencyType) {
		this.sellCurrencyType = sellCurrencyType;
	}

	public int getSellNum() {
		return sellNum;
	}

	public void setSellNum(int sellNum) {
		this.sellNum = sellNum;
	}

	public int getEquipHole() {
		return equipHole;
	}

	public void setEquipHole(int equipHole) {
		this.equipHole = equipHole;
	}

	public KeyValuePair<Integer, Integer>[] getEquipBaseAttributes() {
		return equipBaseAttributes;
	}

	public void setEquipBaseAttributes(
			KeyValuePair<Integer, Integer>[] equipBaseAttributes) {
		this.equipBaseAttributes = equipBaseAttributes;
	}

	public KeyValuePair<Integer, Integer>[] getEquipSpecialAttributes() {
		return equipSpecialAttributes;
	}

	public void setEquipSpecialAttributes(
			KeyValuePair<Integer, Integer>[] equipSpecialAttributes) {
		this.equipSpecialAttributes = equipSpecialAttributes;
	}

	public GemItemInfo[] getEquipGemItemInfos() {
		return equipGemItemInfos;
	}

	public void setEquipGemItemInfos(GemItemInfo[] equipGemItemInfos) {
		this.equipGemItemInfos = equipGemItemInfos;
	}

	public short getEmbedCurrencyType() {
		return embedCurrencyType;
	}

	public void setEmbedCurrencyType(short embedCurrencyType) {
		this.embedCurrencyType = embedCurrencyType;
	}

	public boolean getIsEquiped() {
		return isEquiped;
	}

	public void setIsEquiped(boolean isEquiped) {
		this.isEquiped = isEquiped;
	}

	public int getEmbedCurrencyNum() {
		return embedCurrencyNum;
	}

	public void setEmbedCurrencyNum(int embedCurrencyNum) {
		this.embedCurrencyNum = embedCurrencyNum;
	}

	public short getExtractCurrencyType() {
		return extractCurrencyType;
	}

	public void setExtractCurrencyType(short extractCurrencyType) {
		this.extractCurrencyType = extractCurrencyType;
	}

	public int getExtractCurrencyNum() {
		return extractCurrencyNum;
	}

	public void setExtractCurrencyNum(int extractCurrencyNum) {
		this.extractCurrencyNum = extractCurrencyNum;
	}

	public KeyValuePair<Integer, Integer>[] getGemAttributes() {
		return gemAttributes;
	}

	public void setGemAttributes(KeyValuePair<Integer, Integer>[] gemAttributes) {
		this.gemAttributes = gemAttributes;
	}

	public KeyValuePair<Integer, Integer>[] getEquipUpgradeAttributes() {
		return equipUpgradeAttributes;
	}

	public void setEquipUpgradeAttributes(
			KeyValuePair<Integer, Integer>[] equipUpgradeAttributes) {
		this.equipUpgradeAttributes = equipUpgradeAttributes;
	}

	public short getShopCurrencyType() {
		return shopCurrencyType;
	}

	public void setShopCurrencyType(short shopCurrencyType) {
		this.shopCurrencyType = shopCurrencyType;
	}

	public int getShopCurrencyNum() {
		return shopCurrencyNum;
	}

	public void setShopCurrencyNum(int shopCurrencyNum) {
		this.shopCurrencyNum = shopCurrencyNum;
	}

	public float getExtraSuccessRate() {
		return extraSuccessRate;
	}

	public void setExtraSuccessRate(float extraSuccessRate) {
		this.extraSuccessRate = extraSuccessRate;
	}

	public int getUpgradeLevel() {
		return upgradeLevel;
	}

	public void setUpgradeLevel(int upgradeLevel) {
		this.upgradeLevel = upgradeLevel;
	}

	public int getLimitOccupation() {
		return limitOccupation;
	}

	public void setLimitOccupation(int limitOccupation) {
		this.limitOccupation = limitOccupation;
	}

	public int getLimitLevel() {
		return limitLevel;
	}

	public void setLimitLevel(int limitLevel) {
		this.limitLevel = limitLevel;
	}

	public int getMaxEquipHole() {
		return maxEquipHole;
	}

	public void setMaxEquipHole(int maxEquipHole) {
		this.maxEquipHole = maxEquipHole;
	}

	public KeyValuePair<String, Integer>[] getMaterialsOfEquipPaper() {
		return materialsOfEquipPaper;
	}

	public void setMaterialsOfEquipPaper(KeyValuePair<String, Integer>[] materialsOfEquipPaper) {
		this.materialsOfEquipPaper = materialsOfEquipPaper;
	}

	public int getMaxOverlap() {
		return maxOverlap;
	}

	public void setMaxOverlap(int maxOverlap) {
		this.maxOverlap = maxOverlap;
	}

	public boolean getCanSell() {
		return canSell;
	}

	public void setCanSell(boolean canSell) {
		this.canSell = canSell;
	}

	public int getCanSeeLevelLimit() {
		return canSeeLevelLimit;
	}

	public void setCanSeeLevelLimit(int canSeeLevelLimit) {
		this.canSeeLevelLimit = canSeeLevelLimit;
	}

	public int getCanSeeOccupationLimit() {
		return canSeeOccupationLimit;
	}

	public void setCanSeeOccupationLimit(int canSeeOccupationLimit) {
		this.canSeeOccupationLimit = canSeeOccupationLimit;
	}
	
}
