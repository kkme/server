-- 玩家登录相关GC消息;
-- @author crazyjohn
-- @date 2014-11-09 21:33:52
--

local GCPlayerMessage = class("GCPlayerMessage")

function GCPlayerMessage:register()
    -- 注册处理方法
    GlobalMessageRegistry:register(MessageType.GC_PLAYER_LOGIN_RESULT, handler(self, self.GC_LOGIN_RESULT))
    GlobalMessageRegistry:register(MessageType.GC_CHARACTER_TEMPLATE, handler(self, self.GC_CHARACTER_TEMPLATE))
    GlobalMessageRegistry:register(MessageType.GC_CHAR_LIST, handler(self, self.GC_CHAR_LIST))
    GlobalMessageRegistry:register(MessageType.GC_CREATE_CHAR_RESULT, handler(self, self.GC_CREATE_CHAR_RESULT))
    GlobalMessageRegistry:register(MessageType.GC_ENTER_SCENE, handler(self, self.GC_ENTER_SCENE))
    GlobalMessageRegistry:register(MessageType.GC_AUTO_NAME, handler(self, self.GC_AUTO_NAME))
    -- 注册处理器
    GlobalHandlers:register("LoginHandler", requireNewHandler("LoginHandler"))
end

-- GC_LOGIN_RESULT;
function GCPlayerMessage:GC_LOGIN_RESULT(data)
    -- body
    -- 1. deserizalize all params from data
    -- 2. pass the params to handler
    -- eg:some mock code
    -- local resultCode = readInt(data)
    -- Handlers:getHandler("LoginHandler"):GC_LOGIN_RESULT(resultCode)
    local resultCode = data:readInt()
    GlobalHandlers:getHandler("LoginHandler"):GC_LOGIN_RESULT(resultCode)
end

-- GC_CHAR_LIST;
function GCPlayerMessage:GC_CHAR_LIST(data)
    -- body
    local count = data:readShort()
    local chars = {}
    if count > 0 then
        for i=1,count do
            chars[i] = requireAndNew("client.proto.LoginCharVO")
            chars[i]:readSelf(data)
        end
    end
    GlobalHandlers:getHandler("LoginHandler"):GC_CHAR_LIST(chars)
end

-- GC_CHARACTER_TEMPLATE;
function GCPlayerMessage:GC_CHARACTER_TEMPLATE(data)
    -- body
    local count = data:readShort()
    local templates = {}
    if count > 0 then
        for i=1,count do
            templates[i] = requireAndNew("client.proto.CharacterOccupationVO")
            templates[i]:readSelf(data)
        end
    end
    GlobalHandlers:getHandler("LoginHandler"):GC_CHARACTER_TEMPLATE(templates)
end

function GCPlayerMessage:GC_CREATE_CHAR_RESULT(data)
    local resultCode = data:readInt()
    GlobalHandlers:getHandler("LoginHandler"):GC_CREATE_CHAR_RESULT(resultCode)
end

function GCPlayerMessage:GC_ENTER_SCENE(data)
    GlobalHandlers:getHandler("LoginHandler"):GC_ENTER_SCENE()
end

function GCPlayerMessage:GC_AUTO_NAME(data)
    local roleName = data:readStringUShort()
    GlobalHandlers:getHandler("LoginHandler"):GC_AUTO_NAME(roleName)
end
return GCPlayerMessage
