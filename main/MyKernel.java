package main;

import arvore.Diretorio;
import binary.Binario;
import hardware.Abstracao;
import hardware.Configuracao;
import hardware.HardDisk;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Date;

import arvore.Arquivo;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import operatingSystem.Kernel;

public class MyKernel implements Kernel {
	private HardDisk hd;
	Diretorio raiz;
	Diretorio atual;

	public MyKernel(HardDisk hd) {
		this.hd = hd;
		this.raiz = setRaiz();

	}

	public String ls(String parameters) {
		// variavel result deverah conter o que vai ser impresso na tela apos comando do
		// usuário
		String result = "";
		System.out.println("Chamada de Sistema: ls");
		System.out.println("\tParametros: " + parameters);

		// inicio da implementacao do aluno
		if(parameters.equals(""))
			result = blocoAtual();
		if (parameters.contains("-l")) {
			// imprimeFilhos(Abstracao.getFilhos(hd.blocoToString(buscaParametro(parameters.split("
			// ")[1]).getPonteiro()), hd));
			System.out.println("teste\t " + parameters.replace("-l", "") + "\n"
					+ buscaParametro(parameters.replace("-l", "")).getPonteiro());
			result = imprimeFilhosInfo(Abstracao.getFilhos(
					hd.blocoToString(buscaParametro(parameters.replace("-l", "").trim()).getPonteiro()), hd));

		} else
			{
			result = imprimeFilhos(Abstracao.getFilhos(hd.blocoToString(buscaParametro(parameters).getPonteiro()), hd));
			}

		// fim da implementacao do aluno

		return result;
	}

	public String mkdir(String parameters) {
		// variavel result deverah conter o que vai ser impresso na tela apos comando do
		// usuário
		String result = "";
		System.out.println("Chamada de Sistema: mkdir");
		System.out.println("\tParametros: " + parameters);
		novoDiretorio(parameters);

		// System.out.println("Resultado da busca
		// "+buscaParametro(parameters).getNome());
		// Diretorio dir = new Diretorio(dirRaiz(),"Teste");
		// dir.setPonteiro(hd.buscaEsetaPosicao());
		// hd.setBloco(Abstracao.diretorioToBinario(dir, hd), dir.getPonteiro());
		// System.out.println("Mkdir pai "+ dir.getPai().getPonteiro()+"\t");
		// hd.setBloco(Abstracao.alteraFilho(hd.blocoToString(dir.getPai().getPonteiro()),
		// Binario.intToBinaryString(dir.getPonteiro(),Configuracao.getEndereco()),
		// hd),dir.getPai().getPonteiro());
		//

		return result;
	}

	public String cd(String parameters) {
		// variavel result deverah conter o que vai ser impresso na tela apos comando do
		// usuário
		String result = "";
		String currentDir = "";
		System.out.println("Chamada de Sistema: cd");
		System.out.println("\tParametros: " + parameters);

		// inicio da implementacao do aluno

		// indique o diretório atual. Por exemplo... /
		currentDir = "/";
		hd.setAtual(buscaParametro(parameters).getPonteiro());

		// setando parte gráfica do diretorio atual
		operatingSystem.fileSystem.FileSytemSimulator.currentDir =caminho(dirAtual().getPonteiro());

		// fim da implementacao do aluno

		return result;
	}

	public String rmdir(String parameters) {
		// variavel result deverah conter o que vai ser impresso na tela apos comando do
		// usuário
		String result = "";
		System.out.println("Chamada de Sistema: rmdir");
		System.out.println("\tParametros: " + parameters);

		// inicio da implementacao do aluno
		if (hd.blocoToString(buscaParametro(parameters).getPonteiro()).charAt(0) == '0'
				&& Abstracao.getConteudoBloco(hd.blocoToString(buscaParametro(parameters).getPonteiro()))
						.contains(Abstracao.completaTamanho("", Configuracao.getConteudoBlocoA()))) {
			int posicao = buscaParametro(parameters).getPai().getPonteiro();

			String aux = Abstracao.zeraFilho(hd.blocoToString(posicao),
					Binario.intToBinaryString(buscaParametro(parameters).getPonteiro()), hd);

			hd.setBloco(Abstracao.setConteudo(hd.blocoToString(posicao), aux), posicao);
			hd.setBloco(Abstracao.completaTamanho("", Configuracao.getTamBloco()), posicao);
			hd.setBitDaPosicao(false, posicao);
		} else
			result = "Diretorio nao encontrado ou nao vazio";
		// fim da implementacao do aluno

		return result;
	}

