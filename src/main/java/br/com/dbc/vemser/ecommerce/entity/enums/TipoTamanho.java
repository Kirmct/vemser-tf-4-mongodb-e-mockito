package br.com.dbc.vemser.ecommerce.entity.enums;

public enum TipoTamanho {
    PP(0),
    P(1),
    M(2),
    G(3),
    GG(4),
    XG(5),
    XGG(6),
    EG(7),
    T_UNICO(8);


    private final int value;

    TipoTamanho(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
