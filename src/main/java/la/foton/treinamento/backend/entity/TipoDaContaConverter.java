package la.foton.treinamento.backend.entity;

import javax.persistence.AttributeConverter;

public class TipoDaContaConverter implements AttributeConverter<TipoDaConta, Integer>{

	@Override
	public Integer convertToDatabaseColumn(TipoDaConta attribute) {
		if(attribute.getChave() == null) {
			throw new IllegalArgumentException("Unknown " + attribute);
		}
		return attribute.getChave();
	}

	@Override
	public TipoDaConta convertToEntityAttribute(Integer value) {
        switch(value) {
        	case 1:
        		return TipoDaConta.CORRENTE;
        	case 2:
        		return TipoDaConta.POUPANCA;
        	default:
        		throw new IllegalArgumentException("Unknown " + value);
        }
	}
	
	

}