	public String cp(String parameters) {
		// variavel result deverah conter o que vai ser impresso na tela apos comando do
		// usuário
		String result = "";
		System.out.println("Chamada de Sistema: cp");
		System.out.println("\tParametros: " + parameters);

		// inicio da implementacao do aluno
		String[] auxParametros = parameters.split(" ");
		if(!auxParametros[0].contains(".txt"))
		{
		//auxParametros[0].replace(".txt", "");

		String novoBloco = hd.blocoToString(buscaParametro(auxParametros[0]).getPonteiro());
		String blocoDestino = Abstracao.alteraPai(novoBloco, buscaParametro(auxParametros[1]).getPonteiro());
		int novoEndereco = hd.buscaEsetaPosicao();
		blocoDestino = Abstracao.alteraPonteiro(blocoDestino, novoEndereco);
		hd.setBloco(blocoDestino, novoEndereco);
		hd.setBloco(Abstracao.alteraFilho(hd.blocoToString( buscaParametro(auxParametros[1]).getPonteiro()), Binario.intToBinaryString(novoEndereco), hd),buscaParametro(auxParametros[1]).getPonteiro());
		
		
		
		//hd.setBloco(Abstracao.diretorioToBinario(novoDiretorio, hd), novoDiretorio.getPonteiro());
		// fim da implementacao do aluno
		}
		return result;
	}

	public String mv(String parameters) {
		// variavel result deverah conter o que vai ser impresso na tela apos comando dobuscaParametro(auxParametros[1]).getPonteiro()
		// usuário
		String result = "";
		System.out.println("Chamada de Sistema: mv");
		System.out.println("\tParametros: " + parameters);

		// inicio da implementacao do aluno
		String[] auxParametros = parameters.split(" ");
		auxParametros[0].replace(".txt", "");
		if(!auxParametros[0].contains(".txt"))
		{
			String novoBloco = Abstracao.alteraPai(hd.blocoToString(buscaParametro(auxParametros[0]).getPonteiro()),
					(buscaParametro(auxParametros[1]).getPonteiro()));
			hd.setBloco(novoBloco, buscaParametro(auxParametros[0]).getPonteiro());
		}
		else 
		{
			String[] aux = parameters.split("/");
			String nomeA = aux[aux.length - 1];
			int posicao = buscaParametro(auxParametros[0].substring(0, auxParametros[0].length() - nomeA.length())).getPai()
					.getPonteiro();
			ArrayList<Integer> filhos = Abstracao.getFilhos(hd.blocoToString(posicao), hd);

			for (int i = 0; i < filhos.size(); i++) {
				String nome = Binario.binarioToString(Abstracao.getNomeBloco(hd.blocoToString(filhos.get(i))));
				if (nome.equals(nomeA)) {

					int pos = Binario.binaryStringToInt(Abstracao.getEnderecoBloco(hd.blocoToString(filhos.get(i))));
					String novoBloco = Abstracao.alteraPai(hd.blocoToString(pos),
							(buscaParametro(auxParametros[1]).getPonteiro()));
					hd.setBloco(novoBloco, buscaParametro(auxParametros[1]).getPonteiro());
		
		}
		// fim da implementacao do aluno
			}
		}
		return result;
	}

