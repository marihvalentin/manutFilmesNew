package br.com.test.mfilmes.manutencaoFilmes.model;

import javax.persistence.*;

@Entity(name = "genero") //transformando a classe em entidade da JPA
public class Genero
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idGen;
    private String tipo;

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
}