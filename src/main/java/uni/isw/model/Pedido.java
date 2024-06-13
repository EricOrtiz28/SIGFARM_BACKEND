package uni.isw.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_pedido;

    @Column(name = "id_orden_venta")
    private Long idOrdenVenta;
    
    private Long id_medicamento;
    private Integer cantidad;
    private double precio_unitario;

    @ManyToOne
    @JoinColumn(name = "id_orden_venta", referencedColumnName = "id_orden_venta", insertable = false, updatable = false)
    private OrdenVenta ordenVenta;

    @ManyToOne
    @JoinColumn(name = "id_medicamento", referencedColumnName = "id_medicamento", insertable = false, updatable = false)
    private Inventario inventario;
}

