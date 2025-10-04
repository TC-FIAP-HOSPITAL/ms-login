package com.fiap.ms.login.application.usecase.user.exceptions;

import com.fiap.ms.login.domain.exceptions.UserAlreadyExistsException;
import com.fiap.ms.login.domain.exceptions.UserHasException;
import com.fiap.ms.login.domain.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDomainExceptionsTest {

    @Test
    void testUserNotFoundException() {
        String message = "User not found";
        UserNotFoundException exception = new UserNotFoundException(message);
        
        assertEquals(message, exception.getMessage());
        assertTrue(exception instanceof RuntimeException);
        assertNull(exception.getCause());
    }

    @Test
    void testUserNotFoundException_withNullMessage() {
        UserNotFoundException exception = new UserNotFoundException(null);
        
        assertNull(exception.getMessage());
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void testUserNotFoundException_withEmptyMessage() {
        String message = "";
        UserNotFoundException exception = new UserNotFoundException(message);
        
        assertEquals(message, exception.getMessage());
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void testUserAlreadyExistsException() {
        String message = "User already exists";
        UserAlreadyExistsException exception = new UserAlreadyExistsException(message);
        
        assertEquals(message, exception.getMessage());
        assertTrue(exception instanceof RuntimeException);
        assertNull(exception.getCause());
    }

    @Test
    void testUserAlreadyExistsException_withNullMessage() {
        UserAlreadyExistsException exception = new UserAlreadyExistsException(null);
        
        assertNull(exception.getMessage());
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void testUserAlreadyExistsException_withEmptyMessage() {
        String message = "";
        UserAlreadyExistsException exception = new UserAlreadyExistsException(message);
        
        assertEquals(message, exception.getMessage());
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void testUserHasException() {
        String message = "User has some issue";
        UserHasException exception = new UserHasException(message);
        
        assertEquals(message, exception.getMessage());
        assertTrue(exception instanceof RuntimeException);
        assertNull(exception.getCause());
    }

    @Test
    void testUserHasException_withNullMessage() {
        UserHasException exception = new UserHasException(null);
        
        assertNull(exception.getMessage());
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void testUserHasException_withEmptyMessage() {
        String message = "";
        UserHasException exception = new UserHasException(message);
        
        assertEquals(message, exception.getMessage());
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void testExceptionsAreDifferentTypes() {
        UserNotFoundException userNotFound = new UserNotFoundException("not found");
        UserAlreadyExistsException userExists = new UserAlreadyExistsException("exists");
        UserHasException userHas = new UserHasException("has issue");
        
        assertFalse(userNotFound.getClass().equals(userExists.getClass()));
        assertFalse(userNotFound.getClass().equals(userHas.getClass()));
        assertFalse(userExists.getClass().equals(userHas.getClass()));
        
        // But they are all RuntimeExceptions
        assertTrue(userNotFound instanceof RuntimeException);
        assertTrue(userExists instanceof RuntimeException);
        assertTrue(userHas instanceof RuntimeException);
    }

    @Test
    void testExceptionInheritance() {
        UserNotFoundException userNotFound = new UserNotFoundException("message");
        UserAlreadyExistsException userExists = new UserAlreadyExistsException("message");
        UserHasException userHas = new UserHasException("message");

        assertTrue(RuntimeException.class.isAssignableFrom(UserNotFoundException.class));
        assertTrue(RuntimeException.class.isAssignableFrom(UserAlreadyExistsException.class));
        assertTrue(RuntimeException.class.isAssignableFrom(UserHasException.class));
        
        assertTrue(Exception.class.isAssignableFrom(UserNotFoundException.class));
        assertTrue(Exception.class.isAssignableFrom(UserAlreadyExistsException.class));
        assertTrue(Exception.class.isAssignableFrom(UserHasException.class));
        
        assertTrue(Throwable.class.isAssignableFrom(UserNotFoundException.class));
        assertTrue(Throwable.class.isAssignableFrom(UserAlreadyExistsException.class));
        assertTrue(Throwable.class.isAssignableFrom(UserHasException.class));
    }

    @Test
    void testExceptionCanBeThrown() {
        assertThrows(UserNotFoundException.class, () -> {
            throw new UserNotFoundException("User not found");
        });

        assertThrows(UserAlreadyExistsException.class, () -> {
            throw new UserAlreadyExistsException("User already exists");
        });

        assertThrows(UserHasException.class, () -> {
            throw new UserHasException("User has issue");
        });
    }

    @Test
    void testExceptionMessagesArePreserved() {
        String notFoundMsg = "User with ID 123 not found";
        String existsMsg = "User with email test@example.com already exists";
        String hasMsg = "User has validation errors";

        UserNotFoundException notFound = new UserNotFoundException(notFoundMsg);
        UserAlreadyExistsException exists = new UserAlreadyExistsException(existsMsg);
        UserHasException has = new UserHasException(hasMsg);

        assertEquals(notFoundMsg, notFound.getMessage());
        assertEquals(existsMsg, exists.getMessage());
        assertEquals(hasMsg, has.getMessage());
    }
}