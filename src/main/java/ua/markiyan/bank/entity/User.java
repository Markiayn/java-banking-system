package ua.markiyan.bank.entity;

import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotations.*;


@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter @Setter
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE users SET status = 'DELETED' WHERE id = ?")
@SQLRestriction("status = 'ACTIVE'")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // --- ПОЛЯ ---
    @Column(name = "full_name", nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private UserStatus status = UserStatus.ACTIVE;

    // --- ENUM ---
    public enum UserStatus {
        ACTIVE,
        DELETED,
        BANNED
    }
}
