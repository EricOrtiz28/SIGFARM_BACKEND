package uni.isw.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
@Table(name = "cliente")
public class Cliente {

    @Id
    private Integer dni;  

    private String nombres;
    private String apellidos;
    private String direccion;
    private Long telefono;  
    private String correo;
    
    public Cliente() {
    }
    
    public Cliente(Integer dni, String nombres, String apellidos, String direccion, Long telefono, String correo) {
        this.dni = dni;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
    }

 
}

