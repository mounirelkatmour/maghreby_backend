package com.maghreby.controller;

import com.maghreby.dto.Auth0UserDTO;
import com.maghreby.model.LanguagePreference;
import com.maghreby.model.RegularUser;
import com.maghreby.model.ServiceType;
import com.maghreby.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserSyncController {

    private final UserRepository userRepository;

    @PostMapping("/sync")
    public RegularUser syncUser(@RequestBody Auth0UserDTO auth0User) {
        System.out.println("Syncing user: " + auth0User.getEmail());

        return (RegularUser) userRepository.findByAuth0Id(auth0User.getSub())
            .orElseGet(() -> userRepository.findByEmail(auth0User.getEmail()).orElseGet(() -> {
                RegularUser user = RegularUser.builder()
                    .auth0Id(auth0User.getSub())
                    .email(auth0User.getEmail())
                    .firstName(auth0User.getName().split(" ")[0])
                    .lastName(auth0User.getName().split(" ").length > 1 ? auth0User.getName().split(" ")[1] : "")
                    .profilImg(auth0User.getPicture())
                    .role(com.maghreby.model.Role.USER)
                    .firstTimeLogin(true)
                    .active(false)
                    .languagePreference(LanguagePreference.ENGLISH)
                    .service(ServiceType.NSP)
                    .build();
                return userRepository.save(user);
            }));
    }

}
