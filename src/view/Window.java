package view;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import java.awt.Component;
import javax.swing.JTextPane;
import javax.swing.event.CaretListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import utils.FileUtils;

import javax.swing.event.CaretEvent;
import java.io.File;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Window {
	
	/* Notas:
	 * El compilador se crea solo cuando es necesario compilar
	 * El archivo se carga de lo que se encuentra en EditorPane, así que no es necesario que carge desde un path, cambiar eso en Compilator/Lexicon
	 * 
	*/

	private static String SEARCH_MSG = "Seleccione el archivo \"txt\" con su programa";
	private static String FILE_LOAD_ERROR_MSG = "Error al cargar el archivo";
	private static String FILE_NOT_FOUND_MSG = "Archivo no encontrado";
	private static String SAVE_CORRECT_MSG = "Archivo guardado con éxito";
	private static String SAVE_ERROR_MSG = "Error al guardar el archivo";
	private static String LINE_COUNTER_MSG = "Línea seleccionada: ";
	
	private JFrame frmCompilador;
	private JButton btnGuardar;
	private JButton btnCompilar;
	private JButton btnCargar;
	private JLabel lblNombrePrograma;
	private JTextPane editorPanePrograma;
	private JTextPane editorPane;
	private JTextPane editorPaneTablaSimbolos;
	private JTextPane editorPane_1;
	
	private File file_selected;
	private boolean loaded;
	private JLabel lblNumberLine;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window window = new Window();
					window.frmCompilador.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Window() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCompilador = new JFrame();
		frmCompilador.setTitle("Compilador");
		frmCompilador.setBounds(100, 100, 800, 592);
		frmCompilador.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCompilador.setMinimumSize(new Dimension(800, 582));
		SpringLayout springLayout = new SpringLayout();
		frmCompilador.getContentPane().setLayout(springLayout);
		
		lblNombrePrograma = new JLabel("Cargue su programa con el bot\u00F3n cargar");
		lblNombrePrograma.setAlignmentY(Component.TOP_ALIGNMENT);
		springLayout.putConstraint(SpringLayout.WEST, lblNombrePrograma, 10, SpringLayout.WEST, frmCompilador.getContentPane());
		frmCompilador.getContentPane().add(lblNombrePrograma);
		
		btnCargar = new JButton("Cargar");
		btnCargar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				loaded = false;
				final JFileChooser dialog = new JFileChooser (FileSystemView.getFileSystemView().getHomeDirectory());
				FileNameExtensionFilter filter = new FileNameExtensionFilter("\".txt\"", "txt", "text");
				dialog.setFileFilter(filter);
				dialog.setDialogTitle(SEARCH_MSG);
				int returnValue = dialog.showOpenDialog(null);
				String path_to_file = "";
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					file_selected = dialog.getSelectedFile();
					path_to_file = file_selected.getAbsolutePath().trim();
					System.out.println(path_to_file);
					//Extraer el nombre del archivo de path_to_File
					String nombre_archivo = path_to_file.substring(path_to_file.lastIndexOf("\\")+1);
					//Extraer contenido del archivo y cargarlo en EditorPane
					int readed = FileUtils.openFile(file_selected);
					if (readed == FileUtils.LOAD_CORRECT) {
						editorPanePrograma.setText(FileUtils.getContent());
						lblNombrePrograma.setText(nombre_archivo);
						btnGuardar.setEnabled(true);
						btnCompilar.setEnabled(true);
						loaded = true;
					}
					else if (readed == FileUtils.FILE_NOT_FOUND)
						JOptionPane.showMessageDialog(new JFrame(), FILE_NOT_FOUND_MSG, "Warning", JOptionPane.ERROR_MESSAGE);
					else
						JOptionPane.showMessageDialog(new JFrame(), FILE_LOAD_ERROR_MSG, "Warning", JOptionPane.ERROR_MESSAGE);
					
				}
			}
		});
		btnCargar.setAlignmentY(Component.TOP_ALIGNMENT);
		btnCargar.setAlignmentX(Component.RIGHT_ALIGNMENT);
		springLayout.putConstraint(SpringLayout.NORTH, lblNombrePrograma, 4, SpringLayout.NORTH, btnCargar);
		springLayout.putConstraint(SpringLayout.NORTH, btnCargar, 6, SpringLayout.NORTH, frmCompilador.getContentPane());
		frmCompilador.getContentPane().add(btnCargar);
		
		JScrollPane scrollPane = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 10, SpringLayout.SOUTH, btnCargar);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, -275, SpringLayout.EAST, frmCompilador.getContentPane());
		scrollPane.setAutoscrolls(true);
		scrollPane.setAlignmentY(Component.TOP_ALIGNMENT);
		scrollPane.setAlignmentX(Component.RIGHT_ALIGNMENT);
		frmCompilador.getContentPane().add(scrollPane);
		
		editorPane = new JTextPane();
		editorPane.setAlignmentY(Component.TOP_ALIGNMENT);
		editorPane.setAlignmentX(Component.RIGHT_ALIGNMENT);
		scrollPane.setViewportView(editorPane);
		springLayout.putConstraint(SpringLayout.EAST, editorPane, -10, SpringLayout.EAST, frmCompilador.getContentPane());
		editorPane.setText("<token>, <token> -> <estructura>");
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int writed = FileUtils.saveFile(file_selected, editorPanePrograma.getText());
				if (writed == FileUtils.SAVE_CORRECT)
					JOptionPane.showMessageDialog(new JFrame(), SAVE_CORRECT_MSG, "Guardado", JOptionPane.PLAIN_MESSAGE);
				else
					JOptionPane.showMessageDialog(new JFrame(), SAVE_ERROR_MSG, "Warning", JOptionPane.ERROR_MESSAGE);
			}
		});
		btnGuardar.setAlignmentY(Component.TOP_ALIGNMENT);
		btnGuardar.setAlignmentX(Component.RIGHT_ALIGNMENT);
		springLayout.putConstraint(SpringLayout.NORTH, btnGuardar, 6, SpringLayout.NORTH, frmCompilador.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, btnCargar, -97, SpringLayout.WEST, btnGuardar);
		springLayout.putConstraint(SpringLayout.EAST, btnCargar, -4, SpringLayout.WEST, btnGuardar);
		btnGuardar.setEnabled(false);
		frmCompilador.getContentPane().add(btnGuardar);
		
		btnCompilar = new JButton("Compilar");
		btnCompilar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
								
				/* TODO: Pasar el contenido de EditorPanePrograma a un buffer List<Integer> con los char convertidos a ASCII
				 * TODO: Crear el compilador, ponerlo a compilar
				 * TODO: Sacar tabla de simbolos, tokens, estructuras y errores con warning del compilador
				 */
			}
		});
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, 0, SpringLayout.EAST, btnCompilar);
		btnCompilar.setAlignmentY(Component.TOP_ALIGNMENT);
		btnCompilar.setAlignmentX(Component.RIGHT_ALIGNMENT);
		springLayout.putConstraint(SpringLayout.NORTH, btnCompilar, 6, SpringLayout.NORTH, frmCompilador.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, btnGuardar, -94, SpringLayout.WEST, btnCompilar);
		springLayout.putConstraint(SpringLayout.EAST, btnGuardar, -6, SpringLayout.WEST, btnCompilar);
		springLayout.putConstraint(SpringLayout.WEST, btnCompilar, -103, SpringLayout.EAST, frmCompilador.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnCompilar, -10, SpringLayout.EAST, frmCompilador.getContentPane());
		btnCompilar.setEnabled(false);
		frmCompilador.getContentPane().add(btnCompilar);
		
		JScrollPane scrollPane_TS = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane_TS, 207, SpringLayout.NORTH, frmCompilador.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, scrollPane_TS, -10, SpringLayout.EAST, frmCompilador.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -6, SpringLayout.NORTH, scrollPane_TS);
		scrollPane_TS.setAlignmentY(Component.TOP_ALIGNMENT);
		scrollPane_TS.setAlignmentX(Component.RIGHT_ALIGNMENT);
		frmCompilador.getContentPane().add(scrollPane_TS);
		
		editorPaneTablaSimbolos = new JTextPane();
		editorPaneTablaSimbolos.setAlignmentY(Component.TOP_ALIGNMENT);
		editorPaneTablaSimbolos.setAlignmentX(Component.RIGHT_ALIGNMENT);
		scrollPane_TS.setViewportView(editorPaneTablaSimbolos);
		springLayout.putConstraint(SpringLayout.EAST, editorPaneTablaSimbolos, -10, SpringLayout.EAST, frmCompilador.getContentPane());
		editorPaneTablaSimbolos.setEditable(false);
		springLayout.putConstraint(SpringLayout.NORTH, editorPaneTablaSimbolos, 18, SpringLayout.SOUTH, scrollPane);
		
		JScrollPane scrollPane_WarningYErrores = new JScrollPane();
		springLayout.putConstraint(SpringLayout.EAST, scrollPane_WarningYErrores, -10, SpringLayout.EAST, frmCompilador.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane_TS, -6, SpringLayout.NORTH, scrollPane_WarningYErrores);
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane_WarningYErrores, 375, SpringLayout.NORTH, frmCompilador.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane_WarningYErrores, 537, SpringLayout.NORTH, frmCompilador.getContentPane());
		scrollPane_WarningYErrores.setAlignmentY(Component.TOP_ALIGNMENT);
		scrollPane_WarningYErrores.setAlignmentX(Component.RIGHT_ALIGNMENT);
		frmCompilador.getContentPane().add(scrollPane_WarningYErrores);
		
		editorPane_1 = new JTextPane();
		editorPane_1.setAlignmentX(Component.RIGHT_ALIGNMENT);
		editorPane_1.setAlignmentY(Component.TOP_ALIGNMENT);
		scrollPane_WarningYErrores.setViewportView(editorPane_1);
		springLayout.putConstraint(SpringLayout.EAST, editorPane_1, -10, SpringLayout.EAST, frmCompilador.getContentPane());
		editorPane_1.setText("<l\u00EDnea xx: se trunco en>");
		editorPane_1.setEditable(false);
		
		JScrollPane scrollPane_Codigo = new JScrollPane();
		springLayout.putConstraint(SpringLayout.WEST, scrollPane_Codigo, 10, SpringLayout.WEST, frmCompilador.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, scrollPane_Codigo, -6, SpringLayout.WEST, scrollPane);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane_WarningYErrores, 6, SpringLayout.EAST, scrollPane_Codigo);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane_TS, 6, SpringLayout.EAST, scrollPane_Codigo);
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane_Codigo, 10, SpringLayout.SOUTH, btnCargar);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane_Codigo, -10, SpringLayout.SOUTH, frmCompilador.getContentPane());
		frmCompilador.getContentPane().add(scrollPane_Codigo);
		
		editorPanePrograma = new JTextPane();
		editorPanePrograma.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				if (loaded) {
					int pos = e.getDot();
			        int row = 1;
					String text = editorPanePrograma.getText().replaceAll("\r", "");
					for(int i=0;i<pos;i++){
						if(text.charAt(i)==10){
							row++;
						}
					}
	                lblNumberLine.setText(LINE_COUNTER_MSG + row);
				}
			}
		});
		scrollPane_Codigo.setViewportView(editorPanePrograma);
		springLayout.putConstraint(SpringLayout.NORTH, editorPanePrograma, 241, SpringLayout.NORTH, frmCompilador.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, editorPanePrograma, 54, SpringLayout.WEST, frmCompilador.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, editorPanePrograma, -6, SpringLayout.SOUTH, frmCompilador.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, editorPane, 6, SpringLayout.EAST, editorPanePrograma);
		
		JLabel lblTokensLedos = new JLabel("Tokens y estructuras");
		scrollPane.setColumnHeaderView(lblTokensLedos);
		springLayout.putConstraint(SpringLayout.NORTH, lblTokensLedos, 6, SpringLayout.SOUTH, btnCargar);
		springLayout.putConstraint(SpringLayout.WEST, lblTokensLedos, -275, SpringLayout.EAST, frmCompilador.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, lblTokensLedos, 23, SpringLayout.SOUTH, btnCargar);
		springLayout.putConstraint(SpringLayout.EAST, lblTokensLedos, -23, SpringLayout.EAST, frmCompilador.getContentPane());
		lblTokensLedos.setAlignmentY(Component.TOP_ALIGNMENT);
		lblTokensLedos.setAlignmentX(Component.RIGHT_ALIGNMENT);
		springLayout.putConstraint(SpringLayout.NORTH, editorPane, 6, SpringLayout.SOUTH, lblTokensLedos);
		springLayout.putConstraint(SpringLayout.SOUTH, editorPane, 130, SpringLayout.SOUTH, lblTokensLedos);
		springLayout.putConstraint(SpringLayout.WEST, editorPaneTablaSimbolos, 6, SpringLayout.EAST, editorPanePrograma);
		
		JLabel labelTablaSimbolos = new JLabel("Tabla de s\u00EDmbolos");
		scrollPane_TS.setColumnHeaderView(labelTablaSimbolos);
		springLayout.putConstraint(SpringLayout.EAST, labelTablaSimbolos, -50, SpringLayout.EAST, frmCompilador.getContentPane());
		labelTablaSimbolos.setAlignmentY(Component.TOP_ALIGNMENT);
		labelTablaSimbolos.setAlignmentX(Component.RIGHT_ALIGNMENT);
		springLayout.putConstraint(SpringLayout.SOUTH, labelTablaSimbolos, -6, SpringLayout.NORTH, scrollPane_TS);
		springLayout.putConstraint(SpringLayout.WEST, labelTablaSimbolos, 6, SpringLayout.EAST, scrollPane_Codigo);
		springLayout.putConstraint(SpringLayout.WEST, editorPane_1, 6, SpringLayout.EAST, editorPanePrograma);
		
		JLabel lblWarningsYErrores = new JLabel("Warnings y errores");
		scrollPane_WarningYErrores.setColumnHeaderView(lblWarningsYErrores);
		lblWarningsYErrores.setAlignmentY(Component.TOP_ALIGNMENT);
		lblWarningsYErrores.setAlignmentX(Component.RIGHT_ALIGNMENT);
		springLayout.putConstraint(SpringLayout.EAST, lblWarningsYErrores, -39, SpringLayout.EAST, frmCompilador.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, lblWarningsYErrores, 6, SpringLayout.SOUTH, scrollPane_TS);
		springLayout.putConstraint(SpringLayout.SOUTH, editorPaneTablaSimbolos, -6, SpringLayout.NORTH, lblWarningsYErrores);
		springLayout.putConstraint(SpringLayout.SOUTH, lblWarningsYErrores, -6, SpringLayout.NORTH, scrollPane_WarningYErrores);
		springLayout.putConstraint(SpringLayout.NORTH, editorPane_1, 6, SpringLayout.SOUTH, lblWarningsYErrores);
		springLayout.putConstraint(SpringLayout.SOUTH, editorPane_1, 134, SpringLayout.SOUTH, lblWarningsYErrores);
		springLayout.putConstraint(SpringLayout.WEST, lblWarningsYErrores, 6, SpringLayout.EAST, scrollPane_Codigo);
		springLayout.putConstraint(SpringLayout.EAST, editorPanePrograma, 0, SpringLayout.WEST, lblWarningsYErrores);
		
		lblNumberLine = new JLabel("L\u00EDnea seleccionada:");
		springLayout.putConstraint(SpringLayout.WEST, lblNumberLine, 309, SpringLayout.WEST, frmCompilador.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblNumberLine, -46, SpringLayout.WEST, btnCargar);
		springLayout.putConstraint(SpringLayout.EAST, lblNombrePrograma, -6, SpringLayout.WEST, lblNumberLine);
		springLayout.putConstraint(SpringLayout.NORTH, lblNumberLine, 10, SpringLayout.NORTH, frmCompilador.getContentPane());
		lblNumberLine.setAlignmentY(Component.TOP_ALIGNMENT);
		frmCompilador.getContentPane().add(lblNumberLine);
	}
}
