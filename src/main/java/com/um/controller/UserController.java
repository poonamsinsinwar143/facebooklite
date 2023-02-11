package com.um.controller;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import com.um.dto.CommentsDto;
import com.um.dto.FriendDto;
import com.um.dto.LikeDto;
import com.um.dto.PasswordDto;
import com.um.dto.PostDto;
import com.um.dto.UserDto;
import com.um.service.UserService;

@RestController
@RequestMapping(path = "rest/v1")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping(path = "/user", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createUser(@RequestBody UserDto userDto)
			throws InvalidKeySpecException, NoSuchAlgorithmException {
		String responseMsg = userService.createUser(userDto);
		if (responseMsg == null) {
			return new ResponseEntity<>("User successfully created!!!!", HttpStatus.CREATED);
		}
		return new ResponseEntity<>(responseMsg, HttpStatus.BAD_REQUEST);
	}

	@DeleteMapping(path = "/user/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
		userService.deleteUser(userId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(path = "/user/{userId}")
	public ResponseEntity<?> getUser(@PathVariable Long userId) {
		UserDto userDto = userService.getUser(userId);
		return new ResponseEntity<>(userDto, HttpStatus.OK);
	}

	@PutMapping(path = "/user/{userId}")
	public ResponseEntity<?> updateUser(@RequestBody UserDto userDto, @PathVariable Long userId) {
		userService.updateUser(userDto, userId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping(path = "/user/login", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> loginUser(@RequestBody UserDto userDto)
			throws InvalidKeySpecException, NoSuchAlgorithmException {
		UserDto response = userService.loginUser(userDto);
		if (response == null) {
			return new ResponseEntity<>("Either username or password is wrong!", HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(path = "/user/{userId}/logout", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> logoutUser(@PathVariable Long userId)
			throws InvalidKeySpecException, NoSuchAlgorithmException {
		userService.logoutUser(userId);
		return new ResponseEntity<>("Logout successful", HttpStatus.OK);
	}

	@PostMapping(path = "/user/change_password", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> changePassword(@RequestBody PasswordDto passwordDto)
			throws InvalidKeySpecException, NoSuchAlgorithmException {
		userService.changePassword(passwordDto);
		return new ResponseEntity<>(HttpStatus.OK);

	}

//	API for Post.

	@PostMapping(path = "/user/{userId}/post", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createPost(@PathVariable Long userId, @RequestBody PostDto postDto) {
		userService.createPost(userId, postDto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PutMapping(path = "/user/{userId}/post/{postId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updatePost(@RequestBody PostDto postDto, @PathVariable Long postId,
			@PathVariable Long userId) {
		userService.updatePost(postDto, postId, userId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(path = "/user/{userId}/post/{postId}")
	public ResponseEntity<?> getPost(@PathVariable Long userId, @PathVariable Long postId) {
		PostDto postDto = userService.getPost(userId, postId);
		return new ResponseEntity<>(postDto, HttpStatus.OK);
	}

	@DeleteMapping(path = "/user/{userId}/post/{postId}")
	public ResponseEntity<?> deletePost(@PathVariable Long userId, @PathVariable Long postId) {
		userService.deletePost(userId, postId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(path = "/user/{userId}/post")
	public ResponseEntity<?> getAllPost(@PathVariable Long userId) {
		List<PostDto> postDto = userService.getAllPost(userId);
		return new ResponseEntity<>(postDto, HttpStatus.OK);
	}

//	API for Comments.

	@PostMapping(path = "/user/{userId}/post/{postId}/comment", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createComment(@RequestBody CommentsDto commentsDto, @PathVariable Long postId,
			@PathVariable Long userId) {
		userService.createComment(commentsDto, postId, userId);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping(path = "/user/{userId}/post/{postId}/comment")
	public ResponseEntity<?> getComment(@PathVariable Long userId, @PathVariable Long postId) {
		List<CommentsDto> commentDto = userService.getComment(userId, postId);
		return new ResponseEntity<>(commentDto, HttpStatus.OK);
	}

	@DeleteMapping(path = "/user/{userId}/post/{postId}/comment/{commentId}")
	public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
		userService.deleteComment(commentId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping(path = "/user/{userId}/post/{postId}/comment/{commentId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateComment(@RequestBody CommentsDto commentsDto, @PathVariable Long userId,
			@PathVariable Long postId, @PathVariable Long commentId) {
		userService.updateComment(commentsDto, userId, postId, commentId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
//    API for Likes
	
	@PostMapping(path = "/user/{userId}/post/{postId}/like")
	public ResponseEntity<?> createLike(@RequestBody LikeDto likeDto, @PathVariable Long userId,
			@PathVariable Long postId) {
		userService.createLike(likeDto, userId, postId);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@DeleteMapping(path = "/user/{userId}/post/{postId}/like/{likeId}")
	public ResponseEntity<?> deleteLike(@PathVariable Long likeId) {
		userService.deleteLike(likeId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(path = "/user/{userId}/post/{postId}/like")
	public ResponseEntity<?> getLikes(@PathVariable Long userId, @PathVariable Long postId) {
		List<LikeDto> likeDtos = userService.getLikes(userId, postId);
		return new ResponseEntity<>(likeDtos, HttpStatus.OK);
	}
	
	
//	API for friend.
	
	
	@PostMapping(path = "/user/{userId}/friend",consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addFriend(@RequestBody FriendDto friendDto,@PathVariable Long userId){
		userService.createFriend(friendDto,userId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(path = "/user/{userId}/friend")
	public ResponseEntity<?> getFriend(@PathVariable Long userId){
		List<UserDto> userDtos = userService.getFriend(userId);
		return new ResponseEntity<>(userDtos,HttpStatus.OK);
	}

}
