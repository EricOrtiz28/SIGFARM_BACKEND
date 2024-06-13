package uni.isw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uni.isw.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}