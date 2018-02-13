package larisa.security;

import larisa.entity.Token;
import larisa.repository.AccountRepository;
import larisa.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * Created by home on 23.02.17.
 */
@Service
public class TokenService implements PersistentTokenRepository {

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    AccountRepository accountRepository;

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        tokenRepository.save(entity(token));
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        Optional<Token> token = tokenRepository.findById(series);
        token.ifPresent(t -> {
            t.setTokenValue(tokenValue);
            t.setDate(lastUsed);
            tokenRepository.save(t);
        });
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        return tokenRepository.findById(seriesId).map(entity ->
                new PersistentRememberMeToken(entity.getAccount().getId(),
                        entity.getId(), entity.getTokenValue(),
                        entity.getDate())).get();
    }

    @Override
    public void removeUserTokens(String username) {

    }

    Token entity(PersistentRememberMeToken token) {
        return new Token(token.getSeries(), accountRepository.findById(token.getUsername()).get(), token.getTokenValue(), token.getDate());
    }

}
