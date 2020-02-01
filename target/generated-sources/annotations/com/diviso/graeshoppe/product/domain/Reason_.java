package com.diviso.graeshoppe.product.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Reason.class)
public abstract class Reason_ {

	public static volatile SingularAttribute<Reason, String> name;
	public static volatile SingularAttribute<Reason, String> description;
	public static volatile SingularAttribute<Reason, Long> id;
	public static volatile SingularAttribute<Reason, String> iDPcode;

	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String I_DPCODE = "iDPcode";

}

