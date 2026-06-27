# DaoAuthenticationProvider Breaking Change in Spring Security 7

## Issue

Compilation errors in `ApplicationConfig.java`:

- `DaoAuthenticationProvider` — `setUserDetailsService(UserDetailsService)` method not found
- Class appears "undefined" in IDE (only the method error is real)

## Root Cause

This project uses **Spring Boot 4.1.0** which pulls in **Spring Security 7.1.0**.

In Spring Security 7.x, the `DaoAuthenticationProvider` API was changed:

- **`setUserDetailsService()` was removed**
- `UserDetailsService` must now be passed **via constructor**

## Fix

| Before (Spring Security 6.x) | After (Spring Security 7.x) |
|---|---|
| `DaoAuthenticationProvider p = new DaoAuthenticationProvider();` | `DaoAuthenticationProvider p = new DaoAuthenticationProvider(userDetailsService);` |
| `p.setUserDetailsService(userDetailsService());` | *(removed)* |
| `p.setPasswordEncoder(passwordEncoder());` | `p.setPasswordEncoder(passwordEncoder());` *(unchanged)* |

## Resolved Code

```java
@Bean
public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService());
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
}
```

## API Reference (Spring Security 7.1.0)

```
public class DaoAuthenticationProvider
    extends AbstractUserDetailsAuthenticationProvider {

    public DaoAuthenticationProvider(UserDetailsService);            // ← constructor (required)

    public void setPasswordEncoder(PasswordEncoder);                 // ← still available
    public void setUserDetailsPasswordService(UserDetailsPasswordService);
    public void setCompromisedPasswordChecker(CompromisedPasswordChecker);
}
```

## Verification

```bash
# Confirm Spring Security version in use
mvnw dependency:tree -Dincludes=org.springframework.security:spring-security-core
```

CMD TO RUN MVN IN CMD - mvnw spring-boot:run