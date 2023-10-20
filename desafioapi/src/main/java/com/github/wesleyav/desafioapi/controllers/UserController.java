package com.github.wesleyav.desafioapi.controllers;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.github.wesleyav.desafioapi.entities.User;
import com.github.wesleyav.desafioapi.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/api/v1")
@Tag(name = "User")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Endpoint to list all users in a paginated from")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Operation successful") })
	public ResponseEntity<Page<User>> findAllPaged(@RequestParam(defaultValue = "0") Integer pageNumber,
			@RequestParam(defaultValue = "10") Integer pageSize) {
		Page<User> userPage = userService.findAllPaged(pageNumber, pageSize);
		return new ResponseEntity<>(userPage, HttpStatus.OK);
	}

	@GetMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Endpoint to search user by id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Operation successful") })
	public ResponseEntity<User> findById(@PathVariable Long id) {
		User user = userService.findById(id);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@PostMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Endpoint to create a user")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "User created successfully"),
			@ApiResponse(responseCode = "422", description = "Invalid user data provided") })

	public ResponseEntity<User> save(@RequestBody User obj) {
		User createdUser = userService.create(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdUser.getId())
				.toUri();
		return ResponseEntity.created(uri).body(createdUser);
	}

	@DeleteMapping("/users/{id}")
	@Operation(summary = "Endpoint to remove a user", description = "Delete an existing user based on its ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "User deleted successfully"),
			@ApiResponse(responseCode = "404", description = "User not found") })
	public ResponseEntity<Void> deleteById(@PathVariable Long id) {
		userService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/users/{id}")
	@Operation(summary = "Endpoint to update a user", description = "Update the data of an existing user based on its ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "User updated successfully"),
			@ApiResponse(responseCode = "404", description = "User not found"),
			@ApiResponse(responseCode = "422", description = "Invalid user data provided") })
	public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
		User updatedUser = userService.update(id, user);
		return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	}
}
