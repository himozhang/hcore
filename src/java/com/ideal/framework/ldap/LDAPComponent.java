package com.ideal.framework.ldap;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ideal.framework.exception.LDAPOperationException;
import com.ideal.framework.ldap.util.AttributeMapping;
import com.ideal.framework.ldap.util.LDAPPoolManager;
import com.ideal.framework.ldap.util.ObjectClass;
import com.ideal.framework.ldap.util.StaticObjectClassValue;
import com.ideal.framework.ldap.util.ObjectClass.ObjectType;


@SuppressWarnings("unchecked")
public class LDAPComponent<T extends Serializable>  implements ILDAPComponent<T> {
	
	Logger logger = LoggerFactory.getLogger(LDAPComponent.class);
	
	private String ldapName = "idealpool";
	private LDAPPoolManager manager = null;
	private boolean isADEnv = false;
	private String contextSearchBase;
	private DirContext ctx;
	private Class<T> modelClass;
	private static String userPassword = "userPassword";
	private String baseND;
	

	public LDAPComponent(T t) {
		this.modelClass = (Class<T>) t.getClass();
	}

	public LDAPComponent(Class<T> t) {
		this.modelClass = t;
	}

	public String getLdapName() {
		return ldapName;
	}

	public void setLdapName(String ldapName) {
		this.ldapName = ldapName;
	}
	
	public String getBaseND() {
		return baseND;
	}

	public void setBaseND(String baseND) {
		this.baseND = baseND;
	}

	public LDAPComponent(String fullClassName) {
		try {
			this.modelClass = (Class<T>) Class.forName(fullClassName);
		} catch (ClassNotFoundException e) {
			String logInfo = "Constract function fullClassName can't find the class!";
			logger.info(logInfo, e);
		}
	}

	private void build() {
		if (this.manager != null) {
			ctx = manager.getDirContext(ldapName);
			isADEnv = manager.isADEnv();
		} else {
			logger.info("No pool is avaliable,try to create a single DirContext!");
			ResourceBundle resourceBundle = ResourceBundle.getBundle("ldap");
			Properties env = new Properties();
			env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			env.put(Context.SECURITY_AUTHENTICATION, "simple");
			env.put(Context.PROVIDER_URL, resourceBundle.getString("ldap.ldaphost"));
			env.put(Context.SECURITY_PRINCIPAL, resourceBundle.getString("ldap.rootdn"));
			env.put(Context.SECURITY_CREDENTIALS, resourceBundle.getString("ldap.password"));
			baseND = resourceBundle.getString("ldap.basedn");
			if (resourceBundle.getString("ldap.type.isad") != null
					&& resourceBundle.getString("ldap.type.isad").length() > 0) {
				isADEnv = Boolean.valueOf(resourceBundle.getString("ldap.type.isad"));
				if (isADEnv) {
					String trustStore = resourceBundle.getString("ldap.ad.trustStore");
					String trustStorepassword = resourceBundle.getString("ldap.ad.trustStorepassword");
					if (trustStore.length() <= 0 || trustStorepassword.length() <= 0) {
						logger.info("The server's type is active directory,and should config the"
								+ "trustStore and trustStorepassword value in ldap.properties",
								new LDAPOperationException("ldap config error!"));
					} else {
						System.setProperty("javax.net.ssl.trustStore", trustStore);
						System.setProperty("javax.net.ssl.trustStorePassword", trustStorepassword);
						env.put(Context.SECURITY_PROTOCOL, "ssl");
					}
				}
			}
			try {
				ctx = new InitialDirContext(env);
				logger.info("Now create a single DirContext! DirContext is " + ctx.toString());
			} catch (NamingException e) {
				logger.info("Build a single DirContext error!");
			}
		}
	}

	private void destroy() {
		logger.info("Try to close the Connect");
		if (this.manager != null) {
			this.manager.freeDirContext(ctx, ldapName);
			logger.info("Free a connect to Connect pool!");
		} else {
			try {
				ctx.close();
				logger.info("DisConnect");
			} catch (NamingException e) {
				logger.info("NamingException in closing a single Connect");
			}
		}
	}

