import implementations.dm_kernel.user.JCL_FacadeImpl;
import interfaces.kernel.JCL_facade;


public class JCL_Tasks {
	
	public void switchOpcao1(Integer numeroDados){
		JCL_facade jcl = JCL_FacadeImpl.getInstance();
		
		for(int i=0; i<numeroDados.intValue(); i++){
			String dados = jcl.getValue("memoriaDados_"+i).getCorrectResult().toString();
			System.out.println(dados);
		}
		
		System.out.println("END TASK 1");
	}
	
	public void switchOpcao2(Integer MAR){
		JCL_facade jcl = JCL_FacadeImpl.getInstance();
		
		String dados = jcl.getValue("memoriaDados_"+MAR.intValue()).getCorrectResult().toString();
		System.out.println(dados);		
		System.out.println("END TASK 2");
	}
	
	public String switchOpcao3(Integer MAR){
		JCL_facade jcl = JCL_FacadeImpl.getInstance();
		
		String dados = jcl.getValue("memoriaDados_"+MAR.intValue()).getCorrectResult().toString();
		
		System.out.println("END TASK 3");
		
		return dados;
	}
	
	public String switchOpcao4(Integer MAR, String MQ){
		JCL_facade jcl = JCL_FacadeImpl.getInstance();
		
		String dados = jcl.getValue("memoriaDados_"+MAR.intValue()).getCorrectResult().toString();
		
		MQ +=("!@#$%¨>>>>>");
		MQ+=(dados);
		
		System.out.println(MQ+ "END TASK 4");
		
		return MQ;
	}
	
	public void switchOpcao5(Integer MAR, String MQ){
		JCL_facade jcl = JCL_FacadeImpl.getInstance();
		
		String dados = jcl.getValueLocking("memoriaDados_"+MAR.intValue()).getCorrectResult().toString();
		dados = MQ;
		jcl.setValueUnlocking("memoriaDados_"+MAR.intValue(), dados);
		
		System.out.println("END TASK 5");
	}

}
