package com.diviso.graeshoppe.product.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the AuxilaryLineItem entity.
 */
public class AuxilaryLineItemDTO implements Serializable {

	private Long id;

	private String description;

	private Double quantity;

	private Long productId;

	private Long auxilaryItemId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getAuxilaryItemId() {
		return auxilaryItemId;
	}

	public void setAuxilaryItemId(Long productId) {
		this.auxilaryItemId = productId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		AuxilaryLineItemDTO auxilaryLineItemDTO = (AuxilaryLineItemDTO) o;
		if (auxilaryLineItemDTO.getId() == null || getId() == null) {
			return false;
		}
		System.out.println(((auxilaryLineItemDTO.getProductId().equals(getProductId()))
				&& (auxilaryLineItemDTO.getAuxilaryItemId().equals(getAuxilaryItemId())))+"..........boolean check equals..............");
		
		return !((auxilaryLineItemDTO.getProductId().equals(getProductId()))
				&& (auxilaryLineItemDTO.getAuxilaryItemId().equals(getAuxilaryItemId())));
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "AuxilaryLineItemDTO{" + "id=" + getId() + ", description='" + getDescription() + "'" + ", quantity="
				+ getQuantity() + ", product=" + getProductId() + ", auxilaryItem=" + getAuxilaryItemId() + "}";
	}
}
