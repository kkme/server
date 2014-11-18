



-- create class
local SpriteInfoData = class("SpriteInfoData")

		--[[ uuid ]]
		SpriteInfoData.uuid = nil
		--[[ 精灵id ]]
		SpriteInfoData.spriteId = nil
		--[[ 精灵图标id ]]
		SpriteInfoData.iconId = nil
		--[[ 精灵當前等級 ]]
		SpriteInfoData.level = nil
		--[[ 精灵名称 ]]
		SpriteInfoData.name = nil
		--[[ 精灵品质 ]]
		SpriteInfoData.quality = nil
		--[[ 精灵类型 ]]
		SpriteInfoData.spriteType = nil
		--[[ 升级需要的灵气值 ]]
		SpriteInfoData.levelUpAura = nil
		--[[ 属性id ]]
		SpriteInfoData.propId = nil
		--[[ 属性值 ]]
		SpriteInfoData.propValue = nil
		--[[ 是否出战 ]]
		SpriteInfoData.isEquip = nil
		--[[ 放弃该等级精灵返还灵气值 ]]
		SpriteInfoData.dropReturnAura = nil
