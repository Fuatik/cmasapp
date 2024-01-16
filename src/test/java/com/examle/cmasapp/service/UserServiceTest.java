package com.examle.cmasapp.service;

import com.examle.cmasapp.dto.UserDto;
import com.examle.cmasapp.entity.UserEntity;
import com.examle.cmasapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private static final int USER_ID = 100000;
    private static final UserEntity USER_1 = new UserEntity(
            USER_ID,
            "Mark",
            "Avrelly",
            "ma@gmail.com",
            15,
            true);
    private static final UserEntity USER_2 = new UserEntity(
            USER_ID + 1,
            "Lucius",
            "Verus",
            "lv@gmail.com",
            18,
            true);

    private static final UserDto USER_DTO = new UserDto(
            USER_ID,
            "Mark",
            "Avrelly",
            "ma@gmail.com",
            15,
            true);

    @Mock
    UserRepository repository;

    @InjectMocks
    UserService service;

    @Test
    void createUser() {
        service.createUser(USER_DTO);

        verify(repository).save(eq(USER_1));
    }

    @Test
    void findAllUsers() {
        BDDMockito.given(repository.findAll()).willReturn(List.of(USER_1, USER_2));

        List<UserDto> users = service.findAllUsers();

        assertEquals(2, users.size());
        assertEquals("Mark", users.get(0).getFirstName());
        assertEquals("lv@gmail.com", users.get(1).getEmail());
    }

    @Test
    void findUserById() {
        BDDMockito.given(repository.findById(USER_ID)).willReturn(Optional.of(createUserEntity()));

        UserDto userDto = service.findUserById(USER_ID);

        assertEquals("Mark", userDto.getFirstName());
    }

    @Test
    void updateUser() {
        BDDMockito.when(repository.findById(USER_ID)).thenReturn(Optional.of(createUserEntity()));

        UserDto updateedUserDto = new UserDto(USER_ID,
                "Updated Mark",
                "new Avrelly",
                "ma@gmail.com",
                45,
                true);

        service.updateUser(USER_ID, updateedUserDto);

        verify(repository).save(any(UserEntity.class));
    }

    @Test
    void deleteUserById() {
        BDDMockito.when(repository.findById(USER_ID)).thenReturn(Optional.of(createUserEntity()));

        service.deleteUserById(USER_ID);

        verify(repository).deleteById(eq(USER_ID));
    }

    private UserEntity createUserEntity() {
        return new UserEntity(
                UserServiceTest.USER_1.getId(),
                UserServiceTest.USER_1.getFirstName(),
                UserServiceTest.USER_1.getLastName(),
                UserServiceTest.USER_1.getEmail(),
                UserServiceTest.USER_1.getAge(),
                UserServiceTest.USER_1.isActive());
    }
}