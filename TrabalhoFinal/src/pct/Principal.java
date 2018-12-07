package pct;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

/**
 * @author Alunos
 */
public class Principal implements ActionListener {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Principal();
            }
        });
    }

    private int linhaSelecionada = -1;
    private JFrame jFrame;
    private JTextField jTextFieldNome, jTextFieldRendaFamiliar, jTextFieldAno;
    private JFormattedTextField jFormattedTextFieldCPF;
    private JComboBox jComboBoxSexo;
    private JCheckBox jCheckBoxPrivado;
    private JLabel jLabelAno, jLabelNome, jLabelRendaFamiliar, jLabelSexo, jLabelCPF;
    private JButton jButtonAdicionar, jButtonEditar, jButtonExcluir;
    private JTable jTable;
    private JScrollPane jScrollPane;
    private DefaultTableModel dtm;
    private ArrayList<Aluno> alunos = new ArrayList<>();
    JMenuBar menuBar;
    JMenu menu;
    JMenuItem open, save, quit;
    //DefaultListModel listModel;

    private void configurarJFormattedTextField() {
        try {
            MaskFormatter maskFormatter = new MaskFormatter("###.###.###-##");
            maskFormatter.install(jFormattedTextFieldCPF);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Chame o prog");
        }
    }

    private void configurarJComboBox() {
        DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
        modelo.addElement("Feminino");
        modelo.addElement("Masculino");
        modelo.addElement("Outros");
        jComboBoxSexo.setModel(modelo);
        jComboBoxSexo.setSelectedIndex(-1);
    }

    public Principal() {

        JPanel tudo = new JPanel(new BorderLayout());
        jFrame = new JFrame("Cadastro de Aluno");
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrame.setLayout(null);
        jFrame.setSize(700, 500);
        jFrame.setLocationRelativeTo(null);
        jFrame.setContentPane(tudo);

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
        JPanel panelLeft = new JPanel();
        JPanel panelComponents = new JPanel(new GridLayout(6, 1));

        JPanel nome = new JPanel(new GridLayout(2, 1));
        jLabelNome = new JLabel("Nome");
        jTextFieldNome = new JTextField(18);
        nome.add(jLabelNome);
        nome.add(jTextFieldNome);

        JPanel ano = new JPanel(new GridLayout(2, 1));
        jLabelAno = new JLabel("Ano de Matrícula");
        jTextFieldAno = new JTextField(18);
        ano.add(jLabelAno);
        ano.add(jTextFieldAno);

        JPanel cpf = new JPanel(new GridLayout(2, 1));
        jLabelCPF = new JLabel("CPF");
        jFormattedTextFieldCPF = new JFormattedTextField();
        cpf.add(jLabelCPF);
        cpf.add(jFormattedTextFieldCPF);

        JPanel sexo = new JPanel(new GridLayout(2, 1));
        jLabelSexo = new JLabel("Categoria");
        jComboBoxSexo = new JComboBox();
        sexo.add(jLabelSexo);
        sexo.add(jComboBoxSexo);

        JPanel renda = new JPanel(new GridLayout(2, 1));
        jLabelRendaFamiliar = new JLabel("Renda Familiar");
        jTextFieldRendaFamiliar = new JTextField(18);
        renda.add(jLabelRendaFamiliar);
        renda.add(jTextFieldRendaFamiliar);

        JPanel check = new JPanel(new GridLayout(2, 1));
        jCheckBoxPrivado = new JCheckBox("Privado");
        check.add(jCheckBoxPrivado);

        panelComponents.add(nome);
        panelComponents.add(ano);
        panelComponents.add(cpf);
        panelComponents.add(sexo);
        panelComponents.add(renda);
        panelComponents.add(check);

        panelLeft.add(panelComponents);
        tudo.add(panelLeft, BorderLayout.WEST);

        //RIGHT
        JPanel panelRight = new JPanel(new BorderLayout());
        jTable = new JTable();
        configurarJTable();
        jScrollPane = new JScrollPane(jTable);

        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jButtonAdicionar = new JButton("Adicionar");
        jButtonEditar = new JButton("Editar");
        jButtonExcluir = new JButton("Excluir");
        panelButtons.add(jButtonAdicionar);
        panelButtons.add(jButtonEditar);
        panelButtons.add(jButtonExcluir);

        panelRight.add(jScrollPane, BorderLayout.CENTER);
        panelRight.add(panelButtons, BorderLayout.SOUTH);

        tudo.add(panelRight, BorderLayout.EAST);

        configurarJComboBox();
        configurarJFormattedTextField();

        acaoBotaoAdicionar();
        acaoEditar();
        acaoExcluir();

        jFrame.setVisible(true);
    }

    private void configurarJTable() {
        dtm = new DefaultTableModel();
        dtm.addColumn("Nome");
        dtm.addColumn("CPF");
        dtm.addColumn("Renda Familiar");
        jTable.setModel(dtm);
    }

    private void acaoBotaoAdicionar() {
        jButtonAdicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jTextFieldNome.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Nome deve ser preenchido");
                    jTextFieldNome.requestFocus();
                    return;
                }

                if (jTextFieldNome.getText().trim().length() < 3) {
                    JOptionPane.showMessageDialog(null, "Nome deve conter 3 caracteres");
                    jTextFieldNome.requestFocus();
                    return;
                }

                if (jTextFieldAno.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Ano deve ser preenchido");
                    jTextFieldAno.requestFocus();
                    return;
                }
                short ano = 0;
                try {
                    ano = Short.parseShort(
                            jTextFieldAno.getText().trim());
                    if (ano < 1500) {
                        JOptionPane.showMessageDialog(null, "Ano não pode ser menor que 1500");
                        jTextFieldAno.requestFocus();
                        return;
                    }
                    int anoAtual = LocalDate.now().getYear();
                    if (ano > anoAtual) {
                        JOptionPane.showMessageDialog(null, "Ano não deve ser maior que o ano " + anoAtual);
                        jTextFieldAno.requestFocus();
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Ano deve conter somente números");
                    jTextFieldAno.requestFocus();
                    return;
                }

                String cpf = jFormattedTextFieldCPF.getText()
                        .replace(".", "").replace("-", "");

                if (cpf.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "CPF deve ser preenchido");
                    jFormattedTextFieldCPF.requestFocus();
                    return;
                }

                if (cpf.length() < 11) {
                    JOptionPane.showMessageDialog(null, "CPF deve conter 11 digitos");
                    jFormattedTextFieldCPF.requestFocus();
                    return;
                }

                //if(jComboBoxCategoria.getSelectedIndex() == -1)
                if (jComboBoxSexo.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(null, "Sexo deve ser selecionado");
                    jComboBoxSexo.showPopup();
                    return;
                }
                String rendaFamiliar = jTextFieldRendaFamiliar
                        .getText().trim().toUpperCase().replace("R", "").replace("$", "").replace(".", "").replace(",", ".").replace(" ", "");
                if (rendaFamiliar.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Renda Familiar deve ser preenchida");
                    jTextFieldRendaFamiliar.requestFocus();
                    return;
                }

                double renda = 0;
                try {
                    renda = Double.parseDouble(rendaFamiliar);
                    if (renda < 0) {
                        JOptionPane.showMessageDialog(null, "Renda Anual deve ser positiva");
                        jTextFieldRendaFamiliar.requestFocus();
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Renda Anual deve conter somente números");
                    jTextFieldRendaFamiliar.requestFocus();
                    return;
                }

                Aluno aluno = new Aluno();
                aluno.setNome(jTextFieldNome.getText());
                aluno.setCpf(cpf);
                aluno.setRendaFamiliar(renda);
                aluno.setAno(ano);
                aluno.setPrivado(jCheckBoxPrivado.isSelected());
                aluno.setSexo(jComboBoxSexo.getSelectedItem().toString());

                if (linhaSelecionada == -1) {
                    alunos.add(aluno);
                    dtm.addRow(new Object[]{
                        aluno.getNome(),
                        aluno.getCpf(),
                        aluno.getRendaFamiliar()
                    });
                } else {
                    alunos.set(linhaSelecionada, aluno);
                    dtm.setValueAt(aluno.getNome(), linhaSelecionada, 0);
                    dtm.setValueAt(aluno.getCpf(), linhaSelecionada, 1);
                    dtm.setValueAt(aluno.getRendaFamiliar(), linhaSelecionada, 2);
                }
                limparCampos();
            }
        });
    }

    private void limparCampos() {
        jTextFieldAno.setText("");
        jTextFieldNome.setText("");
        jTextFieldRendaFamiliar.setText("");
        jCheckBoxPrivado.setSelected(false);
        jComboBoxSexo.setSelectedIndex(-1);
        jFormattedTextFieldCPF.setText("");
        jTextFieldNome.requestFocus();
        linhaSelecionada = -1;
    }

    private void acaoEditar() {
        jButtonEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jTable.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Seleciona um registro filho");
                    return;
                }

                linhaSelecionada = jTable.getSelectedRow();
                Aluno aluno = alunos.get(linhaSelecionada);
                preencherCampos(aluno);
            }
        });
    }

    private void preencherCampos(Aluno aluno) {
        jTextFieldNome.setText(aluno.getNome());
        jTextFieldAno.setText(
                String.valueOf(aluno.getAno())
        );
        jTextFieldRendaFamiliar.setText(
                String.valueOf(aluno.getRendaFamiliar())
        );
        jComboBoxSexo.setSelectedItem(
                aluno.getSexo());
        jCheckBoxPrivado.setSelected(aluno.isPrivado());
        jFormattedTextFieldCPF.setText(aluno.getCpf());
    }

    private void acaoExcluir() {
        jButtonExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jTable.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Seleciona um registro filho");
                    return;
                }

                int escolha = JOptionPane.showConfirmDialog(null,
                        "Deseja realmente apagar?", "Aviso",
                        JOptionPane.YES_NO_OPTION);
                if (escolha == JOptionPane.YES_OPTION) {
                    linhaSelecionada = jTable.getSelectedRow();
                    dtm.removeRow(linhaSelecionada);
                    alunos.remove(linhaSelecionada);
                    limparCampos();
                }
            }
        });
    }   

    @Override
    public void actionPerformed(ActionEvent ae) {
//        if (e.getSource() == open) {
//            chooser = new JFileChooser();
//            if (chooser.showOpenDialog(menu) == JFileChooser.APPROVE_OPTION) {
//
//                Path path = chooser.getSelectedFile().toPath();
//
//                try {
//                    FileReader reader = new FileReader(chooser.getSelectedFile());
//                    br = new BufferedReader(reader);
//
//                    String linhas = null;
//                    String texto = new String();
//                    while ((linhas = br.readLine()) != null) {
//                        texto += linhas;
//                    }
//
//                    br.close();
//
//                    int pos = texto.indexOf(";");
//                    String titulo = texto.substring(0, pos);
//                    String descricao = texto.substring(pos + 1, texto.length() - 1);
//
//                    txtTitulo.setText(titulo);
//                    txtArea.setText(descricao);
//                    list.clearSelection();
//
//                } catch (IOException ea) {
//                    // TODO Auto-generated catch block
//                    ea.printStackTrace();
//                }
//            }
//        }
//
//        if (e.getSource() == save) {
//            chooser = new JFileChooser();
//            if (chooser.showSaveDialog(menu) == JFileChooser.APPROVE_OPTION) {
//                String text = txtTitulo.getText() + ";" + txtArea.getText() + ";";
//                File file = chooser.getSelectedFile();
//
//                try {
//                    FileWriter writer = new FileWriter(file.getPath());
//                    bw = new BufferedWriter(writer);
//                    bw.write(text);
//                    bw.close();
//                    list.clearSelection();
//
//                } catch (Exception ae) {
//                }
//            }
//        }
//
//        if (e.getSource() == quit) {                     
//            if(JOptionPane.showConfirmDialog(null, "Deseja realmente sair?") == 0) System.exit(0);
//        }
    }
}
