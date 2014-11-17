package com.hifun.soul.core.server;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

import com.hifun.soul.common.constants.CommonErrorLogInfo;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.core.msg.ISessionMessage;
import com.hifun.soul.core.util.ErrorsUtil;

/**
 * 基于队列的消息处理器,使用单线程依次用于处理队列中消息
 * 
 * @see {@link NoQueueMessageProcessor}
 */
public class QueueMessageProcessor implements Runnable, IMessageProcessor {
	/** 消息队列 * */
	private final BlockingQueue<IMessage> queue;

	/** 消息处理线程停止时剩余的还未处理的消息 **/
	private volatile List<IMessage> leftQueue;

	/** 消息处理器 * */
	private final IMessageHandler<IMessage> handler;

	/** 消息处理线程 */
	private volatile Thread messageProcessorThread;

	/** 运行的线程id * */
	private long threadId;

	/** 是否停止 */
	private volatile boolean stop = false;

	/** 处理的消息总数 */
	private long statisticsMessageCount = 0;

	/** 等待消息处理线程退出 */
	private volatile CountDownLatch stopLatch;

	private final boolean processLeft;
	/** 可以自定义的线程名字, 方便查看 */
	private String goodName;

	public QueueMessageProcessor(String processThreadName,
			IMessageHandler<IMessage> messageHandler) {
		this(messageHandler, false);
		this.goodName = processThreadName;
	}

	public QueueMessageProcessor(IMessageHandler<IMessage> messageHandler,
			boolean processLeft) {
		queue = new LinkedBlockingQueue<IMessage>();
		handler = messageHandler;
		this.processLeft = processLeft;
	}

	/**
	 * @see ExecutableMessageHandler
	 */
	public QueueMessageProcessor(String processThreadName) {
		this(processThreadName, new ExecutableMessageHandler());
	}

	@Override
	public void put(IMessage msg) {
		if (this.stop) {
			return;
		}
		try {
			queue.put(msg);
			if (Loggers.MSG_LOGGER.isDebugEnabled()) {
				Loggers.MSG_LOGGER.debug("put queue size:" + queue.size());
			}
		} catch (InterruptedException e) {
			if (Loggers.MSG_LOGGER.isErrorEnabled()) {
				Loggers.MSG_LOGGER.error(
						CommonErrorLogInfo.THRAD_ERR_INTERRUPTED, e);
			}
		}
	}

	/**
	 * 主处理函数，从消息队列中阻塞取出消息，然后处理
	 */
	public void run() {
		threadId = Thread.currentThread().getId();
		final CountDownLatch countDownLatch = new CountDownLatch(1);
		this.stopLatch = countDownLatch;
		try {
			while (!stop) {
				try {
					process(queue.take());
					if (Loggers.MSG_LOGGER.isDebugEnabled()) {
						Loggers.MSG_LOGGER.debug("run queue size:"
								+ queue.size());
					}
				} catch (InterruptedException e) {
					if (Loggers.MSG_LOGGER.isWarnEnabled()) {
						Loggers.MSG_LOGGER
								.warn("[#CORE.QueueMessageProcessor.run] [Stop process]");
					}
					break;
				} catch (Exception e) {
					Loggers.MSG_LOGGER.error(
							CommonErrorLogInfo.MSG_PRO_ERR_EXP, e);
				}
			}
		} finally {
			countDownLatch.countDown();
		}
	}

	/**
	 * 处理具体的消息，每个消息有自己的参数和来源,如果在处理消息的过程中发生异常,则马上将此消息的发送者断掉
	 * 
	 * @param msg
	 */
	public void process(IMessage msg) {
		if (msg == null) {
			if (Loggers.MSG_LOGGER.isWarnEnabled()) {
				Loggers.MSG_LOGGER
						.warn("[#CORE.QueueMessageProcessor.process] ["
								+ CommonErrorLogInfo.MSG_PRO_ERR_NULL_MSG + "]");
			}
			return;
		}
		long begin = 0;
		if (Loggers.MSG_LOGGER.isInfoEnabled()) {
			begin = System.nanoTime();
		}
		this.statisticsMessageCount++;
		try {
			this.handler.execute(msg);
		} catch (Exception e) {
			if (Loggers.MSG_LOGGER.isErrorEnabled()) {
				Loggers.MSG_LOGGER.error(ErrorsUtil.error("Error",
						"#.QueueMessageProcessor(name:" + this.goodName
								+ ").process", "param"), e);
			}
			try {
				// TODO 此处的逻辑应该由一个接口代为实现
				if (msg instanceof ISessionMessage<?>) {
					// 如果在处理消息的过程中出现了错误,将其断掉
					final ISessionMessage<?> imsg = (ISessionMessage<?>) msg;
					if (imsg.getSession() != null) {
						if (Loggers.MSG_LOGGER.isErrorEnabled()) {
							Loggers.MSG_LOGGER.error(ErrorsUtil.error(
									CommonErrorLogInfo.MSG_PRO_ERR_EXP,
									"Disconnect sender", msg.getTypeName()
											+ "@" + imsg.getSession()), e);
						}
						imsg.getSession().close();
					}
				}
			} catch (Exception ex) {
				Loggers.MSG_LOGGER.error(ErrorsUtil.error(
						CommonErrorLogInfo.MSG_PRO_ERR_DIS_SENDER_FAIL,
						"#CORE.QueueMessageProcessor.process", null), ex);
			}
		} finally {
			if (Loggers.MSG_LOGGER.isDebugEnabled()) {
				// 特例，统计时间跨度
				long time = (System.nanoTime() - begin) / (1000 * 1000);
				if (time > 1) {
					int type = msg.getType();
					Loggers.MSG_LOGGER.debug("Message:" + msg.getTypeName()
							+ " Type:" + type + " Time:" + time + "ms"
							+ " Total:" + this.statisticsMessageCount + "["
							+ this.goodName + "]");
				}
			}
		}
	}

