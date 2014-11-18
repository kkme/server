



-- create class
local GiftData = class("GiftData")

		--[[ 天赋id ]]
		GiftData.id = nil
		--[[ 天赋类型 ]]
		GiftData.type = nil
		--[[ 天赋名称 ]]
		GiftData.name = nil
		--[[ 图标 ]]
		GiftData.icon = nil
		--[[ 当前属性id ]]
		GiftData.currentPropertyId = nil
		--[[ 当前属性值 ]]
		GiftData.currentPropertyValue = nil
		--[[ 加成方式（1整数显示，2百分比显示） ]]
		GiftData.propertyValueType = nil
		--[[ 激活需要的天赋点 ]]
		GiftData.costGiftPoint = nil
		--[[ 激活状态（0未解锁，1可激活，2已激活） ]]
		GiftData.activeState = nil
		--[[ 天赋描述 ]]
		GiftData.desc = nil
		--[[ 当前等级 ]]
		GiftData.currentLevel = nil
		--[[ 最大等级 ]]
		GiftData.maxLevel = nil
		--[[ 开启等级 ]]
		GiftData.openLevel = nil
		--[[ 下级属性id ]]
		GiftData.nextPropertyId = nil
		--[[ 下级属性id ]]
		GiftData.nextPropertyValue = nil
		--[[ 消耗道具 ]]
		GiftData.costItem = nil
		--[[ 消耗物品数量 ]]
		GiftData.costItemNum = nil
		--[[ 背包中物品数量 ]]
		GiftData.bagItemNum = nil
		--[[ 升级需要角色等级 ]]
		GiftData.upgradeNeedLevel = nil
