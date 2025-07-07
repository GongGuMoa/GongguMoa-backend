package com.gonggumoa.gonggumoa.domain.user.repository;

import com.gonggumoa.gonggumoa.domain.user.domain.User;
import com.gonggumoa.gonggumoa.domain.user.exception.handler.UserControllerAdvice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRespository extends JpaRepository<User,Long> {
}
