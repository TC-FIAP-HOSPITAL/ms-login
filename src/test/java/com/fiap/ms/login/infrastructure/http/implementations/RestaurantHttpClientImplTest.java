package com.fiap.ms.login.infrastructure.http.implementations;

import com.fiap.ms.login.infrastructure.http.RestaurantFeignClient;
import com.fiap.ms.login.infrastructure.http.dto.RestaurantDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantHttpClientImplTest {

    @Mock
    private RestaurantFeignClient restaurantFeignClient;

    @InjectMocks
    private RestaurantHttpClientImpl restaurantHttpClient;

    @Test
    void userHasRestaurant_shouldReturnRestaurantDto() {
        Long userId = 1L;
        RestaurantDto expectedDto = new RestaurantDto(true);
        
        when(restaurantFeignClient.userHasRestaurant(userId)).thenReturn(expectedDto);

        RestaurantDto result = restaurantHttpClient.userHasRestaurant(userId);

        assertEquals(expectedDto, result);
    }
}