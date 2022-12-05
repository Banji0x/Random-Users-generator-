package com.generator.randomusersgenerator.repository;


import com.generator.randomusersgenerator.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends PagingAndSortingRepository<User,Long> {
}
