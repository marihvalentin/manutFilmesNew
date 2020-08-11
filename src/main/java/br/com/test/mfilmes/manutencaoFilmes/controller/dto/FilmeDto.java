package br.com.test.mfilmes.manutencaoFilmes.controller.dto;

import br.com.test.mfilmes.manutencaoFilmes.model.Filme;

import java.util.List;
import java.util.stream.Collectors;

public class FilmeDto
{
    private String titulo;
    private String diretor;

    public FilmeDto(Filme filme)
    {
        this.titulo = titulo;
        this.diretor = diretor;
    }

    public String getTitulo()
    {
        return titulo;
    }

    public String getDiretor()
    {
        return diretor;
    }

    //converte a lista de filmes para filmedto
    public static List<FilmeDto> converter(List<Filme> filmes)
    {
        return filmes.stream().map(FilmeDto::new).collect(Collectors.toList());
    }
}
