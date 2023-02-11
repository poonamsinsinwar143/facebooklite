package com.um.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.um.dto.CommentsDto;
import com.um.dto.FriendDto;
import com.um.dto.LikeDto;
import com.um.dto.PasswordDto;
import com.um.dto.PostDto;
import com.um.dto.UserDto;
import com.um.entity.Comments;
import com.um.entity.Friends;
import com.um.entity.Likes;
import com.um.entity.Post;
import com.um.entity.User;
import com.um.entity.UserToken;
import com.um.repository.CommentRepository;
import com.um.repository.FriendRepository;
import com.um.repository.LikeRepository;
import com.um.repository.PostRepository;
import com.um.repository.UserRepository;
import com.um.repository.UserTokenRepository;
import com.um.util.JwtTokenUtility;
import com.um.util.PasswordUtility;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private LikeRepository likeRepository;

	@Autowired
	private FriendRepository friendRepository;

	@Autowired
	private UserTokenRepository userTokenRepository;

	@Transactional
	public String createUser(UserDto userDto) throws InvalidKeySpecException, NoSuchAlgorithmException {

//		Not recommended
//		if(userDto.getPassword() == null || userDto.getPassword().equals("")) {
//			return "Password can't be empty";
//		}

		if (StringUtils.isNotBlank(userDto.getUserName())) {
			return "Password can't be empty";
		}

		if (StringUtils.isNotBlank(userDto.getFirstName())) {
			return "Password can't be empty";
		}

		if (StringUtils.isNotBlank(userDto.getEmail())) {
			return "Password can't be empty";
		}

		if (StringUtils.isNotBlank(userDto.getPhoneNo())) {
			return "Password can't be empty";
		}

		if (StringUtils.isNotBlank(userDto.getPassword())) {
			return "Password can't be empty";
		}

		User user = new User();
		String hasedPassword = PasswordUtility.hashPasswordWithPBKDF2(userDto.getPassword());
		user.setPassword(hasedPassword);
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setDob(userDto.getDob());
		user.setEmail(userDto.getEmail());
		user.setPhoneNo(userDto.getPhoneNo());
		user.setUserName(userDto.getUserName());
		user.setGender(userDto.getGender());
		userRepository.save(user);
		return null;
	}

	@Transactional
	public void deleteUser(Long userId) {
		userRepository.deleteById(userId);

	}

	public UserDto getUser(Long userId) {
		UserDto userDto = null;

		Optional<User> userOpt = userRepository.findById(userId);
		if (userOpt.isPresent()) {
			User user = userOpt.get();
			userDto = new UserDto();
			userDto.setUserId(user.getUserId());
			userDto.setUserName(user.getUserName());
			userDto.setFirstName(user.getFirstName());
			userDto.setLastName(user.getLastName());
			userDto.setDob(user.getDob());
			userDto.setEmail(user.getEmail());
			userDto.setPhoneNo(user.getPhoneNo());
			userDto.setGender(user.getGender());

		}
		return userDto;

	}

	@Transactional
	public void updateUser(UserDto userDto, Long userId) {
		Optional<User> userOpt = userRepository.findById(userId);
		User user = null;
		if (userOpt.isPresent()) {
			user = userOpt.get();
			if (userDto.getFirstName() != null) {
				user.setFirstName(userDto.getFirstName());
			}
			if (userDto.getLastName() != null) {
				user.setLastName(userDto.getLastName());
			}
			if (userDto.getDob() != null) {
				user.setDob(userDto.getDob());
			}
			if (userDto.getEmail() != null) {
				user.setEmail(userDto.getDob());
			}
			if (userDto.getPhoneNo() != null) {
				user.setPhoneNo(userDto.getPhoneNo());
			}
			userRepository.save(user);
		}

	}

	@Transactional
	public UserDto loginUser(UserDto userDto) throws InvalidKeySpecException, NoSuchAlgorithmException {
		UserDto response = null;
		String hashedPassword = PasswordUtility.hashPasswordWithPBKDF2(userDto.getPassword());
		Optional<User> optUser = userRepository.findByUserNameAndPassword(userDto.getUserName(), hashedPassword);
		if (optUser.isPresent()) {
			response = new UserDto();
			User user = optUser.get();
			response.setUserId(user.getUserId());
			response.setUserName(user.getUserName());
			response.setFirstName(user.getFirstName());
			response.setLastName(user.getLastName());
			response.setDob(user.getDob());
			response.setEmail(user.getEmail());
			response.setPhoneNo(user.getPhoneNo());

			userTokenRepository.deleteByUserId(user.getUserId());

			String token = JwtTokenUtility.generateToken(user);
			UserToken userToken = new UserToken();
			userToken.setUserId(user.getUserId());
			userToken.setToken(token);
			userToken.setStatus(true);
//			userToken.setExpiryDate(null);
			userToken.setCreatedOn(new Date());
			userTokenRepository.save(userToken);

			response.setToken(token);
		}
		return response;
	}

	@Transactional
	public void changePassword(PasswordDto passwordDto) throws InvalidKeySpecException, NoSuchAlgorithmException {
		String hashedOldPassword = PasswordUtility.hashPasswordWithPBKDF2(passwordDto.getOldPassword());
		String hashedNewPassword = PasswordUtility.hashPasswordWithPBKDF2(passwordDto.getNewPassword());
		if (passwordDto.getNewPassword().equals(passwordDto.getConfirmPassword())) {
			Optional<User> optUser = userRepository.findByUserNameAndPassword(passwordDto.getUserName(),
					hashedOldPassword);
			if (optUser.isPresent()) {
				User user = optUser.get();
				user.setPassword(hashedNewPassword);
				userRepository.save(user);
			}
		}
	}

	@Transactional
	public void createPost(Long userId, PostDto postDto) {
		Optional<User> userOpt = userRepository.findById(userId);
		if (userOpt.isPresent()) {
			Post post = new Post();
			post.setMessage(postDto.getMessage());
			post.setCreatedOn(new Date());
			post.setUser(userOpt.get());
			postRepository.save(post);
		} else {
			throw new RuntimeException("User does not exist!");
		}

	}

	@Transactional
	public void updatePost(PostDto postDto, Long postId, Long userId) {
		Optional<User> optUser = userRepository.findById(userId);
		if (optUser.isPresent()) {
			Optional<Post> optPost = postRepository.findById(postId);
			if (optPost.isPresent()) {
				Post post = optPost.get();
				post.setMessage(postDto.getMessage());
				post.setUpdatedOn(new Date());
				postRepository.save(post);
			} else {
				throw new RuntimeException("Post does not exist");
			}

		} else {
			throw new RuntimeException("User does not exist");
		}
	}

	public PostDto getPost(Long userId, Long postId) {
		Optional<User> optUser = userRepository.findById(userId);
		if (optUser.isPresent()) {
			Optional<Post> optPost = postRepository.findById(postId);
			PostDto postDto = null;
			if (optPost.isPresent()) {
				postDto = new PostDto();
				postDto.setMessage(optPost.get().getMessage());
				postDto.setCreatedOn(optPost.get().getCreatedOn());
				postDto.setId(optPost.get().getId());
			}
			return postDto;
		}
		return null;

	}

	@Transactional
	public void deletePost(Long userId, Long postId) {

		Optional<Post> optPost = postRepository.findById(postId);
		if (optPost.isPresent()) {
//			Post post = optPost.get();
//			int rows = likeRepository.deleteAllByPost(post);
//			System.out.println("likes rows:"+rows);
//			rows = commentRepository.deleteAllByPost(post);
//			System.out.println("comments rows:"+rows);
			postRepository.deleteById(postId);
		} else {
			throw new RuntimeException("Post doesn't exist");
		}
	}

	public List<PostDto> getAllPost(Long userId) {
		User user = new User();
		user.setUserId(userId);
		List<Post> allPost = postRepository.findAllByUser(user);
		List<PostDto> postDtos = new ArrayList<>();
		for (Post post : allPost) {
			PostDto postDto = new PostDto();
			postDto.setMessage(post.getMessage());
			postDto.setCreatedOn(post.getCreatedOn());
			postDto.setId(post.getId());
			postDtos.add(postDto);
		}
		return postDtos;
	}

