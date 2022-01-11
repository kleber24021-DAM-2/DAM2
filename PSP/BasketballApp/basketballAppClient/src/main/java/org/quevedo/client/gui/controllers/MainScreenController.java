package org.quevedo.client.gui.controllers;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import lombok.extern.log4j.Log4j2;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;
import org.quevedo.client.gui.utils.FxmlPaths;
import org.quevedo.client.services.ServiceLogout;
import org.quevedo.sharedmodels.usuario.LoginResultDTO;
import org.quevedo.sharedmodels.usuario.TipoUsuario;
import retrofit2.HttpException;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Log4j2
public class MainScreenController implements Initializable {

    private final ServiceLogout serviceLogout;
    AnchorPane partidosAdminPane;
    FXMLLoader partidosAdminLoader;
    AnchorPane registerPane;
    FXMLLoader registerLoader;
    RegisterController registerController;
    PartidosAdminController partidosAdminController;
    AnchorPane partidosRegularPane;
    FXMLLoader partidosRegularLoader;
    PartidosUserController partidosUserController;
    AnchorPane equiposPane;
    FXMLLoader equiposLoader;
    EquiposController equiposController;
    AnchorPane jornadasPane;
    FXMLLoader jornadasLoader;
    JornadasController jornadasController;
    AnchorPane usuariosPane;
    FXMLLoader usuariosLoader;
    UsuariosController usuariosController;
    AnchorPane loginPane;
    FXMLLoader loginLoader;
    LoginScreen loginScreenController;
    AnchorPane welcomePane;
    WelcomeController welcomeController;
    FXMLLoader welcomeLoader;
    @FXML
    private MenuItem menuBtnEquipo;
    @FXML
    private MenuItem menuBtnJornada;
    @FXML
    private MenuItem menuBtnUsuarios;
    private LoginResultDTO loggedUser;
    @FXML
    private BorderPane root;

    @Inject
    public MainScreenController(FXMLLoader partidosAdminLoader,
                                FXMLLoader partidosRegularLoader,
                                FXMLLoader equiposLoader,
                                FXMLLoader jornadasLoader,
                                FXMLLoader usuariosLoader,
                                FXMLLoader loginLoader,
                                FXMLLoader welcomeLoader,
                                FXMLLoader registerLoader,
                                ServiceLogout serviceLogout) {
        this.partidosAdminLoader = partidosAdminLoader;
        this.partidosRegularLoader = partidosRegularLoader;
        this.equiposLoader = equiposLoader;
        this.jornadasLoader = jornadasLoader;
        this.usuariosLoader = usuariosLoader;
        this.loginLoader = loginLoader;
        this.welcomeLoader = welcomeLoader;
        this.serviceLogout = serviceLogout;
        this.registerLoader = registerLoader;
    }

    private void preloadRegisterScreen() {
        try {
            registerPane = registerLoader.load(getClass().getResourceAsStream(FxmlPaths.PATH_REGISTER_SCREEN_FXML));
            registerController = registerLoader.getController();
            registerController.setMainController(this);
        } catch (IOException ioEx) {
            log.error(ioEx.getMessage(), ioEx);
        }
    }

    public void showRegister() {
        root.setCenter(registerPane);
    }

    private void preloadWelcomeScreen() {
        try {
            welcomePane = welcomeLoader.load(getClass().getResourceAsStream(FxmlPaths.PATH_WELCOME_SCREEN_FXML));
            welcomeController = welcomeLoader.getController();
        } catch (IOException ioEx) {
            log.error(ioEx.getMessage(), ioEx);
        }
    }

    private void showWelcome() {
        root.setCenter(welcomePane);
    }

    private void preloadUsuarios() {
        try {
            usuariosPane = usuariosLoader.load(getClass().getResourceAsStream(FxmlPaths.PATH_USER_SCREEN_FXML));
            usuariosController = usuariosLoader.getController();
            usuariosController.setMainController(this);
        } catch (IOException ioEx) {
            log.error(ioEx.getMessage(), ioEx);
        }
    }

    public void showUsuarios() {
        root.setCenter(usuariosPane);
        usuariosController.loadUsersList();
    }