	public T queryObject(String key) throws LDAPOperationException {
		String configRootDN = "";
		T obj = null;
		String[] returnAttrs = this.queryReturnAttrs();
		String filter = "";
		Map<String, Object> objectClassMap = this.queryObjectClassValue();
		SearchResult si = null;
		this.build();
		if (objectClassMap != null) {
			String primaryKey = objectClassMap.get("primaryKey").toString();
			if (primaryKey.length() > 0) {
				String baseDN = "";
				if (objectClassMap.containsKey("baseDN")) {
					baseDN = objectClassMap.get("baseDN").toString();
				}
				String attrbutesDN = baseDN + "," + this.manager.getBaseDn();
				logger.info("attrbutesDN is " + attrbutesDN);
				filter = primaryKey + "=" + key;
				logger.info("Searchfilter is " + filter);
				if (this.contextSearchBase != null && this.contextSearchBase.length() > 0) {
					configRootDN = this.contextSearchBase + "," + attrbutesDN;
				} else {
					configRootDN = attrbutesDN;
				}
				logger.info("configRootDN is " + configRootDN);
				logger.info("Searchfilter is " + filter);
				if (ctx != null) {
					try {
						SearchControls sc = new SearchControls();
						sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
						sc.setReturningAttributes(returnAttrs);
						NamingEnumeration<SearchResult> results = ctx.search(configRootDN, filter, sc);
						if (results != null) {
							while (results.hasMoreElements()) {
								si = (SearchResult) results.nextElement();
								obj = this.constructDate(si);
							}
						}
					} catch (Exception e) {
						throw new LDAPOperationException("Error in queryUserByUID!", e);
					}
				} else {
					logger.info("InitialDirContext not success!");
				}
			} else {
				logger.info("Do not find primaryKey annotated in objectClass!");
			}
		} else {
			logger.info("Do not find field annotated objectClass");
		}
		this.destroy();
		return obj;
	}

	public List<T> queryObjects() throws LDAPOperationException {
		String configRootDN = "";
		List<T> ls = null;
		String[] returnAttrs = this.queryReturnAttrs();
		String filter = "";
		Map<String, Object> objectClassMap = this.queryObjectClassValue();
		SearchResult si = null;
		this.build();
		if (objectClassMap != null) {
			String primaryKey = objectClassMap.get("primaryKey").toString();
			if (primaryKey.length() > 0) {
				String baseDN = "";
				if (objectClassMap.containsKey("baseDN")) {
					baseDN = objectClassMap.get("baseDN").toString();
				}
				String attrbutesDN = baseDN + "," + this.manager.getBaseDn();
				logger.info("attrbutesDN is " + attrbutesDN);
				filter = primaryKey + "=*";
				if (this.contextSearchBase != null && this.contextSearchBase.length() > 0) {
					configRootDN = this.contextSearchBase + "," + attrbutesDN;
				} else {
					configRootDN = attrbutesDN;
				}
				logger.info("configRootDN is " + configRootDN);
				logger.info("Searchfilter is " + filter);
				if (ctx != null) {
					try {
						SearchControls sc = new SearchControls();
						sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
						sc.setReturningAttributes(returnAttrs);
						NamingEnumeration<SearchResult> results = ctx.search(configRootDN, filter, sc);
						if (results != null) {
							ls = new ArrayList<T>();
							while (results.hasMoreElements()) {
								si = (SearchResult) results.nextElement();
								ls.add(this.constructDate(si));
							}
						}
					} catch (Exception e) {
						throw new LDAPOperationException("Error in queryObjects!", e);
					}
				}
			} else {
				logger.info("Do not find primaryKey annotated in objectClass!");
			}
		} else {
			logger.info("Do not find field annotated objectClass");
		}
		this.destroy();
		return ls;
	}

