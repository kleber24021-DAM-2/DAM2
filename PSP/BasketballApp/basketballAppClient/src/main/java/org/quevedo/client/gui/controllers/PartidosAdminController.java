package org.quevedo.client.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import lombok.extern.log4j.Log4j2;
import org.quevedo.client.services.ServiceEquipos;
import org.quevedo.client.services.ServiceJornada;
import org.quevedo.client.services.ServicePartidos;
import org.quevedo.sharedmodels.Equipo;
import org.quevedo.sharedmodels.Jornada;
import org.quevedo.sharedmodels.partido.Partido;
import org.quevedo.sharedmodels.partido.RegisterPartidoDTO;
import org.quevedo.sharedmodels.partido.UpdateResultPartidoDTO;
import retrofit2.HttpException;

import javax.inject.Inject;

@Log4j2
public class PartidosAdminController {

    private final ServicePartidos servicePartidos;
    private final ServiceJornada serviceJornada;
    private final ServiceEquipos serviceEquipos;
    private final Alert alert = new Alert(Alert.AlertType.ERROR);
    private MainScreenController mainController;
    @FXML
    private ListView<Partido> lvPartidos;
    @FXML
    private ComboBox<Jornada> cbJornada;
    @FXML
    private ComboBox<Equipo> cbEquipoLocal;
    @FXML
    private ComboBox<Equipo> cbEquipoVisitante;
    @FXML
    private TextField resultadoLocal;
    @FXML
    private TextField resultadoVisitante;

    @Inject
    public PartidosAdminController(ServicePartidos servicePartidos, ServiceJornada serviceJornada, ServiceEquipos serviceEquipos) {
        this.servicePartidos = servicePartidos;
        this.serviceEquipos = serviceEquipos;
        this.serviceJornada = serviceJornada;
    }

