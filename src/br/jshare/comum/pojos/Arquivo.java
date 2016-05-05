package br.jshare.comum.pojos;

import java.io.Serializable;
/**
 * Classe de arquivos
 * Obtem o nome e o tamanho do aquivo, com um método toString sobrescrito
 * @author Kassiano
 *
 */
public class Arquivo implements Serializable {
	private static final long serialVersionUID = 7077294568159222912L;
	
	 
	private String arquivo;
	private long tamanho;
	private String desconhecido;
	
	public String getArquivo() {
		return arquivo;
	}

	public void setArquivo(String arquivo) {
		this.arquivo = arquivo;
	}

	public long getTamanho() {
		return tamanho;
	}

	public void setTamanho(long tamanho) {
		this.tamanho = tamanho;
	}
	
	/**
	 * Metodo 
	 * retornando uma string contendo o Nome e o Tamanho do Arquivo
	 */
	public String getProperts() {
		// TODO Auto-generated method stub
		return arquivo+" - "+tamanho;
	}

	public Arquivo() {
		super();
	}

	public Arquivo(String arquivo, long tamanho, String deconhecido) {
		super();
		this.arquivo = arquivo;
		this.tamanho = tamanho;
		this.desconhecido = desconhecido;
	}

	public String getDesconhecido() {
		return desconhecido;
	}

	public void setDesconhecido(String desconhecido) {
		this.desconhecido = desconhecido;
	}
	
	
}
