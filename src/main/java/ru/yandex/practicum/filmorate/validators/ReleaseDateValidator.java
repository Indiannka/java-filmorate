package ru.yandex.practicum.filmorate.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class ReleaseDateValidator implements ConstraintValidator<ReleaseDate, LocalDate> {

    @Override
    public boolean isValid(LocalDate releaseDateField, ConstraintValidatorContext context) {
        LocalDate releaseDate = LocalDate.of(1895, 12, 28);
        if (releaseDateField == null) {
            return true;
        }
        return !releaseDateField.isBefore(releaseDate);
    }
}
