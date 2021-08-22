package com.spring.junit.test.project.model.dto;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.time.LocalDateTime;

@Component
public class ProjectValidator {
    public void validate(ProjectDto projectDto, Errors errors){

        if(projectDto.getEmployeeId().isBlank()){
            errors.reject("wrongEmployeeId", "EmployeeId is wrong.");
        }

        if(projectDto.getName().isBlank()){
            errors.reject("wrongProjectNm", "ProjectNm is wrong.");
        }

        LocalDateTime beginDateTime = projectDto.getBeginDateTime();
        LocalDateTime endDateTime = projectDto.getEndDateTime();

        if(beginDateTime == null){
            errors.reject("wrongBeginDateTime", "BeginDateTime is Empty.");
        }

        if(endDateTime == null){
            errors.reject("wrongEndDateTime", "EndDateTime is Empty.");
        }

        if(!beginDateTime.isBefore(endDateTime)){
            errors.reject("wrongDateTime", "BeginDateTime or EndDateTime is wrong.");
        }

        if(projectDto.getManMonth() == 0){
            errors.reject("wrongCost", "ManMonth is wrong.");
        }

        if(projectDto.getCost() == 0){
            errors.reject("wrongCost", "Cost is wrong.");
        }
    }
}
