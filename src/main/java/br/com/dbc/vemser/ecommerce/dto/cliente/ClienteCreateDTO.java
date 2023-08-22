package br.com.dbc.vemser.ecommerce.dto.cliente;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteCreateDTO {

    @NotNull
    @NotBlank
    @Size(min = 2, max = 50, message = "O nome deve ter no minimo 2 caracteres")
    @Schema(description = "Nome da Pessoa", example = "Maria Matos", required = true)
    private String nome;

    @NotNull(message = "O número não pode ser nulo")
    @Size(min = 11, max = 11, message = "O número precisa ter 11 caracteres")
    @Schema(description = "Número de contato", required = true, example = "48999008877", minLength = 11, maxLength = 11)
    private String telefone;

    @Email
    @NotNull(message = "Email não pode ser nulo")
    @NotBlank(message = "Email não pode ser vazio")
    @Schema(description = "Endereço de e-mail da pessoa", example = "nome.sobrenome@mail.com")
    private String email;

    //    @CPF
    @NotNull
    @Size(min = 11, max = 11, message = "O CPF precisa ter 11 caracteres")
    @Schema(description = "CPF da pessoa", example = "12345678909", minLength = 11, maxLength = 11)
    private String cpf;

    @NotNull
    @Size(min = 3, max = 20, message = "Senha precisa ter no mínimo 3 caracteres e no máximo 20")
    @Schema(description = "Senha do cliente", example = "senha123", minLength = 3, maxLength = 20)
    private String senha;

}