    private void preloadPartidosAdmin() {
        try {
            partidosAdminPane = partidosAdminLoader.load(getClass().getResourceAsStream(FxmlPaths.PATH_PARTIDOS_ADMIN_SCREEN_FXML));
            partidosAdminController = partidosAdminLoader.getController();
            partidosAdminController.setMainController(this);
        } catch (IOException ioEx) {
            log.error(ioEx.getMessage(), ioEx);
        }
    }

    public void showPartidosAdmin() {
        root.setCenter(partidosAdminPane);
        partidosAdminController.loadAllListas();
    }

    private void preloadLoginScreen() {
        try {
            loginPane = loginLoader.load(getClass().getResourceAsStream(FxmlPaths.PATH_LOGIN_SCREEN_FXML));
            loginScreenController = loginLoader.getController();
            loginScreenController.setMainController(this);
        } catch (IOException ioEx) {
            log.error(ioEx.getMessage(), ioEx);
        }
    }

    public void showLogin() {
        root.setCenter(loginPane);
    }

    private void preloadPartidosUser() {
        try {
            partidosRegularPane = partidosRegularLoader.load(getClass().getResourceAsStream(FxmlPaths.PATH_PARTIDOS_USER_FXML));
            partidosUserController = partidosRegularLoader.getController();
            partidosUserController.setMainController(this);
        } catch (IOException ioEx) {
            log.error(ioEx.getMessage(), ioEx);
        }
    }


    public void showPartidos() {
        if (loggedUser.getNivelAcceso().equals(TipoUsuario.ADMIN)) {
            showPartidosAdmin();
        } else {
            root.setCenter(partidosRegularPane);
        }
    }

    private void preloadJornadasScreen() {
        try {
            jornadasPane = jornadasLoader.load(getClass().getResourceAsStream(FxmlPaths.PATH_JORNADAS_SCREEN_FXML));
            jornadasController = jornadasLoader.getController();
            jornadasController.setMainController(this);
        } catch (IOException ioEx) {
            log.error(ioEx.getMessage(), ioEx);
        }
    }

    public void showJornadasScreen() {
        jornadasController.loadAllJornadas();
        root.setCenter(jornadasPane);
    }

    private void preloadEquiposScreen() {
        try {
            equiposPane = equiposLoader.load(getClass().getResourceAsStream(FxmlPaths.PATH_EQUIPOS_SCREEN_FXML));
            equiposController = equiposLoader.getController();
            equiposController.setMainController(this);
        } catch (IOException ioEx) {
            log.error(ioEx.getMessage(), ioEx);
        }
    }

    public void showEquiposScreen() {
        equiposController.loadLista();
        root.setCenter(equiposPane);
    }

    public void doLogout() {
        Completable completable = Completable
                .fromAction(serviceLogout::doLogout)
                .subscribeOn(Schedulers.io())
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> root.setCursor(Cursor.DEFAULT));
        completable.subscribe(() -> {
            loggedUser = null;
            root.getTop().setVisible(false);
            menuBtnEquipo.setVisible(false);
            menuBtnJornada.setVisible(false);
            menuBtnUsuarios.setVisible(false);
            showLogin();
        }, throwable -> {
            if (throwable instanceof HttpException) {
                new Alert(Alert.AlertType.ERROR, UserMessages.MSG_DB_NO_CONNECTION).showAndWait();
            } else {
                new Alert(Alert.AlertType.ERROR, UserMessages.MSG_UNEXPECTED_ERROR).showAndWait();
            }
        });
        root.setCursor(Cursor.WAIT);
    }

    public void doLogin(LoginResultDTO loginResultDTO) {
        root.getTop().setVisible(true);
        loggedUser = loginResultDTO;
        welcomeController.setUserName(loginResultDTO.getCorreo());
        if (loggedUser.getNivelAcceso().equals(TipoUsuario.ADMIN)) {
            menuBtnEquipo.setVisible(true);
            menuBtnJornada.setVisible(true);
            menuBtnUsuarios.setVisible(true);
        }
        showWelcome();
    }


    public BorderPane getPantallaPrincipal() {
        return root;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        preloadRegisterScreen();
        preloadLoginScreen();
        preloadPartidosUser();
        preloadJornadasScreen();
        preloadUsuarios();
        preloadPartidosAdmin();
        preloadEquiposScreen();
        preloadWelcomeScreen();
        showLogin();
    }
}
