package org.quevedo.models.hibernate;

import javax.persistence.*;

@NamedQuery(
        name = "get_permisos_by_ubicacion",
        query = "select p from EntityPermisosUbicaciones p where p.ubicacionesByIdUbicacion.id = :ubicacion"
)
@NamedQuery(
        name = "get_num_usuarios_permisos_ubicacion",
        query = "select count(p) from EntityPermisosUbicaciones p where p.ubicacionesByIdUbicacion.nombre = :ubicacionName"
)

@Entity
@Table(name = "permisos_ubicaciones", schema = "UserPermissions", catalog = "")
public class EntityPermisosUbicaciones {
    private int id;
    private int idUbicacion;
    private int idUsuario;
    private EntityUbicaciones ubicacionesByIdUbicacion;
    private EntityPermisos permisosByIdPermiso;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "id_usuario")
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityPermisosUbicaciones that = (EntityPermisosUbicaciones) o;

        if (id != that.id) return false;
        if (idUbicacion != that.idUbicacion) return false;
        if (idUsuario != that.idUsuario) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + idUbicacion;
        result = 31 * result + idUsuario;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "id_ubicacion", referencedColumnName = "id", nullable = false)
    public EntityUbicaciones getUbicacionesByIdUbicacion() {
        return ubicacionesByIdUbicacion;
    }

    public void setUbicacionesByIdUbicacion(EntityUbicaciones ubicacionesByIdUbicacion) {
        this.ubicacionesByIdUbicacion = ubicacionesByIdUbicacion;
    }

    @ManyToOne
    @JoinColumn(name = "id_permiso", referencedColumnName = "id", nullable = false)
    public EntityPermisos getPermisosByIdPermiso() {
        return permisosByIdPermiso;
    }

    public void setPermisosByIdPermiso(EntityPermisos permisosByIdPermiso) {
        this.permisosByIdPermiso = permisosByIdPermiso;
    }
}
