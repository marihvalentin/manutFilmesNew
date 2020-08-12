package br.com.test.mfilmes.manutencaoFilmes.controller.form;

import br.com.test.mfilmes.manutencaoFilmes.model.Filme;
import br.com.test.mfilmes.manutencaoFilmes.model.Genero;
import br.com.test.mfilmes.manutencaoFilmes.repository.FilmeRepository;
import br.com.test.mfilmes.manutencaoFilmes.repository.GeneroRepository;
import com.sun.istack.NotNull;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

public class FilmeForm
{
    @NotNull @NotEmpty
    private String titulo;
    @NotNull @NotEmpty
    private String diretor;
    @NotNull @NotEmpty
    private String tipoGenero;
    @Length(max = 1000)
    private String sinopse;
    private int ano;

    public String getTitulo()
    {
        return titulo;
    }

    public void setTitulo(String titulo)
    {
        this.titulo = titulo;
    }

    public String getDiretor()
    {
        return diretor;
    }

    public void setDiretor(String diretor)
    {
        this.diretor = diretor;
    }

    public String getTipoGenero()
    {
        return tipoGenero;
    }

    public void setTipoGenero(String tipoGenero)
    {
        this.tipoGenero = tipoGenero;
    }

    public String getSinopse()
    {
        return sinopse;
    }

    public void setSinopse(String sinopse)
    {
        this.sinopse = sinopse;
    }

    public int getAno()
    {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    //converte um form em um filme
    public Filme converter(GeneroRepository generoRepository)
    {   Genero genero = generoRepository.findByTipo(tipoGenero);
        return new Filme(titulo, diretor, genero, sinopse, ano);
    }

    public Filme editarFilme(Integer id, FilmeRepository filmeRepository)
    {
        Filme filme = filmeRepository.getOne(id);

        filme.setTitulo(this.titulo);
        filme.setDiretor(this.diretor);
        //filme.setGenero();
        filme.setSinopse(this.sinopse);
        filme.setAno(this.ano);

        return filme;
    }
}
