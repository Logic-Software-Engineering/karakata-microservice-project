package com.karakata.userservice.appuserservice.buyer.service;



import com.karakata.userservice.appuserservice.appuser.exception.UserNotFoundException;
import com.karakata.userservice.appuserservice.appuser.model.User;
import com.karakata.userservice.appuserservice.appuser.repository.UserRepository;
import com.karakata.userservice.appuserservice.buyer.exception.BuyerNotFoundException;
import com.karakata.userservice.appuserservice.buyer.model.Buyer;
import com.karakata.userservice.appuserservice.buyer.repository.BuyerRepository;
import com.karakata.userservice.appuserservice.roles.exception.RoleNotFoundException;
import com.karakata.userservice.appuserservice.roles.model.Role;
import com.karakata.userservice.appuserservice.roles.repository.RoleRepository;
import com.karakata.userservice.appuserservice.staticdata.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private BuyerRepository buyerRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Override
    public Buyer addBuyer(Buyer buyer) {
        if (!validatePhoneNumber(buyer)) {
            throw new BuyerNotFoundException("Mobile phone must be in one of these formats: " +
                    "10 or 11 digit, 0000 000 0000, 000 000 0000, 000-000-0000, 000-000-0000 ext0000");
        }
        if (!buyer.getUser().getPassword().equals(buyer.getUser().getConfirmPassword()))
            throw new BuyerNotFoundException("Passwords do not match");
        Role role = roleRepository.findByRoleName("Buyer").orElseThrow(() -> new RoleNotFoundException("Role does not exist"));
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        buyer.getUser().setRoles(roles);
        buyer.getUser().setUserType(UserType.BUYER);
//        buyer.getUser().setPassword(passwordEncoder.encode(buyer.getUser().getPassword()));
        return buyerRepository.save(buyer);
    }

    @Override
    public Buyer fetchBuyerByUsernameOrEmailOrMobile(String searchKey) {
        User user = userRepository.findByUsernameOrEmailOrMobile(searchKey, searchKey, searchKey)
                .orElseThrow(() -> new UserNotFoundException("User " + searchKey + " not found"));
        Buyer buyer = buyerRepository.findByUser(user);
        return buyer;
    }

    @Override
    public List<Buyer> fetchAllBuyersOrByName(String searchKey, int pageNumber, int pageSize) {
        if (searchKey.equals("")) {
            return buyerRepository.findAll(PageRequest.of(pageNumber, pageSize)).stream().toList();
        } else return buyerRepository.findByName(searchKey, searchKey, PageRequest.of(pageNumber, pageSize));
    }

    @Override
    public Buyer editBuyer(Buyer buyer, Long id) {
        Buyer savedBuyer = buyerRepository.findById(id)
                .orElseThrow(() -> new BuyerNotFoundException("Buyer " + id + " not found"));
        if (Objects.nonNull(buyer.getFirstName()) && !"".equalsIgnoreCase(buyer.getFirstName())) {
            savedBuyer.setFirstName(buyer.getFirstName());
        }
        if (Objects.nonNull(buyer.getLastName()) && !"".equalsIgnoreCase(buyer.getLastName())) {
            savedBuyer.setLastName(buyer.getLastName());
        }
        return buyerRepository.save(savedBuyer);
    }

    @Override
    public void deleteBuyer(Long id) {
        if (buyerRepository.existsById(id)) {
            buyerRepository.deleteById(id);
        }
    }

    private static boolean validatePhoneNumber(Buyer buyer) {
        // validate phone numbers of format "1234567890"
        if (buyer.getUser().getMobile().matches("\\d{10}")) {
            return true;
        }
        // validate phone numbers of format "12345678901"
        else if (buyer.getUser().getMobile().matches("\\d{11}")) {
            return true;
        }
        // validating phone number with -, . or spaces
        else if (buyer.getUser().getMobile().matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) {
            return true;
        }
        // validating phone number with extension length from 3 to 5
        else if (buyer.getUser().getMobile().matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")) {
            return true;
        }
        // validating phone number where area code is in braces ()
        else if (buyer.getUser().getMobile().matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) {
            return true;
        }    // Validation for India numbers
        else if (buyer.getUser().getMobile().matches("\\d{4}[-\\.\\s]\\d{3}[-\\.\\s]\\d{3}")) {
            return true;
        } else if (buyer.getUser().getMobile().matches("\\(\\d{5}\\)-\\d{3}-\\d{3}")) {
            return true;
        } else if (buyer.getUser().getMobile().matches("\\(\\d{4}\\)-\\d{3}-\\d{3}")) {
            return true;
        }    // return false if nothing matches the input
        else {
            return false;
        }
    }
}
