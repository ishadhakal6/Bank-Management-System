package com.example.bankmanagementsystem.model.service;

import com.example.bankmanagementsystem.model.dto.CustomerDto;
import com.example.bankmanagementsystem.model.entity.Address;
import com.example.bankmanagementsystem.model.entity.ContactInfo;
import com.example.bankmanagementsystem.model.entity.Customer;
import com.example.bankmanagementsystem.model.exception.CustomerNotFoundException;
import com.example.bankmanagementsystem.model.exception.IdNotFoundException;
import com.example.bankmanagementsystem.model.mapper.CustomerMapper;
import com.example.bankmanagementsystem.model.repository.AddressRepository;
import com.example.bankmanagementsystem.model.repository.ContactInfoRepository;
import com.example.bankmanagementsystem.model.repository.CustomerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.example.bankmanagementsystem.model.mapper.CustomerMapper.mapToDto;

@Service
@Transactional
public class CustomerService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    private final AddressRepository addressRepository;

    private final ContactInfoRepository contactInfoRepository;


    public CustomerService(CustomerRepository customerRepository, AddressRepository addressRepository, ContactInfoRepository contactInfoRepository) {
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
        this.contactInfoRepository = contactInfoRepository;
    }

    public CustomerDto createCustomer(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToEntity(customerDto);


        Address address = new Address();
        address.setStreet(customerDto.getStreet());
        address.setCity(customerDto.getCity());

        customer.setAddress(address);
        addressRepository.save(address);


        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setEmail(customerDto.getEmail());
        contactInfo.setPhoneNumber(customerDto.getPhoneNumber());

        customer.setContactInfo(contactInfo);
        contactInfoRepository.save(contactInfo);


        customerRepository.save(customer);

        return customerDto;
    }

    public List<CustomerDto> getCustomer() {
        List<Customer> customerList = customerRepository.findAll();
        List<CustomerDto> customerDtoList = new ArrayList<>();
        for (Customer customer : customerList) {
            CustomerDto customerDto = mapToDto(customer);
            customerDtoList.add(customerDto);
        }
        return customerDtoList;
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElseThrow(()-> new IdNotFoundException("Customer not found with this ID:" +id));

    }


    public CustomerDto updateCustomer(CustomerDto customerDto){
        Customer existingCustomer = customerRepository.findById(customerDto.getId()).orElseThrow(()-> new CustomerNotFoundException("Customer not found"));

        Long addressId=existingCustomer.getAddress().getId();
        Address customerAddress=addressRepository.findById(addressId).get();
        customerAddress.setStreet(customerDto.getStreet());
        customerAddress.setCity(customerDto.getCity());

        existingCustomer.setName(customerDto.getName());

        Long contactId=existingCustomer.getContactInfo().getId();
        ContactInfo contactInfo=contactInfoRepository.findById(contactId).get();
        contactInfo.setEmail(customerDto.getEmail());
        contactInfo.setPhoneNumber(customerDto.getPhoneNumber());

        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return mapToDto(updatedCustomer);
    }


    public void deleteCustomer(Long id) {
        customerRepository.findById(id).orElseThrow(()-> new IdNotFoundException("Cannot delete!"));
        customerRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        customerRepository.findByName(username);
        return null;
    }
}