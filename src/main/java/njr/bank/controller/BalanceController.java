package njr.bank.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import njr.bank.model.TransferBalanceDto;
import njr.bank.service.BankService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Slf4j
@RestController("/bank")
@AllArgsConstructor
public class BalanceController {

    private final BankService bankService;

    @GetMapping("/{accountId}")
    public BigDecimal getBalance(@PathVariable Long accountId) {
        return bankService.getBalance(accountId);
    }

    @PostMapping("/add")
    public BigDecimal addMoney(@RequestBody TransferBalanceDto transferBalanceDto) {
        return bankService.addMoney(transferBalanceDto.getTo(), transferBalanceDto.getAmount());
    }

    @PostMapping("/transfer")
    public void transfer(@RequestBody TransferBalanceDto transferBalanceDto) {
         bankService.makeTransfer(transferBalanceDto);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handle(IllegalArgumentException e) {
        log.error(e.getMessage());
        return "Argument not founded";
    }
}
