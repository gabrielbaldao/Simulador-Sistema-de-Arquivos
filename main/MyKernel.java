package main;

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
import arvore.Diretorio;
import binary.Binario;
import hardware.Abstracao;
import hardware.HardDisk;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import operatingSystem.Kernel;

public class MyKernel implements Kernel{

	Diretorio raiz ;
	Diretorio atual;
	Diretorio nulo;
	HardDisk hd ;
	public MyKernel(HardDisk hd){
		this.hd = hd;
		raiz = setRaiz();
		
//		System.out.println(Abstracao.diretorioToBinario(raiz, hd));
//		Abstracao.blocoToDir(hd, Abstracao.diretorioToBinario(raiz, hd).toString());

		nulo = new Diretorio(null,"nulo");
		
		setAtual(raiz);
	}
	/**/
	public String ls(String parameters) {
		//variavel result deverah conter o que vai ser impresso na tela apos comando do usuário
		String result = "";
		System.out.println("Chamada de Sistema: ls");
		System.out.println("\tParametros: " + parameters);

		//inicio da implementacao do aluno
		parameters.replace(" ", "");
		if(parameters.equals("-l"))
		{
			result = getAtual().imprimeInfo();
		}
		else
			result =buscaParametro(parameters).imprime();// getAtual().imprime();

		//fim da implementacao do aluno

		return result;
	}

	public String mkdir(String parameters) {

		//variavel result deverah conter o que vai ser impresso na tela apos comando do usuário
		String result = "";
		System.out.println("Chamada de Sistema: mkdir");
		System.out.println("\tParametros: " + parameters);

		Date data = new Date();

		


		//   atual.addDiretorio(novoDiretorio);
		//		if(buscaFilhos(parameters, novo))
		String[] diretorios = parameters.split("/");
		
		String endereco = parameters.substring(0,parameters.length()-diretorios[diretorios.length-1].length());
		Diretorio dest = buscaParametro(endereco);
		if(dest!=null)
		{
			if(!Naoencontrou && !containsDiretorio(buscaParametro(parameters.substring(0,parameters.length()-diretorios[diretorios.length-1].length())), diretorios[diretorios.length-1]))
			{
				Diretorio novoDiretorio = novoDiretorio(parameters);
				buscaParametro(endereco).addDiretorio(novoDiretorio);
				System.out.println("end -"+endereco);
			System.out.println("BUSCA PELO HD >"+hd.buscaParametro(endereco).getNome()+"< diselegante");
				//Abstracao.diretorioToBinario(novoDiretorio, hd);
			String ponteiroBin = Binario.intToBinaryString(Integer.parseInt(novoDiretorio.getPonteiro()),64);
			int ponteiroPai = Integer.parseInt(buscaParametro(endereco).getPonteiro());
			hd.setBloco(Abstracao.setFilho(ponteiroBin,hd ,ponteiroPai), ponteiroPai);
			System.out.println("Tamanho filho "+Abstracao.blocoToDir(hd, hd.blocoToString(0)).getFilhos().size());
			}
			else result = "Diretorio nao foi criado";
		}
		else result = "Caminho inexistente";
		/**
		 * LEMBRA DISSO EM CREATEFILE 
		 * ****/
		//inicio da implementacao do alunoas

		//fim da implementacao do aluno

		return result;
	}
	public String cd(String parameters) {
		//variavel result deverah conter o que vai ser impresso na tela apos comando do usuário
		String result = "";
		System.out.println("Chamada de Sistema: cd");
		System.out.println("\tParametros: " + parameters);

		//inicio da implementacao do aluno
		Diretorio atualAux = buscaParametro(parameters);
		Diretorio atualAux2 = hd.buscaParametro(parameters);
		hd.setAtual(Integer.parseInt(atualAux2.getPonteiro()));
		if(atualAux != null)
			setAtual(atualAux);
		else result = "Diretorio invalido";


		//setando parte gráfica do diretorio atual
		operatingSystem.fileSystem.FileSytemSimulator.currentDir = current(getAtual());

		//		if(encontrou)
		//			result ="Diretório "+ parameters+ "  não existe";

		//fim da implementacao do aluno

		return result;
	}

