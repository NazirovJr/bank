package njr.bank.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransferBalanceDto {
    Long from;
    Long to;
    BigDecimal amount;
}
