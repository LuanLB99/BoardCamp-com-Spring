package com.boardcamp_test.boardcamp_test.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import com.boardcamp_test.boardcamp_test.dtos.RentalDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "rentals")
public class RentalModel {

    
    public RentalModel(RentalDto rentalDto, GameModel game, CustomerModel customer) {
        this.game = game;
        this.customer = customer;
        this.rentDate = setNowDate();
        this.daysRented = rentalDto.getDaysRented();
        this.returnDate = null;
        this.delayFee = BigDecimal.ZERO.setScale(2);
        this.originalPrice = setOriginalPrice(daysRented, game.getPricePerDay());
    }


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String rentDate;

    @Column(nullable = false)
    @Positive(message = "A quantidade de dias alugados deve ser maior que zero.")
    private Long daysRented;

    @Column
    private String returnDate;

    @Column(nullable = false)
    private BigDecimal originalPrice;

    @Column(nullable = false)
    private BigDecimal delayFee;

    @ManyToOne
    @JoinColumn(name = "costumerId")
    private CustomerModel customer;

    @ManyToOne
    @JoinColumn(name = "gameId")
    private GameModel game;

    private String setNowDate(){
        LocalDate date = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }

    private BigDecimal setOriginalPrice(Long daysRented, BigDecimal pricePerDay){
        BigDecimal daysRentedDecimal = new BigDecimal(daysRented);
        return pricePerDay.multiply(daysRentedDecimal);
    }

    public void updateRent(BigDecimal pricePerDay){
        this.returnDate = setNowDate();

        LocalDate formatedRentDate = LocalDate.parse(rentDate);
        LocalDate formatedReturnDate = LocalDate.parse(getReturnDate());
        this.delayFee = calculateDelay(formatedRentDate, daysRented, formatedReturnDate, pricePerDay);
    }

    private static BigDecimal calculateDelay(LocalDate rentDate, Long daysRented, LocalDate returnDate, BigDecimal pricePerDay){
        long diasAtraso = ChronoUnit.DAYS.between(rentDate, returnDate) - daysRented;

        if (diasAtraso > 0) {
            BigDecimal delayFee = pricePerDay.multiply(BigDecimal.valueOf(diasAtraso));
            return delayFee.setScale(2, RoundingMode.HALF_UP);
        } else {
            return BigDecimal.ZERO;
        }
    }
}