package hardware;

import java.beans.FeatureDescriptor;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;

import arvore.Arquivo;
import arvore.Diretorio;
import binary.Binario;
import hardware.Configuracao.*;

public class Abstracao {
	private static String diretorioTipo = "00";
	private static String diretorioContinuacao = "01";
	private static String arquivoTipo = "10";
	private static String arquivoContinuacao = "11";

	// public Diretorio binarioToDiretorio() {
	//
	// }

	public static String diretorioToBinario(Diretorio dir, HardDisk hd) {
		StringBuilder str = new StringBuilder();
		dir.imprimeDir();
		// System.out.println("\nCriou\ntype " + diretorioTipo.length() + "\n" +
		// "endereco "
		// + Binario.intToBinaryString((dir.getPonteiro()),
		// Configuracao.getEndereco()).length() + "\n" + "nome "
		// + completaTamanho(Binario.stringToBinary(dir.getNome()),
		// Configuracao.getNome()).length() + "\n"
		// + "pai " + Binario.intToBinaryString((dir.getPonteiro()),
		// Configuracao.getEndereco()).length() + "\n"
		// + "mes" + Binario.intToBinaryString(dir.getMes(),
		// Configuracao.getMes()).length() + "\n" + "dia "
		// + Binario.intToBinaryString(dir.getDia(), Configuracao.getDia()).length() +
		// "\n" + "hora "
		// + Binario.intToBinaryString(dir.getHora(), Configuracao.getHora()).length() +
		// "\n" + "min "
		// + Binario.intToBinaryString(dir.getMin(), Configuracao.getMinuto()).length()
		// + "\n" + "permisao "
		// + Binario.intToBinaryString(Integer.parseInt(dir.getPermissao().charAt(2) +
		// ""), 3) + "\n" + "filhos "
		// + completaTamanho("", Configuracao.getConteudoBlocoA()) + "\n");
		// System.err.print(str.toString().length() + " -- ");

		str.append(diretorioTipo);

		// System.err.print(str.toString().length() + " -- ");
		str.append(completaTamanho(Binario.intToBinaryString((dir.getPonteiro()), Configuracao.getEndereco()),
				Configuracao.getEndereco()));

		// System.out.println("NOME BIN
		// "+completaTamanho(Binario.stringToBinary(dir.getNome()),8*12)+"\n\n\t\t
		// "+nomeBinarioToNome(completaTamanho(Binario.stringToBinary(dir.getNome()),8*12)));
		// System.err.print(str.toString().length() + " -- ");
		// System.out.println("*" +
		// (completaTamanho(Binario.stringToBinary(dir.getNome()), 8 * 12)) + "*\n"
		// + Binario.stringToBinary(dir.getNome()));

		str.append(completaTamanho(Binario.stringToBinary(dir.getNome()), Configuracao.getNome()));
		// System.err.print(str.toString().length() + " -- ");

		str.append(Binario.intToBinaryString((dir.getPai().getPonteiro()), Configuracao.getEndereco()));

		// System.err.print(str.toString().length() + " -- ");
		str.append(Binario.intToBinaryString(dir.getMes(), Configuracao.getMes()));
		// System.err.print(str.toString().length() + " -- ");
		str.append(Binario.intToBinaryString(dir.getDia(), Configuracao.getDia()));
		// System.err.print(str.toString().length() + " -- ");
		str.append(Binario.intToBinaryString(dir.getHora(), Configuracao.getHora()));
		// System.err.print(str.toString().length() + " -- ");
		str.append(Binario.intToBinaryString(dir.getMin(), Configuracao.getMinuto()));
		// System.err.print(str.toString().length() + " -- ");
		str.append(
				Binario.intToBinaryString(Integer.parseInt(dir.getPermissao() + ""), Configuracao.getPermissao() / 3));

		str.append(
				Binario.intToBinaryString(Integer.parseInt(dir.getPermissao() + ""), Configuracao.getPermissao() / 3));

		str.append(
				Binario.intToBinaryString(Integer.parseInt(dir.getPermissao() + ""), Configuracao.getPermissao() / 3));
		// System.err.print(str.toString().length() + " -- ");
		str.append(completaTamanho("", Configuracao.getConteudoBlocoA()));
		// System.err.print(str.toString().length() + " -- ");
		str.append(completaTamanho("", Configuracao.getResto()));
		// System.err.print(str.toString().length() + " -- ");
		// setFilho(hd, dir, str.toString());
		return str.toString();
	}

