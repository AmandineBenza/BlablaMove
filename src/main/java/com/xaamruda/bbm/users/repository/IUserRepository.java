package com.xaamruda.bbm.users.repository;

import org.springframework.data.repository.CrudRepository;

import com.xaamruda.bbm.users.model.User;

public interface IUserRepository extends CrudRepository<User, String> {

}
