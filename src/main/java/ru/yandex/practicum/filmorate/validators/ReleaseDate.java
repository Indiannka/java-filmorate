package ru.yandex.practicum.filmorate.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ReleaseDateValidator.class)
@Target( { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ReleaseDate {
    String message() default "{ReleaseDate.invalid.must be not earlier 1985-12-28}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
