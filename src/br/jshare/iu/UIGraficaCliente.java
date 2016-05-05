package br.jshare.iu;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import br.jshare.comum.pojos.Arquivo;
import br.jshare.global.Client;
import br.jshare.global.ServerInterface;
import br.jshare.iu.UIGraficaServidor;
import br.jshare.libs.Extras;

/**
 * Classe cliente que instacia uma janela em SWING extende a classe
 * ServerInterface
 * 
 * @author Kassiano
 *
 */
public class UIGraficaCliente extends JFrame implements ServerInterface {
	// Obejtos swing, objetos visuais
	private JPanel contentPane;
	private JTextField textArquivo;
	private JTextField textLocalizar;
	private JTable tableResultados;
	private JTextField textIpAddress;
	private JTextField textPorta;
	//

	// Formatar data utilizando padrão já de finido em Extras
	private SimpleDateFormat dtFormata = new Extras().formatoData();
	private ServerInterface serverInterface;
	private Registry registry;
	private UIGraficaServidor uiGraficaServidor;
	private String usuario;
	private int porta;
	// porta padrão
	private String numberPort = "8090";
	// Exibi por padrão o endereço local 127.0.0.1
	private String ipBase = "127.0.0.1";

	/**
	 * Iniciando o aplicativo
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// instanciano o objeto
					UIGraficaCliente uiGraficaCliente = new UIGraficaCliente();
					uiGraficaCliente.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Método de conexão com o servidor
	 */
	protected void connect() {

		// Validando se o usuário foi informado
		this.usuario = textArquivo.getText().trim();
		if (this.usuario.length() == 0) {
			JOptionPane.showMessageDialog(this, "Informe seu usuário!");
			return;
		}

		// Validando o numero do IP
		String ip = textIpAddress.getText().trim();
		if (!new Extras().validIp(ip)) {
			JOptionPane.showMessageDialog(this, "Endereço IP é inválido.");
			return;
		}

		// Validando porta
		String valiPort = new Extras().portValid(textPorta.getText().trim());

		if (valiPort.equals("")) {
			this.porta = Integer.parseInt(textPorta.getText().trim());
		} else {
			JOptionPane.showMessageDialog(this, valiPort);
			return;
		}

		// Conectando o usuário
		try {
			this.registry = LocateRegistry.getRegistry(ip, this.porta);
			this.serverInterface = (ServerInterface) registry.lookup(ServerInterface.SERVICE_NAME);

			Client client = new Client();

			client.setUsuario(textArquivo.getText());
			client.setIp(textIpAddress.getText());
			client.setPorta(Integer.parseInt(textPorta.getText()));

			this.serverInterface.connectClient(client);

			JOptionPane.showMessageDialog(this, "conectado!");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Cria a janela onde o usuário realiza a conexão
	 */
	public UIGraficaCliente() {
		JPanel panel = new JPanel();
		JButton jButtonCompartilharArquivos = new JButton("Compartilhar arquivos");
		JButton jButtonConectar = new JButton("Conectar");
		JButton jButtonPesquisarArquivos = new JButton("Pesquisar Arquivo");
		JLabel jLabelIp = new JLabel("IP:");
		JLabel jLabelArquivo = new JLabel("Arquivo:");
		JLabel jLabelPorta = new JLabel("Porta:");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 557, 449);
		
		
		contentPane = new JPanel();
		contentPane.setToolTipText("Digite o que deseja localizar");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		panel.setBounds(5, 5, 526, 133);

		contentPane.add(panel);
		panel.setLayout(null);
		jLabelArquivo.setBounds(10, 9, 50, 14);
		panel.add(jLabelArquivo);

		textArquivo = new JTextField();
		textArquivo.setBounds(70, 6, 136, 20);		
		panel.add(textArquivo);
		textArquivo.setColumns(10);
		
		textIpAddress = new JTextField();		
		textIpAddress.setText(this.ipBase);
		textIpAddress.setBounds(250, 6, 136, 20);
		panel.add(textIpAddress);
		textIpAddress.setColumns(10);

		jLabelPorta.setBounds(400, 9, 46, 14);
		panel.add(jLabelPorta);

		textPorta = new JTextField(); 
		textPorta.setText(this.numberPort);
		textPorta.setBounds(445, 6, 70, 20);
		panel.add(textPorta);
		textPorta.setColumns(10);
	
		textLocalizar = new JTextField();
		textLocalizar.setToolTipText("");
		textLocalizar.setColumns(10);
		textLocalizar.setBounds(10, 40, 300, 20);
		panel.add(textLocalizar);
		
		jButtonCompartilharArquivos.setBounds(10, 70, 170, 23);
		panel.add(jButtonCompartilharArquivos);
		
		jButtonConectar.setBounds(421, 70, 95, 23);
		panel.add(jButtonConectar);
		
		jButtonPesquisarArquivos.setBounds(375, 40, 140, 23);
		panel.add(jButtonPesquisarArquivos);
		
		jLabelIp.setBounds(220, 9, 39, 14);
		panel.add(jLabelIp);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(5, 149, 526, 250);
		contentPane.add(scrollPane);

		tableResultados = new JTable();
		scrollPane.setViewportView(tableResultados);

		jButtonConectar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				connect();
			}
		});
	}

	// Métodos da interface extendida
	// Não são utilizados nesta classe
	@Override
	public void connectClient(Client client) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void publishListFiles(Client client, List<Arquivo> list) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public Map<Client, List<Arquivo>> searchFile(String description) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void diconnect(Client client) throws RemoteException {
		// TODO Auto-generated method stub

	}

}
