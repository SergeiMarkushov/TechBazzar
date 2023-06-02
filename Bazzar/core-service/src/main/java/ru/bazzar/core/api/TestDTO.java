package ru.bazzar.core.api;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.math.BigDecimal;
@Data
@AllArgsConstructor
public class TestDTO {
    @NotBlank(message = "----------title не должен быть пустым")
    private String title;
    @Min(value = 5, message = "----------min - is {value} --5")
    private int number;
    @DecimalMin(value = "0.1", inclusive = true, message = "----------min is {value}")
    @Digits(integer=6, fraction=2)
    private BigDecimal bigDecimalPrice;
    @Email(message = "----------Строка должна быть правильно сформированным адресом электронной почты")
    private String email;
    private boolean status;

//    private Long id;
//    @NotBlank
//    @Length(min = 2, max = 100)
//    private String title;
//    @NotBlank
//    @Size(min = 2, max = 1000)
//    private String description;
//    @NotBlank
//    @Size(min = 10, max = 200)
//    private String organizationTitle;
//    @Digits(integer=6, fraction=2)
//    private BigDecimal price;
//    @Min(0)// TODO: 01.06.2023 0-конфликт?
//    private int quantity;
//    @NotNull
//    private boolean isConfirmed;




}
