

package hardware;

import arvore.Arquivo;
import arvore.Diretorio;
import binary.Binario;


public class HardDisk {
    
    private boolean hardDisk[];
    private Integer numeroDeBits;
    private Integer enderecamentoTamanho;
    private Integer tamanhoDoBloco = 512;
    private Integer listaDeBlocos;
    private Integer atual=0;
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
    public int getBlocos()
    {
    	return enderecamentoTamanho;
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
    public void setBitDaPosicao (boolean bit, int posicao){
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