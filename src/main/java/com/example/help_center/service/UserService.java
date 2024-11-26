package com.example.help_center.service;

import com.example.help_center.dto.UserDTO;
import com.example.help_center.model.Roles;
import com.example.help_center.model.User;
import com.example.help_center.repository.RolesRepository;
import com.example.help_center.repository.UserRepository;
import com.example.help_center.security.Crypto;
import com.example.help_center.util.ResponseUtil;
import com.example.help_center.util.TransformToDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TransformToDTO transformToDTO;

    public ResponseEntity<Object> save(User user, HttpServletRequest request) {
        if (user == null) return ResponseUtil.validationFailed("OBJECT NULL", null, request);

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String encrypt = Crypto.performEncrypt(user.getPassword());
            user.setPassword(encrypt);
            user.setCreatedBy(authentication.getPrincipal().toString());

            Optional<Roles> roles = rolesRepository.findByName("USER");
            roles.ifPresent(user::setRoleId);

            userRepository.save(user);
        }catch (Exception e){
            return ResponseUtil.dataNotFound("Data invalid",request);
        }

        return ResponseUtil.dataSaveSuccess(request);
    }

    public ResponseEntity<Object> update(Long id, User user, HttpServletRequest request) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) return ResponseUtil.dataNotFound("Data not found", request);
        try {
            User updateUser = userOptional.get();
            updateUser.setEmail(user.getEmail());
            updateUser.setUsername(user.getUsername());
            String encrypt = Crypto.performEncrypt(user.getPassword());
            updateUser.setPassword(encrypt);
            userRepository.save(updateUser);
        } catch (Exception e) {
            return ResponseUtil.dataNotFound("Data invalid",request);
        }

        return ResponseUtil.dataUpdateSuccess(request);
    }

    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) return ResponseUtil.dataNotFound("Data not found", request);
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            return ResponseUtil.dataNotFound("Data invalid",request);
        }

        return ResponseUtil.dataUpdateSuccess(request);
    }

    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        Page<User> page = null;
        List<User> list = null;
        try{
            page = userRepository.findAll(pageable);
            list = page.getContent();
            if(list.isEmpty()){
                return ResponseUtil.dataNotFound("Data not found", request);
            }
        }catch (Exception e){
            return ResponseUtil.dataNotFound("FE001003031",request);
        }

        return transformToDTO.
                transformObject(new HashMap<>(),
                        list, page,null,null,null ,request);
    }

    public ResponseEntity<Object> getCurrentUser(HttpServletRequest request) {
        User user = new User();
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            user = userRepository.findByUsername(authentication.getPrincipal().toString());

        }catch (Exception e){
            return ResponseUtil.dataNotFound("Data not Found",request);
        }

        return ResponseUtil.dataFound("Data Found", user, request);
    }

    public ResponseEntity<Object> updateUserRole (HttpServletRequest request, Long id, String roleName) {
        Optional<User> userOptional = userRepository.findById(id);
        Optional<Roles> rolesOptional = rolesRepository.findByName(roleName.toUpperCase());
        if (userOptional.isEmpty()) return ResponseUtil.dataNotFound("User not found", request);
        if (rolesOptional.isEmpty()) return ResponseUtil.dataNotFound("Role not found", request);
        try {
            User updateUser = userOptional.get();
            updateUser.setRoleId(rolesOptional.get());
            userRepository.save(updateUser);
        } catch (Exception e) {
            return ResponseUtil.dataNotFound("Data invalid",request);
        }

        return ResponseUtil.dataUpdateSuccess(request);
    }

    public User convertToEntity(UserDTO userDTO){
        return modelMapper.map(userDTO, User.class);
    }

}