//	Second way using userId.
	public List<PostDto> getAllPost_1(Long userId) {
		Optional<User> optUser = userRepository.findById(userId);
		if (optUser.isPresent()) {
			List<Post> posts = optUser.get().getPosts();
			List<PostDto> postDtos = new ArrayList<>();
			posts.forEach(post -> {
				PostDto postDto = new PostDto();
				postDto.setMessage(post.getMessage());
				postDto.setCreatedOn(post.getCreatedOn());
				postDto.setId(post.getId());
				postDtos.add(postDto);
			});
			return postDtos;
		}
		throw new RuntimeException("User does not exist!");
	}

	@Transactional
	public void createComment(CommentsDto commentsDto, Long postId, Long userId) {
		Comments comments = new Comments();
		comments.setMessage(commentsDto.getMessage());
		comments.setCreatedOn(new Date());

		User user = new User();
		user.setUserId(commentsDto.getCommentorUserId());
		comments.setUser(user);

		Post post = new Post();
		post.setId(postId);
		comments.setPost(post);

		commentRepository.save(comments);
	}

	public List<CommentsDto> getComment(Long userId, Long postId) {
		Optional<Post> optPost = postRepository.findById(postId);
		CommentsDto commentsDto = null;
		List<CommentsDto> commentsDtos = new ArrayList<>();
		if (optPost.isPresent()) {
			List<Comments> comments = optPost.get().getComments();

			for (Comments comment : comments) {
				commentsDto = new CommentsDto();
				commentsDto.setId(comment.getId());
				commentsDto.setCreatedOn(comment.getCreatedOn());
				commentsDto.setMessage(comment.getMessage());
				UserDto userDto = new UserDto();
				User user = comment.getUser();
				userDto.setFirstName(user.getFirstName());
				userDto.setLastName(user.getLastName());
				userDto.setUserId(user.getUserId());
				commentsDto.setUser(userDto);
				commentsDtos.add(commentsDto);
			}
		}
		return commentsDtos;
	}

