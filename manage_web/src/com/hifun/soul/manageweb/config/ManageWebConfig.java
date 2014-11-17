package com.hifun.soul.manageweb.config;

import com.hifun.soul.core.config.Config;

/**
 * 配置;
 * 
 * @author crazyjohn
 * 
 */
public class ManageWebConfig implements Config {
	private String templatesDirPath;
	private int debug;
	
	public int getDebug() {
		return debug;
	}

	public void setDebug(int debug) {
		this.debug = debug;
	}
	@Override
	public String getVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validate() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getIsDebug() {
		return getDebug()==1;
	}
	
	public String getTemplatesDirPath() {
		return templatesDirPath;
	}

	public void setTemplatesDirPath(String templatesDirPath) {
		this.templatesDirPath = templatesDirPath;
	}
}
