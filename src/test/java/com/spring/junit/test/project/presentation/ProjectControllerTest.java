package com.spring.junit.test.project.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.junit.test.com.util.TestDescription;
import com.spring.junit.test.project.model.dto.ProjectDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc // SpringBootTest 에서 moc 쓰기
public class ProjectControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void createProject() throws Exception{
        ProjectDto projectDto = ProjectDto.builder()
                                .name("프로젝트")
                                .employeeId("1234")
                                .employeeNm("김철수")
                                .description("프로젝트 입니다.")
                                .beginDateTime(LocalDateTime.of(2021, 1, 20, 12, 00))
                                .endDateTime(LocalDateTime.of(2021, 1, 21, 12, 00))
                                .manMonth(10)
                                .cost(100)
                                .build();

        ResultActions resultActions = mockMvc.perform(post("/api/v1/project")
//                                                    .header(HttpHeaders.AUTHORIZATION, "")
                                                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                    .accept(MediaType.APPLICATION_JSON_VALUE)
                                                    .content(objectMapper.writeValueAsString(projectDto))
                                        )
                                            .andDo(print()) // 요청과 응답 콘솔에서 확인
                                            .andExpect(status().isCreated())
                                            .andExpect(header().exists(HttpHeaders.LOCATION))
                                            .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                                            .andExpect(jsonPath("id").exists())     // id 값있는지 확인
                                            .andExpect(jsonPath("name").value("프로젝트"))   // name 값 있는지 확인

                ;

    }

    @Test
    @TestDescription("프로젝트 시작일시 시작일시가 같은 경우 프로젝트 생성 에러 테스트")
    public void createProject_Bad_Request_WrongDateTime() throws Exception{
        ProjectDto projectDto = ProjectDto.builder()
                .name("프로젝트")
                .employeeId("1234")
                .employeeNm("김철수")
                .description("프로젝트 입니다.")
                .beginDateTime(LocalDateTime.of(2021, 1, 20, 12, 00))
                .endDateTime(LocalDateTime.of(2021, 1, 20, 12, 00))
                .manMonth(10)
                .cost(100)
                .build();

        ResultActions resultActions = mockMvc.perform(post("/api/v1/project")
//                                                    .header(HttpHeaders.AUTHORIZATION, "")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(projectDto))
        )
                .andDo(print()) // 요청과 응답 콘솔에서 확인
                .andExpect(status().isBadRequest()) // BadRequest 인지 확인
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("[0].objectName").exists())
                .andExpect(jsonPath("[0].defaultMessage").exists())
                .andExpect(jsonPath("[0].code").exists())


                ;

    }
}