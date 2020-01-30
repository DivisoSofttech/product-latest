package com.diviso.graeshoppe.product.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Product.class)
public abstract class Product_ {

	public static volatile SingularAttribute<Product, Discount> discount;
	public static volatile SingularAttribute<Product, Boolean> showInCatalogue;
	public static volatile SingularAttribute<Product, Double> minQuantityLevel;
	public static volatile SingularAttribute<Product, Boolean> isActive;
	public static volatile SingularAttribute<Product, Double> storageCost;
	public static volatile SingularAttribute<Product, Manufacturer> manufacturer;
	public static volatile SingularAttribute<Product, String> reference;
	public static volatile SingularAttribute<Product, String> imageLink;
	public static volatile SingularAttribute<Product, Double> sellingPrice;
	public static volatile SingularAttribute<Product, Supplier> supplier;
	public static volatile SingularAttribute<Product, Long> id;
	public static volatile SingularAttribute<Product, String> sku;
	public static volatile SingularAttribute<Product, Boolean> isAuxilaryItem;
	public static volatile SingularAttribute<Product, Brand> brand;
	public static volatile SingularAttribute<Product, Boolean> isServiceItem;
	public static volatile SingularAttribute<Product, Double> buyPrice;
	public static volatile SetAttribute<Product, AuxilaryLineItem> auxilaryLineItems;
	public static volatile SingularAttribute<Product, String> iDPcode;
	public static volatile SingularAttribute<Product, Double> maxQuantityLevel;
	public static volatile SetAttribute<Product, Label> labels;
	public static volatile SingularAttribute<Product, TaxCategory> taxCategory;
	public static volatile SingularAttribute<Product, UOM> unit;
	public static volatile SetAttribute<Product, ComboLineItem> comboLineItems;
	public static volatile SingularAttribute<Product, String> name;
	public static volatile SingularAttribute<Product, Location> location;
	public static volatile SingularAttribute<Product, Category> category;

	public static final String DISCOUNT = "discount";
	public static final String SHOW_IN_CATALOGUE = "showInCatalogue";
	public static final String MIN_QUANTITY_LEVEL = "minQuantityLevel";
	public static final String IS_ACTIVE = "isActive";
	public static final String STORAGE_COST = "storageCost";
	public static final String MANUFACTURER = "manufacturer";
	public static final String REFERENCE = "reference";
	public static final String IMAGE_LINK = "imageLink";
	public static final String SELLING_PRICE = "sellingPrice";
	public static final String SUPPLIER = "supplier";
	public static final String ID = "id";
	public static final String SKU = "sku";
	public static final String IS_AUXILARY_ITEM = "isAuxilaryItem";
	public static final String BRAND = "brand";
	public static final String IS_SERVICE_ITEM = "isServiceItem";
	public static final String BUY_PRICE = "buyPrice";
	public static final String AUXILARY_LINE_ITEMS = "auxilaryLineItems";
	public static final String I_DPCODE = "iDPcode";
	public static final String MAX_QUANTITY_LEVEL = "maxQuantityLevel";
	public static final String LABELS = "labels";
	public static final String TAX_CATEGORY = "taxCategory";
	public static final String UNIT = "unit";
	public static final String COMBO_LINE_ITEMS = "comboLineItems";
	public static final String NAME = "name";
	public static final String LOCATION = "location";
	public static final String CATEGORY = "category";

}

