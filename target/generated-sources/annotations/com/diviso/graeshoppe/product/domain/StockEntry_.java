package com.diviso.graeshoppe.product.domain;

import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(StockEntry.class)
public abstract class StockEntry_ {

	public static volatile SingularAttribute<StockEntry, String> reference;
	public static volatile SingularAttribute<StockEntry, Instant> date;
	public static volatile SingularAttribute<StockEntry, Reason> reason;
	public static volatile SetAttribute<StockEntry, EntryLineItem> entryLineItems;
	public static volatile SingularAttribute<StockEntry, String> description;
	public static volatile SingularAttribute<StockEntry, Location> location;
	public static volatile SingularAttribute<StockEntry, Long> id;
	public static volatile SingularAttribute<StockEntry, String> iDPcode;

	public static final String REFERENCE = "reference";
	public static final String DATE = "date";
	public static final String REASON = "reason";
	public static final String ENTRY_LINE_ITEMS = "entryLineItems";
	public static final String DESCRIPTION = "description";
	public static final String LOCATION = "location";
	public static final String ID = "id";
	public static final String I_DPCODE = "iDPcode";

}

