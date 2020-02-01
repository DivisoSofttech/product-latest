package com.diviso.graeshoppe.product.service.mapper;

import com.diviso.graeshoppe.product.domain.Contact;
import com.diviso.graeshoppe.product.service.dto.ContactDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-02-01T13:02:31+0530",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_222 (Private Build)"
)
@Component
public class ContactMapperImpl implements ContactMapper {

    @Override
    public Contact toEntity(ContactDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Contact contact = new Contact();

        contact.setId( dto.getId() );
        contact.setSaluation( dto.getSaluation() );
        contact.setFirstName( dto.getFirstName() );
        contact.setLastName( dto.getLastName() );
        contact.setEmail( dto.getEmail() );
        contact.setTelephone( dto.getTelephone() );
        contact.setMobile( dto.getMobile() );
        contact.setFax( dto.getFax() );
        contact.setWebsiteURL( dto.getWebsiteURL() );
        contact.setFacebook( dto.getFacebook() );
        contact.setTwitter( dto.getTwitter() );

        return contact;
    }

    @Override
    public ContactDTO toDto(Contact entity) {
        if ( entity == null ) {
            return null;
        }

        ContactDTO contactDTO = new ContactDTO();

        contactDTO.setId( entity.getId() );
        contactDTO.setSaluation( entity.getSaluation() );
        contactDTO.setFirstName( entity.getFirstName() );
        contactDTO.setLastName( entity.getLastName() );
        contactDTO.setEmail( entity.getEmail() );
        contactDTO.setTelephone( entity.getTelephone() );
        contactDTO.setMobile( entity.getMobile() );
        contactDTO.setFax( entity.getFax() );
        contactDTO.setWebsiteURL( entity.getWebsiteURL() );
        contactDTO.setFacebook( entity.getFacebook() );
        contactDTO.setTwitter( entity.getTwitter() );

        return contactDTO;
    }

    @Override
    public List<Contact> toEntity(List<ContactDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Contact> list = new ArrayList<Contact>( dtoList.size() );
        for ( ContactDTO contactDTO : dtoList ) {
            list.add( toEntity( contactDTO ) );
        }

        return list;
    }

    @Override
    public List<ContactDTO> toDto(List<Contact> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ContactDTO> list = new ArrayList<ContactDTO>( entityList.size() );
        for ( Contact contact : entityList ) {
            list.add( toDto( contact ) );
        }

        return list;
    }
}
