package com.fiap.ms.login.application.usecase.user.implementation;

import com.fiap.ms.login.application.gateways.RestaurantGateway;
import com.fiap.ms.login.application.usecase.user.DeleteUserUsecase;
import com.fiap.ms.login.application.usecase.user.exceptions.UserHasRestaurantException;
import com.fiap.ms.login.domain.model.UserRepository;
import com.fiap.ms.login.infrastructure.config.security.SecurityUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class DeleteUserUsecaseImpl implements DeleteUserUsecase {

    private final UserRepository userRepository;
    private final SecurityUtil securityUtil;
    private final RestaurantGateway restaurantGateway;

    public DeleteUserUsecaseImpl(
            UserRepository userRepository,
            SecurityUtil securityUtil,
            RestaurantGateway restaurantGateway
    ) {
        this.userRepository = userRepository;
        this.securityUtil = securityUtil;
        this.restaurantGateway = restaurantGateway;
    }

    public void deleteUser(String userId) {
        Long id = Long.valueOf(userId);
        boolean notSameUser = !id.equals(securityUtil.getUserId());
        boolean notAdmin = !securityUtil.isAdmin();

        if (notSameUser && notAdmin) {
            throw new AccessDeniedException(null);
        }
        try {
            restaurantGateway.userHasRestaurant(id);
            userRepository.delete(id);
        }
        catch (Exception e) {
            throw new UserHasRestaurantException("Deletion failed: User with ID " + userId + " is associated with an existing restaurant.");
        }

    }
}
