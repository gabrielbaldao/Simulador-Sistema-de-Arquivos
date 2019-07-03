package arvore;

import java.util.ArrayList;

public class Diretorio {
	private String nome;
	private ArrayList<Diretorio> filhos;
	private ArrayList<Arquivo> arquivos;
	private Diretorio pai;
	private Diretorio self;
	private String permissao;
	private String data;
	private String ponteiro;
	private int mes,dia,hora,min;
	public int getMes() {
		return mes;
	}
	public void setMes(int mes) {
		this.mes = mes;
	}
	public int getDia() {
		return dia;
	}
	public void setDia(int dia) {
		this.dia = dia;
	}
	public int getHora() {
		return hora;
	}
	public void setHora(int hora) {
		this.hora = hora;
	}
	public int getMin() {
		return min;
	}
	public void setMin(int min) {
		this.min = min;
	}
	public Diretorio(Diretorio pai, String nome) {
		this.nome = nome;
		this.permissao = "777";
		setFilhos(new ArrayList<Diretorio>());
		setArquivos(new ArrayList<Arquivo>());
		if(pai == null)
		{
			this.setPai(this);
		}
		else this.setPai(pai);
		this.setSelf(this);

	}
	public void addDiretorio(Diretorio novo) 
	{
		this.filhos.add(novo);
	}
	public void rmDiretorio(Diretorio diretorio)
	{
		for (int i = 0; i < this.filhos.size(); i++) {
			if(this.filhos.get(i).getNome().equals(diretorio.getNome()))
			{
				this.filhos.remove(i);
				i = this.filhos.size();
			}
		}
	}
	public void addArquivo(Arquivo novo)
	{
		this.arquivos.add(novo);
	}
	public void rmArquivo(Arquivo arquivo)
	{
		for (int i = 0; i < this.arquivos.size(); i++) {
			if(this.arquivos.get(i).getNome().equals(arquivo.getNome()))
			{
				this.arquivos.remove(i);
				i = this.arquivos.size();
			}
		}
	}
	
	public ArrayList<Diretorio> getFilhos() {
		return filhos;
	}
	public void setFilhos(ArrayList<Diretorio> filhos) {
		this.filhos = filhos;
	}
	public ArrayList<Arquivo> getArquivos() {
		return arquivos;
	}
	public void setArquivos(ArrayList<Arquivo> arquivos) {
		this.arquivos = arquivos;
	}
	public Diretorio getPai() {
		return pai;
	}
	public void setPai(Diretorio pai) {
		this.pai = pai;
	}
	public Diretorio getSelf() {
		return self;
	}
	public void setSelf(Diretorio self) {
		this.self = self;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getPermissao() {
		return permissao;
	}
	public void setPermissao(String permissao) {
		this.permissao = permissao;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String imprime() {
		String result = "";
		for(int i=0;i<this.filhos.size();i++)
			result = result + "\n"+filhos.get(i).getNome() ;
		for(int j=0;j<this.arquivos.size();j++)
			result = result + "\n"+arquivos.get(j).getNome()+"."+arquivos.get(j).getTipo() ;
		//System.out.println(result);
		return result;
	}
	public String imprimeInfo() {
		String result = "";
		for(int i=0;i<this.filhos.size();i++)
			result = result + "\n"+filhos.get(i).getData()+"\t"+filhos.get(i).getNome()+"" ;
		for(int j=0;j<this.arquivos.size();j++)
			result = result + "\n"+arquivos.get(j).getData()+"\t"+arquivos.get(j).getNome()+"."+arquivos.get(j).getTipo() ;
		return result;
	}
	public int buscaDiretorio( String nome) {
		for(int i =0;i<filhos.size();i++)
		{

			if(filhos.get(i).getNome().compareTo(nome)==0 )
			{
				return i;
			}
		}
		return -1;
	}
	public boolean containsDir(String arquivo)
	{
		for (int i = 0; i < this.filhos.size(); i++) {
			if(this.filhos.get(i).getNome().equals(arquivo))
			{
				
//				i = this.filhos.size();
				return true;
			}
			
		}
		return false;
	}
	public boolean containsArq(String arquivo)
	{
		for (int i = 0; i < this.arquivos.size(); i++) {
			if(this.arquivos.get(i).getNome().equals(arquivo))
			{
				
//				i = this.filhos.size();
				return true;
			}
			
		}
		return false;
	}
	public String getPonteiro() {
		return ponteiro;
	}
	public void setPonteiro(String ponteiro) {
		this.ponteiro = ponteiro;
	}
	public void imprimeDir()
	{
		System.out.println("\n Funcao imprime Nome "+this.nome+"\n"+
							"Ponteiro "+this.ponteiro+"\n"
								);
	}
}
