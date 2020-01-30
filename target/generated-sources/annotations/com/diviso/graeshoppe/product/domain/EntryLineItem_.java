package com.diviso.graeshoppe.product.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(EntryLineItem.class)
public abstract class EntryLineItem_ {

	public static volatile SingularAttribute<EntryLineItem, Product> product;
	public static volatile SingularAttribute<EntryLineItem, Double> valueAdjustment;
	public static volatile SingularAttribute<EntryLineItem, String> description;
	public static volatile SingularAttribute<EntryLineItem, Double> quantityAdjustment;
	public static volatile SingularAttribute<EntryLineItem, Long> id;
	public static volatile SingularAttribute<EntryLineItem, StockEntry> stockEntry;

	public static final String PRODUCT = "product";
	public static final String VALUE_ADJUSTMENT = "valueAdjustment";
	public static final String DESCRIPTION = "description";
	public static final String QUANTITY_ADJUSTMENT = "quantityAdjustment";
	public static final String ID = "id";
	public static final String STOCK_ENTRY = "stockEntry";

}

