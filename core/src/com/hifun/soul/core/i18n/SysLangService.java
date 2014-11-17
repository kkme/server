package com.hifun.soul.core.i18n;

import com.hifun.soul.common.IInitializeRequired;

/**
 * 多语言管理服务管理
 *
 *
 */
public interface SysLangService extends IInitializeRequired {

	/**
	 * 读取系统内部的多语言数据
	 * 
	 * @param key
	 * @return
	 */
	public String read(Integer key);
	
	/**
	 * 读取系统内部的多语言数据,如果params不为空,则用其格式化结果
	 * 
	 * @param key
	 * @param params
	 * @return
	 */
	public String read(Integer key, Object... params);
	
	/**
	 * 设置读取路径
	 * 
	 * @param sysLangConfigFile
	 */
	public void setSysLangConfigFile(String sysLangConfigFile);
	
}
