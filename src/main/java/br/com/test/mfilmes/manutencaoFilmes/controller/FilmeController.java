package br.com.test.mfilmes.manutencaoFilmes.controller;

import br.com.test.mfilmes.manutencaoFilmes.controller.dto.ConsultaFilmeDto;
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
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class FilmeController
{
    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private GeneroRepository generoRepository;

    //LISTAR FILMES
    @RequestMapping(value = "/listar", method = RequestMethod.GET)
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

    //FILTRAR LISTAGEM POR GENERO
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

    //FILTRAR LISTAGEM POR TITULO
    private List<Filme> getFilmeByTitulo(@RequestParam(required = false) String titulo,
                                         @RequestParam(required = false) String tipoGenero, List<Filme> filmes) {
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

    //INCLUIR FILME
    @RequestMapping(value = "/incluirFilme", method = RequestMethod.POST)
    @Transactional
    public ResponseEntity<FilmeDto> incluir(@RequestBody @Valid FilmeForm form, UriComponentsBuilder uriBuilder) {
        Filme filme = form.converter(generoRepository);
        filmeRepository.save(filme);

        URI uri = uriBuilder.path("/index/{id}").buildAndExpand(filme.getIdFil()).toUri();
        return ResponseEntity.created(uri).body(new FilmeDto(filme));
    }

    //CONSULTAR FILME
    @RequestMapping(value = "/consultar/{id}", method = RequestMethod.GET)
    public ResponseEntity<ConsultaFilmeDto> consultar(@PathVariable Integer id)
    {
        Optional<Filme> filme = filmeRepository.findById(id);
        if(filme.isPresent())
        {
            //se o id passado existir como filme cadastrado, executar a consulta e retornar o filme
            return ResponseEntity.ok(new ConsultaFilmeDto(filme.get()));
        }
        return ResponseEntity.notFound().build(); //senão, retornar 404
    }

    //EDITAR FILME
    @RequestMapping(value = "/editar/{id}", method = RequestMethod.PUT)
    @Transactional
    public ResponseEntity<FilmeDto> editar(@PathVariable Integer id, @RequestBody @Valid FilmeForm form)
    {
        Optional<Filme> optional = filmeRepository.findById(id);
        if(optional.isPresent())
        {
            //se o id passado existir como filme cadastrado, editar e retornar o filme no formato definido na FilmeDto
            Filme filme = form.editarFilme(id, filmeRepository, generoRepository);
            return ResponseEntity.ok(new FilmeDto(filme));
        }
        return ResponseEntity.notFound().build(); //senão, retornar 404
    }

    //EXCLUIR FILME
    @RequestMapping(value = "/excluir/{id}", method = RequestMethod.DELETE)
    @Transactional
    public ResponseEntity<?> excluir(@PathVariable Integer id)
    {
        Optional<Filme> optional = filmeRepository.findById(id);
        if(optional.isPresent())
        {
            //se o id passado existir como filme cadastrado, excluir o filme
            filmeRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build(); //senão, retornar 404
    }

}
