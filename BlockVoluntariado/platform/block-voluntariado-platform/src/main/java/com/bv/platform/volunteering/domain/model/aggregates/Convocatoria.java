package com.bv.platform.volunteering.domain.model.aggregates;

import com.bv.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import com.bv.platform.volunteering.domain.model.valueobjects.EstadoConvocatoria;
import com.bv.platform.volunteering.domain.model.valueobjects.Horario;
import com.bv.platform.volunteering.domain.model.valueobjects.Ubicacion;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Convocatoria extends AbstractDomainAggregateRoot<Convocatoria> {

    @Setter
    private Long id;
    private Long organizationId;
    private String title;
    private String description;
    private String causeType;
    private int totalVacancies;
    private int occupiedVacancies;
    private Horario horario;
    private Ubicacion ubicacion;
    private EstadoConvocatoria status;

    public Convocatoria() {
        this.occupiedVacancies = 0;
        this.status = EstadoConvocatoria.BORRADOR;
    }

    public Convocatoria(Long id, Long organizationId, String title, String description,
                        String causeType, int totalVacancies, int occupiedVacancies,
                        Horario horario, Ubicacion ubicacion, EstadoConvocatoria status) {
        this.id = id;
        this.organizationId = organizationId;
        this.title = title;
        this.description = description;
        this.causeType = causeType;
        this.totalVacancies = totalVacancies;
        this.occupiedVacancies = occupiedVacancies;
        this.horario = horario;
        this.ubicacion = ubicacion;
        this.status = status != null ? status : EstadoConvocatoria.BORRADOR;
    }

    public Convocatoria(Long organizationId, String title, String description,
                        String causeType, int totalVacancies, Horario horario, Ubicacion ubicacion) {
        this(null, organizationId, title, description, causeType, totalVacancies, 0,
                horario, ubicacion, EstadoConvocatoria.BORRADOR);
    }

    public void update(String title, String description, String causeType,
                       int totalVacancies, Horario horario, Ubicacion ubicacion) {
        if (this.status == EstadoConvocatoria.CERRADA) {
            throw new IllegalStateException("No se puede editar una convocatoria cerrada.");
        }
        if (totalVacancies < this.occupiedVacancies) {
            throw new IllegalArgumentException("El número total de vacantes no puede ser menor a las ya ocupadas (" + this.occupiedVacancies + ").");
        }
        this.title = title;
        this.description = description;
        this.causeType = causeType;
        this.totalVacancies = totalVacancies;
        this.horario = horario;
        this.ubicacion = ubicacion;
    }

    public void publish() {
        if (this.status == EstadoConvocatoria.CERRADA) {
            throw new IllegalStateException("No se puede publicar una convocatoria cerrada.");
        }
        this.status = EstadoConvocatoria.PUBLICADA;
    }

    public void close() {
        this.status = EstadoConvocatoria.CERRADA;
    }

    public boolean incrementOccupiedVacancies() {
        if (this.occupiedVacancies >= this.totalVacancies) {
            return false;
        }
        this.occupiedVacancies++;
        if (this.occupiedVacancies >= this.totalVacancies) {
            this.status = EstadoConvocatoria.CERRADA;
        }
        return true;
    }

    public void decrementOccupiedVacancies() {
        if (this.occupiedVacancies > 0) {
            this.occupiedVacancies--;
            if (this.status == EstadoConvocatoria.CERRADA && this.occupiedVacancies < this.totalVacancies) {
                this.status = EstadoConvocatoria.PUBLICADA;
            }
        }
    }

    public boolean hasAvailableVacancies() {
        return this.status == EstadoConvocatoria.PUBLICADA && this.occupiedVacancies < this.totalVacancies;
    }

    public boolean isOpen() {
        return this.status == EstadoConvocatoria.PUBLICADA;
    }

    public int getRemainingVacancies() {
        return Math.max(0, this.totalVacancies - this.occupiedVacancies);
    }
}
