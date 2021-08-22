package com.spring.junit.test.project.service;

import com.spring.junit.test.project.dao.ProjectRepository;
import com.spring.junit.test.project.model.vo.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository){
        this.projectRepository = projectRepository;
    }

    public Project createProject(Project project){
        return projectRepository.save(project);
    }

    public Project getProject(Integer id){
        Optional<Project> optionalProject = projectRepository.findById(id);
        if(optionalProject.isEmpty()){
            return null;
        }

        return optionalProject.get();
    }

    public Page<Project> getAllProject(Pageable pageable){
        return projectRepository.findAll(pageable);
    }

    public Project updateProjects(Project project){

        Optional<Project> optionalProject = projectRepository.findById(project.getId());

        boolean isUpdate = false;

        optionalProject.ifPresent(selectProject ->{

            selectProject.setEmployeeId(project.getEmployeeId());
            selectProject.setEmployeeNm(project.getEmployeeNm());
            selectProject.setName(project.getName());
            selectProject.setStatus(project.getStatus());
            selectProject.setDescription(project.getDescription());
            selectProject.setBeginDateTime(project.getBeginDateTime());
            selectProject.setEndDateTime(project.getEndDateTime());
            selectProject.setManMonth(project.getManMonth());
            selectProject.setCost(project.getCost());

            projectRepository.save(selectProject);

        });

        return null;
    }

}
