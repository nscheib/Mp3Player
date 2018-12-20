package presentation;

import business.Track;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class CustomTrackCell extends ListCell<Track> {

        private HBox content;
        private Label title;
        private Label interpret;
        private ImageView image = new ImageView();


        public CustomTrackCell(){
            super();
            title = new Label();
            interpret = new Label();
            VBox vBox = new VBox(title, interpret);

            content = new HBox((new ImageView(image.getImage())),vBox);

            content.setSpacing(10);
            //Mp3Controller.getMp3Player().getData().title();
        }

        @Override
        protected void updateItem(Track item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null && !empty) { // <== test for null item and empty parameter
                title.setText(item.getTitle());
                interpret.setText(item.getInterpret());
                image.setImage(item.getImage());
                setGraphic(content);
            } else {
                setGraphic(null);
            }
        }
    }
