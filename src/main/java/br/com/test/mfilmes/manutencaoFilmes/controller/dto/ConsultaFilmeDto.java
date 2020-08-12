package br.com.test.mfilmes.manutencaoFilmes.controller.dto;

import br.com.test.mfilmes.manutencaoFilmes.model.Filme;

public class ConsultaFilmeDto
{
    private String titulo;
    private String diretor;
    private String tipoGenero;
    private String sinopse;
    private int ano;

    public ConsultaFilmeDto(Filme filme)
    {
        this.titulo = filme.getTitulo();
        this.diretor = filme.getDiretor();
        this.tipoGenero = filme.getGenero().getTipo();
        this.sinopse = filme.getSinopse();
        this.ano = filme.getAno();
    }

    public String getTitulo()
    {
        return titulo;
    }

    public String getDiretor()
    {
        return diretor;
    }

    public String getTipoGenero()
    {
        return tipoGenero;
    }

    public String getSinopse()
    {
        return sinopse;
    }

    public int getAno()
    {
        return ano;
    }

}
