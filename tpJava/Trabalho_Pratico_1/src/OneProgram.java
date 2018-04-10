import java.util.Random;

public class OneProgram {

	public static Instruction[] createOneProgramAleatory(int memorySize, int opCodeSize) {
		Random r = new Random();
		// o programa a ser criado pode conter entre 1 e 100k instru��es
		int numInstructions = r.nextInt(1000);

		Instruction[] allCode = new Instruction[numInstructions];

		for (int i = 0; i < numInstructions - 1; i++) {
			Instruction inst = new Instruction();
			int addressOne = r.nextInt(memorySize);
			int addressTwo = r.nextInt(memorySize);
			int addressThree = r.nextInt(memorySize);
			int opCode = r.nextInt(opCodeSize);
			inst.createInstruction(opCode, addressOne, addressTwo, addressThree);
			allCode[i] = inst;
		}

		// garantindo que a ultima instru��o tenha opcode igual a HALT
		Instruction inst = new Instruction();
		inst.createInstruction(-1, -1, -1, -1);
		allCode[numInstructions - 1] = inst;

		return allCode;

	}

	// num1 e num2 sao os valores a serem multiplicados
	public static Instruction[] createOneProgramMultiply(int num1, int num2) {

		Instruction[] allCode = new Instruction[num2 + 3];
		// inserindo num1 na memoria na posicao 0
		Instruction inst0 = new Instruction();
		inst0.createInstruction(2, num1, 0, -1);
		allCode[0] = inst0;

		// zerando a memoria na posicao 1
		Instruction inst1 = new Instruction();
		inst1.createInstruction(2, 0, 1, -1);
		allCode[1] = inst1;

		// fazendo somas sucessivas
		for (int i = 0; i < num2; i++) {
			Instruction inst = new Instruction();
			inst.createInstruction(0, 0, 1, 1);
			allCode[i + 2] = inst;
		}

		// garantindo que a ultima instru��o tenha opcode igual a HALT
		Instruction instLast = new Instruction();
		instLast.createInstruction(-1, -1, -1, -1);
		allCode[num2 + 2] = instLast;

		return allCode;

	}

	public static Instruction[] criarUmProgramaPa(int a1, int n,int r) {
		Instruction[] allCode = new Instruction[r + 7];
		// inserindo n na memoria na posicao 0
		Instruction inst0 = new Instruction();
		inst0.createInstruction(2, n, 0, -1);
		allCode[0] = inst0;
		
		// inserindo 1 na memoria na posicao 1
		Instruction inst1 = new Instruction();
		inst1.createInstruction(2, 1, 1, -1);
		allCode[1] = inst1;
		
		// subtraindo n com menos 1
		Instruction inst2 = new Instruction();
		inst2.createInstruction(1,0,1,0);
		allCode[2] = inst2;
		
		// inserindo 0 na memoria na posicao 1
		Instruction inst3 = new Instruction();
		inst3.createInstruction(2, 0, 1, -1);
		allCode[3] = inst3;
		
		// fazendo somas sucessivas
		for (int i = 0; i < r ; i++) {
			Instruction inst = new Instruction();
			inst.createInstruction(0, 0, 1, 1);
			allCode[i + 4] = inst;
		}
		
		// inserindo 0 na memoria na posicao 1
		Instruction inst4 = new Instruction();
		inst4.createInstruction(2, a1, 0, -1);
		allCode[(r + 4)] = inst4;
		
		// subtraindo a1 com [(n-1)*r]
		Instruction inst5 = new Instruction();
		inst5.createInstruction(0,0,1,1);
		allCode[(r+5)] = inst5;
		
		// garantindo que a ultima instru��o tenha opcode igual a HALT
		Instruction instLast = new Instruction();
		instLast.createInstruction(-1, -1, -1, -1);
		allCode[(r + 6)] = instLast;

		return allCode;
	}

}
