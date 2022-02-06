import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JFileChooser;
import java.util.zip.*;


public class FIle_Comprssor extends Component {

    static JFrame f;
    static JTextField fileName, desTination;
    static JButton compress, decompress, back;
    static File selectedFile;
    static JLabel title, labelCom, labelDecom, labelEmpty;

    static String[] letters;
    static int index = -1;

//Method for splash screen
    public static void Loading(){
        ImageIcon img = new ImageIcon("asset/compressed-file.png");

        JLabel label = new JLabel();
        label = new JLabel("Loading....");
        label.setBounds(180,-40, 300,400);
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(SwingConstants.BOTTOM);
        label.setForeground(Color.gray);
        label.setIcon(img);

        ImageIcon ico = new ImageIcon("asset/compressed-file.png");
        JFrame frame = new JFrame();
        frame = new JFrame("File Compressor");
        frame.setIconImage(ico.getImage());
        frame.setLayout(null);
        frame.setSize(500,350);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        frame.add(label);
        frame.setVisible(true);


        try{
            Thread.sleep(5000);
            f.setVisible(true);
            frame.dispose();
        }catch (Exception e){
            System.out.println(e);
        }
    }


//Method for verifying if the compression or decompression is successful
    public static void SearchFile() {
        try {
            Thread.sleep(1000);

            File dir = new File(selectedFile.getParent());
            String children[] = dir.list();

            if (children == null) {
                System.out.println("directory do not exist");
            } else {
                for (int i = 0; i < children.length; i++) {
                    if (children[i] == selectedFile.getName()) {
                        break;
                    }
                }
                JOptionPane.showMessageDialog(null, "OPERATION COMPLETE!");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

//Method for compressing the selected file
    public static void compression(File source, File destination) throws IOException{
        byte[] buffer = new byte[1024];
        FileInputStream fis = new FileInputStream(source);
        FileOutputStream fos = new FileOutputStream(destination);
        GZIPOutputStream gzos = new GZIPOutputStream(fos);
        int read;

        while((read = fis.read(buffer))!= -1){
            gzos.write(buffer, 0, read);
        }
        gzos.finish();
        gzos.close();
        fos.close();
        fis.close();

        SearchFile();
    }

//Method for decompressing the compressed file
    public static void decompression(File source, File destination) throws IOException{
        byte[] buffer = new byte[1204];
        FileInputStream fis = new FileInputStream(source);
        GZIPInputStream gzis = new GZIPInputStream(fis);
        FileOutputStream fos = new FileOutputStream(destination);

        int read;

        while((read = gzis.read(buffer))!= -1){
            fos.write(buffer, 0, read);
        }
        gzis.close();
        fos.close();
        fis.close();

        SearchFile();
    }

//Method for Opening the Flashdrive library after choosing either compression or decompression
    public void OpenDrive(){

                if(index == -1){
                    JOptionPane.showMessageDialog(null,"No Flash Drive is inserted");
                }else{
                    fileName.setText(" ");
                    desTination.setText(" ");

                    JFileChooser fc = new JFileChooser(letters[index]+":\\");
                    int option = fc.showOpenDialog(FIle_Comprssor.this);

                    if(option == JFileChooser.APPROVE_OPTION){
                        selectedFile = fc.getSelectedFile();
                        fileName.setText(selectedFile.getAbsolutePath());
                        desTination.setText(selectedFile.getParent()+"rename_your_file.txt");
                    }
                }

    }

//Method to display the Menu
    public void Menu(){
        labelEmpty.setVisible(false);
        labelCom.setVisible(true);
        labelDecom.setVisible(true);

        labelCom.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                labelCom.setVisible(false);
                labelDecom.setVisible(false);
                fileName.setVisible(true);
                desTination.setVisible(true);
                compress.setVisible(true);
                back.setVisible(true);

                OpenDrive();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        labelDecom.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                labelCom.setVisible(false);
                labelDecom.setVisible(false);
                fileName.setVisible(true);
                desTination.setVisible(true);
                decompress.setVisible(true);
                back.setVisible(true);

                OpenDrive();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

//Method for automatically detecting the inserted flash drive
    public void FlashDriveDetect(){
        letters = new String[]{ "A", "B", "C", "D", "E", "F", "G", "H", "I"};
        File[] drives = new File[letters.length];
        boolean[] isDrive = new boolean[letters.length];
        boolean loop = true;


        for ( int i = 0; i < letters.length; ++i )
        {
            drives[i] = new File(letters[i]+":/");

            isDrive[i] = drives[i].canRead();
        }

        System.out.println("FindDrive: waiting for devices...");

        while(loop)
        {
            // check each drive
            for ( int i = 0; i < letters.length; ++i )
            {
                boolean pluggedIn = drives[i].canRead();

                // if the state has changed output a message
                if ( pluggedIn != isDrive[i] )
                {
                    if ( pluggedIn ){
                        index = i;
                        Menu();
                        loop = false;
                    } else
                    isDrive[i] = pluggedIn;
                }
            }

            // wait before looping
            try { Thread.sleep(100); }
            catch (InterruptedException e) { /* do nothing */ }

        }
    }


//Main method, this is where the initialization of components are declared
    public static void main(String[] args) {

        ImageIcon image = new ImageIcon("asset/folder.png");

        labelEmpty = new JLabel("FLASH DRIVE NOT FOUND");
        labelEmpty.setBounds(170,-40, 300,400);
        labelEmpty.setHorizontalTextPosition(JLabel.CENTER);
        labelEmpty.setVerticalTextPosition(SwingConstants.BOTTOM);
        labelEmpty.setForeground(Color.gray);
        labelEmpty.setIcon(image);

        ImageIcon imageCom = new ImageIcon("asset/data-compression.png");

        labelCom = new JLabel("COMPRESS");
        labelCom.setBounds(70,65, 200,200);
        labelCom.setHorizontalTextPosition(JLabel.CENTER);
        labelCom.setVerticalTextPosition(SwingConstants.BOTTOM);
        labelCom.setForeground(Color.gray);
        labelCom.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        labelCom.setIcon(imageCom);
            labelCom.setVisible(false);

        ImageIcon imageDecom = new ImageIcon("asset/decompress.png");

        labelDecom = new JLabel("DECOMPRESS");
        labelDecom.setBounds(300,65, 200,200);
        labelDecom.setHorizontalTextPosition(JLabel.CENTER);
        labelDecom.setVerticalTextPosition(SwingConstants.BOTTOM);
        labelDecom.setForeground(Color.gray);
        labelDecom.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        labelDecom.setIcon(imageDecom);
            labelDecom.setVisible(false);

        fileName = new JTextField("Insert Path file....");
        desTination = new JTextField("Insert Path destination....");

        fileName.setBounds(100,100, 300,30);
        desTination.setBounds(100,150, 300,30);
            fileName.setVisible(false);
            desTination.setVisible(false);

        compress = new JButton("COMPRESS");
        decompress = new JButton("DECOMPRESS");
        back = new JButton("BACK");

        compress.setBounds(100, 200,130, 20);
        decompress.setBounds(100, 200,130, 20);
        back.setBounds(270, 200,130, 20);
            compress.setVisible(false);
            decompress.setVisible(false);
           back.setVisible(false);

        title = new JLabel("FILE COMPRESSOR/DECOMPRESSOR");
        title.setFont(new Font("ARIAL", Font.BOLD, 22));
        title.setBounds(43, 30,450, 30);

     ImageIcon ico = new ImageIcon("asset/compressed-file.png");
        f = new JFrame("File Compressor");
        f.setIconImage(ico.getImage());
        f.setLayout(null);
        f.setSize(500,350);
        f.setResizable(false);
        f.setLocationRelativeTo(null);

        f.add(fileName);
        f.add(desTination);
        f.add(compress);
        f.add(decompress);
        f.add(back);
        f.add(title);
        f.add(labelEmpty);
        f.add(labelCom);
        f.add(labelDecom);

        Loading();

        compress.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                File source = new File(fileName.getText());
                File destination = new File(desTination.getText());
                try{
                    compression(source, destination);
                }catch(IOException ev){
                    System.out.println(ev);
                }

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        decompress.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                File source = new File(fileName.getText());
                File destination = new File(desTination.getText());
                try{
                    decompression(source, destination);
                }catch(IOException ev){
                    System.out.println(ev);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        back.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                labelCom.setVisible(true);
                labelDecom.setVisible(true);
                fileName.setVisible(false);
                desTination.setVisible(false);
                compress.setVisible(false);
                decompress.setVisible(false);
                back.setVisible(false);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

      FIle_Comprssor Automatic = new FIle_Comprssor();
       Automatic.FlashDriveDetect();

    }
}