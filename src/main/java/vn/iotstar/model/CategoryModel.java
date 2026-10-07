package vn.iotstar.model;

import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryModel {

    private Long categoryId;

    @NotEmpty(message = "{category.name.required}")
    @Length(min = 3, message = "{category.name.length}")
    private String name;

    private Boolean isEdit = false;

}
