package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import presentation.BasePresenter;
import view.auxiliarViews.Config;
import view.auxiliarViews.ImageButton;
import view.auxiliarViews.Predictor;

import java.util.Collection;

abstract class ManageEdgeView extends MainAdminView {

    private HBox predictorHBox;

    protected Predictor node1;
    protected Predictor node2;

    protected Label errorLabel;

    protected ImageButton button;

    protected Collection<String> resultToPredict;

    ManageEdgeView(BasePresenter presenter, Collection<String> resultToPredict) {
        this.presenter = presenter;
        this.resultToPredict = resultToPredict;
    }

    protected void initializePanes() {
        contentVBox = new VBox();
        contentVBox.setPadding(new Insets(50, 200, 0, 200));
        contentVBox.setSpacing(100);
        contentVBox.setAlignment(Pos.CENTER);

        predictorHBox = new HBox();
        predictorHBox.setSpacing(10);
        predictorHBox.setAlignment(Pos.CENTER);
    }

    protected void initializeViews() {
        node1 = new Predictor(resultToPredict, Config.LISTS_SIZE, new Insets(0, 0, 0, 0), "Node 1");
        node1.setMinSize(200, 20);
        node1.setMaxSize(200, 24);

        node2 = new Predictor(resultToPredict, Config.LISTS_SIZE, new Insets(0, 0, 0, 0), "Node 2");
        node2.setMinSize(200, 20);
        node2.setMaxSize(200, 24);

        errorLabel = new Label();
    }

    protected void buildPanes() {
        predictorHBox.getChildren().addAll(
                node1,
                node2
        );
        contentVBox.getChildren().addAll(
                predictorHBox,
                errorLabel,
                button
        );
        topBarPane.setCenter(contentVBox);
    }

    protected abstract void setListeners();

    public void setErrorMessage(String errorMessage) {
        errorLabel.setText(errorMessage);
    }

    public String getNode1() {
        return node1.getText();
    }

    public String getNode2() {
        return node2.getText();
    }
}
