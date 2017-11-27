package larisa.security;

import larisa.entity.Token;
import larisa.repository.AccountRepository;
import larisa.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

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
        Token token = tokenRepository.findOne(series);
        if (token != null) {
            token.setTokenValue(tokenValue);
            token.setDate(lastUsed);
            tokenRepository.save(token);
        }
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        return token(tokenRepository.findOne(seriesId));
    }

    @Override
    public void removeUserTokens(String username) {

    }

    Token entity(PersistentRememberMeToken token) {
        return new Token(token.getSeries(), accountRepository.findOne(token.getUsername()), token.getTokenValue(), token.getDate());
    }

    PersistentRememberMeToken token(Token entity) {
        if (entity != null) {
            return new PersistentRememberMeToken(entity.getAccount().getId(), entity.getId(), entity.getTokenValue(), entity.getDate());
        }
        return null;
    }
}
