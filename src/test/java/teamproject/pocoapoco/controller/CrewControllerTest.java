package teamproject.pocoapoco.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import teamproject.pocoapoco.controller.main.api.CrewController;
import teamproject.pocoapoco.domain.dto.crew.CrewDetailResponse;
import teamproject.pocoapoco.domain.dto.crew.CrewRequest;
import teamproject.pocoapoco.domain.dto.crew.CrewResponse;
import teamproject.pocoapoco.domain.dto.crew.CrewStrictRequest;
import teamproject.pocoapoco.domain.entity.Crew;
import teamproject.pocoapoco.exception.AppException;
import teamproject.pocoapoco.exception.ErrorCode;
import teamproject.pocoapoco.fixture.CrewEntityFixture;
import teamproject.pocoapoco.service.CrewService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CrewController.class)
class CrewControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CrewService crewService;

    Crew crew = CrewEntityFixture.get(1L);
    CrewRequest request = new CrewRequest(crew.getStrict(), crew.getTitle(), crew.getContent(), crew.getCrewLimit());
    CrewDetailResponse crewDetailResponse = CrewDetailResponse.of(CrewEntityFixture.get(crew.getId()));


    @Nested
    @DisplayName("?????? ????????? ??????")
    class AddCrew {

        @Test
        @WithMockUser
        @DisplayName("?????? ????????? ?????? ??????")
        void addCrew() throws Exception {

            //given
            given(crewService.addCrew(any(), any()))
                    .willReturn(new CrewResponse("Crew ?????? ??????", crew.getId()));

            //when
            mockMvc.perform(post("/api/v1/crews")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsBytes(request)))
                    //then
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.resultCode").value("SUCCESS"))
                    .andExpect(jsonPath("$.result.message").value("Crew ?????? ??????"))
                    .andExpect(jsonPath("$.result.crewId").value(crew.getId()))
                    .andDo(print());
        }

        @Test
        @WithMockUser
        @DisplayName("?????? ????????? ?????? ??????1 :  ?????? ????????? ??????")
        void addCrew2() throws Exception {

            //given
            given(crewService.addCrew(any(), any()))
                    .willThrow(new AppException(ErrorCode.USERID_NOT_FOUND, ErrorCode.USERID_NOT_FOUND.getMessage()));

            //when
            mockMvc.perform(post("/api/v1/crews")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsBytes(request)))
                    //then
                    .andExpect(status().isNotFound())
                    .andExpect(content().string(ErrorCode.USERID_NOT_FOUND.name() + " " + ErrorCode.USERID_NOT_FOUND.getMessage()))
                    .andDo(print());
        }

        @Test
        @WithMockUser
        @DisplayName("?????? ????????? ?????? ??????2 : ?????? ????????? ??????")
        void addCrew3() throws Exception {

            //given
            given(crewService.addCrew(any(), any()))
                    .willThrow(new AppException(ErrorCode.CREW_NOT_FOUND, ErrorCode.CREW_NOT_FOUND.getMessage()));

            //when
            mockMvc.perform(post("/api/v1/crews")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsBytes(request)))
                    //then
                    .andExpect(status().isNotFound())
                    .andExpect(content().string(ErrorCode.CREW_NOT_FOUND.name() + " " + ErrorCode.CREW_NOT_FOUND.getMessage()))
                    .andDo(print());
        }
    }


    @Nested
    @DisplayName("?????? ????????? ??????")
    class ModifyCrew {

        @Test
        @WithMockUser
        @DisplayName("?????? ????????? ?????? ??????")
        void modifyCrew1() throws Exception {

            //given
            given(crewService.modifyCrew(any(), any(), any()))
                    .willReturn(new CrewResponse("Crew ?????? ??????", crew.getId()));

            //when
            mockMvc.perform(post("/api/v1/crews/1")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsBytes(request)))
                    //then
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.resultCode").value("SUCCESS"))
                    .andExpect(jsonPath("$.result.message").value("Crew ?????? ??????"))
                    .andExpect(jsonPath("$.result.crewId").value(crew.getId()))
                    .andDo(print());
        }

        @Test
        @WithMockUser
        @DisplayName("?????? ????????? ?????? ??????1 :  ?????? ????????? ??????")
        void modifyCrew2() throws Exception {

            //given
            given(crewService.modifyCrew(any(), any(), any()))
                    .willThrow(new AppException(ErrorCode.USERID_NOT_FOUND, ErrorCode.USERID_NOT_FOUND.getMessage()));

            //when
            mockMvc.perform(post("/api/v1/crews/1")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsBytes(request)))
                    //then
                    .andExpect(status().isNotFound())
                    .andExpect(content().string(ErrorCode.USERID_NOT_FOUND.name() + " " + ErrorCode.USERID_NOT_FOUND.getMessage()))
                    .andDo(print());
        }

        @Test
        @WithMockUser
        @DisplayName("?????? ????????? ?????? ??????2 : ?????? ????????? ??????")
        void modifyCrew3() throws Exception {

            //given
            given(crewService.modifyCrew(any(), any(), any()))
                    .willThrow(new AppException(ErrorCode.CREW_NOT_FOUND, ErrorCode.CREW_NOT_FOUND.getMessage()));

            //when
            mockMvc.perform(post("/api/v1/crews/1")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsBytes(request)))
                    //then
                    .andExpect(status().isNotFound())
                    .andExpect(content().string(ErrorCode.CREW_NOT_FOUND.name() + " " + ErrorCode.CREW_NOT_FOUND.getMessage()))
                    .andDo(print());

        }

        @Test
        @WithMockUser
        @DisplayName("?????? ????????? ?????? ??????3 : ?????? ??????")
        void modifyCrew4() throws Exception {

            //given
            given(crewService.modifyCrew(any(), any(), any()))
                    .willThrow(new AppException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage()));

            //when
            mockMvc.perform(post("/api/v1/crews/1")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsBytes(request)))
                    //then
                    .andExpect(status().isUnauthorized())
                    .andExpect(content().string(ErrorCode.INVALID_PERMISSION.name() + " " + ErrorCode.INVALID_PERMISSION.getMessage()))
                    .andDo(print());
        }
    }


    @Nested
    @DisplayName("?????? ????????? ??????")
    class DeleteCrew {

        @Test
        @WithMockUser
        @DisplayName("?????? ????????? ?????? ??????")
        void deleteCrew1() throws Exception {

            //given
            given(crewService.deleteCrew(any(), any()))
                    .willReturn(new CrewResponse("Crew ?????? ??????", crew.getId()));

            //when
            mockMvc.perform(delete("/api/v1/crews/1")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsBytes(request)))
                    //then
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.resultCode").value("SUCCESS"))
                    .andExpect(jsonPath("$.result.message").value("Crew ?????? ??????"))
                    .andExpect(jsonPath("$.result.crewId").value(crew.getId()))
                    .andDo(print());
        }

        @Test
        @WithMockUser
        @DisplayName("?????? ????????? ?????? ??????1 :  ?????? ????????? ??????")
        void deleteCrew2() throws Exception {

            //given
            given(crewService.deleteCrew(any(), any()))
                    .willThrow(new AppException(ErrorCode.USERID_NOT_FOUND, ErrorCode.USERID_NOT_FOUND.getMessage()));

            //when
            mockMvc.perform(delete("/api/v1/crews/1")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsBytes(request)))
                    //then
                    .andExpect(status().isNotFound())
                    .andExpect(content().string(ErrorCode.USERID_NOT_FOUND.name() + " " + ErrorCode.USERID_NOT_FOUND.getMessage()))
                    .andDo(print());
        }

        @Test
        @WithMockUser
        @DisplayName("?????? ????????? ?????? ??????2 : ?????? ????????? ??????")
        void deleteCrew3() throws Exception {

            //given
            given(crewService.deleteCrew(any(), any()))
                    .willThrow(new AppException(ErrorCode.CREW_NOT_FOUND, ErrorCode.CREW_NOT_FOUND.getMessage()));

            //when
            mockMvc.perform(delete("/api/v1/crews/1")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsBytes(request)))
                    //then
                    .andExpect(status().isNotFound())
                    .andExpect(content().string(ErrorCode.CREW_NOT_FOUND.name() + " " + ErrorCode.CREW_NOT_FOUND.getMessage()))
                    .andDo(print());

        }

        @Test
        @WithMockUser
        @DisplayName("?????? ????????? ?????? ??????3 : ?????? ??????")
        void deleteCrew4() throws Exception {

            //given
            given(crewService.deleteCrew(any(), any()))
                    .willThrow(new AppException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage()));

            //when
            mockMvc.perform(delete("/api/v1/crews/1")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsBytes(request)))
                    //then
                    .andExpect(status().isUnauthorized())
                    .andExpect(content().string(ErrorCode.INVALID_PERMISSION.name() + " " + ErrorCode.INVALID_PERMISSION.getMessage()))
                    .andDo(print());
        }
    }

    @Nested
    @DisplayName("?????? ????????? ????????????")
    class DetailCrew {

        @Test
        @WithMockUser
        @DisplayName("?????? ????????? ???????????? ??????")
        void detailCrew1() throws Exception {

            //given
            given(crewService.detailCrew(any()))
                    .willReturn(crewDetailResponse);

            // when
            mockMvc.perform(get("/api/v1/crews/1")
                            .with(csrf()))
                    //then
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.resultCode").value("SUCCESS"))
                    .andExpect(jsonPath("$.result.strict").value(crew.getStrict()))
                    .andExpect(jsonPath("$.result.title").value(crew.getTitle()))
                    .andExpect(jsonPath("$.result.content").value(crew.getContent()))
                    .andDo(print());
        }

        @Test
        @WithMockUser
        @DisplayName("?????? ????????? ???????????? ??????1 :  ?????? ????????? ??????")
        void detailCrew2() throws Exception {

            //given
            given(crewService.detailCrew(any()))
                    .willThrow(new AppException(ErrorCode.USERID_NOT_FOUND, ErrorCode.USERID_NOT_FOUND.getMessage()));

            // when
            mockMvc.perform(get("/api/v1/crews/1")
                            .with(csrf()))
                    //then
                    .andExpect(status().isNotFound())
                    .andExpect(content().string(ErrorCode.USERID_NOT_FOUND.name() + " " + ErrorCode.USERID_NOT_FOUND.getMessage()))
                    .andDo(print());
        }

        @Test
        @WithMockUser
        @DisplayName("?????? ????????? ???????????? ??????2 : ?????? ????????? ??????")
        void detailCrew3() throws Exception {

            //given
            given(crewService.detailCrew(any()))
                    .willThrow(new AppException(ErrorCode.CREW_NOT_FOUND, ErrorCode.CREW_NOT_FOUND.getMessage()));

            // when
            mockMvc.perform(get("/api/v1/crews/1")
                            .with(csrf()))
                    //then
                    .andExpect(status().isNotFound())
                    .andExpect(content().string(ErrorCode.CREW_NOT_FOUND.name() + " " + ErrorCode.CREW_NOT_FOUND.getMessage()))
                    .andDo(print());
        }
    }


    @Nested
    @DisplayName("?????? ????????? ????????????")
    class FindAllCrews {

        @Test
        @WithMockUser
        @DisplayName("?????? ????????? ???????????? ??????")
        void findAllCrews1() throws Exception {

            Page<Crew> crews = new PageImpl<>(List.of(crew));
            Page<CrewDetailResponse> crewDetailResponses = crews.map(CrewDetailResponse::of);

            //given
            given(crewService.findAllCrews(any()))
                    .willReturn(crewDetailResponses);

            // when
            mockMvc.perform(get("/api/v1/crews")
                            .with(csrf()))
                    //then
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.resultCode").value("SUCCESS"))
                    .andExpect(jsonPath("$.['result']['content'][0]['strict']").value(crew.getStrict()))
                    .andExpect(jsonPath("$.['result']['content'][0]['title']").value(crew.getTitle()))
                    .andExpect(jsonPath("$.['result']['content'][0]['content']").value(crew.getContent()))
                    .andExpect(jsonPath("$.['result']['content'][0]['crewLimit']").value(crew.getCrewLimit()))
                    .andDo(print());
        }
    }

    @Nested
    @DisplayName("?????? ????????? ?????? ?????? ??????")
    class FindallCrewWithStrict {

        @Test
        @WithMockUser
        @DisplayName("?????? ????????? ?????? ?????? ?????? ??????")
        void findAllCrewWithStrict1() throws Exception {

            Page<Crew> crews = new PageImpl<>(List.of(crew));
            Page<CrewDetailResponse> crewDetailResponses = crews.map(CrewDetailResponse::of);

            //given
            given(crewService.findAllCrewsByStrict(any(), any()))
                    .willReturn(crewDetailResponses);

            // when
            mockMvc.perform(post("/api/v1/crews/strict")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsBytes(new CrewStrictRequest("??????"))))
                    //then
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.resultCode").value("SUCCESS"))
                    .andExpect(jsonPath("$.['result']['content'][0]['strict']").value(crew.getStrict()))
                    .andExpect(jsonPath("$.['result']['content'][0]['title']").value(crew.getTitle()))
                    .andExpect(jsonPath("$.['result']['content'][0]['content']").value(crew.getContent()))
                    .andExpect(jsonPath("$.['result']['content'][0]['crewLimit']").value(crew.getCrewLimit()))
                    .andDo(print());
        }
    }

}