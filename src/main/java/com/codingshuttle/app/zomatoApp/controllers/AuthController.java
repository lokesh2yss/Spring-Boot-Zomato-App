package com.codingshuttle.app.zomatoApp.controllers;

import com.codingshuttle.app.zomatoApp.dto.*;
import com.codingshuttle.app.zomatoApp.exceptions.ResourceNotFoundException;
import com.codingshuttle.app.zomatoApp.services.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping(path = "/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginDto loginDto, HttpServletResponse response) {
        String[] tokens = authService.login(loginDto.getEmail(), loginDto.getPassword());
        Cookie cookie = new Cookie("token", tokens[1]);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return ResponseEntity.ok(new LoginResponseDto(tokens[0]));
    }

    @PostMapping(path = "/signup")
    public ResponseEntity<UserDto> signup(@RequestBody SignupDto signupDto) {
        return new ResponseEntity<>(authService.signup(signupDto), HttpStatus.CREATED);
    }

    @PostMapping(path = "/onboardNewRestaurant/{userId}")
    public ResponseEntity<RestaurantDto> onboardNewRestaurant(@PathVariable Long userId, @RequestBody OnboardRestaurantDto onboardRestaurantDto) {
        return new ResponseEntity<>(authService.onboardNewRestaurant(userId, onboardRestaurantDto), HttpStatus.CREATED);
    }

    @PostMapping(path = "/onboardNewDeliveryExecutive/{userId}")
    public ResponseEntity<DeliveryExecutiveDto> onboardNewDeliveryExecutive(@PathVariable Long userId, @RequestBody OnboardDeliveryExecutiveDto onboardDeliveryExecutiveDto) {
        return new ResponseEntity<>(authService.onboardNewDeliveryExecutive(userId, onboardDeliveryExecutiveDto), HttpStatus.CREATED);
    }

    @PostMapping(path = "/refresh")
    public ResponseEntity<LoginResponseDto> refresh(HttpServletRequest request) {
        String refreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> "token".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() ->
                        new ResourceNotFoundException("refreshToken not found in cookie"));

        String accessToken = authService.refreshToken(refreshToken);

        return ResponseEntity.ok(new LoginResponseDto(accessToken));
    }
}
