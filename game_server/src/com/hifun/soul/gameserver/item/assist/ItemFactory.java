package com.hifun.soul.gameserver.item.assist;

import java.util.ArrayList;
import java.util.List;

import com.hifun.soul.core.util.KeyUtil;
import com.hifun.soul.core.util.KeyValuePair;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.ConsumeItem;
import com.hifun.soul.gameserver.item.DynamicPropertyType;
import com.hifun.soul.gameserver.item.EquipItem;
import com.hifun.soul.gameserver.item.Item;
import com.hifun.soul.gameserver.item.ItemDetailType;
import com.hifun.soul.gameserver.item.MaterialItem;
import com.hifun.soul.gameserver.item.feature.ConsumeItemFeature;
import com.hifun.soul.gameserver.item.feature.EquipItemFeature;
import com.hifun.soul.gameserver.item.feature.MaterialItemFeature;
import com.hifun.soul.gameserver.item.template.ItemTemplate;
import com.hifun.soul.proto.common.Items.KeyValuePairInt32Int32;
import com.hifun.soul.proto.data.entity.Entity.HumanItem;
import com.hifun.soul.proto.data.entity.Entity.HumanItem.Builder;

/**
 * 道具的工厂
 * @author magicstone
 * @author crazyjohn
 */
public class ItemFactory {
	/**
	 * 创建新的物品;
	 * @param human
	 * @param itemId
	 * @return
	 */
	public static Item creatNewItem(Human human, Integer itemId) {
		return creatItemWithUUID(human, itemId,KeyUtil.UUIDKey());
	}

	/**
	 * 根据已有的UUID创建物品;<br>
	 * 当物品从数据库反序列化时候调用此方法创建;因为此时物品是有自己的UUID的;
	 * 
	 * @param human
	 * @param itemId
	 * @param bagType
	 * @param bagIndex
	 * @param uuid
	 * @return
	 */
	private static Item creatItemWithUUID(Human human, Integer itemId,
			String uuid) {
		if (GameServerAssist.getItemTemplateManager().isEquip(itemId)) {
			return new EquipItem(human, uuid, itemId);
		} else if (GameServerAssist.getItemTemplateManager().isMaterial(itemId)) {
			return new MaterialItem(human, uuid, itemId);
		} else if (GameServerAssist.getItemTemplateManager().isConsume(itemId)) {
			return new ConsumeItem(human, uuid, itemId);
		} else
			return null;
	}
	
