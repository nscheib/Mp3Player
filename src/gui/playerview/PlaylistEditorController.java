package gui.playerview;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import sample.Main;
import sample.PlaylistVerwalter;

import java.io.File;

public class PlaylistEditorController {

    private PlaylistEditorView view;
    private PlaylistVerwalter playlistVerwalter = new PlaylistVerwalter();



    public PlaylistEditorController(Main application){







        // Scene 2

        view = new PlaylistEditorView(playlistVerwalter);
        view.add.setOnAction(e -> buttonClicked(0));
        view.delete.setOnAction(e -> buttonClicked(1));
        view.loadPlaylist.setOnAction(e-> buttonClicked(2));
        view.add2.setOnAction(e -> buttonClicked(3));
        view.delete2.setOnAction(e -> buttonClicked(4));
        view.changeWindow2.setOnAction(e->application.switchScene("MP3player"));
        view.getStylesheets().add(getClass().getResource("style2.css").toExternalForm());


    }

    private void buttonClicked(int befehl){

        ObservableList<String> playlists, inhalt;
        playlists = view.listView.getSelectionModel().getSelectedItems();

        // Macht es Sinn das hier zu platzieren ?

        // Hier können wir auch aktivieren, dass mehrere Playlist gleichzeitig gelöscht werden können

        if (befehl == 0){
            // Playlist neu erstellen...
            // Neues Fenster in neuer Scene...
            //window.setScene(scene4);

            System.out.println("Gib Name und die Songs ein ...");


        }

        if (befehl == 1){
            // Playlist löschen
            //playlistVerwalter.deletePlaylist(playlists.get(0));

            String ort = "playlisten/"+playlists.get(0);
            File delete = new File(ort);
            delete.delete();
            // listViewListe.remove(playlists); // löscht das auch wirklich ???
            view.listView.refresh();
            //System.out.println("Playlist "+ playlists.get(0) +" wurde gelöscht");

        }

        if (befehl == 2){
            // ausgewählte playlist laden...
            // laden in observable list ????

            // Löschen des Inhaltes
            for (int i = 0; i < view.listViewR.getItems().size(); i++){
                view.listViewR.getItems().remove(i);
                // Ist es sinnvoll alles zu löschen ???
            }

            //listView.setItems(playlists);

            // Laden der zielplaylist lieder...
//            int liedCounter = 0;
//            while (playlistVerwalter.getAllSongs().get(liedCounter) != NULL){
//                listViewR.getItems().addAll(playlistVerwalter.getAllSongs().get(i));
//                liedCounter++;
//            }
            view.listViewR.getItems().addAll(playlistVerwalter.getAllSongs());
            //
            for (int i = 0; i < view.listViewR.getItems().size(); i++){
                playlistVerwalter.loadFromFile(playlists.get(i));
                // Fehler wenn out of bounds...
                view.listViewR.getItems().add(playlistVerwalter.getAllSongs().get(i));
                // lädt aus ausgewählter playlist
            }
            view.listViewR.refresh();
            System.out.println("Playlist "+ playlists + " wurde geladen");
            // rechte listview aktualisieren
        }

        if (befehl == 3){
            System.out.println("Lied "+ playlists + " wurde hinzugefügt");
            // Hier Liedauswähler implementieren
        }

        if (befehl == 2){
            System.out.println("Lied "+ playlists + " wurde gelöscht");
        }

        // hier machen dass playlist erstellt / gelöscht wird

    }


    public Pane getView(){
        return view;
    }
}