	// public static void setFilho(HardDisk hd, Diretorio filho, String str) {
	// if (hd.getBitDaPosicao((filho.getPai().getPonteiro()))) {
	// StringBuilder blocoPai = new StringBuilder();
	// blocoPai.append(hd.blocoToString((filho.getPai().getPonteiro())));
	// System.err.println("\npaizao"+filho.getPai().getPonteiro()+"\n\n"+blocoPai);
	// if (blocoPai.toString().charAt(511) != '1') {
	// for (int i = 0; i < 4; i++) {
	// int cont = 0;
	// for (int j = 0; j < 64; j++) {
	// if (getConteudoBloco(blocoPai.toString()).charAt(j) == '1')
	// cont++;
	// }
	// if (cont == 0) {
	// String blocoPaiNovo = alteraFilho(bloco, novoFilho, hd)(blocoPai.toString(),
	// str, i);
	// System.out.println("blocoPaiNovo "+blocoPaiNovo.length());
	// hd.setBloco(blocoPaiNovo, (filho.getPai().getPonteiro()));
	//
	// }
	//
	// }
	// }
	//
	// }
	// }

	public static ArrayList<String> continuacaoDiretorio(Diretorio dir, int j, HardDisk hd) {
		ArrayList<String> res = new ArrayList<>();

		if (hd.buscaPosicaoLivre() > 0) {
			StringBuilder str = new StringBuilder();
			str.append(diretorioContinuacao);
			str.append(Binario.intToBinaryString(hd.buscaPosicaoLivre()));
			str.append((dir.getPonteiro()));

			for (int i = j; i < dir.getFilhos().size(); i++)

			{
				if (i < j + 4)
					str.append(dir.getFilhos().get(i).getPonteiro());

				else {
					str.append(completaTamanho("", 512 - str.toString().length()));
					str.append(Binario.intToBinaryString(hd.buscaPosicaoLivre()));
					res.add(str.toString());
					continuacaoDiretorio(dir, j, hd);

				}
			}
		} else {
			return null; // HD LOTADO
		}
		return res;
	}

	public static String arquivoToBinario(Arquivo arq, HardDisk hd) {
		ArrayList<String> res = new ArrayList<>();
		StringBuilder str = new StringBuilder();
		// System.err.print(str.toString().length() + " -- ");
		str.append(arquivoTipo);
		// System.err.print(str.toString().length() + " -- ");
		str.append(Binario.intToBinaryString(arq.getPonteiro(), Configuracao.getEndereco()));
		// System.err.print(str.toString().length() + " -- ");
		str.append(completaTamanho(Binario.stringToBinary(arq.getNome()), Configuracao.getNome()));
		// System.err.print(str.toString().length() + " -- ");
		
		str.append(Binario.intToBinaryString(arq.getPai().getPonteiro(), Configuracao.getEndereco()));
		// System.err.print(str.toString().length() + " -- mes " + arq.getMes());
		str.append(Binario.intToBinaryString(arq.getMes(), Configuracao.getMes()));
		// System.err.print(str.toString().length() + " -- ");
		str.append(Binario.intToBinaryString(arq.getDia(), Configuracao.getDia()));
		// System.err.print(str.toString().length() + " -- ");
		str.append(Binario.intToBinaryString(arq.getHora(), Configuracao.getHora()));
		// System.err.print(str.toString().length() + " -- ");
		str.append(Binario.intToBinaryString(arq.getMin(), Configuracao.getMinuto()));
		// System.err.print(str.toString().length() + " -- ");
		// PERMISSAO
		str.append(Binario.intToBinaryString(Integer.parseInt(arq.getPermissao().charAt(0) + ""),
				Configuracao.getPermissao() / 3));
		str.append(Binario.intToBinaryString(Integer.parseInt(arq.getPermissao().charAt(1) + ""),
				Configuracao.getPermissao() / 3));
		str.append(Binario.intToBinaryString(Integer.parseInt(arq.getPermissao().charAt(2) + ""),
				Configuracao.getPermissao() / 3));

		// System.err.print(str.toString().length() + " -- ");
		// PERMISSAO
		if (arq.getConteudo().length() > 0) {
			if (Binario.stringToBinary(arq.getConteudo()).length() <= Configuracao.getConteudoBlocoA())
				str.append(
						completaTamanho(Binario.stringToBinary(arq.getConteudo()), Configuracao.getConteudoBlocoA()));
			// else
			// {
			// str.append(Binario.stringToBinary(arq.getConteudo()).substring(0,256));
			// res.add(str.toString());
			// continuacaoArquivo(arq, hd, res, 256);
			// }
		} else
			str.append(completaTamanho("", Configuracao.getConteudoBlocoA()));
		str.append(completaTamanho("", Configuracao.getResto()));
		return str.toString();
	}