	/**
	 * 根据已有的UUID创建物品;<br>
	 * 当物品从数据库反序列化时候调用此方法创建;因为此时物品是有自己的UUID的;
	 * <pre>创建时该物品不归属于任何角色</pre>
	 * @param itemId
	 * @param uuid
	 * @return
	 */
	private static Item creatItemWithUUID(Integer itemId,String uuid) {
		if (GameServerAssist.getItemTemplateManager().isEquip(itemId)) {
			return new EquipItem(uuid, itemId);
		} else if (GameServerAssist.getItemTemplateManager().isMaterial(itemId)) {
			return new MaterialItem(uuid, itemId);
		} else if (GameServerAssist.getItemTemplateManager().isConsume(itemId)) {
			return new ConsumeItem(uuid, itemId);
		} else
			return null;
	}
	
	
	/**
	 * 将protobuf生成的Item转换为业务模型Item
	 * @param itemBuilder
	 * @return
	 */
	public static Item convertProtoItemToItem(com.hifun.soul.proto.common.Items.Item itemBuilder){
		Item item = ItemFactory.creatItemWithUUID(itemBuilder.getTemplateId(),itemBuilder.getUuid());
		if (item == null) {
			return null;
		}
		//设置默认所在背包为主背包
		item.setBagType(BagType.MAIN_BAG);
		// 设置物品叠加数量
		item.setOverlapNum(itemBuilder.getItemCount());
		if(item.isEquip()){
			EquipItemFeature equipItemFeature = (EquipItemFeature)item.getFeature();
			equipItemFeature.setEndure(itemBuilder.getEndure());
			equipItemFeature.setLevel(itemBuilder.getLevel(),null,"");
			equipItemFeature.setEquipHole(itemBuilder.getEquipHole());
			equipItemFeature.setEquiped(itemBuilder.getIsEquiped());
			// 装备特殊属性
			List<KeyValuePairInt32Int32> equipSpecialAttributesList = itemBuilder.getEquipSpecialAttributesList();
			List<KeyValuePair<Integer,Integer>> equipSpecialAttributes = new ArrayList<KeyValuePair<Integer,Integer>>();
			if(equipSpecialAttributesList != null){
				for(KeyValuePairInt32Int32 keyValuePair : equipSpecialAttributesList){
					KeyValuePair<Integer,Integer> tempKeyValuePair = new KeyValuePair<Integer,Integer>();
					tempKeyValuePair.setKey(keyValuePair.getKey());
					tempKeyValuePair.setValue(keyValuePair.getValue());					
					equipSpecialAttributes.add(tempKeyValuePair);
				}
			}
			equipItemFeature.setEquipSpecialAttributes(equipSpecialAttributes);
			// 装备洗练尚未保存的特殊属性
			List<KeyValuePairInt32Int32> newEquipSpecialAttributesList = itemBuilder.getNewEquipSpecialAttributesList();
			List<KeyValuePair<Integer,Integer>> newEquipSpecialAttributes = new ArrayList<KeyValuePair<Integer,Integer>>();
			if(newEquipSpecialAttributesList != null){
				for(KeyValuePairInt32Int32 keyValuePair : newEquipSpecialAttributesList){
					if(keyValuePair == null){
						continue;
					}
					KeyValuePair<Integer,Integer> tempKeyValuePair = new KeyValuePair<Integer,Integer>();
					tempKeyValuePair.setKey(keyValuePair.getKey());
					tempKeyValuePair.setValue(keyValuePair.getValue());					
					newEquipSpecialAttributes.add(tempKeyValuePair);
				}
			}
			equipItemFeature.setNewEquipSpecialAttributes(newEquipSpecialAttributes);
			// 装备宝石信息
			List<com.hifun.soul.proto.common.Items.GemItemInfo> gemItemInfoList = itemBuilder.getEquipGemItemInfosList();
			if(gemItemInfoList != null
					&& gemItemInfoList.size() > 0){
				for(com.hifun.soul.proto.common.Items.GemItemInfo gemItemInfoProto : gemItemInfoList){
					GemItemInfo gemItemInfo = new GemItemInfo();
					gemItemInfo.setItemId(gemItemInfoProto.getTemplateId());
					gemItemInfo.setIndex(gemItemInfoProto.getIndex());
					// 宝石品质
					ItemTemplate gemTemplate = GameServerAssist.getItemTemplateManager().getItemTemplate(gemItemInfoProto.getTemplateId());
					gemItemInfo.setRarity(gemTemplate.getLevelLimit());
					// 宝石属性
					List<KeyValuePairInt32Int32> gemAttributesList = gemItemInfoProto.getGemAttributesList();
					if(gemAttributesList != null){
						KeyValuePair<Integer,Integer>[] gemAttributes = KeyValuePair.newKeyValuePairArray(gemAttributesList.size());
						int i=0;
						for(KeyValuePairInt32Int32 keyValuePair : gemAttributesList){
							KeyValuePair<Integer,Integer> tempKeyValuePair = new KeyValuePair<Integer,Integer>();
							tempKeyValuePair.setKey(keyValuePair.getKey());
							tempKeyValuePair.setValue(keyValuePair.getValue());
							gemAttributes[i] = tempKeyValuePair;
							i++;
						}
						gemItemInfo.setEquipGemAttributes(gemAttributes);
					}
					else{
						gemItemInfo.setEquipGemAttributes(null);
					}
					equipItemFeature.addGemItemInfo(gemItemInfo,gemItemInfo.getIndex(),null,"");
				}
			}

		}
		else if(item.getType() == ItemDetailType.GEM.getIndex()){
			MaterialItemFeature materialItemFeature = (MaterialItemFeature)item.getFeature();
			// 宝石特殊属性
			List<KeyValuePairInt32Int32> gemAttributesList = itemBuilder.getGemAttributesList();
			List<KeyValuePair<Integer,Integer>> gemAttributes = new ArrayList<KeyValuePair<Integer,Integer>>();
			if(gemAttributesList != null){
				for(KeyValuePairInt32Int32 keyValuePair : gemAttributesList){
					KeyValuePair<Integer,Integer> tempKeyValuePair = new KeyValuePair<Integer,Integer>();
					tempKeyValuePair.setKey(keyValuePair.getKey());
					tempKeyValuePair.setValue(keyValuePair.getValue());
					gemAttributes.add(tempKeyValuePair);
				}
			}
			materialItemFeature.setGemAttributes(gemAttributes);
		}
		else if(item.getType() == ItemDetailType.DYNAMIC_PROPERTY_ITEM.getIndex()){	
			ConsumeItemFeature consumeItemFeature = (ConsumeItemFeature)item.getFeature();
			if(itemBuilder.getDynamicPropertyList()!=null){				
				for(KeyValuePairInt32Int32 keyValuePair:itemBuilder.getDynamicPropertyList()){
					consumeItemFeature.setDynamicProperty(DynamicPropertyType.indexOf(keyValuePair.getKey()),keyValuePair.getValue());
				}
			}
		}
		return item;
	}
	
