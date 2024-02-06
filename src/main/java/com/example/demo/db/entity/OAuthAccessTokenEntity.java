package com.example.demo.db.entity;

import java.time.Instant;
import javax.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "oauth_access_token")
public class OAuthAccessTokenEntity {

    @Id
    @Column(name = "authentication_id", nullable = false)
    private String authenticationId;

    @Column(name = "token_id", length = 256)
    private String tokenId;

    @Column(name = "user_name", length = 256)
    private String username;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false, referencedColumnName = "client_id")
    private OAuthClientDetailsEntity oauthClientDetails;

    @Lob
    @Column(name = "token")
    private byte[] token;

    @Lob
    @Column(name = "authentication")
    private byte[] authentication;

    @OneToOne
    @JoinColumn(name = "refresh_token", referencedColumnName = "token_id")
    private OAuthRefreshTokenEntity refreshToken;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}