	private static String continuacaoArquivo(Arquivo arq, HardDisk hd, ArrayList<String> out, int i) {
		StringBuilder str = new StringBuilder();
		str.append(arquivoContinuacao);
		str.append(Binario.intToBinaryString((arq.getPonteiro())));
		if (Binario.stringToBinary(arq.getConteudo()).length() < i + 320) {
			str.append(Binario.stringToBinary(arq.getConteudo()));
			// str.append();
			return str.toString();
		} else {
			str.append(Binario.stringToBinary(arq.getConteudo()).substring(i, i + 320));
			out.add(str.toString());
			continuacaoArquivo(arq, hd, out, i + 320);

		}
		return "";

	}

	public static String completaTamanho(String str, int tam) {
		if (str.length() > tam)
			return str.substring(0, tam);
		while (str.length() < tam) {
			str = "0" + str;
		}
		return str;
	}

	public boolean blocoIsDiretorio(boolean[] bloco) {
		if (bloco[0] == false)
			return true;
		else
			return false;

	}

	public static Diretorio blocoToDir(HardDisk hd, String teste) {
		teste = teste.replace("[", "").replace("]", "");
		Diretorio pai = null;
		// String binario = Binario.booleanToString(bloco);
		// System.err.println("\nDesconstroi tam " + teste.length() + "\ntype " +
		// teste.substring(0, 2) + "\n"
		// + "endereco " + teste.substring(2, 66) + "\n" + "nome " + teste.substring(66,
		// 162) + "\n" + "pai "
		// + teste.substring(162, 226) + "\n" + "mes" + teste.substring(226, 230) + "\n"
		// + "dia "
		// + teste.substring(230, 235) + "\n" + "hora " + teste.substring(235, 240) +
		// "\n" + "min "
		// + teste.substring(240, 246) + "\n" + "permisao " + teste.substring(246, 255)
		// + "\n" + "filhos "
		// + teste.substring(255, 511) + "\n");
		// String binarioNome = nomeBinarioToNome(teste.substring(66, 162));
		String nome = Binario.binarioToString(getNomeBloco(teste));
		int ponteiro = Binario.binaryStringToInt(getEnderecoBloco(teste));
		// System.out.println("DIR = " + nome +"t"+teste.substring(66, 162));
		ArrayList<Diretorio> listaFilhos = new ArrayList<>();
		ArrayList<Arquivo> arquivos = new ArrayList<>();

		int paiEndereco = Binario.binaryStringToInt(getPaiBloco(teste));
		if (Binario.binaryStringToInt(getPaiBloco(teste)) != 0) {
			pai = blocoToDir(hd, hd.blocoToString(Binario.binaryStringToInt(getPaiBloco(teste))));
			pai.setPonteiro(Binario.binaryStringToInt(getPaiBloco(teste)));
			pai.imprimeDir();
		}

		String permissao = getPermissaoBloco(teste);

		// S String ponteiro = Binario.binarioToString(binario.substring(2,66));
		int mes, dia, hora, min;
		mes = Integer.parseInt(getMesBloco(teste));
		dia = Integer.parseInt(getDiaBloco(teste));
		hora = Integer.parseInt(getHoraBloco(teste));
		min = Integer.parseInt(getMinutoBloco(teste));
		String data = mes + " " + dia + " " + hora + ":" + min;

		Diretorio dir = new Diretorio(pai, nome);
		dir.getPai().setPonteiro(paiEndereco);
		dir.setPonteiro((ponteiro));
		dir.setData(data);
		dir.setMes(mes);
		dir.setDia(dia);
		dir.setHora(hora);
		dir.setMin(min);
		dir.setPermissao(permissao);
		// ArrayList<Diretorio> listaFilhos = filhos(getConteudoBloco(teste), hd);
		if (listaFilhos != null)
			dir.setFilhos(listaFilhos);
		if(arquivos!=null)
			dir.setArquivos(arquivos);
		dir.imprime();

		return dir;

	}

