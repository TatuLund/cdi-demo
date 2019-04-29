package org.vaadin.cdidemo.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

// We need only one instance of this list so thus we make
// it application scoped in real application we would persist
// user date in database and store password using hash
@ApplicationScoped
public class UserListService {
	private List<User> list = new ArrayList<>();
	
	public UserListService() {
		list.add(new User("Admin","admin",true));
		list.add(new User("Demo","demo",false));
		list.add(new User("User","user",false));
	}
	
	public Optional<User> getUser(String username) {
		User found = null;
		for (User user : list) {
			if (user.getName().equals(username)) {
				found = user;
			}
		}
		return Optional.ofNullable(found);
	}
	
	public List<User> getUsers() {
		return list;
	}
}
