package com.diviso.graeshoppe.product.service.dto;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

import javax.persistence.Lob;

/**
 * A DTO for the {@link com.diviso.graeshoppe.product.domain.Category} entity.
 */
public class CategoryDTO implements Serializable {

    private Long id;

    private String iDPcode;

    private String name;

    @Lob
    private byte[] image;
    
    private String imageLink;

    private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getiDPcode() {
		return iDPcode;
	}

	public void setiDPcode(String iDPcode) {
		this.iDPcode = iDPcode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getImageLink() {
		return imageLink;
	}

	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((iDPcode == null) ? 0 : iDPcode.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + Arrays.hashCode(image);
		result = prime * result + ((imageLink == null) ? 0 : imageLink.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CategoryDTO other = (CategoryDTO) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (iDPcode == null) {
			if (other.iDPcode != null)
				return false;
		} else if (!iDPcode.equals(other.iDPcode))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (!Arrays.equals(image, other.image))
			return false;
		if (imageLink == null) {
			if (other.imageLink != null)
				return false;
		} else if (!imageLink.equals(other.imageLink))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CategoryDTO [id=" + id + ", iDPcode=" + iDPcode + ", name=" + name + ", image=" + Arrays.toString(image)
				+ ", imageLink=" + imageLink + ", description=" + description + "]";
	}

}