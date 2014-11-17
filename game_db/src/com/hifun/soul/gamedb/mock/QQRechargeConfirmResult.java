package com.hifun.soul.gamedb.mock;

/**
 * 保存QQRechargeConfirmQuery的结果
 */
public class QQRechargeConfirmResult {
	private static int[] SUCCESS_RESULTCODES = new int[]{0,1069};
	private static int[] FAIL_NEED_CONFIRM_AGIN = new int[]{1061,1062,1099};
	/** 是否成功 */
	private boolean isSuccess;
	/** 是否需要再次确认 */
	private boolean isNeedConfirmAgain;
	/** 返回的结果码 */
	private String resultCode;
	/** 保存数据库实体 */
	private Object object;
	
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public boolean isNeedConfirmAgain() {
		return isNeedConfirmAgain;
	}
	public void setNeedConfirmAgain(boolean isNeedConfirmAgain) {
		this.isNeedConfirmAgain = isNeedConfirmAgain;
	}
	/**
	 * 判断是否是交易成功
	 * @param resultCode
	 * @return
	 */
	public static boolean checkIsSuccess(int resultCode) {
		for(int successCode : SUCCESS_RESULTCODES){
			if(successCode == resultCode){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断是否需要再次确认
	 * @param resultCode
	 * @return
	 */
	public static boolean checkIsNeedConfirmAgain(int resultCode) {
		for(int successCode : FAIL_NEED_CONFIRM_AGIN){
			if(successCode == resultCode){
				return true;
			}
		}
		return false;
	}
}