//	                         OR

	public List<CommentsDto> getComment_m2(Long userId, Long postId) {
		List<CommentsDto> commentsDtos = new ArrayList<>();
		Object[][] records = commentRepository.findAllByPostWithUser(postId);

		for (Object[] record : records) {
			CommentsDto commentsDto = new CommentsDto();
			commentsDto.setId((Long) record[0]);
			commentsDto.setMessage((String) record[1]);
			commentsDto.setCreatedOn((Date) record[2]);
			UserDto userDto = new UserDto();
			userDto.setUserId((Long) record[3]);
			userDto.setFirstName((String) record[4]);
			userDto.setLastName((String) record[5]);
			commentsDto.setUser(userDto);
			commentsDtos.add(commentsDto);
		}

		return commentsDtos;
	}

	@Transactional
	public void deleteComment(Long commentId) {
		commentRepository.deleteById(commentId);
	}

	@Transactional
	public void updateComment(CommentsDto commentsDto, Long userId, Long postId, Long commentId) {
		Optional<Comments> optComment = commentRepository.findById(commentId);
		if (optComment.isPresent()) {
			Comments comment = optComment.get();
			comment.setMessage(commentsDto.getMessage());
			comment.setUpdatedOn(new Date());
			commentRepository.save(comment);
		} else {
			throw new RuntimeException("comment doesn't exist");
		}
	}

	@Transactional
	public void createLike(LikeDto likeDto, Long userId, Long postId) {
		Likes like = new Likes();
		User user = new User();
		user.setUserId(likeDto.getUser().getUserId());
		like.setUser(user);
		like.setCreatedOn(new Date());
		Post post = new Post();
		post.setId(postId);
		like.setPost(post);
		likeRepository.save(like);
	}

	@Transactional
	public void deleteLike(Long likeId) {
		likeRepository.deleteById(likeId);
	}

	public List<LikeDto> getLikes(Long userId, Long postId) {
		Object[][] records = likeRepository.findAllByPostWithUser(postId);
		List<LikeDto> likeDtos = new ArrayList<>();
		for (Object[] record : records) {
			LikeDto likeDto = new LikeDto();
			likeDto.setId((Long) record[0]);
			UserDto userDto = new UserDto();
			userDto.setUserId((Long) record[1]);
			userDto.setFirstName((String) record[2]);
			userDto.setLastName((String) record[3]);
			likeDto.setUser(userDto);
			likeDtos.add(likeDto);
		}
		return likeDtos;
	}

	@Transactional
	public void createFriend(FriendDto friendDto, Long userId) {
		Optional<User> optUser1 = userRepository.findById(userId);
		Optional<User> optUser2 = userRepository.findById(friendDto.getUser2Id());
		if (optUser1.isPresent() && optUser2.isPresent()) {
			Friends friends = new Friends();
			friends.setUser2(optUser2.get());
			friends.setCreatedOn(new Date());
			friends.setUser1(optUser1.get());
			friendRepository.save(friends);
		}
	}

	public List<UserDto> getFriend(Long userId) {
		List<UserDto> friends = friendRepository.findAllUserFriend(userId);
		return friends;
	}

	@Transactional
	public void logoutUser(Long userId) {
		userTokenRepository.deleteByUserId(userId);
	}

}
