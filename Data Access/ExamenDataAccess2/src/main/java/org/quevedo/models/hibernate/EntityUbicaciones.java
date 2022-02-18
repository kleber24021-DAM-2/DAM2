package org.quevedo.models.hibernate;

import javax.persistence.*;

@NamedQuery(
        name = "delete_ubicacion",
        query = "delete from EntityUbicaciones u where u.id = :ubicacion"
)

@NamedQuery(
        name = "get_all_ubicaciones",
        query = "from EntityUbicaciones "
)

@Entity
@Table(name = "ubicaciones", schema = "UserPermissions", catalog = "")
public class EntityUbicaciones {
    private int id;
    private String nombre;
    private String edificio;
    private String planta;
    private String puesto;

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
    @Column(name = "edificio")
    public String getEdificio() {
        return edificio;
    }

    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }

    @Basic
    @Column(name = "planta")
    public String getPlanta() {
        return planta;
    }

    public void setPlanta(String planta) {
        this.planta = planta;
    }

    @Basic
    @Column(name = "puesto")
    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityUbicaciones that = (EntityUbicaciones) o;

        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (edificio != null ? edificio.hashCode() : 0);
        result = 31 * result + (planta != null ? planta.hashCode() : 0);
        result = 31 * result + (puesto != null ? puesto.hashCode() : 0);
        return result;
    }
}
