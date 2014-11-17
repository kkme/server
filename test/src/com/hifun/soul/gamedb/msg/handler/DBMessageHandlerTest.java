package com.hifun.soul.gamedb.msg.handler;

import java.io.Serializable;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.core.server.IMessageProcessor;
import com.hifun.soul.gamedb.DBCallbackFactory;
import com.hifun.soul.gamedb.entity.HumanEntity;
import com.hifun.soul.gamedb.msg.DBCallbackMessage;
import com.hifun.soul.gamedb.msg.DBInsertMessage;

/**
 * 测试单元: 数据库消息处理器的测试;<br>
 * 测试目的: 保证此处理器的逻辑运行正常;<br>
 * 
 * @author crazyjohn
 * 
 */
public class DBMessageHandlerTest {
	DBMessageHandler handler;
	IMessageProcessor msgProcessor;

	@Before
	public void setUp() throws Exception {
		handler = new DBMessageHandler();
		msgProcessor = new IMessageProcessor() {

			@Override
			public void start() {
				// TODO Auto-generated method stub

			}

			@Override
			public void stop() {
				// TODO Auto-generated method stub

			}

			@Override
			public void put(IMessage msg) {
				Assert.assertTrue(msg instanceof DBCallbackMessage);
			}

			@Override
			public boolean isFull() {
				// TODO Auto-generated method stub
				return false;
			}

		};
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testExecute() {
		// 测试插入动作
		DBInsertMessage<HumanEntity> insertMsg = new DBInsertMessage<HumanEntity>(
				DBCallbackFactory.<Serializable> getNullDBCallback(),
				new HumanEntity());
		this.handler.execute(insertMsg);
	}

}
