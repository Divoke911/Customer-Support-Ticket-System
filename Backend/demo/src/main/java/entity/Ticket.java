package entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
@SQLDelete(sql = "UPDATE tickets SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_name", nullable = false, length = 255)
    private String customerName;

    @Column(nullable = false, length = 255)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Category category;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private Status status = Status.Open;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
    

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    

    public enum Category {
        Payment, Technical, General
    }

    public enum Status {
        Open, In_Progress, Resolved;

        
        @com.fasterxml.jackson.annotation.JsonCreator
        public static Status fromValue(String value) {
            if (value == null) return null;
            return switch (value.trim()) {
                case "Open"        -> Open;
                case "In Progress",
                     "In_Progress" -> In_Progress;
                case "Resolved"    -> Resolved;
                default -> throw new IllegalArgumentException("Unknown status: " + value);
            };
        }

        @com.fasterxml.jackson.annotation.JsonValue
        public String toValue() {
            return this == In_Progress ? "In Progress" : this.name();
        }
    }
}
