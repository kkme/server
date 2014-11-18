



-- create class
local TitleInfoData = class("TitleInfoData")

		--[[ 军衔id ]]
		TitleInfoData.titleId = nil
		--[[ 军衔名称 ]]
		TitleInfoData.titleName = nil
		--[[ 升级所需威望 ]]
		TitleInfoData.needPrestige = nil
		--[[ 荣誉上限 ]]
		TitleInfoData.maxHonor = nil
		--[[ 每日俸禄 ]]
		TitleInfoData.titleSalary = nil
		--[[ 携带技能数量 ]]
		TitleInfoData.titleSkillNum = nil
		--[[ 获得的属性加成 ]]
		TitleInfoData.titleProperties = {}
