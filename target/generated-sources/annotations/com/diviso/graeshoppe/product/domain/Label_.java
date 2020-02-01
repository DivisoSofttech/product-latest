package com.diviso.graeshoppe.product.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Label.class)
public abstract class Label_ {

	public static volatile SingularAttribute<Label, Product> product;
	public static volatile SingularAttribute<Label, String> name;
	public static volatile SingularAttribute<Label, Long> id;
	public static volatile SingularAttribute<Label, String> iDPcode;

	public static final String PRODUCT = "product";
	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String I_DPCODE = "iDPcode";

}

