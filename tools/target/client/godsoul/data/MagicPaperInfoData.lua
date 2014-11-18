



-- create class
local MagicPaperInfoData = class("MagicPaperInfoData")

		--[[ 灵图ID ]]
		MagicPaperInfoData.paperId = nil
		--[[ 当前等级 ]]
		MagicPaperInfoData.currentLevel = nil
		--[[ 属性ID ]]
		MagicPaperInfoData.propertyId = nil
		--[[ 当前效果 ]]
		MagicPaperInfoData.currentEffect = nil
		--[[ 当前装备位等级上限 ]]
		MagicPaperInfoData.currentEquipBitMaxLevel = nil
		--[[ 升级效果 ]]
		MagicPaperInfoData.nextEffect = nil
		--[[ 升级后装备位等级上限 ]]
		MagicPaperInfoData.nextEquipBitMaxLevel = nil
		--[[ 消耗道具 ]]
		MagicPaperInfoData.costItem = nil
		--[[ 消耗道具数量 ]]
		MagicPaperInfoData.costItemNum = nil
		--[[ 是否是满级 ]]
		MagicPaperInfoData.isMaxLevel = nil
		--[[ 拥有道具数量 ]]
		MagicPaperInfoData.hasItemNum = nil
