import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import implementations.dm_kernel.user.JCL_FacadeImpl;
import interfaces.kernel.JCL_facade;
import interfaces.kernel.JCL_result;

public class CalculadoraJCL {
	private JCL_facade jcl;
		
	public static void main(String[] args) {
		
		try {
			new CalculadoraJCL();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public CalculadoraJCL() throws InterruptedException, ExecutionException{
		
		
		/*Opcode das instruções:
		
		0: imprime conteúdo da memória, ou seja, conteudo de MAR E MAR1
		1: soma
		2: subtrai
		3: mul
		4: div
		5: end*/
		
		jcl = JCL_FacadeImpl.getInstance();
		
		montarMemoriaInst();
		
		montarMemoriaDados();
		
		
		int PC=0;
		int IR = -1; //valor q indica opcode 
		int MAR = -1; // valor q indica endereco 
		int MAR1 = -1; 
		String MBR = "";
		String MQ = "";
		
		//registrando a task q executará em N máquinas
		Boolean b = jcl.register(CalculadoraTask.class, "CalculadoraTask");
		System.err.println(b);	
		
		List<Future> tickets = new LinkedList<Future>();
	
				
		//começa o programa
		while (IR!=5){
			MBR = jcl.getValue("memoriaInst_"+PC).getCorrectResult().toString();
			
			String[] aux = MBR.split(":");
			
			IR = Integer.parseInt(aux[0]);
			MAR = Integer.parseInt(aux[1]);
			MAR1 = Integer.parseInt(aux[2]);
			
			switch (IR){
				case 0: {
					Object[] args ={new Integer(MAR), new Integer(MAR1)};
					Future ticket = jcl.execute("CalculadoraTask", "switchOpcao0", args);
					tickets.add(ticket);
					break;
				}
				case 1: {
					Object[] args ={new Integer(MAR), new Integer(MAR1)};
					Future ticket = jcl.execute("CalculadoraTask", "switchOpcao1", args);
					tickets.add(ticket);
					break;
				}
				case 2: {
					Object[] args ={new Integer(MAR), new Integer(MAR1)};
					Future ticket = jcl.execute("CalculadoraTask", "switchOpcao2", args);
					tickets.add(ticket);
					break;
				}
				case 3: {
					Object[] args ={new Integer(MAR), new Integer(MAR1)};
					Future ticket = jcl.execute("CalculadoraTask", "switchOpcao3", args);
					tickets.add(ticket);
					break;
				}
				case 4: {
					Object[] args ={new Integer(MAR), new Integer(MAR1)};
					Future ticket = jcl.execute("CalculadoraTask", "switchOpcao4", args);
					tickets.add(ticket);
					break;
				}
			}
			
			PC++;
		}//end while
		
		//coletar os resultados remanescentes 
		for(Future str:tickets){
			JCL_result jclR=(JCL_result) str.get();
			System.out.println("RESULT: " + jclR.getCorrectResult().toString());
		}		
		
		//permite que o cluster possa ser reutilizado n vezes sem
		//ter q desligar e ligar o cluster n vezes
		//jcl.cleanEnvironment();
		jcl.destroy();
	}
	
	private void montarMemoriaInst(){
		Random r = new Random();
		for(int i=0; i<1000; i++){
			int j= r.nextInt(1000);
			int k = r.nextInt(1000);
			int opCode = r.nextInt(5);
			jcl.instantiateGlobalVar("memoriaInst_"+i,opCode+":"+j+":"+k);
		}
		
		jcl.instantiateGlobalVar("memoriaInst_"+1000,5+":"+-1+":"+-1);
	}
	
	private void montarMemoriaDados(){
		Random r = new Random();
		for (int i=0; i<1000; i++)
			jcl.instantiateGlobalVar(("memoriaDados_"+i), new Integer(r.nextInt()));
		
		
	}

}
