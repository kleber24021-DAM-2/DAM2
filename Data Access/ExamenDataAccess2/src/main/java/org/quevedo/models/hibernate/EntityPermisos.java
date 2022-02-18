package org.quevedo.models.hibernate;

import javax.persistence.*;

@NamedQuery(
        name = "get_all_permisos",
        query = "from EntityPermisos"
)

@Entity
@Table(name = "permisos", schema = "UserPermissions", catalog = "")
public class EntityPermisos {
    private int id;
    private String descripcion;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "descripcion")
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityPermisos that = (EntityPermisos) o;

        if (id != that.id) return false;
        if (descripcion != null ? !descripcion.equals(that.descripcion) : that.descripcion != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (descripcion != null ? descripcion.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EntityPermisos{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
