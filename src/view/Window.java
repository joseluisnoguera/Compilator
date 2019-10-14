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

import logic.Compilator;
import utils.ElementoTS;
import utils.FileUtils;
import utils.MsgStack;

import javax.swing.event.CaretEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JEditorPane;
import javax.swing.JPanel;

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
	private static String FILE_CREATED_MSG = "El archivo fue creado exitosamente";
	private static String FILE_ALREADY_EXIST_MSG = "El archivo especificado ya existe";
	private static String FILE_CREATE_ERROR_MSG = "Hubo algún error al crear el archivo";
	private static int END_OF_TEXT = 3;
	
	private JFrame frmCompilator;
	private JButton btnSave;
	private JButton btnCompile;
	private JButton btnLoad;
	private JLabel lblProgramName;
	private JTextPane editorPaneProgram;
	private JTextPane editorPaneTokens;
	private JTextPane editorPaneSymbolTable;
	private JTextPane editorPaneMsgs;
	
	private File file_selected;
	private JLabel lblNumberLine;
	private JButton btnNew;
	private JScrollPane scrollPane_EstructurasSemanticas;
	private JEditorPane editorPaneSemanticStruct;
	private JLabel lblEstructurasDetectas;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window window = new Window();
					window.frmCompilator.setVisible(true);
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
		frmCompilator = new JFrame();
		frmCompilator.setTitle("Compilador");
		frmCompilator.setBounds(100, 100, 900, 552);
		frmCompilator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCompilator.setMinimumSize(new Dimension(900, 600));
		SpringLayout springLayout = new SpringLayout();
		frmCompilator.getContentPane().setLayout(springLayout);
		
		lblProgramName = new JLabel("Cree un nuevo documento o cargue su programa");
		springLayout.putConstraint(SpringLayout.NORTH, lblProgramName, 10, SpringLayout.NORTH, frmCompilator.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblProgramName, 10, SpringLayout.WEST, frmCompilator.getContentPane());
		lblProgramName.setAlignmentY(Component.TOP_ALIGNMENT);
		frmCompilator.getContentPane().add(lblProgramName);
		
		JPanel panel = new JPanel();
		springLayout.putConstraint(SpringLayout.EAST, panel, -10, SpringLayout.EAST, frmCompilator.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblProgramName, -64, SpringLayout.WEST, panel);
		springLayout.putConstraint(SpringLayout.SOUTH, panel, 34, SpringLayout.NORTH, lblProgramName);
		springLayout.putConstraint(SpringLayout.NORTH, panel, 0, SpringLayout.NORTH, lblProgramName);
		panel.setAlignmentY(Component.TOP_ALIGNMENT);
		panel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		frmCompilator.getContentPane().add(panel);
		
		btnNew = new JButton("Nuevo");
		btnNew.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				final JFileChooser dialog = new JFileChooser (FileSystemView.getFileSystemView().getHomeDirectory());
				FileNameExtensionFilter filter = new FileNameExtensionFilter("\".txt\"", "txt", "text");
				dialog.setFileFilter(filter);
				dialog.setDialogTitle(SEARCH_MSG);
				int returnValue = dialog.showOpenDialog(null);
				String path_and_name = "";
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					file_selected = dialog.getSelectedFile();
					path_and_name = file_selected.getAbsolutePath().trim();
					String ext = path_and_name.substring(path_and_name.lastIndexOf('.')+1, path_and_name.length());
					if (ext.equals("ext") || !path_and_name.contains("."))
						path_and_name += ".txt";
					File file = new File(path_and_name);
					file_selected = file;
					FileUtils.reset();
					try {
						if(file.createNewFile()){
							String nombre_archivo = path_and_name.substring(path_and_name.lastIndexOf("\\")+1);
							editorPaneProgram.setEnabled(true);
							editorPaneProgram.setText(FileUtils.getContent());
							lblProgramName.setText(nombre_archivo);
							btnSave.setEnabled(true);
							btnCompile.setEnabled(true);
							JOptionPane.showMessageDialog(new JFrame(), FILE_CREATED_MSG, "Creado", JOptionPane.PLAIN_MESSAGE);
						}else
							JOptionPane.showMessageDialog(new JFrame(), FILE_ALREADY_EXIST_MSG, "Archivo ya existente", JOptionPane.PLAIN_MESSAGE);
					} catch (IOException e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(new JFrame(), FILE_CREATE_ERROR_MSG, "Warning", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		panel.add(btnNew);
		btnNew.setAlignmentY(Component.TOP_ALIGNMENT);
		
		btnLoad = new JButton("Cargar");
		panel.add(btnLoad);
		btnLoad.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				final JFileChooser dialog = new JFileChooser (FileSystemView.getFileSystemView().getHomeDirectory());
				FileNameExtensionFilter filter = new FileNameExtensionFilter("\".txt\"", "txt", "text");
				dialog.setFileFilter(filter);
				dialog.setDialogTitle(SEARCH_MSG);
				int returnValue = dialog.showOpenDialog(null);
				String path_to_file = "";
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					file_selected = dialog.getSelectedFile();
					path_to_file = file_selected.getAbsolutePath().trim();
					String nombre_archivo = path_to_file.substring(path_to_file.lastIndexOf("\\")+1);
					int readed = FileUtils.openFile(file_selected);
					if (readed == FileUtils.LOAD_CORRECT) {
						editorPaneProgram.setEnabled(true);
						editorPaneProgram.setText(FileUtils.getContent());
						lblProgramName.setText(nombre_archivo);
						btnSave.setEnabled(true);
						btnCompile.setEnabled(true);
					}
					else if (readed == FileUtils.FILE_NOT_FOUND)
						JOptionPane.showMessageDialog(new JFrame(), FILE_NOT_FOUND_MSG, "Warning", JOptionPane.ERROR_MESSAGE);
					else
						JOptionPane.showMessageDialog(new JFrame(), FILE_LOAD_ERROR_MSG, "Warning", JOptionPane.ERROR_MESSAGE);
					
				}
			}
		});
		btnLoad.setAlignmentY(Component.TOP_ALIGNMENT);
		btnLoad.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		btnSave = new JButton("Guardar");
		panel.add(btnSave);
		btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int writed = FileUtils.saveFile(file_selected, editorPaneProgram.getText());
				if (writed == FileUtils.SAVE_CORRECT)
					JOptionPane.showMessageDialog(new JFrame(), SAVE_CORRECT_MSG, "Guardado", JOptionPane.PLAIN_MESSAGE);
				else
					JOptionPane.showMessageDialog(new JFrame(), SAVE_ERROR_MSG, "Warning", JOptionPane.ERROR_MESSAGE);
			}
		});
		btnSave.setAlignmentY(Component.TOP_ALIGNMENT);
		btnSave.setAlignmentX(Component.RIGHT_ALIGNMENT);
		btnSave.setEnabled(false);
		
		btnCompile = new JButton("Compilar");
		panel.add(btnCompile);
		btnCompile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				List<Integer> programBuffer = new ArrayList<Integer>();
				String text = editorPaneProgram.getText().replaceAll("\r", ""); //Esto ahora el problema del doble símbolo para el salto de línea
				for (int i = 0; i < text.length(); i++)
					programBuffer.add((int)text.charAt(i));
				programBuffer.add(END_OF_TEXT); //Caracter de fin de texto en ASCII, tiene que coordinar con el que se usa en Lexicon
				Compilator compilator = Compilator.getInstance(programBuffer);
				compilator.compilate();
				editorPaneMsgs.setText(getStringFromStack(compilator.getMsgStack()));
				editorPaneTokens .setText(getStringFromStack(compilator.getTokenStack()));
				editorPaneSemanticStruct.setText(getStringFromStack(compilator.getSemanticStructStack()));
				loadSymbolTable(compilator.getSimbTable());
			}
		});
		btnCompile.setAlignmentY(Component.TOP_ALIGNMENT);
		btnCompile.setAlignmentX(Component.RIGHT_ALIGNMENT);
		btnCompile.setEnabled(false);
		
		lblNumberLine = new JLabel("L\u00EDnea seleccionada:");
		springLayout.putConstraint(SpringLayout.WEST, panel, 11, SpringLayout.EAST, lblNumberLine);
		springLayout.putConstraint(SpringLayout.WEST, lblNumberLine, 10, SpringLayout.WEST, frmCompilator.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblNumberLine, -424, SpringLayout.EAST, frmCompilator.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, lblNumberLine, 6, SpringLayout.SOUTH, lblProgramName);
		lblNumberLine.setAlignmentY(Component.TOP_ALIGNMENT);
		frmCompilator.getContentPane().add(lblNumberLine);
		
		JScrollPane scrollPane_Codigo = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane_Codigo, 6, SpringLayout.SOUTH, lblNumberLine);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane_Codigo, 10, SpringLayout.WEST, frmCompilator.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane_Codigo, -10, SpringLayout.SOUTH, frmCompilator.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, scrollPane_Codigo, 0, SpringLayout.EAST, lblNumberLine);
		frmCompilator.getContentPane().add(scrollPane_Codigo);
		
		editorPaneProgram = new JTextPane();
		editorPaneProgram.setEnabled(false);
		editorPaneProgram.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				int pos = e.getDot();
		        int row = 1;
				String text = editorPaneProgram.getText().replaceAll("\r", "");
				for(int i = 0; i < pos; i++){
					if(text.charAt(i) == '\n'){ //new line
						row++;
					}
				}
                lblNumberLine.setText(LINE_COUNTER_MSG + row);
			}
		});
		scrollPane_Codigo.setViewportView(editorPaneProgram);
		
		JScrollPane scrollPane_Tokens = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane_Tokens, 6, SpringLayout.SOUTH, panel);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane_Tokens, -403, SpringLayout.EAST, panel);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane_Tokens, 116, SpringLayout.SOUTH, panel);
		springLayout.putConstraint(SpringLayout.EAST, scrollPane_Tokens, 0, SpringLayout.EAST, panel);
		scrollPane_Tokens.setMaximumSize(new Dimension(403, 162));
		scrollPane_Tokens.setMinimumSize(new Dimension(403, 162));
		scrollPane_Tokens.setAutoscrolls(true);
		scrollPane_Tokens.setAlignmentY(Component.TOP_ALIGNMENT);
		scrollPane_Tokens.setAlignmentX(Component.RIGHT_ALIGNMENT);
		frmCompilator.getContentPane().add(scrollPane_Tokens);
		
		editorPaneTokens = new JTextPane();
		editorPaneTokens.setEditable(false);
		editorPaneTokens.setAlignmentY(Component.TOP_ALIGNMENT);
		editorPaneTokens.setAlignmentX(Component.RIGHT_ALIGNMENT);
		scrollPane_Tokens.setViewportView(editorPaneTokens);
		
		JLabel lblTokens = new JLabel("Tokens detectados");
		scrollPane_Tokens.setColumnHeaderView(lblTokens);
		lblTokens.setAlignmentY(Component.TOP_ALIGNMENT);
		lblTokens.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		scrollPane_EstructurasSemanticas = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane_EstructurasSemanticas, 6, SpringLayout.SOUTH, scrollPane_Tokens);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane_EstructurasSemanticas, -403, SpringLayout.EAST, panel);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane_EstructurasSemanticas, 116, SpringLayout.SOUTH, scrollPane_Tokens);
		springLayout.putConstraint(SpringLayout.EAST, scrollPane_EstructurasSemanticas, 0, SpringLayout.EAST, panel);
		scrollPane_EstructurasSemanticas.setMinimumSize(new Dimension(403, 162));
		scrollPane_EstructurasSemanticas.setMaximumSize(new Dimension(403, 162));
		scrollPane_EstructurasSemanticas.setAlignmentX(Component.RIGHT_ALIGNMENT);
		scrollPane_EstructurasSemanticas.setAlignmentY(Component.TOP_ALIGNMENT);
		frmCompilator.getContentPane().add(scrollPane_EstructurasSemanticas);
		
		editorPaneSemanticStruct = new JEditorPane();
		editorPaneSemanticStruct.setEditable(false);
		scrollPane_EstructurasSemanticas.setViewportView(editorPaneSemanticStruct);
		
		lblEstructurasDetectas = new JLabel("Estructuras detectas");
		scrollPane_EstructurasSemanticas.setColumnHeaderView(lblEstructurasDetectas);
		
		JScrollPane scrollPane_TS = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane_TS, 6, SpringLayout.SOUTH, scrollPane_EstructurasSemanticas);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane_TS, -403, SpringLayout.EAST, panel);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane_TS, 116, SpringLayout.SOUTH, scrollPane_EstructurasSemanticas);
		springLayout.putConstraint(SpringLayout.EAST, scrollPane_TS, 0, SpringLayout.EAST, panel);
		scrollPane_TS.setMaximumSize(new Dimension(403, 162));
		scrollPane_TS.setMinimumSize(new Dimension(403, 162));
		scrollPane_TS.setAlignmentY(Component.TOP_ALIGNMENT);
		scrollPane_TS.setAlignmentX(Component.RIGHT_ALIGNMENT);
		frmCompilator.getContentPane().add(scrollPane_TS);
		
		editorPaneSymbolTable = new JTextPane();
		editorPaneSymbolTable.setAlignmentY(Component.TOP_ALIGNMENT);
		editorPaneSymbolTable.setAlignmentX(Component.RIGHT_ALIGNMENT);
		scrollPane_TS.setViewportView(editorPaneSymbolTable);
		editorPaneSymbolTable.setEditable(false);
		
		JLabel labelTablaSimbolos = new JLabel("Tabla de s\u00EDmbolos");
		scrollPane_TS.setColumnHeaderView(labelTablaSimbolos);
		labelTablaSimbolos.setAlignmentY(Component.TOP_ALIGNMENT);
		labelTablaSimbolos.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		JScrollPane scrollPane_WarningYErrores = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane_WarningYErrores, 6, SpringLayout.SOUTH, scrollPane_TS);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane_WarningYErrores, -403, SpringLayout.EAST, panel);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane_WarningYErrores, 116, SpringLayout.SOUTH, scrollPane_TS);
		springLayout.putConstraint(SpringLayout.EAST, scrollPane_WarningYErrores, 0, SpringLayout.EAST, panel);
		scrollPane_WarningYErrores.setMinimumSize(new Dimension(403, 162));
		scrollPane_WarningYErrores.setMaximumSize(new Dimension(403, 162));
		scrollPane_WarningYErrores.setAlignmentY(Component.TOP_ALIGNMENT);
		scrollPane_WarningYErrores.setAlignmentX(Component.RIGHT_ALIGNMENT);
		frmCompilator.getContentPane().add(scrollPane_WarningYErrores);
		
		editorPaneMsgs = new JTextPane();
		editorPaneMsgs.setAlignmentX(Component.RIGHT_ALIGNMENT);
		editorPaneMsgs.setAlignmentY(Component.TOP_ALIGNMENT);
		scrollPane_WarningYErrores.setViewportView(editorPaneMsgs);
		editorPaneMsgs.setEditable(false);
		
		JLabel lblWarningsYErrores = new JLabel("Warnings y errores");
		scrollPane_WarningYErrores.setColumnHeaderView(lblWarningsYErrores);
		lblWarningsYErrores.setAlignmentY(Component.TOP_ALIGNMENT);
		lblWarningsYErrores.setAlignmentX(Component.RIGHT_ALIGNMENT);
	}
	
	private void loadSymbolTable(Hashtable<String, ElementoTS> symbolTable) {
		String text = "";
		Set<String> keys = symbolTable.keySet();
		for(String key:keys) {
			ElementoTS elem = symbolTable.get(key);
			text += "Token: " + elem.getTipoToken() + " - Tipo:" + elem.getTipoAtributo() + 
					" - Lexema: " + key + " - Valor: " + elem.getValue() + "\n";
		}
		editorPaneSymbolTable.setText(text);
	}
	
	private String getStringFromStack(MsgStack stack) {
		String text = "";
		for (int i = 0; i < stack.getSize(); i++) {
			text += stack.getMsg(i) + "\n";
		}
		return text;
	}
}
