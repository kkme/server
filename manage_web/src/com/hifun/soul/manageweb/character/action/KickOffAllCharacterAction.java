package com.hifun.soul.manageweb.character.action;

import com.google.protobuf.ServiceException;
import com.hifun.soul.manageweb.Managers;
import com.hifun.soul.manageweb.action.BaseAction;
import com.hifun.soul.manageweb.log.ManageLogType;
import com.hifun.soul.manageweb.log.ManageLogger;
import com.hifun.soul.manageweb.permission.PermissionType;
import com.hifun.soul.proto.services.Services.CharacterRpcService;
import com.hifun.soul.proto.services.Services.KickOffAllCharacterRequest;
import com.hifun.soul.proto.services.Services.KickOffAllCharacterResponse;

public class KickOffAllCharacterAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8374417387158730359L;
	/**
	 * 踢人下线
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public String kickOffAllCharacter() throws ServiceException {
		CharacterRpcService.BlockingInterface characterService = Managers.getServiceManager().getCharacterService();
		KickOffAllCharacterRequest request = KickOffAllCharacterRequest.newBuilder().build();
		KickOffAllCharacterResponse response = characterService.kickOffAllCharacter(null, request);
		ManageLogger.log(ManageLogType.KICK_OFF_ALL_CHARACTER,PermissionType.KICK_OFF_ALL_CHARACTER, "", response.getResult());
		if(response.getResult()){
			return "success";
		}
		else{
			return "failed";
		}
	}
}
