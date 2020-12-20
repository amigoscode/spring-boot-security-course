package com.example.demo.auth;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.example.demo.security.ApplicationUserRole.*;

@AllArgsConstructor
@Repository("fake")
public class FakeApplicationUserDaoService implements ApplicationUserDao {

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        return getApplicationUsers().stream().filter(applicationUser -> username.equals(applicationUser.getUsername())).findFirst();
    }

    private List<ApplicationUser> getApplicationUsers() {
        List<ApplicationUser> applicationUserList = Lists.newArrayList(
                new ApplicationUser(STUDENT.getGrantedAuthorities(), "arham", passwordEncoder.encode("password"),
                        true, true, true, true),
                new ApplicationUser(ADMIN.getGrantedAuthorities(), "ammaar", passwordEncoder.encode("password1"),
                        true, true, true, true),
                new ApplicationUser(ADMIN_TRAINEE.getGrantedAuthorities(), "ahmed", passwordEncoder.encode("password2"),
                        true, true, true, true)
        );

        return applicationUserList;
    }
}
