package br.jshare.libs;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Classe contendo fun��es auxiliares Fun��o de lista os ip's Fun��o que retorna
 * a formata��o da data padr�o Fun��o para valida��o de ip Fun��o para valida��o
 * de portas
 * 
 * @author Kassiano
 *
 */
public class Extras {
	/**
	 * Fun��o que lista os ip's, retornando uma lista de todos dispon�veis.
	 * 
	 * @return lista de string
	 */
	public List<String> localizarIp() {
		List<String> listaIp = new ArrayList<String>();
		try {
			Enumeration<NetworkInterface> listIntefaces = NetworkInterface.getNetworkInterfaces();
			// La�o que varre a lista de endere�os dispon�veis
			// chama-se interface o nome do disposivito que cont�m um ip
			while (listIntefaces.hasMoreElements()) {
				// Pega 1 objeto do tipo NetworkInterface
				NetworkInterface networkInterface = listIntefaces.nextElement();
				if (networkInterface.isUp()) {
					// Obtem os endere�os do dispositivo
					Enumeration<InetAddress> listAddress = networkInterface.getInetAddresses();
					while (listAddress.hasMoreElements()) {
						InetAddress addr = listAddress.nextElement();
						// Obtem o ip do dispositivo
						String ip = addr.getHostAddress();
						// Verifica se o ip � valido, checando se cada poi��o
						// obdece a regra de forma��o
						// como se trata de IPv4 segue o seguinte padr�o
						// 000.000.000.000
						// n�mero compostdo por at� 12 posi��es e cada posi��o
						// de 1 a 9
						if (this.validIp(ip)) {
							listaIp.add(ip);
						}
					}
				}
			}
		} catch (SocketException e) {
			// Caso ocorra algum erro retonar o mesmo na tela
			e.printStackTrace();
		}
		// Se n�o ocorrer nenhum erro retonar a lista de IP's dispon�veis
		return listaIp;
	}

	/**
	 * Fun��o que retorna a formata��o para data padr�o
	 * 
	 * @return formato padr�o para data
	 */
	public SimpleDateFormat formatoData() {
		return new SimpleDateFormat("dd/MM/yyyy H:mm:ss");
	}

	/**
	 * Verifica se o Ip � valido
	 * 
	 * @param ipAddress
	 * @return
	 */
	public Boolean validIp(String ipAddress) {
		return ipAddress.matches("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}");
	}

	/**
	 * Valida se a porta informada � v�lida Utilizado o como porta inicial a
	 * 2000, evitando conflitos com portas baixas que por padr�o j� s�o
	 * utilizadas em alguns servi�os, retona uma string contendo o erro caso a
	 * porta seja inv�lida
	 * 
	 * @param port
	 * @return
	 */
	public String portValid(String port) {

		if (!port.matches("[0-9]+"))
			return "A porta s� pode conter n�meros";

		if (port.trim().length() < 4)
			return "A porta inv�lida";

		if (!this.tryParseInt(port))
			return "O valor informado n�o � um numero v�lido";

		if (2000 > Integer.parseInt(port))
			return "A porta informada deve ser entre 2000 e 65535";

		return "";
	}

	/**
	 * Verifica se o valor informado � do tipo inteiro
	 * 
	 * @param value
	 * @return
	 */
	private boolean tryParseInt(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
