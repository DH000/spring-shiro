package com.lin.shiro.redis;

import java.util.Collection;
import java.util.List;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/*.xml"})
public class RedisSessionDaoTest {
	@Autowired
	private RedisSessionDao redisSessionDao;
	@Autowired
	private RedisTemplate<String, Session> redisTemplate;
	
	@Test
	public void createTest(){
		for(int i=0; i<100; i++){
			redisSessionDao.create(new SimpleSession());
		}
	}
	
	@Test
	public void updateTest(){
		SimpleSession session = new SimpleSession();
		session.setId("54fdd566-781e-47e1-9394-bc16711697eb");
		redisSessionDao.update(session);
	}
	
	@Test
	public void readSessionTest(){
		Session session = redisSessionDao.readSession("54fdd566-781e-47e1-9394-bc16711697eb");
		System.out.println(JSON.toJSONString(session));
	}
	
	@Test
	public void deleteTest(){
		SimpleSession session = new SimpleSession();
		session.setId("d2df1a55-8f30-4611-8982-ae6587cdd295");
		redisSessionDao.delete(session);
	}
	
	@Test
	public void getAllTest(){
		Collection<Session> col = redisSessionDao.getActiveSessions();
		System.out.println(JSON.toJSONString(col));
	}
	
	@Test
	public void deleteAll(){
		redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				List list = (List) connection.execute("keys", "*".getBytes());
				for(Object obj : list){
					connection.del(obj.toString().getBytes());
				}
				return null;
			}
		});
	}
	
	@Test
	public void allTest(){
		Collection<Session> list = redisSessionDao.getActiveSessions();
		for(Session session : list){
			System.out.println(session.getId());
		}
	}
	
	@Test
	public void pageTest(){
		List<Session> list = redisTemplate.opsForList().range(null, 0, 10);
		System.out.println(list.size());
	}
	
}



