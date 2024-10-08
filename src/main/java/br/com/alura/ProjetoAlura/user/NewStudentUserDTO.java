package br.com.alura.ProjetoAlura.user;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import static br.com.alura.ProjetoAlura.user.Role.STUDENT;

public class NewStudentUserDTO {

    @NotNull
    @Length(min = 3, max = 50)
    private String name;

    @NotBlank(message = "o campo email nao pode estar vazio")
    @Email(message = "o email nao e valido")
    private String email;

    @NotNull
    @Length(min = 8, max = 16, message="a senha deve conter entre 8 e 16 caracteres")
    private String password;

    public NewStudentUserDTO() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User toModel() {
        return new User(name, email, STUDENT, password);
    }

}