	public IMessageHandler<IMessage> getHandler() {
		return handler;
	}

	public long getThreadId() {
		return threadId;
	}

	/**
	 * 取得未处理消息队列的长度
	 * 
	 * @return
	 */
	public int getQueueLength() {
		return queue.size();
	}

	public synchronized void stop() {
		Loggers.MSG_LOGGER.info("Message processor thread "
				+ this.messageProcessorThread + " stopping ...");
		this.stop = true;
		if (this.messageProcessorThread != null) {
			this.messageProcessorThread.interrupt();
		}
		Loggers.MSG_LOGGER.info("Message processor thread "
				+ this.messageProcessorThread + " finish");
		this.messageProcessorThread = null;
		// 需要处理完未处理的消息
		// 等待消息处理线程退出
		try {
			final CountDownLatch _stopLatch = this.stopLatch;
			if (_stopLatch != null) {
				_stopLatch.await();
			}
			// 将未处理的消息放入到leftQueue中,以备后续处理
			if (this.processLeft) {
				this.leftQueue = new LinkedList<IMessage>();
				while (true) {
					IMessage _msg = this.queue.poll();
					if (_msg != null) {
						this.leftQueue.add(_msg);
					} else {
						break;
					}
				}
			}
		} catch (InterruptedException e) {
		}
		this.queue.clear();
	}

	/**
	 * 开始消息处理
	 */
	public synchronized void start() {
		this.queue.clear();
		stop = false;
		if (this.messageProcessorThread != null) {
			throw new IllegalStateException("The thread has already run"
					+ this.messageProcessorThread);
		}
		this.messageProcessorThread = new Thread(this,
				"MessageProcessor Thread" + "--" + this.goodName);
		this.messageProcessorThread.start();
		Loggers.MSG_LOGGER.info("Message processor thread started ["
				+ this.messageProcessorThread + "]");
	}

	/**
	 * 用于监控主消息处理线程异常退出的情况,如果发现线程退出,则尝试重新启动它
	 * 
	 * 
	 * 
	 */

	/**
	 * 达到5000上限时认为满了
	 */
	@Override
	public boolean isFull() {
		return this.queue.size() > 5000;
	}

	/**
	 * 取得消息处理器停止后的遗留的消息
	 * 
	 * @return the leftQueue
	 */
	public List<IMessage> getLeftQueue() {
		return leftQueue;
	}

	/**
	 * 重置
	 */
	public void resetLeftQueue() {
		this.leftQueue = null;
	}

	/**
	 * 用于监控主消息处理线程异常退出的情况,如果发现线程退出,则尝试重新启动它
	 * 
	 * 
	 * 
	 */
	@Deprecated
	protected class MessageProcessorThreadMonitor implements
			Thread.UncaughtExceptionHandler {
		private long lastCreateTime = 0;

		@Override
		public void uncaughtException(Thread t, Throwable e) {
			Loggers.MSG_LOGGER.error(
					"Uncaught exception in thread [" + t + "]", e);
			if (t == messageProcessorThread) {
				if (System.currentTimeMillis() - lastCreateTime < 1000 * 60) {
					Loggers.MSG_LOGGER
							.warn("The message process's last create time is ["
									+ lastCreateTime
									+ "],too frequently to restart the thread.");
					return;
				}
				Loggers.MSG_LOGGER
						.warn("The message process thread has broken,restart it.");
				// 主消息处理线程挂了,尝试重新启动一个
				System.gc();
				System.runFinalization();
				start();
				Loggers.MSG_LOGGER
						.warn("The message process thread has restarted.");
			} else {
				throw new RuntimeException(e);
			}
		}
	}

	public boolean isStop() {
		return stop;
	}
}
