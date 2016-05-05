package br.jshare.global;

import java.io.Serializable;

/**
 * Classe de identifção do cliente, contendo os seguintes dados
 *  - Usuário 
 *  - Ip da máquina cliente
 *  - Porta para conexão
 * @author Kassiano
 *
 */
public class Client implements Serializable {

	private static final long serialVersionUID = 6789870883011342904L;
	
	private String usuario;
	private String ip;
	private int porta;
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPorta() {
		return porta;
	}
	public void setPorta(int porta) {
		this.porta = porta;
	}
	public Client(String usuario, String ip, int porta) {
		super();
		this.usuario = usuario;
		this.ip = ip;
		this.porta = porta;
	}
	public Client() {
		super();
	} 
}
