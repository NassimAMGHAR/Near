package com.near.friendly.core.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.near.friendly.core.Constants;
import com.near.friendly.core.utils.DigestUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 */
@Data
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "nr_user_session")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserSession implements Serializable {

    @Id
    private String id;

    @JsonIgnore
    @NotNull
    @Column(nullable = false)
    private String value;

    @Column(name = "session_date")
    private LocalDate date;

    @Size(min = 0, max = 39)
    @Column(name = "ip_address", length = 39)
    private String ipAddress;

    @Column(name = "user_agent")
    private String userAgent;

    @ManyToOne
    @JsonIgnore
    private User user;


    public UserSession(final User user, final String ipAddress, final String userAgent){

        this.setId(DigestUtils.generate(Constants.DEFAULT_USER_SESSION_ID_LENGTH));
        this.setValue(DigestUtils.generate(Constants.DEFAULT_USER_SESSION_TOKEN_LENGTH));
        this.setDate(LocalDate.now());

        this.setUser(user);
        this.setIpAddress(ipAddress);
        this.setUserAgent(userAgent);

    }

    public void setUserAgent(String userAgent) {
        //truncate user agent value if sup than 255 chars.
        if (userAgent.length() >= 255) {
            this.userAgent = userAgent.substring(0, 255 - 1);
        } else {
            this.userAgent = userAgent;
        }
    }
}
