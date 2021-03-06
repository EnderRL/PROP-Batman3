package view;

import javafx.application.Platform;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import presentation.MainPresenter;
import view.auxiliarViews.ImageButton;
import view.auxiliarViews.Predictor;

import java.util.List;

public class MainView extends BaseView {

    private VBox contentVBox;

    private HBox errorHBox;
    private Label errorLabel;

    private HBox searchTextHBox;

    private ProgressIndicator progressIndicator;
    private ImageButton searchButton;
    private Predictor predictor;
    private List<String> resultToPredict;

     private Font font;

    public MainView(MainPresenter mainPresenter, List<String> resultToPredict) {
        presenter = mainPresenter;
        this.resultToPredict = resultToPredict;
        search.press();
        initializePanes();
        initializeViews();
        buildPanes();
        setListeners();
        topBarPane.setCenter(contentVBox);
    }

    private void initializePanes() {
        contentVBox = new VBox();
        errorHBox = new HBox();
        errorHBox.setAlignment(Pos.CENTER);
        searchTextHBox = new HBox();
        searchTextHBox.setPadding(new Insets(0,0,10,80));
        searchTextHBox.setSpacing(10);
    }

    protected void initializeViews() {
        font = Font.loadFont(this.getClass().getResource("../fonts/Nilland-Black.ttf").toExternalForm(), 14.95);
        errorLabel = new Label();
        errorLabel.setFont(font);
        errorLabel.setTextFill(Paint.valueOf("#6E0000"));

        searchButton = new ImageButton("searchButton", 143, 51);

        initializePredictor("Introduce the name of the node.");

        progressIndicator = new ProgressIndicator();
        progressIndicator.setProgress(-1);
    }

    private void buildPanes() {
        searchTextHBox.getChildren().add(predictor);
        searchTextHBox.getChildren().add(searchButton);
        errorHBox.getChildren().add(errorLabel);

        contentVBox.getChildren().addAll(
                errorHBox,
                searchTextHBox
        );
        contentVBox.setAlignment(Pos.CENTER);
    }

    private void setListeners() {

        predictor.setTextListener(
                event -> ((MainPresenter) presenter).clickSearchButton()
        );

        searchButton.setOnMousePressed(
                event -> searchButton.changeButtonImage("../images/searchButtonPressed.png")
        );

        searchButton.setOnMouseReleased(
                event -> {
                    searchButton.changeButtonImage("../images/searchButton.png");
                    ((MainPresenter)presenter).clickSearchButton();
                }
        );
    }

    public void startProgress() {
        searchButton.setDisable(true);
        if (Platform.isFxApplicationThread()) {
            searchTextHBox.getChildren().set(0, progressIndicator);
        }
        else {
            Platform.runLater(() -> searchTextHBox.getChildren().set(0, progressIndicator));
        }
    }

    public void stopProgress() {
        searchButton.setDisable(false);
        if (Platform.isFxApplicationThread()) {
            searchTextHBox.getChildren().set(0, predictor);
        }
        else {
            Platform.runLater(() -> searchTextHBox.getChildren().set(0, predictor));
        }
    }
    public String getSearchText() { return predictor.getText();}

    void initializePredictor(String name) {
        predictor = new Predictor(resultToPredict, 10, new Insets(8, 0, 0, 0), name);
        predictor.setMaxSize(600,200);
        predictor.setMinSize(600,200);

    }

    public void showError(String s) {
        errorLabel.setText(s);
    }

}
