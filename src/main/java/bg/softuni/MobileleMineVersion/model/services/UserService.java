package bg.softuni.MobileleMineVersion.model.services;


import bg.softuni.MobileleMineVersion.model.dto.UserLogInDTO;
import bg.softuni.MobileleMineVersion.model.entities.UserEntity;
import bg.softuni.MobileleMineVersion.model.repositories.UserRepository;

import bg.softuni.MobileleMineVersion.user.CurrentUser;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private CurrentUser currentUser;

    private Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository userRepository, CurrentUser currentUser) {

        this.userRepository = userRepository;
        this.currentUser = currentUser;
    }



    public boolean login(UserLogInDTO logInDTO) {

        Optional<UserEntity> userOpt = this.userRepository.findByEmail(logInDTO.getUsername());

        if (userOpt.isEmpty()) {

            LOGGER.info("User with name [{}] not found.", logInDTO.getUsername());

            return false;
        }

        boolean success = userOpt.get().getPassword().equals(logInDTO.getPassword());

        if (success) {
            login(userOpt.get());
        } else {
            logOut();
        }
        return success;
    }

    private void login(UserEntity userEntity) {
        currentUser.
                setLoggedIn(true).setName(userEntity.getFirstName() + " " + userEntity.getLastName());
    }

    public void logOut() {
        currentUser.clear();
    }
}