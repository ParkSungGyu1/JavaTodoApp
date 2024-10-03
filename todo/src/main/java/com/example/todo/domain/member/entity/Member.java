package com.example.todo.domain.member.entity;


import com.example.todo.domain.todo.entity.Todo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.ResultSet;
import java.sql.SQLException;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Member {
    @Setter
    private Long id;
    private String username;
    private String email;
    private String createdAt;
    private String updatedAt;


    public static Member from(ResultSet rs) throws SQLException {
        Member member = new Member();
        member.init(rs);
        return member;
    }

    private void init(ResultSet rs) throws SQLException {
        this.id = rs.getLong("id");
        this.username = rs.getString("username");
        this.email = rs.getString("email");
        this.createdAt = rs.getString("created_at");
        this.updatedAt = rs.getString("updated_at");
    }
}
