package com.example.todo.domain.todo.controller;

import com.example.todo.domain.todo.dto.TodoRequestDto;
import com.example.todo.domain.todo.dto.TodoResponseDto;
import com.example.todo.domain.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todo")
public class TodoController {

    private final TodoService todoService;

    //일정 생성하기
    @PostMapping()
    public ResponseEntity<TodoResponseDto> createTodo(@RequestBody TodoRequestDto requestDto){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(todoService.createTodo(requestDto));
    }

    //전체 일정 조회
    @GetMapping()
    public ResponseEntity<List<TodoResponseDto>> getTodoList(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(todoService.getTodoList());
    }

    //선택 일정 조회
    @GetMapping("/{todoId}")
    public ResponseEntity<TodoResponseDto>getTodo(@PathVariable Long todoId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(todoService.getTodo(todoId));
    }

    //선택 일정 수정
    @PutMapping("/{todoId}")
    public ResponseEntity<Void> updateTodo(
            @PathVariable Long todoId,
            @RequestBody TodoRequestDto requestDto
    ){
        todoService.updateTodo(todoId, requestDto);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();

    }

    //선택 일정 삭제
    @DeleteMapping("/{todoId}")
    public ResponseEntity<Void> deleteTodo(
            @PathVariable Long todoId,
            @RequestBody TodoRequestDto requestDto
    ){
        todoService.deleteTodo(todoId, requestDto);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();

    }
}
