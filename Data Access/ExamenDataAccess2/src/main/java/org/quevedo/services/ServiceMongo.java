package org.quevedo.services;

import org.bson.Document;
import org.quevedo.dao.implementation.hibernate.DaoHibernatePermisosUbicaciones;
import org.quevedo.dao.implementation.mongodb.DaoMongo;
import org.quevedo.dao.interfaces.DaoPermisosUbicaciones;
import org.quevedo.models.hibernate.EntityUbicaciones;

import java.util.List;
import java.util.stream.Collectors;


public class ServiceMongo {
    public void addMongoUbicacion(EntityUbicaciones entityUbicaciones) {
        org.quevedo.dao.interfaces.DaoMongo daoMongo = new DaoMongo();
        DaoPermisosUbicaciones daoPermisosUbicaciones = new DaoHibernatePermisosUbicaciones();
        List<Document> listaDoc = daoPermisosUbicaciones.getPermisosByUbicacion(entityUbicaciones.getId()).get()
                .stream().map(ubicacion -> {
                    Document document = new Document();
                    document.append("idUsuario", ubicacion.getIdUsuario());
                    document.append("permisos", ubicacion.getPermisosByIdPermiso().getDescripcion());
                    return document;
                }).collect(Collectors.toList());
        Document d = new Document();
        d.append("name", entityUbicaciones.getNombre());
        d.append("building", entityUbicaciones.getEdificio());
        d.append("users", listaDoc);

        daoMongo.addUbicacion(d);
    }
}
