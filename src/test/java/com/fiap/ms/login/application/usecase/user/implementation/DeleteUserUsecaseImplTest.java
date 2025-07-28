package com.fiap.ms.login.application.usecase.user.implementation;

import com.fiap.ms.login.application.gateways.RestaurantGateway;
import com.fiap.ms.login.application.usecase.user.exceptions.UserHasRestaurantException;
import com.fiap.ms.login.domain.model.UserRepository;
import com.fiap.ms.login.infrastructure.config.security.SecurityUtil;
import com.fiap.ms.login.infrastructure.http.dto.RestaurantDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteUserUsecaseImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SecurityUtil securityUtil;

    @Mock
    private RestaurantGateway restaurantGateway;

    @InjectMocks
    private DeleteUserUsecaseImpl deleteUserUsecase;

    @Test
    void deleteUser_sameUser_shouldDeleteUser() {
        String userId = "1";
        when(securityUtil.getUserId()).thenReturn(1L);
        when(restaurantGateway.userHasRestaurant(1L)).thenReturn(new RestaurantDto(false));
        doNothing().when(userRepository).delete(1L);

        deleteUserUsecase.deleteUser(userId);

        verify(restaurantGateway).userHasRestaurant(1L);
        verify(userRepository).delete(1L);
    }

    @Test
    void deleteUser_adminDeletingOtherUser_shouldDeleteUser() {
        String userId = "2";
        when(securityUtil.getUserId()).thenReturn(1L);
        when(securityUtil.isAdmin()).thenReturn(true);
        when(restaurantGateway.userHasRestaurant(2L)).thenReturn(new RestaurantDto(false));
        doNothing().when(userRepository).delete(2L);

        deleteUserUsecase.deleteUser(userId);

        verify(restaurantGateway).userHasRestaurant(2L);
        verify(userRepository).delete(2L);
    }

    @Test
    void deleteUser_nonAdminDeletingOtherUser_shouldThrowAccessDeniedException() {
        String userId = "2";
        when(securityUtil.getUserId()).thenReturn(1L);
        when(securityUtil.isAdmin()).thenReturn(false);

        assertThrows(AccessDeniedException.class, () -> deleteUserUsecase.deleteUser(userId));
        verify(userRepository, never()).delete(anyLong());
        verify(restaurantGateway, never()).userHasRestaurant(anyLong());
    }

    @Test
    void deleteUser_userHasRestaurant_shouldThrowUserHasRestaurantException() {
        String userId = "1";
        when(securityUtil.getUserId()).thenReturn(1L);
        when(restaurantGateway.userHasRestaurant(1L)).thenReturn(new RestaurantDto(true));

        UserHasRestaurantException exception = assertThrows(UserHasRestaurantException.class, 
            () -> deleteUserUsecase.deleteUser(userId));

        verify(restaurantGateway).userHasRestaurant(1L);
        verify(userRepository, never()).delete(anyLong());
        assert(exception.getMessage().contains("Deletion failed: User with ID 1 is associated with an existing restaurant."));
    }
}
