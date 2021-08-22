package com.spring.junit.test.project.model.vo;

import com.spring.junit.test.project.model.ProjectStatus;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
public class Project extends RepresentationModel<Project> {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String employeeId;
    private String employeeNm;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status = ProjectStatus.ENROLLMENT;

    private String description;

    private LocalDateTime beginDateTime;
    private LocalDateTime endDateTime;

    private int manMonth;
    private int cost;

}
