package com.diviso.graeshoppe.product.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Supplier.class)
public abstract class Supplier_ {

	public static volatile SingularAttribute<Supplier, Double> currentDebt;
	public static volatile SingularAttribute<Supplier, Boolean> visible;
	public static volatile SingularAttribute<Supplier, Address> address;
	public static volatile SingularAttribute<Supplier, String> companyName;
	public static volatile SingularAttribute<Supplier, Contact> contact;
	public static volatile SingularAttribute<Supplier, Double> creditLimit;
	public static volatile SingularAttribute<Supplier, LocalDate> debtDate;
	public static volatile SingularAttribute<Supplier, Long> id;
	public static volatile SingularAttribute<Supplier, String> iDPcode;

	public static final String CURRENT_DEBT = "currentDebt";
	public static final String VISIBLE = "visible";
	public static final String ADDRESS = "address";
	public static final String COMPANY_NAME = "companyName";
	public static final String CONTACT = "contact";
	public static final String CREDIT_LIMIT = "creditLimit";
	public static final String DEBT_DATE = "debtDate";
	public static final String ID = "id";
	public static final String I_DPCODE = "iDPcode";

}

