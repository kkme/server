-- CharacterOccupationVO
-- @author crazyjohn
-- @date 2014-11-10 14:03:57
--

-- create logger
local logger = LoggerFactory:getLogger("CharacterOccupationVO")
-- create class
local CharacterOccupationVO = class("CharacterOccupationVO")

-- ctor
function CharacterOccupationVO:ctor()
    -- body of ctor
end

function CharacterOccupationVO:readSelf(data)
	-- read self from data
	local objcharacterTemplates = self
	objcharacterTemplates.occupationType = data:readInt()
	objcharacterTemplates.nameIcon = data:readInt()
	objcharacterTemplates.feature = data:readStringUShort()
	objcharacterTemplates.classes = data:readStringUShort()
	objcharacterTemplates.giftIcon1 = data:readInt()
	objcharacterTemplates.giftName1 = data:readStringUShort()
	objcharacterTemplates.giftDesc1 = data:readStringUShort()
	objcharacterTemplates.giftIcon2 = data:readInt()
	objcharacterTemplates.giftName2 = data:readStringUShort()
	objcharacterTemplates.giftDesc2 = data:readStringUShort()
	objcharacterTemplates.giftIcon3 = data:readInt()
	objcharacterTemplates.giftName3 = data:readStringUShort()
	objcharacterTemplates.giftDesc3 = data:readStringUShort()
end

function CharacterOccupationVO:writeSelf(data)
	-- write self to data
	local objcharacterTemplates = self
	data:writeInt(objcharacterTemplates.occupationType)
	data:writeInt(objcharacterTemplates.nameIcon)
	data:writeStringUShort(objcharacterTemplates.feature)
	data:writeStringUShort(objcharacterTemplates.classes)
	data:writeInt(objcharacterTemplates.giftIcon1)
	data:writeStringUShort(objcharacterTemplates.giftName1)
	data:writeStringUShort(objcharacterTemplates.giftDesc1)
	data:writeInt(objcharacterTemplates.giftIcon2)
	data:writeStringUShort(objcharacterTemplates.giftName2)
	data:writeStringUShort(objcharacterTemplates.giftDesc2)
	data:writeInt(objcharacterTemplates.giftIcon3)
	data:writeStringUShort(objcharacterTemplates.giftName3)
	data:writeStringUShort(objcharacterTemplates.giftDesc3)
end
return CharacterOccupationVO
