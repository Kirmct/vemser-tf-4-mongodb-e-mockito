package br.com.dbc.vemser.ecommerce.entity.enums;

public enum TipoSetor {
    INFANTIL(0),
    FEMININO(1),
    MASCULINO(2),

    UNISSEX(3);

    private final int value;

    TipoSetor(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