	public static ArrayList<Diretorio> converteFilhos(String filhos, HardDisk hd) {
		ArrayList<Diretorio> res = new ArrayList<>();
		System.out.println("ENTROU CONVER FILHOS");
		if (!filhos.contains(completaTamanho("", Configuracao.getConteudoBlocoA())))
			for (int i = 0; i < filhos.length(); i = i + Configuracao.getEndereco()) {
				String filho = filhos.substring(i, i + Configuracao.getEndereco());
				int posicaoFilho = Binario.binaryStringToInt(filho);
				if (hd.blocoToString(posicaoFilho).charAt(0) == '0') {
					System.out.println("POSICAO "+posicaoFilho);
					Diretorio dir = blocoToDir(hd, hd.blocoToString(posicaoFilho));
					res.add(dir);
				}
			}
		return res;

	}

	public static ArrayList<Integer> converteArquivos(String filhos, HardDisk hd) {
		ArrayList<Integer> res = new ArrayList<>();
		if (!filhos.contains(completaTamanho("", Configuracao.getConteudoBlocoA())))
			for (int i = 0; i < filhos.length(); i = i + Configuracao.getEndereco()) {
				String filho = filhos.substring(i, i + Configuracao.getEndereco());
				int posicaoFilho = Binario.binaryStringToInt(filho);
				if (hd.blocoToString(posicaoFilho).charAt(0) == '1') {
					
					Arquivo arq = blocoToArq(hd.blocoToString(posicaoFilho), hd);
					res.add(posicaoFilho);
				}
			}
		return res;

	}

	public static Arquivo blocoToArq(String teste, HardDisk hd) {
		teste = teste.replace("[", "").replace("]", "");
		Diretorio pai;
		// String binario = Binario.booleanToString(bloco);
		// System.err.println("\nDesconstroi tam " + teste.length() + "\ntype " +
		// teste.substring(0, 2) + "\n"
		// + "endereco " + teste.substring(2, 66) + "\n" + "nome " + teste.substring(66,
		// 162) + "\n" + "pai "
		// + teste.substring(162, 226) + "\n" + "mes" + teste.substring(226, 230) + "\n"
		// + "dia "
		// + teste.substring(230, 235) + "\n" + "hora " + teste.substring(235, 240) +
		// "\n" + "min "
		// + teste.substring(240, 246) + "\n" + "permisao " + teste.substring(246, 255)
		// + "\n" + "filhos "
		// + teste.substring(255, 511) + "\n");
		// String binarioNome = nomeBinarioToNome(teste.substring(66, 162));
		String nome = Binario.binarioToString(getNomeBloco(teste));
		// System.out.println("DIR = " + nome +"t"+teste.substring(66, 162));

		if (Integer.parseInt(getPaiBloco(teste)) != 0) {
			pai = blocoToDir(hd, hd.blocoToString(Binario.binaryStringToInt(getPaiBloco(teste))));
		} else
			pai = null;

		String permissao = getPermissaoBloco(teste);

		// S String ponteiro = Binario.binarioToString(binario.substring(2,66));
		int mes, dia, hora, min;
		mes = Integer.parseInt(getMesBloco(teste));
		dia = Integer.parseInt(getDiaBloco(teste));
		hora = Integer.parseInt(getHoraBloco(teste));
		min = Integer.parseInt(getMinutoBloco(teste));
		String data = mes + " " + dia + " " + hora + ":" + min;

		Arquivo arq = new Arquivo(nome, "txt", "", pai, data);
		arq.setData(data);
		arq.setMes(mes);
		arq.setDia(dia);
		arq.setHora(hora);
		arq.setMin(min);
		arq.setPermissao(permissao);
		arq.setConteudo(Binario.binarioToString(getConteudoBloco(teste)));
		return arq;

	}

