package com.bv.platform.recognition.domain.model.commands;

import com.bv.platform.recognition.domain.model.valueobjects.TipoEvaluador;

public record CreateEvaluacionCommand(
        Long evaluadorId,
        Long evaluadoId,
        TipoEvaluador tipoEvaluador,
        int score,
        String feedback
) {}
