package com.ideal.framework.shrio.redies;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.shiro.cache.CacheException;
import org.apache.shiro.session.Session;
import org.apache.shiro.util.CollectionUtils;
 
/**
 * redis save shiro session class 
 * From：<a href="http://www.zyiqibook.com">在一起：技术分享_源码分享平台</a> 欢迎到网站进行交流
 */
public class JedisShiroSessionRepository implements ShiroSessionRepository {
 
    private static final String REDIS_SHIRO_SESSION = "shiro_session:";
    // private static final int SESSION_VAL_TIME_SPAN = 1800;
    private static final int DB_INDEX = 0;
 
    private JedisManager jedisManager;
 
    @Override
    public void saveSession(Session session) {
        if (session == null || session.getId() == null)
            throw new NullPointerException("session is empty");
        try {
            byte[] key = SerializeUtil.serialize(buildRedisSessionKey(session
                    .getId()));
            byte[] value = SerializeUtil.serialize(session);
            long sessionTimeOut = session.getTimeout() / 1000;
            Long expireTime = sessionTimeOut + (5 * 60);// +
                                                        // SESSION_VAL_TIME_SPAN
                                                        // + (5 * 60);
            getJedisManager().saveValueByKey(DB_INDEX, key, value,
                    expireTime.intValue());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("save session error");
        }
    }
 
    @Override
    public void deleteSession(Serializable id) {
        if (id == null) {
            throw new NullPointerException("session id is empty");
        }
        try {
            getJedisManager().deleteByKey(DB_INDEX,
                    SerializeUtil.serialize(buildRedisSessionKey(id)));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("delete session error");
        }
    }
 
    @Override
    public Session getSession(Serializable id) {
        if (id == null)
            throw new NullPointerException("session id is empty");
        Session session = null;
        try {
            byte[] value = getJedisManager().getValueByKey(DB_INDEX,
                    SerializeUtil.serialize(buildRedisSessionKey(id)));
            session = SerializeUtil.deserialize(value, Session.class);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("get session error");
        }
        return session;
    }
 
    @Override
    public Collection<Session> getAllSessions() {
        System.out.println("get all sessions");
        try {
            Set<byte[]> keys = jedisManager.keys("*session*");
            if (!CollectionUtils.isEmpty(keys)) {
                List<Session> values = new ArrayList<Session>(keys.size());
                for (byte[] key : keys) {
                    Session value = SerializeUtil.deserialize(getJedisManager()
                            .getValueByKey(DB_INDEX, key), Session.class);
                    System.out.println("get all sessions---活动session---"
                            + value.getId());
                    if (value != null) {
                        values.add(value);
                    }
                }
                return Collections.unmodifiableList(values);
            } else {
                return Collections.emptyList();
            }
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }
 
    private String buildRedisSessionKey(Serializable sessionId) {
        return REDIS_SHIRO_SESSION + sessionId;
    }
 
    public JedisManager getJedisManager() {
        return jedisManager;
    }
 
    public void setJedisManager(JedisManager jedisManager) {
        this.jedisManager = jedisManager;
    }
}