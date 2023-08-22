package br.com.dbc.vemser.ecommerce.gerarSenha;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CriadorDeSenhas {

    public static void main(String[] args) throws Exception {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String senha = bCryptPasswordEncoder.encode("123");
        System.out.println(senha);
        //$2a$10$fP3fNbhDrkixHZHOqW4zKu9QdYiIWkhxH8NIXWcq7AQiUXAHivZEO

        String minhaSenhaCript = "$2a$10$GmzooTT.LrDzaH5U76ktJe20NcgDg0pbUBUuqB./jClx7xLggsu92";
        boolean matches = bCryptPasswordEncoder.matches("123", minhaSenhaCript);
        System.out.println(matches);
        //true ou false
    }

}
