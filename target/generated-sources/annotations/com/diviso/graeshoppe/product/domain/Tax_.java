package com.diviso.graeshoppe.product.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Tax.class)
public abstract class Tax_ {

	public static volatile SingularAttribute<Tax, Double> rate;
	public static volatile SingularAttribute<Tax, String> name;
	public static volatile SingularAttribute<Tax, String> description;
	public static volatile SingularAttribute<Tax, Long> id;
	public static volatile SingularAttribute<Tax, TaxCategory> taxCategory;

	public static final String RATE = "rate";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String TAX_CATEGORY = "taxCategory";

}

