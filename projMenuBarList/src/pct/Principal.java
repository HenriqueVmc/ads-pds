package pct;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.spi.FileTypeDetector;
import java.util.ArrayList;

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
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;

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

    DefaultListModel<String> listModel;

    JFileChooser chooser;
    FileInputStream fis;
    BufferedReader br;
    BufferedWriter bw;
    //static Principal app;

    Publicacao nota;

    ArrayList<Publicacao> anotacoes = new ArrayList<Publicacao>();

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
        listModel = new DefaultListModel<String>();

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
        btnAdd.addActionListener(this);

        btnDelete = new JButton("Remover");
        btnDelete.setActionCommand("Delete");
        btnDelete.addActionListener(this);

        btnEdit = new JButton("Alterar");
        btnEdit.setActionCommand("Edit");
        btnEdit.addActionListener(this);

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
        int selectedIndex = list.getSelectedIndex();

        if (selectedIndex != -1) {
            Publicacao notaSelecionada = anotacoes.get(selectedIndex);
            setNota(notaSelecionada);          
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getActionCommand().equalsIgnoreCase("Add")) {

            Publicacao nota = new Publicacao();

            nota.setTitulo(txtTitulo.getText());
            nota.setDescricao(txtArea.getText());

            anotacoes.add(nota);

            listModel.addElement(nota.getTitulo());

            txtArea.setText("");
            txtTitulo.setText("");
        }

        if (e.getActionCommand().equalsIgnoreCase("Delete")) {

            int selectedIndex = list.getSelectedIndex();

            anotacoes.remove(selectedIndex);
            listModel.remove(selectedIndex);

            txtArea.setText("");
            txtTitulo.setText("");
        }

        if (e.getActionCommand().equalsIgnoreCase("Edit")) {

            int selectedIndex = list.getSelectedIndex();

            Publicacao p = anotacoes.get(selectedIndex);

            p.setTitulo(txtTitulo.getText());
            p.setDescricao(txtArea.getText());

            listModel.set(selectedIndex, p.getTitulo());

            txtArea.setText("");
            txtTitulo.setText("");
        }

        if (e.getSource() == open) {
            chooser = new JFileChooser();
            if (chooser.showOpenDialog(menu) == JFileChooser.APPROVE_OPTION) {

                Path path = chooser.getSelectedFile().toPath();

                try {
                    FileReader reader = new FileReader(chooser.getSelectedFile());
                    br = new BufferedReader(reader);

                    String linhas = null;
                    String texto = new String();
                    while ((linhas = br.readLine()) != null) {
                        texto += linhas;
                    }

                    br.close();

                    int pos = texto.indexOf(";");
                    String titulo = texto.substring(0, pos);
                    String descricao = texto.substring(pos + 1, texto.length() - 1);

                    txtTitulo.setText(titulo);
                    txtArea.setText(descricao);

                } catch (IOException ea) {
                    // TODO Auto-generated catch block
                    ea.printStackTrace();
                }
            }
        }

        if (e.getSource() == save) {
            chooser = new JFileChooser();
            if (chooser.showSaveDialog(menu) == JFileChooser.APPROVE_OPTION) {
                String text = txtTitulo.getText() + ";" + txtArea.getText() + ";";
                File file = chooser.getSelectedFile();

                try {
                    FileWriter writer = new FileWriter(file.getPath());
                    bw = new BufferedWriter(writer);
                    bw.write(text);
                    bw.close();

                } catch (Exception ae) {
                }
            }
        }

        if (e.getSource() == quit) {                     
            if(JOptionPane.showConfirmDialog(null, "Deseja realmente sair?") == 0) System.exit(0);
        }
    }

    public void setNota(Publicacao p) {
        txtArea.setText(p.getDescricao());
        txtTitulo.setText(p.getTitulo());
    }
}