	/**
	 * 将模型Item转换为业务protobuf生成的Item
	 * @param item
	 * @return
	 */
	public static com.hifun.soul.proto.common.Items.Item convertItemToProtoItem(Item item){
		if(item==null){
			return null;
		}
		com.hifun.soul.proto.common.Items.Item.Builder builderData = com.hifun.soul.proto.common.Items.Item.newBuilder();
		builderData.setIndex(item.getBagIndex());
		builderData.setTemplateId(item.getItemId());
		builderData.setUuid(item.getUUID());
		builderData.setItemCount(item.getOverlapNum());		
		if(item.isEquip()){
			EquipItemFeature equipItemFeature = (EquipItemFeature)item.getFeature();
			builderData.setEndure(equipItemFeature.getEndure());
			builderData.setLevel(equipItemFeature.getLevel());
			builderData.setEquipHole(equipItemFeature.getEquipHole());
			builderData.setIsEquiped(equipItemFeature.isEquiped());
			// 装备特殊属性
			List<KeyValuePair<Integer,Integer>> equipSpecialAttributes = equipItemFeature.getEquipSpecialAttributes();
			if(equipSpecialAttributes != null){
				for(KeyValuePair<Integer,Integer> keyValuePair : equipSpecialAttributes){
					if(keyValuePair == null){
						continue;
					}
					com.hifun.soul.proto.common.Items.KeyValuePairInt32Int32.Builder tempKeyValuePairBuilder =  KeyValuePairInt32Int32.newBuilder();
					tempKeyValuePairBuilder.setKey(keyValuePair.getKey());
					tempKeyValuePairBuilder.setValue(keyValuePair.getValue());					
					builderData.addEquipSpecialAttributes(tempKeyValuePairBuilder);
				}
			}
			// 装备洗练尚未保存的特殊属性
			List<KeyValuePair<Integer,Integer>> newEquipSpecialAttributes = equipItemFeature.getNewEquipSpecialAttributes();
			if(newEquipSpecialAttributes != null){
				for(KeyValuePair<Integer,Integer> keyValuePair : newEquipSpecialAttributes){
					if(keyValuePair == null){
						continue;
					}
					com.hifun.soul.proto.common.Items.KeyValuePairInt32Int32.Builder tempKeyValuePairBuilder =  KeyValuePairInt32Int32.newBuilder();
					tempKeyValuePairBuilder.setKey(keyValuePair.getKey());
					tempKeyValuePairBuilder.setValue(keyValuePair.getValue());					
					builderData.addNewEquipSpecialAttributes(tempKeyValuePairBuilder);
				}
			}
			// 装备的宝石
			GemItemInfo[] gemItemInfos = equipItemFeature.getGemItemInfos();
			if(gemItemInfos != null){
				for(GemItemInfo gemItemInfo : gemItemInfos){
					if(gemItemInfo == null){
						continue;
					}
					com.hifun.soul.proto.common.Items.GemItemInfo.Builder gemItemInfoBuilder = com.hifun.soul.proto.common.Items.GemItemInfo.newBuilder();
					gemItemInfoBuilder.setTemplateId(gemItemInfo.getItemId());
					gemItemInfoBuilder.setIndex(gemItemInfo.getIndex());
					// 装备上面的宝石的属性
					KeyValuePair<Integer,Integer>[] equipGemAttributes = gemItemInfo.getEquipGemAttributes();
					if(equipGemAttributes != null){
						for(KeyValuePair<Integer,Integer> keyValuePair : equipGemAttributes){
							if(keyValuePair == null){
								continue;
							}
							com.hifun.soul.proto.common.Items.KeyValuePairInt32Int32.Builder tempKeyValuePairBuilder =  KeyValuePairInt32Int32.newBuilder();
							tempKeyValuePairBuilder.setKey(keyValuePair.getKey());
							tempKeyValuePairBuilder.setValue(keyValuePair.getValue());
							gemItemInfoBuilder.addGemAttributes(tempKeyValuePairBuilder);
						}
					}
					builderData.addEquipGemItemInfos(gemItemInfoBuilder);
				}
			}
		}
		else if(item.getType() == ItemDetailType.GEM.getIndex()){
			MaterialItemFeature materialItemFeature = (MaterialItemFeature)item.getFeature();			
			List<KeyValuePair<Integer,Integer>> gemAttributes = materialItemFeature.getGemAttributes();
			if(gemAttributes != null){
				for(KeyValuePair<Integer,Integer> keyValuePair : gemAttributes){
					if(keyValuePair == null){
						continue;
					}
					com.hifun.soul.proto.common.Items.KeyValuePairInt32Int32.Builder tempKeyValuePairBuilder =  KeyValuePairInt32Int32.newBuilder();
					tempKeyValuePairBuilder.setKey(keyValuePair.getKey());
					tempKeyValuePairBuilder.setValue(keyValuePair.getValue());
					builderData.addGemAttributes(tempKeyValuePairBuilder);
				}
			}
		}
		else if(item.getType() == ItemDetailType.DYNAMIC_PROPERTY_ITEM.getIndex()){
			ConsumeItemFeature consumeItemFeature = (ConsumeItemFeature) item.getFeature();
			for (DynamicPropertyType type : consumeItemFeature.getDynamicProperties().keySet()) {
				com.hifun.soul.proto.common.Items.KeyValuePairInt32Int32.Builder tempKeyValuePairBuilder =  KeyValuePairInt32Int32.newBuilder();
				tempKeyValuePairBuilder.setKey(type.getIndex());
				tempKeyValuePairBuilder.setValue(consumeItemFeature.getDynamicPropertyValue(type));
				builderData.addDynamicProperty(tempKeyValuePairBuilder);
			}
		}
		return builderData.build();
	}
	
	/**
	 * 将持久化数据转化为内存数据
	 * @param human
	 * @param itemBuilder
	 * @return
	 */
	public static Item convertHumanItemToItem(Human human,HumanItem itemBuilder) {
		BagType bagType = BagType.indexOf(itemBuilder.getBagType());
		int index = itemBuilder.getItem().getIndex();
		if (bagType == null) {
			return null;
		}
		Item item = convertProtoItemToItem(itemBuilder.getItem());
		if (item == null) {
			return null;
		}
		item.setHuman(human);
		item.setBagType(bagType);
		item.setBagIndex(index);
		return item;
	}

	
	/**
	 * 内存数据到持久化数据的转化
	 * @param human
	 * @param item
	 * @param bagType
	 * @return
	 */
	public static Builder converItemToHumanItem(long humanGuid, Item item, BagType bagType) {
		HumanItem.Builder itemBuilder = HumanItem.newBuilder();
		itemBuilder.setBagType(bagType.getIndex());
		itemBuilder.setHumanGuid(humanGuid);		
		itemBuilder.setItem(convertItemToProtoItem(item));
		return itemBuilder;
	}
}
