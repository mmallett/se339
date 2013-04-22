package edu.iastate.se339.text;

public abstract class AbstractDecorator extends AbstractRepresentation {
	
	protected AbstractRepresentation component;
	
	public AbstractDecorator(AbstractRepresentation component){
		this.component = component;
	}

	@Override
	public abstract String toString();

}
