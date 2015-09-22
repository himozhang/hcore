package com.ideal.framework.cache.redis;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import com.ideal.core.user.entity.User;

/** 
 * @Description:
 * @CreateTime 2015-9-12 下午06:46:40
 * @author:himo
 * @mail:zhangyao0905@gmail.com
 */
public class UserDAO {

	/** 日志工具类 */
	public  Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
    protected RedisTemplate<Serializable, Serializable> redisTemplate;

    public void saveUser(final User user) {
        redisTemplate.execute(new RedisCallback<Object>() {

            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.set(redisTemplate.getStringSerializer().serialize("user.uid." + user.getId()),
                               redisTemplate.getStringSerializer().serialize(user.getLoginName()));
                logger.debug("user save success....");
                return null;
            }
        });
    }

    public User getUser(final String id) {
        return redisTemplate.execute(new RedisCallback<User>() {
            @Override
            public User doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] key = redisTemplate.getStringSerializer().serialize("user.uid." + id);
                if (connection.exists(key)) {
                    byte[] value = connection.get(key);
                    String name = redisTemplate.getStringSerializer().deserialize(value);
                    User user = new User();
                    user.setLoginName(name);
                    user.setId(id);
                    return user;
                }
                return null;
            }
        });
    }
    
}
