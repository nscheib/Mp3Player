package gui.Controller;

import businessLogic.Track;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Klasse CustomTrackCell zeigt alle einzelnen Track mit Titel, Interpret und Coverbild in der ListView an
 */
public class CustomTrackCell extends ListCell<Track> {
    private HBox content;
    private Label title;
    private Label interpret;
    private ImageView image, img;
    private VBox vBox;

    // Constructor
    public CustomTrackCell(){
        super();
        title = new Label();
        interpret = new Label();
        vBox = new VBox(title, interpret);
        content = new HBox();
        content.setSpacing(10);
        image = new ImageView();
        img = new ImageView();
    }

    /**
     * Methode zeigt die Songs der gewählten Playlist in der ListView an
     * @param item
     * @param empty
     */
    @Override
    protected void updateItem(Track item, boolean empty) {
        super.updateItem(item, empty);

        if (item != null && !empty) {
            title.setText(item.getTitle());
            interpret.setText(item.getInterpret());
            image.setFitHeight(30);
            image.setFitWidth(30);
            image.setImage(item.getImage());
        }
        content = new HBox(image,vBox);
        setGraphic(content);
    }
}
