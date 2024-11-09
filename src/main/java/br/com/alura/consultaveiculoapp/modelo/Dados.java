package br.com.alura.consultaveiculoapp.modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Dados (String codigo,
                     String nome) {
}
