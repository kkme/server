package com.hifun.soul.gameserver.currency.manager;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.currency.manager.IWallet;
import com.hifun.soul.gameserver.currency.manager.Wallet;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.startup.MinaGameClientSession;

public class WalletTest {

	private Human human ;
	private IWallet wallet;
	private int initCoin=1000;
	private int initCrystal = 100;
		
	@Before
	public void setUp() throws Exception {		
		MinaGameClientSession session = new MinaGameClientSession(null);
		human = new Human(new Player(session));
		human.setCoin(initCoin);
		human.setCrystal(initCrystal);
		wallet = new Wallet(human);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddMoney() {
		wallet.addMoney(CurrencyType.COIN, 100, true, MoneyLogReason.TEST, "");
		assertEquals(1100,human.getCoin());
		wallet.addMoney(CurrencyType.COIN, -10, true, MoneyLogReason.TEST, "");
		assertEquals(1100,human.getCoin());
		
		wallet.addMoney(CurrencyType.CRYSTAL,50, true, MoneyLogReason.TEST, "");
		assertEquals(150,human.getCrystal());
		wallet.addMoney(CurrencyType.CRYSTAL, -10, true, MoneyLogReason.TEST, "");
		assertEquals(150,human.getCrystal());		
	}
	
	@Test
	public void testCostMoney(){
		int expectedCoin = 900; 
		int expectedCrystal = 50;
		
		
		boolean costResult = wallet.costMoney(CurrencyType.COIN, 2000, MoneyLogReason.TEST, "");
		assertFalse(costResult);
		costResult = wallet.costMoney(CurrencyType.COIN, 100, MoneyLogReason.TEST, "");
		assertTrue(costResult);
		assertEquals(expectedCoin, human.getCoin());
		costResult = wallet.costMoney(CurrencyType.COIN, -10, MoneyLogReason.TEST, "");
		assertFalse(costResult);

		costResult = wallet.costMoney(CurrencyType.CRYSTAL, 200, MoneyLogReason.TEST, "");
		assertFalse(costResult);
		costResult = wallet.costMoney(CurrencyType.CRYSTAL, 50, MoneyLogReason.TEST, "");
		assertTrue(costResult);
		assertEquals(expectedCrystal, human.getCrystal());
		costResult = wallet.costMoney(CurrencyType.CRYSTAL, -10, MoneyLogReason.TEST, "");
		assertFalse(costResult);
	}

	@Test
	public void testIsEnough(){
		boolean isEnough = wallet.isEnough(CurrencyType.COIN, 900);
		assertTrue(isEnough);
		isEnough = wallet.isEnough(CurrencyType.COIN, 1000);
		assertTrue(isEnough);
		isEnough = wallet.isEnough(CurrencyType.COIN, 0);
		assertTrue(isEnough);
		isEnough = wallet.isEnough(CurrencyType.COIN, 1100);
		assertFalse(isEnough);
		isEnough = wallet.isEnough(CurrencyType.COIN, -10);
		assertFalse(isEnough);
		
		isEnough = wallet.isEnough(CurrencyType.CRYSTAL, 90);
		assertTrue(isEnough);
		isEnough = wallet.isEnough(CurrencyType.CRYSTAL, 0);
		assertTrue(isEnough);
		isEnough = wallet.isEnough(CurrencyType.CRYSTAL, 110);
		assertFalse(isEnough);
		isEnough = wallet.isEnough(CurrencyType.CRYSTAL, -10);
		assertFalse(isEnough);
	}

}