	public List<T> queryObjects(int length) throws LDAPOperationException {
		String configRootDN = "";
		List<T> ls = null;
		String[] returnAttrs = this.queryReturnAttrs();
		String filter = "";
		Map<String, Object> objectClassMap = this.queryObjectClassValue();
		SearchResult si = null;
		this.build();
		if (objectClassMap != null) {
			String primaryKey = objectClassMap.get("primaryKey").toString();
			if (primaryKey.length() > 0) {
				String baseDN = "";
				if (objectClassMap.containsKey("baseDN")) {
					baseDN = objectClassMap.get("baseDN").toString();
				}
				String attrbutesDN = baseDN + "," + this.manager.getBaseDn();
				logger.info("attrbutesDN is " + attrbutesDN);
				filter = primaryKey + "=*";
				if (this.contextSearchBase != null && this.contextSearchBase.length() > 0) {
					configRootDN = this.contextSearchBase + "," + attrbutesDN;
				} else {
					configRootDN = attrbutesDN;
				}
				logger.info("configRootDN is " + configRootDN);
				logger.info("Searchfilter is " + filter);
				if (ctx != null) {
					try {
						SearchControls sc = new SearchControls();
						sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
						sc.setReturningAttributes(returnAttrs);
						sc.setCountLimit(length);
						NamingEnumeration<SearchResult> results = ctx.search(configRootDN, filter, sc);
						if (results != null) {
							ls = new ArrayList<T>();
							while (results.hasMoreElements()) {
								si = (SearchResult) results.nextElement();
								ls.add(this.constructDate(si));
							}
						}
					} catch (Exception e) {
						throw new LDAPOperationException("Error in queryObjects!", e);
					}
				}
			} else {
				logger.info("Do not find primaryKey annotated in objectClass!");
			}
		} else {
			logger.info("Do not find field annotated objectClass");
		}
		this.destroy();
		return ls;
	}

	public List<T> queryObjects(String searchFilter) throws LDAPOperationException {
		String configRootDN = "";
		List<T> ls = null;
		String[] returnAttrs = this.queryReturnAttrs();
		String filter = searchFilter;
		Map<String, Object> objectClassMap = this.queryObjectClassValue();
		SearchResult si = null;
		this.build();
		if (objectClassMap != null) {
			String primaryKey = objectClassMap.get("primaryKey").toString();
			if (primaryKey.length() > 0) {
				String baseDN = "";
				if (objectClassMap.containsKey("baseDN")) {
					baseDN = objectClassMap.get("baseDN").toString();
				}
				String attrbutesDN = baseDN + "," + this.manager.getBaseDn();
				logger.info("attrbutesDN is " + attrbutesDN);
				if (this.contextSearchBase != null && this.contextSearchBase.length() > 0) {
					configRootDN = this.contextSearchBase + "," + attrbutesDN;
				} else {
					configRootDN = attrbutesDN;
				}
				logger.info("configRootDN is " + configRootDN);
				logger.info("Searchfilter is " + filter);
				if (ctx != null) {
					try {
						SearchControls sc = new SearchControls();
						sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
						sc.setReturningAttributes(returnAttrs);
						NamingEnumeration<SearchResult> results = ctx.search(configRootDN, filter, sc);
						if (results != null) {
							ls = new ArrayList<T>();
							while (results.hasMoreElements()) {
								si = (SearchResult) results.nextElement();
								ls.add(this.constructDate(si));
							}
						}
					} catch (Exception e) {
						throw new LDAPOperationException("Error in queryObjects!", e);
					}
				}
			} else {
				logger.info("Do not find primaryKey annotated in objectClass!");
			}
		} else {
			logger.info("Do not find field annotated objectClass");
		}
		this.destroy();
		return ls;
	}

