package com.cydeo.entity;

import com.cydeo.entity.common.BaseEntity;
import com.cydeo.enums.ClientVendorType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;

@Entity
@Table(name = "clients_vendors")
@NoArgsConstructor
@AllArgsConstructor
public class ClientVendor extends BaseEntity {

   private String clientVendorName;
   private String phone;
   private String website;
   @Enumerated(EnumType.STRING)
   private ClientVendorType clientVendorType;
   @OneToOne(cascade = CascadeType.ALL)
   @JoinColumn(name = "address_id", referencedColumnName = "id")
   private Address address;
   @ManyToOne
   @JoinColumn(name = "company_id")
   private Company company;


/* 2. "ClientVendor" Entity Class should have below fields:

String clientVendorName
String phone
String website
ClientVendorType clientVendorType / enum
Address address / one-to-one / will be seen under "address_id" column on the "clients_vendors" table
Company company / many-to-one / will be seen under "company_id" column on the "clients_vendors" table

3. Table name should be "clients_vendors"

@OneToOne(fetch = FetchType.LAZY) ?
@OneToOne(cascade = CascadeType.ALL) ?


 */

}
