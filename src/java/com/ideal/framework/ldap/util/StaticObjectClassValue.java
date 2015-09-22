package com.ideal.framework.ldap.util;

public class StaticObjectClassValue {
	public final static String[] commonCN = { "top", "container" };
	public final static String[] group = {"top","group"};
	public final static String[] userTypeForIMSV16 = { "top", "person", "organizationalPerson", "inetOrgPerson",
			"eContactPerson", "eSAP", "ePerson", "cimLogicalElement", "cimManagedElement", "cimManagedSystemElement" };
	public final static String[] orgTypeForIMSV16 = { "top", "groupOfUniqueNames", "liOrganization", "organization" };
	public final static String[] userForAD = {"top", "person", "organizationalPerson","user"};
	public final static String[] organizationForAD = {"organizationalUnit","top"};
}