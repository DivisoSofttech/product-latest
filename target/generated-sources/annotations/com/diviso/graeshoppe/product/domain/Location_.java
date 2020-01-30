package com.diviso.graeshoppe.product.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Location.class)
public abstract class Location_ {

	public static volatile SingularAttribute<Location, Address> address;
	public static volatile SingularAttribute<Location, String> latLon;
	public static volatile SingularAttribute<Location, String> name;
	public static volatile SingularAttribute<Location, Long> id;
	public static volatile SingularAttribute<Location, String> iDPcode;

	public static final String ADDRESS = "address";
	public static final String LAT_LON = "latLon";
	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String I_DPCODE = "iDPcode";

}