	public List<T> queryObjects(String searchFilter, int length) throws LDAPOperationException {
		String configRootDN = "";
		List<T> ls = null;
		String[] returnAttrs = this.queryReturnAttrs();
		String filter = searchFilter;
		Map<String, Object> objectClassMap = this.queryObjectClassValue();
		SearchResult si = null;
		this.build();
		if (objectClassMap != null) {
			String primaryKey = objectClassMap.get("primaryKey").toString();
			if (primaryKey.length() > 0) {
				String baseDN = "";
				if (objectClassMap.containsKey("baseDN")) {
					baseDN = objectClassMap.get("baseDN").toString();
				}
				String attrbutesDN = baseDN + "," + this.manager.getBaseDn();
				logger.info("attrbutesDN is " + attrbutesDN);
				if (this.contextSearchBase != null && this.contextSearchBase.length() > 0) {
					configRootDN = this.contextSearchBase + "," + attrbutesDN;
				} else {
					configRootDN = attrbutesDN;
				}
				logger.info("configRootDN is " + configRootDN);
				logger.info("Searchfilter is " + filter);
				if (ctx != null) {
					try {
						SearchControls sc = new SearchControls();
						sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
						sc.setReturningAttributes(returnAttrs);
						sc.setCountLimit(length);
						NamingEnumeration<SearchResult> results = ctx.search(configRootDN, filter, sc);
						if (results != null) {
							ls = new ArrayList<T>();
							while (results.hasMoreElements()) {
								si = (SearchResult) results.nextElement();
								ls.add(this.constructDate(si));
							}
						}
					} catch (Exception e) {
						throw new LDAPOperationException("Error in queryObjects!", e);
					}
				}
			} else {
				logger.info("Do not find primaryKey annotated in objectClass!");
			}
		} else {
			logger.info("Do not find field annotated objectClass");
		}
		this.destroy();
		return ls;
	}

	public boolean addObject(T t) throws LDAPOperationException {
		String configRootDN = "";
		boolean success = false;
		Attributes attrs = null;
		BasicAttribute objectSet = this.queryBasicAttrs();
		Map<String, Object> objectClassMap = this.queryObjectClassValue();
		if (objectClassMap != null) {
			this.build();
			if (objectClassMap.containsKey("primaryKey")) {
				String primaryKey = objectClassMap.get("primaryKey").toString();
				if (primaryKey.length() > 0) {
					String baseDN = "";
					if (objectClassMap.containsKey("baseDN")) {
						baseDN = objectClassMap.get("baseDN").toString();
					}
					String keyValue = (String) queryAttributeValueByAnnotation(t, primaryKey);
					String queryDN = "";
					String attrbutesDN = "";
					if (keyValue != null && keyValue.length() > 0) {
						attrbutesDN = baseDN + "," + this.manager.getBaseDn();
						queryDN = primaryKey + "=" + keyValue;
						logger.info("attrbutesDN is " + attrbutesDN);
						T obj = this.queryObject(keyValue);
						if (obj != null) {
							logger.info("The object with baseDN :" + queryDN + " is already exist!");
						} else {
							try {
								if (this.contextSearchBase != null && this.contextSearchBase.length() > 0) {
									configRootDN = queryDN + "," + this.contextSearchBase + "," + attrbutesDN;
								} else {
									configRootDN = queryDN + "," + attrbutesDN;
								}
								logger.info("configRootDN is " + configRootDN);
								attrs = new BasicAttributes(true);
								attrs.put(objectSet);
								attrs = this.constructAttributes(t, attrs);
								if (attrs != null) {
									ctx.bind(configRootDN, null, attrs);
									success = true;
								}
							} catch (Exception e) {
								throw new LDAPOperationException("Error in addObject!", e);
							}
						}
					} else {
						logger.info("baseDN dose't been found! Please check the LdapObject's fields that one of them has been annotation the baseDN");
					}
				} else {
					logger.info("primaryKey's value is null!");
				}
			} else {
				logger.info("Do not find primaryKey annotated in objectClass!!");
			}
			this.destroy();
		} else {
			logger.info("Do not find field annotated objectClass");
		}
		return success;
	}

