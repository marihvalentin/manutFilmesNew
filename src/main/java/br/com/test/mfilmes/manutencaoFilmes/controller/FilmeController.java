package br.com.test.mfilmes.manutencaoFilmes.controller;

import br.com.test.mfilmes.manutencaoFilmes.controller.dto.FilmeDto;
import br.com.test.mfilmes.manutencaoFilmes.controller.form.FilmeForm;
import br.com.test.mfilmes.manutencaoFilmes.model.Filme;
import br.com.test.mfilmes.manutencaoFilmes.model.Genero;
import br.com.test.mfilmes.manutencaoFilmes.repository.FilmeRepository;
import br.com.test.mfilmes.manutencaoFilmes.repository.GeneroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FilmeController
{
    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private GeneroRepository generoRepository;

    @RequestMapping(value = "/listarFilmes", method = RequestMethod.GET)
    public List<FilmeDto> listarFilmes(@RequestParam(required = false) String titulo,
                                       @RequestParam(required = false) String tipoGenero)
    {
        List<Filme> filmes = new ArrayList<>();

        if (!(titulo == null))
        {
            filmes = getFilmeByTitulo(titulo, tipoGenero, filmes);
        }
        else
        {
            getFilmeByGenero(tipoGenero, filmes);
        }

        return FilmeDto.converter(filmes);
    }

    private void getFilmeByGenero(@RequestParam(required = false) String tipoGenero, List<Filme> filmes) {
        if (!(tipoGenero == null))
        {
            List<Genero> listaGenero = generoRepository.findByTipoContainingIgnoreCase(tipoGenero);
            List<Filme> finalFilmes = filmes;
            listaGenero.forEach(genero -> {
                finalFilmes.addAll(genero.getListaFilmes());
            });
        }
        else {
            filmes.addAll(filmeRepository.findAll());
        }
    }

    private List<Filme> getFilmeByTitulo(@RequestParam(required = false) String titulo, @RequestParam(required = false) String tipoGenero, List<Filme> filmes) {
        filmes.addAll(filmeRepository.findByTituloContainingIgnoreCase(titulo));
        if (!(tipoGenero == null))
        {
            List<Genero> listaGenero = generoRepository.findByTipoContainingIgnoreCase(tipoGenero);
            filmes = filmes.stream().filter(filme -> {
               return listaGenero.stream().anyMatch(genero -> {
                   return genero.equals(filme.getGenero());
               });
            }).collect(Collectors.toList());
        }
        return filmes;
    }

    @RequestMapping(value = "/incluirFilme", method = RequestMethod.POST)
    public ResponseEntity<FilmeDto> incluirFilme(@RequestBody @Valid FilmeForm form, UriComponentsBuilder uriBuilder) {
        Filme filme = form.converter(generoRepository);
        filmeRepository.save(filme);

        URI uri = uriBuilder.path("/index/{id}").buildAndExpand(filme.getIdFil()).toUri();
        return ResponseEntity.created(uri).body(new FilmeDto(filme));
    }

    @RequestMapping(value = "/consultarFilme/{id}", method = RequestMethod.GET)
    public FilmeDto consultar(@PathVariable Integer id)
    {
        Filme filme = filmeRepository.getOne(id);
        return new FilmeDto(filme);
    }

}
