package sample;

import de.hsrm.mi.prog.util.StaticScanner;

public class Controller {

    Mp3Player mp3Player = new Mp3Player();
    private final String [] BEFEHLE = {"", "play", "pause", "stop", "volume", "quit"};

    void start(){

        boolean anAus = false;
        while (anAus == false) {
            String eingabe = StaticScanner.nextString();
            String[] parts = eingabe.split(" ");
            TrackVerwalter trackList = new TrackVerwalter();
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
                        default:
                            System.out.println("Nope");
                    }
                }
            }
        }
        System.out.println("Ende");
    }
}
