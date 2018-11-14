package sample;

import de.hsrm.mi.prog.util.StaticScanner;

public class Controller {

    private Mp3Player mp3Player = new Mp3Player();
    private PlaylistVerwalter playlistVerwalter = new PlaylistVerwalter();
    private final String [] BEFEHLE = {"", "play", "pause", "stop", "volume", "quit", "bearbeiten"};

    public void start(){

        boolean anAus = false;
        while (anAus == false) {
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

                            System.out.println("bearbeiten");
                            bearbeiten();


                        default:
                            System.out.println("Nope");
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
            System.out.println("Wie soll die Playlist heißen?");
            eingabe = StaticScanner.nextString();
            eingabe = eingabeAbfrage(eingabe);
            playlistVerwalter.addPlaylist(eingabe);

            hinzufügenLied(eingabe);

        }

    }

    private void hinzufügenLied(String playlist){

        System.out.println("Welchen Song möchtest du zur Playlist hinzufügen? :");



        for (int i = 0; i < playlistVerwalter.getAllSongs().size(); i++){

            System.out.println("Song "+ (i+1)+": "+ playlistVerwalter.getAllSongs().get(i));

        }

        String eingabe = StaticScanner.nextString();


        int position = playlistVerwalter.findPlaylist(playlist);

       playlistVerwalter.getPlaylist(position).trackHinzufügen(eingabe);


       löschenLied(playlist);

    }

    private void löschenLied(String playlist){

        System.out.println("Welchen Song möchtest du aus der Playlist löschen? :");

        String eingabe = StaticScanner.nextString();

        int position = playlistVerwalter.findPlaylist(playlist);

        playlistVerwalter.getPlaylist(position).trackLöschen(eingabe);


    }

    private String eingabeAbfrage(String eingabe){
        System.out.println("Sie haben "+ eingabe + "eingegeben. \n Wenn sie damit einverstanden sind bestätigen sie mit 'y',");
        System.out.println("anderenfalles geben sie ein 'n' ein.");
        String prüfung = StaticScanner.nextString();
        if (prüfung.equals("y")) {
            return eingabe;
        }else{
            System.out.println("\n Sie sind unzufrieden! Bitte neu eingeben: ");
            eingabe = StaticScanner.nextString();
            return eingabeAbfrage(eingabe);
        }
    }

}
