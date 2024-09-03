package main;

import java.io.*;

public class Config {
    Panel panel;

    public Config(Panel panel) {
        this.panel = panel;
    }

    public void saveConfig() {
        try {
            BufferedWriter bw  = new BufferedWriter(new FileWriter("config.txt"));

            //Full screen
            if (panel.fullScreenOn) {
                bw.write("On");
            }
            else {
                bw.write("Off");
            }
            bw.newLine();

            //Music volume
            bw.write(String.valueOf(panel.music.volumeScale));
            bw.newLine();

            //Sound Effect volume
            bw.write(String.valueOf(panel.soundEffect.volumeScale));
            bw.newLine();

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadConfig() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("config.txt"));

            String s = br.readLine();

            //Full screen
            if (s.equals("On")) {
                panel.fullScreenOn = true;
            }
            else if (s.equals("Off")) {
                panel.fullScreenOn = false;
            }

            //Music volume
            s = br.readLine();
            panel.music.volumeScale = Integer.parseInt(s);

            //Sound Effect volume
            s = br.readLine();
            panel.soundEffect.volumeScale = Integer.parseInt(s);

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
