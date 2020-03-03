package com.zenhome.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "consumption", schema = "zenhome")
@EntityListeners(AuditingEntityListener.class)
public class Consumption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Counter counter;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "reported_at")
    @CreatedDate
    private OffsetDateTime reportedAt;

    public Integer getCounterId() {
        return counter != null ? counter.getId() : null;
    }

    public Village getVillage() {
        return counter.getVillage();
    }
}
