package com.sigmamales.sigmafoodserver.service;

import com.sigmamales.sigmafoodserver.api.request.ChangePasswordRequest;
import com.sigmamales.sigmafoodserver.api.request.OldPasswordNotMatchException;
import com.sigmamales.sigmafoodserver.api.request.UserRequest;
import com.sigmamales.sigmafoodserver.authentication.PrincipalContext;
import com.sigmamales.sigmafoodserver.database.model.Address;
import com.sigmamales.sigmafoodserver.database.model.User;
import com.sigmamales.sigmafoodserver.database.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final ValidationService validationService;


    public User getCurrentUser() {
        return userRepository.getById(PrincipalContext.getCurrentUserId());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(Strings.EMPTY));
    }

    public Address getUserAddress() {
        return getCurrentUser().getAddress();
    }

    public User updateUserData(UserRequest userRequest) {
        var currentUser = getCurrentUser();
        currentUser.updateWith(userRequest);
        return currentUser;
    }

    public void changePassword(@NotNull @Valid ChangePasswordRequest request) {
        var user = getCurrentUser();
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw OldPasswordNotMatchException.instance();
        }
        validationService.validatePasswordComplexity(request.getNewPassword());
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

}
