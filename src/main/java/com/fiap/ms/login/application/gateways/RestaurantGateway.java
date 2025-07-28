package com.fiap.ms.login.application.gateways;

import com.fiap.ms.login.infrastructure.http.dto.RestaurantDto;

public interface RestaurantGateway {
    RestaurantDto userHasRestaurant(Long userId);
}
