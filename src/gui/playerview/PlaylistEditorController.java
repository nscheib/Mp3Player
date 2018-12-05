package gui.playerview;

import javafx.scene.layout.Pane;
import sample.Main;

public class PlaylistEditorController {

    private PlaylistEditorView view;

    public PlaylistEditorController(Main application){
        view = new PlaylistEditorView();
    }


    public Pane getView(){
        return view;
    }
}
