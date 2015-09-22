package com.ideal.framework.ldap.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LDAPPoolManager {
	
	Logger logger = LoggerFactory.getLogger(LDAPPoolManager.class);
	
	private Map<String, String> environment = null;
	private String baseDn;
	private String contextUrl;
	private String contextUserName;
	private String contextPassword;
	private static LDAPPoolManager instance;
	private Hashtable<String, DirContextPool> pools = new Hashtable<String, LDAPPoolManager.DirContextPool>();
	private boolean isADEnv = false;
	private String poolName = "idealpool";

	public static synchronized LDAPPoolManager getInstance() {
		if (instance == null) {
			instance = new LDAPPoolManager();
		}
		return instance;
	}

	private LDAPPoolManager() {
		init();
	}

	private void init() {
		ResourceBundle initResoure = ResourceBundle.getBundle("app");
		String[] ldapconfigs = initResoure.getString("ldapconfigs") == null ? new String[] {} : initResoure.getString(
				"ldapconfigs").split(",");
		if (ldapconfigs.length>0) {
			for (String config : ldapconfigs) {
				logger.info("Now config the " + config + " file!");
				ResourceBundle resourceBundle = ResourceBundle.getBundle(config);
				environment = new HashMap<String, String>();
				environment.put("contextUrl", resourceBundle.getString("ldap.ldaphost"));
				environment.put("contextUserName", resourceBundle.getString("ldap.rootdn"));
				environment.put("contextPassword", resourceBundle.getString("ldap.password"));
				environment.put("maxInit", resourceBundle.getString("ldap.maxinit"));
				environment.put("maxConn", resourceBundle.getString("ldap.maxconn"));
				this.baseDn = resourceBundle.getString("ldap.basedn");
				if (resourceBundle.getString("ldap.type.isad") != null
						&& resourceBundle.getString("ldap.type.isad").length() > 0) {
					isADEnv = Boolean.valueOf(resourceBundle.getString("ldap.type.isad"));
					if (isADEnv) {
						String trustStore = resourceBundle.getString("ldap.ad.trustStore");
						String trustStorepassword = resourceBundle.getString("ldap.ad.trustStorepassword");
						if (trustStore.length() <= 0 || trustStorepassword.length() <= 0) {
							logger.info("The server's type is active directory,and should config the"
									+ "trustStore and trustStorepassword value in ldap.properties",
									new RuntimeException("ldap config error!"));
						} else {
							environment.put("trustStore", trustStore);
							environment.put("trustStorepassword", trustStorepassword);
						}
					}
				}
				DirContextPool pool = new DirContextPool(environment);
				pools.put(config, pool);
			}
		} else {
			logger.info("Do not config the ldapconfigs in the properties file named app. Now Confing the default pool.");
			ResourceBundle resourceBundle = ResourceBundle.getBundle("ldap");
			if (resourceBundle != null) {
				environment = new HashMap<String, String>();
				environment.put("contextUrl", resourceBundle.getString("ldap.ldaphost"));
				environment.put("contextUserName", resourceBundle.getString("ldap.rootdn"));
				environment.put("contextPassword", resourceBundle.getString("ldap.password"));
				environment.put("maxInit", resourceBundle.getString("ldap.maxinit"));
				environment.put("maxConn", resourceBundle.getString("ldap.maxconn"));
				environment.put("isADEnv", resourceBundle.getString("ldap.type.isad"));
				this.baseDn = resourceBundle.getString("ldap.basedn");
				if (resourceBundle.getString("ldap.type.isad") != null
						&& resourceBundle.getString("ldap.type.isad").length() > 0) {
					isADEnv = Boolean.valueOf(resourceBundle.getString("ldap.type.isad"));
					if (isADEnv) {
						String trustStore = resourceBundle.getString("ldap.ad.trustStore");
						String trustStorepassword = resourceBundle.getString("ldap.ad.trustStorepassword");
						if (trustStore.length() <= 0 || trustStorepassword.length() <= 0) {
							logger.info("The server's type is active directory,and should config the"
									+ "trustStore and trustStorepassword value in ldap.properties",
									new RuntimeException("ldap config error!"));
						} else {
							environment.put("trustStore", trustStore);
							environment.put("trustStorepassword", trustStorepassword);
						}
					}
				}
				DirContextPool pool = new DirContextPool(environment);
				pools.put(poolName, pool);
			} else {
				logger.info("Do not found the ldap.properties config file!", new RuntimeException(
						"Do not have and ldap config files in this application"));
			}
		}
	}

	public DirContext getDirContext() {
		DirContext ctx = null;
		DirContextPool pool = this.pools.get(poolName);
		try {
			ctx = pool.getConnection();
		} catch (Exception e) {
			logger.info("Error in getDirContext!", e);
		}
		return ctx;
	}

	public DirContext getDirContext(String poolName) {
		DirContext ctx = null;
		DirContextPool pool = this.pools.get(poolName);
		try {
			ctx = pool.getConnection();
		} catch (Exception e) {
			logger.info("Error in getDirContext!", e);
		}
		return ctx;
	}

	public synchronized void freeDirContext(DirContext ctx) {
		DirContextPool pool = this.pools.get(poolName);
		if (pool != null) {
			pool.freeConnection(ctx);
		}
	}

	public synchronized void freeDirContext(DirContext ctx, String poolName) {
		DirContextPool pool = this.pools.get(poolName);
		if (pool != null) {
			pool.freeConnection(ctx);
		}
	}

	public synchronized void releaseAll() {
		Enumeration<DirContextPool> allpools = pools.elements();
		while (allpools.hasMoreElements()) {
			DirContextPool pool = allpools.nextElement();
			if (pool != null) {
				try {
					pool.release();
				} catch (NamingException e) {
					logger.info("Error in releaseAll!", e);
				}
			}
		}
		pools.clear();
	}

	public String getContextUrl() {
		return contextUrl;
	}

	public void setContextUrl(String contextUrl) {
		this.contextUrl = contextUrl;
	}

	public String getContextUserName() {
		return contextUserName;
	}

	public void setContextUserName(String contextUserName) {
		this.contextUserName = contextUserName;
	}

	public String getContextPassword() {
		return contextPassword;
	}

	public void setContextPassword(String contextPassword) {
		this.contextPassword = contextPassword;
	}

	public Map<String, String> getEnvironment() {
		return environment;
	}

	public void setEnvironment(Map<String, String> environment) {
		this.environment = environment;
	}

	public String getBaseDn() {
		return baseDn;
	}

	public void setBaseDn(String baseDn) {
		this.baseDn = baseDn;
	}

	public boolean isADEnv() {
		return isADEnv;
	}

	protected class DirContextPool {
		int maxInit = 10; // 初始化个数
		int minConn = 1; // 最少存放个数
		int maxConn = 200; // 最大存放个数
		int inUsed = 0; // 在用个数
		int freeUsed = 0; // 空闲个数
		private boolean ifInit = false;
		private final LinkedList<Object> waitQueue = new LinkedList<Object>();
		private Map<String, String> environment;
		private String CONTEXT_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";
		private String CONTEXT_SECURITY_AUTHENTICATION = "simple";
		private String HOST;
		private String LOGON_NAME;
		private String PASSWORD;
		private String TRUSTSTORE;
		private String TRUSTSTOREPASSWORD;
		private Vector<DirContext> connections = null;

		DirContextPool(Map<String, String> env) {
			this.environment = env;
		}

		// 创建一个连接
		private DirContext createDirContext() {
			DirContext ctx = null;
			boolean success = false;
			if (this.getEnvironment() != null) {
				if (environment.containsKey("contextUrl") && environment.containsKey("contextUserName")
						&& environment.containsKey("contextPassword")) {
					HOST = environment.get("contextUrl").toString();
					LOGON_NAME = environment.get("contextUserName").toString();
					PASSWORD = environment.get("contextPassword").toString();
					if(isADEnv){
						TRUSTSTORE = environment.get("trustStore").toString();
						TRUSTSTOREPASSWORD = environment.get("trustStorepassword").toString();
					}
					if (environment.get("maxInit") != null) {
						this.maxInit = Integer.parseInt(environment.get("maxInit").toString());
					}
					if (environment.get("maxConn") != null) {
						this.maxConn = Integer.parseInt(environment.get("maxConn").toString());
					}
					success = true;
				}
			} else {
				logger.info("Environment parameter should be intact! ");
				logger.info("Please check your the parameter "
						+ "[contextUrl,contextUserName,contextPassword] is configed!");
			}
			if (success) {
				Properties env = new Properties();
				env.put(Context.INITIAL_CONTEXT_FACTORY, CONTEXT_FACTORY);
				env.put(Context.SECURITY_AUTHENTICATION, CONTEXT_SECURITY_AUTHENTICATION);
				env.put(Context.PROVIDER_URL, HOST);
				env.put(Context.SECURITY_PRINCIPAL, LOGON_NAME);
				env.put(Context.SECURITY_CREDENTIALS, PASSWORD);
				if (isADEnv) {
					env.put(Context.SECURITY_PROTOCOL, "ssl");
					System.setProperty("javax.net.ssl.trustStore", TRUSTSTORE);
					System.setProperty("javax.net.ssl.trustStorePassword", TRUSTSTOREPASSWORD);
				}
				try {
					ctx = new InitialDirContext(env);
				} catch (NamingException e) {
				}
			}
			return ctx;
		}

		private void createLdapPool() {
			boolean success = false;
			logger.info("Start to create LdapConnectPool");
			if (this.getEnvironment() != null) {
				if (environment.containsKey("contextUrl") && environment.containsKey("contextUserName")
						&& environment.containsKey("contextPassword")) {
					HOST = environment.get("contextUrl").toString();
					LOGON_NAME = environment.get("contextUserName").toString();
					PASSWORD = environment.get("contextPassword").toString();
					if(isADEnv){
						TRUSTSTORE = environment.get("trustStore").toString();
						TRUSTSTOREPASSWORD = environment.get("trustStorepassword").toString();
					}
					if (environment.get("maxInit") != null) {
						this.maxInit = Integer.parseInt(environment.get("maxInit").toString());
					}
					if (environment.get("maxConn") != null) {
						this.maxConn = Integer.parseInt(environment.get("maxConn").toString());
					}
					success = true;
				}
			} else {
				logger.info("Environment parameter should be intact! ");
				logger.info("Please check your the parameter "
						+ "[contextUrl,contextUserName,contextPassword] is configed!");
			}
			if (success) {
				Properties env = new Properties();
				env.put(Context.INITIAL_CONTEXT_FACTORY, CONTEXT_FACTORY);
				env.put(Context.SECURITY_AUTHENTICATION, CONTEXT_SECURITY_AUTHENTICATION);
				env.put(Context.PROVIDER_URL, HOST);
				env.put(Context.SECURITY_PRINCIPAL, LOGON_NAME);
				env.put(Context.SECURITY_CREDENTIALS, PASSWORD);
				if (isADEnv) {
					env.put(Context.SECURITY_PROTOCOL, "ssl");
					System.setProperty("javax.net.ssl.trustStore", TRUSTSTORE);
					System.setProperty("javax.net.ssl.trustStorePassword", TRUSTSTOREPASSWORD);
				}
				try {
					DirContext ctx = null;
					connections = new Vector<DirContext>();
					if (this.maxInit > this.maxConn) {
						this.maxInit = this.maxConn;
					}
					if (this.maxInit <= 0) {
						this.maxInit = this.minConn;
					}

					for (int i = 0; i < this.getMaxInit(); i++) {
						ctx = new InitialDirContext(env);
						connections.add(ctx);
						this.freeUsed++;
					}
					ifInit = true;
					logger.info("Create LdapPool Success! Total connections is " + this.freeUsed);
				} catch (Exception e) {
					logger.info("Ldap connects error!", e);
				}
			}
		}

		// 得到一个连接
		public synchronized DirContext getConnection() {
			DirContext ctx = null;
			if (!ifInit)
				createLdapPool();
			while (true) {
				if (this.connections != null) {
					ctx = connections.remove(0);
					this.inUsed++;
					this.freeUsed--;
					logger.info("Free connections is " + this.freeUsed + " and used connections is " + this.inUsed);
					break;
				} else {
					++inUsed;
					if (inUsed > this.maxConn) {
						Object monitor = new Object();
						logger.info("Connection is already to max size! Thread " + Thread.currentThread().getName()
								+ " now wait a connection!");
						synchronized (monitor) {
							synchronized (waitQueue) {
								waitQueue.add(monitor);
								try {
									waitQueue.wait();
								} catch (InterruptedException e) {
								}
							}
						}
						logger.info("Thread " + Thread.currentThread().getName() + " wakeup!");
					} else {
						ctx = this.createDirContext();
					}
				}
			}
			return ctx;
		}

		// 断开所有连接，释放占用的系统资源
		public synchronized void release() throws NamingException {
			if (connections != null) {
				Enumeration<DirContext> eu = connections.elements();
				while (eu.hasMoreElements()) {
					DirContext ctx = eu.nextElement();
					ctx.close();
				}
				connections.clear();
			}
		}

		// 释放连接
		public synchronized void freeConnection(DirContext usedctx) {
			DirContext ctx = usedctx;
			connections.addElement(ctx);
			inUsed--;
			freeUsed++;
			notifyThread();
		}

		private void notifyThread() {
			Object waitMonitor = null;
			synchronized (waitQueue) {
				if (waitQueue.size() > 0) {
					waitMonitor = waitQueue.removeFirst();
				}
			}
			if (waitMonitor != null) {
				synchronized (waitMonitor) {
					waitMonitor.notify();
				}
			}
		}

		public int getMaxInit() {
			return maxInit;
		}

		public void setMaxInit(int maxInit) {
			this.maxInit = maxInit;
		}

		public int getMinConn() {
			return minConn;
		}

		public void setMinConn(int minConn) {
			this.minConn = minConn;
		}

		public Map<String, String> getEnvironment() {
			return environment;
		}

		public void setEnvironment(Map<String, String> environment) {
			this.environment = environment;
		}

		public int getInUsed() {
			return inUsed;
		}

		public int getFreeUsed() {
			return freeUsed;
		}
	}

}
