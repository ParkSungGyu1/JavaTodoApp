package com.example.todo.domain.todo.service;

import com.example.todo.domain.todo.dto.TodoRequestDto;
import com.example.todo.domain.todo.dto.TodoResponseDto;
import com.example.todo.domain.todo.entity.Todo;
import com.example.todo.domain.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoResponseDto createTodo(TodoRequestDto requestDto) {
        Todo todo = todoRepository.save(Todo.from(requestDto));
        return todo.to();
    }

    public List<TodoResponseDto> getTodoList() {
        return todoRepository.findAll();
    }

    public TodoResponseDto getTodo(Long todoId) {
        return todoRepository.findById(todoId).to();
    }

    public void updateTodo(Long todoId, TodoRequestDto requestDto) {
        Todo todo = todoRepository.findById(todoId);
        if(todo == null){
            throw new IllegalArgumentException("해당 id를 찾을 수 없음");
        }

        if(!Objects.equals(todo.getPassword(), requestDto.getPassword())){
            throw new IllegalArgumentException("패스워드가 틀립니다.");
        }
        todoRepository.update(todoId, requestDto);
    }

    public void deleteTodo(Long todoId, TodoRequestDto requestDto) {
        Todo todo = todoRepository.findById(todoId);
        if(todo == null){
            throw new IllegalArgumentException("해당 id를 찾을 수 없음");
        }

        if(!Objects.equals(todo.getPassword(), requestDto.getPassword())){
            throw new IllegalArgumentException("패스워드가 틀립니다.");
        }
        todoRepository.deleteById(todoId);
    }
}
