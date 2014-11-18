-- LoginCharVO
-- @author crazyjohn
-- @date 2014-11-10 19:02:05
--

-- create logger
local logger = LoggerFactory:getLogger("LoginCharVO")
-- create class
local LoginCharVO = class("LoginCharVO")

-- ctor
function LoginCharVO:ctor()
    -- body of ctor
end

function LoginCharVO:readSelf(data)
	local objcharList = self
	--[[
		writeLong(objcharList.getHumanGuid());
		writeString(objcharList.getName());
		writeInteger(objcharList.getOccupation());
		writeInteger(objcharList.getLevel());
		writeLong(objcharList.getEnergy());
		writeInteger(objcharList.getHomeLevel());
		writeLong(objcharList.getCoins());
	--]]
	objcharList.humanGuid = data:readLong()
	objcharList.name = data:readStringUShort()
	objcharList.occupation = data:readInt()
	objcharList.level = data:readInt()
	objcharList.energy = data:readLong()
	objcharList.homeLevel = data:readInt()
	objcharList.coins = data:readLong()
end

function LoginCharVO:writeSelf(data)
	local objcharList = self
	data:writeLong(objcharList.humanGuid)
	data:writeStringUShort(objcharList.name)
	data:writeInt(objcharList.occupation)
	data:writeInt(objcharList.level)
	data:writeLong(objcharList.energy)
	data:writeInt(objcharList.homeLevel)
	data:writeLong(objcharList.coins)
end

return LoginCharVO
