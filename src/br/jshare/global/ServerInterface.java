package br.jshare.global;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import br.jshare.comum.pojos.Arquivo;

/**
 * Interface que extende a interface Remote, contendo os métodos do servidor a
 * serem implementados, cada método com seu respectivo erro caso ocorra
 * 
 * @author Kassiano
 *
 */
public interface ServerInterface extends Remote {

	//Nome do serviço
	public static final String SERVICE_NAME = "JShare";

	/**
	 * Registrando o um novo acesso. Recebe um parametro do tipo cliente, caso
	 * ocorra algum erro, retorna uma exceção do tipo RemoteException
	 * 
	 * @param client
	 * @throws RemoteException
	 */
	public void connectClient(Client client) throws RemoteException;

	/**
	 * Disponibiliza ao cliente solicitante a lista de arquivos diponíveis,
	 * retorna uma exceção do tipo RemoteException
	 * 
	 * @param client
	 * @param list
	 * @throws RemoteException
	 */
	public void publishListFiles(Client client, List<Arquivo> list) throws RemoteException;

	/**
	 * Método responsável por realizar pesquisa de arquivos por sua descrição,
	 * retorna uma listagem de arquivos para o cliente solicitante, retorna uma
	 * exceção do tipo RemoteException
	 * 
	 * @param description
	 * @return
	 * @throws RemoteException
	 */
	public Map<Client, List<Arquivo>> searchFile(String description) throws RemoteException;
 
	/**
	 * Desconecta o servidor, indisponibilizando os arquivos para os demais clientes 
	 * exceção do tipo RemoteException
	 * 
	 * @param client
	 * @throws RemoteException
	 */
	public void diconnect(Client client) throws RemoteException;

}
