package com.diviso.graeshoppe.product.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ComboLineItem.class)
public abstract class ComboLineItem_ {

	public static volatile SingularAttribute<ComboLineItem, Product> product;
	public static volatile SingularAttribute<ComboLineItem, Double> quantity;
	public static volatile SingularAttribute<ComboLineItem, String> description;
	public static volatile SingularAttribute<ComboLineItem, Long> id;
	public static volatile SingularAttribute<ComboLineItem, Product> comboItem;

	public static final String PRODUCT = "product";
	public static final String QUANTITY = "quantity";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String COMBO_ITEM = "comboItem";

}

