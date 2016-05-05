package br.jshare.libs;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Classe contendo funções auxiliares Função de lista os ip's Função que retorna
 * a formatação da data padrão Função para validação de ip Função para validação
 * de portas
 * 
 * @author Kassiano
 *
 */
public class Extras {
	/**
	 * Função que lista os ip's, retornando uma lista de todos disponíveis.
	 * 
	 * @return lista de string
	 */
	public List<String> localizarIp() {
		List<String> listaIp = new ArrayList<String>();
		try {
			Enumeration<NetworkInterface> listIntefaces = NetworkInterface.getNetworkInterfaces();
			// Laço que varre a lista de endereços disponíveis
			// chama-se interface o nome do disposivito que contém um ip
			while (listIntefaces.hasMoreElements()) {
				// Pega 1 objeto do tipo NetworkInterface
				NetworkInterface networkInterface = listIntefaces.nextElement();
				if (networkInterface.isUp()) {
					// Obtem os endereços do dispositivo
					Enumeration<InetAddress> listAddress = networkInterface.getInetAddresses();
					while (listAddress.hasMoreElements()) {
						InetAddress addr = listAddress.nextElement();
						// Obtem o ip do dispositivo
						String ip = addr.getHostAddress();
						// Verifica se o ip é valido, checando se cada poição
						// obdece a regra de formação
						// como se trata de IPv4 segue o seguinte padrão
						// 000.000.000.000
						// número compostdo por até 12 posições e cada posição
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
		// Se não ocorrer nenhum erro retonar a lista de IP's disponíveis
		return listaIp;
	}

	/**
	 * Função que retorna a formatação para data padrão
	 * 
	 * @return formato padrão para data
	 */
	public SimpleDateFormat formatoData() {
		return new SimpleDateFormat("dd/MM/yyyy H:mm:ss");
	}

	/**
	 * Verifica se o Ip é valido
	 * 
	 * @param ipAddress
	 * @return
	 */
	public Boolean validIp(String ipAddress) {
		return ipAddress.matches("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}");
	}

	/**
	 * Valida se a porta informada é válida Utilizado o como porta inicial a
	 * 2000, evitando conflitos com portas baixas que por padrão já são
	 * utilizadas em alguns serviços, retona uma string contendo o erro caso a
	 * porta seja inválida
	 * 
	 * @param port
	 * @return
	 */
	public String portValid(String port) {

		if (!port.matches("[0-9]+"))
			return "A porta só pode conter números";

		if (port.trim().length() < 4)
			return "A porta inválida";

		if (!this.tryParseInt(port))
			return "O valor informado não é um numero válido";

		if (2000 > Integer.parseInt(port))
			return "A porta informada deve ser entre 2000 e 65535";

		return "";
	}

	/**
	 * Verifica se o valor informado é do tipo inteiro
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
