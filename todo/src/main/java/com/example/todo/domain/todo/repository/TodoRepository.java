package com.example.todo.domain.todo.repository;


import com.example.todo.domain.todo.dto.TodoMemberDto;
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

        String sql = "INSERT INTO todo (member_id, title,description,password,created_at) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(sql,
                            Statement.RETURN_GENERATED_KEYS);

                    preparedStatement.setLong(1, todo.getMemberId());
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

    public List<TodoMemberDto>  findAll() {
        String sql = "SELECT * FROM todo\n" +
                     "left join member m on todo.member_id = m.id";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            Long id = rs.getLong("id");
            Long member_id = rs.getLong("member_id");
            String title = rs.getString("title");
            String username = rs.getString("username");
            String email = rs.getString("email");
            String description = rs.getString("description");
            String createdAt = rs.getString("created_at");
            String updatedAt = rs.getString("updated_at");
            return new TodoMemberDto(
                    id,
                    member_id,
                    title,
                    username,
                    email,
                    description,
                    createdAt,
                    updatedAt
            );
        });
    }

    /**
     * 페이지네이션 적용
     */
    public List<TodoMemberDto>  findAllWithPaging(int page, int size) {
        String sql = "SELECT * FROM todo " +
                "LEFT JOIN member m ON todo.member_id = m.id " +
                "ORDER BY todo.id LIMIT ? OFFSET ?";


        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            Long id = rs.getLong("id");
            Long member_id = rs.getLong("member_id");
            String title = rs.getString("title");
            String username = rs.getString("username");
            String email = rs.getString("email");
            String description = rs.getString("description");
            String createdAt = rs.getString("created_at");
            String updatedAt = rs.getString("updated_at");
            return new TodoMemberDto(
                    id,
                    member_id,
                    title,
                    username,
                    email,
                    description,
                    createdAt,
                    updatedAt
            );
        },size, page*size);
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
        String sql = "UPDATE todo SET description = ?, member_id = ?, updated_at = ? WHERE id = ?";
        jdbcTemplate.update(sql, requestDto.getDescription(), requestDto.getMemberId(), requestDto.getUpdatedAt(), id);
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM todo WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
