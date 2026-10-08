package com.bv.platform.volunteering.domain.model.valueobjects;

public record Ubicacion(
        String district,
        String addressLine,
        Double latitude,
        Double longitude
) {
    public static Ubicacion of(String district, String addressLine, Double latitude, Double longitude) {
        return new Ubicacion(district, addressLine, latitude, longitude);
    }
}
