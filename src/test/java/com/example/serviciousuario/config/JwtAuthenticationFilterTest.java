package com.example.serviciousuario.config;

import com.example.serviciousuario.DTO.UserResponse;
import com.example.serviciousuario.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Mock
    private AuthService authService;

    @Mock
    private FilterChain filterChain;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtAuthenticationFilter = new JwtAuthenticationFilter(authService);
    }

    @Test
    void testDoFilterInternal_ValidToken() throws Exception {
        // Arrange
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer valid-token");
        MockHttpServletResponse response = new MockHttpServletResponse();

        UserResponse mockUserResponse = new UserResponse();
        mockUserResponse.setUsername("testuser");
        mockUserResponse.setAuthorities(List.of("ROLE_USER"));

        when(authService.validateToken("valid-token")).thenReturn(mockUserResponse);

        // Use reflection to invoke the protected method
        Method method = JwtAuthenticationFilter.class.getDeclaredMethod("doFilterInternal", 
            HttpServletRequest.class, HttpServletResponse.class, FilterChain.class);
        method.setAccessible(true);

        // Act
        method.invoke(jwtAuthenticationFilter, request, response, filterChain);

        // Assert
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(authentication);
        assertEquals("testuser", authentication.getName());
        assertTrue(authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_InvalidToken() throws Exception {
        // Arrange
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer invalid-token");
        MockHttpServletResponse response = new MockHttpServletResponse();

        when(authService.validateToken("invalid-token")).thenReturn(null);

        // Use reflection to invoke the protected method
        Method method = JwtAuthenticationFilter.class.getDeclaredMethod("doFilterInternal", 
            HttpServletRequest.class, HttpServletResponse.class, FilterChain.class);
        method.setAccessible(true);

        // Act
        method.invoke(jwtAuthenticationFilter, request, response, filterChain);

        // Assert
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNull(authentication);
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_NoAuthorizationHeader() throws Exception {
        // Arrange
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        Method method = JwtAuthenticationFilter.class.getDeclaredMethod("doFilterInternal",
                HttpServletRequest.class, HttpServletResponse.class, FilterChain.class);
        method.setAccessible(true);

        // Act
        method.invoke(jwtAuthenticationFilter, request, response, filterChain);

        // Assert
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNull(authentication); // Valida que no hay autenticación establecida
        verify(filterChain, times(1)).doFilter(request, response); // Verifica que se llama a la cadena de filtros
    }

}