	public String rmdir(String parameters) {
		//variavel result deverah conter o que vai ser impresso na tela apos comando do usuário
		String result = "";
		System.out.println("Chamada de Sistema: rmdir");
		System.out.println("\tParametros: " + parameters);

		//inicio da implementacao do aluno
		String[] diretorios = parameters.split("/");
		String nome = diretorios[diretorios.length-1];
		Diretorio aux = buscaParametro(parameters.substring(0,parameters.length()-diretorios[diretorios.length-1].length()));
		for(int i=0;i<aux.getFilhos().size();i++)
		{
			if(aux.getFilhos().get(i).getNome().equals(nome))

			{
				if(aux.getFilhos().get(i).getFilhos().isEmpty() && aux.getFilhos().get(i).getArquivos().isEmpty())
				{
					//result = aux.getNome()+" removido com sucesso";
					aux.getFilhos().remove(i);

				}
				else result = "Diretorio nao vazio, diretorio nao excluido";
			}
		}
		//fim da implementacao do aluno

		return result;
	}

	public String cp(String parameters) {
		//variavel result deverah conter o que vai ser impresso na tela apos comando do usuário
		String result = "";
		System.out.println("Chamada de Sistema: cp");
		System.out.println("\tParametros: " + parameters);

		String[] auxParametros = parameters.split(" ");

		if(auxParametros[0].contains(".txt")) {
			String[] parametro = parameters.split(" ");
			String[] caminho = parametro[0].split("/");			
			String nome = caminho[caminho.length-1].substring(0, caminho[caminho.length-1].length()-4);
			String tipo = caminho[caminho.length-1].substring( caminho[caminho.length-1].length()-3, caminho[caminho.length-1].length());
			String conteudo = parametro[parametro.length-1];
			System.out.println("Nome "+nome);
			System.out.println("Tipo "+tipo);
			System.out.println("Conteudo "+conteudo);
			System.out.println("Caminho ");
			Diretorio dir = buscaParametro(auxParametros[0].substring(0, auxParametros[0].length()-(nome.length()+3)));

			for(int i=0;i<dir.getArquivos().size();i++)
			{
				if(dir.getArquivos().get(i).getNome().equals(nome))
				{
					buscaParametro(auxParametros[1]).addArquivo(dir.getArquivos().get(i));
				}
			}
		}
		else {

			Naoencontrou = false;
			/***ESSE AUX TA COM OS PARAMETROS ERRADODS****/
			Diretorio origem = buscaParametro(auxParametros[0]);			
			Diretorio destino = buscaParametro(auxParametros[1]);
			destino.getFilhos().add(origem);


		}
		return result;
	}

	public String mv(String parameters) {
		//variavel result deverah conter o que vai ser impresso na tela apos comando do usuário
		String result = "";
		System.out.println("Chamada de Sistema: mv");
		System.out.println("\tParametros: " + parameters);

		//inicio da implementacao do aluno
		String[] auxParametros = parameters.split(" ");

		if(auxParametros[0].contains(".txt")) {
			String[] parametro = parameters.split(" ");
			String[] caminho = parametro[0].split("/");			
			String nome = caminho[caminho.length-1].substring(0, caminho[caminho.length-1].length()-4);
			String tipo = caminho[caminho.length-1].substring( caminho[caminho.length-1].length()-3, caminho[caminho.length-1].length());
			String conteudo = parametro[parametro.length-1];
			System.out.println("Nome "+nome);
			System.out.println("Tipo "+tipo);
			System.out.println("Conteudo "+conteudo);
			System.out.println("Caminho ");
			Diretorio dir = buscaParametro(auxParametros[0].substring(0, auxParametros[0].length()-(nome.length()+3)));

			for(int i=0;i<dir.getArquivos().size();i++)
			{
				if(dir.getArquivos().get(i).getNome().equals(nome))
				{
					buscaParametro(auxParametros[1]).addArquivo(dir.getArquivos().get(i));
				}
			}
			for (int i = 0; i <dir.getPai().getArquivos().size(); i++) {
				if(dir.getPai().getArquivos().get(i).getNome().equals(nome))
				{
					dir.getPai().getArquivos().remove(i);
				}
			}
		}
		else {

			Naoencontrou = false;
			/***ESSE AUX TA COM OS PARAMETROS ERRADODS****/
			Diretorio origem = buscaParametro(auxParametros[0]);
			Diretorio destino = buscaParametro(auxParametros[1]);
			destino.getFilhos().add(origem);
			for(int i=0;i<origem.getPai().getFilhos().size();i++)
			{
				if(origem.getPai().getFilhos().get(i).getNome().equals(origem.getNome()))
					origem.getPai().getFilhos().remove(i);
			}



		}
		//fim da implementacao do aluno

		return result;
	}