	public String rm(String parameters) {
		// variavel result deverah conter o que vai ser impresso na tela apos comando do
		// usuário
		String result = "";
		System.out.println("Chamada de Sistema: rm");
		System.out.println("\tParametros: " + parameters);

		// inicio da implementacao do aluno
		System.out.println("e para remover "+buscaParametro(parameters).getNome());
		if(true/*!buscaParametro(parameters).getNome().equals("raiz")*/)
		{	
		if (!parameters.contains(".txt")) {
			
			int posicao = buscaParametro(parameters).getPai().getPonteiro();
			if(posicao !=0)
			{
			String aux = Abstracao.zeraFilho(hd.blocoToString(posicao),
					Binario.intToBinaryString(buscaParametro(parameters).getPonteiro()), hd);

			hd.setBloco(Abstracao.setConteudo(hd.blocoToString(posicao), aux), posicao);
			hd.setBloco(Abstracao.completaTamanho("", Configuracao.getTamBloco()), posicao);
			hd.setBitDaPosicao(false, posicao);
			}
		} else {
			String[] aux = parameters.split("/");
			
			String nomeA = aux[aux.length - 1];
			int posicao = buscaParametro(parameters.substring(0, parameters.length() - nomeA.length())).getPai()
					.getPonteiro();
		
		
			
			System.err.println("apagou " + posicao);
			ArrayList<Integer> filhos = Abstracao.getFilhos(hd.blocoToString(posicao), hd);

			for (int i = 0; i < filhos.size(); i++) {
				String nome = Binario.binarioToString(Abstracao.getNomeBloco(hd.blocoToString(filhos.get(i))));
				if (nome.equals(nomeA)) {

					int pos = Binario.binaryStringToInt(Abstracao.getEnderecoBloco(hd.blocoToString(filhos.get(i))));
					String auxA = Abstracao.zeraFilho(hd.blocoToString(posicao),
							Binario.intToBinaryString(
									buscaParametro(parameters.substring(0, parameters.length() - nomeA.length()))
											.getPonteiro()),
							hd);
				//	System.out.println("desse tamanho "+);
					hd.setBloco(Abstracao.setConteudo(hd.blocoToString(posicao), auxA), posicao);
					hd.setBloco(Abstracao.completaTamanho("", Configuracao.getTamBloco()), pos);
					hd.setBitDaPosicao(false, pos);
					i = filhos.size();

				}

			
			
			}
		
		}
		// String[] parametros = parameters.split("/");
		// Diretorio dir =
		// buscaParametro(parameters.substring(0,parametros[parametros.length-1].length()));
		//
		// if(parametros[parametros.length-1].contains(".txt"))
		// {
		// dir.setArquivos(Abstracao.apagaArq(dir.getArquivos(),
		// parametros[parametros.length-1],hd));
		// }
		// else {
		// dir.setFilhos(Abstracao.apagaFilho(dir.getFilhos(),
		// parametros[parametros.length-1],hd));
		// }
		// hd.setBloco(Abstracao.diretorioToBinario(dir, hd), dir.getPonteiro());

		// fim da implementacao do aluno
		}
		return result;
	}

	public String chmod(String parameters) {
		// variavel result deverah conter o que vai ser impresso na tela apos comando do
		// usuário
		String result = "";
		System.out.println("Chamada de Sistema: chmod");
		System.out.println("\tParametros: " + parameters);

		// inicio da implementacao do aluno

		// fim da implementacao do aluno

		return result;
	}

	public String createfile(String parameters) {
		// variavel result deverah conter o que vai ser impresso na tela apos comando do
		// usuário
		String result = "";
		System.out.println("Chamada de Sistema: createfile");
		System.out.println("\tParametros: " + parameters);

		// inicio da implementacao do aluno
		novoArquivo(parameters);

		// fim da implementacao do aluno

		return result;
	}

	public String cat(String parameters) {
		// variavel result deverah conter o que vai ser impresso na tela apos comando do
		// usuário
		String result = "";
		System.out.println("Chamada de Sistema: cat");
		System.out.println("\tParametros: " + parameters);
		String[] parametro = parameters.split("/");

		// inicio da implementacao do aluno

		ArrayList<Integer> arqs = buscaArquivo(parameters);
		if (arqs.size() > 0) {
			for (int i = 0; i < arqs.size(); i++) {
				System.out.println("ARQS " + arqs.size());
				String nomeArquivo = Binario.binarioToString(Abstracao.getNomeBloco(hd.blocoToString(arqs.get(i))));
				System.out.println("NOME QUE TEM " + nomeArquivo);
				if (nomeArquivo.equals(parametro[parametro.length - 1])) {
					result = Binario
							.binarioToString(Abstracao.completaTamanho("", Configuracao.getCompletaConteudoBinario())
									+ Abstracao.getConteudoBloco(hd.blocoToString(arqs.get(i))));
					i = arqs.size();
				}
			}
		} else
			result = "cat: Arquivo nao existe";
		// fim da implementacao do aluno

		return result;
	}

	private ArrayList<Integer> buscaArquivo(String parametro) {
		String[] aux = parametro.split("/");

		Diretorio dir = buscaParametro(parametro.substring(0, parametro.length() - aux[aux.length - 1].length()));
		String arquivos = Abstracao.getConteudoBloco(hd.blocoToString(dir.getPonteiro()));

		return Abstracao.converteArquivos(arquivos, hd);
	}

