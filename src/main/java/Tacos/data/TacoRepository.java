package Tacos.data;

import org.springframework.data.repository.CrudRepository;

import Tacos.Taco;

public interface TacoRepository extends CrudRepository<Taco, Long>{
}
