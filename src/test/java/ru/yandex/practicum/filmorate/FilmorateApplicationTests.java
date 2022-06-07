package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class FilmorateApplicationTests {

	private Validator validator;
	private Film film = new Film();
	private User user = new User();

	@BeforeEach
	public void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();

		film.setName("filmName");
		film.setDescription("filmDescription");
		film.setReleaseDate(LocalDate.now());
		film.setDuration(100L);

		user.setId(1);
		user.setName("UserName");
		user.setBirthday(LocalDate.now().minusDays(1));
		user.setLogin("userLogin");
		user.setEmail("useremai@mail.ru");
	}


	@Test
	@DisplayName("Film.class Description field with size over 200 symbols does not pass validation")
	void filmDescriptionFieldSizeValidation() {
		film.setDescription("Пятеро друзей ( комик-группа «Шарло»), приезжают в город Бризуль." +
						" Здесь они хотят разыскать господина Огюста Куглова, который задолжал им деньги," +
						" а именно 20 миллионов. о Куглов, который за время «своего отсутствия»," +
						" стал кандидатом Коломбани.");
		Set<ConstraintViolation<Film>> violations = validator.validate(film);
		assertThat(violations.size()).isEqualTo(1);
		violations.forEach(action -> {
			assertThat(action.getMessage()).isEqualTo("Описание не должно превышать 200 символов");
			assertThat(action.getPropertyPath().toString()).hasToString("description");
		});
	}

	@Test
	@DisplayName("Film.class name field does not pass validation if null")
	void filmNameFieldNotNullValidation() {
		film.setName(null);
		Set<ConstraintViolation<Film>> violations = validator.validate(film);
		assertThat(violations.size()).isEqualTo(2);
		violations.forEach(action -> {
			assertThat(action.getPropertyPath().toString()).hasToString("name");
		});
	}

	@Test
	@DisplayName("Film.class name field does not pass validation if blank")
	void filmNameFieldNotBlankValidation() {
		film.setName("  ");
		Set<ConstraintViolation<Film>> violations = validator.validate(film);
		assertThat(violations.size()).isEqualTo(1);
		violations.forEach(action -> {
			assertThat(action.getMessage()).isEqualTo("Поле не должно быть пустым");
			assertThat(action.getPropertyPath().toString()).hasToString("name");
		});
	}

	@Test
	@DisplayName("Film.class duration field does not pass validation if value is negative")
	void filmDurationFieldPositiveValueValidation() {
		film.setDuration((long) -3);
		Set<ConstraintViolation<Film>> violations = validator.validate(film);
		assertThat(violations.size()).isEqualTo(1);
		System.out.println(violations);
		violations.forEach(action -> {
		assertThat(action.getMessage()).isEqualTo("Длительность фильма должна быть больше 0");
		assertThat(action.getPropertyPath().toString()).hasToString("duration");
	});
	}

	@Test
	@DisplayName("Film.class releaseDate field does not pass validation if value is before 1895-12-28")
	void filmReleaseDateFieldValidation() {
		film.setReleaseDate(LocalDate.of(1895, 12,27));
		Set<ConstraintViolation<Film>> violations = validator.validate(film);
		assertThat(violations.size()).isEqualTo(1);

		violations.forEach(action -> {
			assertThat(action.getMessage()).isEqualTo("Дата выхода фильма не должна быть раньше 1895-12-28");
			assertThat(action.getPropertyPath().toString()).hasToString("releaseDate");
		});
	}

	@Test
	@DisplayName("User.class email field does not pass validation if value is non-compliant")
	void userEmailFieldValidation() {
		user.setEmail("mail");
		Set<ConstraintViolation<User>> violations = validator.validate(user);
		System.out.println(violations);
		assertThat(violations.size()).isEqualTo(1);
		violations.forEach(action -> {
			assertThat(action.getMessage()).isEqualTo("должно иметь формат адреса электронной почты");
			assertThat(action.getPropertyPath().toString()).hasToString("email");
		});
	}

	@Test
	@DisplayName("User.class login field does not pass validation if null")
	void userLoginFieldNotNullValidation() {
		user.setLogin(null);
		Set<ConstraintViolation<User>> violations = validator.validate(user);
		assertThat(violations.size()).isEqualTo(2);
		violations.forEach(action -> {
			assertThat(action.getPropertyPath().toString()).hasToString("login");
		});
	}

	@Test
	@DisplayName("User.class login field does not pass validation if blank")
	void userLoginFieldNotBlankValidation() {
		user.setLogin("  ");
		Set<ConstraintViolation<User>> violations = validator.validate(user);
		assertThat(violations.size()).isEqualTo(1);
		violations.forEach(action -> {
			assertThat(action.getMessage()).isEqualTo("Поле не должно быть пустым");
			assertThat(action.getPropertyPath().toString()).hasToString("login");
		});
	}

	@Test
	@DisplayName("User.class login BirthDate does not pass validation if it is in the future")
	void userBirthDateFieldNotNullValidation() {
		user.setBirthday(LocalDate.of(2200,01,01));
		Set<ConstraintViolation<User>> violations = validator.validate(user);
		assertThat(violations.size()).isEqualTo(1);
		violations.forEach(action -> {
			assertThat(action.getMessage()).isEqualTo("должно содержать прошедшую дату");
			assertThat(action.getPropertyPath().toString()).hasToString("birthday");
		});
	}
}