	public String batch(String parameters) {
		// variavel result deverah conter o que vai ser impresso na tela apos comando do
		// usuário
		String result = "";
		System.out.println("Chamada de Sistema: batch");
		System.out.println("\tParametros: " + parameters);
		System.out.println(parameters);
		// inicio da implementacao do aluno
		try {
			BufferedReader br = new BufferedReader(new FileReader(parameters));
			while (br.ready()) {
				String linha = br.readLine();
				System.err.println(linha);
				if (linha.contains("mkdir")) {
					mkdir(linha.replace("mkdir", "").trim());
				}
				if (linha.contains("createfile")) {
					createfile(linha.replace("createfile", "").trim());
				}
				if (linha.contains("cd")) {
					cd(linha.replace("cd", "").trim());
				}
				if (linha.contains("rmdir")) {
					rmdir(linha.replace("rmdir", "").trim());
				}
				if (linha.contains("rm")) {
					System.out.println("REMOVE");
					if(!linha.contains("rm -r"))
					rm(linha.replace("rm", "").trim());
				}
				if (linha.contains("chmod")) {
				//	chmod(linha.replace("chmod", "").trim());
				}
				if (linha.contains("cp")) {
				//	cp(linha.replace("cp", "").trim());
				}
				if (linha.contains("mv")) {
					//mv(linha.replace("mv", "").trim());
				}
				if (linha.contains("dump")) {
					dump(linha.replace("dump", "").trim());
				}
				System.out.println(linha);
			}
			br.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

		// fim da implementacao do aluno

		return result;
	}

	public String dump(String parameters) {
		// variavel result deverah conter o que vai ser impresso na tela apos comando do
		// usuário
		String result = "";
		System.out.println("Chamada de Sistema: dump");
		System.out.println("\tParametros: " + parameters);

		// inicio da implementacao do aluno
		System.out.println("CHEGOU ");
		File arq = new File(parameters);
		System.err.println("caminho \n\n\n\n\n\n\n grava");
		try {
			if (!arq.exists())
				arq.delete();
			arq.createNewFile();
			// {
			System.out.println("Vai gravar");
			arq.createNewFile();
			BufferedWriter bufW = new BufferedWriter(new FileWriter(arq));

			// bufW.write(recursaoDumpArq( ).replaceAll("//", "/"));
			bufW.write(recDump());
			bufW.newLine();

			bufW.close();
			// }
		} catch (Exception e) {
			// TODO: handle exception
		}
		// fim da implementacao do aluno

		return result;
	}

	public String info() {
		// variavel result deverah conter o que vai ser impresso na tela apos comando do
		// usuário
		String result = "";
		System.out.println("Chamada de Sistema: info");
		System.out.println("\tParametros: sem parametros");

		// nome do aluno
		String name = "Gabriel de Carvalho Baldao";
		// numero de matricula
		String registration = "14152000441";
		// versao do sistema de arquivos
		String version = "2.3";

		result += "Nome do Aluno:        " + name;
		result += "\nMatricula do Aluno:   " + registration;
		result += "\nVersao do Kernel:     " + version;

		return result;
	}

	/*************************************************************************************/

	public String recDump() {
		StringBuilder str = new StringBuilder();
		for (int i = 1; i < hd.getBlocos(); i++) {
			if (hd.getBitDaPosicao(i)) {
				if (hd.blocoToString(i).charAt(0) == '0') {
					str.append("mkdir /" + caminho(i) + "\n");
				} else {
					String conteudo = Binario
							.binarioToString(Abstracao.completaTamanho("", Configuracao.getCompletaConteudoBinario())
									+ Abstracao.getConteudoBloco(hd.blocoToString(i)));
					str.append("createfile /" + caminho(i) + " " + conteudo + "\n");
				}
			}

		}
		return str.toString();
	}

	public String caminho(int indice) {
		if (Binario.binaryStringToInt(Abstracao.getPaiBloco(hd.blocoToString(indice))) != 0) {
			return (caminho(Binario.binaryStringToInt(Abstracao.getPaiBloco(hd.blocoToString(indice)))) + "/"
					+ Binario.binarioToString(Abstracao.getNomeBloco(hd.blocoToString(indice))));
		}

		return Binario.binarioToString(Abstracao.getNomeBloco(hd.blocoToString(indice)));
	}

	public String imprimeFilhos(ArrayList<Integer> filhos) {
		StringBuilder str = new StringBuilder();
		System.out.println("tamanho filhos");
		for (int i = 0; i < filhos.size(); i++) {
			if (hd.getBitDaPosicao(filhos.get(i))) {
				str.append(Binario.binarioToString(Abstracao.getNomeBloco(hd.blocoToString(filhos.get(i)))));

				str.append("\n");
			}
		}
		return str.toString();
	}

	public String imprimeFilhosInfo(ArrayList<Integer> filhos) {
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < filhos.size(); i++) {
			if (hd.getBitDaPosicao(filhos.get(i))) {

				String bloco = hd.blocoToString(filhos.get(i));
				String data = monthToString(Binario.binaryStringToInt(Abstracao.getMesBloco(bloco))) + " "
						+ Binario.binaryStringToInt(Abstracao.getDiaBloco(bloco)) + " "
						+ Binario.binaryStringToInt(Abstracao.getHoraBloco(bloco)) + ":"
						+ Binario.binaryStringToInt(Abstracao.getMinutoBloco(bloco)) + " ";

				String nome = Binario.binarioToString(Abstracao.getNomeBloco(bloco));
				str.append(data + " " + nome);

				str.append("\n");

			}
		}
		return str.toString();
	}

