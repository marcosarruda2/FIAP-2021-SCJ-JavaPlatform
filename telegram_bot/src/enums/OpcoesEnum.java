package enums;

public enum OpcoesEnum {

	//https://nomics.com/docs/#section/SDKs-and-Libraries/Community-Submissions-(Not-supported-by-Nomics)
    CRIPTO ("CRIPTO"),
    CHUCK ("CHUCK"),
    OPEN_WEATHER ("OPEN_WEATHER"),
    MENU ("MENU"),
    SIM_CRIPTO ("SIM_CRIPTO"),
    SIM_CHUCK ("SIM_CHUCK"),
    SIM_OPEN_WEATHER ("SIM_OPEN_WEATHER"),
    NAO_OPEN_WEATHER ("NAO_OPEN_WEATHER");
	
	
    OpcoesEnum (String opcao) {
        this.opcao = opcao;
    }
    
    public String opcao;
        

    public String getOpcao() {
        return opcao;
    }

}

      
 
    
