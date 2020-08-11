package br.com.test.mfilmes.manutencaoFilmes.repository;

import br.com.test.mfilmes.manutencaoFilmes.model.Filme;
import br.com.test.mfilmes.manutencaoFilmes.model.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GeneroRepository extends JpaRepository<Genero, Integer>
{
    Genero findByTipo(String tipoGenero);

    //List<Filme> findByTipo(String tipogen);
}
