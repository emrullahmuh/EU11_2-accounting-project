package com.cydeo.fintracker.entity;

import com.cydeo.fintracker.entity.common.BaseEntity;
import com.cydeo.fintracker.enums.CompanyStatus;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "companies")
public class Company extends BaseEntity {

    @Column(name = "title", unique = true)
    private String title;

    @Column(name = "phone")
    private String phone;

    @Column(name = "website")
    private String website;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @Enumerated(EnumType.STRING)
    @Column(name = "company_status")
    private CompanyStatus companyStatus;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    private List<User> users;


}