    @FXML
    private void savePartido() {
        if (cbJornada.getSelectionModel().isEmpty() || cbEquipoLocal.getSelectionModel().isEmpty() || cbEquipoVisitante.getSelectionModel().isEmpty()) {
            alert.setContentText(UserMessages.MSG_NO_EMPTY_FIELD);
            alert.showAndWait();
        } else {
            RegisterPartidoDTO partidoToRegister = new RegisterPartidoDTO();
            partidoToRegister.setIdJornada(cbJornada.getSelectionModel().getSelectedItem().getIdJornada());
            partidoToRegister.setIdEquipoLocal(cbEquipoLocal.getSelectionModel().getSelectedItem().getIdEquipo());
            partidoToRegister.setIdEquipoVisitante(cbEquipoVisitante.getSelectionModel().getSelectedItem().getIdEquipo());
            if (!resultadoLocal.getText().isEmpty() || !resultadoVisitante.getText().isEmpty()) {
                try {
                    partidoToRegister.setResultadoLocal(Integer.parseInt(resultadoLocal.getText()));
                    partidoToRegister.setResultadoVisitante(Integer.parseInt(resultadoVisitante.getText()));
                } catch (NumberFormatException numForEx) {
                    alert.setContentText(UserMessages.MSG_NUM_EX);
                    alert.showAndWait();
                }
            }


            servicePartidos.addPartido(partidoToRegister)
                    .doFinally(() -> this.mainController
                            .getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                    .subscribe(result -> result
                                    .peek(partido -> lvPartidos.getItems().add(partido))
                                    .peekLeft(errorMensaje -> {
                                        alert.setContentText(errorMensaje);
                                        alert.showAndWait();
                                    }),
                            throwable -> {
                                if (throwable instanceof HttpException) {
                                    alert.setContentText(UserMessages.MSG_DB_NO_CONNECTION);
                                } else {
                                    alert.setContentText(UserMessages.MSG_UNEXPECTED_ERROR);
                                }
                                log.error(throwable.getMessage(), throwable);
                                alert.showAndWait();
                            });
            mainController.getPantallaPrincipal().setCursor(Cursor.WAIT);
        }
    }

    @FXML
    private void setResultado() {
        Integer resultadoLocalNum = null;
        Integer resultadoVisitanteNum = null;
        if (!resultadoLocal.getText().isEmpty() && !resultadoVisitante.getText().isEmpty()) {
            try {
                resultadoLocalNum = Integer.parseInt(resultadoLocal.getText());
                resultadoVisitanteNum = Integer.parseInt(resultadoVisitante.getText());
            } catch (NumberFormatException numForEx) {
                alert.setContentText(UserMessages.MSG_NUM_EX);
                alert.showAndWait();
            }
        }

        if (resultadoLocalNum != null && resultadoVisitanteNum != null && !lvPartidos.getSelectionModel().isEmpty()) {
            UpdateResultPartidoDTO newResult = new UpdateResultPartidoDTO();
            newResult.setResultadoLocal(resultadoLocalNum);
            newResult.setResultadoVisitante(resultadoVisitanteNum);
            newResult.setIdPartido(lvPartidos.getSelectionModel().getSelectedItem().getIdPartido());

            int lvIndex = lvPartidos.getSelectionModel().getSelectedIndex();

            servicePartidos.registerResult(newResult)
                    .doFinally(() -> this.mainController
                            .getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                    .subscribe(result -> result
                                    .peek(partido -> {
                                        lvPartidos.getItems().remove(lvIndex);
                                        lvPartidos.getItems().add(lvIndex, partido);
                                    }).peekLeft(errorMensaje -> {
                                        alert.setContentText(errorMensaje);
                                        alert.showAndWait();
                                    }),
                            throwable -> {
                                if (throwable instanceof HttpException) {
                                    alert.setContentText(UserMessages.MSG_DB_NO_CONNECTION);
                                } else {
                                    alert.setContentText(UserMessages.MSG_UNEXPECTED_ERROR);
                                }
                                log.error(throwable.getMessage(), throwable);
                                alert.showAndWait();
                            });
            mainController.getPantallaPrincipal().setCursor(Cursor.WAIT);
        } else {
            alert.setContentText(UserMessages.MSG_PARTIDO_RESULT_INFO);
            alert.showAndWait();
        }
    }

    public void loadAllListas() {

        servicePartidos.getAllPartidos()
                .doFinally(() -> mainController.getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                .subscribe(result -> result
                                .peek(partidos -> lvPartidos.getItems().setAll(partidos))
                                .peekLeft(errorMensaje -> {
                                    alert.setContentText(errorMensaje);
                                    alert.showAndWait();
                                }),
                        throwable -> {
                            if (throwable instanceof HttpException) {
                                alert.setContentText(UserMessages.MSG_DB_NO_CONNECTION);
                            } else {
                                alert.setContentText(UserMessages.MSG_UNEXPECTED_ERROR);
                            }
                            log.error(throwable.getMessage(), throwable);
                            alert.showAndWait();
                        });

        serviceJornada.getAllJornadas()
                .doFinally(() -> mainController.getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                .subscribe(result -> result
                                .peek(jornadas -> cbJornada.getItems().setAll(jornadas))
                                .peekLeft(errorMensaje -> {
                                    alert.setContentText(errorMensaje);
                                    alert.showAndWait();
                                }),
                        throwable -> {
                            if (throwable instanceof HttpException) {
                                alert.setContentText(UserMessages.MSG_DB_NO_CONNECTION);
                            } else {
                                alert.setContentText(UserMessages.MSG_UNEXPECTED_ERROR);
                            }
                            log.error(throwable.getMessage(), throwable);
                            alert.showAndWait();
                        });

        serviceEquipos.getAllEquipos()
                .doFinally(() -> mainController.getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                .subscribe(result -> result
                                .peek(equipoList -> {
                                    cbEquipoLocal.getItems().setAll(equipoList);
                                    cbEquipoVisitante.getItems().setAll(equipoList);
                                })
                                .peekLeft(errorMensaje -> {
                                    alert.setContentText(errorMensaje);
                                    alert.showAndWait();
                                }),
                        throwable -> {
                            if (throwable instanceof HttpException) {
                                alert.setContentText(UserMessages.MSG_DB_NO_CONNECTION);
                            } else {
                                alert.setContentText(UserMessages.MSG_UNEXPECTED_ERROR);
                            }
                            log.error(throwable.getMessage(), throwable);
                            alert.showAndWait();
                        });
        mainController.getPantallaPrincipal().setCursor(Cursor.WAIT);
    }

    public void setMainController(MainScreenController mainController) {
        this.mainController = mainController;
    }
}
