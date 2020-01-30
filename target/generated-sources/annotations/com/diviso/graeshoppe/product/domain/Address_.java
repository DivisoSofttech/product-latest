package com.diviso.graeshoppe.product.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Address.class)
public abstract class Address_ {

	public static volatile SingularAttribute<Address, String> zipcode;
	public static volatile SingularAttribute<Address, String> country;
	public static volatile SingularAttribute<Address, String> addressLine1;
	public static volatile SingularAttribute<Address, String> addressLine2;
	public static volatile SingularAttribute<Address, Long> id;
	public static volatile SingularAttribute<Address, String> state;
	public static volatile SingularAttribute<Address, String> cityOrTown;

	public static final String ZIPCODE = "zipcode";
	public static final String COUNTRY = "country";
	public static final String ADDRESS_LINE1 = "addressLine1";
	public static final String ADDRESS_LINE2 = "addressLine2";
	public static final String ID = "id";
	public static final String STATE = "state";
	public static final String CITY_OR_TOWN = "cityOrTown";

}

