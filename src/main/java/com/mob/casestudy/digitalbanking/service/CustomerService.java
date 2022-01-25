package com.mob.casestudy.digitalbanking.service;

import com.mob.casestudy.digitalbanking.dto.CustomerDto;
import com.mob.casestudy.digitalbanking.entity.Customer;
import com.mob.casestudy.digitalbanking.enums.CustomerStatus;
import com.mob.casestudy.digitalbanking.enums.Language;
import com.mob.casestudy.digitalbanking.exceptionResponse.*;
import com.mob.casestudy.digitalbanking.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void saveCustomer(){

        Customer customer=Customer.builder().userName("kep")
                .firstName("kevin").lastName("patel")
                .phoneNumber("9664847593").email("kevinpatel1142@gmail.com")
                .status(CustomerStatus.ACTIVE).preferredLanguage(Language.EN.toString())
                .externalId("1").createdBy("self").createdOn(LocalDateTime.now())
                .updatedBy("k-win").updatedOn(LocalDateTime.now()).build();
        customerRepository.save(customer);
    }


       public void updateCustomer(String userName, CustomerDto customerDto){
        Customer customer = validateAndGetCustomer(userName, customerDto);
        customerRepository.save(customer);
        }

        public Customer validateAndGetCustomer(String userName,CustomerDto customerDto){
            validateUserNameAndCustomerDto(userName,customerDto);
            return getCustomer(userName,customerDto);
        }


        private void validateUserNameAndCustomerDto(String username, CustomerDto customerDto) {

        if (Objects.isNull(username)){
            throw new MandatoryFieldException();
        }

        if (!isPhoneValid(customerDto.getPhoneNumber())){
            throw  new InvalidPhoneNumberException();
        }

        if (!isEmailValid(customerDto.getEmail())){
            throw new InvalidEmailException();
        }

        if (!isLanguageValid(customerDto.getPreferredLanguage())){
            throw new InvalidLanguageException();
        }
    }
    private Customer findCustomerByUserName(String userName) {
        Optional<Customer> customerResultOptional = customerRepository.findByUserName(userName);

        if (customerResultOptional.isEmpty()){
            throw new UserNotFoundException();
        }

        return customerResultOptional.get();
    }

    private Customer getCustomer(String userName,CustomerDto customerDto) {

        Customer customerResult = findCustomerByUserName(userName);

        return mapDtoToEntity(customerDto, customerResult);
    }

    private Customer mapDtoToEntity(CustomerDto customerDto, Customer customerResult) {
        customerResult.setFirstName(customerDto.getFirstName());
        customerResult.setLastName(customerDto.getLastName());
        customerResult.setPhoneNumber(customerDto.getPhoneNumber());
        customerResult.setEmail(customerDto.getEmail());
        customerResult.setPreferredLanguage(customerDto.getPreferredLanguage());
        customerResult.setStatus(CustomerStatus.valueOf(customerDto.getStatus()));

        return customerResult;
    }


    public Customer validateUserNameAndReturnCustomer(String userName, CustomerDto customerDto){

        if (userName.isEmpty() && customerDto ==null){
            throw new MandatoryFieldException();
        }

        Optional<Customer> customerResultOptional = customerRepository.findByUserName(userName);

        if (customerResultOptional.isEmpty()){
            throw new UserNotFoundException();
        }
        Customer customerResult=customerResultOptional.get();


            String firstName = customerDto.getFirstName();
            String lastName = customerDto.getLastName();
            String phoneNumber = customerDto.getPhoneNumber();

            if (!isPhoneValid(phoneNumber)){
                throw  new InvalidPhoneNumberException();
            }

            String email = customerDto.getEmail();

            if (!isEmailValid(email)){
                throw new InvalidEmailException();
            }

            String preferredLanguage = customerDto.getPreferredLanguage();
            String language = preferredLanguage.trim().toUpperCase(Locale.ROOT);

            if (!isLanguageValid(language)){
                throw new InvalidLanguageException();
            }
            String status = customerDto.getStatus();


            customerResult.setFirstName(firstName);
            customerResult.setLastName(lastName);
            customerResult.setPhoneNumber(phoneNumber);
            customerResult.setEmail(email);
            customerResult.setPreferredLanguage(language);
            customerResult.setStatus(CustomerStatus.valueOf(status));

            return customerResult;
        }


    public static boolean isEmailValid(String email) {
        /**
        final Pattern EMAIL_REGEX =
                Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", Pattern.CASE_INSENSITIVE);
        return EMAIL_REGEX.matcher(email).matches();
        Pattern pattern = Pattern.compile(regex);
         **/
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        return email.matches(regex);
    }

    public boolean isPhoneValid(String number){

        return  number.matches("^\\d{10}$");
    }

    public boolean isLanguageValid(String language){
        return   language.equalsIgnoreCase(Language.EN.name())
                || language.equalsIgnoreCase(Language.DE.toString())
                || language.equalsIgnoreCase(Language.FR.toString());

    }

}
