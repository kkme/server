



-- create class
local EliteStageInfo = class("EliteStageInfo")

		--[[ 关卡id ]]
		EliteStageInfo.stageId = nil
		--[[ 怪物名称 ]]
		EliteStageInfo.monsterName = nil
		--[[ 怪物图标id ]]
		EliteStageInfo.monsterIconId = nil
		--[[ 开启等级 ]]
		EliteStageInfo.openLevel = nil
		--[[  开启状态：0表示未开启，1表示已开启，2表示锁定可见 ]]
		EliteStageInfo.openState = nil
		--[[ 是否已挑战：true表示已挑战，false表示未挑战 ]]
		EliteStageInfo.challengeState = nil
		--[[ 奖励金币 ]]
		EliteStageInfo.coinNum = nil
		--[[ 奖励经验 ]]
		EliteStageInfo.exp = nil
		--[[ 奖励科技点 ]]
		EliteStageInfo.techPoint = nil
		--[[ 奖励物品名称 ]]
		EliteStageInfo.itemNames = {}
		--[[ 消耗体力值 ]]
		EliteStageInfo.energyNum = nil
		--[[ 关卡评星 ]]
		EliteStageInfo.star = nil
		--[[ 是否战胜过该关卡 ]]
		EliteStageInfo.passState = nil
		--[[ 副本类型信息 ]]
		EliteStageInfo.typeInfo = nil