	public boolean modifyObject(T t) throws LDAPOperationException {
		String configRootDN = "";
		boolean success = false;
		Map<String, Object> objectClassMap = this.queryObjectClassValue();
		ModificationItem[] modifyItems = null;
		if (objectClassMap != null) {
			this.build();
			if (objectClassMap.containsKey("primaryKey")) {
				String primaryKey = objectClassMap.get("primaryKey").toString();
				if (primaryKey.length() > 0) {
					String baseDN = "";
					if (objectClassMap.containsKey("baseDN")) {
						baseDN = objectClassMap.get("baseDN").toString();
					}
					String keyValue = (String) queryAttributeValueByAnnotation(t, primaryKey);
					String queryDN = "";
					String attrbutesDN = "";
					if (keyValue != null && keyValue.length() > 0) {
						attrbutesDN = baseDN + "," + this.manager.getBaseDn();
						queryDN = primaryKey + "=" + keyValue;
						logger.info("attrbutesDN is " + attrbutesDN);
						logger.info("queryDN is " + queryDN);
						T obj = this.queryObject(keyValue);
						if (obj != null) {
							if (this.contextSearchBase != null && this.contextSearchBase.length() > 0) {
								configRootDN = queryDN + "," + this.contextSearchBase + "," + attrbutesDN;
							} else {
								configRootDN = queryDN + "," + attrbutesDN;
							}
							logger.info("configRootDN is " + configRootDN);
							modifyItems = this.constructModifyItems(t, obj);
							try {
								if (modifyItems.length > 0)
									ctx.modifyAttributes(configRootDN, modifyItems);
								success = true;
							} catch (Exception e) {
								throw new LDAPOperationException("Error in modify the object!", e);
							}
						} else {
							logger.info("Modify Object does not sueccess , the object does not exist!");
						}
					} else {
						logger.info("baseDN dose't been found! Please check the LdapObject's fields that one of them has been annotation the baseDN");
					}
				} else {
					logger.info("primaryKey's value is null!");
				}
			} else {
				logger.info("Do not find primaryKey annotated in objectClass!");
			}
			this.destroy();
		} else {
			logger.info("Do not find field annotated objectClass");
		}
		return success;
	}

	public boolean deleteObject(T t) throws LDAPOperationException {
		String configRootDN = "";
		boolean success = false;
		Map<String, Object> objectClassMap = this.queryObjectClassValue();
		if (objectClassMap != null) {
			this.build();
			if (objectClassMap.containsKey("primaryKey")) {
				String primaryKey = objectClassMap.get("primaryKey").toString();
				if (primaryKey.length() > 0) {
					String baseDN = "";
					if (objectClassMap.containsKey("baseDN")) {
						baseDN = objectClassMap.get("baseDN").toString();
					}
					String keyValue = (String) queryAttributeValueByAnnotation(t, primaryKey);
					String queryDN = "";
					String attrbutesDN = "";
					if (keyValue != null && keyValue.length() > 0) {
						attrbutesDN = baseDN + "," + this.manager.getBaseDn();
						queryDN = primaryKey + "=" + keyValue;
						logger.info("attrbutesDN is " + attrbutesDN);
						T obj = this.queryObject(keyValue);
						if (obj != null) {
							if (this.contextSearchBase != null && this.contextSearchBase.length() > 0) {
								configRootDN = queryDN + "," + this.contextSearchBase + "," + attrbutesDN;
							} else {
								configRootDN = queryDN + "," + attrbutesDN;
							}
							logger.info("configRootDN is " + configRootDN);
							try {
								ctx.destroySubcontext(configRootDN);
								success = true;
							} catch (Exception e) {
								throw new LDAPOperationException("Error in addObject!", e);
							}
						} else {
							logger.info("The object with baseDN : " + queryDN + " does not exist!");
						}
					} else {
						logger.info("baseDN dose't been found! Please check the LdapObject's fields that one of them has been annotation the baseDN");
					}
				} else {
					logger.info("primaryKey's value is null!");
				}
			} else {
				logger.info("Do not find primaryKey annotated in objectClass!");
			}
			this.destroy();
		} else {
			logger.info("Do not find field annotated objectClass");
		}
		return success;
	}

