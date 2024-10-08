package br.com.alura.ProjetoAlura.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.sql.DataSource;
import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DataSource dataSource;

    @Test
    void newStudent__should_return_bad_request_when_password_is_blank() throws Exception {
        NewStudentUserDTO newStudentUserDTO = new NewStudentUserDTO();
        newStudentUserDTO.setEmail("test@test.com");
        newStudentUserDTO.setName("Charles");
        newStudentUserDTO.setPassword("");

        mockMvc.perform(post("/user/newStudent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newStudentUserDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field").value("password"))
                .andExpect(jsonPath("$[0].message").value("a senha deve conter entre 8 e 16 caracteres"));
    }

    @Test
    void newInstructor__should_return_bad_request_when_password_is_blank() throws Exception {
        NewInstructorUserDTO newInstr = new NewInstructorUserDTO();
        newInstr.setEmail("test@test.com");
        newInstr.setName("Charles");
        newInstr.setPassword("");

        mockMvc.perform(post("/user/newInstructor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newInstr)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field").value("password"))
                .andExpect(jsonPath("$[0].message").value("a senha deve conter entre 8 e 16 caracteres"));
    }

    @Test
    void newStudent__should_return_bad_request_when_email_is_blank() throws Exception {
        NewStudentUserDTO newStudentUserDTO = new NewStudentUserDTO();
        newStudentUserDTO.setEmail("");
        newStudentUserDTO.setName("Charles");
        newStudentUserDTO.setPassword("mudar12345");

        mockMvc.perform(post("/user/newStudent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newStudentUserDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field").value("email"))
                .andExpect(jsonPath("$[0].message").value("o campo email nao pode estar vazio"));
    }

    @Test
    void newInstructor__should_return_bad_request_when_email_is_blank() throws Exception {
        NewInstructorUserDTO newInstr = new NewInstructorUserDTO();
        newInstr.setEmail("");
        newInstr.setName("Charles");
        newInstr.setPassword("mudar12345");

        mockMvc.perform(post("/user/newInstructor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newInstr)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field").value("email"))
                .andExpect(jsonPath("$[0].message").value("o campo email nao pode estar vazio"));
    }

    @Test
    void newStudent__should_return_bad_request_when_email_is_invalid() throws Exception {
        NewStudentUserDTO newStudentUserDTO = new NewStudentUserDTO();
        newStudentUserDTO.setEmail("Charles");
        newStudentUserDTO.setName("Charles");
        newStudentUserDTO.setPassword("mudar12345");

        mockMvc.perform(post("/user/newStudent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newStudentUserDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field").value("email"))
                .andExpect(jsonPath("$[0].message").value("o email nao e valido"));
    }

    @Test
    void newInstructor__should_return_bad_request_when_email_is_invalid() throws Exception {
        NewInstructorUserDTO newInstr = new NewInstructorUserDTO();
        newInstr.setEmail("Charles");
        newInstr.setName("Charles");
        newInstr.setPassword("mudar12345");

        mockMvc.perform(post("/user/newStudent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newInstr)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field").value("email"))
                .andExpect(jsonPath("$[0].message").value("o email nao e valido"));
    }

    @Test
    void newStudent__should_return_bad_request_when_email_already_exists() throws Exception {
        NewStudentUserDTO newStudentUserDTO = new NewStudentUserDTO();
        newStudentUserDTO.setEmail("charles@alura.com.br");
        newStudentUserDTO.setName("Charles");
        newStudentUserDTO.setPassword("mudar12345");

        when(userRepository.existsByEmail(newStudentUserDTO.getEmail())).thenReturn(true);

        mockMvc.perform(post("/user/newStudent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newStudentUserDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.field").value("email"))
                .andExpect(jsonPath("$.message").value("Email já cadastrado no sistema"));
    }

    @Test
    void newInstructor__should_return_bad_request_when_email_already_exists() throws Exception {
        NewInstructorUserDTO newInstr = new NewInstructorUserDTO();
        newInstr.setEmail("charles@alura.com.br");
        newInstr.setName("Charles");
        newInstr.setPassword("mudar12345");

        when(userRepository.existsByEmail(newInstr.getEmail())).thenReturn(true);

        mockMvc.perform(post("/user/newInstructor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newInstr)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.field").value("email"))
                .andExpect(jsonPath("$.message").value("Email já cadastrado no sistema"));
    }

    @Test
    void newStudent__should_return_created_when_user_request_is_valid() throws Exception {
        NewStudentUserDTO newStudentUserDTO = new NewStudentUserDTO();
        newStudentUserDTO.setEmail("charles@alura.com.br");
        newStudentUserDTO.setName("Charles");
        newStudentUserDTO.setPassword("mudar12345");

        when(userRepository.existsByEmail(newStudentUserDTO.getEmail())).thenReturn(false);

        mockMvc.perform(post("/user/newStudent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newStudentUserDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void newInstructor__should_return_created_when_user_request_is_valid() throws Exception {
        NewInstructorUserDTO newInstr = new NewInstructorUserDTO();
        newInstr.setEmail("charles@alura.com.br");
        newInstr.setName("Charles");
        newInstr.setPassword("mudar12345");

        when(userRepository.existsByEmail(newInstr.getEmail())).thenReturn(false);

        mockMvc.perform(post("/user/newStudent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newInstr)))
                .andExpect(status().isCreated());
    }

    @Test
    void listAllUsers__should_list_all_users() throws Exception {
        User user1 = new User("User 1", "user1@test.com", Role.STUDENT,"mudar123");
        User user2 = new User("User 2", "user2@test.com",Role.STUDENT,"mudar123");

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        mockMvc.perform(get("/user/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("User 1"))
                .andExpect(jsonPath("$[1].name").value("User 2"));
    }
}