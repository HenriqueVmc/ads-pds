package pct;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Principal implements ListSelectionListener, ActionListener {

    //https://coderanch.com/t/275345/java/File-Open-Save-Operations-Swing
    JList<String> list;
    JScrollPane scroll;
    JTextArea txtArea;
    JTextField txtTitulo;
    JLabel lblTitulo, lblDescricao;
    JButton btnAdd, btnDelete, btnEdit;

    JMenuBar menuBar;
    JMenu menu, submenu;
    JMenuItem open, save, quit;

    JFileChooser chooser;
    FileInputStream fis;
    BufferedReader br;
    //static Principal app;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Principal();
            }
        });
    }

    public Principal() {

        JPanel tudo = new JPanel(new BorderLayout());
        JFrame frame = new JFrame("JList");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 428);
        frame.setVisible(true);
        frame.setContentPane(tudo);

        //MENU        
        menuBar = new JMenuBar();
        menu = new JMenu("ARQUIVO");
        menuBar.add(menu);

        open = new JMenuItem("OPEN");
        open.addActionListener(this);
        menu.add(open);

        save = new JMenuItem("SAVE");
        save.addActionListener(this);
        menu.add(save);

        menu.addSeparator();

        quit = new JMenuItem("QUIT");
        quit.addActionListener(this);
        menu.add(quit);

        tudo.add(menuBar, BorderLayout.NORTH);

        //LEFT
        DefaultListModel<String> listModel = new DefaultListModel<String>();
        //listModel.addElement("Figura 1");
        //listModel.addElement("Figura 2");
        list = new JList<String>(listModel);
        list.setPreferredSize(new Dimension(200, 100));
        list.addListSelectionListener(this);
        scroll = new JScrollPane(list);

        tudo.add(scroll, BorderLayout.WEST);

        //RIGHT
        JPanel panelRight = new JPanel();
        JPanel panelTitulo = new JPanel(new GridLayout(3, 1));
        JPanel panelDescricao = new JPanel(new GridLayout(2, 1));

        lblTitulo = new JLabel("Título:");
        txtTitulo = new JTextField(25);
        lblDescricao = new JLabel("Descrição:");
        txtArea = new JTextArea(16, 24);
        JScrollPane panelTextA = new JScrollPane(txtArea);
        panelTextA.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        panelTitulo.add(lblTitulo);
        panelTitulo.add(txtTitulo);
        panelTitulo.add(lblDescricao);
        panelDescricao.add(panelTextA);

        JPanel panelButtons = new JPanel();
        btnAdd = new JButton("Adicionar");
        btnAdd.setActionCommand("Add");
        btnDelete = new JButton("Remover");
        btnEdit = new JButton("Alterar");

        panelButtons.add(btnAdd);
        panelButtons.add(btnDelete);
        panelButtons.add(btnEdit);

        panelDescricao.add(panelButtons);

        panelRight.add(panelTitulo);
        panelRight.add(panelDescricao);

        tudo.add(panelRight, BorderLayout.CENTER);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        // TODO Auto-generated method stub
        if (list.getSelectedValue().equalsIgnoreCase("")) {
            
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getActionCommand().equals("Add")){
            
        }
        
        chooser = new JFileChooser();
        if (chooser.showOpenDialog(menu) == JFileChooser.APPROVE_OPTION) {

            Path path = chooser.getSelectedFile().toPath();

            try {
                String contentString = "";

                for (String s : Files.readAllLines(path, StandardCharsets.UTF_8)) {
                    contentString += s;
                }

                txtArea.setText(contentString);

            } catch (IOException ea) {
                // TODO Auto-generated catch block
                ea.printStackTrace();
            }
        }
    }
}
