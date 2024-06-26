package uni.isw.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
@Table(name = "orden_venta")
public class OrdenVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_orden_venta;
    
    private Integer dni;
    private Boolean estado;
    
    @ManyToOne
    @JoinColumn(name = "dni", referencedColumnName = "dni", insertable = false, updatable = false)
    private Cliente cliente;
    
        public OrdenVenta() {
    }

    public OrdenVenta(Long id_orden_venta, Integer dni, Boolean estado, Cliente cliente) {
        this.id_orden_venta = id_orden_venta;
        this.dni = dni;
        this.estado = estado;
        this.cliente = cliente;
    }
}