	public String rm(String parameters) {
		//variavel result deverah conter o que vai ser impresso na tela apos comando do usuário
		String result = "";
		System.out.println("Chamada de Sistema: rm");
		System.out.println("\tParametros: " + parameters);
		String[] auxParametros = parameters.split(" ");
		for (String k : auxParametros) {
			System.out.println(k+"<-");
		}
		//inicio da implementacao do aluno
		if(parameters.contains(".txt")) {
			String[] parametro = parameters.split(" ");
			String[] caminho = parametro[0].split("/");			
			String nome = caminho[caminho.length-1].substring(0, caminho[caminho.length-1].length()-4);
			String tipo = caminho[caminho.length-1].substring( caminho[caminho.length-1].length()-3, caminho[caminho.length-1].length());
			String conteudo = parametro[parametro.length-1];
			String caminhoArquivo = parametro[0].substring(0, parametro[0].length()-(nome.length()+1+tipo.length()));
			System.out.println("Nome "+nome);
			System.out.println("Tipo "+tipo);
			System.out.println("Conteudo "+conteudo);
			System.out.println("Caminho "+ caminhoArquivo);
			Diretorio dir = buscaParametro(caminhoArquivo);
			for(int i=0;i<dir.getArquivos().size();i++)
			{
				if(dir.getArquivos().get(i).getNome().equals(nome))
					dir.getArquivos().remove(i);
			}
		}
		else {
			Diretorio diretorio = buscaParametro(auxParametros[auxParametros.length-1]);
			String[] diretorios = auxParametros[auxParametros.length-1].split("/");
			String nome = diretorios[diretorios.length-1];
			Diretorio aux = buscaParametro(auxParametros[auxParametros.length-1].substring(0,auxParametros[auxParametros.length-1].length()-diretorios[diretorios.length-1].length()));


			for(int i=0;i<aux.getFilhos().size();i++)
			{
				if(aux.getFilhos().get(i).getNome().equals(nome))

				{
					aux.getFilhos().remove(i);
					//	result = aux.getNome()+" removido com sucesso";

				}
			}
		}
		//fim da implementacao do aluno

		return result;
	}

	public String chmod(String parameters) {
		//variavel result deverah conter o que vai ser impresso na tela apos comando do usuário
		String result = "";
		System.out.println("Chamada de Sistema: chmod");
		System.out.println("\tParametros: " + parameters);

		//inicio da implementacao do aluno

		//fim da implementacao do aluno

		return result;
	}

	public String createfile(String parameters) {
		//variavel result deverah conter o que vai ser impresso na tela apos comando do usuário
		String result = "";
		System.out.println("Chamada de Sistema: createfile");
		System.out.println("\tParametros: " + parameters);

		//inicio da implementacao do aluno

		Arquivo novoArquivo = novoArquivo(parameters);
		String[] parametro = parameters.split(" ");
		String caminhoArquivo = parametro[0].substring(0, parametro[0].length()-(novoArquivo.getNome().length()+1+novoArquivo.getTipo().length()));

		if(!Naoencontrou && !containsArq(buscaParametro(caminhoArquivo), novoArquivo.getNome()))
		{
			novoArquivo.setPonteiro(String.valueOf(hd.buscaEsetaPosicao()));
			hd.setBloco(Abstracao.arquivoToBinario(novoArquivo, hd), Integer.parseInt(novoArquivo.getPonteiro()));
			buscaParametro(caminhoArquivo).addArquivo(novoArquivo);
		}
		else result = "Arquivo nao foi criado";
		//	Arquivo novoArquivo = new Arquivo(caminho[caminho.length-1].substring(0, caminho[caminho.length-1].length()-4),caminho[caminho.length-1].substring( caminho[caminho.length-1].length()-3, caminho[caminho.length-1].length()),parametro[parametro.length-1]);
		//	novoArquivo.setData(String.valueOf(monthToString(data.getMonth()+1))+" "+String.valueOf(data.getDate())+" "+String.valueOf(data.getHours())+":"+String.valueOf(data.getMinutes()));
		//	buscaParametro(caminho[caminho.length-1]).addArquivo(novoArquivo);
		//fim da implementacao do aluno

		return result;
	}

