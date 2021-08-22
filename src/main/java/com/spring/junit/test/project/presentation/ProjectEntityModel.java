package com.spring.junit.test.project.presentation;

import com.spring.junit.test.project.model.vo.Project;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class ProjectEntityModel extends EntityModel<Project> {

    public ProjectEntityModel(Project project, Link... links){
        super(project, links);
        add(linkTo(ProjectController.class).slash(project.getId()).withSelfRel());
    }

    public ProjectEntityModel(List<Project> projectList, Link... links){

        for(Project project : projectList){
            add(linkTo(ProjectController.class).slash(project.getId()).withSelfRel());
        }

    }
}
