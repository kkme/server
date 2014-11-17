package com.hifun.soul.manageweb.permission;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.hifun.soul.proto.services.Services.User;

/**
 * 权限工具
 * 
 * @author crazyjohn
 * 
 */
public class PermissionUtil {
	/**
	 * 判断某个用户是否有指定的权限
	 * 
	 * @param user
	 *            用户
	 * @param action
	 *            action名称
	 * @return true 有相应的权限; false 没有相应的权限
	 */
	public static boolean hasPermission(User user, String action) {
		// 找出User的操作列表
		List<PermissionType> userPermissions = null;
		try {
			userPermissions = initUserPermission(user);
		} catch (Exception e) {
			return false;
		}
		for (PermissionType p : userPermissions) {
			if (p.getAction().equals(action)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 初始化用户权限
	 * 
	 * @param user
	 * @return
	 * @throws IllegalStateException
	 *             当此权限不存在时候抛出
	 */
	private static List<PermissionType> initUserPermission(User user)
			throws IllegalStateException {
		List<PermissionType> userPermissions = stringToList(user
				.getPermissions());
		return userPermissions;
	}

	/**
	 * 对模块名进行解析
	 * 
	 * @param modulePath
	 *            原始模块名,可能会带/
	 * @return
	 */
	public static String getPath(String modulePath) {
		return modulePath.startsWith("/") ? modulePath.substring(1)
				: modulePath;
	}

	/**
	 * 把String权限转化为Permission列表;用在从数据库中加载的时候
	 * 
	 * @param str
	 *            用户权限在db中的存在形式
	 * @return Permission列表
	 */
	public static List<PermissionType> stringToList(String str) {
		List<PermissionType> permissionList = new ArrayList<PermissionType>();
		String[] firstArray = str.split(",");
		PermissionType permission = null;
		for (String permissionStr : firstArray) {
			permission = PermissionType
					.valueOf(Integer.parseInt(permissionStr));
			if(permission == null){
				continue;
			}
			permissionList.add(permission);
		}
		return permissionList;
	}

	/**
	 * 把Permission列表转化为String;用在存库的时候
	 * 
	 * @param list
	 * @return 在入库的时候把Permission转化成为字符窜
	 */
	public static String listToString(List<PermissionType> list) {
		if (list == null || list.size() == 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder("");
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i).getType());
			if (i < (list.size() - 1)) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

	/**
	 * 生成用户权限
	 * 
	 * @param arr
	 *            从request.getParameterValues中获取到的String数组
	 * @return 返回用户权限的String格式
	 */
	public static String generatePermissions(String[] arr) {
		if (arr == null) {
			arr = new String[0];
		}
		// 对权限进行排序
		List<Integer> intList = new ArrayList<Integer>();
		for (String item : arr) {
			intList.add(Integer.parseInt(item));
		}
		Collections.sort(intList);
		StringBuilder permissions = new StringBuilder();
		if (arr != null) {
			for (int i : intList) {
				PermissionType permission = PermissionType.valueOf(i);
				permissions.append(permission.toPermissionString());
				permissions.append(",");
			}
		}
		//FIXME : 是否知道的太多?
		permissions.append(PermissionType.USER_LOGIN_OUT.getType());
		return permissions.toString();
	}

	public static String getDefaultPermissions() {
		// TODO Auto-generated method stub
		return null;
	}
}