	/**
	 * 构建修改属性
	 */
	private ModificationItem[] constructModifyItems(T newInstance, T oldInstance) throws LDAPOperationException {
		ModificationItem[] modifyItem = null;
		List<ModificationItem> modifyList = new ArrayList<ModificationItem>();
		Field[] fs = newInstance.getClass().getDeclaredFields();
		for (Field f : fs) {
			ModificationItem item = null;
			boolean hasAnn = f.isAnnotationPresent(AttributeMapping.class);
			if (hasAnn) {
				String mName = this.parGetName(f.getName());
				AttributeMapping am = f.getAnnotation(AttributeMapping.class);
				String[] amVs = am.mappingValue();
				boolean isArray = am.isArray();
				Method method = null;
				try {
					method = newInstance.getClass().getMethod(mName);
					if (amVs != null && amVs.length > 0) {
						for (String amV : amVs) {
							Object newValue = null, oldValue = null;
							try {
								newValue = method.invoke(newInstance);
								oldValue = method.invoke(oldInstance);
							} catch (Exception e) {
								throw new LDAPOperationException("Invoke the value of Object error!", e);
							}
							BasicAttribute attr = null;
							if (newValue == null && oldValue != null) { // 原值存在更新对象值不存在，跳过该值
								continue;
							}
							if (newValue != null) { // 新对象有值 则需要怕u盾那
								if (amV.equals(userPassword)) { // 如果是密码，则直接做更新操作
									if (isADEnv) {
										String pwd = String.valueOf(newValue);
										if (pwd.length() > 0) {
											pwd = "\"" + pwd + "\"";
											try {
												byte[] pwdbs = pwd.getBytes("UTF-16LE");
												BasicAttribute attrUserPwd = new BasicAttribute("unicodePwd", pwdbs);
												modifyList.add(new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
														attrUserPwd));
											} catch (UnsupportedEncodingException e) {
												logger.info("get user's unicodePwd error!", e);
											}
										}
									}
									attr = new BasicAttribute(amV, newValue);
									item = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr);
								} else { // 非密码则需要判断
									if (isArray) {
										if (newValue.getClass().isArray()) {
											String key = am.keyOfMember();
											String dn = am.dnOfMember();
											attr = new BasicAttribute(amV);
											String tempUQ = "";
											for (Object s : (Object[]) newValue) {
												tempUQ = "";
												if (key.length() > 0)
													tempUQ += key + "=";
												tempUQ += s.toString();
												if (dn.length() > 0)
													tempUQ += "," + dn + "," + this.manager.getBaseDn();
												attr.add(tempUQ);
												logger.info("[" + amV + ":" + tempUQ + "]");
											}
										} else {
											logger.info("The field named "
													+ f.getName()
													+ " was annotated the value of mappingValue's isArray is true ,but the field si not a array!");
											continue;
										}
									} else {
										attr = new BasicAttribute(amV, newValue);
									}
									if (oldValue == null) { // 如果没有该值，新增操作
										item = new ModificationItem(DirContext.ADD_ATTRIBUTE, attr);
									} else {
										if (!newValue.equals(oldValue))
											item = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr);
									}
								}
							}
							if (item != null)
								modifyList.add(item);
						}
					}
				} catch (NoSuchMethodException e) {
					logger.info("The class " + newInstance.getClass() + " do not find the method named : " + mName
							+ " in method constructModifyItems", e);
				} catch (SecurityException e) {
					logger.info("SecurityException in method constructModifyItems");
				}
			}
		}
		modifyItem = modifyList.toArray(new ModificationItem[modifyList.size()]);
		return modifyItem;
	}

	/**
	 * 构建基础属性
	 */
	private Attributes constructAttributes(T t, Attributes bs) throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		List<Object> ums = new ArrayList<Object>();
		if (!isADEnv)
			ums.add("uid=dummy");
		Field[] fs = t.getClass().getDeclaredFields();
		Attribute attr = null;
		for (Field f : fs) {
			boolean hasAnn = f.isAnnotationPresent(AttributeMapping.class);
			if (hasAnn) {
				AttributeMapping am = f.getAnnotation(AttributeMapping.class);
				boolean ifArray = am.isArray();
				String[] amVs = am.mappingValue();
				String mName = this.parGetName(f.getName());
				try {
					Method method = t.getClass().getMethod(mName);
					Object o = method.invoke(t);
					if (o != null)
						for (String amV : amVs) {
							if (!ifArray) {
								attr = new BasicAttribute(amV, o);
								if (amV.equals(userPassword)) {
									if (isADEnv) {
										String pwd = String.valueOf(o);
										pwd = "\"" + pwd + "\"";
										try {
											byte[] pwdbs = pwd.getBytes("UTF-16LE");
											BasicAttribute attrUserPwd = new BasicAttribute("unicodePwd", pwdbs);
											bs.put(attrUserPwd);
										} catch (UnsupportedEncodingException e) {
											logger.info("get user's unicodePwd error!", e);
										}

									}
								}
							} else {
								if (amV.equals(userPassword)) {
									logger.info("userPassword attrbute only can be annotated on the field which type is not array!");
									continue;
								}
								String key = am.keyOfMember();
								String dn = am.dnOfMember();
								if (o != null) {
									attr = new BasicAttribute(amV);
									if (o.getClass().isArray()) {
										String tempUQ = "";
										for (Object s : (Object[]) o) {
											tempUQ = "";
											if (key.length() > 0)
												tempUQ += key + "=";
											tempUQ += s.toString();
											if (dn.length() > 0)
												tempUQ += "," + dn + "," + this.manager.getBaseDn();
											attr.add(tempUQ);
											logger.info("[" + amV + ":" + tempUQ + "]");
										}
									}
								}
							}
							bs.put(attr);
						}
				} catch (NoSuchMethodException e) {
					logger.info("The class " + t.getClass() + " do not find the method named : " + mName
							+ " in method constructAttributes", e);
				} catch (SecurityException e) {
					logger.info("SecurityException in method constructBasicAttributes");
				}
			}
		}
		return bs;
	}

	/**
	 * 根据对象注解获取需要到LDAP中查找的属性列表
	 */
	private String[] queryReturnAttrs() throws LDAPOperationException {
		String[] attrs = null;
		List<String> ls = new ArrayList<String>();
		Field[] fs = this.modelClass.getDeclaredFields();
		for (Field f : fs) {
			boolean hasAnn = f.isAnnotationPresent(AttributeMapping.class);
			if (hasAnn) {
				try {
					AttributeMapping am = f.getAnnotation(AttributeMapping.class);
					String[] amVs = am.mappingValue();
					if (amVs != null && amVs.length > 0) {
						for (String amv : amVs)
							ls.add(amv);
					} else {
						continue;
					}
				} catch (SecurityException e) {
					logger.info("QueryReturnAttrs error!", e);
				}
			}
		}
		attrs = ls.toArray(new String[ls.size()]);
		return attrs;
	}

	/**
	 * 根据对象属性的注解到ldap中获取注解值对应的值组装到对象属性中
	 */
	private T constructDate(SearchResult si) throws InstantiationException, IllegalAccessException,
			InvocationTargetException {
		T object = this.modelClass.newInstance();
		try {
			NamingEnumeration<? extends Attribute> ae = si.getAttributes().getAll();
			while (ae.hasMoreElements()) {
				Attribute attr = (Attribute) ae.nextElement();
				String id = attr.getID();
				Field[] fs = this.modelClass.getDeclaredFields();
				for (Field f : fs) {
					boolean hasAnn = f.isAnnotationPresent(AttributeMapping.class);
					if (hasAnn) {
						AttributeMapping am = f.getAnnotation(AttributeMapping.class);
						String[] amVs = am.mappingValue();
						boolean ifArray = am.isArray();
						boolean ifIgnore = am.ifIgnore();
						for (String amV : amVs) {
							if (id.equals(amV)) {
								String methodSetName = this.parSetName(f.getName());
								Object o = null;
								if (!ifIgnore) {
									if (ifArray) {
										Method method = this.modelClass
												.getDeclaredMethod(methodSetName, String[].class);
										NamingEnumeration<?> ne = attr.getAll();
										List<Object> ls = new ArrayList<Object>();
										while (ne.hasMoreElements()) {
											ls.add(ne.nextElement());
										}
										Object os = ls.toArray(new String[ls.size()]);
										method.invoke(object, os);
									} else {
										Method method = this.modelClass.getDeclaredMethod(methodSetName, f.getType());
										o = attr.get();
										method.invoke(object, o);
									}
									break;
								}
							}
						}
					}
				}
			}
		} catch (IllegalArgumentException e) {
			logger.info("Error missing at least one attribute or attribute value!", e);
		} catch (NoSuchMethodException e) {
			logger.info("The class do not find the method in method constructDate", e);
		} catch (SecurityException e) {
			logger.info("SecurityException in constructDate");
		} catch (NamingException e) {
			logger.info("Error communication with ldap server!");
		}
		return object;
	}

	/**
	 * 根据传入的对象与注解值，获取被注解AttributeMapping该值的属性的的值
	 */
	private Object queryAttributeValueByAnnotation(T t, String annoValue) {
		Object o = null;
		Field[] fs = t.getClass().getDeclaredFields();
		for (Field f : fs) {
			boolean hasAnn = f.isAnnotationPresent(AttributeMapping.class);
			if (hasAnn) {
				AttributeMapping am = f.getAnnotation(AttributeMapping.class);
				String[] amVs = am.mappingValue();
				for (String amv : amVs) {
					if (amv.equals(annoValue)) {
						String mName = this.parGetName(f.getName());
						try {
							Method method = t.getClass().getMethod(mName);
							o = method.invoke(t);
						} catch (Exception e) {
							logger.info("Error in getAttributeValue!", e);
						}
						break;
					}
				}
			}
		}
		return o;
	}

	private String parGetName(String fieldName) {
		if (null == fieldName || "".equals(fieldName)) {
			return null;
		}
		return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}

	private String parSetName(String fieldName) {
		if (null == fieldName || "".equals(fieldName)) {
			return null;
		}
		return "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}

	private Map<String, Object> queryObjectClassValue() throws LDAPOperationException {
		Map<String, Object> map = null;
		Method[] methods = this.modelClass.getDeclaredMethods();
		for (Method m : methods) {
			boolean hasAnn = m.isAnnotationPresent(ObjectClass.class);
			if (hasAnn) {
				map = new HashMap<String, Object>();
				ObjectClass oc = m.getAnnotation(ObjectClass.class);
				if (oc.baseDN() != null && oc.baseDN().length() > 0) {
					map.put("baseDN", oc.baseDN());
				}
				if (oc.primaryKey() != null && oc.primaryKey().length() > 0) {
					map.put("primaryKey", oc.primaryKey());
				}
				if (oc.objectType() != null) {
					map.put("objectType", oc.objectType());
				}
			}
		}
		return map;
	}

	private BasicAttribute queryBasicAttrs() throws LDAPOperationException {
		BasicAttribute objclassSet = null;
		Method[] methods = this.modelClass.getDeclaredMethods();
		for (Method m : methods) {
			boolean hasAnn = m.isAnnotationPresent(ObjectClass.class);
			if (hasAnn) {
				ObjectClass oc = m.getAnnotation(ObjectClass.class);
				if (oc.objectType() != null) {
					objclassSet = new BasicAttribute("objectclass");
					String[] objectClasses = {};
					ObjectType oj = oc.objectType();
					switch (oj) {
						case NORMAL: {
							objectClasses = StaticObjectClassValue.commonCN;
							break;
						}
						case USER: {
							objectClasses = StaticObjectClassValue.commonCN;
							break;
						}
						case ORGANIZATION: {
							objectClasses = StaticObjectClassValue.commonCN;
							break;
						}
						case USERFORIMS: {
							objectClasses = StaticObjectClassValue.userTypeForIMSV16;
							break;
						}
						case ORGANIZATIONFORIMS: {
							objectClasses = StaticObjectClassValue.orgTypeForIMSV16;
							break;
						}
						case GROUP: {
							objectClasses = StaticObjectClassValue.group;
							break;
						}
						case USERFORAD: {
							objectClasses = StaticObjectClassValue.userForAD;
							break;
						}
						case ORGANIZATIONFORAD: {
							objectClasses = StaticObjectClassValue.organizationForAD;
							break;
						}
					}
					for (String oClass : objectClasses) {
						objclassSet.add(oClass);
					}
				}
			}
		}
		return objclassSet;
	}

	public String getContextSearchBase() {
		return contextSearchBase;
	}

	public void setContextSearchBase(String contextSearchBase) {
		this.contextSearchBase = contextSearchBase;
	}

	public LDAPPoolManager getManager() {
		return manager;
	}

	public void setManager(LDAPPoolManager manager) {
		this.manager = manager;
	}
}
