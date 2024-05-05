package we.hack.securityservice.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "roles")
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 16)
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
