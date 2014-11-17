package com.hifun.soul.gamedb.msg;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.gamedb.DBUtil;
import com.hifun.soul.gamedb.agent.IDBAgent;
import com.hifun.soul.gamedb.callback.IDBCallback;

/**
 * 测试单元: 数据库消息的自处理; <br>
 * 测试目的: 数据库消息的工作流是否正常;<br>
 * 
 * @author crazyjohn
 * 
 */
public class AbstractDBMessageTest {
	protected static final Integer EXPECT_RESULT = 100;
	AbstractDBMessage<Integer> mockExceptionDBMessage = null;
	AbstractDBMessage<Integer> mockSucceedDBMessage = null;
	IDBCallback<Integer> callback = null;

	private boolean isExecuteFailed = false;

	@Before
	public void setUp() throws Exception {
		// 构建回调对象
		callback = new IDBCallback<Integer>() {
			@Override
			public void onSucceed(Integer result) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFailed(String errorMsg) {

			}

		};
		// 构建mock数据库消息
		mockExceptionDBMessage = new AbstractDBMessage<Integer>(callback) {

			@Override
			protected void doRealDBAction(IDBAgent dbAgent,
					DBCallbackMessage<Integer> callbackMsg) {
				throw new RuntimeException("Action failed!");
			}

			@Override
			protected DBCallbackMessage<Integer> buildCallbackMessage() {
				// TODO Auto-generated method stub
				return new DBCallbackMessage<Integer>() {
					@Override
					public void actionFailed(String errorInfo) {
						isExecuteFailed = true;
						super.actionFailed(errorInfo);
					}
				};
			}

			@Override
			public Class<? extends IEntity> getEntityClass() {
				// TODO Auto-generated method stub
				return null;
			}

		};

		mockSucceedDBMessage = new AbstractDBMessage<Integer>(callback) {

			@Override
			protected void doRealDBAction(IDBAgent dbAgent,
					DBCallbackMessage<Integer> callbackMsg) {
				// 成功执行
				callbackMsg.setResult(EXPECT_RESULT);
			}

			@Override
			protected DBCallbackMessage<Integer> buildCallbackMessage() {
				// TODO Auto-generated method stub
				return new DBCallbackMessage<Integer>() {
					@Override
					public void actionFailed(String errorInfo) {
						super.actionFailed(errorInfo);
					}
				};
			}

			@Override
			public Class<? extends IEntity> getEntityClass() {
				// TODO Auto-generated method stub
				return null;
			}

		};

	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * 测试异常执行情况
	 */
	@Test
	public void testExecuteException() {
		mockExceptionDBMessage.execute(DBUtil.getDBAgent());
		Assert.assertTrue(isExecuteFailed);
	}

	/**
	 * 测试正常执行情况
	 */
	@Test
	public void testExecuteSucceed() {
		DBCallbackMessage<Integer> callbackMsg = mockSucceedDBMessage
				.execute(DBUtil.getDBAgent());
		Assert.assertTrue(callbackMsg.getResult() == EXPECT_RESULT);
	}

}
