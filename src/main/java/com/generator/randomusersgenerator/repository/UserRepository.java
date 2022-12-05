package com.generator.randomusersgenerator.repository;


import com.paging.paging.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User,Long> {
}
