package view;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import presentation.LoginPresenter;
import view.auxiliarViews.Config;
import view.auxiliarViews.EditText;
import view.auxiliarViews.ImageButton;


public class LoginView {

    private BorderPane basePane;
    private VBox titleVBox;
    private VBox contentVBox;
    private VBox textFieldsVBox;
    private VBox buttonsVBox;

    private ImageView applicationLogo;

    private EditText usernameEditText;

    private HBox passwordHBox;
    private Label passwordLabel;
    private PasswordField passwordTextField;

    private ImageButton loginButton;
    private ImageButton registerButton;
    private Text registerText;

    private LoginPresenter loginPresenter;

    private ProgressIndicator progressIndicator;
    private Text errorText;
    private HBox errorHBox;

    private Font coolFont;

    public LoginView(LoginPresenter loginPresenter) {
        this.loginPresenter = loginPresenter;
        initializePanes();
        initializeViews();
        buildPanes();
        setListeners();
    }

    public void destroy() {
        loginPresenter = null;
    }

    private void initializePanes(){
        basePane = new BorderPane();
        basePane.setPadding(new Insets(50, 0, 100, 0));

        titleVBox = new VBox();

        contentVBox = new VBox();
        contentVBox.setPadding(new Insets(45, 0, 50, 0));

        textFieldsVBox = new VBox();
        textFieldsVBox.setSpacing(4);

        usernameEditText = new EditText("Username");

        passwordHBox = new HBox();
        passwordHBox.setSpacing(6);


        buttonsVBox = new VBox();
        buttonsVBox.setPadding(new Insets(20, 0, 50, 0));
        buttonsVBox.setSpacing(4);

        errorHBox = new HBox();
        errorHBox.setPadding(new Insets(0,0,10,0));
    }

    private void initializeViews() {
        coolFont = Font.loadFont(this.getClass().getResource("../fonts/Nilland-Black.ttf").toExternalForm(), 14.95);
        applicationLogo = new ImageView();
        Image image2 = new Image(getClass().getResourceAsStream("../images/login/titleLogo.png"));

        applicationLogo.setImage(image2);
        applicationLogo.setFitWidth(525);
        applicationLogo.setFitHeight(147);

        usernameEditText.setFont(coolFont);

        passwordLabel = new Label("Password");
        passwordLabel.setFont(coolFont);
        passwordLabel.setTextFill(Config.LABEL_DARK_COLOR);
        passwordTextField = new PasswordField();

        loginButton = new ImageButton("loginButton", 227, 50);

        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.color(0,0,0));
        registerText = new Text("Don't have an account yet?");
        registerText.setEffect(ds);
        registerText.setStyle(
                "-fx-fill: #d0b494;" +
                "-fx-font: 20px Caspian");

        registerButton = new ImageButton("login/registerButton", 148, 50);

        progressIndicator = new ProgressIndicator();
        progressIndicator.setMaxSize(227, 50);
        progressIndicator.setMinSize(227, 50);
        progressIndicator.setProgress(-1.0f);

        errorText = new Text();
        errorText.setFont(coolFont);
        errorText.setFill(Color.valueOf("#6E0000"));

    }

    private void buildPanes() {
        errorHBox.getChildren().add(errorText);
        errorHBox.setAlignment(Pos.CENTER);
        titleVBox.getChildren().add(applicationLogo);

        titleVBox.setAlignment(Pos.CENTER);

        passwordHBox.getChildren().add(passwordLabel);
        passwordHBox.getChildren().add(passwordTextField);
        passwordHBox.setAlignment(Pos.CENTER);

        textFieldsVBox.getChildren().add(errorHBox);
        textFieldsVBox.getChildren().add(usernameEditText.getBase());
        textFieldsVBox.getChildren().add(passwordHBox);
        textFieldsVBox.setAlignment(Pos.CENTER);

        buttonsVBox.getChildren().add(loginButton);
        buttonsVBox.getChildren().add(registerText);
        buttonsVBox.getChildren().add(registerButton);
        buttonsVBox.setAlignment(Pos.CENTER);

        contentVBox.getChildren().add(textFieldsVBox);
        contentVBox.getChildren().add(buttonsVBox);
        contentVBox.setAlignment(Pos.CENTER);

        String image = this.getClass().getResource("../images/background.jpg").toExternalForm();
        basePane.setStyle(
                "-fx-background-image: url('" + image + "'); " +
                "-fx-background-position: center center; " +
                "-fx-background-repeat: stretch;" +
                "-fx-background-size: 1024 576"
        );
        basePane.setTop(titleVBox);
        basePane.setCenter(contentVBox);
    }

    public void startProgress() {
        loginButton.setDisable(true);
        registerButton.setDisable(true);
        if (Platform.isFxApplicationThread()) {
            contentVBox.getChildren().set(0, progressIndicator);
        }
        else {
            Platform.runLater(() -> contentVBox.getChildren().set(0, progressIndicator));
        }
    }

    public void stopProgress() {
        loginButton.setDisable(false);
        registerButton.setDisable(false);
        if (Platform.isFxApplicationThread()) {
            contentVBox.getChildren().set(0, textFieldsVBox);
        }
        else {
            Platform.runLater(() -> contentVBox.getChildren().set(0, textFieldsVBox));
        }
    }

    public void displayErrorMessage(String errorMessage){
        errorText.setText(errorMessage);

        errorText.setFill(Paint.valueOf("#6E0000"));
        System.out.println("Login Failure");
    }

    public void displaySuccessMessage(String errorMessage){

        errorText.setText(errorMessage);
        errorText.setFill(Paint.valueOf("green"));
    }

    private void setListeners() {

        passwordTextField.setOnAction(
                event -> loginPresenter.login()
        );

        loginButton.setOnMousePressed(
                event -> loginButton.press()
        );

        loginButton.setOnMouseReleased(
                event -> {
                    loginButton.release();
                    loginPresenter.login();
                }
        );

        registerButton.setOnMousePressed(
                event -> registerButton.changeButtonImage("../images/login/registerButtonPressed.png")
        );

        registerButton.setOnMouseReleased(
                event -> {
                    registerButton.changeButtonImage("../images/login/registerButton.png");
                    loginPresenter.register();
                }
        );
    }

    public String getUsername(){
        return usernameEditText.getText();
    }

    public String getPassword(){
        return passwordTextField.getText();
    }

    public Pane getContent() {
        return basePane;
    }

}

