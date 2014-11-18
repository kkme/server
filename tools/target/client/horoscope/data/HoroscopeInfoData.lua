



-- create class
local HoroscopeInfoData = class("HoroscopeInfoData")

		--[[ 星运所在包 ]]
		HoroscopeInfoData.horoscopeBag = nil
		--[[ 星运所在包中的位置 ]]
		HoroscopeInfoData.index = nil
		--[[ 星运id ]]
		HoroscopeInfoData.horoscopeId = nil
		--[[ 星运名字 ]]
		HoroscopeInfoData.name = nil
		--[[ 星运说明 ]]
		HoroscopeInfoData.desc = nil
		--[[ 颜色 ]]
		HoroscopeInfoData.color = nil
		--[[ 星运等级 ]]
		HoroscopeInfoData.level = nil
		--[[ 当前经验 ]]
		HoroscopeInfoData.experience = nil
		--[[ 升级需要的经验 ]]
		HoroscopeInfoData.maxExperience = nil
		--[[ 星运效果 ]]
		HoroscopeInfoData.key = nil
		--[[ 效果值 ]]
		HoroscopeInfoData.value = nil
		--[[ 星运所在包 ]]
		HoroscopeInfoData.icon = nil
		--[[ 下一级星运id ]]
		HoroscopeInfoData.nextHoroscopeId = nil
		--[[ 属性加成方式(1=+;2=*) ]]
		HoroscopeInfoData.propertyAddType = nil
