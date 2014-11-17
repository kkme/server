package com.hifun.soul.gameserver.battle.processor;

import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.core.server.QueueMessageProcessor;

/**
 * 战斗处理器基础实现;
 * 
 * @author crazyjohn
 * 
 */
public class BattleProcessor implements IBattleProcessor {
	private QueueMessageProcessor processor;

	public BattleProcessor(String processorName) {
		this.processor = new QueueMessageProcessor(processorName);
	}

	@Override
	public void start() {
		processor.start();
	}

	@Override
	public void stop() {
		processor.stop();
	}

	@Override
	public void put(IMessage msg) {
		processor.put(msg);
	}

	@Override
	public boolean isFull() {
		return processor.isFull();
	}

}
