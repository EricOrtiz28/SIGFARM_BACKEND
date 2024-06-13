package uni.isw.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    private Integer dni;  

    private String nombres;
    private String apellidos;
    private String direccion;
    private Long telefono;  
    private String correo;

 
}

