package com.example.todo.domain.member.repository;

import com.example.todo.domain.member.entity.Member;
import com.example.todo.domain.todo.entity.Todo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final JdbcTemplate jdbcTemplate;

    public Member findById(Long id) {
        String sql = "SELECT * FROM member WHERE id = ?";

        return jdbcTemplate.query(sql, rs -> {
            if (rs.next()) {
                return Member.from(rs);
            } else {
                return null;
            }
        }, id);
    }
}
