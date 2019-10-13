package org.vaadin.cdidemo.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.slf4j.Logger;

// We need only one instance of this list so thus we make
// it application scoped in real application we would persist
// user date in database and store password using hash
@ApplicationScoped
public class UserListServiceImpl implements UserListService, Serializable {

	@Inject
	Logger logger;
	
	private List<User> list = new ArrayList<>();

	public UserListServiceImpl() {
		list.add(new User("Admin","admin",true));
		list.add(new User("Demo","demo",false));
		list.add(new User("User","user",false));
	}
	
	@Override
	public Optional<User> getUser(String username) {
		User found = null;
		for (User user : list) {
			if (user.getName().equals(username)) {
				found = user;
			}
		}
		return Optional.ofNullable(found);
	}
	
	@Override
	public Stream<User> getUsers() {
		// Simulate slow database query with 1.5s sleep
		long m = System.currentTimeMillis();
		try {
			Thread.sleep(1500);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
		logger.info("Fetched users in {} ms",System.currentTimeMillis()-m);
		return list.stream();
	}

	@Override
	public Stream<User> getAdmins() {
		return list.stream().filter(user -> user.isAdmin());
	}

}
