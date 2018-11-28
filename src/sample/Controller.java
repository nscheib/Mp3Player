package sample;

import de.hsrm.mi.prog.util.StaticScanner;

public class Controller {

    private Mp3Player mp3Player = new Mp3Player();
    private PlaylistVerwalter playlistVerwalter = new PlaylistVerwalter();
    private final String [] BEFEHLE = {"", "play", "pause", "stop", "volume", "quit", "playlist"};

    public void start(){

        boolean anAus = false;
        while (anAus == false) {
            System.out.println("Gib einen Befehl ein: ");
            System.out.println("play, pause, stop, volume, quit, playlist");
            String eingabe = StaticScanner.nextString();
            String[] parts = eingabe.split(" ");
            PlaylistVerwalter trackList = new PlaylistVerwalter();
            for (int i = 0; i < BEFEHLE.length; i++) {
                if (BEFEHLE[i].equals(parts[0])) {
                    switch (i) {
                        case 1:
                            if (parts.length > 1) {
                                System.out.println(parts[1]);
                                mp3Player.play(parts[1]);
                            }else{
                                mp3Player.play();
                            }
                            break;
                        case 2:
                            System.out.println("pause");
                            mp3Player.pause();
                            break;
                        case 3:
                            System.out.println("stop");
                            mp3Player.pause();
                            break;
                        case 4:
                            System.out.println("volume");
                            if (parts.length > 1) {
                                System.out.println(parts[1]);
                                float lautstaerke = Float.parseFloat(parts[1]);
                                System.out.println(lautstaerke);
                                mp3Player.volume(lautstaerke);
                            }
                            break;
                        case 5:
                            System.out.println("quit");
                            mp3Player.pause();

                            anAus = true;
                            break;
                        case 6:

                            bearbeiten();



                        default:
                            System.out.println("Ende Controller");
                    }
                }
            }
        }
        System.out.println("Ende");
    }


    private void bearbeiten(){


        System.out.println("Wollen sie eine Playlist erstellen? <y/n>");
        String eingabe = StaticScanner.nextString();
        if (eingabe.equals("y")){
            System.out.println("Wie soll die Playlist heissen?");
            eingabe = StaticScanner.nextString();
            eingabe = eingabeAbfrage(eingabe);
            String playlistFile = playlistVerwalter.createPlaylist(eingabe);
            System.out.println("Playlist "+ eingabe + " wurde erstellt.");

            //____________________________________________________________________________________

            System.out.println("Welches der zur verfuegung stehenden Lieder moechten Sie der Playlist hinzufuegen?");

            // File Explorer

            for (int i = 0; i < playlistVerwalter.getAllSongs().size(); i++) {
                System.out.println("Song " + (i + 1) + ": " + playlistVerwalter.getAllSongs().get(i));
            } // Ausgabe der Daten
            eingabe= StaticScanner.nextString();
            eingabeAbfrage(eingabe);
            playlistVerwalter.fileBeschreiben(playlistFile, "#EXTINF:" + eingabe + "\n"); // runtime, title

            //____________________________________________________________________________________

            System.out.println("Deine aktuelle Playlist: ");
            // In eine andere Methode auslagern / gar nicht erst im Controller machen...

            playlistVerwalter.printPlaylistSongs(playlistFile);

            //____________________________________________________________________________________



            System.out.println("M�chtest du eines dieser Lieder aus der Playlist l�schen? (y/n)");
            // Nat�rlich auch noch verbessern, sodass falsche eingaben nicht alles zerst�ren
            eingabe = StaticScanner.nextString();
            if (eingabe.equals("y")) {
                System.out.println("Welches Lied soll gel�scht werden?");
                eingabe = StaticScanner.nextString();
                playlistVerwalter.loeschenLied(playlistFile, eingabe); // Immer noch dieselbe playlistFile
                System.out.println("\n\n");
                playlistVerwalter.printPlaylistSongs(playlistFile);


            } else System.out.println("Ja gut, dann net. Viel Spass mit deiner Playlist.");


            //____________________________________________________________________________________

            System.out.println("Möchtest du diese Playlist löschen? (y/n)");
            eingabe = StaticScanner.nextString();
            if (eingabe.equals("y")) {

                playlistVerwalter.deletePlaylist(playlistFile);


            } else System.out.println("Ja gut, dann net. Viel Spass mit deiner Playlist.");


        }

    }










    private void playlist(){

        System.out.println("Welche Playlist moechten Sie laden?: ");
        playlistVerwalter.loadFromFile("ausgabe");
        String eingabe = StaticScanner.nextString();
        playlistVerwalter.loadFromFile(eingabe);

    }

    private String eingabeAbfrage(String eingabe){
        System.out.println("Sie haben "+ eingabe + " eingegeben. \nWenn sie damit einverstanden sind bestaetigen sie mit 'y',");
        System.out.println("anderenfalles geben sie ein 'n' ein.");
        String pruefung = StaticScanner.nextString();
        if (pruefung.equals("y")) {
            return eingabe;
        }else{
            System.out.println("\n Sie sind unzufrieden! Bitte neu eingeben: ");
            eingabe = StaticScanner.nextString();
            return eingabeAbfrage(eingabe);
        }
    }

}
