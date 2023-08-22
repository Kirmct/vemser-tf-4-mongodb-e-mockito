package br.com.dbc.vemser.ecommerce.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class LoginDTO {

    @NotNull
    private String login;

    @NotNull
    private String senha;

}
