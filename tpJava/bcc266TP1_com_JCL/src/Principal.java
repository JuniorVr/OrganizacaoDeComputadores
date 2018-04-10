import implementations.dm_kernel.user.JCL_FacadeImpl;
import interfaces.kernel.JCL_facade;
import interfaces.kernel.JCL_result;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;




//uma máquina de concatenar strings !!!

public class Principal {
	
	private JCL_facade jcl;
	private int numeroDados;

	public static void main(String[] args) {
				
		try {
			new Principal();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Principal() throws InterruptedException, ExecutionException{
		
		
		/*Opcode das instruções:
		-1: imprime todo o conteúdo da memória de dados!!!!
		0: imprime conteúdo do MAR
		1: busca conteúdo
		2: busca conteúdo e concatena com conteúdo armazenado no MQ
		3: armazena conteúdo MQ no endereço MAR
		4: termina
		5: etc....*/
		
		jcl = JCL_FacadeImpl.getInstance();
		
		montarMemoriaInst();
		
		montarMemoriaDados();
		
		
		int PC=0;
		int IR = -1; //valor q indica opcode 
		int MAR = -1; // valor q indica endereco 
		String MBR = "";
		String MQ = "";
		
		//registrando a task q executará em N máquinas
		Boolean b = jcl.register(JCL_Tasks.class, "JCL_Tasks");
		System.err.println(b);	
		
		List<Future> tickets = new LinkedList<Future>();
				
		//começa o programa
		while (IR!=4){
			MBR = jcl.getValue("memoriaInst_"+PC).getCorrectResult().toString();
			
			String[] aux = MBR.split(":");
			
			IR = Integer.parseInt(aux[0]);
			MAR = Integer.parseInt(aux[1]);
			
			switch (IR){
				case -1: {
					Object[] args ={new Integer(numeroDados)};
					Future ticket = jcl.execute("JCL_Tasks", "switchOpcao1", args);
					tickets.add(ticket);
					break;
				}
				case 0: {
					Object[] args ={new Integer(MAR)};
					Future ticket = jcl.execute("JCL_Tasks", "switchOpcao2", args);
					tickets.add(ticket);
					break;
				}
				case 1: {
					Object[] args ={new Integer(MAR)};
					Future ticket = jcl.execute("JCL_Tasks", "switchOpcao3", args);
					JCL_result jclr = (JCL_result) ticket.get();
					MQ = jclr.getCorrectResult().toString();
					break;
				}
				case 2: {
					Object[] args ={new Integer(MAR), MQ};
					Future ticket = jcl.execute("JCL_Tasks", "switchOpcao4", args);
					JCL_result jclr = (JCL_result) ticket.get();
					MQ = jclr.getCorrectResult().toString();
					System.out.println(MQ);
					break;
				}
				case 3: {
					Object[] args ={new Integer(MAR), MQ};
					Future ticket = jcl.execute("JCL_Tasks", "switchOpcao5", args);
					tickets.add(ticket);
					break;
				}
			}
			
			PC++;
		}//end while
		
		//coletar os resultados remanescentes 
		for(Future str:tickets)
			str.get();

		//SOMENTE PARA FORCAR a impressao da memoria ao final
		Object[] args ={new Integer(numeroDados)};
		Future ticket = jcl.execute("JCL_Tasks", "switchOpcao1", args);
		ticket.get();
		
		
		//permite que o cluster possa ser reutilizado n vezes sem
		//ter q desligar e ligar o cluster n vezes
		jcl.cleanEnvironment();
		jcl.destroy();
	}
	
	private void montarMemoriaInst(){
		//poderia ser feito usando alguma estrategia de geracao aleatoria de instrucoes!!!
		
		jcl.instantiateGlobalVar("memoriaInst_0", "1:6");
		jcl.instantiateGlobalVar("memoriaInst_1", "2:7");
		jcl.instantiateGlobalVar("memoriaInst_2", "2:13");
		jcl.instantiateGlobalVar("memoriaInst_3", "3:6");
		jcl.instantiateGlobalVar("memoriaInst_4", "1:6");
		jcl.instantiateGlobalVar("memoriaInst_5", "2:18");
		jcl.instantiateGlobalVar("memoriaInst_6", "2:0");
		jcl.instantiateGlobalVar("memoriaInst_7", "3:6");
		jcl.instantiateGlobalVar("memoriaInst_8", "1:8");
		jcl.instantiateGlobalVar("memoriaInst_9", "2:22");
		jcl.instantiateGlobalVar("memoriaInst_10", "3:15");
		jcl.instantiateGlobalVar("memoriaInst_11", "0:6");
		jcl.instantiateGlobalVar("memoriaInst_12", "0:15");
		jcl.instantiateGlobalVar("memoriaInst_13", "-1:-1");
		jcl.instantiateGlobalVar("memoriaInst_14", "4:-1");
		
							//estas strings sao instrucoes para a maquina, 
							//daí eu adiciono quantas instruções eu queira e na ordem que eu queira !!!
							//temos uma maquina de concatenar
							// o mesmo pode ser feito para uma calculadora
							// o mesmo pode ser feito para um computador de uso geral, com ~500 instrucoes!!!
		
	}
	
	private void montarMemoriaDados(){
		Random r = new Random();
		for (int i=0; i<100; i++)
			jcl.instantiateGlobalVar(("memoriaDados_"+i), String.valueOf(r.nextInt()));
		
		this.numeroDados = 100;
	}
	
	

}

