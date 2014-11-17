package com.hifun.soul.robot;

import com.hifun.soul.core.context.ApplicationContext;
import com.hifun.soul.robot.task.impl.ForgeTask;
import com.hifun.soul.robot.task.impl.ForsterTask;
import com.hifun.soul.robot.task.impl.GiftTask;
import com.hifun.soul.robot.task.impl.MatchBattleTask;
import com.hifun.soul.robot.task.impl.PrepareTask;
import com.hifun.soul.robot.task.impl.RefineTask;

public class RobotApp {
	public static void main(String[] args) {
		ApplicationContext.initAndScan();
		if (args.length < 4) {
			System.out.println("Usage: java com.hifun.soul.robot.RobotApp <robotIdStart> <robotCount> <ip> <port>");
			System.exit(1);
		}
		int robotIdStart = Integer.valueOf(args[0]);
		int robotCount = Integer.valueOf(args[1]);
		String targetServerIp = args[2];
		int port =  Integer.valueOf(args[3]);
		// 完整测试
		completeTest(
			robotIdStart, robotCount, targetServerIp, port);
		try {
			System.out.println("press any key to continue ...");
			System.in.read();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		System.exit(0);
	}

	/**
	 * 功能测试
	 * 
	 * @param robotIdStart
	 * @param robotCount
	 * @param targetServerIp
	 * @param port
	 * 
	 */
	private static void completeTest(
		int robotIdStart, int robotCount, String targetServerIp, int port) {

		for (int i = robotIdStart; i < robotIdStart + robotCount; i++) {
			Robot robot = createRobot(i, targetServerIp, port);
			// 前期准备任务
			robot.addTask(new PrepareTask(robot,1000));
//			// 建筑任务
//			robot.addTask(new BuildingTask(robot, 1000, 1000));
//			// 聊天任务
//			robot.addTask(new ChatTask(robot, 1000, 1000));
//			// 魔晶兑换任务
//			robot.addTask(new CrystalExchangeTask(robot, 1000, 1000));
//			// 抽奖任务
//			robot.addTask(new LotteryTask(robot, 1000, 1000));
//			// 好友任务
//			robot.addTask(new FriendTask(robot, 120000, 10000));
//			// 星运任务
//			robot.addTask(new HoroscopeTask(robot, 1000, 1000));
//			// 登陆奖励任务
//			robot.addTask(new LoginRewardTask(robot, 10000, 1000));
//			// 商城任务
//			robot.addTask(new MallTask(robot, 10000, 1000));
//			// 在线奖励任务
//			robot.addTask(new OnlineRewardTask(robot, 10000, 1000));
//			// 商店任务
//			robot.addTask(new ShopTask(robot, 10000, 1000));
//			// 科技任务
//			robot.addTask(new TechnologyTask(robot, 10000, 1000));
//			// 装备制作任务
//			robot.addTask(new EquipMakeTask(robot, 10000, 1000));
//			// 装备强化任务
//			robot.addTask(new EquipUpgradeTask(robot, 10000, 1000));
//			// 宝石镶嵌任务
//			robot.addTask(new GemEmbedTask(robot, 10000, 1000));
//			// 关卡任务
//			robot.addTask(new StageTask(robot, 10000, 1000));
//			// 消费提醒设置
//			robot.addTask(new CostNotifyTask(robot, 10000, 60000));
//			// 竞技场
//			robot.addTask(new ArenaTask(robot, 1000, 6000000));
//			// boss战
//			robot.addTask(new BossWarTask(robot, 1000, 6000000));
//			// gm问答
//			robot.addTask(new GmQuestionTask(robot, 1000, 6000000));
			// 洗练
			robot.addTask(new ForgeTask(robot, 60000, 120000));
			// 试炼
			robot.addTask(new RefineTask(robot, 70000, 120000));
			// 培养
			robot.addTask(new ForsterTask(robot, 80000, 120000));
			// 天赋
			robot.addTask(new GiftTask(robot, 90000, 120000));
			// 阵营战
			robot.addTask(new MatchBattleTask(robot, 100000, 30000));
			
			robot.start();
		}
	}

	/**
	 * 创建机器人
	 * 
	 * @param robotId
	 * @param targetServerIp
	 * @param port
	 * @return
	 */
	private static Robot createRobot(
		int robotId, String targetServerIp, int port) {
		return new Robot("bot", robotId, targetServerIp, port);
	}
}
