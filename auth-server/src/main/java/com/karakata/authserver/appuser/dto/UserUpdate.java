package com.karakata.authserver.appuser.dto;



import com.karakata.authserver.address.model.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.usertype.UserType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdate {
    private Long id;
    private String email;
    private String mobile;
    private UserType userType;
    private Address address;
}
