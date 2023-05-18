package dev.paul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@SpringBootApplication
@RequestMapping("api/v1/customers")
public class Main {

    private final CustomerRepository customerRepository;

    public Main(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping
    public List<Customer> getCustomer() {
        return customerRepository.findAll();
    }

    @GetMapping("{id}")
    public Optional<Customer> getCustomerById(@PathVariable("id") Integer id){
        return customerRepository.findById(id);
    }

    @PostMapping
    public void addCustomer(@RequestBody NewCustomerRequest request) {
        Customer customer = new Customer();
        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setAge(request.age());
        customerRepository.save(customer);
    }

    record NewCustomerRequest(
            String name,
            String email,
            Integer age
    ){}

    @DeleteMapping("{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Integer id) {
        customerRepository.deleteById(id);
    }

    // assignment
    @PutMapping("{customerId}")
    public void updateCustomer(@PathVariable("customerId") Integer id,
                               @RequestBody Customer cstUpdate) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow();
        existingCustomer.setName(cstUpdate.getName());
        existingCustomer.setAge(cstUpdate.getAge());
        existingCustomer.setEmail(cstUpdate.getEmail());
        customerRepository.save(existingCustomer);
    }

}
