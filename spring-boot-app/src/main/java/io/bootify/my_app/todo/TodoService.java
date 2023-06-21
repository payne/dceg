package io.bootify.my_app.todo;

import io.bootify.my_app.model.SimplePage;
import io.bootify.my_app.util.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(final TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public SimplePage<TodoDTO> findAll(final String filter, final Pageable pageable) {
        Page<Todo> page;
        if (filter != null) {
            Long longFilter = null;
            try {
                longFilter = Long.parseLong(filter);
            } catch (final NumberFormatException numberFormatException) {
                // keep null - no parseable input
            }
            page = todoRepository.findAllById(longFilter, pageable);
        } else {
            page = todoRepository.findAll(pageable);
        }
        return new SimplePage<>(page.getContent()
                .stream()
                .map(todo -> mapToDTO(todo, new TodoDTO()))
                .toList(),
                page.getTotalElements(), pageable);
    }

    public TodoDTO get(final Long id) {
        return todoRepository.findById(id)
                .map(todo -> mapToDTO(todo, new TodoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final TodoDTO todoDTO) {
        final Todo todo = new Todo();
        mapToEntity(todoDTO, todo);
        return todoRepository.save(todo).getId();
    }

    public void update(final Long id, final TodoDTO todoDTO) {
        final Todo todo = todoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(todoDTO, todo);
        todoRepository.save(todo);
    }

    public void delete(final Long id) {
        todoRepository.deleteById(id);
    }

    private TodoDTO mapToDTO(final Todo todo, final TodoDTO todoDTO) {
        todoDTO.setId(todo.getId());
        todoDTO.setItem(todo.getItem());
        todoDTO.setCompleted(todo.getCompleted());
        return todoDTO;
    }

    private Todo mapToEntity(final TodoDTO todoDTO, final Todo todo) {
        todo.setItem(todoDTO.getItem());
        todo.setCompleted(todoDTO.getCompleted());
        return todo;
    }

    public boolean itemExists(final String item) {
        return todoRepository.existsByItemIgnoreCase(item);
    }

}
