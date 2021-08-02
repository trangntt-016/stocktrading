package com.canada.edu.stocktrading.model;

import com.canada.edu.stocktrading.service.utils.UserIdPrefixed;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User {
    @Id
    @Column(name = "user_id",updatable = false,nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "user_gen")
    @GenericGenerator(
            name = "user_gen",
            strategy = "com.canada.edu.stocktrading.service.utils.UserIdPrefixed",
            parameters = {
                    @Parameter(name = UserIdPrefixed.INCREMENT_PARAM,value = "1"),
                    @Parameter(name = UserIdPrefixed.CODE_NUMBER_SEPARATOR_PARAMETER,value = "_"),
                    @Parameter(name = UserIdPrefixed.NUMBER_FORMAT_PARAMETER,value = "%03d")
            }
    )
    private String userId;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name="authentication_type", length=10)
    private AuthenticationType authenticationType;

}
