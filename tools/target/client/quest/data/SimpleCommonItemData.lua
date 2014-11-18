



-- create class
local SimpleCommonItemData = class("SimpleCommonItemData")

		--[[ 道具实例uuid，目前用于客户端实现快捷栏，不可以放入快捷栏的和道具此属性为空 ]]
		SimpleCommonItemData.UUID = nil
		--[[ 道具Id ]]
		SimpleCommonItemData.itemId = nil
		--[[ 道具名称 ]]
		SimpleCommonItemData.name = nil
		--[[ 道具描述 ]]
		SimpleCommonItemData.desc = nil
		--[[ 道具的图标 ]]
		SimpleCommonItemData.icon = nil
