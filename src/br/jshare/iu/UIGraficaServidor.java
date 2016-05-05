package br.jshare.iu;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import br.jshare.comum.pojos.Arquivo;
import br.jshare.global.Client;
import br.jshare.global.ServerInterface;
import br.jshare.libs.Extras;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Color;

/**
 * Classe UI para o servidor de arquivos
 * 
 * @author Kassiano
 *
 */
public class UIGraficaServidor extends JFrame implements ServerInterface {

	// Elementos Swing
	private JPanel jPanel;
	private JTextField textPorta;
	private JButton jButtonParar;
	private JButton jButtonIniciar;
	private JComboBox jComboBoxIp;
	private JTextArea jTextAreaLog;

	// Maps
	private Map<String, Client> listClients = new HashMap<String, Client>();
	private Map<Client, List<Arquivo>> listFilesClients = new HashMap<Client, List<Arquivo>>();
	private Map<Client, List<Arquivo>> listFilesFound = new HashMap<Client, List<Arquivo>>();

	private SimpleDateFormat dateFormat = new Extras().formatoData();
	private ServerInterface serverInterface;
	private Registry registry;
	//
	// porta padr√£o
	private String numberPort = "8090";
	// Exibi por padr√£o o endere√ßo local 127.0.0.1
	private String ipBase = "127.0.0.1";

	@Override
	public void connectClient(Client client) throws RemoteException {
		listClients.put(client.getIp(), client);
		writeLog("Novo arquivo: " + client.getUsuario());

	}

	@Override
	public void publishListFiles(Client client, List<Arquivo> list) throws RemoteException {
		// TODO Auto-generated method stub
		listFilesClients.put(client, list);
	}

	@Override
	public Map<Client, List<Arquivo>> searchFile(String description) throws RemoteException {

		for (Map.Entry<Client, List<Arquivo>> listaProcura : listFilesClients.entrySet()) {
			for (Arquivo arquivo : listFilesClients.get(listaProcura.getKey())) {
			 writeLog(arquivo.getProperts());
			}
		}
		return listFilesFound;
	}

	@Override
	public void diconnect(Client client) throws RemoteException {
		// TODO Auto-generated method stub
		writeLog("Usu·rio desconectado:" + client.getUsuario());
		listClients.remove(client.getIp());
	}

	/**
	 * Iniciando o aplicativo
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIGraficaServidor uiGraficaServidor = new UIGraficaServidor();
					uiGraficaServidor.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UIGraficaServidor() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 571, 443);
		jPanel = new JPanel();
		jPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(jPanel);
		jPanel.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(5, 5, 540, 28);
		jPanel.add(panel);

		JLabel lblServidorDeBusca = new JLabel("Servidor de arquivos");
		panel.add(lblServidorDeBusca);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(5, 44, 540, 33);
		jPanel.add(panel_1);

		JLabel lblIp = new JLabel("IP:");
		lblIp.setVerticalAlignment(SwingConstants.BOTTOM);
		panel_1.add(lblIp);

		jComboBoxIp = new JComboBox();
		panel_1.add(jComboBoxIp);

		JLabel lblPorta = new JLabel("Porta: ");
		panel_1.add(lblPorta);

		textPorta = new JTextField();
		textPorta.setText(this.numberPort);
		panel_1.add(textPorta);
		textPorta.setColumns(10);

		jButtonIniciar = new JButton("Iniciar");
		jButtonIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}

		});
		panel_1.add(jButtonIniciar);

		jButtonParar = new JButton("Parar");
		jButtonParar.setEnabled(false);
		jButtonParar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});

		panel_1.add(jButtonParar);

		List<String> lista = new Extras().localizarIp();
		jComboBoxIp.setModel(new DefaultComboBoxModel<String>(new Vector<String>(lista)));
		jComboBoxIp.setSelectedIndex(0);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(5, 81, 540, 312);
		jPanel.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel_2.add(scrollPane, BorderLayout.CENTER);

		jTextAreaLog = new JTextArea();
		jTextAreaLog.setLineWrap(true);
		jTextAreaLog.setForeground(Color.WHITE);
		jTextAreaLog.setFont(new Font("Consolas", Font.PLAIN, 15));
		jTextAreaLog.setBackground(Color.BLACK);
		scrollPane.setViewportView(jTextAreaLog);

		jButtonIniciar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				iniciando();
			}
		});

		jButtonParar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stopServer();
			}
		});
	}

	protected void iniciando() {
		// Validando porta
		String valiPort = new Extras().portValid(textPorta.getText().trim());

		if (!valiPort.equals("")) {
			JOptionPane.showMessageDialog(this, valiPort);
			return;
		}
		
		startServer(Integer.parseInt(textPorta.getText().trim()));
	}

	protected void startServer(int intPorta) {

		try {
			serverInterface = (ServerInterface) UnicastRemoteObject.exportObject(this, 0);
			registry = LocateRegistry.createRegistry(intPorta);
			registry.rebind(SERVICE_NAME, serverInterface);
			writeLog("Servidor ativo.");
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(this, "Erro: erifique se a porta j· est· em uso por outro serviÁo.");
			e.printStackTrace();
		}
		this.disableFields(false);
		this.disableButtons(true);
		
	}
	
	protected void stopServer() {
		try {
			UnicastRemoteObject.unexportObject(this, true);
			UnicastRemoteObject.unexportObject(registry, true);

			writeLog("O servidor foi encerrado");

			disableButtons(false);
			disableFields(true);

		} catch (NoSuchObjectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//
	// Interagindo com os componetes SWING do servidor
	// Escrita de log e bloqueios
	//
	// Escrevendo no log de a√ß√µes
	private void writeLog(String texto) {
		jTextAreaLog.append("Data/Hora:" + dateFormat.format(new Date()));
		jTextAreaLog.append(" - " + texto);
		jTextAreaLog.append("\n");
	}

	// Metodos de bloqueio de campos Text, JCombo e Button
	private void disableFields(Boolean status) {
		jComboBoxIp.setEnabled(status);
		textPorta.setEnabled(status);
	}

	private void disableButtons(Boolean status) {
		jButtonIniciar.setEnabled(!status);
		jButtonParar.setEnabled(status);
	}
	//
	// Fim intera√ß√£o
	//

}
