package org.vaadin.cdidemo.data;

import java.util.Optional;
import java.util.stream.Stream;

public interface UserListService {

	Optional<User> getUser(String username);

	Stream<User> getUsers();

	Stream<User> getAdmins();
	
}
