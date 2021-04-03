package com.taskManger.services;

import com.taskManger.entities.Entity;
import com.taskManger.entities.User;
import com.taskManger.exception.EntityNotFoundException;
import com.taskManger.exception.UUIDIsNotUniqueException;
import com.taskManger.exception.UsernameNotUniqueException;
import com.taskManger.repositories.UserRepository;
import lombok.NonNull;
import org.bouncycastle.util.encoders.Hex;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

public class UserService {
    UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User signIn(String username, String password) {
        if(username == null)
            throw new NullPointerException("Username must be not null");
        if(password == null)
            throw new NullPointerException("Password must be not null");

        if (username.isEmpty())
            throw new IllegalArgumentException("Login is empty");

        List<User> userList = userRepository.findBy((Entity user) ->
                ((User)user).getUsername().equals(username));
//        if (userList.size() > 1)
//            throw new UsernameNotUniqueException(String.format("Username %s is not unique", username));
//        else

        if(userList.size() == 0)
            return null;
        else{
            User user = userList.get(0);
            if(user.getPassword().equals(sha256(password))){
                return user;
            }
            else
                return null;
        }
    }

    public User registerNewUser(String username, String password, String firstName, String lastName, String phone) throws UsernameNotUniqueException, UUIDIsNotUniqueException {

        if (username == null)
            throw new NullPointerException("Username must be not null");

        if (password == null)
            throw new NullPointerException("Password must be not null");

        if (firstName == null)
            throw new NullPointerException("FirstName must be not null");

        if (lastName == null)
            throw new NullPointerException("LastName must be not null");

        if (phone == null)
            phone = "";

        List<User> checkUserList = userRepository.findBy((Entity user) -> {return ((User)user).getUsername().equals(username);});
        if (checkUserList.size() > 0)
            throw new UsernameNotUniqueException(String.format("Username %s is alredy exists", username));


        String password_Sha256hex = sha256(password);
        String userUuid = UUID.randomUUID().toString();
        User user = new User(userUuid, username, password_Sha256hex, firstName, lastName, phone);

        userRepository.create(user);

        return user;
    }
    private String sha256(String str){
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] hash = digest.digest(
                str.getBytes(StandardCharsets.UTF_8));
        return new String(Hex.encode(hash));
    }

    public User getUserByUuid(@NonNull String userUuid) throws UUIDIsNotUniqueException, EntityNotFoundException {
        return userRepository.getEntity(userUuid);
    }

    public List<User> getAllUsers() {
        return userRepository.getAll();
    }
}
