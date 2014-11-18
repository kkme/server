



-- create class
local OwnLegionInfoData = class("OwnLegionInfoData")

		--[[ 军团名称 ]]
		OwnLegionInfoData.legionName = nil
		--[[ 团长名称 ]]
		OwnLegionInfoData.commanderName = nil
		--[[ 公告 ]]
		OwnLegionInfoData.notice = nil
		--[[ 军团等级 ]]
		OwnLegionInfoData.legionLevel = nil
		--[[ 军团人数限制 ]]
		OwnLegionInfoData.memberLimit = nil
		--[[ 军团当前人数 ]]
		OwnLegionInfoData.memberNum = nil
		--[[ 经验值 ]]
		OwnLegionInfoData.experience = nil
		--[[ 升级所需经验值 ]]
		OwnLegionInfoData.levelMaxExperience = nil
		--[[ 捐献1魔晶获得军团经验 ]]
		OwnLegionInfoData.donateGetLegionExp = nil
		--[[ 捐献1魔晶获得个人贡献 ]]
		OwnLegionInfoData.donateGetSelfContri = nil
		--[[ 捐献1魔晶获得军团勋章 ]]
		OwnLegionInfoData.donateGetMedal = nil
