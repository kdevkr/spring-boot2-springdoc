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
@Table(name = "oauth_refresh_token")
public class OAuthRefreshTokenEntity {

    @Id
    @Column(name = "token_id", length = 256, nullable = false)
    private String tokenId;

    @Lob
    @Column(name = "token")
    private byte[] token;

    @Lob
    @Column(name = "authentication")
    private byte[] authentication;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}
