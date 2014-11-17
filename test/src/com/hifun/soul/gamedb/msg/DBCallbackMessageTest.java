package com.hifun.soul.gamedb.msg;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.hifun.soul.gamedb.callback.IDBCallback;

/**
 * 测试单元: 数据库回调消息;<br>
 * 测试目的: 检验数据库回调消息的工作流程的正确性;<br>
 * 
 * @author crazyjohn
 * 
 */
public class DBCallbackMessageTest {
	private static final Integer EXPECT_RESULT = 100;
	protected static final String EXPECT_ERROR_INFO = "Error";
	DBCallbackMessage<Integer> callbackMsg = null;
	IDBCallback<Integer> callback = null;
	
	@Before
	public void setUp() throws Exception {
		callbackMsg = new DBCallbackMessage<Integer>();
		callbackMsg.setDBCallback(callback);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSucceed() {
		callback = new IDBCallback<Integer>(){

			@Override
			public void onSucceed(Integer result) {
				Assert.assertTrue(result == EXPECT_RESULT);
			}

			@Override
			public void onFailed(String errorMsg) {
				// TODO Auto-generated method stub
				
			}
			
		};
		callbackMsg.setResult(EXPECT_RESULT);
		callbackMsg.execute();
		
	}
	
	@Test
	public void testFailed() {
		callback = new IDBCallback<Integer>(){

			@Override
			public void onSucceed(Integer result) {
				
			}

			@Override
			public void onFailed(String errorMsg) {
				Assert.assertTrue(errorMsg == EXPECT_ERROR_INFO);
			}
			
		};
		callbackMsg.actionFailed(EXPECT_ERROR_INFO);
		callbackMsg.execute();
		
	}

}
