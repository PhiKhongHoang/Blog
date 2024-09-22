package FA.JustBlog.Core.config;

import FA.JustBlog.Core.models.User;
import FA.JustBlog.Core.repositories.IPermissionRepository;
import FA.JustBlog.Core.repositories.IRoleRepository;
import FA.JustBlog.Core.repositories.IUserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;
    IPermissionRepository permissionRepository;
    IRoleRepository roleRepository;

    @Bean
    ApplicationRunner applicationRunner(IUserRepository userRepository) {
//        Role role = Role.builder()
//                .name(FA.JustBlog.Core.enums.Role.BLOG_OWNER.name())
//                .description("This is" + FA.JustBlog.Core.enums.Role.BLOG_OWNER.name())
//                .permissions()
//                .build();
//        var roles = new HashSet<Role>();
//        roles.add(role);

        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
//                        .roles(roles)
                        .build();

                userRepository.save(user);
                log.warn("admin user has been created with default password: admin, please change it");
            }
        };
    }
}
