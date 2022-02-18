package org.quevedo.models.hibernate;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NamedQuery(
        name = "get_object_permission_by_objeto",
        query = "select po from EntityPermisosObjetos po where po.objetosByIdObjeto.id = :objeto"
)

@Entity
@Table(name = "permisos_objetos", schema = "UserPermissions", catalog = "")
public class EntityPermisosObjetos {
    private int id;
    private int idUsuario;
    private EntityObjeto objetosByIdObjeto;
    private EntityPermisos permisosByIdPermiso;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

        EntityPermisosObjetos that = (EntityPermisosObjetos) o;

        if (id != that.id) return false;
        return idUsuario == that.idUsuario;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + idUsuario;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "id_objeto", referencedColumnName = "id", nullable = false)
    public EntityObjeto getObjetosByIdObjeto() {
        return objetosByIdObjeto;
    }

    public void setObjetosByIdObjeto(EntityObjeto objetosByIdObjeto) {
        this.objetosByIdObjeto = objetosByIdObjeto;
    }

    @ManyToOne
    @JoinColumn(name = "id_permiso", referencedColumnName = "id", nullable = false)
    public EntityPermisos getPermisosByIdPermiso() {
        return permisosByIdPermiso;
    }

    public void setPermisosByIdPermiso(EntityPermisos permisosByIdPermiso) {
        this.permisosByIdPermiso = permisosByIdPermiso;
    }

    @Override
    public String toString() {
        return "EntityPermisosObjetos{" +
                "id=" + id +
                ", idUsuario=" + idUsuario +
                ", objetosByIdObjeto=" + objetosByIdObjeto +
                ", permisosByIdPermiso=" + permisosByIdPermiso +
                '}';
    }
}
