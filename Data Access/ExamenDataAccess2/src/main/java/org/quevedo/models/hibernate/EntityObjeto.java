package org.quevedo.models.hibernate;

import javax.persistence.*;

@NamedQuery(
        name = "get_objects_by_ubicacion",
        query = "select o from EntityObjeto o where o.ubicacionesByIdUbicacion.id = :ubicacion"
)

@Entity
@Table(name = "objetos", schema = "UserPermissions", catalog = "")
public class EntityObjeto {
    private int id;
    private String nombre;
    private String descripcion;
    private String marca;
    private String tipo;
    private EntityUbicaciones ubicacionesByIdUbicacion;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Basic
    @Column(name = "descripcion")
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Basic
    @Column(name = "marca")
    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    @Basic
    @Column(name = "tipo")
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityObjeto that = (EntityObjeto) o;

        if (id != that.id) return false;
        if (nombre != null ? !nombre.equals(that.nombre) : that.nombre != null) return false;
        if (descripcion != null ? !descripcion.equals(that.descripcion) : that.descripcion != null) return false;
        if (marca != null ? !marca.equals(that.marca) : that.marca != null) return false;
        if (tipo != null ? !tipo.equals(that.tipo) : that.tipo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (descripcion != null ? descripcion.hashCode() : 0);
        result = 31 * result + (marca != null ? marca.hashCode() : 0);
        result = 31 * result + (tipo != null ? tipo.hashCode() : 0);
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
}
