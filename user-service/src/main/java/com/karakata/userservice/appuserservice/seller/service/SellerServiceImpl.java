package com.karakata.userservice.appuserservice.seller.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.karakata.userservice.appuserservice.admin.exception.AdminNotFoundException;
import com.karakata.userservice.appuserservice.admin.model.Admin;
import com.karakata.userservice.appuserservice.admin.repository.AdminRepository;
import com.karakata.userservice.appuserservice.appuser.exception.UserNotFoundException;
import com.karakata.userservice.appuserservice.appuser.model.User;
import com.karakata.userservice.appuserservice.appuser.repository.UserRepository;
import com.karakata.userservice.appuserservice.makerchecker.exception.MakerCheckerNotFoundException;
import com.karakata.userservice.appuserservice.makerchecker.model.MakerChecker;
import com.karakata.userservice.appuserservice.makerchecker.repository.MakerCheckerRepository;
import com.karakata.userservice.appuserservice.roles.exception.RoleNotFoundException;
import com.karakata.userservice.appuserservice.roles.model.Role;
import com.karakata.userservice.appuserservice.roles.repository.RoleRepository;
import com.karakata.userservice.appuserservice.seller.exception.SellerNotFoundException;
import com.karakata.userservice.appuserservice.seller.model.Seller;
import com.karakata.userservice.appuserservice.seller.repository.SellerRepository;
import com.karakata.userservice.appuserservice.staticdata.EntityType;
import com.karakata.userservice.appuserservice.staticdata.RequestStatus;
import com.karakata.userservice.appuserservice.staticdata.RequestType;
import com.karakata.userservice.appuserservice.staticdata.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class SellerServiceImpl implements SellerService {
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private MakerCheckerRepository makerCheckerRepository;
    @Autowired
    private AdminRepository adminRepository;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Override
    public void addSeller(Seller seller) throws JsonProcessingException {
        MakerChecker makerChecker=new MakerChecker();
        ObjectMapper objectMapper=new ObjectMapper();
        if (!seller.getUser().getPassword().equals(seller.getUser().getConfirmPassword())) {
            throw new SellerNotFoundException("Not a matching passwords");
        }
        if (!validatePhoneNumber(seller)) {
            throw new SellerNotFoundException("Mobile phone must be in one of these formats: " +
                    "10 or 11 digit, 0000 000 0000, 000 000 0000, 000-000-0000, 000-000-0000 ext0000");
        }

        Role role = roleRepository.findByRoleName("Seller").orElseThrow(() -> new RoleNotFoundException("Role not found"));
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        seller.getUser().setRoles(roles);
        seller.getUser().setUserType(UserType.SELLER);
//        seller.getUser().setPassword(passwordEncoder.encode(seller.getUser().getPassword()));
        makerChecker.setEntityType(EntityType.SELLER);
        makerChecker.setNewState(objectMapper.writeValueAsString(seller));
        makerChecker.setRequestStatus(RequestStatus.PENDING);
        makerChecker.setRequestType(RequestType.CREATE);
        makerCheckerRepository.save(makerChecker);
    }

    @Override
    public void approveProductCreation(String makerCheckerId, Long checkerId, RequestStatus requestStatus) throws JsonProcessingException {
        ObjectMapper objectMapper=new ObjectMapper();
        Admin admin=adminRepository.findById(checkerId)
                .orElseThrow(()->new AdminNotFoundException("Admin ID "+ checkerId+" not found"));
        MakerChecker makerChecker=makerCheckerRepository.findById(makerCheckerId)
                .orElseThrow(()->new MakerCheckerNotFoundException("Maker Checker ID "+makerCheckerId+" not found"));
        Seller seller=objectMapper.readValue(makerChecker.getNewState(), Seller.class);
        makerChecker.setOldState(objectMapper.writeValueAsString(seller));
        makerChecker.setAdminId(admin.getId());
        makerChecker.setRequestStatus(RequestStatus.APPROVED);
        if (makerChecker.getRequestStatus()==RequestStatus.APPROVED){
            sellerRepository.save(seller);
            makerChecker.setNewState(objectMapper.writeValueAsString(seller));
            makerChecker.setEntityId(seller.getId());
        }
        makerCheckerRepository.save(makerChecker);

    }

    @Override
    public Seller fetchSellerById(Long id) {
        return sellerRepository.findById(id).orElseThrow(() -> new SellerNotFoundException("Seller ID " + id + " not found"));
    }

    @Override
    public Seller fetchSellerByNameOrTaxId(String sellerName) {
        return sellerRepository.findBySellerNameOrTaxId(sellerName, sellerName)
                .orElseThrow(()->new SellerNotFoundException("Seller name or tax id " + sellerName + " not found"));
    }

    @Override
    public Seller fetchByUsernameOrEmailOrMobile(String searchKey) {
        User user = userRepository.findByUsernameOrEmailOrMobile(searchKey, searchKey, searchKey)
                .orElseThrow(() -> new UserNotFoundException("User " + searchKey + " not found"));
        Seller seller = sellerRepository.findByUserNameOrEmailOrMobile(user);
        return seller;
    }

    @Override
    public List<Seller> fetchAllSellers(int pageNumber, int pageSize) {
        return sellerRepository.findAll(PageRequest.of(pageNumber, pageSize)).toList();
    }

    @Override
    public void editSeller(String makerCheckerId,Seller seller) throws JsonProcessingException {
        ObjectMapper objectMapper=new ObjectMapper();
        MakerChecker makerChecker=makerCheckerRepository.findById(makerCheckerId)
                .orElseThrow(()->new MakerCheckerNotFoundException("MakeChecker ID "+makerCheckerId+" not found"));
        Seller savedSeller = sellerRepository.findById(makerChecker.getEntityId())
                .orElseThrow(() -> new SellerNotFoundException("Seller " + makerChecker.getEntityId() + " not found"));
        makerChecker.setOldState(objectMapper.writeValueAsString(savedSeller));
        makerChecker.setRequestStatus(RequestStatus.PENDING);
        makerChecker.setRequestType(RequestType.UPDATE);
        makerChecker.setEntityType(makerChecker.getEntityType());
        savedSeller.setCompanyRepresentative(seller.getCompanyRepresentative());
        savedSeller.setNatureOfBusiness(seller.getNatureOfBusiness());
        makerChecker.setNewState(objectMapper.writeValueAsString(savedSeller));
        makerCheckerRepository.save(makerChecker);
    }

    @Override
    public void approveSellerUpdate(String makerCheckerId, Long checkerId, RequestStatus requestStatus) throws JsonProcessingException {
        ObjectMapper objectMapper=new ObjectMapper();
        Admin admin=adminRepository.findById(checkerId)
                .orElseThrow(()->new AdminNotFoundException("Admin ID "+ checkerId+" not found"));
        MakerChecker makerChecker=makerCheckerRepository.findById(makerCheckerId)
                .orElseThrow(()->new MakerCheckerNotFoundException("MakeChecker ID "+makerCheckerId+" not found"));
        Seller seller=objectMapper.readValue(makerChecker.getNewState(), Seller.class);
        makerChecker.setRequestStatus(requestStatus);
        makerChecker.setAdminId(admin.getId());
        if (makerChecker.getRequestStatus()==RequestStatus.APPROVED){
            sellerRepository.save(seller);
            makerChecker.setNewState(objectMapper.writeValueAsString(seller));
            makerChecker.setEntityId(seller.getId());
        }
        makerCheckerRepository.save(makerChecker);

    }

    @Override
    public void deleteSeller(String makerCheckerId) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        MakerChecker makerChecker=makerCheckerRepository.findById(makerCheckerId)
                .orElseThrow(()->new MakerCheckerNotFoundException("MakeChecker ID "+makerCheckerId+" not found"));
        Seller seller=sellerRepository.findById(makerChecker.getEntityId())
                .orElseThrow(() -> new SellerNotFoundException("Seller " + makerChecker.getEntityId() + " not found"));
        makerChecker.setOldState(objectMapper.writeValueAsString(seller));
        makerChecker.setEntityType(EntityType.SELLER);
        makerChecker.setRequestStatus(RequestStatus.PENDING);
        makerChecker.setRequestType(RequestType.DELETE);
        makerCheckerRepository.save(makerChecker);
    }

    @Override
    public void approveSellerDeletion(String makerCheckerId, Long checkerId, RequestStatus requestStatus) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Admin admin=adminRepository.findById(checkerId)
                .orElseThrow(()->new AdminNotFoundException("Admin ID "+checkerId+" not found"));
        MakerChecker makerChecker=makerCheckerRepository.findById(makerCheckerId)
                .orElseThrow(()->new MakerCheckerNotFoundException("MakeChecker ID "+makerCheckerId+" not found"));

        Seller seller=objectMapper.readValue(makerChecker.getOldState(),Seller.class);
        makerChecker.setRequestStatus(requestStatus);
        makerChecker.setEntityId(seller.getId());
        makerChecker.setAdminId(admin.getId());
        if (makerChecker.getRequestStatus()==RequestStatus.APPROVED){
            sellerRepository.deleteById(seller.getId());
            makerChecker.setNewState(objectMapper.writeValueAsString(seller));
        }
        makerCheckerRepository.save(makerChecker);
    }

    private static boolean validatePhoneNumber(Seller seller) {
        // validate phone numbers of format "1234567890"
        if (seller.getUser().getMobile().matches("\\d{10}")) {
            return true;
        }
        // validate phone numbers of format "12345678901"
        else if (seller.getUser().getMobile().matches("\\d{11}")) {
            return true;
        }
        // validating phone number with -, . or spaces
        else if (seller.getUser().getMobile().matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) {
            return true;
        }
        // validating phone number with extension length from 3 to 5
        else if (seller.getUser().getMobile().matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")) {
            return true;
        }
        // validating phone number where area code is in braces ()
        else if (seller.getUser().getMobile().matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) {
            return true;
        }    // Validation for India numbers
        else if (seller.getUser().getMobile().matches("\\d{4}[-\\.\\s]\\d{3}[-\\.\\s]\\d{3}")) {
            return true;
        } else if (seller.getUser().getMobile().matches("\\(\\d{5}\\)-\\d{3}-\\d{3}")) {
            return true;
        } else if (seller.getUser().getMobile().matches("\\(\\d{4}\\)-\\d{3}-\\d{3}")) {
            return true;
        }    // return false if nothing matches the input
        else {
            return false;
        }
    }
}
