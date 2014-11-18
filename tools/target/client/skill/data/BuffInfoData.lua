



-- create class
local BuffInfoData = class("BuffInfoData")

		--[[ 当前buff的唯一标识 ]]
		BuffInfoData.buffId = nil
		--[[ buff类型 ]]
		BuffInfoData.buffType = nil
		--[[ buff资源id ]]
		BuffInfoData.buffResourceId = nil
		--[[ buff名称 ]]
		BuffInfoData.buffName = nil
		--[[ buff描述 ]]
		BuffInfoData.buffDesc = nil
		--[[ buff自身类型(buff or debuff) ]]
		BuffInfoData.buffSelfType = nil
		--[[ 持续的回合数 ]]
		BuffInfoData.round = nil
