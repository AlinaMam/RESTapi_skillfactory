package com.example.restapi_skillfactory.controller;

import com.example.restapi_skillfactory.exception.NoSuchElementException;
import com.example.restapi_skillfactory.exception.NotEnoughMoney;
import com.example.restapi_skillfactory.model.UserBalance;
import com.example.restapi_skillfactory.repository.UserBalanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/balance")
@RequiredArgsConstructor
public class UserBalanceController {

    private final UserBalanceRepository userBalanceRepository;

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserBalance>> listAll() {
        return ResponseEntity.status(HttpStatus.OK).body(userBalanceRepository.findAll());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserBalance> getBalance(@PathVariable("id") Integer id) {
        Optional<UserBalance> userBalanceOptional = userBalanceRepository.findById(id);
        if (userBalanceOptional.isEmpty()) {
            throw new NoSuchElementException("Пользователя с" +
                    " ID = " + id + " не существует");
        }
        UserBalance userBalance = userBalanceOptional.get();
        return ResponseEntity.status(HttpStatus.OK).body(userBalance);
    }

    @RequestMapping(value = "/putMoney/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserBalance> putMoney(@PathVariable("id") Integer id, @RequestParam(value = "putMoney") BigDecimal amount) {
        UserBalance userBalance = userBalanceRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Пользователя с ID = " + id + " не существует"));
        userBalance.setBalance(userBalance.getBalance().add(amount));
        UserBalance savedUserBalance = userBalanceRepository.save(userBalance);
        return ResponseEntity.status(HttpStatus.OK).body(savedUserBalance);
    }

    @RequestMapping(value = "/takeMoney/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserBalance> takeMoney(@PathVariable("id") Integer id, @RequestParam(value = "takeMoney") BigDecimal amount) throws NotEnoughMoney {
        UserBalance userBalance = userBalanceRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Пользователя с ID = " + id + " не существует"));
        BigDecimal balance = userBalance.getBalance();
        if (balance.compareTo(amount) == -1) {
            throw new NotEnoughMoney("Недостаточно средств");
        }
        userBalance.setBalance(userBalance.getBalance().subtract(amount));
        UserBalance savedUserBalance = userBalanceRepository.save(userBalance);
        return ResponseEntity.status(HttpStatus.OK).body(savedUserBalance);
    }
}
