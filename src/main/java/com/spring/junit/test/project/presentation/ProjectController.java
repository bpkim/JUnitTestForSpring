package com.spring.junit.test.project.presentation;

import com.spring.junit.test.com.util.PagedModelUtil;
import com.spring.junit.test.project.model.dto.ProjectDto;
import com.spring.junit.test.project.model.dto.ProjectValidator;
import com.spring.junit.test.project.model.vo.Project;
import com.spring.junit.test.project.service.ProjectService;
import org.modelmapper.ModelMapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.*;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/api/v1/project", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ProjectController {

    private final ProjectService projectService;
    private final ProjectValidator projectValidator;
    private final ModelMapper modelMapper;

    public ProjectController(ProjectService projectService, ProjectValidator projectValidator, ModelMapper modelMapper){
        this.projectService = projectService;
        this.projectValidator = projectValidator;
        this.modelMapper = modelMapper;
    }


    @PostMapping
    public ResponseEntity createProject(@RequestBody @Valid ProjectDto projectDto, Errors errors){

        if(errors.hasErrors()){
            return ResponseEntity.badRequest().body(errors);
        }

        projectValidator.validate(projectDto, errors);

        if(errors.hasErrors()){
            return ResponseEntity.badRequest().body(errors);
        }

        Project project = modelMapper.map(projectDto, Project.class);

        Project newProject = projectService.createProject(project);

        WebMvcLinkBuilder selfLinkBuilder = linkTo(ProjectController.class).slash(newProject.getId());
        URI createUri = selfLinkBuilder.toUri();
        ProjectEntityModel resource = new ProjectEntityModel(newProject);

        // 조회 링크
        resource.add(linkTo(ProjectController.class).withRel("query-project"));
        // 업데이트
        resource.add(selfLinkBuilder.withRel("update-project"));
        resource.add(new Link("/docs/index.html#resources-project-create").withRel("profile"));


        return ResponseEntity.created(createUri).body(resource);
    }

    
    @GetMapping
    public ResponseEntity getAllProject(Pageable pageable, PagedResourcesAssembler<Project> assembler) {
        Page<Project> projectPage = projectService.getAllProject(pageable);


        // 못찾았을 때
        if(projectPage.getNumber() == 0){
            return ResponseEntity.notFound().build();
        }


        PagedModel<EntityModel<Project>> entityModels = PagedModelUtil.getEntityModels(assembler, projectPage
            , linkTo(this.getClass()), Project::getId);

        return ResponseEntity.ok(entityModels);
    }


    @GetMapping("/{id}")
    public ResponseEntity getProject(@PathVariable("id") Integer projectId){

        Project project = null;

        project = projectService.getProject(projectId);

        // 못찾았을 때
        if(project == null){
            return ResponseEntity.notFound().build();
        }

        WebMvcLinkBuilder selfLinkBuilder = linkTo(ProjectController.class).slash(project.getId());
        ProjectEntityModel resource = new ProjectEntityModel(project);

        // 조회 링크
        resource.add(linkTo(ProjectController.class).withRel("query-project"));
        // 업데이트
        resource.add(selfLinkBuilder.withRel("update-project"));
        resource.add(new Link("/docs/index.html#get-a-project").withRel("profile"));


        return ResponseEntity.ok(resource);
    }

    @PutMapping
    public ResponseEntity updateProject(@RequestBody @Valid ProjectDto projectDto, Errors errors){

        if(errors.hasErrors()){
            return ResponseEntity.badRequest().body(errors);
        }

        projectValidator.validate(projectDto, errors);

        if(errors.hasErrors()){
            return ResponseEntity.badRequest().body(errors);
        }

        Project project = modelMapper.map(projectDto, Project.class);

        Project newProject = projectService.updateProjects(project);

        WebMvcLinkBuilder selfLinkBuilder = linkTo(ProjectController.class).slash(newProject.getId());
        URI createUri = selfLinkBuilder.toUri();
        ProjectEntityModel resource = new ProjectEntityModel(newProject);

        // 조회 링크
        resource.add(linkTo(ProjectController.class).withRel("query-project"));
        // 업데이트
        resource.add(selfLinkBuilder.withRel("create-project"));
        resource.add(new Link("/docs/index.html#resources-project-create").withRel("profile"));


        return ResponseEntity.created(createUri).body(resource);
    }

}
