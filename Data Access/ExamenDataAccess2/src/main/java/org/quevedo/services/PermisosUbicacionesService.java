package org.quevedo.services;

import org.quevedo.dao.implementation.hibernate.DaoHibernatePermisosUbicaciones;
import org.quevedo.dao.interfaces.DaoPermisosUbicaciones;

public class PermisosUbicacionesService {
    public Integer getNumUsuariosByNombreUbicacion(String locationName) {
        DaoPermisosUbicaciones daoPermisosUbicaciones = new DaoHibernatePermisosUbicaciones();
        return daoPermisosUbicaciones.getEjercicio5(locationName);
    }
}
