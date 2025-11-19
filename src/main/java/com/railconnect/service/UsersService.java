package com.railconnect.service;

import java.util.List;

import com.railconnect.entities.Users;

public interface UsersService {
	public Users ragister(Users user);

	public String loginuser(Users user);

	public List<Users> getallUser();

	public Users updateUser(int id, Users updatedUser);

	public String deleteUser(int id);

	public Users getUserById(int id);
}
