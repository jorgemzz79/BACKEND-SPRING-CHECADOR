package com.backend.APIRest.model.entidades.checador;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name="usuarios")
public class Usuario implements UserDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 4, max = 100, message = " debe tener entre 4 y 100 caracteres")
    @NotNull
    private String nombre;

    @NotNull
    @Size(min = 4, max = 100, message = " debe tener entre 4 y 100 caracteres")
    @Column(unique = true)
    @Email
    private String email;


    @Column(name = "caja",nullable=false , unique=true)
    Integer caja;

    @Column(name = "cajero",nullable=false , unique=false)
    Integer cajero;

    @NotNull
    private String password;

    @ManyToOne
    @JoinColumn(name = "perfil_nombre")
    private Perfil perfil;

    @ColumnDefault("true")
    private boolean activo;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        // Agregar roles como GrantedAuthority
        if (perfil != null)
        {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + perfil.getNombre()));
            // Agregar privilegios relacionados con el rol
            if (perfil.getPrivilegios() != null)
            {
                for (Privilegio privilegio : perfil.getPrivilegios())
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + privilegio.getNombre()));

            }
    }

    return authorities;
}
    @Override
    public String getUsername() {return email;}
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return activo;
    }


}
