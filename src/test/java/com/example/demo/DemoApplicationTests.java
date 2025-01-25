// src/main/java/com/example/demo/model/User.java
package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String username;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	// Additional fields if needed
}

// src/main/java/com/example/demo/repository/UserRepository.java
package com.example.demo.repository;

		import com.example.demo.model.User;
		import org.springframework.data.jpa.repository.JpaRepository;
		import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
}

// src/main/java/com/example/demo/service/UserService.java
package com.example.demo.service;

		import com.example.demo.model.User;
		import com.example.demo.repository.UserRepository;
		import org.springframework.beans.factory.annotation.Autowired;
		import org.springframework.stereotype.Service;

		import java.util.Optional;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	public User createUser(User user) {
		// Hash password and save user logic here
		return userRepository.save(user);
	}

	public Optional<User> authenticateUser(String email, String password) {
		// Custom authentication logic
		return userRepository.findByEmail(email)
				.filter(user -> user.getPassword().equals(password)); // Replace with hashed password check
	}
}
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;
}
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(String username, String email, String password) {
        User user = new User(username, email, password);
        return userRepository.save(user);
    }

    public User authenticate(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(user -> user.getPassword().equals(password))
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
    }
}