	public static boolean contemFilho(String bloco, String novoArquivo, HardDisk hd) {
		ArrayList<Integer> filhos = getFilhos(bloco, hd);
		for (int i = 0; i < filhos.size(); i++) {
			if (Binario.binarioToString(getNomeBloco(bloco)).equals(novoArquivo))
				return true;
		}
		return false;
	}

	public static String nomeBinarioToNome(String binario) {
		String res = new String();
		for (int i = 0; i < binario.length(); i = i + 8) {
			res = res + "" + binario.substring(i, i + 8) + " ";
		}
		return res;
	}

	private static String getTipoBloco(String bloco) {
		return bloco.substring(0, 2);
	}

	public static String getEnderecoBloco(String bloco) {
		return bloco.substring(2, 23);
	}

	public static String alteraPai(String binario, int novoPai) {
		return binario.substring(0, 119) + "" + completaTamanho(Binario.intToBinaryString(novoPai,Configuracao.getEndereco()), Configuracao.getEndereco())
				+ "" + binario.substring(140, 512);
	}
	public static String alteraPonteiro(String binario, int novoEndereco) {
		return binario.substring(0, 2) + "" + completaTamanho(Binario.intToBinaryString(novoEndereco,Configuracao.getEndereco()), Configuracao.getEndereco())
				+ "" + binario.substring(23, 512);
	}
	public static String alteraFilho(String bloco, String novoFilho, HardDisk hd) {
		String filhos = getConteudoBloco(bloco);
		for (int i = 0; i < filhos.length(); i = i + Configuracao.getEndereco()) {

			if (filhos.substring(i, i + Configuracao.getEndereco())
					.contains(completaTamanho("", Configuracao.getEndereco()))) {
				filhos = filhos.substring(0, i) + novoFilho
						+ filhos.substring(i + Configuracao.getEndereco(), filhos.length());
				return setConteudo(bloco, filhos);
			}
		}
		return bloco;
	}

	public static String zeraFilho(String bloco, String filho, HardDisk hd) {
		String filhos = getConteudoBloco(bloco);
		for (int i = 0; i < filhos.length(); i = i + Configuracao.getEndereco()) {
			if (filhos.substring(i, i + Configuracao.getEndereco()).equals(filho)) {
				filhos = filhos.substring(0, i) + completaTamanho("", Configuracao.getEndereco())
						+ filhos.substring(i + Configuracao.getEndereco(), filhos.length());
				i = filhos.length();
			}

		}
		return filhos;
	}

	public static ArrayList<Diretorio> apagaFilho(ArrayList<Diretorio> filhos, String filho, HardDisk hd) {
		int pos;
		for (int i = 0; i < filhos.size(); i++) {
			if (filhos.get(i).getNome().equals(filho)) {
				pos = filhos.remove(i).getPonteiro();
				i = filhos.size();
				hd.setBloco(completaTamanho("", Configuracao.getTamBloco()), pos);
				hd.setBitDaPosicao(false, pos);
			}
		}
		return filhos;
	}

	public static ArrayList<Arquivo> apagaArq(ArrayList<Arquivo> filhos, String filho, HardDisk hd) {
		int pos;
		for (int i = 0; i < filhos.size(); i++) {
			if (filhos.get(i).getNome().equals(filho)) {
				pos = filhos.remove(i).getPonteiro();
				i = filhos.size();
				hd.setBloco(completaTamanho("", Configuracao.getTamBloco()), pos);
				hd.setBitDaPosicao(false, pos);
			}
		}

		return filhos;
	}

