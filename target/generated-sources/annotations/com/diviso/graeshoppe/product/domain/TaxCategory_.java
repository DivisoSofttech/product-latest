package com.diviso.graeshoppe.product.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TaxCategory.class)
public abstract class TaxCategory_ {

	public static volatile SingularAttribute<TaxCategory, String> name;
	public static volatile SingularAttribute<TaxCategory, String> description;
	public static volatile SetAttribute<TaxCategory, Tax> taxes;
	public static volatile SingularAttribute<TaxCategory, Long> id;
	public static volatile SingularAttribute<TaxCategory, String> iDPcode;

	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String TAXES = "taxes";
	public static final String ID = "id";
	public static final String I_DPCODE = "iDPcode";

}

