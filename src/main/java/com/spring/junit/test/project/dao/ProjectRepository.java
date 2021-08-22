package com.spring.junit.test.project.dao;

import com.spring.junit.test.project.model.vo.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Integer>
{

}
