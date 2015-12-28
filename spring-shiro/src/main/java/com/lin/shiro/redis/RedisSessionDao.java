package com.lin.shiro.redis;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 
 * desc: redis缓存session
 * 
 * @author xuelin
 * @param <K>
 * @date Dec 27, 2015
 */
public class RedisSessionDao extends AbstractSessionDAO {
	private final static String DEFAULT_PREFIX = "shiro_session:";
	private String keyPrefix = DEFAULT_PREFIX;
	private RedisTemplate<String, Session> redisTemplate;
	
	public void setRedisTemplate(RedisTemplate<String, Session> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	public String getKeyPrefix() {
		return keyPrefix;
	}

	public void setKeyPrefix(String keyPrefix) {
		this.keyPrefix = keyPrefix;
	}
	public RedisSessionDao() {
		super();
	}
	
	@Override
	public void update(Session session) throws UnknownSessionException {
		storeSession(session.getId(), session);
	}
	
	@Override
	public void delete(Session session) {
		if (session == null) {
			throw new NullPointerException("session argument cannot be null.");
		}
		Serializable id = session.getId();
		if (id != null) {
			redisTemplate.delete(generateKey(id));
		}
	}
	
	protected String generateKey(Serializable id){
		return getKeyPrefix() + id.toString();
	}
	
	protected String generateAllKey(){
		return getKeyPrefix() + "*";
	}
	
	/**
	 * 
	 * desc: 最好不使用该方法 redis会自动清理
	 * 		 上层shiro代码会对session进行validate操作
	 * 		 高并发情况下容易造成内存溢出 而且速度慢
	 * 		 或许可以分页方式进行查询管理	
	 * @return
	 */
	@Override
	public Collection<Session> getActiveSessions() {
		return Collections.emptyList();
//		Set<String> keys = redisTemplate.keys(generateAllKey());
//		Set<Session> sessions = new HashSet<Session>(keys.size());
//		for(String key : keys){
//			Session session = redisTemplate.opsForValue().get(key);
//			sessions.add(session);
//		}
//		
//		return sessions;
	}
	
	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = generateSessionId(session);
		assignSessionId(session, sessionId);
		storeSession(sessionId, session);
		return sessionId;
	}
	
	protected Session storeSession(Serializable id, Session session) {
		if (id == null) {
			throw new NullPointerException("id argument cannot be null.");
		}
		
		long timeout = session.getTimeout();
		redisTemplate.opsForValue().set(generateKey(id), session, timeout, TimeUnit.MILLISECONDS);
		return session;
	}
	
	@Override
	protected Session doReadSession(Serializable sessionId) {
		if (sessionId == null) {
			throw new NullPointerException("id argument cannot be null.");
		}
		return redisTemplate.opsForValue().get(generateKey(sessionId));
	}
	
}
