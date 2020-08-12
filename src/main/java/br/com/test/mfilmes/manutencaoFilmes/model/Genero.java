package br.com.test.mfilmes.manutencaoFilmes.model;

import javax.persistence.*;
import java.util.List;

@Entity(name = "genero") //transformando a classe em entidade da JPA
public class Genero
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idGen;
    private String tipo;
    @OneToMany(mappedBy = "genero")
    private List<Filme> listaFilmes;

    //getters e setters
    public Integer getIdGen()
    {
        return idGen;
    }

    public void setIdGen(Integer idGen)
    {
        this.idGen = idGen;
    }

    public String getTipo()
    {
        return tipo;
    }

    public void setTipo(String tipo)
    {
        this.tipo = tipo;
    }

    public List<Filme> getListaFilmes() {
        return listaFilmes;
    }

    public void setListaFilmes(List<Filme> listaFilmes) {
        this.listaFilmes = listaFilmes;
    }
}