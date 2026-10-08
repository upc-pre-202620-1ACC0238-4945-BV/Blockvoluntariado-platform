package com.bv.platform.recognition.infrastructure.persistence.jpa.entities;

import com.bv.platform.recognition.domain.model.valueobjects.TipoEvaluador;
import com.bv.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "evaluaciones")
@Getter
@Setter
@NoArgsConstructor
public class EvaluacionPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "evaluador_id", nullable = false)
    private Long evaluadorId;

    @Column(name = "evaluado_id", nullable = false)
    private Long evaluadoId;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_evaluador", nullable = false, length = 20)
    private TipoEvaluador tipoEvaluador;

    @Column(name = "score", nullable = false)
    private int score;

    @Column(name = "feedback", columnDefinition = "TEXT")
    private String feedback;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;
}
