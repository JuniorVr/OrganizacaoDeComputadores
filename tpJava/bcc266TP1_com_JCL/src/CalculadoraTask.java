import implementations.dm_kernel.user.JCL_FacadeImpl;
import interfaces.kernel.JCL_facade;

public class CalculadoraTask {
	
	public void switchOpcao0(Integer MAR, Integer MAR1){
		JCL_facade jcl = JCL_FacadeImpl.getInstance();
		Integer content= (Integer) jcl.getValue("memoriaDados_"+MAR.intValue()).getCorrectResult();
		Integer content1= (Integer) jcl.getValue("memoriaDados_"+MAR1.intValue()).getCorrectResult();
		System.out.println(content);		
		System.out.println(content1);
		System.out.println("END TASK PRINT");
	}
	
	public Integer switchOpcao1(Integer MAR, Integer MAR1){
		JCL_facade jcl = JCL_FacadeImpl.getInstance();
		Integer content= (Integer) jcl.getValue("memoriaDados_"+MAR.intValue()).getCorrectResult();
		Integer content1= (Integer) jcl.getValueLocking("memoriaDados_"+MAR1.intValue()).getCorrectResult();
		Integer soma = new Integer(content.intValue()+content1.intValue());
		jcl.setValueUnlocking("memoriaDados_"+MAR1.intValue(), soma);
		
		System.out.println("END TASK SOMA => " + soma);
		
		return soma;
	}	
	
	public Integer switchOpcao2(Integer MAR, Integer MAR1){
		JCL_facade jcl = JCL_FacadeImpl.getInstance();
		Integer content= (Integer) jcl.getValue("memoriaDados_"+MAR.intValue()).getCorrectResult();
		Integer content1= (Integer) jcl.getValueLocking("memoriaDados_"+MAR1.intValue()).getCorrectResult();
		Integer sub = new Integer(content.intValue()-content1.intValue());
		jcl.setValueUnlocking("memoriaDados_"+MAR1.intValue(), sub);
		
		System.out.println("END TASK SUB => " + sub);
		
		return sub;
	}	
	
	public Integer switchOpcao3(Integer MAR, Integer MAR1){
		JCL_facade jcl = JCL_FacadeImpl.getInstance();
		Integer content= (Integer) jcl.getValue("memoriaDados_"+MAR.intValue()).getCorrectResult();
		Integer content1= (Integer) jcl.getValueLocking("memoriaDados_"+MAR1.intValue()).getCorrectResult();
		Integer mul = null;
		try{
			mul = new Integer(content.intValue()*content1.intValue());
		}catch (Exception e){
			mul = new Integer(0);
		}
						
		jcl.setValueUnlocking("memoriaDados_"+MAR1.intValue(), mul);
		
		System.out.println("END TASK MULT => " + mul);
		
		return mul;
	}
	
	public Integer switchOpcao4(Integer MAR, Integer MAR1){
		JCL_facade jcl = JCL_FacadeImpl.getInstance();
		Integer content= (Integer) jcl.getValue("memoriaDados_"+MAR.intValue()).getCorrectResult();
		Integer content1= (Integer) jcl.getValueLocking("memoriaDados_"+MAR1.intValue()).getCorrectResult();
		Integer div = null;
		try{
			div = new Integer(content.intValue()/content1.intValue());
		}catch (Exception e){
			div = new Integer(0);
		}
						
		jcl.setValueUnlocking("memoriaDados_"+MAR1.intValue(), div);
		
		System.out.println("END TASK MULT => " + div);
		
		return div;
	}

}
