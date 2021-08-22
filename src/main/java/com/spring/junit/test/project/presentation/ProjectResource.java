package com.spring.junit.test.project.presentation;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.spring.junit.test.project.model.vo.Project;
import org.springframework.hateoas.RepresentationModel;

public class ProjectResource extends RepresentationModel {
    @JsonUnwrapped
    private Project project;

    public ProjectResource(Project project){
        this.project = project;
    }

    public Project getProject(){
        return project;
    }
}
