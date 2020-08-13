package br.com.test.mfilmes.manutencaoFilmes.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Entity(name = "filme") //transformando a classe em entidade da JPA
public class Filme
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //hibernate gera id auto-increment
    private int idFil;
    private String titulo;
    private String diretor;
    @ManyToOne
    private Genero genero;
    @Length(max = 1000)
    private String sinopse;
    private int ano;

    public Filme()
    {
        //construtor padrão
    }

    //construtor para cadastro de novos filmes
    public Filme(String titulo, String diretor, Genero genero, String sinopse, int ano)
    {
        super();
        this.titulo = titulo;
        this.diretor = diretor;
        this.genero = genero;
        this.sinopse = sinopse;
        this.ano = ano;
    }

    //getters and setters
    public int getIdFil()
    {
        return idFil;
    }

    public void setIdFil(int idFil)
    {
        this.idFil = idFil;
    }


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

    public void setAno(int ano)
    {
        this.ano = ano;
    }

    public Genero getGenero()
    {
        return genero;
    }

    public void setGenero(Genero genero)
    {
        this.genero = genero;
    }

}
