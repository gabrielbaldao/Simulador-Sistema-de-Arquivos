package arvore;

public class Arquivo {
	private String nome;
	private String tipo;
	private String conteudo;
	private String permissao;
	private String data;
	private Diretorio pai;
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
	public Arquivo(String nome, String tipo, String conteudo, Diretorio pai, String data) {
		this.nome = nome;
		this.tipo = tipo;
		this.conteudo = conteudo;
		this.setPai(pai);
		this.data = data;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getConteudo() {
		return conteudo;
	}
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
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
	public String getPonteiro() {
		return ponteiro;
	}
	public void setPonteiro(String ponteiro) {
		this.ponteiro = ponteiro;
	}
	public Diretorio getPai() {
		return pai;
	}
	public void setPai(Diretorio pai) {
		this.pai = pai;
	}
	
}
