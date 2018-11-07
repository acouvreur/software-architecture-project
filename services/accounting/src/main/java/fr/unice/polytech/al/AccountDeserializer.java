package fr.unice.polytech.al;

import fr.unice.polytech.al.model.Account;
import org.springframework.kafka.support.serializer.JsonDeserializer;

public class AccountDeserializer extends JsonDeserializer<Account> {

    public AccountDeserializer() {
        super(Account.class);
    }
}
