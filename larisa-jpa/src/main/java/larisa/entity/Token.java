package larisa.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by home on 26.03.17.
 */
@Entity
@Table(name = "token")
public class Token extends DefaultEntity<String> {
    @Id
    @Column(name = "series", nullable = false)
    private String id;

    @ManyToOne
    @JoinColumn(name = "login", nullable = false)
    private Account account;

    @Column(name = "token_value", nullable = false)
    private String tokenValue;

    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;


    public Token() {
    }

    public Token(String id) {
        this.id = id;
    }

    public Token(String id, Account account, String tokenValue, Date date) {
        this.id = id;
        this.account = account;
        this.tokenValue = tokenValue;
        this.date = date;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
