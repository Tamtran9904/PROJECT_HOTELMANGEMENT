package mock.project.hotelmanagement.dto;

import lombok.Data;

@Data
public class BaseDto {
    private Long id;

    private String createdDate;

    private String modifiedDate;

    private String createdBy;

    private String modifiedBy;
}
