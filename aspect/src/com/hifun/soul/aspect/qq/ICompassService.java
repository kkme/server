package com.hifun.soul.aspect.qq;


/**
 * 罗盘服务接口;
 * 
 * @author crazyjohn
 * 
 */
public interface ICompassService {

	public void login(int appid, int domain, int worldid, long opuid,
			String opopenid, int level);

	public void register(int appid, int domain, int worldid, long opuid,
			String opopenid);

	public void consume(int appid, int domain, int worldid, long opuid,
			String opopenid, int modifyfee);
}
