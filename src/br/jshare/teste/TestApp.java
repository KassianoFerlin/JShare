package br.jshare.teste;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.jshare.comum.pojos.Arquivo;
import br.jshare.global.Client;

/**
 * Applicação de teste somente um exemplo arquivos ficticios
 * 
 * @author Kassiano
 *
 */
public class TestApp {

	public static void main(String[] args) {
		//
		Map<Client, List<Arquivo>> listFileClients = new HashMap<Client, List<Arquivo>>();
		Map<Client, List<Arquivo>> listFilesFound = new HashMap<Client, List<Arquivo>>();

		List<Arquivo> arquivos1 = new ArrayList<Arquivo>();
		List<Arquivo> arquivos2 = new ArrayList<Arquivo>();
		List<Arquivo> arquivos3 = new ArrayList<Arquivo>();
		List<Arquivo> arquivos4 = new ArrayList<Arquivo>();
		List<Arquivo> arquivos5 = new ArrayList<Arquivo>();
		List<Arquivo> arquivos6 = new ArrayList<Arquivo>();

		// Adicionando arquivos na lista
		// os tamanhos são vaiaveis
		arquivos1.add(new Arquivo("txt", 255,""));
		arquivos1.add(new Arquivo("ini", 278,""));
		arquivos1.add(new Arquivo("php", 400,""));
		arquivos1.add(new Arquivo("docx", 3072,""));
		arquivos1.add(new Arquivo("xlsx", 2048,""));
		arquivos1.add(new Arquivo("dbf", 1024,""));
		arquivos1.add(new Arquivo("java", 24,""));
		arquivos1.add(new Arquivo("cpp", 69,""));

		arquivos2.add(new Arquivo("pptx", 265,""));
		arquivos2.add(new Arquivo("exe", 1278,""));

		arquivos3.add(new Arquivo("txt", 5,""));
		arquivos4.add(new Arquivo("ini", 10,""));
		arquivos5.add(new Arquivo("php", 567,""));
		arquivos6.add(new Arquivo("docx", 172,""));
		arquivos6.add(new Arquivo("java", 32,""));
		//
		
		// Adicionando usuário (cliente) e seus respetivos arquivos
		listFileClients.put(new Client("usuário 1", "192.168.25.10", 2001), arquivos1);
		listFileClients.put(new Client("usuário 2", "192.168.25.11", 2002), arquivos2);
		listFileClients.put(new Client("usuário 3", "192.168.25.12", 2003), arquivos3);
		listFileClients.put(new Client("usuário 4", "192.168.25.13", 2004), arquivos4);
		listFileClients.put(new Client("usuário 5", "192.168.25.14", 2005), arquivos5);
		listFileClients.put(new Client("usuário 6", "192.168.25.15", 2006), arquivos6);

		// Percorrendo a HashMap principal.
		for (Map.Entry<Client, List<Arquivo>> listaProcura : listFileClients.entrySet()) {

			System.out.println("Nome do Usuário: " + listaProcura.getKey().getUsuario());

			List<Arquivo> listaArquivos = new ArrayList<Arquivo>();
			for (Arquivo arquivo : listFileClients.get(listaProcura.getKey())) {
				System.out.println("\tArquivo  : " + arquivo.getArquivo());
				System.out.println("\tTamanho  : " + arquivo.getTamanho());

				//Adicona o arquivo na lista
				//caso o arquivo seja desconhecido ele informa
				switch (arquivo.getArquivo()) {

				case "txt":					
					listaArquivos.add(arquivo);
					listFilesFound.put(new Client(listaProcura.getKey().getUsuario(), listaProcura.getKey().getIp(),
							listaProcura.getKey().getPorta()), listaArquivos);
					break;
				case "ini":
					listaArquivos.add(arquivo);
					listFilesFound.put(new Client(listaProcura.getKey().getUsuario(), listaProcura.getKey().getIp(),
							listaProcura.getKey().getPorta()), listaArquivos);
					break;
				case "docx":
					listaArquivos.add(arquivo);
					listFilesFound.put(new Client(listaProcura.getKey().getUsuario(), listaProcura.getKey().getIp(),
							listaProcura.getKey().getPorta()), listaArquivos);
					break;
				case "xlsx":
					listFilesFound.put(new Client(listaProcura.getKey().getUsuario(), listaProcura.getKey().getIp(),
							listaProcura.getKey().getPorta()), listaArquivos);
					break;
				case "dbf":
					listaArquivos.add(arquivo);
					listFilesFound.put(new Client(listaProcura.getKey().getUsuario(), listaProcura.getKey().getIp(),
							listaProcura.getKey().getPorta()), listaArquivos);
					break;
				case "java":
					listaArquivos.add(arquivo);
					listFilesFound.put(new Client(listaProcura.getKey().getUsuario(), listaProcura.getKey().getIp(),
							listaProcura.getKey().getPorta()), listaArquivos);
					break;
				default:
					arquivo.setDesconhecido("arquivo desconhecido");
					listaArquivos.add(arquivo);
					listFilesFound.put(new Client(listaProcura.getKey().getUsuario(), listaProcura.getKey().getIp(),
							listaProcura.getKey().getPorta()), listaArquivos);
					break;
				}

			}
			System.out.println("\n");
		}
		
		System.out.println("+Lista de arquivos encontrados");
		System.out.println("\n\n");

		for (Map.Entry<Client, List<Arquivo>> listaProcura : listFilesFound.entrySet()) {

			System.out.println("Nome do Usuário: " + listaProcura.getKey().getUsuario());

			for (Arquivo arquivo : listFilesFound.get(listaProcura.getKey())) {

				System.out.println("\tNome do Arquivos  : " + arquivo.getArquivo());
				System.out.println("\tTamanho do Arquivo: " + arquivo.getTamanho());
				if(arquivo.getDesconhecido() != null)
				if(!arquivo.getDesconhecido().trim().equals(""))
					System.out.println("\tArquivo desconhecido: " + arquivo.getTamanho());
			}

			System.out.println("\n");
		}
	}
}
