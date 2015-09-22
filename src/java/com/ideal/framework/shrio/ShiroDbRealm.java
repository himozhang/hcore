package com.ideal.framework.shrio;

import java.util.HashSet;
import java.util.Set;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

 
import com.ideal.framework.constants.GlobalConstants;
import com.ideal.framework.utils.string.EmptyUtil;

public class ShiroDbRealm extends AuthorizingRealm {
	
	private static Logger logger = LoggerFactory.getLogger(ShiroDbRealm.class);
	private static final String ALGORITHM = "MD5";
	
	@Autowired
	private IRoleService userService;

	public ShiroDbRealm() {
		super();
	}
	
	/**
	 * 认证回调函数,登录时调用
	 * 首先根据传入的用户名获取User信息；然后如果user为空，那么抛出没找到帐号异常UnknownAccountException；
	 * 如果user找到但锁定了抛出锁定异常LockedAccountException；最后生成AuthenticationInfo信息，
	 * 交给间接父类AuthenticatingRealm使用CredentialsMatcher进行判断密码是否匹配，
	 * 如果不匹配将抛出密码错误异常IncorrectCredentialsException；
	 * 另外如果密码重试此处太多将抛出超出重试次数异常ExcessiveAttemptsException；
	 * 在组装SimpleAuthenticationInfo信息时， 需要传入：身份信息（用户名）、凭据（密文密码）、盐（username+salt），
	 * CredentialsMatcher使用盐加密传入的明文密码和此处的密文密码进行匹配。
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		System.out.println(token.getUsername());
		Role user = userService.findUserByLoginName(token.getUsername());
		System.out.println(user);
		if (!EmptyUtil.isEmpty(user)) {
			if (GlobalConstants.YES.equals(user.getLocked())) {
				throw new LockedAccountException(); // 帐号锁定
			}
			// 从数据库查询出来的账号名和密码,与用户输入的账号和密码对比
			// 当用户执行登录时,在方法处理上要实现user.login(token);
			// 然后会自动进入这个类进行认证
			// 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
			System.out.println("getName() : "+getName());
			SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getLoginName(), user.getPassword(), getName());
			
			// 当验证都通过后，把用户信息放在session里
			Session session = SecurityUtils.getSubject().getSession();
			session.setAttribute("userSession", user);
			session.setAttribute("userSessionId",user.getId());
			return authenticationInfo;
		}else{
			throw new AuthenticationException(); 
		}
	}

	/**
	 * 只有需要验证权限时才会调用, 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.在配有缓存的情况下，只加载一次.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		/* 这里编写授权代码 */
		System.out.println("=---------------获取用户权限===================");
		String userId = (String) principals.fromRealm(getName()).iterator().next();
        System.out.println("当前登录用户principal:'"+userId);
        Role user = userService.findUserByUid(userId);
        
        if( user != null ) {
        	System.out.println("==============开始获取用户权限===================");
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            for( Role role : user.getRoles() ) {
            	System.out.println(user.getUsername()+"角色："+role.getName());
                info.addRole(role.getName());
                System.out.println(user.getUsername()+"权限："+role.getPermissions());
                Set<String> stringPermissions=new HashSet<String>();
                for (Iterator<Permission> iterator = role.getPermissions().iterator(); iterator
						.hasNext();) {
					String stringPermission =  iterator.next().getRealm();
					stringPermissions.add(stringPermission);
					System.out.println(user.getUsername()+"权限："+stringPermission);
				}
                info.addStringPermissions( stringPermissions );
            }
            System.out.println("=============结束获取用户权=========================");
            return info;
            
        } else {
            return null;
        }
	}

	/**
     * 更新用户授权信息缓存.
     */
	public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
	}
	/**
     * 更新用户信息缓存.
     */
	public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
		super.clearCachedAuthenticationInfo(principals);
	}

	/**
	 * 清除用户授权信息缓存.
	 */
	public void clearAllCachedAuthorizationInfo() {
		getAuthorizationCache().clear();
	}

	/**
	 * 清除用户信息缓存.
	 */
	public void clearAllCachedAuthenticationInfo() {
		getAuthenticationCache().clear();
	}
	
	/**
	 * 清空所有缓存
	 */
	public void clearCache(PrincipalCollection principals) {
		super.clearCache(principals);
	}


	/**
	 * 清空所有认证缓存
	 */
	public void clearAllCache() {
		clearAllCachedAuthenticationInfo();
		clearAllCachedAuthorizationInfo();
	}
}
