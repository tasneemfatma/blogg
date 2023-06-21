package com.blogg.controller;


import com.blogg.entity.Role;
import com.blogg.entity.User;
import com.blogg.payload.JWTAuthResponse;
import com.blogg.payload.LoginDto;
import com.blogg.payload.SignUpDto;
import com.blogg.repository.RoleRepository;
import com.blogg.repository.UserRepository;
import com.blogg.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@Data

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // get token form tokenProvider
        String token = tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTAuthResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){

        // add check for username exists in a DB
        if(userRepository.existsByUsername(signUpDto.getUsername())){
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        // add check for email exists in DB
        if(userRepository.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        // create user object
        User user = new User();
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Role roles = roleRepository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles));

        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);

    }
}
//@RestController
//@RequestMapping("/api/auth")
//public class AuthController {
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private UserRepository userRepo;
//    @Autowired
//    private RoleRepository roleRepo;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @PostMapping("/signin")
//    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto){
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword())
//        );
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
//    }
//
//
//    @PostMapping("/signup")
//    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){
//
//        if(userRepo.existsByUsername(signUpDto.getUsername())){
//            return new ResponseEntity<>("user already exists!", HttpStatus.BAD_REQUEST)
//        }
//        if(userRepo.existsByEmail(signUpDto.getEmail())){
//            return new ResponseEntity<>("user already exists!", HttpStatus.BAD_REQUEST)
//        }
//
//        User user= new User();
//        user.setName(signUpDto.getName());
//        user.setEmail(signUpDto.getEmail());
//        user.setUsername(signUpDto.getUsername());
//        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
//        Role role = roleRepo.findByName("ROLE_ADMIN").get();
//        user.setRoles(role);
//        userRepo.save(user);
//
//        return new ResponseEntity<>("User sign-up successfully!.", HttpStatus.OK);
//    }
//}

//@RestController
//@RequestMapping("/api/auth")
//public class AuthController {
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//
//
//
//    @PostMapping("/signin")
//    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto){
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                loginDto.getUsernameOrEmail(), loginDto.getPassword()));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
//    }
//
//   // @PostMapping("/signup")
////    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){
////
////        if(userRepo.existsByUsername(signUpDto.getUsername())){
////            return new ResponseEntity<>("user already exists!", HttpStatus.BAD_REQUEST)
////        }
////        if(userRepo.existsByEmail(signUpDto.getEmail())){
////            return new ResponseEntity<>("user already exists!", HttpStatus.BAD_REQUEST)
////        }
////
////        User user= new User();
////        user.setName(signUpDto.getName());
////        user.setEmail(signUpDto.getEmail());
////        user.setUsername(signUpDto.getUsername());
////        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
////        Role role = roleRepo.findByName("ROLE_ADMIN").get();
////        user.setRoles(role);
////        userRepo.save(user);
////
////        return new ResponseEntity<>("User sign-up successfully!.", HttpStatus.OK);
////    }
//
//    @PostMapping("/signup")
//    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){
//
//        // add check for username exists in a DB
//        if(userRepository.existsByUsername(signUpDto.getUsername())){
//            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
//        }
//
//        // add check for email exists in DB
//        if(userRepository.existsByEmail(signUpDto.getEmail())){
//            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
//        }
//
//        // create user object
//        User user = new User();
//        user.setName(signUpDto.getName());
//        user.setUsername(signUpDto.getUsername());
//        user.setEmail(signUpDto.getEmail());
//        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
//
//        Role roles = roleRepository.findByName("ROLE_ADMIN").get();
//        user.setRoles(Collections.singleton(roles));
//
//       userRepository.save(user);
//
//        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
//
//    }
//}
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//
//@RequestMapping("/api/auth/")
//@RestController
//public class AuthController {
//
//    @Autowired
//    private AuthenticationManager authManager;
////http://localhost:8080/api/auth/signin
//    @PostMapping("/signin")
//    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto){
//        Authentication authenticate = authManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));
//        SecurityContextHolder.getContext().setAuthentication(authenticate);
//        return new ResponseEntity<>("user signin successfully!", HttpStatus.OK);
//    }
//}
