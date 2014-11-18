



-- create class
local StageInfoData = class("StageInfoData")

		--[[ 关卡id ]]
		StageInfoData.stageId = nil
		--[[ x位置 ]]
		StageInfoData.x = nil
		--[[ y位置 ]]
		StageInfoData.y = nil
		--[[ 关卡名称 ]]
		StageInfoData.stageName = nil
		--[[ 关卡描述 ]]
		StageInfoData.stageDesc = nil
		--[[ 关卡等级 ]]
		StageInfoData.minLevel = nil
		--[[ 怪物id ]]
		StageInfoData.monsterId = nil
		--[[ 怪物名称 ]]
		StageInfoData.monsterName = nil
		--[[ 怪物等级 ]]
		StageInfoData.monsterLevel = nil
		--[[ 经验值 ]]
		StageInfoData.rewardExperience = nil
		--[[ 货币类型 ]]
		StageInfoData.rewardCurrencyType = nil
		--[[ 货币数量 ]]
		StageInfoData.rewardCurrencyNum = nil
		--[[ 掉落物品名称 ]]
		StageInfoData.itemName = nil
		--[[ 怪物职业 ]]
		StageInfoData.monsterOccupation = nil
