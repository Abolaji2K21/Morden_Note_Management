package africa.semicolon.services;

import africa.semicolon.noteException.BigNoteManagementException;
import africa.semicolon.noteException.InvalidPassCodeException;
import africa.semicolon.noteException.UserExistsException;
import africa.semicolon.noteException.UserNotFoundException;
import africa.semicolon.data.model.User;
import africa.semicolon.dtos.requests.LoginUserRequest;
import africa.semicolon.dtos.requests.LogoutUserRequest;
import africa.semicolon.dtos.requests.RegisterUserRequest;
import africa.semicolon.dtos.responds.LoginUserResponse;
import africa.semicolon.dtos.responds.LogoutUserResponse;
import africa.semicolon.dtos.responds.RegisterUserResponse;
import africa.semicolon.data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static africa.semicolon.utils.Mapper.map;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;


    @Override
    public RegisterUserResponse register(RegisterUserRequest registerUserRequest) {
        String username = registerUserRequest.getUsername().toLowerCase();
        registerUserRequest.setUsername(username);
        validate(username.toLowerCase());
        User myUser = map(registerUserRequest);
        userRepository.save(myUser);

        return map(myUser);
    }

    @Override
    public LoginUserResponse login(LoginUserRequest loginUserRequest) {
        String username = loginUserRequest.getUsername().toLowerCase();
        String password = loginUserRequest.getPassword();
        User user = findUserBy(username);
        if (user == null) {
            throw new UserNotFoundException("User with username " + username + " not found");
        }

        if (!password.equals(user.getPassword())) {
            throw new InvalidPassCodeException("Invalid password for user " + username);
        }

        user.setLoggedIn(true);
        userRepository.save(user);
        return new LoginUserResponse(user.getId(), user.getUsername().toLowerCase());    }

    @Override
    public LogoutUserResponse logout(LogoutUserRequest logoutUserRequest) {
        String username = logoutUserRequest.getUsername().toLowerCase();
        User user = findUserBy(username);
        if (user == null) {
            throw new UserNotFoundException("User with username " + username + " not found");
        } else {
            user.setLoggedIn(false);
            userRepository.save(user);
            return new LogoutUserResponse(user.getId(), user.getUsername());
        }
    }

    @Override
    public User findUserBy(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("User with username " + username + " not found");
        }
        return user;
    }


    private void validate(String username) {
        boolean userExists = userRepository.existsByUsername(username);
        if (userExists) throw new UserExistsException(String.format("%s already exists", username));
    }


    @Override
    public boolean isUserRegistered(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean isUserLoggedIn(String username) {
        String lowercaseUsername = username.toLowerCase();
        User user = findUserBy(lowercaseUsername);
        return user.isLoggedIn();
    }

}
