package com.ada.moviebattle.error;

public enum ResponseErrorEnum {
    //ABSTRACT RESPONSE
    NOT_AUTH("NOT_AUTH - Não autorizado"),
    NOT_FOUND("NOT_FOUND - Não encontrado"),
    NO_GAME_STARTED("NO_GAME_STARTED - Nenhum jogo em andamento"),
    AUTH001("AUTH001 - Usuário já registrado");

    private final String text;

    ResponseErrorEnum(final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
