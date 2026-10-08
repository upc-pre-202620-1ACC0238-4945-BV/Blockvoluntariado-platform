package com.bv.platform.volunteering.interfaces.rest.resources;

public record UbicacionResource(
        String district,
        String addressLine,
        Double latitude,
        Double longitude
) {}
