package com.hifun.soul.gamedb;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.hifun.soul.core.orm.DataAccessException;
import com.hifun.soul.core.orm.IDBService;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.core.orm.SoftDeleteEntity;
import com.hifun.soul.gamedb.agent.ICacheableAgent;
import com.hifun.soul.gamedb.agent.IDBAgent;
import com.hifun.soul.gamedb.agent.IEntityAgent;
import com.hifun.soul.gamedb.agent.XQLAgent;
import com.hifun.soul.gamedb.monitor.DBOperationType;
import com.hifun.soul.gamedb.msg.DBDeleteMessage;
import com.hifun.soul.gamedb.msg.DBGetMessage;
import com.hifun.soul.gamedb.msg.DBInsertMessage;
import com.hifun.soul.gamedb.msg.DBQueryMessage;
import com.hifun.soul.gamedb.msg.DBUpdateMessage;

public class DBUtil {

	public static IDBService getDBService() {
		return new IDBService() {

			@Override
			public <T extends IEntity> T get(Class<T> entityClass,
					Serializable id) throws DataAccessException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Serializable insert(IEntity entity) throws DataAccessException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void update(IEntity entity) throws DataAccessException {
				// TODO Auto-generated method stub

			}

			@Override
			public void delete(IEntity entity) throws DataAccessException {
				// TODO Auto-generated method stub

			}

			@Override
			public void deleteById(Class<? extends IEntity> entityClass,
					Serializable id) {
				// TODO Auto-generated method stub

			}

			@Override
			public void softDelete(SoftDeleteEntity entity)
					throws DataAccessException {
				// TODO Auto-generated method stub

			}

			@Override
			public void softDeleteById(
					Class<? extends SoftDeleteEntity> entityClass,
					Serializable id) throws DataAccessException {
				// TODO Auto-generated method stub

			}

			@Override
			public List<?> findByNamedQuery(String queryName)
					throws DataAccessException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public List<?> findByNamedQueryAndNamedParam(String queryName,
					String[] paramNames, Object[] values)
					throws DataAccessException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int queryForUpdate(String queryName, String[] paramNames,
					Object[] values) throws DataAccessException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public List<?> findByNamedQueryAndNamedParam(String queryName,
					String[] paramNames, Object[] values, int maxResult,
					int start) throws DataAccessException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void saveOrUpdate(IEntity entity) throws DataAccessException {
				// TODO Auto-generated method stub

			}

			@Override
			public void check() {
				// TODO Auto-generated method stub

			}

			@Override
			public void checkDbVersion(String version) {
				// TODO Auto-generated method stub
				
			}

		};
	}

	public static IDBAgent getDBAgent() {
		return new IDBAgent() {

			@Override
			public <E extends IEntity> Serializable insert(
					DBInsertMessage<E> msg) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public <E extends IEntity> void delete(DBDeleteMessage<E> msg) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public <E extends IEntity> void update(DBUpdateMessage<E> msg) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public <E extends IEntity> E get(DBGetMessage<E> msg) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public List<?> query(DBQueryMessage msg) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void registerXQLAgent(XQLAgent queryAgent) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void registerEntityAgent(
					IEntityAgent<? extends IEntity> entityAgent) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void registerCacheableAgent(ICacheableAgent<?, ?> agent) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public List<ICacheableAgent<?, ?>> getModifiedEntityAgent() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Map<Class<?>, Map<DBOperationType, Integer>> getEntityDBOperationInfos() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Map<String, Integer> getDbQueryTimesInfo() {
				// TODO Auto-generated method stub
				return null;
			}

			
		
			
		};
	}

}
