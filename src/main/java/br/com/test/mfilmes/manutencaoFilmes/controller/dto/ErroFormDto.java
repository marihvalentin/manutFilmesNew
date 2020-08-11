package br.com.test.mfilmes.manutencaoFilmes.controller.dto;

public class ErroFormDto
{
    private String campoErro;
    private String mensagemErro;

    public ErroFormDto(String campoErro, String menssagemErro)
    {
        this.campoErro = campoErro;
        this.mensagemErro = menssagemErro;
    }

    public String getCampoErro()
    {
        return campoErro;
    }

    public String getMensagemErro()
    {
        return mensagemErro;
    }
}
