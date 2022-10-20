package com.winners.libraryproject.service;

import com.winners.libraryproject.dto.UserDTO;
import com.winners.libraryproject.entity.Role;
import com.winners.libraryproject.entity.User;
import com.winners.libraryproject.entity.enumeration.UserRole;
import com.winners.libraryproject.payload.BadRequestException;
import com.winners.libraryproject.payload.ConflictException;
import com.winners.libraryproject.payload.ResourceNotFoundException;
import com.winners.libraryproject.repository.RoleRepository;
import com.winners.libraryproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.message.AuthException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final static String USER_NOT_FOUND_MSG = "user with id %d not found";


    public void register(User user){

        if (userRepository.existsByEmail(user.getEmail())){
            throw new ConflictException("Error: Email is already in use!");
        }

        LocalDateTime createDate=LocalDateTime.now();

        user.setCreateDate(createDate);

        Set<Role> roles = new HashSet<>();
        Role memberRole = roleRepository.findByName(UserRole.ROLE_MEMBER);

        roles.add(memberRole);

        user.setRoles(roles);

        userRepository.save(user);
    }


    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public UserDTO findById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(String.format(USER_NOT_FOUND_MSG, id)));

        UserDTO userDTO=new UserDTO();
        userDTO.setRoles(user.getRoles());
        return new UserDTO(user.getFirstName(), user.getLastName(),user.getScore(),user.getAddress(),user.getPhone(),user.getBirthDate(),user.getEmail(),user.getCreateDate(),user.getResetPasswordCode(),user.getBuiltIn(),userDTO.getRoles() );
    }


    public void removeById(Long id){
        User user = userRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(String.format(USER_NOT_FOUND_MSG, id)));

        userRepository.deleteById(id);
    }

    public void login(String email,String password) throws AuthException {
        try {
        User user= userRepository.findByEmail(email, password);

        if (!user.getPassword().equals(password))
            throw  new AuthException("invalid credentials");

         }catch (Exception e){
             throw new AuthException("invalid credentials");
    }
    }

  /*  public void updateUser(Long  id,UserDTO userDTO){
        boolean emailExists = userRepository.existsByEmail(userDTO.getEmail());

        Optional<User> userDetails=userRepository.findById(id);

        if (userDetails.get().getBuiltIn()) {
            throw new BadRequestException("You dont have permission to update user info!");
        }

        if (emailExists && !userDTO.getEmail().equals(userDetails.get().getEmail())){
            throw new ConflictException("Error: Email is already in use!");
        }

       userRepository.update(id,userDTO.getFirstName(),userDTO.getLastName()
                ,userDTO.getAddress(),userDTO.getPhone(),userDTO.getEmail(),userDTO.getResetPasswordCode());
    }
*/

    public void createdUser(User user){

        if (userRepository.existsByEmail(user.getEmail())){
            throw new ConflictException("Error: Email is already in use!");
        }

        LocalDateTime createDate=LocalDateTime.now();

        user.setCreateDate(createDate);


        userRepository.save(user);
    }

    public void addLoans(){

    }

    public void getUsersAllInformations(){

    }




}
