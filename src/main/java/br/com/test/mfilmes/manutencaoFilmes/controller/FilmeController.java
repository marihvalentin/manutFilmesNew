package br.com.test.mfilmes.manutencaoFilmes.controller;

import br.com.test.mfilmes.manutencaoFilmes.controller.dto.FilmeDto;
import br.com.test.mfilmes.manutencaoFilmes.controller.form.FilmeForm;
import br.com.test.mfilmes.manutencaoFilmes.model.Filme;
import br.com.test.mfilmes.manutencaoFilmes.repository.FilmeRepository;
import br.com.test.mfilmes.manutencaoFilmes.repository.GeneroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
public class FilmeController
{
    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private GeneroRepository generoRepository;

    @RequestMapping("/")
    public String index()
    {
        return "index";
    }

    @RequestMapping(value = "/listarFilmes", method = RequestMethod.GET)
    @ResponseBody
    public List<FilmeDto> listarFilmes()
    {
        /*if (!(titulo == null))
        {
            List<Filme> filmes = filmeRepository.findByTitulo(titulo);
            return FilmeDto.converter(filmes);
        }
        /*else if (!(tipogen == null))
        {
            List<Filme> filmes = generoRepository.findByGeneroTipogen(tipogen);
            return FilmeDto.converter(filmes);
        }*/

        List<Filme> filmes = filmeRepository.findAll(); //consulta e traz todos os registros do banco de dados
        return FilmeDto.converter(filmes);
    }

    @RequestMapping(value = "/incluirFilme", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<FilmeDto> incluirFilme(@RequestBody FilmeForm form, UriComponentsBuilder uriBuilder)
    {
        Filme filme = form.converter(generoRepository);
        filmeRepository.save(filme);

        URI uri = uriBuilder.path("/index/{id}").buildAndExpand(filme.getIdFil()).toUri();
        return ResponseEntity.created(uri).body(new FilmeDto(filme));
    }

}