	public static String setConteudo(String bloco, String conteudo) {
		return bloco.substring(0, 169) + completaTamanho(conteudo,Configuracao.getConteudoBlocoA()) + bloco.substring(484, 512);
	}

	public static String getNomeBloco(String bloco) {
		return bloco.substring(23, 119);
	}

	public static String getPaiBloco(String bloco) {
		return bloco.substring(119, 140);
	}

	public static String getMesBloco(String bloco) {
		return bloco.substring(140, 144);
	}

	public static String getDiaBloco(String bloco) {
		return bloco.substring(144, 149);
	}

	public static String getHoraBloco(String bloco) {
		return bloco.substring(149, 154);
	}

	public static String getMinutoBloco(String bloco) {
		return bloco.substring(154, 160);
	}

	public static String getPermissaoBloco(String bloco) {
		return bloco.substring(160, 169);
	}

	public static String getConteudoBloco(String bloco) {
		return bloco.substring(169, 484);
	}

	private static String getRestoBloco(String bloco) {
		return bloco.substring(484, 512);
	}

	private static ArrayList<Diretorio> filhos(String filhosStr, HardDisk hd) {
		ArrayList<Diretorio> res = new ArrayList<>();
		if (!filhosStr.contains(completaTamanho("0", filhosStr.length() - 5)))
			for (int i = 0; i < filhosStr.length(); i = i
					+ (Configuracao.getConteudoBlocoA() / Configuracao.getEndereco())) {
				{
					if (!filhosStr.substring(i, i + Configuracao.getEndereco())
							.contains(completaTamanho("", Configuracao.getEndereco()))) {
						Diretorio dir = blocoToDir(hd, hd.blocoToString(
								Integer.parseInt(filhosStr.substring(i, i + Configuracao.getEndereco()))));
						res.add(dir);
					}
				}

				return res;
			}
		return null;

	}

	// public static String setFilho(String filho, HardDisk hd, int bloco) {
	// String blocoStr = hd.blocoToString(bloco);
	// String filhosBloco = getConteudoBloco(blocoStr);
	// for (int i = 0; i < filhosBloco.length(); i++) {
	// if (filhosBloco.substring(i, i + 64).contains(completaTamanho("", 64))) {
	// filhosBloco = filhosBloco.substring(0, i) + filho + filhosBloco.substring(i +
	// 64, filhosBloco.length());
	//
	// i = filhosBloco.length();
	// }
	// }
	// blocoStr = blocoStr.substring(0, 255) + filhosBloco +
	// blocoStr.charAt(blocoStr.length() - 1);
	// return blocoStr;
	//
	// }



	public static ArrayList<Integer> getFilhos(String bloco, HardDisk hd) {
		ArrayList<Integer> filhos = new ArrayList<>();
		String nomes = new String();
		bloco = getConteudoBloco(bloco);
		System.out.println("Abstracao filhos \n" + bloco);
		for (int i = 0; i < bloco.length(); i = i + Configuracao.getEndereco()) {
			if (!bloco.substring(i, i + Configuracao.getEndereco())
					.contains(completaTamanho("", Configuracao.getEndereco()))) {
				int endereco = Binario.binaryStringToInt(bloco.substring(i, i + Configuracao.getEndereco()));
				System.out.println("Endereco do filho\t " + endereco);
				if (hd.getBitDaPosicao(endereco)) {

					nomes = nomes + "\n" + Binario.binarioToString(getNomeBloco(hd.blocoToString(endereco)));
					System.out.println("Endereco filho" + endereco);
					if (!contem(filhos, endereco))
						filhos.add(endereco);
				}
				System.out.println("Nome " + nomes);

			}
		}
		return filhos;
	}

	private static boolean contem(ArrayList<Integer> lista, int valor) {
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i) == valor)
				return true;
		}
		return false;
	}

}