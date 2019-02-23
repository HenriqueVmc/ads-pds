package pct;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
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
    private JComboBox jComboBoxCurso;
    private JCheckBox jCheckBoxSexoM;
    private JCheckBox jCheckBoxSexoF;
    private JLabel jLabelAno, jLabelNome, jLabelRendaFamiliar, jLabelCurso, jLabelCPF;
    private JButton jButtonSalvar, jButtonEditar, jButtonExcluir;
    private JTable jTable;
    private JScrollPane jScrollPane;
    private DefaultTableModel dtm;
    private ArrayList<Aluno> alunos = new ArrayList<>();
    JMenuBar menuBar;
    JMenu menu;
    JMenuItem open, save, quit;

    JFileChooser chooser;
    FileInputStream fis;
    BufferedReader br;
    BufferedWriter bw;

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
        modelo.addElement("ADS");
        modelo.addElement("PG");
        modelo.addElement("MODA");
        jComboBoxCurso.setModel(modelo);
        jComboBoxCurso.setSelectedIndex(-1);
    }

    public Principal() {

        JPanel tudo = new JPanel(new BorderLayout());
        jFrame = new JFrame("Cadastro de Aluno");
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrame.setLayout(null);
        jFrame.setSize(739, 380);
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
        JPanel panelComponents = new JPanel(new GridLayout(7, 1));

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

        JPanel Curso = new JPanel(new GridLayout(2, 1));
        jLabelCurso = new JLabel("Categoria");
        jComboBoxCurso = new JComboBox();
        Curso.add(jLabelCurso);
        Curso.add(jComboBoxCurso);

        JPanel renda = new JPanel(new GridLayout(2, 1));
        jLabelRendaFamiliar = new JLabel("Renda Familiar");
        jTextFieldRendaFamiliar = new JTextField(18);
        renda.add(jLabelRendaFamiliar);
        renda.add(jTextFieldRendaFamiliar);

        JPanel check = new JPanel();
        jCheckBoxSexoM = new JCheckBox("Masculino");
        jCheckBoxSexoF = new JCheckBox("Feminino");
        check.add(jCheckBoxSexoM);
        check.add(jCheckBoxSexoF);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jButtonSalvar = new JButton("Salvar");
        jButtonEditar = new JButton("Editar");
        jButtonExcluir = new JButton("Excluir");
        buttons.add(jButtonSalvar);
        buttons.add(jButtonEditar);
        buttons.add(jButtonExcluir);
        
        panelComponents.add(nome);
        panelComponents.add(ano);
        panelComponents.add(cpf);
        panelComponents.add(Curso);
        panelComponents.add(renda);
        panelComponents.add(check);
        panelComponents.add(buttons);

        panelLeft.add(panelComponents);
        tudo.add(panelLeft, BorderLayout.WEST);

        //RIGHT
        JPanel panelRight = new JPanel(new BorderLayout());
        jTable = new JTable();
        configurarJTable();
        jScrollPane = new JScrollPane(jTable);        

        panelRight.add(jScrollPane, BorderLayout.CENTER);  

        tudo.add(panelRight, BorderLayout.EAST);

        configurarJComboBox();
        configurarJFormattedTextField();

        acaoBotaoSalvar();
        acaoEditar();
        acaoExcluir();
        check();

        jFrame.setVisible(true);
    }

    private void configurarJTable() {
        dtm = new DefaultTableModel();
        dtm.addColumn("Nome");
        dtm.addColumn("CPF");
        dtm.addColumn("Curso");
        jTable.setModel(dtm);
    }
    
    short ano; double renda; String cpf;
    private void acaoBotaoSalvar() {
        jButtonSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                            	
            	if(validarCampos() == 0) return;

                Aluno aluno = new Aluno();
               
                salvarAluno(aluno);
                
                alunos.add(aluno);
                dtm.addRow(new Object[]{
                    aluno.getNome(),
                    aluno.getCpf(),
                    aluno.getCurso()
                });
                
                limparCampos(); 
            }
        });
    }

    private void limparCampos() {
        jTextFieldAno.setText("");
        jTextFieldNome.setText("");
        jTextFieldRendaFamiliar.setText("");
        jCheckBoxSexoM.setSelected(false);
        jComboBoxCurso.setSelectedIndex(-1);
        jFormattedTextFieldCPF.setText("");
        jTextFieldNome.requestFocus();
        linhaSelecionada = -1;
    }

    private void acaoEditar() {
        jButtonEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jTable.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Selecione um registro");
                    return;
                }

                linhaSelecionada = jTable.getSelectedRow();
                Aluno aluno = alunos.get(linhaSelecionada);
               
                salvarAluno(aluno);
                
                alunos.set(linhaSelecionada, aluno);
                dtm.setValueAt(aluno.getNome(), linhaSelecionada, 0);
                dtm.setValueAt(aluno.getCpf(), linhaSelecionada, 1);
                dtm.setValueAt(aluno.getCurso(), linhaSelecionada, 2);
                
                limparCampos(); 
            }
        });
        
        jTable.addMouseListener(new MouseAdapter() {		
        	public void mouseClicked(MouseEvent e) {
                if (jTable.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Selecione um registro");
                    return;
                }
                
        		linhaSelecionada = jTable.getSelectedRow();
                Aluno aluno = alunos.get(linhaSelecionada);
                preencherCampos(aluno);
        	}
        });
        
    }
    
    private void check() {
        jCheckBoxSexoF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jCheckBoxSexoF.isSelected()){
                    jCheckBoxSexoM.setSelected(false);
                }
            }
        });
        jCheckBoxSexoM.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jCheckBoxSexoM.isSelected()){
                    jCheckBoxSexoF.setSelected(false);
                }
            }
        });
    }

    private void preencherCampos(Aluno aluno) {
        jTextFieldNome.setText(aluno.getNome());
        jTextFieldAno.setText(String.valueOf(aluno.getAno()));
        jTextFieldRendaFamiliar.setText(String.valueOf(aluno.getRendaFamiliar()));
        jComboBoxCurso.setSelectedItem(aluno.getCurso());
        
        if(aluno.getSexo() == 1) jCheckBoxSexoM.setSelected(true);
        else jCheckBoxSexoF.setSelected(true);
        
        jFormattedTextFieldCPF.setText(aluno.getCpf());
    }

    private void acaoExcluir() {
        jButtonExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jTable.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Selecione um registro");
                    return;
                }

                int escolha = JOptionPane.showConfirmDialog(null,"Deseja realmente apagar?", "Aviso",JOptionPane.YES_NO_OPTION);
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
    public void actionPerformed(ActionEvent e) {
    	
        if (e.getSource() == open) {
            chooser = new JFileChooser();
            if (chooser.showOpenDialog(menu) == JFileChooser.APPROVE_OPTION) {

                Path path = chooser.getSelectedFile().toPath();

                try {
                    FileReader reader = new FileReader(chooser.getSelectedFile());
                    br = new BufferedReader(reader);
                    String linha = null;                                        
                    while ((linha = br.readLine()) != null) {
                        dtm.addRow(linha.split("; "));
                    }
                    
//                    Aluno aluno = new Aluno();
//                    aluno.setNome(dtm.getColumnClass(0).toString());
//                    aluno.setAno((dtm.getColumnClass(1).toString());
//                    aluno.setNome(dtm.getColumnClass(2).toString());                  
//                    preencherCampos(aluno);                                        
//                    salvarAluno(aluno);
                    
                    reader.close();
                    br.close();

                } catch (IOException ea) {
                    // TODO Auto-generated catch block
                    ea.printStackTrace();
                }
            }
        }

        if (e.getSource() == save) {
            chooser = new JFileChooser();
            if (chooser.showSaveDialog(menu) == JFileChooser.APPROVE_OPTION) {

                try {
                    File file = chooser.getSelectedFile();
                    PrintWriter os = new PrintWriter(file);

                    for (int row = 0; row < jTable.getRowCount(); row++) {
                        for (int col = 0; col < jTable.getColumnCount(); col++) {                                                                                  
                            os.print(jTable.getValueAt(row, col)+"; ");
                        }
                    }

                    os.close();

                } catch (Exception ae) {
                }
            }
        }

        if (e.getSource() == quit) {
            if (JOptionPane.showConfirmDialog(null, "Deseja realmente sair?") == 0) {
                System.exit(0);
            }
        }                      
        
    }
    
    private int validarCampos() {
    	ano = 0; renda = 0; cpf = "";
    	
    	if (jTextFieldNome.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nome deve ser preenchido");
            jTextFieldNome.requestFocus();
            return 0;
        }

        if (jTextFieldNome.getText().length() < 3) {
            JOptionPane.showMessageDialog(null, "Nome deve conter 3 caracteres");
            jTextFieldNome.requestFocus();
            return 0;
        }

        if (jTextFieldAno.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ano deve ser preenchido");
            jTextFieldAno.requestFocus();
            return 0;
        }
        ano = 0;
        try {
            ano = Short.parseShort(jTextFieldAno.getText());
            if (ano < 1500) {
                JOptionPane.showMessageDialog(null, "Ano não pode ser menor que 1500");
                jTextFieldAno.requestFocus();
                return 0;
            }
            int anoAtual = LocalDate.now().getYear();
            if (ano > anoAtual) {
                JOptionPane.showMessageDialog(null, "Ano não deve ser maior que o ano " + anoAtual);
                jTextFieldAno.requestFocus();
                return 0;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Ano deve conter somente números");
            jTextFieldAno.requestFocus();
            return 0;
        }

        cpf = jFormattedTextFieldCPF.getText().replace(".", "").replace("-", "");

        if (cpf.isEmpty()) {
            JOptionPane.showMessageDialog(null, "CPF deve ser preenchido");
            jFormattedTextFieldCPF.requestFocus();
            return 0;
        }

        if (cpf.length() < 11) {
            JOptionPane.showMessageDialog(null, "CPF deve conter 11 digitos");
            jFormattedTextFieldCPF.requestFocus();
            return 0;
        }

        //if(jComboBoxCurso.getSelectedIndex() == -1)
        if (jComboBoxCurso.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "Curso deve ser selecionado");
            jComboBoxCurso.showPopup();
            return 0;
        }
        String rendaFamiliar = jTextFieldRendaFamiliar.getText().toUpperCase().replace("R", "").replace("$", "").replace(".", "").replace(",", ".").replace(" ", "");
        if (rendaFamiliar.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Renda Familiar deve ser preenchida");
            jTextFieldRendaFamiliar.requestFocus();
            return 0;
        }

        renda = 0;
        try {
            renda = Double.parseDouble(rendaFamiliar);
            if (renda < 0) {
                JOptionPane.showMessageDialog(null, "Renda Familiar deve ser positiva");
                jTextFieldRendaFamiliar.requestFocus();
                return 0;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Renda Familiar deve conter somente números");
            jTextFieldRendaFamiliar.requestFocus();
            return 0;
        }
        return 1;
    }
       
    private void salvarAluno(Aluno aluno) {
    	
    	aluno.setNome(jTextFieldNome.getText());
        aluno.setCpf(cpf);
        aluno.setRendaFamiliar(renda);
        aluno.setAno(ano);
        
        int sexo = (jCheckBoxSexoM.isSelected() == true) ? 1 : 0;
        aluno.setSexo((short)sexo);
        
        aluno.setCurso(jComboBoxCurso.getSelectedItem().toString());   
    }
}
