package com.example.todo.domain.todo.repository;


import com.example.todo.domain.todo.dto.TodoRequestDto;
import com.example.todo.domain.todo.dto.TodoResponseDto;
import com.example.todo.domain.todo.entity.Todo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TodoRepository {

    private final JdbcTemplate jdbcTemplate;

    public Todo save(Todo todo) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO todo (username, title,description,password,created_at) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(sql,
                            Statement.RETURN_GENERATED_KEYS);

                    preparedStatement.setString(1, todo.getUsername());
                    preparedStatement.setString(2, todo.getTitle());
                    preparedStatement.setString(3, todo.getDescription());
                    preparedStatement.setString(4, todo.getPassword());
                    preparedStatement.setString(5, todo.getCreatedAt());
                    return preparedStatement;
                },
                keyHolder);

        // DB Insert 후 받아온 기본키 확인
        Long id = keyHolder.getKey().longValue();
        todo.setId(id);

        return todo;
    }

    public List<TodoResponseDto>  findAll() {
        String sql = "SELECT * FROM todo";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            Long id = rs.getLong("id");
            String username = rs.getString("username");
            String title = rs.getString("title");
            String description = rs.getString("description");
            String createdAt = rs.getString("created_at");
            String updatedAt = rs.getString("updated_at");
            return new TodoResponseDto(
                    id,
                    username,
                    title,
                    description,
                    createdAt,
                    updatedAt
            );
        });
    }

    public Todo findById(Long todoId) {
        String sql = "SELECT * FROM todo WHERE id = ?";

        return jdbcTemplate.query(sql, rs -> {
            if (rs.next()) {
                return Todo.from(rs);
            } else {
                return null;
            }
        }, todoId);
    }

    public void update(Long id, TodoRequestDto requestDto) {
        String sql = "UPDATE todo SET description = ?, username = ?, updated_at = ? WHERE id = ?";
        jdbcTemplate.update(sql, requestDto.getDescription(), requestDto.getUsername(), requestDto.getUpdatedAt(), id);
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM todo WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
