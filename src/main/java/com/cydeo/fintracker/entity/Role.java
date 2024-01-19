package com.cydeo.fintracker.entity;

import com.cydeo.fintracker.entity.common.BaseEntity;
import lombok.*;

import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "roles")
public class Role extends BaseEntity {

    private String description;

}
