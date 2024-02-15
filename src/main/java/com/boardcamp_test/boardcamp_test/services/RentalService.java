package com.boardcamp_test.boardcamp_test.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boardcamp_test.boardcamp_test.dtos.RentalDto;
import com.boardcamp_test.boardcamp_test.exceptions.NotFoundException;
import com.boardcamp_test.boardcamp_test.exceptions.UnprocessableException;
import com.boardcamp_test.boardcamp_test.models.CustomerModel;
import com.boardcamp_test.boardcamp_test.models.GameModel;
import com.boardcamp_test.boardcamp_test.models.RentalModel;
import com.boardcamp_test.boardcamp_test.repositories.CustomerRepository;
import com.boardcamp_test.boardcamp_test.repositories.GameRepository;
import com.boardcamp_test.boardcamp_test.repositories.RentalRepository;

@Service
public class RentalService {
    final RentalRepository rentalRepository;
    final GameRepository gameRepository;
    final CustomerRepository customerRepository;

    public RentalService(RentalRepository rentalRepository, GameRepository gameRepository,
            CustomerRepository customerRepository) {
        this.rentalRepository = rentalRepository;
        this.gameRepository = gameRepository;
        this.customerRepository = customerRepository;
    }

    public List<RentalModel> listRentals(){
        return rentalRepository.findAll();
    }

    @Transactional
    public RentalModel createRental(RentalDto rentalDto){
        Optional <CustomerModel> customerOptional = customerRepository.findById(rentalDto.getCustomerId());
        Optional <GameModel> gameOptional  = gameRepository.findById(rentalDto.getGameId());

        if(!customerOptional.isPresent() || !gameOptional.isPresent()){
            throw new NotFoundException("Usuário ou jogo não encontrados. Verifique a identificação.");
        }

        if(gameOptional.get().getStockTotal() -1 < 0){
            throw new UnprocessableException("Não há jogos disponíveis para serem alugados");
        }

        gameOptional.get().setRentStockTotal();
        
        RentalModel rental = new RentalModel(rentalDto, gameOptional.get(), customerOptional.get());
        gameRepository.save(gameOptional.get());
        rentalRepository.save(rental);
        return rental;

    }

    @Transactional
    public RentalModel updateRental(Long idRental){
        Optional<RentalModel> rentalOptional = rentalRepository.findById(idRental);

        if(!rentalOptional.isPresent()){
            throw new NotFoundException("Aluguel não encontrado!");
        }

        RentalModel rental = rentalOptional.get();
        if(rental.getReturnDate() != null){
            throw new UnprocessableException("Aluguel já finalizado!");
        }

        rental.getGame().setDevolutionStockTotal();
        
        rental.updateRent(rental.getGame().getPricePerDay());

        return rental;
    }

}