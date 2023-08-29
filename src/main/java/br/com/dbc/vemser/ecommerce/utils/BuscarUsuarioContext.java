package br.com.dbc.vemser.ecommerce.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class BuscarUsuarioContext {

    public Integer idUsuarioLogado(){

        return Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }


}
