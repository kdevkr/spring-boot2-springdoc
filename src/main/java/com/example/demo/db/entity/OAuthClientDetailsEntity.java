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
@Table(name = "oauth_client_details")
public class OAuthClientDetailsEntity {
    @Id
    @Column(name = "client_id", length = 256, nullable = false)
    private String clientId;

    @Column(name = "client_secret", length = 256)
    private String clientSecret;

    @Column(name = "resource_ids", length = 256)
    private String resourceIds;

    @Column(name = "scope", length = 256)
    private String scope;

    @Column(name = "authorized_grant_types", length = 256)
    private String authorizedGrantTypes;

    @Column(name = "web_server_redirect_uri", length = 256)
    private String webServerRedirectUri;

    @Column(name = "authorities", length = 256)
    private String authorities;

    @Column(name = "access_token_validity")
    private Integer accessTokenValidity;

    @Column(name = "refresh_token_validity")
    private Integer refreshTokenValidity;

    @Column(name = "additional_information", length = 4096)
    private String additionalInformation;

    @Column(name = "autoapprove", length = 256)
    private String autoapprove;

    @ManyToOne
    @JoinColumn(name = "user_uid", nullable = false, referencedColumnName = "uid")
    private UserEntity user;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}
