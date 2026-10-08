package com.bv.platform.recognition.domain.model.aggregates;

import com.bv.platform.recognition.domain.model.valueobjects.TipoEvaluador;
import com.bv.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class Evaluacion extends AbstractDomainAggregateRoot<Evaluacion> {

    @Setter
    private Long id;
    private Long evaluadorId;
    private Long evaluadoId;
    private TipoEvaluador tipoEvaluador;
    private int score;
    private String feedback;
    private LocalDateTime fecha;

    public Evaluacion() {
        this.fecha = LocalDateTime.now();
    }

    public Evaluacion(Long id, Long evaluadorId, Long evaluadoId,
                      TipoEvaluador tipoEvaluador, int score,
                      String feedback, LocalDateTime fecha) {
        this.id = id;
        this.evaluadorId = evaluadorId;
        this.evaluadoId = evaluadoId;
        this.tipoEvaluador = tipoEvaluador;
        this.score = Math.max(1, Math.min(5, score));
        this.feedback = feedback;
        this.fecha = fecha != null ? fecha : LocalDateTime.now();
    }

    public Evaluacion(Long evaluadorId, Long evaluadoId, TipoEvaluador tipoEvaluador, int score, String feedback) {
        this(null, evaluadorId, evaluadoId, tipoEvaluador, score, feedback, LocalDateTime.now());
    }
}
