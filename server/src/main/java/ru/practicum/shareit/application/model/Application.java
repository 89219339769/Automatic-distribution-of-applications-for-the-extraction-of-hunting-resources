package ru.practicum.shareit.application.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "applications")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column(name = "application_type", length = 100,  nullable = false)
    private ApplicationType type;


    @Column(name = "start_of_issue", nullable = false)
    private LocalDate dateOfIssue;

    @Column(nullable = false)
    private Integer series;

    @Column(nullable = false)
    private Integer number;


    @ManyToOne
    @JoinColumn(name = "requested_resources_id", referencedColumnName = "id", nullable = false)
    private RequestedResources requestedResources;


    @Enumerated(EnumType.STRING)
    @Column(name = "application_status", length = 100,  nullable = false)
    private ApplicationStatus status;

}