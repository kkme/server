-- 玩家登录相关CG消息;
-- @author crazyjohn
-- @date 2014-11-09 21:33:20
--

local CGPlayerMessage = class("CGPlayerMessage")

-- login(rpc method for client to use);
-- @param userName;
-- @param password;
function CGPlayerMessage:CG_PLAYER_LOGIN(userName, password)
	-- bodyArray;
	local bodyArray = requireNewByteArray()
	-- 1. write to bodyArray
	bodyArray:writeStringUShort(userName)
	bodyArray:writeStringUShort(password)
	--bodyArray:setPos(1)
	-- 2. use socket to send
	GameSocket:sendPacket(MessageType.CG_PLAYER_LOGIN, bodyArray)
end

function CGPlayerMessage:CG_GET_CHAR_LIST()
	GameSocket:sendPacket(MessageType.CG_GET_CHAR_LIST)
end

function CGPlayerMessage:CG_CREATE_CHAR(name, occupation)
	-- bodyArray;
	local bodyArray = requireNewByteArray()
	bodyArray:writeStringUShort(name)
	bodyArray:writeInt(occupation)
	GameSocket:sendPacket(MessageType.CG_CREATE_CHAR, bodyArray)
end

function CGPlayerMessage:CG_SELECT_CHAR(charIndex)
	local bodyArray = requireNewByteArray()
	bodyArray:writeInt(charIndex)
	GameSocket:sendPacket(MessageType.CG_SELECT_CHAR, bodyArray)
end

function CGPlayerMessage:CG_ENTER_SCENE_READY()
	GameSocket:sendPacket(MessageType.CG_ENTER_SCENE_READY)
end

function CGPlayerMessage:CG_AUTO_NAME(occupation)
	local bodyArray = requireNewByteArray()
	bodyArray:writeInt(occupation)
	GameSocket:sendPacket(MessageType.CG_AUTO_NAME, bodyArray)
end

return CGPlayerMessage
