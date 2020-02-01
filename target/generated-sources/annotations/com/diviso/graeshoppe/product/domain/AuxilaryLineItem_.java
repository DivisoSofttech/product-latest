package com.diviso.graeshoppe.product.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AuxilaryLineItem.class)
public abstract class AuxilaryLineItem_ {

	public static volatile SingularAttribute<AuxilaryLineItem, Product> product;
	public static volatile SingularAttribute<AuxilaryLineItem, Double> quantity;
	public static volatile SingularAttribute<AuxilaryLineItem, Product> auxilaryItem;
	public static volatile SingularAttribute<AuxilaryLineItem, String> description;
	public static volatile SingularAttribute<AuxilaryLineItem, Long> id;

	public static final String PRODUCT = "product";
	public static final String QUANTITY = "quantity";
	public static final String AUXILARY_ITEM = "auxilaryItem";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";

}

