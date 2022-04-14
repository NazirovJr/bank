package njr.bank.service;

import lombok.AllArgsConstructor;
import njr.bank.model.TransferBalanceDto;
import njr.bank.repository.BalanceRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class BankService {

    private final BalanceRepository repository;

    public BigDecimal getBalance(Long accountId) {
        BigDecimal balance = repository.getBalanceForId(accountId);
        if (balance == null) {
            throw  new IllegalArgumentException();
        }
        return balance;
    }

    public BigDecimal addMoney(Long to, BigDecimal amount) {
        BigDecimal currentBalance = repository.getBalanceForId(to);
        if (currentBalance == null) {
            repository.save(to, amount);
            return amount;
        } else {
            BigDecimal updateBalance = currentBalance.add(amount);
            repository.save(to, updateBalance);
            return updateBalance;
        }
    }

    public void makeTransfer(TransferBalanceDto transferBalanceDto) {
        BigDecimal fromBalance = repository.getBalanceForId(transferBalanceDto.getFrom());
        BigDecimal toBalance = repository.getBalanceForId(transferBalanceDto.getTo());
        if (fromBalance == null || toBalance == null) {
            throw new IllegalArgumentException("no Id");
        }
        if (transferBalanceDto.getAmount().compareTo(fromBalance) > 0) {
            throw new IllegalArgumentException("no money");
        }
        BigDecimal updatedFromBalance = fromBalance.subtract(transferBalanceDto.getAmount());
        BigDecimal updatedToBalance = toBalance.add(transferBalanceDto.getAmount());
        repository.save(transferBalanceDto.getFrom(), updatedFromBalance);
        repository.save(transferBalanceDto.getTo(), updatedToBalance);
    }
}
