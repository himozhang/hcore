package com.ideal.framework.shrio.redies;

import java.io.Serializable;
import java.util.Collection;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
 
/**
 * custom shiro sessionDAO
 * From：<a href="http://www.zyiqibook.com">在一起：技术分享_源码分享平台</a> 欢迎到网站进行交流
 */
public class CustomShiroSessionDAO extends AbstractSessionDAO {
 
    private ShiroSessionRepository shiroSessionRepository;
 
    @Override
    public void update(Session session) throws UnknownSessionException {
        System.out.println("update session----更新："+session.getId());
        getShiroSessionRepository().saveSession(session);
    }
 
    @Override
    public void delete(Session session) {
        if (session == null) {
            return;
        }
        Serializable id = session.getId();
        if (id != null) {
            System.out.println("delete session----:"+id);
            getShiroSessionRepository().deleteSession(id);
        }
        //TODO if session is too large,when session destory clear shiro cache
    }
 
    @Override
    public Collection<Session> getActiveSessions() {
        return getShiroSessionRepository().getAllSessions();
    }
 
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        getShiroSessionRepository().saveSession(session);
        System.out.println("do create session----建完后："+sessionId);
        return sessionId;
    }
 
    @Override
    protected Session doReadSession(Serializable sessionId) {
        System.out.println("do read session----参数："+sessionId);
        return getShiroSessionRepository().getSession(sessionId);
    }
 
    public ShiroSessionRepository getShiroSessionRepository() {
        return shiroSessionRepository;
    }
 
    public void setShiroSessionRepository(
            ShiroSessionRepository shiroSessionRepository) {
        this.shiroSessionRepository = shiroSessionRepository;
    }
 
}