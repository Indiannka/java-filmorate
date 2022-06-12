package ru.yandex.practicum.filmorate.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class ReleaseDateValidator implements ConstraintValidator<ReleaseDate, LocalDate> {

    private final LocalDate releaseDate = LocalDate.of(1895, 12, 28);

    @Override
    public boolean isValid(LocalDate releaseDateField, ConstraintValidatorContext context) {
        if (releaseDateField == null) {
            return true;
        }
        return !releaseDateField.isBefore(releaseDate);
    }
}