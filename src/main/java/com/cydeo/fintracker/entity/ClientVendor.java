package com.cydeo.fintracker.entity;

import com.cydeo.fintracker.entity.common.BaseEntity;
import com.cydeo.fintracker.enums.ClientVendorType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "clients_vendors")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ClientVendor extends BaseEntity {
    private String clientVendorName;
    private String phone;
    private String website;

    @Enumerated(EnumType.STRING)
    private ClientVendorType clientVendorType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
}
