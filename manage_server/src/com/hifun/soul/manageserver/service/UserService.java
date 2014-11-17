package com.hifun.soul.manageserver.service;

import java.util.ArrayList;
import java.util.List;

import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;
import com.hifun.soul.core.orm.IDBService;
import com.hifun.soul.manageserver.entity.UserEntity;
import com.hifun.soul.proto.services.Services.AddUserRequest;
import com.hifun.soul.proto.services.Services.AddUserResponse;
import com.hifun.soul.proto.services.Services.DeleteUserRequest;
import com.hifun.soul.proto.services.Services.DeleteUserResponse;
import com.hifun.soul.proto.services.Services.DoLoginRequest;
import com.hifun.soul.proto.services.Services.DoLoginResponse;
import com.hifun.soul.proto.services.Services.GetUserByIdRequest;
import com.hifun.soul.proto.services.Services.GetUserByIdResponse;
import com.hifun.soul.proto.services.Services.GetUserListRequest;
import com.hifun.soul.proto.services.Services.GetUserListResponse;
import com.hifun.soul.proto.services.Services.ManagerUpdateUserPasswordRequest;
import com.hifun.soul.proto.services.Services.ManagerUpdateUserPasswordResponse;
import com.hifun.soul.proto.services.Services.UpdateUserPasswordRequest;
import com.hifun.soul.proto.services.Services.UpdateUserPasswordResponse;
import com.hifun.soul.proto.services.Services.UpdateUserPermissionRequest;
import com.hifun.soul.proto.services.Services.UpdateUserPermissionResponse;
import com.hifun.soul.proto.services.Services.User;
import com.hifun.soul.proto.services.Services.UserRpcService;
import com.hifun.soul.core.encrypt.EncryptUtils;

/**
 * 后台用户服务;
 * 
 * @author crazyjohn
 * 
 */
public class UserService extends UserRpcService {
	/** Manage-DB 数据服务 */
	private IDBService manageDbService;

	public UserService(IDBService dbService) {
		this.manageDbService = dbService;
	}

	@Override
	public void doLogin(RpcController controller, DoLoginRequest request,
			RpcCallback<DoLoginResponse> done) {
		if (request.getUserName() == null && request.getPassword() == null) {
			done.run(DoLoginResponse.newBuilder().setResult(false).build());
			return;
		}
		String encryptPwd = EncryptUtils.generateMD5String(request.getPassword().getBytes());			
		List<?> users = manageDbService.findByNamedQueryAndNamedParam(
				"queryUserByNameAndPwd",
				new String[] { "userName", "password" },
				new Object[] { request.getUserName(), encryptPwd });
		if (users == null || users.isEmpty()) {
			done.run(DoLoginResponse.newBuilder().setResult(false).build());
			return;
		}
		User.Builder user = ((UserEntity) users.get(0)).getBuilder();
		done.run(DoLoginResponse.newBuilder().setResult(true).setUser(user)
				.build());		
	}

	@Override
	public void getUserList(RpcController controller,
			GetUserListRequest request, RpcCallback<GetUserListResponse> done) {
		@SuppressWarnings("unchecked")
		List<UserEntity> users = (List<UserEntity>) manageDbService
				.findByNamedQuery("queryAllUser");
		if (users == null || users.isEmpty()) {
			done.run(GetUserListResponse.newBuilder().build());
			return;
		}
		List<User> results = new ArrayList<User>();
		for (UserEntity entity : users) {
			results.add(entity.getBuilder().build());
		}
		done.run(GetUserListResponse.newBuilder().addAllUser(results).build());
	}

	@Override
	public void addUser(RpcController controller, AddUserRequest request,
			RpcCallback<AddUserResponse> done) {
		if(request.getPassword().length()<6){
			done.run(AddUserResponse.newBuilder().setResult(false).build());
			return;
		}
		User.Builder user = User.newBuilder()
				.setUserName(request.getUserName())
				.setPassword(EncryptUtils.generateMD5String(request.getPassword().getBytes()))
				.setPermissions(request.getPermissions());
		UserEntity entity = new UserEntity(user);
		this.manageDbService.insert(entity);
		done.run(AddUserResponse.newBuilder().setResult(true).build());
	}

	@SuppressWarnings("deprecation")
	@Override
	public void deleteUser(RpcController controller, DeleteUserRequest request,
			RpcCallback<DeleteUserResponse> done) {
		this.manageDbService.deleteById(UserEntity.class, request.getUserId());
		done.run(DeleteUserResponse.newBuilder().setResult(true).build());
	}

	@Override
	public void updateUserPermission(RpcController controller, UpdateUserPermissionRequest request,
			RpcCallback<UpdateUserPermissionResponse> done) {
		// 更新用户信息
		UserEntity entity = getUserEntity(request.getUserId());
		if(entity == null){
			done.run(UpdateUserPermissionResponse.newBuilder().setResult(false).build());
			return ;
		}
		entity.setPermissions(request.getPermissions());
		this.manageDbService.update(entity);		
		done.run(UpdateUserPermissionResponse.newBuilder().setResult(true).build());
	}

	@Override
	public void getUserById(RpcController controller,
			GetUserByIdRequest request, RpcCallback<GetUserByIdResponse> done) {
		UserEntity entity = getUserEntity(request.getUserId());
		if (entity == null) {
			done.run(GetUserByIdResponse.newBuilder().setResult(false).build());
			return;
		}
		GetUserByIdResponse.Builder response = GetUserByIdResponse.newBuilder().setResult(true)
		.setUser(entity.getBuilder());		
		done.run(response.build());
	}
	
	private UserEntity getUserEntity(int userId){
		return this.manageDbService.get(UserEntity.class,userId);
	}
	
	@Override
	public void updateUserPassword(RpcController controller, UpdateUserPasswordRequest request,
			RpcCallback<UpdateUserPasswordResponse> done) {
		UserEntity entity = getUserEntity(request.getUserId());
		if(entity == null){
			done.run(UpdateUserPasswordResponse.newBuilder().setResult(false).build());
			return ;
		}
		// 更新用户信息
		String oldPasswordEncrypt = EncryptUtils.generateMD5String(request.getOldPassword().getBytes());
		if(!oldPasswordEncrypt.equals(entity.getPassword())){
			done.run(UpdateUserPasswordResponse.newBuilder().setResult(false).build());
			return ;
		}
		String encryptPwd = EncryptUtils.generateMD5String(request.getNewPassword().getBytes());
		entity.setPassword(encryptPwd);
		this.manageDbService.update(entity);
		done.run(UpdateUserPasswordResponse.newBuilder().setResult(true).build());

	}

	@Override
	public void managerUpdateUserPassword(RpcController controller, ManagerUpdateUserPasswordRequest request,
			RpcCallback<ManagerUpdateUserPasswordResponse> done) {
		UserEntity entity = getUserEntity(request.getUserId());
		if(entity == null){
			done.run(ManagerUpdateUserPasswordResponse.newBuilder().setResult(false).build());
			return ;
		}
		// 更新用户信息		
		String encryptPwd = EncryptUtils.generateMD5String(request.getNewPassword().getBytes());
		entity.setPassword(encryptPwd);
		this.manageDbService.update(entity);
		done.run(ManagerUpdateUserPasswordResponse.newBuilder().setResult(true).build());
	}


}