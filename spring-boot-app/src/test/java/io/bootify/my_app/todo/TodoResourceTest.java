package io.bootify.my_app.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.bootify.my_app.config.BaseIT;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;


public class TodoResourceTest extends BaseIT {

    @Test
    @Sql("/data/todoData.sql")
    void getAllTodos_success() throws Exception {
        mockMvc.perform(get("/api/todos")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.content[0].id").value(((long)1000)));
    }

    @Test
    @Sql("/data/todoData.sql")
    void getAllTodos_filtered() throws Exception {
        mockMvc.perform(get("/api/todos?filter=1001")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.content[0].id").value(((long)1001)));
    }

    @Test
    @Sql("/data/todoData.sql")
    void getTodo_success() throws Exception {
        mockMvc.perform(get("/api/todos/1000")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.item").value("Lorem ipsum dolor."));
    }

    @Test
    void getTodo_notFound() throws Exception {
        mockMvc.perform(get("/api/todos/1666")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.exception").value("NotFoundException"));
    }

    @Test
    void createTodo_success() throws Exception {
        mockMvc.perform(post("/api/todos")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(readResource("/requests/todoDTORequest.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        assertEquals(1, todoRepository.count());
    }

    @Test
    void createTodo_missingField() throws Exception {
        mockMvc.perform(post("/api/todos")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(readResource("/requests/todoDTORequest_missingField.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.exception").value("MethodArgumentNotValidException"))
                .andExpect(jsonPath("$.fieldErrors[0].field").value("item"));
    }

    @Test
    @Sql("/data/todoData.sql")
    void updateTodo_success() throws Exception {
        mockMvc.perform(put("/api/todos/1000")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(readResource("/requests/todoDTORequest.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        assertEquals("Ut wisi enim.", todoRepository.findById(((long)1000)).get().getItem());
        assertEquals(2, todoRepository.count());
    }

    @Test
    @Sql("/data/todoData.sql")
    void deleteTodo_success() throws Exception {
        mockMvc.perform(delete("/api/todos/1000")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        assertEquals(1, todoRepository.count());
    }

}
