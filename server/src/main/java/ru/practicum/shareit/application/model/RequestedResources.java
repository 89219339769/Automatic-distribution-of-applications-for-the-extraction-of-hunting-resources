package ru.practicum.shareit.application.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "resources", schema = "public")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestedResources {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String district;

    @Column(nullable = false,length = 50)
    private String name;

    @Column(nullable = false,length = 50)
    private Integer quantity;
    @Enumerated(EnumType.STRING)
    @Column(name = "application_status", length = 100, nullable = false)
    private ResourceStatus resourcesStatus;


}
