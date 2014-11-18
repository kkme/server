



-- create class
local ActivityBaseData = class("ActivityBaseData")

		--[[ 活动Id(或分类id) ]]
		ActivityBaseData.id = nil
		--[[ 活动类型：0表示该对象是一个分类 ]]
		ActivityBaseData.activityType = nil
		--[[ 图标id ]]
		ActivityBaseData.iconId = nil
		--[[ 名称 ]]
		ActivityBaseData.name = nil
		--[[ 活动状态：参见ActivityState枚举 ]]
		ActivityBaseData.state = nil
