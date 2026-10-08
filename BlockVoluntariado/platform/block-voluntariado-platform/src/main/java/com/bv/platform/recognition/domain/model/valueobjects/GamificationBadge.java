package com.bv.platform.recognition.domain.model.valueobjects;

public record GamificationBadge(
        String code,
        String title,
        String description,
        String iconUrl
) {
    public static GamificationBadge bronze() {
        return new GamificationBadge(
                "BRONZE_VOLUNTEER",
                "Voluntario de Bronce",
                "Completó sus primeras horas de servicio social con compromiso.",
                "/assets/badges/bronze.png"
        );
    }

    public static GamificationBadge silver() {
        return new GamificationBadge(
                "SILVER_VOLUNTEER",
                "Voluntario de Plata",
                "Alcanzó más de 20 horas acumuladas de impacto social verificado.",
                "/assets/badges/silver.png"
        );
    }

    public static GamificationBadge gold() {
        return new GamificationBadge(
                "GOLD_VOLUNTEER",
                "Voluntario de Oro",
                "Superó las 50 horas de voluntariado activo demostrando liderazgo.",
                "/assets/badges/gold.png"
        );
    }

    public static GamificationBadge platinum() {
        return new GamificationBadge(
                "PLATINUM_VOLUNTEER",
                "Líder Social Platino",
                "Más de 100 horas de dedicación transformando vidas y comunidades.",
                "/assets/badges/platinum.png"
        );
    }
}
