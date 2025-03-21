package ru.service.utilities.dto.response;


import lombok.Value;
import ru.service.utilities.entity.MeterReading;

import java.time.LocalDate;

@Value
public class MeterReadingDTO {
    public String meterName;
    public int data;
    public LocalDate createdAt;

    public MeterReadingDTO(MeterReading meterReading) {
        this.meterName = meterReading.getMeter().getName();
        this.data = meterReading.getMeter().getData();
        this.createdAt = meterReading.getCreatedAt();
    }
}
