



-- create class
local LegionMineInfoData = class("LegionMineInfoData")

		--[[ 矿堆索引 ]]
		LegionMineInfoData.mineIndex = nil
		--[[ 是否可战斗 ]]
		LegionMineInfoData.canBattle = nil
		--[[ 是否可移动 ]]
		LegionMineInfoData.canMove = nil
		--[[ 是否可干扰 ]]
		LegionMineInfoData.canDisturb = nil
		--[[ 占领军团类型 ]]
		LegionMineInfoData.joinLegionType = nil
		--[[ 包围状态 ]]
		LegionMineInfoData.surroundState = nil
		--[[ 占领人数 ]]
		LegionMineInfoData.occupyNum = nil
		--[[ 占领人数是否可见 ]]
		LegionMineInfoData.occupyNumVisible = nil
		--[[ 收益是否加倍 ]]
		LegionMineInfoData.isDouble = nil
		--[[ 是否是红矿 ]]
		LegionMineInfoData.isRedMine = nil
		--[[ 是否是自己所在的矿 ]]
		LegionMineInfoData.isSelf = nil
		--[[ 矿位类型 ]]
		LegionMineInfoData.mineType = nil
