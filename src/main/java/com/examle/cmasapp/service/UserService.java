package com.examle.cmasapp.service;

import com.examle.cmasapp.dto.UserDto;
import com.examle.cmasapp.entity.UserEntity;
import com.examle.cmasapp.exception.BadRequestException;
import com.examle.cmasapp.exception.NotFoundException;
import com.examle.cmasapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for working with users.
 * <p>
 * This service provides methods for creating, updating, searching, and deleting users.
 * It also facilitates the conversion between user entities and their Data Transfer Objects (DTOs).
 * All methods assume valid and correct input data.
 *
 * @author Fuat Safiulin
 * @version 1.0
 */
@Transactional(readOnly = true)
@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository repository;

    /**
     * Creates a new user based on the provided data.
     *
     * @param userDto Data of the new user in DTO format.
     * @return Created user in DTO format.
     * @throws BadRequestException If the provided user data (email) is invalid.
     */
    @Transactional
    public UserDto createUser(UserDto userDto) {
        UserEntity user = convertToUser(userDto);

        Boolean existEmail = repository.selectExistEmail(user.getEmail());

        if(existEmail) {
            throw new BadRequestException("Email " + user.getEmail() + " taken");
        }

        repository.save(user);

        return convertToUserDto(user);
    }

    /**
     * Returns a list of all users in the system.
     *
     * @return List of users in DTO format.
     */
    public List<UserDto> findAllUsers() {
        var userList = new ArrayList<>(repository.findAll());

        return userList.stream()
                //.sorted(Comparator.comparing(User::getEmail)) // Grouping the list of users by email
                .map(this::convertToUserDto)
                .toList();
    }

    /**
     * Returns a user by their identifier.
     *
     * @param id User identifier.
     * @return User in DTO format.
     * @throws NotFoundException If a user with the specified identifier is not found.
     */
    public UserDto findUserById(int id) {
        UserEntity user = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User by id " + id + " was not found"));

        return convertToUserDto(user);
    }

    /**
     * Updates user data based on the provided data.
     *
     * @param id User identifier to be updated.
     * @param updatedUserDto New user data in DTO format.
     * @throws NotFoundException If a user with the specified identifier is not found.
     * @throws BadRequestException If the provided user data is invalid.
     */
    @Transactional
    public UserDto updateUser(int id, UserDto updatedUserDto) {
        UserEntity user = findOrThrow(id);

        UserEntity userParam = convertToUser(updatedUserDto);

        user.setFirstName(userParam.getFirstName());
        user.setLastName(userParam.getLastName());
        user.setEmail(userParam.getEmail());
        user.setAge(userParam.getAge());
        user.setActive(userParam.isActive());

        repository.save(user);

        return convertToUserDto(user);
    }

    /**
     * Deletes a user by their identifier.
     *
     * @param id User identifier to be deleted.
     * @throws NotFoundException If a user with the specified identifier is not found.
     */
    @Transactional
    public void deleteUserById(int id) {
        findOrThrow(id);

        repository.deleteById(id);
    }

    /**
     * Finds a user by their identifier or throws a NotFoundException if not found.
     *
     * @param id The identifier of the user to find.
     * @return The user entity if found.
     * @throws NotFoundException If a user with the specified identifier is not found.
     */
    private UserEntity findOrThrow(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User by id " + id + " was not found"));
    }

    /**
     * Converts a UserDto object to a UserEntity object.
     *
     * @param userDto The UserDto to convert.
     * @return The converted UserEntity.
     */
    private UserEntity convertToUser(UserDto userDto) {
        return new UserEntity(
                userDto.getId(),
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getEmail(),
                userDto.getAge(),
                userDto.isActive());
    }

    /**
     * Converts a UserEntity object to a UserDto object.
     *
     * @param user The UserEntity to convert.
     * @return The converted UserDto.
     */
    private UserDto convertToUserDto(UserEntity user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getAge(),
                user.isActive()
        );
    }
}

