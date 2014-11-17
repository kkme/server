package com.hifun.soul.gameserver.compass;

/**
 * 罗盘服务接口;
 * 
 * @author crazyjohn
 * 
 */
public interface ICompassService {

	/**
	 * 登录统计接口;
	 * 
	 * @param opuid
	 *            玩家的GUID(咱们自己的是long但是腾讯要求要int32fuck)
	 * @param opopenid
	 * @param level
	 */
	public void login(int opuid, String opopenid, int level);

	/**
	 * 注册统计接口; FIXME: server-guy 参考login接口, 能从config配置中拿到的直接拿, 然后改造接口, 去掉不必要的参数;
	 * 
	 * @param appid
	 * @param domain
	 * @param worldid
	 * @param opuid
	 * @param opopenid
	 */
	public void register(int opuid, String opopenid);

	/**
	 * 消费游戏币接口;
	 * 
	 * @param opuid
	 *            玩家的guid 需要强转为int32;
	 * @param opopenid
	 *            直接取Player.getAccount;
	 * @param itemid
	 *            物品的ID;
	 * @param itemtype
	 *            物品的类型;
	 * @param itemcnt
	 *            物品的数量;
	 * @param modifycoin
	 *            用户进行操作后，游戏虚拟金币的变化值。例如用户购买道具后，虚拟金币减少3000，则填入-3000。
	 * @param totalcoin用户进行操作后
	 *            ，虚拟金币的总量。例如用户拥有虚拟金币100，购买了某物品，消耗虚拟金币10，此处填入90。
	 * @param level
	 */
	public void consumeCoin(int opuid, String opopenid, String itemid,
			String itemtype, int itemcnt, int modifycoin, int totalcoin,
			int level);

	/**
	 * 消费魔晶接口;
	 * 
	 * @param opuid
	 * @param opopenid
	 * @param modifyfee
	 *            游戏币变化值。 用户进行操作后，游戏币的变化值。如果没有变化，则填0。上报单位为Q分（100Q分 = 10Q点 =
	 *            1Q币）。 游戏币为用户通过人民币或者Q点/Q
	 *            币兑换的游戏内等值货币(例如“点券/金币/元宝”等)，在游戏内具有真实的价值，可用于购买游戏内商品。 例如：
	 *            (1)某用户通过Q币直购游戏内商品，消费10Q币，则记入1000。
	 *            (2)某用户通过点券(游戏币)购买游戏内商品，消费10点券(1Q币=100点券)，则记入100。
	 *            (3)某用户通过Q币兑换点券(游戏币)，消费10Q币，则记入1000。 <br>
	 *           <font color='red'> 咱们的1魔晶就是1Q点</font>;
	 * @param itemid
	 * @param itemtype
	 * @param itemcnt
	 * @param totalfee用户进行操作后
	 *            ，游戏币的总量。 例如用户拥有游戏币100，购买了某物品，消耗游戏币10，此处填入90。
	 * @param level
	 */
	public void consumeCrystal(int opuid, String opopenid, int modifyfee,
			String itemid, String itemtype, int itemcnt, int totalfee, int level);
}
