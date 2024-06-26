package uni.isw.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
@Table(name = "inventario")
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_medicamento;

    private String nombre_medicamento;
    private Integer stock;
    private Double precio_unitario;
    private String ubicacion_del_medicamento;
    
    public Inventario() {
    }

    public Inventario(Long id_medicamento, String nombre_medicamento, Integer stock, Double precio_unitario, String ubicacion_del_medicamento) {
        this.id_medicamento = id_medicamento;
        this.nombre_medicamento = nombre_medicamento;
        this.stock = stock;
        this.precio_unitario = precio_unitario;
        this.ubicacion_del_medicamento = ubicacion_del_medicamento;
    }
}