	public String cat(String parameters) {
		String result = "";
		System.out.println("Chamada de Sistema: cat");
		System.out.println("\tParametros: " + parameters);
		String caminho[] = parameters.split("/");
		Naoencontrou = false;
		boolean arqExist = false;
		Diretorio aux = buscaParametro(parameters.substring(0,parameters.length()-caminho[caminho.length-1].length()));
		if(!Naoencontrou)
			for(int i=0;i<aux.getArquivos().size();i++)
			{
				if(	aux.getArquivos().get(i).getNome().equals(caminho[caminho.length-1].substring(0, caminho[caminho.length-1].length()-4)))
				{
					arqExist = true;
					result = "\n" +aux.getArquivos().get(i).getConteudo();
				}
			}
		else result = "Caminho nao encontrado";
		if (!arqExist) {
			result = "Arquivo nao encontrado";
		}
		return result;
	}    

	public String batch(String parameters) {
		//variavel result deverah conter o que vai ser impresso na tela apos comando do usuário
		String result = "";
		System.out.println("Chamada de Sistema: batch");
		System.out.println("\tParametros: " + parameters);
		hd.imprimeOcupados();
		//inicio da implementacao do aluno

		//fim da implementacao do aluno

		return result;
	}

	public String dump(String parameters) {
		//variavel result deverah conter o que vai ser impresso na tela apos comando do usuário
		String result = "";
		System.out.println("Chamada de Sistema: dump");
		System.out.println("\tParametros: " + parameters);

		//inicio da implementacao do aluno
		System.out.println();
		File arq = new File("/home/gabriel/Documentos/dump.txt");
		try {
			if(!arq.exists())
				arq.delete();
			arq.createNewFile();
			//			{
			System.out.println("Vai gravar");
			arq.createNewFile();
			BufferedWriter bufW = new BufferedWriter(new FileWriter(arq));
			

				bufW.write(recursaoDumpArq( raiz).replaceAll("//", "/"));
				bufW.newLine();
			
			bufW.close();
			//			}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		//fim da implementacao do aluno

		return result;
	}

	public String info() {
		//variavel result deverah conter o que vai ser impresso na tela apos comando do usuário
		String result = "";
		System.out.println("Chamada de Sistema: info");
		System.out.println("\tParametros: sem parametros" );

		//nome do aluno
		String name = "Gabriel de Carvalho Baldão";
		//numero de matricula
		String registration = "14152000441";
		//versao do sistema de arquivos
		String version = "0.1";

		result += "Nome do Aluno:        " + name;
		result += "\nMatricula do Aluno:   " + registration;
		result += "\nVersao do Kernel:     " + version;

		return result;
	}
	/*************************************************************************************************************/
	public void setAtual(Diretorio atual)
	{
		this.atual = atual;
		System.err.println("\t\t\tAtual\t"+this.atual.getNome());

	}
	public Diretorio getAtual() {
		return this.atual;
	}
	
	public Diretorio buscaParametro(String parametro)
	{

		String[] aux = parametro.split("/");

		if(parametro.equals(""))
			return getAtual();
		if(parametro.charAt(0)== '/')
		{
			//System.out.println("\n\n\t\tRAIZ");
			return buscaDiretorio(raiz, aux, 1);
		}
		else {
			return buscaDiretorio(getAtual(), aux, 0);
		}

	}
	static boolean Naoencontrou = false;
	public Diretorio buscaDiretorio(Diretorio atual,String[] parametro, int j)
	{


		//		if(parametro[j].equals("..") || parametro[j].equals(".") )
		if(parametro.length>j) {
			//System.out.println(parametro[j]);
			if(parametro[j].compareTo("..") == 0)
			{
				return buscaDiretorio(atual.getPai(), parametro, ++j);
			}
			if(parametro[j].compareTo(".")==0)
				return buscaDiretorio(atual, parametro, ++j);
			for (int i = 0; i < atual.getFilhos().size(); i++) {
				if(atual.getFilhos().get(i).getNome().compareTo(parametro[j])==0)
				{
					if(j == parametro.length-1)
					{
						return atual.getFilhos().get(i);
					}
					else
					{
						return buscaDiretorio(atual.getFilhos().get(i), parametro, ++j);
					}
				}

			}
		}
		Naoencontrou = true;
		//return atual;
		return atual;
	}
	public boolean buscaFilhos(String parametro,String novo)
	{
		Diretorio aux = buscaParametro(parametro);
		for (int i = 0; i <aux.getFilhos().size(); i++) {
			if(aux.getFilhos().get(i).getNome().equals(novo))
				return true;

		}
		return false;
	}
	String caminhoTerminal;
	public String caminho(Diretorio diretorio)
	{
		ArrayList<String> diretorios = new ArrayList<String>();
		caminhoRecursao(diretorios, diretorio);
		String result = "";
		for(int i=diretorios.size();i<0;i--)
			result = result +"/"+ diretorios.get(i);
		return result;
	}
	public String caminhoRecursao(ArrayList<String> diretorios, Diretorio diretorio)
	{
		if(diretorio.getNome().equals("raiz"))
		{

			return caminhoTerminal;
		}
		else {
			diretorios.add(diretorio.getNome());
			return caminhoRecursao(diretorios, diretorio.getPai());
		}
	}
	public String monthToString(int mes) {
		switch (mes)
		{
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
	public  String current(Diretorio dir)
	{
		if(!dir.getNome().equals("raiz"))
		{
			return( current(dir.getPai()) +"/"+ dir.getNome());
		}
		return "";
	}
	public boolean containsDiretorio(Diretorio dir, String nome)
	{
		for (int i = 0; i < dir.getFilhos().size(); i++) {
			if(dir.getFilhos().get(i).getNome().equals(nome))
				return true;
		}
		return false;
	}
	public boolean containsArq(Diretorio dir, String nome)
	{
		for (int i = 0; i < dir.getArquivos().size(); i++) {
			if(dir.getArquivos().get(i).getNome().equals(nome))
				return true;
		}
		return false;
	}
	public boolean setHorario(Diretorio dir, String data)
	{
		if(dir.getNome().equals("raiz"))
		{dir.setData(data);
		return true;
		}
		else {
			dir.setData(data);
			return setHorario(dir.getPai(), data);
		}
	}
	public String recursaoDumpDir(Diretorio dir)
	{
		if(dir.getFilhos().isEmpty()) {
			return "";
		}
		else {

			for(int i = 0; i < dir.getFilhos().size();i++)
			{
				return( "mkdir "+ current(dir.getFilhos().get(i))+"\n"+ recursaoDumpDir(dir.getFilhos().get(i)));
			}
			return "";
		}

	}
	/**********************************************************************/
	public String recursaoDumpArq( Diretorio dir)
	{
		String aux = "";
		//System.out.println("nem aqui");
		for (int i = 0; i < dir.getArquivos().size(); i++) 
			aux = aux + ("createfile /"+current(dir)+"/"+dir.getArquivos().get(i).getNome()+"."
					+dir.getArquivos().get(i).getTipo()+" "+dir.getArquivos().get(i).getConteudo())+"\n";


		String filhos = aux;
		for(int i = 0; i < dir.getFilhos().size();i++)
			filhos =( filhos +("mkdir /"+ current(dir.getFilhos().get(i))+"\n"+ recursaoDumpArq(dir.getFilhos().get(i))));

		return filhos;
	}
	public Diretorio copy(Diretorio dir)
	{
		Diretorio novo = new Diretorio(dir.getPai(), dir.getNome());
		novo.setArquivos(dir.getArquivos());
		novo.setData(dir.getData());
		novo.setFilhos(dir.getFilhos());
		novo.setPermissao(dir.getPermissao());
		return novo;
	}
	public Arquivo copy(Arquivo arq)
	{
		Arquivo novo = new Arquivo(arq.getNome(), arq.getTipo(), arq.getConteudo(), arq.getPai(), arq.getData());
		novo.setPermissao(arq.getPermissao());
		novo.setPonteiro(String.valueOf(hd.buscaEsetaPosicao()));
		return novo;
	}
	public Diretorio novoDiretorio(String parameters)
	{
		System.out.println(parameters);
		Date data = new Date();
		String[] diretorios = parameters.split("/");
		Naoencontrou = false;
		Diretorio novoDiretorio = new Diretorio(buscaParametro(parameters.substring(0,parameters.length()-diretorios[diretorios.length-1].length())),diretorios[diretorios.length-1]);
		novoDiretorio.setPonteiro(String.valueOf(hd.buscaEsetaPosicao()));
		//System.out.println("bit "+novoDiretorio.getPonteiro());
		setHora(novoDiretorio);
		hd.setBloco(Abstracao.diretorioToBinario(novoDiretorio, hd), Integer.parseInt(novoDiretorio.getPonteiro()));
		return novoDiretorio;
	}
	public Diretorio setRaiz() 
	{
		Date data = new Date();
		Diretorio novaRaiz = new Diretorio(null,"raiz");
		novaRaiz.setPermissao("777");
		
		novaRaiz.setPonteiro(String.valueOf(hd.buscaEsetaPosicao()));
		
		setHora(novaRaiz);
		hd.setBloco(Abstracao.diretorioToBinario(novaRaiz, hd), Integer.parseInt(novaRaiz.getPonteiro()));
		System.out.println(hd.blocoToString(0));
		return novaRaiz;
		
	}
	public void setHora(Diretorio dir)
	{
		Date data = new Date();
		dir.setData(String.valueOf(monthToString(data.getMonth()+1))+" "+String.valueOf(data.getDate())+" "+String.valueOf(data.getHours())+":"+String.valueOf(data.getMinutes()));
		//	String endereco = parameters.substring(0,parameters.length()-diretorios[diretorios.length-1].length());
		dir.setMes(data.getMonth()+1);
		dir.setDia(data.getDate());
		dir.setHora(data.getHours());
		dir.setMin(data.getMinutes());
	}
	public void setHora(Arquivo arq)
	{
		Date data = new Date();
		arq.setData(String.valueOf(monthToString(data.getMonth()+1))+" "+String.valueOf(data.getDate())+" "+String.valueOf(data.getHours())+":"+String.valueOf(data.getMinutes()));
		//	String endereco = parameters.substring(0,parameters.length()-diretorios[diretorios.length-1].length());
		arq.setMes(data.getMonth()+1);
		arq.setDia(data.getDate());
		arq.setHora(data.getHours());
		arq.setMin(data.getMinutes());
	}
	public Arquivo novoArquivo(String parameters)
	{
		System.out.println(parameters);
		String[] parametro = parameters.split(" ");
		String[] caminho = parametro[0].split("/");
		Date data = new Date();

		String nome = caminho[caminho.length-1].substring(0, caminho[caminho.length-1].length()-4);
		String tipo = caminho[caminho.length-1].substring( caminho[caminho.length-1].length()-3, caminho[caminho.length-1].length());
		String conteudo = parametro[parametro.length-1];
		String caminhoArquivo = parametro[0].substring(0, parametro[0].length()-(nome.length()+1+tipo.length()));
		System.out.println("Nome "+nome);
		System.out.println("Tipo "+tipo);
		System.out.println("Conteudo "+conteudo);
		System.out.println("Caminho "+ caminhoArquivo);
		Naoencontrou = false;
	
		Arquivo novoArquivo = new Arquivo(nome, tipo, conteudo, buscaParametro(caminhoArquivo), String.valueOf(monthToString(data.getMonth()+1))+" "+String.valueOf(data.getDate())+" "+String.valueOf(data.getHours())+":"+String.valueOf(data.getMinutes()));
		setHora(novoArquivo);
		novoArquivo.setPermissao("777");
		setHora(novoArquivo);
			hd.imprimeOcupados();
		return novoArquivo;
	}

}



