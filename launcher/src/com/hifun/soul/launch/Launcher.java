package com.hifun.soul.launch;

import java.io.File;
import java.lang.reflect.Method;

import com.hifun.soul.core.loader.MSClassLoader;

/**
 *  从加密的类包中启动程序 
 * @author crazyjohn
 *
 */
public class Launcher {
	private static Launcher instance;
	private static final String JAR_FILE = "soul.encrypt";
	private static final String JAR_MAIN = "com.hifun.soul.gameserver.GameServer";
	
	private Launcher(){
	}
	
	public static Launcher getInstance(){
		if(instance==null){
			instance = new Launcher();
		}
		return instance;
	}
	
	
	public void init(){
		try{
			loadClassJar();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void loadClassJar() throws Exception {
		try{
			MSClassLoader loader = new MSClassLoader(Thread.currentThread().getContextClassLoader(),new File(JAR_FILE));
			Thread.currentThread().setContextClassLoader(loader);
			Class<?> clazz = loader.loadClass(JAR_MAIN);
			Method method = clazz.getMethod("main", new Class[]{String[].class});
			method.invoke(null, new Object[]{new String[0]});
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args){
		Launcher.getInstance().init();
	}
}
