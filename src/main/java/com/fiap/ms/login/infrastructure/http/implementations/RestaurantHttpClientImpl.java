package com.fiap.ms.login.infrastructure.http.implementations;

import com.fiap.ms.login.application.gateways.RestaurantGateway;
import com.fiap.ms.login.infrastructure.http.RestaurantFeignClient;
import com.fiap.ms.login.infrastructure.http.dto.RestaurantDto;
import org.springframework.stereotype.Component;

@Component
public class RestaurantHttpClientImpl implements RestaurantGateway {

    private final RestaurantFeignClient feign;

    public RestaurantHttpClientImpl(RestaurantFeignClient restaurantFeignClient) {
        this.feign = restaurantFeignClient;
    }

    @Override
    public RestaurantDto userHasRestaurant(Long userId) {
        return feign.userHasRestaurant(userId);
    }
}
