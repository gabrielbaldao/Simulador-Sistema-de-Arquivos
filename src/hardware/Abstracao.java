package hardware;

import java.beans.FeatureDescriptor;
import java.util.ArrayList;

import arvore.Arquivo;
import arvore.Diretorio;
import binary.Binario;

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
		// System.out.println("\nCriou\ntype " + diretorioTipo.length() + "\n" +
		// "endereco "
		// + Binario.intToBinaryString(Integer.parseInt(dir.getPonteiro()), 64) + "\n" +
		// "nome "
		// + completaTamanho(Binario.stringToBinary(dir.getNome()), 8 * 12) + "\n" +
		// "pai "
		// + Binario.intToBinaryString(Integer.parseInt(dir.getPonteiro()), 64) + "\n" +
		// "mes"
		// + Binario.intToBinaryString(dir.getMes(), 4) + "\n" + "dia "
		// + Binario.intToBinaryString(dir.getDia(), 5) + "\n" + "hora "
		// + Binario.intToBinaryString(dir.getHora(), 5) + "\n" + "min "
		// + Binario.intToBinaryString(dir.getMin(), 6) + "\n" + "permisao "
		// + Binario.intToBinaryString(Integer.parseInt(dir.getPermissao().charAt(2) +
		// ""), 3) + "\n" + "filhos "
		// + completaTamanho("", 64 * 4) + "\n");
		// System.err.print(str.toString().length() + " -- ");

		str.append(diretorioTipo);

		// System.err.print(str.toString().length() + " -- ");
		str.append(completaTamanho(Binario.intToBinaryString(Integer.parseInt(dir.getPonteiro()), 64), 64));

		// System.out.println("NOME BIN
		// "+completaTamanho(Binario.stringToBinary(dir.getNome()),8*12)+"\n\n\t\t
		// "+nomeBinarioToNome(completaTamanho(Binario.stringToBinary(dir.getNome()),8*12)));
		// System.err.print(str.toString().length() + " -- ");
		// System.out.println("*" +
		// (completaTamanho(Binario.stringToBinary(dir.getNome()), 8 * 12)) + "*\n"
		// + Binario.stringToBinary(dir.getNome()));
		str.append(completaTamanho(Binario.stringToBinary(dir.getNome()), 8 * 12));
		System.out.println("para binario \n "+completaTamanho(Binario.stringToBinary(dir.getNome()), 8 * 12)+"\nome ^");
		// System.err.print(str.toString().length() + " -- ");

		if (dir.getPai() == null)
			str.append(Binario.intToBinaryString(Integer.parseInt(dir.getPonteiro()), 64));
		else
			str.append(Binario.intToBinaryString(Integer.parseInt(dir.getPai().getPonteiro()), 64));
		// System.err.print(str.toString().length() + " -- ");
		str.append(Binario.intToBinaryString(dir.getMes(), 4));
		// System.err.print(str.toString().length() + " -- ");
		str.append(Binario.intToBinaryString(dir.getDia(), 5));
		// System.err.print(str.toString().length() + " -- ");
		str.append(Binario.intToBinaryString(dir.getHora(), 5));
		// System.err.print(str.toString().length() + " -- ");
		str.append(Binario.intToBinaryString(dir.getMin(), 6));
		// System.err.print(str.toString().length() + " -- ");
		str.append(Binario.intToBinaryString(Integer.parseInt(dir.getPermissao().charAt(0) + ""), 3));
		// System.err.print(str.toString().length() + " -- ");
		str.append(Binario.intToBinaryString(Integer.parseInt(dir.getPermissao().charAt(1) + ""), 3));
		// System.err.print(str.toString().length() + " -- ");
		str.append(Binario.intToBinaryString(Integer.parseInt(dir.getPermissao().charAt(2) + ""), 3));
		// System.err.print(str.toString().length() + " -- ");
		str.append(completaTamanho("", 64 * 4));
		// System.err.print(str.toString().length() + " -- ");
		str.append("0");
		// System.err.print(str.toString().length() + " -- ");
		//setFilho(hd, dir, str.toString());
		return str.toString();
	}

	public static void setFilho(HardDisk hd, Diretorio filho, String str) {
		if (hd.getBitDaPosicao(Integer.parseInt(filho.getPai().getPonteiro()))) {
			StringBuilder blocoPai = new StringBuilder();
			blocoPai.append(hd.blocoToString(Integer.parseInt(filho.getPai().getPonteiro())));
			System.err.println("\npaizao"+filho.getPai().getPonteiro()+"\n\n"+blocoPai);
			if (blocoPai.toString().charAt(511) != '1') {
				for (int i = 0; i < 4; i++) {
					int cont = 0;
					for (int j = 0; j < 64; j++) {
						if (getConteudoBloco(blocoPai.toString()).charAt(j) == '1')
							cont++;
					}
					if (cont == 0) {
						String blocoPaiNovo = setFilhoBloco(blocoPai.toString(), str, i);
						System.out.println("blocoPaiNovo "+blocoPaiNovo.length());
						hd.setBloco(blocoPaiNovo, Integer.parseInt(filho.getPai().getPonteiro()));

					}

				}
			}

		}
	}

	public static ArrayList<String> continuacaoDiretorio(Diretorio dir, int j, HardDisk hd) {
		ArrayList<String> res = new ArrayList<>();

		if (hd.buscaPosicaoLivre() > 0) {
			StringBuilder str = new StringBuilder();
			str.append(diretorioContinuacao);
			str.append(Binario.intToBinaryString(hd.buscaPosicaoLivre()));
			str.append(Integer.parseInt(dir.getPonteiro()));

			for (int i = j; i < dir.getFilhos().size(); i++)

			{
				if (i < j + 4)
					str.append(Integer.parseInt(dir.getFilhos().get(i).getPonteiro()));

				else {
					str.append(completaTamanho("", 512 - str.toString().length()));
					str.append(Binario.intToBinaryString(hd.buscaPosicaoLivre()));
					System.out.println(str.toString().length() + " tamanho continuacao");
					res.add(str.toString());
					continuacaoDiretorio(dir, j, hd);

				}
			}
		} else {
			return null; // HD LOTADO
		}
		return res;
	}

	public static String arquivoToBinario(Arquivo arq,HardDisk hd) {
		ArrayList<String> res = new ArrayList<>();
		StringBuilder str = new StringBuilder();
		System.err.print(str.toString().length() + " -- ");
		str.append(arquivoTipo);
		System.err.print(str.toString().length() + " -- ");
		str.append(Binario.intToBinaryString(Integer.parseInt(arq.getPonteiro()),64));
		System.err.print(str.toString().length() + " -- ");
		str.append(completaTamanho(Binario.stringToBinary(arq.getNome()), 8 * 12));
		System.err.print(str.toString().length() + " -- ");
		str.append(Binario.intToBinaryString(Integer.parseInt(arq.getPai().getPonteiro()),64));
		System.err.print(str.toString().length() + " -- mes "+arq.getMes());
		str.append(Binario.intToBinaryString(arq.getMes(),4));
		System.err.print(str.toString().length() + " -- ");
		str.append(Binario.intToBinaryString(arq.getDia(),5));
		System.err.print(str.toString().length() + " -- ");
		str.append(Binario.intToBinaryString(arq.getHora(),5));
		System.err.print(str.toString().length() + " -- ");
		str.append(Binario.intToBinaryString(arq.getMin(),6));
		System.err.print(str.toString().length() + " -- ");
		// PERMISSAO
		str.append(Binario.intToBinaryString(Integer.parseInt(arq.getPermissao().charAt(0) + ""), 3));
		str.append(Binario.intToBinaryString(Integer.parseInt(arq.getPermissao().charAt(1) + ""), 3));
		str.append(Binario.intToBinaryString(Integer.parseInt(arq.getPermissao().charAt(2) + ""), 3));

		System.err.print(str.toString().length() + " -- ");
		// PERMISSAO
		if (arq.getConteudo().length() > 0) {
			if(Binario.stringToBinary(arq.getConteudo()).length()<=256)
			str.append(completaTamanho(Binario.stringToBinary(arq.getConteudo()),256));
//			else
//			{
//				str.append(Binario.stringToBinary(arq.getConteudo()).substring(0,256));
//				res.add(str.toString());
//				continuacaoArquivo(arq, hd, res, 256);
//			}
		} else
			str.append(completaTamanho("", 256));
		
			str.append("0");
			System.err.print("\n\nfinal "+str.toString().length() + " -- ");
		return str.toString();
	}
	private static String continuacaoArquivo(Arquivo arq, HardDisk hd, ArrayList<String> out, int i)
	{
		StringBuilder str = new StringBuilder();
		str.append(arquivoContinuacao);
		str.append(Binario.intToBinaryString(Integer.parseInt(arq.getPonteiro())));
		if(Binario.stringToBinary(arq.getConteudo()).length()<i+320)
		{
			str.append(Binario.stringToBinary(arq.getConteudo()));
//			str.append();
			return str.toString();
		}
		else {
			str.append(Binario.stringToBinary(arq.getConteudo()).substring(i,i+320));
			out.add(str.toString());
			continuacaoArquivo(arq, hd, out, i+320);

		}
		return "";
		
		
	}
	private static String completaTamanho(String str, int tam) {
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
		Diretorio pai;
		// String binario = Binario.booleanToString(bloco);
//		System.err.println("\nDesconstroi tam " + teste.length() + "\ntype " + teste.substring(0, 2) + "\n"
//				+ "endereco " + teste.substring(2, 66) + "\n" + "nome " + teste.substring(66, 162) + "\n" + "pai "
//				+ teste.substring(162, 226) + "\n" + "mes" + teste.substring(226, 230) + "\n" + "dia "
//				+ teste.substring(230, 235) + "\n" + "hora " + teste.substring(235, 240) + "\n" + "min "
//				+ teste.substring(240, 246) + "\n" + "permisao " + teste.substring(246, 255) + "\n" + "filhos "
//				+ teste.substring(255, 511) + "\n");
		//String binarioNome = nomeBinarioToNome(teste.substring(66, 162));
		String nome = Binario.binarioToString(getNomeBloco(teste));
		int ponteiro = Binario.binaryStringToInt(getEnderecoBloco(teste));
		//System.out.println("DIR = " + nome +"t"+teste.substring(66, 162));

			ArrayList<Diretorio> listaFilhos = filhos(getConteudoBloco(teste), hd);
	

			ArrayList<Arquivo> arquivos ;

		if(Integer.parseInt(getPaiBloco(teste))!=0)
		{
			 pai = blocoToDir(hd, hd.blocoToString(Integer.parseInt(getPaiBloco(teste)))) ;
		}
		else pai = null;
		
		String permissao = getPermissaoBloco(teste);
		
		//S String ponteiro = Binario.binarioToString(binario.substring(2,66));
		int mes, dia, hora, min;
		mes = Integer.parseInt(getMesBloco(teste));
		dia = Integer.parseInt(getDiaBloco(teste));
		hora = Integer.parseInt(getHoraBloco(teste));
		min = Integer.parseInt(getMinutoBloco(teste));
		String data  = mes+" "+dia+" "+hora+":"+min;
		

			Diretorio dir = new Diretorio(pai, nome);
			dir.setPonteiro(String.valueOf(ponteiro));
			dir.setData(data);
			dir.setMes(mes);
			dir.setDia(dia);
			dir.setHora(hora);
			dir.setMin(min);
			dir.setPermissao(permissao);
		//	ArrayList<Diretorio> listaFilhos = filhos(getConteudoBloco(teste), hd);
			dir.setFilhos(listaFilhos);
			dir.imprime();
			return dir;

		
	}

	public Arquivo blocoToArq(String teste, HardDisk hd) {
		teste = teste.replace("[", "").replace("]", "");
		Diretorio pai;
		// String binario = Binario.booleanToString(bloco);
//		System.err.println("\nDesconstroi tam " + teste.length() + "\ntype " + teste.substring(0, 2) + "\n"
//				+ "endereco " + teste.substring(2, 66) + "\n" + "nome " + teste.substring(66, 162) + "\n" + "pai "
//				+ teste.substring(162, 226) + "\n" + "mes" + teste.substring(226, 230) + "\n" + "dia "
//				+ teste.substring(230, 235) + "\n" + "hora " + teste.substring(235, 240) + "\n" + "min "
//				+ teste.substring(240, 246) + "\n" + "permisao " + teste.substring(246, 255) + "\n" + "filhos "
//				+ teste.substring(255, 511) + "\n");
		//String binarioNome = nomeBinarioToNome(teste.substring(66, 162));
		System.err.println("\t"+getNomeBloco(teste));
		String nome = Binario.binarioToString(getNomeBloco(teste));
		//System.out.println("DIR = " + nome +"t"+teste.substring(66, 162));

		if(Integer.parseInt(getPaiBloco(teste))!=0)
		{
			 pai = blocoToDir(hd, hd.blocoToString(Integer.parseInt(getPaiBloco(teste)))) ;
		}
		else pai = null;
		
		String permissao = getPermissaoBloco(teste);
		
		//S String ponteiro = Binario.binarioToString(binario.substring(2,66));
		int mes, dia, hora, min;
		mes = Integer.parseInt(getMesBloco(teste));
		dia = Integer.parseInt(getDiaBloco(teste));
		hora = Integer.parseInt(getHoraBloco(teste));
		min = Integer.parseInt(getMinutoBloco(teste));
		String data  = mes+" "+dia+" "+hora+":"+min;

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

	private static String getEnderecoBloco(String bloco) {
		return bloco.substring(2, 66);
	}

	private static String getNomeBloco(String bloco) {
		return bloco.substring(66, 162);
	}

	private static String getPaiBloco(String bloco) {
		return bloco.substring(162, 226);
	}

	private static String getMesBloco(String bloco) {
		return bloco.substring(226, 230);
	}

	private static String getDiaBloco(String bloco) {
		return bloco.substring(230, 235);
	}

	private static String getHoraBloco(String bloco) {
		return bloco.substring(235, 240);
	}

	private static String getMinutoBloco(String bloco) {
		return bloco.substring(240, 246);
	}

	private static String getPermissaoBloco(String bloco) {
		return bloco.substring(246, 255);
	}

	private static String getConteudoBloco(String bloco) {
		return bloco.substring(255, 511);
	}

	private static ArrayList<Diretorio> filhos(String filhosStr, HardDisk hd) {
		ArrayList<Diretorio> res = new ArrayList<>();
		System.out.println("TAMANHO FILHOS \t"+filhosStr.length());
		if(!filhosStr.contains(completaTamanho("0", filhosStr.length()-1)))
		for (int i = 0; i < filhosStr.length() ; i=i+64) {
			Diretorio dir = blocoToDir(hd,  hd.blocoToString(Integer.parseInt(filhosStr.substring(i, i+ 64))));
			System.out.println("FILHO "+dir.getNome());
			res.add(dir);

		}
		return res;
	}
	public static String setFilho(String filho, HardDisk hd, int bloco)
	{
		String blocoStr = hd.blocoToString(bloco);
		String filhosBloco = getConteudoBloco(blocoStr);
		for(int i=0;i<filhosBloco.length();i++)
		{
			if(filhosBloco.substring(i,i+64).contains(completaTamanho("", 64)))
			{
				filhosBloco = filhosBloco.substring(0,i)+filho+filhosBloco.substring(i+64,filhosBloco.length());
				
				i = filhosBloco.length();
			}
		}
		blocoStr = blocoStr.substring(0,255)+filhosBloco+blocoStr.charAt(blocoStr.length()-1);
		return blocoStr;
		
	}
	private static String getContnuacaoBloco(String bloco) {
		return bloco.substring(511, 512);
	}

	private static String getPaiContinuacaoBloco(String bloco) {
		return bloco.substring(0, 2);
	}

	private static String getConteudoContinuacaoBloco(String bloco) {
		return bloco.substring(0, 2);
	}

	private static String getSegmentoContinuacaoBloco(String bloco) {
		return bloco.substring(0, 2);
	}

	private static String setFilhoBloco(String bloco, String filho, int nFilho) {

		return bloco.substring(0, 255) + bloco.substring(255, 255 + nFilho * 64) + filho
				+ bloco.substring(255 + (nFilho + 1) * 64, 512);

	}

}
