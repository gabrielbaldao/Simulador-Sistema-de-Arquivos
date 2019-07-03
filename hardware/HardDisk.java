/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hardware;

import arvore.Arquivo;
import arvore.Diretorio;
import binary.Binario;

/**
 *
 * @author douglas
 */
public class HardDisk {
    
    private boolean hardDisk[];
    private Integer numeroDeBits;
    private Integer enderecamentoTamanho;
    private Integer tamanhoDoBloco = 512;
    private Integer listaDeBlocos;
    private Integer atual;
    private Integer raiz = 0;
    public HardDisk (Integer tamanhoDaMemoriaSecundaria){
        this.numeroDeBits = tamanhoDaMemoriaSecundaria * 8 * 1024 * 1024;
        this.hardDisk = new boolean[this.numeroDeBits];
        this.enderecamentoTamanho = hardDisk.length/tamanhoDoBloco;
        this.listaDeBlocos = (int)(hardDisk.length/Math.pow(tamanhoDoBloco, 2));
        setAtual(0);
       // System.out.println("Numero de Bits\t"+numeroDeBits+"\n quantos blocos "+enderecamentoTamanho);
        inicializarMemoriaSecundaria();
    }
    
    public void inicializarMemoriaSecundaria (){
        for (int i = 0; i < numeroDeBits; i++) {
        
            this.hardDisk[i] = false;
        }
    }
  
    private boolean[] buscaBloco(int posicao)
    {
    	boolean[] bloco = new boolean[tamanhoDoBloco];
    	int j = 0;
    	for(int i = posicao;i< tamanhoDoBloco+posicao ; i++)
    	{
    		bloco[j++] = getBitDaPosicao(i);
    	}
    	return bloco;
    }
    public boolean[] stringToBloco(String str)
    {

    	if(str.length()!=tamanhoDoBloco )
    		{
    		error("Bloco endereçado para setar, possui tamanho diferente do pedido");
    		}
    	else
    	{
        	boolean[] bloco = new boolean[tamanhoDoBloco];
    		for(int i = 0; i < str.length();i++)
    		{
    			if(str.charAt(i) == '1')
    			{
    				bloco[i] = true;
    			}
    			else bloco[i] = false;
    		}
    		return bloco;
    	}
		return null;
    	
    }
    public String blocoToString(int blocoPosicao)
    {
    	int blocoReal = posicaoBloco(blocoPosicao);
    	
    	StringBuilder blocoStr = new StringBuilder();
    	
    	if(getBitDaPosicao(blocoPosicao))
    	{
    		
    		for(int j=0 ; j<512;j++ )
    		{
    			if(getBitDaPosicao(blocoReal+j))
    				blocoStr.append("1");
    			else blocoStr.append("0");
    			
    				
    		}
    		
    	}
    	//System.out.println("\n\n posicao "+blocoStr.toString());
    	return blocoStr.toString();
    }
   public boolean setBloco(String str, int blocoPosicao)
   {
	   int blocoReal = posicaoBloco(blocoPosicao);
	   if(this.getBitDaPosicao(blocoPosicao) == true)
	   {
		   int j=0;
		   for(int i = blocoReal;i<blocoReal+512;i++)
	   
		   {
		   if(str.charAt(j++) == '1')
		   {
			   setBitDaPosicao(true, i);
		   }
		   else 
			   {
			   setBitDaPosicao(false, i);
			   }
		   }
		   System.out.println("Possivel nome "+this.blocoToString(blocoPosicao).substring(66,162));
		  System.out.println("---"+Binario.binarioToString(this.blocoToString(blocoPosicao).substring(66,162)));
		   return true;
	   }
	   else return false;
   }
 
    public int buscaPosicaoLivre()
    {
    	for (int i = 0; i < enderecamentoTamanho; i++) {
			if(getBitDaPosicao(i) == false)
				{
				setBitDaPosicao(true, i);
				return i;
				}
		}
    	return -1; //disco cheio
    }
    public int buscaEsetaPosicao()
    {
    	int pos = buscaPosicaoLivre();
    	setBitDaPosicao(true, pos);
    	return pos;
    }
    private void setBitDaPosicao (boolean bit, int posicao){
        this.hardDisk[posicao] = bit;
    }
    
    public boolean getBitDaPosicao (int i){
        return hardDisk[i];
    }
    private void error(String str)
    {
    	System.err.println(str);
    }
    public void imprimeOcupados()
    {
    	System.out.println("numero de blocos "+(numeroDeBits/512));
    	System.out.println("enderecar esses malucos "+ Math.log(numeroDeBits/512)/Math.log(2));
    	System.out.println(listaDeBlocos);
    	System.out.println("------------------------------");
    	System.out.println(blocoToString(0));
    	for(int i=0;i<10;i++)
    	{
    		System.out.println(this.hardDisk[i]);
    	}
    	
//    	for(int j = listaDeBlocos; j < listaDeBlocos + 512*5;j++)
//    	{
//    		if(this.hardDisk[j])
//    			System.out.print("1");
//    		else System.out.print("0");
//    	}
    }
    public int posicaoBloco(int pos)
    {
    	
    	return ((numeroDeBits)/512)*pos+enderecamentoTamanho;
    }
    public Diretorio buscaParametro(String parametro)
    {

		String[] aux = parametro.split("/");
		System.out.println(getAtual()+"Teste "+Abstracao.blocoToDir(this, this.blocoToString(getAtual())).getNome());
		if(parametro.equals(""))
			return Abstracao.blocoToDir(this, this.blocoToString(getAtual()));
		if(parametro.charAt(0)== '/')
		{
			System.out.println("\n\n\t\tRAIZ");
			return buscaDiretorio(Abstracao.blocoToDir(this, this.blocoToString(getRaiz())), aux, 1);
		}
		else {
			return buscaDiretorio(Abstracao.blocoToDir(this, this.blocoToString(getAtual())), aux, 0);
		}
    }
    static boolean Naoencontrou = false;
	public Diretorio buscaDiretorio(Diretorio atual,String[] parametro, int j)
	{

		System.out.println("Entrou busca diretorio\n"+
						"Diretorio >"+atual.getFilhos().size()+"<");
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
						System.out.println("Encontrou");
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
	public Integer getAtual() {
		return atual;
	}

	public void setAtual(Integer atual) {
		this.atual = atual;
	}

	public Integer getRaiz() {
		return raiz;
	}

	public void setRaiz(Integer raiz) {
		this.raiz = raiz;
	}
}
