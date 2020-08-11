package br.com.test.mfilmes.manutencaoFilmes.repository;

import br.com.test.mfilmes.manutencaoFilmes.model.Filme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilmeRepository extends JpaRepository<Filme, Integer>
{
    List<Filme> findByTitulo(String titulo);
}