	private String blocoAtual() {
		return hd.blocoToString(hd.getAtual());
	}

	private String blocoRaiz() {
		return hd.blocoToString(hd.getRaiz());
	}

	private Diretorio dirAtual() {
		return Abstracao.blocoToDir(hd, blocoAtual());
	}

	private Diretorio dirRaiz() {
		return Abstracao.blocoToDir(hd, blocoRaiz());
	}

	public Diretorio buscaParametro(String parametro) {

		String[] aux = parametro.split("/");
		if (parametro.equals(""))
			return dirAtual();
		if (parametro.charAt(0) == '/') {
			// System.out.println("\n\n\t\tRAIZ");
			return buscaDiretorio(dirRaiz(), aux, 1);
		} else {
			return buscaDiretorio(dirAtual(), aux, 0);
		}
	}

	private String getBlocoHD(int endereco) {
		return hd.blocoToString(endereco);
	}

	private Diretorio dirNoHd(int endereco) {
		return Abstracao.blocoToDir(hd, getBlocoHD(endereco));
	}

	private Diretorio buscaDiretorio(Diretorio diretorio, String[] parametro, int j) {

		if (j < parametro.length) {
			if (parametro[j].equals("..")) {
				if (diretorio.getPonteiro() == diretorio.getPai().getPonteiro())
					return buscaDiretorio(dirNoHd(0), parametro, ++j);
				return buscaDiretorio(dirNoHd(diretorio.getPai().getPonteiro()), parametro, ++j);
			}
			if (parametro[j].equals(".")) {
				return buscaDiretorio(dirNoHd(diretorio.getPonteiro()), parametro, ++j);
			}
			ArrayList<Integer> filhos = Abstracao.getFilhos(hd.blocoToString(diretorio.getPonteiro()), hd);
			if (filhos.size() > 0)
				for (int i = 0; i < filhos.size(); i++) {
					if (Binario.binarioToString(Abstracao.getNomeBloco(hd.blocoToString(filhos.get(i))))
							.equals(parametro[j])) {

						if (j == parametro.length - 1) {
							return Abstracao.blocoToDir(hd, hd.blocoToString(filhos.get(i)));
						} else
							return buscaDiretorio(Abstracao.blocoToDir(hd, hd.blocoToString(filhos.get(i))), parametro,
									++j);

					}
				}

		}
		return diretorio;
	}

	private Diretorio novoDiretorio(String parameters) {

		String[] diretorios = parameters.split("/");
		String caminho = parameters.substring(0, parameters.length() - diretorios[diretorios.length - 1].length());
		if (!Abstracao.contemFilho(hd.blocoToString(buscaParametro(caminho).getPonteiro()),
				diretorios[diretorios.length - 1], hd)) {
			Diretorio pai = buscaParametro(caminho);
			if(pai.getFilhos().size()+pai.getArquivos().size() < Configuracao.getQntdFilhos())
			{
			Diretorio novoDiretorio = new Diretorio(pai, diretorios[diretorios.length - 1]);
			novoDiretorio.setPonteiro(hd.buscaEsetaPosicao());
			// System.out.println("bit "+novoDiretorio.getPonteiro());
			novoDiretorio.setPermissao("777");
			setHora(novoDiretorio);
			hd.setBloco(Abstracao.diretorioToBinario(novoDiretorio, hd), novoDiretorio.getPonteiro());
			hd.setBloco(
					Abstracao.alteraFilho(hd.blocoToString(novoDiretorio.getPai().getPonteiro()),
							Binario.intToBinaryString(novoDiretorio.getPonteiro(), Configuracao.getEndereco()), hd),
					novoDiretorio.getPai().getPonteiro());

			return novoDiretorio;
			}
		}
		return null;
	}

