package com.boardcamp_test.boardcamp_test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.boardcamp_test.boardcamp_test.dtos.CustomerDto;
import com.boardcamp_test.boardcamp_test.exceptions.ConflictException;
import com.boardcamp_test.boardcamp_test.exceptions.NotFoundException;
import com.boardcamp_test.boardcamp_test.models.CustomerModel;
import com.boardcamp_test.boardcamp_test.repositories.CustomerRepository;
import com.boardcamp_test.boardcamp_test.services.CustomerService;

@SpringBootTest
class CustomerUnitTests {

	@InjectMocks
	private CustomerService customerService;

	@Mock
	private CustomerRepository customerRepository;


	@Test
	void givenReapeatedCPF_whenCreatingCustomer_ThenThrowsError(){

		String cpf = "01234567891";
		CustomerDto customerDto = new CustomerDto("Josias", cpf);

		doReturn(Optional.of(true)).when(customerRepository).findByCpf(cpf);

		ConflictException exception = assertThrows(ConflictException.class,() -> customerService.createCustomer(customerDto));


		assertNotNull(exception);
		assertEquals("Cpf já cadastrado!", exception.getMessage());
		verify(customerRepository, times(1)).findByCpf(cpf);
		verify(customerRepository, times(0)).save(any());
	
	}

	@Test
	void givenValidCpf_whenCreatingUser_thenCreatesCustomer(){
		String cpf = "01234567891";
		CustomerDto customerDto = new CustomerDto("Josias", cpf);
		CustomerModel customer = new CustomerModel(customerDto);

		doReturn(Optional.empty()).when(customerRepository).findByCpf(cpf);
		doReturn(customer).when(customerRepository).save(any());
	
		CustomerModel result = customerService.createCustomer(customerDto);
		
		verify(customerRepository, times(1)).findByCpf(cpf);
		verify(customerRepository, times(1)).save(any());
		assertEquals(customer, result);
	}	


	
	@Test
	void givenCustomerId_whenSearchCustomer_thenThrowsError(){
		Long id = 1L;
		
		doReturn(Optional.empty()).when(customerRepository).findById(id);
		
		NotFoundException exception = assertThrows(NotFoundException.class, () -> customerService.findCustomerById(id));
		
		assertNotNull(exception);
		assertEquals("Usuário não encontrado!", exception.getMessage());
		verify(customerRepository,times(1)).findById(id);
	}
	
	@Test
	void givenInexistendCustomerId_whenSearchCustomer_thenReturnCustomer(){
		Long id = 1L;
		CustomerModel customer = new CustomerModel(id, "josias", "01234567891");

		doReturn(Optional.of(customer)).when(customerRepository).findById(id);
		CustomerModel result = customerService.findCustomerById(id);

		assertEquals(customer, result);
		verify(customerRepository,times(1)).findById(id);
	}

	

}