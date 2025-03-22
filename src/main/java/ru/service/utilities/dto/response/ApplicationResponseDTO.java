package ru.service.utilities.dto.response;

import lombok.Value;
import ru.service.utilities.entity.Application;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
public class ApplicationResponseDTO {
    public UUID id;
    public short applicationType;
    public String name;
    public String description;
    public LocalDateTime createdAt;
    public LocalDateTime updateUt;
    public int status;

    public ApplicationResponseDTO(Application Application){
        this.id = Application.getId();
        this.applicationType = Application.getApplicationType();
        this.name = Application.getName();
        this.description = Application.getDescription();
        this.createdAt = Application.getCreatedAt();
        this.updateUt = Application.getUpdatedAt();
        this.status = Application.getStatus();
    }
}
