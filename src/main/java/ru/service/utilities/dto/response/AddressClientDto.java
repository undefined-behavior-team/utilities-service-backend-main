package ru.service.utilities.dto.response;

import lombok.Value;
import ru.service.utilities.entity.AddressClient;

import java.util.UUID;

/**
 * DTO for {@link AddressClient}
 */
@Value
public class AddressClientDto {
    UUID id;
    String town;
    String street;
    String house;
    String building;
    String apartment;

    public AddressClientDto(AddressClient addressClient){
        this.id = addressClient.getId();
        this.town = addressClient.getTown();
        this.street = addressClient.getStreet();
        this.house = addressClient.getHouse();
        this.building = addressClient.getBuilding();
        this.apartment = addressClient.getApartment();
    }
}