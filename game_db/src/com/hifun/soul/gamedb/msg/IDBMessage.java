package com.hifun.soul.gamedb.msg;

import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.core.orm.IDBService;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.gamedb.agent.IDBAgent;

/**
 * 数据库操作相关的消息接口;<br>
 * 
 * <pre>
 * 1. 此对象的{@link #execute(IDBService)} 方法由数据库线程调用;
 * 2. 如果该方法被调用后有回调消息返回,则需要把此回调消息<font color='red'><b>投递到主线程的消息队列中<b></font>;
 * </pre>
 * 
 * @author crazyjohn
 * 
 */
public interface IDBMessage extends IMessage {

	/**
	 * 数据库操作消息的执行方法;
	 * 
	 * @param dbAgent
	 *            数据库代理入口;
	 * @return 返回一个数据库回调消息,此消息需要被重新投递到主线程中进行处理;
	 */
	public DBCallbackMessage<?> execute(IDBAgent dbAgent);

	/**
	 * 获取关联的class类型;
	 * 
	 * @return
	 */
	public Class<? extends IEntity> getEntityClass();
}
