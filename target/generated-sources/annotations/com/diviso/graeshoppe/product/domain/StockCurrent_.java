package com.diviso.graeshoppe.product.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(StockCurrent.class)
public abstract class StockCurrent_ {

	public static volatile SingularAttribute<StockCurrent, Product> product;
	public static volatile SingularAttribute<StockCurrent, Double> quantity;
	public static volatile SingularAttribute<StockCurrent, String> notes;
	public static volatile SingularAttribute<StockCurrent, Double> sellPrice;
	public static volatile SingularAttribute<StockCurrent, Long> id;
	public static volatile SingularAttribute<StockCurrent, String> iDPcode;

	public static final String PRODUCT = "product";
	public static final String QUANTITY = "quantity";
	public static final String NOTES = "notes";
	public static final String SELL_PRICE = "sellPrice";
	public static final String ID = "id";
	public static final String I_DPCODE = "iDPcode";

}

