
//uma máquina de concatenar strings !!!

public class Principal {
	
	class BlocoMP {
		String[] palavras = new String[4];
	}
	
	class LinhaCache {
		String[] palavras = new String[4];
		int endBloco = -1;
		boolean atualizado = false;		
	}
	
	class Instrucao{
		int endBloco;
		int endPalavra;
		int opcode;
	}
	
	private Instrucao[] memoriaInst;
	private BlocoMP[] memoriaDados;
	private int PC=0;
	private int IR = -1; //valor q indica opcode 
	private int MAR = -1; // valor q indica endereco bloco ou linha
	private int MARp = -1; // valor q indica endereco da palavra
	private Instrucao MBR = null;
	private String MQ = "";
	
	private final int tCache1 = 4;
	private final int tCache2 = 6;
	
		
	private LinhaCache[] cacheNivel1 = new LinhaCache[tCache1];
	private LinhaCache[] cacheNivel2 = new LinhaCache[tCache2];
	
		
	private int cacheMissN1, cacheMissN2;
	private int cacheHitN1, cacheHitN2;

	
	public static void main(String[] args) {
				
		new Principal();
	}
	
	public Principal(){
		
		
		/*Opcode das instruções:
		-1: imprime todo o conteúdo da memória de dados!!!!
		0: imprime conteúdo do MAR
		1: busca conteúdo
		2: busca conteúdo e concatena com conteúdo armazenado no MQ
		3: armazena conteúdo MQ no endereço MAR
		4: termina
		5: etc....*/
		
		preencherMemorias();
		
		executarCPU_l1l2();
		
		System.err.println("numero de cache hits L1: " + cacheHitN1);
		System.err.println("numero de cache miss L1: " + cacheMissN1);
		
		System.err.println("numero de cache hits L2: " + cacheHitN2);
		System.err.println("numero de cache miss L2: " + cacheMissN2);
		
	}
	
	private void atualizaCache_2niveis(String[] palavras, int bloco){
		if(cacheNivel1[bloco%tCache1]!=null)
			cacheNivel1[bloco%tCache1].palavras = palavras;
		cacheNivel2[bloco%tCache2].palavras = palavras;
	}	
	
	private String[] MMU_acessoDireto_2niveis(Instrucao inst){
		int bloco = inst.endBloco;
		int linhaC1= bloco%tCache1;
		int linhaC2 = bloco%tCache2;
										
		try{
		
			if(cacheNivel1[linhaC1]==null){
				cacheMissN1++;
				if(cacheNivel2[linhaC2]==null){
										
						cacheMissN2++;
						LinhaCache lc = new LinhaCache();
						lc.palavras = memoriaDados[bloco].palavras;
						lc.endBloco = bloco;
						cacheNivel2[linhaC2] = lc;
						cacheNivel1[linhaC1] = lc;						
						return null;
				}else{
					int endBloco = cacheNivel2[linhaC2].endBloco;
					
					if(endBloco == bloco){
						cacheHitN2++;
						return cacheNivel2[linhaC2].palavras;
					}else {
						cacheMissN2++;
						cacheNivel2[linhaC2].palavras = memoriaDados[bloco].palavras;
						return null;
					}
				}
				
			}else{
				int endBloco = cacheNivel1[linhaC1].endBloco;
				
				if(endBloco == bloco){
					cacheHitN1++;
					return cacheNivel1[linhaC1].palavras;
				}else {
					cacheMissN1++;
					cacheNivel1[linhaC1].palavras = memoriaDados[bloco].palavras;
					
					if(cacheNivel2[linhaC2]==null){
						
						cacheMissN2++;
						LinhaCache lc = new LinhaCache();
						lc.palavras = memoriaDados[bloco].palavras;
						lc.endBloco = bloco;
						cacheNivel2[linhaC2] = lc;
												
						return null;
					}else{
						int endBlocoaux = cacheNivel2[linhaC2].endBloco;
						
						if(endBlocoaux == bloco){
							cacheHitN2++;
							return cacheNivel2[linhaC2].palavras;
						}else {
							cacheMissN2++;
							cacheNivel2[linhaC2].palavras = memoriaDados[bloco].palavras;
							return null;
						}
					}					
				}
			}
		}catch (Exception e){
			return null;
		}
		
	}	
	
	private void executarCPU_l1l2(){
		String[] dados = null;
		
		//começa o programa
		while (IR!=4){
			
			MBR = memoriaInst[PC];
			
			IR = MBR.opcode;
			MAR = MBR.endBloco;
			MARp = MBR.endPalavra;
			
			if(IR!=-1) 
				//testa politica de cache
				if((dados=MMU_acessoDireto_2niveis(MBR))==null)	
					if(MAR!=-1)
						dados = memoriaDados[MAR].palavras;
						
						
			switch (IR){
				case -1: {
					System.err.println("Inicio da Mem");
					for(BlocoMP bloco: memoriaDados)
						for(String palavra: bloco.palavras)
							System.err.println(palavra);
								
					System.err.println("Fim da Mem");
					break;
				}
				case 0: {
					System.err.println(dados[MARp]);
					break;
				}
				case 1: {
					MQ = dados[MARp];
					break;
				}
				case 2: {
					MQ = MQ.concat(dados[MARp]);
					break;
				}
				case 3: {
					memoriaDados[MAR].palavras[MARp] = MQ;
					atualizaCache_2niveis(dados, MAR);
					break;
				}
			}
			
			PC++;
		}
	}
	
	private void preencherMemorias(){
		montarMemoriaInst();
		montarMemoriaDados();
	}
	
	private void montarMemoriaInst(){
		//poderia ser feito usando alguma estrategia de geracao aleatoria de instrucoes!!!
		String[] memoriaSimples = {"1:6:0", "2:0:1", "2:4:2", "3:6:3", "1:6:1", "2:2:1", "2:0:3", "3:6:3", "1:3:2", "2:1:0", "3:1:1", "0:6:0", "0:1:3", "-1:-1", "4:-1"};
							//estas strings sao instrucoes para a maquina, 
							//daí eu adiciono quantas instruções eu queira e na ordem que eu queira !!!
							//temos uma maquina de concatenar
							// o mesmo pode ser feito para uma calculadora
							// o mesmo pode ser feito para um computador de uso geral, com ~500 instrucoes!!!
		memoriaInst = new Instrucao[memoriaSimples.length];
		for(int i=0; i<memoriaSimples.length; i++){
			Instrucao inst = new Instrucao();
			String[] aux = memoriaSimples[i].split(":");
			inst.opcode = Integer.parseInt(aux[0]);
			inst.endBloco = Integer.parseInt(aux[1]);
			if(aux.length>2)
				inst.endPalavra = Integer.parseInt(aux[2]);
			
			memoriaInst[i] = inst;
		}	
		
	}
	
	private void montarMemoriaDados(){
		String[][] memoriaSimples = {{"A", "B","C", "G"}, {"D", "E", "F", "J"}, {"G", "H", "I", "J"}, {"K", "L", "M", "N"}, {"O", "P", "Q", "R"}, {"S", "T", "U", "V"}, {"X", "Z", "Y", "W"}};
		memoriaDados = new BlocoMP[memoriaSimples.length];
		for(int i = 0; i< memoriaSimples.length; i++){
			BlocoMP bloco = new BlocoMP();
			for(int j=0; j<memoriaSimples[0].length; j++)
				bloco.palavras[j] = memoriaSimples[i][j];
			
			memoriaDados[i] = bloco;
		}		
	}

}