	private Arquivo novoArquivo(String parameters) {
		System.out.println(parameters);
		String[] parametro = parameters.split(" ");
		String[] caminho = parametro[0].split("/");
		Date data = new Date();

		String nome = caminho[caminho.length - 1].substring(0, caminho[caminho.length - 1].length() - 4);
		String tipo = caminho[caminho.length - 1].substring(caminho[caminho.length - 1].length() - 3,
				caminho[caminho.length - 1].length());
		String conteudo = parametro[parametro.length - 1];
		String caminhoArquivo = parametro[0].substring(0, parametro[0].length() - (nome.length() + 1 + tipo.length()));

		System.out.println("Nome " + nome);
		System.out.println("Tipo " + tipo);
		System.out.println("Conteudo " + conteudo);
		System.out.println("Caminho " + caminhoArquivo);

		Arquivo novoArquivo = new Arquivo(caminho[caminho.length - 1], tipo, conteudo, buscaParametro(caminhoArquivo),
				String.valueOf(monthToString(data.getMonth() + 1)) + " " + String.valueOf(data.getDate()) + " "
						+ String.valueOf(data.getHours()) + ":" + String.valueOf(data.getMinutes()));
		setHora(novoArquivo);
		novoArquivo.setPonteiro(hd.buscaEsetaPosicao());
		novoArquivo.setPermissao("777");
		setHora(novoArquivo);
		novoArquivo.setPai(buscaParametro(caminhoArquivo));
		System.out.println("Arquivo tem ponteiro " + novoArquivo.getPonteiro());

		hd.setBloco(
				Abstracao.alteraFilho(hd.blocoToString(novoArquivo.getPai().getPonteiro()),
						Binario.intToBinaryString(novoArquivo.getPonteiro(), Configuracao.getEndereco()), hd),
				novoArquivo.getPai().getPonteiro());

		hd.setBloco(Abstracao.arquivoToBinario(novoArquivo, hd), novoArquivo.getPonteiro());

		return novoArquivo;
	}

	public void setHora(Diretorio dir) {
		Date data = new Date();

		dir.setData(String.valueOf(monthToString(data.getMonth() + 1)) + " " + String.valueOf(data.getDate()) + " "
				+ String.valueOf(data.getHours()) + ":" + String.valueOf(data.getMinutes()));
		// String endereco =
		// parameters.substring(0,parameters.length()-diretorios[diretorios.length-1].length());
		dir.setMes(data.getMonth() + 1);
		dir.setDia(data.getDate());
		dir.setHora(data.getHours());
		dir.setMin(data.getMinutes());
	}

	public void setHora(Arquivo arq) {
		Date data = new Date();
		arq.setData(String.valueOf(monthToString(data.getMonth() + 1)) + " " + String.valueOf(data.getDate()) + " "
				+ String.valueOf(data.getHours()) + ":" + String.valueOf(data.getMinutes()));
		// String endereco =
		// parameters.substring(0,parameters.length()-diretorios[diretorios.length-1].length());
		arq.setMes(data.getMonth() + 1);
		arq.setDia(data.getDate());
		arq.setHora(data.getHours());
		arq.setMin(data.getMinutes());
	}

	public String monthToString(int mes) {
		switch (mes) {
		case 1:
			return "Jan";
		case 2:
			return "Fev";
		case 3:
			return "Mar";
		case 4:
			return "Abr";
		case 5:
			return "Mai";
		case 6:
			return "Jun";
		case 7:
			return "Jul";
		case 8:
			return "Aug";
		case 9:
			return "Set";
		case 10:
			return "Out";
		case 11:
			return "Nov";
		case 12:
			return "Dez";

		}
		return "";
	}

	public Diretorio setRaiz() {
		Date data = new Date();
		Diretorio novaRaiz = new Diretorio(null, "raiz");
		novaRaiz.setPermissao("777");

		novaRaiz.setPonteiro(hd.buscaEsetaPosicao());

		setHora(novaRaiz);
		hd.setBloco(Abstracao.diretorioToBinario(novaRaiz, hd), novaRaiz.getPonteiro());
		// hd.setBloco(Abstracao.diretorioToBinario(novaRaiz, hd),
		// (novaRaiz.getPonteiro()));
		return novaRaiz;

	}
}
